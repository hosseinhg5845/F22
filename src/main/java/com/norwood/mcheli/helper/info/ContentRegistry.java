/*     */ package com.norwood.mcheli.helper.info;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.norwood.mcheli.MCH_BaseInfo;
/*     */ import com.norwood.mcheli.helper.MCH_Logger;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class ContentRegistry<T extends MCH_BaseInfo>
/*     */ {
/*     */   private final Class<T> contentClass;
/*     */   private final String dir;
/*     */   private final Map<String, T> registry;
/*     */   
/*     */   private ContentRegistry(Class<T> contentClass, String dir, Map<String, T> table) {
/*  22 */     this.contentClass = contentClass;
/*  23 */     this.dir = dir;
/*  24 */     this.registry = Maps.newHashMap(table);
/*     */   }
/*     */   
/*     */   private static <TYPE extends IContentData> void putTable(Map<String, TYPE> table, TYPE content) {
/*  28 */     table.put(content.getLoation().getResourcePath(), content);
/*     */   }
/*     */   
/*     */   public static <TYPE extends MCH_BaseInfo> Builder<TYPE> builder(Class<TYPE> type, String dir) {
/*  32 */     return new Builder<>(type, dir);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T get(@Nullable String key) {
/*  37 */     return (key == null) ? null : this.registry.get(key);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T findFirst(Predicate<? super T> filter) {
/*  42 */     return (T)this.registry.values().stream().filter(filter).findFirst().orElse(null);
/*     */   }
/*     */   
/*     */   public boolean reload(String key) {
/*  46 */     T content = get(key);
/*  47 */     if (content != null) {
/*  48 */       IContentData newContent = ContentRegistries.reparseContent((IContentData)content, this.dir);
/*  49 */       if (this.contentClass.isInstance(newContent)) {
/*  50 */         MCH_BaseInfo mCH_BaseInfo = (MCH_BaseInfo)this.contentClass.cast(newContent);
/*  51 */         this.registry.replace(key, (T)mCH_BaseInfo);
/*  52 */         return true;
/*     */       } 
/*     */       
/*  55 */       MCH_Logger.get().error("Content cast error, old dir:{}, new dir:{}", content.getClass(), newContent.getClass());
/*     */     } 
/*     */     
/*  58 */     return false;
/*     */   }
/*     */   
/*     */   public void reloadAll() {
/*  62 */     for (MCH_BaseInfo mCH_BaseInfo : ContentRegistries.<T>reloadAllAddonContents(this)) {
/*  63 */       this.registry.replace(mCH_BaseInfo.getLoation().getResourcePath(), (T)mCH_BaseInfo);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<T> values() {
/*  68 */     return (List<T>)ImmutableList.copyOf(this.registry.values());
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<String, T>> entries() {
/*  72 */     return this.registry.entrySet();
/*     */   }
/*     */   
/*     */   public void forEachValue(Consumer<? super T> action) {
/*  76 */     this.registry.values().forEach(action);
/*     */   }
/*     */   
/*     */   public boolean contains(String key) {
/*  80 */     return this.registry.containsKey(key);
/*     */   }
/*     */   
/*     */   public int size() {
/*  84 */     return this.registry.size();
/*     */   }
/*     */   
/*     */   public Class<T> getType() {
/*  88 */     return this.contentClass;
/*     */   }
/*     */   
/*     */   public String getDirectoryName() {
/*  92 */     return this.dir;
/*     */   }
/*     */   
/*     */   public static class Builder<E extends MCH_BaseInfo> {
/*     */     private final Class<E> clazz;
/*     */     private final String dirName;
/*  98 */     private final Map<String, E> map = Maps.newHashMap();
/*     */     
/*     */     Builder(Class<E> clazz, String dir) {
/* 101 */       this.clazz = clazz;
/* 102 */       this.dirName = dir;
/*     */     }
/*     */     
/*     */     public void put(E content) {
/* 106 */       ContentRegistry.putTable(this.map, content);
/*     */     }
/*     */     
/*     */     public ContentRegistry<E> build() {
/* 110 */       return new ContentRegistry<>(this.clazz, this.dirName, this.map);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\info\ContentRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */