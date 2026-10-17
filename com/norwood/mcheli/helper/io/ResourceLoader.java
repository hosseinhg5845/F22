/*     */ package com.norwood.mcheli.helper.io;
/*     */ import com.google.common.base.CharMatcher;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import javax.annotation.Nullable;
/*     */ import org.jline.utils.OSUtils;
/*     */ 
/*     */ public abstract class ResourceLoader implements Closeable {
/*     */   ResourceLoader(File file) {
/*  22 */     this.dir = file;
/*     */   }
/*     */   protected final File dir;
/*     */   public static ResourceLoader create(File file) {
/*  26 */     return file.isDirectory() ? new DirectoryLoader(file) : new ZipJarFileLoader(file);
/*     */   }
/*     */   
/*     */   public List<ResourceEntry> loadAll() throws IOException {
/*  30 */     return loadAll(null);
/*     */   }
/*     */   
/*     */   public abstract List<ResourceEntry> loadAll(@Nullable Predicate<? super ResourceEntry> paramPredicate) throws IOException;
/*     */   
/*     */   public Optional<ResourceEntry> loadFirst() throws IOException {
/*  36 */     return loadAll(null).stream().findFirst();
/*     */   }
/*     */   
/*     */   public abstract ResourceEntry load(String paramString) throws IOException;
/*     */   
/*     */   public abstract InputStream getInputStreamFromEntry(ResourceEntry paramResourceEntry) throws IOException;
/*     */   
/*     */   public InputStream getInputStream(String relativePath) throws IOException {
/*  44 */     return getInputStreamFromEntry(load(relativePath));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  49 */     close();
/*  50 */     super.finalize();
/*     */   }
/*     */   
/*     */   static class DirectoryLoader extends ResourceLoader {
/*  54 */     private static final boolean ON_WINDOWS = OSUtils.IS_WINDOWS;
/*  55 */     private static final CharMatcher BACKSLASH_MATCHER = CharMatcher.is('\\');
/*     */     
/*     */     DirectoryLoader(File file) {
/*  58 */       super(file);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<ResourceLoader.ResourceEntry> loadAll(@Nullable Predicate<? super ResourceLoader.ResourceEntry> filePathFilter) throws IOException {
/*  63 */       List<ResourceLoader.ResourceEntry> list = Lists.newLinkedList();
/*  64 */       filePathFilter = (filePathFilter == null) ? (path -> true) : filePathFilter;
/*  65 */       loadFiles(this.dir, list, filePathFilter);
/*  66 */       return list;
/*     */     }
/*     */     
/*     */     private void loadFiles(File dir, List<ResourceLoader.ResourceEntry> list, Predicate<? super ResourceLoader.ResourceEntry> filePathFilter) {
/*  70 */       if (dir.exists()) {
/*  71 */         if (dir.isDirectory()) {
/*  72 */           for (File file : dir.listFiles()) {
/*  73 */             loadFiles(file, list, filePathFilter);
/*     */           }
/*     */         } else {
/*  76 */           Path file = dir.toPath();
/*  77 */           Path root = this.dir.toPath();
/*  78 */           String s = root.relativize(file).toString();
/*  79 */           if (ON_WINDOWS) {
/*  80 */             s = BACKSLASH_MATCHER.replaceFrom(s, '/');
/*     */           }
/*     */           
/*  83 */           ResourceLoader.ResourceEntry resourceFile = new ResourceLoader.ResourceEntry(s, dir.isDirectory());
/*  84 */           if (filePathFilter.test(resourceFile)) {
/*  85 */             list.add(resourceFile);
/*     */           }
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ResourceLoader.ResourceEntry load(String relativePath) throws IOException {
/*  93 */       File file = getFile(relativePath);
/*  94 */       if (file != null && file.exists()) {
/*  95 */         return new ResourceLoader.ResourceEntry(relativePath, file.isDirectory());
/*     */       }
/*  97 */       throw new FileNotFoundException(relativePath);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream getInputStreamFromEntry(ResourceLoader.ResourceEntry resource) throws IOException {
/* 103 */       File file1 = getFile(resource.getPath());
/* 104 */       if (file1 == null) {
/* 105 */         throw new FileNotFoundException(String.format("'%s' in ResourcePack '%s'", new Object[] { this.dir, resource.getPath() }));
/*     */       }
/* 107 */       return new BufferedInputStream(Files.newInputStream(file1.toPath(), new java.nio.file.OpenOption[0]));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private File getFile(String filepath) {
/*     */       try {
/* 114 */         File file1 = new File(this.dir, filepath);
/* 115 */         if (file1.isFile() && validatePath(file1, filepath)) {
/* 116 */           return file1;
/*     */         }
/* 118 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/* 121 */       return null;
/*     */     }
/*     */     
/*     */     private boolean validatePath(File file, String filepath) throws IOException {
/* 125 */       String s = file.getCanonicalPath();
/* 126 */       if (ON_WINDOWS) {
/* 127 */         s = BACKSLASH_MATCHER.replaceFrom(s, '/');
/*     */       }
/*     */       
/* 130 */       return s.endsWith(filepath);
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() {}
/*     */   }
/*     */   
/*     */   public static class ResourceEntry
/*     */   {
/*     */     private final String path;
/*     */     private final boolean isDirectory;
/*     */     
/*     */     public ResourceEntry(String path, boolean isDirectory) {
/* 143 */       this.path = path;
/* 144 */       this.isDirectory = isDirectory;
/*     */     }
/*     */     
/*     */     public boolean isDirectory() {
/* 148 */       return this.isDirectory;
/*     */     }
/*     */     
/*     */     public String getPath() {
/* 152 */       return this.path;
/*     */     }
/*     */   }
/*     */   
/*     */   static class ZipJarFileLoader extends ResourceLoader {
/*     */     private ZipFile resourcePackZipFile;
/*     */     
/*     */     ZipJarFileLoader(File file) {
/* 160 */       super(file);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<ResourceLoader.ResourceEntry> loadAll(@Nullable Predicate<? super ResourceLoader.ResourceEntry> filePathFilter) throws IOException {
/* 165 */       ZipFile zipfile = getResourcePackZipFile();
/* 166 */       filePathFilter = (filePathFilter == null) ? (path -> true) : filePathFilter;
/* 167 */       return (List<ResourceLoader.ResourceEntry>)zipfile.stream()
/* 168 */         .<ResourceLoader.ResourceEntry>map(enrty -> new ResourceLoader.ResourceEntry(enrty.getName(), enrty.isDirectory()))
/* 169 */         .filter(filePathFilter)
/* 170 */         .collect(Collectors.toList());
/*     */     }
/*     */ 
/*     */     
/*     */     public ResourceLoader.ResourceEntry load(String relativePath) throws IOException {
/* 175 */       ZipFile zipfile = getResourcePackZipFile();
/* 176 */       ZipEntry zipEntry = zipfile.getEntry(relativePath);
/* 177 */       if (zipEntry != null) {
/* 178 */         return new ResourceLoader.ResourceEntry(zipEntry.getName(), zipEntry.isDirectory());
/*     */       }
/* 180 */       throw new FileNotFoundException(relativePath);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream getInputStreamFromEntry(ResourceLoader.ResourceEntry resource) throws IOException {
/* 186 */       ZipFile zipfile = getResourcePackZipFile();
/* 187 */       ZipEntry zipentry = zipfile.getEntry(resource.getPath());
/* 188 */       if (zipentry == null) {
/* 189 */         throw new FileNotFoundException(String.format("'%s' in ResourcePack '%s'", new Object[] { this.dir, resource.getPath() }));
/*     */       }
/* 191 */       return zipfile.getInputStream(zipentry);
/*     */     }
/*     */ 
/*     */     
/*     */     private ZipFile getResourcePackZipFile() throws IOException {
/* 196 */       if (this.resourcePackZipFile == null) {
/* 197 */         this.resourcePackZipFile = new ZipFile(this.dir);
/*     */       }
/*     */       
/* 200 */       return this.resourcePackZipFile;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 205 */       if (this.resourcePackZipFile != null) {
/* 206 */         this.resourcePackZipFile.close();
/* 207 */         this.resourcePackZipFile = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\io\ResourceLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */