/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class IfExpression extends Col3Expression {
/*    */   public IfExpression() {
/*  5 */     setOperator("?");
/*  6 */     setEndOperator(":");
/*    */   }
/*    */   
/*    */   protected IfExpression(IfExpression from, ShareExpValue s) {
/* 10 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 15 */     return new IfExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public long evalLong() {
/* 20 */     return (this.exp1.evalLong() != 0L) ? this.exp2.evalLong() : this.exp3.evalLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/* 25 */     return (this.exp1.evalDouble() != 0.0D) ? this.exp2.evalDouble() : this.exp3.evalDouble();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 30 */     return this.share.oper.bool(this.exp1.evalObject()) ? this.exp2.evalObject() : this.exp3.evalObject();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\IfExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */