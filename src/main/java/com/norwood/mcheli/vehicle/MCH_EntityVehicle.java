/*     */ package com.norwood.mcheli.vehicle;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_PacketStatusRequest;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponParam;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponSet;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class MCH_EntityVehicle
/*     */   extends MCH_EntityAircraft {
/*     */   public boolean isUsedPlayer;
/*     */   public float lastRiderYaw;
/*     */   public float lastRiderPitch;
/*     */   public double fixPosX;
/*     */   public double fixPosY;
/*     */   public double fixPosZ;
/*  32 */   private MCH_VehicleInfo vehicleInfo = null;
/*     */   
/*     */   public MCH_EntityVehicle(World world) {
/*  35 */     super(world);
/*  36 */     this.currentSpeed = 0.07D;
/*  37 */     this.field_70156_m = true;
/*  38 */     func_70105_a(2.0F, 0.7F);
/*  39 */     this.field_70159_w = 0.0D;
/*  40 */     this.field_70181_x = 0.0D;
/*  41 */     this.field_70179_y = 0.0D;
/*  42 */     this.isUsedPlayer = false;
/*  43 */     this.lastRiderYaw = 0.0F;
/*  44 */     this.lastRiderPitch = 0.0F;
/*  45 */     this.weapons = createWeapon(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKindName() {
/*  50 */     return "vehicles";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEntityType() {
/*  55 */     return "Vehicle";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MCH_VehicleInfo getVehicleInfo() {
/*  60 */     return this.vehicleInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public void changeType(String type) {
/*  65 */     MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityVehicle.changeType " + type + " : " + this, new Object[0]);
/*  66 */     if (!type.isEmpty()) {
/*  67 */       this.vehicleInfo = MCH_VehicleInfoManager.get(type);
/*     */     }
/*     */     
/*  70 */     if (this.vehicleInfo == null) {
/*  71 */       MCH_Lib.Log((Entity)this, "##### MCH_EntityVehicle changeVehicleType() Vehicle info null %d, %s, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName() });
/*  72 */       func_70106_y();
/*     */     } else {
/*  74 */       setAcInfo(this.vehicleInfo);
/*  75 */       newSeats(getAcInfo().getNumSeatAndRack());
/*  76 */       this.weapons = createWeapon(1 + getSeatNum());
/*  77 */       initPartRotation(this.field_70177_z, this.field_70125_A);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMountWithNearEmptyMinecart() {
/*  83 */     return MCH_Config.MountMinecartVehicle.prmBool;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  88 */     super.func_70088_a();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/*  93 */     super.func_70014_b(par1NBTTagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/*  98 */     super.func_70037_a(par1NBTTagCompound);
/*  99 */     if (this.vehicleInfo == null) {
/* 100 */       this.vehicleInfo = MCH_VehicleInfoManager.get(getTypeName());
/* 101 */       if (this.vehicleInfo == null) {
/* 102 */         MCH_Lib.Log((Entity)this, "##### MCH_EntityVehicle readEntityFromNBT() Vehicle info null %d, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName() });
/* 103 */         func_70106_y();
/*     */       } else {
/* 105 */         setAcInfo(this.vehicleInfo);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem() {
/* 112 */     return (getVehicleInfo() != null) ? (Item)(getVehicleInfo()).item : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 117 */     super.func_70106_y();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSoundVolume() {
/* 122 */     return (float)getCurrentThrottle() * 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSoundPitch() {
/* 127 */     return (float)(getCurrentThrottle() * 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefaultSoundName() {
/* 132 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void zoomCamera() {
/* 138 */     if (canZoom()) {
/* 139 */       float z = this.camera.getCameraZoom();
/* 140 */       z++;
/* 141 */       this.camera.setCameraZoom((z <= getZoomMax() + 0.01D) ? z : 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void _updateCameraRotate(float yaw, float pitch) {
/* 146 */     this.camera.prevRotationYaw = this.camera.rotationYaw;
/* 147 */     this.camera.prevRotationPitch = this.camera.rotationPitch;
/* 148 */     if (pitch > 89.0F) {
/* 149 */       pitch = 89.0F;
/*     */     }
/*     */     
/* 152 */     if (pitch < -89.0F) {
/* 153 */       pitch = -89.0F;
/*     */     }
/*     */     
/* 156 */     this.camera.rotationYaw = yaw;
/* 157 */     this.camera.rotationPitch = pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCameraView(Entity entity) {
/* 162 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useCurrentWeapon(MCH_WeaponParam prm) {
/* 167 */     if (prm.user != null) {
/* 168 */       MCH_WeaponSet currentWs = getCurrentWeapon(prm.user);
/* 169 */       if (currentWs != null) {
/* 170 */         MCH_AircraftInfo.Weapon w = getAcInfo().getWeaponByName((currentWs.getInfo()).name);
/* 171 */         if (w != null && w.maxYaw != 0.0F && w.minYaw != 0.0F) {
/* 172 */           return super.useCurrentWeapon(prm);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     float breforeUseWeaponPitch = this.field_70125_A;
/* 178 */     float breforeUseWeaponYaw = this.field_70177_z;
/* 179 */     this.field_70125_A = prm.user.field_70125_A;
/* 180 */     this.field_70177_z = prm.user.field_70177_z;
/* 181 */     boolean result = super.useCurrentWeapon(prm);
/* 182 */     this.field_70125_A = breforeUseWeaponPitch;
/* 183 */     this.field_70177_z = breforeUseWeaponYaw;
/* 184 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mountWithNearEmptyMinecart() {
/* 189 */     if (!MCH_Config.FixVehicleAtPlacedPoint.prmBool) {
/* 190 */       super.mountWithNearEmptyMinecart();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateAircraft() {
/* 196 */     if (this.vehicleInfo == null) {
/* 197 */       changeType(getTypeName());
/* 198 */       this.field_70169_q = this.field_70165_t;
/* 199 */       this.field_70167_r = this.field_70163_u;
/* 200 */       this.field_70166_s = this.field_70161_v;
/*     */     } else {
/* 202 */       if (this.field_70173_aa >= 200 && MCH_Config.FixVehicleAtPlacedPoint.prmBool) {
/* 203 */         func_184210_p();
/* 204 */         this.field_70159_w = 0.0D;
/* 205 */         this.field_70181_x = 0.0D;
/* 206 */         this.field_70179_y = 0.0D;
/* 207 */         if (this.field_70170_p.field_72995_K && this.field_70173_aa % 4 == 0) {
/* 208 */           this.fixPosY = this.field_70163_u;
/*     */         }
/*     */         
/* 211 */         func_70107_b((this.field_70165_t + this.fixPosX) / 2.0D, (this.field_70163_u + this.fixPosY) / 2.0D, (this.field_70161_v + this.fixPosZ) / 2.0D);
/*     */       } else {
/* 213 */         this.fixPosX = this.field_70165_t;
/* 214 */         this.fixPosY = this.field_70163_u;
/* 215 */         this.fixPosZ = this.field_70161_v;
/*     */       } 
/*     */       
/* 218 */       if (!this.isRequestedSyncStatus) {
/* 219 */         this.isRequestedSyncStatus = true;
/* 220 */         if (this.field_70170_p.field_72995_K) {
/* 221 */           MCH_PacketStatusRequest.requestStatus(this);
/*     */         }
/*     */       } 
/*     */       
/* 225 */       if (this.lastRiddenByEntity == null && getRiddenByEntity() != null) {
/* 226 */         (getRiddenByEntity()).field_70125_A = 0.0F;
/* 227 */         (getRiddenByEntity()).field_70127_C = 0.0F;
/* 228 */         initCurrentWeapon(getRiddenByEntity());
/*     */       } 
/*     */       
/* 231 */       updateWeapons();
/* 232 */       onUpdate_Seats();
/* 233 */       onUpdate_Control();
/* 234 */       this.field_70169_q = this.field_70165_t;
/* 235 */       this.field_70167_r = this.field_70163_u;
/* 236 */       this.field_70166_s = this.field_70161_v;
/* 237 */       if (func_70090_H()) {
/* 238 */         this.field_70125_A *= 0.9F;
/*     */       }
/*     */       
/* 241 */       if (this.field_70170_p.field_72995_K) {
/* 242 */         onUpdate_Client();
/*     */       } else {
/* 244 */         onUpdate_Server();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onUpdate_Control() {
/* 250 */     if (getRiddenByEntity() == null || (getRiddenByEntity()).field_70128_L) {
/* 251 */       if (getCurrentThrottle() > 0.0D) {
/* 252 */         addCurrentThrottle(-0.00125D);
/*     */       } else {
/* 254 */         setCurrentThrottle(0.0D);
/*     */       } 
/* 256 */     } else if ((getVehicleInfo()).isEnableMove || (getVehicleInfo()).isEnableRot) {
/* 257 */       onUpdate_ControlOnGround();
/*     */     } 
/*     */     
/* 260 */     if (getCurrentThrottle() < 0.0D) {
/* 261 */       setCurrentThrottle(0.0D);
/*     */     }
/*     */     
/* 264 */     if (this.field_70170_p.field_72995_K) {
/* 265 */       if (!W_Lib.isClientPlayer(getRiddenByEntity())) {
/* 266 */         double ct = getThrottle();
/* 267 */         if (getCurrentThrottle() > ct) {
/* 268 */           addCurrentThrottle(-0.005D);
/*     */         }
/*     */         
/* 271 */         if (getCurrentThrottle() < ct) {
/* 272 */           addCurrentThrottle(0.005D);
/*     */         }
/*     */       } 
/*     */     } else {
/* 276 */       setThrottle(getCurrentThrottle());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onUpdate_ControlOnGround() {
/* 281 */     if (!this.field_70170_p.field_72995_K) {
/* 282 */       boolean move = false;
/*     */       
/* 284 */       double x = 0.0D;
/* 285 */       double z = 0.0D;
/* 286 */       if ((getVehicleInfo()).isEnableMove) {
/* 287 */         if (this.throttleUp) {
/* 288 */           float yaw = this.field_70177_z;
/* 289 */           x += Math.sin(yaw * Math.PI / 180.0D);
/* 290 */           z += Math.cos(yaw * Math.PI / 180.0D);
/* 291 */           move = true;
/*     */         } 
/*     */         
/* 294 */         if (this.throttleDown) {
/* 295 */           float yaw = this.field_70177_z - 180.0F;
/* 296 */           x += Math.sin(yaw * Math.PI / 180.0D);
/* 297 */           z += Math.cos(yaw * Math.PI / 180.0D);
/* 298 */           move = true;
/*     */         } 
/*     */       } 
/*     */       
/* 302 */       if ((getVehicleInfo()).isEnableMove) {
/* 303 */         if (this.moveLeft && !this.moveRight) {
/* 304 */           this.field_70177_z = (float)(this.field_70177_z - 0.5D);
/*     */         }
/*     */         
/* 307 */         if (this.moveRight && !this.moveLeft) {
/* 308 */           this.field_70177_z = (float)(this.field_70177_z + 0.5D);
/*     */         }
/*     */       } 
/*     */       
/* 312 */       if (move) {
/* 313 */         double d = Math.sqrt(x * x + z * z);
/* 314 */         this.field_70159_w -= x / d * 0.029999999329447746D;
/* 315 */         this.field_70179_y += z / d * 0.029999999329447746D;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onUpdate_Particle() {
/* 321 */     double particlePosY = this.field_70163_u;
/* 322 */     boolean b = false;
/*     */     
/*     */     int y;
/* 325 */     for (y = 0; y < 5 && !b; y++) {
/* 326 */       int x; for (x = -1; x <= 1; x++) {
/* 327 */         for (int z = -1; z <= 1; z++) {
/* 328 */           int block = W_WorldFunc.getBlockId(this.field_70170_p, (int)(this.field_70165_t + 0.5D) + x, (int)(this.field_70163_u + 0.5D) - y, (int)(this.field_70161_v + 0.5D) + z);
/* 329 */           if (block != 0 && !b) {
/* 330 */             particlePosY = ((int)(this.field_70163_u + 1.0D) - y);
/* 331 */             b = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 336 */       for (x = -3; b && x <= 3; x++) {
/* 337 */         for (int zx = -3; zx <= 3; zx++) {
/* 338 */           if (W_WorldFunc.isBlockWater(this.field_70170_p, (int)(this.field_70165_t + 0.5D) + x, (int)(this.field_70163_u + 0.5D) - y, (int)(this.field_70161_v + 0.5D) + zx)) {
/* 339 */             for (int i = 0; i < 7.0D * getCurrentThrottle(); i++) {
/* 340 */               this.field_70170_p
/* 341 */                 .func_175688_a(EnumParticleTypes.WATER_SPLASH, this.field_70165_t + 0.5D + x + (this.field_70146_Z
/*     */                   
/* 343 */                   .nextDouble() - 0.5D) * 2.0D, particlePosY + this.field_70146_Z
/* 344 */                   .nextDouble(), this.field_70161_v + 0.5D + zx + (this.field_70146_Z
/* 345 */                   .nextDouble() - 0.5D) * 2.0D, x + (this.field_70146_Z
/* 346 */                   .nextDouble() - 0.5D) * 2.0D, -0.3D, zx + (this.field_70146_Z
/*     */                   
/* 348 */                   .nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */             }
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 356 */     double pn = (5 - y + 1) / 5.0D;
/* 357 */     if (b) {
/* 358 */       for (int k = 0; k < (int)(getCurrentThrottle() * 6.0D * pn); k++) {
/* 359 */         this.field_70170_p
/* 360 */           .func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + this.field_70146_Z
/*     */             
/* 362 */             .nextDouble() - 0.5D, particlePosY + this.field_70146_Z
/* 363 */             .nextDouble() - 0.5D, this.field_70161_v + this.field_70146_Z
/* 364 */             .nextDouble() - 0.5D, (this.field_70146_Z
/* 365 */             .nextDouble() - 0.5D) * 2.0D, -0.4D, (this.field_70146_Z
/*     */             
/* 367 */             .nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUpdate_Client() {
/* 374 */     updateCameraViewers();
/* 375 */     if (getRiddenByEntity() != null && W_Lib.isClientPlayer(getRiddenByEntity())) {
/* 376 */       (getRiddenByEntity()).field_70125_A = (getRiddenByEntity()).field_70127_C;
/*     */     }
/*     */     
/* 379 */     if (this.aircraftPosRotInc > 0) {
/* 380 */       double rpinc = this.aircraftPosRotInc;
/* 381 */       double yaw = MathHelper.func_76138_g(this.aircraftYaw - this.field_70177_z);
/* 382 */       this.field_70177_z = (float)(this.field_70177_z + yaw / rpinc);
/* 383 */       this.field_70125_A = (float)(this.field_70125_A + (this.aircraftPitch - this.field_70125_A) / rpinc);
/* 384 */       func_70107_b(this.field_70165_t + (this.aircraftX - this.field_70165_t) / rpinc, this.field_70163_u + (this.aircraftY - this.field_70163_u) / rpinc, this.field_70161_v + (this.aircraftZ - this.field_70161_v) / rpinc);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 389 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/* 390 */       this.aircraftPosRotInc--;
/*     */     } else {
/* 392 */       func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 393 */       if (this.field_70122_E) {
/* 394 */         this.field_70159_w *= 0.95D;
/* 395 */         this.field_70179_y *= 0.95D;
/*     */       } 
/*     */       
/* 398 */       if (func_70090_H()) {
/* 399 */         this.field_70159_w *= 0.99D;
/* 400 */         this.field_70179_y *= 0.99D;
/*     */       } 
/*     */     } 
/*     */     
/* 404 */     if (getRiddenByEntity() != null);
/*     */ 
/*     */     
/* 407 */     updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */   }
/*     */   
/*     */   private void onUpdate_Server() {
/* 411 */     double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 412 */     updateCameraViewers();
/* 413 */     double dp = 0.0D;
/* 414 */     if (canFloatWater()) {
/* 415 */       dp = getWaterDepth();
/*     */     }
/*     */     
/* 418 */     if (dp == 0.0D) {
/* 419 */       this.field_70181_x += (!func_70090_H() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
/* 420 */     } else if (dp < 1.0D) {
/* 421 */       this.field_70181_x -= 1.0E-4D;
/* 422 */       this.field_70181_x += 0.007D * getCurrentThrottle();
/*     */     } else {
/* 424 */       if (this.field_70181_x < 0.0D) {
/* 425 */         this.field_70181_x /= 2.0D;
/*     */       }
/*     */       
/* 428 */       this.field_70181_x += 0.007D;
/*     */     } 
/*     */     
/* 431 */     double motion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 432 */     float speedLimit = (getAcInfo()).speed;
/* 433 */     if (motion > speedLimit) {
/* 434 */       this.field_70159_w *= speedLimit / motion;
/* 435 */       this.field_70179_y *= speedLimit / motion;
/* 436 */       motion = speedLimit;
/*     */     } 
/*     */     
/* 439 */     if (motion > prevMotion && this.currentSpeed < speedLimit) {
/* 440 */       this.currentSpeed += (speedLimit - this.currentSpeed) / 35.0D;
/* 441 */       if (this.currentSpeed > speedLimit) {
/* 442 */         this.currentSpeed = speedLimit;
/*     */       }
/*     */     } else {
/* 445 */       this.currentSpeed -= (this.currentSpeed - 0.07D) / 35.0D;
/* 446 */       if (this.currentSpeed < 0.07D) {
/* 447 */         this.currentSpeed = 0.07D;
/*     */       }
/*     */     } 
/*     */     
/* 451 */     if (this.field_70122_E) {
/* 452 */       this.field_70159_w *= 0.5D;
/* 453 */       this.field_70179_y *= 0.5D;
/*     */     } 
/*     */     
/* 456 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 457 */     this.field_70181_x *= 0.95D;
/* 458 */     this.field_70159_w *= 0.99D;
/* 459 */     this.field_70179_y *= 0.99D;
/* 460 */     onUpdate_updateBlock();
/* 461 */     if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L) {
/* 462 */       unmountEntity();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdateAngles(float partialTicks) {}
/*     */ 
/*     */   
/*     */   public boolean canSwitchFreeLook() {
/* 472 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\vehicle\MCH_EntityVehicle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */