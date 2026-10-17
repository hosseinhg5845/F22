/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ public class MCH_LowPassFilterFloat {
/*    */   private final MCH_Queue<Float> filter;
/*    */   
/*    */   public MCH_LowPassFilterFloat(int filterLength) {
/*  7 */     this.filter = new MCH_Queue<>(filterLength, Float.valueOf(0.0F));
/*    */   }
/*    */   
/*    */   public void clear() {
/* 11 */     this.filter.clear(Float.valueOf(0.0F));
/*    */   }
/*    */   
/*    */   public void put(float t) {
/* 15 */     this.filter.put(Float.valueOf(t));
/*    */   }
/*    */   
/*    */   public float getAvg() {
/* 19 */     float f = 0.0F;
/*    */     
/* 21 */     for (int i = 0; i < this.filter.size(); i++) {
/* 22 */       f += ((Float)this.filter.get(i)).floatValue();
/*    */     }
/*    */     
/* 25 */     return f / this.filter.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_LowPassFilterFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */