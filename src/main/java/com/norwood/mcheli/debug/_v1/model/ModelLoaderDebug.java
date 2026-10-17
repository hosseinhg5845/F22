/*    */ package com.norwood.mcheli.debug._v1.model;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
/*    */ import java.net.URL;
/*    */ import java.nio.file.Files;
/*    */ 
/*    */ public class ModelLoaderDebug {
/*    */   public static void main(String[] args) {
/*    */     try {
/* 12 */       ObjModel model = ObjParser.parse(Files.newInputStream((new File(getResource("models/gltd.obj"))).toPath(), new java.nio.file.OpenOption[0]));
/* 13 */       model.printInfo();
/* 14 */     } catch (Exception var2) {
/* 15 */       var2.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   static URI getResource(String path) {
/* 20 */     URL url = ModelLoaderDebug.class.getClassLoader().getResource("assets/mcheli/" + path);
/*    */     
/*    */     try {
/* 23 */       return (url != null) ? url.toURI() : null;
/* 24 */     } catch (URISyntaxException var3) {
/* 25 */       var3.printStackTrace();
/* 26 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v1\model\ModelLoaderDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */