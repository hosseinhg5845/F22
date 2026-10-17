/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class GreaterEqualExpression extends Col2Expression {
/*    */   public GreaterEqualExpression() {
/*  5 */     setOperator(">=");
/*    */   }
/*    */   
/*    */   protected GreaterEqualExpression(GreaterEqualExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new GreaterEqualExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 19 */     return (vl >= vr) ? 1L : 0L;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 24 */     return (vl >= vr) ? 1.0D : 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 29 */     return this.share.oper.greaterEqual(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\GreaterEqualExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */