/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*     */ import com.norwood.mcheli.helper.info.ContentParseException;
/*     */ import com.norwood.mcheli.helper.info.IContentData;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public abstract class MCH_BaseInfo
/*     */   implements IContentData {
/*     */   public final String filePath;
/*     */   public final AddonResourceLocation location;
/*     */   
/*     */   public MCH_BaseInfo(AddonResourceLocation location, String filePath) {
/*  15 */     this.location = location;
/*  16 */     this.filePath = filePath;
/*     */   }
/*     */   
/*     */   public boolean toBool(String s) {
/*  20 */     return s.equalsIgnoreCase("true");
/*     */   }
/*     */   
/*     */   public boolean toBool(String s, boolean defaultValue) {
/*  24 */     if (s.equalsIgnoreCase("true")) {
/*  25 */       return true;
/*     */     }
/*  27 */     return (!s.equalsIgnoreCase("false") && defaultValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public float toFloat(String s) {
/*  32 */     return Float.parseFloat(s);
/*     */   }
/*     */   
/*     */   public float toFloat(String s, float min, float max) {
/*  36 */     float f = Float.parseFloat(s);
/*  37 */     return (f > max) ? max : Math.max(f, min);
/*     */   }
/*     */   
/*     */   public double toDouble(String s) {
/*  41 */     return Double.parseDouble(s);
/*     */   }
/*     */   
/*     */   public Vec3d toVec3(String x, String y, String z) {
/*  45 */     return new Vec3d(toDouble(x), toDouble(y), toDouble(z));
/*     */   }
/*     */   
/*     */   public int toInt(String s) {
/*  49 */     return Integer.parseInt(s);
/*     */   }
/*     */   
/*     */   public int toInt(String s, int min, int max) {
/*  53 */     int f = Integer.parseInt(s);
/*  54 */     return (f > max) ? max : Math.max(f, min);
/*     */   }
/*     */   
/*     */   public int hex2dec(String s) {
/*  58 */     return (!s.startsWith("0x") && !s.startsWith("0X") && s.indexOf(false) != 35) ? (int)(Long.decode("0x" + s).longValue() & 0xFFFFFFFFFFFFFFFFL) : (int)(Long.decode(s).longValue() & 0xFFFFFFFFFFFFFFFFL);
/*     */   }
/*     */   
/*     */   public String[] splitParam(String data) {
/*  62 */     return data.split("\\s*,\\s*");
/*     */   }
/*     */   
/*     */   public String[] splitParamSlash(String data) {
/*  66 */     return data.split("\\s*/\\s*");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validate() throws Exception {
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadItemData(String item, String data) {}
/*     */ 
/*     */   
/*     */   public void onPostReload() {}
/*     */ 
/*     */   
/*     */   public boolean canReloadItem(String item) {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void parse(List<String> lines, String fileExtension, boolean reload) {
/*  87 */     if ("txt".equals(fileExtension)) {
/*  88 */       int line = 0;
/*     */       
/*     */       try {
/*  91 */         for (String str : lines) {
/*  92 */           line++;
/*  93 */           str = str.trim();
/*  94 */           int eqIdx = str.indexOf('=');
/*  95 */           if (eqIdx >= 0 && str.length() > eqIdx + 1) {
/*  96 */             loadItemData(str.substring(0, eqIdx).trim().toLowerCase(), str.substring(eqIdx + 1).trim());
/*     */           }
/*     */         } 
/*  99 */       } catch (Exception var8) {
/* 100 */         throw new ContentParseException(var8, line);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AddonResourceLocation getLoation() {
/* 107 */     return this.location;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentPath() {
/* 112 */     return this.filePath;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_BaseInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */