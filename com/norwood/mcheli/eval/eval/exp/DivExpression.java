/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class DivExpression extends Col2Expression {
/*    */   public DivExpression() {
/*  5 */     setOperator("/");
/*    */   }
/*    */   
/*    */   protected DivExpression(DivExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new DivExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 19 */     return vl / vr;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 24 */     return vl / vr;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 29 */     return this.share.oper.div(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\DivExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */