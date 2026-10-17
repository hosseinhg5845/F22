/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class BitXorExpression extends Col2Expression {
/*    */   public BitXorExpression() {
/*  5 */     setOperator("^");
/*    */   }
/*    */   
/*    */   protected BitXorExpression(BitXorExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new BitXorExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 19 */     return vl ^ vr;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 24 */     return ((long)vl ^ (long)vr);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 29 */     return this.share.oper.bitXor(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\BitXorExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */