package com.norwood.mcheli.eval.eval.rule;

import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
import com.norwood.mcheli.eval.eval.exp.Col1Expression;
import com.norwood.mcheli.eval.eval.exp.NumberExpression;
import com.norwood.mcheli.eval.eval.exp.ParenExpression;
import com.norwood.mcheli.eval.eval.exp.StringExpression;
import com.norwood.mcheli.eval.eval.exp.VariableExpression;
import com.norwood.mcheli.eval.eval.lex.Lex;

public class PrimaryRule extends AbstractRule {
    public PrimaryRule(ShareRuleValue share) {
        super(share);
    }

    @Override
    public AbstractExpression parse(Lex lex) {
        int type = lex.getType();
        if (type == Lex.TYPE_NUM) {
            return NumberExpression.create(lex, this.prio);
        }
        if (type == Lex.TYPE_WORD) {
            return VariableExpression.create(lex, this.prio);
        }
        if (type == Lex.TYPE_STRING) {
            return StringExpression.create(lex, this.prio);
        }
        if (type == Lex.TYPE_OPE) {
            String ope = lex.getOperator();
            if (isMyOperator(ope)) {
                int pos = lex.getPos();
                AbstractExpression x = newExpression(ope, lex.getShare());
                // The ParenExpression parses a parenthesized sub-expression.
                // Skip the open paren, parse the inner expression via the
                // next rule, then skip the close paren.
                lex.next();
                AbstractExpression inner = this.nextRule.parse(lex);
                lex.next();   // skip ')'
                return Col1Expression.create(x, lex.getString(), pos, inner);
            }
        }
        return this.nextRule.parse(lex);
    }
}
