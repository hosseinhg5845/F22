/*     */ package com.norwood.mcheli.helicopter;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_ServerSettings;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_PacketStatusRequest;
/*     */ import com.norwood.mcheli.aircraft.MCH_Rotor;
/*     */ import com.norwood.mcheli.particles.MCH_ParticleParam;
/*     */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_EntityHeli
/*     */   extends MCH_EntityAircraft
/*     */ {
/*  29 */   private static final DataParameter<Byte> FOLD_STAT = EntityDataManager.createKey(MCH_EntityHeli.class, DataSerializers.BYTE);
/*  30 */   public double prevRotationRotor = 0.0D;
/*  31 */   public double rotationRotor = 0.0D;
/*     */   public MCH_Rotor[] rotors;
/*     */   public byte lastFoldBladeStat;
/*     */   public int foldBladesCooldown;
/*  35 */   public float prevRollFactor = 0.0F;
/*     */   private MCH_HeliInfo heliInfo;
/*     */   
/*     */   public MCH_EntityHeli(World world) {
/*  39 */     super(world);
/*  40 */     this.heliInfo = null;
/*  41 */     this.currentSpeed = 0.07D;
/*  42 */     this.preventEntitySpawning = true;
/*  43 */     setSize(2.0F, 0.7F);
/*  44 */     this.motionX = 0.0D;
/*  45 */     this.motionY = 0.0D;
/*  46 */     this.motionZ = 0.0D;
/*  47 */     this.weapons = createWeapon(0);
/*  48 */     this.rotors = new MCH_Rotor[0];
/*  49 */     this.lastFoldBladeStat = -1;
/*  50 */     if (this.world.isRemote) {
/*  51 */       this.foldBladesCooldown = 40;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKindName() {
/*  57 */     return "helicopters";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEntityType() {
/*  62 */     return "Plane";
/*     */   }
/*     */   
/*     */   public MCH_HeliInfo getHeliInfo() {
/*  66 */     return this.heliInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public void changeType(String type) {
/*  71 */     MCH_Lib.DbgLog(this.world, "MCH_EntityHeli.changeType " + type + " : " + this, new Object[0]);
/*  72 */     if (!type.isEmpty()) {
/*  73 */       this.heliInfo = MCH_HeliInfoManager.get(type);
/*     */     }
/*     */     
/*  76 */     if (this.heliInfo == null) {
/*  77 */       MCH_Lib.Log((Entity)this, "##### MCH_EntityHeli changeHeliType() Heli info null %d, %s, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName() });
/*  78 */       setDead(true);
/*     */     } else {
/*  80 */       setAcInfo(this.heliInfo);
/*  81 */       newSeats(getAcInfo().getNumSeatAndRack());
/*  82 */       createRotors();
/*  83 */       this.weapons = createWeapon(1 + getSeatNum());
/*  84 */       initPartRotation(getRotYaw(), getRotPitch());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Item getItem() {
/*  91 */     return (getHeliInfo() != null) ? (Item)(getHeliInfo()).item : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMountWithNearEmptyMinecart() {
/*  96 */     return MCH_Config.MountMinecartHeli.prmBool;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 101 */     super.entityInit();
/* 102 */     this.dataManager.register(FOLD_STAT, Byte.valueOf((byte)2));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
/* 107 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 108 */     par1NBTTagCompound.setDouble("RotorSpeed", getCurrentThrottle());
/* 109 */     par1NBTTagCompound.setDouble("rotetionRotor", this.rotationRotor);
/* 110 */     par1NBTTagCompound.setBoolean("FoldBlade", (getFoldBladeStat() == 0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
/* 115 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 116 */     boolean beforeFoldBlade = (getFoldBladeStat() == 0);
/* 117 */     if (getCommonUniqueId().isEmpty()) {
/* 118 */       setCommonUniqueId(par1NBTTagCompound.getString("HeliUniqueId"));
/* 119 */       MCH_Lib.Log((Entity)this, "# MCH_EntityHeli readEntityFromNBT() " + 
/*     */ 
/*     */           
/* 122 */           W_Entity.getEntityId((Entity)this) + ", " + 
/*     */           
/* 124 */           getEntityName() + ", AircraftUniqueId=null, HeliUniqueId=" + 
/*     */           
/* 126 */           getCommonUniqueId(), new Object[0]);
/*     */     } 
/*     */ 
/*     */     
/* 130 */     if (getTypeName().isEmpty()) {
/* 131 */       setTypeName(par1NBTTagCompound.getString("HeliType"));
/* 132 */       MCH_Lib.Log((Entity)this, "# MCH_EntityHeli readEntityFromNBT() " + 
/*     */ 
/*     */           
/* 135 */           W_Entity.getEntityId((Entity)this) + ", " + 
/*     */           
/* 137 */           getEntityName() + ", TypeName=null, HeliType=" + 
/*     */           
/* 139 */           getTypeName(), new Object[0]);
/*     */     } 
/*     */ 
/*     */     
/* 143 */     setCurrentThrottle(par1NBTTagCompound.getDouble("RotorSpeed"));
/* 144 */     this.rotationRotor = par1NBTTagCompound.getDouble("rotetionRotor");
/* 145 */     setFoldBladeStat((byte)(par1NBTTagCompound.getBoolean("FoldBlade") ? 0 : 2));
/* 146 */     if (this.heliInfo == null) {
/* 147 */       this.heliInfo = MCH_HeliInfoManager.get(getTypeName());
/* 148 */       if (this.heliInfo == null) {
/* 149 */         MCH_Lib.Log((Entity)this, "##### MCH_EntityHeli readEntityFromNBT() Heli info null %d, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName() });
/* 150 */         setDead(true);
/*     */       } else {
/* 152 */         setAcInfo(this.heliInfo);
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     if (!beforeFoldBlade && getFoldBladeStat() == 0) {
/* 157 */       forceFoldBlade();
/*     */     }
/*     */     
/* 160 */     this.prevRotationRotor = this.rotationRotor;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSoundVolume() {
/* 165 */     return (getAcInfo() != null && (getAcInfo()).throttleUpDown <= 0.0F) ? 0.0F : ((float)getCurrentThrottle() * 2.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSoundPitch() {
/* 170 */     return (float)(0.2D + getCurrentThrottle() * 0.2D);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefaultSoundName() {
/* 175 */     return "heli";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getUnfoldLandingGearThrottle() {
/* 180 */     double x = this.posX - this.prevPosX;
/* 181 */     double y = this.posY - this.prevPosY;
/* 182 */     double z = this.posZ - this.prevPosZ;
/* 183 */     float s = (getAcInfo()).speed / 3.5F;
/* 184 */     return (x * x + y * y + z * z <= s) ? 0.8F : 0.3F;
/*     */   }
/*     */   
/*     */   protected void createRotors() {
/* 188 */     if (this.heliInfo != null) {
/* 189 */       this.rotors = new MCH_Rotor[this.heliInfo.rotorList.size()];
/* 190 */       int i = 0;
/*     */       
/* 192 */       for (MCH_HeliInfo.Rotor r : this.heliInfo.rotorList) {
/* 193 */         this.rotors[i] = new MCH_Rotor(r.bladeNum, r.bladeRot, 2, (float)r.pos.x, (float)r.pos.y, (float)r.pos.z, (float)r.rot.x, (float)r.rot.y, (float)r.rot.z, r.haveFoldFunc);
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
/* 205 */         i++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void forceFoldBlade() {
/* 211 */     if (this.heliInfo != null && this.rotors.length > 0 && this.heliInfo.isEnableFoldBlade) {
/* 212 */       for (MCH_Rotor r : this.rotors) {
/* 213 */         r.update((float)this.rotationRotor);
/* 214 */         foldBlades();
/* 215 */         r.forceFold();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isFoldBlades() {
/* 221 */     return (this.heliInfo != null && this.rotors.length > 0 && getFoldBladeStat() == 0);
/*     */   }
/*     */   
/*     */   protected boolean canSwitchFoldBlades() {
/* 225 */     return (this.heliInfo != null && this.rotors.length > 0 && this.heliInfo.isEnableFoldBlade && 
/* 226 */       getCurrentThrottle() <= 0.01D && this.foldBladesCooldown == 0 && (
/*     */       
/* 228 */       getFoldBladeStat() == 2 || getFoldBladeStat() == 0));
/*     */   }
/*     */   
/*     */   protected boolean canUseBlades() {
/* 232 */     if (this.heliInfo == null)
/* 233 */       return false; 
/* 234 */     if (this.rotors.length == 0)
/* 235 */       return true; 
/* 236 */     if (getFoldBladeStat() == 2) {
/* 237 */       for (MCH_Rotor r : this.rotors) {
/* 238 */         if (r.isFoldingOrUnfolding()) {
/* 239 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 243 */       return true;
/*     */     } 
/* 245 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void foldBlades() {
/* 250 */     if (this.heliInfo != null && this.rotors.length > 0) {
/* 251 */       setCurrentThrottle(0.0D);
/*     */       
/* 253 */       for (MCH_Rotor r : this.rotors) {
/* 254 */         r.startFold();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unfoldBlades() {
/* 260 */     if (this.heliInfo != null) {
/* 261 */       for (MCH_Rotor r : this.rotors) {
/* 262 */         r.startUnfold();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRideEntity(Entity ridingEntity) {
/* 269 */     if (ridingEntity instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat) {
/* 270 */       if (this.heliInfo == null || this.rotors.length == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 274 */       if (this.heliInfo.isEnableFoldBlade) {
/* 275 */         forceFoldBlade();
/* 276 */         setFoldBladeStat((byte)0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected byte getFoldBladeStat() {
/* 282 */     return ((Byte)this.dataManager.get(FOLD_STAT)).byteValue();
/*     */   }
/*     */   
/*     */   public void setFoldBladeStat(byte b) {
/* 286 */     if (!this.world.isRemote && b >= 0 && b <= 3) {
/* 287 */       this.dataManager.set(FOLD_STAT, Byte.valueOf(b));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSwitchGunnerMode() {
/* 293 */     if (super.canSwitchGunnerMode() && canUseBlades()) {
/* 294 */       float roll = MathHelper.abs(MathHelper.wrapDegrees(getRotRoll()));
/* 295 */       float pitch = MathHelper.abs(MathHelper.wrapDegrees(getRotPitch()));
/* 296 */       return (roll < 40.0F && pitch < 40.0F);
/*     */     } 
/*     */     
/* 299 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSwitchHoveringMode() {
/* 304 */     if (super.canSwitchHoveringMode() && canUseBlades()) {
/* 305 */       float roll = MathHelper.abs(MathHelper.wrapDegrees(getRotRoll()));
/* 306 */       float pitch = MathHelper.abs(MathHelper.wrapDegrees(getRotPitch()));
/* 307 */       return (roll < 40.0F && pitch < 40.0F);
/*     */     } 
/*     */     
/* 310 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateAircraft() {
/* 315 */     if (this.heliInfo == null) {
/* 316 */       changeType(getTypeName());
/* 317 */       this.prevPosX = this.posX;
/* 318 */       this.prevPosY = this.posY;
/* 319 */       this.prevPosZ = this.posZ;
/*     */     } else {
/* 321 */       if (!this.isRequestedSyncStatus) {
/* 322 */         this.isRequestedSyncStatus = true;
/* 323 */         if (this.world.isRemote) {
/* 324 */           int stat = getFoldBladeStat();
/* 325 */           if (stat == 1 || stat == 0) {
/* 326 */             forceFoldBlade();
/*     */           }
/*     */           
/* 329 */           MCH_PacketStatusRequest.requestStatus(this);
/*     */         } 
/*     */       } 
/*     */       
/* 333 */       if (this.lastRiddenByEntity == null && getRiddenByEntity() != null) {
/* 334 */         initCurrentWeapon(getRiddenByEntity());
/*     */       }
/*     */       
/* 337 */       updateWeapons();
/* 338 */       onUpdate_Seats();
/* 339 */       onUpdate_Control();
/* 340 */       onUpdate_Rotor();
/* 341 */       this.prevPosX = this.posX;
/* 342 */       this.prevPosY = this.posY;
/* 343 */       this.prevPosZ = this.posZ;
/* 344 */       if (!isDestroyed() && isHovering() && MathHelper.abs(getRotPitch()) < 70.0F) {
/* 345 */         setRotPitch(getRotPitch() * 0.95F);
/*     */       }
/*     */       
/* 348 */       if (isDestroyed() && getCurrentThrottle() > 0.0D) {
/* 349 */         if (MCH_Lib.getBlockIdY((Entity)this, 3, -2) > 0) {
/* 350 */           setCurrentThrottle(getCurrentThrottle() * 0.8D);
/*     */         }
/*     */         
/* 353 */         if (isExploded()) {
/* 354 */           setCurrentThrottle(getCurrentThrottle() * 0.98D);
/*     */         }
/*     */       } 
/*     */       
/* 358 */       updateCameraViewers();
/* 359 */       if (this.world.isRemote) {
/* 360 */         onUpdate_Client();
/*     */       } else {
/* 362 */         onUpdate_Server();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMouseRot() {
/* 369 */     return super.canMouseRot();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUpdatePitch(Entity player) {
/* 374 */     return (super.canUpdatePitch(player) && !isHovering());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUpdateRoll(Entity player) {
/* 379 */     return (super.canUpdateRoll(player) && !isHovering());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOverridePlayerPitch() {
/* 384 */     return (super.isOverridePlayerPitch() && !isHovering());
/*     */   }
/*     */ 
/*     */   
/*     */   public float getRollFactor() {
/* 389 */     float roll = super.getRollFactor();
/* 390 */     double d = getDistanceSq(this.prevPosX, this.posY, this.prevPosZ);
/* 391 */     double s = (getAcInfo()).speed;
/* 392 */     if (s > 0.1D) {
/* 393 */       double d1 = d / s;
/*     */     } else {
/* 395 */       double d1 = 0.0D;
/*     */     } 
/*     */     
/* 398 */     float f = this.prevRollFactor;
/* 399 */     this.prevRollFactor = roll;
/* 400 */     return (roll + f) / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getControlRotYaw(float mouseX, float mouseY, float tick) {
/* 405 */     return mouseX;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getControlRotPitch(float mouseX, float mouseY, float tick) {
/* 410 */     return mouseY;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getControlRotRoll(float mouseX, float mouseY, float tick) {
/* 415 */     return mouseX;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateAngles(float partialTicks) {
/* 420 */     if (!isDestroyed()) {
/* 421 */       float rotRoll = !isHovering() ? 0.04F : 0.07F;
/* 422 */       rotRoll = 1.0F - rotRoll * partialTicks;
/* 423 */       if (MCH_ServerSettings.enableRotationLimit) {
/* 424 */         if (getRotPitch() > MCH_ServerSettings.pitchLimitMax) {
/* 425 */           setRotPitch(getRotPitch() - Math.abs((getRotPitch() - MCH_ServerSettings.pitchLimitMax) * 0.1F * partialTicks));
/*     */         }
/*     */         
/* 428 */         if (getRotPitch() < MCH_ServerSettings.pitchLimitMin) {
/* 429 */           setRotPitch(getRotPitch() + Math.abs((getRotPitch() - MCH_ServerSettings.pitchLimitMin) * 0.2F * partialTicks));
/*     */         }
/*     */         
/* 432 */         if (getRotRoll() > MCH_ServerSettings.rollLimit) {
/* 433 */           setRotRoll(getRotRoll() - Math.abs((getRotRoll() - MCH_ServerSettings.rollLimit) * 0.03F * partialTicks));
/*     */         }
/*     */         
/* 436 */         if (getRotRoll() < -MCH_ServerSettings.rollLimit) {
/* 437 */           setRotRoll(getRotRoll() + Math.abs((getRotRoll() + MCH_ServerSettings.rollLimit) * 0.03F * partialTicks));
/*     */         }
/*     */       } 
/*     */       
/* 441 */       if (getRotRoll() > 0.1D && getRotRoll() < 65.0F) {
/* 442 */         setRotRoll(getRotRoll() * rotRoll);
/*     */       }
/*     */       
/* 445 */       if (getRotRoll() < -0.1D && getRotRoll() > -65.0F) {
/* 446 */         setRotRoll(getRotRoll() * rotRoll);
/*     */       }
/*     */       
/* 449 */       if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
/* 450 */         if (this.moveLeft && !this.moveRight) {
/* 451 */           setRotRoll(getRotRoll() - 1.2F * partialTicks);
/*     */         }
/*     */         
/* 454 */         if (this.moveRight && !this.moveLeft) {
/* 455 */           setRotRoll(getRotRoll() + 1.2F * partialTicks);
/*     */         }
/*     */       } else {
/* 458 */         if (MathHelper.abs(getRotPitch()) < 40.0F) {
/* 459 */           applyOnGroundPitch(0.97F);
/*     */         }
/*     */         
/* 462 */         if (this.heliInfo.isEnableFoldBlade && this.rotors.length > 0 && getFoldBladeStat() == 0 && !isDestroyed()) {
/* 463 */           if (this.moveLeft && !this.moveRight) {
/* 464 */             setRotYaw(getRotYaw() - 0.5F * partialTicks);
/*     */           }
/*     */           
/* 467 */           if (this.moveRight && !this.moveLeft) {
/* 468 */             setRotYaw(getRotYaw() + 0.5F * partialTicks);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onUpdate_Rotor() {
/* 476 */     byte stat = getFoldBladeStat();
/* 477 */     boolean isEndSwitch = true;
/* 478 */     if (stat != this.lastFoldBladeStat) {
/* 479 */       if (stat == 1) {
/* 480 */         foldBlades();
/* 481 */       } else if (stat == 3) {
/* 482 */         unfoldBlades();
/*     */       } 
/*     */       
/* 485 */       if (this.world.isRemote) {
/* 486 */         this.foldBladesCooldown = 40;
/*     */       }
/*     */       
/* 489 */       this.lastFoldBladeStat = stat;
/* 490 */     } else if (this.foldBladesCooldown > 0) {
/* 491 */       this.foldBladesCooldown--;
/*     */     } 
/*     */     
/* 494 */     for (MCH_Rotor r : this.rotors) {
/* 495 */       r.update((float)this.rotationRotor);
/* 496 */       if (r.isFoldingOrUnfolding()) {
/* 497 */         isEndSwitch = false;
/*     */       }
/*     */     } 
/*     */     
/* 501 */     if (isEndSwitch) {
/* 502 */       if (stat == 1) {
/* 503 */         setFoldBladeStat((byte)0);
/* 504 */       } else if (stat == 3) {
/* 505 */         setFoldBladeStat((byte)2);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void onUpdate_Control() {
/* 511 */     if (isHoveringMode() && !canUseFuel(true)) {
/* 512 */       switchHoveringMode(false);
/*     */     }
/*     */     
/* 515 */     if (this.isGunnerMode && !canUseFuel()) {
/* 516 */       switchGunnerMode(false);
/*     */     }
/*     */     
/* 519 */     if (!isDestroyed() && (
/* 520 */       getRiddenByEntity() != null || isHoveringMode()) && 
/* 521 */       canUseBlades() && 
/* 522 */       isCanopyClose() && 
/* 523 */       canUseFuel(true)) {
/* 524 */       if (!isHovering()) {
/* 525 */         onUpdate_ControlNotHovering();
/*     */       } else {
/* 527 */         onUpdate_ControlHovering();
/*     */       } 
/*     */     } else {
/* 530 */       if (getCurrentThrottle() > 0.0D) {
/* 531 */         addCurrentThrottle(-0.00125D);
/*     */       } else {
/* 533 */         setCurrentThrottle(0.0D);
/*     */       } 
/*     */       
/* 536 */       if (this.heliInfo.isEnableFoldBlade && this.rotors.length > 0 && getFoldBladeStat() == 0 && this.onGround && !isDestroyed()) {
/* 537 */         onUpdate_ControlFoldBladeAndOnGround();
/*     */       }
/*     */     } 
/*     */     
/* 541 */     if (this.world.isRemote) {
/* 542 */       if (!W_Lib.isClientPlayer(getRiddenByEntity())) {
/* 543 */         double ct = getThrottle();
/* 544 */         if (getCurrentThrottle() >= ct - 0.02D) {
/* 545 */           addCurrentThrottle(-0.01D);
/* 546 */         } else if (getCurrentThrottle() < ct) {
/* 547 */           addCurrentThrottle(0.01D);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 551 */       setThrottle(getCurrentThrottle());
/*     */     } 
/*     */     
/* 554 */     if (getCurrentThrottle() < 0.0D) {
/* 555 */       setCurrentThrottle(0.0D);
/*     */     }
/*     */     
/* 558 */     this.prevRotationRotor = this.rotationRotor;
/* 559 */     this.rotationRotor += (1.0D - Math.pow(1.0D - getCurrentThrottle(), 5.0D)) * (getAcInfo()).rotorSpeed;
/* 560 */     this.rotationRotor %= 360.0D;
/*     */   }
/*     */   
/*     */   protected void onUpdate_ControlNotHovering() {
/* 564 */     float throttleUpDown = (getAcInfo()).throttleUpDown;
/* 565 */     if (this.throttleUp) {
/* 566 */       if (getCurrentThrottle() < 1.0D) {
/* 567 */         addCurrentThrottle(0.02D * throttleUpDown);
/*     */       } else {
/* 569 */         setCurrentThrottle(1.0D);
/*     */       } 
/* 571 */     } else if (this.throttleDown) {
/* 572 */       if (getCurrentThrottle() > 0.0D) {
/* 573 */         addCurrentThrottle(-0.014285714285714285D * throttleUpDown);
/*     */       } else {
/* 575 */         setCurrentThrottle(0.0D);
/*     */       } 
/* 577 */     } else if ((!this.world.isRemote || W_Lib.isClientPlayer(getRiddenByEntity())) && this.cs_heliAutoThrottleDown) {
/* 578 */       if (getCurrentThrottle() > 0.52D) {
/* 579 */         addCurrentThrottle(-0.01D * throttleUpDown);
/* 580 */       } else if (getCurrentThrottle() < 0.48D) {
/* 581 */         addCurrentThrottle(0.01D * throttleUpDown);
/*     */       } 
/*     */     } 
/*     */     
/* 585 */     if (!this.world.isRemote) {
/* 586 */       boolean move = false;
/*     */       
/* 588 */       double x = 0.0D;
/* 589 */       double z = 0.0D;
/* 590 */       if (this.moveLeft && !this.moveRight) {
/* 591 */         float yaw = getRotYaw() - 90.0F;
/* 592 */         x += Math.sin(yaw * Math.PI / 180.0D);
/* 593 */         z += Math.cos(yaw * Math.PI / 180.0D);
/* 594 */         move = true;
/*     */       } 
/*     */       
/* 597 */       if (this.moveRight && !this.moveLeft) {
/* 598 */         float yaw = getRotYaw() + 90.0F;
/* 599 */         x += Math.sin(yaw * Math.PI / 180.0D);
/* 600 */         z += Math.cos(yaw * Math.PI / 180.0D);
/* 601 */         move = true;
/*     */       } 
/*     */       
/* 604 */       if (move) {
/* 605 */         double f = 1.0D;
/* 606 */         double d = Math.sqrt(x * x + z * z);
/* 607 */         this.motionX -= x / d * 0.019999999552965164D * f * (getAcInfo()).speed;
/* 608 */         this.motionZ += z / d * 0.019999999552965164D * f * (getAcInfo()).speed;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onUpdate_ControlHovering() {
/* 614 */     if (getCurrentThrottle() < 1.0D) {
/* 615 */       addCurrentThrottle(0.03333333333333333D);
/*     */     } else {
/* 617 */       setCurrentThrottle(1.0D);
/*     */     } 
/*     */     
/* 620 */     if (!this.world.isRemote) {
/* 621 */       boolean move = false;
/*     */       
/* 623 */       double x = 0.0D;
/* 624 */       double z = 0.0D;
/* 625 */       if (this.throttleUp) {
/* 626 */         float yaw = getRotYaw();
/* 627 */         x += Math.sin(yaw * Math.PI / 180.0D);
/* 628 */         z += Math.cos(yaw * Math.PI / 180.0D);
/* 629 */         move = true;
/*     */       } 
/*     */       
/* 632 */       if (this.throttleDown) {
/* 633 */         float yaw = getRotYaw() - 180.0F;
/* 634 */         x += Math.sin(yaw * Math.PI / 180.0D);
/* 635 */         z += Math.cos(yaw * Math.PI / 180.0D);
/* 636 */         move = true;
/*     */       } 
/*     */       
/* 639 */       if (this.moveLeft && !this.moveRight) {
/* 640 */         float yaw = getRotYaw() - 90.0F;
/* 641 */         x += Math.sin(yaw * Math.PI / 180.0D);
/* 642 */         z += Math.cos(yaw * Math.PI / 180.0D);
/* 643 */         move = true;
/*     */       } 
/*     */       
/* 646 */       if (this.moveRight && !this.moveLeft) {
/* 647 */         float yaw = getRotYaw() + 90.0F;
/* 648 */         x += Math.sin(yaw * Math.PI / 180.0D);
/* 649 */         z += Math.cos(yaw * Math.PI / 180.0D);
/* 650 */         move = true;
/*     */       } 
/*     */       
/* 653 */       if (move) {
/* 654 */         double d = Math.sqrt(x * x + z * z);
/* 655 */         this.motionX -= x / d * 0.009999999776482582D * (getAcInfo()).speed;
/* 656 */         this.motionZ += z / d * 0.009999999776482582D * (getAcInfo()).speed;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onUpdate_ControlFoldBladeAndOnGround() {
/* 662 */     if (!this.world.isRemote) {
/* 663 */       boolean move = false;
/*     */       
/* 665 */       double x = 0.0D;
/* 666 */       double z = 0.0D;
/* 667 */       if (this.throttleUp) {
/* 668 */         float yaw = getRotYaw();
/* 669 */         x += Math.sin(yaw * Math.PI / 180.0D);
/* 670 */         z += Math.cos(yaw * Math.PI / 180.0D);
/* 671 */         move = true;
/*     */       } 
/*     */       
/* 674 */       if (this.throttleDown) {
/* 675 */         float yaw = getRotYaw() - 180.0F;
/* 676 */         x += Math.sin(yaw * Math.PI / 180.0D);
/* 677 */         z += Math.cos(yaw * Math.PI / 180.0D);
/* 678 */         move = true;
/*     */       } 
/*     */       
/* 681 */       if (move) {
/* 682 */         double d = Math.sqrt(x * x + z * z);
/* 683 */         this.motionX -= x / d * 0.029999999329447746D;
/* 684 */         this.motionZ += z / d * 0.029999999329447746D;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onUpdate_Particle2() {
/* 690 */     if (this.world.isRemote && 
/* 691 */       getHP() <= getMaxHP() * 0.5D && 
/* 692 */       getHeliInfo() != null) {
/* 693 */       int rotorNum = (getHeliInfo()).rotorList.size();
/* 694 */       if (rotorNum > 0) {
/* 695 */         if (this.isFirstDamageSmoke) {
/* 696 */           this.prevDamageSmokePos = new Vec3d[rotorNum];
/*     */         }
/*     */         
/* 699 */         for (int ri = 0; ri < rotorNum; ri++) {
/* 700 */           Vec3d rotor_pos = ((MCH_HeliInfo.Rotor)(getHeliInfo()).rotorList.get(ri)).pos;
/* 701 */           float yaw = getRotYaw();
/* 702 */           float pitch = getRotPitch();
/* 703 */           Vec3d pos = MCH_Lib.RotVec3(rotor_pos, -yaw, -pitch, -getRotRoll());
/* 704 */           double x = this.posX + pos.x;
/* 705 */           double y = this.posY + pos.y;
/* 706 */           double z = this.posZ + pos.z;
/* 707 */           if (this.isFirstDamageSmoke) {
/* 708 */             this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*     */           }
/*     */           
/* 711 */           Vec3d prev = this.prevDamageSmokePos[ri];
/* 712 */           double dx = x - prev.x;
/* 713 */           double dy = y - prev.y;
/* 714 */           double dz = z - prev.z;
/* 715 */           int num = (int)(MathHelper.sqrt(dx * dx + dy * dy + dz * dz) * 2.0F) + 1;
/*     */           double i;
/* 717 */           for (i = 0.0D; i < num; i++) {
/* 718 */             double p = getHP() / getMaxHP();
/* 719 */             if (p < (this.rand.nextFloat() / 2.0F)) {
/* 720 */               float c = 0.2F + this.rand.nextFloat() * 0.3F;
/* 721 */               MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", prev.x + (x - prev.x) * i / num, prev.y + (y - prev.y) * i / num, prev.z + (z - prev.z) * i / num);
/*     */ 
/*     */               
/* 724 */               prm.motionX = (this.rand.nextDouble() - 0.5D) * 0.3D;
/* 725 */               prm.motionY = this.rand.nextDouble() * 0.1D;
/* 726 */               prm.motionZ = (this.rand.nextDouble() - 0.5D) * 0.3D;
/* 727 */               prm.size = this.rand.nextInt(5) + 5.0F;
/* 728 */               prm.setColor(0.7F + this.rand.nextFloat() * 0.1F, c, c, c);
/* 729 */               MCH_ParticlesUtil.spawnParticle(prm);
/* 730 */               int ebi = this.rand.nextInt(1 + this.extraBoundingBox.length);
/* 731 */               if (p < 0.3D && ebi > 0) {
/* 732 */                 AxisAlignedBB bb = this.extraBoundingBox[ebi - 1].getBoundingBox();
/* 733 */                 double bx = (bb.maxX + bb.minX) / 2.0D;
/* 734 */                 double by = (bb.maxY + bb.minY) / 2.0D;
/* 735 */                 double bz = (bb.maxZ + bb.minZ) / 2.0D;
/* 736 */                 prm.posX = bx;
/* 737 */                 prm.posY = by;
/* 738 */                 prm.posZ = bz;
/* 739 */                 MCH_ParticlesUtil.spawnParticle(prm);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 744 */           this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*     */         } 
/*     */         
/* 747 */         this.isFirstDamageSmoke = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onUpdate_Client() {
/* 755 */     if (getRiddenByEntity() != null && W_Lib.isClientPlayer(getRiddenByEntity())) {
/* 756 */       (getRiddenByEntity()).rotationPitch = (getRiddenByEntity()).prevRotationPitch;
/*     */     }
/*     */     
/* 759 */     if (this.aircraftPosRotInc > 0) {
/* 760 */       applyServerPositionAndRotation();
/*     */     } else {
/* 762 */       setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 763 */       if (!isDestroyed() && (this.onGround || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0)) {
/* 764 */         this.motionX *= 0.95D;
/* 765 */         this.motionZ *= 0.95D;
/* 766 */         applyOnGroundPitch(0.95F);
/*     */       } 
/*     */       
/* 769 */       if (isInWater()) {
/* 770 */         this.motionX *= 0.99D;
/* 771 */         this.motionZ *= 0.99D;
/*     */       } 
/*     */     } 
/*     */     
/* 775 */     if (isDestroyed()) {
/* 776 */       if (this.rotDestroyedYaw < 15.0F) {
/* 777 */         this.rotDestroyedYaw += 0.3F;
/*     */       }
/*     */       
/* 780 */       setRotYaw(getRotYaw() + this.rotDestroyedYaw * (float)getCurrentThrottle());
/* 781 */       if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
/* 782 */         if (MathHelper.abs(getRotPitch()) < 10.0F) {
/* 783 */           setRotPitch(getRotPitch() + this.rotDestroyedPitch);
/*     */         }
/*     */         
/* 786 */         setRotRoll(getRotRoll() + this.rotDestroyedRoll);
/*     */       } 
/*     */     } 
/*     */     
/* 790 */     if (getRiddenByEntity() != null);
/*     */ 
/*     */     
/* 793 */     onUpdate_ParticleSandCloud(false);
/* 794 */     onUpdate_Particle2();
/* 795 */     updateCamera(this.posX, this.posY, this.posZ);
/*     */   }
/*     */   
/*     */   private void onUpdate_Server() {
/* 799 */     double prevMotion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 800 */     float ogp = (getAcInfo()).onGroundPitch;
/* 801 */     if (!isHovering()) {
/* 802 */       double dp = 0.0D;
/* 803 */       if (canFloatWater()) {
/* 804 */         dp = getWaterDepth();
/*     */       }
/*     */       
/* 807 */       if (dp == 0.0D) {
/* 808 */         this.motionY += (!isInWater() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
/* 809 */         float yaw = getRotYaw() / 180.0F * 3.1415927F;
/* 810 */         float pitch = getRotPitch();
/* 811 */         if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) > 0) {
/* 812 */           pitch -= ogp;
/*     */         }
/*     */         
/* 815 */         this.motionX += 0.1D * MathHelper.sin(yaw) * this.currentSpeed * -(pitch * pitch * pitch / 20000.0F) * getCurrentThrottle();
/* 816 */         this.motionZ += 0.1D * MathHelper.cos(yaw) * this.currentSpeed * (pitch * pitch * pitch / 20000.0F) * getCurrentThrottle();
/* 817 */         double y = 0.0D;
/* 818 */         if (MathHelper.abs(getRotPitch()) + MathHelper.abs(getRotRoll() * 0.9F) <= 40.0F) {
/* 819 */           y = 1.0D - y / 40.0D;
/*     */         }
/*     */         
/* 822 */         double throttle = getCurrentThrottle();
/* 823 */         if (isDestroyed()) {
/* 824 */           throttle *= -0.65D;
/*     */         }
/*     */         
/* 827 */         this.motionY += (y * 0.025D + 0.03D) * throttle;
/*     */       } else {
/* 829 */         if (MathHelper.abs(getRotPitch()) < 40.0F) {
/* 830 */           float pitchx = getRotPitch();
/* 831 */           pitchx -= ogp;
/* 832 */           pitchx *= 0.9F;
/* 833 */           pitchx += ogp;
/* 834 */           setRotPitch(pitchx);
/*     */         } 
/*     */         
/* 837 */         if (MathHelper.abs(getRotRoll()) < 40.0F) {
/* 838 */           setRotRoll(getRotRoll() * 0.9F);
/*     */         }
/*     */         
/* 841 */         if (dp < 1.0D) {
/* 842 */           this.motionY -= 1.0E-4D;
/* 843 */           this.motionY += 0.007D * getCurrentThrottle();
/*     */         } else {
/* 845 */           if (this.motionY < 0.0D) {
/* 846 */             this.motionY *= 0.7D;
/*     */           }
/*     */           
/* 849 */           this.motionY += 0.007D;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 853 */       if (this.rand.nextInt(50) == 0) {
/* 854 */         this.motionX += (this.rand.nextDouble() - 0.5D) / 30.0D;
/*     */       }
/*     */       
/* 857 */       if (this.rand.nextInt(50) == 0) {
/* 858 */         this.motionY += (this.rand.nextDouble() - 0.5D) / 50.0D;
/*     */       }
/*     */       
/* 861 */       if (this.rand.nextInt(50) == 0) {
/* 862 */         this.motionZ += (this.rand.nextDouble() - 0.5D) / 30.0D;
/*     */       }
/*     */     } 
/*     */     
/* 866 */     double motion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 867 */     float speedLimit = (getAcInfo()).speed;
/* 868 */     if (motion > speedLimit) {
/* 869 */       this.motionX *= speedLimit / motion;
/* 870 */       this.motionZ *= speedLimit / motion;
/* 871 */       motion = speedLimit;
/*     */     } 
/*     */     
/* 874 */     if (isDestroyed()) {
/* 875 */       this.motionX *= 0.0D;
/* 876 */       this.motionZ *= 0.0D;
/* 877 */       this.currentSpeed = 0.0D;
/*     */     } 
/*     */     
/* 880 */     if (motion > prevMotion && this.currentSpeed < speedLimit) {
/* 881 */       this.currentSpeed += (speedLimit - this.currentSpeed) / 35.0D;
/* 882 */       if (this.currentSpeed > speedLimit) {
/* 883 */         this.currentSpeed = speedLimit;
/*     */       }
/*     */     } else {
/* 886 */       this.currentSpeed -= (this.currentSpeed - 0.07D) / 35.0D;
/* 887 */       if (this.currentSpeed < 0.07D) {
/* 888 */         this.currentSpeed = 0.07D;
/*     */       }
/*     */     } 
/*     */     
/* 892 */     if (this.onGround) {
/* 893 */       this.motionX *= 0.5D;
/* 894 */       this.motionZ *= 0.5D;
/* 895 */       if (MathHelper.abs(getRotPitch()) < 40.0F) {
/* 896 */         float pitchx = getRotPitch();
/* 897 */         pitchx -= ogp;
/* 898 */         pitchx *= 0.9F;
/* 899 */         pitchx += ogp;
/* 900 */         setRotPitch(pitchx);
/*     */       } 
/*     */       
/* 903 */       if (MathHelper.abs(getRotRoll()) < 40.0F) {
/* 904 */         setRotRoll(getRotRoll() * 0.9F);
/*     */       }
/*     */     } 
/*     */     
/* 908 */     move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 909 */     this.motionY *= 0.95D;
/* 910 */     this.motionX *= 0.99D;
/* 911 */     this.motionZ *= 0.99D;
/* 912 */     setRotation(getRotYaw(), getRotPitch());
/* 913 */     onUpdate_updateBlock();
/* 914 */     if (getRiddenByEntity() != null && (getRiddenByEntity()).isDead)
/* 915 */       unmountEntity(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helicopter\MCH_EntityHeli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */