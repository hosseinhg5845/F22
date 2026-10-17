/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class ShiftLeftExpression extends Col2Expression {
/*    */   public ShiftLeftExpression() {
/*  5 */     setOperator("<<");
/*    */   }
/*    */   
/*    */   protected ShiftLeftExpression(ShiftLeftExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new ShiftLeftExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 19 */     return vl << (int)vr;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 24 */     return vl * Math.pow(2.0D, vr);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 29 */     return this.share.oper.shiftLeft(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\ShiftLeftExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */