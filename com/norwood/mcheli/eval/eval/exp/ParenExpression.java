/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class ParenExpression extends Col1Expression {
/*    */   public ParenExpression() {
/*  5 */     setOperator("(");
/*  6 */     setEndOperator(")");
/*    */   }
/*    */   
/*    */   protected ParenExpression(ParenExpression from, ShareExpValue s) {
/* 10 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 15 */     return new ParenExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long val) {
/* 20 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double val) {
/* 25 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 30 */     return this.exp.evalObject();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 35 */     return (this.exp == null) ? "" : (getOperator() + this.exp + getEndOperator());
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\ParenExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */