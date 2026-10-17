/*    */ package com.norwood.mcheli.eval.eval.func;
/*    */ 
/*    */ public class VoidFunction
/*    */   implements Function {
/*    */   public long evalLong(Object object, String name, Long[] args) {
/*  6 */     System.out.println(object + "." + name + "関数が呼ばれた(long)");
/*    */     
/*  8 */     for (int i = 0; i < args.length; i++) {
/*  9 */       System.out.println("arg[" + i + "] " + args[i]);
/*    */     }
/*    */     
/* 12 */     return 0L;
/*    */   }
/*    */ 
/*    */   
/*    */   public double evalDouble(Object object, String name, Double[] args) {
/* 17 */     System.out.println(object + "." + name + "関数が呼ばれた(double)");
/*    */     
/* 19 */     for (int i = 0; i < args.length; i++) {
/* 20 */       System.out.println("arg[" + i + "] " + args[i]);
/*    */     }
/*    */     
/* 23 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject(Object object, String name, Object[] args) {
/* 28 */     System.out.println(object + "." + name + "関数が呼ばれた(Object)");
/*    */     
/* 30 */     for (int i = 0; i < args.length; i++) {
/* 31 */       System.out.println("arg[" + i + "] " + args[i]);
/*    */     }
/*    */     
/* 34 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\func\VoidFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */