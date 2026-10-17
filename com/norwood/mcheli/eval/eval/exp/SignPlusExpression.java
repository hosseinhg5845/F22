/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class SignPlusExpression extends Col1Expression {
/*    */   public SignPlusExpression() {
/*  5 */     setOperator("+");
/*    */   }
/*    */   
/*    */   protected SignPlusExpression(SignPlusExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new SignPlusExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long val) {
/* 19 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double val) {
/* 24 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 29 */     return this.share.oper.signPlus(this.exp.evalObject());
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\SignPlusExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */