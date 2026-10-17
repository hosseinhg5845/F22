/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class MCH_DamageFactor
/*    */ {
/*  8 */   private final HashMap<Class<? extends Entity>, Float> map = new HashMap<>();
/*    */   
/*    */   public void clear() {
/* 11 */     this.map.clear();
/*    */   }
/*    */   
/*    */   public void add(Class<? extends Entity> c, float value) {
/* 15 */     this.map.put(c, Float.valueOf(value));
/*    */   }
/*    */   
/*    */   public float getDamageFactor(Class<? extends Entity> c) {
/* 19 */     return ((Float)this.map.getOrDefault(c, Float.valueOf(1.0F))).floatValue();
/*    */   }
/*    */   
/*    */   public float getDamageFactor(Entity e) {
/* 23 */     return (e != null) ? getDamageFactor((Class)e.getClass()) : 1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_DamageFactor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */