/*     */ package com.norwood.mcheli;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.SidedProxy;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityHitBox;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */ import com.norwood.mcheli.aircraft.MCH_ItemAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_ItemFuel;
/*     */ import com.norwood.mcheli.block.MCH_DraftingTableBlock;
/*     */ import com.norwood.mcheli.block.MCH_DraftingTableTileEntity;
/*     */ import com.norwood.mcheli.chain.MCH_EntityChain;
/*     */ import com.norwood.mcheli.chain.MCH_ItemChain;
/*     */ import com.norwood.mcheli.container.MCH_ItemContainer;
/*     */ import com.norwood.mcheli.flare.MCH_EntityFlare;
/*     */ import com.norwood.mcheli.gltd.MCH_ItemGLTD;
/*     */ import com.norwood.mcheli.gui.MCH_GuiCommonHandler;
/*     */ import com.norwood.mcheli.helicopter.MCH_EntityHeli;
/*     */ import com.norwood.mcheli.helicopter.MCH_HeliInfo;
/*     */ import com.norwood.mcheli.helper.MCH_Blocks;
/*     */ import com.norwood.mcheli.helper.MCH_CriteriaTriggers;
/*     */ import com.norwood.mcheli.helper.MCH_Entities;
/*     */ import com.norwood.mcheli.helper.MCH_Items;
/*     */ import com.norwood.mcheli.helper.MCH_Logger;
/*     */ import com.norwood.mcheli.helper.MCH_Utils;
/*     */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*     */ import com.norwood.mcheli.lweapon.MCH_ItemLightWeaponBase;
/*     */ import com.norwood.mcheli.lweapon.MCH_ItemLightWeaponBullet;
/*     */ import com.norwood.mcheli.mob.MCH_EntityGunner;
/*     */ import com.norwood.mcheli.mob.MCH_ItemSpawnGunner;
/*     */ import com.norwood.mcheli.parachute.MCH_ItemParachute;
/*     */ import com.norwood.mcheli.plane.MCP_EntityPlane;
/*     */ import com.norwood.mcheli.plane.MCP_ItemPlane;
/*     */ import com.norwood.mcheli.plane.MCP_PlaneInfo;
/*     */ import com.norwood.mcheli.tank.MCH_ItemTank;
/*     */ import com.norwood.mcheli.tank.MCH_TankInfo;
/*     */ import com.norwood.mcheli.throwable.MCH_EntityThrowable;
/*     */ import com.norwood.mcheli.throwable.MCH_ItemThrowable;
/*     */ import com.norwood.mcheli.throwable.MCH_ThrowableInfo;
/*     */ import com.norwood.mcheli.tool.MCH_ItemWrench;
/*     */ import com.norwood.mcheli.tool.rangefinder.MCH_ItemRangeFinder;
/*     */ import com.norwood.mcheli.uav.MCH_ItemUavStation;
/*     */ import com.norwood.mcheli.vehicle.MCH_VehicleInfo;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityASMissile;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityATMissile;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityBomb;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityBullet;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityMarkerRocket;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityTorpedo;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponInfoManager;
/*     */ import com.norwood.mcheli.wrapper.W_Item;
/*     */ import com.norwood.mcheli.wrapper.W_LanguageRegistry;
/*     */ import com.norwood.mcheli.wrapper.W_NetworkRegistry;
/*     */ import java.io.File;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.command.CommandHandler;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.Mod.EventHandler;
/*     */ import net.minecraftforge.fml.common.Mod.Instance;
/*     */ import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
/*     */ import net.minecraftforge.fml.common.network.IGuiHandler;
/*     */ import net.minecraftforge.fml.common.registry.GameRegistry;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ @Mod(modid = "mcheli", name = "MC Helicopter MOD", dependencies = "required-after:forge@[14.23.5.2847,)")
/*     */ public class MCH_MOD {
/*     */   public static final String MOD_ID = "mcheli";
/*     */   @Deprecated
/*     */   public static final String DOMAIN = "mcheli";
/*     */   public static final String MOD_NAME = "MC Helicopter MOD";
/*  75 */   public static final MCH_PacketHandler packetHandler = new MCH_PacketHandler(); public static final String MCVER = "1.12.2"; public static final String MOD_CH = "MCHeli_CH"; public static final String ADDON_FOLDER_NAME = "mcheli_addons";
/*  76 */   public static String VER = "";
/*     */   
/*     */   @Instance("mcheli")
/*     */   public static MCH_MOD instance;
/*     */   
/*     */   @SidedProxy(clientSide = "com.norwood.mcheli.MCH_ClientProxy", serverSide = "com.norwood.mcheli.MCH_CommonProxy")
/*     */   public static MCH_CommonProxy proxy;
/*     */   
/*     */   public static MCH_Config config;
/*     */   public static String sourcePath;
/*     */   public static MCH_InvisibleItem invisibleItem;
/*     */   public static MCH_ItemGLTD itemGLTD;
/*     */   public static MCH_ItemLightWeaponBullet itemStingerBullet;
/*     */   public static MCH_ItemLightWeaponBase itemStinger;
/*     */   public static MCH_ItemLightWeaponBullet itemJavelinBullet;
/*     */   public static MCH_ItemLightWeaponBase itemJavelin;
/*     */   public static MCH_ItemUavStation[] itemUavStation;
/*     */   public static MCH_ItemParachute itemParachute;
/*     */   public static MCH_ItemContainer itemContainer;
/*     */   public static MCH_ItemChain itemChain;
/*     */   public static MCH_ItemFuel itemFuel;
/*     */   public static MCH_ItemWrench itemWrench;
/*     */   public static MCH_ItemRangeFinder itemRangeFinder;
/*     */   public static MCH_ItemSpawnGunner itemSpawnGunnerVsPlayer;
/*     */   public static MCH_ItemSpawnGunner itemSpawnGunnerVsMonster;
/*     */   public static MCH_CreativeTabs creativeTabs;
/*     */   public static MCH_CreativeTabs creativeTabsHeli;
/*     */   public static MCH_CreativeTabs creativeTabsPlane;
/*     */   public static MCH_CreativeTabs creativeTabsTank;
/*     */   public static MCH_CreativeTabs creativeTabsVehicle;
/*     */   public static MCH_DraftingTableBlock blockDraftingTable;
/*     */   public static MCH_DraftingTableBlock blockDraftingTableLit;
/*     */   private static File sourceFile;
/*     */   private static File addonDir;
/*     */   
/*     */   public static void registerItem(W_Item item, String name, MCH_CreativeTabs ct) {
/* 112 */     item.setUnlocalizedName("mcheli:" + name);
/* 113 */     if (ct != null) {
/* 114 */       item.setCreativeTab(ct);
/* 115 */       ct.addIconItem((Item)item);
/*     */     } 
/*     */     
/* 118 */     MCH_Items.register((Item)item, name);
/*     */   }
/*     */   
/*     */   public static void registerItemThrowable() {
/* 122 */     for (Map.Entry<String, MCH_ThrowableInfo> entry : (Iterable<Map.Entry<String, MCH_ThrowableInfo>>)ContentRegistries.throwable().entries()) {
/* 123 */       MCH_ThrowableInfo info = entry.getValue();
/* 124 */       info.item = (W_Item)new MCH_ItemThrowable(info.itemID);
/* 125 */       info.item.setMaxStackSize(info.stackSize);
/* 126 */       registerItem(info.item, entry.getKey(), creativeTabs);
/* 127 */       MCH_ItemThrowable.registerDispenseBehavior((Item)info.item);
/* 128 */       info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
/* 129 */       W_LanguageRegistry.addName(info.item, info.displayName);
/*     */       
/* 131 */       for (String lang : info.displayNameLang.keySet()) {
/* 132 */         W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void registerItemAircraft() {
/* 138 */     for (Map.Entry<String, MCH_HeliInfo> entry : (Iterable<Map.Entry<String, MCH_HeliInfo>>)ContentRegistries.heli().entries()) {
/* 139 */       MCH_HeliInfo info = entry.getValue();
/* 140 */       info.item = new MCH_ItemHeli(info.itemID);
/* 141 */       info.item.setMaxDamage(info.maxHp);
/* 142 */       if (info.canRide || (info.ammoSupplyRange <= 0.0F && info.fuelSupplyRange <= 0.0F)) {
/* 143 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabsHeli);
/*     */       } else {
/* 145 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabs);
/*     */       } 
/*     */       
/* 148 */       MCH_ItemAircraft.registerDispenseBehavior((Item)info.item);
/* 149 */       info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
/* 150 */       W_LanguageRegistry.addName(info.item, info.displayName);
/*     */       
/* 152 */       for (String lang : info.displayNameLang.keySet()) {
/* 153 */         W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang));
/*     */       }
/*     */     } 
/*     */     
/* 157 */     for (Map.Entry<String, MCP_PlaneInfo> entry : (Iterable<Map.Entry<String, MCP_PlaneInfo>>)ContentRegistries.plane().entries()) {
/* 158 */       MCP_PlaneInfo info = entry.getValue();
/* 159 */       info.item = new MCP_ItemPlane(info.itemID);
/* 160 */       info.item.setMaxDamage(info.maxHp);
/* 161 */       if (info.canRide || (info.ammoSupplyRange <= 0.0F && info.fuelSupplyRange <= 0.0F)) {
/* 162 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabsPlane);
/*     */       } else {
/* 164 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabs);
/*     */       } 
/*     */       
/* 167 */       MCH_ItemAircraft.registerDispenseBehavior((Item)info.item);
/* 168 */       info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
/* 169 */       W_LanguageRegistry.addName(info.item, info.displayName);
/*     */       
/* 171 */       for (String lang : info.displayNameLang.keySet()) {
/* 172 */         W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang));
/*     */       }
/*     */     } 
/*     */     
/* 176 */     for (Map.Entry<String, MCH_TankInfo> entry : (Iterable<Map.Entry<String, MCH_TankInfo>>)ContentRegistries.tank().entries()) {
/* 177 */       MCH_TankInfo info = entry.getValue();
/* 178 */       info.item = new MCH_ItemTank(info.itemID);
/* 179 */       info.item.setMaxDamage(info.maxHp);
/* 180 */       if (info.canRide || (info.ammoSupplyRange <= 0.0F && info.fuelSupplyRange <= 0.0F)) {
/* 181 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabsTank);
/*     */       } else {
/* 183 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabs);
/*     */       } 
/*     */       
/* 186 */       MCH_ItemAircraft.registerDispenseBehavior((Item)info.item);
/* 187 */       info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
/* 188 */       W_LanguageRegistry.addName(info.item, info.displayName);
/*     */       
/* 190 */       for (String lang : info.displayNameLang.keySet()) {
/* 191 */         W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang));
/*     */       }
/*     */     } 
/*     */     
/* 195 */     for (Map.Entry<String, MCH_VehicleInfo> entry : (Iterable<Map.Entry<String, MCH_VehicleInfo>>)ContentRegistries.vehicle().entries()) {
/* 196 */       MCH_VehicleInfo info = entry.getValue();
/* 197 */       info.item = new MCH_ItemVehicle(info.itemID);
/* 198 */       info.item.setMaxDamage(info.maxHp);
/* 199 */       if (info.canRide || (info.ammoSupplyRange <= 0.0F && info.fuelSupplyRange <= 0.0F)) {
/* 200 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabsVehicle);
/*     */       } else {
/* 202 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabs);
/*     */       } 
/*     */       
/* 205 */       MCH_ItemAircraft.registerDispenseBehavior((Item)info.item);
/* 206 */       info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
/* 207 */       W_LanguageRegistry.addName(info.item, info.displayName);
/*     */       
/* 209 */       for (String lang : info.displayNameLang.keySet()) {
/* 210 */         W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static Logger getLogger() {
/* 217 */     return MCH_Logger.get();
/*     */   }
/*     */   
/*     */   public static File getSource() {
/* 221 */     return sourceFile;
/*     */   }
/*     */   
/*     */   public static File getAddonDir() {
/* 225 */     return addonDir;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void PreInit(FMLPreInitializationEvent evt) {
/* 230 */     MCH_Logger.setLogger(evt.getModLog());
/* 231 */     VER = Loader.instance().activeModContainer().getVersion();
/* 232 */     MCH_Lib.init();
/* 233 */     MCH_Lib.Log("MC Ver:1.12.2 MOD Ver:" + VER, new Object[0]);
/* 234 */     MCH_Lib.Log("Start load...", new Object[0]);
/* 235 */     sourcePath = evt.getSourceFile().getPath();
/* 236 */     sourceFile = evt.getSourceFile();
/* 237 */     addonDir = new File(evt.getModConfigurationDirectory().getParentFile(), "/mcheli_addons/");
/* 238 */     MCH_Lib.Log("SourcePath: " + sourcePath, new Object[0]);
/* 239 */     MCH_Lib.Log("CurrentDirectory:" + (new File(".")).getAbsolutePath(), new Object[0]);
/* 240 */     proxy.init();
/* 241 */     creativeTabs = new MCH_CreativeTabs("MC Heli Item");
/* 242 */     creativeTabsHeli = new MCH_CreativeTabs("MC Heli Helicopters");
/* 243 */     creativeTabsPlane = new MCH_CreativeTabs("MC Heli Planes");
/* 244 */     creativeTabsTank = new MCH_CreativeTabs("MC Heli Tanks");
/* 245 */     creativeTabsVehicle = new MCH_CreativeTabs("MC Heli Vehicles");
/* 246 */     proxy.loadConfig("config/mcheli.cfg");
/* 247 */     config = proxy.config;
/* 248 */     ContentRegistries.loadContents(addonDir);
/* 249 */     MCH_Lib.Log("Register item", new Object[0]);
/* 250 */     registerItemSpawnGunner();
/* 251 */     registerItemRangeFinder();
/* 252 */     registerItemWrench();
/* 253 */     registerItemFuel();
/* 254 */     registerItemGLTD();
/* 255 */     registerItemChain();
/* 256 */     registerItemParachute();
/* 257 */     registerItemContainer();
/* 258 */     registerItemUavStation();
/* 259 */     registerItemInvisible();
/* 260 */     registerItemThrowable();
/* 261 */     registerItemLightWeaponBullet();
/* 262 */     registerItemLightWeapon();
/* 263 */     registerItemAircraft();
/* 264 */     blockDraftingTable = new MCH_DraftingTableBlock(MCH_Config.BlockID_DraftingTableOFF.prmInt, false);
/* 265 */     blockDraftingTable.setUnlocalizedName("drafting_table");
/* 266 */     blockDraftingTable.setCreativeTab(creativeTabs);
/* 267 */     blockDraftingTableLit = new MCH_DraftingTableBlock(MCH_Config.BlockID_DraftingTableON.prmInt, true);
/* 268 */     blockDraftingTableLit.setUnlocalizedName("lit_drafting_table");
/* 269 */     MCH_Blocks.register((Block)blockDraftingTable, "drafting_table");
/* 270 */     MCH_Blocks.register((Block)blockDraftingTableLit, "lit_drafting_table");
/* 271 */     MCH_Items.registerBlock((Block)blockDraftingTable);
/* 272 */     W_LanguageRegistry.addName(blockDraftingTable, "Drafting Table");
/* 273 */     W_LanguageRegistry.addNameForObject(blockDraftingTable, "ja_jp", "製図台");
/* 274 */     MCH_CriteriaTriggers.registerTriggers();
/* 275 */     MCH_Lib.Log("Register system", new Object[0]);
/* 276 */     W_NetworkRegistry.registerChannel(packetHandler, "MCHeli_CH");
/* 277 */     MinecraftForge.EVENT_BUS.register(new MCH_EventHook());
/* 278 */     proxy.registerClientTick();
/* 279 */     W_NetworkRegistry.registerGuiHandler(this, (IGuiHandler)new MCH_GuiCommonHandler());
/* 280 */     MCH_Lib.Log("Register entity", new Object[0]);
/* 281 */     registerEntity();
/* 282 */     MCH_Lib.Log("Register renderer", new Object[0]);
/* 283 */     proxy.registerRenderer();
/* 284 */     MCH_Lib.Log("Register models", new Object[0]);
/* 285 */     proxy.registerModels();
/* 286 */     MCH_Lib.Log("Register Sounds", new Object[0]);
/* 287 */     proxy.registerSounds();
/* 288 */     proxy.updateGeneratedLanguage();
/* 289 */     MCH_Lib.Log("End load", new Object[0]);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void init(FMLInitializationEvent evt) {
/* 294 */     GameRegistry.registerTileEntity(MCH_DraftingTableTileEntity.class, MCH_Utils.suffix("drafting_table"));
/* 295 */     proxy.registerBlockRenderer();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void postInit(FMLPostInitializationEvent evt) {
/* 300 */     creativeTabs.setFixedIconItem(MCH_Config.CreativeTabIcon.prmString);
/* 301 */     creativeTabsHeli.setFixedIconItem(MCH_Config.CreativeTabIconHeli.prmString);
/* 302 */     creativeTabsPlane.setFixedIconItem(MCH_Config.CreativeTabIconPlane.prmString);
/* 303 */     creativeTabsTank.setFixedIconItem(MCH_Config.CreativeTabIconTank.prmString);
/* 304 */     creativeTabsVehicle.setFixedIconItem(MCH_Config.CreativeTabIconVehicle.prmString);
/* 305 */     MCH_WeaponInfoManager.setRoundItems();
/* 306 */     proxy.readClientModList();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onStartServer(FMLServerStartingEvent event) {
/* 311 */     proxy.registerServerTick();
/*     */   }
/*     */   
/*     */   public void registerEntity() {
/* 315 */     MCH_Entities.register(MCH_EntitySeat.class, "MCH.E.Seat", 100, this, 400, 10, true);
/* 316 */     MCH_Entities.register(MCH_EntityHeli.class, "MCH.E.Heli", 101, this, 400, 10, true);
/* 317 */     MCH_Entities.register(MCH_EntityGLTD.class, "MCH.E.GLTD", 102, this, 200, 10, true);
/* 318 */     MCH_Entities.register(MCP_EntityPlane.class, "MCH.E.Plane", 103, this, 400, 10, true);
/* 319 */     MCH_Entities.register(MCH_EntityChain.class, "MCH.E.Chain", 104, this, 200, 10, true);
/* 320 */     MCH_Entities.register(MCH_EntityHitBox.class, "MCH.E.PSeat", 105, this, 200, 10, true);
/* 321 */     MCH_Entities.register(MCH_EntityParachute.class, "MCH.E.Parachute", 106, this, 200, 10, true);
/* 322 */     MCH_Entities.register(MCH_EntityContainer.class, "MCH.E.Container", 107, this, 200, 10, true);
/* 323 */     MCH_Entities.register(MCH_EntityVehicle.class, "MCH.E.Vehicle", 108, this, 400, 10, true);
/* 324 */     MCH_Entities.register(MCH_EntityUavStation.class, "MCH.E.UavStation", 109, this, 400, 10, true);
/* 325 */     MCH_Entities.register(MCH_EntityHitBox.class, "MCH.E.HitBox", 110, this, 200, 10, true);
/* 326 */     MCH_Entities.register(MCH_EntityHide.class, "MCH.E.Hide", 111, this, 200, 10, true);
/* 327 */     MCH_Entities.register(MCH_EntityTank.class, "MCH.E.Tank", 112, this, 400, 10, true);
/* 328 */     MCH_Entities.register(MCH_EntityRocket.class, "MCH.E.Rocket", 200, this, 530, 5, true);
/* 329 */     MCH_Entities.register(MCH_EntityTvMissile.class, "MCH.E.TvMissle", 201, this, 530, 5, true);
/* 330 */     MCH_Entities.register(MCH_EntityBullet.class, "MCH.E.Bullet", 202, this, 530, 5, true);
/* 331 */     MCH_Entities.register(MCH_EntityA10.class, "MCH.E.A10", 203, this, 530, 5, true);
/* 332 */     MCH_Entities.register(MCH_EntityAAMissile.class, "MCH.E.AAM", 204, this, 530, 5, true);
/* 333 */     MCH_Entities.register(MCH_EntityASMissile.class, "MCH.E.ASM", 205, this, 530, 5, true);
/* 334 */     MCH_Entities.register(MCH_EntityTorpedo.class, "MCH.E.Torpedo", 206, this, 530, 5, true);
/* 335 */     MCH_Entities.register(MCH_EntityATMissile.class, "MCH.E.ATMissle", 207, this, 530, 5, true);
/* 336 */     MCH_Entities.register(MCH_EntityBomb.class, "MCH.E.Bomb", 208, this, 530, 5, true);
/* 337 */     MCH_Entities.register(MCH_EntityMarkerRocket.class, "MCH.E.MkRocket", 209, this, 530, 5, true);
/* 338 */     MCH_Entities.register(MCH_EntityDispensedItem.class, "MCH.E.DispItem", 210, this, 530, 5, true);
/* 339 */     MCH_Entities.register(MCH_EntityFlare.class, "MCH.E.Flare", 300, this, 330, 10, true);
/* 340 */     MCH_Entities.register(MCH_EntityThrowable.class, "MCH.E.Throwable", 400, this, 330, 10, true);
/* 341 */     MCH_Entities.register(MCH_EntityGunner.class, "MCH.E.Gunner", 500, this, 530, 5, true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void registerCommand(FMLServerStartedEvent e) {
/* 346 */     CommandHandler handler = (CommandHandler)FMLCommonHandler.instance().getSidedDelegate().getServer().getCommandManager();
/* 347 */     handler.registerCommand((ICommand)new MCH_Command());
/*     */   }
/*     */   
/*     */   private void registerItemSpawnGunner() {
/* 351 */     String name = "spawn_gunner_vs_monster";
/* 352 */     MCH_ItemSpawnGunner item = new MCH_ItemSpawnGunner();
/* 353 */     item.targetType = 0;
/* 354 */     item.primaryColor = 12632224;
/* 355 */     item.secondaryColor = 12582912;
/* 356 */     itemSpawnGunnerVsMonster = item;
/* 357 */     registerItem((W_Item)item, name, creativeTabs);
/* 358 */     W_LanguageRegistry.addName(item, "Gunner (vs Monster)");
/* 359 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "対モンスター 射撃手");
/* 360 */     name = "spawn_gunner_vs_player";
/* 361 */     item = new MCH_ItemSpawnGunner();
/* 362 */     item.targetType = 1;
/* 363 */     item.primaryColor = 12632224;
/* 364 */     item.secondaryColor = 49152;
/* 365 */     itemSpawnGunnerVsPlayer = item;
/* 366 */     registerItem((W_Item)item, name, creativeTabs);
/* 367 */     W_LanguageRegistry.addName(item, "Gunner (vs Player of other team)");
/* 368 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "対他チームプレイヤー 射撃手");
/*     */   }
/*     */   
/*     */   private void registerItemRangeFinder() {
/* 372 */     String name = "rangefinder";
/* 373 */     MCH_ItemRangeFinder item = new MCH_ItemRangeFinder(MCH_Config.ItemID_RangeFinder.prmInt);
/* 374 */     itemRangeFinder = item;
/* 375 */     registerItem((W_Item)item, name, creativeTabs);
/* 376 */     W_LanguageRegistry.addName(item, "Laser Rangefinder");
/* 377 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "レーザー レンジ ファインダー");
/*     */   }
/*     */   
/*     */   private void registerItemWrench() {
/* 381 */     String name = "wrench";
/* 382 */     MCH_ItemWrench item = new MCH_ItemWrench(MCH_Config.ItemID_Wrench.prmInt, Item.ToolMaterial.IRON);
/* 383 */     itemWrench = item;
/* 384 */     registerItem((W_Item)item, name, creativeTabs);
/* 385 */     W_LanguageRegistry.addName(item, "Wrench");
/* 386 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "レンチ");
/*     */   }
/*     */   
/*     */   public void registerItemInvisible() {
/* 390 */     String name = "internal";
/* 391 */     MCH_InvisibleItem item = new MCH_InvisibleItem(MCH_Config.ItemID_InvisibleItem.prmInt);
/* 392 */     invisibleItem = item;
/* 393 */     registerItem(item, name, null);
/*     */   }
/*     */   
/*     */   public void registerItemUavStation() {
/* 397 */     String[] dispName = { "UAV Station", "Portable UAV Controller" };
/* 398 */     String[] localName = { "UAVステーション", "携帯UAV制御端末" };
/* 399 */     itemUavStation = new MCH_ItemUavStation[2];
/* 400 */     String name = "uav_station";
/*     */     
/* 402 */     for (int i = 0; i < itemUavStation.length; i++) {
/* 403 */       String nn = (i > 0) ? ("" + (i + 1)) : "";
/* 404 */       MCH_ItemUavStation item = new MCH_ItemUavStation((MCH_Config.ItemID_UavStation[i]).prmInt, 1 + i);
/* 405 */       itemUavStation[i] = item;
/* 406 */       registerItem((W_Item)item, name + nn, creativeTabs);
/* 407 */       W_LanguageRegistry.addName(item, dispName[i]);
/* 408 */       W_LanguageRegistry.addNameForObject(item, "ja_jp", localName[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerItemParachute() {
/* 413 */     String name = "parachute";
/* 414 */     MCH_ItemParachute item = new MCH_ItemParachute(MCH_Config.ItemID_Parachute.prmInt);
/* 415 */     itemParachute = item;
/* 416 */     registerItem((W_Item)item, name, creativeTabs);
/* 417 */     W_LanguageRegistry.addName(item, "Parachute");
/* 418 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "パラシュート");
/*     */   }
/*     */   
/*     */   public void registerItemContainer() {
/* 422 */     String name = "container";
/* 423 */     MCH_ItemContainer item = new MCH_ItemContainer(MCH_Config.ItemID_Container.prmInt);
/* 424 */     itemContainer = item;
/* 425 */     registerItem((W_Item)item, name, creativeTabs);
/* 426 */     W_LanguageRegistry.addName(item, "Container");
/* 427 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "コンテナ");
/*     */   }
/*     */   
/*     */   public void registerItemLightWeapon() {
/* 431 */     String name = "fim92";
/* 432 */     MCH_ItemLightWeaponBase item = new MCH_ItemLightWeaponBase(MCH_Config.ItemID_Stinger.prmInt, itemStingerBullet);
/* 433 */     itemStinger = item;
/* 434 */     registerItem((W_Item)item, name, creativeTabs);
/* 435 */     W_LanguageRegistry.addName(item, "FIM-92 Stinger");
/* 436 */     name = "fgm148";
/* 437 */     item = new MCH_ItemLightWeaponBase(MCH_Config.ItemID_Stinger.prmInt, itemJavelinBullet);
/* 438 */     itemJavelin = item;
/* 439 */     registerItem((W_Item)item, name, creativeTabs);
/* 440 */     W_LanguageRegistry.addName(item, "FGM-148 Javelin");
/*     */   }
/*     */   
/*     */   public void registerItemLightWeaponBullet() {
/* 444 */     String name = "fim92_bullet";
/* 445 */     MCH_ItemLightWeaponBullet item = new MCH_ItemLightWeaponBullet(MCH_Config.ItemID_StingerMissile.prmInt);
/* 446 */     itemStingerBullet = item;
/* 447 */     registerItem((W_Item)item, name, creativeTabs);
/* 448 */     W_LanguageRegistry.addName(item, "FIM-92 Stinger missile");
/* 449 */     name = "fgm148_bullet";
/* 450 */     item = new MCH_ItemLightWeaponBullet(MCH_Config.ItemID_StingerMissile.prmInt);
/* 451 */     itemJavelinBullet = item;
/* 452 */     registerItem((W_Item)item, name, creativeTabs);
/* 453 */     W_LanguageRegistry.addName(item, "FGM-148 Javelin missile");
/*     */   }
/*     */   
/*     */   public void registerItemChain() {
/* 457 */     String name = "chain";
/* 458 */     MCH_ItemChain item = new MCH_ItemChain(MCH_Config.ItemID_Chain.prmInt);
/* 459 */     itemChain = item;
/* 460 */     registerItem((W_Item)item, name, creativeTabs);
/* 461 */     W_LanguageRegistry.addName(item, "Chain");
/* 462 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "鎖");
/*     */   }
/*     */   
/*     */   public void registerItemFuel() {
/* 466 */     String name = "fuel";
/* 467 */     MCH_ItemFuel item = new MCH_ItemFuel(MCH_Config.ItemID_Fuel.prmInt);
/* 468 */     itemFuel = item;
/* 469 */     registerItem((W_Item)item, name, creativeTabs);
/* 470 */     W_LanguageRegistry.addName(item, "Fuel");
/* 471 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "燃料");
/*     */   }
/*     */   
/*     */   public void registerItemGLTD() {
/* 475 */     String name = "gltd";
/* 476 */     MCH_ItemGLTD item = new MCH_ItemGLTD(MCH_Config.ItemID_GLTD.prmInt);
/* 477 */     itemGLTD = item;
/* 478 */     registerItem((W_Item)item, name, creativeTabs);
/* 479 */     W_LanguageRegistry.addName(item, "GLTD:Target Designator");
/* 480 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "GLTD:レーザー目標指示装置");
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_MOD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */