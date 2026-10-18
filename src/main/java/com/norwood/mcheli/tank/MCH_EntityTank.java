/*      */ package com.norwood.mcheli.tank;
/*      */ 
/*      */ import com.norwood.mcheli.MCH_Config;
/*      */ import com.norwood.mcheli.MCH_Lib;
/*      */ import com.norwood.mcheli.MCH_MOD;
/*      */ import com.norwood.mcheli.MCH_Math;
/*      */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*      */ import com.norwood.mcheli.aircraft.MCH_BoundingBox;
/*      */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*      */ import com.norwood.mcheli.aircraft.MCH_EntityHitBox;
/*      */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*      */ import com.norwood.mcheli.aircraft.MCH_PacketStatusRequest;
/*      */ import com.norwood.mcheli.aircraft.MCH_Parts;
/*      */ import com.norwood.mcheli.particles.MCH_ParticleParam;
/*      */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*      */ import com.norwood.mcheli.weapon.MCH_WeaponSet;
/*      */ import com.norwood.mcheli.wrapper.W_Block;
/*      */ import com.norwood.mcheli.wrapper.W_Blocks;
/*      */ import com.norwood.mcheli.wrapper.W_Entity;
/*      */ import com.norwood.mcheli.wrapper.W_Lib;
/*      */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.MoverType;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraftforge.fml.relauncher.Side;
/*      */ import net.minecraftforge.fml.relauncher.SideOnly;
/*      */ 
/*      */ public class MCH_EntityTank
/*      */   extends MCH_EntityAircraft
/*      */ {
/*      */   public final MCH_WheelManager WheelMng;
/*      */   public float soundVolume;
/*      */   public float soundVolumeTarget;
/*   52 */   private MCH_TankInfo tankInfo = null; public float rotationRotor; public float prevRotationRotor; public float addkeyRotValue;
/*      */   
/*      */   public MCH_EntityTank(World world) {
/*   55 */     super(world);
/*   56 */     this.currentSpeed = 0.07D;
/*   57 */     this.preventEntitySpawning = true;
/*   58 */     setSize(2.0F, 0.7F);
/*   59 */     this.motionX = 0.0D;
/*   60 */     this.motionY = 0.0D;
/*   61 */     this.motionZ = 0.0D;
/*   62 */     this.weapons = createWeapon(0);
/*   63 */     this.soundVolume = 0.0F;
/*   64 */     this.stepHeight = 0.6F;
/*   65 */     this.rotationRotor = 0.0F;
/*   66 */     this.prevRotationRotor = 0.0F;
/*   67 */     this.WheelMng = new MCH_WheelManager(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getKindName() {
/*   72 */     return "tanks";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getEntityType() {
/*   77 */     return "Vehicle";
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MCH_TankInfo getTankInfo() {
/*   82 */     return this.tankInfo;
/*      */   }
/*      */ 
/*      */   
/*      */   public void changeType(String type) {
/*   87 */     MCH_Lib.DbgLog(this.world, "MCH_EntityTank.changeType " + type + " : " + this, new Object[0]);
/*   88 */     if (!type.isEmpty()) {
/*   89 */       this.tankInfo = MCH_TankInfoManager.get(type);
/*      */     }
/*      */     
/*   92 */     if (this.tankInfo == null) {
/*   93 */       MCH_Lib.Log((Entity)this, "##### MCH_EntityTank changeTankType() Tank info null %d, %s, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName() });
/*   94 */       setDead();
/*      */     } else {
/*   96 */       setAcInfo(this.tankInfo);
/*   97 */       newSeats(getAcInfo().getNumSeatAndRack());
/*   98 */       switchFreeLookModeClient((getAcInfo()).defaultFreelook);
/*   99 */       this.weapons = createWeapon(1 + getSeatNum());
/*  100 */       initPartRotation(getRotYaw(), getRotPitch());
/*  101 */       this.WheelMng.createWheels(this.world, (getAcInfo()).wheels, new Vec3d(0.0D, -0.35D, (getTankInfo()).weightedCenterZ));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Item getItem() {
/*  108 */     return (getTankInfo() != null) ? (Item)(getTankInfo()).item : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canMountWithNearEmptyMinecart() {
/*  113 */     return MCH_Config.MountMinecartTank.prmBool;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  118 */     super.entityInit();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getGiveDamageRot() {
/*  123 */     return 91.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
/*  128 */     super.writeEntityToNBT(par1NBTTagCompound);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
/*  133 */     super.readEntityFromNBT(par1NBTTagCompound);
/*  134 */     if (this.tankInfo == null) {
/*  135 */       this.tankInfo = MCH_TankInfoManager.get(getTypeName());
/*  136 */       if (this.tankInfo == null) {
/*  137 */         MCH_Lib.Log((Entity)this, "##### MCH_EntityTank readEntityFromNBT() Tank info null %d, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName() });
/*  138 */         setDead();
/*      */       } else {
/*  140 */         setAcInfo(this.tankInfo);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDead() {
/*  147 */     super.setDead();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onInteractFirst(EntityPlayer player) {
/*  152 */     this.addkeyRotValue = 0.0F;
/*  153 */     player.rotationYawHead = player.prevRotationYawHead = getLastRiderYaw();
/*  154 */     player.prevRotationYaw = player.rotationYaw = getLastRiderYaw();
/*  155 */     player.rotationPitch = getLastRiderPitch();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSwitchGunnerMode() {
/*  160 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdateAircraft() {
/*  165 */     if (this.tankInfo == null) {
/*  166 */       changeType(getTypeName());
/*  167 */       this.prevPosX = this.posX;
/*  168 */       this.prevPosY = this.posY;
/*  169 */       this.prevPosZ = this.posZ;
/*      */     } else {
/*  171 */       if (!this.isRequestedSyncStatus) {
/*  172 */         this.isRequestedSyncStatus = true;
/*  173 */         if (this.world.isRemote) {
/*  174 */           MCH_PacketStatusRequest.requestStatus(this);
/*      */         }
/*      */       } 
/*      */       
/*  178 */       if (this.lastRiddenByEntity == null && getRiddenByEntity() != null) {
/*  179 */         initCurrentWeapon(getRiddenByEntity());
/*      */       }
/*      */       
/*  182 */       updateWeapons();
/*  183 */       onUpdate_Seats();
/*  184 */       onUpdate_Control();
/*  185 */       this.prevRotationRotor = this.rotationRotor;
/*  186 */       this.rotationRotor = (float)(this.rotationRotor + getCurrentThrottle() * (getAcInfo()).rotorSpeed);
/*  187 */       if (this.rotationRotor > 360.0F) {
/*  188 */         this.rotationRotor -= 360.0F;
/*  189 */         this.prevRotationRotor -= 360.0F;
/*      */       } 
/*      */       
/*  192 */       if (this.rotationRotor < 0.0F) {
/*  193 */         this.rotationRotor += 360.0F;
/*  194 */         this.prevRotationRotor += 360.0F;
/*      */       } 
/*      */       
/*  197 */       this.prevPosX = this.posX;
/*  198 */       this.prevPosY = this.posY;
/*  199 */       this.prevPosZ = this.posZ;
/*  200 */       if (isDestroyed() && getCurrentThrottle() > 0.0D) {
/*  201 */         if (MCH_Lib.getBlockIdY((Entity)this, 3, -2) > 0) {
/*  202 */           setCurrentThrottle(getCurrentThrottle() * 0.8D);
/*      */         }
/*      */         
/*  205 */         if (isExploded()) {
/*  206 */           setCurrentThrottle(getCurrentThrottle() * 0.98D);
/*      */         }
/*      */       } 
/*      */       
/*  210 */       updateCameraViewers();
/*  211 */       if (this.world.isRemote) {
/*  212 */         onUpdate_Client();
/*      */       } else {
/*  214 */         onUpdate_Server();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public boolean canRenderOnFire() {
/*  221 */     return (isDestroyed() || super.canRenderOnFire());
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateExtraBoundingBox() {
/*  226 */     if (this.world.isRemote) {
/*  227 */       super.updateExtraBoundingBox();
/*  228 */     } else if (getCountOnUpdate() <= 1) {
/*  229 */       super.updateExtraBoundingBox();
/*  230 */       super.updateExtraBoundingBox();
/*      */     } 
/*      */   }
/*      */   
/*      */   public ClacAxisBB calculateXOffset(List<AxisAlignedBB> list, AxisAlignedBB bb, double x) {
/*  235 */     for (AxisAlignedBB axisAlignedBB : list) {
/*  236 */       x = axisAlignedBB.calculateXOffset(bb, x);
/*      */     }
/*      */     
/*  239 */     return new ClacAxisBB(x, bb.offset(x, 0.0D, 0.0D));
/*      */   }
/*      */   
/*      */   public ClacAxisBB calculateYOffset(List<AxisAlignedBB> list, AxisAlignedBB bb, double y) {
/*  243 */     return calculateYOffset(list, bb, bb, y);
/*      */   }
/*      */   
/*      */   public ClacAxisBB calculateYOffset(List<AxisAlignedBB> list, AxisAlignedBB calcBB, AxisAlignedBB offsetBB, double y) {
/*  247 */     for (AxisAlignedBB axisAlignedBB : list) {
/*  248 */       y = axisAlignedBB.calculateYOffset(calcBB, y);
/*      */     }
/*      */     
/*  251 */     return new ClacAxisBB(y, offsetBB.offset(0.0D, y, 0.0D));
/*      */   }
/*      */   
/*      */   public ClacAxisBB calculateZOffset(List<AxisAlignedBB> list, AxisAlignedBB bb, double z) {
/*  255 */     for (AxisAlignedBB axisAlignedBB : list) {
/*  256 */       z = axisAlignedBB.calculateZOffset(bb, z);
/*      */     }
/*      */     
/*  259 */     return new ClacAxisBB(z, bb.offset(0.0D, 0.0D, z));
/*      */   }
/*      */ 
/*      */   
/*      */   public void move(MoverType type, double x, double y, double z) {
/*  264 */     this.world.profiler.startSection("move");
/*  265 */     double d2 = x;
/*  266 */     double d3 = y;
/*  267 */     double d4 = z;
/*  268 */     List<AxisAlignedBB> list1 = getCollisionBoxes((Entity)this, getEntityBoundingBox().expand(x, y, z));
/*  269 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*  270 */     if (y != 0.0D) {
/*  271 */       ClacAxisBB v = calculateYOffset(list1, getEntityBoundingBox(), y);
/*  272 */       y = v.value;
/*  273 */       setEntityBoundingBox(v.bb);
/*      */     } 
/*      */     
/*  276 */     boolean flag = (this.onGround || (d3 != y && d3 < 0.0D));
/*      */     
/*  278 */     for (MCH_BoundingBox ebb : this.extraBoundingBox) {
/*  279 */       ebb.updatePosition(this.posX, this.posY, this.posZ, getRotYaw(), getRotPitch(), getRotRoll());
/*      */     }
/*      */     
/*  282 */     if (x != 0.0D) {
/*  283 */       ClacAxisBB v = calculateXOffset(list1, getEntityBoundingBox(), x);
/*  284 */       x = v.value;
/*  285 */       if (x != 0.0D) {
/*  286 */         setEntityBoundingBox(v.bb);
/*      */       }
/*      */     } 
/*      */     
/*  290 */     if (z != 0.0D) {
/*  291 */       ClacAxisBB v = calculateZOffset(list1, getEntityBoundingBox(), z);
/*  292 */       z = v.value;
/*  293 */       if (z != 0.0D) {
/*  294 */         setEntityBoundingBox(v.bb);
/*      */       }
/*      */     } 
/*      */     
/*  298 */     if (this.stepHeight > 0.0F && flag && (d2 != x || d4 != z)) {
/*  299 */       double d14 = x;
/*  300 */       double d6 = y;
/*  301 */       double d7 = z;
/*  302 */       AxisAlignedBB axisalignedbb1 = getEntityBoundingBox();
/*  303 */       setEntityBoundingBox(axisalignedbb);
/*  304 */       y = this.stepHeight;
/*  305 */       List<AxisAlignedBB> list = getCollisionBoxes((Entity)this, getEntityBoundingBox().expand(d2, y, d4));
/*  306 */       AxisAlignedBB axisalignedbb2 = getEntityBoundingBox();
/*  307 */       AxisAlignedBB axisalignedbb3 = axisalignedbb2.expand(d2, 0.0D, d4);
/*  308 */       ClacAxisBB v = calculateYOffset(list, axisalignedbb3, axisalignedbb2, y);
/*  309 */       double d8 = v.value;
/*  310 */       axisalignedbb2 = v.bb;
/*  311 */       v = calculateXOffset(list, axisalignedbb2, d2);
/*  312 */       double d18 = v.value;
/*  313 */       axisalignedbb2 = v.bb;
/*  314 */       v = calculateZOffset(list, axisalignedbb2, d4);
/*  315 */       double d19 = v.value;
/*  316 */       axisalignedbb2 = v.bb;
/*  317 */       AxisAlignedBB axisalignedbb4 = getEntityBoundingBox();
/*  318 */       v = calculateYOffset(list, axisalignedbb4, y);
/*  319 */       double d20 = v.value;
/*  320 */       axisalignedbb4 = v.bb;
/*  321 */       v = calculateXOffset(list, axisalignedbb4, d2);
/*  322 */       double d21 = v.value;
/*  323 */       axisalignedbb4 = v.bb;
/*  324 */       v = calculateZOffset(list, axisalignedbb4, d4);
/*  325 */       double d22 = v.value;
/*  326 */       axisalignedbb4 = v.bb;
/*  327 */       double d23 = d18 * d18 + d19 * d19;
/*  328 */       double d9 = d21 * d21 + d22 * d22;
/*  329 */       if (d23 > d9) {
/*  330 */         x = d18;
/*  331 */         z = d19;
/*  332 */         y = -d8;
/*  333 */         setEntityBoundingBox(axisalignedbb2);
/*      */       } else {
/*  335 */         x = d21;
/*  336 */         z = d22;
/*  337 */         y = -d20;
/*  338 */         setEntityBoundingBox(axisalignedbb4);
/*      */       } 
/*      */       
/*  341 */       v = calculateYOffset(list, getEntityBoundingBox(), y);
/*  342 */       y = v.value;
/*  343 */       setEntityBoundingBox(v.bb);
/*  344 */       if (d14 * d14 + d7 * d7 >= x * x + z * z) {
/*  345 */         x = d14;
/*  346 */         y = d6;
/*  347 */         z = d7;
/*  348 */         setEntityBoundingBox(axisalignedbb1);
/*      */       } 
/*      */     } 
/*      */     
/*  352 */     this.world.profiler.endSection();
/*  353 */     this.world.profiler.startSection("rest");
/*  354 */     resetPositionToBB();
/*  355 */     this.collidedHorizontally = (d2 != x || d4 != z);
/*  356 */     this.collidedVertically = (d3 != y);
/*  357 */     this.onGround = (this.collidedVertically && d3 < 0.0D);
/*  358 */     this.collided = (this.collidedHorizontally || this.collidedVertically);
/*  359 */     int j6 = MathHelper.floor(this.posX);
/*  360 */     int i1 = MathHelper.floor(this.posY - 0.20000000298023224D);
/*  361 */     int k6 = MathHelper.floor(this.posZ);
/*  362 */     BlockPos blockpos = new BlockPos(j6, i1, k6);
/*  363 */     IBlockState iblockstate = this.world.getBlockState(blockpos);
/*  364 */     if (iblockstate.getMaterial() == Material.AIR) {
/*  365 */       BlockPos blockpos1 = blockpos.down();
/*  366 */       IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
/*  367 */       Block block1 = iblockstate1.getBlock();
/*  368 */       if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/*  369 */         iblockstate = iblockstate1;
/*  370 */         blockpos = blockpos1;
/*      */       } 
/*      */     } 
/*      */     
/*  374 */     updateFallState(y, this.onGround, iblockstate, blockpos);
/*  375 */     if (d2 != x) {
/*  376 */       this.motionX = 0.0D;
/*      */     }
/*      */     
/*  379 */     if (d4 != z) {
/*  380 */       this.motionZ = 0.0D;
/*      */     }
/*      */     
/*  383 */     Block block = iblockstate.getBlock();
/*  384 */     if (d3 != y) {
/*  385 */       block.onLanded(this.world, (Entity)this);
/*      */     }
/*      */     
/*      */     try {
/*  389 */       doBlockCollisions();
/*  390 */     } catch (Throwable var45) {
/*  391 */       CrashReport crashreport = CrashReport.makeCrashReport(var45, "Checking entity block collision");
/*  392 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
/*  393 */       addEntityCrashInfo(crashreportcategory);
/*  394 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */     
/*  397 */     this.world.profiler.endSection();
/*      */   }
/*      */   
/*      */   private void rotationByKey(float partialTicks) {
/*  401 */     float rot = 0.2F;
/*  402 */     if (this.moveLeft && !this.moveRight) {
/*  403 */       this.addkeyRotValue -= rot * partialTicks;
/*      */     }
/*      */     
/*  406 */     if (this.moveRight && !this.moveLeft) {
/*  407 */       this.addkeyRotValue += rot * partialTicks;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdateAngles(float partialTicks) {
/*  413 */     if (!isDestroyed()) {
/*  414 */       if (this.isGunnerMode) {
/*  415 */         setRotPitch(getRotPitch() * 0.95F);
/*  416 */         setRotYaw(getRotYaw() + (getAcInfo()).autoPilotRot * 0.2F);
/*  417 */         if (MathHelper.abs(getRotRoll()) > 20.0F) {
/*  418 */           setRotRoll(getRotRoll() * 0.95F);
/*      */         }
/*      */       } 
/*      */       
/*  422 */       updateRecoil(partialTicks);
/*  423 */       setRotPitch(getRotPitch() + (this.WheelMng.targetPitch - getRotPitch()) * partialTicks);
/*  424 */       setRotRoll(getRotRoll() + (this.WheelMng.targetRoll - getRotRoll()) * partialTicks);
/*  425 */       boolean isFly = (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0);
/*  426 */       if (!isFly || ((getAcInfo()).isFloat && getWaterDepth() > 0.0D)) {
/*  427 */         float gmy = 1.0F;
/*  428 */         if (!isFly) {
/*  429 */           gmy = (getAcInfo()).mobilityYawOnGround;
/*  430 */           if (!(getAcInfo()).canRotOnGround) {
/*  431 */             Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
/*  432 */             if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.AIR)) {
/*  433 */               gmy = 0.0F;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  438 */         float pivotTurnThrottle = (getAcInfo()).pivotTurnThrottle;
/*  439 */         double dx = this.posX - this.prevPosX;
/*  440 */         double dz = this.posZ - this.prevPosZ;
/*  441 */         double dist = dx * dx + dz * dz;
/*  442 */         if (pivotTurnThrottle <= 0.0F || 
/*  443 */           getCurrentThrottle() >= pivotTurnThrottle || this.throttleBack >= pivotTurnThrottle / 10.0F || dist > this.throttleBack * 0.01D) {
/*      */ 
/*      */           
/*  446 */           float sf = (float)Math.sqrt(Math.min(dist, 1.0D));
/*  447 */           if (pivotTurnThrottle <= 0.0F) {
/*  448 */             sf = 1.0F;
/*      */           }
/*      */           
/*  451 */           float flag = (!this.throttleUp && this.throttleDown && getCurrentThrottle() < pivotTurnThrottle + 0.05D) ? -1.0F : 1.0F;
/*  452 */           if (this.moveLeft && !this.moveRight) {
/*  453 */             setRotYaw(getRotYaw() - 0.6F * gmy * partialTicks * flag * sf);
/*      */           }
/*      */           
/*  456 */           if (this.moveRight && !this.moveLeft) {
/*  457 */             setRotYaw(getRotYaw() + 0.6F * gmy * partialTicks * flag * sf);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  462 */       this.addkeyRotValue = (float)(this.addkeyRotValue * (1.0D - (0.1F * partialTicks)));
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onUpdate_Control() {
/*  467 */     if (this.isGunnerMode && !canUseFuel()) {
/*  468 */       switchGunnerMode(false);
/*      */     }
/*      */     
/*  471 */     this.throttleBack = (float)(this.throttleBack * 0.8D);
/*  472 */     if (getBrake()) {
/*  473 */       this.throttleBack = (float)(this.throttleBack * 0.5D);
/*  474 */       if (getCurrentThrottle() > 0.0D) {
/*  475 */         addCurrentThrottle(-0.02D * (getAcInfo()).throttleUpDown);
/*      */       } else {
/*  477 */         setCurrentThrottle(0.0D);
/*      */       } 
/*      */     } 
/*      */     
/*  481 */     if (getRiddenByEntity() != null && !(getRiddenByEntity()).isDead && isCanopyClose() && canUseFuel() && !isDestroyed()) {
/*  482 */       onUpdate_ControlSub();
/*  483 */     } else if (isTargetDrone() && canUseFuel() && !isDestroyed()) {
/*  484 */       this.throttleUp = true;
/*  485 */       onUpdate_ControlSub();
/*  486 */     } else if (getCurrentThrottle() > 0.0D) {
/*  487 */       addCurrentThrottle(-0.0025D * (getAcInfo()).throttleUpDown);
/*      */     } else {
/*  489 */       setCurrentThrottle(0.0D);
/*      */     } 
/*      */     
/*  492 */     if (getCurrentThrottle() < 0.0D) {
/*  493 */       setCurrentThrottle(0.0D);
/*      */     }
/*      */     
/*  496 */     if (this.world.isRemote) {
/*  497 */       if (!W_Lib.isClientPlayer(getRiddenByEntity()) || getCountOnUpdate() % 200 == 0) {
/*  498 */         double ct = getThrottle();
/*  499 */         if (getCurrentThrottle() > ct) {
/*  500 */           addCurrentThrottle(-0.005D);
/*      */         }
/*      */         
/*  503 */         if (getCurrentThrottle() < ct) {
/*  504 */           addCurrentThrottle(0.005D);
/*      */         }
/*      */       } 
/*      */     } else {
/*  508 */       setThrottle(getCurrentThrottle());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onUpdate_ControlSub() {
/*  513 */     if (!this.isGunnerMode) {
/*  514 */       float throttleUpDown = (getAcInfo()).throttleUpDown;
/*  515 */       if (this.throttleUp) {
/*  516 */         float f = throttleUpDown;
/*  517 */         if (getRidingEntity() != null) {
/*  518 */           double mx = (getRidingEntity()).motionX;
/*  519 */           double mz = (getRidingEntity()).motionZ;
/*  520 */           f = throttleUpDown * MathHelper.sqrt(mx * mx + mz * mz) * (getAcInfo()).throttleUpDownOnEntity;
/*      */         } 
/*      */         
/*  523 */         if ((getAcInfo()).enableBack && this.throttleBack > 0.0F) {
/*  524 */           this.throttleBack = (float)(this.throttleBack - 0.01D * f);
/*      */         } else {
/*  526 */           this.throttleBack = 0.0F;
/*  527 */           if (getCurrentThrottle() < 1.0D) {
/*  528 */             addCurrentThrottle(0.01D * f);
/*      */           } else {
/*  530 */             setCurrentThrottle(1.0D);
/*      */           } 
/*      */         } 
/*  533 */       } else if (this.throttleDown) {
/*  534 */         if (getCurrentThrottle() > 0.0D) {
/*  535 */           addCurrentThrottle(-0.01D * throttleUpDown);
/*      */         } else {
/*  537 */           setCurrentThrottle(0.0D);
/*  538 */           if ((getAcInfo()).enableBack) {
/*  539 */             this.throttleBack = (float)(this.throttleBack + 0.0025D * throttleUpDown);
/*  540 */             if (this.throttleBack > 0.6F) {
/*  541 */               this.throttleBack = 0.6F;
/*      */             }
/*      */           } 
/*      */         } 
/*  545 */       } else if (this.cs_tankAutoThrottleDown && getCurrentThrottle() > 0.0D) {
/*  546 */         addCurrentThrottle(-0.005D * throttleUpDown);
/*  547 */         if (getCurrentThrottle() <= 0.0D) {
/*  548 */           setCurrentThrottle(0.0D);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onUpdate_Particle2() {
/*  555 */     if (this.world.isRemote && 
/*  556 */       getHP() < getMaxHP() * 0.5D && 
/*  557 */       getTankInfo() != null) {
/*  558 */       int bbNum = (getTankInfo()).extraBoundingBox.size();
/*      */       
/*  560 */       if (this.isFirstDamageSmoke || this.prevDamageSmokePos.length != bbNum + 1) {
/*  561 */         this.prevDamageSmokePos = new Vec3d[bbNum + 1];
/*      */       }
/*      */       
/*  564 */       float yaw = getRotYaw();
/*  565 */       float pitch = getRotPitch();
/*  566 */       float roll = getRotRoll();
/*      */       
/*  568 */       for (int ri = 0; ri < bbNum; ri++) {
/*  569 */         if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
/*  570 */           int d = (int)((getHP() / getMaxHP() - 0.2D) / 0.3D * 15.0D);
/*  571 */           if (d <= 0 || this.rand.nextInt(d) > 0);
/*      */         } else {
/*      */           
/*  574 */           MCH_BoundingBox bb = (getTankInfo()).extraBoundingBox.get(ri);
/*  575 */           Vec3d pos = getTransformedPosition(bb.offsetX, bb.offsetY, bb.offsetZ);
/*  576 */           double x = pos.x;
/*  577 */           double y = pos.y;
/*  578 */           double z = pos.z;
/*  579 */           onUpdate_Particle2SpawnSmoke(ri, x, y, z, 1.0F);
/*      */         } 
/*      */       } 
/*      */       
/*  583 */       boolean b = true;
/*  584 */       if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
/*  585 */         int d = (int)((getHP() / getMaxHP() - 0.2D) / 0.3D * 15.0D);
/*  586 */         if (d > 0 && this.rand.nextInt(d) > 0) {
/*  587 */           b = false;
/*      */         }
/*      */       } 
/*      */       
/*  591 */       if (b) {
/*  592 */         double px = this.posX;
/*  593 */         double py = this.posY;
/*  594 */         double pz = this.posZ;
/*  595 */         if (getSeatInfo(0) != null && (getSeatInfo(0)).pos != null) {
/*  596 */           Vec3d pos = MCH_Lib.RotVec3(0.0D, (getSeatInfo(0)).pos.y, -2.0D, -yaw, -pitch, -roll);
/*  597 */           px += pos.x;
/*  598 */           py += pos.y;
/*  599 */           pz += pos.z;
/*      */         } 
/*      */         
/*  602 */         onUpdate_Particle2SpawnSmoke(bbNum, px, py, pz, (bbNum == 0) ? 2.0F : 1.0F);
/*      */       } 
/*      */       
/*  605 */       this.isFirstDamageSmoke = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate_Particle2SpawnSmoke(int ri, double x, double y, double z, float size) {
/*  612 */     if (this.isFirstDamageSmoke || this.prevDamageSmokePos[ri] == null) {
/*  613 */       this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */     }
/*      */     
/*  616 */     int num = 1;
/*      */     
/*  618 */     for (int i = 0; i < num; i++) {
/*  619 */       float c = 0.2F + this.rand.nextFloat() * 0.3F;
/*  620 */       MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", x, y, z);
/*  621 */       prm.motionX = size * (this.rand.nextDouble() - 0.5D) * 0.3D;
/*  622 */       prm.motionY = size * this.rand.nextDouble() * 0.1D;
/*  623 */       prm.motionZ = size * (this.rand.nextDouble() - 0.5D) * 0.3D;
/*  624 */       prm.size = size * (this.rand.nextInt(5) + 5.0F) * 1.0F;
/*  625 */       prm.setColor(0.7F + this.rand.nextFloat() * 0.1F, c, c, c);
/*  626 */       MCH_ParticlesUtil.spawnParticle(prm);
/*      */     } 
/*      */     
/*  629 */     this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */   }
/*      */   
/*      */   public void onUpdate_Particle2SpawnSmode(int ri, double x, double y, double z, float size) {
/*  633 */     if (this.isFirstDamageSmoke) {
/*  634 */       this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */     }
/*      */     
/*  637 */     Vec3d prev = this.prevDamageSmokePos[ri];
/*  638 */     double dx = x - prev.x;
/*  639 */     double dy = y - prev.y;
/*  640 */     double dz = z - prev.z;
/*  641 */     int num = (int)(MathHelper.sqrt(dx * dx + dy * dy + dz * dz) / 0.3D) + 1;
/*      */     
/*  643 */     for (int i = 0; i < num; i++) {
/*  644 */       float c = 0.2F + this.rand.nextFloat() * 0.3F;
/*  645 */       MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", x, y, z);
/*  646 */       prm.motionX = size * (this.rand.nextDouble() - 0.5D) * 0.3D;
/*  647 */       prm.motionY = size * this.rand.nextDouble() * 0.1D;
/*  648 */       prm.motionZ = size * (this.rand.nextDouble() - 0.5D) * 0.3D;
/*  649 */       prm.size = size * (this.rand.nextInt(5) + 5.0F) * 1.0F;
/*  650 */       prm.setColor(0.7F + this.rand.nextFloat() * 0.1F, c, c, c);
/*  651 */       MCH_ParticlesUtil.spawnParticle(prm);
/*      */     } 
/*      */     
/*  654 */     this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */   }
/*      */   
/*      */   public void onUpdate_ParticleLandingGear() {
/*  658 */     this.WheelMng.particleLandingGear();
/*      */   }
/*      */   
/*      */   private void onUpdate_ParticleSplash() {
/*  662 */     if (getAcInfo() != null && 
/*  663 */       this.world.isRemote) {
/*  664 */       double mx = this.posX - this.prevPosX;
/*  665 */       double mz = this.posZ - this.prevPosZ;
/*  666 */       double dist = mx * mx + mz * mz;
/*  667 */       if (dist > 1.0D) {
/*  668 */         dist = 1.0D;
/*      */       }
/*      */       
/*  671 */       for (MCH_AircraftInfo.ParticleSplash p : (getAcInfo()).particleSplashs) {
/*  672 */         for (int i = 0; i < p.num; i++) {
/*  673 */           if (dist > 0.03D + this.rand.nextFloat() * 0.1D) {
/*  674 */             setParticleSplash(p.pos, -mx * p.acceleration, p.motionY, -mz * p.acceleration, p.gravity, p.size * (0.5D + dist * 0.5D), p.age);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setParticleSplash(Vec3d pos, double mx, double my, double mz, float gravity, double size, int age) {
/*  683 */     Vec3d v = getTransformedPosition(pos);
/*  684 */     v = v.addVector(this.rand.nextDouble() - 0.5D, (this.rand.nextDouble() - 0.5D) * 0.5D, this.rand.nextDouble() - 0.5D);
/*  685 */     int x = (int)(v.x + 0.5D);
/*  686 */     int y = (int)(v.y + 0.0D);
/*  687 */     int z = (int)(v.z + 0.5D);
/*  688 */     if (W_WorldFunc.isBlockWater(this.world, x, y, z)) {
/*  689 */       float c = this.rand.nextFloat() * 0.3F + 0.7F;
/*  690 */       MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", v.x, v.y, v.z);
/*  691 */       prm.motionX = mx + (this.rand.nextFloat() - 0.5D) * 0.7D;
/*  692 */       prm.motionY = my;
/*  693 */       prm.motionZ = mz + (this.rand.nextFloat() - 0.5D) * 0.7D;
/*  694 */       prm.size = (float)size * (this.rand.nextFloat() * 0.2F + 0.8F);
/*  695 */       prm.setColor(0.9F, c, c, c);
/*  696 */       prm.age = age + (int)(this.rand.nextFloat() * 0.5D * age);
/*  697 */       prm.gravity = gravity;
/*  698 */       MCH_ParticlesUtil.spawnParticle(prm);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void destroyAircraft() {
/*  704 */     super.destroyAircraft();
/*  705 */     this.rotDestroyedPitch = 0.0F;
/*  706 */     this.rotDestroyedRoll = 0.0F;
/*  707 */     this.rotDestroyedYaw = 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getClientPositionDelayCorrection() {
/*  712 */     return ((getTankInfo()).weightType == 1) ? 2 : 7;
/*      */   }
/*      */   
/*      */   protected void onUpdate_Client() {
/*  716 */     if (getRiddenByEntity() != null && W_Lib.isClientPlayer(getRiddenByEntity())) {
/*  717 */       (getRiddenByEntity()).rotationPitch = (getRiddenByEntity()).prevRotationPitch;
/*      */     }
/*      */     
/*  720 */     if (this.aircraftPosRotInc > 0) {
/*  721 */       applyServerPositionAndRotation();
/*      */     } else {
/*  723 */       setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*  724 */       if (!isDestroyed() && (this.onGround || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0)) {
/*  725 */         this.motionX *= 0.95D;
/*  726 */         this.motionZ *= 0.95D;
/*  727 */         applyOnGroundPitch(0.95F);
/*      */       } 
/*      */       
/*  730 */       if (isInWater()) {
/*  731 */         this.motionX *= 0.99D;
/*  732 */         this.motionZ *= 0.99D;
/*      */       } 
/*      */     } 
/*      */     
/*  736 */     updateWheels();
/*  737 */     onUpdate_Particle2();
/*  738 */     updateSound();
/*  739 */     if (this.world.isRemote) {
/*  740 */       onUpdate_ParticleLandingGear();
/*  741 */       onUpdate_ParticleSplash();
/*  742 */       onUpdate_ParticleSandCloud(true);
/*      */     } 
/*      */     
/*  745 */     updateCamera(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public void applyOnGroundPitch(float factor) {}
/*      */ 
/*      */   
/*      */   private void onUpdate_Server() {
/*  753 */     double prevMotion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  754 */     double dp = 0.0D;
/*  755 */     if (canFloatWater()) {
/*  756 */       dp = getWaterDepth();
/*      */     }
/*      */     
/*  759 */     boolean levelOff = this.isGunnerMode;
/*  760 */     if (dp == 0.0D) {
/*  761 */       if (!levelOff) {
/*  762 */         this.motionY += 0.04D + (!isInWater() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
/*  763 */         this.motionY += -0.047D * (1.0D - getCurrentThrottle());
/*      */       } else {
/*  765 */         this.motionY *= 0.8D;
/*      */       } 
/*  767 */     } else if (MathHelper.abs(getRotRoll()) < 40.0F && dp >= 1.0D) {
/*  768 */       if (this.motionY < 0.0D) {
/*  769 */         this.motionY /= 2.0D;
/*      */       }
/*      */       
/*  772 */       this.motionY += 0.007D;
/*      */     } else {
/*  774 */       this.motionY -= 1.0E-4D;
/*  775 */       this.motionY += 0.007D * getCurrentThrottle();
/*      */     } 
/*      */     
/*  778 */     float throttle = (float)(getCurrentThrottle() / 10.0D);
/*  779 */     Vec3d v = MCH_Lib.Rot2Vec3(getRotYaw(), getRotPitch() - 10.0F);
/*  780 */     if (!levelOff) {
/*  781 */       this.motionY += v.y * throttle / 8.0D;
/*      */     }
/*      */     
/*  784 */     boolean canMove = true;
/*  785 */     if (!(getAcInfo()).canMoveOnGround) {
/*  786 */       Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
/*  787 */       if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.AIR)) {
/*  788 */         canMove = false;
/*      */       }
/*      */     } 
/*      */     
/*  792 */     if (canMove) {
/*  793 */       if ((getAcInfo()).enableBack && this.throttleBack > 0.0F) {
/*  794 */         this.motionX -= v.x * this.throttleBack;
/*  795 */         this.motionZ -= v.z * this.throttleBack;
/*      */       } else {
/*  797 */         this.motionX += v.x * throttle;
/*  798 */         this.motionZ += v.z * throttle;
/*      */       } 
/*      */     }
/*      */     
/*  802 */     double motion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  803 */     float speedLimit = getMaxSpeed();
/*  804 */     if (motion > speedLimit) {
/*  805 */       this.motionX *= speedLimit / motion;
/*  806 */       this.motionZ *= speedLimit / motion;
/*  807 */       motion = speedLimit;
/*      */     } 
/*      */     
/*  810 */     if (motion > prevMotion && this.currentSpeed < speedLimit) {
/*  811 */       this.currentSpeed += (speedLimit - this.currentSpeed) / 35.0D;
/*  812 */       if (this.currentSpeed > speedLimit) {
/*  813 */         this.currentSpeed = speedLimit;
/*      */       }
/*      */     } else {
/*  816 */       this.currentSpeed -= (this.currentSpeed - 0.07D) / 35.0D;
/*  817 */       if (this.currentSpeed < 0.07D) {
/*  818 */         this.currentSpeed = 0.07D;
/*      */       }
/*      */     } 
/*      */     
/*  822 */     if (this.onGround || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0) {
/*  823 */       this.motionX *= (getAcInfo()).motionFactor;
/*  824 */       this.motionZ *= (getAcInfo()).motionFactor;
/*  825 */       if (MathHelper.abs(getRotPitch()) < 40.0F) {
/*  826 */         applyOnGroundPitch(0.8F);
/*      */       }
/*      */     } 
/*      */     
/*  830 */     updateWheels();
/*  831 */     move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/*  832 */     this.motionY *= 0.95D;
/*  833 */     this.motionX *= (getAcInfo()).motionFactor;
/*  834 */     this.motionZ *= (getAcInfo()).motionFactor;
/*  835 */     setRotation(getRotYaw(), getRotPitch());
/*  836 */     onUpdate_updateBlock();
/*  837 */     updateCollisionBox();
/*  838 */     if (getRiddenByEntity() != null && (getRiddenByEntity()).isDead) {
/*  839 */       unmountEntity();
/*      */     }
/*      */   }
/*      */   
/*      */   private void collisionEntity(AxisAlignedBB bb) {
/*  844 */     if (bb != null) {
/*  845 */       double speed = Math.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/*  846 */       if (speed > 0.05D) {
/*  847 */         Entity rider = getRiddenByEntity();
/*  848 */         float damage = (float)(speed * 15.0D);
/*      */ 
/*      */         
/*  851 */         MCH_EntityAircraft rideAc = (getRidingEntity() instanceof MCH_EntitySeat) ? ((MCH_EntitySeat)getRidingEntity()).getParent() : ((getRidingEntity() instanceof MCH_EntityAircraft) ? (MCH_EntityAircraft)getRidingEntity() : null);
/*      */         
/*  853 */         List<Entity> list = this.world.getEntitiesInAABBexcluding((Entity)this, bb
/*      */             
/*  855 */             .grow(0.3D, 0.3D, 0.3D), ex -> {
/*      */               if (ex != rideAc && !(ex instanceof net.minecraft.entity.item.EntityItem) && !(ex instanceof net.minecraft.entity.item.EntityXPOrb) && !(ex instanceof com.norwood.mcheli.weapon.MCH_EntityBaseBullet) && !(ex instanceof com.norwood.mcheli.chain.MCH_EntityChain) && !(ex instanceof MCH_EntitySeat)) {
/*      */                 if (ex instanceof MCH_EntityTank) {
/*      */                   MCH_EntityTank tank = (MCH_EntityTank)ex;
/*      */ 
/*      */ 
/*      */                   
/*      */                   if (tank.getTankInfo() != null && (tank.getTankInfo()).weightType == 2) {
/*      */                     return MCH_Config.Collision_EntityTankDamage.prmBool;
/*      */                   }
/*      */                 } 
/*      */ 
/*      */                 
/*      */                 return MCH_Config.Collision_EntityDamage.prmBool;
/*      */               } 
/*      */ 
/*      */               
/*      */               return false;
/*      */             });
/*      */ 
/*      */         
/*  876 */         for (Entity e : list) {
/*  877 */           if (shouldCollisionDamage(e)) {
/*  878 */             DamageSource ds; double dx = e.posX - this.posX;
/*  879 */             double dz = e.posZ - this.posZ;
/*  880 */             double dist = Math.sqrt(dx * dx + dz * dz);
/*  881 */             if (dist > 5.0D) {
/*  882 */               dist = 5.0D;
/*      */             }
/*      */             
/*  885 */             damage = (float)(damage + 5.0D - dist);
/*      */             
/*  887 */             if (rider instanceof EntityLivingBase) {
/*  888 */               ds = DamageSource.causeMobDamage((EntityLivingBase)rider);
/*      */             } else {
/*  890 */               ds = DamageSource.GENERIC;
/*      */             } 
/*      */             
/*  893 */             MCH_Lib.applyEntityHurtResistantTimeConfig(e);
/*  894 */             e.attackEntityFrom(ds, damage);
/*  895 */             if (e instanceof MCH_EntityAircraft) {
/*  896 */               e.motionX += this.motionX * 0.05D;
/*  897 */               e.motionZ += this.motionZ * 0.05D;
/*  898 */             } else if (e instanceof net.minecraft.entity.projectile.EntityArrow) {
/*  899 */               e.setDead();
/*      */             } else {
/*  901 */               e.motionX += this.motionX * 1.5D;
/*  902 */               e.motionZ += this.motionZ * 1.5D;
/*      */             } 
/*      */             
/*  905 */             if ((getTankInfo()).weightType != 2 && (e.width >= 1.0F || e.height >= 1.5D)) {
/*  906 */               if (e instanceof EntityLivingBase) {
/*  907 */                 ds = DamageSource.causeMobDamage((EntityLivingBase)e);
/*      */               } else {
/*  909 */                 ds = DamageSource.GENERIC;
/*      */               } 
/*      */               
/*  912 */               attackEntityFrom(ds, damage / 3.0F);
/*      */             } 
/*      */             
/*  915 */             MCH_Lib.DbgLog(this.world, "MCH_EntityTank.collisionEntity damage=%.1f %s", new Object[] { Float.valueOf(damage), e.toString() });
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean shouldCollisionDamage(Entity e) {
/*  923 */     if (getSeatIdByEntity(e) >= 0)
/*  924 */       return false; 
/*  925 */     if (this.noCollisionEntities.containsKey(e)) {
/*  926 */       return false;
/*      */     }
/*  928 */     if (e instanceof MCH_EntityHitBox && ((MCH_EntityHitBox)e).parent != null) {
/*  929 */       MCH_EntityAircraft ac = ((MCH_EntityHitBox)e).parent;
/*  930 */       if (this.noCollisionEntities.containsKey(ac)) {
/*  931 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  935 */     return ((!(e.getRidingEntity() instanceof MCH_EntityAircraft) || !this.noCollisionEntities.containsKey(e.getRidingEntity())) && (!(e.getRidingEntity() instanceof MCH_EntitySeat) || ((MCH_EntitySeat)e
/*  936 */       .getRidingEntity()).getParent() == null || 
/*  937 */       !this.noCollisionEntities.containsKey(((MCH_EntitySeat)e.getRidingEntity()).getParent())));
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateCollisionBox() {
/*  942 */     if (getAcInfo() != null) {
/*  943 */       this.WheelMng.updateBlock();
/*      */       
/*  945 */       for (MCH_BoundingBox bb : this.extraBoundingBox) {
/*  946 */         if (this.rand.nextInt(3) == 0) {
/*  947 */           if (MCH_Config.Collision_DestroyBlock.prmBool) {
/*  948 */             Vec3d v = getTransformedPosition(bb.offsetX, bb.offsetY, bb.offsetZ);
/*  949 */             destoryBlockRange(v, bb.width, bb.height);
/*      */           } 
/*      */           
/*  952 */           collisionEntity(bb.getBoundingBox());
/*      */         } 
/*      */       } 
/*      */       
/*  956 */       if (MCH_Config.Collision_DestroyBlock.prmBool) {
/*  957 */         destoryBlockRange(getTransformedPosition(0.0D, 0.0D, 0.0D), this.width * 1.5D, (this.height * 2.0F));
/*      */       }
/*      */       
/*  960 */       collisionEntity(getCollisionBoundingBox());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void destoryBlockRange(Vec3d v, double w, double h) {
/*  965 */     if (getAcInfo() != null) {
/*  966 */       List<Block> destroyBlocks = MCH_Config.getBreakableBlockListFromType((getTankInfo()).weightType);
/*  967 */       List<Block> noDestroyBlocks = MCH_Config.getNoBreakableBlockListFromType((getTankInfo()).weightType);
/*  968 */       List<Material> destroyMaterials = MCH_Config.getBreakableMaterialListFromType((getTankInfo()).weightType);
/*  969 */       int ws = (int)(w + 2.0D) / 2;
/*  970 */       int hs = (int)(h + 2.0D) / 2;
/*      */       
/*  972 */       for (int x = -ws; x <= ws; x++) {
/*  973 */         for (int z = -ws; z <= ws; z++) {
/*  974 */           for (int y = -hs; y <= hs + 1; y++) {
/*  975 */             int bx = (int)(v.x + x - 0.5D);
/*  976 */             int by = (int)(v.y + y - 1.0D);
/*  977 */             int bz = (int)(v.z + z - 0.5D);
/*  978 */             BlockPos blockpos = new BlockPos(bx, by, bz);
/*  979 */             IBlockState iblockstate = this.world.getBlockState(blockpos);
/*  980 */             Block block = (by >= 0 && by < 256) ? iblockstate.getBlock() : Blocks.AIR;
/*  981 */             Material mat = iblockstate.getMaterial();
/*  982 */             if (!Block.isEqualTo(block, Blocks.AIR)) {
/*  983 */               for (Block c : noDestroyBlocks) {
/*  984 */                 if (Block.isEqualTo(block, c)) {
/*  985 */                   block = null;
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*  990 */               if (block == null) {
/*      */                 break;
/*      */               }
/*      */               
/*  994 */               for (Block cx : destroyBlocks) {
/*  995 */                 if (Block.isEqualTo(block, cx)) {
/*  996 */                   destroyBlock(blockpos);
/*  997 */                   mat = null;
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/* 1002 */               if (mat == null) {
/*      */                 break;
/*      */               }
/*      */               
/* 1006 */               for (Material m : destroyMaterials) {
/* 1007 */                 if (iblockstate.getMaterial() == m) {
/* 1008 */                   destroyBlock(blockpos);
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void destroyBlock(BlockPos blockpos) {
/* 1020 */     if (this.rand.nextInt(8) == 0) {
/* 1021 */       W_WorldFunc.destroyBlock(this.world, blockpos, true);
/*      */     } else {
/* 1023 */       this.world.setBlockToAir(blockpos);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateWheels() {
/* 1028 */     this.WheelMng.move(this.motionX, this.motionY, this.motionZ);
/*      */   }
/*      */   
/*      */   public float getMaxSpeed() {
/* 1032 */     return (getTankInfo()).speed + 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAngles(Entity player, boolean fixRot, float fixYaw, float fixPitch, float deltaX, float deltaY, float x, float y, float partialTicks) {
/* 1037 */     if (partialTicks < 0.03F) {
/* 1038 */       partialTicks = 0.4F;
/*      */     }
/*      */     
/* 1041 */     if (partialTicks > 0.9F) {
/* 1042 */       partialTicks = 0.6F;
/*      */     }
/*      */     
/* 1045 */     this.lowPassPartialTicks.put(partialTicks);
/* 1046 */     partialTicks = this.lowPassPartialTicks.getAvg();
/* 1047 */     float ac_pitch = getRotPitch();
/* 1048 */     float ac_yaw = getRotYaw();
/* 1049 */     float ac_roll = getRotRoll();
/* 1050 */     if (isFreeLookMode()) {
/* 1051 */       y = 0.0F;
/* 1052 */       x = 0.0F;
/*      */     } 
/*      */     
/* 1055 */     float yaw = 0.0F;
/* 1056 */     float pitch = 0.0F;
/* 1057 */     float roll = 0.0F;
/* 1058 */     MCH_Math.FMatrix m_add = MCH_Math.newMatrix();
/* 1059 */     MCH_Math.MatTurnZ(m_add, roll / 180.0F * 3.1415927F);
/* 1060 */     MCH_Math.MatTurnX(m_add, pitch / 180.0F * 3.1415927F);
/* 1061 */     MCH_Math.MatTurnY(m_add, yaw / 180.0F * 3.1415927F);
/* 1062 */     MCH_Math.MatTurnZ(m_add, (float)((getRotRoll() / 180.0F) * Math.PI));
/* 1063 */     MCH_Math.MatTurnX(m_add, (float)((getRotPitch() / 180.0F) * Math.PI));
/* 1064 */     MCH_Math.MatTurnY(m_add, (float)((getRotYaw() / 180.0F) * Math.PI));
/* 1065 */     MCH_Math.FVector3D v = MCH_Math.MatrixToEuler(m_add);
/* 1066 */     v.x = MCH_Lib.RNG(v.x, -90.0F, 90.0F);
/* 1067 */     v.z = MCH_Lib.RNG(v.z, -90.0F, 90.0F);
/* 1068 */     if (v.z > 180.0F) {
/* 1069 */       v.z -= 360.0F;
/*      */     }
/*      */     
/* 1072 */     if (v.z < -180.0F) {
/* 1073 */       v.z += 360.0F;
/*      */     }
/*      */     
/* 1076 */     setRotYaw(v.y);
/* 1077 */     setRotPitch(v.x);
/* 1078 */     setRotRoll(v.z);
/* 1079 */     onUpdateAngles(partialTicks);
/* 1080 */     if ((getAcInfo()).limitRotation) {
/* 1081 */       v.x = MCH_Lib.RNG(getRotPitch(), -90.0F, 90.0F);
/* 1082 */       v.z = MCH_Lib.RNG(getRotRoll(), -90.0F, 90.0F);
/* 1083 */       setRotPitch(v.x);
/* 1084 */       setRotRoll(v.z);
/*      */     } 
/*      */     
/* 1087 */     if (MathHelper.abs(getRotPitch()) > 90.0F) {
/* 1088 */       MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setAngles Error:Pitch=%.1f", new Object[] { Float.valueOf(getRotPitch()) });
/* 1089 */       setRotPitch(0.0F);
/*      */     } 
/*      */     
/* 1092 */     if (getRotRoll() > 180.0F) {
/* 1093 */       setRotRoll(getRotRoll() - 360.0F);
/*      */     }
/*      */     
/* 1096 */     if (getRotRoll() < -180.0F) {
/* 1097 */       setRotRoll(getRotRoll() + 360.0F);
/*      */     }
/*      */     
/* 1100 */     this.prevRotationRoll = getRotRoll();
/* 1101 */     this.prevRotationPitch = getRotPitch();
/* 1102 */     if (getRidingEntity() == null) {
/* 1103 */       this.prevRotationYaw = getRotYaw();
/*      */     }
/*      */     
/* 1106 */     float deltaLimit = (getAcInfo()).cameraRotationSpeed * partialTicks;
/* 1107 */     MCH_WeaponSet ws = getCurrentWeapon(player);
/* 1108 */     deltaLimit *= (ws != null && ws.getInfo() != null) ? (ws.getInfo()).cameraRotationSpeedPitch : 1.0F;
/* 1109 */     if (deltaX > deltaLimit) {
/* 1110 */       deltaX = deltaLimit;
/*      */     }
/*      */     
/* 1113 */     if (deltaX < -deltaLimit) {
/* 1114 */       deltaX = -deltaLimit;
/*      */     }
/*      */     
/* 1117 */     if (deltaY > deltaLimit) {
/* 1118 */       deltaY = deltaLimit;
/*      */     }
/*      */     
/* 1121 */     if (deltaY < -deltaLimit) {
/* 1122 */       deltaY = -deltaLimit;
/*      */     }
/*      */     
/* 1125 */     if (!isOverridePlayerYaw() && !fixRot) {
/* 1126 */       player.turn(deltaX, 0.0F);
/*      */     } else {
/* 1128 */       if (getRidingEntity() == null) {
/* 1129 */         player.prevRotationYaw = getRotYaw() + fixYaw;
/*      */       } else {
/* 1131 */         if (getRotYaw() - player.rotationYaw > 180.0F) {
/* 1132 */           player.prevRotationYaw += 360.0F;
/*      */         }
/*      */         
/* 1135 */         if (getRotYaw() - player.rotationYaw < -180.0F) {
/* 1136 */           player.prevRotationYaw -= 360.0F;
/*      */         }
/*      */       } 
/*      */       
/* 1140 */       player.rotationYaw = getRotYaw() + fixYaw;
/*      */     } 
/*      */     
/* 1143 */     if (!isOverridePlayerPitch() && !fixRot) {
/* 1144 */       player.turn(0.0F, deltaY);
/*      */     } else {
/* 1146 */       player.prevRotationPitch = getRotPitch() + fixPitch;
/* 1147 */       player.rotationPitch = getRotPitch() + fixPitch;
/*      */     } 
/*      */     
/* 1150 */     float playerYaw = MathHelper.wrapDegrees(getRotYaw() - player.rotationYaw);
/*      */     
/* 1152 */     float playerPitch = getRotPitch() * MathHelper.cos((float)(playerYaw * Math.PI / 180.0D)) + -getRotRoll() * MathHelper.sin((float)(playerYaw * Math.PI / 180.0D));
/* 1153 */     if (MCH_MOD.proxy.isFirstPerson()) {
/* 1154 */       player.rotationPitch = MCH_Lib.RNG(player.rotationPitch, playerPitch + 
/* 1155 */           (getAcInfo()).minRotationPitch, playerPitch + (getAcInfo()).maxRotationPitch);
/*      */       
/* 1157 */       player.rotationPitch = MCH_Lib.RNG(player.rotationPitch, -90.0F, 90.0F);
/*      */     } 
/*      */     
/* 1160 */     player.prevRotationPitch = player.rotationPitch;
/* 1161 */     if ((getRidingEntity() == null && ac_yaw != getRotYaw()) || ac_pitch != getRotPitch() || ac_roll != getRotRoll()) {
/* 1162 */       this.aircraftRotChanged = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public float getSoundVolume() {
/* 1168 */     return (getAcInfo() != null && (getAcInfo()).throttleUpDown <= 0.0F) ? 0.0F : (this.soundVolume * 0.7F);
/*      */   }
/*      */   
/*      */   public void updateSound() {
/* 1172 */     float target = (float)getCurrentThrottle();
/* 1173 */     if (getRiddenByEntity() != null && (this.partCanopy == null || getCanopyRotation() < 1.0F)) {
/* 1174 */       target += 0.1F;
/*      */     }
/*      */     
/* 1177 */     if (!this.moveLeft && !this.moveRight && !this.throttleDown) {
/* 1178 */       this.soundVolumeTarget *= 0.8F;
/*      */     } else {
/* 1180 */       this.soundVolumeTarget += 0.1F;
/* 1181 */       if (this.soundVolumeTarget > 0.75F) {
/* 1182 */         this.soundVolumeTarget = 0.75F;
/*      */       }
/*      */     } 
/*      */     
/* 1186 */     if (target < this.soundVolumeTarget) {
/* 1187 */       target = this.soundVolumeTarget;
/*      */     }
/*      */     
/* 1190 */     if (this.soundVolume < target) {
/* 1191 */       this.soundVolume += 0.02F;
/* 1192 */       if (this.soundVolume >= target) {
/* 1193 */         this.soundVolume = target;
/*      */       }
/* 1195 */     } else if (this.soundVolume > target) {
/* 1196 */       this.soundVolume -= 0.02F;
/* 1197 */       if (this.soundVolume <= target) {
/* 1198 */         this.soundVolume = target;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getSoundPitch() {
/* 1205 */     float target1 = (float)(0.5D + getCurrentThrottle() * 0.5D);
/* 1206 */     float target2 = (float)(0.5D + this.soundVolumeTarget * 0.5D);
/* 1207 */     return Math.max(target1, target2);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDefaultSoundName() {
/* 1212 */     return "prop";
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasBrake() {
/* 1217 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateParts(int stat) {
/* 1222 */     super.updateParts(stat);
/* 1223 */     if (!isDestroyed()) {
/* 1224 */       MCH_Parts[] parts = new MCH_Parts[0];
/*      */       
/* 1226 */       for (MCH_Parts p : parts) {
/* 1227 */         if (p != null) {
/* 1228 */           p.updateStatusClient(stat);
/* 1229 */           p.update();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getUnfoldLandingGearThrottle() {
/* 1237 */     return 0.7F;
/*      */   }
/*      */   
/*      */   public static class ClacAxisBB {
/*      */     public final double value;
/*      */     public final AxisAlignedBB bb;
/*      */     
/*      */     public ClacAxisBB(double value, AxisAlignedBB bb) {
/* 1245 */       this.value = value;
/* 1246 */       this.bb = bb;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tank\MCH_EntityTank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */