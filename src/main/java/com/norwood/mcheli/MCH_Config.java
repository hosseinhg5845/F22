/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import com.norwood.mcheli.wrapper.W_Block;
/*     */ import java.io.File;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.DamageSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_Config
/*     */ {
/*  21 */   public static final List<Block> dummyBreakableBlocks = new ArrayList<>();
/*  22 */   public static final List<Material> dummyBreakableMaterials = new ArrayList<>();
/*     */   public static String mcPath;
/*     */   public static String configFilePath;
/*     */   public static boolean DebugLog;
/*     */   public static String configVer;
/*     */   public static int hitMarkColorRGB;
/*     */   public static float hitMarkColorAlpha;
/*     */   public static List<Block> bulletBreakableBlocks;
/*     */   public static List<Block> carNoBreakableBlocks;
/*     */   public static List<Block> carBreakableBlocks;
/*     */   public static List<Material> carBreakableMaterials;
/*     */   public static List<Block> tankNoBreakableBlocks;
/*     */   public static List<Block> tankBreakableBlocks;
/*     */   public static List<Material> tankBreakableMaterials;
/*     */   public static MCH_ConfigPrm KeyUp;
/*     */   public static MCH_ConfigPrm KeyDown;
/*     */   public static MCH_ConfigPrm KeyRight;
/*     */   public static MCH_ConfigPrm KeyLeft;
/*     */   public static MCH_ConfigPrm KeySwitchMode;
/*     */   public static MCH_ConfigPrm KeySwitchHovering;
/*     */   public static MCH_ConfigPrm KeyAttack;
/*     */   public static MCH_ConfigPrm KeyUseWeapon;
/*     */   public static MCH_ConfigPrm KeySwitchWeapon1;
/*     */   public static MCH_ConfigPrm KeySwitchWeapon2;
/*     */   public static MCH_ConfigPrm KeySwWeaponMode;
/*     */   public static MCH_ConfigPrm KeyZoom;
/*     */   public static MCH_ConfigPrm KeyCameraMode;
/*     */   public static MCH_ConfigPrm KeyUnmount;
/*     */   public static MCH_ConfigPrm KeyFlare;
/*     */   public static MCH_ConfigPrm KeyExtra;
/*     */   public static MCH_ConfigPrm KeyCameraDistUp;
/*     */   public static MCH_ConfigPrm KeyCameraDistDown;
/*     */   public static MCH_ConfigPrm KeyFreeLook;
/*     */   public static MCH_ConfigPrm KeyGUI;
/*     */   public static MCH_ConfigPrm KeyGearUpDown;
/*     */   public static MCH_ConfigPrm KeyPutToRack;
/*     */   public static MCH_ConfigPrm KeyDownFromRack;
/*     */   public static MCH_ConfigPrm KeyScoreboard;
/*     */   public static MCH_ConfigPrm KeyMultiplayManager;
/*     */   public static List<MCH_ConfigPrm> DamageVs;
/*     */   public static List<String> IgnoreBulletHitList;
/*     */   public static MCH_ConfigPrm IgnoreBulletHitItem;
/*     */   public static DamageFactor[] DamageFactorList;
/*     */   public static DamageFactor DamageVsEntity;
/*     */   public static DamageFactor DamageVsLiving;
/*     */   public static DamageFactor DamageVsPlayer;
/*     */   public static DamageFactor DamageVsMCHeliAircraft;
/*     */   public static DamageFactor DamageVsMCHeliTank;
/*     */   public static DamageFactor DamageVsMCHeliVehicle;
/*     */   public static DamageFactor DamageVsMCHeliOther;
/*     */   public static DamageFactor DamageAircraftByExternal;
/*     */   public static DamageFactor DamageTankByExternal;
/*     */   public static DamageFactor DamageVehicleByExternal;
/*     */   public static DamageFactor DamageOtherByExternal;
/*     */   public static List<MCH_ConfigPrm> CommandPermission;
/*     */   public static List<CommandPermission> CommandPermissionList;
/*     */   public static MCH_ConfigPrm TestMode;
/*     */   public static MCH_ConfigPrm __TextureAlpha;
/*     */   public static MCH_ConfigPrm EnableCommand;
/*     */   public static MCH_ConfigPrm PlaceableOnSpongeOnly;
/*     */   public static MCH_ConfigPrm HideKeybind;
/*     */   public static MCH_ConfigPrm ItemDamage;
/*     */   public static MCH_ConfigPrm ItemFuel;
/*     */   public static MCH_ConfigPrm AutoRepairHP;
/*     */   public static MCH_ConfigPrm Collision_DestroyBlock;
/*     */   public static MCH_ConfigPrm Explosion_DestroyBlock;
/*     */   public static MCH_ConfigPrm Explosion_FlamingBlock;
/*     */   public static MCH_ConfigPrm BulletBreakableBlock;
/*     */   public static MCH_ConfigPrm Collision_Car_BreakableBlock;
/*     */   public static MCH_ConfigPrm Collision_Car_NoBreakableBlock;
/*     */   public static MCH_ConfigPrm Collision_Car_BreakableMaterial;
/*     */   public static MCH_ConfigPrm Collision_Tank_BreakableBlock;
/*     */   public static MCH_ConfigPrm Collision_Tank_NoBreakableBlock;
/*     */   public static MCH_ConfigPrm Collision_Tank_BreakableMaterial;
/*     */   public static MCH_ConfigPrm Collision_EntityDamage;
/*     */   public static MCH_ConfigPrm Collision_EntityTankDamage;
/*     */   public static MCH_ConfigPrm LWeaponAutoFire;
/*     */   public static MCH_ConfigPrm DismountAll;
/*     */   public static MCH_ConfigPrm MountMinecartHeli;
/*     */   public static MCH_ConfigPrm MountMinecartPlane;
/*     */   public static MCH_ConfigPrm MountMinecartVehicle;
/*     */   public static MCH_ConfigPrm MountMinecartTank;
/*     */   public static MCH_ConfigPrm AutoThrottleDownHeli;
/*     */   public static MCH_ConfigPrm AutoThrottleDownPlane;
/*     */   public static MCH_ConfigPrm AutoThrottleDownTank;
/*     */   public static MCH_ConfigPrm DisableItemRender;
/*     */   public static MCH_ConfigPrm RenderDistanceWeight;
/*     */   public static MCH_ConfigPrm MobRenderDistanceWeight;
/*     */   public static MCH_ConfigPrm CreativeTabIcon;
/*     */   public static MCH_ConfigPrm CreativeTabIconHeli;
/*     */   public static MCH_ConfigPrm CreativeTabIconPlane;
/*     */   public static MCH_ConfigPrm CreativeTabIconTank;
/*     */   public static MCH_ConfigPrm CreativeTabIconVehicle;
/*     */   public static MCH_ConfigPrm DisableShader;
/*     */   public static MCH_ConfigPrm AliveTimeOfCartridge;
/*     */   public static MCH_ConfigPrm InfinityAmmo;
/*     */   public static MCH_ConfigPrm InfinityFuel;
/*     */   public static MCH_ConfigPrm HitMarkColor;
/*     */   public static MCH_ConfigPrm SmoothShading;
/*     */   public static MCH_ConfigPrm EnableModEntityRender;
/*     */   public static MCH_ConfigPrm DisableRenderLivingSpecials;
/*     */   public static MCH_ConfigPrm PreventingBroken;
/*     */   public static MCH_ConfigPrm DropItemInCreativeMode;
/*     */   public static MCH_ConfigPrm BreakableOnlyPickaxe;
/*     */   public static MCH_ConfigPrm InvertMouse;
/*     */   public static MCH_ConfigPrm MouseSensitivity;
/*     */   public static MCH_ConfigPrm MouseControlStickModeHeli;
/*     */   public static MCH_ConfigPrm MouseControlStickModePlane;
/*     */   public static MCH_ConfigPrm MouseControlFlightSimMode;
/*     */   public static MCH_ConfigPrm SwitchWeaponWithMouseWheel;
/*     */   public static MCH_ConfigPrm AllPlaneSpeed;
/*     */   public static MCH_ConfigPrm AllHeliSpeed;
/*     */   public static MCH_ConfigPrm AllTankSpeed;
/*     */   public static MCH_ConfigPrm HurtResistantTime;
/*     */   public static MCH_ConfigPrm DisplayHUDThirdPerson;
/*     */   public static MCH_ConfigPrm DisableCameraDistChange;
/*     */   public static MCH_ConfigPrm EnableReplaceTextureManager;
/*     */   public static MCH_ConfigPrm DisplayEntityMarker;
/*     */   public static MCH_ConfigPrm EntityMarkerSize;
/*     */   public static MCH_ConfigPrm BlockMarkerSize;
/*     */   public static MCH_ConfigPrm DisplayMarkThroughWall;
/*     */   public static MCH_ConfigPrm ReplaceRenderViewEntity;
/*     */   public static MCH_ConfigPrm StingerLockRange;
/*     */   public static MCH_ConfigPrm DefaultExplosionParticle;
/*     */   public static MCH_ConfigPrm RangeFinderSpotDist;
/*     */   public static MCH_ConfigPrm RangeFinderSpotTime;
/*     */   public static MCH_ConfigPrm RangeFinderConsume;
/*     */   public static MCH_ConfigPrm EnablePutRackInFlying;
/*     */   public static MCH_ConfigPrm EnableDebugBoundingBox;
/*     */   public static MCH_ConfigPrm DespawnCount;
/*     */   public static MCH_ConfigPrm HitBoxDelayTick;
/*     */   public static MCH_ConfigPrm EnableRotationLimit;
/*     */   public static MCH_ConfigPrm PitchLimitMax;
/*     */   public static MCH_ConfigPrm PitchLimitMin;
/*     */   public static MCH_ConfigPrm RollLimit;
/*     */   public static MCH_ConfigPrm RangeOfGunner_VsMonster_Vertical;
/*     */   public static MCH_ConfigPrm RangeOfGunner_VsMonster_Horizontal;
/*     */   public static MCH_ConfigPrm RangeOfGunner_VsPlayer_Vertical;
/*     */   public static MCH_ConfigPrm RangeOfGunner_VsPlayer_Horizontal;
/*     */   public static MCH_ConfigPrm FixVehicleAtPlacedPoint;
/*     */   public static MCH_ConfigPrm KillPassengersWhenDestroyed;
/*     */   public static MCH_ConfigPrm ItemID_Fuel;
/*     */   public static MCH_ConfigPrm ItemID_GLTD;
/*     */   public static MCH_ConfigPrm ItemID_Chain;
/*     */   public static MCH_ConfigPrm ItemID_Parachute;
/*     */   public static MCH_ConfigPrm ItemID_Container;
/*     */   public static MCH_ConfigPrm ItemID_Stinger;
/*     */   public static MCH_ConfigPrm ItemID_StingerMissile;
/*     */   public static MCH_ConfigPrm[] ItemID_UavStation;
/*     */   public static MCH_ConfigPrm ItemID_InvisibleItem;
/*     */   public static MCH_ConfigPrm ItemID_DraftingTable;
/*     */   public static MCH_ConfigPrm ItemID_Wrench;
/*     */   public static MCH_ConfigPrm ItemID_RangeFinder;
/*     */   public static MCH_ConfigPrm BlockID_DraftingTableOFF;
/*     */   public static MCH_ConfigPrm BlockID_DraftingTableON;
/*     */   public static MCH_ConfigPrm[] KeyConfig;
/*     */   public static MCH_ConfigPrm[] General;
/* 179 */   public final String destroyBlockNames = "glass_pane, stained_glass_pane, tallgrass, double_plant, yellow_flower, red_flower, vine, wheat, reeds, waterlily";
/*     */   
/*     */   public MCH_Config(String minecraftPath, String cfgFile) {
/* 182 */     mcPath = minecraftPath;
/* 183 */     configFilePath = mcPath + cfgFile;
/* 184 */     DebugLog = false;
/* 185 */     configVer = "0.0.0";
/* 186 */     bulletBreakableBlocks = new ArrayList<>();
/* 187 */     carBreakableBlocks = new ArrayList<>();
/* 188 */     carNoBreakableBlocks = new ArrayList<>();
/* 189 */     carBreakableMaterials = new ArrayList<>();
/* 190 */     tankBreakableBlocks = new ArrayList<>();
/* 191 */     tankNoBreakableBlocks = new ArrayList<>();
/* 192 */     tankBreakableMaterials = new ArrayList<>();
/* 193 */     KeyUp = new MCH_ConfigPrm("KeyUp", 17);
/* 194 */     KeyDown = new MCH_ConfigPrm("KeyDown", 31);
/* 195 */     KeyRight = new MCH_ConfigPrm("KeyRight", 32);
/* 196 */     KeyLeft = new MCH_ConfigPrm("KeyLeft", 30);
/* 197 */     KeySwitchMode = new MCH_ConfigPrm("KeySwitchGunner", 35);
/* 198 */     KeySwitchHovering = new MCH_ConfigPrm("KeySwitchHovering", 57);
/* 199 */     KeyAttack = new MCH_ConfigPrm("KeyAttack", -100);
/* 200 */     KeyUseWeapon = new MCH_ConfigPrm("KeyUseWeapon", -99);
/* 201 */     KeySwitchWeapon1 = new MCH_ConfigPrm("KeySwitchWeapon1", -98);
/* 202 */     KeySwitchWeapon2 = new MCH_ConfigPrm("KeySwitchWeapon2", 34);
/* 203 */     KeySwWeaponMode = new MCH_ConfigPrm("KeySwitchWeaponMode", 45);
/* 204 */     KeyZoom = new MCH_ConfigPrm("KeyZoom", 44);
/* 205 */     KeyCameraMode = new MCH_ConfigPrm("KeyCameraMode", 46);
/* 206 */     KeyUnmount = new MCH_ConfigPrm("KeyUnmountMob", 21);
/* 207 */     KeyFlare = new MCH_ConfigPrm("KeyFlare", 47);
/* 208 */     KeyExtra = new MCH_ConfigPrm("KeyExtra", 33);
/* 209 */     KeyCameraDistUp = new MCH_ConfigPrm("KeyCameraDistanceUp", 201);
/* 210 */     KeyCameraDistDown = new MCH_ConfigPrm("KeyCameraDistanceDown", 209);
/* 211 */     KeyFreeLook = new MCH_ConfigPrm("KeyFreeLook", 29);
/* 212 */     KeyGUI = new MCH_ConfigPrm("KeyGUI", 19);
/* 213 */     KeyGearUpDown = new MCH_ConfigPrm("KeyGearUpDown", 48);
/* 214 */     KeyPutToRack = new MCH_ConfigPrm("KeyPutToRack", 36);
/* 215 */     KeyDownFromRack = new MCH_ConfigPrm("KeyDownFromRack", 22);
/* 216 */     KeyScoreboard = new MCH_ConfigPrm("KeyScoreboard", 38);
/* 217 */     KeyMultiplayManager = new MCH_ConfigPrm("KeyMultiplayManager", 50);
/* 218 */     KeyConfig = new MCH_ConfigPrm[] { KeyUp, KeyDown, KeyRight, KeyLeft, KeySwitchMode, KeySwitchHovering, KeySwitchWeapon1, KeySwitchWeapon2, KeySwWeaponMode, KeyZoom, KeyCameraMode, KeyUnmount, KeyFlare, KeyExtra, KeyCameraDistUp, KeyCameraDistDown, KeyFreeLook, KeyGUI, KeyGearUpDown, KeyPutToRack, KeyDownFromRack, KeyScoreboard, KeyMultiplayManager };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     DamageVs = new ArrayList<>();
/* 244 */     CommandPermission = new ArrayList<>();
/* 245 */     CommandPermissionList = new ArrayList<>();
/* 246 */     IgnoreBulletHitList = new ArrayList<>();
/* 247 */     IgnoreBulletHitItem = new MCH_ConfigPrm("IgnoreBulletHit", "");
/* 248 */     TestMode = new MCH_ConfigPrm("TestMode", false);
/* 249 */     __TextureAlpha = new MCH_ConfigPrm("__TextureAlphaDebug", 1.0D);
/* 250 */     EnableCommand = new MCH_ConfigPrm("EnableCommand", true);
/* 251 */     PlaceableOnSpongeOnly = new MCH_ConfigPrm("PlaceableOnSpongeOnly", false);
/* 252 */     HideKeybind = new MCH_ConfigPrm("HideKeybind", false);
/* 253 */     ItemDamage = new MCH_ConfigPrm("ItemDamage", true);
/* 254 */     ItemFuel = new MCH_ConfigPrm("ItemFuel", true);
/* 255 */     AutoRepairHP = new MCH_ConfigPrm("AutoRepairHP", 0.5D);
/* 256 */     Collision_DestroyBlock = new MCH_ConfigPrm("Collision_DestroyBlock", true);
/* 257 */     Explosion_DestroyBlock = new MCH_ConfigPrm("Explosion_DestroyBlock", true);
/* 258 */     Explosion_FlamingBlock = new MCH_ConfigPrm("Explosion_FlamingBlock", true);
/* 259 */     Collision_Car_BreakableBlock = new MCH_ConfigPrm("Collision_Car_BreakableBlock", "double_plant, glass_pane,stained_glass_pane");
/* 260 */     Collision_Car_NoBreakableBlock = new MCH_ConfigPrm("Collision_Car_NoBreakBlock", "torch");
/* 261 */     Collision_Car_BreakableMaterial = new MCH_ConfigPrm("Collision_Car_BreakableMaterial", "cactus, cake, gourd, leaves, vine, plants");
/* 262 */     Collision_Tank_BreakableBlock = new MCH_ConfigPrm("Collision_Tank_BreakableBlock", "nether_brick_fence");
/* 263 */     Collision_Tank_BreakableBlock.validVer = "1.0.0";
/* 264 */     Collision_Tank_NoBreakableBlock = new MCH_ConfigPrm("Collision_Tank_NoBreakBlock", "torch, glowstone");
/* 265 */     Collision_Tank_BreakableMaterial = new MCH_ConfigPrm("Collision_Tank_BreakableMaterial", "cactus, cake, carpet, circuits, glass, gourd, leaves, vine, wood, plants");
/*     */ 
/*     */     
/* 268 */     Collision_EntityDamage = new MCH_ConfigPrm("Collision_EntityDamage", true);
/* 269 */     Collision_EntityTankDamage = new MCH_ConfigPrm("Collision_EntityTankDamage", false);
/* 270 */     LWeaponAutoFire = new MCH_ConfigPrm("LWeaponAutoFire", false);
/* 271 */     DismountAll = new MCH_ConfigPrm("DismountAll", false);
/* 272 */     MountMinecartHeli = new MCH_ConfigPrm("MountMinecartHeli", true);
/* 273 */     MountMinecartPlane = new MCH_ConfigPrm("MountMinecartPlane", true);
/* 274 */     MountMinecartVehicle = new MCH_ConfigPrm("MountMinecartVehicle", false);
/* 275 */     MountMinecartTank = new MCH_ConfigPrm("MountMinecartTank", true);
/* 276 */     AutoThrottleDownHeli = new MCH_ConfigPrm("AutoThrottleDownHeli", true);
/* 277 */     AutoThrottleDownPlane = new MCH_ConfigPrm("AutoThrottleDownPlane", false);
/* 278 */     AutoThrottleDownTank = new MCH_ConfigPrm("AutoThrottleDownTank", false);
/* 279 */     DisableItemRender = new MCH_ConfigPrm("DisableItemRender", 1);
/* 280 */     DisableItemRender.desc = ";DisableItemRender = 0 ~ 3 (1 = Recommended)";
/* 281 */     RenderDistanceWeight = new MCH_ConfigPrm("RenderDistanceWeight", 10.0D);
/* 282 */     MobRenderDistanceWeight = new MCH_ConfigPrm("MobRenderDistanceWeight", 1.0D);
/* 283 */     CreativeTabIcon = new MCH_ConfigPrm("CreativeTabIconItem", "fuel");
/* 284 */     CreativeTabIconHeli = new MCH_ConfigPrm("CreativeTabIconHeli", "ah-64");
/* 285 */     CreativeTabIconPlane = new MCH_ConfigPrm("CreativeTabIconPlane", "f22a");
/* 286 */     CreativeTabIconTank = new MCH_ConfigPrm("CreativeTabIconTank", "merkava_mk4");
/* 287 */     CreativeTabIconVehicle = new MCH_ConfigPrm("CreativeTabIconVehicle", "mk15");
/* 288 */     DisableShader = new MCH_ConfigPrm("DisableShader", false);
/* 289 */     AliveTimeOfCartridge = new MCH_ConfigPrm("AliveTimeOfCartridge", 200);
/* 290 */     InfinityAmmo = new MCH_ConfigPrm("InfinityAmmo", false);
/* 291 */     InfinityFuel = new MCH_ConfigPrm("InfinityFuel", false);
/* 292 */     HitMarkColor = new MCH_ConfigPrm("HitMarkColor", "255, 255, 0, 0");
/* 293 */     HitMarkColor.desc = ";HitMarkColor = Alpha, Red, Green, Blue";
/* 294 */     SmoothShading = new MCH_ConfigPrm("SmoothShading", true);
/* 295 */     BulletBreakableBlock = new MCH_ConfigPrm("BulletBreakableBlocks", "glass_pane, stained_glass_pane, tallgrass, double_plant, yellow_flower, red_flower, vine, wheat, reeds, waterlily");
/*     */ 
/*     */     
/* 298 */     BulletBreakableBlock.validVer = "0.10.4";
/* 299 */     EnableModEntityRender = new MCH_ConfigPrm("EnableModEntityRender", true);
/* 300 */     DisableRenderLivingSpecials = new MCH_ConfigPrm("DisableRenderLivingSpecials", true);
/* 301 */     PreventingBroken = new MCH_ConfigPrm("PreventingBroken", false);
/* 302 */     DropItemInCreativeMode = new MCH_ConfigPrm("DropItemInCreativeMode", false);
/* 303 */     BreakableOnlyPickaxe = new MCH_ConfigPrm("BreakableOnlyPickaxe", false);
/* 304 */     InvertMouse = new MCH_ConfigPrm("InvertMouse", false);
/* 305 */     MouseSensitivity = new MCH_ConfigPrm("MouseSensitivity", 10.0D);
/* 306 */     MouseControlStickModeHeli = new MCH_ConfigPrm("MouseControlStickModeHeli", false);
/* 307 */     MouseControlStickModePlane = new MCH_ConfigPrm("MouseControlStickModePlane", false);
/* 308 */     MouseControlFlightSimMode = new MCH_ConfigPrm("MouseControlFlightSimMode", false);
/* 309 */     MouseControlFlightSimMode.desc = ";MouseControlFlightSimMode = true ( Yaw:key, Roll=mouse )";
/* 310 */     SwitchWeaponWithMouseWheel = new MCH_ConfigPrm("SwitchWeaponWithMouseWheel", true);
/* 311 */     AllHeliSpeed = new MCH_ConfigPrm("AllHeliSpeed", 1.0D);
/* 312 */     AllPlaneSpeed = new MCH_ConfigPrm("AllPlaneSpeed", 1.0D);
/* 313 */     AllTankSpeed = new MCH_ConfigPrm("AllTankSpeed", 1.0D);
/* 314 */     HurtResistantTime = new MCH_ConfigPrm("HurtResistantTime", 0.0D);
/* 315 */     DisplayHUDThirdPerson = new MCH_ConfigPrm("DisplayHUDThirdPerson", false);
/* 316 */     DisableCameraDistChange = new MCH_ConfigPrm("DisableThirdPersonCameraDistChange", false);
/* 317 */     EnableReplaceTextureManager = new MCH_ConfigPrm("EnableReplaceTextureManager", true);
/* 318 */     DisplayEntityMarker = new MCH_ConfigPrm("DisplayEntityMarker", true);
/* 319 */     DisplayMarkThroughWall = new MCH_ConfigPrm("DisplayMarkThroughWall", true);
/* 320 */     EntityMarkerSize = new MCH_ConfigPrm("EntityMarkerSize", 10.0D);
/* 321 */     BlockMarkerSize = new MCH_ConfigPrm("BlockMarkerSize", 10.0D);
/* 322 */     ReplaceRenderViewEntity = new MCH_ConfigPrm("ReplaceRenderViewEntity", true);
/* 323 */     StingerLockRange = new MCH_ConfigPrm("StingerLockRange", 320.0D);
/* 324 */     StingerLockRange.validVer = "1.0.0";
/* 325 */     DefaultExplosionParticle = new MCH_ConfigPrm("DefaultExplosionParticle", false);
/* 326 */     RangeFinderSpotDist = new MCH_ConfigPrm("RangeFinderSpotDist", 400);
/* 327 */     RangeFinderSpotTime = new MCH_ConfigPrm("RangeFinderSpotTime", 15);
/* 328 */     RangeFinderConsume = new MCH_ConfigPrm("RangeFinderConsume", true);
/* 329 */     EnablePutRackInFlying = new MCH_ConfigPrm("EnablePutRackInFlying", true);
/* 330 */     EnableDebugBoundingBox = new MCH_ConfigPrm("EnableDebugBoundingBox", true);
/* 331 */     DespawnCount = new MCH_ConfigPrm("DespawnCount", 25);
/* 332 */     HitBoxDelayTick = new MCH_ConfigPrm("HitBoxDelayTick", 0);
/* 333 */     EnableRotationLimit = new MCH_ConfigPrm("EnableRotationLimit", false);
/* 334 */     PitchLimitMax = new MCH_ConfigPrm("PitchLimitMax", 10);
/* 335 */     PitchLimitMin = new MCH_ConfigPrm("PitchLimitMin", -10);
/* 336 */     RollLimit = new MCH_ConfigPrm("RollLimit", 35);
/* 337 */     RangeOfGunner_VsMonster_Horizontal = new MCH_ConfigPrm("RangeOfGunner_VsMonster_Horizontal", 80);
/* 338 */     RangeOfGunner_VsMonster_Vertical = new MCH_ConfigPrm("RangeOfGunner_VsMonster_Vertical", 160);
/* 339 */     RangeOfGunner_VsPlayer_Horizontal = new MCH_ConfigPrm("RangeOfGunner_VsPlayer_Horizontal", 200);
/* 340 */     RangeOfGunner_VsPlayer_Vertical = new MCH_ConfigPrm("RangeOfGunner_VsPlayer_Vertical", 300);
/* 341 */     FixVehicleAtPlacedPoint = new MCH_ConfigPrm("FixVehicleAtPlacedPoint", true);
/* 342 */     KillPassengersWhenDestroyed = new MCH_ConfigPrm("KillPassengersWhenDestroyed", false);
/* 343 */     hitMarkColorAlpha = 1.0F;
/* 344 */     hitMarkColorRGB = 16711680;
/* 345 */     ItemID_GLTD = new MCH_ConfigPrm("ItemID_GLTD", 28799);
/* 346 */     ItemID_Chain = new MCH_ConfigPrm("ItemID_Chain", 28798);
/* 347 */     ItemID_Parachute = new MCH_ConfigPrm("ItemID_Parachute", 28797);
/* 348 */     ItemID_Container = new MCH_ConfigPrm("ItemID_Container", 28796);
/* 349 */     ItemID_UavStation = new MCH_ConfigPrm[] { new MCH_ConfigPrm("ItemID_UavStation", 28795), new MCH_ConfigPrm("ItemID_UavStation2", 28790) };
/* 350 */     ItemID_InvisibleItem = new MCH_ConfigPrm("ItemID_Internal", 28794);
/* 351 */     ItemID_Fuel = new MCH_ConfigPrm("ItemID_Fuel", 28793);
/* 352 */     ItemID_DraftingTable = new MCH_ConfigPrm("ItemID_DraftingTable", 28792);
/* 353 */     ItemID_Wrench = new MCH_ConfigPrm("ItemID_Wrench", 28791);
/* 354 */     ItemID_RangeFinder = new MCH_ConfigPrm("ItemID_RangeFinder", 28789);
/* 355 */     ItemID_Stinger = new MCH_ConfigPrm("ItemID_Stinger", 28900);
/* 356 */     ItemID_StingerMissile = new MCH_ConfigPrm("ItemID_StingerMissile", 28901);
/* 357 */     BlockID_DraftingTableOFF = new MCH_ConfigPrm("BlockID_DraftingTable", 3450);
/* 358 */     BlockID_DraftingTableON = new MCH_ConfigPrm("BlockID_DraftingTableON", 3451);
/* 359 */     General = new MCH_ConfigPrm[] { TestMode, __TextureAlpha, EnableCommand, null, PlaceableOnSpongeOnly, ItemDamage, ItemFuel, AutoRepairHP, Explosion_DestroyBlock, Explosion_FlamingBlock, BulletBreakableBlock, Collision_DestroyBlock, Collision_Car_BreakableBlock, Collision_Car_BreakableMaterial, Collision_Tank_BreakableBlock, Collision_Tank_BreakableMaterial, Collision_EntityDamage, Collision_EntityTankDamage, InfinityAmmo, InfinityFuel, DismountAll, MountMinecartHeli, MountMinecartPlane, MountMinecartVehicle, MountMinecartTank, PreventingBroken, DropItemInCreativeMode, BreakableOnlyPickaxe, AllHeliSpeed, AllPlaneSpeed, AllTankSpeed, HurtResistantTime, StingerLockRange, RangeFinderSpotDist, RangeFinderSpotTime, RangeFinderConsume, EnablePutRackInFlying, EnableDebugBoundingBox, DespawnCount, HitBoxDelayTick, EnableRotationLimit, PitchLimitMax, PitchLimitMin, RollLimit, RangeOfGunner_VsMonster_Horizontal, RangeOfGunner_VsMonster_Vertical, RangeOfGunner_VsPlayer_Horizontal, RangeOfGunner_VsPlayer_Vertical, FixVehicleAtPlacedPoint, KillPassengersWhenDestroyed, null, InvertMouse, MouseSensitivity, MouseControlStickModeHeli, MouseControlStickModePlane, MouseControlFlightSimMode, AutoThrottleDownHeli, AutoThrottleDownPlane, AutoThrottleDownTank, SwitchWeaponWithMouseWheel, LWeaponAutoFire, DisableItemRender, HideKeybind, RenderDistanceWeight, MobRenderDistanceWeight, CreativeTabIcon, CreativeTabIconHeli, CreativeTabIconPlane, CreativeTabIconTank, CreativeTabIconVehicle, DisableShader, DefaultExplosionParticle, AliveTimeOfCartridge, HitMarkColor, SmoothShading, EnableModEntityRender, DisableRenderLivingSpecials, DisplayHUDThirdPerson, DisableCameraDistChange, EnableReplaceTextureManager, DisplayEntityMarker, EntityMarkerSize, BlockMarkerSize, ReplaceRenderViewEntity, null };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 446 */     DamageVsEntity = new DamageFactor(this, "DamageVsEntity");
/* 447 */     DamageVsLiving = new DamageFactor(this, "DamageVsLiving");
/* 448 */     DamageVsPlayer = new DamageFactor(this, "DamageVsPlayer");
/* 449 */     DamageVsMCHeliAircraft = new DamageFactor(this, "DamageVsMCHeliAircraft");
/* 450 */     DamageVsMCHeliTank = new DamageFactor(this, "DamageVsMCHeliTank");
/* 451 */     DamageVsMCHeliVehicle = new DamageFactor(this, "DamageVsMCHeliVehicle");
/* 452 */     DamageVsMCHeliOther = new DamageFactor(this, "DamageVsMCHeliOther");
/* 453 */     DamageAircraftByExternal = new DamageFactor(this, "DamageMCHeliAircraftByExternal");
/* 454 */     DamageTankByExternal = new DamageFactor(this, "DamageMCHeliTankByExternal");
/* 455 */     DamageVehicleByExternal = new DamageFactor(this, "DamageMCHeliVehicleByExternal");
/* 456 */     DamageOtherByExternal = new DamageFactor(this, "DamageMCHeliOtherByExternal");
/* 457 */     DamageFactorList = new DamageFactor[] { DamageVsEntity, DamageVsLiving, DamageVsPlayer, DamageVsMCHeliAircraft, DamageVsMCHeliTank, DamageVsMCHeliVehicle, DamageVsMCHeliOther, DamageAircraftByExternal, DamageTankByExternal, DamageVehicleByExternal, DamageOtherByExternal };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float applyDamageByExternal(Entity target, DamageSource ds, float damage) {
/*     */     List<DamageEntity> list;
/* 474 */     if (target instanceof com.norwood.mcheli.helicopter.MCH_EntityHeli || target instanceof com.norwood.mcheli.plane.MCP_EntityPlane) {
/* 475 */       list = DamageAircraftByExternal.list;
/* 476 */     } else if (target instanceof com.norwood.mcheli.tank.MCH_EntityTank) {
/* 477 */       list = DamageTankByExternal.list;
/* 478 */     } else if (target instanceof com.norwood.mcheli.vehicle.MCH_EntityVehicle) {
/* 479 */       list = DamageVehicleByExternal.list;
/*     */     } else {
/* 481 */       list = DamageOtherByExternal.list;
/*     */     } 
/*     */     
/* 484 */     Entity attacker = ds.getTrueSource();
/* 485 */     Entity attackerSource = ds.getImmediateSource();
/*     */     
/* 487 */     for (DamageEntity de : list) {
/* 488 */       if (de.name.isEmpty() || (attacker != null && attacker
/* 489 */         .getClass().toString().indexOf(de.name) > 0) || (attackerSource != null && attackerSource
/* 490 */         .getClass().toString().indexOf(de.name) > 0)) {
/* 491 */         damage = (float)(damage * de.factor);
/*     */       }
/*     */     } 
/*     */     
/* 495 */     return damage;
/*     */   }
/*     */   
/*     */   public static float applyDamageVsEntity(Entity target, DamageSource ds, float damage) {
/* 499 */     if (target != null) {
/* 500 */       List<DamageEntity> list; String targetName = target.getClass().toString();
/*     */       
/* 502 */       if (target instanceof com.norwood.mcheli.helicopter.MCH_EntityHeli || target instanceof com.norwood.mcheli.plane.MCP_EntityPlane) {
/* 503 */         list = DamageVsMCHeliAircraft.list;
/* 504 */       } else if (target instanceof com.norwood.mcheli.tank.MCH_EntityTank) {
/* 505 */         list = DamageVsMCHeliTank.list;
/* 506 */       } else if (target instanceof com.norwood.mcheli.vehicle.MCH_EntityVehicle) {
/* 507 */         list = DamageVsMCHeliVehicle.list;
/* 508 */       } else if (targetName.indexOf("com.norwood.mcheli.") > 0) {
/* 509 */         list = DamageVsMCHeliOther.list;
/* 510 */       } else if (target instanceof net.minecraft.entity.player.EntityPlayer) {
/* 511 */         list = DamageVsPlayer.list;
/* 512 */       } else if (target instanceof net.minecraft.entity.EntityLivingBase) {
/* 513 */         list = DamageVsLiving.list;
/*     */       } else {
/* 515 */         list = DamageVsEntity.list;
/*     */       } 
/*     */       
/* 518 */       for (DamageEntity de : list) {
/* 519 */         if (de.name.isEmpty() || targetName.indexOf(de.name) > 0) {
/* 520 */           damage = (float)(damage * de.factor);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 525 */     return damage;
/*     */   }
/*     */   
/*     */   public static List<Block> getBreakableBlockListFromType(int n) {
/* 529 */     if (n == 2) {
/* 530 */       return tankBreakableBlocks;
/*     */     }
/* 532 */     return (n == 1) ? carBreakableBlocks : dummyBreakableBlocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<Block> getNoBreakableBlockListFromType(int n) {
/* 537 */     if (n == 2) {
/* 538 */       return tankNoBreakableBlocks;
/*     */     }
/* 540 */     return (n == 1) ? carNoBreakableBlocks : dummyBreakableBlocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<Material> getBreakableMaterialListFromType(int n) {
/* 545 */     if (n == 2) {
/* 546 */       return tankBreakableMaterials;
/*     */     }
/* 548 */     return (n == 1) ? carBreakableMaterials : dummyBreakableMaterials;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockListFromString(List<Block> list, String str) {
/* 553 */     list.clear();
/* 554 */     String[] s = str.split("\\s*,\\s*");
/*     */     
/* 556 */     for (String blockName : s) {
/* 557 */       Block b = W_Block.getBlockFromName(blockName);
/* 558 */       if (b != null) {
/* 559 */         list.add(b);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMaterialListFromString(List<Material> list, String str) {
/* 565 */     list.clear();
/* 566 */     String[] s = str.split("\\s*,\\s*");
/*     */     
/* 568 */     for (String name : s) {
/* 569 */       Material m = MCH_Lib.getMaterialFromName(name);
/* 570 */       if (m != null) {
/* 571 */         list.add(m);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void correctionParameter() {
/* 577 */     String[] s = HitMarkColor.prmString.split("\\s*,\\s*");
/* 578 */     if (s.length == 4) {
/* 579 */       hitMarkColorAlpha = toInt255(s[0]) / 255.0F;
/* 580 */       hitMarkColorRGB = toInt255(s[1]) << 16 | toInt255(s[2]) << 8 | toInt255(s[3]);
/*     */     } 
/*     */     
/* 583 */     AllHeliSpeed.prmDouble = MCH_Lib.RNG(AllHeliSpeed.prmDouble, 0.0D, 1000.0D);
/* 584 */     AllPlaneSpeed.prmDouble = MCH_Lib.RNG(AllPlaneSpeed.prmDouble, 0.0D, 1000.0D);
/* 585 */     AllTankSpeed.prmDouble = MCH_Lib.RNG(AllTankSpeed.prmDouble, 0.0D, 1000.0D);
/* 586 */     setBlockListFromString(bulletBreakableBlocks, BulletBreakableBlock.prmString);
/* 587 */     setBlockListFromString(carBreakableBlocks, Collision_Car_BreakableBlock.prmString);
/* 588 */     setBlockListFromString(carNoBreakableBlocks, Collision_Car_NoBreakableBlock.prmString);
/* 589 */     setMaterialListFromString(carBreakableMaterials, Collision_Car_BreakableMaterial.prmString);
/* 590 */     setBlockListFromString(tankBreakableBlocks, Collision_Tank_BreakableBlock.prmString);
/* 591 */     setBlockListFromString(tankNoBreakableBlocks, Collision_Tank_NoBreakableBlock.prmString);
/* 592 */     setMaterialListFromString(tankBreakableMaterials, Collision_Tank_BreakableMaterial.prmString);
/* 593 */     if (EntityMarkerSize.prmDouble < 0.0D) {
/* 594 */       EntityMarkerSize.prmDouble = 0.0D;
/*     */     }
/*     */     
/* 597 */     if (BlockMarkerSize.prmDouble < 0.0D) {
/* 598 */       BlockMarkerSize.prmDouble = 0.0D;
/*     */     }
/*     */     
/* 601 */     if (HurtResistantTime.prmDouble < 0.0D) {
/* 602 */       HurtResistantTime.prmDouble = 0.0D;
/*     */     }
/*     */     
/* 605 */     if (HurtResistantTime.prmDouble > 10000.0D) {
/* 606 */       HurtResistantTime.prmDouble = 10000.0D;
/*     */     }
/*     */     
/* 609 */     if (MobRenderDistanceWeight.prmDouble < 0.1D) {
/* 610 */       MobRenderDistanceWeight.prmDouble = 0.1D;
/* 611 */     } else if (MobRenderDistanceWeight.prmDouble > 10.0D) {
/* 612 */       MobRenderDistanceWeight.prmDouble = 10.0D;
/*     */     } 
/*     */     
/* 615 */     for (MCH_ConfigPrm p : CommandPermission) {
/* 616 */       CommandPermission cpm = new CommandPermission(this, p.prmString);
/* 617 */       if (!cpm.name.isEmpty()) {
/* 618 */         CommandPermissionList.add(cpm);
/*     */       }
/*     */     } 
/*     */     
/* 622 */     if (IgnoreBulletHitList.isEmpty()) {
/* 623 */       IgnoreBulletHitList.add("flansmod.common.guns.EntityBullet");
/* 624 */       IgnoreBulletHitList.add("flansmod.common.guns.EntityGrenade");
/*     */     } 
/*     */     
/* 627 */     boolean isNoDamageVsSetting = DamageVs.isEmpty();
/*     */     
/* 629 */     for (MCH_ConfigPrm px : DamageVs) {
/* 630 */       for (DamageFactor df : DamageFactorList) {
/* 631 */         if (px.name.equals(df.itemName)) {
/* 632 */           df.list.add(newDamageEntity(px.prmString));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 637 */     for (DamageFactor dfx : DamageFactorList) {
/* 638 */       if (dfx.list.isEmpty()) {
/* 639 */         DamageVs.add(new MCH_ConfigPrm(dfx.itemName, "1.0"));
/*     */       } else {
/* 641 */         boolean foundCommon = false;
/*     */         
/* 643 */         for (DamageEntity n : dfx.list) {
/* 644 */           if (n.name.isEmpty()) {
/* 645 */             foundCommon = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 650 */         if (!foundCommon) {
/* 651 */           DamageVs.add(new MCH_ConfigPrm(dfx.itemName, "1.0"));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 656 */     if (DespawnCount.prmInt <= 0) {
/* 657 */       DespawnCount.prmInt = 1;
/*     */     }
/*     */     
/* 660 */     if (HitBoxDelayTick.prmInt < 0) {
/* 661 */       HitBoxDelayTick.prmInt = 0;
/*     */     }
/*     */     
/* 664 */     if (HitBoxDelayTick.prmInt > 50) {
/* 665 */       HitBoxDelayTick.prmInt = 50;
/*     */     }
/*     */     
/* 668 */     PitchLimitMax.prmInt = (PitchLimitMax.prmInt < 0) ? 0 : Math.min(PitchLimitMax.prmInt, 80);
/* 669 */     PitchLimitMin.prmInt = (PitchLimitMin.prmInt > 0) ? 0 : Math.max(PitchLimitMin.prmInt, -80);
/* 670 */     RollLimit.prmInt = (RollLimit.prmInt < 0) ? 0 : Math.min(RollLimit.prmInt, 80);
/* 671 */     if (isNoDamageVsSetting) {
/* 672 */       DamageVs.add(new MCH_ConfigPrm("DamageVsEntity", "3.0, flansmod"));
/* 673 */       DamageVs.add(new MCH_ConfigPrm("DamageMCHeliAircraftByExternal", "0.5, flansmod"));
/* 674 */       DamageVs.add(new MCH_ConfigPrm("DamageMCHeliVehicleByExternal", "0.5, flansmod"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public DamageEntity newDamageEntity(String s) {
/* 679 */     String[] splt = s.split("\\s*,\\s*");
/* 680 */     if (splt.length == 1) {
/* 681 */       return new DamageEntity(this, Double.parseDouble(splt[0]), "");
/*     */     }
/* 683 */     return (splt.length == 2) ? new DamageEntity(this, Double.parseDouble(splt[0]), splt[1]) : new DamageEntity(this, 1.0D, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public int toInt255(String s) {
/* 688 */     int a = Integer.parseInt(s);
/* 689 */     return (a > 255) ? 255 : Math.max(a, 0);
/*     */   }
/*     */   
/*     */   public void load() {
/* 693 */     MCH_InputFile file = new MCH_InputFile();
/* 694 */     if (file.open(configFilePath)) {
/* 695 */       for (String str = file.readLine(); str != null; str = file.readLine()) {
/* 696 */         if (str.trim().equalsIgnoreCase("McHeliOutputDebugLog")) {
/* 697 */           DebugLog = true;
/*     */         } else {
/* 699 */           readConfigData(str);
/*     */         } 
/*     */       } 
/*     */       
/* 703 */       file.close();
/* 704 */       MCH_Lib.Log("loaded " + file.file.getAbsolutePath(), new Object[0]);
/*     */     } else {
/* 706 */       MCH_Lib.Log((new File(configFilePath)).getAbsolutePath() + " not found.", new Object[0]);
/*     */     } 
/*     */     
/* 709 */     correctionParameter();
/*     */   }
/*     */   
/*     */   private void readConfigData(String str) {
/* 713 */     String[] s = str.split("=");
/* 714 */     if (s.length == 2) {
/* 715 */       s[0] = s[0].trim();
/* 716 */       s[1] = s[1].trim();
/* 717 */       if (s[0].equalsIgnoreCase("MOD_Version")) {
/* 718 */         configVer = s[1];
/*     */       } else {
/* 720 */         if (s[0].equalsIgnoreCase("CommandPermission")) {
/* 721 */           CommandPermission.add(new MCH_ConfigPrm("CommandPermission", s[1]));
/*     */         }
/*     */         
/* 724 */         for (DamageFactor item : DamageFactorList) {
/* 725 */           if (item.itemName.equalsIgnoreCase(s[0])) {
/* 726 */             DamageVs.add(new MCH_ConfigPrm(item.itemName, s[1]));
/*     */           }
/*     */         } 
/*     */         
/* 730 */         if (IgnoreBulletHitItem.compare(s[0])) {
/* 731 */           IgnoreBulletHitList.add(s[1]);
/*     */         }
/*     */         
/* 734 */         for (MCH_ConfigPrm p : KeyConfig) {
/* 735 */           if (p != null && p.compare(s[0]) && p.isValidVer(configVer)) {
/* 736 */             p.setPrm(s[1]);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 741 */         for (MCH_ConfigPrm px : General) {
/* 742 */           if (px != null && px.compare(s[0]) && px.isValidVer(configVer)) {
/* 743 */             px.setPrm(s[1]);
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write() {
/* 752 */     MCH_OutputFile file = new MCH_OutputFile();
/* 753 */     if (file.open(configFilePath)) {
/* 754 */       writeConfigData(file.pw);
/* 755 */       file.close();
/* 756 */       MCH_Lib.Log("update " + file.file.getAbsolutePath(), new Object[0]);
/*     */     } else {
/* 758 */       MCH_Lib.Log((new File(configFilePath)).getAbsolutePath() + " cannot open.", new Object[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeConfigData(PrintWriter pw) {
/* 763 */     pw.println("[General]");
/* 764 */     pw.println("MOD_Name = mcheli");
/* 765 */     pw.println("MOD_Version = " + MCH_MOD.VER);
/* 766 */     pw.println("MOD_MC_Version = 1.12.2");
/* 767 */     pw.println();
/* 768 */     if (DebugLog) {
/* 769 */       pw.println("McHeliOutputDebugLog");
/* 770 */       pw.println();
/*     */     } 
/*     */     
/* 773 */     for (MCH_ConfigPrm p : General) {
/* 774 */       if (p != null) {
/* 775 */         if (!p.desc.isEmpty()) {
/* 776 */           pw.println(p.desc);
/*     */         }
/*     */         
/* 779 */         pw.println(p.name + " = " + p);
/*     */       } else {
/* 781 */         pw.println("");
/*     */       } 
/*     */     } 
/*     */     
/* 785 */     pw.println();
/*     */     
/* 787 */     for (MCH_ConfigPrm px : DamageVs) {
/* 788 */       pw.println(px.name + " = " + px);
/*     */     }
/*     */     
/* 791 */     pw.println();
/*     */     
/* 793 */     for (String s : IgnoreBulletHitList) {
/* 794 */       pw.println(IgnoreBulletHitItem.name + " = " + s);
/*     */     }
/*     */     
/* 797 */     pw.println();
/* 798 */     pw.println(";CommandPermission = commandName(eg, modlist, status, fill...):playerName1, playerName2, playerName3...");
/* 799 */     if (CommandPermission.isEmpty()) {
/* 800 */       pw.println(";CommandPermission = modlist :example1, example2");
/* 801 */       pw.println(";CommandPermission = status :  example2");
/*     */     } 
/*     */     
/* 804 */     for (MCH_ConfigPrm px : CommandPermission) {
/* 805 */       pw.println(px.name + " = " + px);
/*     */     }
/*     */     
/* 808 */     pw.println();
/* 809 */     pw.println();
/* 810 */     pw.println("[Key config]");
/* 811 */     pw.println("https://minecraft.gamepedia.com/Key_codes");
/* 812 */     pw.println();
/*     */     
/* 814 */     for (MCH_ConfigPrm px : KeyConfig)
/* 815 */       pw.println(px.name + " = " + px); 
/*     */   }
/*     */   
/*     */   public static class CommandPermission
/*     */   {
/*     */     public final String name;
/*     */     public final String[] players;
/*     */     
/*     */     public CommandPermission(MCH_Config arg1, String param) {
/* 824 */       String[] s = param.split(":");
/* 825 */       if (s.length == 2) {
/* 826 */         this.name = s[0].toLowerCase().trim();
/* 827 */         this.players = s[1].trim().split("\\s*,\\s*");
/*     */       } else {
/* 829 */         this.name = "";
/* 830 */         this.players = new String[0];
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DamageEntity {
/*     */     public final double factor;
/*     */     public final String name;
/*     */     
/*     */     public DamageEntity(MCH_Config paramMCH_Config, double factor, String name) {
/* 840 */       this.factor = factor;
/* 841 */       this.name = name;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DamageFactor {
/*     */     public final String itemName;
/*     */     public final List<MCH_Config.DamageEntity> list;
/*     */     
/*     */     public DamageFactor(MCH_Config paramMCH_Config, String itemName) {
/* 850 */       this.itemName = itemName;
/* 851 */       this.list = new ArrayList<>();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */