/*     */ package com.norwood.mcheli;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import com.norwood.mcheli.helper.addon.AddonManager;
import com.norwood.mcheli.helper.client.renderer.item.BuiltInGLTDItemRenderer;
import com.norwood.mcheli.helper.client.renderer.item.BuiltInInvisibleItemRenderer;
import com.norwood.mcheli.helper.client.renderer.item.BuiltInRangeFinderItemRenderer;
import com.norwood.mcheli.helper.client.model.LegacyModelLoader;
import com.norwood.mcheli.weapon.MCH_EntityA10;
import com.norwood.mcheli.weapon.MCH_EntityBullet;
import com.norwood.mcheli.chain.MCH_EntityChain;
import com.norwood.mcheli.weapon.MCH_EntityDispensedItem;
import com.norwood.mcheli.flare.MCH_EntityFlare;
import com.norwood.mcheli.gltd.MCH_EntityGLTD;
import com.norwood.mcheli.helicopter.MCH_EntityHeli;
import com.norwood.mcheli.parachute.MCH_EntityParachute;
import com.norwood.mcheli.weapon.MCH_EntityRocket;
import com.norwood.mcheli.aircraft.MCH_EntitySeat;
import com.norwood.mcheli.tank.MCH_EntityTank;
import com.norwood.mcheli.weapon.MCH_EntityTorpedo;
import com.norwood.mcheli.vehicle.MCH_EntityVehicle;
import com.norwood.mcheli.command.MCH_GuiTitle;
import com.norwood.mcheli.multiplay.MCH_MultiplayClient;
import com.norwood.mcheli.particles.MCH_ParticlesUtil;
import com.norwood.mcheli.weapon.MCH_RenderA10;
import com.norwood.mcheli.weapon.MCH_RenderAAMissile;
import com.norwood.mcheli.weapon.MCH_RenderASMissile;
import com.norwood.mcheli.aircraft.MCH_RenderAircraft;
import com.norwood.mcheli.weapon.MCH_RenderBomb;
import com.norwood.mcheli.parachute.MCH_RenderParachute;
import com.norwood.mcheli.tank.MCH_RenderTank;
import com.norwood.mcheli.vehicle.MCH_RenderVehicle;
import com.norwood.mcheli.plane.MCP_EntityPlane;
import com.norwood.mcheli.plane.MCP_RenderPlane;
import net.minecraftforge.fml.relauncher.Side;
import com.norwood.mcheli.wrapper.W_Item;
import com.norwood.mcheli.wrapper.W_Reflection;
import com.norwood.mcheli.wrapper.W_TickRegistry;
import com.norwood.mcheli.helper.addon.AddonPack;
import java.util.List;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityHide;
/*     */ import com.norwood.mcheli.aircraft.MCH_SoundUpdater;
/*     */ import com.norwood.mcheli.block.MCH_DraftingTableRenderer;
/*     */ import com.norwood.mcheli.block.MCH_DraftingTableTileEntity;
/*     */ import com.norwood.mcheli.chain.MCH_RenderChain;
/*     */ import com.norwood.mcheli.container.MCH_EntityContainer;
/*     */ import com.norwood.mcheli.container.MCH_RenderContainer;
/*     */ import com.norwood.mcheli.debug.MCH_RenderTest;
/*     */ import com.norwood.mcheli.flare.MCH_RenderFlare;
/*     */ import com.norwood.mcheli.gltd.MCH_RenderGLTD;
/*     */ import com.norwood.mcheli.helicopter.MCH_HeliInfo;
/*     */ import com.norwood.mcheli.helicopter.MCH_RenderHeli;
/*     */ import com.norwood.mcheli.helper.client.MCH_ItemModelRenderers;
/*     */ import com.norwood.mcheli.helper.client._IModelCustom;
/*     */ import com.norwood.mcheli.helper.client.renderer.item.BuiltInDraftingTableItemRenderer;
/*     */ import com.norwood.mcheli.helper.client.renderer.item.BuiltInLightWeaponItemRenderer;
/*     */ import com.norwood.mcheli.helper.client.renderer.item.BuiltInWrenchItemRenderer;
/*     */ import com.norwood.mcheli.helper.client.renderer.item.IItemModelRenderer;
/*     */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*     */ import com.norwood.mcheli.mob.MCH_EntityGunner;
/*     */ import com.norwood.mcheli.mob.MCH_RenderGunner;
/*     */ import com.norwood.mcheli.plane.MCP_PlaneInfo;
/*     */ import com.norwood.mcheli.tank.MCH_TankInfo;
/*     */ import com.norwood.mcheli.throwable.MCH_EntityThrowable;
/*     */ import com.norwood.mcheli.throwable.MCH_RenderThrowable;
/*     */ import com.norwood.mcheli.throwable.MCH_ThrowableInfo;
/*     */ import com.norwood.mcheli.uav.MCH_EntityUavStation;
/*     */ import com.norwood.mcheli.uav.MCH_RenderUavStation;
/*     */ import com.norwood.mcheli.vehicle.MCH_VehicleInfo;
/*     */ import com.norwood.mcheli.weapon.MCH_BulletModel;
/*     */ import com.norwood.mcheli.weapon.MCH_DefaultBulletModels;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityAAMissile;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityASMissile;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityATMissile;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityBomb;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityCartridge;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityMarkerRocket;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityTvMissile;
/*     */ import com.norwood.mcheli.weapon.MCH_RenderBullet;
/*     */ import com.norwood.mcheli.weapon.MCH_RenderCartridge;
/*     */ import com.norwood.mcheli.weapon.MCH_RenderNone;
/*     */ import com.norwood.mcheli.weapon.MCH_RenderTvMissile;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponInfo;
/*     */ import com.norwood.mcheli.wrapper.W_LanguageRegistry;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.modelloader.W_ModelCustom;
/*     */ import java.io.File;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraftforge.client.model.ModelLoaderRegistry;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.client.registry.ClientRegistry;
/*     */ import net.minecraftforge.fml.client.registry.RenderingRegistry;
/*     */ 
/*     */ public class MCH_ClientProxy extends MCH_CommonProxy {
/*  62 */   public String lastLoadHUDPath = "";
/*     */   
/*     */   public static void registerModels_Bullet() {
/*  65 */     for (MCH_WeaponInfo wi : ContentRegistries.weapon().values()) {
/*     */       
/*  67 */       if (!wi.bulletModelName.isEmpty()) {
/*  68 */         _IModelCustom m = MCH_ModelManager.load("bullets", wi.bulletModelName);
/*  69 */         if (m != null) {
/*  70 */           wi.bulletModel = new MCH_BulletModel(wi.bulletModelName, m);
/*     */         }
/*     */       } 
/*     */       
/*  74 */       if (!wi.bombletModelName.isEmpty()) {
/*  75 */         _IModelCustom m = MCH_ModelManager.load("bullets", wi.bombletModelName);
/*  76 */         if (m != null) {
/*  77 */           wi.bombletModel = new MCH_BulletModel(wi.bombletModelName, m);
/*     */         }
/*     */       } 
/*     */       
/*  81 */       if (wi.cartridge != null && !wi.cartridge.name.isEmpty()) {
/*  82 */         wi.cartridge.model = MCH_ModelManager.load("bullets", wi.cartridge.name);
/*  83 */         if (wi.cartridge.model == null) {
/*  84 */           wi.cartridge = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDataDir() {
/*  92 */     return (Minecraft.getMinecraft()).mcDataDir.getPath();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerRenderer() {
/*  97 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntitySeat.class, MCH_RenderTest.factory(0.0F, 0.3125F, 0.0F, "seat"));
/*  98 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityHeli.class, MCH_RenderHeli.FACTORY);
/*  99 */     RenderingRegistry.registerEntityRenderingHandler(MCP_EntityPlane.class, MCP_RenderPlane.FACTORY);
/* 100 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityTank.class, MCH_RenderTank.FACTORY);
/* 101 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityGLTD.class, MCH_RenderGLTD.FACTORY);
/* 102 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityChain.class, MCH_RenderChain.FACTORY);
/* 103 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityParachute.class, MCH_RenderParachute.FACTORY);
/* 104 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityContainer.class, MCH_RenderContainer.FACTORY);
/* 105 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityVehicle.class, MCH_RenderVehicle.FACTORY);
/* 106 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityUavStation.class, MCH_RenderUavStation.FACTORY);
/* 107 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityCartridge.class, MCH_RenderCartridge.FACTORY);
/* 108 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityHide.class, MCH_RenderNull.FACTORY);
/* 109 */     RenderingRegistry.registerEntityRenderingHandler(MCH_ViewEntityDummy.class, MCH_RenderNull.FACTORY);
/* 110 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityRocket.class, MCH_RenderBullet.FACTORY);
/* 111 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityTvMissile.class, MCH_RenderTvMissile.FACTORY);
/* 112 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityBullet.class, MCH_RenderBullet.FACTORY);
/* 113 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityA10.class, MCH_RenderA10.FACTORY);
/* 114 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityAAMissile.class, MCH_RenderAAMissile.FACTORY);
/* 115 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityASMissile.class, MCH_RenderASMissile.FACTORY);
/* 116 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityATMissile.class, MCH_RenderTvMissile.FACTORY);
/* 117 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityTorpedo.class, MCH_RenderBullet.FACTORY);
/* 118 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityBomb.class, MCH_RenderBomb.FACTORY);
/* 119 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityMarkerRocket.class, MCH_RenderBullet.FACTORY);
/* 120 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityDispensedItem.class, MCH_RenderNone.FACTORY);
/* 121 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityFlare.class, MCH_RenderFlare.FACTORY);
/* 122 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityThrowable.class, MCH_RenderThrowable.FACTORY);
/* 123 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityGunner.class, MCH_RenderGunner.FACTORY);
/* 124 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemJavelin, (IItemModelRenderer)new BuiltInLightWeaponItemRenderer());
/* 125 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemStinger, (IItemModelRenderer)new BuiltInLightWeaponItemRenderer());
/* 126 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.invisibleItem, (IItemModelRenderer)new BuiltInInvisibleItemRenderer());
/* 127 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemGLTD, (IItemModelRenderer)new BuiltInGLTDItemRenderer());
/* 128 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemWrench, (IItemModelRenderer)new BuiltInWrenchItemRenderer());
/* 129 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemRangeFinder, (IItemModelRenderer)new BuiltInRangeFinderItemRenderer());
/* 130 */     ModelLoaderRegistry.registerLoader((ICustomModelLoader)LegacyModelLoader.INSTANCE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerBlockRenderer() {
/* 135 */     ClientRegistry.bindTileEntitySpecialRenderer(MCH_DraftingTableTileEntity.class, (TileEntitySpecialRenderer)new MCH_DraftingTableRenderer());
/* 136 */     MCH_ItemModelRenderers.registerRenderer(W_Item.getItemFromBlock((Block)MCH_MOD.blockDraftingTable), (IItemModelRenderer)new BuiltInDraftingTableItemRenderer());
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerModels() {
/* 141 */     MCH_ModelManager.setForceReloadMode(true);
/* 142 */     MCH_RenderAircraft.debugModel = MCH_ModelManager.load("box");
/* 143 */     MCH_ModelManager.load("a-10");
/* 144 */     MCH_RenderGLTD.model = MCH_ModelManager.load("gltd");
/* 145 */     MCH_ModelManager.load("chain");
/* 146 */     MCH_ModelManager.load("container");
/* 147 */     MCH_ModelManager.load("parachute1");
/* 148 */     MCH_ModelManager.load("parachute2");
/* 149 */     MCH_ModelManager.load("lweapons", "fim92");
/* 150 */     MCH_ModelManager.load("lweapons", "fgm148");
/*     */     
/* 152 */     for (String s : MCH_RenderUavStation.MODEL_NAME) {
/* 153 */       MCH_ModelManager.load(s);
/*     */     }
/*     */     
/* 156 */     MCH_ModelManager.load("wrench");
/* 157 */     MCH_ModelManager.load("rangefinder");
/* 158 */     ContentRegistries.heli().forEachValue(info -> registerModelsHeli(info, false));
/* 159 */     ContentRegistries.plane().forEachValue(info -> registerModelsPlane(info, false));
/* 160 */     ContentRegistries.tank().forEachValue(info -> registerModelsTank(info, false));
/* 161 */     ContentRegistries.vehicle().forEachValue(info -> registerModelsVehicle(info, false));
/* 162 */     registerModels_Bullet();
/* 163 */     MCH_DefaultBulletModels.Bullet = loadBulletModel("bullet");
/* 164 */     MCH_DefaultBulletModels.AAMissile = loadBulletModel("aamissile");
/* 165 */     MCH_DefaultBulletModels.ATMissile = loadBulletModel("asmissile");
/* 166 */     MCH_DefaultBulletModels.ASMissile = loadBulletModel("asmissile");
/* 167 */     MCH_DefaultBulletModels.Bomb = loadBulletModel("bomb");
/* 168 */     MCH_DefaultBulletModels.Rocket = loadBulletModel("rocket");
/* 169 */     MCH_DefaultBulletModels.Torpedo = loadBulletModel("torpedo");
/*     */     
/* 171 */     for (MCH_ThrowableInfo wi : ContentRegistries.throwable().values()) {
/* 172 */       wi.model = MCH_ModelManager.load("throwable", wi.name);
/*     */     }
/*     */     
/* 175 */     MCH_ModelManager.load("blocks", "drafting_table");
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerModelsHeli(MCH_HeliInfo info, boolean reload) {
/* 180 */     MCH_ModelManager.setForceReloadMode(reload);
/* 181 */     info.model = MCH_ModelManager.load("helicopters", info.name);
/*     */     
/* 183 */     for (MCH_HeliInfo.Rotor rotor : info.rotorList) {
/* 184 */       rotor.model = loadPartModel("helicopters", info.name, info.model, rotor.modelName);
/*     */     }
/*     */     
/* 187 */     registerCommonPart("helicopters", (MCH_AircraftInfo)info);
/* 188 */     MCH_ModelManager.setForceReloadMode(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerModelsPlane(MCP_PlaneInfo info, boolean reload) {
/* 193 */     MCH_ModelManager.setForceReloadMode(reload);
/* 194 */     info.model = MCH_ModelManager.load("planes", info.name);
/*     */     
/* 196 */     for (MCH_AircraftInfo.DrawnPart n : info.nozzles) {
/* 197 */       n.model = loadPartModel("planes", info.name, info.model, n.modelName);
/*     */     }
/*     */     
/* 200 */     for (MCP_PlaneInfo.Rotor r : info.rotorList) {
/* 201 */       r.model = loadPartModel("planes", info.name, info.model, r.modelName);
/*     */       
/* 203 */       for (MCP_PlaneInfo.Blade b : r.blades) {
/* 204 */         b.model = loadPartModel("planes", info.name, info.model, b.modelName);
/*     */       }
/*     */     } 
/*     */     
/* 208 */     for (MCP_PlaneInfo.Wing w : info.wingList) {
/* 209 */       w.model = loadPartModel("planes", info.name, info.model, w.modelName);
/* 210 */       if (w.pylonList != null) {
/* 211 */         for (MCP_PlaneInfo.Pylon p : w.pylonList) {
/* 212 */           p.model = loadPartModel("planes", info.name, info.model, p.modelName);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 217 */     registerCommonPart("planes", (MCH_AircraftInfo)info);
/* 218 */     MCH_ModelManager.setForceReloadMode(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerModelsVehicle(MCH_VehicleInfo info, boolean reload) {
/* 223 */     MCH_ModelManager.setForceReloadMode(reload);
/* 224 */     info.model = MCH_ModelManager.load("vehicles", info.name);
/*     */     
/* 226 */     for (MCH_VehicleInfo.VPart vp : info.partList) {
/* 227 */       vp.model = loadPartModel("vehicles", info.name, info.model, vp.modelName);
/* 228 */       if (vp.child != null) {
/* 229 */         registerVCPModels(info, vp);
/*     */       }
/*     */     } 
/*     */     
/* 233 */     registerCommonPart("vehicles", (MCH_AircraftInfo)info);
/* 234 */     MCH_ModelManager.setForceReloadMode(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerModelsTank(MCH_TankInfo info, boolean reload) {
/* 239 */     MCH_ModelManager.setForceReloadMode(reload);
/* 240 */     info.model = MCH_ModelManager.load("tanks", info.name);
/* 241 */     registerCommonPart("tanks", (MCH_AircraftInfo)info);
/* 242 */     MCH_ModelManager.setForceReloadMode(false);
/*     */   }
/*     */   
/*     */   private MCH_BulletModel loadBulletModel(String name) {
/* 246 */     _IModelCustom m = MCH_ModelManager.load("bullets", name);
/* 247 */     return (m != null) ? new MCH_BulletModel(name, m) : null;
/*     */   }
/*     */   
/*     */   private _IModelCustom loadPartModel(String path, String name, _IModelCustom body, String part) {
/* 251 */     return (body instanceof W_ModelCustom && ((W_ModelCustom)body).containsPart("$" + part)) ? null : MCH_ModelManager.load(path, name + "_" + part);
/*     */   }
/*     */   
/*     */   private void registerCommonPart(String path, MCH_AircraftInfo info) {
/* 255 */     for (MCH_AircraftInfo.Hatch h : info.hatchList) {
/* 256 */       h.model = loadPartModel(path, info.name, info.model, h.modelName);
/*     */     }
/*     */     
/* 259 */     for (MCH_AircraftInfo.Camera c : info.cameraList) {
/* 260 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 263 */     for (MCH_AircraftInfo.Throttle c : info.partThrottle) {
/* 264 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 267 */     for (MCH_AircraftInfo.RotPart c : info.partRotPart) {
/* 268 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 271 */     for (MCH_AircraftInfo.PartWeapon p : info.partWeapon) {
/* 272 */       p.model = loadPartModel(path, info.name, info.model, p.modelName);
/*     */       
/* 274 */       for (MCH_AircraftInfo.PartWeaponChild wc : p.child) {
/* 275 */         wc.model = loadPartModel(path, info.name, info.model, wc.modelName);
/*     */       }
/*     */     } 
/*     */     
/* 279 */     for (MCH_AircraftInfo.Canopy c : info.canopyList) {
/* 280 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 283 */     for (MCH_AircraftInfo.DrawnPart n : info.landingGear) {
/* 284 */       n.model = loadPartModel(path, info.name, info.model, n.modelName);
/*     */     }
/*     */     
/* 287 */     for (MCH_AircraftInfo.WeaponBay w : info.partWeaponBay) {
/* 288 */       w.model = loadPartModel(path, info.name, info.model, w.modelName);
/*     */     }
/*     */     
/* 291 */     for (MCH_AircraftInfo.CrawlerTrack c : info.partCrawlerTrack) {
/* 292 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 295 */     for (MCH_AircraftInfo.TrackRoller c : info.partTrackRoller) {
/* 296 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 299 */     for (MCH_AircraftInfo.PartWheel c : info.partWheel) {
/* 300 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 303 */     for (MCH_AircraftInfo.PartWheel c : info.partSteeringWheel) {
/* 304 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */   }
/*     */   
/*     */   private void registerVCPModels(MCH_VehicleInfo info, MCH_VehicleInfo.VPart vp) {
/* 309 */     for (MCH_VehicleInfo.VPart vcp : vp.child) {
/* 310 */       vcp.model = loadPartModel("vehicles", info.name, info.model, vcp.modelName);
/* 311 */       if (vcp.child != null) {
/* 312 */         registerVCPModels(info, vcp);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerClientTick() {
/* 319 */     Minecraft mc = Minecraft.getMinecraft();
/* 320 */     MCH_ClientCommonTickHandler.instance = new MCH_ClientCommonTickHandler(mc, MCH_MOD.config);
/* 321 */     W_TickRegistry.registerTickHandler(MCH_ClientCommonTickHandler.instance, Side.CLIENT);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRemote() {
/* 326 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String side() {
/* 331 */     return "Client";
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_SoundUpdater CreateSoundUpdater(MCH_EntityAircraft aircraft) {
/* 336 */     return (aircraft != null && aircraft.world.isRemote) ? new MCH_SoundUpdater(Minecraft.getMinecraft(), aircraft, (Minecraft.getMinecraft()).player) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSounds() {
/* 341 */     super.registerSounds();
/* 342 */     W_McClient.addSound("alert.ogg");
/* 343 */     W_McClient.addSound("locked.ogg");
/* 344 */     W_McClient.addSound("gltd.ogg");
/* 345 */     W_McClient.addSound("zoom.ogg");
/* 346 */     W_McClient.addSound("ng.ogg");
/* 347 */     W_McClient.addSound("a-10_snd.ogg");
/* 348 */     W_McClient.addSound("gau-8_snd.ogg");
/* 349 */     W_McClient.addSound("hit.ogg");
/* 350 */     W_McClient.addSound("helidmg.ogg");
/* 351 */     W_McClient.addSound("heli.ogg");
/* 352 */     W_McClient.addSound("plane.ogg");
/* 353 */     W_McClient.addSound("plane_cc.ogg");
/* 354 */     W_McClient.addSound("plane_cv.ogg");
/* 355 */     W_McClient.addSound("chain.ogg");
/* 356 */     W_McClient.addSound("chain_ct.ogg");
/* 357 */     W_McClient.addSound("eject_seat.ogg");
/* 358 */     W_McClient.addSound("fim92_snd.ogg");
/* 359 */     W_McClient.addSound("fim92_reload.ogg");
/* 360 */     W_McClient.addSound("lockon.ogg");
/*     */     
/* 362 */     for (MCH_WeaponInfo info : ContentRegistries.weapon().values()) {
/* 363 */       W_McClient.addSound(info.soundFileName + ".ogg");
/*     */     }
/*     */     
/* 366 */     for (MCH_AircraftInfo info : ContentRegistries.plane().values()) {
/* 367 */       if (!info.soundMove.isEmpty()) {
/* 368 */         W_McClient.addSound(info.soundMove + ".ogg");
/*     */       }
/*     */     } 
/*     */     
/* 372 */     for (MCH_AircraftInfo infox : ContentRegistries.heli().values()) {
/* 373 */       if (!infox.soundMove.isEmpty()) {
/* 374 */         W_McClient.addSound(infox.soundMove + ".ogg");
/*     */       }
/*     */     } 
/*     */     
/* 378 */     for (MCH_AircraftInfo infoxx : ContentRegistries.tank().values()) {
/* 379 */       if (!infoxx.soundMove.isEmpty()) {
/* 380 */         W_McClient.addSound(infoxx.soundMove + ".ogg");
/*     */       }
/*     */     } 
/*     */     
/* 384 */     for (MCH_AircraftInfo infoxxx : ContentRegistries.vehicle().values()) {
/* 385 */       if (!infoxxx.soundMove.isEmpty()) {
/* 386 */         W_McClient.addSound(infoxxx.soundMove + ".ogg");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadConfig(String fileName) {
/* 393 */     this.lastConfigFileName = fileName;
/* 394 */     this.config = new MCH_Config((Minecraft.getMinecraft()).mcDataDir.getPath(), "/" + fileName);
/* 395 */     this.config.load();
/* 396 */     this.config.write();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reconfig() {
/* 401 */     MCH_Lib.DbgLog(false, "MCH_ClientProxy.reconfig()", new Object[0]);
/* 402 */     loadConfig(this.lastConfigFileName);
/* 403 */     MCH_ClientCommonTickHandler.instance.updatekeybind(this.config);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadHUD() {
/* 408 */     ContentRegistries.hud().reloadAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity getClientPlayer() {
/* 413 */     return (Entity)(Minecraft.getMinecraft()).player;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/* 418 */     MinecraftForge.EVENT_BUS.register(new MCH_ParticlesUtil());
/* 419 */     MinecraftForge.EVENT_BUS.register(new MCH_ClientEventHook());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCreativeDigDelay(int n) {
/* 424 */     W_Reflection.setCreativeDigSpeed(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFirstPerson() {
/* 429 */     return ((Minecraft.getMinecraft()).gameSettings.thirdPersonView == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSinglePlayer() {
/* 434 */     return Minecraft.getMinecraft().isSingleplayer();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readClientModList() {
/*     */     try {
/* 440 */       Minecraft mc = Minecraft.getMinecraft();
/* 441 */       MCH_MultiplayClient.readModList(mc.getSession().getPlayerID(), mc.getSession().getUsername());
/* 442 */     } catch (Exception var2) {
/* 443 */       var2.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void printChatMessage(ITextComponent chat, int showTime, int pos) {
/* 449 */     ((MCH_GuiTitle)MCH_ClientCommonTickHandler.instance.gui_Title).setupTitle(chat, showTime, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void hitBullet() {
/* 454 */     MCH_ClientCommonTickHandler.instance.gui_Common.hitBullet();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clientLocked() {
/* 459 */     MCH_ClientCommonTickHandler.isLocked = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderEntityDistanceWeight(double renderDistWeight) {
/* 464 */     Entity.setRenderDistanceWeight(renderDistWeight);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<AddonPack> loadAddonPacks(File addonDir) {
/* 469 */     return AddonManager.loadAddonsAndAddResources(addonDir);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLoadContentDirName(String dir) {
/* 474 */     return ("hud".equals(dir) || super.canLoadContentDirName(dir));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateGeneratedLanguage() {
/* 479 */     W_LanguageRegistry.updateGeneratedLang();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void updateSoundsJson() {
/* 485 */     MCH_SoundsJson.updateGenerated();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_ClientProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */