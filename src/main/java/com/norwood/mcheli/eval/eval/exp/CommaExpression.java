/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class CommaExpression extends Col2OpeExpression {
/*    */   public CommaExpression() {
/*  5 */     setOperator(",");
/*    */   }
/*    */   
/*    */   protected CommaExpression(CommaExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new CommaExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public long evalLong() {
/* 19 */     this.expl.evalLong();
/* 20 */     return this.expr.evalLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/* 25 */     this.expl.evalDouble();
/* 26 */     return this.expr.evalDouble();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 31 */     this.expl.evalObject();
/* 32 */     return this.expr.evalObject();
/*    */   }
/*    */ 
/*    */   
/*    */   protected String toStringLeftSpace() {
/* 37 */     return "";
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\CommaExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */