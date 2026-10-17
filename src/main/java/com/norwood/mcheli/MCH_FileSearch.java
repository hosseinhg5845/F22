/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.TreeSet;
/*    */ 
/*    */ public class MCH_FileSearch {
/*    */   public static final int TYPE_FILE_OR_DIR = 1;
/*    */   public static final int TYPE_FILE = 2;
/*    */   public static final int TYPE_DIR = 3;
/* 12 */   private final TreeSet<File> set = new TreeSet<>();
/*    */   
/*    */   public File[] listFiles(String directoryPath, String fileName) {
/* 15 */     if (fileName != null) {
/* 16 */       fileName = fileName.replace(".", "\\.");
/* 17 */       fileName = fileName.replace("*", ".*");
/*    */     } 
/*    */     
/* 20 */     return listFiles(directoryPath, fileName, 2, true, 0);
/*    */   }
/*    */   
/*    */   public File[] listFiles(String directoryPath, String fileNamePattern, int type, boolean isRecursive, int period) {
/* 24 */     File dir = new File(directoryPath);
/* 25 */     if (!dir.isDirectory()) {
/* 26 */       throw new IllegalArgumentException("引数で指定されたパス[" + dir.getAbsolutePath() + "]はディレクトリではありません。");
/*    */     }
/* 28 */     File[] files = dir.listFiles();
/*    */     
/* 30 */     for (File file : files) {
/* 31 */       addFile(type, fileNamePattern, this.set, file, period);
/* 32 */       if (isRecursive && file.isDirectory()) {
/* 33 */         listFiles(file.getAbsolutePath(), fileNamePattern, type, true, period);
/*    */       }
/*    */     } 
/*    */     
/* 37 */     return (File[])this.set.toArray((Object[])new File[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   private void addFile(int type, String match, TreeSet<File> set, File file, int period) {
/* 42 */     switch (type) {
/*    */       case 2:
/* 44 */         if (!file.isFile()) {
/*    */           return;
/*    */         }
/*    */         break;
/*    */       case 3:
/* 49 */         if (!file.isDirectory()) {
/*    */           return;
/*    */         }
/*    */         break;
/*    */     } 
/* 54 */     if (match == null || file.getName().matches(match)) {
/* 55 */       if (period != 0) {
/* 56 */         Date lastModifiedDate = new Date(file.lastModified());
/* 57 */         String lastModifiedDateStr = (new SimpleDateFormat("yyyyMMdd")).format(lastModifiedDate);
/* 58 */         long oneDayTime = 86400000L;
/* 59 */         long periodTime = oneDayTime * Math.abs(period);
/* 60 */         Date designatedDate = new Date(System.currentTimeMillis() - periodTime);
/* 61 */         String designatedDateStr = (new SimpleDateFormat("yyyyMMdd")).format(designatedDate);
/* 62 */         if (period > 0) {
/* 63 */           if (lastModifiedDateStr.compareTo(designatedDateStr) >= 0);
/*    */         }
/* 65 */         else if (lastModifiedDateStr.compareTo(designatedDateStr) > 0) {
/*    */           return;
/*    */         } 
/*    */       } 
/*    */       
/* 70 */       set.add(file);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void clear() {
/* 75 */     this.set.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_FileSearch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */