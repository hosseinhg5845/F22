/*    */ package com.norwood.mcheli.eval.eval.var;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class MapVariable implements Variable {
/*    */   protected Map<Object, Object> map;
/*    */   
/*    */   public MapVariable() {
/* 12 */     this(new HashMap<>());
/*    */   }
/*    */   
/*    */   public MapVariable(Map<Object, Object> varMap) {
/* 16 */     this.map = varMap;
/*    */   }
/*    */   
/*    */   public Map<Object, Object> getMap() {
/* 20 */     return this.map;
/*    */   }
/*    */   
/*    */   public void setMap(Map<Object, Object> varMap) {
/* 24 */     this.map = varMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object name, Object obj) {
/* 29 */     this.map.put(name, obj);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getObject(Object name) {
/* 34 */     return this.map.get(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public long evalLong(Object val) {
/* 39 */     return ((Number)val).longValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public double evalDouble(Object val) {
/* 44 */     return ((Number)val).doubleValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getObject(Object array, int index) {
/* 49 */     return Array.get(array, index);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object array, int index, Object val) {
/* 54 */     Array.set(array, index, val);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getObject(Object obj, String field) {
/*    */     try {
/* 60 */       Class<?> c = obj.getClass();
/* 61 */       Field f = c.getField(field);
/* 62 */       return f.get(obj);
/* 63 */     } catch (RuntimeException var5) {
/* 64 */       throw var5;
/* 65 */     } catch (Exception var6) {
/* 66 */       throw new RuntimeException(var6);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object obj, String field, Object val) {
/*    */     try {
/* 73 */       Class<?> c = obj.getClass();
/* 74 */       Field f = c.getField(field);
/* 75 */       f.set(obj, val);
/* 76 */     } catch (RuntimeException var6) {
/* 77 */       throw var6;
/* 78 */     } catch (Exception var7) {
/* 79 */       throw new RuntimeException(var7);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\var\MapVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */