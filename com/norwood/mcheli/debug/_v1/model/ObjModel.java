/*    */ package com.norwood.mcheli.debug._v1.model;
/*    */ 
/*    */ import com.norwood.mcheli.debug._v1.PrintStreamWrapper;
/*    */ import com.norwood.mcheli.helper.debug.DebugInfoObject;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ObjModel
/*    */   implements DebugInfoObject {
/*    */   private final List<_GroupObject> groupObjects;
/*    */   private final int vertexNum;
/*    */   private final int faceNum;
/*    */   
/*    */   public ObjModel(List<_GroupObject> groupObjects, int verts, int faces) {
/* 14 */     this.groupObjects = groupObjects;
/* 15 */     this.vertexNum = verts;
/* 16 */     this.faceNum = faces;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 21 */     return "ObjModel[verts:" + this.vertexNum + ", face:" + this.faceNum + ", obj:" + this.groupObjects + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public void printInfo(PrintStreamWrapper stream) {
/* 26 */     stream.push("Obj Model Info:");
/* 27 */     this.groupObjects.forEach(g -> g.printInfo(stream));
/* 28 */     stream.pop();
/* 29 */     stream.println();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v1\model\ObjModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */