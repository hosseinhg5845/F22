/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ public class MCH_Color {
/*    */   public final float a;
/*    */   public final float r;
/*    */   public final float g;
/*    */   public final float b;
/*    */   
/*    */   public MCH_Color(float aa, float rr, float gg, float bb) {
/* 10 */     this.a = round(aa);
/* 11 */     this.r = round(rr);
/* 12 */     this.g = round(gg);
/* 13 */     this.b = round(bb);
/*    */   }
/*    */   
/*    */   public MCH_Color(float rr, float gg, float bb) {
/* 17 */     this(1.0F, rr, gg, bb);
/*    */   }
/*    */   
/*    */   public MCH_Color() {
/* 21 */     this(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */   
/*    */   public float round(float f) {
/* 25 */     return (f > 1.0F) ? 1.0F : Math.max(f, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_Color.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */