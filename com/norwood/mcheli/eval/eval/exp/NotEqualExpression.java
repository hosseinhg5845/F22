/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class NotEqualExpression extends Col2Expression {
/*    */   public NotEqualExpression() {
/*  5 */     setOperator("!=");
/*    */   }
/*    */   
/*    */   protected NotEqualExpression(NotEqualExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new NotEqualExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 19 */     return (vl != vr) ? 1L : 0L;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 24 */     return (vl != vr) ? 1.0D : 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 29 */     return this.share.oper.notEqual(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\NotEqualExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */