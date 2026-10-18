/*    */ package com.norwood.mcheli.wrapper.modelloader;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class W_Face {
/*    */   public int[] verticesID;
/*    */   public W_Vertex[] vertices;
/*    */   public W_Vertex[] vertexNormals;
/*    */   public W_Vertex faceNormal;
/*    */   public W_TextureCoordinate[] textureCoordinates;
/*    */   
/*    */   public W_Face copy() {
/* 18 */     return new W_Face();
/*    */   }
/*    */   
/*    */   public void addFaceForRender(Tessellator tessellator) {
/* 22 */     addFaceForRender(tessellator, 0.0F);
/*    */   }
/*    */   
/*    */   public void addFaceForRender(Tessellator tessellator, float textureOffset) {
/* 26 */     BufferBuilder builder = tessellator.getBuffer();
/* 27 */     if (this.faceNormal == null) {
/* 28 */       this.faceNormal = calculateFaceNormal();
/*    */     }
/*    */     
/* 31 */     float averageU = 0.0F;
/* 32 */     float averageV = 0.0F;
/* 33 */     if (this.textureCoordinates != null && this.textureCoordinates.length > 0) {
/* 34 */       for (W_TextureCoordinate textureCoordinate : this.textureCoordinates) {
/* 35 */         averageU += textureCoordinate.u;
/* 36 */         averageV += textureCoordinate.v;
/*    */       } 
/*    */       
/* 39 */       averageU /= this.textureCoordinates.length;
/* 40 */       averageV /= this.textureCoordinates.length;
/*    */     } 
/*    */     
/* 43 */     for (int i = 0; i < this.vertices.length; i++) {
/* 44 */       if (this.textureCoordinates != null && this.textureCoordinates.length > 0) {
/* 45 */         float offsetU = textureOffset;
/* 46 */         float offsetV = textureOffset;
/* 47 */         if ((this.textureCoordinates[i]).u > averageU) {
/* 48 */           offsetU = -textureOffset;
/*    */         }
/*    */         
/* 51 */         if ((this.textureCoordinates[i]).v > averageV) {
/* 52 */           offsetV = -textureOffset;
/*    */         }
/*    */         
/* 55 */         builder.pos((this.vertices[i]).x, (this.vertices[i]).y, (this.vertices[i]).z)
/* 56 */           .tex(((this.textureCoordinates[i]).u + offsetU), ((this.textureCoordinates[i]).v + offsetV));
/*    */       } else {
/* 58 */         builder.pos((this.vertices[i]).x, (this.vertices[i]).y, (this.vertices[i]).z).tex(0.0D, 0.0D);
/*    */       } 
/*    */       
/* 61 */       if (this.vertexNormals != null && i < this.vertexNormals.length) {
/* 62 */         builder.normal((this.vertexNormals[i]).x, (this.vertexNormals[i]).y, (this.vertexNormals[i]).z).endVertex();
/*    */       } else {
/* 64 */         builder.normal(this.faceNormal.x, this.faceNormal.y, this.faceNormal.z).endVertex();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public W_Vertex calculateFaceNormal() {
/* 70 */     Vec3d v1 = new Vec3d(((this.vertices[1]).x - (this.vertices[0]).x), ((this.vertices[1]).y - (this.vertices[0]).y), ((this.vertices[1]).z - (this.vertices[0]).z));
/* 71 */     Vec3d v2 = new Vec3d(((this.vertices[2]).x - (this.vertices[0]).x), ((this.vertices[2]).y - (this.vertices[0]).y), ((this.vertices[2]).z - (this.vertices[0]).z));
/* 72 */     Vec3d normalVector = v1.crossProduct(v2).normalize();
/* 73 */     return new W_Vertex((float)normalVector.x, (float)normalVector.y, (float)normalVector.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 78 */     return "W_Face[id:" + this.verticesID + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\modelloader\W_Face.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */