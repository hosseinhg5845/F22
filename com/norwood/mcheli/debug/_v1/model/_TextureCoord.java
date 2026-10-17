/*    */ package com.norwood.mcheli.debug._v1.model;
/*    */ 
/*    */ import com.norwood.mcheli.debug._v1.PrintStreamWrapper;
/*    */ import com.norwood.mcheli.helper.debug.DebugInfoObject;
/*    */ 
/*    */ class _TextureCoord implements DebugInfoObject {
/*    */   public final float u;
/*    */   public final float v;
/*    */   public final float w;
/*    */   
/*    */   public _TextureCoord(float u, float v) {
/* 12 */     this(u, v, 0.0F);
/*    */   }
/*    */   
/*    */   public _TextureCoord(float u, float v, float w) {
/* 16 */     this.u = u;
/* 17 */     this.v = v;
/* 18 */     this.w = w;
/*    */   }
/*    */ 
/*    */   
/*    */   public void printInfo(PrintStreamWrapper stream) {
/* 23 */     stream.println(String.format("T: [%.6f, %.6f, %.6f]", new Object[] { Float.valueOf(this.u), Float.valueOf(this.v), Float.valueOf(this.w) }));
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v1\model\_TextureCoord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */