/*      */ package com.norwood.mcheli.aircraft;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import com.norwood.mcheli.helper.entity.IEntitySinglePassenger;
import com.norwood.mcheli.helper.entity.ITargetMarkerObject;
import com.norwood.mcheli.weapon.MCH_IEntityLockChecker;
import net.minecraft.entity.MoverType;
/*      */ import com.norwood.mcheli.MCH_Camera;
/*      */ import com.norwood.mcheli.MCH_Config;
/*      */ import com.norwood.mcheli.MCH_Explosion;
/*      */ import com.norwood.mcheli.MCH_Lib;
/*      */ import com.norwood.mcheli.MCH_LowPassFilterFloat;
/*      */ import com.norwood.mcheli.MCH_MOD;
/*      */ import com.norwood.mcheli.MCH_Math;
/*      */ import com.norwood.mcheli.MCH_Queue;
/*      */ import com.norwood.mcheli.MCH_Vector2;
/*      */ import com.norwood.mcheli.MCH_ViewEntityDummy;
/*      */ import com.norwood.mcheli.chain.MCH_EntityChain;
/*      */ import com.norwood.mcheli.flare.MCH_Flare;
/*      */ import com.norwood.mcheli.helper.MCH_CriteriaTriggers;
/*      */ import com.norwood.mcheli.parachute.MCH_EntityParachute;
/*      */ import com.norwood.mcheli.particles.MCH_ParticleParam;
/*      */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*      */ import com.norwood.mcheli.uav.MCH_EntityUavStation;
/*      */ import com.norwood.mcheli.weapon.MCH_EntityTvMissile;
/*      */ import com.norwood.mcheli.weapon.MCH_WeaponBase;
/*      */ import com.norwood.mcheli.weapon.MCH_WeaponCreator;
/*      */ import com.norwood.mcheli.weapon.MCH_WeaponDummy;
/*      */ import com.norwood.mcheli.weapon.MCH_WeaponInfo;
/*      */ import com.norwood.mcheli.weapon.MCH_WeaponParam;
/*      */ import com.norwood.mcheli.weapon.MCH_WeaponSet;
/*      */ import com.norwood.mcheli.wrapper.W_AxisAlignedBB;
/*      */ import com.norwood.mcheli.wrapper.W_Block;
/*      */ import com.norwood.mcheli.wrapper.W_Blocks;
/*      */ import com.norwood.mcheli.wrapper.W_Entity;
/*      */ import com.norwood.mcheli.wrapper.W_EntityContainer;
/*      */ import com.norwood.mcheli.wrapper.W_EntityPlayer;
/*      */ import com.norwood.mcheli.wrapper.W_EntityRenderer;
/*      */ import com.norwood.mcheli.wrapper.W_Item;
/*      */ import com.norwood.mcheli.wrapper.W_Lib;
/*      */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.MobEffects;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.minecraftforge.fml.relauncher.Side;
/*      */ import net.minecraftforge.fml.relauncher.SideOnly;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ 
/*      */ public abstract class MCH_EntityAircraft extends W_EntityContainer implements MCH_IEntityLockChecker, MCH_IEntityCanRideAircraft, IEntityAdditionalSpawnData, IEntitySinglePassenger, ITargetMarkerObject {
/*      */   public static final float Y_OFFSET = 0.35F;
/*      */   public static final byte LIMIT_GROUND_PITCH = 40;
/*   80 */   protected static final DataParameter<Integer> PART_STAT = EntityDataManager.createKey(MCH_EntityAircraft.class, DataSerializers.VARINT); public static final byte LIMIT_GROUND_ROLL = 40; public static final int CAMERA_PITCH_MIN = -30; public static final int CAMERA_PITCH_MAX = 70;
/*      */   protected static final int PART_ID_CANOPY = 0;
/*      */   protected static final int PART_ID_NOZZLE = 1;
/*      */   protected static final int PART_ID_LANDINGGEAR = 2;
/*      */   protected static final int PART_ID_WING = 3;
/*      */   protected static final int PART_ID_HATCH = 4;
/*   86 */   private static final DataParameter<Integer> DAMAGE = EntityDataManager.createKey(MCH_EntityAircraft.class, DataSerializers.VARINT);
/*   87 */   private static final DataParameter<String> ID_TYPE = EntityDataManager.createKey(MCH_EntityAircraft.class, DataSerializers.STRING);
/*   88 */   private static final DataParameter<String> TEXTURE_NAME = EntityDataManager.createKey(MCH_EntityAircraft.class, DataSerializers.STRING);
/*   89 */   private static final DataParameter<Integer> UAV_STATION = EntityDataManager.createKey(MCH_EntityAircraft.class, DataSerializers.VARINT);
/*   90 */   private static final DataParameter<Integer> STATUS = EntityDataManager.createKey(MCH_EntityAircraft.class, DataSerializers.VARINT);
/*   91 */   private static final DataParameter<Integer> USE_WEAPON = EntityDataManager.createKey(MCH_EntityAircraft.class, DataSerializers.VARINT);
/*   92 */   private static final DataParameter<Integer> FUEL = EntityDataManager.createKey(MCH_EntityAircraft.class, DataSerializers.VARINT);
/*   93 */   private static final DataParameter<Integer> ROT_ROLL = EntityDataManager.createKey(MCH_EntityAircraft.class, DataSerializers.VARINT);
/*   94 */   private static final DataParameter<String> COMMAND = EntityDataManager.createKey(MCH_EntityAircraft.class, DataSerializers.STRING);
/*   95 */   private static final DataParameter<Integer> THROTTLE = EntityDataManager.createKey(MCH_EntityAircraft.class, DataSerializers.VARINT);
/*   96 */   private static final MCH_EntitySeat[] seatsDummy = new MCH_EntitySeat[0];
/*      */   public final MCH_MissileDetector missileDetector;
/*   98 */   public final HashMap<Entity, Integer> noCollisionEntities = new HashMap<>();
/*      */   protected final MCH_SoundUpdater soundUpdater;
/*      */   private final MCH_AircraftInventory inventory;
/*      */   public boolean isRequestedSyncStatus;
/*      */   public boolean keepOnRideRotation;
/*      */   public boolean aircraftRollRev;
/*      */   public boolean aircraftRotChanged;
/*      */   public float rotationRoll;
/*      */   public float prevRotationRoll;
/*      */   public double currentSpeed;
/*      */   public final int currentFuel;
/*  109 */   public float throttleBack = 0.0F;
/*      */   public double beforeHoverThrottle;
/*  111 */   public int waitMountEntity = 0;
/*      */   public boolean throttleUp = false;
/*      */   public boolean throttleDown = false;
/*      */   public boolean moveLeft = false;
/*      */   public boolean moveRight = false;
/*      */   public final MCH_LowPassFilterFloat lowPassPartialTicks;
/*      */   public float lastRiderYaw;
/*      */   public float prevLastRiderYaw;
/*      */   public float lastRiderPitch;
/*      */   public float prevLastRiderPitch;
/*  121 */   public final List<UnmountReserve> listUnmountReserve = new ArrayList<>();
/*      */   public final MCH_Camera camera;
/*  123 */   public int serverNoMoveCount = 0;
/*      */   public int repairCount;
/*      */   public int beforeDamageTaken;
/*      */   public int timeSinceHit;
/*      */   public float rotDestroyedYaw;
/*      */   public float rotDestroyedPitch;
/*      */   public float rotDestroyedRoll;
/*      */   public int damageSinceDestroyed;
/*      */   public boolean isFirstDamageSmoke = true;
/*  132 */   public Vec3d[] prevDamageSmokePos = new Vec3d[0];
/*      */   public boolean cs_dismountAll;
/*      */   public boolean cs_heliAutoThrottleDown;
/*      */   public boolean cs_planeAutoThrottleDown;
/*      */   public boolean cs_tankAutoThrottleDown;
/*      */   public MCH_Parts partHatch;
/*      */   public MCH_Parts partCanopy;
/*      */   public MCH_Parts partLandingGear;
/*      */   public double prevRidingEntityPosX;
/*      */   public double prevRidingEntityPosY;
/*      */   public double prevRidingEntityPosZ;
/*      */   public boolean canRideRackStatus;
/*      */   public MCH_BoundingBox[] extraBoundingBox;
/*      */   public float lastBBDamageFactor;
/*      */   public WeaponBay[] weaponBays;
/*      */   public float[] rotPartRotation;
/*      */   public float[] prevRotPartRotation;
/*  149 */   public final float[] rotCrawlerTrack = new float[2];
/*  150 */   public final float[] prevRotCrawlerTrack = new float[2];
/*  151 */   public final float[] throttleCrawlerTrack = new float[2];
/*  152 */   public final float[] rotTrackRoller = new float[2];
/*  153 */   public final float[] prevRotTrackRoller = new float[2];
/*  154 */   public float rotWheel = 0.0F;
/*  155 */   public float prevRotWheel = 0.0F;
/*  156 */   public float rotYawWheel = 0.0F;
/*  157 */   public float prevRotYawWheel = 0.0F;
/*  158 */   public float ropesLength = 0.0F;
/*      */   public float lastSearchLightYaw;
/*      */   public float lastSearchLightPitch;
/*  161 */   public float rotLightHatch = 0.0F;
/*  162 */   public float prevRotLightHatch = 0.0F;
/*  163 */   public int recoilCount = 0;
/*  164 */   public float recoilYaw = 0.0F;
/*  165 */   public float recoilValue = 0.0F;
/*  166 */   public int brightnessHigh = 240;
/*  167 */   public int brightnessLow = 240;
/*  168 */   public float thirdPersonDist = 4.0F;
/*  169 */   public Entity lastAttackedEntity = null;
/*      */   protected double velocityX;
/*      */   protected double velocityY;
/*      */   protected double velocityZ;
/*      */   protected int aircraftPosRotInc;
/*      */   protected double aircraftX;
/*      */   protected double aircraftY;
/*      */   protected double aircraftZ;
/*      */   protected double aircraftYaw;
/*      */   protected double aircraftPitch;
/*      */   protected MCH_WeaponSet[] weapons;
/*      */   protected int[] currentWeaponID;
/*      */   protected final MCH_WeaponSet dummyWeapon;
/*      */   protected int useWeaponStat;
/*      */   protected int hitStatus;
/*      */   protected Entity lastRiddenByEntity;
/*      */   protected Entity lastRidingEntity;
/*      */   protected boolean isGunnerMode = false;
/*      */   protected boolean isGunnerModeOtherSeat = false;
/*      */   protected boolean isGunnerFreeLookMode = false;
/*      */   private MCH_AircraftInfo acInfo;
/*      */   private int commonStatus;
/*      */   private Entity[] partEntities;
/*      */   private final MCH_EntityHitBox pilotSeat;
/*      */   private MCH_EntitySeat[] seats;
/*      */   private MCH_SeatInfo[] seatsInfo;
/*      */   private String commonUniqueId;
/*      */   private int seatSearchCount;
/*      */   private double currentThrottle;
/*      */   private double prevCurrentThrottle;
/*      */   private final MCH_Radar entityRadar;
/*      */   private int radarRotate;
/*      */   private final MCH_Flare flareDv;
/*      */   private int currentFlareIndex;
/*      */   private int countOnUpdate;
/*      */   private MCH_EntityChain towChainEntity;
/*      */   private MCH_EntityChain towedChainEntity;
/*      */   private int cameraId;
/*      */   private boolean isHoveringMode = false;
/*      */   private MCH_EntityTvMissile TVmissile;
/*      */   private int despawnCount;
/*      */   private MCH_EntityUavStation uavStation;
/*      */   private int modeSwitchCooldown;
/*      */   private double fuelConsumption;
/*      */   private int fuelSuppliedCount;
/*      */   private int supplyAmmoWait;
/*      */   private boolean beforeSupplyAmmo;
/*      */   private boolean isParachuting;
/*      */   private final MCH_Queue<Vec3d> prevPosition;
/*      */   private int tickRepelling;
/*      */   private int lastUsedRopeIndex;
/*      */   private boolean dismountedUserCtrl;
/*      */   private double lastCalcLandInDistanceCount;
/*      */   private double lastLandInDistance;
/*      */   private boolean switchSeat = false;
/*      */   
/*      */   public MCH_EntityAircraft(World world) {
/*  226 */     super(world);
/*  227 */     MCH_Lib.DbgLog(world, "MCH_EntityAircraft : " + this, new Object[0]);
/*  228 */     this.isRequestedSyncStatus = false;
/*  229 */     setAcInfo((MCH_AircraftInfo)null);
/*  230 */     this.dropContentsWhenDead = false;
/*  231 */     this.ignoreFrustumCheck = true;
/*  232 */     this.flareDv = new MCH_Flare(world, this);
/*  233 */     this.currentFlareIndex = 0;
/*  234 */     this.entityRadar = new MCH_Radar(world);
/*  235 */     this.radarRotate = 0;
/*  236 */     this.currentWeaponID = new int[0];
/*  237 */     this.aircraftPosRotInc = 0;
/*  238 */     this.aircraftX = 0.0D;
/*  239 */     this.aircraftY = 0.0D;
/*  240 */     this.aircraftZ = 0.0D;
/*  241 */     this.aircraftYaw = 0.0D;
/*  242 */     this.aircraftPitch = 0.0D;
/*  243 */     this.currentSpeed = 0.0D;
/*  244 */     setCurrentThrottle(0.0D);
/*  245 */     this.currentFuel = 0;
/*  246 */     this.cs_dismountAll = false;
/*  247 */     this.cs_heliAutoThrottleDown = true;
/*  248 */     this.cs_planeAutoThrottleDown = false;
/*  249 */     this._renderDistanceWeight = 2.0D * MCH_Config.RenderDistanceWeight.prmDouble;
/*  250 */     setCommonUniqueId("");
/*  251 */     this.seatSearchCount = 0;
/*  252 */     this.seatsInfo = null;
/*  253 */     this.seats = new MCH_EntitySeat[0];
/*  254 */     this.pilotSeat = new MCH_EntityHitBox(world, this, 1.0F, 1.0F);
/*  255 */     this.pilotSeat.parent = this;
/*  256 */     this.partEntities = new Entity[] { (Entity)this.pilotSeat };
/*  257 */     setTextureName("");
/*  258 */     this.camera = new MCH_Camera(world, (Entity)this, this.posX, this.posY, this.posZ);
/*  259 */     setCameraId(0);
/*  260 */     this.lastRiddenByEntity = null;
/*  261 */     this.lastRidingEntity = null;
/*  262 */     this.soundUpdater = MCH_MOD.proxy.CreateSoundUpdater(this);
/*  263 */     this.countOnUpdate = 0;
/*  264 */     setTowChainEntity((MCH_EntityChain)null);
/*  265 */     this.dummyWeapon = new MCH_WeaponSet((MCH_WeaponBase)new MCH_WeaponDummy(this.world, Vec3d.ZERO, 0.0F, 0.0F, "", null));
/*  266 */     this.useWeaponStat = 0;
/*  267 */     this.hitStatus = 0;
/*  268 */     this.repairCount = 0;
/*  269 */     this.beforeDamageTaken = 0;
/*  270 */     this.timeSinceHit = 0;
/*  271 */     setDespawnCount(0);
/*  272 */     this.missileDetector = new MCH_MissileDetector(this, world);
/*  273 */     this.uavStation = null;
/*  274 */     this.modeSwitchCooldown = 0;
/*  275 */     this.partHatch = null;
/*  276 */     this.partCanopy = null;
/*  277 */     this.partLandingGear = null;
/*  278 */     this.weaponBays = new WeaponBay[0];
/*  279 */     this.rotPartRotation = new float[0];
/*  280 */     this.prevRotPartRotation = new float[0];
/*  281 */     this.lastRiderYaw = 0.0F;
/*  282 */     this.prevLastRiderYaw = 0.0F;
/*  283 */     this.lastRiderPitch = 0.0F;
/*  284 */     this.prevLastRiderPitch = 0.0F;
/*  285 */     this.rotationRoll = 0.0F;
/*  286 */     this.prevRotationRoll = 0.0F;
/*  287 */     this.lowPassPartialTicks = new MCH_LowPassFilterFloat(10);
/*  288 */     this.extraBoundingBox = new MCH_BoundingBox[0];
/*  289 */     setEntityBoundingBox(new MCH_AircraftBoundingBox(this));
/*  290 */     this.lastBBDamageFactor = 1.0F;
/*  291 */     this.inventory = new MCH_AircraftInventory(this);
/*  292 */     this.fuelConsumption = 0.0D;
/*  293 */     this.fuelSuppliedCount = 0;
/*  294 */     this.canRideRackStatus = false;
/*  295 */     this.isParachuting = false;
/*  296 */     this.prevPosition = new MCH_Queue(10, Vec3d.ZERO);
/*  297 */     this.lastSearchLightYaw = this.lastSearchLightPitch = 0.0F;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static MCH_EntityAircraft getAircraft_RiddenOrControl(@Nullable Entity rider) {
/*  302 */     if (rider != null) {
/*  303 */       if (rider.getRidingEntity() instanceof MCH_EntityAircraft) {
/*  304 */         return (MCH_EntityAircraft)rider.getRidingEntity();
/*      */       }
/*      */       
/*  307 */       if (rider.getRidingEntity() instanceof MCH_EntitySeat) {
/*  308 */         return ((MCH_EntitySeat)rider.getRidingEntity()).getParent();
/*      */       }
/*      */       
/*  311 */       Entity entity = rider.getRidingEntity(); if (entity instanceof MCH_EntityUavStation) { MCH_EntityUavStation uavStation = (MCH_EntityUavStation)entity;
/*  312 */         return uavStation.getControlAircract(); }
/*      */     
/*      */     } 
/*      */     
/*  316 */     return null;
/*      */   }
/*      */   
/*      */   public static boolean isSeatPassenger(@Nullable Entity rider) {
/*  320 */     return (rider != null && rider.getRidingEntity() instanceof MCH_EntitySeat);
/*      */   }
/*      */   
/*      */   private static boolean getCollisionBoxes(@Nullable Entity entityIn, AxisAlignedBB aabb, List<AxisAlignedBB> outList) {
/*  324 */     int i = MathHelper.floor(aabb.minX) - 1;
/*  325 */     int j = MathHelper.ceil(aabb.maxX) + 1;
/*  326 */     int k = MathHelper.floor(aabb.minY) - 1;
/*  327 */     int l = MathHelper.ceil(aabb.maxY) + 1;
/*  328 */     int i1 = MathHelper.floor(aabb.minZ) - 1;
/*  329 */     int j1 = MathHelper.ceil(aabb.maxZ) + 1;
/*  330 */     WorldBorder worldborder = entityIn.world.getWorldBorder();
/*  331 */     boolean flag = entityIn.isOutsideBorder();
/*  332 */     boolean flag1 = entityIn.world.isInsideWorldBorder(entityIn);
/*  333 */     IBlockState iblockstate = Blocks.STONE.getDefaultState();
/*  334 */     BlockPos.PooledMutableBlockPos blockpos = BlockPos.PooledMutableBlockPos.retain();
/*      */     
/*      */     try {
/*  337 */       for (int k1 = i; k1 < j; k1++) {
/*  338 */         for (int l1 = i1; l1 < j1; l1++) {
/*  339 */           boolean flag2 = (k1 == i || k1 == j - 1);
/*  340 */           boolean flag3 = (l1 == i1 || l1 == j1 - 1);
/*  341 */           if ((!flag2 || !flag3) && entityIn.world.isBlockLoaded((BlockPos)blockpos.setPos(k1, 64, l1))) {
/*  342 */             for (int i2 = k; i2 < l; i2++) {
/*  343 */               if ((!flag2 && !flag3) || i2 != l - 1) {
/*  344 */                 IBlockState iblockstate1; if (flag == flag1) {
/*  345 */                   entityIn.setOutsideBorder(!flag1);
/*      */                 }
/*      */                 
/*  348 */                 blockpos.setPos(k1, i2, l1);
/*      */                 
/*  350 */                 if (!worldborder.contains((BlockPos)blockpos) && flag1) {
/*  351 */                   iblockstate1 = iblockstate;
/*      */                 } else {
/*  353 */                   iblockstate1 = entityIn.world.getBlockState((BlockPos)blockpos);
/*      */                 } 
/*      */                 
/*  356 */                 iblockstate1.addCollisionBoxToList(entityIn.world, (BlockPos)blockpos, aabb, outList, entityIn, false);
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  363 */       blockpos.release();
/*      */     } 
/*      */     
/*  366 */     return !outList.isEmpty();
/*      */   }
/*      */   
/*      */   public static List<AxisAlignedBB> getCollisionBoxes(@Nullable Entity entityIn, AxisAlignedBB aabb) {
/*  370 */     List<AxisAlignedBB> list = new ArrayList<>();
/*  371 */     getCollisionBoxes(entityIn, aabb, list);
/*  372 */     if (entityIn != null) {
/*  373 */       List<Entity> list1 = entityIn.world.getEntitiesWithinAABBExcludingEntity(entityIn, aabb.grow(0.25D));
/*      */       
/*  375 */       for (Entity entity : list1) {
/*  376 */         if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_EntitySeat) && !(entity instanceof MCH_EntityHitBox)) {
/*  377 */           AxisAlignedBB axisalignedbb = entity.getCollisionBoundingBox();
/*  378 */           if (axisalignedbb != null && axisalignedbb.intersects(aabb)) {
/*  379 */             list.add(axisalignedbb);
/*      */           }
/*      */           
/*  382 */           axisalignedbb = entityIn.getCollisionBox(entity);
/*  383 */           if (axisalignedbb != null && axisalignedbb.intersects(aabb)) {
/*  384 */             list.add(axisalignedbb);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  390 */     return list;
/*      */   }
/*      */   
/*      */   public static float abs(float value) {
/*  394 */     return (value >= 0.0F) ? value : -value;
/*      */   }
/*      */   
/*      */   public static double abs(double value) {
/*  398 */     return (value >= 0.0D) ? value : -value;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  403 */     super.entityInit();
/*  404 */     this.dataManager.register(ID_TYPE, "");
/*  405 */     this.dataManager.register(DAMAGE, Integer.valueOf(0));
/*  406 */     this.dataManager.register(STATUS, Integer.valueOf(0));
/*  407 */     this.dataManager.register(USE_WEAPON, Integer.valueOf(0));
/*  408 */     this.dataManager.register(FUEL, Integer.valueOf(0));
/*  409 */     this.dataManager.register(TEXTURE_NAME, "");
/*  410 */     this.dataManager.register(UAV_STATION, Integer.valueOf(0));
/*  411 */     this.dataManager.register(ROT_ROLL, Integer.valueOf(0));
/*  412 */     this.dataManager.register(COMMAND, "");
/*  413 */     this.dataManager.register(THROTTLE, Integer.valueOf(0));
/*  414 */     this.dataManager.register(PART_STAT, Integer.valueOf(0));
/*  415 */     if (!this.world.isRemote) {
/*  416 */       setCommonStatus(3, MCH_Config.InfinityAmmo.prmBool);
/*  417 */       setCommonStatus(4, MCH_Config.InfinityFuel.prmBool);
/*  418 */       setGunnerStatus(true);
/*      */     } 
/*      */     
/*  421 */     getEntityData().setString("EntityType", getEntityType());
/*      */   }
/*      */   
/*      */   public float getServerRoll() {
/*  425 */     return ((Integer)this.dataManager.get(ROT_ROLL)).shortValue();
/*      */   }
/*      */   
/*      */   public float getRotYaw() {
/*  429 */     return this.rotationYaw;
/*      */   }
/*      */   
/*      */   public void setRotYaw(float f) {
/*  433 */     this.rotationYaw = f;
/*      */   }
/*      */   
/*      */   public float getRotPitch() {
/*  437 */     return this.rotationPitch;
/*      */   }
/*      */   
/*      */   public void setRotPitch(float f) {
/*  441 */     this.rotationPitch = f;
/*      */   }
/*      */   
/*      */   public float getRotRoll() {
/*  445 */     return this.rotationRoll;
/*      */   }
/*      */   
/*      */   public void setRotRoll(float f) {
/*  449 */     this.rotationRoll = f;
/*      */   }
/*      */   
/*      */   public void setRotPitch(float f, String msg) {
/*  453 */     setRotPitch(f);
/*      */   }
/*      */   
/*      */   public void applyOnGroundPitch(float factor) {
/*  457 */     if (getAcInfo() != null) {
/*  458 */       float ogp = (getAcInfo()).onGroundPitch;
/*  459 */       float pitch = getRotPitch();
/*  460 */       pitch -= ogp;
/*  461 */       pitch *= factor;
/*  462 */       pitch += ogp;
/*  463 */       setRotPitch(pitch, "applyOnGroundPitch");
/*      */     } 
/*      */     
/*  466 */     setRotRoll(getRotRoll() * factor);
/*      */   }
/*      */   
/*      */   public float calcRotYaw(float partialTicks) {
/*  470 */     return this.prevRotationYaw + (getRotYaw() - this.prevRotationYaw) * partialTicks;
/*      */   }
/*      */   
/*      */   public float calcRotPitch(float partialTicks) {
/*  474 */     return this.prevRotationPitch + (getRotPitch() - this.prevRotationPitch) * partialTicks;
/*      */   }
/*      */   
/*      */   public float calcRotRoll(float partialTicks) {
/*  478 */     return this.prevRotationRoll + (getRotRoll() - this.prevRotationRoll) * partialTicks;
/*      */   }
/*      */   
/*      */   protected void setRotation(float y, float p) {
/*  482 */     setRotYaw(y % 360.0F);
/*  483 */     setRotPitch(p % 360.0F);
/*      */   }
/*      */   
/*      */   public boolean isInfinityAmmo(Entity player) {
/*  487 */     return (isCreative(player) || getCommonStatus(3));
/*      */   }
/*      */   
/*      */   public boolean isInfinityFuel(Entity player, boolean checkOtherSeet) {
/*  491 */     if (!isCreative(player) && !getCommonStatus(4)) {
/*  492 */       if (checkOtherSeet) {
/*  493 */         for (MCH_EntitySeat seat : getSeats()) {
/*  494 */           if (seat != null && isCreative(seat.getRiddenByEntity())) {
/*  495 */             return true;
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  500 */       return false;
/*      */     } 
/*  502 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCommand(String s, EntityPlayer player) {
/*  507 */     if (!this.world.isRemote && MCH_Command.canUseCommand((Entity)player)) {
/*  508 */       setCommandForce(s);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setCommandForce(String s) {
/*  513 */     if (!this.world.isRemote) {
/*  514 */       this.dataManager.set(COMMAND, s);
/*      */     }
/*      */   }
/*      */   
/*      */   public String getCommand() {
/*  519 */     return (String)this.dataManager.get(COMMAND);
/*      */   }
/*      */   
/*      */   public String getKindName() {
/*  523 */     return "";
/*      */   }
/*      */   
/*      */   public String getEntityType() {
/*  527 */     return "";
/*      */   }
/*      */   
/*      */   public String getTypeName() {
/*  531 */     return (String)this.dataManager.get(ID_TYPE);
/*      */   }
/*      */   
/*      */   public void setTypeName(String s) {
/*  535 */     String beforeType = getTypeName();
/*  536 */     if (s != null && !s.isEmpty() && s.compareTo(beforeType) != 0) {
/*  537 */       this.dataManager.set(ID_TYPE, s);
/*  538 */       changeType(s);
/*  539 */       initRotationYaw(getRotYaw());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTargetDrone() {
/*  546 */     return (getAcInfo() != null && (getAcInfo()).isTargetDrone);
/*      */   }
/*      */   
/*      */   public boolean isUAV() {
/*  550 */     return (getAcInfo() != null && (getAcInfo()).isUAV);
/*      */   }
/*      */   
/*      */   public boolean isSmallUAV() {
/*  554 */     return (getAcInfo() != null && (getAcInfo()).isSmallUAV);
/*      */   }
/*      */   
/*      */   public boolean isAlwaysCameraView() {
/*  558 */     return (getAcInfo() != null && (getAcInfo()).alwaysCameraView);
/*      */   }
/*      */   
/*      */   public float getStealth() {
/*  562 */     return (getAcInfo() != null) ? (getAcInfo()).stealth : 0.0F;
/*      */   }
/*      */   
/*      */   public MCH_AircraftInventory getGuiInventory() {
/*  566 */     return this.inventory;
/*      */   }
/*      */   
/*      */   public void openGui(EntityPlayer player) {
/*  570 */     if (!this.world.isRemote) {
/*  571 */       player.openGui(MCH_MOD.instance, 1, this.world, (int)this.posX, (int)this.posY, (int)this.posZ);
/*      */     }
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MCH_EntityUavStation getUavStation() {
/*  577 */     return isUAV() ? this.uavStation : null;
/*      */   }
/*      */   
/*      */   public void setUavStation(MCH_EntityUavStation uavSt) {
/*  581 */     this.uavStation = uavSt;
/*  582 */     if (!this.world.isRemote) {
/*  583 */       if (uavSt != null) {
/*  584 */         this.dataManager.set(UAV_STATION, Integer.valueOf(W_Entity.getEntityId((Entity)uavSt)));
/*      */       } else {
/*  586 */         this.dataManager.set(UAV_STATION, Integer.valueOf(0));
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isCreative(@Nullable Entity entity) {
/*  592 */     return ((entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) || (entity instanceof MCH_EntityGunner && ((MCH_EntityGunner)entity).isCreative));
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity getRiddenByEntity() {
/*  598 */     if (isUAV() && this.uavStation != null) {
/*  599 */       return this.uavStation.getRiddenByEntity();
/*      */     }
/*  601 */     List<Entity> passengers = getPassengers();
/*  602 */     return passengers.isEmpty() ? null : passengers.get(0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getCommonStatus(int bit) {
/*  607 */     return ((this.commonStatus >> bit & 0x1) != 0);
/*      */   }
/*      */   
/*      */   public void setCommonStatus(int bit, boolean b) {
/*  611 */     setCommonStatus(bit, b, false);
/*      */   }
/*      */   
/*      */   public void setCommonStatus(int bit, boolean b, boolean writeClient) {
/*  615 */     if (!this.world.isRemote || writeClient) {
/*  616 */       int bofore = this.commonStatus;
/*  617 */       int mask = 1 << bit;
/*  618 */       if (b) {
/*  619 */         this.commonStatus |= mask;
/*      */       } else {
/*  621 */         this.commonStatus &= mask ^ 0xFFFFFFFF;
/*      */       } 
/*      */       
/*  624 */       if (bofore != this.commonStatus) {
/*  625 */         MCH_Lib.DbgLog(this.world, "setCommonStatus : %08X -> %08X ", new Object[] { this.dataManager.get(STATUS), Integer.valueOf(this.commonStatus) });
/*  626 */         this.dataManager.set(STATUS, Integer.valueOf(this.commonStatus));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public double getThrottle() {
/*  632 */     return 0.05D * ((Integer)this.dataManager.get(THROTTLE)).intValue();
/*      */   }
/*      */   
/*      */   public void setThrottle(double t) {
/*  636 */     int n = (int)(t * 20.0D);
/*  637 */     if (n == 0 && t > 0.0D) {
/*  638 */       n = 1;
/*      */     }
/*      */     
/*  641 */     this.dataManager.set(THROTTLE, Integer.valueOf(n));
/*      */   }
/*      */   
/*      */   public int getMaxHP() {
/*  645 */     return (getAcInfo() != null) ? (getAcInfo()).maxHp : 100;
/*      */   }
/*      */   
/*      */   public int getHP() {
/*  649 */     return Math.max(getMaxHP() - getDamageTaken(), 0);
/*      */   }
/*      */   
/*      */   public int getDamageTaken() {
/*  653 */     return ((Integer)this.dataManager.get(DAMAGE)).intValue();
/*      */   }
/*      */   
/*      */   public void setDamageTaken(int par1) {
/*  657 */     if (par1 < 0) {
/*  658 */       par1 = 0;
/*      */     }
/*      */     
/*  661 */     if (par1 > getMaxHP()) {
/*  662 */       par1 = getMaxHP();
/*      */     }
/*      */     
/*  665 */     this.dataManager.set(DAMAGE, Integer.valueOf(par1));
/*      */   }
/*      */   
/*      */   public void destroyAircraft() {
/*  669 */     setSearchLight(false);
/*  670 */     switchHoveringMode(false);
/*  671 */     switchGunnerMode(false);
/*      */     
/*  673 */     for (int i = 0; i < getSeatNum() + 1; i++) {
/*  674 */       Entity e = getEntityBySeatId(i);
/*  675 */       if (e instanceof EntityPlayer) {
/*  676 */         switchCameraMode((EntityPlayer)e, 0);
/*      */       }
/*      */     } 
/*      */     
/*  680 */     if (isTargetDrone()) {
/*  681 */       setDespawnCount(20 * MCH_Config.DespawnCount.prmInt / 10);
/*      */     } else {
/*  683 */       setDespawnCount(20 * MCH_Config.DespawnCount.prmInt);
/*      */     } 
/*      */     
/*  686 */     this.rotDestroyedPitch = this.rand.nextFloat() - 0.5F;
/*  687 */     this.rotDestroyedRoll = (this.rand.nextFloat() - 0.5F) * 0.5F;
/*  688 */     this.rotDestroyedYaw = 0.0F;
/*  689 */     if (isUAV() && getRiddenByEntity() != null) {
/*  690 */       getRiddenByEntity().dismountRidingEntity();
/*      */     }
/*      */     
/*  693 */     if (!this.world.isRemote) {
/*  694 */       ejectSeat(getRiddenByEntity());
/*  695 */       Entity entity = getEntityBySeatId(1);
/*  696 */       if (entity != null) {
/*  697 */         ejectSeat(entity);
/*      */       }
/*      */       
/*  700 */       float dmg = MCH_Config.KillPassengersWhenDestroyed.prmBool ? 100000.0F : 0.001F;
/*  701 */       DamageSource dse = DamageSource.GENERIC;
/*  702 */       if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
/*  703 */         if (this.lastAttackedEntity instanceof EntityPlayer) {
/*  704 */           dse = DamageSource.causePlayerDamage((EntityPlayer)this.lastAttackedEntity);
/*      */         }
/*      */       } else {
/*  707 */         dse = DamageSource.causeExplosionDamage(new Explosion(this.world, this.lastAttackedEntity, this.posX, this.posY, this.posZ, 1.0F, false, true));
/*      */       } 
/*      */       
/*  710 */       Entity riddenByEntity = getRiddenByEntity();
/*  711 */       if (riddenByEntity != null) {
/*  712 */         riddenByEntity.attackEntityFrom(dse, dmg);
/*      */       }
/*      */       
/*  715 */       for (MCH_EntitySeat seat : getSeats()) {
/*  716 */         if (seat != null && seat.getRiddenByEntity() != null) {
/*  717 */           seat.getRiddenByEntity().attackEntityFrom(dse, dmg);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isDestroyed() {
/*  724 */     return (getDespawnCount() > 0);
/*      */   }
/*      */   
/*      */   public int getDespawnCount() {
/*  728 */     return this.despawnCount;
/*      */   }
/*      */   
/*      */   public void setDespawnCount(int despawnCount) {
/*  732 */     this.despawnCount = despawnCount;
/*      */   }
/*      */   
/*      */   public boolean isEntityRadarMounted() {
/*  736 */     return (getAcInfo() != null && (getAcInfo()).isEnableEntityRadar);
/*      */   }
/*      */   
/*      */   public boolean canFloatWater() {
/*  740 */     return (getAcInfo() != null && (getAcInfo()).isFloat && !isDestroyed());
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public int getBrightnessForRender() {
/*  745 */     if (haveSearchLight() && isSearchLightON()) {
/*  746 */       return 15728880;
/*      */     }
/*  748 */     int i = MathHelper.floor(this.posX);
/*  749 */     int j = MathHelper.floor(this.posZ);
/*  750 */     if (this.world.isBlockLoaded(new BlockPos(i, 0, j))) {
/*  751 */       double d0 = ((getEntityBoundingBox()).maxY - (getEntityBoundingBox()).minY) * 0.66D;
/*  752 */       float fo = (getAcInfo() != null) ? (getAcInfo()).submergedDamageHeight : 0.0F;
/*  753 */       if (canFloatWater()) {
/*  754 */         fo = (getAcInfo()).floatOffset;
/*  755 */         if (fo < 0.0F) {
/*  756 */           fo = -fo;
/*      */         }
/*      */         
/*  759 */         fo++;
/*      */       } 
/*      */       
/*  762 */       int k = MathHelper.floor(this.posY + fo + d0);
/*  763 */       int val = this.world.getCombinedLight(new BlockPos(i, k, j), 0);
/*  764 */       int low = val & 0xFFFF;
/*  765 */       int high = val >> 16 & 0xFFFF;
/*  766 */       if (high < this.brightnessHigh) {
/*  767 */         if (getCountOnUpdate() % 2 == 0) {
/*  768 */           this.brightnessHigh--;
/*      */         }
/*  770 */       } else if (high > this.brightnessHigh) {
/*  771 */         this.brightnessHigh += 4;
/*  772 */         if (this.brightnessHigh > 240) {
/*  773 */           this.brightnessHigh = 240;
/*      */         }
/*      */       } 
/*      */       
/*  777 */       return this.brightnessHigh << 16 | low;
/*      */     } 
/*  779 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public MCH_AircraftInfo.CameraPosition getCameraPosInfo() {
/*  786 */     if (getAcInfo() == null) {
/*  787 */       return null;
/*      */     }
/*  789 */     Entity player = MCH_Lib.getClientPlayer();
/*  790 */     int sid = getSeatIdByEntity(player);
/*  791 */     if (sid == 0 && canSwitchCameraPos() && getCameraId() > 0 && getCameraId() < (getAcInfo()).cameraPosition.size()) {
/*  792 */       return (getAcInfo()).cameraPosition.get(getCameraId());
/*      */     }
/*  794 */     return (sid > 0 && sid < (getSeatsInfo()).length && (getSeatsInfo()[sid]).invCamPos) ? 
/*  795 */       getSeatsInfo()[sid].getCamPos() : 
/*  796 */       (getAcInfo()).cameraPosition.get(0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCameraId() {
/*  802 */     return this.cameraId;
/*      */   }
/*      */   
/*      */   public void setCameraId(int cameraId) {
/*  806 */     MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setCameraId %d -> %d", new Object[] { Integer.valueOf(this.cameraId), Integer.valueOf(cameraId) });
/*  807 */     this.cameraId = cameraId;
/*      */   }
/*      */   
/*      */   public boolean canSwitchCameraPos() {
/*  811 */     return (getCameraPosNum() >= 2);
/*      */   }
/*      */   
/*      */   public int getCameraPosNum() {
/*  815 */     return (getAcInfo() != null) ? (getAcInfo()).cameraPosition.size() : 1;
/*      */   }
/*      */   
/*      */   public void onAcInfoReloaded() {
/*  819 */     if (getAcInfo() != null) {
/*  820 */       setSize((getAcInfo()).bodyWidth, (getAcInfo()).bodyHeight);
/*      */     }
/*      */   }
/*      */   
/*      */   public void writeSpawnData(ByteBuf buffer) {
/*  825 */     if (getAcInfo() != null) {
/*  826 */       buffer.writeFloat((getAcInfo()).bodyHeight);
/*  827 */       buffer.writeFloat((getAcInfo()).bodyWidth);
/*  828 */       buffer.writeFloat((getAcInfo()).thirdPersonDist);
/*  829 */       byte[] name = getTypeName().getBytes();
/*  830 */       buffer.writeShort(name.length);
/*  831 */       buffer.writeBytes(name);
/*      */     } else {
/*  833 */       buffer.writeFloat(this.height);
/*  834 */       buffer.writeFloat(this.width);
/*  835 */       buffer.writeFloat(4.0F);
/*  836 */       buffer.writeShort(0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void readSpawnData(ByteBuf data) {
/*      */     try {
/*  842 */       float height = data.readFloat();
/*  843 */       float width = data.readFloat();
/*  844 */       setSize(width, height);
/*  845 */       this.thirdPersonDist = data.readFloat();
/*  846 */       int len = data.readShort();
/*  847 */       if (len > 0) {
/*  848 */         byte[] dst = new byte[len];
/*  849 */         data.readBytes(dst);
/*  850 */         changeType(new String(dst));
/*      */       } 
/*  852 */     } catch (Exception var6) {
/*  853 */       MCH_Lib.Log((Entity)this, "readSpawnData error!", new Object[0]);
/*  854 */       var6.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void readEntityFromNBT(NBTTagCompound nbt) {
/*  860 */     setDespawnCount(nbt.getInteger("AcDespawnCount"));
/*  861 */     setTextureName(nbt.getString("TextureName"));
/*  862 */     setCommonUniqueId(nbt.getString("AircraftUniqueId"));
/*  863 */     setRotRoll(nbt.getFloat("AcRoll"));
/*  864 */     this.prevRotationRoll = getRotRoll();
/*  865 */     this.prevLastRiderYaw = this.lastRiderYaw = nbt.getFloat("AcLastRYaw");
/*  866 */     this.prevLastRiderPitch = this.lastRiderPitch = nbt.getFloat("AcLastRPitch");
/*  867 */     setPartStatus(nbt.getInteger("PartStatus"));
/*  868 */     setTypeName(nbt.getString("TypeName"));
/*  869 */     super.readEntityFromNBT(nbt);
/*  870 */     getGuiInventory().readEntityFromNBT(nbt);
/*  871 */     setCommandForce(nbt.getString("AcCommand"));
/*  872 */     setGunnerStatus(nbt.getBoolean("AcGunnerStatus"));
/*  873 */     setFuel(nbt.getInteger("AcFuel"));
/*  874 */     int[] wa_list = nbt.getIntArray("AcWeaponsAmmo");
/*      */     
/*  876 */     for (int i = 0; i < wa_list.length; i++) {
/*  877 */       getWeapon(i).setRestAllAmmoNum(wa_list[i]);
/*  878 */       getWeapon(i).reloadMag();
/*      */     } 
/*      */     
/*  881 */     if (getDespawnCount() > 0) {
/*  882 */       setDamageTaken(getMaxHP());
/*  883 */     } else if (nbt.hasKey("AcDamage")) {
/*  884 */       setDamageTaken(nbt.getInteger("AcDamage"));
/*      */     } 
/*      */     
/*  887 */     if (haveSearchLight() && nbt.hasKey("SearchLight")) {
/*  888 */       setSearchLight(nbt.getBoolean("SearchLight"));
/*      */     }
/*      */     
/*  891 */     this.dismountedUserCtrl = nbt.getBoolean("AcDismounted");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeEntityToNBT(NBTTagCompound nbt) {
/*  896 */     nbt.setString("TextureName", getTextureName());
/*  897 */     nbt.setString("AircraftUniqueId", getCommonUniqueId());
/*  898 */     nbt.setString("TypeName", getTypeName());
/*  899 */     nbt.setInteger("PartStatus", getPartStatus() & getLastPartStatusMask());
/*  900 */     nbt.setInteger("AcFuel", getFuel());
/*  901 */     nbt.setInteger("AcDespawnCount", getDespawnCount());
/*  902 */     nbt.setFloat("AcRoll", getRotRoll());
/*  903 */     nbt.setBoolean("SearchLight", isSearchLightON());
/*  904 */     nbt.setFloat("AcLastRYaw", getLastRiderYaw());
/*  905 */     nbt.setFloat("AcLastRPitch", getLastRiderPitch());
/*  906 */     nbt.setString("AcCommand", getCommand());
/*  907 */     if (!nbt.hasKey("AcGunnerStatus")) {
/*  908 */       setGunnerStatus(true);
/*      */     }
/*      */     
/*  911 */     nbt.setBoolean("AcGunnerStatus", getGunnerStatus());
/*  912 */     super.writeEntityToNBT(nbt);
/*  913 */     getGuiInventory().writeEntityToNBT(nbt);
/*  914 */     int[] wa_list = new int[getWeaponNum()];
/*      */     
/*  916 */     for (int i = 0; i < wa_list.length; i++) {
/*  917 */       wa_list[i] = getWeapon(i).getRestAllAmmoNum() + getWeapon(i).getAmmoNum();
/*      */     }
/*      */     
/*  920 */     nbt.setTag("AcWeaponsAmmo", (NBTBase)W_NBTTag.newTagIntArray("AcWeaponsAmmo", wa_list));
/*  921 */     nbt.setInteger("AcDamage", getDamageTaken());
/*  922 */     nbt.setBoolean("AcDismounted", this.dismountedUserCtrl);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource damageSource, float org_damage) {
/*  927 */     float damageFactor = this.lastBBDamageFactor;
/*  928 */     this.lastBBDamageFactor = 1.0F;
/*  929 */     if (isEntityInvulnerable(damageSource))
/*  930 */       return false; 
/*  931 */     if (this.isDead)
/*  932 */       return false; 
/*  933 */     if (this.timeSinceHit > 0) {
/*  934 */       return false;
/*      */     }
/*  936 */     String dmt = damageSource.getDamageType();
/*  937 */     if (dmt.equalsIgnoreCase("inFire"))
/*  938 */       return false; 
/*  939 */     if (dmt.equalsIgnoreCase("cactus"))
/*  940 */       return false; 
/*  941 */     if (this.world.isRemote) {
/*  942 */       return true;
/*      */     }
/*  944 */     float damage = MCH_Config.applyDamageByExternal((Entity)this, damageSource, org_damage);
/*  945 */     if (getAcInfo() != null && (getAcInfo()).invulnerable) {
/*  946 */       damage = 0.0F;
/*      */     }
/*      */     
/*  949 */     if (damageSource == DamageSource.OUT_OF_WORLD) {
/*  950 */       setDead();
/*      */     }
/*      */     
/*  953 */     if (!MCH_Multiplay.canAttackEntity(damageSource, (Entity)this)) {
/*  954 */       return false;
/*      */     }
/*  956 */     if (dmt.equalsIgnoreCase("lava")) {
/*  957 */       damage *= (this.rand.nextInt(8) + 2);
/*  958 */       this.timeSinceHit = 2;
/*      */     } 
/*      */     
/*  961 */     if (dmt.startsWith("explosion")) {
/*  962 */       this.timeSinceHit = 1;
/*  963 */     } else if (isMountedEntity(damageSource.getTrueSource())) {
/*  964 */       return false;
/*      */     } 
/*      */     
/*  967 */     if (dmt.equalsIgnoreCase("onFire")) {
/*  968 */       this.timeSinceHit = 10;
/*      */     }
/*      */     
/*  971 */     boolean isCreative = false;
/*  972 */     boolean isSneaking = false;
/*  973 */     Entity entity = damageSource.getTrueSource();
/*  974 */     if (entity instanceof EntityLivingBase) {
/*  975 */       this.lastAttackedEntity = entity;
/*      */     }
/*      */     
/*  978 */     boolean isDamegeSourcePlayer = false;
/*  979 */     boolean playDamageSound = false;
/*  980 */     if (entity instanceof EntityPlayer) { EntityPlayer player = (EntityPlayer)entity;
/*  981 */       isCreative = player.capabilities.isCreativeMode;
/*  982 */       isSneaking = player.isSneaking();
/*  983 */       if (dmt.equalsIgnoreCase("player")) {
/*  984 */         if (isCreative) {
/*  985 */           isDamegeSourcePlayer = true;
/*  986 */         } else if (getAcInfo() != null && !(getAcInfo()).creativeOnly && !MCH_Config.PreventingBroken.prmBool) {
/*  987 */           if (MCH_Config.BreakableOnlyPickaxe.prmBool) {
/*  988 */             if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemPickaxe) {
/*  989 */               isDamegeSourcePlayer = true;
/*      */             }
/*      */           } else {
/*  992 */             isDamegeSourcePlayer = !isRidePlayer();
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*  997 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "hit", (damage > 0.0F) ? 1.0F : 0.5F, 1.0F); }
/*      */     else
/*  999 */     { playDamageSound = true; }
/*      */ 
/*      */     
/* 1002 */     if (!isDestroyed()) {
/* 1003 */       if (!isDamegeSourcePlayer) {
/* 1004 */         MCH_AircraftInfo acInfo = getAcInfo();
/* 1005 */         if (acInfo != null && !dmt.equalsIgnoreCase("lava") && !dmt.equalsIgnoreCase("onFire")) {
/* 1006 */           if (damage > acInfo.armorMaxDamage) {
/* 1007 */             damage = acInfo.armorMaxDamage;
/*      */           }
/*      */           
/* 1010 */           if (damageFactor <= 1.0F) {
/* 1011 */             damage *= damageFactor;
/*      */           }
/*      */           
/* 1014 */           damage *= acInfo.armorDamageFactor;
/* 1015 */           damage -= acInfo.armorMinDamage;
/* 1016 */           if (damage <= 0.0F) {
/* 1017 */             MCH_Lib.DbgLog(this.world, "MCH_EntityAircraft.attackEntityFrom:no damage=%.1f -> %.1f(factor=%.2f):%s", new Object[] {
/* 1018 */                   Float.valueOf(org_damage), Float.valueOf(damage), Float.valueOf(damageFactor), dmt
/*      */                 });
/* 1020 */             return false;
/*      */           } 
/*      */           
/* 1023 */           if (damageFactor > 1.0F) {
/* 1024 */             damage *= damageFactor;
/*      */           }
/*      */         } 
/*      */         
/* 1028 */         MCH_Lib.DbgLog(this.world, "MCH_EntityAircraft.attackEntityFrom:damage=%.1f(factor=%.2f):%s", new Object[] { Float.valueOf(damage), Float.valueOf(damageFactor), dmt });
/* 1029 */         setDamageTaken(getDamageTaken() + (int)damage);
/*      */       } 
/*      */       
/* 1032 */       markVelocityChanged();
/* 1033 */       if (getDamageTaken() >= getMaxHP() || isDamegeSourcePlayer) {
/* 1034 */         if (!isDamegeSourcePlayer) {
/* 1035 */           setDamageTaken(getMaxHP());
/* 1036 */           destroyAircraft();
/* 1037 */           this.timeSinceHit = 20;
/* 1038 */           String cmd = getCommand().trim();
/* 1039 */           if (cmd.startsWith("/")) {
/* 1040 */             cmd = cmd.substring(1);
/*      */           }
/*      */           
/* 1043 */           if (!cmd.isEmpty()) {
/* 1044 */             MCH_DummyCommandSender.execCommand(cmd);
/*      */           }
/*      */           
/* 1047 */           if (dmt.equalsIgnoreCase("inWall")) {
/* 1048 */             explosionByCrash(0.0D);
/* 1049 */             this.damageSinceDestroyed = getMaxHP();
/*      */           } else {
/* 1051 */             MCH_Explosion.newExplosion(this.world, null, entity, this.posX, this.posY, this.posZ, 2.0F, 2.0F, true, true, true, true, 5);
/*      */           } 
/*      */         } else {
/* 1054 */           if (getAcInfo() != null && getAcInfo().getItem() != null) {
/* 1055 */             if (isCreative) {
/* 1056 */               if (MCH_Config.DropItemInCreativeMode.prmBool && !isSneaking) {
/* 1057 */                 dropItemWithOffset(getAcInfo().getItem(), 1, 0.0F);
/*      */               }
/*      */               
/* 1060 */               if (!MCH_Config.DropItemInCreativeMode.prmBool && isSneaking) {
/* 1061 */                 dropItemWithOffset(getAcInfo().getItem(), 1, 0.0F);
/*      */               }
/*      */             } else {
/* 1064 */               dropItemWithOffset(getAcInfo().getItem(), 1, 0.0F);
/*      */             } 
/*      */           }
/*      */           
/* 1068 */           setDead(true);
/*      */         } 
/*      */       }
/* 1071 */     } else if (isDamegeSourcePlayer && isCreative) {
/* 1072 */       setDead(true);
/*      */     } 
/*      */     
/* 1075 */     if (playDamageSound) {
/* 1076 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "helidmg", 1.0F, 0.9F + this.rand.nextFloat() * 0.1F);
/*      */     }
/*      */     
/* 1079 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isExploded() {
/* 1086 */     return (isDestroyed() && this.damageSinceDestroyed > getMaxHP() / 10 + 1);
/*      */   }
/*      */   
/*      */   public void destruct() {
/* 1090 */     if (getRiddenByEntity() != null) {
/* 1091 */       getRiddenByEntity().dismountRidingEntity();
/*      */     }
/*      */     
/* 1094 */     setDead(true);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public EntityItem entityDropItem(ItemStack is, float par2) {
/* 1099 */     if (is.getCount() == 0) {
/* 1100 */       return null;
/*      */     }
/* 1102 */     setAcDataToItem(is);
/* 1103 */     return super.entityDropItem(is, par2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAcDataToItem(ItemStack is) {
/* 1108 */     if (!is.hasTagCompound()) {
/* 1109 */       is.setTagCompound(new NBTTagCompound());
/*      */     }
/*      */     
/* 1112 */     NBTTagCompound nbt = is.getTagCompound();
/* 1113 */     nbt.setString("MCH_Command", getCommand());
/* 1114 */     if (MCH_Config.ItemFuel.prmBool) {
/* 1115 */       nbt.setInteger("MCH_Fuel", getFuel());
/*      */     }
/*      */     
/* 1118 */     if (MCH_Config.ItemDamage.prmBool) {
/* 1119 */       is.setItemDamage(getDamageTaken());
/*      */     }
/*      */   }
/*      */   
/*      */   public void getAcDataFromItem(ItemStack is) {
/* 1124 */     if (is.hasTagCompound()) {
/* 1125 */       NBTTagCompound nbt = is.getTagCompound();
/* 1126 */       setCommandForce(nbt.getString("MCH_Command"));
/* 1127 */       if (MCH_Config.ItemFuel.prmBool) {
/* 1128 */         setFuel(nbt.getInteger("MCH_Fuel"));
/*      */       }
/*      */       
/* 1131 */       if (MCH_Config.ItemDamage.prmBool) {
/* 1132 */         setDamageTaken(is.getMetadata());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isUsableByPlayer(EntityPlayer player) {
/* 1139 */     if (isUAV())
/* 1140 */       return super.isUsableByPlayer(player); 
/* 1141 */     if (!this.isDead) {
/* 1142 */       return (getSeatIdByEntity((Entity)player) >= 0) ? ((player.getDistanceSq((Entity)this) <= 4096.0D)) : ((player.getDistanceSq((Entity)this) <= 64.0D));
/*      */     }
/* 1144 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void applyEntityCollision(@NotNull Entity par1Entity) {}
/*      */ 
/*      */   
/*      */   public void addVelocity(double par1, double par3, double par5) {}
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void setVelocity(double par1, double par3, double par5) {
/* 1156 */     this.velocityX = this.motionX = par1;
/* 1157 */     this.velocityY = this.motionY = par3;
/* 1158 */     this.velocityZ = this.motionZ = par5;
/*      */   }
/*      */   
/*      */   public void onFirstUpdate() {
/* 1162 */     if (!this.world.isRemote) {
/* 1163 */       setCommonStatus(3, MCH_Config.InfinityAmmo.prmBool);
/* 1164 */       setCommonStatus(4, MCH_Config.InfinityFuel.prmBool);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onRidePilotFirstUpdate() {
/* 1169 */     if (this.world.isRemote && W_Lib.isClientPlayer(getRiddenByEntity())) {
/* 1170 */       updateClientSettings(0);
/*      */     }
/*      */     
/* 1173 */     Entity pilot = getRiddenByEntity();
/* 1174 */     if (pilot != null) {
/* 1175 */       pilot.rotationYaw = getLastRiderYaw();
/* 1176 */       pilot.rotationPitch = getLastRiderPitch();
/*      */     } 
/*      */     
/* 1179 */     this.keepOnRideRotation = false;
/* 1180 */     if (getAcInfo() != null) {
/* 1181 */       switchFreeLookModeClient((getAcInfo()).defaultFreelook);
/*      */     }
/*      */   }
/*      */   
/*      */   public double getCurrentThrottle() {
/* 1186 */     return this.currentThrottle;
/*      */   }
/*      */   
/*      */   public void setCurrentThrottle(double throttle) {
/* 1190 */     this.currentThrottle = throttle;
/*      */   }
/*      */   
/*      */   public void addCurrentThrottle(double throttle) {
/* 1194 */     setCurrentThrottle(getCurrentThrottle() + throttle);
/*      */   }
/*      */   
/*      */   public double getPrevCurrentThrottle() {
/* 1198 */     return this.prevCurrentThrottle;
/*      */   }
/*      */   
/*      */   public boolean canMouseRot() {
/* 1202 */     return (!this.isDead && getRiddenByEntity() != null && !isDestroyed());
/*      */   }
/*      */   
/*      */   public boolean canUpdateYaw(Entity player) {
/* 1206 */     if (getRidingEntity() != null) {
/* 1207 */       return false;
/*      */     }
/* 1209 */     return (getCountOnUpdate() >= 30 && MCH_Lib.getBlockIdY((Entity)this, 3, -2) == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canUpdatePitch(Entity player) {
/* 1214 */     return (getCountOnUpdate() >= 30 && MCH_Lib.getBlockIdY((Entity)this, 3, -2) == 0);
/*      */   }
/*      */   
/*      */   public boolean canUpdateRoll(Entity player) {
/* 1218 */     if (getRidingEntity() != null) {
/* 1219 */       return false;
/*      */     }
/* 1221 */     return (getCountOnUpdate() >= 30 && MCH_Lib.getBlockIdY((Entity)this, 3, -2) == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOverridePlayerYaw() {
/* 1226 */     return !isFreeLookMode();
/*      */   }
/*      */   
/*      */   public boolean isOverridePlayerPitch() {
/* 1230 */     return !isFreeLookMode();
/*      */   }
/*      */   
/*      */   public double getAddRotationYawLimit() {
/* 1234 */     return (getAcInfo() != null) ? (40.0D * (getAcInfo()).mobilityYaw) : 40.0D;
/*      */   }
/*      */   
/*      */   public double getAddRotationPitchLimit() {
/* 1238 */     return (getAcInfo() != null) ? (40.0D * (getAcInfo()).mobilityPitch) : 40.0D;
/*      */   }
/*      */   
/*      */   public double getAddRotationRollLimit() {
/* 1242 */     return (getAcInfo() != null) ? (40.0D * (getAcInfo()).mobilityRoll) : 40.0D;
/*      */   }
/*      */   
/*      */   public float getYawFactor() {
/* 1246 */     return 1.0F;
/*      */   }
/*      */   
/*      */   public float getPitchFactor() {
/* 1250 */     return 1.0F;
/*      */   }
/*      */   
/*      */   public float getRollFactor() {
/* 1254 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getControlRotYaw(float mouseX, float mouseY, float tick) {
/* 1260 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public float getControlRotPitch(float mouseX, float mouseY, float tick) {
/* 1264 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public float getControlRotRoll(float mouseX, float mouseY, float tick) {
/* 1268 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public void setAngles(Entity player, boolean fixRot, float fixYaw, float fixPitch, float deltaX, float deltaY, float x, float y, float partialTicks) {
/* 1272 */     if (partialTicks < 0.03F) {
/* 1273 */       partialTicks = 0.4F;
/*      */     }
/*      */     
/* 1276 */     if (partialTicks > 0.9F) {
/* 1277 */       partialTicks = 0.6F;
/*      */     }
/*      */     
/* 1280 */     this.lowPassPartialTicks.put(partialTicks);
/* 1281 */     partialTicks = this.lowPassPartialTicks.getAvg();
/* 1282 */     float ac_pitch = getRotPitch();
/* 1283 */     float ac_yaw = getRotYaw();
/* 1284 */     float ac_roll = getRotRoll();
/* 1285 */     if (isFreeLookMode()) {
/* 1286 */       y = 0.0F;
/* 1287 */       x = 0.0F;
/*      */     } 
/*      */     
/* 1290 */     float yaw = 0.0F;
/* 1291 */     float pitch = 0.0F;
/* 1292 */     float roll = 0.0F;
/* 1293 */     if (canUpdateYaw(player)) {
/* 1294 */       double limit = getAddRotationYawLimit();
/* 1295 */       yaw = getControlRotYaw(x, y, partialTicks);
/* 1296 */       if (yaw < -limit) {
/* 1297 */         yaw = (float)-limit;
/*      */       }
/*      */       
/* 1300 */       if (yaw > limit) {
/* 1301 */         yaw = (float)limit;
/*      */       }
/*      */       
/* 1304 */       yaw = (float)((yaw * getYawFactor()) * 0.06D * partialTicks);
/*      */     } 
/*      */     
/* 1307 */     if (canUpdatePitch(player)) {
/* 1308 */       double limitx = getAddRotationPitchLimit();
/* 1309 */       pitch = getControlRotPitch(x, y, partialTicks);
/* 1310 */       if (pitch < -limitx) {
/* 1311 */         pitch = (float)-limitx;
/*      */       }
/*      */       
/* 1314 */       if (pitch > limitx) {
/* 1315 */         pitch = (float)limitx;
/*      */       }
/*      */       
/* 1318 */       pitch = (float)((-pitch * getPitchFactor()) * 0.06D * partialTicks);
/*      */     } 
/*      */     
/* 1321 */     if (canUpdateRoll(player)) {
/* 1322 */       double limitxx = getAddRotationRollLimit();
/* 1323 */       roll = getControlRotRoll(x, y, partialTicks);
/* 1324 */       if (roll < -limitxx) {
/* 1325 */         roll = (float)-limitxx;
/*      */       }
/*      */       
/* 1328 */       if (roll > limitxx) {
/* 1329 */         roll = (float)limitxx;
/*      */       }
/*      */       
/* 1332 */       roll = roll * getRollFactor() * 0.06F * partialTicks;
/*      */     } 
/*      */     
/* 1335 */     MCH_Math.FMatrix m_add = MCH_Math.newMatrix();
/* 1336 */     MCH_Math.MatTurnZ(m_add, roll / 180.0F * 3.1415927F);
/* 1337 */     MCH_Math.MatTurnX(m_add, pitch / 180.0F * 3.1415927F);
/* 1338 */     MCH_Math.MatTurnY(m_add, yaw / 180.0F * 3.1415927F);
/* 1339 */     MCH_Math.MatTurnZ(m_add, (float)((getRotRoll() / 180.0F) * Math.PI));
/* 1340 */     MCH_Math.MatTurnX(m_add, (float)((getRotPitch() / 180.0F) * Math.PI));
/* 1341 */     MCH_Math.MatTurnY(m_add, (float)((getRotYaw() / 180.0F) * Math.PI));
/* 1342 */     MCH_Math.FVector3D v = MCH_Math.MatrixToEuler(m_add);
/* 1343 */     if ((getAcInfo()).limitRotation) {
/* 1344 */       v.x = MCH_Lib.RNG(v.x, (getAcInfo()).minRotationPitch, (getAcInfo()).maxRotationPitch);
/* 1345 */       v.z = MCH_Lib.RNG(v.z, (getAcInfo()).minRotationRoll, (getAcInfo()).maxRotationRoll);
/*      */     } 
/*      */     
/* 1348 */     if (v.z > 180.0F) {
/* 1349 */       v.z -= 360.0F;
/*      */     }
/*      */     
/* 1352 */     if (v.z < -180.0F) {
/* 1353 */       v.z += 360.0F;
/*      */     }
/*      */     
/* 1356 */     setRotYaw(v.y);
/* 1357 */     setRotPitch(v.x);
/* 1358 */     setRotRoll(v.z);
/* 1359 */     onUpdateAngles(partialTicks);
/* 1360 */     if ((getAcInfo()).limitRotation) {
/* 1361 */       v.x = MCH_Lib.RNG(getRotPitch(), (getAcInfo()).minRotationPitch, (getAcInfo()).maxRotationPitch);
/* 1362 */       v.z = MCH_Lib.RNG(getRotRoll(), (getAcInfo()).minRotationRoll, (getAcInfo()).maxRotationRoll);
/* 1363 */       setRotPitch(v.x);
/* 1364 */       setRotRoll(v.z);
/*      */     } 
/*      */     
/* 1367 */     if (MathHelper.abs(getRotPitch()) > 90.0F) {
/* 1368 */       MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setAngles Error:Pitch=%.1f", new Object[] { Float.valueOf(getRotPitch()) });
/*      */     }
/*      */     
/* 1371 */     if (getRotRoll() > 180.0F) {
/* 1372 */       setRotRoll(getRotRoll() - 360.0F);
/*      */     }
/*      */     
/* 1375 */     if (getRotRoll() < -180.0F) {
/* 1376 */       setRotRoll(getRotRoll() + 360.0F);
/*      */     }
/*      */     
/* 1379 */     this.prevRotationRoll = getRotRoll();
/* 1380 */     this.prevRotationPitch = getRotPitch();
/* 1381 */     if (getRidingEntity() == null) {
/* 1382 */       this.prevRotationYaw = getRotYaw();
/*      */     }
/*      */     
/* 1385 */     if (!isOverridePlayerYaw() && !fixRot) {
/* 1386 */       player.turn(deltaX, 0.0F);
/*      */     } else {
/* 1388 */       if (getRidingEntity() == null) {
/* 1389 */         player.prevRotationYaw = getRotYaw() + (fixRot ? fixYaw : 0.0F);
/*      */       } else {
/* 1391 */         if (getRotYaw() - player.rotationYaw > 180.0F) {
/* 1392 */           player.prevRotationYaw += 360.0F;
/*      */         }
/*      */         
/* 1395 */         if (getRotYaw() - player.rotationYaw < -180.0F) {
/* 1396 */           player.prevRotationYaw -= 360.0F;
/*      */         }
/*      */       } 
/*      */       
/* 1400 */       player.rotationYaw = getRotYaw() + (fixRot ? fixYaw : 0.0F);
/*      */     } 
/*      */     
/* 1403 */     if (!isOverridePlayerPitch() && !fixRot) {
/* 1404 */       player.turn(0.0F, deltaY);
/*      */     } else {
/* 1406 */       player.prevRotationPitch = getRotPitch() + (fixRot ? fixPitch : 0.0F);
/* 1407 */       player.rotationPitch = getRotPitch() + (fixRot ? fixPitch : 0.0F);
/*      */     } 
/*      */     
/* 1410 */     if ((getRidingEntity() == null && ac_yaw != getRotYaw()) || ac_pitch != getRotPitch() || ac_roll != getRotRoll()) {
/* 1411 */       this.aircraftRotChanged = true;
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean canSwitchSearchLight(Entity entity) {
/* 1416 */     return (haveSearchLight() && getSeatIdByEntity(entity) <= 1);
/*      */   }
/*      */   
/*      */   public boolean isSearchLightON() {
/* 1420 */     return getCommonStatus(6);
/*      */   }
/*      */   
/*      */   public void setSearchLight(boolean onoff) {
/* 1424 */     setCommonStatus(6, onoff);
/*      */   }
/*      */   
/*      */   public boolean haveSearchLight() {
/* 1428 */     return (getAcInfo() != null && !(getAcInfo()).searchLights.isEmpty());
/*      */   }
/*      */   
/*      */   public float getSearchLightValue(Entity entity) {
/* 1432 */     if (haveSearchLight() && isSearchLightON()) {
/* 1433 */       for (MCH_AircraftInfo.SearchLight sl : (getAcInfo()).searchLights) {
/* 1434 */         Vec3d pos = getTransformedPosition(sl.pos);
/* 1435 */         double dist = entity.getDistanceSq(pos.x, pos.y, pos.z);
/* 1436 */         if (dist > 2.0D && dist < (sl.height * sl.height + 20.0F)) {
/* 1437 */           double h, v, cx = entity.posX - pos.x;
/* 1438 */           double cy = entity.posY - pos.y;
/* 1439 */           double cz = entity.posZ - pos.z;
/*      */ 
/*      */           
/* 1442 */           if (!sl.fixDir) {
/* 1443 */             Vec3d vv = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -this.lastSearchLightYaw + sl.yaw, -this.lastSearchLightPitch + sl.pitch, -getRotRoll());
/* 1444 */             h = MCH_Lib.getPosAngle(vv.x, vv.z, cx, cz);
/* 1445 */             v = Math.atan2(cy, Math.sqrt(cx * cx + cz * cz)) * 180.0D / Math.PI;
/* 1446 */             v = Math.abs(v + this.lastSearchLightPitch + sl.pitch);
/*      */           } else {
/* 1448 */             float stRot = 0.0F;
/* 1449 */             if (sl.steering) {
/* 1450 */               stRot = this.rotYawWheel * sl.stRot;
/*      */             }
/*      */             
/* 1453 */             Vec3d vv = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -getRotYaw() + sl.yaw + stRot, -getRotPitch() + sl.pitch, -getRotRoll());
/* 1454 */             h = MCH_Lib.getPosAngle(vv.x, vv.z, cx, cz);
/* 1455 */             v = Math.atan2(cy, Math.sqrt(cx * cx + cz * cz)) * 180.0D / Math.PI;
/* 1456 */             v = Math.abs(v + getRotPitch() + sl.pitch);
/*      */           } 
/*      */           
/* 1459 */           float angle = sl.angle * 3.0F;
/* 1460 */           if (h < angle && v < angle) {
/* 1461 */             float value = 0.0F;
/* 1462 */             if (h + v < angle) {
/* 1463 */               value = (float)(1440.0D * (1.0D - (h + v) / angle));
/*      */             }
/*      */             
/* 1466 */             return Math.min(value, 240.0F);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1472 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/* 1478 */     if (getCountOnUpdate() < 2) {
/* 1479 */       this.prevPosition.clear(new Vec3d(this.posX, this.posY, this.posZ));
/*      */     }
/*      */     
/* 1482 */     this.prevCurrentThrottle = getCurrentThrottle();
/* 1483 */     this.lastBBDamageFactor = 1.0F;
/* 1484 */     updateControl();
/* 1485 */     checkServerNoMove();
/* 1486 */     onUpdate_RidingEntity();
/* 1487 */     Iterator<UnmountReserve> itr = this.listUnmountReserve.iterator();
/*      */     
/* 1489 */     while (itr.hasNext()) {
/* 1490 */       UnmountReserve ur = itr.next();
/* 1491 */       if (ur.entity != null && !ur.entity.isDead) {
/* 1492 */         ur.entity.setPosition(ur.posX, ur.posY, ur.posZ);
/* 1493 */         ur.entity.fallDistance = this.fallDistance;
/*      */       } 
/*      */       
/* 1496 */       if (ur.cnt > 0) {
/* 1497 */         ur.cnt--;
/*      */       }
/*      */       
/* 1500 */       if (ur.cnt == 0) {
/* 1501 */         itr.remove();
/*      */       }
/*      */     } 
/*      */     
/* 1505 */     if (isDestroyed() && getCountOnUpdate() % 20 == 0) {
/* 1506 */       for (int sid = 0; sid < getSeatNum() + 1; sid++) {
/* 1507 */         Entity entity = getEntityBySeatId(sid);
/* 1508 */         if (entity != null && (sid != 0 || !isUAV()) && MCH_Config.applyDamageVsEntity(entity, DamageSource.IN_FIRE, 1.0F) > 0.0F) {
/* 1509 */           entity.setFire(5);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1514 */     if ((this.aircraftRotChanged || this.aircraftRollRev) && this.world.isRemote && getRiddenByEntity() != null) {
/* 1515 */       MCH_PacketIndRotation.send(this);
/* 1516 */       this.aircraftRotChanged = false;
/* 1517 */       this.aircraftRollRev = false;
/*      */     } 
/*      */     
/* 1520 */     if (!this.world.isRemote && (int)this.prevRotationRoll != (int)getRotRoll()) {
/* 1521 */       float roll = MathHelper.wrapDegrees(getRotRoll());
/* 1522 */       this.dataManager.set(ROT_ROLL, Integer.valueOf((int)roll));
/*      */     } 
/*      */     
/* 1525 */     this.prevRotationRoll = getRotRoll();
/* 1526 */     if (!this.world.isRemote && isTargetDrone() && !isDestroyed() && getCountOnUpdate() > 20 && !canUseFuel()) {
/* 1527 */       setDamageTaken(getMaxHP());
/* 1528 */       destroyAircraft();
/* 1529 */       MCH_Explosion.newExplosion(this.world, null, null, this.posX, this.posY, this.posZ, 2.0F, 2.0F, true, true, true, true, 5);
/*      */     } 
/*      */     
/* 1532 */     if (this.world.isRemote && getAcInfo() != null && getHP() <= 0 && getDespawnCount() <= 0) {
/* 1533 */       destroyAircraft();
/*      */     }
/*      */     
/* 1536 */     if (!this.world.isRemote && getDespawnCount() > 0) {
/* 1537 */       setDespawnCount(getDespawnCount() - 1);
/* 1538 */       if (getDespawnCount() <= 1) {
/* 1539 */         setDead(true);
/*      */       }
/*      */     } 
/*      */     
/* 1543 */     super.onUpdate();
/* 1544 */     if (getParts() != null) {
/* 1545 */       for (Entity e : getParts()) {
/* 1546 */         if (e != null) {
/* 1547 */           e.onUpdate();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1552 */     updateNoCollisionEntities();
/* 1553 */     updateUAV();
/* 1554 */     supplyFuel();
/* 1555 */     supplyAmmoToOtherAircraft();
/* 1556 */     updateFuel();
/* 1557 */     repairOtherAircraft();
/* 1558 */     if (this.modeSwitchCooldown > 0) {
/* 1559 */       this.modeSwitchCooldown--;
/*      */     }
/*      */     
/* 1562 */     if (this.lastRiddenByEntity == null && getRiddenByEntity() != null) {
/* 1563 */       onRidePilotFirstUpdate();
/*      */     }
/*      */     
/* 1566 */     if (this.countOnUpdate == 0) {
/* 1567 */       onFirstUpdate();
/*      */     }
/*      */     
/* 1570 */     this.countOnUpdate++;
/* 1571 */     if (this.countOnUpdate >= 1000000) {
/* 1572 */       this.countOnUpdate = 1;
/*      */     }
/*      */     
/* 1575 */     if (this.world.isRemote) {
/* 1576 */       this.commonStatus = ((Integer)this.dataManager.get(STATUS)).intValue();
/*      */     }
/*      */     
/* 1579 */     this.fallDistance = 0.0F;
/* 1580 */     Entity riddenByEntity = getRiddenByEntity();
/* 1581 */     if (riddenByEntity != null) {
/* 1582 */       riddenByEntity.fallDistance = 0.0F;
/*      */     }
/*      */     
/* 1585 */     if (this.missileDetector != null) {
/* 1586 */       this.missileDetector.update();
/*      */     }
/*      */     
/* 1589 */     if (this.soundUpdater != null) {
/* 1590 */       this.soundUpdater.update();
/*      */     }
/*      */     
/* 1593 */     if (getTowChainEntity() != null && (getTowChainEntity()).isDead) {
/* 1594 */       setTowChainEntity((MCH_EntityChain)null);
/*      */     }
/*      */     
/* 1597 */     updateSupplyAmmo();
/* 1598 */     autoRepair();
/* 1599 */     int ft = getFlareTick();
/* 1600 */     this.flareDv.update();
/* 1601 */     if (!this.world.isRemote && getFlareTick() == 0 && ft != 0) {
/* 1602 */       setCommonStatus(0, false);
/*      */     }
/*      */     
/* 1605 */     Entity ex = getRiddenByEntity();
/* 1606 */     if (ex != null && !ex.isDead && !isDestroyed()) {
/* 1607 */       this.lastRiderYaw = ex.rotationYaw;
/* 1608 */       this.prevLastRiderYaw = ex.prevRotationYaw;
/* 1609 */       this.lastRiderPitch = ex.rotationPitch;
/* 1610 */       this.prevLastRiderPitch = ex.prevRotationPitch;
/* 1611 */     } else if (getTowedChainEntity() != null || getRidingEntity() != null) {
/* 1612 */       this.lastRiderYaw = this.rotationYaw;
/* 1613 */       this.prevLastRiderYaw = this.prevRotationYaw;
/* 1614 */       this.lastRiderPitch = this.rotationPitch;
/* 1615 */       this.prevLastRiderPitch = this.prevRotationPitch;
/*      */     } 
/*      */     
/* 1618 */     updatePartCameraRotate();
/* 1619 */     updatePartWheel();
/* 1620 */     updatePartCrawlerTrack();
/* 1621 */     updatePartLightHatch();
/* 1622 */     regenerationMob();
/* 1623 */     if (getRiddenByEntity() == null && this.lastRiddenByEntity != null) {
/* 1624 */       unmountEntity();
/*      */     }
/*      */     
/* 1627 */     updateExtraBoundingBox();
/* 1628 */     boolean prevOnGround = this.onGround;
/* 1629 */     double prevMotionY = this.motionY;
/* 1630 */     onUpdateAircraft();
/* 1631 */     if (getAcInfo() != null) {
/* 1632 */       updateParts(getPartStatus());
/*      */     }
/*      */     
/* 1635 */     if (this.recoilCount > 0) {
/* 1636 */       this.recoilCount--;
/*      */     }
/*      */     
/* 1639 */     if (!W_Entity.isEqual(MCH_MOD.proxy.getClientPlayer(), getRiddenByEntity())) {
/* 1640 */       updateRecoil(1.0F);
/*      */     }
/*      */     
/* 1643 */     if (!this.world.isRemote && isDestroyed() && !isExploded() && !prevOnGround && this.onGround && prevMotionY < -0.2D) {
/* 1644 */       explosionByCrash(prevMotionY);
/* 1645 */       this.damageSinceDestroyed = getMaxHP();
/*      */     } 
/*      */     
/* 1648 */     onUpdate_PartRotation();
/* 1649 */     onUpdate_ParticleSmoke();
/* 1650 */     updateSeatsPosition(this.posX, this.posY, this.posZ, false);
/* 1651 */     updateHitBoxPosition();
/* 1652 */     onUpdate_CollisionGroundDamage();
/* 1653 */     onUpdate_UnmountCrew();
/* 1654 */     onUpdate_Repelling();
/* 1655 */     checkRideRack();
/* 1656 */     if (this.lastRidingEntity == null && getRidingEntity() != null) {
/* 1657 */       onRideEntity(getRidingEntity());
/*      */     }
/*      */     
/* 1660 */     this.lastRiddenByEntity = getRiddenByEntity();
/* 1661 */     this.lastRidingEntity = getRidingEntity();
/* 1662 */     this.prevPosition.put(new Vec3d(this.posX, this.posY, this.posZ));
/*      */   }
/*      */   
/*      */   private void updateNoCollisionEntities() {
/* 1666 */     if (!this.world.isRemote && 
/* 1667 */       getCountOnUpdate() % 10 == 0) {
/* 1668 */       for (int i = 0; i < 1 + getSeatNum(); i++) {
/* 1669 */         Entity e = getEntityBySeatId(i);
/* 1670 */         if (e != null) {
/* 1671 */           this.noCollisionEntities.put(e, Integer.valueOf(8));
/*      */         }
/*      */       } 
/*      */       
/* 1675 */       if (getTowChainEntity() != null && (getTowChainEntity()).towedEntity != null) {
/* 1676 */         this.noCollisionEntities.put((getTowChainEntity()).towedEntity, Integer.valueOf(60));
/*      */       }
/*      */       
/* 1679 */       if (getTowedChainEntity() != null && (getTowedChainEntity()).towEntity != null) {
/* 1680 */         this.noCollisionEntities.put((getTowedChainEntity()).towEntity, Integer.valueOf(60));
/*      */       }
/*      */       
/* 1683 */       if (getRidingEntity() instanceof MCH_EntitySeat) {
/* 1684 */         MCH_EntityAircraft ac = ((MCH_EntitySeat)getRidingEntity()).getParent();
/* 1685 */         if (ac != null) {
/* 1686 */           this.noCollisionEntities.put(ac, Integer.valueOf(60));
/*      */         }
/* 1688 */       } else if (getRidingEntity() != null) {
/* 1689 */         this.noCollisionEntities.put(getRidingEntity(), Integer.valueOf(60));
/*      */       } 
/*      */       
/* 1692 */       this.noCollisionEntities.replaceAll((k, v) -> Integer.valueOf(((Integer)this.noCollisionEntities.get(k)).intValue() - 1));
/*      */       
/* 1694 */       this.noCollisionEntities.values().removeIf(integer -> (integer.intValue() <= 0));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateControl() {
/* 1700 */     if (!this.world.isRemote) {
/* 1701 */       setCommonStatus(7, this.moveLeft);
/* 1702 */       setCommonStatus(8, this.moveRight);
/* 1703 */       setCommonStatus(9, this.throttleUp);
/* 1704 */       setCommonStatus(10, this.throttleDown);
/* 1705 */     } else if (MCH_MOD.proxy.getClientPlayer() != getRiddenByEntity()) {
/* 1706 */       this.moveLeft = getCommonStatus(7);
/* 1707 */       this.moveRight = getCommonStatus(8);
/* 1708 */       this.throttleUp = getCommonStatus(9);
/* 1709 */       this.throttleDown = getCommonStatus(10);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void updateRecoil(float partialTicks) {
/* 1714 */     if (this.recoilCount > 0 && this.recoilCount >= 12) {
/* 1715 */       float pitch = MathHelper.cos((float)((this.recoilYaw - getRotRoll()) * Math.PI / 180.0D));
/* 1716 */       float roll = MathHelper.sin((float)((this.recoilYaw - getRotRoll()) * Math.PI / 180.0D));
/* 1717 */       float recoil = MathHelper.cos((float)((this.recoilCount * 6) * Math.PI / 180.0D)) * this.recoilValue;
/* 1718 */       setRotPitch(getRotPitch() + recoil * pitch * partialTicks);
/* 1719 */       setRotRoll(getRotRoll() + recoil * roll * partialTicks);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updatePartLightHatch() {
/* 1724 */     this.prevRotLightHatch = this.rotLightHatch;
/* 1725 */     if (isSearchLightON()) {
/* 1726 */       this.rotLightHatch = (float)(this.rotLightHatch + 0.5D);
/*      */     } else {
/* 1728 */       this.rotLightHatch = (float)(this.rotLightHatch - 0.5D);
/*      */     } 
/*      */     
/* 1731 */     if (this.rotLightHatch > 1.0F) {
/* 1732 */       this.rotLightHatch = 1.0F;
/*      */     }
/*      */     
/* 1735 */     if (this.rotLightHatch < 0.0F) {
/* 1736 */       this.rotLightHatch = 0.0F;
/*      */     }
/*      */   }
/*      */   
/*      */   public void updateExtraBoundingBox() {
/* 1741 */     for (MCH_BoundingBox bb : this.extraBoundingBox) {
/* 1742 */       bb.updatePosition(this.posX, this.posY, this.posZ, getRotYaw(), getRotPitch(), getRotRoll());
/*      */     }
/*      */   }
/*      */   
/*      */   public void updatePartWheel() {
/* 1747 */     if (this.world.isRemote && 
/* 1748 */       getAcInfo() != null) {
/* 1749 */       this.prevRotWheel = this.rotWheel;
/* 1750 */       this.prevRotYawWheel = this.rotYawWheel;
/* 1751 */       double throttle = getCurrentThrottle();
/* 1752 */       double pivotTurnThrottle = (getAcInfo()).pivotTurnThrottle;
/* 1753 */       if (pivotTurnThrottle <= 0.0D) {
/* 1754 */         pivotTurnThrottle = 1.0D;
/*      */       } else {
/* 1756 */         pivotTurnThrottle *= 0.10000000149011612D;
/*      */       } 
/*      */       
/* 1759 */       boolean localMoveLeft = this.moveLeft;
/* 1760 */       boolean localMoveRight = this.moveRight;
/* 1761 */       if ((getAcInfo()).enableBack && this.throttleBack > 0.01D && throttle <= 0.0D) {
/* 1762 */         throttle = (-this.throttleBack * 15.0F);
/*      */       }
/*      */       
/* 1765 */       if (localMoveLeft && !localMoveRight) {
/* 1766 */         this.rotYawWheel += 0.1F;
/* 1767 */         if (this.rotYawWheel > 1.0F) {
/* 1768 */           this.rotYawWheel = 1.0F;
/*      */         }
/* 1770 */       } else if (!localMoveLeft && localMoveRight) {
/* 1771 */         this.rotYawWheel -= 0.1F;
/* 1772 */         if (this.rotYawWheel < -1.0F) {
/* 1773 */           this.rotYawWheel = -1.0F;
/*      */         }
/*      */       } else {
/* 1776 */         this.rotYawWheel *= 0.9F;
/*      */       } 
/*      */       
/* 1779 */       this.rotWheel = (float)(this.rotWheel + throttle * (getAcInfo()).partWheelRot);
/* 1780 */       if (this.rotWheel >= 360.0F) {
/* 1781 */         this.rotWheel -= 360.0F;
/* 1782 */         this.prevRotWheel -= 360.0F;
/* 1783 */       } else if (this.rotWheel < 0.0F) {
/* 1784 */         this.rotWheel += 360.0F;
/* 1785 */         this.prevRotWheel += 360.0F;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updatePartCrawlerTrack() {
/* 1792 */     if (this.world.isRemote && 
/* 1793 */       getAcInfo() != null) {
/* 1794 */       this.prevRotTrackRoller[0] = this.rotTrackRoller[0];
/* 1795 */       this.prevRotTrackRoller[1] = this.rotTrackRoller[1];
/* 1796 */       this.prevRotCrawlerTrack[0] = this.rotCrawlerTrack[0];
/* 1797 */       this.prevRotCrawlerTrack[1] = this.rotCrawlerTrack[1];
/* 1798 */       double throttle = getCurrentThrottle();
/* 1799 */       double pivotTurnThrottle = (getAcInfo()).pivotTurnThrottle;
/* 1800 */       if (pivotTurnThrottle <= 0.0D) {
/* 1801 */         pivotTurnThrottle = 1.0D;
/*      */       } else {
/* 1803 */         pivotTurnThrottle *= 0.10000000149011612D;
/*      */       } 
/*      */       
/* 1806 */       boolean localMoveLeft = this.moveLeft;
/* 1807 */       boolean localMoveRight = this.moveRight;
/* 1808 */       int dir = 1;
/* 1809 */       if ((getAcInfo()).enableBack && this.throttleBack > 0.0F && throttle <= 0.0D) {
/* 1810 */         throttle = (-this.throttleBack * 5.0F);
/* 1811 */         if (localMoveLeft != localMoveRight) {
/* 1812 */           boolean tmp = localMoveLeft;
/* 1813 */           localMoveLeft = localMoveRight;
/* 1814 */           localMoveRight = tmp;
/* 1815 */           dir = -1;
/*      */         } 
/*      */       } 
/*      */       
/* 1819 */       if (localMoveLeft && !localMoveRight) {
/* 1820 */         throttle = 0.2D * dir;
/* 1821 */         int tmp203_202 = 0;
/* 1822 */         float[] tmp203_199 = this.throttleCrawlerTrack;
/* 1823 */         tmp203_199[tmp203_202] = (float)(tmp203_199[tmp203_202] + throttle);
/* 1824 */         int tmp215_214 = 1;
/* 1825 */         float[] tmp215_211 = this.throttleCrawlerTrack;
/* 1826 */         tmp215_211[tmp215_214] = (float)(tmp215_211[tmp215_214] - pivotTurnThrottle * throttle);
/* 1827 */       } else if (!localMoveLeft && localMoveRight) {
/* 1828 */         throttle = 0.2D * dir;
/* 1829 */         int tmp251_250 = 0;
/* 1830 */         float[] tmp251_247 = this.throttleCrawlerTrack;
/* 1831 */         tmp251_247[tmp251_250] = (float)(tmp251_247[tmp251_250] - pivotTurnThrottle * throttle);
/* 1832 */         int tmp266_265 = 1;
/* 1833 */         float[] tmp266_262 = this.throttleCrawlerTrack;
/* 1834 */         tmp266_262[tmp266_265] = (float)(tmp266_262[tmp266_265] + throttle);
/*      */       } else {
/* 1836 */         if (throttle > 0.2D) {
/* 1837 */           throttle = 0.2D;
/*      */         }
/*      */         
/* 1840 */         if (throttle < -0.2D) {
/* 1841 */           throttle = -0.2D;
/*      */         }
/*      */         
/* 1844 */         int tmp305_304 = 0;
/* 1845 */         float[] tmp305_301 = this.throttleCrawlerTrack;
/* 1846 */         tmp305_301[tmp305_304] = (float)(tmp305_301[tmp305_304] + throttle);
/* 1847 */         int tmp317_316 = 1;
/* 1848 */         float[] tmp317_313 = this.throttleCrawlerTrack;
/* 1849 */         tmp317_313[tmp317_316] = (float)(tmp317_313[tmp317_316] + throttle);
/*      */       } 
/*      */       
/* 1852 */       for (int i = 0; i < 2; i++) {
/* 1853 */         if (this.throttleCrawlerTrack[i] < -0.72F) {
/* 1854 */           this.throttleCrawlerTrack[i] = -0.72F;
/* 1855 */         } else if (this.throttleCrawlerTrack[i] > 0.72F) {
/* 1856 */           this.throttleCrawlerTrack[i] = 0.72F;
/*      */         } 
/*      */         
/* 1859 */         this.rotTrackRoller[i] = this.rotTrackRoller[i] + this.throttleCrawlerTrack[i] * (getAcInfo()).trackRollerRot;
/* 1860 */         if (this.rotTrackRoller[i] >= 360.0F) {
/* 1861 */           this.rotTrackRoller[i] = this.rotTrackRoller[i] - 360.0F;
/* 1862 */           this.prevRotTrackRoller[i] = this.prevRotTrackRoller[i] - 360.0F;
/* 1863 */         } else if (this.rotTrackRoller[i] < 0.0F) {
/* 1864 */           this.rotTrackRoller[i] = this.rotTrackRoller[i] + 360.0F;
/* 1865 */           this.prevRotTrackRoller[i] = this.prevRotTrackRoller[i] + 360.0F;
/*      */         } 
/*      */         
/* 1868 */         this.rotCrawlerTrack[i] = this.rotCrawlerTrack[i] - this.throttleCrawlerTrack[i];
/* 1869 */         for (; this.rotCrawlerTrack[i] >= 1.0F; 
/* 1870 */           this.prevRotCrawlerTrack[i] = this.prevRotCrawlerTrack[i] - 1.0F)
/*      */         {
/* 1872 */           this.rotCrawlerTrack[i] = this.rotCrawlerTrack[i] - 1.0F;
/*      */         }
/*      */         
/* 1875 */         while (this.rotCrawlerTrack[i] < 0.0F) {
/* 1876 */           this.rotCrawlerTrack[i] = this.rotCrawlerTrack[i] + 1.0F;
/*      */         }
/*      */         
/* 1879 */         while (this.prevRotCrawlerTrack[i] < 0.0F) {
/* 1880 */           this.prevRotCrawlerTrack[i] = this.prevRotCrawlerTrack[i] + 1.0F;
/*      */         }
/*      */         
/* 1883 */         float[] tmp602_597 = this.throttleCrawlerTrack;
/* 1884 */         tmp602_597[i] = (float)(tmp602_597[i] * 0.75D);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkServerNoMove() {
/* 1891 */     if (!this.world.isRemote) {
/* 1892 */       double moti = this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ;
/* 1893 */       if (moti < 1.0E-4D) {
/* 1894 */         if (this.serverNoMoveCount < 20) {
/* 1895 */           this.serverNoMoveCount++;
/* 1896 */           if (this.serverNoMoveCount >= 20) {
/* 1897 */             this.serverNoMoveCount = 0;
/* 1898 */             if (this.world instanceof WorldServer) {
/* 1899 */               ((WorldServer)this.world).getEntityTracker().sendToTracking((Entity)this, (Packet)new SPacketEntityVelocity(getEntityId(), 0.0D, 0.0D, 0.0D));
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1904 */         this.serverNoMoveCount = 0;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean haveRotPart() {
/* 1910 */     return (this.world.isRemote && 
/* 1911 */       getAcInfo() != null && this.rotPartRotation.length > 0 && this.rotPartRotation.length == 
/*      */       
/* 1913 */       (getAcInfo()).partRotPart.size());
/*      */   }
/*      */   
/*      */   public void onUpdate_PartRotation() {
/* 1917 */     if (haveRotPart()) {
/* 1918 */       for (int i = 0; i < this.rotPartRotation.length; i++) {
/* 1919 */         this.prevRotPartRotation[i] = this.rotPartRotation[i];
/* 1920 */         if ((!isDestroyed() && ((MCH_AircraftInfo.RotPart)(getAcInfo()).partRotPart.get(i)).rotAlways) || getRiddenByEntity() != null) {
/* 1921 */           this.rotPartRotation[i] = this.rotPartRotation[i] + ((MCH_AircraftInfo.RotPart)(getAcInfo()).partRotPart.get(i)).rotSpeed;
/* 1922 */           if (this.rotPartRotation[i] < 0.0F) {
/* 1923 */             this.rotPartRotation[i] = this.rotPartRotation[i] + 360.0F;
/*      */           }
/*      */           
/* 1926 */           if (this.rotPartRotation[i] >= 360.0F) {
/* 1927 */             this.rotPartRotation[i] = this.rotPartRotation[i] - 360.0F;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onRideEntity(Entity ridingEntity) {}
/*      */   
/*      */   public int getAlt(double px, double py, double pz) {
/* 1938 */     int i = 0;
/*      */     
/* 1940 */     while (i < 256 && py - i > 0.0D && (py - i >= 256.0D || 0 == W_WorldFunc.getBlockId(this.world, (int)px, (int)py - i, (int)pz))) {
/* 1941 */       i++;
/*      */     }
/*      */     
/* 1944 */     return i;
/*      */   }
/*      */   
/*      */   public boolean canRepelling(Entity entity) {
/* 1948 */     return (isRepelling() && this.tickRepelling > 50);
/*      */   }
/*      */   
/*      */   private void onUpdate_Repelling() {
/* 1952 */     if (getAcInfo() != null && getAcInfo().haveRepellingHook()) {
/* 1953 */       if (isRepelling()) {
/* 1954 */         int alt = getAlt(this.posX, this.posY, this.posZ);
/* 1955 */         if (this.ropesLength > -50.0F && this.ropesLength > -alt) {
/* 1956 */           this.ropesLength = (float)(this.ropesLength - (this.world.isRemote ? 0.30000001192092896D : 0.25D));
/*      */         }
/*      */       } else {
/* 1959 */         this.ropesLength = 0.0F;
/*      */       } 
/*      */     }
/*      */     
/* 1963 */     onUpdate_UnmountCrewRepelling();
/*      */   }
/*      */   
/*      */   private void onUpdate_UnmountCrewRepelling() {
/* 1967 */     if (getAcInfo() != null) {
/* 1968 */       if (!isRepelling()) {
/* 1969 */         this.tickRepelling = 0;
/* 1970 */       } else if (this.tickRepelling < 60) {
/* 1971 */         this.tickRepelling++;
/* 1972 */       } else if (!this.world.isRemote) {
/* 1973 */         for (int ropeIdx = 0; ropeIdx < (getAcInfo()).repellingHooks.size(); ropeIdx++) {
/* 1974 */           MCH_AircraftInfo.RepellingHook hook = (getAcInfo()).repellingHooks.get(ropeIdx);
/* 1975 */           if (getCountOnUpdate() % hook.interval == 0) {
/* 1976 */             for (int i = 1; i < getSeatNum(); i++) {
/* 1977 */               MCH_EntitySeat seat = getSeat(i);
/* 1978 */               if (seat != null && seat
/* 1979 */                 .getRiddenByEntity() != null && 
/* 1980 */                 !W_EntityPlayer.isPlayer(seat.getRiddenByEntity()) && 
/* 1981 */                 !(seat.getRiddenByEntity() instanceof MCH_EntityGunner) && 
/* 1982 */                 !(getSeatInfo(i + 1) instanceof MCH_SeatRackInfo)) {
/* 1983 */                 Entity entity = seat.getRiddenByEntity();
/* 1984 */                 Vec3d dropPos = getTransformedPosition(hook.pos, (Vec3d)this.prevPosition.oldest());
/* 1985 */                 seat.posX = dropPos.x;
/* 1986 */                 seat.posY = dropPos.y - 2.0D;
/* 1987 */                 seat.posZ = dropPos.z;
/* 1988 */                 entity.dismountRidingEntity();
/* 1989 */                 unmountEntityRepelling(entity, dropPos, ropeIdx);
/* 1990 */                 this.lastUsedRopeIndex = ropeIdx;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void unmountEntityRepelling(Entity entity, Vec3d dropPos, int ropeIdx) {
/* 2001 */     entity.posX = dropPos.x;
/* 2002 */     entity.posY = dropPos.y - 2.0D;
/* 2003 */     entity.posZ = dropPos.z;
/* 2004 */     MCH_EntityHide hideEntity = new MCH_EntityHide(this.world, entity.posX, entity.posY, entity.posZ);
/* 2005 */     hideEntity.setParent(this, entity, ropeIdx);
/* 2006 */     hideEntity.motionX = entity.motionX = 0.0D;
/* 2007 */     hideEntity.motionY = entity.motionY = 0.0D;
/* 2008 */     hideEntity.motionZ = entity.motionZ = 0.0D;
/* 2009 */     hideEntity.fallDistance = entity.fallDistance = 0.0F;
/* 2010 */     this.world.spawnEntity((Entity)hideEntity);
/*      */   }
/*      */   
/*      */   private void onUpdate_UnmountCrew() {
/* 2014 */     if (getAcInfo() != null && 
/* 2015 */       this.isParachuting) {
/* 2016 */       if (MCH_Lib.getBlockIdY((Entity)this, 3, -10) != 0) {
/* 2017 */         stopUnmountCrew();
/* 2018 */       } else if ((!haveHatch() || getHatchRotation() > 89.0F) && 
/* 2019 */         getCountOnUpdate() % (getAcInfo()).mobDropOption.interval == 0 && 
/* 2020 */         !unmountCrew(true)) {
/* 2021 */         stopUnmountCrew();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void unmountAircraft() {
/* 2028 */     Vec3d v = new Vec3d(this.posX, this.posY, this.posZ);
/* 2029 */     if (getRidingEntity() instanceof MCH_EntitySeat) {
/* 2030 */       MCH_EntityAircraft ac = ((MCH_EntitySeat)getRidingEntity()).getParent();
/* 2031 */       MCH_SeatInfo seatInfo = ac.getSeatInfo((Entity)this);
/* 2032 */       if (seatInfo instanceof MCH_SeatRackInfo) {
/* 2033 */         v = ((MCH_SeatRackInfo)seatInfo).getEntryPos();
/* 2034 */         v = ac.getTransformedPosition(v);
/*      */       } 
/* 2036 */     } else if (getRidingEntity() instanceof net.minecraft.entity.item.EntityMinecartEmpty) {
/* 2037 */       this.dismountedUserCtrl = true;
/*      */     } 
/*      */     
/* 2040 */     setLocationAndAngles(v.x, v.y, v.z, getRotYaw(), getRotPitch());
/* 2041 */     dismountRidingEntity();
/* 2042 */     setLocationAndAngles(v.x, v.y, v.z, getRotYaw(), getRotPitch());
/*      */   }
/*      */   
/*      */   public boolean canUnmount(Entity entity) {
/* 2046 */     if (getAcInfo() == null)
/* 2047 */       return false; 
/* 2048 */     if (!(getAcInfo()).isEnableParachuting) {
/* 2049 */       return false;
/*      */     }
/* 2051 */     return (getSeatIdByEntity(entity) > 1 && (!haveHatch() || getHatchRotation() >= 89.0F));
/*      */   }
/*      */ 
/*      */   
/*      */   public void unmount(Entity entity) {
/* 2056 */     if (getAcInfo() != null) {
/* 2057 */       if (canRepelling(entity) && getAcInfo().haveRepellingHook()) {
/* 2058 */         MCH_EntitySeat seat = getSeatByEntity(entity);
/* 2059 */         if (seat != null) {
/* 2060 */           this.lastUsedRopeIndex = (this.lastUsedRopeIndex + 1) % (getAcInfo()).repellingHooks.size();
/* 2061 */           Vec3d dropPos = getTransformedPosition(((MCH_AircraftInfo.RepellingHook)(getAcInfo()).repellingHooks.get(this.lastUsedRopeIndex)).pos, (Vec3d)this.prevPosition.oldest());
/* 2062 */           dropPos = dropPos.addVector(0.0D, -2.0D, 0.0D);
/* 2063 */           seat.posX = dropPos.x;
/* 2064 */           seat.posY = dropPos.y;
/* 2065 */           seat.posZ = dropPos.z;
/* 2066 */           entity.dismountRidingEntity();
/* 2067 */           entity.posX = dropPos.x;
/* 2068 */           entity.posY = dropPos.y;
/* 2069 */           entity.posZ = dropPos.z;
/* 2070 */           unmountEntityRepelling(entity, dropPos, this.lastUsedRopeIndex);
/*      */         } else {
/* 2072 */           MCH_Lib.Log((Entity)this, "Error:MCH_EntityAircraft.unmount seat=null : " + entity, new Object[0]);
/*      */         } 
/* 2074 */       } else if (canUnmount(entity)) {
/* 2075 */         MCH_EntitySeat seat = getSeatByEntity(entity);
/* 2076 */         if (seat != null) {
/* 2077 */           Vec3d dropPos = getTransformedPosition((getAcInfo()).mobDropOption.pos, (Vec3d)this.prevPosition.oldest());
/* 2078 */           seat.posX = dropPos.x;
/* 2079 */           seat.posY = dropPos.y;
/* 2080 */           seat.posZ = dropPos.z;
/* 2081 */           entity.dismountRidingEntity();
/* 2082 */           entity.posX = dropPos.x;
/* 2083 */           entity.posY = dropPos.y;
/* 2084 */           entity.posZ = dropPos.z;
/* 2085 */           dropEntityParachute(entity);
/*      */         } else {
/* 2087 */           MCH_Lib.Log((Entity)this, "Error:MCH_EntityAircraft.unmount seat=null : " + entity, new Object[0]);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean canParachuting(Entity entity) {
/* 2094 */     if (getAcInfo() == null || !(getAcInfo()).isEnableParachuting || getSeatIdByEntity(entity) <= 1 || MCH_Lib.getBlockIdY((Entity)this, 3, -13) != 0) {
/* 2095 */       return false;
/*      */     }
/* 2097 */     return (getSeatIdByEntity(entity) > 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdate_RidingEntity() {
/* 2102 */     if (!this.world.isRemote && this.waitMountEntity == 0 && getCountOnUpdate() > 20 && canMountWithNearEmptyMinecart()) {
/* 2103 */       mountWithNearEmptyMinecart();
/*      */     }
/*      */     
/* 2106 */     if (this.waitMountEntity > 0) {
/* 2107 */       this.waitMountEntity--;
/*      */     }
/*      */     
/* 2110 */     if (!this.world.isRemote && getRidingEntity() != null) {
/* 2111 */       setRotRoll(getRotRoll() * 0.9F);
/* 2112 */       setRotPitch(getRotPitch() * 0.95F);
/* 2113 */       Entity re = getRidingEntity();
/* 2114 */       float target = MathHelper.wrapDegrees(re.rotationYaw + 90.0F);
/* 2115 */       if (target - this.rotationYaw > 180.0F) {
/* 2116 */         target -= 360.0F;
/*      */       }
/*      */       
/* 2119 */       if (target - this.rotationYaw < -180.0F) {
/* 2120 */         target += 360.0F;
/*      */       }
/*      */       
/* 2123 */       if (this.ticksExisted % 2 == 0);
/*      */ 
/*      */       
/* 2126 */       float dist = 50.0F * (float)re.getDistanceSq(re.prevPosX, re.prevPosY, re.prevPosZ);
/* 2127 */       if (dist > 0.001D) {
/* 2128 */         dist = MathHelper.sqrt(dist);
/* 2129 */         float distYaw = MCH_Lib.RNG(target - this.rotationYaw, -dist, dist);
/* 2130 */         this.rotationYaw += distYaw;
/*      */       } 
/*      */       
/* 2133 */       double bkPosX = this.posX;
/* 2134 */       double bkPosY = this.posY;
/* 2135 */       double bkPosZ = this.posZ;
/* 2136 */       if ((getRidingEntity()).isDead) {
/* 2137 */         dismountRidingEntity();
/* 2138 */         this.waitMountEntity = 20;
/* 2139 */       } else if (getCurrentThrottle() > 0.8D) {
/* 2140 */         this.motionX = (getRidingEntity()).motionX;
/* 2141 */         this.motionY = (getRidingEntity()).motionY;
/* 2142 */         this.motionZ = (getRidingEntity()).motionZ;
/* 2143 */         dismountRidingEntity();
/* 2144 */         this.waitMountEntity = 20;
/*      */       } 
/*      */       
/* 2147 */       this.posX = bkPosX;
/* 2148 */       this.posY = bkPosY;
/* 2149 */       this.posZ = bkPosZ;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void explosionByCrash(double prevMotionY) {
/* 2154 */     float exp = (getAcInfo() != null) ? ((getAcInfo()).maxFuel / 400.0F) : 2.0F;
/* 2155 */     if (exp < 1.0F) {
/* 2156 */       exp = 1.0F;
/*      */     }
/*      */     
/* 2159 */     if (exp > 15.0F) {
/* 2160 */       exp = 15.0F;
/*      */     }
/*      */     
/* 2163 */     MCH_Lib.DbgLog(this.world, "OnGroundAfterDestroyed:motionY=%.3f", new Object[] { Float.valueOf((float)prevMotionY) });
/* 2164 */     MCH_Explosion.newExplosion(this.world, null, null, this.posX, this.posY, this.posZ, exp, (exp >= 2.0F) ? (exp * 0.5F) : 1.0F, true, true, true, true, 5);
/*      */   }
/*      */   
/*      */   public void onUpdate_CollisionGroundDamage() {
/* 2168 */     if (!isDestroyed()) {
/* 2169 */       if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) > 0 && !this.world.isRemote) {
/* 2170 */         float roll = MathHelper.abs(MathHelper.wrapDegrees(getRotRoll()));
/* 2171 */         float pitch = MathHelper.abs(MathHelper.wrapDegrees(getRotPitch()));
/* 2172 */         if (roll > getGiveDamageRot() || pitch > getGiveDamageRot()) {
/* 2173 */           float dmg = MathHelper.abs(roll) + MathHelper.abs(pitch);
/* 2174 */           if (dmg < 90.0F) {
/* 2175 */             dmg *= 0.4F * (float)getDistance(this.prevPosX, this.prevPosY, this.prevPosZ);
/*      */           } else {
/* 2177 */             dmg *= 0.4F;
/*      */           } 
/*      */           
/* 2180 */           if (dmg > 1.0F && this.rand.nextInt(4) == 0) {
/* 2181 */             attackEntityFrom(DamageSource.IN_WALL, dmg);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 2186 */       if (getCountOnUpdate() % 30 == 0 && (
/* 2187 */         getAcInfo() == null || !(getAcInfo()).isFloat) && 
/* 2188 */         MCH_Lib.isBlockInWater(this.world, (int)(this.posX + 0.5D), 
/* 2189 */           (int)(this.posY + 1.5D + (getAcInfo()).submergedDamageHeight), (int)(this.posZ + 0.5D))) {
/*      */         
/* 2191 */         int hp = getMaxHP() / 10;
/* 2192 */         if (hp <= 0) {
/* 2193 */           hp = 1;
/*      */         }
/*      */         
/* 2196 */         attackEntityFrom(DamageSource.IN_WALL, hp);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getGiveDamageRot() {
/* 2202 */     return 40.0F;
/*      */   }
/*      */   
/*      */   public void applyServerPositionAndRotation() {
/* 2206 */     double rpinc = this.aircraftPosRotInc;
/* 2207 */     double yaw = MathHelper.wrapDegrees(this.aircraftYaw - getRotYaw());
/* 2208 */     double roll = MathHelper.wrapDegrees(getServerRoll() - getRotRoll());
/* 2209 */     if (!isDestroyed() && (!W_Lib.isClientPlayer(getRiddenByEntity()) || getRidingEntity() != null)) {
/* 2210 */       setRotYaw((float)(getRotYaw() + yaw / rpinc));
/* 2211 */       setRotPitch((float)(getRotPitch() + (this.aircraftPitch - getRotPitch()) / rpinc));
/* 2212 */       setRotRoll((float)(getRotRoll() + roll / rpinc));
/*      */     } 
/*      */     
/* 2215 */     setPosition(this.posX + (this.aircraftX - this.posX) / rpinc, this.posY + (this.aircraftY - this.posY) / rpinc, this.posZ + (this.aircraftZ - this.posZ) / rpinc);
/*      */ 
/*      */     
/* 2218 */     setRotation(getRotYaw(), getRotPitch());
/* 2219 */     this.aircraftPosRotInc--;
/*      */   }
/*      */   
/*      */   protected void autoRepair() {
/* 2223 */     if (this.timeSinceHit > 0) {
/* 2224 */       this.timeSinceHit--;
/*      */     }
/*      */     
/* 2227 */     if (getMaxHP() > 0) {
/* 2228 */       if (!isDestroyed()) {
/* 2229 */         if (getDamageTaken() > this.beforeDamageTaken) {
/* 2230 */           this.repairCount = 600;
/* 2231 */         } else if (this.repairCount > 0) {
/* 2232 */           this.repairCount--;
/*      */         } else {
/* 2234 */           this.repairCount = 40;
/* 2235 */           double hpp = getHP() / getMaxHP();
/* 2236 */           if (hpp >= MCH_Config.AutoRepairHP.prmDouble) {
/* 2237 */             repair(getMaxHP() / 100);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 2242 */       this.beforeDamageTaken = getDamageTaken();
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean repair(int tpd) {
/* 2247 */     if (tpd < 1) {
/* 2248 */       tpd = 1;
/*      */     }
/*      */     
/* 2251 */     int damage = getDamageTaken();
/* 2252 */     if (damage > 0) {
/* 2253 */       if (!this.world.isRemote) {
/* 2254 */         setDamageTaken(damage - tpd);
/*      */       }
/*      */       
/* 2257 */       return true;
/*      */     } 
/* 2259 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void repairOtherAircraft() {
/* 2264 */     float range = (getAcInfo() != null) ? (getAcInfo()).repairOtherVehiclesRange : 0.0F;
/* 2265 */     if (range > 0.0F && 
/* 2266 */       !this.world.isRemote && getCountOnUpdate() % 20 == 0) {
/*      */       
/* 2268 */       List<MCH_EntityAircraft> list = this.world.getEntitiesWithinAABB(MCH_EntityAircraft.class, getCollisionBoundingBox().grow(range, range, range));
/*      */       
/* 2270 */       for (MCH_EntityAircraft ac : list) {
/* 2271 */         if (!W_Entity.isEqual((Entity)this, (Entity)ac) && ac.getHP() < ac.getMaxHP()) {
/* 2272 */           ac.setDamageTaken(ac.getDamageTaken() - (getAcInfo()).repairOtherVehiclesValue);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void regenerationMob() {
/* 2280 */     if (!isDestroyed() && 
/* 2281 */       !this.world.isRemote && 
/* 2282 */       getAcInfo() != null && (getAcInfo()).regeneration && getRiddenByEntity() != null) {
/* 2283 */       MCH_EntitySeat[] st = getSeats();
/*      */       
/* 2285 */       for (MCH_EntitySeat s : st) {
/* 2286 */         if (s != null && !s.isDead) {
/* 2287 */           Entity e = s.getRiddenByEntity();
/* 2288 */           if (W_Lib.isEntityLivingBase(e) && !e.isDead) {
/* 2289 */             PotionEffect pe = W_Entity.getActivePotionEffect(e, MobEffects.REGENERATION);
/* 2290 */             if (pe == null || pe.getDuration() < 500) {
/* 2291 */               W_Entity.addPotionEffect(e, new PotionEffect(MobEffects.REGENERATION, 250, 0, true, true));
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getWaterDepth() {
/* 2302 */     byte b0 = 5;
/* 2303 */     double d0 = 0.0D;
/*      */     
/* 2305 */     for (int i = 0; i < b0; i++) {
/* 2306 */       double d1 = (getEntityBoundingBox()).minY + ((getEntityBoundingBox()).maxY - (getEntityBoundingBox()).minY) * i / b0 - 0.125D;
/* 2307 */       double d2 = (getEntityBoundingBox()).minY + ((getEntityBoundingBox()).maxY - (getEntityBoundingBox()).minY) * (i + 1) / b0 - 0.125D;
/* 2308 */       d1 += (getAcInfo()).floatOffset;
/* 2309 */       d2 += (getAcInfo()).floatOffset;
/* 2310 */       AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(
/* 2311 */           (getEntityBoundingBox()).minX, d1, (getEntityBoundingBox()).minZ, (getEntityBoundingBox()).maxX, d2, (getEntityBoundingBox()).maxZ);
/*      */       
/* 2313 */       if (this.world.isMaterialInBB(axisalignedbb, Material.WATER)) {
/* 2314 */         d0 += 1.0D / b0;
/*      */       }
/*      */     } 
/*      */     
/* 2318 */     return d0;
/*      */   }
/*      */   
/*      */   public int getCountOnUpdate() {
/* 2322 */     return this.countOnUpdate;
/*      */   }
/*      */   
/*      */   public boolean canSupply() {
/* 2326 */     return canFloatWater() ? ((MCH_Lib.getBlockIdY((Entity)this, 1, -3) != 0)) : ((MCH_Lib.getBlockIdY((Entity)this, 1, -3) != 0 && !isInWater()));
/*      */   }
/*      */   
/*      */   public int getFuel() {
/* 2330 */     return ((Integer)this.dataManager.get(FUEL)).intValue();
/*      */   }
/*      */   
/*      */   public void setFuel(int fuel) {
/* 2334 */     if (!this.world.isRemote) {
/* 2335 */       if (fuel < 0) {
/* 2336 */         fuel = 0;
/*      */       }
/*      */       
/* 2339 */       if (fuel > getMaxFuel()) {
/* 2340 */         fuel = getMaxFuel();
/*      */       }
/*      */       
/* 2343 */       if (fuel != getFuel()) {
/* 2344 */         this.dataManager.set(FUEL, Integer.valueOf(fuel));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getFuelP() {
/* 2350 */     int m = getMaxFuel();
/* 2351 */     return (m == 0) ? 0.0F : (getFuel() / m);
/*      */   }
/*      */   
/*      */   public boolean canUseFuel(boolean checkOtherSeet) {
/* 2355 */     return (getMaxFuel() <= 0 || getFuel() > 1 || isInfinityFuel(getRiddenByEntity(), checkOtherSeet));
/*      */   }
/*      */   
/*      */   public boolean canUseFuel() {
/* 2359 */     return canUseFuel(false);
/*      */   }
/*      */   
/*      */   public int getMaxFuel() {
/* 2363 */     return (getAcInfo() != null) ? (getAcInfo()).maxFuel : 0;
/*      */   }
/*      */   
/*      */   public void supplyFuel() {
/* 2367 */     float range = (getAcInfo() != null) ? (getAcInfo()).fuelSupplyRange : 0.0F;
/* 2368 */     if (range > 0.0F && 
/* 2369 */       !this.world.isRemote && getCountOnUpdate() % 10 == 0) {
/*      */       
/* 2371 */       List<MCH_EntityAircraft> list = this.world.getEntitiesWithinAABB(MCH_EntityAircraft.class, getCollisionBoundingBox().grow(range, range, range));
/*      */       
/* 2373 */       for (MCH_EntityAircraft ac : list) {
/* 2374 */         if (!W_Entity.isEqual((Entity)this, (Entity)ac)) {
/* 2375 */           if ((!this.onGround || ac.canSupply()) && ac.getFuel() < ac.getMaxFuel()) {
/* 2376 */             int fc = ac.getMaxFuel() - ac.getFuel();
/* 2377 */             if (fc > 30) {
/* 2378 */               fc = 30;
/*      */             }
/*      */             
/* 2381 */             ac.setFuel(ac.getFuel() + fc);
/*      */           } 
/*      */           
/* 2384 */           ac.fuelSuppliedCount = 40;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateFuel() {
/* 2392 */     if (getMaxFuel() != 0) {
/* 2393 */       if (this.fuelSuppliedCount > 0) {
/* 2394 */         this.fuelSuppliedCount--;
/*      */       }
/*      */       
/* 2397 */       if (!isDestroyed() && !this.world.isRemote) {
/* 2398 */         if (getCountOnUpdate() % 20 == 0 && getFuel() > 1 && getThrottle() > 0.0D && this.fuelSuppliedCount <= 0) {
/* 2399 */           double t = getThrottle() * 1.4D;
/* 2400 */           if (t > 1.0D) {
/* 2401 */             t = 1.0D;
/*      */           }
/*      */           
/* 2404 */           this.fuelConsumption += t * (getAcInfo()).fuelConsumption * getFuelConsumptionFactor();
/* 2405 */           if (this.fuelConsumption > 1.0D) {
/* 2406 */             int f = (int)this.fuelConsumption;
/* 2407 */             this.fuelConsumption -= f;
/* 2408 */             setFuel(getFuel() - f);
/*      */           } 
/*      */         } 
/*      */         
/* 2412 */         int curFuel = getFuel();
/* 2413 */         if (canSupply() && getCountOnUpdate() % 10 == 0 && curFuel < getMaxFuel()) {
/* 2414 */           for (int i = 0; i < 3; i++) {
/* 2415 */             if (curFuel < getMaxFuel()) {
/* 2416 */               ItemStack fuel = getGuiInventory().getFuelSlotItemStack(i);
/* 2417 */               if (!fuel.isEmpty() && fuel.getItem() instanceof MCH_ItemFuel && fuel.getMetadata() < fuel.getMaxDamage()) {
/* 2418 */                 int fc = getMaxFuel() - curFuel;
/* 2419 */                 if (fc > 100) {
/* 2420 */                   fc = 100;
/*      */                 }
/*      */                 
/* 2423 */                 if (fuel.getMetadata() > fuel.getMaxDamage() - fc) {
/* 2424 */                   fc = fuel.getMaxDamage() - fuel.getMetadata();
/*      */                 }
/*      */                 
/* 2427 */                 fuel.setItemDamage(fuel.getMetadata() + fc);
/* 2428 */                 curFuel += fc;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/* 2433 */           if (getFuel() != curFuel && getRiddenByEntity() instanceof EntityPlayerMP) {
/* 2434 */             MCH_CriteriaTriggers.SUPPLY_FUEL.trigger((EntityPlayerMP)getRiddenByEntity());
/*      */           }
/*      */           
/* 2437 */           setFuel(curFuel);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getFuelConsumptionFactor() {
/* 2444 */     return 1.0F;
/*      */   }
/*      */   
/*      */   public void updateSupplyAmmo() {
/* 2448 */     if (!this.world.isRemote) {
/*      */ 
/*      */       
/* 2451 */       boolean isReloading = (getRiddenByEntity() instanceof EntityPlayer && !(getRiddenByEntity()).isDead && ((EntityPlayer)getRiddenByEntity()).openContainer instanceof MCH_AircraftGuiContainer);
/*      */       
/* 2453 */       setCommonStatus(2, isReloading);
/* 2454 */       if (!isDestroyed() && this.beforeSupplyAmmo && !isReloading) {
/* 2455 */         reloadAllWeapon();
/* 2456 */         MCH_PacketNotifyAmmoNum.sendAllAmmoNum(this, null);
/*      */       } 
/*      */       
/* 2459 */       this.beforeSupplyAmmo = isReloading;
/*      */     } 
/*      */     
/* 2462 */     if (getCommonStatus(2)) {
/* 2463 */       this.supplyAmmoWait = 20;
/*      */     }
/*      */     
/* 2466 */     if (this.supplyAmmoWait > 0) {
/* 2467 */       this.supplyAmmoWait--;
/*      */     }
/*      */   }
/*      */   
/*      */   public void supplyAmmo(int weaponID) {
/* 2472 */     if (this.world.isRemote) {
/* 2473 */       MCH_WeaponSet ws = getWeapon(weaponID);
/* 2474 */       ws.supplyRestAllAmmo();
/*      */     } else {
/* 2476 */       if (getRiddenByEntity() instanceof EntityPlayerMP) {
/* 2477 */         MCH_CriteriaTriggers.SUPPLY_AMMO.trigger((EntityPlayerMP)getRiddenByEntity());
/*      */       }
/*      */       
/* 2480 */       Entity entity = getRiddenByEntity(); if (entity instanceof EntityPlayer) { EntityPlayer player = (EntityPlayer)entity;
/* 2481 */         if (canPlayerSupplyAmmo(player, weaponID)) {
/* 2482 */           MCH_WeaponSet ws = getWeapon(weaponID);
/*      */           
/* 2484 */           for (MCH_WeaponInfo.RoundItem ri : (ws.getInfo()).roundItems) {
/* 2485 */             int num = ri.num;
/*      */             
/* 2487 */             for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
/* 2488 */               ItemStack itemStack = (ItemStack)player.inventory.mainInventory.get(i);
/* 2489 */               if (!itemStack.isEmpty() && itemStack.isItemEqual(ri.itemStack)) {
/* 2490 */                 if (itemStack.getItem() != W_Item.getItemByName("water_bucket") && itemStack.getItem() != W_Item.getItemByName("lava_bucket")) {
/* 2491 */                   if (itemStack.getCount() > num) {
/* 2492 */                     itemStack.shrink(num);
/* 2493 */                     num = 0;
/*      */                   } else {
/* 2495 */                     num -= itemStack.getCount();
/* 2496 */                     itemStack.setCount(0);
/* 2497 */                     player.inventory.mainInventory.set(i, ItemStack.EMPTY);
/*      */                   } 
/* 2499 */                 } else if (itemStack.getCount() == 1) {
/* 2500 */                   player.inventory.setInventorySlotContents(i, new ItemStack(W_Item.getItemByName("bucket"), 1));
/* 2501 */                   num--;
/*      */                 } 
/*      */               }
/*      */               
/* 2505 */               if (num <= 0) {
/*      */                 break;
/*      */               }
/*      */             } 
/*      */           } 
/*      */           
/* 2511 */           ws.supplyRestAllAmmo();
/*      */         }  }
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public void supplyAmmoToOtherAircraft() {
/* 2518 */     float range = (getAcInfo() != null) ? (getAcInfo()).ammoSupplyRange : 0.0F;
/* 2519 */     if (range > 0.0F && 
/* 2520 */       !this.world.isRemote && getCountOnUpdate() % 40 == 0) {
/*      */       
/* 2522 */       List<MCH_EntityAircraft> list = this.world.getEntitiesWithinAABB(MCH_EntityAircraft.class, getCollisionBoundingBox().grow(range, range, range));
/*      */       
/* 2524 */       for (MCH_EntityAircraft ac : list) {
/* 2525 */         if (!W_Entity.isEqual((Entity)this, (Entity)ac) && ac.canSupply()) {
/* 2526 */           for (int wid = 0; wid < ac.getWeaponNum(); wid++) {
/* 2527 */             MCH_WeaponSet ws = ac.getWeapon(wid);
/* 2528 */             int num = ws.getRestAllAmmoNum() + ws.getAmmoNum();
/* 2529 */             if (num < ws.getAllAmmoNum()) {
/* 2530 */               int ammo = ws.getAllAmmoNum() / 10;
/* 2531 */               if (ammo < 1) {
/* 2532 */                 ammo = 1;
/*      */               }
/*      */               
/* 2535 */               ws.setRestAllAmmoNum(num + ammo);
/* 2536 */               EntityPlayer player = ac.getEntityByWeaponId(wid);
/* 2537 */               if (num != ws.getRestAllAmmoNum() + ws.getAmmoNum()) {
/* 2538 */                 if (ws.getAmmoNum() <= 0) {
/* 2539 */                   ws.reloadMag();
/*      */                 }
/*      */                 
/* 2542 */                 MCH_PacketNotifyAmmoNum.sendAmmoNum(ac, player, wid);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canPlayerSupplyAmmo(EntityPlayer player, int weaponId) {
/* 2553 */     if (MCH_Lib.getBlockIdY((Entity)this, 1, -3) == 0)
/* 2554 */       return false; 
/* 2555 */     if (!canSupply()) {
/* 2556 */       return false;
/*      */     }
/* 2558 */     MCH_WeaponSet ws = getWeapon(weaponId);
/* 2559 */     if (ws.getRestAllAmmoNum() + ws.getAmmoNum() >= ws.getAllAmmoNum()) {
/* 2560 */       return false;
/*      */     }
/* 2562 */     for (MCH_WeaponInfo.RoundItem ri : (ws.getInfo()).roundItems) {
/* 2563 */       int num = ri.num;
/*      */       
/* 2565 */       for (ItemStack itemStack : player.inventory.mainInventory) {
/* 2566 */         if (!itemStack.isEmpty() && itemStack.isItemEqual(ri.itemStack)) {
/* 2567 */           num -= itemStack.getCount();
/*      */         }
/*      */         
/* 2570 */         if (num <= 0) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/* 2575 */       if (num > 0) {
/* 2576 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 2580 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTextureName() {
/* 2586 */     return (String)this.dataManager.get(TEXTURE_NAME);
/*      */   }
/*      */   
/*      */   public MCH_EntityAircraft setTextureName(@Nullable String name) {
/* 2590 */     if (name != null && !name.isEmpty()) {
/* 2591 */       this.dataManager.set(TEXTURE_NAME, name);
/*      */     }
/*      */     
/* 2594 */     return this;
/*      */   }
/*      */   
/*      */   public void switchNextTextureName() {
/* 2598 */     if (getAcInfo() != null) {
/* 2599 */       setTextureName(getAcInfo().getNextTextureName(getTextureName()));
/*      */     }
/*      */   }
/*      */   
/*      */   public void zoomCamera() {
/* 2604 */     if (canZoom()) {
/* 2605 */       float z = this.camera.getCameraZoom();
/* 2606 */       if (z >= getZoomMax() - 0.01D) {
/* 2607 */         z = 1.0F;
/*      */       } else {
/* 2609 */         z *= 2.0F;
/* 2610 */         if (z >= getZoomMax()) {
/* 2611 */           z = getZoomMax();
/*      */         }
/*      */       } 
/*      */       
/* 2615 */       this.camera.setCameraZoom((z <= getZoomMax() + 0.01D) ? z : 1.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getZoomMax() {
/* 2620 */     return (getAcInfo() != null) ? (getAcInfo()).cameraZoom : 1;
/*      */   }
/*      */   
/*      */   public boolean canZoom() {
/* 2624 */     return (getZoomMax() > 1);
/*      */   }
/*      */   
/*      */   public boolean canSwitchCameraMode() {
/* 2628 */     return (!isDestroyed() && getAcInfo() != null && (getAcInfo()).isEnableNightVision);
/*      */   }
/*      */   
/*      */   public boolean canSwitchCameraMode(int seatID) {
/* 2632 */     return (!isDestroyed() && canSwitchCameraMode() && this.camera.isValidUid(seatID));
/*      */   }
/*      */   
/*      */   public int getCameraMode(EntityPlayer player) {
/* 2636 */     return this.camera.getMode(getSeatIdByEntity((Entity)player));
/*      */   }
/*      */   
/*      */   public String getCameraModeName(EntityPlayer player) {
/* 2640 */     return this.camera.getModeName(getSeatIdByEntity((Entity)player));
/*      */   }
/*      */   
/*      */   public void switchCameraMode(EntityPlayer player) {
/* 2644 */     switchCameraMode(player, this.camera.getMode(getSeatIdByEntity((Entity)player)) + 1);
/*      */   }
/*      */   
/*      */   public void switchCameraMode(EntityPlayer player, int mode) {
/* 2648 */     this.camera.setMode(getSeatIdByEntity((Entity)player), mode);
/*      */   }
/*      */   
/*      */   public void updateCameraViewers() {
/* 2652 */     for (int i = 0; i < getSeatNum() + 1; i++) {
/* 2653 */       this.camera.updateViewer(i, getEntityBySeatId(i));
/*      */     }
/*      */   }
/*      */   
/*      */   public void updateRadar(int radarSpeed) {
/* 2658 */     if (isEntityRadarMounted()) {
/* 2659 */       this.radarRotate += radarSpeed;
/* 2660 */       if (this.radarRotate >= 360) {
/* 2661 */         this.radarRotate = 0;
/*      */       }
/*      */       
/* 2664 */       if (this.radarRotate == 0) {
/* 2665 */         this.entityRadar.updateXZ((Entity)this, 64);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getRadarRotate() {
/* 2671 */     return this.radarRotate;
/*      */   }
/*      */   
/*      */   public void initRadar() {
/* 2675 */     this.entityRadar.clear();
/* 2676 */     this.radarRotate = 0;
/*      */   }
/*      */   
/*      */   public ArrayList<MCH_Vector2> getRadarEntityList() {
/* 2680 */     return this.entityRadar.getEntityList();
/*      */   }
/*      */   
/*      */   public ArrayList<MCH_Vector2> getRadarEnemyList() {
/* 2684 */     return this.entityRadar.getEnemyList();
/*      */   }
/*      */   
/*      */   public void move(@NotNull MoverType type, double x, double y, double z) {
/* 2688 */     if (getAcInfo() != null) {
/* 2689 */       this.world.profiler.startSection("move");
/* 2690 */       double d2 = x;
/* 2691 */       double d3 = y;
/* 2692 */       double d4 = z;
/* 2693 */       List<AxisAlignedBB> list1 = getCollisionBoxes((Entity)this, getEntityBoundingBox().expand(x, y, z));
/* 2694 */       AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/* 2695 */       if (y != 0.0D) {
/* 2696 */         for (AxisAlignedBB axisAlignedBB : list1) {
/* 2697 */           y = axisAlignedBB.calculateYOffset(getEntityBoundingBox(), y);
/*      */         }
/*      */         
/* 2700 */         setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*      */       } 
/*      */       
/* 2703 */       boolean flag = (this.onGround || (d3 != y && d3 < 0.0D));
/* 2704 */       if (x != 0.0D) {
/* 2705 */         for (AxisAlignedBB axisAlignedBB : list1) {
/* 2706 */           x = axisAlignedBB.calculateXOffset(getEntityBoundingBox(), x);
/*      */         }
/*      */         
/* 2709 */         if (x != 0.0D) {
/* 2710 */           setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
/*      */         }
/*      */       } 
/*      */       
/* 2714 */       if (z != 0.0D)
/*      */       {
/* 2716 */         setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
/*      */       }
/*      */       
/* 2719 */       if (this.stepHeight > 0.0F && flag && (d2 != x || d4 != z)) {
/* 2720 */         double d14 = x;
/* 2721 */         double d6 = y;
/* 2722 */         double d7 = z;
/* 2723 */         AxisAlignedBB axisalignedbb1 = getEntityBoundingBox();
/* 2724 */         setEntityBoundingBox(axisalignedbb);
/* 2725 */         y = this.stepHeight;
/* 2726 */         List<AxisAlignedBB> list = getCollisionBoxes((Entity)this, getEntityBoundingBox().expand(d2, y, d4));
/* 2727 */         AxisAlignedBB axisalignedbb2 = getEntityBoundingBox();
/* 2728 */         AxisAlignedBB axisalignedbb3 = axisalignedbb2.expand(d2, 0.0D, d4);
/* 2729 */         double d8 = y;
/*      */         
/* 2731 */         for (AxisAlignedBB axisAlignedBB1 : list) {
/* 2732 */           d8 = axisAlignedBB1.calculateYOffset(axisalignedbb3, d8);
/*      */         }
/*      */         
/* 2735 */         axisalignedbb2 = axisalignedbb2.offset(0.0D, d8, 0.0D);
/* 2736 */         double d18 = d2;
/*      */         
/* 2738 */         for (AxisAlignedBB element : list) {
/* 2739 */           d18 = element.calculateXOffset(axisalignedbb2, d18);
/*      */         }
/*      */         
/* 2742 */         axisalignedbb2 = axisalignedbb2.offset(d18, 0.0D, 0.0D);
/* 2743 */         double d19 = d4;
/*      */         
/* 2745 */         for (AxisAlignedBB item : list) {
/* 2746 */           d19 = item.calculateZOffset(axisalignedbb2, d19);
/*      */         }
/*      */         
/* 2749 */         axisalignedbb2 = axisalignedbb2.offset(0.0D, 0.0D, d19);
/* 2750 */         AxisAlignedBB axisalignedbb4 = getEntityBoundingBox();
/* 2751 */         double d20 = y;
/*      */         
/* 2753 */         for (AxisAlignedBB value : list) {
/* 2754 */           d20 = value.calculateYOffset(axisalignedbb4, d20);
/*      */         }
/*      */         
/* 2757 */         axisalignedbb4 = axisalignedbb4.offset(0.0D, d20, 0.0D);
/* 2758 */         double d21 = d2;
/*      */         
/* 2760 */         for (AxisAlignedBB bb : list) {
/* 2761 */           d21 = bb.calculateXOffset(axisalignedbb4, d21);
/*      */         }
/*      */         
/* 2764 */         axisalignedbb4 = axisalignedbb4.offset(d21, 0.0D, 0.0D);
/* 2765 */         double d22 = d4;
/*      */         
/* 2767 */         for (AxisAlignedBB alignedBB : list) {
/* 2768 */           d22 = alignedBB.calculateZOffset(axisalignedbb4, d22);
/*      */         }
/*      */         
/* 2771 */         axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d22);
/* 2772 */         double d23 = d18 * d18 + d19 * d19;
/* 2773 */         double d9 = d21 * d21 + d22 * d22;
/* 2774 */         if (d23 > d9) {
/* 2775 */           x = d18;
/* 2776 */           z = d19;
/* 2777 */           y = -d8;
/* 2778 */           setEntityBoundingBox(axisalignedbb2);
/*      */         } else {
/* 2780 */           x = d21;
/* 2781 */           z = d22;
/* 2782 */           y = -d20;
/* 2783 */           setEntityBoundingBox(axisalignedbb4);
/*      */         } 
/*      */         
/* 2786 */         for (AxisAlignedBB axisAlignedBB : list) {
/* 2787 */           y = axisAlignedBB.calculateYOffset(getEntityBoundingBox(), y);
/*      */         }
/*      */         
/* 2790 */         setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/* 2791 */         if (d14 * d14 + d7 * d7 >= x * x + z * z) {
/* 2792 */           x = d14;
/* 2793 */           y = d6;
/* 2794 */           z = d7;
/* 2795 */           setEntityBoundingBox(axisalignedbb1);
/*      */         } 
/*      */       } 
/*      */       
/* 2799 */       this.world.profiler.endSection();
/* 2800 */       this.world.profiler.startSection("rest");
/* 2801 */       resetPositionToBB();
/* 2802 */       this.collidedHorizontally = (d2 != x || d4 != z);
/* 2803 */       this.collidedVertically = (d3 != y);
/* 2804 */       this.onGround = (this.collidedVertically && d3 < 0.0D);
/* 2805 */       this.collided = (this.collidedHorizontally || this.collidedVertically);
/* 2806 */       int j6 = MathHelper.floor(this.posX);
/* 2807 */       int i1 = MathHelper.floor(this.posY - 0.20000000298023224D);
/* 2808 */       int k6 = MathHelper.floor(this.posZ);
/* 2809 */       BlockPos blockpos = new BlockPos(j6, i1, k6);
/* 2810 */       IBlockState iblockstate = this.world.getBlockState(blockpos);
/* 2811 */       if (iblockstate.getMaterial() == Material.AIR) {
/* 2812 */         BlockPos blockpos1 = blockpos.down();
/* 2813 */         IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
/* 2814 */         Block block1 = iblockstate1.getBlock();
/* 2815 */         if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/* 2816 */           iblockstate = iblockstate1;
/* 2817 */           blockpos = blockpos1;
/*      */         } 
/*      */       } 
/*      */       
/* 2821 */       updateFallState(y, this.onGround, iblockstate, blockpos);
/* 2822 */       if (d2 != x) {
/* 2823 */         this.motionX = 0.0D;
/*      */       }
/*      */       
/* 2826 */       if (d4 != z) {
/* 2827 */         this.motionZ = 0.0D;
/*      */       }
/*      */       
/* 2830 */       Block block = iblockstate.getBlock();
/* 2831 */       if (d3 != y) {
/* 2832 */         block.onLanded(this.world, (Entity)this);
/*      */       }
/*      */       
/*      */       try {
/* 2836 */         doBlockCollisions();
/* 2837 */       } catch (Throwable var45) {
/* 2838 */         CrashReport crashreport = CrashReport.makeCrashReport(var45, "Checking entity block collision");
/* 2839 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
/* 2840 */         addEntityCrashInfo(crashreportcategory);
/* 2841 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/* 2844 */       this.world.profiler.endSection();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onUpdate_updateBlock() {
/* 2849 */     if (MCH_Config.Collision_DestroyBlock.prmBool) {
/* 2850 */       for (int l = 0; l < 4; l++) {
/* 2851 */         int i1 = MathHelper.floor(this.posX + ((l % 2) - 0.5D) * 0.8D);
/* 2852 */         int j1 = MathHelper.floor(this.posZ + (l / 2.0D - 0.5D) * 0.8D);
/*      */         
/* 2854 */         for (int k1 = 0; k1 < 2; k1++) {
/* 2855 */           int l1 = MathHelper.floor(this.posY) + k1;
/* 2856 */           Block block = W_WorldFunc.getBlock(this.world, i1, l1, j1);
/* 2857 */           if (!W_Block.isNull(block)) {
/* 2858 */             if (block == W_Block.getSnowLayer()) {
/* 2859 */               this.world.setBlockToAir(new BlockPos(i1, l1, j1));
/*      */             }
/*      */             
/* 2862 */             if (block == W_Blocks.WATERLILY || block == W_Blocks.CAKE) {
/* 2863 */               W_WorldFunc.destroyBlock(this.world, i1, l1, j1, false);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void onUpdate_ParticleSmoke() {
/* 2872 */     if (this.world.isRemote && 
/* 2873 */       getCurrentThrottle() > 0.10000000149011612D) {
/* 2874 */       float yaw = getRotYaw();
/* 2875 */       float pitch = getRotPitch();
/* 2876 */       float roll = getRotRoll();
/* 2877 */       MCH_WeaponSet ws = getCurrentWeapon(getRiddenByEntity());
/* 2878 */       if (ws.getFirstWeapon() instanceof com.norwood.mcheli.weapon.MCH_WeaponSmoke) {
/* 2879 */         for (int i = 0; i < ws.getWeaponNum(); i++) {
/* 2880 */           MCH_WeaponBase wb = ws.getWeapon(i);
/* 2881 */           if (wb != null) {
/* 2882 */             MCH_WeaponInfo wi = wb.getInfo();
/* 2883 */             if (wi != null) {
/* 2884 */               Vec3d rot = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -yaw - 180.0F + wb.fixRotationYaw, pitch - wb.fixRotationPitch, roll);
/* 2885 */               if (this.rand.nextFloat() <= getCurrentThrottle() * 1.5D) {
/* 2886 */                 Vec3d pos = MCH_Lib.RotVec3(wb.position, -yaw, -pitch, -roll);
/* 2887 */                 double x = this.posX + pos.x + rot.x;
/* 2888 */                 double y = this.posY + pos.y + rot.y;
/* 2889 */                 double z = this.posZ + pos.z + rot.z;
/*      */                 
/* 2891 */                 for (int smk = 0; smk < wi.smokeNum; smk++) {
/* 2892 */                   float c = this.rand.nextFloat() * 0.05F;
/* 2893 */                   int maxAge = (int)(this.rand.nextDouble() * wi.smokeMaxAge);
/* 2894 */                   MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", x, y, z);
/* 2895 */                   prm.setMotion(rot.x * wi.acceleration + (this.rand
/* 2896 */                       .nextDouble() - 0.5D) * 0.2D, rot.y * wi.acceleration + (this.rand
/* 2897 */                       .nextDouble() - 0.5D) * 0.2D, rot.z * wi.acceleration + (this.rand
/* 2898 */                       .nextDouble() - 0.5D) * 0.2D);
/*      */                   
/* 2900 */                   prm.size = (this.rand.nextInt(5) + 5.0F) * wi.smokeSize;
/* 2901 */                   prm.setColor(wi.color.a + this.rand.nextFloat() * 0.05F, wi.color.r + c, wi.color.g + c, wi.color.b + c);
/* 2902 */                   prm.age = maxAge;
/* 2903 */                   prm.toWhite = true;
/* 2904 */                   prm.diffusible = true;
/* 2905 */                   MCH_ParticlesUtil.spawnParticle(prm);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_ParticleSandCloud(boolean seaOnly) {
/* 2917 */     if (!seaOnly || (getAcInfo()).enableSeaSurfaceParticle) {
/* 2918 */       double particlePosY = (int)this.posY;
/* 2919 */       boolean b = false;
/* 2920 */       float scale = (getAcInfo()).particlesScale * 3.0F;
/* 2921 */       if (seaOnly) {
/* 2922 */         scale *= 2.0F;
/*      */       }
/*      */       
/* 2925 */       double throttle = getCurrentThrottle();
/* 2926 */       throttle *= 2.0D;
/* 2927 */       if (throttle > 1.0D) {
/* 2928 */         throttle = 1.0D;
/*      */       }
/*      */       
/* 2931 */       int count = seaOnly ? (int)(scale * 7.0F) : 0;
/* 2932 */       int rangeY = (int)(scale * 10.0F) + 1;
/*      */       
/*      */       int y;
/* 2935 */       for (y = 0; y < rangeY && !b; y++) {
/* 2936 */         for (int x = -1; x <= 1; x++) {
/* 2937 */           for (int z = -1; z <= 1; z++) {
/* 2938 */             Block block = W_WorldFunc.getBlock(this.world, (int)(this.posX + 0.5D) + x, (int)(this.posY + 0.5D) - y, (int)(this.posZ + 0.5D) + z);
/* 2939 */             if (!b && !Block.isEqualTo(block, Blocks.AIR)) {
/* 2940 */               if (seaOnly && W_Block.isEqual(block, W_Block.getWater())) {
/* 2941 */                 count--;
/*      */               }
/*      */               
/* 2944 */               if (count <= 0) {
/* 2945 */                 particlePosY = this.posY + 1.0D + (scale / 5.0F) - y;
/* 2946 */                 b = true;
/* 2947 */                 x += 100;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2955 */       double pn = (rangeY - y + 1) / 5.0D * scale / 2.0D;
/* 2956 */       if (b && (getAcInfo()).particlesScale > 0.01F) {
/* 2957 */         for (int k = 0; k < (int)(throttle * 6.0D * pn); k++) {
/* 2958 */           float r = (float)(this.rand.nextDouble() * Math.PI * 2.0D);
/* 2959 */           double dx = MathHelper.cos(r);
/* 2960 */           double dz = MathHelper.sin(r);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2965 */           MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", this.posX + dx * scale * 3.0D, particlePosY + (this.rand.nextDouble() - 0.5D) * scale, this.posZ + dz * scale * 3.0D, scale * dx * 0.3D, scale * -0.4D * 0.05D, scale * dz * 0.3D, scale * 5.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2972 */           prm.setColor(prm.a * 0.6F, prm.r, prm.g, prm.b);
/* 2973 */           prm.age = (int)(10.0F * scale);
/* 2974 */           prm.motionYUpAge = seaOnly ? 0.2F : 0.1F;
/* 2975 */           MCH_ParticlesUtil.spawnParticle(prm);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean canTriggerWalking() {
/* 2982 */     return false;
/*      */   }
/*      */   
/*      */   public AxisAlignedBB getCollisionBox(Entity par1Entity) {
/* 2986 */     return par1Entity.getEntityBoundingBox();
/*      */   }
/*      */   
/*      */   public AxisAlignedBB getCollisionBoundingBox() {
/* 2990 */     return getEntityBoundingBox();
/*      */   }
/*      */   
/*      */   public double getMountedYOffset() {
/* 2994 */     return 0.0D;
/*      */   }
/*      */   
/*      */   public float getShadowSize() {
/* 2998 */     return 2.0F;
/*      */   }
/*      */   
/*      */   public boolean canBeCollidedWith() {
/* 3002 */     return !this.isDead;
/*      */   }
/*      */   
/*      */   public boolean useFlare(int type) {
/* 3006 */     if (getAcInfo() != null && getAcInfo().haveFlare()) {
/* 3007 */       for (int i : (getAcInfo()).flare.types) {
/* 3008 */         if (i == type) {
/* 3009 */           setCommonStatus(0, true);
/* 3010 */           if (this.flareDv.use(type)) {
/* 3011 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 3016 */       return false;
/*      */     } 
/* 3018 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCurrentFlareType() {
/* 3023 */     return !haveFlare() ? 0 : (getAcInfo()).flare.types[this.currentFlareIndex];
/*      */   }
/*      */   
/*      */   public void nextFlareType() {
/* 3027 */     if (haveFlare()) {
/* 3028 */       this.currentFlareIndex = (this.currentFlareIndex + 1) % (getAcInfo()).flare.types.length;
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean canUseFlare() {
/* 3033 */     if (getAcInfo() == null || !getAcInfo().haveFlare()) {
/* 3034 */       return false;
/*      */     }
/* 3036 */     return (!getCommonStatus(0) && this.flareDv.tick == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFlarePreparation() {
/* 3041 */     return this.flareDv.isInPreparation();
/*      */   }
/*      */   
/*      */   public boolean isFlareUsing() {
/* 3045 */     return this.flareDv.isUsing();
/*      */   }
/*      */   
/*      */   public int getFlareTick() {
/* 3049 */     return this.flareDv.tick;
/*      */   }
/*      */   
/*      */   public boolean haveFlare() {
/* 3053 */     return (getAcInfo() != null && getAcInfo().haveFlare());
/*      */   }
/*      */   
/*      */   public boolean haveFlare(int seatID) {
/* 3057 */     return (haveFlare() && seatID >= 0 && seatID <= 1);
/*      */   }
/*      */   
/*      */   public MCH_EntitySeat[] getSeats() {
/* 3061 */     return (this.seats != null) ? this.seats : seatsDummy;
/*      */   }
/*      */   
/*      */   public int getSeatIdByEntity(@Nullable Entity entity) {
/* 3065 */     if (entity == null)
/* 3066 */       return -1; 
/* 3067 */     if (isEqual(getRiddenByEntity(), entity)) {
/* 3068 */       return 0;
/*      */     }
/* 3070 */     for (int i = 0; i < (getSeats()).length; i++) {
/* 3071 */       MCH_EntitySeat seat = getSeats()[i];
/* 3072 */       if (seat != null && isEqual(seat.getRiddenByEntity(), entity)) {
/* 3073 */         return i + 1;
/*      */       }
/*      */     } 
/*      */     
/* 3077 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public MCH_EntitySeat getSeatByEntity(@Nullable Entity entity) {
/* 3083 */     int idx = getSeatIdByEntity(entity);
/* 3084 */     return (idx > 0) ? getSeat(idx - 1) : null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Entity getEntityBySeatId(int id) {
/* 3089 */     if (id == 0) {
/* 3090 */       return getRiddenByEntity();
/*      */     }
/* 3092 */     id--;
/* 3093 */     if (id >= 0 && id < (getSeats()).length) {
/* 3094 */       return (this.seats[id] != null) ? this.seats[id].getRiddenByEntity() : null;
/*      */     }
/* 3096 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayer getEntityByWeaponId(int id) {
/* 3103 */     if (id >= 0 && id < getWeaponNum()) {
/* 3104 */       for (int i = 0; i < this.currentWeaponID.length; i++) {
/* 3105 */         if (this.currentWeaponID[i] == id) {
/* 3106 */           Entity e = getEntityBySeatId(i);
/* 3107 */           if (e instanceof EntityPlayer) {
/* 3108 */             return (EntityPlayer)e;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 3114 */     return null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Entity getWeaponUserByWeaponName(String name) {
/* 3119 */     if (getAcInfo() == null) {
/* 3120 */       return null;
/*      */     }
/* 3122 */     MCH_AircraftInfo.Weapon weapon = getAcInfo().getWeaponByName(name);
/* 3123 */     Entity entity = null;
/* 3124 */     if (weapon != null) {
/* 3125 */       entity = getEntityBySeatId(getWeaponSeatID((MCH_WeaponInfo)null, weapon));
/* 3126 */       if (entity == null && weapon.canUsePilot) {
/* 3127 */         entity = getRiddenByEntity();
/*      */       }
/*      */     } 
/*      */     
/* 3131 */     return entity;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void newSeats(int seatsNum) {
/* 3136 */     if (seatsNum >= 2) {
/* 3137 */       if (this.seats != null) {
/* 3138 */         for (int i = 0; i < this.seats.length; i++) {
/* 3139 */           if (this.seats[i] != null) {
/* 3140 */             this.seats[i].setDead();
/* 3141 */             this.seats[i] = null;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 3146 */       this.seats = new MCH_EntitySeat[seatsNum - 1];
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MCH_EntitySeat getSeat(int idx) {
/* 3152 */     return (idx < this.seats.length) ? this.seats[idx] : null;
/*      */   }
/*      */   
/*      */   public void setSeat(int idx, MCH_EntitySeat seat) {
/* 3156 */     if (idx < this.seats.length) {
/* 3157 */       MCH_Lib.DbgLog(this.world, "MCH_EntityAircraft.setSeat SeatID=" + idx + " / seat[]" + ((this.seats[idx] != null) ? 1 : 0) + " / " + (
/* 3158 */           (seat.getRiddenByEntity() != null) ? 1 : 0), new Object[0]);
/*      */       
/* 3160 */       if (this.seats[idx] == null || this.seats[idx].getRiddenByEntity() != null);
/*      */ 
/*      */       
/* 3163 */       this.seats[idx] = seat;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isValidSeatID(int seatID) {
/* 3168 */     return (seatID >= 0 && seatID < getSeatNum() + 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateHitBoxPosition() {}
/*      */   
/*      */   public void updateSeatsPosition(double px, double py, double pz, boolean setPrevPos) {
/* 3175 */     MCH_SeatInfo[] info = getSeatsInfo();
/* 3176 */     py += 0.3499999940395355D;
/* 3177 */     if (this.pilotSeat != null && !this.pilotSeat.isDead) {
/* 3178 */       this.pilotSeat.prevPosX = this.pilotSeat.posX;
/* 3179 */       this.pilotSeat.prevPosY = this.pilotSeat.posY;
/* 3180 */       this.pilotSeat.prevPosZ = this.pilotSeat.posZ;
/* 3181 */       this.pilotSeat.setPosition(px, py, pz);
/* 3182 */       if (info != null && info.length > 0 && info[0] != null) {
/* 3183 */         Vec3d v = getTransformedPosition((info[0]).pos.x, (info[0]).pos.y, (info[0]).pos.z, px, py, pz, (info[0]).rotSeat);
/* 3184 */         this.pilotSeat.setPosition(v.x, v.y, v.z);
/*      */       } 
/*      */       
/* 3187 */       this.pilotSeat.rotationPitch = getRotPitch();
/* 3188 */       this.pilotSeat.rotationYaw = getRotYaw();
/* 3189 */       if (setPrevPos) {
/* 3190 */         this.pilotSeat.prevPosX = this.pilotSeat.posX;
/* 3191 */         this.pilotSeat.prevPosY = this.pilotSeat.posY;
/* 3192 */         this.pilotSeat.prevPosZ = this.pilotSeat.posZ;
/*      */       } 
/*      */     } 
/*      */     
/* 3196 */     int i = 0;
/*      */     
/* 3198 */     for (MCH_EntitySeat seat : this.seats) {
/* 3199 */       i++;
/* 3200 */       if (seat != null && !seat.isDead) {
/* 3201 */         float offsetY = -0.5F;
/* 3202 */         if (seat.getRiddenByEntity() == null || W_Lib.isClientPlayer(seat.getRiddenByEntity()) || (seat.getRiddenByEntity()).height >= 1.0F);
/*      */ 
/*      */         
/* 3205 */         seat.prevPosX = seat.posX;
/* 3206 */         seat.prevPosY = seat.posY;
/* 3207 */         seat.prevPosZ = seat.posZ;
/* 3208 */         MCH_SeatInfo si = (i < info.length) ? info[i] : info[0];
/* 3209 */         Vec3d v = getTransformedPosition(si.pos.x, si.pos.y + offsetY, si.pos.z, px, py, pz, si.rotSeat);
/* 3210 */         seat.setPosition(v.x, v.y, v.z);
/* 3211 */         seat.rotationPitch = getRotPitch();
/* 3212 */         seat.rotationYaw = getRotYaw();
/* 3213 */         if (setPrevPos) {
/* 3214 */           seat.prevPosX = seat.posX;
/* 3215 */           seat.prevPosY = seat.posY;
/* 3216 */           seat.prevPosZ = seat.posZ;
/*      */         } 
/*      */         
/* 3219 */         if (si instanceof MCH_SeatRackInfo) {
/* 3220 */           seat.updateRotation(seat.getRiddenByEntity(), si.fixYaw + getRotYaw(), si.fixPitch);
/*      */         }
/*      */         
/* 3223 */         seat.updatePosition(seat.getRiddenByEntity());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getClientPositionDelayCorrection() {
/* 3229 */     return 7;
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void setPositionAndRotationDirect(double par1, double par3, double par5, float par7, float par8, int par9, boolean teleport) {
/* 3234 */     this.aircraftPosRotInc = par9 + getClientPositionDelayCorrection();
/* 3235 */     this.aircraftX = par1;
/* 3236 */     this.aircraftY = par3;
/* 3237 */     this.aircraftZ = par5;
/* 3238 */     this.aircraftYaw = par7;
/* 3239 */     this.aircraftPitch = par8;
/* 3240 */     this.motionX = this.velocityX;
/* 3241 */     this.motionY = this.velocityY;
/* 3242 */     this.motionZ = this.velocityZ;
/*      */   }
/*      */   
/*      */   public void updateRiderPosition(Entity passenger, double px, double py, double pz) {
/* 3246 */     MCH_SeatInfo[] info = getSeatsInfo();
/* 3247 */     if (isPassenger(passenger) && !passenger.isDead) {
/* 3248 */       Vec3d v; float riddenEntityYOffset = 0.0F;
/* 3249 */       if (!(passenger instanceof EntityPlayer) || !W_Lib.isClientPlayer(passenger));
/*      */ 
/*      */ 
/*      */       
/* 3253 */       if (info != null && info.length > 0) {
/* 3254 */         v = getTransformedPosition((info[0]).pos.x, (info[0]).pos.y + riddenEntityYOffset - 0.5D, (info[0]).pos.z, px, py + 0.3499999940395355D, pz, (info[0]).rotSeat);
/*      */       } else {
/* 3256 */         v = getTransformedPosition(0.0D, (riddenEntityYOffset - 1.0F), 0.0D);
/*      */       } 
/*      */       
/* 3259 */       passenger.setPosition(v.x, v.y, v.z);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void updatePassenger(@NotNull Entity passenger) {
/* 3264 */     updateRiderPosition(passenger, this.posX, this.posY, this.posZ);
/*      */   }
/*      */   
/*      */   public Vec3d calcOnTurretPos(Vec3d pos) {
/* 3268 */     float ry = getLastRiderYaw();
/* 3269 */     if (getRiddenByEntity() != null) {
/* 3270 */       ry = (getRiddenByEntity()).rotationYaw;
/*      */     }
/*      */     
/* 3273 */     Vec3d tpos = (getAcInfo()).turretPosition.addVector(0.0D, pos.y, 0.0D);
/* 3274 */     Vec3d v = pos.addVector(-tpos.x, -tpos.y, -tpos.z);
/* 3275 */     v = MCH_Lib.RotVec3(v, -ry, 0.0F, 0.0F);
/* 3276 */     Vec3d vv = MCH_Lib.RotVec3(tpos, -getRotYaw(), -getRotPitch(), -getRotRoll());
/* 3277 */     return v.add(vv);
/*      */   }
/*      */   
/*      */   public float getLastRiderYaw() {
/* 3281 */     return this.lastRiderYaw;
/*      */   }
/*      */   
/*      */   public float getLastRiderPitch() {
/* 3285 */     return this.lastRiderPitch;
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void setupAllRiderRenderPosition(float tick, EntityPlayer player) {
/* 3290 */     double x = this.lastTickPosX + (this.posX - this.lastTickPosX) * tick;
/* 3291 */     double y = this.lastTickPosY + (this.posY - this.lastTickPosY) * tick;
/* 3292 */     double z = this.lastTickPosZ + (this.posZ - this.lastTickPosZ) * tick;
/* 3293 */     updateRiderPosition(getRiddenByEntity(), x, y, z);
/* 3294 */     updateSeatsPosition(x, y, z, true);
/*      */     
/* 3296 */     for (int i = 0; i < getSeatNum() + 1; i++) {
/* 3297 */       Entity e = getEntityBySeatId(i);
/* 3298 */       if (e != null) {
/* 3299 */         e.lastTickPosX = e.posX;
/* 3300 */         e.lastTickPosY = e.posY;
/* 3301 */         e.lastTickPosZ = e.posZ;
/*      */       } 
/*      */     } 
/*      */     
/* 3305 */     if (getTVMissile() != null && W_Lib.isClientPlayer((getTVMissile()).shootingEntity)) {
/* 3306 */       MCH_EntityTvMissile mCH_EntityTvMissile = getTVMissile();
/* 3307 */       x = ((Entity)mCH_EntityTvMissile).prevPosX + (((Entity)mCH_EntityTvMissile).posX - ((Entity)mCH_EntityTvMissile).prevPosX) * tick;
/* 3308 */       y = ((Entity)mCH_EntityTvMissile).prevPosY + (((Entity)mCH_EntityTvMissile).posY - ((Entity)mCH_EntityTvMissile).prevPosY) * tick;
/* 3309 */       z = ((Entity)mCH_EntityTvMissile).prevPosZ + (((Entity)mCH_EntityTvMissile).posZ - ((Entity)mCH_EntityTvMissile).prevPosZ) * tick;
/* 3310 */       MCH_ViewEntityDummy.setCameraPosition(x, y, z);
/*      */     } else {
/* 3312 */       MCH_AircraftInfo.CameraPosition cpi = getCameraPosInfo();
/* 3313 */       if (cpi != null && cpi.pos != null) {
/* 3314 */         Vec3d v; MCH_SeatInfo seatInfo = getSeatInfo((Entity)player);
/*      */         
/* 3316 */         if (seatInfo != null && seatInfo.rotSeat) {
/* 3317 */           v = calcOnTurretPos(cpi.pos);
/*      */         } else {
/* 3319 */           v = MCH_Lib.RotVec3(cpi.pos, -getRotYaw(), -getRotPitch(), -getRotRoll());
/*      */         } 
/*      */         
/* 3322 */         MCH_ViewEntityDummy.setCameraPosition(x + v.x, y + v.y, z + v.z);
/* 3323 */         if (!cpi.fixRot);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3d getTurretPos(Vec3d pos, boolean turret) {
/* 3330 */     if (turret) {
/* 3331 */       float ry = getLastRiderYaw();
/* 3332 */       if (getRiddenByEntity() != null) {
/* 3333 */         ry = (getRiddenByEntity()).rotationYaw;
/*      */       }
/*      */       
/* 3336 */       Vec3d tpos = (getAcInfo()).turretPosition.addVector(0.0D, pos.y, 0.0D);
/* 3337 */       Vec3d v = pos.addVector(-tpos.x, -tpos.y, -tpos.z);
/* 3338 */       v = MCH_Lib.RotVec3(v, -ry, 0.0F, 0.0F);
/* 3339 */       Vec3d vv = MCH_Lib.RotVec3(tpos, -getRotYaw(), -getRotPitch(), -getRotRoll());
/* 3340 */       return v.add(vv);
/*      */     } 
/* 3342 */     return Vec3d.ZERO;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3d getTransformedPosition(Vec3d v) {
/* 3347 */     return getTransformedPosition(v.x, v.y, v.z);
/*      */   }
/*      */   
/*      */   public Vec3d getTransformedPosition(double x, double y, double z) {
/* 3351 */     return getTransformedPosition(x, y, z, this.posX, this.posY, this.posZ);
/*      */   }
/*      */   
/*      */   public Vec3d getTransformedPosition(Vec3d v, Vec3d pos) {
/* 3355 */     return getTransformedPosition(v.x, v.y, v.z, pos.x, pos.y, pos.z);
/*      */   }
/*      */   
/*      */   public Vec3d getTransformedPosition(Vec3d v, double px, double py, double pz) {
/* 3359 */     return getTransformedPosition(v.x, v.y, v.z, this.posX, this.posY, this.posZ);
/*      */   }
/*      */   
/*      */   public Vec3d getTransformedPosition(double x, double y, double z, double px, double py, double pz) {
/* 3363 */     Vec3d v = MCH_Lib.RotVec3(x, y, z, -getRotYaw(), -getRotPitch(), -getRotRoll());
/* 3364 */     return v.addVector(px, py, pz);
/*      */   }
/*      */   
/*      */   public Vec3d getTransformedPosition(double x, double y, double z, double px, double py, double pz, boolean rotSeat) {
/* 3368 */     if (rotSeat && getAcInfo() != null) {
/* 3369 */       MCH_AircraftInfo info = getAcInfo();
/* 3370 */       Vec3d tv = MCH_Lib.RotVec3(x - info.turretPosition.x, y - info.turretPosition.y, z - info.turretPosition.z, 
/* 3371 */           -getLastRiderYaw() + getRotYaw(), 0.0F, 0.0F);
/*      */       
/* 3373 */       x = tv.x + info.turretPosition.x;
/* 3374 */       y = tv.y + info.turretPosition.x;
/* 3375 */       z = tv.z + info.turretPosition.x;
/*      */     } 
/*      */     
/* 3378 */     Vec3d v = MCH_Lib.RotVec3(x, y, z, -getRotYaw(), -getRotPitch(), -getRotRoll());
/* 3379 */     return v.addVector(px, py, pz);
/*      */   }
/*      */   
/*      */   protected MCH_SeatInfo[] getSeatsInfo() {
/* 3383 */     if (this.seatsInfo == null) {
/* 3384 */       newSeatsPos();
/*      */     }
/* 3386 */     return this.seatsInfo;
/*      */   }
/*      */   
/*      */   protected void setSeatsInfo(MCH_SeatInfo[] v) {
/* 3390 */     this.seatsInfo = v;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MCH_SeatInfo getSeatInfo(int index) {
/* 3395 */     MCH_SeatInfo[] seats = getSeatsInfo();
/* 3396 */     return (index >= 0 && seats != null && index < seats.length) ? seats[index] : null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MCH_SeatInfo getSeatInfo(@Nullable Entity entity) {
/* 3401 */     return getSeatInfo(getSeatIdByEntity(entity));
/*      */   }
/*      */   
/*      */   public int getSeatNum() {
/* 3405 */     if (getAcInfo() == null) {
/* 3406 */       return 0;
/*      */     }
/* 3408 */     int s = getAcInfo().getNumSeatAndRack();
/* 3409 */     return (s >= 1) ? (s - 1) : 1;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void newSeatsPos() {
/* 3414 */     if (getAcInfo() != null) {
/* 3415 */       MCH_SeatInfo[] v = new MCH_SeatInfo[getAcInfo().getNumSeatAndRack()];
/*      */       
/* 3417 */       for (int i = 0; i < v.length; i++) {
/* 3418 */         v[i] = (getAcInfo()).seatList.get(i);
/*      */       }
/*      */       
/* 3421 */       setSeatsInfo(v);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void createSeats(String uuid) {
/* 3426 */     if (!this.world.isRemote && 
/* 3427 */       !uuid.isEmpty()) {
/* 3428 */       setCommonUniqueId(uuid);
/* 3429 */       this.seats = new MCH_EntitySeat[getSeatNum()];
/*      */       
/* 3431 */       for (int i = 0; i < this.seats.length; i++) {
/* 3432 */         this.seats[i] = new MCH_EntitySeat(this.world, this.posX, this.posY, this.posZ);
/* 3433 */         (this.seats[i]).parentUniqueID = getCommonUniqueId();
/* 3434 */         (this.seats[i]).seatID = i;
/* 3435 */         this.seats[i].setParent(this);
/* 3436 */         this.world.spawnEntity((Entity)this.seats[i]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean interactFirstSeat(EntityPlayer player) {
/* 3443 */     if (getSeats() == null) {
/* 3444 */       return false;
/*      */     }
/* 3446 */     int seatId = 1;
/*      */     
/* 3448 */     for (MCH_EntitySeat seat : getSeats()) {
/* 3449 */       if (seat != null && seat.getRiddenByEntity() == null && !isMountedEntity((Entity)player) && canRideSeatOrRack(seatId, (Entity)player)) {
/* 3450 */         if (!this.world.isRemote) {
/* 3451 */           player.startRiding((Entity)seat);
/*      */         }
/*      */         
/*      */         break;
/*      */       } 
/* 3456 */       seatId++;
/*      */     } 
/*      */     
/* 3459 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onMountPlayerSeat(MCH_EntitySeat seat, Entity entity) {
/* 3464 */     if (seat != null) {
/* 3465 */       if (entity instanceof EntityPlayer || entity instanceof MCH_EntityGunner);
/*      */ 
/*      */       
/* 3468 */       if (this.world.isRemote && MCH_Lib.getClientPlayer() == entity) {
/* 3469 */         switchGunnerFreeLookMode(false);
/*      */       }
/*      */       
/* 3472 */       initCurrentWeapon(entity);
/* 3473 */       MCH_Lib.DbgLog(this.world, "onMountEntitySeat:%d", new Object[] { Integer.valueOf(W_Entity.getEntityId(entity)) });
/* 3474 */       Entity pilot = getRiddenByEntity();
/* 3475 */       int sid = getSeatIdByEntity(entity);
/* 3476 */       if (sid == 1 && (getAcInfo() == null || !(getAcInfo()).isEnableConcurrentGunnerMode)) {
/* 3477 */         switchGunnerMode(false);
/*      */       }
/*      */       
/* 3480 */       if (sid > 0) {
/* 3481 */         this.isGunnerModeOtherSeat = true;
/*      */       }
/*      */       
/* 3484 */       if (pilot != null && getAcInfo() != null) {
/* 3485 */         int cwid = getCurrentWeaponID(pilot);
/* 3486 */         MCH_AircraftInfo.Weapon w = getAcInfo().getWeaponById(cwid);
/* 3487 */         if (w != null && getWeaponSeatID(getWeaponInfoById(cwid), w) == sid) {
/* 3488 */           int next = getNextWeaponID(pilot, 1);
/* 3489 */           MCH_Lib.DbgLog(this.world, "onMountEntitySeat:%d:->%d", new Object[] { Integer.valueOf(W_Entity.getEntityId(pilot)), Integer.valueOf(next) });
/* 3490 */           if (next >= 0) {
/* 3491 */             switchWeapon(pilot, next);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 3496 */       if (this.world.isRemote) {
/* 3497 */         updateClientSettings(sid);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MCH_WeaponInfo getWeaponInfoById(int id) {
/* 3504 */     if (id >= 0) {
/* 3505 */       MCH_WeaponSet ws = getWeapon(id);
/* 3506 */       if (ws != null) {
/* 3507 */         return ws.getInfo();
/*      */       }
/*      */     } 
/*      */     
/* 3511 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mountWithNearEmptyMinecart() {
/* 3517 */     if (getRidingEntity() == null) {
/* 3518 */       int d = 2;
/* 3519 */       if (this.dismountedUserCtrl) {
/* 3520 */         d = 6;
/*      */       }
/*      */       
/* 3523 */       List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(d, d, d));
/* 3524 */       if (!list.isEmpty()) {
/* 3525 */         for (Entity entity : list) {
/* 3526 */           if (entity instanceof net.minecraft.entity.item.EntityMinecartEmpty) {
/* 3527 */             if (this.dismountedUserCtrl) {
/*      */               return;
/*      */             }
/*      */             
/* 3531 */             if (!entity.isBeingRidden() && entity.canBePushed()) {
/* 3532 */               this.waitMountEntity = 20;
/* 3533 */               MCH_Lib.DbgLog(this.world.isRemote, "MCH_EntityAircraft.mountWithNearEmptyMinecart:" + entity, new Object[0]);
/* 3534 */               startRiding(entity);
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/* 3541 */       this.dismountedUserCtrl = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isRidePlayer() {
/* 3546 */     if (getRiddenByEntity() instanceof EntityPlayer) {
/* 3547 */       return true;
/*      */     }
/* 3549 */     for (MCH_EntitySeat seat : getSeats()) {
/* 3550 */       if (seat != null && seat.getRiddenByEntity() instanceof EntityPlayer) {
/* 3551 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 3555 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUnmountPlayerSeat(MCH_EntitySeat seat, Entity entity) {
/* 3560 */     MCH_Lib.DbgLog(this.world, "onUnmountPlayerSeat:%d", new Object[] { Integer.valueOf(W_Entity.getEntityId(entity)) });
/* 3561 */     int sid = getSeatIdByEntity(entity);
/* 3562 */     this.camera.initCamera(sid, entity);
/* 3563 */     MCH_SeatInfo seatInfo = getSeatInfo(seat.seatID + 1);
/* 3564 */     if (seatInfo != null) {
/* 3565 */       setUnmountPosition(entity, new Vec3d(seatInfo.pos.x, 0.0D, seatInfo.pos.z));
/*      */     }
/*      */     
/* 3568 */     if (!isRidePlayer()) {
/* 3569 */       switchGunnerMode(false);
/* 3570 */       switchHoveringMode(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isCreatedSeats() {
/* 3575 */     return !getCommonUniqueId().isEmpty();
/*      */   }
/*      */   
/*      */   public void onUpdate_Seats() {
/* 3579 */     boolean b = false;
/*      */     
/* 3581 */     for (MCH_EntitySeat seat : this.seats) {
/* 3582 */       if (seat != null) {
/* 3583 */         if (!seat.isDead) {
/* 3584 */           seat.fallDistance = 0.0F;
/*      */         }
/*      */       } else {
/* 3587 */         b = true;
/*      */       } 
/*      */     } 
/*      */     
/* 3591 */     if (b) {
/* 3592 */       if (this.seatSearchCount > 40) {
/* 3593 */         if (this.world.isRemote) {
/* 3594 */           MCH_PacketSeatListRequest.requestSeatList(this);
/*      */         } else {
/* 3596 */           searchSeat();
/*      */         } 
/*      */         
/* 3599 */         this.seatSearchCount = 0;
/*      */       } 
/*      */       
/* 3602 */       this.seatSearchCount++;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void searchSeat() {
/* 3607 */     List<MCH_EntitySeat> list = this.world.getEntitiesWithinAABB(MCH_EntitySeat.class, getEntityBoundingBox().grow(60.0D, 60.0D, 60.0D));
/*      */     
/* 3609 */     for (MCH_EntitySeat seat : list) {
/* 3610 */       if (!seat.isDead && seat.parentUniqueID
/* 3611 */         .equals(getCommonUniqueId()) && seat.seatID >= 0 && seat.seatID < 
/*      */         
/* 3613 */         getSeatNum() && this.seats[seat.seatID] == null) {
/*      */         
/* 3615 */         this.seats[seat.seatID] = seat;
/* 3616 */         seat.setParent(this);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public String getCommonUniqueId() {
/* 3622 */     return this.commonUniqueId;
/*      */   }
/*      */   
/*      */   public void setCommonUniqueId(String uniqId) {
/* 3626 */     this.commonUniqueId = uniqId;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDead() {
/* 3631 */     setDead(false);
/*      */   }
/*      */   
/*      */   public void setDead(boolean dropItems) {
/* 3635 */     this.dropContentsWhenDead = dropItems;
/* 3636 */     super.setDead();
/* 3637 */     if (getRiddenByEntity() != null) {
/* 3638 */       getRiddenByEntity().dismountRidingEntity();
/*      */     }
/*      */     
/* 3641 */     getGuiInventory().setDead();
/*      */     
/* 3643 */     for (MCH_EntitySeat s : this.seats) {
/* 3644 */       if (s != null) {
/* 3645 */         s.setDead();
/*      */       }
/*      */     } 
/*      */     
/* 3649 */     if (this.soundUpdater != null) {
/* 3650 */       this.soundUpdater.update();
/*      */     }
/*      */     
/* 3653 */     if (getTowChainEntity() != null) {
/* 3654 */       getTowChainEntity().setDead();
/* 3655 */       setTowChainEntity((MCH_EntityChain)null);
/*      */     } 
/*      */     
/* 3658 */     for (Entity e : getParts()) {
/* 3659 */       if (e != null) {
/* 3660 */         e.setDead();
/*      */       }
/*      */     } 
/*      */     
/* 3664 */     MCH_Lib.DbgLog(this.world, "setDead:" + ((getAcInfo() != null) ? (getAcInfo()).name : "null"), new Object[0]);
/*      */   }
/*      */   
/*      */   public void unmountEntity() {
/* 3668 */     if (!isRidePlayer()) {
/* 3669 */       switchHoveringMode(false);
/*      */     }
/*      */     
/* 3672 */     this.moveLeft = this.moveRight = this.throttleDown = this.throttleUp = false;
/* 3673 */     Entity rByEntity = null;
/* 3674 */     if (getRiddenByEntity() != null) {
/* 3675 */       rByEntity = getRiddenByEntity();
/* 3676 */       this.camera.initCamera(0, rByEntity);
/* 3677 */       if (!this.world.isRemote) {
/* 3678 */         getRiddenByEntity().dismountRidingEntity();
/*      */       }
/* 3680 */     } else if (this.lastRiddenByEntity != null) {
/* 3681 */       rByEntity = this.lastRiddenByEntity;
/* 3682 */       if (rByEntity instanceof EntityPlayer) {
/* 3683 */         this.camera.initCamera(0, rByEntity);
/*      */       }
/*      */     } 
/*      */     
/* 3687 */     MCH_Lib.DbgLog(this.world, "unmountEntity:" + rByEntity, new Object[0]);
/* 3688 */     if (!isRidePlayer()) {
/* 3689 */       switchGunnerMode(false);
/*      */     }
/*      */     
/* 3692 */     setCommonStatus(1, false);
/* 3693 */     if (!isUAV()) {
/* 3694 */       setUnmountPosition(rByEntity, (getSeatsInfo()[0]).pos);
/* 3695 */     } else if (rByEntity != null && rByEntity.getRidingEntity() instanceof MCH_EntityUavStation) {
/* 3696 */       rByEntity.dismountRidingEntity();
/*      */     } 
/*      */     
/* 3699 */     this.lastRiddenByEntity = null;
/* 3700 */     if (this.cs_dismountAll) {
/* 3701 */       unmountCrew(false);
/*      */     }
/*      */   }
/*      */   
/*      */   public Entity getRidingEntity() {
/* 3706 */     return super.getRidingEntity();
/*      */   }
/*      */   
/*      */   public void startUnmountCrew() {
/* 3710 */     this.isParachuting = true;
/* 3711 */     if (haveHatch()) {
/* 3712 */       foldHatch(true, true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void stopUnmountCrew() {
/* 3717 */     this.isParachuting = false;
/*      */   }
/*      */   
/*      */   public void unmountCrew() {
/* 3721 */     if (getAcInfo() != null) {
/* 3722 */       if (getAcInfo().haveRepellingHook()) {
/* 3723 */         if (!isRepelling()) {
/* 3724 */           if (MCH_Lib.getBlockIdY((Entity)this, 3, -4) > 0) {
/* 3725 */             unmountCrew(false);
/* 3726 */           } else if (canStartRepelling()) {
/* 3727 */             startRepelling();
/*      */           } 
/*      */         } else {
/* 3730 */           stopRepelling();
/*      */         } 
/* 3732 */       } else if (this.isParachuting) {
/* 3733 */         stopUnmountCrew();
/* 3734 */       } else if ((getAcInfo()).isEnableParachuting && MCH_Lib.getBlockIdY((Entity)this, 3, -10) == 0) {
/* 3735 */         startUnmountCrew();
/*      */       } else {
/* 3737 */         unmountCrew(false);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isRepelling() {
/* 3743 */     return getCommonStatus(5);
/*      */   }
/*      */   
/*      */   public void setRepellingStat(boolean b) {
/* 3747 */     setCommonStatus(5, b);
/*      */   }
/*      */   
/*      */   public Vec3d getRopePos(int ropeIndex) {
/* 3751 */     return (getAcInfo() != null && getAcInfo().haveRepellingHook() && ropeIndex < (getAcInfo()).repellingHooks.size()) ? 
/* 3752 */       getTransformedPosition(((MCH_AircraftInfo.RepellingHook)(getAcInfo()).repellingHooks.get(ropeIndex)).pos) : 
/* 3753 */       new Vec3d(this.posX, this.posY, this.posZ);
/*      */   }
/*      */   
/*      */   private void startRepelling() {
/* 3757 */     MCH_Lib.DbgLog(this.world, "MCH_EntityAircraft.startRepelling()", new Object[0]);
/* 3758 */     setRepellingStat(true);
/* 3759 */     this.throttleUp = false;
/* 3760 */     this.throttleDown = false;
/* 3761 */     this.moveLeft = false;
/* 3762 */     this.moveRight = false;
/* 3763 */     this.tickRepelling = 0;
/*      */   }
/*      */   
/*      */   private void stopRepelling() {
/* 3767 */     MCH_Lib.DbgLog(this.world, "MCH_EntityAircraft.stopRepelling()", new Object[0]);
/* 3768 */     setRepellingStat(false);
/*      */   }
/*      */   
/*      */   public boolean canStartRepelling() {
/* 3772 */     if (getAcInfo().haveRepellingHook() && isHovering() && abs(getRotPitch()) < 3.0F && abs(getRotRoll()) < 3.0F) {
/* 3773 */       Vec3d v = ((Vec3d)this.prevPosition.oldest()).addVector(-this.posX, -this.posY, -this.posZ);
/* 3774 */       return (v.lengthVector() < 0.3D);
/*      */     } 
/*      */     
/* 3777 */     return false;
/*      */   }
/*      */   
/*      */   public boolean unmountCrew(boolean unmountParachute) {
/* 3781 */     boolean ret = false;
/* 3782 */     MCH_SeatInfo[] pos = getSeatsInfo();
/*      */     
/* 3784 */     for (int i = 0; i < this.seats.length; i++) {
/* 3785 */       if (this.seats[i] != null && this.seats[i].getRiddenByEntity() != null) {
/* 3786 */         Entity entity = this.seats[i].getRiddenByEntity();
/* 3787 */         if (!(entity instanceof EntityPlayer) && !(pos[i + 1] instanceof MCH_SeatRackInfo)) {
/* 3788 */           if (unmountParachute) {
/* 3789 */             if (getSeatIdByEntity(entity) > 1) {
/* 3790 */               ret = true;
/* 3791 */               Vec3d dropPos = getTransformedPosition((getAcInfo()).mobDropOption.pos, (Vec3d)this.prevPosition.oldest());
/* 3792 */               (this.seats[i]).posX = dropPos.x;
/* 3793 */               (this.seats[i]).posY = dropPos.y;
/* 3794 */               (this.seats[i]).posZ = dropPos.z;
/* 3795 */               entity.dismountRidingEntity();
/* 3796 */               entity.posX = dropPos.x;
/* 3797 */               entity.posY = dropPos.y;
/* 3798 */               entity.posZ = dropPos.z;
/* 3799 */               dropEntityParachute(entity);
/*      */               break;
/*      */             } 
/*      */           } else {
/* 3803 */             ret = true;
/* 3804 */             setUnmountPosition((Entity)this.seats[i], (pos[i + 1]).pos);
/* 3805 */             entity.dismountRidingEntity();
/* 3806 */             setUnmountPosition(entity, (pos[i + 1]).pos);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3812 */     return ret;
/*      */   }
/*      */   
/*      */   public void setUnmountPosition(@Nullable Entity rByEntity, Vec3d pos) {
/* 3816 */     if (rByEntity != null) {
/* 3817 */       Vec3d v; MCH_AircraftInfo info = getAcInfo();
/*      */       
/* 3819 */       if (info != null && info.unmountPosition != null) {
/* 3820 */         v = getTransformedPosition(info.unmountPosition);
/*      */       } else {
/* 3822 */         double x = pos.x;
/* 3823 */         x = (x >= 0.0D) ? (x + 3.0D) : (x - 3.0D);
/* 3824 */         v = getTransformedPosition(x, 2.0D, pos.z);
/*      */       } 
/*      */       
/* 3827 */       rByEntity.setPosition(v.x, v.y, v.z);
/* 3828 */       this.listUnmountReserve.add(new UnmountReserve(this, rByEntity, v.x, v.y, v.z));
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean unmountEntityFromSeat(@Nullable Entity entity) {
/* 3833 */     if (entity != null && this.seats != null && this.seats.length != 0) {
/* 3834 */       for (MCH_EntitySeat seat : this.seats) {
/* 3835 */         if (seat != null && seat.getRiddenByEntity() != null && W_Entity.isEqual(seat.getRiddenByEntity(), entity)) {
/* 3836 */           entity.dismountRidingEntity();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 3841 */     return false;
/*      */   }
/*      */   
/*      */   public void ejectSeat(@Nullable Entity entity) {
/* 3845 */     int sid = getSeatIdByEntity(entity);
/* 3846 */     if (sid >= 0 && sid <= 1 && 
/* 3847 */       getGuiInventory().haveParachute()) {
/* 3848 */       if (sid == 0) {
/* 3849 */         getGuiInventory().consumeParachute();
/* 3850 */         unmountEntity();
/* 3851 */         ejectSeatSub(entity, 0);
/* 3852 */         entity = getEntityBySeatId(1);
/* 3853 */         if (entity instanceof EntityPlayer) {
/* 3854 */           entity = null;
/*      */         }
/*      */       } 
/*      */       
/* 3858 */       if (getGuiInventory().haveParachute() && entity != null) {
/* 3859 */         getGuiInventory().consumeParachute();
/* 3860 */         unmountEntityFromSeat(entity);
/* 3861 */         ejectSeatSub(entity, 1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void ejectSeatSub(Entity entity, int sid) {
/* 3868 */     Vec3d pos = (getSeatInfo(sid) != null) ? (getSeatInfo(sid)).pos : null;
/* 3869 */     if (pos != null) {
/* 3870 */       Vec3d vec3d = getTransformedPosition(pos.x, pos.y + 2.0D, pos.z);
/* 3871 */       entity.setPosition(vec3d.x, vec3d.y, vec3d.z);
/*      */     } 
/*      */     
/* 3874 */     Vec3d v = MCH_Lib.RotVec3(0.0D, 2.0D, 0.0D, -getRotYaw(), -getRotPitch(), -getRotRoll());
/* 3875 */     entity.motionX = this.motionX + v.x + (this.rand.nextFloat() - 0.5D) * 0.1D;
/* 3876 */     entity.motionY = this.motionY + v.y;
/* 3877 */     entity.motionZ = this.motionZ + v.z + (this.rand.nextFloat() - 0.5D) * 0.1D;
/* 3878 */     MCH_EntityParachute parachute = new MCH_EntityParachute(this.world, entity.posX, entity.posY, entity.posZ);
/* 3879 */     parachute.rotationYaw = entity.rotationYaw;
/* 3880 */     parachute.motionX = entity.motionX;
/* 3881 */     parachute.motionY = entity.motionY;
/* 3882 */     parachute.motionZ = entity.motionZ;
/* 3883 */     parachute.fallDistance = entity.fallDistance;
/* 3884 */     parachute.user = entity;
/* 3885 */     parachute.setType(2);
/* 3886 */     this.world.spawnEntity((Entity)parachute);
/* 3887 */     if (getAcInfo().haveCanopy() && isCanopyClose()) {
/* 3888 */       openCanopy_EjectSeat();
/*      */     }
/*      */     
/* 3891 */     W_WorldFunc.MOD_playSoundAtEntity(entity, "eject_seat", 5.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public boolean canEjectSeat(@Nullable Entity entity) {
/* 3895 */     int sid = getSeatIdByEntity(entity);
/* 3896 */     return ((sid != 0 || !isUAV()) && sid >= 0 && sid < 2 && getAcInfo() != null && (getAcInfo()).isEnableEjectionSeat);
/*      */   }
/*      */   
/*      */   public int getNumEjectionSeat() {
/* 3900 */     return 0;
/*      */   }
/*      */   
/*      */   public int getMountedEntityNum() {
/* 3904 */     int num = 0;
/* 3905 */     if (getRiddenByEntity() != null && !(getRiddenByEntity()).isDead) {
/* 3906 */       num++;
/*      */     }
/*      */     
/* 3909 */     if (this.seats != null) {
/* 3910 */       for (MCH_EntitySeat seat : this.seats) {
/* 3911 */         if (seat != null && seat.getRiddenByEntity() != null && !(seat.getRiddenByEntity()).isDead) {
/* 3912 */           num++;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 3917 */     return num;
/*      */   }
/*      */   
/*      */   public void mountMobToSeats() {
/* 3921 */     List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(W_Lib.getEntityLivingBaseClass(), getEntityBoundingBox().grow(3.0D, 2.0D, 3.0D));
/*      */     
/* 3923 */     for (Entity entity : list) {
/* 3924 */       if (!(entity instanceof EntityPlayer) && entity.getRidingEntity() == null) {
/* 3925 */         int sid = 1;
/*      */         
/* 3927 */         for (MCH_EntitySeat seat : getSeats()) {
/* 3928 */           if (seat != null && seat.getRiddenByEntity() == null && !isMountedEntity(entity) && canRideSeatOrRack(sid, entity)) {
/* 3929 */             if (getSeatInfo(sid) instanceof MCH_SeatRackInfo) {
/*      */               break;
/*      */             }
/*      */             
/* 3933 */             entity.startRiding((Entity)seat);
/*      */           } 
/*      */           
/* 3936 */           sid++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void mountEntityToRack() {
/* 3943 */     if (!MCH_Config.EnablePutRackInFlying.prmBool) {
/* 3944 */       if (getCurrentThrottle() > 0.3D) {
/*      */         return;
/*      */       }
/*      */       
/* 3948 */       Block block = MCH_Lib.getBlockY((Entity)this, 1, -3, true);
/* 3949 */       if (block == null || W_Block.isEqual(block, Blocks.AIR)) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/* 3954 */     int countRideEntity = 0;
/*      */     
/* 3956 */     for (int sid = 0; sid < getSeatNum(); sid++) {
/* 3957 */       MCH_EntitySeat seat = getSeat(sid);
/* 3958 */       MCH_SeatInfo mCH_SeatInfo = getSeatInfo(1 + sid); if (mCH_SeatInfo instanceof MCH_SeatRackInfo) { MCH_SeatRackInfo info = (MCH_SeatRackInfo)mCH_SeatInfo; if (seat != null && seat.getRiddenByEntity() == null) {
/* 3959 */           Vec3d v = MCH_Lib.RotVec3(
/* 3960 */               (info.getEntryPos()).x, (info.getEntryPos()).y, (info.getEntryPos()).z, -getRotYaw(), -getRotPitch(), -getRotRoll());
/*      */           
/* 3962 */           v = v.addVector(this.posX, this.posY, this.posZ);
/* 3963 */           AxisAlignedBB bb = new AxisAlignedBB(v.x, v.y, v.z, v.x, v.y, v.z);
/* 3964 */           float range = info.range;
/* 3965 */           List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, bb.grow(range, range, range));
/*      */           
/* 3967 */           for (Entity entity : list) {
/* 3968 */             if (canRideSeatOrRack(1 + sid, entity)) {
/* 3969 */               if (entity instanceof MCH_IEntityCanRideAircraft) {
/* 3970 */                 if (((MCH_IEntityCanRideAircraft)entity).canRideAircraft(this, sid, info)) {
/* 3971 */                   MCH_Lib.DbgLog(this.world, "MCH_EntityAircraft.mountEntityToRack:%d:%s", new Object[] { Integer.valueOf(sid), entity });
/* 3972 */                   entity.startRiding((Entity)seat);
/* 3973 */                   countRideEntity++; break;
/*      */                 }  continue;
/*      */               } 
/* 3976 */               if (entity.getRidingEntity() == null) {
/* 3977 */                 NBTTagCompound nbt = entity.getEntityData();
/* 3978 */                 if (nbt.hasKey("CanMountEntity") && nbt.getBoolean("CanMountEntity")) {
/* 3979 */                   MCH_Lib.DbgLog(this.world, "MCH_EntityAircraft.mountEntityToRack:%d:%s:%s", new Object[] { Integer.valueOf(sid), entity, entity.getClass() });
/* 3980 */                   entity.startRiding((Entity)seat);
/* 3981 */                   countRideEntity++;
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }  }
/*      */     
/*      */     } 
/* 3990 */     if (countRideEntity > 0) {
/* 3991 */       W_WorldFunc.DEF_playSoundEffect(this.world, this.posX, this.posY, this.posZ, "random.click", 1.0F, 1.0F);
/*      */     }
/*      */   }
/*      */   
/*      */   public void unmountEntityFromRack() {
/* 3996 */     for (int sid = getSeatNum() - 1; sid >= 0; sid--) {
/* 3997 */       MCH_EntitySeat seat = getSeat(sid);
/* 3998 */       MCH_SeatInfo mCH_SeatInfo = getSeatInfo(sid + 1); if (mCH_SeatInfo instanceof MCH_SeatRackInfo) { MCH_SeatRackInfo info = (MCH_SeatRackInfo)mCH_SeatInfo; if (seat != null && seat.getRiddenByEntity() != null) {
/* 3999 */           Entity entity = seat.getRiddenByEntity();
/* 4000 */           Vec3d pos = info.getEntryPos();
/* 4001 */           if (entity instanceof MCH_EntityAircraft) {
/* 4002 */             if (pos.z >= (getAcInfo()).bbZ) {
/* 4003 */               pos = pos.addVector(0.0D, 0.0D, 12.0D);
/*      */             } else {
/* 4005 */               pos = pos.addVector(0.0D, 0.0D, -12.0D);
/*      */             } 
/*      */           }
/*      */           
/* 4009 */           Vec3d v = MCH_Lib.RotVec3(pos.x, pos.y, pos.z, -getRotYaw(), -getRotPitch(), -getRotRoll());
/* 4010 */           seat.posX = entity.posX = this.posX + v.x;
/* 4011 */           seat.posY = entity.posY = this.posY + v.y;
/* 4012 */           seat.posZ = entity.posZ = this.posZ + v.z;
/* 4013 */           UnmountReserve ur = new UnmountReserve(this, entity, entity.posX, entity.posY, entity.posZ);
/* 4014 */           ur.cnt = 8;
/* 4015 */           this.listUnmountReserve.add(ur);
/* 4016 */           entity.dismountRidingEntity();
/* 4017 */           if (MCH_Lib.getBlockIdY((Entity)this, 3, -20) > 0) {
/* 4018 */             MCH_Lib.DbgLog(this.world, "MCH_EntityAircraft.unmountEntityFromRack:%d:%s", new Object[] { Integer.valueOf(sid), entity }); break;
/*      */           } 
/* 4020 */           MCH_Lib.DbgLog(this.world, "MCH_EntityAircraft.unmountEntityFromRack:%d Parachute:%s", new Object[] { Integer.valueOf(sid), entity });
/* 4021 */           dropEntityParachute(entity);
/*      */           break;
/*      */         }  }
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public void dropEntityParachute(Entity entity) {
/* 4029 */     entity.motionX = this.motionX;
/* 4030 */     entity.motionY = this.motionY;
/* 4031 */     entity.motionZ = this.motionZ;
/* 4032 */     MCH_EntityParachute parachute = new MCH_EntityParachute(this.world, entity.posX, entity.posY, entity.posZ);
/* 4033 */     parachute.rotationYaw = entity.rotationYaw;
/* 4034 */     parachute.motionX = entity.motionX;
/* 4035 */     parachute.motionY = entity.motionY;
/* 4036 */     parachute.motionZ = entity.motionZ;
/* 4037 */     parachute.fallDistance = entity.fallDistance;
/* 4038 */     parachute.user = entity;
/* 4039 */     parachute.setType(3);
/* 4040 */     this.world.spawnEntity((Entity)parachute);
/*      */   }
/*      */   
/*      */   public void rideRack() {
/* 4044 */     if (getRidingEntity() == null) {
/* 4045 */       AxisAlignedBB bb = getCollisionBoundingBox();
/* 4046 */       List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, bb.grow(60.0D, 60.0D, 60.0D));
/*      */       
/* 4048 */       for (Entity entity : list) {
/* 4049 */         if (entity instanceof MCH_EntityAircraft) { MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
/* 4050 */           if (ac.getAcInfo() != null)
/* 4051 */             for (int sid = 0; sid < ac.getSeatNum(); sid++) {
/* 4052 */               MCH_SeatInfo seatInfo = ac.getSeatInfo(1 + sid);
/* 4053 */               if (seatInfo instanceof MCH_SeatRackInfo) { MCH_SeatRackInfo info = (MCH_SeatRackInfo)seatInfo; if (ac.canRideSeatOrRack(1 + sid, entity)) {
/* 4054 */                   MCH_EntitySeat seat = ac.getSeat(sid);
/* 4055 */                   if (seat != null && seat.getRiddenByEntity() == null) {
/* 4056 */                     Vec3d v = ac.getTransformedPosition(info.getEntryPos());
/* 4057 */                     float r = info.range;
/* 4058 */                     if (this.posX >= v.x - r && this.posX <= v.x + r && this.posY >= v.y - r && this.posY <= v.y + r && this.posZ >= v.z - r && this.posZ <= v.z + r && 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/* 4064 */                       canRideAircraft(ac, sid, info)) {
/* 4065 */                       W_WorldFunc.DEF_playSoundEffect(this.world, this.posX, this.posY, this.posZ, "random.click", 1.0F, 1.0F);
/* 4066 */                       startRiding((Entity)seat);
/*      */                       return;
/*      */                     } 
/*      */                   } 
/*      */                 }  }
/*      */             
/*      */             }   }
/*      */       
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean canPutToRack() {
/* 4079 */     for (int i = 0; i < getSeatNum(); i++) {
/* 4080 */       MCH_EntitySeat seat = getSeat(i);
/* 4081 */       MCH_SeatInfo seatInfo = getSeatInfo(i + 1);
/* 4082 */       if (seat != null && seat.getRiddenByEntity() == null && seatInfo instanceof MCH_SeatRackInfo) {
/* 4083 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 4087 */     return false;
/*      */   }
/*      */   
/*      */   public boolean canDownFromRack() {
/* 4091 */     for (int i = 0; i < getSeatNum(); i++) {
/* 4092 */       MCH_EntitySeat seat = getSeat(i);
/* 4093 */       MCH_SeatInfo seatInfo = getSeatInfo(i + 1);
/* 4094 */       if (seat != null && seat.getRiddenByEntity() != null && seatInfo instanceof MCH_SeatRackInfo) {
/* 4095 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 4099 */     return false;
/*      */   }
/*      */   
/*      */   public void checkRideRack() {
/* 4103 */     if (getCountOnUpdate() % 10 == 0) {
/* 4104 */       this.canRideRackStatus = false;
/* 4105 */       if (getRidingEntity() == null) {
/* 4106 */         AxisAlignedBB bb = getCollisionBoundingBox();
/* 4107 */         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, bb.grow(60.0D, 60.0D, 60.0D));
/*      */         
/* 4109 */         for (Entity entity : list) {
/* 4110 */           if (entity instanceof MCH_EntityAircraft) { MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
/* 4111 */             if (ac.getAcInfo() != null) {
/* 4112 */               for (int sid = 0; sid < ac.getSeatNum(); sid++) {
/* 4113 */                 MCH_SeatInfo seatInfo = ac.getSeatInfo(1 + sid);
/* 4114 */                 if (seatInfo instanceof MCH_SeatRackInfo) { MCH_SeatRackInfo info = (MCH_SeatRackInfo)seatInfo;
/* 4115 */                   MCH_EntitySeat seat = ac.getSeat(sid);
/* 4116 */                   if (seat != null && seat.getRiddenByEntity() == null) {
/* 4117 */                     Vec3d v = ac.getTransformedPosition(info.getEntryPos());
/* 4118 */                     float r = info.range;
/* 4119 */                     if (this.posX >= v.x - r && this.posX <= v.x + r && this.posY >= v.y - r && this.posY <= v.y + r && this.posZ >= v.z - r && this.posZ <= v.z + r && 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/* 4125 */                       canRideAircraft(ac, sid, info)) {
/* 4126 */                       this.canRideRackStatus = true;
/*      */                       return;
/*      */                     } 
/*      */                   }  }
/*      */               
/*      */               } 
/*      */             } }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean canRideRack() {
/* 4140 */     return (getRidingEntity() == null && this.canRideRackStatus);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canRideAircraft(MCH_EntityAircraft ac, int seatID, MCH_SeatRackInfo info) {
/* 4145 */     if (getAcInfo() == null)
/* 4146 */       return false; 
/* 4147 */     if (ac.getRidingEntity() != null)
/* 4148 */       return false; 
/* 4149 */     if (getRidingEntity() != null) {
/* 4150 */       return false;
/*      */     }
/* 4152 */     boolean canRide = false;
/*      */     
/* 4154 */     for (String s : info.names) {
/* 4155 */       if (s.equalsIgnoreCase((getAcInfo()).name) || s.equalsIgnoreCase(getAcInfo().getKindName())) {
/* 4156 */         canRide = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 4161 */     if (!canRide) {
/* 4162 */       for (MCH_AircraftInfo.RideRack rr : (getAcInfo()).rideRacks) {
/* 4163 */         int id = ac.getAcInfo().getNumSeat() - 1 + rr.rackID - 1;
/* 4164 */         if (id == seatID && rr.name.equalsIgnoreCase((ac.getAcInfo()).name)) {
/* 4165 */           MCH_EntitySeat seat = ac.getSeat(ac.getAcInfo().getNumSeat() - 1 + rr.rackID - 1);
/* 4166 */           if (seat != null && seat.getRiddenByEntity() == null) {
/* 4167 */             canRide = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 4173 */       if (!canRide) {
/* 4174 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 4178 */     for (MCH_EntitySeat seat : getSeats()) {
/* 4179 */       if (seat != null && seat.getRiddenByEntity() instanceof MCH_IEntityCanRideAircraft) {
/* 4180 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 4184 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isMountedEntity(@Nullable Entity entity) {
/* 4189 */     return (entity != null && isMountedEntity(W_Entity.getEntityId(entity)));
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayer getFirstMountPlayer() {
/* 4194 */     if (getRiddenByEntity() instanceof EntityPlayer) {
/* 4195 */       return (EntityPlayer)getRiddenByEntity();
/*      */     }
/* 4197 */     for (MCH_EntitySeat seat : getSeats()) {
/* 4198 */       if (seat != null && seat.getRiddenByEntity() instanceof EntityPlayer) {
/* 4199 */         return (EntityPlayer)seat.getRiddenByEntity();
/*      */       }
/*      */     } 
/*      */     
/* 4203 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isMountedSameTeamEntity(@Nullable EntityLivingBase player) {
/* 4208 */     if (player != null && player.getTeam() != null) {
/* 4209 */       if (getRiddenByEntity() instanceof EntityLivingBase && player.isOnSameTeam(getRiddenByEntity())) {
/* 4210 */         return true;
/*      */       }
/* 4212 */       for (MCH_EntitySeat seat : getSeats()) {
/* 4213 */         if (seat != null && seat.getRiddenByEntity() instanceof EntityLivingBase && player.isOnSameTeam(seat.getRiddenByEntity())) {
/* 4214 */           return true;
/*      */         }
/*      */       } 
/*      */       
/* 4218 */       return false;
/*      */     } 
/*      */     
/* 4221 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isMountedOtherTeamEntity(@Nullable EntityLivingBase player) {
/* 4226 */     if (player != null) {
/*      */       
/* 4228 */       if (getRiddenByEntity() instanceof EntityLivingBase) {
/* 4229 */         EntityLivingBase target = (EntityLivingBase)getRiddenByEntity();
/* 4230 */         if (player.getTeam() != null && target.getTeam() != null && !player.isOnSameTeam((Entity)target)) {
/* 4231 */           return true;
/*      */         }
/*      */       } 
/*      */       
/* 4235 */       for (MCH_EntitySeat seat : getSeats()) {
/* 4236 */         if (seat != null && seat.getRiddenByEntity() instanceof EntityLivingBase) {
/* 4237 */           EntityLivingBase target = (EntityLivingBase)seat.getRiddenByEntity();
/* 4238 */           if (player.getTeam() != null && target.getTeam() != null && !player.isOnSameTeam((Entity)target)) {
/* 4239 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 4245 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isMountedEntity(int entityId) {
/* 4249 */     if (W_Entity.getEntityId(getRiddenByEntity()) == entityId) {
/* 4250 */       return true;
/*      */     }
/* 4252 */     for (MCH_EntitySeat seat : getSeats()) {
/* 4253 */       if (seat != null && seat.getRiddenByEntity() != null && W_Entity.getEntityId(seat.getRiddenByEntity()) == entityId) {
/* 4254 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 4258 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onInteractFirst(EntityPlayer player) {}
/*      */ 
/*      */   
/*      */   public boolean checkTeam(EntityPlayer player) {
/* 4266 */     for (int i = 0; i < 1 + getSeatNum(); i++) {
/* 4267 */       Entity entity = getEntityBySeatId(i);
/* 4268 */       if (entity instanceof EntityPlayer || entity instanceof MCH_EntityGunner) {
/* 4269 */         EntityLivingBase riddenEntity = (EntityLivingBase)entity;
/* 4270 */         if (riddenEntity.getTeam() != null && !riddenEntity.isOnSameTeam((Entity)player)) {
/* 4271 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 4276 */     return true;
/*      */   }
/*      */   
/*      */   public boolean processInitialInteract(EntityPlayer player, boolean ss, EnumHand hand) {
/* 4280 */     this.switchSeat = ss;
/* 4281 */     boolean ret = processInitialInteract(player, hand);
/* 4282 */     this.switchSeat = false;
/* 4283 */     return ret;
/*      */   }
/*      */   
/*      */   public boolean processInitialInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 4287 */     if (isDestroyed())
/* 4288 */       return false; 
/* 4289 */     if (getAcInfo() == null)
/* 4290 */       return false; 
/* 4291 */     if (!checkTeam(player)) {
/* 4292 */       return false;
/*      */     }
/* 4294 */     ItemStack itemStack = player.getHeldItem(hand);
/* 4295 */     if (!itemStack.isEmpty() && itemStack.getItem() instanceof com.norwood.mcheli.tool.MCH_ItemWrench) {
/* 4296 */       if (!this.world.isRemote && player.isSneaking()) {
/* 4297 */         switchNextTextureName();
/*      */       }
/*      */       
/* 4300 */       return false;
/* 4301 */     }  if (!itemStack.isEmpty() && itemStack.getItem() instanceof com.norwood.mcheli.mob.MCH_ItemSpawnGunner)
/* 4302 */       return false; 
/* 4303 */     if (player.isSneaking()) {
/* 4304 */       displayInventory(player);
/* 4305 */       return false;
/* 4306 */     }  if (!(getAcInfo()).canRide)
/* 4307 */       return false; 
/* 4308 */     if (getRiddenByEntity() != null || isUAV())
/* 4309 */       return interactFirstSeat(player); 
/* 4310 */     if (player.getRidingEntity() instanceof MCH_EntitySeat)
/* 4311 */       return false; 
/* 4312 */     if (!canRideSeatOrRack(0, (Entity)player)) {
/* 4313 */       return false;
/*      */     }
/* 4315 */     if (!this.switchSeat) {
/* 4316 */       if (getAcInfo().haveCanopy() && isCanopyClose()) {
/* 4317 */         openCanopy();
/* 4318 */         return false;
/*      */       } 
/*      */       
/* 4321 */       if (getModeSwitchCooldown() > 0) {
/* 4322 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 4326 */     closeCanopy();
/* 4327 */     this.lastRiddenByEntity = null;
/* 4328 */     initRadar();
/* 4329 */     if (!this.world.isRemote) {
/* 4330 */       player.startRiding((Entity)this);
/* 4331 */       if (!this.keepOnRideRotation) {
/* 4332 */         mountMobToSeats();
/*      */       }
/*      */     } else {
/* 4335 */       updateClientSettings(0);
/*      */     } 
/*      */     
/* 4338 */     setCameraId(0);
/* 4339 */     initPilotWeapon();
/* 4340 */     this.lowPassPartialTicks.clear();
/* 4341 */     if ((getAcInfo()).name.equalsIgnoreCase("uh-1c") && player instanceof EntityPlayerMP) {
/* 4342 */       MCH_CriteriaTriggers.RIDING_VALKYRIES.trigger((EntityPlayerMP)player);
/*      */     }
/*      */     
/* 4345 */     onInteractFirst(player);
/* 4346 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRideSeatOrRack(int seatId, Entity entity) {
/* 4352 */     if (getAcInfo() == null) {
/* 4353 */       return false;
/*      */     }
/* 4355 */     for (Integer[] a : (getAcInfo()).exclusionSeatList) {
/* 4356 */       if (Arrays.<Integer>asList(a).contains(Integer.valueOf(seatId))) {
/* 4357 */         int len$ = a.length; Integer[] arrayOfInteger; int i;
/*      */         byte b;
/* 4359 */         for (arrayOfInteger = a, i = arrayOfInteger.length, b = 0; b < i; ) { int id = arrayOfInteger[b].intValue();
/* 4360 */           if (getEntityBySeatId(id) != null) {
/* 4361 */             return false;
/*      */           }
/*      */           b++; }
/*      */       
/*      */       } 
/*      */     } 
/* 4367 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateClientSettings(int seatId) {
/* 4372 */     this.cs_dismountAll = MCH_Config.DismountAll.prmBool;
/* 4373 */     this.cs_heliAutoThrottleDown = MCH_Config.AutoThrottleDownHeli.prmBool;
/* 4374 */     this.cs_planeAutoThrottleDown = MCH_Config.AutoThrottleDownPlane.prmBool;
/* 4375 */     this.cs_tankAutoThrottleDown = MCH_Config.AutoThrottleDownTank.prmBool;
/* 4376 */     this.camera.setShaderSupport(seatId, Boolean.valueOf(W_EntityRenderer.isShaderSupport()));
/* 4377 */     MCH_PacketNotifyClientSetting.send();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canLockEntity(Entity entity) {
/* 4382 */     return !isMountedEntity(entity);
/*      */   }
/*      */   
/*      */   public void switchNextSeat(Entity entity) {
/* 4386 */     if (entity != null && 
/* 4387 */       this.seats != null && this.seats.length > 0 && 
/* 4388 */       isMountedEntity(entity)) {
/* 4389 */       boolean isFound = false;
/* 4390 */       int sid = 1;
/*      */       
/* 4392 */       for (MCH_EntitySeat seat : this.seats) {
/* 4393 */         if (seat != null) {
/* 4394 */           if (getSeatInfo(sid) instanceof MCH_SeatRackInfo) {
/*      */             break;
/*      */           }
/*      */           
/* 4398 */           if (W_Entity.isEqual(seat.getRiddenByEntity(), entity)) {
/* 4399 */             isFound = true;
/* 4400 */           } else if (isFound && seat.getRiddenByEntity() == null) {
/* 4401 */             entity.startRiding((Entity)seat);
/*      */             
/*      */             return;
/*      */           } 
/* 4405 */           sid++;
/*      */         } 
/*      */       } 
/*      */       
/* 4409 */       sid = 1;
/*      */       
/* 4411 */       for (MCH_EntitySeat seatx : this.seats) {
/* 4412 */         if (seatx != null && seatx.getRiddenByEntity() == null) {
/* 4413 */           if (!(getSeatInfo(sid) instanceof MCH_SeatRackInfo)) {
/* 4414 */             onMountPlayerSeat(seatx, entity);
/*      */             
/*      */             return;
/*      */           } 
/*      */           break;
/*      */         } 
/* 4420 */         sid++;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void switchPrevSeat(Entity entity) {
/* 4428 */     if (entity != null && 
/* 4429 */       this.seats != null && this.seats.length > 0 && 
/* 4430 */       isMountedEntity(entity)) {
/* 4431 */       boolean isFound = false;
/*      */       
/* 4433 */       for (int i = this.seats.length - 1; i >= 0; i--) {
/* 4434 */         MCH_EntitySeat seat = this.seats[i];
/* 4435 */         if (seat != null) {
/* 4436 */           if (W_Entity.isEqual(seat.getRiddenByEntity(), entity)) {
/* 4437 */             isFound = true;
/* 4438 */           } else if (isFound && seat.getRiddenByEntity() == null) {
/* 4439 */             entity.startRiding((Entity)seat);
/*      */             
/*      */             return;
/*      */           } 
/*      */         }
/*      */       } 
/* 4445 */       for (int ix = this.seats.length - 1; ix >= 0; ix--) {
/* 4446 */         MCH_EntitySeat seat = this.seats[ix];
/* 4447 */         if (!(getSeatInfo(ix + 1) instanceof MCH_SeatRackInfo) && seat != null && seat.getRiddenByEntity() == null) {
/* 4448 */           entity.startRiding((Entity)seat);
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity[] getParts() {
/* 4458 */     return this.partEntities;
/*      */   }
/*      */   
/*      */   public float getSoundVolume() {
/* 4462 */     return 1.0F;
/*      */   }
/*      */   
/*      */   public float getSoundPitch() {
/* 4466 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSoundName() {
/* 4472 */     if (getAcInfo() == null) {
/* 4473 */       return "";
/*      */     }
/* 4475 */     return !(getAcInfo()).soundMove.isEmpty() ? (getAcInfo()).soundMove : getDefaultSoundName();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSkipNormalRender() {
/* 4481 */     return getRidingEntity() instanceof MCH_EntitySeat;
/*      */   }
/*      */   
/*      */   public boolean isRenderBullet(Entity entity, Entity rider) {
/* 4485 */     return (!isCameraView(rider) || !W_Entity.isEqual((Entity)getTVMissile(), entity) || !W_Entity.isEqual((getTVMissile()).shootingEntity, rider));
/*      */   }
/*      */   
/*      */   public boolean isCameraView(Entity entity) {
/* 4489 */     return (getIsGunnerMode(entity) || isUAV());
/*      */   }
/*      */   
/*      */   public void updateCamera(double x, double y, double z) {
/* 4493 */     if (this.world.isRemote) {
/* 4494 */       if (getTVMissile() != null) {
/* 4495 */         this.camera.setPosition(this.TVmissile.posX, this.TVmissile.posY, this.TVmissile.posZ);
/* 4496 */         this.camera.setCameraZoom(1.0F);
/* 4497 */         this.TVmissile.isSpawnParticle = !isMissileCameraMode(this.TVmissile.shootingEntity);
/*      */       } else {
/* 4499 */         setTVMissile((MCH_EntityTvMissile)null);
/* 4500 */         MCH_AircraftInfo.CameraPosition cpi = getCameraPosInfo();
/* 4501 */         Vec3d cp = (cpi != null) ? cpi.pos : Vec3d.ZERO;
/* 4502 */         Vec3d v = MCH_Lib.RotVec3(cp, -getRotYaw(), -getRotPitch(), -getRotRoll());
/* 4503 */         this.camera.setPosition(x + v.x, y + v.y, z + v.z);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void updateCameraRotate(float yaw, float pitch) {
/* 4509 */     this.camera.prevRotationYaw = this.camera.rotationYaw;
/* 4510 */     this.camera.prevRotationPitch = this.camera.rotationPitch;
/* 4511 */     this.camera.rotationYaw = yaw;
/* 4512 */     this.camera.rotationPitch = pitch;
/*      */   }
/*      */   
/*      */   public void updatePartCameraRotate() {
/* 4516 */     if (this.world.isRemote) {
/* 4517 */       Entity e = getEntityBySeatId(1);
/* 4518 */       if (e == null) {
/* 4519 */         e = getRiddenByEntity();
/*      */       }
/*      */       
/* 4522 */       if (e != null) {
/* 4523 */         this.camera.partRotationYaw = e.rotationYaw;
/* 4524 */         float pitch = e.rotationPitch;
/* 4525 */         this.camera.prevPartRotationYaw = this.camera.partRotationYaw;
/* 4526 */         this.camera.prevPartRotationPitch = this.camera.partRotationPitch;
/* 4527 */         this.camera.partRotationPitch = pitch;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MCH_EntityTvMissile getTVMissile() {
/* 4534 */     return (this.TVmissile != null && !this.TVmissile.isDead) ? this.TVmissile : null;
/*      */   }
/*      */   
/*      */   public void setTVMissile(MCH_EntityTvMissile entity) {
/* 4538 */     this.TVmissile = entity;
/*      */   }
/*      */   
/*      */   public MCH_WeaponSet[] createWeapon(int seat_num) {
/* 4542 */     this.currentWeaponID = new int[seat_num];
/*      */     
/* 4544 */     Arrays.fill(this.currentWeaponID, -1);
/*      */     
/* 4546 */     if (getAcInfo() != null && !(getAcInfo()).weaponSetList.isEmpty() && seat_num > 0) {
/* 4547 */       MCH_WeaponSet[] weaponSetArray = new MCH_WeaponSet[(getAcInfo()).weaponSetList.size()];
/*      */       
/* 4549 */       for (int i = 0; i < (getAcInfo()).weaponSetList.size(); i++) {
/* 4550 */         MCH_AircraftInfo.WeaponSet ws = (getAcInfo()).weaponSetList.get(i);
/* 4551 */         MCH_WeaponBase[] wb = new MCH_WeaponBase[ws.weapons.size()];
/*      */         
/* 4553 */         for (int j = 0; j < ws.weapons.size(); j++) {
/* 4554 */           wb[j] = MCH_WeaponCreator.createWeapon(this.world, ws.type, ((MCH_AircraftInfo.Weapon)ws.weapons
/* 4555 */               .get(j)).pos, ((MCH_AircraftInfo.Weapon)ws.weapons.get(j)).yaw, ((MCH_AircraftInfo.Weapon)ws.weapons.get(j)).pitch, this, ((MCH_AircraftInfo.Weapon)ws.weapons.get(j)).turret);
/*      */           
/* 4557 */           (wb[j]).aircraft = this;
/*      */         } 
/*      */         
/* 4560 */         if (wb.length > 0 && wb[0] != null) {
/* 4561 */           float defYaw = ((MCH_AircraftInfo.Weapon)ws.weapons.get(0)).defaultYaw;
/* 4562 */           weaponSetArray[i] = new MCH_WeaponSet(wb);
/* 4563 */           (weaponSetArray[i]).prevRotationYaw = defYaw;
/* 4564 */           (weaponSetArray[i]).rotationYaw = defYaw;
/* 4565 */           (weaponSetArray[i]).defaultRotationYaw = defYaw;
/*      */         } 
/*      */       } 
/*      */       
/* 4569 */       return weaponSetArray;
/*      */     } 
/* 4571 */     return new MCH_WeaponSet[] { this.dummyWeapon };
/*      */   }
/*      */ 
/*      */   
/*      */   public void switchWeapon(Entity entity, int id) {
/* 4576 */     int sid = getSeatIdByEntity(entity);
/* 4577 */     if (isValidSeatID(sid) && 
/* 4578 */       getWeaponNum() > 0 && this.currentWeaponID.length > 0) {
/* 4579 */       if (id < 0) {
/* 4580 */         this.currentWeaponID[sid] = -1;
/*      */       }
/*      */       
/* 4583 */       if (id >= getWeaponNum()) {
/* 4584 */         id = getWeaponNum() - 1;
/*      */       }
/*      */       
/* 4587 */       MCH_Lib.DbgLog(this.world, "switchWeapon:" + W_Entity.getEntityId(entity) + " -> " + id, new Object[0]);
/* 4588 */       getCurrentWeapon(entity).reload();
/* 4589 */       this.currentWeaponID[sid] = id;
/* 4590 */       MCH_WeaponSet ws = getCurrentWeapon(entity);
/* 4591 */       ws.onSwitchWeapon(this.world.isRemote, isInfinityAmmo(entity));
/* 4592 */       if (!this.world.isRemote) {
/* 4593 */         MCH_PacketNotifyWeaponID.send((Entity)this, sid, id, ws.getAmmoNum(), ws.getRestAllAmmoNum());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateWeaponID(int sid, int id) {
/* 4600 */     if (sid >= 0 && sid < this.currentWeaponID.length && 
/* 4601 */       getWeaponNum() > 0 && this.currentWeaponID.length > 0) {
/* 4602 */       if (id < 0) {
/* 4603 */         this.currentWeaponID[sid] = -1;
/*      */       }
/*      */       
/* 4606 */       if (id >= getWeaponNum()) {
/* 4607 */         id = getWeaponNum() - 1;
/*      */       }
/*      */       
/* 4610 */       MCH_Lib.DbgLog(this.world, "switchWeapon:seatID=" + sid + ", WeaponID=" + id, new Object[0]);
/* 4611 */       this.currentWeaponID[sid] = id;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateWeaponRestAmmo(int id, int num) {
/* 4617 */     if (id < getWeaponNum()) {
/* 4618 */       getWeapon(id).setRestAllAmmoNum(num);
/*      */     }
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MCH_WeaponSet getWeaponByName(String name) {
/* 4624 */     for (MCH_WeaponSet ws : this.weapons) {
/* 4625 */       if (ws.isEqual(name)) {
/* 4626 */         return ws;
/*      */       }
/*      */     } 
/*      */     
/* 4630 */     return null;
/*      */   }
/*      */   
/*      */   public int getWeaponIdByName(String name) {
/* 4634 */     int id = 0;
/*      */     
/* 4636 */     for (MCH_WeaponSet ws : this.weapons) {
/* 4637 */       if (ws.isEqual(name)) {
/* 4638 */         return id;
/*      */       }
/*      */       
/* 4641 */       id++;
/*      */     } 
/*      */     
/* 4644 */     return -1;
/*      */   }
/*      */   
/*      */   public void reloadAllWeapon() {
/* 4648 */     for (int i = 0; i < getWeaponNum(); i++) {
/* 4649 */       getWeapon(i).reloadMag();
/*      */     }
/*      */   }
/*      */   
/*      */   public MCH_WeaponSet getFirstSeatWeapon() {
/* 4654 */     return (this.currentWeaponID != null && this.currentWeaponID.length > 0 && this.currentWeaponID[0] >= 0) ? 
/* 4655 */       getWeapon(this.currentWeaponID[0]) : 
/* 4656 */       getWeapon(0);
/*      */   }
/*      */   
/*      */   public void initCurrentWeapon(Entity entity) {
/* 4660 */     int sid = getSeatIdByEntity(entity);
/* 4661 */     MCH_Lib.DbgLog(this.world, "initCurrentWeapon:" + W_Entity.getEntityId(entity) + ":%d", new Object[] { Integer.valueOf(sid) });
/* 4662 */     if (sid >= 0 && sid < this.currentWeaponID.length) {
/* 4663 */       this.currentWeaponID[sid] = -1;
/* 4664 */       if (entity instanceof EntityPlayer || entity instanceof MCH_EntityGunner) {
/* 4665 */         this.currentWeaponID[sid] = getNextWeaponID(entity, 1);
/* 4666 */         switchWeapon(entity, getCurrentWeaponID(entity));
/* 4667 */         if (this.world.isRemote) {
/* 4668 */           MCH_PacketIndNotifyAmmoNum.send(this, -1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void initPilotWeapon() {
/* 4675 */     this.currentWeaponID[0] = -1;
/*      */   }
/*      */   
/*      */   public MCH_WeaponSet getCurrentWeapon(Entity entity) {
/* 4679 */     return getWeapon(getCurrentWeaponID(entity));
/*      */   }
/*      */   
/*      */   protected MCH_WeaponSet getWeapon(int id) {
/* 4683 */     return (id >= 0 && this.weapons.length > 0 && id < this.weapons.length) ? this.weapons[id] : this.dummyWeapon;
/*      */   }
/*      */   
/*      */   public int getWeaponIDBySeatID(int sid) {
/* 4687 */     return (sid >= 0 && sid < this.currentWeaponID.length) ? this.currentWeaponID[sid] : -1;
/*      */   }
/*      */   
/*      */   public double getLandInDistance(Entity user) {
/* 4691 */     if (this.lastCalcLandInDistanceCount != getCountOnUpdate() && getCountOnUpdate() % 5 == 0) {
/* 4692 */       this.lastCalcLandInDistanceCount = getCountOnUpdate();
/* 4693 */       MCH_WeaponParam prm = new MCH_WeaponParam();
/* 4694 */       prm.setPosition(this.posX, this.posY, this.posZ);
/* 4695 */       prm.entity = (Entity)this;
/* 4696 */       prm.user = user;
/* 4697 */       prm.isInfinity = isInfinityAmmo(prm.user);
/* 4698 */       if (prm.user != null) {
/* 4699 */         MCH_WeaponSet currentWs = getCurrentWeapon(prm.user);
/* 4700 */         if (currentWs != null) {
/* 4701 */           int sid = getSeatIdByEntity(prm.user);
/* 4702 */           if (getAcInfo().getWeaponSetById(sid) != null) {
/* 4703 */             prm.isTurret = ((MCH_AircraftInfo.Weapon)(getAcInfo().getWeaponSetById(sid)).weapons.get(0)).turret;
/*      */           }
/*      */           
/* 4706 */           this.lastLandInDistance = currentWs.getLandInDistance(prm);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 4711 */     return this.lastLandInDistance;
/*      */   }
/*      */   
/*      */   public boolean useCurrentWeapon(Entity user) {
/* 4715 */     MCH_WeaponParam prm = new MCH_WeaponParam();
/* 4716 */     prm.setPosition(this.posX, this.posY, this.posZ);
/* 4717 */     prm.entity = (Entity)this;
/* 4718 */     prm.user = user;
/* 4719 */     return useCurrentWeapon(prm);
/*      */   }
/*      */   
/*      */   public boolean useCurrentWeapon(MCH_WeaponParam prm) {
/* 4723 */     prm.isInfinity = isInfinityAmmo(prm.user);
/* 4724 */     if (prm.user != null) {
/* 4725 */       MCH_WeaponSet currentWs = getCurrentWeapon(prm.user);
/* 4726 */       if (currentWs != null && currentWs.canUse()) {
/* 4727 */         int sid = getSeatIdByEntity(prm.user);
/* 4728 */         if (getAcInfo().getWeaponSetById(sid) != null) {
/* 4729 */           prm.isTurret = ((MCH_AircraftInfo.Weapon)(getAcInfo().getWeaponSetById(sid)).weapons.get(0)).turret;
/*      */         }
/*      */         
/* 4732 */         int lastUsedIndex = currentWs.getCurrentWeaponIndex();
/* 4733 */         if (currentWs.use(prm)) {
/* 4734 */           for (MCH_WeaponSet ws : this.weapons) {
/* 4735 */             if (ws != currentWs && !(ws.getInfo()).group.isEmpty() && (ws.getInfo()).group.equals((currentWs.getInfo()).group)) {
/* 4736 */               ws.waitAndReloadByOther(prm.reload);
/*      */             }
/*      */           } 
/*      */           
/* 4740 */           if (!this.world.isRemote) {
/* 4741 */             int shift = 0;
/*      */             
/* 4743 */             for (MCH_WeaponSet wsx : this.weapons) {
/* 4744 */               if (wsx == currentWs) {
/*      */                 break;
/*      */               }
/*      */               
/* 4748 */               shift += wsx.getWeaponNum();
/*      */             } 
/*      */             
/* 4751 */             shift += lastUsedIndex;
/* 4752 */             this.useWeaponStat |= (shift < 32) ? (1 << shift) : 0;
/*      */           } 
/*      */           
/* 4755 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 4760 */     return false;
/*      */   }
/*      */   
/*      */   public void switchCurrentWeaponMode(Entity entity) {
/* 4764 */     getCurrentWeapon(entity).switchMode();
/*      */   }
/*      */   
/*      */   public int getWeaponNum() {
/* 4768 */     return this.weapons.length;
/*      */   }
/*      */   
/*      */   public int getCurrentWeaponID(Entity entity) {
/* 4772 */     if (!(entity instanceof EntityPlayer) && !(entity instanceof MCH_EntityGunner)) {
/* 4773 */       return -1;
/*      */     }
/* 4775 */     int id = getSeatIdByEntity(entity);
/* 4776 */     return (id >= 0 && id < this.currentWeaponID.length) ? this.currentWeaponID[id] : -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNextWeaponID(Entity entity, int step) {
/* 4781 */     if (getAcInfo() == null) {
/* 4782 */       return -1;
/*      */     }
/* 4784 */     int sid = getSeatIdByEntity(entity);
/* 4785 */     if (sid < 0) {
/* 4786 */       return -1;
/*      */     }
/* 4788 */     int id = getCurrentWeaponID(entity);
/*      */     
/*      */     int i;
/* 4791 */     for (i = 0; i < getWeaponNum(); i++) {
/* 4792 */       if (step >= 0) {
/* 4793 */         id = (id + 1) % getWeaponNum();
/*      */       } else {
/* 4795 */         id = (id > 0) ? (id - 1) : (getWeaponNum() - 1);
/*      */       } 
/*      */       
/* 4798 */       MCH_AircraftInfo.Weapon w = getAcInfo().getWeaponById(id);
/* 4799 */       if (w != null) {
/* 4800 */         MCH_WeaponInfo wi = getWeaponInfoById(id);
/* 4801 */         int wpsid = getWeaponSeatID(wi, w);
/* 4802 */         if (wpsid < getSeatNum() + 1 + 1 && (wpsid == sid || (sid == 0 && w.canUsePilot && 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4807 */           !(getEntityBySeatId(wpsid) instanceof EntityPlayer) && 
/* 4808 */           !(getEntityBySeatId(wpsid) instanceof MCH_EntityGunner)))) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4815 */     if (i >= getWeaponNum()) {
/* 4816 */       return -1;
/*      */     }
/* 4818 */     MCH_Lib.DbgLog(this.world, "getNextWeaponID:%d:->%d", new Object[] { Integer.valueOf(W_Entity.getEntityId(entity)), Integer.valueOf(id) });
/* 4819 */     return id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWeaponSeatID(MCH_WeaponInfo wi, MCH_AircraftInfo.Weapon w) {
/* 4826 */     return (wi == null || (wi.target & 0xC3) != 0 || !wi.type.isEmpty() || (!MCH_MOD.proxy.isSinglePlayer() && !MCH_Config.TestMode.prmBool)) ? w.seatID : 1000;
/*      */   }
/*      */   
/*      */   public boolean isMissileCameraMode(Entity entity) {
/* 4830 */     return (getTVMissile() != null && isCameraView(entity));
/*      */   }
/*      */   
/*      */   public boolean isPilotReloading() {
/* 4834 */     return (getCommonStatus(2) || this.supplyAmmoWait > 0);
/*      */   }
/*      */   
/*      */   public int getUsedWeaponStat() {
/* 4838 */     if (getAcInfo() == null)
/* 4839 */       return 0; 
/* 4840 */     if (getAcInfo().getWeaponNum() <= 0) {
/* 4841 */       return 0;
/*      */     }
/* 4843 */     int stat = 0;
/* 4844 */     int i = 0;
/*      */     
/* 4846 */     for (MCH_WeaponSet w : this.weapons) {
/* 4847 */       if (i >= 32) {
/*      */         break;
/*      */       }
/*      */       
/* 4851 */       for (int wi = 0; wi < w.getWeaponNum() && i < 32; wi++) {
/* 4852 */         stat |= w.isUsed(wi) ? (1 << i) : 0;
/* 4853 */         i++;
/*      */       } 
/*      */     } 
/*      */     
/* 4857 */     return stat;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWeaponNotCooldown(MCH_WeaponSet checkWs, int index) {
/* 4862 */     if (getAcInfo() == null)
/* 4863 */       return false; 
/* 4864 */     if (getAcInfo().getWeaponNum() <= 0) {
/* 4865 */       return false;
/*      */     }
/* 4867 */     int shift = 0;
/*      */     
/* 4869 */     for (MCH_WeaponSet ws : this.weapons) {
/* 4870 */       if (ws == checkWs) {
/*      */         break;
/*      */       }
/*      */       
/* 4874 */       shift += ws.getWeaponNum();
/*      */     } 
/*      */     
/* 4877 */     shift += index;
/* 4878 */     return ((this.useWeaponStat & 1 << shift) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateWeapons() {
/* 4883 */     if (getAcInfo() != null && 
/* 4884 */       getAcInfo().getWeaponNum() > 0) {
/* 4885 */       int prevUseWeaponStat = this.useWeaponStat;
/* 4886 */       if (!this.world.isRemote) {
/* 4887 */         this.useWeaponStat |= getUsedWeaponStat();
/* 4888 */         this.dataManager.set(USE_WEAPON, Integer.valueOf(this.useWeaponStat));
/* 4889 */         this.useWeaponStat = 0;
/*      */       } else {
/* 4891 */         this.useWeaponStat = ((Integer)this.dataManager.get(USE_WEAPON)).intValue();
/*      */       } 
/*      */       
/* 4894 */       float yaw = MathHelper.wrapDegrees(getRotYaw());
/* 4895 */       float pitch = MathHelper.wrapDegrees(getRotPitch());
/* 4896 */       int id = 0;
/*      */       
/* 4898 */       for (int wid = 0; wid < this.weapons.length; wid++) {
/* 4899 */         MCH_WeaponSet w = this.weapons[wid];
/* 4900 */         boolean isLongDelay = false;
/* 4901 */         if (w.getFirstWeapon() != null) {
/* 4902 */           isLongDelay = w.isLongDelayWeapon();
/*      */         }
/*      */         
/* 4905 */         boolean isSelected = false;
/*      */         
/* 4907 */         for (int swid : this.currentWeaponID) {
/* 4908 */           if (swid == wid) {
/* 4909 */             isSelected = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 4914 */         boolean isWpnUsed = false;
/*      */         
/* 4916 */         for (int index = 0; index < w.getWeaponNum(); index++) {
/* 4917 */           boolean isPrevUsed = (id < 32 && (prevUseWeaponStat & 1 << id) != 0);
/* 4918 */           boolean isUsed = (id < 32 && (this.useWeaponStat & 1 << id) != 0);
/* 4919 */           if (isLongDelay && isPrevUsed && isUsed) {
/* 4920 */             isUsed = false;
/*      */           }
/*      */           
/* 4923 */           isWpnUsed |= isUsed;
/* 4924 */           if (!isPrevUsed && isUsed) {
/* 4925 */             float recoil = (w.getInfo()).recoil;
/* 4926 */             if (recoil > 0.0F) {
/* 4927 */               this.recoilCount = 30;
/* 4928 */               this.recoilValue = recoil;
/* 4929 */               this.recoilYaw = w.rotationYaw;
/*      */             } 
/*      */           } 
/*      */           
/* 4933 */           if (this.world.isRemote && isUsed) {
/* 4934 */             Vec3d wrv = MCH_Lib.RotVec3(0.0D, 0.0D, -1.0D, -w.rotationYaw - yaw, -w.rotationPitch);
/* 4935 */             Vec3d spv = w.getCurrentWeapon().getShotPos((Entity)this);
/* 4936 */             spawnParticleMuzzleFlash(this.world, w.getInfo(), this.posX + spv.x, this.posY + spv.y, this.posZ + spv.z, wrv);
/*      */           } 
/*      */           
/* 4939 */           w.updateWeapon((Entity)this, isUsed, index);
/* 4940 */           id++;
/*      */         } 
/*      */         
/* 4943 */         w.update((Entity)this, isSelected, isWpnUsed);
/* 4944 */         MCH_AircraftInfo.Weapon wi = getAcInfo().getWeaponById(wid);
/* 4945 */         if (wi != null && !isDestroyed()) {
/* 4946 */           Entity entity = getEntityBySeatId(getWeaponSeatID(getWeaponInfoById(wid), wi));
/* 4947 */           if (wi.canUsePilot && !(entity instanceof EntityPlayer) && !(entity instanceof MCH_EntityGunner)) {
/* 4948 */             entity = getEntityBySeatId(0);
/*      */           }
/*      */           
/* 4951 */           if (!(entity instanceof EntityPlayer) && !(entity instanceof MCH_EntityGunner)) {
/* 4952 */             w.rotationTurretYaw = getLastRiderYaw() - getRotYaw();
/* 4953 */             if (getTowedChainEntity() != null || getRidingEntity() != null) {
/* 4954 */               w.rotationYaw = 0.0F;
/*      */             }
/*      */           } else {
/* 4957 */             if ((int)wi.minYaw != 0 || (int)wi.maxYaw != 0) {
/* 4958 */               float ty = wi.turret ? (MathHelper.wrapDegrees(getLastRiderYaw()) - yaw) : 0.0F;
/* 4959 */               float ey = MathHelper.wrapDegrees(entity.rotationYaw - yaw - wi.defaultYaw - ty);
/* 4960 */               if (Math.abs((int)wi.minYaw) < 360 && Math.abs((int)wi.maxYaw) < 360) {
/* 4961 */                 float targetYaw = MCH_Lib.RNG(ey, wi.minYaw, wi.maxYaw);
/* 4962 */                 float wy = w.rotationYaw - wi.defaultYaw - ty;
/* 4963 */                 if (targetYaw < wy) {
/* 4964 */                   if (wy - targetYaw > 15.0F) {
/* 4965 */                     wy -= 15.0F;
/*      */                   } else {
/* 4967 */                     wy = targetYaw;
/*      */                   } 
/* 4969 */                 } else if (targetYaw > wy) {
/* 4970 */                   if (targetYaw - wy > 15.0F) {
/* 4971 */                     wy += 15.0F;
/*      */                   } else {
/* 4973 */                     wy = targetYaw;
/*      */                   } 
/*      */                 } 
/*      */                 
/* 4977 */                 w.rotationYaw = wy + wi.defaultYaw + ty;
/*      */               } else {
/* 4979 */                 w.rotationYaw = ey + ty;
/*      */               } 
/*      */             } 
/*      */             
/* 4983 */             float ep = MathHelper.wrapDegrees(entity.rotationPitch - pitch);
/* 4984 */             w.rotationPitch = MCH_Lib.RNG(ep, wi.minPitch, wi.maxPitch);
/* 4985 */             w.rotationTurretYaw = 0.0F;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 4990 */       updateWeaponBay();
/* 4991 */       if (this.hitStatus > 0) {
/* 4992 */         this.hitStatus--;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateWeaponsRotation() {
/* 4999 */     if (getAcInfo() != null && 
/* 5000 */       getAcInfo().getWeaponNum() > 0 && 
/* 5001 */       !isDestroyed()) {
/* 5002 */       float yaw = MathHelper.wrapDegrees(getRotYaw());
/* 5003 */       float pitch = MathHelper.wrapDegrees(getRotPitch());
/*      */       
/* 5005 */       for (int wid = 0; wid < this.weapons.length; wid++) {
/* 5006 */         MCH_WeaponSet w = this.weapons[wid];
/* 5007 */         MCH_AircraftInfo.Weapon wi = getAcInfo().getWeaponById(wid);
/* 5008 */         if (wi != null) {
/* 5009 */           Entity entity = getEntityBySeatId(getWeaponSeatID(getWeaponInfoById(wid), wi));
/* 5010 */           if (wi.canUsePilot && !(entity instanceof EntityPlayer) && !(entity instanceof MCH_EntityGunner)) {
/* 5011 */             entity = getEntityBySeatId(0);
/*      */           }
/*      */           
/* 5014 */           if (!(entity instanceof EntityPlayer) && !(entity instanceof MCH_EntityGunner)) {
/* 5015 */             w.rotationTurretYaw = getLastRiderYaw() - getRotYaw();
/*      */           } else {
/* 5017 */             if ((int)wi.minYaw != 0 || (int)wi.maxYaw != 0) {
/* 5018 */               float ty = wi.turret ? (MathHelper.wrapDegrees(getLastRiderYaw()) - yaw) : 0.0F;
/* 5019 */               float ey = MathHelper.wrapDegrees(entity.rotationYaw - yaw - wi.defaultYaw - ty);
/* 5020 */               if (Math.abs((int)wi.minYaw) < 360 && Math.abs((int)wi.maxYaw) < 360) {
/* 5021 */                 float targetYaw = MCH_Lib.RNG(ey, wi.minYaw, wi.maxYaw);
/* 5022 */                 float wy = w.rotationYaw - wi.defaultYaw - ty;
/* 5023 */                 if (targetYaw < wy) {
/* 5024 */                   if (wy - targetYaw > 15.0F) {
/* 5025 */                     wy -= 15.0F;
/*      */                   } else {
/* 5027 */                     wy = targetYaw;
/*      */                   } 
/* 5029 */                 } else if (targetYaw > wy) {
/* 5030 */                   if (targetYaw - wy > 15.0F) {
/* 5031 */                     wy += 15.0F;
/*      */                   } else {
/* 5033 */                     wy = targetYaw;
/*      */                   } 
/*      */                 } 
/*      */                 
/* 5037 */                 w.rotationYaw = wy + wi.defaultYaw + ty;
/*      */               } else {
/* 5039 */                 w.rotationYaw = ey + ty;
/*      */               } 
/*      */             } 
/*      */             
/* 5043 */             float ep = MathHelper.wrapDegrees(entity.rotationPitch - pitch);
/* 5044 */             w.rotationPitch = MCH_Lib.RNG(ep, wi.minPitch, wi.maxPitch);
/* 5045 */             w.rotationTurretYaw = 0.0F;
/*      */           } 
/*      */         } 
/*      */         
/* 5049 */         w.prevRotationYaw = w.rotationYaw;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void spawnParticleMuzzleFlash(World w, MCH_WeaponInfo wi, double px, double py, double pz, Vec3d wrv) {
/* 5057 */     if (wi.listMuzzleFlashSmoke != null) {
/* 5058 */       for (MCH_WeaponInfo.MuzzleFlash mf : wi.listMuzzleFlashSmoke) {
/* 5059 */         double x = px + -wrv.x * mf.dist;
/* 5060 */         double y = py + -wrv.y * mf.dist;
/* 5061 */         double z = pz + -wrv.z * mf.dist;
/* 5062 */         MCH_ParticleParam p = new MCH_ParticleParam(w, "smoke", px, py, pz);
/* 5063 */         p.size = mf.size;
/*      */         
/* 5065 */         for (int i = 0; i < mf.num; i++) {
/* 5066 */           p.a = mf.a * 0.9F + w.rand.nextFloat() * 0.1F;
/* 5067 */           float color = w.rand.nextFloat() * 0.1F;
/* 5068 */           p.r = color + mf.r * 0.9F;
/* 5069 */           p.g = color + mf.g * 0.9F;
/* 5070 */           p.b = color + mf.b * 0.9F;
/* 5071 */           p.age = (int)(mf.age + 0.1D * mf.age * w.rand.nextFloat());
/* 5072 */           p.posX = x + (w.rand.nextDouble() - 0.5D) * mf.range;
/* 5073 */           p.posY = y + (w.rand.nextDouble() - 0.5D) * mf.range;
/* 5074 */           p.posZ = z + (w.rand.nextDouble() - 0.5D) * mf.range;
/* 5075 */           p.motionX = w.rand.nextDouble() * ((p.posX < x) ? -0.2D : 0.2D);
/* 5076 */           p.motionY = w.rand.nextDouble() * ((p.posY < y) ? -0.03D : 0.03D);
/* 5077 */           p.motionZ = w.rand.nextDouble() * ((p.posZ < z) ? -0.2D : 0.2D);
/* 5078 */           MCH_ParticlesUtil.spawnParticle(p);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 5083 */     if (wi.listMuzzleFlash != null) {
/* 5084 */       for (MCH_WeaponInfo.MuzzleFlash mf : wi.listMuzzleFlash) {
/* 5085 */         float color = this.rand.nextFloat() * 0.1F + 0.9F;
/* 5086 */         MCH_ParticlesUtil.spawnParticleExplode(this.world, px + -wrv.x * mf.dist, py + -wrv.y * mf.dist, pz + -wrv.z * mf.dist, mf.size, color * mf.r, color * mf.g, color * mf.b, mf.a, mf.age + w.rand
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 5096 */             .nextInt(3));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateWeaponBay() {
/* 5103 */     for (int i = 0; i < this.weaponBays.length; i++) {
/* 5104 */       WeaponBay wb = this.weaponBays[i];
/* 5105 */       MCH_AircraftInfo.WeaponBay info = (getAcInfo()).partWeaponBay.get(i);
/* 5106 */       boolean isSelected = false;
/* 5107 */       Integer[] arr$ = info.weaponIds;
/* 5108 */       int len$ = arr$.length; Integer[] arrayOfInteger1; int j;
/*      */       byte b;
/* 5110 */       for (arrayOfInteger1 = arr$, j = arrayOfInteger1.length, b = 0; b < j; ) { int wid = arrayOfInteger1[b].intValue();
/* 5111 */         for (int sid = 0; sid < this.currentWeaponID.length; sid++) {
/* 5112 */           if (wid == this.currentWeaponID[sid] && getEntityBySeatId(sid) != null) {
/* 5113 */             isSelected = true;
/*      */           }
/*      */         } 
/*      */         b++; }
/*      */       
/* 5118 */       wb.prevRot = wb.rot;
/* 5119 */       if (isSelected) {
/* 5120 */         if (wb.rot < 90.0F) {
/* 5121 */           wb.rot += 3.0F;
/*      */         }
/*      */         
/* 5124 */         if (wb.rot >= 90.0F) {
/* 5125 */           wb.rot = 90.0F;
/*      */         }
/*      */       } else {
/* 5128 */         if (wb.rot > 0.0F) {
/* 5129 */           wb.rot -= 3.0F;
/*      */         }
/*      */         
/* 5132 */         if (wb.rot <= 0.0F) {
/* 5133 */           wb.rot = 0.0F;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getHitStatus() {
/* 5140 */     return this.hitStatus;
/*      */   }
/*      */   
/*      */   public int getMaxHitStatus() {
/* 5144 */     return 15;
/*      */   }
/*      */   
/*      */   public void hitBullet() {
/* 5148 */     this.hitStatus = getMaxHitStatus();
/*      */   }
/*      */   
/*      */   public void initRotationYaw(float yaw) {
/* 5152 */     this.rotationYaw = yaw;
/* 5153 */     this.prevRotationYaw = yaw;
/* 5154 */     this.lastRiderYaw = yaw;
/* 5155 */     this.lastSearchLightYaw = yaw;
/*      */     
/* 5157 */     for (MCH_WeaponSet w : this.weapons) {
/* 5158 */       w.rotationYaw = w.defaultRotationYaw;
/* 5159 */       w.rotationPitch = 0.0F;
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MCH_AircraftInfo getAcInfo() {
/* 5165 */     return this.acInfo;
/*      */   }
/*      */   
/*      */   public void setAcInfo(@Nullable MCH_AircraftInfo info) {
/* 5169 */     this.acInfo = info;
/* 5170 */     if (info != null) {
/* 5171 */       this.partHatch = createHatch();
/* 5172 */       this.partCanopy = createCanopy();
/* 5173 */       this.partLandingGear = createLandingGear();
/* 5174 */       this.weaponBays = createWeaponBays();
/* 5175 */       this.rotPartRotation = new float[info.partRotPart.size()];
/* 5176 */       this.prevRotPartRotation = new float[info.partRotPart.size()];
/* 5177 */       this.extraBoundingBox = createExtraBoundingBox();
/* 5178 */       this.partEntities = createParts();
/* 5179 */       this.stepHeight = info.stepHeight;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MCH_BoundingBox[] createExtraBoundingBox() {
/* 5187 */     MCH_BoundingBox[] ar = new MCH_BoundingBox[(getAcInfo()).extraBoundingBox.size()];
/* 5188 */     int i = 0;
/*      */     
/* 5190 */     for (MCH_BoundingBox bb : (getAcInfo()).extraBoundingBox) {
/* 5191 */       ar[i] = bb.copy();
/* 5192 */       i++;
/*      */     } 
/*      */     
/* 5195 */     return ar;
/*      */   }
/*      */   
/*      */   public Entity[] createParts() {
/* 5199 */     return new Entity[] { this.partEntities[0] };
/*      */   }
/*      */   
/*      */   public void updateUAV() {
/* 5203 */     if (isUAV()) {
/* 5204 */       if (this.world.isRemote) {
/* 5205 */         int eid = ((Integer)this.dataManager.get(UAV_STATION)).intValue();
/* 5206 */         if (eid > 0) {
/* 5207 */           if (this.uavStation == null) {
/* 5208 */             Entity uavEntity = this.world.getEntityByID(eid);
/* 5209 */             if (uavEntity instanceof MCH_EntityUavStation) {
/* 5210 */               this.uavStation = (MCH_EntityUavStation)uavEntity;
/* 5211 */               this.uavStation.setControlAircract(this);
/*      */             } 
/*      */           } 
/* 5214 */         } else if (this.uavStation != null) {
/* 5215 */           this.uavStation.setControlAircract(null);
/* 5216 */           this.uavStation = null;
/*      */         } 
/* 5218 */       } else if (this.uavStation != null) {
/* 5219 */         double udx = this.posX - this.uavStation.posX;
/* 5220 */         double udz = this.posZ - this.uavStation.posZ;
/* 5221 */         if (udx * udx + udz * udz > 15129.0D) {
/* 5222 */           this.uavStation.setControlAircract(null);
/* 5223 */           setUavStation((MCH_EntityUavStation)null);
/* 5224 */           attackEntityFrom(DamageSource.OUT_OF_WORLD, getMaxHP() + 10);
/*      */         } 
/*      */       } 
/*      */       
/* 5228 */       if (this.uavStation != null && this.uavStation.isDead) {
/* 5229 */         this.uavStation = null;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void switchGunnerMode(boolean mode) {
/* 5235 */     boolean debug_bk_mode = this.isGunnerMode;
/* 5236 */     Entity pilot = getEntityBySeatId(0);
/* 5237 */     if (!mode || canSwitchGunnerMode()) {
/* 5238 */       if (this.isGunnerMode && !mode) {
/* 5239 */         setCurrentThrottle(this.beforeHoverThrottle);
/* 5240 */         this.isGunnerMode = false;
/* 5241 */         this.camera.setCameraZoom(1.0F);
/* 5242 */         getCurrentWeapon(pilot).onSwitchWeapon(this.world.isRemote, isInfinityAmmo(pilot));
/* 5243 */       } else if (!this.isGunnerMode && mode) {
/* 5244 */         this.beforeHoverThrottle = getCurrentThrottle();
/* 5245 */         this.isGunnerMode = true;
/* 5246 */         this.camera.setCameraZoom(1.0F);
/* 5247 */         getCurrentWeapon(pilot).onSwitchWeapon(this.world.isRemote, isInfinityAmmo(pilot));
/*      */       } 
/*      */     }
/*      */     
/* 5251 */     MCH_Lib.DbgLog(this.world, "switchGunnerMode %s->%s", new Object[] { debug_bk_mode ? "ON" : "OFF", mode ? "ON" : "OFF" });
/*      */   }
/*      */   
/*      */   public boolean canSwitchGunnerMode() {
/* 5255 */     if (getAcInfo() == null || !(getAcInfo()).isEnableGunnerMode)
/* 5256 */       return false; 
/* 5257 */     if (!isCanopyClose()) {
/* 5258 */       return false;
/*      */     }
/* 5260 */     return (((getAcInfo()).isEnableConcurrentGunnerMode || !(getEntityBySeatId(1) instanceof EntityPlayer)) && !isHoveringMode());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSwitchGunnerModeOtherSeat(EntityPlayer player) {
/* 5265 */     int sid = getSeatIdByEntity((Entity)player);
/* 5266 */     if (sid > 0) {
/* 5267 */       MCH_SeatInfo info = getSeatInfo(sid);
/* 5268 */       if (info != null) {
/* 5269 */         return (info.gunner && info.switchgunner);
/*      */       }
/*      */     } 
/*      */     
/* 5273 */     return false;
/*      */   }
/*      */   
/*      */   public void switchGunnerModeOtherSeat(EntityPlayer player) {
/* 5277 */     this.isGunnerModeOtherSeat = !this.isGunnerModeOtherSeat;
/*      */   }
/*      */   
/*      */   public boolean isHoveringMode() {
/* 5281 */     return this.isHoveringMode;
/*      */   }
/*      */   
/*      */   public void switchHoveringMode(boolean mode) {
/* 5285 */     stopRepelling();
/* 5286 */     if (canSwitchHoveringMode() && isHoveringMode() != mode) {
/* 5287 */       if (mode) {
/* 5288 */         this.beforeHoverThrottle = getCurrentThrottle();
/*      */       } else {
/* 5290 */         setCurrentThrottle(this.beforeHoverThrottle);
/*      */       } 
/*      */       
/* 5293 */       this.isHoveringMode = mode;
/* 5294 */       Entity riddenByEntity = getRiddenByEntity();
/* 5295 */       if (riddenByEntity != null) {
/* 5296 */         riddenByEntity.rotationPitch = 0.0F;
/* 5297 */         riddenByEntity.prevRotationPitch = 0.0F;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean canSwitchHoveringMode() {
/* 5303 */     return (getAcInfo() != null && !this.isGunnerMode);
/*      */   }
/*      */   
/*      */   public boolean isHovering() {
/* 5307 */     return (this.isGunnerMode || isHoveringMode());
/*      */   }
/*      */   
/*      */   public boolean getIsGunnerMode(Entity entity) {
/* 5311 */     if (getAcInfo() == null) {
/* 5312 */       return false;
/*      */     }
/* 5314 */     int id = getSeatIdByEntity(entity);
/* 5315 */     if (id < 0)
/* 5316 */       return false; 
/* 5317 */     if (id == 0 && (getAcInfo()).isEnableGunnerMode) {
/* 5318 */       return this.isGunnerMode;
/*      */     }
/* 5320 */     MCH_SeatInfo[] st = getSeatsInfo();
/* 5321 */     if (id >= st.length || !(st[id]).gunner) {
/* 5322 */       return false;
/*      */     }
/* 5324 */     return (!this.world.isRemote || !(st[id]).switchgunner || this.isGunnerModeOtherSeat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPilot(Entity player) {
/* 5331 */     return W_Entity.isEqual(getRiddenByEntity(), player);
/*      */   }
/*      */   
/*      */   public boolean canSwitchFreeLook() {
/* 5335 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isFreeLookMode() {
/* 5339 */     return (getCommonStatus(1) || isRepelling());
/*      */   }
/*      */   
/*      */   public void switchFreeLookMode(boolean b) {
/* 5343 */     setCommonStatus(1, b);
/*      */   }
/*      */   
/*      */   public void switchFreeLookModeClient(boolean b) {
/* 5347 */     setCommonStatus(1, b, true);
/*      */   }
/*      */   
/*      */   public boolean canSwitchGunnerFreeLook(EntityPlayer player) {
/* 5351 */     MCH_SeatInfo seatInfo = getSeatInfo((Entity)player);
/* 5352 */     return (seatInfo != null && seatInfo.fixRot && getIsGunnerMode((Entity)player));
/*      */   }
/*      */   
/*      */   public boolean isGunnerLookMode(EntityPlayer player) {
/* 5356 */     return (!isPilot((Entity)player) && this.isGunnerFreeLookMode);
/*      */   }
/*      */   
/*      */   public void switchGunnerFreeLookMode(boolean b) {
/* 5360 */     this.isGunnerFreeLookMode = b;
/*      */   }
/*      */   
/*      */   public void switchGunnerFreeLookMode() {
/* 5364 */     switchGunnerFreeLookMode(!this.isGunnerFreeLookMode);
/*      */   }
/*      */   
/*      */   public void updateParts(int stat) {
/* 5368 */     if (!isDestroyed()) {
/* 5369 */       MCH_Parts[] parts = { this.partHatch, this.partCanopy, this.partLandingGear };
/*      */       
/* 5371 */       for (MCH_Parts p : parts) {
/* 5372 */         if (p != null) {
/* 5373 */           p.updateStatusClient(stat);
/* 5374 */           p.update();
/*      */         } 
/*      */       } 
/*      */       
/* 5378 */       if (!isDestroyed() && !this.world.isRemote && this.partLandingGear != null)
/*      */       {
/* 5380 */         if (!isLandingGearFolded() && this.partLandingGear.getFactor() <= 0.1F) {
/* 5381 */           int blockId = MCH_Lib.getBlockIdY((Entity)this, 3, -20);
/* 5382 */           if ((getCurrentThrottle() <= 0.800000011920929D || this.onGround || blockId != 0) && 
/* 5383 */             (getAcInfo()).isFloat && (
/* 5384 */             isInWater() || MCH_Lib.getBlockY((Entity)this, 3, -20, true) == W_Block.getWater())) {
/* 5385 */             this.partLandingGear.setStatusServer(true);
/*      */           }
/* 5387 */         } else if (isLandingGearFolded() && this.partLandingGear.getFactor() >= 0.9F) {
/* 5388 */           int blockId = MCH_Lib.getBlockIdY((Entity)this, 3, -10);
/* 5389 */           if (getCurrentThrottle() < getUnfoldLandingGearThrottle() && blockId != 0) {
/* 5390 */             boolean unfold = true;
/* 5391 */             if ((getAcInfo()).isFloat) {
/* 5392 */               blockId = MCH_Lib.getBlockIdY(this.world, this.posX, this.posY + 1.0D + (getAcInfo()).floatOffset, this.posZ, 1, 65386, true);
/* 5393 */               if (W_Block.isEqual(blockId, W_Block.getWater())) {
/* 5394 */                 unfold = false;
/*      */               }
/*      */             } 
/*      */             
/* 5398 */             if (unfold) {
/* 5399 */               this.partLandingGear.setStatusServer(false);
/*      */             }
/* 5401 */           } else if (getVtolMode() == 2 && blockId != 0) {
/* 5402 */             this.partLandingGear.setStatusServer(false);
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getUnfoldLandingGearThrottle() {
/* 5410 */     return 0.8F;
/*      */   }
/*      */   
/*      */   private int getPartStatus() {
/* 5414 */     return ((Integer)this.dataManager.get(PART_STAT)).intValue();
/*      */   }
/*      */   
/*      */   private void setPartStatus(int n) {
/* 5418 */     this.dataManager.set(PART_STAT, Integer.valueOf(n));
/*      */   }
/*      */   
/*      */   protected void initPartRotation(float yaw, float pitch) {
/* 5422 */     this.lastRiderYaw = yaw;
/* 5423 */     this.prevLastRiderYaw = yaw;
/* 5424 */     this.camera.partRotationYaw = yaw;
/* 5425 */     this.camera.prevPartRotationYaw = yaw;
/* 5426 */     this.lastSearchLightYaw = yaw;
/*      */   }
/*      */   
/*      */   public int getLastPartStatusMask() {
/* 5430 */     return 24;
/*      */   }
/*      */   
/*      */   public int getModeSwitchCooldown() {
/* 5434 */     return this.modeSwitchCooldown;
/*      */   }
/*      */   
/*      */   public void setModeSwitchCooldown(int n) {
/* 5438 */     this.modeSwitchCooldown = n;
/*      */   }
/*      */   
/*      */   protected WeaponBay[] createWeaponBays() {
/* 5442 */     WeaponBay[] wbs = new WeaponBay[(getAcInfo()).partWeaponBay.size()];
/*      */     
/* 5444 */     for (int i = 0; i < wbs.length; i++) {
/* 5445 */       wbs[i] = new WeaponBay(this);
/*      */     }
/*      */     
/* 5448 */     return wbs;
/*      */   }
/*      */   
/*      */   protected MCH_Parts createHatch() {
/* 5452 */     MCH_Parts hatch = null;
/* 5453 */     if (getAcInfo().haveHatch()) {
/* 5454 */       hatch = new MCH_Parts((Entity)this, 4, PART_STAT, "Hatch");
/* 5455 */       hatch.rotationMax = 90.0F;
/* 5456 */       hatch.rotationInv = 1.5F;
/* 5457 */       hatch.soundEndSwichOn.setPrm("plane_cc", 1.0F, 1.0F);
/* 5458 */       hatch.soundEndSwichOff.setPrm("plane_cc", 1.0F, 1.0F);
/* 5459 */       hatch.soundSwitching.setPrm("plane_cv", 1.0F, 0.5F);
/*      */     } 
/*      */     
/* 5462 */     return hatch;
/*      */   }
/*      */   
/*      */   public boolean haveHatch() {
/* 5466 */     return (this.partHatch != null);
/*      */   }
/*      */   
/*      */   public boolean canFoldHatch() {
/* 5470 */     return (this.partHatch != null && this.modeSwitchCooldown <= 0 && this.partHatch.isOFF());
/*      */   }
/*      */   
/*      */   public boolean canUnfoldHatch() {
/* 5474 */     return (this.partHatch != null && this.modeSwitchCooldown <= 0 && this.partHatch.isON());
/*      */   }
/*      */   
/*      */   public void foldHatch(boolean fold) {
/* 5478 */     foldHatch(fold, false);
/*      */   }
/*      */   
/*      */   public void foldHatch(boolean fold, boolean force) {
/* 5482 */     if (this.partHatch != null && (
/* 5483 */       force || this.modeSwitchCooldown <= 0)) {
/* 5484 */       this.partHatch.setStatusServer(fold);
/* 5485 */       this.modeSwitchCooldown = 20;
/* 5486 */       if (!fold) {
/* 5487 */         stopUnmountCrew();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getHatchRotation() {
/* 5494 */     return (this.partHatch != null) ? this.partHatch.rotation : 0.0F;
/*      */   }
/*      */   
/*      */   public float getPrevHatchRotation() {
/* 5498 */     return (this.partHatch != null) ? this.partHatch.prevRotation : 0.0F;
/*      */   }
/*      */   
/*      */   public void foldLandingGear() {
/* 5502 */     if (this.partLandingGear != null && getModeSwitchCooldown() <= 0) {
/* 5503 */       this.partLandingGear.setStatusServer(true);
/* 5504 */       setModeSwitchCooldown(20);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void unfoldLandingGear() {
/* 5509 */     if (this.partLandingGear != null && getModeSwitchCooldown() <= 0 && 
/* 5510 */       isLandingGearFolded()) {
/* 5511 */       this.partLandingGear.setStatusServer(false);
/* 5512 */       setModeSwitchCooldown(20);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canFoldLandingGear() {
/* 5518 */     if (getLandingGearRotation() >= 1.0F) {
/* 5519 */       return false;
/*      */     }
/* 5521 */     Block block = MCH_Lib.getBlockY((Entity)this, 3, -10, true);
/* 5522 */     return (!isLandingGearFolded() && block == W_Blocks.AIR);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canUnfoldLandingGear() {
/* 5527 */     return (getLandingGearRotation() >= 89.0F && isLandingGearFolded());
/*      */   }
/*      */   
/*      */   public boolean isLandingGearFolded() {
/* 5531 */     return (this.partLandingGear != null && this.partLandingGear.getStatus());
/*      */   }
/*      */   
/*      */   protected MCH_Parts createLandingGear() {
/* 5535 */     MCH_Parts lg = null;
/* 5536 */     if (getAcInfo().haveLandingGear()) {
/* 5537 */       lg = new MCH_Parts((Entity)this, 2, PART_STAT, "LandingGear");
/* 5538 */       lg.rotationMax = 90.0F;
/* 5539 */       lg.rotationInv = 2.5F;
/* 5540 */       lg.soundStartSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
/* 5541 */       lg.soundEndSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
/* 5542 */       lg.soundStartSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
/* 5543 */       lg.soundEndSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
/* 5544 */       lg.soundSwitching.setPrm("plane_cv", 1.0F, 0.75F);
/*      */     } 
/*      */     
/* 5547 */     return lg;
/*      */   }
/*      */   
/*      */   public float getLandingGearRotation() {
/* 5551 */     return (this.partLandingGear != null) ? this.partLandingGear.rotation : 0.0F;
/*      */   }
/*      */   
/*      */   public float getPrevLandingGearRotation() {
/* 5555 */     return (this.partLandingGear != null) ? this.partLandingGear.prevRotation : 0.0F;
/*      */   }
/*      */   
/*      */   public int getVtolMode() {
/* 5559 */     return 0;
/*      */   }
/*      */   
/*      */   public void openCanopy() {
/* 5563 */     if (this.partCanopy != null && getModeSwitchCooldown() <= 0) {
/* 5564 */       this.partCanopy.setStatusServer(true);
/* 5565 */       setModeSwitchCooldown(20);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void openCanopy_EjectSeat() {
/* 5570 */     if (this.partCanopy != null) {
/* 5571 */       this.partCanopy.setStatusServer(true, false);
/* 5572 */       setModeSwitchCooldown(40);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void closeCanopy() {
/* 5577 */     if (this.partCanopy != null && getModeSwitchCooldown() <= 0 && 
/* 5578 */       getCanopyStat()) {
/* 5579 */       this.partCanopy.setStatusServer(false);
/* 5580 */       setModeSwitchCooldown(20);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getCanopyStat() {
/* 5586 */     return (this.partCanopy != null && this.partCanopy.getStatus());
/*      */   }
/*      */   
/*      */   public boolean isCanopyClose() {
/* 5590 */     return (this.partCanopy == null || (!getCanopyStat() && getCanopyRotation() <= 0.01F));
/*      */   }
/*      */   
/*      */   public float getCanopyRotation() {
/* 5594 */     return (this.partCanopy != null) ? this.partCanopy.rotation : 0.0F;
/*      */   }
/*      */   
/*      */   public float getPrevCanopyRotation() {
/* 5598 */     return (this.partCanopy != null) ? this.partCanopy.prevRotation : 0.0F;
/*      */   }
/*      */   
/*      */   protected MCH_Parts createCanopy() {
/* 5602 */     MCH_Parts canopy = null;
/* 5603 */     if (getAcInfo().haveCanopy()) {
/* 5604 */       canopy = new MCH_Parts((Entity)this, 0, PART_STAT, "Canopy");
/* 5605 */       canopy.rotationMax = 90.0F;
/* 5606 */       canopy.rotationInv = 3.5F;
/* 5607 */       canopy.soundEndSwichOn.setPrm("plane_cc", 1.0F, 1.0F);
/* 5608 */       canopy.soundEndSwichOff.setPrm("plane_cc", 1.0F, 1.0F);
/*      */     } 
/*      */     
/* 5611 */     return canopy;
/*      */   }
/*      */   
/*      */   public boolean hasBrake() {
/* 5615 */     return false;
/*      */   }
/*      */   
/*      */   public boolean getBrake() {
/* 5619 */     return getCommonStatus(11);
/*      */   }
/*      */   
/*      */   public void setBrake(boolean b) {
/* 5623 */     if (!this.world.isRemote) {
/* 5624 */       setCommonStatus(11, b);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean getGunnerStatus() {
/* 5629 */     return getCommonStatus(12);
/*      */   }
/*      */   
/*      */   public void setGunnerStatus(boolean b) {
/* 5633 */     if (!this.world.isRemote) {
/* 5634 */       setCommonStatus(12, b);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSizeInventory() {
/* 5640 */     return (getAcInfo() != null) ? (getAcInfo()).inventorySize : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getInvName() {
/* 5645 */     if (getAcInfo() == null) {
/* 5646 */       return super.getInvName();
/*      */     }
/* 5648 */     String s = (getAcInfo()).displayName;
/* 5649 */     return (s.length() <= 32) ? s : s.substring(0, 31);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInvNameLocalized() {
/* 5655 */     return (getAcInfo() != null);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MCH_EntityChain getTowChainEntity() {
/* 5660 */     return this.towChainEntity;
/*      */   }
/*      */   
/*      */   public void setTowChainEntity(MCH_EntityChain chainEntity) {
/* 5664 */     this.towChainEntity = chainEntity;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MCH_EntityChain getTowedChainEntity() {
/* 5669 */     return this.towedChainEntity;
/*      */   }
/*      */   
/*      */   public void setTowedChainEntity(MCH_EntityChain towedChainEntity) {
/* 5673 */     this.towedChainEntity = towedChainEntity;
/*      */   }
/*      */   
/*      */   public void setEntityBoundingBox(@NotNull AxisAlignedBB bb) {
/* 5677 */     super.setEntityBoundingBox(new MCH_AircraftBoundingBox(this, bb));
/*      */   }
/*      */ 
/*      */   
/*      */   public double getX() {
/* 5682 */     return this.posX;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getY() {
/* 5687 */     return this.posY;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getZ() {
/* 5692 */     return this.posZ;
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity getEntity() {
/* 5697 */     return (Entity)this;
/*      */   } public abstract void changeType(String paramString);
/*      */   public abstract void onUpdateAngles(float paramFloat);
/*      */   public abstract void onUpdateAircraft();
/*      */   public abstract boolean canMountWithNearEmptyMinecart();
/*      */   public abstract String getDefaultSoundName();
/*      */   @Nullable
/*      */   public abstract Item getItem();
/* 5705 */   public static class UnmountReserve { final Entity entity; final double posX; final double posY; final double posZ; int cnt = 5;
/*      */     
/*      */     public UnmountReserve(MCH_EntityAircraft paramMCH_EntityAircraft, Entity e, double x, double y, double z) {
/* 5708 */       this.entity = e;
/* 5709 */       this.posX = x;
/* 5710 */       this.posY = y;
/* 5711 */       this.posZ = z;
/*      */     } }
/*      */ 
/*      */   
/*      */   public static class WeaponBay {
/* 5716 */     public float rot = 0.0F;
/* 5717 */     public float prevRot = 0.0F;
/*      */     
/*      */     public WeaponBay(MCH_EntityAircraft paramMCH_EntityAircraft) {}
/*      */   }
/*      */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_EntityAircraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */