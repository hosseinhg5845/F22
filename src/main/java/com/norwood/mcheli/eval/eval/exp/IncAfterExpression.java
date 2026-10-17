/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class IncAfterExpression extends Col1AfterExpression {
/*    */   public IncAfterExpression() {
/*  5 */     setOperator("++");
/*    */   }
/*    */   
/*    */   protected IncAfterExpression(IncAfterExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new IncAfterExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long val) {
/* 19 */     this.exp.let(val + 1L, this.pos);
/* 20 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double val) {
/* 25 */     this.exp.let(val + 1.0D, this.pos);
/* 26 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 31 */     Object val = this.exp.evalObject();
/* 32 */     this.exp.let(this.share.oper.inc(val, 1), this.pos);
/* 33 */     return val;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\IncAfterExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */