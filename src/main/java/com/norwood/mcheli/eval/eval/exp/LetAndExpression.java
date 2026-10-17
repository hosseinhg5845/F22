/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public final class LetAndExpression extends BitAndExpression {
/*    */   public LetAndExpression() {
/*  5 */     setOperator("&=");
/*    */   }
/*    */   
/*    */   private LetAndExpression(LetAndExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new LetAndExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public long evalLong() {
/* 19 */     long val = super.evalLong();
/* 20 */     this.expl.let(val, this.pos);
/* 21 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/* 26 */     double val = super.evalDouble();
/* 27 */     this.expl.let(val, this.pos);
/* 28 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 33 */     Object val = super.evalObject();
/* 34 */     this.expl.let(val, this.pos);
/* 35 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression replace() {
/* 40 */     this.expl = this.expl.replaceVar();
/* 41 */     this.expr = this.expr.replace();
/* 42 */     return this.share.repl.replaceLet(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\LetAndExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */