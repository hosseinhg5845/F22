/*     */ package com.norwood.mcheli.gltd;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Camera;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.helper.entity.IEntitySinglePassenger;
/*     */ import com.norwood.mcheli.multiplay.MCH_Multiplay;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponCAS;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponInfo;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponInfoManager;
/*     */ import com.norwood.mcheli.wrapper.W_Block;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityGLTD extends W_Entity implements IEntitySinglePassenger {
/*     */   public static final float Y_OFFSET = 0.25F;
/*  37 */   private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.func_187226_a(MCH_EntityGLTD.class, DataSerializers.field_187192_b);
/*  38 */   private static final DataParameter<Integer> FORWARD_DIR = EntityDataManager.func_187226_a(MCH_EntityGLTD.class, DataSerializers.field_187192_b);
/*  39 */   private static final DataParameter<Integer> DAMAGE_TAKEN = EntityDataManager.func_187226_a(MCH_EntityGLTD.class, DataSerializers.field_187192_b);
/*     */   public final MCH_Camera camera;
/*     */   public final MCH_WeaponCAS weaponCAS;
/*     */   public boolean zoomDir;
/*     */   public int countWait;
/*     */   public boolean isUsedPlayer;
/*     */   public float renderRotaionYaw;
/*     */   public float renderRotaionPitch;
/*     */   public int retryRiddenByEntityCheck;
/*     */   public Entity lastRiddenByEntity;
/*     */   private boolean isBoatEmpty = true;
/*  50 */   private double speedMultiplier = 0.07D;
/*     */   private int gltdPosRotInc;
/*     */   private double gltdX;
/*     */   private double gltdY;
/*     */   private double gltdZ;
/*     */   private double gltdYaw;
/*     */   private double gltdPitch;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityY;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityZ;
/*     */   
/*     */   public MCH_EntityGLTD(World world) {
/*  65 */     super(world);
/*  66 */     this.field_70156_m = true;
/*  67 */     func_70105_a(0.5F, 0.5F);
/*  68 */     this.camera = new MCH_Camera(world, (Entity)this);
/*  69 */     MCH_WeaponInfo wi = MCH_WeaponInfoManager.get("a10gau8");
/*  70 */     this.weaponCAS = new MCH_WeaponCAS(world, Vec3d.field_186680_a, 0.0F, 0.0F, "a10gau8", wi);
/*  71 */     this.weaponCAS.interval += (this.weaponCAS.interval > 0) ? 150 : 65386;
/*  72 */     this.weaponCAS.displayName = "A-10 GAU-8 Avenger";
/*  73 */     this.field_70158_ak = true;
/*  74 */     this.countWait = 0;
/*  75 */     this.retryRiddenByEntityCheck = 0;
/*  76 */     this.lastRiddenByEntity = null;
/*  77 */     this.isUsedPlayer = false;
/*  78 */     this.renderRotaionYaw = 0.0F;
/*  79 */     this.renderRotaionYaw = 0.0F;
/*  80 */     this.renderRotaionPitch = 0.0F;
/*  81 */     this.zoomDir = true;
/*  82 */     this._renderDistanceWeight = 2.0D;
/*     */   }
/*     */   
/*     */   public MCH_EntityGLTD(World par1World, double x, double y, double z) {
/*  86 */     this(par1World);
/*  87 */     func_70107_b(x, y, z);
/*  88 */     this.field_70159_w = 0.0D;
/*  89 */     this.field_70181_x = 0.0D;
/*  90 */     this.field_70179_y = 0.0D;
/*  91 */     this.field_70169_q = x;
/*  92 */     this.field_70167_r = y;
/*  93 */     this.field_70166_s = z;
/*  94 */     this.camera.setPosition(x, y, z);
/*     */   }
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/* 103 */     this.field_70180_af.func_187214_a(TIME_SINCE_HIT, Integer.valueOf(0));
/* 104 */     this.field_70180_af.func_187214_a(FORWARD_DIR, Integer.valueOf(1));
/* 105 */     this.field_70180_af.func_187214_a(DAMAGE_TAKEN, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/* 109 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/* 113 */     return func_174813_aQ();
/*     */   }
/*     */   
/*     */   public double func_70042_X() {
/* 117 */     return this.field_70131_O * 0.0D - 0.3D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource ds, float damage) {
/* 122 */     if (func_180431_b(ds))
/* 123 */       return false; 
/* 124 */     if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
/* 125 */       damage = MCH_Config.applyDamageByExternal((Entity)this, ds, damage);
/* 126 */       if (!MCH_Multiplay.canAttackEntity(ds, (Entity)this)) {
/* 127 */         return false;
/*     */       }
/* 129 */       setForwardDirection(-getForwardDirection());
/* 130 */       setTimeSinceHit(10);
/* 131 */       setDamageTaken((int)(getDamageTaken() + damage * 100.0F));
/* 132 */       func_70018_K();
/* 133 */       boolean flag = (ds.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)ds.func_76346_g()).field_71075_bZ.field_75098_d);
/* 134 */       if (flag || getDamageTaken() > 40.0F) {
/* 135 */         Entity riddenByEntity = getRiddenByEntity();
/* 136 */         this.camera.initCamera(0, riddenByEntity);
/* 137 */         if (riddenByEntity != null) {
/* 138 */           riddenByEntity.func_184220_m((Entity)this);
/*     */         }
/*     */         
/* 141 */         if (!flag) {
/* 142 */           func_145778_a((Item)MCH_MOD.itemGLTD, 1, 0.0F);
/*     */         }
/*     */         
/* 145 */         W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "hit", 1.0F, 1.0F);
/* 146 */         func_70106_y();
/*     */       } 
/*     */       
/* 149 */       return true;
/*     */     } 
/*     */     
/* 152 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70057_ab() {}
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 161 */     return !this.field_70128_L;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
/* 166 */     if (this.isBoatEmpty) {
/* 167 */       this.gltdPosRotInc = par9 + 5;
/*     */     } else {
/* 169 */       double x = par1 - this.field_70165_t;
/* 170 */       double y = par3 - this.field_70163_u;
/* 171 */       double z = par5 - this.field_70161_v;
/* 172 */       if (x * x + y * y + z * z <= 1.0D) {
/*     */         return;
/*     */       }
/*     */       
/* 176 */       this.gltdPosRotInc = 3;
/*     */     } 
/*     */     
/* 179 */     this.gltdX = par1;
/* 180 */     this.gltdY = par3;
/* 181 */     this.gltdZ = par5;
/* 182 */     this.gltdYaw = par7;
/* 183 */     this.gltdPitch = par8;
/* 184 */     this.field_70159_w = this.velocityX;
/* 185 */     this.field_70181_x = this.velocityY;
/* 186 */     this.field_70179_y = this.velocityZ;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double x, double y, double z) {
/* 191 */     this.velocityX = this.field_70159_w = x;
/* 192 */     this.velocityY = this.field_70181_x = y;
/* 193 */     this.velocityZ = this.field_70179_y = z;
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/* 197 */     super.func_70071_h_();
/* 198 */     if (getTimeSinceHit() > 0) {
/* 199 */       setTimeSinceHit(getTimeSinceHit() - 1);
/*     */     }
/*     */     
/* 202 */     if (getDamageTaken() > 0.0F) {
/* 203 */       setDamageTaken(getDamageTaken() - 1);
/*     */     }
/*     */     
/* 206 */     this.field_70169_q = this.field_70165_t;
/* 207 */     this.field_70167_r = this.field_70163_u;
/* 208 */     this.field_70166_s = this.field_70161_v;
/* 209 */     double d3 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 210 */     Entity riddenByEntity = getRiddenByEntity();
/* 211 */     if (riddenByEntity != null) {
/* 212 */       this.camera.updateViewer(0, riddenByEntity);
/*     */     }
/*     */     
/* 215 */     if (!this.field_70170_p.field_72995_K || !this.isBoatEmpty) {
/* 216 */       this.field_70181_x -= 0.04D;
/* 217 */       double d4 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 218 */       if (d4 > 0.35D) {
/* 219 */         double d = 0.35D / d4;
/* 220 */         this.field_70159_w *= d;
/* 221 */         this.field_70179_y *= d;
/* 222 */         d4 = 0.35D;
/*     */       } 
/*     */       
/* 225 */       if (d4 > d3 && this.speedMultiplier < 0.35D) {
/* 226 */         this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
/* 227 */         if (this.speedMultiplier > 0.35D) {
/* 228 */           this.speedMultiplier = 0.35D;
/*     */         }
/*     */       } else {
/* 231 */         this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
/* 232 */         if (this.speedMultiplier < 0.07D) {
/* 233 */           this.speedMultiplier = 0.07D;
/*     */         }
/*     */       } 
/*     */       
/* 237 */       if (this.field_70122_E) {
/* 238 */         this.field_70159_w *= 0.5D;
/* 239 */         this.field_70181_x *= 0.5D;
/* 240 */         this.field_70179_y *= 0.5D;
/*     */       } 
/*     */       
/* 243 */       func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 244 */       this.field_70159_w *= 0.99D;
/* 245 */       this.field_70181_x *= 0.95D;
/* 246 */       this.field_70179_y *= 0.99D;
/* 247 */       this.field_70125_A = 0.0F;
/* 248 */       double d5 = this.field_70177_z;
/* 249 */       double d11 = this.field_70169_q - this.field_70165_t;
/* 250 */       double d10 = this.field_70166_s - this.field_70161_v;
/* 251 */       if (d11 * d11 + d10 * d10 > 0.001D) {
/* 252 */         d5 = (float)(Math.atan2(d10, d11) * 180.0D / Math.PI);
/*     */       }
/*     */       
/* 255 */       double d12 = MathHelper.func_76138_g(d5 - this.field_70177_z);
/* 256 */       if (d12 > 20.0D) {
/* 257 */         d12 = 20.0D;
/*     */       }
/*     */       
/* 260 */       if (d12 < -20.0D) {
/* 261 */         d12 = -20.0D;
/*     */       }
/*     */       
/* 264 */       this.field_70177_z = (float)(this.field_70177_z + d12);
/* 265 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/* 266 */       if (!this.field_70170_p.field_72995_K) {
/* 267 */         if (MCH_Config.Collision_DestroyBlock.prmBool) {
/* 268 */           for (int l = 0; l < 4; l++) {
/* 269 */             int i1 = MathHelper.func_76128_c(this.field_70165_t + ((l % 2) - 0.5D) * 0.8D);
/* 270 */             int j1 = MathHelper.func_76128_c(this.field_70161_v + (l / 2.0D - 0.5D) * 0.8D);
/*     */             
/* 272 */             for (int k1 = 0; k1 < 2; k1++) {
/* 273 */               int l1 = MathHelper.func_76128_c(this.field_70163_u) + k1;
/* 274 */               if (W_WorldFunc.isEqualBlock(this.field_70170_p, i1, l1, j1, W_Block.getSnowLayer())) {
/* 275 */                 this.field_70170_p.func_175698_g(new BlockPos(i1, l1, j1));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 281 */         riddenByEntity = getRiddenByEntity();
/* 282 */         if (riddenByEntity != null && riddenByEntity.field_70128_L) {
/* 283 */           riddenByEntity.func_184210_p();
/*     */         }
/*     */       } 
/* 286 */     } else if (this.gltdPosRotInc > 0) {
/* 287 */       double d4x = this.field_70165_t + (this.gltdX - this.field_70165_t) / this.gltdPosRotInc;
/* 288 */       double d5x = this.field_70163_u + (this.gltdY - this.field_70163_u) / this.gltdPosRotInc;
/* 289 */       double d11x = this.field_70161_v + (this.gltdZ - this.field_70161_v) / this.gltdPosRotInc;
/* 290 */       double d10x = MathHelper.func_76138_g(this.gltdYaw - this.field_70177_z);
/* 291 */       this.field_70177_z = (float)(this.field_70177_z + d10x / this.gltdPosRotInc);
/* 292 */       this.field_70125_A = (float)(this.field_70125_A + (this.gltdPitch - this.field_70125_A) / this.gltdPosRotInc);
/* 293 */       this.gltdPosRotInc--;
/* 294 */       func_70107_b(d4x, d5x, d11x);
/* 295 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */     } else {
/* 297 */       double d4x = this.field_70165_t + this.field_70159_w;
/* 298 */       double d5x = this.field_70163_u + this.field_70181_x;
/* 299 */       double d11x = this.field_70161_v + this.field_70179_y;
/* 300 */       func_70107_b(d4x, d5x, d11x);
/* 301 */       if (this.field_70122_E) {
/* 302 */         this.field_70159_w *= 0.5D;
/* 303 */         this.field_70181_x *= 0.5D;
/* 304 */         this.field_70179_y *= 0.5D;
/*     */       } 
/*     */       
/* 307 */       this.field_70159_w *= 0.99D;
/* 308 */       this.field_70181_x *= 0.95D;
/* 309 */       this.field_70179_y *= 0.99D;
/*     */     } 
/*     */     
/* 312 */     updateCameraPosition(false);
/* 313 */     if (this.countWait > 0) {
/* 314 */       this.countWait--;
/*     */     }
/*     */     
/* 317 */     if (this.countWait < 0) {
/* 318 */       this.countWait++;
/*     */     }
/*     */     
/* 321 */     this.weaponCAS.update(this.countWait);
/* 322 */     riddenByEntity = getRiddenByEntity();
/* 323 */     if (this.lastRiddenByEntity == null || riddenByEntity != null) {
/* 324 */       this.retryRiddenByEntityCheck = 0;
/* 325 */     } else if (this.retryRiddenByEntityCheck < 3) {
/* 326 */       this.retryRiddenByEntityCheck++;
/* 327 */       setUnmoundPosition(this.lastRiddenByEntity);
/*     */     } else {
/* 329 */       unmountEntity();
/*     */     } 
/*     */     
/* 332 */     riddenByEntity = getRiddenByEntity();
/* 333 */     if (riddenByEntity != null) {
/* 334 */       this.lastRiddenByEntity = riddenByEntity;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setUnmoundPosition(Entity e) {
/* 339 */     if (e != null) {
/* 340 */       float yaw = this.field_70177_z;
/* 341 */       double d0 = Math.sin(yaw * Math.PI / 180.0D) * 1.2D;
/* 342 */       double d1 = -Math.cos(yaw * Math.PI / 180.0D) * 1.2D;
/* 343 */       e.func_70107_b(this.field_70165_t + d0, this.field_70163_u + func_70042_X() + e.func_70033_W() + 1.0D, this.field_70161_v + d1);
/* 344 */       e.field_70142_S = e.field_70169_q = e.field_70165_t;
/* 345 */       e.field_70137_T = e.field_70167_r = e.field_70163_u;
/* 346 */       e.field_70136_U = e.field_70166_s = e.field_70161_v;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unmountEntity() {
/* 351 */     this.camera.setMode(0, 0);
/* 352 */     this.camera.setCameraZoom(1.0F);
/* 353 */     if (!this.field_70170_p.field_72995_K) {
/* 354 */       Entity riddenByEntity = getRiddenByEntity();
/* 355 */       if (riddenByEntity != null) {
/* 356 */         if (!riddenByEntity.field_70128_L) {
/* 357 */           riddenByEntity.func_184210_p();
/*     */         }
/* 359 */       } else if (this.lastRiddenByEntity != null && !this.lastRiddenByEntity.field_70128_L) {
/* 360 */         this.camera.updateViewer(0, this.lastRiddenByEntity);
/* 361 */         setUnmoundPosition(this.lastRiddenByEntity);
/*     */       } 
/*     */     } 
/*     */     
/* 365 */     this.lastRiddenByEntity = null;
/*     */   }
/*     */   
/*     */   public void updateCameraPosition(boolean foreceUpdate) {
/* 369 */     Entity riddenByEntity = getRiddenByEntity();
/* 370 */     if (foreceUpdate || (riddenByEntity != null && this.camera != null)) {
/* 371 */       double x = -Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.6D;
/* 372 */       double z = Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.6D;
/* 373 */       this.camera.setPosition(this.field_70165_t + x, this.field_70163_u + 0.7D, this.field_70161_v + z);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void zoomCamera(float f) {
/* 379 */     float z = this.camera.getCameraZoom();
/* 380 */     z += f;
/* 381 */     if (z < 1.0F) {
/* 382 */       z = 1.0F;
/*     */     }
/*     */     
/* 385 */     if (z > 10.0F) {
/* 386 */       z = 10.0F;
/*     */     }
/*     */     
/* 389 */     this.camera.setCameraZoom(z);
/*     */   }
/*     */   
/*     */   public void func_184232_k(@NotNull Entity passenger) {
/* 393 */     if (func_184196_w(passenger)) {
/* 394 */       double x = Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.5D;
/* 395 */       double z = -Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.5D;
/* 396 */       passenger.func_70107_b(this.field_70165_t + x, this.field_70163_u + func_70042_X() + passenger.func_70033_W(), this.field_70161_v + z);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void switchWeapon(int id) {}
/*     */   
/*     */   public boolean useCurrentWeapon(int option1, int option2) {
/* 404 */     Entity riddenByEntity = getRiddenByEntity();
/* 405 */     if (this.countWait == 0 && riddenByEntity != null && this.weaponCAS
/*     */       
/* 407 */       .shot(riddenByEntity, this.camera.posX, this.camera.posY, this.camera.posZ, option1, option2)) {
/* 408 */       this.countWait = this.weaponCAS.interval;
/* 409 */       if (this.field_70170_p.field_72995_K) {
/* 410 */         this.countWait += (this.countWait > 0) ? 10 : -10;
/*     */       } else {
/* 412 */         W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "gltd", 0.5F, 1.0F);
/*     */       } 
/*     */       
/* 415 */       return true;
/*     */     } 
/* 417 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(@NotNull NBTTagCompound par1NBTTagCompound) {}
/*     */ 
/*     */   
/*     */   protected void func_70037_a(@NotNull NBTTagCompound par1NBTTagCompound) {}
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 429 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean func_184230_a(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 433 */     Entity riddenByEntity = getRiddenByEntity();
/* 434 */     if (!(riddenByEntity instanceof EntityPlayer) || riddenByEntity == player) {
/* 435 */       player.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
/* 436 */       player.field_70125_A = MathHelper.func_76142_g(this.field_70125_A);
/* 437 */       if (!this.field_70170_p.field_72995_K) {
/* 438 */         player.func_184220_m((Entity)this);
/*     */       } else {
/* 440 */         this.zoomDir = true;
/* 441 */         this.camera.setCameraZoom(1.0F);
/* 442 */         if (this.countWait > 0) {
/* 443 */           this.countWait = -this.countWait;
/*     */         }
/*     */         
/* 446 */         if (this.countWait > -60) {
/* 447 */           this.countWait = -60;
/*     */         }
/*     */       } 
/*     */       
/* 451 */       updateCameraPosition(true);
/*     */     } 
/* 453 */     return true;
/*     */   }
/*     */   
/*     */   public int getDamageTaken() {
/* 457 */     return ((Integer)this.field_70180_af.func_187225_a(DAMAGE_TAKEN)).intValue();
/*     */   }
/*     */   
/*     */   public void setDamageTaken(int par1) {
/* 461 */     this.field_70180_af.func_187227_b(DAMAGE_TAKEN, Integer.valueOf(par1));
/*     */   }
/*     */   
/*     */   public int getTimeSinceHit() {
/* 465 */     return ((Integer)this.field_70180_af.func_187225_a(TIME_SINCE_HIT)).intValue();
/*     */   }
/*     */   
/*     */   public void setTimeSinceHit(int par1) {
/* 469 */     this.field_70180_af.func_187227_b(TIME_SINCE_HIT, Integer.valueOf(par1));
/*     */   }
/*     */   
/*     */   public int getForwardDirection() {
/* 473 */     return 0;
/*     */   }
/*     */   
/*     */   public void setForwardDirection(int par1) {
/* 477 */     this.field_70180_af.func_187227_b(FORWARD_DIR, Integer.valueOf(par1));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setIsBoatEmpty(boolean par1) {
/* 482 */     this.isBoatEmpty = par1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 488 */     List<Entity> passengers = func_184188_bt();
/* 489 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gltd\MCH_EntityGLTD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */