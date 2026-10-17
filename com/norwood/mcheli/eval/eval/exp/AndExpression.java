/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class AndExpression extends Col2OpeExpression {
/*    */   public AndExpression() {
/*  5 */     setOperator("&&");
/*    */   }
/*    */   
/*    */   protected AndExpression(AndExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new AndExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public long evalLong() {
/* 19 */     long val = this.expl.evalLong();
/* 20 */     return (val == 0L) ? val : this.expr.evalLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/* 25 */     double val = this.expl.evalDouble();
/* 26 */     return (val == 0.0D) ? val : this.expr.evalDouble();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 31 */     Object val = this.expl.evalObject();
/* 32 */     return !this.share.oper.bool(val) ? val : this.expr.evalObject();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\AndExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */