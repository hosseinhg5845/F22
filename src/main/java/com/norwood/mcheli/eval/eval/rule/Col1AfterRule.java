/*    */ package com.norwood.mcheli.eval.eval.rule;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.EvalException;
/*    */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*    */ import com.norwood.mcheli.eval.eval.exp.Col1Expression;
/*    */ import com.norwood.mcheli.eval.eval.exp.Col2Expression;
/*    */ import com.norwood.mcheli.eval.eval.exp.FunctionExpression;
/*    */ import com.norwood.mcheli.eval.eval.lex.Lex;
/*    */ 
/*    */ public class Col1AfterRule extends AbstractRule {
/*    */   public AbstractExpression func;
/*    */   public AbstractExpression array;
/*    */   public AbstractExpression field;
/*    */   
/*    */   public Col1AfterRule(ShareRuleValue share) {
/* 16 */     super(share);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression parse(Lex lex) {
/* 21 */     AbstractExpression x = this.nextRule.parse(lex);
/*    */ 
/*    */     
/* 24 */     while (lex.getType() == 2147483634) {
/* 25 */       String ope = lex.getOperator();
/* 26 */       int pos = lex.getPos();
/* 27 */       if (!isMyOperator(ope)) {
/* 28 */         return x;
/*    */       }
/*    */       
/* 31 */       if (lex.isOperator(this.func.getOperator())) {
/* 32 */         x = parseFunc(lex, x); continue;
/* 33 */       }  if (lex.isOperator(this.array.getOperator())) {
/* 34 */         x = parseArray(lex, x, ope, pos); continue;
/* 35 */       }  if (lex.isOperator(this.field.getOperator())) {
/* 36 */         x = parseField(lex, x, ope, pos); continue;
/*    */       } 
/* 38 */       x = Col1Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x);
/* 39 */       lex.next();
/*    */     } 
/*    */     
/* 42 */     return x;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression parseFunc(Lex lex, AbstractExpression x) {
/* 48 */     AbstractExpression a = null;
/* 49 */     lex.next();
/* 50 */     if (!lex.isOperator(this.func.getEndOperator())) {
/* 51 */       a = this.share.funcArgRule.parse(lex);
/* 52 */       if (!lex.isOperator(this.func.getEndOperator())) {
/* 53 */         throw new EvalException(1001, new String[] { this.func.getEndOperator() }, lex);
/*    */       }
/*    */     } 
/*    */     
/* 57 */     lex.next();
/* 58 */     return FunctionExpression.create(x, a, this.prio, lex.getShare());
/*    */   }
/*    */   
/*    */   protected AbstractExpression parseArray(Lex lex, AbstractExpression x, String ope, int pos) {
/* 62 */     AbstractExpression y = this.share.topRule.parse(lex.next());
/* 63 */     if (!lex.isOperator(this.array.getEndOperator())) {
/* 64 */       throw new EvalException(1001, new String[] { this.array.getEndOperator() }, lex);
/*    */     }
/* 66 */     lex.next();
/* 67 */     return Col2Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression parseField(Lex lex, AbstractExpression x, String ope, int pos) {
/* 72 */     AbstractExpression y = this.nextRule.parse(lex.next());
/* 73 */     return Col2Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\rule\Col1AfterRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */