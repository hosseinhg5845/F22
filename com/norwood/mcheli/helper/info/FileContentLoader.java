/*    */ package com.norwood.mcheli.helper.info;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.norwood.mcheli.helper.MCH_Logger;
/*    */ import java.io.Closeable;
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.zip.ZipEntry;
/*    */ import java.util.zip.ZipFile;
/*    */ 
/*    */ public class FileContentLoader extends ContentLoader implements Closeable {
/*    */   public FileContentLoader(String domain, File addonDir, String loaderVersion, Predicate<String> fileFilter) {
/* 17 */     super(domain, addonDir, loaderVersion, fileFilter);
/*    */   }
/*    */   private ZipFile resourcePackZipFile;
/*    */   private ZipFile getResourcePackZipFile() throws IOException {
/* 21 */     if (this.resourcePackZipFile == null) {
/* 22 */       this.resourcePackZipFile = new ZipFile(this.dir);
/*    */     }
/*    */     
/* 25 */     return this.resourcePackZipFile;
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<ContentLoader.ContentEntry> getEntries() {
/* 30 */     return walkEntries("2".equals(this.loaderVersion));
/*    */   }
/*    */   
/*    */   private List<ContentLoader.ContentEntry> walkEntries(boolean findDeep) {
/* 34 */     List<ContentLoader.ContentEntry> list = Lists.newLinkedList();
/*    */     
/*    */     try {
/* 37 */       ZipFile zipfile = getResourcePackZipFile();
/* 38 */       Iterator<? extends ZipEntry> itr = zipfile.stream().filter(ex -> filter(ex, findDeep)).iterator();
/*    */       
/* 40 */       while (itr.hasNext()) {
/* 41 */         String name = ((ZipEntry)itr.next()).getName();
/* 42 */         String[] s = name.split("/");
/* 43 */         String typeDirName = (s.length >= 3) ? s[2] : null;
/* 44 */         IContentFactory factory = getFactory(typeDirName);
/* 45 */         if (factory != null) {
/* 46 */           list.add(makeEntry(name, factory, false));
/*    */         }
/*    */       } 
/* 49 */     } catch (IOException var9) {
/* 50 */       MCH_Logger.get().error("IO Error from file loader!", var9);
/*    */     } 
/*    */     
/* 53 */     return list;
/*    */   }
/*    */   
/*    */   private boolean filter(ZipEntry zipEntry, boolean deep) {
/* 57 */     String name = zipEntry.getName();
/* 58 */     String[] split = name.split("/");
/* 59 */     String lootDir = (split.length >= 2) ? split[0] : "";
/* 60 */     return (!zipEntry.isDirectory() && (deep || "assets".equals(lootDir) || split.length <= 2) && isReadable(name));
/*    */   }
/*    */ 
/*    */   
/*    */   protected InputStream getInputStreamByName(String name) throws IOException {
/* 65 */     ZipFile zipfile = getResourcePackZipFile();
/* 66 */     ZipEntry zipentry = zipfile.getEntry(name);
/* 67 */     if (zipentry == null) {
/* 68 */       throw new FileNotFoundException(String.format("'%s' in AddonPack '%s'", new Object[] { this.dir, name }));
/*    */     }
/* 70 */     return zipfile.getInputStream(zipentry);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void finalize() throws Throwable {
/* 76 */     close();
/* 77 */     super.finalize();
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 82 */     if (this.resourcePackZipFile != null) {
/* 83 */       this.resourcePackZipFile.close();
/* 84 */       this.resourcePackZipFile = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\info\FileContentLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */