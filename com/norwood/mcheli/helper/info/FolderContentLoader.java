/*     */ package com.norwood.mcheli.helper.info;
/*     */ import com.google.common.base.CharMatcher;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class FolderContentLoader extends ContentLoader {
/*  14 */   private static final boolean ON_WINDOWS = OSUtils.IS_WINDOWS;
/*  15 */   private static final CharMatcher BACKSLASH_MATCHER = CharMatcher.is('\\');
/*     */   private final File addonFolder;
/*     */   
/*     */   public FolderContentLoader(String domain, File addonDir, String loaderVersion, Predicate<String> fileFilter) {
/*  19 */     super(domain, addonDir, loaderVersion, fileFilter);
/*  20 */     this.addonFolder = addonDir.getParentFile();
/*     */   }
/*     */   
/*     */   protected static boolean validatePath(File file, String filepath) throws IOException {
/*  24 */     String s = file.getCanonicalPath();
/*  25 */     if (ON_WINDOWS) {
/*  26 */       s = BACKSLASH_MATCHER.replaceFrom(s, '/');
/*     */     }
/*     */     
/*  29 */     return s.endsWith(filepath);
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ContentLoader.ContentEntry> getEntries() {
/*  34 */     return walkDir(this.dir, (IContentFactory)null, this.loaderVersion.equals("2"), 0);
/*     */   }
/*     */   
/*     */   private List<ContentLoader.ContentEntry> walkDir(File dir, @Nullable IContentFactory factory, boolean loadDeep, int depth) {
/*  38 */     List<ContentLoader.ContentEntry> list = Lists.newArrayList();
/*  39 */     if (dir != null && dir.exists()) {
/*  40 */       if (dir.isDirectory()) {
/*  41 */         if (loadDeep || depth <= 1) {
/*  42 */           for (File f : dir.listFiles()) {
/*  43 */             IContentFactory contentFactory = factory;
/*  44 */             boolean flag = (loadDeep || (depth == 0 && "assets".equals(f.getName())));
/*  45 */             if (factory == null) {
/*  46 */               contentFactory = getFactory(f.getName());
/*     */             }
/*     */             
/*  49 */             list.addAll(walkDir(f, contentFactory, flag, depth + 1));
/*     */           } 
/*     */         }
/*     */       } else {
/*     */         try {
/*  54 */           String s = getDirPath(dir);
/*  55 */           if (isReadable(s) && factory != null) {
/*  56 */             list.add(makeEntry(s, factory, false));
/*     */           }
/*  58 */         } catch (IOException var12) {
/*  59 */           var12.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/*  63 */       return list;
/*     */     } 
/*  65 */     return Lists.newArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDirPath(File file) throws IOException {
/*  70 */     String s = this.dir.getName();
/*  71 */     String s1 = file.getCanonicalPath();
/*  72 */     if (ON_WINDOWS) {
/*  73 */       s1 = BACKSLASH_MATCHER.replaceFrom(s1, '/');
/*     */     }
/*     */     
/*  76 */     String[] split = s1.split(this.addonFolder.getName() + "/" + s + "/", 2);
/*  77 */     if (split.length < 2) {
/*  78 */       throw new FileNotFoundException(String.format("'%s' in AddonPack '%s'", new Object[] { this.dir, s }));
/*     */     }
/*  80 */     return split[1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected InputStream getInputStreamByName(String name) throws IOException {
/*  86 */     File file1 = getFile(name);
/*  87 */     if (file1 == null) {
/*  88 */       throw new FileNotFoundException(String.format("'%s' in AddonPack '%s'", new Object[] { this.dir, name }));
/*     */     }
/*  90 */     return new BufferedInputStream(Files.newInputStream(file1.toPath(), new java.nio.file.OpenOption[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private File getFile(String filepath) {
/*     */     try {
/*  97 */       File file1 = new File(this.dir, filepath);
/*  98 */       if (file1.isFile() && validatePath(file1, filepath)) {
/*  99 */         return file1;
/*     */       }
/* 101 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/* 104 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\info\FolderContentLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */