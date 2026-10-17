/*    */ package com.norwood.mcheli.wrapper.modelloader;
/*    */ 
/*    */ import com.norwood.mcheli.helper.client._IModelCustom;
/*    */ 
/*    */ public abstract class W_ModelCustom implements _IModelCustom {
/*  6 */   public float min = 100000.0F;
/*  7 */   public float minX = 100000.0F;
/*  8 */   public float minY = 100000.0F;
/*  9 */   public float minZ = 100000.0F;
/* 10 */   public float max = -100000.0F;
/* 11 */   public float maxX = -100000.0F;
/* 12 */   public float maxY = -100000.0F;
/* 13 */   public float maxZ = -100000.0F;
/* 14 */   public float size = 0.0F;
/* 15 */   public float sizeX = 0.0F;
/* 16 */   public float sizeY = 0.0F;
/* 17 */   public float sizeZ = 0.0F;
/*    */   
/*    */   public void checkMinMax(W_Vertex v) {
/* 20 */     if (v.x < this.minX) {
/* 21 */       this.minX = v.x;
/*    */     }
/*    */     
/* 24 */     if (v.y < this.minY) {
/* 25 */       this.minY = v.y;
/*    */     }
/*    */     
/* 28 */     if (v.z < this.minZ) {
/* 29 */       this.minZ = v.z;
/*    */     }
/*    */     
/* 32 */     if (v.x > this.maxX) {
/* 33 */       this.maxX = v.x;
/*    */     }
/*    */     
/* 36 */     if (v.y > this.maxY) {
/* 37 */       this.maxY = v.y;
/*    */     }
/*    */     
/* 40 */     if (v.z > this.maxZ) {
/* 41 */       this.maxZ = v.z;
/*    */     }
/*    */   }
/*    */   
/*    */   public void checkMinMaxFinal() {
/* 46 */     if (this.minX < this.min) {
/* 47 */       this.min = this.minX;
/*    */     }
/*    */     
/* 50 */     if (this.minY < this.min) {
/* 51 */       this.min = this.minY;
/*    */     }
/*    */     
/* 54 */     if (this.minZ < this.min) {
/* 55 */       this.min = this.minZ;
/*    */     }
/*    */     
/* 58 */     if (this.maxX > this.max) {
/* 59 */       this.max = this.maxX;
/*    */     }
/*    */     
/* 62 */     if (this.maxY > this.max) {
/* 63 */       this.max = this.maxY;
/*    */     }
/*    */     
/* 66 */     if (this.maxZ > this.max) {
/* 67 */       this.max = this.maxZ;
/*    */     }
/*    */     
/* 70 */     this.sizeX = this.maxX - this.minX;
/* 71 */     this.sizeY = this.maxY - this.minY;
/* 72 */     this.sizeZ = this.maxZ - this.minZ;
/* 73 */     this.size = this.max - this.min;
/*    */   }
/*    */   
/*    */   public abstract boolean containsPart(String paramString);
/*    */   
/*    */   public abstract void renderAll(int paramInt1, int paramInt2);
/*    */   
/*    */   public abstract void renderAllLine(int paramInt1, int paramInt2);
/*    */   
/*    */   public abstract int getVertexNum();
/*    */   
/*    */   public abstract int getFaceNum();
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\modelloader\W_ModelCustom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */