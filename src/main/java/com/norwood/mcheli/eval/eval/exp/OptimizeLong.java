/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class OptimizeLong
/*    */   extends OptimizeObject
/*    */ {
/*    */   protected AbstractExpression toConst(AbstractExpression exp) {
/*    */     try {
/*  8 */       long val = exp.evalLong();
/*  9 */       return NumberExpression.create(exp, Long.toString(val));
/* 10 */     } catch (Exception var4) {
/* 11 */       return exp;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\OptimizeLong.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */