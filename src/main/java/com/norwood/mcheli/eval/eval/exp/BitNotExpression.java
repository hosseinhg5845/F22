/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class BitNotExpression extends Col1Expression {
/*    */   public BitNotExpression() {
/*  5 */     setOperator("~");
/*    */   }
/*    */   
/*    */   protected BitNotExpression(BitNotExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new BitNotExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long val) {
/* 19 */     return val ^ 0xFFFFFFFFFFFFFFFFL;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double val) {
/* 24 */     return ((long)val ^ 0xFFFFFFFFFFFFFFFFL);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 29 */     return this.share.oper.bitNot(this.exp.evalObject());
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\BitNotExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */