/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public abstract class Col2OpeExpression
/*    */   extends Col2Expression {
/*    */   protected Col2OpeExpression() {}
/*    */   
/*    */   protected Col2OpeExpression(Col2Expression from, ShareExpValue s) {
/*  8 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final long operateLong(long vl, long vr) {
/* 13 */     throw new RuntimeException("この関数が呼ばれてはいけない");
/*    */   }
/*    */ 
/*    */   
/*    */   protected final double operateDouble(double vl, double vr) {
/* 18 */     throw new RuntimeException("この関数が呼ばれてはいけない");
/*    */   }
/*    */ 
/*    */   
/*    */   protected final Object operateObject(Object vl, Object vr) {
/* 23 */     throw new RuntimeException("この関数が呼ばれてはいけない");
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression replace() {
/* 28 */     this.expl = this.expl.replace();
/* 29 */     this.expr = this.expr.replace();
/* 30 */     return this.share.repl.replace2(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression replaceVar() {
/* 35 */     this.expl = this.expl.replaceVar();
/* 36 */     this.expr = this.expr.replaceVar();
/* 37 */     return this.share.repl.replaceVar2(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\Col2OpeExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */