/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_SoundUpdater;
/*     */ import com.norwood.mcheli.helicopter.MCH_HeliInfo;
/*     */ import com.norwood.mcheli.helper.MCH_SoundEvents;
/*     */ import com.norwood.mcheli.helper.MCH_Utils;
/*     */ import com.norwood.mcheli.helper.addon.AddonManager;
/*     */ import com.norwood.mcheli.helper.addon.AddonPack;
/*     */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*     */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*     */ import com.norwood.mcheli.plane.MCP_PlaneInfo;
/*     */ import com.norwood.mcheli.tank.MCH_TankInfo;
/*     */ import com.norwood.mcheli.vehicle.MCH_VehicleInfo;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponInfo;
/*     */ import com.norwood.mcheli.wrapper.W_LanguageRegistry;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ 
/*     */ public class MCH_CommonProxy
/*     */ {
/*  25 */   protected static final String[] CONTENT_DIRS = new String[] { "helicopters", "planes", "tanks", "vehicles", "weapons", "throwable" };
/*  26 */   public MCH_Config config = null;
/*     */   public String lastConfigFileName;
/*     */   
/*     */   public String getDataDir() {
/*  30 */     return MCH_Utils.getServer().func_71270_I();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerRenderer() {}
/*     */ 
/*     */   
/*     */   public void registerBlockRenderer() {}
/*     */ 
/*     */   
/*     */   public void registerModels() {}
/*     */ 
/*     */   
/*     */   public void registerModelsHeli(MCH_HeliInfo info, boolean reload) {}
/*     */ 
/*     */   
/*     */   public void registerModelsPlane(MCP_PlaneInfo info, boolean reload) {}
/*     */ 
/*     */   
/*     */   public void registerModelsVehicle(MCH_VehicleInfo info, boolean reload) {}
/*     */ 
/*     */   
/*     */   public void registerModelsTank(MCH_TankInfo info, boolean reload) {}
/*     */ 
/*     */   
/*     */   public void registerClientTick() {}
/*     */ 
/*     */   
/*     */   public void registerServerTick() {}
/*     */   
/*     */   public boolean isRemote() {
/*  61 */     return false;
/*     */   }
/*     */   
/*     */   public String side() {
/*  65 */     return "Server";
/*     */   }
/*     */   
/*     */   public MCH_SoundUpdater CreateSoundUpdater(MCH_EntityAircraft aircraft) {
/*  69 */     return null;
/*     */   }
/*     */   
/*     */   public void registerSounds() {
/*  73 */     MCH_SoundEvents.registerSoundEventName("alert");
/*  74 */     MCH_SoundEvents.registerSoundEventName("locked");
/*  75 */     MCH_SoundEvents.registerSoundEventName("gltd");
/*  76 */     MCH_SoundEvents.registerSoundEventName("zoom");
/*  77 */     MCH_SoundEvents.registerSoundEventName("ng");
/*  78 */     MCH_SoundEvents.registerSoundEventName("a-10_snd");
/*  79 */     MCH_SoundEvents.registerSoundEventName("gau-8_snd");
/*  80 */     MCH_SoundEvents.registerSoundEventName("hit");
/*  81 */     MCH_SoundEvents.registerSoundEventName("helidmg");
/*  82 */     MCH_SoundEvents.registerSoundEventName("heli");
/*  83 */     MCH_SoundEvents.registerSoundEventName("plane");
/*  84 */     MCH_SoundEvents.registerSoundEventName("plane_cc");
/*  85 */     MCH_SoundEvents.registerSoundEventName("plane_cv");
/*  86 */     MCH_SoundEvents.registerSoundEventName("chain");
/*  87 */     MCH_SoundEvents.registerSoundEventName("chain_ct");
/*  88 */     MCH_SoundEvents.registerSoundEventName("eject_seat");
/*  89 */     MCH_SoundEvents.registerSoundEventName("fim92_snd");
/*  90 */     MCH_SoundEvents.registerSoundEventName("fim92_reload");
/*  91 */     MCH_SoundEvents.registerSoundEventName("lockon");
/*  92 */     MCH_SoundEvents.registerSoundEventName("wrench");
/*     */     
/*  94 */     for (MCH_WeaponInfo info : ContentRegistries.weapon().values()) {
/*  95 */       MCH_SoundEvents.registerSoundEventName(info.soundFileName);
/*     */     }
/*     */     
/*  98 */     for (MCH_AircraftInfo info : ContentRegistries.plane().values()) {
/*  99 */       if (!info.soundMove.isEmpty()) {
/* 100 */         MCH_SoundEvents.registerSoundEventName(info.soundMove);
/*     */       }
/*     */     } 
/*     */     
/* 104 */     for (MCH_AircraftInfo infox : ContentRegistries.heli().values()) {
/* 105 */       if (!infox.soundMove.isEmpty()) {
/* 106 */         MCH_SoundEvents.registerSoundEventName(infox.soundMove);
/*     */       }
/*     */     } 
/*     */     
/* 110 */     for (MCH_AircraftInfo infoxx : ContentRegistries.tank().values()) {
/* 111 */       if (!infoxx.soundMove.isEmpty()) {
/* 112 */         MCH_SoundEvents.registerSoundEventName(infoxx.soundMove);
/*     */       }
/*     */     } 
/*     */     
/* 116 */     for (MCH_AircraftInfo infoxxx : ContentRegistries.vehicle().values()) {
/* 117 */       if (!infoxxx.soundMove.isEmpty()) {
/* 118 */         MCH_SoundEvents.registerSoundEventName(infoxxx.soundMove);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadConfig(String fileName) {
/* 124 */     this.lastConfigFileName = fileName;
/* 125 */     this.config = new MCH_Config("./", fileName);
/* 126 */     this.config.load();
/* 127 */     this.config.write();
/*     */   }
/*     */   
/*     */   public void reconfig() {
/* 131 */     MCH_Lib.DbgLog(false, "MCH_CommonProxy.reconfig()", new Object[0]);
/* 132 */     loadConfig(this.lastConfigFileName);
/*     */   }
/*     */   
/*     */   public void save() {
/* 136 */     MCH_Lib.DbgLog(false, "MCH_CommonProxy.save()", new Object[0]);
/* 137 */     this.config.write();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadHUD() {}
/*     */   
/*     */   public Entity getClientPlayer() {
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCreativeDigDelay(int n) {}
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */   
/*     */   public boolean isFirstPerson() {
/* 154 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isSinglePlayer() {
/* 158 */     return MCH_Utils.getServer().func_71264_H();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readClientModList() {}
/*     */ 
/*     */   
/*     */   public void printChatMessage(ITextComponent chat, int showTime, int pos) {}
/*     */ 
/*     */   
/*     */   public void hitBullet() {}
/*     */ 
/*     */   
/*     */   public void clientLocked() {}
/*     */ 
/*     */   
/*     */   public void setRenderEntityDistanceWeight(double renderDistWeight) {}
/*     */   
/*     */   public List<AddonPack> loadAddonPacks(File addonDir) {
/* 177 */     return AddonManager.loadAddons(addonDir);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoadStartAddons(int addonSize) {}
/*     */   
/*     */   public void onLoadStepAddon(String addonDomain) {
/* 184 */     MCH_Utils.logger().debug("addon({}) loading start.", addonDomain);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoadFinishAddons() {}
/*     */   
/*     */   public void onLoadStartContents(String typeName, int fileSize) {
/* 191 */     MCH_Utils.logger().debug("content type({}) loading start. steps:{}", typeName, Integer.valueOf(fileSize));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoadFinishContents(String typeName) {}
/*     */   
/*     */   public void onParseStartFile(AddonResourceLocation location) {
/* 198 */     MCH_Utils.logger().debug("content file({}) loading start.", location);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onParseFinishFile(AddonResourceLocation location) {}
/*     */   
/*     */   public boolean canLoadContentDirName(String dir) {
/* 205 */     return MCH_Utils.inArray((Object[])CONTENT_DIRS, dir);
/*     */   }
/*     */   
/*     */   public void updateGeneratedLanguage() {
/* 209 */     W_LanguageRegistry.clear();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void updateSoundsJson() {}
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_CommonProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */