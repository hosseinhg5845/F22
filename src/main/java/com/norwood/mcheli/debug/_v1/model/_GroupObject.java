/*    */ package com.norwood.mcheli.debug._v1.model;
/*    */ 
/*    */ import com.norwood.mcheli.debug._v1.PrintStreamWrapper;
/*    */ import com.norwood.mcheli.helper.debug.DebugInfoObject;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class _GroupObject
/*    */   implements DebugInfoObject {
/*    */   public final String name;
/*    */   private final List<_Face> faces;
/*    */   
/*    */   private _GroupObject(String name, List<_Face> faces) {
/* 14 */     this.name = name;
/* 15 */     this.faces = faces;
/*    */   }
/*    */   
/*    */   public static Builder builder() {
/* 19 */     return new Builder();
/*    */   }
/*    */ 
/*    */   
/*    */   public void printInfo(PrintStreamWrapper stream) {
/* 24 */     stream.push(String.format("G: [name: %s]", new Object[] { this.name }));
/* 25 */     this.faces.forEach(f -> f.printInfo(stream));
/* 26 */     stream.pop();
/* 27 */     stream.println();
/*    */   }
/*    */   
/*    */   public static class Builder {
/*    */     private String name;
/* 32 */     private final List<_Face> faces = new ArrayList<>();
/*    */     
/*    */     public Builder name(String name) {
/* 35 */       this.name = name;
/* 36 */       return this;
/*    */     }
/*    */     
/*    */     public Builder addFace(_Face face) {
/* 40 */       this.faces.add(face);
/* 41 */       return this;
/*    */     }
/*    */     
/*    */     public int faceSize() {
/* 45 */       return this.faces.size();
/*    */     }
/*    */     
/*    */     public _GroupObject build() {
/* 49 */       return new _GroupObject(this.name, this.faces);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v1\model\_GroupObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */