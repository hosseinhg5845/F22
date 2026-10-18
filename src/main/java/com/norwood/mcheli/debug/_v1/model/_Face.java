/*    */ package com.norwood.mcheli.debug._v1.model;
/*    */ 
/*    */ import com.norwood.mcheli.debug._v1.PrintStreamWrapper;
/*    */ import com.norwood.mcheli.helper.debug.DebugInfoObject;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class _Face
/*    */   implements DebugInfoObject {
/*    */   private final int[] verticesID;
/*    */   private final _Vertex[] vertices;
/*    */   private final _Vertex[] vertexNormals;
/*    */   private final _TextureCoord[] textureCoordinates;
/*    */   private final _Vertex faceNormal;
/*    */   
/*    */   _Face(int[] ids, _Vertex[] verts, _TextureCoord[] texCoords) {
/* 18 */     this(ids, verts, verts, texCoords);
/*    */   }
/*    */   
/*    */   _Face(int[] ids, _Vertex[] verts, _Vertex[] normals, _TextureCoord[] texCoords) {
/* 22 */     this.verticesID = ids;
/* 23 */     this.vertices = verts;
/* 24 */     this.vertexNormals = normals;
/* 25 */     this.textureCoordinates = texCoords;
/* 26 */     this.faceNormal = calculateFaceNormal(verts);
/*    */   }
/*    */   
/*    */   private static _Vertex calculateFaceNormal(_Vertex[] verts) {
/* 30 */     Vec3d v1 = new Vec3d(((verts[1]).x - (verts[0]).x), ((verts[1]).y - (verts[0]).y), ((verts[1]).z - (verts[0]).z));
/* 31 */     Vec3d v2 = new Vec3d(((verts[2]).x - (verts[0]).x), ((verts[2]).y - (verts[0]).y), ((verts[2]).z - (verts[0]).z));
/* 32 */     Vec3d normalVector = v1.crossProduct(v2).normalize();
/* 33 */     return new _Vertex((float)normalVector.x, (float)normalVector.y, (float)normalVector.z);
/*    */   }
/*    */   
/*    */   private static _Vertex getVerticesNormalFromFace(_Vertex fnormal, int verticesID, List<_Face> faces, float facet) {
/* 37 */     _Vertex v = new _Vertex(0.0F, 0.0F, 0.0F);
/*    */     
/* 39 */     for (_Face f : faces) {
/* 40 */       for (int id : f.verticesID) {
/* 41 */         if (id == verticesID) {
/* 42 */           if (f.faceNormal.x * fnormal.x + f.faceNormal.y * fnormal.y + f.faceNormal.z * fnormal.z >= facet) {
/* 43 */             v = v.add(f.faceNormal);
/*    */           }
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/* 50 */     return v.normalize();
/*    */   }
/*    */   
/*    */   _Face calcVerticesNormal(List<_Face> faces, boolean shading, double facet) {
/* 54 */     _Vertex[] vnormals = new _Vertex[this.verticesID.length];
/*    */     
/* 56 */     for (int i = 0; i < this.verticesID.length; i++) {
/* 57 */       _Vertex vn = getVerticesNormalFromFace(this.faceNormal, this.verticesID[i], faces, (float)facet);
/* 58 */       vn = vn.normalize();
/* 59 */       if (shading) {
/* 60 */         if ((this.faceNormal.x * vn.x + this.faceNormal.y * vn.y + this.faceNormal.z * vn.z) >= facet) {
/* 61 */           vnormals[i] = vn;
/*    */         } else {
/* 63 */           vnormals[i] = this.faceNormal;
/*    */         } 
/*    */       } else {
/* 66 */         vnormals[i] = this.faceNormal;
/*    */       } 
/*    */     } 
/*    */     
/* 70 */     return new _Face(this.verticesID, this.vertices, vnormals, this.textureCoordinates);
/*    */   }
/*    */ 
/*    */   
/*    */   public void printInfo(PrintStreamWrapper stream) {
/* 75 */     stream.println("F: [");
/* 76 */     stream.push();
/* 77 */     stream.println("ids: " + Arrays.toString(this.verticesID));
/* 78 */     stream.println("--- verts");
/* 79 */     Arrays.<_Vertex>stream(this.vertices).forEach(v -> v.printInfo(stream));
/* 80 */     stream.println("--- normals");
/* 81 */     Arrays.<_Vertex>stream(this.vertexNormals).forEach(n -> n.printInfo(stream));
/* 82 */     stream.println("--- tex coords");
/* 83 */     Arrays.<_TextureCoord>stream(this.textureCoordinates).forEach(t -> t.printInfo(stream));
/* 84 */     stream.println("--- face normal");
/* 85 */     this.faceNormal.printInfo(stream);
/* 86 */     stream.pop();
/* 87 */     stream.println("]");
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v1\model\_Face.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */