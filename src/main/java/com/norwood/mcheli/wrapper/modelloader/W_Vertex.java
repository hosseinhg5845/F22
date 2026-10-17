/*    */ package com.norwood.mcheli.wrapper.modelloader;
/*    */ 
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class W_Vertex {
/*    */   public float x;
/*    */   public float y;
/*    */   public float z;
/*    */   
/*    */   public W_Vertex(float x, float y) {
/* 13 */     this(x, y, 0.0F);
/*    */   }
/*    */   
/*    */   public W_Vertex(float x, float y, float z) {
/* 17 */     this.x = x;
/* 18 */     this.y = y;
/* 19 */     this.z = z;
/*    */   }
/*    */   
/*    */   public void normalize() {
/* 23 */     double d = Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z));
/* 24 */     this.x = (float)(this.x / d);
/* 25 */     this.y = (float)(this.y / d);
/* 26 */     this.z = (float)(this.z / d);
/*    */   }
/*    */   
/*    */   public void add(W_Vertex v) {
/* 30 */     this.x += v.x;
/* 31 */     this.y += v.y;
/* 32 */     this.z += v.z;
/*    */   }
/*    */   
/*    */   public boolean equal(W_Vertex v) {
/* 36 */     return (this.x == v.x && this.y == v.y && this.z == v.z);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\modelloader\W_Vertex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */