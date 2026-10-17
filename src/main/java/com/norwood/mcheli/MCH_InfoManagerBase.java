/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MCH_InfoManagerBase<T extends MCH_BaseInfo>
/*    */ {
/*    */   public abstract T newInfo(AddonResourceLocation paramAddonResourceLocation, String paramString);
/*    */   
/*    */   protected void put(String name, T info) {}
/*    */   
/*    */   protected abstract boolean contains(String paramString);
/*    */   
/*    */   protected abstract int size();
/*    */   
/*    */   public boolean load(String path, String type) {
/* 19 */     path = path.replace('\\', '/');
/* 20 */     File dir = new File(path + type);
/* 21 */     File[] files = dir.listFiles(pathname -> {
/*    */           String s = pathname.getName().toLowerCase();
/* 23 */           return (pathname.isFile() && s.length() >= 5 && s.substring(s.length() - 4).compareTo(".txt") == 0);
/*    */         });
/* 25 */     if (files != null && files.length > 0) {
/* 26 */       for (File f : files) {
/* 27 */         int line = 0;
/* 28 */         MCH_InputFile inFile = new MCH_InputFile();
/*    */       } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 66 */       MCH_Lib.Log("Read %d %s", new Object[] { Integer.valueOf(size()), type });
/* 67 */       return (size() > 0);
/*    */     } 
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_InfoManagerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */