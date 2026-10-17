/*     */ package com.norwood.mcheli.helper.info;
/*     */ import com.google.common.collect.LinkedHashMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.common.io.Files;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.helper.MCH_Logger;
/*     */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public abstract class ContentLoader {
/*     */   protected final String domain;
/*     */   protected final File dir;
/*     */   protected final String loaderVersion;
/*     */   private final Predicate<String> fileFilter;
/*     */   
/*     */   public ContentLoader(String domain, File addonDir, String loaderVersion, Predicate<String> fileFilter) {
/*  26 */     this.domain = domain;
/*  27 */     this.dir = addonDir;
/*  28 */     this.loaderVersion = loaderVersion;
/*  29 */     this.fileFilter = fileFilter;
/*     */   }
/*     */   
/*     */   public boolean isReadable(String path) {
/*  33 */     return this.fileFilter.test(path);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IContentFactory getFactory(@Nullable String dirName) {
/*  38 */     return ContentFactories.getFactory(dirName);
/*     */   }
/*     */   
/*     */   public Multimap<ContentType, ContentEntry> load() {
/*  42 */     LinkedHashMultimap linkedHashMultimap = LinkedHashMultimap.create();
/*     */     
/*  44 */     for (ContentEntry entry : getEntries()) {
/*  45 */       linkedHashMultimap.put(entry.getType(), entry);
/*     */     }
/*     */     
/*  48 */     return (Multimap<ContentType, ContentEntry>)linkedHashMultimap;
/*     */   }
/*     */   
/*     */   protected abstract List<ContentEntry> getEntries();
/*     */   
/*     */   protected abstract InputStream getInputStreamByName(String paramString) throws IOException;
/*     */   
/*     */   public <TYPE extends IContentData> List<TYPE> reloadAndParse(Class<TYPE> clazz, List<TYPE> oldContents, IContentFactory factory) {
/*  56 */     List<TYPE> list = Lists.newLinkedList();
/*     */     
/*  58 */     for (IContentData iContentData : oldContents) {
/*     */       try {
/*  60 */         ContentEntry entry = makeEntry(iContentData.getContentPath(), factory, true);
/*  61 */         IContentData content = entry.parse();
/*  62 */         if (content != null) {
/*  63 */           content.onPostReload();
/*     */         } else {
/*  65 */           content = iContentData;
/*     */         } 
/*     */         
/*  68 */         if (clazz.isInstance(content)) {
/*  69 */           list.add(clazz.cast(content));
/*     */         }
/*  71 */       } catch (IOException var9) {
/*  72 */         MCH_Logger.get().error("IO Error from file loader!", var9);
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return list;
/*     */   }
/*     */   
/*     */   public IContentData reloadAndParseSingle(IContentData oldData, String dir) {
/*  80 */     IContentData content = oldData;
/*     */     
/*     */     try {
/*  83 */       ContentEntry entry = makeEntry(oldData.getContentPath(), getFactory(dir), true);
/*  84 */       content = entry.parse();
/*  85 */       if (content != null) {
/*  86 */         content.onPostReload();
/*     */       } else {
/*  88 */         content = oldData;
/*     */       } 
/*  90 */     } catch (IOException var5) {
/*  91 */       MCH_Logger.get().error("IO Error from file loader!", var5);
/*     */     } 
/*     */     
/*  94 */     return content;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ContentEntry makeEntry(String filepath, @Nullable IContentFactory factory, boolean reload) throws IOException {
/*     */     List<String> lines;
/* 100 */     BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(getInputStreamByName(filepath), StandardCharsets.UTF_8)); 
/* 101 */     try { lines = bufferedreader.lines().collect((Collector)Collectors.toList());
/* 102 */       bufferedreader.close(); } catch (Throwable throwable) { try { bufferedreader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 104 */      return new ContentEntry(filepath, this.domain, factory, lines, reload);
/*     */   }
/*     */   
/*     */   public static class ContentEntry {
/*     */     private final String filepath;
/*     */     private final String domain;
/*     */     private final IContentFactory factory;
/*     */     private final List<String> lines;
/*     */     private final boolean reload;
/*     */     
/*     */     private ContentEntry(String filepath, String domain, @Nullable IContentFactory factory, List<String> lines, boolean reload) {
/* 115 */       this.filepath = filepath;
/* 116 */       this.domain = domain;
/* 117 */       this.factory = factory;
/* 118 */       this.lines = lines;
/* 119 */       this.reload = reload;
/*     */     }
/*     */     @Nullable
/*     */     public IContentData parse() {
/*     */       IContentData var4;
/* 124 */       AddonResourceLocation location = MCH_Utils.addon(this.domain, Files.getNameWithoutExtension(this.filepath));
/* 125 */       if (!this.reload) {
/* 126 */         MCH_MOD.proxy.onParseStartFile(location);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 131 */         IContentData content = this.factory.create(location, this.filepath);
/* 132 */         if (content != null) {
/* 133 */           content.parse(this.lines, Files.getFileExtension(this.filepath), this.reload);
/* 134 */           if (!content.validate()) {
/* 135 */             MCH_Logger.get().debug("Invalid content info: {}", this.filepath);
/*     */           }
/*     */         } 
/*     */         
/* 139 */         return content;
/* 140 */       } catch (Exception var8) {
/* 141 */         String msg = "An error occurred while file loading ";
/* 142 */         if (var8 instanceof ContentParseException) {
/* 143 */           msg = msg + "at line:" + ((ContentParseException)var8).getLineNo() + ".";
/*     */         }
/*     */         
/* 146 */         MCH_Logger.get().error("{} file:{}, domain:{}", msg, location.func_110623_a(), this.domain, var8);
/* 147 */         var4 = null;
/*     */       } finally {
/* 149 */         MCH_MOD.proxy.onParseFinishFile(location);
/*     */       } 
/*     */       
/* 152 */       return var4;
/*     */     }
/*     */     
/*     */     public ContentType getType() {
/* 156 */       return this.factory.getType();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\info\ContentLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */