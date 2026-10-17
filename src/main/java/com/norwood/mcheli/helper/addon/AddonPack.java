/*     */ package com.norwood.mcheli.helper.addon;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.norwood.mcheli.helper.MCH_Logger;
/*     */ import com.norwood.mcheli.helper.io.ResourceLoader;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AddonPack
/*     */ {
/*     */   private final String addonDomain;
/*     */   private final String addonName;
/*     */   private final String addonVersion;
/*     */   protected final ImmutableMap<String, JsonElement> packMetaMap;
/*     */   private final File addonFile;
/*     */   private final String credits;
/*     */   private final List<String> authors;
/*     */   private final String description;
/*     */   private final String loaderVersion;
/*     */   
/*     */   public AddonPack(String addonDomain, String addonName, String addonVersion, File addonFile, String credits, List<String> authors, String description, String loaderVersion, ImmutableMap<String, JsonElement> packMetaMap) {
/*  40 */     this.addonDomain = addonDomain;
/*  41 */     this.addonName = addonName;
/*  42 */     this.addonVersion = addonVersion;
/*  43 */     this.addonFile = addonFile;
/*  44 */     this.credits = credits;
/*  45 */     this.authors = authors;
/*  46 */     this.description = description;
/*  47 */     this.loaderVersion = loaderVersion;
/*  48 */     this.packMetaMap = packMetaMap;
/*     */   }
/*     */   
/*     */   public static AddonPack create(File addonFile) {
/*  52 */     JsonObject packMetaJson = loadPackMeta(addonFile);
/*  53 */     JsonObject packJson = JsonUtils.func_151218_a(packMetaJson, "pack", new JsonObject());
/*  54 */     JsonObject addonJson = JsonUtils.func_151218_a(packMetaJson, "addon", new JsonObject());
/*  55 */     String addonDomain = JsonUtils.func_151219_a(addonJson, "domain", null);
/*  56 */     String packName = JsonUtils.func_151219_a(packJson, "description", addonFile.getName());
/*  57 */     String version = JsonUtils.func_151219_a(addonJson, "version", "0.0");
/*     */     
/*  59 */     String credits = JsonUtils.func_151219_a(addonJson, "credits", "");
/*  60 */     String description = JsonUtils.func_151219_a(addonJson, "description", "");
/*  61 */     String loaderVersion = JsonUtils.func_151219_a(addonJson, "loader_version", "1");
/*  62 */     List<String> authors = getAuthors(addonJson);
/*  63 */     return new AddonPack(addonDomain, packName, version, addonFile, credits, authors, description, loaderVersion, 
/*  64 */         ImmutableMap.copyOf(packMetaJson.entrySet()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<String> getAuthors(JsonObject jsonObject) {
/*  69 */     List<String> list = Lists.newLinkedList();
/*  70 */     if (jsonObject.has("authors")) {
/*  71 */       JsonElement jsonElement = jsonObject.get("authors");
/*  72 */       if (jsonElement.isJsonArray()) {
/*  73 */         for (JsonElement jsonElement1 : jsonElement.getAsJsonArray()) {
/*  74 */           list.add(jsonElement1.getAsString());
/*     */         }
/*     */       }
/*  77 */     } else if (jsonObject.has("author")) {
/*  78 */       JsonElement jsonElement2 = jsonObject.get("author");
/*  79 */       if (jsonElement2.isJsonPrimitive()) {
/*  80 */         list.add(jsonElement2.getAsString());
/*     */       }
/*     */     } 
/*     */     
/*  84 */     return list;
/*     */   }
/*     */   
/*     */   private static JsonObject loadPackMeta(File addonFile) {
/*  88 */     ResourceLoader loader = ResourceLoader.create(addonFile);
/*  89 */     BufferedReader bufferedReader = null;
/*     */     
/*     */     try {
/*  92 */       bufferedReader = new BufferedReader(new InputStreamReader(loader.getInputStream("pack.mcmeta"), StandardCharsets.UTF_8));
/*  93 */       return (new JsonParser()).parse(bufferedReader).getAsJsonObject();
/*  94 */     } catch (FileNotFoundException var8) {
/*  95 */       MCH_Logger.get().warn("'pack.mcmeta' does not found in '{}'", addonFile.getName());
/*  96 */     } catch (IOException var9) {
/*  97 */       var9.printStackTrace();
/*     */     } finally {
/*  99 */       IOUtils.closeQuietly(bufferedReader);
/* 100 */       IOUtils.closeQuietly((Closeable)loader);
/*     */     } 
/*     */     
/* 103 */     return new JsonObject();
/*     */   }
/*     */   
/*     */   public String getDomain() {
/* 107 */     return this.addonDomain;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 111 */     return this.addonName;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 115 */     return this.addonVersion;
/*     */   }
/*     */   
/*     */   public String getAuthorsString() {
/* 119 */     return Joiner.on(", ").join(this.authors);
/*     */   }
/*     */   
/*     */   public String getCredits() {
/* 123 */     return this.credits;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 127 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getLoaderVersion() {
/* 131 */     return this.loaderVersion;
/*     */   }
/*     */   
/*     */   public File getFile() {
/* 135 */     return this.addonFile;
/*     */   }
/*     */   
/*     */   public ImmutableMap<String, JsonElement> getPackMetaMap() {
/* 139 */     return this.packMetaMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\addon\AddonPack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */