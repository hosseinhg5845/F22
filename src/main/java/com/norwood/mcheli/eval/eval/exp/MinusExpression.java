/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class MinusExpression extends Col2Expression {
/*    */   public MinusExpression() {
/*  5 */     setOperator("-");
/*    */   }
/*    */   
/*    */   protected MinusExpression(MinusExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new MinusExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 19 */     return vl - vr;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 24 */     return vl - vr;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 29 */     return this.share.oper.minus(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\MinusExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */