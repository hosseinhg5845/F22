/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class SignMinusExpression extends Col1Expression {
/*    */   public SignMinusExpression() {
/*  5 */     setOperator("-");
/*    */   }
/*    */   
/*    */   protected SignMinusExpression(SignMinusExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new SignMinusExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long val) {
/* 19 */     return -val;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double val) {
/* 24 */     return -val;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 29 */     return this.share.oper.signMinus(this.exp.evalObject());
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\SignMinusExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */