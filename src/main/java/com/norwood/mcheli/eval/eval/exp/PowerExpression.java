/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class PowerExpression extends Col2Expression {
/*    */   public PowerExpression() {
/*  5 */     setOperator("**");
/*    */   }
/*    */   
/*    */   protected PowerExpression(PowerExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new PowerExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 19 */     return (long)Math.pow(vl, vr);
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 24 */     return Math.pow(vl, vr);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 29 */     return this.share.oper.power(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\PowerExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */