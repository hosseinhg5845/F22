/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class FuncArgExpression extends Col2OpeExpression {
/*    */   public FuncArgExpression() {
/*  7 */     setOperator(",");
/*    */   }
/*    */   
/*    */   protected FuncArgExpression(FuncArgExpression from, ShareExpValue s) {
/* 11 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 16 */     return new FuncArgExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void evalArgsLong(List<Long> args) {
/* 21 */     this.expl.evalArgsLong(args);
/* 22 */     this.expr.evalArgsLong(args);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void evalArgsDouble(List<Double> args) {
/* 27 */     this.expl.evalArgsDouble(args);
/* 28 */     this.expr.evalArgsDouble(args);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void evalArgsObject(List<Object> args) {
/* 33 */     this.expl.evalArgsObject(args);
/* 34 */     this.expr.evalArgsObject(args);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String toStringLeftSpace() {
/* 39 */     return "";
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\FuncArgExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */