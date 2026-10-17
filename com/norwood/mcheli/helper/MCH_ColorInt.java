/*    */ package com.norwood.mcheli.helper;
/*    */ 
/*    */ public class MCH_ColorInt {
/*    */   public final int r;
/*    */   public final int g;
/*    */   public final int b;
/*    */   public final int a;
/*    */   
/*    */   public MCH_ColorInt(int color) {
/* 10 */     this(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, color >> 24 & 0xFF);
/*    */   }
/*    */   
/*    */   public MCH_ColorInt(int r, int g, int b, int a) {
/* 14 */     this.r = r;
/* 15 */     this.g = g;
/* 16 */     this.b = b;
/* 17 */     this.a = a;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\MCH_ColorInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */