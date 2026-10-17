/*    */ package com.norwood.mcheli.debug._v1.model;
/*    */ 
/*    */ import com.norwood.mcheli.debug._v1.PrintStreamWrapper;
/*    */ import com.norwood.mcheli.helper.debug.DebugInfoObject;
/*    */ 
/*    */ class _Vertex implements DebugInfoObject {
/*    */   public final float x;
/*    */   public final float y;
/*    */   public final float z;
/*    */   
/*    */   _Vertex(float x, float y) {
/* 12 */     this(x, y, 0.0F);
/*    */   }
/*    */   
/*    */   _Vertex(float x, float y, float z) {
/* 16 */     this.x = x;
/* 17 */     this.y = y;
/* 18 */     this.z = z;
/*    */   }
/*    */   
/*    */   public _Vertex normalize() {
/* 22 */     double d = Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z));
/* 23 */     return new _Vertex((float)(this.x / d), (float)(this.y / d), (float)(this.z / d));
/*    */   }
/*    */   
/*    */   public _Vertex add(_Vertex v) {
/* 27 */     return new _Vertex(this.x + v.x, this.y + v.y, this.z + v.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void printInfo(PrintStreamWrapper stream) {
/* 32 */     stream.println(String.format("V: [%.6f, %.6f, %.6f]", new Object[] { Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.z) }));
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v1\model\_Vertex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */