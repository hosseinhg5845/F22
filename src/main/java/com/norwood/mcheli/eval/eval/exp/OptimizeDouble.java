/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class OptimizeDouble
/*    */   extends OptimizeObject {
/*    */   protected boolean isTrue(AbstractExpression x) {
/*  6 */     return (x.evalDouble() != 0.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression toConst(AbstractExpression exp) {
/*    */     try {
/* 12 */       double val = exp.evalDouble();
/* 13 */       return NumberExpression.create(exp, Double.toString(val));
/* 14 */     } catch (Exception var4) {
/* 15 */       return exp;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\OptimizeDouble.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */