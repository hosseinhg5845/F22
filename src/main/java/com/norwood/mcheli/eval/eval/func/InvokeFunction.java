/*    */ package com.norwood.mcheli.eval.eval.func;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ public class InvokeFunction implements Function {
/*    */   public static Object callMethod(Object obj, String name, Object[] args) throws Exception {
/*  7 */     Class<?> c = obj.getClass();
/*  8 */     Class<?>[] types = new Class[args.length];
/*    */     
/* 10 */     for (int i = 0; i < types.length; i++) {
/* 11 */       types[i] = args[i].getClass();
/*    */     }
/*    */     
/* 14 */     Method m = c.getMethod(name, types);
/* 15 */     return m.invoke(obj, args);
/*    */   }
/*    */ 
/*    */   
/*    */   public long evalLong(Object object, String name, Long[] args) throws Throwable {
/* 20 */     if (object == null) {
/* 21 */       return 0L;
/*    */     }
/* 23 */     Object r = callMethod(object, name, (Object[])args);
/* 24 */     return ((Number)r).longValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double evalDouble(Object object, String name, Double[] args) throws Throwable {
/* 30 */     if (object == null) {
/* 31 */       return 0.0D;
/*    */     }
/* 33 */     Object r = callMethod(object, name, (Object[])args);
/* 34 */     return ((Number)r).doubleValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject(Object object, String name, Object[] args) throws Throwable {
/* 40 */     return (object == null) ? null : callMethod(object, name, args);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\func\InvokeFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */