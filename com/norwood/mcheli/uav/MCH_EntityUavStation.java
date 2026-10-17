/*     */ package com.norwood.mcheli.uav;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Explosion;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.helicopter.MCH_EntityHeli;
/*     */ import com.norwood.mcheli.helicopter.MCH_HeliInfo;
/*     */ import com.norwood.mcheli.helicopter.MCH_HeliInfoManager;
/*     */ import com.norwood.mcheli.helicopter.MCH_ItemHeli;
/*     */ import com.norwood.mcheli.helper.entity.IEntitySinglePassenger;
/*     */ import com.norwood.mcheli.helper.network.PooledGuiParameter;
/*     */ import com.norwood.mcheli.multiplay.MCH_Multiplay;
/*     */ import com.norwood.mcheli.plane.MCP_EntityPlane;
/*     */ import com.norwood.mcheli.plane.MCP_ItemPlane;
/*     */ import com.norwood.mcheli.plane.MCP_PlaneInfo;
/*     */ import com.norwood.mcheli.plane.MCP_PlaneInfoManager;
/*     */ import com.norwood.mcheli.tank.MCH_EntityTank;
/*     */ import com.norwood.mcheli.tank.MCH_ItemTank;
/*     */ import com.norwood.mcheli.tank.MCH_TankInfo;
/*     */ import com.norwood.mcheli.tank.MCH_TankInfoManager;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_EntityContainer;
/*     */ import com.norwood.mcheli.wrapper.W_EntityPlayer;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityUavStation extends W_EntityContainer implements IEntitySinglePassenger {
/*  49 */   private static final DataParameter<Byte> STATUS = EntityDataManager.func_187226_a(MCH_EntityUavStation.class, DataSerializers.field_187191_a); public static final float Y_OFFSET = 0.35F;
/*  50 */   private static final DataParameter<Integer> LAST_AC_ID = EntityDataManager.func_187226_a(MCH_EntityUavStation.class, DataSerializers.field_187192_b);
/*  51 */   private static final DataParameter<BlockPos> UAV_POS = EntityDataManager.func_187226_a(MCH_EntityUavStation.class, DataSerializers.field_187200_j);
/*     */   public boolean isRequestedSyncStatus;
/*     */   public int posUavX;
/*     */   public int posUavY;
/*     */   public int posUavZ;
/*     */   public float rotCover;
/*     */   public float prevRotCover;
/*     */   protected Entity lastRiddenByEntity;
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected double velocityX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected double velocityY;
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected double velocityZ;
/*     */   protected int aircraftPosRotInc;
/*     */   protected double aircraftX;
/*     */   protected double aircraftY;
/*     */   protected double aircraftZ;
/*     */   protected double aircraftYaw;
/*     */   protected double aircraftPitch;
/*     */   private MCH_EntityAircraft controlAircraft;
/*     */   private MCH_EntityAircraft lastControlAircraft;
/*     */   private String loadedLastControlAircraftGuid;
/*     */   
/*     */   public MCH_EntityUavStation(World world) {
/*  76 */     super(world);
/*  77 */     this.dropContentsWhenDead = false;
/*  78 */     this.field_70156_m = true;
/*  79 */     func_70105_a(2.0F, 0.7F);
/*  80 */     this.field_70159_w = 0.0D;
/*  81 */     this.field_70181_x = 0.0D;
/*  82 */     this.field_70179_y = 0.0D;
/*  83 */     this.field_70158_ak = true;
/*  84 */     this.lastRiddenByEntity = null;
/*  85 */     this.aircraftPosRotInc = 0;
/*  86 */     this.aircraftX = 0.0D;
/*  87 */     this.aircraftY = 0.0D;
/*  88 */     this.aircraftZ = 0.0D;
/*  89 */     this.aircraftYaw = 0.0D;
/*  90 */     this.aircraftPitch = 0.0D;
/*  91 */     this.posUavX = 0;
/*  92 */     this.posUavY = 0;
/*  93 */     this.posUavZ = 0;
/*  94 */     this.rotCover = 0.0F;
/*  95 */     this.prevRotCover = 0.0F;
/*  96 */     setControlAircract((MCH_EntityAircraft)null);
/*  97 */     setLastControlAircraft((MCH_EntityAircraft)null);
/*  98 */     this.loadedLastControlAircraftGuid = "";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/* 103 */     super.func_70088_a();
/* 104 */     this.field_70180_af.func_187214_a(STATUS, Byte.valueOf((byte)0));
/* 105 */     this.field_70180_af.func_187214_a(LAST_AC_ID, Integer.valueOf(0));
/* 106 */     this.field_70180_af.func_187214_a(UAV_POS, BlockPos.field_177992_a);
/* 107 */     setOpen(true);
/*     */   }
/*     */   
/*     */   public int getStatus() {
/* 111 */     return ((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue();
/*     */   }
/*     */   
/*     */   public void setStatus(int n) {
/* 115 */     if (!this.field_70170_p.field_72995_K) {
/* 116 */       MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityUavStation.setStatus(%d)", new Object[] { Integer.valueOf(n) });
/* 117 */       this.field_70180_af.func_187227_b(STATUS, Byte.valueOf((byte)n));
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getKind() {
/* 122 */     return 0x7F & getStatus();
/*     */   }
/*     */   
/*     */   public void setKind(int n) {
/* 126 */     setStatus(getStatus() & 0x80 | n);
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 130 */     return ((getStatus() & 0x80) != 0);
/*     */   }
/*     */   
/*     */   public void setOpen(boolean b) {
/* 134 */     setStatus((b ? 128 : 0) | getStatus() & 0x7F);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MCH_EntityAircraft getControlAircract() {
/* 139 */     return this.controlAircraft;
/*     */   }
/*     */   
/*     */   public void setControlAircract(@Nullable MCH_EntityAircraft ac) {
/* 143 */     this.controlAircraft = ac;
/* 144 */     if (ac != null && !ac.field_70128_L) {
/* 145 */       setLastControlAircraft(ac);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setUavPosition(int x, int y, int z) {
/* 150 */     if (!this.field_70170_p.field_72995_K) {
/* 151 */       this.posUavX = x;
/* 152 */       this.posUavY = y;
/* 153 */       this.posUavZ = z;
/* 154 */       this.field_70180_af.func_187227_b(UAV_POS, new BlockPos(x, y, z));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateUavPosition() {
/* 159 */     BlockPos uavPos = (BlockPos)this.field_70180_af.func_187225_a(UAV_POS);
/* 160 */     this.posUavX = uavPos.func_177958_n();
/* 161 */     this.posUavY = uavPos.func_177956_o();
/* 162 */     this.posUavZ = uavPos.func_177952_p();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound nbt) {
/* 167 */     super.func_70014_b(nbt);
/* 168 */     nbt.func_74768_a("UavStatus", getStatus());
/* 169 */     nbt.func_74768_a("PosUavX", this.posUavX);
/* 170 */     nbt.func_74768_a("PosUavY", this.posUavY);
/* 171 */     nbt.func_74768_a("PosUavZ", this.posUavZ);
/* 172 */     String s = "";
/* 173 */     if (getLastControlAircraft() != null && !(getLastControlAircraft()).field_70128_L) {
/* 174 */       s = getLastControlAircraft().getCommonUniqueId();
/*     */     }
/*     */     
/* 177 */     if (s.isEmpty()) {
/* 178 */       s = this.loadedLastControlAircraftGuid;
/*     */     }
/*     */     
/* 181 */     nbt.func_74778_a("LastCtrlAc", s);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbt) {
/* 186 */     super.func_70037_a(nbt);
/* 187 */     setUavPosition(nbt.func_74762_e("PosUavX"), nbt.func_74762_e("PosUavY"), nbt.func_74762_e("PosUavZ"));
/* 188 */     if (nbt.func_74764_b("UavStatus")) {
/* 189 */       setStatus(nbt.func_74762_e("UavStatus"));
/*     */     } else {
/* 191 */       setKind(1);
/*     */     } 
/*     */     
/* 194 */     this.loadedLastControlAircraftGuid = nbt.func_74779_i("LastCtrlAc");
/*     */   }
/*     */   
/*     */   public void initUavPostion() {
/* 198 */     int rt = (int)(MCH_Lib.getRotate360((this.field_70177_z + 45.0F)) / 90.0D);
/* 199 */     this.posUavX = (rt != 0 && rt != 3) ? -12 : 12;
/* 200 */     this.posUavZ = (rt != 0 && rt != 1) ? -12 : 12;
/* 201 */     this.posUavY = 2;
/* 202 */     setUavPosition(this.posUavX, this.posUavY, this.posUavZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 207 */     super.func_70106_y();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource damageSource, float damage) {
/* 212 */     if (func_180431_b(damageSource))
/* 213 */       return false; 
/* 214 */     if (this.field_70128_L)
/* 215 */       return true; 
/* 216 */     if (this.field_70170_p.field_72995_K) {
/* 217 */       return true;
/*     */     }
/* 219 */     String dmt = damageSource.func_76355_l();
/* 220 */     damage = MCH_Config.applyDamageByExternal((Entity)this, damageSource, damage);
/* 221 */     if (!MCH_Multiplay.canAttackEntity(damageSource, (Entity)this)) {
/* 222 */       return false;
/*     */     }
/* 224 */     boolean isCreative = false;
/* 225 */     Entity entity = damageSource.func_76346_g();
/* 226 */     boolean isDamegeSourcePlayer = false;
/* 227 */     if (entity instanceof EntityPlayer) {
/* 228 */       isCreative = ((EntityPlayer)entity).field_71075_bZ.field_75098_d;
/* 229 */       if (dmt.compareTo("player") == 0) {
/* 230 */         isDamegeSourcePlayer = true;
/*     */       }
/*     */       
/* 233 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "hit", 1.0F, 1.0F);
/*     */     } else {
/* 235 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "helidmg", 1.0F, 0.9F + this.field_70146_Z.nextFloat() * 0.1F);
/*     */     } 
/*     */     
/* 238 */     func_70018_K();
/* 239 */     if (damage > 0.0F) {
/* 240 */       Entity riddenByEntity = getRiddenByEntity();
/* 241 */       if (riddenByEntity != null) {
/* 242 */         riddenByEntity.func_184220_m((Entity)this);
/*     */       }
/*     */       
/* 245 */       this.dropContentsWhenDead = true;
/* 246 */       func_70106_y();
/* 247 */       if (!isDamegeSourcePlayer) {
/* 248 */         MCH_Explosion.newExplosion(this.field_70170_p, null, riddenByEntity, this.field_70165_t, this.field_70163_u, this.field_70161_v, 1.0F, 0.0F, true, true, false, false, 0);
/*     */       }
/*     */       
/* 251 */       if (!isCreative) {
/* 252 */         int kind = getKind();
/* 253 */         if (kind > 0) {
/* 254 */           func_145778_a((Item)MCH_MOD.itemUavStation[kind - 1], 1, 0.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 259 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/* 265 */     return false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/* 269 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/* 273 */     return func_174813_aQ();
/*     */   }
/*     */   
/*     */   public double func_70042_X() {
/* 277 */     Entity riddenByEntity = getRiddenByEntity();
/* 278 */     if (getKind() == 2 && riddenByEntity != null) {
/* 279 */       double px = -Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.9D;
/* 280 */       double pz = Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.9D;
/* 281 */       int x = (int)(this.field_70165_t + px);
/* 282 */       int y = (int)(this.field_70163_u - 0.5D);
/* 283 */       int z = (int)(this.field_70161_v + pz);
/* 284 */       BlockPos blockpos = new BlockPos(x, y, z);
/* 285 */       IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
/* 286 */       return iblockstate.func_185914_p() ? -0.4D : -0.9D;
/*     */     } 
/* 288 */     return 0.35D;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 294 */     return 2.0F;
/*     */   }
/*     */   
/*     */   public boolean func_70067_L() {
/* 298 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70108_f(@NotNull Entity par1Entity) {}
/*     */ 
/*     */   
/*     */   public void func_70024_g(double par1, double par3, double par5) {}
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double par1, double par3, double par5) {
/* 309 */     this.velocityX = this.field_70159_w = par1;
/* 310 */     this.velocityY = this.field_70181_x = par3;
/* 311 */     this.velocityZ = this.field_70179_y = par5;
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/* 315 */     super.func_70071_h_();
/* 316 */     this.prevRotCover = this.rotCover;
/* 317 */     if (isOpen()) {
/* 318 */       if (this.rotCover < 1.0F) {
/* 319 */         this.rotCover += 0.1F;
/*     */       } else {
/* 321 */         this.rotCover = 1.0F;
/*     */       } 
/* 323 */     } else if (this.rotCover > 0.0F) {
/* 324 */       this.rotCover -= 0.1F;
/*     */     } else {
/* 326 */       this.rotCover = 0.0F;
/*     */     } 
/*     */     
/* 329 */     Entity riddenByEntity = getRiddenByEntity();
/* 330 */     if (riddenByEntity == null) {
/* 331 */       if (this.lastRiddenByEntity != null) {
/* 332 */         unmountEntity(true);
/*     */       }
/*     */       
/* 335 */       setControlAircract((MCH_EntityAircraft)null);
/*     */     } 
/*     */     
/* 338 */     int uavStationKind = getKind();
/* 339 */     if (this.field_70173_aa >= 30 || uavStationKind != 2 || (this.field_70170_p.field_72995_K && !this.isRequestedSyncStatus)) {
/* 340 */       this.isRequestedSyncStatus = true;
/*     */     }
/*     */     
/* 343 */     this.field_70169_q = this.field_70165_t;
/* 344 */     this.field_70167_r = this.field_70163_u;
/* 345 */     this.field_70166_s = this.field_70161_v;
/* 346 */     if (getControlAircract() != null && (getControlAircract()).field_70128_L) {
/* 347 */       setControlAircract((MCH_EntityAircraft)null);
/*     */     }
/*     */     
/* 350 */     if (getLastControlAircraft() != null && (getLastControlAircraft()).field_70128_L) {
/* 351 */       setLastControlAircraft((MCH_EntityAircraft)null);
/*     */     }
/*     */     
/* 354 */     if (this.field_70170_p.field_72995_K) {
/* 355 */       onUpdate_Client();
/*     */     } else {
/* 357 */       onUpdate_Server();
/*     */     } 
/*     */     
/* 360 */     this.lastRiddenByEntity = getRiddenByEntity();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MCH_EntityAircraft getLastControlAircraft() {
/* 365 */     return this.lastControlAircraft;
/*     */   }
/*     */   
/*     */   public void setLastControlAircraft(MCH_EntityAircraft ac) {
/* 369 */     MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityUavStation.setLastControlAircraft:" + ac, new Object[0]);
/* 370 */     this.lastControlAircraft = ac;
/*     */   }
/*     */   
/*     */   public MCH_EntityAircraft getAndSearchLastControlAircraft() {
/* 374 */     if (getLastControlAircraft() == null) {
/* 375 */       int id = getLastControlAircraftEntityId().intValue();
/* 376 */       if (id > 0) {
/* 377 */         Entity entity = this.field_70170_p.func_73045_a(id);
/* 378 */         if (entity instanceof MCH_EntityAircraft) { MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
/* 379 */           if (ac.isUAV()) {
/* 380 */             setLastControlAircraft(ac);
/*     */           } }
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 386 */     return getLastControlAircraft();
/*     */   }
/*     */   
/*     */   public Integer getLastControlAircraftEntityId() {
/* 390 */     return (Integer)this.field_70180_af.func_187225_a(LAST_AC_ID);
/*     */   }
/*     */   
/*     */   public void setLastControlAircraftEntityId(int s) {
/* 394 */     if (!this.field_70170_p.field_72995_K) {
/* 395 */       this.field_70180_af.func_187227_b(LAST_AC_ID, Integer.valueOf(s));
/*     */     }
/*     */   }
/*     */   
/*     */   public void searchLastControlAircraft() {
/* 400 */     if (!this.loadedLastControlAircraftGuid.isEmpty()) {
/* 401 */       List<MCH_EntityAircraft> list = this.field_70170_p.func_72872_a(MCH_EntityAircraft.class, func_70046_E().func_72314_b(120.0D, 120.0D, 120.0D));
/* 402 */       for (MCH_EntityAircraft ac : list) {
/* 403 */         if (ac.getCommonUniqueId().equals(this.loadedLastControlAircraftGuid)) {
/* 404 */           String n = "no info : " + ac;
/* 405 */           MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityUavStation.searchLastControlAircraft:found" + n, new Object[0]);
/* 406 */           setLastControlAircraft(ac);
/* 407 */           setLastControlAircraftEntityId(W_Entity.getEntityId((Entity)ac));
/* 408 */           this.loadedLastControlAircraftGuid = "";
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onUpdate_Client() {
/* 416 */     if (this.aircraftPosRotInc > 0) {
/* 417 */       double rpinc = this.aircraftPosRotInc;
/* 418 */       double yaw = MathHelper.func_76138_g(this.aircraftYaw - this.field_70177_z);
/* 419 */       this.field_70177_z = (float)(this.field_70177_z + yaw / rpinc);
/* 420 */       this.field_70125_A = (float)(this.field_70125_A + (this.aircraftPitch - this.field_70125_A) / rpinc);
/* 421 */       func_70107_b(this.field_70165_t + (this.aircraftX - this.field_70165_t) / rpinc, this.field_70163_u + (this.aircraftY - this.field_70163_u) / rpinc, this.field_70161_v + (this.aircraftZ - this.field_70161_v) / rpinc);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 426 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/* 427 */       this.aircraftPosRotInc--;
/*     */     } else {
/* 429 */       func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 430 */       this.field_70181_x *= 0.96D;
/* 431 */       this.field_70159_w = 0.0D;
/* 432 */       this.field_70179_y = 0.0D;
/*     */     } 
/*     */     
/* 435 */     updateUavPosition();
/*     */   }
/*     */   
/*     */   private void onUpdate_Server() {
/* 439 */     this.field_70181_x -= 0.03D;
/* 440 */     func_70091_d(MoverType.SELF, 0.0D, this.field_70181_x, 0.0D);
/* 441 */     this.field_70181_x *= 0.96D;
/* 442 */     this.field_70159_w = 0.0D;
/* 443 */     this.field_70179_y = 0.0D;
/* 444 */     func_70101_b(this.field_70177_z, this.field_70125_A);
/* 445 */     Entity riddenByEntity = getRiddenByEntity();
/* 446 */     if (riddenByEntity != null) {
/* 447 */       if (riddenByEntity.field_70128_L) {
/* 448 */         unmountEntity(true);
/*     */       } else {
/* 450 */         ItemStack item = func_70301_a(0);
/* 451 */         if (!item.func_190926_b()) {
/* 452 */           handleItem(riddenByEntity, item);
/* 453 */           if (item.func_190916_E() == 0) {
/* 454 */             func_70299_a(0, ItemStack.field_190927_a);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 460 */     if (getLastControlAircraft() == null && this.field_70173_aa % 40 == 0) {
/* 461 */       searchLastControlAircraft();
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_180426_a(double par1, double par3, double par5, float par7, float par8, int par9, boolean teleport) {
/* 466 */     this.aircraftPosRotInc = par9 + 8;
/* 467 */     this.aircraftX = par1;
/* 468 */     this.aircraftY = par3;
/* 469 */     this.aircraftZ = par5;
/* 470 */     this.aircraftYaw = par7;
/* 471 */     this.aircraftPitch = par8;
/* 472 */     this.field_70159_w = this.velocityX;
/* 473 */     this.field_70181_x = this.velocityY;
/* 474 */     this.field_70179_y = this.velocityZ;
/*     */   }
/*     */   
/*     */   public void func_184232_k(@NotNull Entity passenger) {
/* 478 */     if (func_184196_w(passenger)) {
/* 479 */       double x = -Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.9D;
/* 480 */       double z = Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.9D;
/* 481 */       passenger.func_70107_b(this.field_70165_t + x, this.field_70163_u + func_70042_X() + passenger.func_70033_W() + 0.3499999940395355D, this.field_70161_v + z);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void controlLastAircraft(Entity user) {
/* 486 */     if (getLastControlAircraft() != null && !(getLastControlAircraft()).field_70128_L) {
/* 487 */       getLastControlAircraft().setUavStation(this);
/* 488 */       setControlAircract(getLastControlAircraft());
/* 489 */       W_EntityPlayer.closeScreen(user);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleItem(@Nullable Entity user, ItemStack itemStack) {
/* 494 */     if (user != null && !user.field_70128_L && !itemStack.func_190926_b() && itemStack.func_190916_E() == 1 && 
/* 495 */       !this.field_70170_p.field_72995_K) {
/* 496 */       MCH_EntityTank mCH_EntityTank; MCH_EntityAircraft ac = null;
/* 497 */       double x = this.field_70165_t + this.posUavX;
/* 498 */       double y = this.field_70163_u + this.posUavY;
/* 499 */       double z = this.field_70161_v + this.posUavZ;
/* 500 */       if (y <= 1.0D) {
/* 501 */         y = 2.0D;
/*     */       }
/*     */       
/* 504 */       Item item = itemStack.func_77973_b();
/* 505 */       if (item instanceof MCP_ItemPlane) {
/* 506 */         MCP_PlaneInfo pi = MCP_PlaneInfoManager.getFromItem(item);
/* 507 */         if (pi != null && pi.isUAV) {
/* 508 */           if (!pi.isSmallUAV && getKind() == 2) {
/* 509 */             ac = null;
/*     */           } else {
/* 511 */             MCP_EntityPlane mCP_EntityPlane = ((MCP_ItemPlane)item).createAircraft(this.field_70170_p, x, y, z, itemStack);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 516 */       if (item instanceof MCH_ItemHeli) {
/* 517 */         MCH_HeliInfo hi = MCH_HeliInfoManager.getFromItem(item);
/* 518 */         if (hi != null && hi.isUAV) {
/* 519 */           if (!hi.isSmallUAV && getKind() == 2) {
/* 520 */             ac = null;
/*     */           } else {
/* 522 */             MCH_EntityHeli mCH_EntityHeli = ((MCH_ItemHeli)item).createAircraft(this.field_70170_p, x, y, z, itemStack);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 527 */       if (item instanceof MCH_ItemTank) {
/* 528 */         MCH_TankInfo hi = MCH_TankInfoManager.getFromItem(item);
/* 529 */         if (hi != null && hi.isUAV) {
/* 530 */           if (!hi.isSmallUAV && getKind() == 2) {
/* 531 */             ac = null;
/*     */           } else {
/* 533 */             mCH_EntityTank = ((MCH_ItemTank)item).createAircraft(this.field_70170_p, x, y, z, itemStack);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 538 */       if (mCH_EntityTank != null) {
/* 539 */         ((MCH_EntityAircraft)mCH_EntityTank).field_70177_z = this.field_70177_z - 180.0F;
/* 540 */         ((MCH_EntityAircraft)mCH_EntityTank).field_70126_B = ((MCH_EntityAircraft)mCH_EntityTank).field_70177_z;
/* 541 */         user.field_70177_z = this.field_70177_z - 180.0F;
/* 542 */         if (this.field_70170_p.func_184144_a((Entity)mCH_EntityTank, mCH_EntityTank.func_174813_aQ().func_72314_b(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/* 543 */           itemStack.func_190918_g(1);
/* 544 */           MCH_Lib.DbgLog(this.field_70170_p, "Create UAV: %s : %s", new Object[] { item.func_77658_a(), item });
/* 545 */           user.field_70177_z = this.field_70177_z - 180.0F;
/* 546 */           if (!mCH_EntityTank.isTargetDrone()) {
/* 547 */             mCH_EntityTank.setUavStation(this);
/* 548 */             setControlAircract((MCH_EntityAircraft)mCH_EntityTank);
/*     */           } 
/*     */           
/* 551 */           this.field_70170_p.func_72838_d((Entity)mCH_EntityTank);
/* 552 */           if (!mCH_EntityTank.isTargetDrone()) {
/* 553 */             mCH_EntityTank.setFuel((int)(mCH_EntityTank.getMaxFuel() * 0.05F));
/* 554 */             W_EntityPlayer.closeScreen(user);
/*     */           } else {
/* 556 */             mCH_EntityTank.setFuel(mCH_EntityTank.getMaxFuel());
/*     */           } 
/*     */         } else {
/* 559 */           mCH_EntityTank.func_70106_y();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void _setInventorySlotContents(int par1, ItemStack itemStack) {
/* 567 */     func_70299_a(par1, itemStack);
/*     */   }
/*     */   
/*     */   public boolean func_184230_a(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 571 */     if (hand != EnumHand.MAIN_HAND) {
/* 572 */       return false;
/*     */     }
/* 574 */     int kind = getKind();
/* 575 */     if (kind <= 0)
/* 576 */       return false; 
/* 577 */     if (getRiddenByEntity() != null) {
/* 578 */       return false;
/*     */     }
/* 580 */     if (kind == 2) {
/* 581 */       if (player.func_70093_af()) {
/* 582 */         setOpen(!isOpen());
/* 583 */         return false;
/*     */       } 
/*     */       
/* 586 */       if (!isOpen()) {
/* 587 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 591 */     this.lastRiddenByEntity = null;
/* 592 */     PooledGuiParameter.setEntity(player, (Entity)this);
/* 593 */     if (!this.field_70170_p.field_72995_K) {
/* 594 */       player.func_184220_m((Entity)this);
/* 595 */       player.openGui(MCH_MOD.instance, 0, player.field_70170_p, (int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v);
/*     */     } 
/*     */     
/* 598 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 605 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 610 */     return 1;
/*     */   }
/*     */   
/*     */   public void unmountEntity(boolean unmountAllEntity) {
/* 614 */     Entity rByEntity = null;
/* 615 */     Entity riddenByEntity = getRiddenByEntity();
/* 616 */     if (riddenByEntity != null) {
/* 617 */       if (!this.field_70170_p.field_72995_K) {
/* 618 */         rByEntity = riddenByEntity;
/* 619 */         riddenByEntity.func_184210_p();
/*     */       } 
/* 621 */     } else if (this.lastRiddenByEntity != null) {
/* 622 */       rByEntity = this.lastRiddenByEntity;
/*     */     } 
/*     */     
/* 625 */     if (getControlAircract() != null) {
/* 626 */       getControlAircract().setUavStation(null);
/*     */     }
/*     */     
/* 629 */     setControlAircract((MCH_EntityAircraft)null);
/* 630 */     if (this.field_70170_p.field_72995_K) {
/* 631 */       W_EntityPlayer.closeScreen(rByEntity);
/*     */     }
/*     */     
/* 634 */     this.lastRiddenByEntity = null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 640 */     List<Entity> passengers = func_184188_bt();
/* 641 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mchel\\uav\MCH_EntityUavStation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */