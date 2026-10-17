/*    */ package com.norwood.mcheli.helper.addon;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.norwood.mcheli.MCH_MOD;
/*    */ import com.norwood.mcheli.MCH_OutputFile;
/*    */ import com.norwood.mcheli.helper.MCH_Utils;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ 
/*    */ public class GeneratedAddonPack
/*    */   extends AddonPack {
/* 13 */   private static GeneratedAddonPack instance = null;
/* 14 */   private static File generatedDir = null;
/*    */   
/*    */   private GeneratedAddonPack() {
/* 17 */     super("@generated", "Generated", "1.0", null, "EMB4-MCHeli", 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 23 */         (List<String>)ImmutableList.of("EMB4", "Murachiki27"), "Generated addon(auto generate or update files)", "1", 
/*    */ 
/*    */         
/* 26 */         ImmutableMap.of());
/*    */   }
/*    */ 
/*    */   
/*    */   public static GeneratedAddonPack instance() {
/* 31 */     if (instance == null) {
/* 32 */       instance = new GeneratedAddonPack();
/*    */     }
/*    */     
/* 35 */     return instance;
/*    */   }
/*    */   
/*    */   public static boolean isGeneratedAddonName(File file) {
/* 39 */     return "generated".equals(file.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public File getFile() {
/* 44 */     if (generatedDir == null) {
/* 45 */       generatedDir = new File(MCH_MOD.getAddonDir(), "/generated/");
/*    */     }
/*    */     
/* 48 */     return generatedDir;
/*    */   }
/*    */   
/*    */   public boolean updateAssetFile(String targetAssetPath, List<String> lines) {
/* 52 */     File assets = checkExistAssets();
/* 53 */     MCH_OutputFile file = new MCH_OutputFile();
/* 54 */     if (!file.openUTF8(assets.getPath() + "/" + targetAssetPath)) {
/* 55 */       return false;
/*    */     }
/* 57 */     for (String s : lines) {
/* 58 */       file.writeLine(s);
/*    */     }
/*    */     
/* 61 */     file.close();
/* 62 */     MCH_Utils.logger().info("Update file:{}", file.file.getAbsolutePath());
/* 63 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public File checkMkdirsAssets(String dir) {
/* 68 */     File assets = new File(getFile(), "/assets/mcheli/" + dir + "/");
/* 69 */     if (!assets.exists()) {
/* 70 */       assets.mkdirs();
/*    */     }
/*    */     
/* 73 */     return assets;
/*    */   }
/*    */   
/*    */   private File checkExistAssets() {
/* 77 */     File assets = new File(getFile(), "/assets/mcheli/");
/* 78 */     if (!assets.exists()) {
/* 79 */       assets.mkdirs();
/*    */     }
/*    */     
/* 82 */     return assets;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\addon\GeneratedAddonPack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */