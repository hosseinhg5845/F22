/*     */ package com.norwood.mcheli.helper.addon;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.helper.MCH_Utils;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import net.minecraftforge.fml.common.FMLModContainer;
/*     */ import net.minecraftforge.fml.common.MetadataCollection;
/*     */ import net.minecraftforge.fml.common.ModContainer;
/*     */ import net.minecraftforge.fml.common.discovery.ContainerType;
/*     */ import net.minecraftforge.fml.common.discovery.ModCandidate;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class AddonManager {
/*  24 */   public static final Pattern ZIP_PATTERN = Pattern.compile("(.+).(zip|jar)$");
/*     */   public static final String BUILTIN_ADDON_DOMAIN = "@builtin";
/*  26 */   private static final Map<String, AddonPack> ADDON_LIST = Maps.newLinkedHashMap();
/*     */   
/*     */   @Nullable
/*     */   public static AddonPack get(String addonDomain) {
/*  30 */     return "@builtin".equals(addonDomain) ? BuiltinAddonPack.instance() : ADDON_LIST.get(addonDomain);
/*     */   }
/*     */   
/*     */   public static List<AddonPack> getLoadedAddons() {
/*  34 */     return (List<AddonPack>)ImmutableList.builder()
/*  35 */       .addAll(ADDON_LIST.values())
/*  36 */       .build();
/*     */   }
/*     */   
/*     */   public static List<AddonPack> loadAddons(File addonDir) {
/*  40 */     checkExistAddonDir(addonDir);
/*  41 */     List<AddonPack> addons = Lists.newArrayList();
/*  42 */     File[] addonFiles = addonDir.listFiles();
/*     */     
/*  44 */     for (File addonFile : addonFiles) {
/*  45 */       if (validAddonPath(addonFile)) {
/*  46 */         AddonPack data = loadAddon(addonFile);
/*  47 */         if (data != null) {
/*  48 */           addons.add(data);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  53 */     MCH_Utils.logger().info("Load complete addons. count:{}", Integer.valueOf(addons.size()));
/*  54 */     return addons;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static List<AddonPack> loadAddonsAndAddResources(File addonDir) {
/*  59 */     checkExistAddonDir(addonDir);
/*  60 */     List<AddonPack> addons = Lists.newArrayList();
/*  61 */     File[] addonFiles = addonDir.listFiles();
/*     */     
/*  63 */     for (File addonFile : addonFiles) {
/*  64 */       if (validAddonPath(addonFile)) {
/*  65 */         AddonPack data = loadAddonAndAddResource(addonFile, MCH_MOD.class);
/*  66 */         if (data != null) {
/*  67 */           addons.add(data);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     checkExistAddonDir(GeneratedAddonPack.instance().getFile());
/*  73 */     addReloadableResource(GeneratedAddonPack.instance(), MCH_MOD.class);
/*  74 */     FMLClientHandler.instance().refreshResources((Predicate)null);
/*  75 */     MCH_Utils.logger().info("Load complete addons and add resources. count:{}", Integer.valueOf(addons.size()));
/*  76 */     return addons;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static AddonPack loadAddon(File addonFile) {
/*     */     try {
/*  82 */       AddonPack addonPack = AddonPack.create(addonFile);
/*  83 */       ADDON_LIST.put(addonPack.getDomain(), addonPack);
/*  84 */       return addonPack;
/*  85 */     } catch (Exception var2) {
/*  86 */       MCH_Utils.logger().error("Failed to load for pack:{} :", addonFile.getName(), var2);
/*  87 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @SideOnly(Side.CLIENT)
/*     */   private static AddonPack loadAddonAndAddResource(File addonFile, Class<?> clazz) {
/*  94 */     AddonPack addonPack = loadAddon(addonFile);
/*  95 */     if (addonPack == null) {
/*  96 */       return null;
/*     */     }
/*  98 */     addReloadableResource(addonPack, clazz);
/*  99 */     return addonPack;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private static void addReloadableResource(AddonPack addonPack, Class<?> clazz) {
/* 105 */     Map<String, Object> descriptor = Maps.newHashMap();
/* 106 */     descriptor.put("modid", "mcheli");
/* 107 */     descriptor.put("name", "MCHeli#" + addonPack.getName());
/* 108 */     descriptor.put("version", addonPack.getVersion());
/* 109 */     File file = addonPack.getFile();
/*     */     
/* 111 */     FMLModContainer container = new FMLModContainer(clazz.getName(), new ModCandidate(file, file, file.isDirectory() ? ContainerType.DIR : ContainerType.JAR), descriptor);
/*     */     
/* 113 */     container.bindMetadata(MetadataCollection.from(null, ""));
/* 114 */     FMLClientHandler.instance().addModAsResource((ModContainer)container);
/*     */   }
/*     */   
/*     */   private static boolean validAddonPath(File addonFile) {
/* 118 */     return (!GeneratedAddonPack.isGeneratedAddonName(addonFile) && (addonFile.isDirectory() || ZIP_PATTERN.matcher(addonFile.getName()).matches()));
/*     */   }
/*     */   
/*     */   private static void checkExistAddonDir(File addonDir) {
/* 122 */     if (!addonDir.exists())
/* 123 */       addonDir.mkdirs(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\addon\AddonManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */