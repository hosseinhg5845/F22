/*      */ package com.norwood.mcheli.plane;
/*      */ 
/*      */ import com.norwood.mcheli.MCH_Config;
/*      */ import com.norwood.mcheli.MCH_Lib;
/*      */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*      */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*      */ import com.norwood.mcheli.aircraft.MCH_PacketStatusRequest;
/*      */ import com.norwood.mcheli.aircraft.MCH_Parts;
/*      */ import com.norwood.mcheli.particles.MCH_ParticleParam;
/*      */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*      */ import com.norwood.mcheli.wrapper.W_Block;
/*      */ import com.norwood.mcheli.wrapper.W_Blocks;
/*      */ import com.norwood.mcheli.wrapper.W_Entity;
/*      */ import com.norwood.mcheli.wrapper.W_Lib;
/*      */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.MoverType;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public class MCP_EntityPlane extends MCH_EntityAircraft {
/*      */   public float soundVolume;
/*      */   public MCH_Parts partNozzle;
/*      */   public MCH_Parts partWing;
/*   31 */   private MCP_PlaneInfo planeInfo = null; public float rotationRotor; public float prevRotationRotor; public float addkeyRotValue;
/*      */   private boolean addKeyFlag;
/*      */   
/*      */   public MCP_EntityPlane(World world) {
/*   35 */     super(world);
/*   36 */     this.currentSpeed = 0.07D;
/*   37 */     this.field_70156_m = true;
/*   38 */     func_70105_a(2.0F, 0.7F);
/*   39 */     this.field_70159_w = 0.0D;
/*   40 */     this.field_70181_x = 0.0D;
/*   41 */     this.field_70179_y = 0.0D;
/*   42 */     this.weapons = createWeapon(0);
/*   43 */     this.soundVolume = 0.0F;
/*   44 */     this.partNozzle = null;
/*   45 */     this.partWing = null;
/*   46 */     this.field_70138_W = 0.6F;
/*   47 */     this.rotationRotor = 0.0F;
/*   48 */     this.prevRotationRotor = 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getKindName() {
/*   53 */     return "planes";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getEntityType() {
/*   58 */     return "Plane";
/*      */   }
/*      */   
/*      */   public MCP_PlaneInfo getPlaneInfo() {
/*   62 */     return this.planeInfo;
/*      */   }
/*      */ 
/*      */   
/*      */   public void changeType(String type) {
/*   67 */     MCH_Lib.DbgLog(this.field_70170_p, "MCP_EntityPlane.changeType " + type + " : " + this, new Object[0]);
/*   68 */     if (!type.isEmpty()) {
/*   69 */       this.planeInfo = MCP_PlaneInfoManager.get(type);
/*      */     }
/*      */     
/*   72 */     if (this.planeInfo == null) {
/*   73 */       MCH_Lib.Log((Entity)this, "##### MCP_EntityPlane changePlaneType() Plane info null %d, %s, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName() });
/*   74 */       func_70106_y();
/*      */     } else {
/*   76 */       setAcInfo(this.planeInfo);
/*   77 */       newSeats(getAcInfo().getNumSeatAndRack());
/*   78 */       this.partNozzle = createNozzle(this.planeInfo);
/*   79 */       this.partWing = createWing(this.planeInfo);
/*   80 */       this.weapons = createWeapon(1 + getSeatNum());
/*   81 */       initPartRotation(getRotYaw(), getRotPitch());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Item getItem() {
/*   88 */     return (getPlaneInfo() != null) ? (Item)(getPlaneInfo()).item : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canMountWithNearEmptyMinecart() {
/*   93 */     return MCH_Config.MountMinecartPlane.prmBool;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_70088_a() {
/*   98 */     super.func_70088_a();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/*  103 */     super.func_70014_b(par1NBTTagCompound);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/*  108 */     super.func_70037_a(par1NBTTagCompound);
/*  109 */     if (this.planeInfo == null) {
/*  110 */       this.planeInfo = MCP_PlaneInfoManager.get(getTypeName());
/*  111 */       if (this.planeInfo == null) {
/*  112 */         MCH_Lib.Log((Entity)this, "##### MCP_EntityPlane readEntityFromNBT() Plane info null %d, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName() });
/*  113 */         func_70106_y();
/*      */       } else {
/*  115 */         setAcInfo(this.planeInfo);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_70106_y() {
/*  122 */     super.func_70106_y();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNumEjectionSeat() {
/*  127 */     if (getAcInfo() != null && (getAcInfo()).isEnableEjectionSeat) {
/*  128 */       int n = getSeatNum() + 1;
/*  129 */       return (n <= 2) ? n : 0;
/*      */     } 
/*  131 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onInteractFirst(EntityPlayer player) {
/*  137 */     this.addkeyRotValue = 0.0F;
/*  138 */     this.addKeyFlag = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSwitchGunnerMode() {
/*  143 */     if (!super.canSwitchGunnerMode()) {
/*  144 */       return false;
/*      */     }
/*  146 */     float roll = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotRoll()));
/*  147 */     float pitch = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotPitch()));
/*  148 */     return (roll <= 40.0F && pitch <= 40.0F && getCurrentThrottle() > 0.6000000238418579D && MCH_Lib.getBlockIdY((Entity)this, 3, -5) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdateAircraft() {
/*  154 */     if (this.planeInfo == null) {
/*  155 */       changeType(getTypeName());
/*  156 */       this.field_70169_q = this.field_70165_t;
/*  157 */       this.field_70167_r = this.field_70163_u;
/*  158 */       this.field_70166_s = this.field_70161_v;
/*      */     } else {
/*  160 */       if (this.addKeyFlag) {
/*  161 */         this.addKeyFlag = false;
/*      */       }
/*      */       
/*  164 */       if (!this.isRequestedSyncStatus) {
/*  165 */         this.isRequestedSyncStatus = true;
/*  166 */         if (this.field_70170_p.field_72995_K) {
/*  167 */           MCH_PacketStatusRequest.requestStatus(this);
/*      */         }
/*      */       } 
/*      */       
/*  171 */       if (this.lastRiddenByEntity == null && getRiddenByEntity() != null) {
/*  172 */         initCurrentWeapon(getRiddenByEntity());
/*      */       }
/*      */       
/*  175 */       updateWeapons();
/*  176 */       onUpdate_Seats();
/*  177 */       onUpdate_Control();
/*  178 */       this.prevRotationRotor = this.rotationRotor;
/*  179 */       this.rotationRotor = (float)(this.rotationRotor + getCurrentThrottle() * (getAcInfo()).rotorSpeed);
/*  180 */       if (this.rotationRotor > 360.0F) {
/*  181 */         this.rotationRotor -= 360.0F;
/*  182 */         this.prevRotationRotor -= 360.0F;
/*      */       } 
/*      */       
/*  185 */       if (this.rotationRotor < 0.0F) {
/*  186 */         this.rotationRotor += 360.0F;
/*  187 */         this.prevRotationRotor += 360.0F;
/*      */       } 
/*      */       
/*  190 */       if (this.field_70122_E && getVtolMode() == 0 && this.planeInfo.isDefaultVtol) {
/*  191 */         swithVtolMode(true);
/*      */       }
/*      */       
/*  194 */       this.field_70169_q = this.field_70165_t;
/*  195 */       this.field_70167_r = this.field_70163_u;
/*  196 */       this.field_70166_s = this.field_70161_v;
/*  197 */       if (!isDestroyed() && isHovering() && MathHelper.func_76135_e(getRotPitch()) < 70.0F) {
/*  198 */         setRotPitch(getRotPitch() * 0.95F, "isHovering()");
/*      */       }
/*      */       
/*  201 */       if (isDestroyed() && getCurrentThrottle() > 0.0D) {
/*  202 */         if (MCH_Lib.getBlockIdY((Entity)this, 3, -2) > 0) {
/*  203 */           setCurrentThrottle(getCurrentThrottle() * 0.8D);
/*      */         }
/*      */         
/*  206 */         if (isExploded()) {
/*  207 */           setCurrentThrottle(getCurrentThrottle() * 0.98D);
/*      */         }
/*      */       } 
/*      */       
/*  211 */       updateCameraViewers();
/*  212 */       if (this.field_70170_p.field_72995_K) {
/*  213 */         onUpdate_Client();
/*      */       } else {
/*  215 */         onUpdate_Server();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canUpdateYaw(Entity player) {
/*  222 */     return (super.canUpdateYaw(player) && !isHovering());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canUpdatePitch(Entity player) {
/*  227 */     return (super.canUpdatePitch(player) && !isHovering());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canUpdateRoll(Entity player) {
/*  232 */     return (super.canUpdateRoll(player) && !isHovering());
/*      */   }
/*      */ 
/*      */   
/*      */   public float getYawFactor() {
/*  237 */     float yaw = (getVtolMode() > 0) ? (getPlaneInfo()).vtolYaw : super.getYawFactor();
/*  238 */     return yaw * 0.8F;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getPitchFactor() {
/*  243 */     float pitch = (getVtolMode() > 0) ? (getPlaneInfo()).vtolPitch : super.getPitchFactor();
/*  244 */     return pitch * 0.8F;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getRollFactor() {
/*  249 */     float roll = (getVtolMode() > 0) ? (getPlaneInfo()).vtolYaw : super.getRollFactor();
/*  250 */     return roll * 0.8F;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOverridePlayerPitch() {
/*  255 */     return (super.isOverridePlayerPitch() && !isHovering());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOverridePlayerYaw() {
/*  260 */     return (super.isOverridePlayerYaw() && !isHovering());
/*      */   }
/*      */ 
/*      */   
/*      */   public float getControlRotYaw(float mouseX, float mouseY, float tick) {
/*  265 */     if (MCH_Config.MouseControlFlightSimMode.prmBool) {
/*  266 */       rotationByKey(tick);
/*  267 */       return this.addkeyRotValue * 20.0F;
/*      */     } 
/*  269 */     return mouseX;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getControlRotPitch(float mouseX, float mouseY, float tick) {
/*  275 */     return mouseY;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getControlRotRoll(float mouseX, float mouseY, float tick) {
/*  280 */     if (MCH_Config.MouseControlFlightSimMode.prmBool) {
/*  281 */       return mouseX * 2.0F;
/*      */     }
/*  283 */     return (getVtolMode() == 0) ? (mouseX * 0.5F) : mouseX;
/*      */   }
/*      */ 
/*      */   
/*      */   private void rotationByKey(float partialTicks) {
/*  288 */     float rot = 0.2F;
/*  289 */     if (!MCH_Config.MouseControlFlightSimMode.prmBool && getVtolMode() != 0) {
/*  290 */       rot *= 0.0F;
/*      */     }
/*      */     
/*  293 */     if (!this.addKeyFlag) {
/*  294 */       this.addKeyFlag = true;
/*  295 */       if (this.moveLeft && !this.moveRight) {
/*  296 */         this.addkeyRotValue -= rot * partialTicks;
/*      */       }
/*      */       
/*  299 */       if (this.moveRight && !this.moveLeft) {
/*  300 */         this.addkeyRotValue += rot * partialTicks;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdateAngles(float partialTicks) {
/*  307 */     if (!isDestroyed()) {
/*  308 */       if (this.isGunnerMode) {
/*  309 */         setRotPitch(getRotPitch() * 0.95F);
/*  310 */         setRotYaw(getRotYaw() + (getAcInfo()).autoPilotRot * 0.2F);
/*  311 */         if (MathHelper.func_76135_e(getRotRoll()) > 20.0F) {
/*  312 */           setRotRoll(getRotRoll() * 0.95F);
/*      */         }
/*      */       } 
/*      */       
/*  316 */       boolean isFly = (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0);
/*  317 */       if (!isFly || isFreeLookMode() || this.isGunnerMode || ((getAcInfo()).isFloat && getWaterDepth() > 0.0D)) {
/*  318 */         float gmy = 1.0F;
/*  319 */         if (!isFly) {
/*  320 */           gmy = (getAcInfo()).mobilityYawOnGround;
/*  321 */           if (!(getAcInfo()).canRotOnGround) {
/*  322 */             Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
/*  323 */             if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.field_150350_a)) {
/*  324 */               gmy = 0.0F;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  329 */         if (this.moveLeft && !this.moveRight) {
/*  330 */           setRotYaw(getRotYaw() - 0.6F * gmy * partialTicks);
/*      */         }
/*      */         
/*  333 */         if (this.moveRight && !this.moveLeft) {
/*  334 */           setRotYaw(getRotYaw() + 0.6F * gmy * partialTicks);
/*      */         }
/*  336 */       } else if (!MCH_Config.MouseControlFlightSimMode.prmBool) {
/*  337 */         rotationByKey(partialTicks);
/*  338 */         setRotRoll(getRotRoll() + this.addkeyRotValue * 0.5F * (getAcInfo()).mobilityRoll);
/*      */       } 
/*      */       
/*  341 */       this.addkeyRotValue = (float)(this.addkeyRotValue * (1.0D - (0.1F * partialTicks)));
/*  342 */       if (!isFly && MathHelper.func_76135_e(getRotPitch()) < 40.0F) {
/*  343 */         applyOnGroundPitch(0.97F);
/*      */       }
/*      */       
/*  346 */       if (getNozzleRotation() > 0.001F) {
/*  347 */         float rot = 1.0F - 0.03F * partialTicks;
/*  348 */         setRotPitch(getRotPitch() * rot);
/*  349 */         rot = 1.0F - 0.1F * partialTicks;
/*  350 */         setRotRoll(getRotRoll() * rot);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onUpdate_Control() {
/*  356 */     if (this.isGunnerMode && !canUseFuel()) {
/*  357 */       switchGunnerMode(false);
/*      */     }
/*      */     
/*  360 */     this.throttleBack = (float)(this.throttleBack * 0.8D);
/*  361 */     if (getRiddenByEntity() != null && 
/*  362 */       !(getRiddenByEntity()).field_70128_L && 
/*  363 */       isCanopyClose() && 
/*  364 */       canUseWing() && 
/*  365 */       canUseFuel() && 
/*  366 */       !isDestroyed()) {
/*  367 */       onUpdate_ControlNotHovering();
/*  368 */     } else if (isTargetDrone() && canUseFuel() && !isDestroyed()) {
/*  369 */       this.throttleUp = true;
/*  370 */       onUpdate_ControlNotHovering();
/*  371 */     } else if (getCurrentThrottle() > 0.0D) {
/*  372 */       addCurrentThrottle(-0.0025D * (getAcInfo()).throttleUpDown);
/*      */     } else {
/*  374 */       setCurrentThrottle(0.0D);
/*      */     } 
/*      */     
/*  377 */     if (getCurrentThrottle() < 0.0D) {
/*  378 */       setCurrentThrottle(0.0D);
/*      */     }
/*      */     
/*  381 */     if (this.field_70170_p.field_72995_K) {
/*  382 */       if (!W_Lib.isClientPlayer(getRiddenByEntity())) {
/*  383 */         double ct = getThrottle();
/*  384 */         if (getCurrentThrottle() > ct) {
/*  385 */           addCurrentThrottle(-0.005D);
/*      */         }
/*      */         
/*  388 */         if (getCurrentThrottle() < ct) {
/*  389 */           addCurrentThrottle(0.005D);
/*      */         }
/*      */       } 
/*      */     } else {
/*  393 */       setThrottle(getCurrentThrottle());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onUpdate_ControlNotHovering() {
/*  398 */     if (!this.isGunnerMode) {
/*  399 */       float throttleUpDown = (getAcInfo()).throttleUpDown;
/*  400 */       boolean turn = ((this.moveLeft && !this.moveRight) || (!this.moveLeft && this.moveRight));
/*  401 */       boolean localThrottleUp = this.throttleUp;
/*  402 */       if (turn && getCurrentThrottle() < (getAcInfo()).pivotTurnThrottle && !localThrottleUp && !this.throttleDown) {
/*  403 */         localThrottleUp = true;
/*  404 */         throttleUpDown *= 2.0F;
/*      */       } 
/*      */       
/*  407 */       if (localThrottleUp) {
/*  408 */         float f = throttleUpDown;
/*  409 */         if (func_184187_bx() != null) {
/*  410 */           double mx = (func_184187_bx()).field_70159_w;
/*  411 */           double mz = (func_184187_bx()).field_70179_y;
/*  412 */           f = throttleUpDown * MathHelper.func_76133_a(mx * mx + mz * mz) * (getAcInfo()).throttleUpDownOnEntity;
/*      */         } 
/*      */         
/*  415 */         if ((getAcInfo()).enableBack && this.throttleBack > 0.0F) {
/*  416 */           this.throttleBack = (float)(this.throttleBack - 0.01D * f);
/*      */         } else {
/*  418 */           this.throttleBack = 0.0F;
/*  419 */           if (getCurrentThrottle() < 1.0D) {
/*  420 */             addCurrentThrottle(0.01D * f);
/*      */           } else {
/*  422 */             setCurrentThrottle(1.0D);
/*      */           } 
/*      */         } 
/*  425 */       } else if (this.throttleDown) {
/*  426 */         if (getCurrentThrottle() > 0.0D) {
/*  427 */           addCurrentThrottle(-0.01D * throttleUpDown);
/*      */         } else {
/*  429 */           setCurrentThrottle(0.0D);
/*  430 */           if ((getAcInfo()).enableBack) {
/*  431 */             this.throttleBack = (float)(this.throttleBack + 0.0025D * throttleUpDown);
/*  432 */             if (this.throttleBack > 0.6F) {
/*  433 */               this.throttleBack = 0.6F;
/*      */             }
/*      */           } 
/*      */         } 
/*  437 */       } else if (this.cs_planeAutoThrottleDown && getCurrentThrottle() > 0.0D) {
/*  438 */         addCurrentThrottle(-0.005D * throttleUpDown);
/*  439 */         if (getCurrentThrottle() <= 0.0D) {
/*  440 */           setCurrentThrottle(0.0D);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onUpdate_Particle() {
/*  447 */     if (this.field_70170_p.field_72995_K) {
/*  448 */       onUpdate_ParticleLandingGear();
/*  449 */       onUpdate_ParticleNozzle();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onUpdate_Particle2() {
/*  454 */     if (this.field_70170_p.field_72995_K && 
/*  455 */       getHP() < getMaxHP() * 0.5D && 
/*  456 */       getPlaneInfo() != null) {
/*  457 */       int rotorNum = (getPlaneInfo()).rotorList.size();
/*      */       
/*  459 */       if (this.isFirstDamageSmoke) {
/*  460 */         this.prevDamageSmokePos = new Vec3d[rotorNum + 1];
/*      */       }
/*      */       
/*  463 */       float yaw = getRotYaw();
/*  464 */       float pitch = getRotPitch();
/*  465 */       float roll = getRotRoll();
/*  466 */       boolean spawnSmoke = true;
/*      */       
/*  468 */       for (int ri = 0; ri < rotorNum; ri++) {
/*  469 */         if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
/*  470 */           int d = (int)((getHP() / getMaxHP() - 0.2D) / 0.3D * 15.0D);
/*  471 */           if (d > 0 && this.field_70146_Z.nextInt(d) > 0) {
/*  472 */             spawnSmoke = false;
/*      */           }
/*      */         } 
/*      */         
/*  476 */         Vec3d rotor_pos = ((MCP_PlaneInfo.Rotor)(getPlaneInfo()).rotorList.get(ri)).pos;
/*  477 */         Vec3d pos = MCH_Lib.RotVec3(rotor_pos, -yaw, -pitch, -roll);
/*  478 */         double x = this.field_70165_t + pos.field_72450_a;
/*  479 */         double y = this.field_70163_u + pos.field_72448_b;
/*  480 */         double z = this.field_70161_v + pos.field_72449_c;
/*  481 */         onUpdate_Particle2SpawnSmoke(ri, x, y, z, 1.0F, spawnSmoke);
/*      */       } 
/*      */       
/*  484 */       spawnSmoke = true;
/*  485 */       if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
/*  486 */         int d = (int)((getHP() / getMaxHP() - 0.2D) / 0.3D * 15.0D);
/*  487 */         if (d > 0 && this.field_70146_Z.nextInt(d) > 0) {
/*  488 */           spawnSmoke = false;
/*      */         }
/*      */       } 
/*      */       
/*  492 */       double px = this.field_70165_t;
/*  493 */       double py = this.field_70163_u;
/*  494 */       double pz = this.field_70161_v;
/*  495 */       if (getSeatInfo(0) != null && (getSeatInfo(0)).pos != null) {
/*  496 */         Vec3d pos = MCH_Lib.RotVec3(0.0D, (getSeatInfo(0)).pos.field_72448_b, -2.0D, -yaw, -pitch, -roll);
/*  497 */         px += pos.field_72450_a;
/*  498 */         py += pos.field_72448_b;
/*  499 */         pz += pos.field_72449_c;
/*      */       } 
/*      */       
/*  502 */       onUpdate_Particle2SpawnSmoke(rotorNum, px, py, pz, (rotorNum == 0) ? 2.0F : 1.0F, spawnSmoke);
/*  503 */       this.isFirstDamageSmoke = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate_Particle2SpawnSmoke(int ri, double x, double y, double z, float size, boolean spawnSmoke) {
/*  510 */     if (this.isFirstDamageSmoke || this.prevDamageSmokePos[ri] == null) {
/*  511 */       this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */     }
/*      */     
/*  514 */     Vec3d prev = this.prevDamageSmokePos[ri];
/*  515 */     double dx = x - prev.field_72450_a;
/*  516 */     double dy = y - prev.field_72448_b;
/*  517 */     double dz = z - prev.field_72449_c;
/*  518 */     int num = (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) / 0.3D) + 1;
/*      */     
/*  520 */     for (int i = 0; i < num; i++) {
/*  521 */       float c = 0.2F + this.field_70146_Z.nextFloat() * 0.3F;
/*  522 */       MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", prev.field_72450_a + (x - prev.field_72450_a) * i / 3.0D, prev.field_72448_b + (y - prev.field_72448_b) * i / 3.0D, prev.field_72449_c + (z - prev.field_72449_c) * i / 3.0D);
/*      */ 
/*      */       
/*  525 */       prm.motionX = size * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
/*  526 */       prm.motionY = size * this.field_70146_Z.nextDouble() * 0.1D;
/*  527 */       prm.motionZ = size * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
/*  528 */       prm.size = size * (this.field_70146_Z.nextInt(5) + 5.0F) * 1.0F;
/*  529 */       prm.setColor(0.7F + this.field_70146_Z.nextFloat() * 0.1F, c, c, c);
/*  530 */       MCH_ParticlesUtil.spawnParticle(prm);
/*      */     } 
/*      */     
/*  533 */     this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */   }
/*      */   
/*      */   public void onUpdate_ParticleLandingGear() {
/*  537 */     double d = this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y;
/*  538 */     if (d > 0.01D) {
/*  539 */       int x = MathHelper.func_76128_c(this.field_70165_t + 0.5D);
/*  540 */       int y = MathHelper.func_76128_c(this.field_70163_u - 0.5D);
/*  541 */       int z = MathHelper.func_76128_c(this.field_70161_v + 0.5D);
/*  542 */       MCH_ParticlesUtil.spawnParticleTileCrack(this.field_70170_p, x, y, z, this.field_70165_t + (this.field_70146_Z
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  547 */           .nextFloat() - 0.5D) * this.field_70130_N, 
/*  548 */           (func_174813_aQ()).field_72338_b + 0.1D, this.field_70161_v + (this.field_70146_Z
/*  549 */           .nextFloat() - 0.5D) * this.field_70130_N, -this.field_70159_w * 4.0D, 1.5D, -this.field_70179_y * 4.0D);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void onUpdate_ParticleSplash() {
/*  558 */     if (getAcInfo() != null && 
/*  559 */       this.field_70170_p.field_72995_K) {
/*  560 */       double mx = this.field_70165_t - this.field_70169_q;
/*  561 */       double mz = this.field_70161_v - this.field_70166_s;
/*  562 */       double dist = mx * mx + mz * mz;
/*  563 */       if (dist > 1.0D) {
/*  564 */         dist = 1.0D;
/*      */       }
/*      */       
/*  567 */       for (MCH_AircraftInfo.ParticleSplash p : (getAcInfo()).particleSplashs) {
/*  568 */         for (int i = 0; i < p.num; i++) {
/*  569 */           if (dist > 0.03D + this.field_70146_Z.nextFloat() * 0.1D) {
/*  570 */             setParticleSplash(p.pos, -mx * p.acceleration, p.motionY, -mz * p.acceleration, p.gravity, p.size * (0.5D + dist * 0.5D), p.age);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setParticleSplash(Vec3d pos, double mx, double my, double mz, float gravity, double size, int age) {
/*  579 */     Vec3d v = getTransformedPosition(pos);
/*  580 */     v = v.func_72441_c(this.field_70146_Z.nextDouble() - 0.5D, (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D, this.field_70146_Z.nextDouble() - 0.5D);
/*  581 */     int x = (int)(v.field_72450_a + 0.5D);
/*  582 */     int y = (int)(v.field_72448_b + 0.0D);
/*  583 */     int z = (int)(v.field_72449_c + 0.5D);
/*  584 */     if (W_WorldFunc.isBlockWater(this.field_70170_p, x, y, z)) {
/*  585 */       float c = this.field_70146_Z.nextFloat() * 0.3F + 0.7F;
/*  586 */       MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", v.field_72450_a, v.field_72448_b, v.field_72449_c);
/*  587 */       prm.motionX = mx + (this.field_70146_Z.nextFloat() - 0.5D) * 0.7D;
/*  588 */       prm.motionY = my;
/*  589 */       prm.motionZ = mz + (this.field_70146_Z.nextFloat() - 0.5D) * 0.7D;
/*  590 */       prm.size = (float)size * (this.field_70146_Z.nextFloat() * 0.2F + 0.8F);
/*  591 */       prm.setColor(0.9F, c, c, c);
/*  592 */       prm.age = age + (int)(this.field_70146_Z.nextFloat() * 0.5D * age);
/*  593 */       prm.gravity = gravity;
/*  594 */       MCH_ParticlesUtil.spawnParticle(prm);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onUpdate_ParticleNozzle() {
/*  599 */     if (this.planeInfo != null && this.planeInfo.haveNozzle() && 
/*  600 */       getCurrentThrottle() > 0.10000000149011612D) {
/*  601 */       float yaw = getRotYaw();
/*  602 */       float pitch = getRotPitch();
/*  603 */       float roll = getRotRoll();
/*  604 */       Vec3d nozzleRot = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -yaw - 180.0F, pitch - getNozzleRotation(), roll);
/*      */       
/*  606 */       for (MCH_AircraftInfo.DrawnPart nozzle : this.planeInfo.nozzles) {
/*  607 */         if (this.field_70146_Z.nextFloat() <= getCurrentThrottle() * 1.5D) {
/*  608 */           Vec3d nozzlePos = MCH_Lib.RotVec3(nozzle.pos, -yaw, -pitch, -roll);
/*  609 */           double x = this.field_70165_t + nozzlePos.field_72450_a + nozzleRot.field_72450_a;
/*  610 */           double y = this.field_70163_u + nozzlePos.field_72448_b + nozzleRot.field_72448_b;
/*  611 */           double z = this.field_70161_v + nozzlePos.field_72449_c + nozzleRot.field_72449_c;
/*  612 */           float a = 0.7F;
/*  613 */           if (W_WorldFunc.getBlockId(this.field_70170_p, (int)(x + nozzleRot.field_72450_a * 3.0D), (int)(y + nozzleRot.field_72448_b * 3.0D), (int)(z + nozzleRot.field_72449_c * 3.0D)) != 0) {
/*  614 */             a = 2.0F;
/*      */           }
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
/*  626 */           MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", x, y, z, nozzleRot.field_72450_a + ((this.field_70146_Z.nextFloat() - 0.5F) * a), nozzleRot.field_72448_b, nozzleRot.field_72449_c + ((this.field_70146_Z.nextFloat() - 0.5F) * a), 5.0F * (getAcInfo()).particlesScale);
/*      */           
/*  628 */           MCH_ParticlesUtil.spawnParticle(prm);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroyAircraft() {
/*  637 */     super.destroyAircraft();
/*  638 */     int inv = 1;
/*  639 */     if (getRotRoll() >= 0.0F) {
/*  640 */       if (getRotRoll() > 90.0F) {
/*  641 */         inv = -1;
/*      */       }
/*  643 */     } else if (getRotRoll() > -90.0F) {
/*  644 */       inv = -1;
/*      */     } 
/*      */     
/*  647 */     this.rotDestroyedRoll = (0.5F + this.field_70146_Z.nextFloat()) * inv;
/*      */   }
/*      */   
/*      */   protected void onUpdate_Client() {
/*  651 */     if (getRiddenByEntity() != null && W_Lib.isClientPlayer(getRiddenByEntity())) {
/*  652 */       (getRiddenByEntity()).field_70125_A = (getRiddenByEntity()).field_70127_C;
/*      */     }
/*      */     
/*  655 */     if (this.aircraftPosRotInc > 0) {
/*  656 */       applyServerPositionAndRotation();
/*      */     } else {
/*  658 */       func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*  659 */       if (!isDestroyed() && (this.field_70122_E || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0)) {
/*  660 */         this.field_70159_w *= 0.95D;
/*  661 */         this.field_70179_y *= 0.95D;
/*  662 */         applyOnGroundPitch(0.95F);
/*      */       } 
/*      */       
/*  665 */       if (func_70090_H()) {
/*  666 */         this.field_70159_w *= 0.99D;
/*  667 */         this.field_70179_y *= 0.99D;
/*      */       } 
/*      */     } 
/*      */     
/*  671 */     if (isDestroyed()) {
/*  672 */       if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
/*  673 */         if (MathHelper.func_76135_e(getRotPitch()) < 10.0F) {
/*  674 */           setRotPitch(getRotPitch() + this.rotDestroyedPitch);
/*      */         }
/*      */         
/*  677 */         float roll = MathHelper.func_76135_e(getRotRoll());
/*  678 */         if (roll < 45.0F || roll > 135.0F) {
/*  679 */           setRotRoll(getRotRoll() + this.rotDestroyedRoll);
/*      */         }
/*  681 */       } else if (MathHelper.func_76135_e(getRotPitch()) > 20.0F) {
/*  682 */         setRotPitch(getRotPitch() * 0.99F);
/*      */       } 
/*      */     }
/*      */     
/*  686 */     if (getRiddenByEntity() != null);
/*      */ 
/*      */     
/*  689 */     updateSound();
/*  690 */     onUpdate_Particle();
/*  691 */     onUpdate_Particle2();
/*  692 */     onUpdate_ParticleSplash();
/*  693 */     onUpdate_ParticleSandCloud(true);
/*  694 */     updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*      */   }
/*      */   private void onUpdate_Server() {
/*      */     Vec3d v;
/*  698 */     double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*  699 */     double dp = 0.0D;
/*  700 */     if (canFloatWater()) {
/*  701 */       dp = getWaterDepth();
/*      */     }
/*      */     
/*  704 */     boolean levelOff = this.isGunnerMode;
/*  705 */     if (dp == 0.0D) {
/*  706 */       if (isTargetDrone() && canUseFuel() && !isDestroyed()) {
/*  707 */         Block block = MCH_Lib.getBlockY((Entity)this, 3, -40, true);
/*  708 */         if (block != null && !W_Block.isEqual(block, W_Blocks.field_150350_a)) {
/*  709 */           block = MCH_Lib.getBlockY((Entity)this, 3, -5, true);
/*  710 */           if (block == null || W_Block.isEqual(block, W_Blocks.field_150350_a)) {
/*  711 */             setRotYaw(getRotYaw() + (getAcInfo()).autoPilotRot * 2.0F);
/*  712 */             if (getRotPitch() > -20.0F) {
/*  713 */               setRotPitch(getRotPitch() - 0.5F);
/*      */             }
/*      */           } 
/*      */         } else {
/*  717 */           setRotYaw(getRotYaw() + (getAcInfo()).autoPilotRot);
/*  718 */           setRotPitch(getRotPitch() * 0.95F);
/*  719 */           if (canFoldLandingGear()) {
/*  720 */             foldLandingGear();
/*      */           }
/*      */           
/*  723 */           levelOff = true;
/*      */         } 
/*      */       } 
/*      */       
/*  727 */       if (!levelOff) {
/*  728 */         this.field_70181_x += 0.04D + (!func_70090_H() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
/*  729 */         this.field_70181_x += -0.047D * (1.0D - getCurrentThrottle());
/*      */       } else {
/*  731 */         this.field_70181_x *= 0.8D;
/*      */       } 
/*      */     } else {
/*  734 */       setRotPitch(getRotPitch() * 0.8F, "getWaterDepth != 0");
/*  735 */       if (MathHelper.func_76135_e(getRotRoll()) < 40.0F) {
/*  736 */         setRotRoll(getRotRoll() * 0.9F);
/*      */       }
/*      */       
/*  739 */       if (dp < 1.0D) {
/*  740 */         this.field_70181_x -= 1.0E-4D;
/*  741 */         this.field_70181_x += 0.007D * getCurrentThrottle();
/*      */       } else {
/*  743 */         if (this.field_70181_x < 0.0D) {
/*  744 */           this.field_70181_x /= 2.0D;
/*      */         }
/*      */         
/*  747 */         this.field_70181_x += 0.007D;
/*      */       } 
/*      */     } 
/*      */     
/*  751 */     float throttle = (float)(getCurrentThrottle() / 10.0D);
/*      */     
/*  753 */     if (getNozzleRotation() > 0.001F) {
/*  754 */       setRotPitch(getRotPitch() * 0.95F);
/*  755 */       v = MCH_Lib.Rot2Vec3(getRotYaw(), getRotPitch() - getNozzleRotation());
/*  756 */       if (getNozzleRotation() >= 90.0F) {
/*  757 */         v = new Vec3d(v.field_72450_a * 0.800000011920929D, v.field_72448_b, v.field_72449_c * 0.800000011920929D);
/*      */       }
/*      */     } else {
/*  760 */       v = MCH_Lib.Rot2Vec3(getRotYaw(), getRotPitch() - 10.0F);
/*      */     } 
/*      */     
/*  763 */     if (!levelOff) {
/*  764 */       if (getNozzleRotation() <= 0.01F) {
/*  765 */         this.field_70181_x += v.field_72448_b * throttle / 2.0D;
/*      */       } else {
/*  767 */         this.field_70181_x += v.field_72448_b * throttle / 8.0D;
/*      */       } 
/*      */     }
/*      */     
/*  771 */     boolean canMove = true;
/*  772 */     if (!(getAcInfo()).canMoveOnGround) {
/*  773 */       Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
/*  774 */       if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.field_150350_a)) {
/*  775 */         canMove = false;
/*      */       }
/*      */     } 
/*      */     
/*  779 */     if (canMove) {
/*  780 */       if ((getAcInfo()).enableBack && this.throttleBack > 0.0F) {
/*  781 */         this.field_70159_w -= v.field_72450_a * this.throttleBack;
/*  782 */         this.field_70179_y -= v.field_72449_c * this.throttleBack;
/*      */       } else {
/*  784 */         this.field_70159_w += v.field_72450_a * throttle;
/*  785 */         this.field_70179_y += v.field_72449_c * throttle;
/*      */       } 
/*      */     }
/*      */     
/*  789 */     double motion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*  790 */     float speedLimit = getMaxSpeed();
/*  791 */     if (motion > speedLimit) {
/*  792 */       this.field_70159_w *= speedLimit / motion;
/*  793 */       this.field_70179_y *= speedLimit / motion;
/*  794 */       motion = speedLimit;
/*      */     } 
/*      */     
/*  797 */     if (motion > prevMotion && this.currentSpeed < speedLimit) {
/*  798 */       this.currentSpeed += (speedLimit - this.currentSpeed) / 35.0D;
/*  799 */       if (this.currentSpeed > speedLimit) {
/*  800 */         this.currentSpeed = speedLimit;
/*      */       }
/*      */     } else {
/*  803 */       this.currentSpeed -= (this.currentSpeed - 0.07D) / 35.0D;
/*  804 */       if (this.currentSpeed < 0.07D) {
/*  805 */         this.currentSpeed = 0.07D;
/*      */       }
/*      */     } 
/*      */     
/*  809 */     if (this.field_70122_E || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0) {
/*  810 */       this.field_70159_w *= (getAcInfo()).motionFactor;
/*  811 */       this.field_70179_y *= (getAcInfo()).motionFactor;
/*  812 */       if (MathHelper.func_76135_e(getRotPitch()) < 40.0F) {
/*  813 */         applyOnGroundPitch(0.8F);
/*      */       }
/*      */     } 
/*      */     
/*  817 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*  818 */     this.field_70181_x *= 0.95D;
/*  819 */     this.field_70159_w *= (getAcInfo()).motionFactor;
/*  820 */     this.field_70179_y *= (getAcInfo()).motionFactor;
/*  821 */     func_70101_b(getRotYaw(), getRotPitch());
/*  822 */     onUpdate_updateBlock();
/*  823 */     if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L) {
/*  824 */       unmountEntity();
/*      */     }
/*      */   }
/*      */   
/*      */   public float getMaxSpeed() {
/*  829 */     float f = 0.0F;
/*  830 */     if (this.partWing != null && (getPlaneInfo()).isVariableSweepWing) {
/*  831 */       f = ((getPlaneInfo()).sweepWingSpeed - (getPlaneInfo()).speed) * this.partWing.getFactor();
/*  832 */     } else if (this.partHatch != null && (getPlaneInfo()).isVariableSweepWing) {
/*  833 */       f = ((getPlaneInfo()).sweepWingSpeed - (getPlaneInfo()).speed) * this.partHatch.getFactor();
/*      */     } 
/*      */     
/*  836 */     return (getPlaneInfo()).speed + f;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getSoundVolume() {
/*  841 */     return (getAcInfo() != null && (getAcInfo()).throttleUpDown <= 0.0F) ? 0.0F : (this.soundVolume * 0.7F);
/*      */   }
/*      */   
/*      */   public void updateSound() {
/*  845 */     float target = (float)getCurrentThrottle();
/*  846 */     if (getRiddenByEntity() != null && (this.partCanopy == null || getCanopyRotation() < 1.0F)) {
/*  847 */       target += 0.1F;
/*      */     }
/*      */     
/*  850 */     if (this.soundVolume < target) {
/*  851 */       this.soundVolume += 0.02F;
/*  852 */       if (this.soundVolume >= target) {
/*  853 */         this.soundVolume = target;
/*      */       }
/*  855 */     } else if (this.soundVolume > target) {
/*  856 */       this.soundVolume -= 0.02F;
/*  857 */       if (this.soundVolume <= target) {
/*  858 */         this.soundVolume = target;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getSoundPitch() {
/*  865 */     return (float)(0.6D + getCurrentThrottle() * 0.4D);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDefaultSoundName() {
/*  870 */     return "plane";
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateParts(int stat) {
/*  875 */     super.updateParts(stat);
/*  876 */     if (!isDestroyed()) {
/*  877 */       MCH_Parts[] parts = { this.partNozzle, this.partWing };
/*      */       
/*  879 */       for (MCH_Parts p : parts) {
/*  880 */         if (p != null) {
/*  881 */           p.updateStatusClient(stat);
/*  882 */           p.update();
/*      */         } 
/*      */       } 
/*      */       
/*  886 */       if (!this.field_70170_p.field_72995_K && this.partWing != null && 
/*      */         
/*  888 */         (getPlaneInfo()).isVariableSweepWing && this.partWing
/*  889 */         .isON() && 
/*  890 */         getCurrentThrottle() >= 0.20000000298023224D && (
/*  891 */         getCurrentThrottle() < 0.5D || MCH_Lib.getBlockIdY((Entity)this, 1, -10) != 0)) {
/*  892 */         this.partWing.setStatusServer(false);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getUnfoldLandingGearThrottle() {
/*  899 */     return 0.7F;
/*      */   }
/*      */   
/*      */   public boolean canSwitchVtol() {
/*  903 */     if (this.planeInfo == null || !this.planeInfo.isEnableVtol)
/*  904 */       return false; 
/*  905 */     if (getModeSwitchCooldown() > 0)
/*  906 */       return false; 
/*  907 */     if (getVtolMode() == 1)
/*  908 */       return false; 
/*  909 */     if (MathHelper.func_76135_e(getRotRoll()) > 30.0F)
/*  910 */       return false; 
/*  911 */     if (this.field_70122_E && this.planeInfo.isDefaultVtol) {
/*  912 */       return false;
/*      */     }
/*  914 */     setModeSwitchCooldown(20);
/*  915 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getNozzleStat() {
/*  920 */     return (this.partNozzle != null && this.partNozzle.getStatus());
/*      */   }
/*      */ 
/*      */   
/*      */   public int getVtolMode() {
/*  925 */     if (!getNozzleStat()) {
/*  926 */       return (getNozzleRotation() <= 0.005F) ? 0 : 1;
/*      */     }
/*  928 */     return (getNozzleRotation() >= 89.995F) ? 2 : 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFuleConsumptionFactor() {
/*  933 */     return getFuelConsumptionFactor() * 1.0F;
/*      */   }
/*      */   
/*      */   public float getNozzleRotation() {
/*  937 */     return (this.partNozzle != null) ? this.partNozzle.rotation : 0.0F;
/*      */   }
/*      */   
/*      */   public float getPrevNozzleRotation() {
/*  941 */     return (this.partNozzle != null) ? this.partNozzle.prevRotation : 0.0F;
/*      */   }
/*      */   
/*      */   public void swithVtolMode(boolean mode) {
/*  945 */     if (this.partNozzle != null) {
/*  946 */       if (this.planeInfo.isDefaultVtol && this.field_70122_E && !mode) {
/*      */         return;
/*      */       }
/*      */       
/*  950 */       if (!this.field_70170_p.field_72995_K) {
/*  951 */         this.partNozzle.setStatusServer(mode);
/*      */       }
/*      */       
/*  954 */       if (getRiddenByEntity() != null && !(getRiddenByEntity()).field_70128_L) {
/*  955 */         (getRiddenByEntity()).field_70125_A = (getRiddenByEntity()).field_70127_C = 0.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected MCH_Parts createNozzle(MCP_PlaneInfo info) {
/*  961 */     MCH_Parts nozzle = null;
/*  962 */     if (info.haveNozzle() || info.haveRotor() || info.isEnableVtol) {
/*  963 */       nozzle = new MCH_Parts((Entity)this, 1, PART_STAT, "Nozzle");
/*  964 */       nozzle.rotationMax = 90.0F;
/*  965 */       nozzle.rotationInv = 1.5F;
/*  966 */       nozzle.soundStartSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
/*  967 */       nozzle.soundEndSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
/*  968 */       nozzle.soundStartSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
/*  969 */       nozzle.soundEndSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
/*  970 */       nozzle.soundSwitching.setPrm("plane_cv", 1.0F, 0.5F);
/*  971 */       if (info.isDefaultVtol) {
/*  972 */         nozzle.forceSwitch(true);
/*      */       }
/*      */     } 
/*      */     
/*  976 */     return nozzle;
/*      */   }
/*      */   
/*      */   protected MCH_Parts createWing(MCP_PlaneInfo info) {
/*  980 */     MCH_Parts wing = null;
/*  981 */     if (this.planeInfo.haveWing()) {
/*  982 */       wing = new MCH_Parts((Entity)this, 3, PART_STAT, "Wing");
/*  983 */       wing.rotationMax = 90.0F;
/*  984 */       wing.rotationInv = 2.5F;
/*  985 */       wing.soundStartSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
/*  986 */       wing.soundEndSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
/*  987 */       wing.soundStartSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
/*  988 */       wing.soundEndSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
/*      */     } 
/*      */     
/*  991 */     return wing;
/*      */   }
/*      */   
/*      */   public boolean canUseWing() {
/*  995 */     if (this.partWing == null)
/*  996 */       return true; 
/*  997 */     if ((getPlaneInfo()).isVariableSweepWing) {
/*  998 */       return (getCurrentThrottle() >= 0.2D || this.partWing.isOFF());
/*      */     }
/* 1000 */     return this.partWing.isOFF();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canFoldWing() {
/* 1005 */     if (this.partWing != null && getModeSwitchCooldown() <= 0) {
/* 1006 */       if ((getPlaneInfo()).isVariableSweepWing) {
/* 1007 */         if (!this.field_70122_E && MCH_Lib.getBlockIdY((Entity)this, 3, -20) == 0) {
/* 1008 */           if (getCurrentThrottle() < 0.699999988079071D) {
/* 1009 */             return false;
/*      */           }
/* 1011 */         } else if (getCurrentThrottle() > 0.10000000149011612D) {
/* 1012 */           return false;
/*      */         } 
/*      */       } else {
/* 1015 */         if (!this.field_70122_E && MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
/* 1016 */           return false;
/*      */         }
/*      */         
/* 1019 */         if (getCurrentThrottle() > 0.009999999776482582D) {
/* 1020 */           return false;
/*      */         }
/*      */       } 
/*      */       
/* 1024 */       return this.partWing.isOFF();
/*      */     } 
/* 1026 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canUnfoldWing() {
/* 1031 */     return (this.partWing != null && getModeSwitchCooldown() <= 0 && this.partWing.isON());
/*      */   }
/*      */   
/*      */   public void foldWing(boolean fold) {
/* 1035 */     if (this.partWing != null && getModeSwitchCooldown() <= 0) {
/* 1036 */       this.partWing.setStatusServer(fold);
/* 1037 */       setModeSwitchCooldown(20);
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getWingRotation() {
/* 1042 */     return (this.partWing != null) ? this.partWing.rotation : 0.0F;
/*      */   }
/*      */   
/*      */   public float getPrevWingRotation() {
/* 1046 */     return (this.partWing != null) ? this.partWing.prevRotation : 0.0F;
/*      */   }
/*      */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\plane\MCP_EntityPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */