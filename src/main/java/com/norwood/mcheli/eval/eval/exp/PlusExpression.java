/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class PlusExpression extends Col2Expression {
/*    */   public PlusExpression() {
/*  5 */     setOperator("+");
/*    */   }
/*    */   
/*    */   protected PlusExpression(PlusExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new PlusExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 19 */     return vl + vr;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 24 */     return vl + vr;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 29 */     return this.share.oper.plus(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\PlusExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */