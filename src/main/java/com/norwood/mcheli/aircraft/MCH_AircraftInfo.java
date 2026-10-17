/*      */ package com.norwood.mcheli.aircraft;
import com.norwood.mcheli.MCH_BaseInfo;
/*      */ import com.norwood.mcheli.MCH_MOD;
/*      */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*      */ import com.norwood.mcheli.helper.client._IModelCustom;
/*      */ import com.norwood.mcheli.helper.info.IItemContent;
/*      */ import com.norwood.mcheli.hud.MCH_Hud;
/*      */ import com.norwood.mcheli.hud.MCH_HudManager;
/*      */ import com.norwood.mcheli.weapon.MCH_WeaponInfoManager;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.crafting.IRecipe;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ 
/*      */ public abstract class MCH_AircraftInfo extends MCH_BaseInfo implements IItemContent {
/*      */   public final String name;
/*      */   public String displayName;
/*      */   public final HashMap<String, String> displayNameLang;
/*      */   public int itemID;
/*      */   public List<String> recipeString;
/*      */   public final List<IRecipe> recipe;
/*      */   public boolean isShapedRecipe;
/*      */   public String category;
/*      */   public boolean creativeOnly;
/*      */   public boolean invulnerable;
/*      */   public boolean isEnableGunnerMode;
/*      */   public int cameraZoom;
/*      */   public boolean isEnableConcurrentGunnerMode;
/*      */   public boolean isEnableNightVision;
/*      */   public boolean isEnableEntityRadar;
/*      */   public boolean isEnableEjectionSeat;
/*      */   public boolean isEnableParachuting;
/*      */   public final Flare flare;
/*      */   public float bodyHeight;
/*      */   public float bodyWidth;
/*      */   public boolean isFloat;
/*      */   public float floatOffset;
/*      */   public float gravity;
/*      */   public float gravityInWater;
/*      */   public int maxHp;
/*      */   public float armorMinDamage;
/*      */   public float armorMaxDamage;
/*      */   public float armorDamageFactor;
/*      */   public boolean enableBack;
/*      */   public int inventorySize;
/*      */   public boolean isUAV;
/*      */   public boolean isSmallUAV;
/*      */   public boolean isTargetDrone;
/*      */   public float autoPilotRot;
/*      */   public float onGroundPitch;
/*      */   public boolean canMoveOnGround;
/*      */   public boolean canRotOnGround;
/*      */   public final List<WeaponSet> weaponSetList;
/*      */   public final List<MCH_SeatInfo> seatList;
/*      */   public final List<Integer[]> exclusionSeatList;
/*      */   public final List<MCH_Hud> hudList;
/*      */   public MCH_Hud hudTvMissile;
/*      */   public float damageFactor;
/*      */   public float submergedDamageHeight;
/*      */   public boolean regeneration;
/*      */   public final List<MCH_BoundingBox> extraBoundingBox;
/*      */   public final List<Wheel> wheels;
/*      */   public int maxFuel;
/*      */   public float fuelConsumption;
/*      */   public float fuelSupplyRange;
/*      */   public float ammoSupplyRange;
/*      */   public float repairOtherVehiclesRange;
/*      */   public int repairOtherVehiclesValue;
/*      */   public float stealth;
/*      */   public boolean canRide;
/*      */   public float entityWidth;
/*      */   public float entityHeight;
/*      */   public float entityPitch;
/*      */   public float entityRoll;
/*      */   public float stepHeight;
/*      */   public final List<MCH_SeatRackInfo> entityRackList;
/*      */   public int mobSeatNum;
/*      */   public int entityRackNum;
/*      */   public final MCH_MobDropOption mobDropOption;
/*      */   public final List<RepellingHook> repellingHooks;
/*      */   public final List<RideRack> rideRacks;
/*      */   public final List<ParticleSplash> particleSplashs;
/*      */   public final List<SearchLight> searchLights;
/*      */   public float rotorSpeed;
/*      */   public boolean enableSeaSurfaceParticle;
/*      */   public float pivotTurnThrottle;
/*      */   public float trackRollerRot;
/*      */   public float partWheelRot;
/*      */   public float onGroundPitchFactor;
/*      */   public float onGroundRollFactor;
/*      */   public Vec3d turretPosition;
/*      */   public boolean defaultFreelook;
/*      */   public Vec3d unmountPosition;
/*      */   public float thirdPersonDist;
/*      */   public float markerWidth;
/*      */   public float markerHeight;
/*      */   public float bbZmin;
/*      */   public float bbZmax;
/*      */   public float bbZ;
/*      */   public boolean alwaysCameraView;
/*      */   public final List<CameraPosition> cameraPosition;
/*      */   public float cameraRotationSpeed;
/*      */   public float speed;
/*      */   public float motionFactor;
/*      */   public float mobilityYaw;
/*      */   public float mobilityPitch;
/*      */   public float mobilityRoll;
/*      */   public float mobilityYawOnGround;
/*      */   public float minRotationPitch;
/*      */   public float maxRotationPitch;
/*      */   public float minRotationRoll;
/*      */   public float maxRotationRoll;
/*      */   public boolean limitRotation;
/*      */   public float throttleUpDown;
/*      */   public float throttleUpDownOnEntity;
/*      */   public int textureCount;
/*      */   public float particlesScale;
/*      */   public boolean hideEntity;
/*      */   public boolean smoothShading;
/*      */   public String soundMove;
/*      */   public float soundRange;
/*      */   public float soundVolume;
/*      */   public float soundPitch;
/*      */   public _IModelCustom model;
/*      */   public final List<Hatch> hatchList;
/*      */   public final List<Camera> cameraList;
/*      */   public final List<PartWeapon> partWeapon;
/*      */   public final List<WeaponBay> partWeaponBay;
/*      */   public final List<Canopy> canopyList;
/*      */   public final List<LandingGear> landingGear;
/*      */   public final List<Throttle> partThrottle;
/*      */   public final List<RotPart> partRotPart;
/*      */   public final List<CrawlerTrack> partCrawlerTrack;
/*      */   public final List<TrackRoller> partTrackRoller;
/*      */   public final List<PartWheel> partWheel;
/*      */   public final List<PartWheel> partSteeringWheel;
/*      */   public final List<Hatch> lightHatchList;
/*      */   private final List<String> textureNameList;
/*  142 */   private String lastWeaponType = "";
/*  143 */   private int lastWeaponIndex = -1;
/*      */   private PartWeapon lastWeaponPart;
/*      */   
/*      */   public MCH_AircraftInfo(AddonResourceLocation location, String path) {
/*  147 */     super(location, path);
/*  148 */     this.name = location.func_110623_a();
/*  149 */     this.displayName = this.name;
/*  150 */     this.displayNameLang = new HashMap<>();
/*  151 */     this.itemID = 0;
/*  152 */     this.recipeString = new ArrayList<>();
/*  153 */     this.recipe = new ArrayList<>();
/*  154 */     this.isShapedRecipe = true;
/*  155 */     this.category = "zzz";
/*  156 */     this.creativeOnly = false;
/*  157 */     this.invulnerable = false;
/*  158 */     this.isEnableGunnerMode = false;
/*  159 */     this.isEnableConcurrentGunnerMode = false;
/*  160 */     this.isEnableNightVision = false;
/*  161 */     this.isEnableEntityRadar = false;
/*  162 */     this.isEnableEjectionSeat = false;
/*  163 */     this.isEnableParachuting = false;
/*  164 */     this.flare = new Flare(this);
/*  165 */     this.weaponSetList = new ArrayList<>();
/*  166 */     this.seatList = new ArrayList<>();
/*  167 */     this.exclusionSeatList = (List)new ArrayList<>();
/*  168 */     this.hudList = new ArrayList<>();
/*  169 */     this.hudTvMissile = null;
/*  170 */     this.bodyHeight = 0.7F;
/*  171 */     this.bodyWidth = 2.0F;
/*  172 */     this.isFloat = false;
/*  173 */     this.floatOffset = 0.0F;
/*  174 */     this.gravity = -0.04F;
/*  175 */     this.gravityInWater = -0.04F;
/*  176 */     this.maxHp = 50;
/*  177 */     this.damageFactor = 0.2F;
/*  178 */     this.submergedDamageHeight = 0.0F;
/*  179 */     this.inventorySize = 0;
/*  180 */     this.armorDamageFactor = 1.0F;
/*  181 */     this.armorMaxDamage = 100000.0F;
/*  182 */     this.armorMinDamage = 0.0F;
/*  183 */     this.enableBack = false;
/*  184 */     this.isUAV = false;
/*  185 */     this.isSmallUAV = false;
/*  186 */     this.isTargetDrone = false;
/*  187 */     this.autoPilotRot = -0.6F;
/*  188 */     this.regeneration = false;
/*  189 */     this.onGroundPitch = 0.0F;
/*  190 */     this.canMoveOnGround = true;
/*  191 */     this.canRotOnGround = true;
/*  192 */     this.cameraZoom = getDefaultMaxZoom();
/*  193 */     this.extraBoundingBox = new ArrayList<>();
/*  194 */     this.maxFuel = 0;
/*  195 */     this.fuelConsumption = 1.0F;
/*  196 */     this.fuelSupplyRange = 0.0F;
/*  197 */     this.ammoSupplyRange = 0.0F;
/*  198 */     this.repairOtherVehiclesRange = 0.0F;
/*  199 */     this.repairOtherVehiclesValue = 10;
/*  200 */     this.stealth = 0.0F;
/*  201 */     this.canRide = true;
/*  202 */     this.entityWidth = 1.0F;
/*  203 */     this.entityHeight = 1.0F;
/*  204 */     this.entityPitch = 0.0F;
/*  205 */     this.entityRoll = 0.0F;
/*  206 */     this.stepHeight = getDefaultStepHeight();
/*  207 */     this.entityRackList = new ArrayList<>();
/*  208 */     this.mobSeatNum = 0;
/*  209 */     this.entityRackNum = 0;
/*  210 */     this.mobDropOption = new MCH_MobDropOption();
/*  211 */     this.repellingHooks = new ArrayList<>();
/*  212 */     this.rideRacks = new ArrayList<>();
/*  213 */     this.particleSplashs = new ArrayList<>();
/*  214 */     this.searchLights = new ArrayList<>();
/*  215 */     this.markerHeight = 1.0F;
/*  216 */     this.markerWidth = 2.0F;
/*  217 */     this.bbZmax = 1.0F;
/*  218 */     this.bbZmin = -1.0F;
/*  219 */     this.rotorSpeed = getDefaultRotorSpeed();
/*  220 */     this.wheels = getDefaultWheelList();
/*  221 */     this.onGroundPitchFactor = 0.0F;
/*  222 */     this.onGroundRollFactor = 0.0F;
/*  223 */     this.turretPosition = new Vec3d(0.0D, 0.0D, 0.0D);
/*  224 */     this.defaultFreelook = false;
/*  225 */     this.unmountPosition = null;
/*  226 */     this.thirdPersonDist = 4.0F;
/*  227 */     this.cameraPosition = new ArrayList<>();
/*  228 */     this.alwaysCameraView = false;
/*  229 */     this.cameraRotationSpeed = 100.0F;
/*  230 */     this.speed = 0.1F;
/*  231 */     this.motionFactor = 0.96F;
/*  232 */     this.mobilityYaw = 1.0F;
/*  233 */     this.mobilityPitch = 1.0F;
/*  234 */     this.mobilityRoll = 1.0F;
/*  235 */     this.mobilityYawOnGround = 1.0F;
/*  236 */     this.minRotationPitch = getMinRotationPitch();
/*  237 */     this.maxRotationPitch = getMaxRotationPitch();
/*  238 */     this.minRotationRoll = getMinRotationPitch();
/*  239 */     this.maxRotationRoll = getMaxRotationPitch();
/*  240 */     this.limitRotation = false;
/*  241 */     this.throttleUpDown = 1.0F;
/*  242 */     this.throttleUpDownOnEntity = 2.0F;
/*  243 */     this.pivotTurnThrottle = 0.0F;
/*  244 */     this.trackRollerRot = 30.0F;
/*  245 */     this.partWheelRot = 30.0F;
/*  246 */     this.textureNameList = new ArrayList<>();
/*  247 */     this.textureNameList.add(this.name);
/*  248 */     this.textureCount = 0;
/*  249 */     this.particlesScale = 1.0F;
/*  250 */     this.enableSeaSurfaceParticle = false;
/*  251 */     this.hideEntity = false;
/*  252 */     this.smoothShading = true;
/*  253 */     this.soundMove = "";
/*  254 */     this.soundPitch = 1.0F;
/*  255 */     this.soundVolume = 1.0F;
/*  256 */     this.soundRange = getDefaultSoundRange();
/*  257 */     this.model = null;
/*  258 */     this.hatchList = new ArrayList<>();
/*  259 */     this.cameraList = new ArrayList<>();
/*  260 */     this.partWeapon = new ArrayList<>();
/*  261 */     this.lastWeaponPart = null;
/*  262 */     this.partWeaponBay = new ArrayList<>();
/*  263 */     this.canopyList = new ArrayList<>();
/*  264 */     this.landingGear = new ArrayList<>();
/*  265 */     this.partThrottle = new ArrayList<>();
/*  266 */     this.partRotPart = new ArrayList<>();
/*  267 */     this.partCrawlerTrack = new ArrayList<>();
/*  268 */     this.partTrackRoller = new ArrayList<>();
/*  269 */     this.partWheel = new ArrayList<>();
/*  270 */     this.partSteeringWheel = new ArrayList<>();
/*  271 */     this.lightHatchList = new ArrayList<>();
/*      */   }
/*      */   
/*      */   public static String[] getCannotReloadItem() {
/*  275 */     return new String[] { "DisplayName", "AddDisplayName", "ItemID", "AddRecipe", "AddShapelessRecipe", "InventorySize", "Sound", "UAV", "SmallUAV", "TargetDrone", "Category" };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getItemStack() {
/*  281 */     return new ItemStack(getItem());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultSoundRange() {
/*  289 */     return 100.0F;
/*      */   }
/*      */   
/*      */   public List<Wheel> getDefaultWheelList() {
/*  293 */     return new ArrayList<>();
/*      */   }
/*      */   
/*      */   public float getDefaultRotorSpeed() {
/*  297 */     return 0.0F;
/*      */   }
/*      */   
/*      */   private float getDefaultStepHeight() {
/*  301 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public boolean haveRepellingHook() {
/*  305 */     return !this.repellingHooks.isEmpty();
/*      */   }
/*      */   
/*      */   public boolean haveFlare() {
/*  309 */     return (this.flare.types.length > 0);
/*      */   }
/*      */   
/*      */   public boolean haveCanopy() {
/*  313 */     return !this.canopyList.isEmpty();
/*      */   }
/*      */   
/*      */   public boolean haveLandingGear() {
/*  317 */     return !this.landingGear.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean validate() throws Exception {
/*  324 */     if (this.cameraPosition.isEmpty()) {
/*  325 */       this.cameraPosition.add(new CameraPosition(this));
/*      */     }
/*      */     
/*  328 */     this.bbZ = (this.bbZmax + this.bbZmin) / 2.0F;
/*  329 */     if (this.isTargetDrone) {
/*  330 */       this.isUAV = true;
/*      */     }
/*      */     
/*  333 */     if (this.isEnableParachuting && !this.repellingHooks.isEmpty()) {
/*  334 */       this.isEnableParachuting = false;
/*  335 */       this.repellingHooks.clear();
/*      */     } 
/*      */     
/*  338 */     if (this.isUAV) {
/*  339 */       this.alwaysCameraView = true;
/*  340 */       if (this.seatList.isEmpty()) {
/*  341 */         MCH_SeatInfo s = new MCH_SeatInfo(new Vec3d(0.0D, 0.0D, 0.0D), false);
/*  342 */         this.seatList.add(s);
/*      */       } 
/*      */     } 
/*      */     
/*  346 */     this.mobSeatNum = this.seatList.size();
/*  347 */     this.entityRackNum = this.entityRackList.size();
/*  348 */     if (getNumSeat() < 1) {
/*  349 */       throw new Exception("At least one seat must be set.");
/*      */     }
/*  351 */     if (getNumHud() < getNumSeat()) {
/*  352 */       for (int j = getNumHud(); j < getNumSeat(); j++) {
/*  353 */         this.hudList.add(MCH_HudManager.get(getDefaultHudName(j)));
/*      */       }
/*      */     }
/*      */     
/*  357 */     if (getNumSeat() == 1 && getNumHud() == 1) {
/*  358 */       this.hudList.add(MCH_HudManager.get(getDefaultHudName(1)));
/*      */     }
/*      */     
/*  361 */     this.seatList.addAll((Collection)this.entityRackList);
/*      */     
/*  363 */     this.entityRackList.clear();
/*  364 */     if (this.hudTvMissile == null) {
/*  365 */       this.hudTvMissile = MCH_HudManager.get("tv_missile");
/*      */     }
/*      */     
/*  368 */     if (this.textureNameList.isEmpty()) {
/*  369 */       throw new Exception("At least one texture must be set.");
/*      */     }
/*  371 */     if (this.itemID <= 0);
/*      */ 
/*      */     
/*  374 */     for (int i = 0; i < this.partWeaponBay.size(); i++) {
/*  375 */       WeaponBay wb = this.partWeaponBay.get(i);
/*  376 */       String[] weaponNames = wb.weaponName.split("\\s*/\\s*");
/*  377 */       if (weaponNames.length == 0) {
/*  378 */         this.partWeaponBay.remove(i);
/*      */       } else {
/*  380 */         List<Integer> list = new ArrayList<>();
/*      */         
/*  382 */         for (String s : weaponNames) {
/*  383 */           int id = getWeaponIdByName(s);
/*  384 */           if (id >= 0) {
/*  385 */             list.add(Integer.valueOf(id));
/*      */           }
/*      */         } 
/*      */         
/*  389 */         if (list.isEmpty()) {
/*  390 */           this.partWeaponBay.remove(i);
/*      */         } else {
/*  392 */           ((WeaponBay)this.partWeaponBay.get(i)).weaponIds = list.<Integer>toArray(new Integer[0]);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  397 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInfo_MaxSeatNum() {
/*  403 */     return 30;
/*      */   }
/*      */   
/*      */   public int getNumSeatAndRack() {
/*  407 */     return this.seatList.size();
/*      */   }
/*      */   
/*      */   public int getNumSeat() {
/*  411 */     return this.mobSeatNum;
/*      */   }
/*      */   
/*      */   public int getNumRack() {
/*  415 */     return this.entityRackNum;
/*      */   }
/*      */   
/*      */   public int getNumHud() {
/*  419 */     return this.hudList.size();
/*      */   }
/*      */   
/*      */   public float getMaxSpeed() {
/*  423 */     return 0.8F;
/*      */   }
/*      */   
/*      */   public float getMinRotationPitch() {
/*  427 */     return -89.9F;
/*      */   }
/*      */   
/*      */   public float getMaxRotationPitch() {
/*  431 */     return 80.0F;
/*      */   }
/*      */   
/*      */   public float getMinRotationRoll() {
/*  435 */     return -80.0F;
/*      */   }
/*      */   
/*      */   public float getMaxRotationRoll() {
/*  439 */     return 80.0F;
/*      */   }
/*      */   
/*      */   public int getDefaultMaxZoom() {
/*  443 */     return 1;
/*      */   }
/*      */   
/*      */   public boolean haveHatch() {
/*  447 */     return !this.hatchList.isEmpty();
/*      */   }
/*      */   
/*      */   public boolean havePartCamera() {
/*  451 */     return !this.cameraList.isEmpty();
/*      */   }
/*      */   
/*      */   public boolean havePartThrottle() {
/*  455 */     return !this.partThrottle.isEmpty();
/*      */   }
/*      */   
/*      */   public WeaponSet getWeaponSetById(int id) {
/*  459 */     return (id >= 0 && id < this.weaponSetList.size()) ? this.weaponSetList.get(id) : null;
/*      */   }
/*      */   
/*      */   public Weapon getWeaponById(int id) {
/*  463 */     WeaponSet ws = getWeaponSetById(id);
/*  464 */     return (ws != null) ? ws.weapons.get(0) : null;
/*      */   }
/*      */   
/*      */   public int getWeaponIdByName(String s) {
/*  468 */     for (int i = 0; i < this.weaponSetList.size(); i++) {
/*  469 */       if (((WeaponSet)this.weaponSetList.get(i)).type.equalsIgnoreCase(s)) {
/*  470 */         return i;
/*      */       }
/*      */     } 
/*      */     
/*  474 */     return -1;
/*      */   }
/*      */   
/*      */   public Weapon getWeaponByName(String s) {
/*  478 */     for (int i = 0; i < this.weaponSetList.size(); i++) {
/*  479 */       if (((WeaponSet)this.weaponSetList.get(i)).type.equalsIgnoreCase(s)) {
/*  480 */         return getWeaponById(i);
/*      */       }
/*      */     } 
/*      */     
/*  484 */     return null;
/*      */   }
/*      */   
/*      */   public int getWeaponNum() {
/*  488 */     return this.weaponSetList.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadItemData(String item, String data) {
/*  493 */     if (item.compareTo("displayname") == 0) {
/*  494 */       this.displayName = data.trim();
/*  495 */     } else if (item.compareTo("adddisplayname") == 0) {
/*  496 */       String[] s = data.split("\\s*,\\s*");
/*  497 */       if (s.length == 2) {
/*  498 */         this.displayNameLang.put(s[0].toLowerCase().trim(), s[1].trim());
/*      */       }
/*  500 */     } else if (item.equalsIgnoreCase("Category")) {
/*  501 */       this.category = data.toUpperCase().replaceAll("[,;:]", ".").replaceAll("[ \t]", "");
/*  502 */     } else if (item.equalsIgnoreCase("CanRide")) {
/*  503 */       this.canRide = toBool(data, true);
/*  504 */     } else if (item.equalsIgnoreCase("CreativeOnly")) {
/*  505 */       this.creativeOnly = toBool(data, false);
/*  506 */     } else if (item.equalsIgnoreCase("Invulnerable")) {
/*  507 */       this.invulnerable = toBool(data, false);
/*  508 */     } else if (item.equalsIgnoreCase("MaxFuel")) {
/*  509 */       this.maxFuel = toInt(data, 0, 100000000);
/*  510 */     } else if (item.equalsIgnoreCase("FuelConsumption")) {
/*  511 */       this.fuelConsumption = toFloat(data, 0.0F, 10000.0F);
/*  512 */     } else if (item.equalsIgnoreCase("FuelSupplyRange")) {
/*  513 */       this.fuelSupplyRange = toFloat(data, 0.0F, 1000.0F);
/*  514 */     } else if (item.equalsIgnoreCase("AmmoSupplyRange")) {
/*  515 */       this.ammoSupplyRange = toFloat(data, 0.0F, 1000.0F);
/*  516 */     } else if (item.equalsIgnoreCase("RepairOtherVehicles")) {
/*  517 */       String[] s = splitParam(data);
/*  518 */       if (s.length >= 1) {
/*  519 */         this.repairOtherVehiclesRange = toFloat(s[0], 0.0F, 1000.0F);
/*  520 */         if (s.length >= 2) {
/*  521 */           this.repairOtherVehiclesValue = toInt(s[1], 0, 10000000);
/*      */         }
/*      */       } 
/*  524 */     } else if (item.compareTo("itemid") == 0) {
/*  525 */       this.itemID = toInt(data, 0, 65535);
/*  526 */     } else if (item.compareTo("addtexture") == 0) {
/*  527 */       this.textureNameList.add(data.toLowerCase());
/*  528 */     } else if (item.compareTo("particlesscale") == 0) {
/*  529 */       this.particlesScale = toFloat(data, 0.0F, 50.0F);
/*  530 */     } else if (item.equalsIgnoreCase("EnableSeaSurfaceParticle")) {
/*  531 */       this.enableSeaSurfaceParticle = toBool(data);
/*  532 */     } else if (item.equalsIgnoreCase("AddParticleSplash")) {
/*  533 */       String[] s = splitParam(data);
/*  534 */       if (s.length >= 3) {
/*  535 */         Vec3d v = toVec3(s[0], s[1], s[2]);
/*  536 */         int num = (s.length >= 4) ? toInt(s[3], 1, 100) : 2;
/*  537 */         float size = (s.length >= 5) ? toFloat(s[4]) : 2.0F;
/*  538 */         float acc = (s.length >= 6) ? toFloat(s[5]) : 1.0F;
/*  539 */         int age = (s.length >= 7) ? toInt(s[6], 1, 100000) : 80;
/*  540 */         float motionY = (s.length >= 8) ? toFloat(s[7]) : 0.01F;
/*  541 */         float gravity = (s.length >= 9) ? toFloat(s[8]) : 0.0F;
/*  542 */         this.particleSplashs.add(new ParticleSplash(this, v, num, size, acc, age, motionY, gravity));
/*      */       } 
/*  544 */     } else if (item.equalsIgnoreCase("AddSearchLight") || item.equalsIgnoreCase("AddFixedSearchLight") || item.equalsIgnoreCase("AddSteeringSearchLight")) {
/*  545 */       String[] s = splitParam(data);
/*  546 */       if (s.length >= 7) {
/*  547 */         Vec3d v = toVec3(s[0], s[1], s[2]);
/*  548 */         int cs = hex2dec(s[3]);
/*  549 */         int ce = hex2dec(s[4]);
/*  550 */         float h = toFloat(s[5]);
/*  551 */         float w = toFloat(s[6]);
/*  552 */         float yaw = (s.length >= 8) ? toFloat(s[7]) : 0.0F;
/*  553 */         float pitch = (s.length >= 9) ? toFloat(s[8]) : 0.0F;
/*  554 */         float stRot = (s.length >= 10) ? toFloat(s[9]) : 0.0F;
/*  555 */         boolean fixDir = !item.equalsIgnoreCase("AddSearchLight");
/*  556 */         boolean steering = item.equalsIgnoreCase("AddSteeringSearchLight");
/*  557 */         this.searchLights.add(new SearchLight(this, v, cs, ce, h, w, fixDir, yaw, pitch, steering, stRot));
/*      */       } 
/*  559 */     } else if (item.equalsIgnoreCase("AddPartLightHatch")) {
/*  560 */       String[] s = splitParam(data);
/*  561 */       if (s.length >= 6) {
/*  562 */         float mx = (s.length >= 7) ? toFloat(s[6], -1800.0F, 1800.0F) : 90.0F;
/*  563 */         this.lightHatchList
/*  564 */           .add(new Hatch(this, 
/*      */ 
/*      */               
/*  567 */               toFloat(s[0]), 
/*  568 */               toFloat(s[1]), 
/*  569 */               toFloat(s[2]), 
/*  570 */               toFloat(s[3]), 
/*  571 */               toFloat(s[4]), 
/*  572 */               toFloat(s[5]), mx, "light_hatch" + this.lightHatchList
/*      */               
/*  574 */               .size(), false));
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  579 */     else if (item.equalsIgnoreCase("AddRepellingHook")) {
/*  580 */       String[] s = splitParam(data);
/*  581 */       if (s != null && s.length >= 3) {
/*  582 */         int inv = (s.length >= 4) ? toInt(s[3], 1, 100000) : 10;
/*  583 */         this.repellingHooks.add(new RepellingHook(this, toVec3(s[0], s[1], s[2]), inv));
/*      */       } 
/*  585 */     } else if (item.equalsIgnoreCase("AddRack")) {
/*  586 */       String[] s = data.toLowerCase().split("\\s*,\\s*");
/*  587 */       if (s.length >= 7) {
/*  588 */         String[] names = s[0].split("\\s*/\\s*");
/*  589 */         float range = (s.length >= 8) ? toFloat(s[7]) : 6.0F;
/*  590 */         float para = (s.length >= 9) ? toFloat(s[8], 0.0F, 1000000.0F) : 20.0F;
/*  591 */         float yaw = (s.length >= 10) ? toFloat(s[9]) : 0.0F;
/*  592 */         float pitch = (s.length >= 11) ? toFloat(s[10]) : 0.0F;
/*  593 */         boolean rs = (s.length >= 12 && toBool(s[11]));
/*  594 */         this.entityRackList
/*  595 */           .add(new MCH_SeatRackInfo(names, 
/*      */ 
/*      */               
/*  598 */               toDouble(s[1]), 
/*  599 */               toDouble(s[2]), 
/*  600 */               toDouble(s[3]), new CameraPosition(this, 
/*  601 */                 toVec3(s[4], s[5], s[6]).func_72441_c(0.0D, 1.5D, 0.0D)), range, para, yaw, pitch, rs));
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  610 */     else if (item.equalsIgnoreCase("RideRack")) {
/*  611 */       String[] s = splitParam(data);
/*  612 */       if (s.length >= 2) {
/*  613 */         RideRack r = new RideRack(this, s[0].trim().toLowerCase(), toInt(s[1], 1, 10000));
/*  614 */         this.rideRacks.add(r);
/*      */       } 
/*  616 */     } else if (item.equalsIgnoreCase("AddSeat") || item.equalsIgnoreCase("AddGunnerSeat") || item.equalsIgnoreCase("AddFixRotSeat")) {
/*  617 */       if (this.seatList.size() >= getInfo_MaxSeatNum()) {
/*      */         return;
/*      */       }
/*      */       
/*  621 */       String[] s = splitParam(data);
/*  622 */       if (s.length < 3) {
/*      */         return;
/*      */       }
/*      */       
/*  626 */       Vec3d p = toVec3(s[0], s[1], s[2]);
/*  627 */       if (item.equalsIgnoreCase("AddSeat")) {
/*  628 */         boolean rs = (s.length >= 4 && toBool(s[3]));
/*  629 */         MCH_SeatInfo seat = new MCH_SeatInfo(p, rs);
/*  630 */         this.seatList.add(seat);
/*      */       } else {
/*      */         MCH_SeatInfo seat;
/*  633 */         if (s.length >= 6) {
/*  634 */           CameraPosition c = new CameraPosition(this, toVec3(s[3], s[4], s[5]));
/*  635 */           boolean sg = (s.length >= 7 && toBool(s[6]));
/*  636 */           if (item.equalsIgnoreCase("AddGunnerSeat")) {
/*  637 */             if (s.length >= 9) {
/*  638 */               float minPitch = toFloat(s[7], -90.0F, 90.0F);
/*  639 */               float maxPitch = toFloat(s[8], -90.0F, 90.0F);
/*  640 */               if (minPitch > maxPitch) {
/*  641 */                 float t = minPitch;
/*  642 */                 minPitch = maxPitch;
/*  643 */                 maxPitch = t;
/*      */               } 
/*      */               
/*  646 */               boolean rs = (s.length >= 10 && toBool(s[9]));
/*  647 */               seat = new MCH_SeatInfo(p, true, c, true, sg, false, 0.0F, 0.0F, minPitch, maxPitch, rs);
/*      */             } else {
/*  649 */               seat = new MCH_SeatInfo(p, true, c, true, sg, false, 0.0F, 0.0F, false);
/*      */             } 
/*      */           } else {
/*  652 */             boolean fixRot = (s.length >= 9);
/*  653 */             float fixYaw = fixRot ? toFloat(s[7]) : 0.0F;
/*  654 */             float fixPitch = fixRot ? toFloat(s[8]) : 0.0F;
/*  655 */             boolean rs = (s.length >= 10 && toBool(s[9]));
/*  656 */             seat = new MCH_SeatInfo(p, true, c, true, sg, fixRot, fixYaw, fixPitch, rs);
/*      */           } 
/*      */         } else {
/*  659 */           seat = new MCH_SeatInfo(p, true, new CameraPosition(this), false, false, false, 0.0F, 0.0F, false);
/*      */         } 
/*      */         
/*  662 */         this.seatList.add(seat);
/*      */       } 
/*  664 */     } else if (item.equalsIgnoreCase("SetWheelPos")) {
/*  665 */       String[] sx = splitParam(data);
/*  666 */       if (sx.length >= 4) {
/*  667 */         float x = Math.abs(toFloat(sx[0]));
/*  668 */         float y = toFloat(sx[1]);
/*  669 */         this.wheels.clear();
/*      */         
/*  671 */         for (int i = 2; i < sx.length; i++) {
/*  672 */           this.wheels.add(new Wheel(this, new Vec3d(x, y, toFloat(sx[i]))));
/*      */         }
/*      */         
/*  675 */         this.wheels.sort((arg0, arg1) -> (arg0.pos.field_72449_c > arg1.pos.field_72449_c) ? -1 : 1);
/*      */       } 
/*  677 */     } else if (item.equalsIgnoreCase("ExclusionSeat")) {
/*  678 */       String[] sx = splitParam(data);
/*  679 */       if (sx.length >= 2) {
/*  680 */         Integer[] a = new Integer[sx.length];
/*      */         
/*  682 */         for (int i = 0; i < a.length; i++) {
/*  683 */           a[i] = Integer.valueOf(toInt(sx[i], 1, 10000) - 1);
/*      */         }
/*      */         
/*  686 */         this.exclusionSeatList.add(a);
/*      */       } 
/*  688 */     } else if (MCH_MOD.proxy.isRemote() && item.equalsIgnoreCase("HUD")) {
/*  689 */       this.hudList.clear();
/*  690 */       String[] ss = data.split("\\s*,\\s*");
/*      */       
/*  692 */       for (String sx : ss) {
/*  693 */         MCH_Hud hud = MCH_HudManager.get(sx);
/*  694 */         if (hud == null) {
/*  695 */           hud = MCH_Hud.NoDisp;
/*      */         }
/*      */         
/*  698 */         this.hudList.add(hud);
/*      */       } 
/*  700 */     } else if (item.compareTo("enablenightvision") == 0) {
/*  701 */       this.isEnableNightVision = toBool(data);
/*  702 */     } else if (item.compareTo("enableentityradar") == 0) {
/*  703 */       this.isEnableEntityRadar = toBool(data);
/*  704 */     } else if (item.equalsIgnoreCase("EnableEjectionSeat")) {
/*  705 */       this.isEnableEjectionSeat = toBool(data);
/*  706 */     } else if (item.equalsIgnoreCase("EnableParachuting")) {
/*  707 */       this.isEnableParachuting = toBool(data);
/*  708 */     } else if (item.equalsIgnoreCase("MobDropOption")) {
/*  709 */       String[] sx = splitParam(data);
/*  710 */       if (sx.length >= 3) {
/*  711 */         this.mobDropOption.pos = toVec3(sx[0], sx[1], sx[2]);
/*  712 */         this.mobDropOption.interval = (sx.length >= 4) ? toInt(sx[3]) : 12;
/*      */       } 
/*  714 */     } else if (item.equalsIgnoreCase("Width")) {
/*  715 */       this.bodyWidth = toFloat(data, 0.1F, 1000.0F);
/*  716 */     } else if (item.equalsIgnoreCase("Height")) {
/*  717 */       this.bodyHeight = toFloat(data, 0.1F, 1000.0F);
/*  718 */     } else if (item.compareTo("float") == 0) {
/*  719 */       this.isFloat = toBool(data);
/*  720 */     } else if (item.compareTo("floatoffset") == 0) {
/*  721 */       this.floatOffset = -toFloat(data);
/*  722 */     } else if (item.compareTo("gravity") == 0) {
/*  723 */       this.gravity = toFloat(data, -50.0F, 50.0F);
/*  724 */     } else if (item.compareTo("gravityinwater") == 0) {
/*  725 */       this.gravityInWater = toFloat(data, -50.0F, 50.0F);
/*  726 */     } else if (item.compareTo("cameraposition") == 0) {
/*  727 */       String[] sx = data.split("\\s*,\\s*");
/*  728 */       if (sx.length >= 3) {
/*  729 */         this.alwaysCameraView = (sx.length >= 4 && toBool(sx[3]));
/*  730 */         boolean fixRot = (sx.length >= 5);
/*  731 */         float yaw = (sx.length >= 5) ? toFloat(sx[4]) : 0.0F;
/*  732 */         float pitch = (sx.length >= 6) ? toFloat(sx[5]) : 0.0F;
/*  733 */         this.cameraPosition.add(new CameraPosition(this, toVec3(sx[0], sx[1], sx[2]), fixRot, yaw, pitch));
/*      */       } 
/*  735 */     } else if (item.equalsIgnoreCase("UnmountPosition")) {
/*  736 */       String[] sx = data.split("\\s*,\\s*");
/*  737 */       if (sx.length >= 3) {
/*  738 */         this.unmountPosition = toVec3(sx[0], sx[1], sx[2]);
/*      */       }
/*  740 */     } else if (item.equalsIgnoreCase("ThirdPersonDist")) {
/*  741 */       this.thirdPersonDist = toFloat(data, 4.0F, 100.0F);
/*  742 */     } else if (item.equalsIgnoreCase("TurretPosition")) {
/*  743 */       String[] sx = data.split("\\s*,\\s*");
/*  744 */       if (sx.length >= 3) {
/*  745 */         this.turretPosition = toVec3(sx[0], sx[1], sx[2]);
/*      */       }
/*  747 */     } else if (item.equalsIgnoreCase("CameraRotationSpeed")) {
/*  748 */       this.cameraRotationSpeed = toFloat(data, 0.0F, 10000.0F);
/*  749 */     } else if (item.compareTo("regeneration") == 0) {
/*  750 */       this.regeneration = toBool(data);
/*  751 */     } else if (item.compareTo("speed") == 0) {
/*  752 */       this.speed = toFloat(data, 0.0F, getMaxSpeed());
/*  753 */     } else if (item.equalsIgnoreCase("EnableBack")) {
/*  754 */       this.enableBack = toBool(data);
/*  755 */     } else if (item.equalsIgnoreCase("MotionFactor")) {
/*  756 */       this.motionFactor = toFloat(data, 0.0F, 1.0F);
/*  757 */     } else if (item.equalsIgnoreCase("MobilityYawOnGround")) {
/*  758 */       this.mobilityYawOnGround = toFloat(data, 0.0F, 100.0F);
/*  759 */     } else if (item.equalsIgnoreCase("MobilityYaw")) {
/*  760 */       this.mobilityYaw = toFloat(data, 0.0F, 100.0F);
/*  761 */     } else if (item.equalsIgnoreCase("MobilityPitch")) {
/*  762 */       this.mobilityPitch = toFloat(data, 0.0F, 100.0F);
/*  763 */     } else if (item.equalsIgnoreCase("MobilityRoll")) {
/*  764 */       this.mobilityRoll = toFloat(data, 0.0F, 100.0F);
/*  765 */     } else if (item.equalsIgnoreCase("MinRotationPitch")) {
/*  766 */       this.limitRotation = true;
/*  767 */       this.minRotationPitch = toFloat(data, getMinRotationPitch(), 0.0F);
/*  768 */     } else if (item.equalsIgnoreCase("MaxRotationPitch")) {
/*  769 */       this.limitRotation = true;
/*  770 */       this.maxRotationPitch = toFloat(data, 0.0F, getMaxRotationPitch());
/*  771 */     } else if (item.equalsIgnoreCase("MinRotationRoll")) {
/*  772 */       this.limitRotation = true;
/*  773 */       this.minRotationRoll = toFloat(data, getMinRotationRoll(), 0.0F);
/*  774 */     } else if (item.equalsIgnoreCase("MaxRotationRoll")) {
/*  775 */       this.limitRotation = true;
/*  776 */       this.maxRotationRoll = toFloat(data, 0.0F, getMaxRotationRoll());
/*  777 */     } else if (item.compareTo("throttleupdown") == 0) {
/*  778 */       this.throttleUpDown = toFloat(data, 0.0F, 3.0F);
/*  779 */     } else if (item.equalsIgnoreCase("ThrottleUpDownOnEntity")) {
/*  780 */       this.throttleUpDownOnEntity = toFloat(data, 0.0F, 100000.0F);
/*  781 */     } else if (item.equalsIgnoreCase("Stealth")) {
/*  782 */       this.stealth = toFloat(data, 0.0F, 1.0F);
/*  783 */     } else if (item.equalsIgnoreCase("EntityWidth")) {
/*  784 */       this.entityWidth = toFloat(data, -100.0F, 100.0F);
/*  785 */     } else if (item.equalsIgnoreCase("EntityHeight")) {
/*  786 */       this.entityHeight = toFloat(data, -100.0F, 100.0F);
/*  787 */     } else if (item.equalsIgnoreCase("EntityPitch")) {
/*  788 */       this.entityPitch = toFloat(data, -360.0F, 360.0F);
/*  789 */     } else if (item.equalsIgnoreCase("EntityRoll")) {
/*  790 */       this.entityRoll = toFloat(data, -360.0F, 360.0F);
/*  791 */     } else if (item.equalsIgnoreCase("StepHeight")) {
/*  792 */       this.stepHeight = toFloat(data, 0.0F, 1000.0F);
/*  793 */     } else if (item.equalsIgnoreCase("CanMoveOnGround")) {
/*  794 */       this.canMoveOnGround = toBool(data);
/*  795 */     } else if (item.equalsIgnoreCase("CanRotOnGround")) {
/*  796 */       this.canRotOnGround = toBool(data);
/*  797 */     } else if (item.equalsIgnoreCase("AddWeapon") || item.equalsIgnoreCase("AddTurretWeapon")) {
/*  798 */       String[] sx = data.split("\\s*,\\s*");
/*  799 */       String type = sx[0].toLowerCase();
/*  800 */       if (sx.length >= 4 && MCH_WeaponInfoManager.contains(type)) {
/*  801 */         float y = (sx.length >= 5) ? toFloat(sx[4]) : 0.0F;
/*  802 */         float p = (sx.length >= 6) ? toFloat(sx[5]) : 0.0F;
/*  803 */         boolean canUsePilot = (sx.length < 7 || toBool(sx[6]));
/*  804 */         int seatID = (sx.length >= 8) ? (toInt(sx[7], 1, getInfo_MaxSeatNum()) - 1) : 0;
/*  805 */         if (seatID <= 0) {
/*  806 */           canUsePilot = true;
/*      */         }
/*      */         
/*  809 */         float dfy = (sx.length >= 9) ? toFloat(sx[8]) : 0.0F;
/*  810 */         dfy = MathHelper.func_76142_g(dfy);
/*  811 */         float mny = (sx.length >= 10) ? toFloat(sx[9]) : 0.0F;
/*  812 */         float mxy = (sx.length >= 11) ? toFloat(sx[10]) : 0.0F;
/*  813 */         float mnp = (sx.length >= 12) ? toFloat(sx[11]) : 0.0F;
/*  814 */         float mxp = (sx.length >= 13) ? toFloat(sx[12]) : 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  829 */         Weapon e = new Weapon(this, toFloat(sx[1]), toFloat(sx[2]), toFloat(sx[3]), y, p, canUsePilot, seatID, dfy, mny, mxy, mnp, mxp, item.equalsIgnoreCase("AddTurretWeapon"));
/*      */         
/*  831 */         if (type.compareTo(this.lastWeaponType) != 0) {
/*  832 */           this.weaponSetList.add(new WeaponSet(this, type));
/*  833 */           this.lastWeaponIndex++;
/*  834 */           this.lastWeaponType = type;
/*      */         } 
/*      */         
/*  837 */         ((WeaponSet)this.weaponSetList.get(this.lastWeaponIndex)).weapons.add(e);
/*      */       } 
/*  839 */     } else if (item.equalsIgnoreCase("AddPartWeapon") || item
/*  840 */       .equalsIgnoreCase("AddPartRotWeapon") || item
/*  841 */       .equalsIgnoreCase("AddPartTurretWeapon") || item
/*  842 */       .equalsIgnoreCase("AddPartTurretRotWeapon") || item
/*  843 */       .equalsIgnoreCase("AddPartWeaponMissile")) {
/*  844 */       String[] sx = data.split("\\s*,\\s*");
/*  845 */       if (sx.length >= 7) {
/*  846 */         float rx = 0.0F;
/*  847 */         float ry = 0.0F;
/*  848 */         float rz = 0.0F;
/*  849 */         float rb = 0.0F;
/*  850 */         boolean isRot = (item.equalsIgnoreCase("AddPartRotWeapon") || item.equalsIgnoreCase("AddPartTurretRotWeapon"));
/*  851 */         boolean isMissile = item.equalsIgnoreCase("AddPartWeaponMissile");
/*  852 */         boolean turret = (item.equalsIgnoreCase("AddPartTurretWeapon") || item.equalsIgnoreCase("AddPartTurretRotWeapon"));
/*  853 */         if (isRot) {
/*  854 */           rx = (sx.length >= 10) ? toFloat(sx[7]) : 0.0F;
/*  855 */           ry = (sx.length >= 10) ? toFloat(sx[8]) : 0.0F;
/*  856 */           rz = (sx.length >= 10) ? toFloat(sx[9]) : -1.0F;
/*      */         } else {
/*  858 */           rb = (sx.length >= 8) ? toFloat(sx[7]) : 0.0F;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  872 */         PartWeapon w = new PartWeapon(this, splitParamSlash(sx[0].toLowerCase().trim()), isRot, isMissile, toBool(sx[1]), toBool(sx[2]), toBool(sx[3]), toFloat(sx[4]), toFloat(sx[5]), toFloat(sx[6]), "weapon" + this.partWeapon.size(), rx, ry, rz, rb, turret);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  879 */         this.lastWeaponPart = w;
/*  880 */         this.partWeapon.add(w);
/*      */       } 
/*  882 */     } else if (item.equalsIgnoreCase("AddPartWeaponChild")) {
/*  883 */       String[] sx = data.split("\\s*,\\s*");
/*  884 */       if (sx.length >= 5 && this.lastWeaponPart != null) {
/*  885 */         float rb = (sx.length >= 6) ? toFloat(sx[5]) : 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  894 */         PartWeaponChild w = new PartWeaponChild(this, this.lastWeaponPart.name, toBool(sx[0]), toBool(sx[1]), toFloat(sx[2]), toFloat(sx[3]), toFloat(sx[4]), this.lastWeaponPart.modelName + "_" + this.lastWeaponPart.child.size(), 0.0F, 0.0F, 0.0F, rb);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  900 */         this.lastWeaponPart.child.add(w);
/*      */       } 
/*  902 */     } else if (item.compareTo("addrecipe") == 0 || item.compareTo("addshapelessrecipe") == 0) {
/*  903 */       this.isShapedRecipe = (item.compareTo("addrecipe") == 0);
/*  904 */       this.recipeString.add(data.toUpperCase());
/*  905 */     } else if (item.compareTo("maxhp") == 0) {
/*  906 */       this.maxHp = toInt(data, 1, 1000000000);
/*  907 */     } else if (item.compareTo("inventorysize") == 0) {
/*  908 */       this.inventorySize = toInt(data, 0, 54);
/*  909 */     } else if (item.compareTo("damagefactor") == 0) {
/*  910 */       this.damageFactor = toFloat(data, 0.0F, 1.0F);
/*  911 */     } else if (item.equalsIgnoreCase("SubmergedDamageHeight")) {
/*  912 */       this.submergedDamageHeight = toFloat(data, -1000.0F, 1000.0F);
/*  913 */     } else if (item.equalsIgnoreCase("ArmorDamageFactor")) {
/*  914 */       this.armorDamageFactor = toFloat(data, 0.0F, 10000.0F);
/*  915 */     } else if (item.equalsIgnoreCase("ArmorMinDamage")) {
/*  916 */       this.armorMinDamage = toFloat(data, 0.0F, 1000000.0F);
/*  917 */     } else if (item.equalsIgnoreCase("ArmorMaxDamage")) {
/*  918 */       this.armorMaxDamage = toFloat(data, 0.0F, 1000000.0F);
/*  919 */     } else if (item.equalsIgnoreCase("FlareType")) {
/*  920 */       String[] sx = data.split("\\s*,\\s*");
/*  921 */       this.flare.types = new int[sx.length];
/*      */       
/*  923 */       for (int i = 0; i < sx.length; i++) {
/*  924 */         this.flare.types[i] = toInt(sx[i], 1, 10);
/*      */       }
/*  926 */     } else if (item.equalsIgnoreCase("FlareOption")) {
/*  927 */       String[] sx = splitParam(data);
/*  928 */       if (sx.length >= 3) {
/*  929 */         this.flare.pos = toVec3(sx[0], sx[1], sx[2]);
/*      */       }
/*  931 */     } else if (item.equalsIgnoreCase("Sound")) {
/*  932 */       this.soundMove = data.toLowerCase();
/*  933 */     } else if (item.equalsIgnoreCase("SoundRange")) {
/*  934 */       this.soundRange = toFloat(data, 1.0F, 1000.0F);
/*  935 */     } else if (item.equalsIgnoreCase("SoundVolume")) {
/*  936 */       this.soundVolume = toFloat(data, 0.0F, 10.0F);
/*  937 */     } else if (item.equalsIgnoreCase("SoundPitch")) {
/*  938 */       this.soundPitch = toFloat(data, 0.0F, 10.0F);
/*  939 */     } else if (item.equalsIgnoreCase("UAV")) {
/*  940 */       this.isUAV = toBool(data);
/*  941 */       this.isSmallUAV = false;
/*  942 */     } else if (item.equalsIgnoreCase("SmallUAV")) {
/*  943 */       this.isUAV = toBool(data);
/*  944 */       this.isSmallUAV = true;
/*  945 */     } else if (item.equalsIgnoreCase("TargetDrone")) {
/*  946 */       this.isTargetDrone = toBool(data);
/*  947 */     } else if (item.compareTo("autopilotrot") == 0) {
/*  948 */       this.autoPilotRot = toFloat(data, -5.0F, 5.0F);
/*  949 */     } else if (item.compareTo("ongroundpitch") == 0) {
/*  950 */       this.onGroundPitch = -toFloat(data, -90.0F, 90.0F);
/*  951 */     } else if (item.compareTo("enablegunnermode") == 0) {
/*  952 */       this.isEnableGunnerMode = toBool(data);
/*  953 */     } else if (item.compareTo("hideentity") == 0) {
/*  954 */       this.hideEntity = toBool(data);
/*  955 */     } else if (item.equalsIgnoreCase("SmoothShading")) {
/*  956 */       this.smoothShading = toBool(data);
/*  957 */     } else if (item.compareTo("concurrentgunnermode") == 0) {
/*  958 */       this.isEnableConcurrentGunnerMode = toBool(data);
/*  959 */     } else if (item.equalsIgnoreCase("AddPartWeaponBay") || item.equalsIgnoreCase("AddPartSlideWeaponBay")) {
/*  960 */       boolean slide = item.equalsIgnoreCase("AddPartSlideWeaponBay");
/*  961 */       String[] sx = data.split("\\s*,\\s*");
/*      */       
/*  963 */       if (slide) {
/*  964 */         if (sx.length >= 4) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  975 */           WeaponBay n = new WeaponBay(this, sx[0].trim().toLowerCase(), toFloat(sx[1]), toFloat(sx[2]), toFloat(sx[3]), 0.0F, 0.0F, 0.0F, 90.0F, "wb" + this.partWeaponBay.size(), true);
/*      */ 
/*      */           
/*  978 */           this.partWeaponBay.add(n);
/*      */         } 
/*  980 */       } else if (sx.length >= 7) {
/*  981 */         float mx = (sx.length >= 8) ? toFloat(sx[7], -180.0F, 180.0F) : 90.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  992 */         WeaponBay n = new WeaponBay(this, sx[0].trim().toLowerCase(), toFloat(sx[1]), toFloat(sx[2]), toFloat(sx[3]), toFloat(sx[4]), toFloat(sx[5]), toFloat(sx[6]), mx / 90.0F, "wb" + this.partWeaponBay.size(), false);
/*      */ 
/*      */         
/*  995 */         this.partWeaponBay.add(n);
/*      */       } 
/*  997 */     } else if (item.compareTo("addparthatch") == 0 || item.compareTo("addpartslidehatch") == 0) {
/*  998 */       boolean slide = (item.compareTo("addpartslidehatch") == 0);
/*  999 */       String[] sx = data.split("\\s*,\\s*");
/*      */       
/* 1001 */       if (slide) {
/* 1002 */         if (sx.length >= 3) {
/*      */           
/* 1004 */           Hatch n = new Hatch(this, toFloat(sx[0]), toFloat(sx[1]), toFloat(sx[2]), 0.0F, 0.0F, 0.0F, 90.0F, "hatch" + this.hatchList.size(), true);
/*      */           
/* 1006 */           this.hatchList.add(n);
/*      */         } 
/* 1008 */       } else if (sx.length >= 6) {
/* 1009 */         float mx = (sx.length >= 7) ? toFloat(sx[6], -180.0F, 180.0F) : 90.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1019 */         Hatch n = new Hatch(this, toFloat(sx[0]), toFloat(sx[1]), toFloat(sx[2]), toFloat(sx[3]), toFloat(sx[4]), toFloat(sx[5]), mx, "hatch" + this.hatchList.size(), false);
/*      */ 
/*      */         
/* 1022 */         this.hatchList.add(n);
/*      */       } 
/* 1024 */     } else if (item.compareTo("addpartcanopy") == 0 || item.compareTo("addpartslidecanopy") == 0) {
/* 1025 */       String[] sx = data.split("\\s*,\\s*");
/* 1026 */       boolean slide = (item.compareTo("addpartslidecanopy") == 0);
/* 1027 */       int canopyNum = this.canopyList.size();
/* 1028 */       if (canopyNum > 0) {
/* 1029 */         canopyNum--;
/*      */       }
/*      */       
/* 1032 */       if (slide) {
/* 1033 */         if (sx.length >= 3) {
/*      */           
/* 1035 */           Canopy c = new Canopy(this, toFloat(sx[0]), toFloat(sx[1]), toFloat(sx[2]), 0.0F, 0.0F, 0.0F, 90.0F, "canopy" + canopyNum, true);
/*      */           
/* 1037 */           this.canopyList.add(c);
/* 1038 */           if (canopyNum == 0) {
/* 1039 */             c = new Canopy(this, toFloat(sx[0]), toFloat(sx[1]), toFloat(sx[2]), 0.0F, 0.0F, 0.0F, 90.0F, "canopy", true);
/* 1040 */             this.canopyList.add(c);
/*      */           } 
/*      */         } 
/* 1043 */       } else if (sx.length >= 6) {
/* 1044 */         float mx = (sx.length >= 7) ? toFloat(sx[6], -180.0F, 180.0F) : 90.0F;
/* 1045 */         mx /= 90.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1053 */         Canopy c = new Canopy(this, toFloat(sx[0]), toFloat(sx[1]), toFloat(sx[2]), toFloat(sx[3]), toFloat(sx[4]), toFloat(sx[5]), mx, "canopy" + canopyNum, false);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1058 */         this.canopyList.add(c);
/* 1059 */         if (canopyNum == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1067 */           c = new Canopy(this, toFloat(sx[0]), toFloat(sx[1]), toFloat(sx[2]), toFloat(sx[3]), toFloat(sx[4]), toFloat(sx[5]), mx, "canopy", false);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1072 */           this.canopyList.add(c);
/*      */         } 
/*      */       } 
/* 1075 */     } else if (item.equalsIgnoreCase("AddPartLG") || item
/* 1076 */       .equalsIgnoreCase("AddPartSlideRotLG") || item
/* 1077 */       .equalsIgnoreCase("AddPartLGRev") || item
/* 1078 */       .equalsIgnoreCase("AddPartLGHatch")) {
/* 1079 */       String[] sxx = data.split("\\s*,\\s*");
/* 1080 */       if (!item.equalsIgnoreCase("AddPartSlideRotLG") && sxx.length >= 6) {
/* 1081 */         float maxRot = (sxx.length >= 7) ? toFloat(sxx[6], -180.0F, 180.0F) : 90.0F;
/* 1082 */         maxRot /= 90.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1094 */         LandingGear n = new LandingGear(this, toFloat(sxx[0]), toFloat(sxx[1]), toFloat(sxx[2]), toFloat(sxx[3]), toFloat(sxx[4]), toFloat(sxx[5]), "lg" + this.landingGear.size(), maxRot, item.equalsIgnoreCase("AddPartLgRev"), item.equalsIgnoreCase("AddPartLGHatch"));
/*      */         
/* 1096 */         if (sxx.length >= 8) {
/* 1097 */           n.enableRot2 = true;
/* 1098 */           n.maxRotFactor2 = (sxx.length >= 11) ? toFloat(sxx[10], -180.0F, 180.0F) : 90.0F;
/* 1099 */           n.maxRotFactor2 /= 90.0F;
/* 1100 */           n.rot2 = new Vec3d(toFloat(sxx[7]), toFloat(sxx[8]), toFloat(sxx[9]));
/*      */         } 
/*      */         
/* 1103 */         this.landingGear.add(n);
/*      */       } 
/*      */       
/* 1106 */       if (item.equalsIgnoreCase("AddPartSlideRotLG") && sxx.length >= 9) {
/* 1107 */         float maxRot = (sxx.length >= 10) ? toFloat(sxx[9], -180.0F, 180.0F) : 90.0F;
/* 1108 */         maxRot /= 90.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1117 */         LandingGear n = new LandingGear(this, toFloat(sxx[3]), toFloat(sxx[4]), toFloat(sxx[5]), toFloat(sxx[6]), toFloat(sxx[7]), toFloat(sxx[8]), "lg" + this.landingGear.size(), maxRot, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1122 */         n.slide = new Vec3d(toFloat(sxx[0]), toFloat(sxx[1]), toFloat(sxx[2]));
/* 1123 */         this.landingGear.add(n);
/*      */       } 
/* 1125 */     } else if (item.equalsIgnoreCase("AddPartThrottle")) {
/* 1126 */       String[] sxxx = data.split("\\s*,\\s*");
/* 1127 */       if (sxxx.length >= 7) {
/* 1128 */         float x = (sxxx.length >= 8) ? toFloat(sxxx[7]) : 0.0F;
/* 1129 */         float yx = (sxxx.length >= 9) ? toFloat(sxxx[8]) : 0.0F;
/* 1130 */         float z = (sxxx.length >= 10) ? toFloat(sxxx[9]) : 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1140 */         Throttle c = new Throttle(this, toFloat(sxxx[0]), toFloat(sxxx[1]), toFloat(sxxx[2]), toFloat(sxxx[3]), toFloat(sxxx[4]), toFloat(sxxx[5]), toFloat(sxxx[6]), "throttle" + this.partThrottle.size(), x, yx, z);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1145 */         this.partThrottle.add(c);
/*      */       } 
/* 1147 */     } else if (item.equalsIgnoreCase("AddPartRotation")) {
/* 1148 */       String[] sxxx = data.split("\\s*,\\s*");
/* 1149 */       if (sxxx.length >= 7) {
/* 1150 */         boolean always = (sxxx.length < 8 || toBool(sxxx[7]));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1161 */         RotPart c = new RotPart(this, toFloat(sxxx[0]), toFloat(sxxx[1]), toFloat(sxxx[2]), toFloat(sxxx[3]), toFloat(sxxx[4]), toFloat(sxxx[5]), toFloat(sxxx[6]), always, "rotpart" + this.partThrottle.size());
/*      */         
/* 1163 */         this.partRotPart.add(c);
/*      */       } 
/* 1165 */     } else if (item.compareTo("addpartcamera") == 0) {
/* 1166 */       String[] sxxx = data.split("\\s*,\\s*");
/* 1167 */       if (sxxx.length >= 3) {
/* 1168 */         boolean ys = (sxxx.length < 4 || toBool(sxxx[3]));
/* 1169 */         boolean ps = (sxxx.length >= 5 && toBool(sxxx[4]));
/*      */         
/* 1171 */         Camera c = new Camera(this, toFloat(sxxx[0]), toFloat(sxxx[1]), toFloat(sxxx[2]), 0.0F, -1.0F, 0.0F, "camera" + this.cameraList.size(), ys, ps);
/*      */         
/* 1173 */         this.cameraList.add(c);
/*      */       } 
/* 1175 */     } else if (item.equalsIgnoreCase("AddPartWheel")) {
/* 1176 */       String[] sxxx = splitParam(data);
/* 1177 */       if (sxxx.length >= 3) {
/* 1178 */         float rd = (sxxx.length >= 4) ? toFloat(sxxx[3], -1800.0F, 1800.0F) : 0.0F;
/* 1179 */         float rx = (sxxx.length >= 7) ? toFloat(sxxx[4]) : 0.0F;
/* 1180 */         float ry = (sxxx.length >= 7) ? toFloat(sxxx[5]) : 1.0F;
/* 1181 */         float rz = (sxxx.length >= 7) ? toFloat(sxxx[6]) : 0.0F;
/* 1182 */         float px = (sxxx.length >= 10) ? toFloat(sxxx[7]) : toFloat(sxxx[0]);
/* 1183 */         float py = (sxxx.length >= 10) ? toFloat(sxxx[8]) : toFloat(sxxx[1]);
/* 1184 */         float pz = (sxxx.length >= 10) ? toFloat(sxxx[9]) : toFloat(sxxx[2]);
/* 1185 */         this.partWheel
/* 1186 */           .add(new PartWheel(this, 
/*      */               
/* 1188 */               toFloat(sxxx[0]), toFloat(sxxx[1]), toFloat(sxxx[2]), rx, ry, rz, rd, px, py, pz, "wheel" + this.partWheel.size()));
/*      */       }
/*      */     
/*      */     }
/* 1192 */     else if (item.equalsIgnoreCase("AddPartSteeringWheel")) {
/* 1193 */       String[] sxxx = splitParam(data);
/* 1194 */       if (sxxx.length >= 7) {
/* 1195 */         this.partSteeringWheel
/* 1196 */           .add(new PartWheel(this, 
/*      */ 
/*      */               
/* 1199 */               toFloat(sxxx[0]), 
/* 1200 */               toFloat(sxxx[1]), 
/* 1201 */               toFloat(sxxx[2]), 
/* 1202 */               toFloat(sxxx[3]), 
/* 1203 */               toFloat(sxxx[4]), 
/* 1204 */               toFloat(sxxx[5]), 
/* 1205 */               toFloat(sxxx[6]), "steering_wheel" + this.partSteeringWheel
/* 1206 */               .size()));
/*      */       
/*      */       }
/*      */     }
/* 1210 */     else if (item.equalsIgnoreCase("AddTrackRoller")) {
/* 1211 */       String[] sxxx = splitParam(data);
/* 1212 */       if (sxxx.length >= 3) {
/* 1213 */         this.partTrackRoller
/* 1214 */           .add(new TrackRoller(this, 
/*      */               
/* 1216 */               toFloat(sxxx[0]), toFloat(sxxx[1]), toFloat(sxxx[2]), "track_roller" + this.partTrackRoller.size()));
/*      */       
/*      */       }
/*      */     }
/* 1220 */     else if (item.equalsIgnoreCase("AddCrawlerTrack")) {
/* 1221 */       this.partCrawlerTrack.add(createCrawlerTrack(data, "crawler_track" + this.partCrawlerTrack.size()));
/* 1222 */     } else if (item.equalsIgnoreCase("PivotTurnThrottle")) {
/* 1223 */       this.pivotTurnThrottle = toFloat(data, 0.0F, 1.0F);
/* 1224 */     } else if (item.equalsIgnoreCase("TrackRollerRot")) {
/* 1225 */       this.trackRollerRot = toFloat(data, -10000.0F, 10000.0F);
/* 1226 */     } else if (item.equalsIgnoreCase("PartWheelRot")) {
/* 1227 */       this.partWheelRot = toFloat(data, -10000.0F, 10000.0F);
/* 1228 */     } else if (item.compareTo("camerazoom") == 0) {
/* 1229 */       this.cameraZoom = toInt(data, 1, 10);
/* 1230 */     } else if (item.equalsIgnoreCase("DefaultFreelook")) {
/* 1231 */       this.defaultFreelook = toBool(data);
/* 1232 */     } else if (item.equalsIgnoreCase("BoundingBox")) {
/* 1233 */       String[] sxxx = data.split("\\s*,\\s*");
/* 1234 */       if (sxxx.length >= 5) {
/* 1235 */         float df = (sxxx.length >= 6) ? toFloat(sxxx[5]) : 1.0F;
/*      */         
/* 1237 */         MCH_BoundingBox c = new MCH_BoundingBox(toFloat(sxxx[0]), toFloat(sxxx[1]), toFloat(sxxx[2]), toFloat(sxxx[3]), toFloat(sxxx[4]), df);
/*      */         
/* 1239 */         this.extraBoundingBox.add(c);
/* 1240 */         if ((c.getBoundingBox()).field_72337_e > this.markerHeight) {
/* 1241 */           this.markerHeight = (float)(c.getBoundingBox()).field_72337_e;
/*      */         }
/*      */         
/* 1244 */         this.markerWidth = (float)Math.max(this.markerWidth, Math.abs((c.getBoundingBox()).field_72336_d) / 2.0D);
/* 1245 */         this.markerWidth = (float)Math.max(this.markerWidth, Math.abs((c.getBoundingBox()).field_72340_a) / 2.0D);
/* 1246 */         this.markerWidth = (float)Math.max(this.markerWidth, Math.abs((c.getBoundingBox()).field_72334_f) / 2.0D);
/* 1247 */         this.markerWidth = (float)Math.max(this.markerWidth, Math.abs((c.getBoundingBox()).field_72339_c) / 2.0D);
/* 1248 */         this.bbZmin = (float)Math.min(this.bbZmin, (c.getBoundingBox()).field_72339_c);
/* 1249 */         this.bbZmax = (float)Math.min(this.bbZmax, (c.getBoundingBox()).field_72334_f);
/*      */       } 
/* 1251 */     } else if (item.equalsIgnoreCase("RotorSpeed")) {
/* 1252 */       this.rotorSpeed = toFloat(data, -10000.0F, 10000.0F);
/* 1253 */       if (this.rotorSpeed > 0.01D) {
/* 1254 */         this.rotorSpeed = (float)(this.rotorSpeed - 0.01D);
/*      */       }
/*      */       
/* 1257 */       if (this.rotorSpeed < -0.01D) {
/* 1258 */         this.rotorSpeed = (float)(this.rotorSpeed + 0.01D);
/*      */       }
/* 1260 */     } else if (item.equalsIgnoreCase("OnGroundPitchFactor")) {
/* 1261 */       this.onGroundPitchFactor = toFloat(data, 0.0F, 180.0F);
/* 1262 */     } else if (item.equalsIgnoreCase("OnGroundRollFactor")) {
/* 1263 */       this.onGroundRollFactor = toFloat(data, 0.0F, 180.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   public CrawlerTrack createCrawlerTrack(String data, String name) {
/* 1268 */     String[] s = splitParam(data);
/* 1269 */     int PC = s.length - 3;
/* 1270 */     boolean REV = toBool(s[0]);
/* 1271 */     float LEN = toFloat(s[1], 0.001F, 1000.0F) * 0.9F;
/* 1272 */     float Z = toFloat(s[2]);
/* 1273 */     if (PC < 4) {
/* 1274 */       return null;
/*      */     }
/* 1276 */     double[] cx = new double[PC];
/* 1277 */     double[] cy = new double[PC];
/*      */     
/* 1279 */     for (int i = 0; i < PC; i++) {
/* 1280 */       int idx = !REV ? i : (PC - i - 1);
/* 1281 */       String[] xy = splitParamSlash(s[3 + idx]);
/* 1282 */       cx[i] = toFloat(xy[0]);
/* 1283 */       cy[i] = toFloat(xy[1]);
/*      */     } 
/*      */     
/* 1286 */     List<CrawlerTrackPrm> lp = new ArrayList<>();
/* 1287 */     lp.add(new CrawlerTrackPrm(this, (float)cx[0], (float)cy[0]));
/* 1288 */     double dist = 0.0D;
/*      */     int j;
/* 1290 */     for (j = 0; j < PC; j++) {
/* 1291 */       double x = cx[(j + 1) % PC] - cx[j];
/* 1292 */       double y = cy[(j + 1) % PC] - cy[j];
/* 1293 */       dist += Math.sqrt(x * x + y * y);
/* 1294 */       double dist2 = dist;
/*      */       
/* 1296 */       for (int k = 1; dist >= LEN; k++) {
/* 1297 */         lp.add(new CrawlerTrackPrm(this, (float)(cx[j] + x * (LEN * k) / dist2), (float)(cy[j] + y * (LEN * k) / dist2)));
/* 1298 */         dist -= LEN;
/*      */       } 
/*      */     } 
/*      */     
/* 1302 */     for (j = 0; j < lp.size(); j++) {
/* 1303 */       CrawlerTrackPrm pp = lp.get((j + lp.size() - 1) % lp.size());
/* 1304 */       CrawlerTrackPrm cp = lp.get(j);
/* 1305 */       CrawlerTrackPrm np = lp.get((j + 1) % lp.size());
/* 1306 */       float pr = (float)(Math.atan2((pp.x - cp.x), (pp.y - cp.y)) * 180.0D / Math.PI);
/* 1307 */       float nr = (float)(Math.atan2((np.x - cp.x), (np.y - cp.y)) * 180.0D / Math.PI);
/* 1308 */       float ppr = (pr + 360.0F) % 360.0F;
/* 1309 */       float nnr = nr + 180.0F;
/* 1310 */       if ((nnr < ppr - 0.3D || nnr > ppr + 0.3D) && nnr - ppr < 100.0F && nnr - ppr > -100.0F) {
/* 1311 */         nnr = (nnr + ppr) / 2.0F;
/*      */       }
/*      */       
/* 1314 */       cp.r = nnr;
/*      */     } 
/*      */     
/* 1317 */     CrawlerTrack c = new CrawlerTrack(this, name);
/* 1318 */     c.len = LEN;
/* 1319 */     c.cx = cx;
/* 1320 */     c.cy = cy;
/* 1321 */     c.lp = lp;
/* 1322 */     c.z = Z;
/* 1323 */     c.side = (Z >= 0.0F) ? 1 : 0;
/* 1324 */     return c;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getTextureName() {
/* 1329 */     String s = this.textureNameList.get(this.textureCount);
/* 1330 */     this.textureCount = (this.textureCount + 1) % this.textureNameList.size();
/* 1331 */     return s;
/*      */   }
/*      */   
/*      */   public String getNextTextureName(String base) {
/* 1335 */     if (this.textureNameList.size() >= 2) {
/* 1336 */       for (int i = 0; i < this.textureNameList.size(); i++) {
/* 1337 */         String s = this.textureNameList.get(i);
/* 1338 */         if (s.equalsIgnoreCase(base)) {
/* 1339 */           i = (i + 1) % this.textureNameList.size();
/* 1340 */           return this.textureNameList.get(i);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1345 */     return base;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canReloadItem(String item) {
/* 1350 */     String[] ignoreItems = getCannotReloadItem();
/*      */     
/* 1352 */     for (String s : ignoreItems) {
/* 1353 */       if (s.equalsIgnoreCase(item)) {
/* 1354 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1358 */     return true;
/*      */   }
/*      */   public abstract String getDirectoryName();
/*      */   public abstract String getKindName();
/*      */   public abstract String getDefaultHudName(int paramInt);
/*      */   public static class Camera extends DrawnPart { public final boolean yawSync;
/*      */     
/*      */     public Camera(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, String name, boolean ys, boolean ps) {
/* 1366 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1367 */       this.yawSync = ys;
/* 1368 */       this.pitchSync = ps;
/*      */     }
/*      */     
/*      */     public final boolean pitchSync; }
/*      */   
/*      */   public static class CameraPosition { public final Vec3d pos;
/*      */     public final boolean fixRot;
/*      */     public final float yaw;
/*      */     public final float pitch;
/*      */     
/*      */     public CameraPosition(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d vec3, boolean fixRot, float yaw, float pitch) {
/* 1379 */       this.pos = vec3;
/* 1380 */       this.fixRot = fixRot;
/* 1381 */       this.yaw = yaw;
/* 1382 */       this.pitch = pitch;
/*      */     }
/*      */     
/*      */     public CameraPosition(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d vec3) {
/* 1386 */       this(paramMCH_AircraftInfo, vec3, false, 0.0F, 0.0F);
/*      */     }
/*      */     
/*      */     public CameraPosition(MCH_AircraftInfo paramMCH_AircraftInfo) {
/* 1390 */       this(paramMCH_AircraftInfo, new Vec3d(0.0D, 0.0D, 0.0D));
/*      */     } }
/*      */ 
/*      */   
/*      */   public static class Canopy extends DrawnPart {
/*      */     public final float maxRotFactor;
/*      */     public final boolean isSlide;
/*      */     
/*      */     public Canopy(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, float mr, String name, boolean slide) {
/* 1399 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1400 */       this.maxRotFactor = mr;
/* 1401 */       this.isSlide = slide;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class CrawlerTrack extends DrawnPart {
/* 1406 */     public float len = 0.35F;
/*      */     public double[] cx;
/*      */     public double[] cy;
/*      */     public List<MCH_AircraftInfo.CrawlerTrackPrm> lp;
/*      */     public float z;
/*      */     public int side;
/*      */     
/*      */     public CrawlerTrack(MCH_AircraftInfo paramMCH_AircraftInfo, String name) {
/* 1414 */       super(paramMCH_AircraftInfo, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, name);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class CrawlerTrackPrm {
/*      */     final float x;
/*      */     final float y;
/*      */     float nx;
/*      */     float ny;
/*      */     float r;
/*      */     
/*      */     public CrawlerTrackPrm(MCH_AircraftInfo paramMCH_AircraftInfo, float x, float y) {
/* 1426 */       this.x = x;
/* 1427 */       this.y = y;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class DrawnPart {
/*      */     public final Vec3d pos;
/*      */     public final Vec3d rot;
/*      */     public final String modelName;
/*      */     public _IModelCustom model;
/*      */     
/*      */     public DrawnPart(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, String name) {
/* 1438 */       this.pos = new Vec3d(px, py, pz);
/* 1439 */       this.rot = new Vec3d(rx, ry, rz);
/* 1440 */       this.modelName = name;
/* 1441 */       this.model = null;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Flare {
/* 1446 */     public int[] types = new int[0];
/* 1447 */     public Vec3d pos = new Vec3d(0.0D, 0.0D, 0.0D);
/*      */     
/*      */     public Flare(MCH_AircraftInfo paramMCH_AircraftInfo) {}
/*      */   }
/*      */   
/*      */   public static class Hatch
/*      */     extends DrawnPart {
/*      */     public final float maxRotFactor;
/*      */     public final float maxRot;
/*      */     public final boolean isSlide;
/*      */     
/*      */     public Hatch(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, float mr, String name, boolean slide) {
/* 1459 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1460 */       this.maxRot = mr;
/* 1461 */       this.maxRotFactor = this.maxRot / 90.0F;
/* 1462 */       this.isSlide = slide;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class LandingGear extends DrawnPart {
/*      */     public final float maxRotFactor;
/*      */     public final boolean reverse;
/*      */     public final boolean hatch;
/* 1470 */     public Vec3d slide = null;
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean enableRot2;
/*      */ 
/*      */ 
/*      */     
/*      */     public Vec3d rot2;
/*      */ 
/*      */ 
/*      */     
/*      */     public float maxRotFactor2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LandingGear(MCH_AircraftInfo paramMCH_AircraftInfo, float x, float y, float z, float rx, float ry, float rz, String model, float maxRotF, boolean rev, boolean isHatch) {
/* 1488 */       super(paramMCH_AircraftInfo, x, y, z, rx, ry, rz, model);
/* 1489 */       this.maxRotFactor = maxRotF;
/* 1490 */       this.enableRot2 = false;
/* 1491 */       this.rot2 = new Vec3d(0.0D, 0.0D, 0.0D);
/* 1492 */       this.maxRotFactor2 = 0.0F;
/* 1493 */       this.reverse = rev;
/* 1494 */       this.hatch = isHatch;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class PartWeapon
/*      */     extends DrawnPart
/*      */   {
/*      */     public final String[] name;
/*      */ 
/*      */     
/*      */     public final boolean rotBarrel;
/*      */ 
/*      */     
/*      */     public final boolean isMissile;
/*      */ 
/*      */     
/*      */     public final boolean hideGM;
/*      */ 
/*      */     
/*      */     public final boolean yaw;
/*      */     
/*      */     public final boolean pitch;
/*      */     
/*      */     public final float recoilBuf;
/*      */     
/*      */     public final boolean turret;
/*      */     
/*      */     public final List<MCH_AircraftInfo.PartWeaponChild> child;
/*      */ 
/*      */     
/*      */     public PartWeapon(MCH_AircraftInfo paramMCH_AircraftInfo, String[] name, boolean rotBrl, boolean missile, boolean hgm, boolean y, boolean p, float px, float py, float pz, String modelName, float rx, float ry, float rz, float rb, boolean turret) {
/* 1527 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, modelName);
/* 1528 */       this.name = name;
/* 1529 */       this.rotBarrel = rotBrl;
/* 1530 */       this.isMissile = missile;
/* 1531 */       this.hideGM = hgm;
/* 1532 */       this.yaw = y;
/* 1533 */       this.pitch = p;
/* 1534 */       this.recoilBuf = rb;
/* 1535 */       this.child = new ArrayList<>();
/* 1536 */       this.turret = turret;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class PartWeaponChild
/*      */     extends DrawnPart
/*      */   {
/*      */     public final String[] name;
/*      */ 
/*      */     
/*      */     public final boolean yaw;
/*      */ 
/*      */     
/*      */     public final boolean pitch;
/*      */ 
/*      */     
/*      */     public final float recoilBuf;
/*      */ 
/*      */ 
/*      */     
/*      */     public PartWeaponChild(MCH_AircraftInfo paramMCH_AircraftInfo, String[] name, boolean y, boolean p, float px, float py, float pz, String modelName, float rx, float ry, float rz, float rb) {
/* 1560 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, modelName);
/* 1561 */       this.name = name;
/* 1562 */       this.yaw = y;
/* 1563 */       this.pitch = p;
/* 1564 */       this.recoilBuf = rb;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class PartWheel
/*      */     extends DrawnPart
/*      */   {
/*      */     final float rotDir;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final Vec3d pos2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PartWheel(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, float rd, float px2, float py2, float pz2, String name) {
/* 1586 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1587 */       this.rotDir = rd;
/* 1588 */       this.pos2 = new Vec3d(px2, py2, pz2);
/*      */     }
/*      */     
/*      */     public PartWheel(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, float rd, String name) {
/* 1592 */       this(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, rd, px, py, pz, name);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class ParticleSplash {
/*      */     public final int num;
/*      */     public final float acceleration;
/*      */     public final float size;
/*      */     public final Vec3d pos;
/*      */     public final int age;
/*      */     public final float motionY;
/*      */     public final float gravity;
/*      */     
/*      */     public ParticleSplash(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d v, int nm, float siz, float acc, int ag, float my, float gr) {
/* 1606 */       this.num = nm;
/* 1607 */       this.pos = v;
/* 1608 */       this.size = siz;
/* 1609 */       this.acceleration = acc;
/* 1610 */       this.age = ag;
/* 1611 */       this.motionY = my;
/* 1612 */       this.gravity = gr;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RepellingHook {
/*      */     final Vec3d pos;
/*      */     final int interval;
/*      */     
/*      */     public RepellingHook(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d pos, int inv) {
/* 1621 */       this.pos = pos;
/* 1622 */       this.interval = inv;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RideRack {
/*      */     public final String name;
/*      */     public final int rackID;
/*      */     
/*      */     public RideRack(MCH_AircraftInfo paramMCH_AircraftInfo, String n, int id) {
/* 1631 */       this.name = n;
/* 1632 */       this.rackID = id;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RotPart extends DrawnPart {
/*      */     public final float rotSpeed;
/*      */     public final boolean rotAlways;
/*      */     
/*      */     public RotPart(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, float mr, boolean a, String name) {
/* 1641 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1642 */       this.rotSpeed = mr;
/* 1643 */       this.rotAlways = a;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class SearchLight
/*      */   {
/*      */     public final int colorStart;
/*      */     public final int colorEnd;
/*      */     public final Vec3d pos;
/*      */     public final float height;
/*      */     public final float width;
/*      */     public final float angle;
/*      */     public final boolean fixDir;
/*      */     public final float yaw;
/*      */     public final float pitch;
/*      */     public final boolean steering;
/*      */     public final float stRot;
/*      */     
/*      */     public SearchLight(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d pos, int cs, int ce, float h, float w, boolean fix, float y, float p, boolean st, float stRot) {
/* 1663 */       this.colorStart = cs;
/* 1664 */       this.colorEnd = ce;
/* 1665 */       this.pos = pos;
/* 1666 */       this.height = h;
/* 1667 */       this.width = w;
/* 1668 */       this.angle = (float)(Math.atan2((w / 2.0F), h) * 180.0D / Math.PI);
/* 1669 */       this.fixDir = fix;
/* 1670 */       this.steering = st;
/* 1671 */       this.yaw = y;
/* 1672 */       this.pitch = p;
/* 1673 */       this.stRot = stRot;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Throttle
/*      */     extends DrawnPart
/*      */   {
/*      */     public final Vec3d slide;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final float rot2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Throttle(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, float rot, String name, float px2, float py2, float pz2) {
/* 1695 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1696 */       this.rot2 = rot;
/* 1697 */       this.slide = new Vec3d(px2, py2, pz2);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class TrackRoller extends DrawnPart {
/*      */     final int side;
/*      */     
/*      */     public TrackRoller(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, String name) {
/* 1705 */       super(paramMCH_AircraftInfo, px, py, pz, 0.0F, 0.0F, 0.0F, name);
/* 1706 */       this.side = (px >= 0.0F) ? 1 : 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Weapon
/*      */   {
/*      */     public final Vec3d pos;
/*      */ 
/*      */     
/*      */     public final float yaw;
/*      */     
/*      */     public final float pitch;
/*      */     
/*      */     public final boolean canUsePilot;
/*      */     
/*      */     public final int seatID;
/*      */     
/*      */     public final float defaultYaw;
/*      */     
/*      */     public final float minYaw;
/*      */     
/*      */     public final float maxYaw;
/*      */     
/*      */     public final float minPitch;
/*      */     
/*      */     public final float maxPitch;
/*      */     
/*      */     public final boolean turret;
/*      */ 
/*      */     
/*      */     public Weapon(MCH_AircraftInfo paramMCH_AircraftInfo, float x, float y, float z, float yaw, float pitch, boolean canPirot, int seatId, float defy, float mny, float mxy, float mnp, float mxp, boolean turret) {
/* 1739 */       this.pos = new Vec3d(x, y, z);
/* 1740 */       this.yaw = yaw;
/* 1741 */       this.pitch = pitch;
/* 1742 */       this.canUsePilot = canPirot;
/* 1743 */       this.seatID = seatId;
/* 1744 */       this.defaultYaw = defy;
/* 1745 */       this.minYaw = mny;
/* 1746 */       this.maxYaw = mxy;
/* 1747 */       this.minPitch = mnp;
/* 1748 */       this.maxPitch = mxp;
/* 1749 */       this.turret = turret;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WeaponBay
/*      */     extends DrawnPart
/*      */   {
/*      */     public final float maxRotFactor;
/*      */     public final boolean isSlide;
/*      */     private final String weaponName;
/*      */     public Integer[] weaponIds;
/*      */     
/*      */     public WeaponBay(MCH_AircraftInfo paramMCH_AircraftInfo, String wn, float px, float py, float pz, float rx, float ry, float rz, float mr, String name, boolean slide) {
/* 1762 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1763 */       this.maxRotFactor = mr;
/* 1764 */       this.isSlide = slide;
/* 1765 */       this.weaponName = wn;
/* 1766 */       this.weaponIds = new Integer[0];
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WeaponSet {
/*      */     public final String type;
/*      */     public final ArrayList<MCH_AircraftInfo.Weapon> weapons;
/*      */     
/*      */     public WeaponSet(MCH_AircraftInfo paramMCH_AircraftInfo, String t) {
/* 1775 */       this.type = t;
/* 1776 */       this.weapons = new ArrayList<>();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Wheel {
/*      */     public final float size;
/*      */     public final Vec3d pos;
/*      */     
/*      */     public Wheel(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d v, float sz) {
/* 1785 */       this.pos = v;
/* 1786 */       this.size = sz;
/*      */     }
/*      */     
/*      */     public Wheel(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d v) {
/* 1790 */       this(paramMCH_AircraftInfo, v, 1.0F);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_AircraftInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */