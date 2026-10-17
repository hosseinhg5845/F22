/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class ShiftRightLogicalExpression extends Col2Expression {
/*    */   public ShiftRightLogicalExpression() {
/*  5 */     setOperator(">>>");
/*    */   }
/*    */   
/*    */   protected ShiftRightLogicalExpression(ShiftRightLogicalExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new ShiftRightLogicalExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 19 */     return vl >>> (int)vr;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 24 */     if (vl < 0.0D) {
/* 25 */       vl = -vl;
/*    */     }
/*    */     
/* 28 */     return vl / Math.pow(2.0D, vr);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 33 */     return this.share.oper.shiftRightLogical(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\ShiftRightLogicalExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */