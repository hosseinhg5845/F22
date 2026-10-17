/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class IncBeforeExpression extends Col1Expression {
/*    */   public IncBeforeExpression() {
/*  5 */     setOperator("++");
/*    */   }
/*    */   
/*    */   protected IncBeforeExpression(IncBeforeExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new IncBeforeExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long val) {
/* 19 */     this.exp.let(++val, this.pos);
/* 20 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double val) {
/* 25 */     this.exp.let(++val, this.pos);
/* 26 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 31 */     Object val = this.exp.evalObject();
/* 32 */     val = this.share.oper.inc(val, 1);
/* 33 */     this.exp.let(val, this.pos);
/* 34 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression replace() {
/* 39 */     this.exp = this.exp.replaceVar();
/* 40 */     return this.share.repl.replaceVar1(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\IncBeforeExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */