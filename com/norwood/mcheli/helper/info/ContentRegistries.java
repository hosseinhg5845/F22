/*     */ package com.norwood.mcheli.helper.info;
/*     */ 
/*     */ import com.google.common.collect.LinkedHashMultimap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.helicopter.MCH_HeliInfo;
/*     */ import com.norwood.mcheli.helper.addon.AddonManager;
/*     */ import com.norwood.mcheli.helper.addon.AddonPack;
/*     */ import com.norwood.mcheli.helper.addon.BuiltinAddonPack;
/*     */ import com.norwood.mcheli.hud.MCH_Hud;
/*     */ import com.norwood.mcheli.plane.MCP_PlaneInfo;
/*     */ import com.norwood.mcheli.tank.MCH_TankInfo;
/*     */ import com.norwood.mcheli.throwable.MCH_ThrowableInfo;
/*     */ import com.norwood.mcheli.vehicle.MCH_VehicleInfo;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponInfo;
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ public class ContentRegistries
/*     */ {
/*  25 */   private static ContentRegistry<MCH_HeliInfo> REGISTORY_HELI = null;
/*  26 */   private static ContentRegistry<MCP_PlaneInfo> REGISTORY_PLANE = null;
/*  27 */   private static ContentRegistry<MCH_TankInfo> REGISTORY_TANK = null;
/*  28 */   private static ContentRegistry<MCH_VehicleInfo> REGISTORY_VEHICLE = null;
/*  29 */   private static ContentRegistry<MCH_WeaponInfo> REGISTORY_WEAPON = null;
/*  30 */   private static ContentRegistry<MCH_ThrowableInfo> REGISTORY_THROWABLE = null;
/*  31 */   private static ContentRegistry<MCH_Hud> REGISTORY_HUD = null;
/*     */   
/*     */   public static ContentRegistry<MCH_HeliInfo> heli() {
/*  34 */     return REGISTORY_HELI;
/*     */   }
/*     */   
/*     */   public static ContentRegistry<MCP_PlaneInfo> plane() {
/*  38 */     return REGISTORY_PLANE;
/*     */   }
/*     */   
/*     */   public static ContentRegistry<MCH_TankInfo> tank() {
/*  42 */     return REGISTORY_TANK;
/*     */   }
/*     */   
/*     */   public static ContentRegistry<MCH_VehicleInfo> vehicle() {
/*  46 */     return REGISTORY_VEHICLE;
/*     */   }
/*     */   
/*     */   public static ContentRegistry<MCH_WeaponInfo> weapon() {
/*  50 */     return REGISTORY_WEAPON;
/*     */   }
/*     */   
/*     */   public static ContentRegistry<MCH_ThrowableInfo> throwable() {
/*  54 */     return REGISTORY_THROWABLE;
/*     */   }
/*     */   
/*     */   public static ContentRegistry<MCH_Hud> hud() {
/*  58 */     return REGISTORY_HUD;
/*     */   }
/*     */   
/*     */   public static <T extends com.norwood.mcheli.MCH_BaseInfo> ContentRegistry<T> get(Class<T> clazz) {
/*  62 */     if (clazz == MCH_HeliInfo.class)
/*  63 */       return (ContentRegistry)REGISTORY_HELI; 
/*  64 */     if (clazz == MCP_PlaneInfo.class)
/*  65 */       return (ContentRegistry)REGISTORY_PLANE; 
/*  66 */     if (clazz == MCH_TankInfo.class)
/*  67 */       return (ContentRegistry)REGISTORY_TANK; 
/*  68 */     if (clazz == MCH_VehicleInfo.class)
/*  69 */       return (ContentRegistry)REGISTORY_VEHICLE; 
/*  70 */     if (clazz == MCH_WeaponInfo.class)
/*  71 */       return (ContentRegistry)REGISTORY_WEAPON; 
/*  72 */     if (clazz == MCH_ThrowableInfo.class)
/*  73 */       return (ContentRegistry)REGISTORY_THROWABLE; 
/*  74 */     if (clazz == MCH_Hud.class) {
/*  75 */       return (ContentRegistry)REGISTORY_HUD;
/*     */     }
/*  77 */     throw new RuntimeException("Unknown type:" + clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadContents(File addonDir) {
/*  82 */     LinkedHashMultimap linkedHashMultimap = LinkedHashMultimap.create();
/*  83 */     List<AddonPack> addons = MCH_MOD.proxy.loadAddonPacks(addonDir);
/*  84 */     MCH_MOD.proxy.onLoadStartAddons(addons.size());
/*  85 */     linkedHashMultimap.putAll(loadAddonContents((AddonPack)BuiltinAddonPack.instance()));
/*     */     
/*  87 */     for (AddonPack pack : addons) {
/*  88 */       linkedHashMultimap.putAll(loadAddonContents(pack));
/*     */     }
/*     */     
/*  91 */     MCH_MOD.proxy.onLoadFinishAddons();
/*  92 */     REGISTORY_HUD = parseContents(MCH_Hud.class, "hud", linkedHashMultimap.get(ContentType.HUD));
/*  93 */     REGISTORY_WEAPON = parseContents(MCH_WeaponInfo.class, "weapons", linkedHashMultimap.get(ContentType.WEAPON));
/*  94 */     REGISTORY_HELI = parseContents(MCH_HeliInfo.class, "helicopters", linkedHashMultimap.get(ContentType.HELICOPTER));
/*  95 */     REGISTORY_PLANE = parseContents(MCP_PlaneInfo.class, "planes", linkedHashMultimap.get(ContentType.PLANE));
/*  96 */     REGISTORY_TANK = parseContents(MCH_TankInfo.class, "tanks", linkedHashMultimap.get(ContentType.TANK));
/*  97 */     REGISTORY_VEHICLE = parseContents(MCH_VehicleInfo.class, "vehicles", linkedHashMultimap.get(ContentType.VEHICLE));
/*  98 */     REGISTORY_THROWABLE = parseContents(MCH_ThrowableInfo.class, "throwable", linkedHashMultimap.get(ContentType.THROWABLE));
/*     */   }
/*     */   
/*     */   public static IContentData reparseContent(IContentData content, String dir) {
/* 102 */     AddonPack addonPack = AddonManager.get(content.getLoation().getAddonDomain());
/* 103 */     if (addonPack == null) {
/* 104 */       return content;
/*     */     }
/* 106 */     ContentLoader packLoader = getDefaultPackLoader(addonPack);
/* 107 */     return packLoader.reloadAndParseSingle(content, dir);
/*     */   }
/*     */ 
/*     */   
/*     */   static <T extends com.norwood.mcheli.MCH_BaseInfo> List<T> reloadAllAddonContents(ContentRegistry<T> registry) {
/* 112 */     List<T> list = Lists.newLinkedList();
/*     */     
/* 114 */     for (AddonPack addon : AddonManager.getLoadedAddons()) {
/* 115 */       ContentLoader packLoader = getPackLoader(addon, getFilterOnly(registry.getDirectoryName()));
/* 116 */       list.addAll(packLoader.reloadAndParse(registry.getType(), registry.values(), ContentFactories.getFactory(registry.getDirectoryName())));
/*     */     } 
/*     */     
/* 119 */     return list;
/*     */   }
/*     */   
/*     */   private static Multimap<ContentType, ContentLoader.ContentEntry> loadAddonContents(AddonPack pack) {
/* 123 */     ContentLoader packLoader = getDefaultPackLoader(pack);
/* 124 */     MCH_MOD.proxy.onLoadStepAddon(pack.getDomain());
/* 125 */     return packLoader.load();
/*     */   }
/*     */   
/*     */   private static <T extends com.norwood.mcheli.MCH_BaseInfo> ContentRegistry<T> parseContents(Class<T> clazz, String dir, Collection<ContentLoader.ContentEntry> values) {
/* 129 */     ContentRegistry.Builder<T> builder = ContentRegistry.builder(clazz, dir);
/* 130 */     MCH_MOD.proxy.onLoadStartContents(dir, values.size());
/*     */     
/* 132 */     for (ContentLoader.ContentEntry entry : values) {
/* 133 */       IContentData content = entry.parse();
/* 134 */       if (content != null) {
/* 135 */         builder.put(clazz.cast(content));
/*     */       }
/*     */     } 
/*     */     
/* 139 */     MCH_MOD.proxy.onLoadFinishContents(dir);
/* 140 */     return builder.build();
/*     */   }
/*     */   
/*     */   public static ContentLoader getDefaultPackLoader(AddonPack pack) {
/* 144 */     return getPackLoader(pack, ContentRegistries::filter);
/*     */   }
/*     */   
/*     */   public static ContentLoader getPackLoader(AddonPack pack, Predicate<String> fileFilter) {
/* 148 */     String loaderVersion = pack.getLoaderVersion();
/* 149 */     return pack.getFile().isDirectory() ? 
/* 150 */       new FolderContentLoader(pack.getDomain(), pack.getFile(), loaderVersion, fileFilter) : 
/* 151 */       new FileContentLoader(pack.getDomain(), pack.getFile(), loaderVersion, fileFilter);
/*     */   }
/*     */   
/*     */   private static boolean filter(String filepath) {
/* 155 */     String[] split = filepath.split("/");
/* 156 */     String lootDir = (split.length >= 2) ? split[0] : "";
/* 157 */     if (lootDir.equals("assets") && split.length == 4) {
/* 158 */       String modDir = split[1];
/* 159 */       String infoDir = split[2];
/* 160 */       return (modDir.equals("mcheli") && MCH_MOD.proxy.canLoadContentDirName(infoDir));
/*     */     } 
/* 162 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Predicate<String> getFilterOnly(String dir) {
/* 167 */     return filepath -> {
/*     */         String[] split = filepath.split("/");
/*     */         String lootDir = (split.length >= 2) ? split[0] : "";
/*     */         if (lootDir.equals("assets") && split.length == 4) {
/*     */           String modDir = split[1];
/*     */           String infoDir = split[2];
/* 173 */           return (modDir.equals("mcheli") && dir.equals(infoDir));
/*     */         } 
/*     */         return false;
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\info\ContentRegistries.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */