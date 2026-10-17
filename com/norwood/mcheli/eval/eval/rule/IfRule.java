/*    */ package com.norwood.mcheli.eval.eval.rule;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.EvalException;
/*    */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*    */ import com.norwood.mcheli.eval.eval.exp.Col3Expression;
/*    */ import com.norwood.mcheli.eval.eval.lex.Lex;
/*    */ 
/*    */ public class IfRule extends AbstractRule {
/*    */   public AbstractExpression cond;
/*    */   
/*    */   public IfRule(ShareRuleValue share) {
/* 12 */     super(share);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression parse(Lex lex) {
/* 17 */     AbstractExpression x = this.nextRule.parse(lex);
/* 18 */     if (lex.getType() == 2147483634) {
/* 19 */       String ope = lex.getOperator();
/* 20 */       int pos = lex.getPos();
/* 21 */       if (isMyOperator(ope) && lex.isOperator(this.cond.getOperator())) {
/* 22 */         x = parseCond(lex, x, ope, pos);
/*    */       }
/*    */       
/* 25 */       return x;
/*    */     } 
/* 27 */     return x;
/*    */   }
/*    */   
/*    */   protected AbstractExpression parseCond(Lex lex, AbstractExpression x, String ope, int pos) {
/* 31 */     AbstractExpression y = parse(lex.next());
/* 32 */     if (!lex.isOperator(this.cond.getEndOperator())) {
/* 33 */       throw new EvalException(1001, new String[] { this.cond.getEndOperator() }, lex);
/*    */     }
/* 35 */     AbstractExpression z = parse(lex.next());
/* 36 */     return Col3Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\rule\IfRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */