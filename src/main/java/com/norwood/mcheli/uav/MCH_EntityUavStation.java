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
/*  49 */   private static final DataParameter<Byte> STATUS = EntityDataManager.createKey(MCH_EntityUavStation.class, DataSerializers.BYTE); public static final float Y_OFFSET = 0.35F;
/*  50 */   private static final DataParameter<Integer> LAST_AC_ID = EntityDataManager.createKey(MCH_EntityUavStation.class, DataSerializers.VARINT);
/*  51 */   private static final DataParameter<BlockPos> UAV_POS = EntityDataManager.createKey(MCH_EntityUavStation.class, DataSerializers.BLOCK_POS);
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
/*  78 */     this.preventEntitySpawning = true;
/*  79 */     setSize(2.0F, 0.7F);
/*  80 */     this.motionX = 0.0D;
/*  81 */     this.motionY = 0.0D;
/*  82 */     this.motionZ = 0.0D;
/*  83 */     this.ignoreFrustumCheck = true;
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
/*     */   protected void entityInit() {
/* 103 */     super.entityInit();
/* 104 */     this.dataManager.register(STATUS, Byte.valueOf((byte)0));
/* 105 */     this.dataManager.register(LAST_AC_ID, Integer.valueOf(0));
/* 106 */     this.dataManager.register(UAV_POS, BlockPos.ORIGIN);
/* 107 */     setOpen(true);
/*     */   }
/*     */   
/*     */   public int getStatus() {
/* 111 */     return ((Byte)this.dataManager.get(STATUS)).byteValue();
/*     */   }
/*     */   
/*     */   public void setStatus(int n) {
/* 115 */     if (!this.world.isRemote) {
/* 116 */       MCH_Lib.DbgLog(this.world, "MCH_EntityUavStation.setStatus(%d)", new Object[] { Integer.valueOf(n) });
/* 117 */       this.dataManager.set(STATUS, Byte.valueOf((byte)n));
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
/* 144 */     if (ac != null && !ac.isDead) {
/* 145 */       setLastControlAircraft(ac);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setUavPosition(int x, int y, int z) {
/* 150 */     if (!this.world.isRemote) {
/* 151 */       this.posUavX = x;
/* 152 */       this.posUavY = y;
/* 153 */       this.posUavZ = z;
/* 154 */       this.dataManager.set(UAV_POS, new BlockPos(x, y, z));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateUavPosition() {
/* 159 */     BlockPos uavPos = (BlockPos)this.dataManager.get(UAV_POS);
/* 160 */     this.posUavX = uavPos.getX();
/* 161 */     this.posUavY = uavPos.getY();
/* 162 */     this.posUavZ = uavPos.getZ();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound nbt) {
/* 167 */     super.writeEntityToNBT(nbt);
/* 168 */     nbt.setInteger("UavStatus", getStatus());
/* 169 */     nbt.setInteger("PosUavX", this.posUavX);
/* 170 */     nbt.setInteger("PosUavY", this.posUavY);
/* 171 */     nbt.setInteger("PosUavZ", this.posUavZ);
/* 172 */     String s = "";
/* 173 */     if (getLastControlAircraft() != null && !(getLastControlAircraft()).isDead) {
/* 174 */       s = getLastControlAircraft().getCommonUniqueId();
/*     */     }
/*     */     
/* 177 */     if (s.isEmpty()) {
/* 178 */       s = this.loadedLastControlAircraftGuid;
/*     */     }
/*     */     
/* 181 */     nbt.setString("LastCtrlAc", s);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound nbt) {
/* 186 */     super.readEntityFromNBT(nbt);
/* 187 */     setUavPosition(nbt.getInteger("PosUavX"), nbt.getInteger("PosUavY"), nbt.getInteger("PosUavZ"));
/* 188 */     if (nbt.hasKey("UavStatus")) {
/* 189 */       setStatus(nbt.getInteger("UavStatus"));
/*     */     } else {
/* 191 */       setKind(1);
/*     */     } 
/*     */     
/* 194 */     this.loadedLastControlAircraftGuid = nbt.getString("LastCtrlAc");
/*     */   }
/*     */   
/*     */   public void initUavPostion() {
/* 198 */     int rt = (int)(MCH_Lib.getRotate360((this.rotationYaw + 45.0F)) / 90.0D);
/* 199 */     this.posUavX = (rt != 0 && rt != 3) ? -12 : 12;
/* 200 */     this.posUavZ = (rt != 0 && rt != 1) ? -12 : 12;
/* 201 */     this.posUavY = 2;
/* 202 */     setUavPosition(this.posUavX, this.posUavY, this.posUavZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 207 */     super.setDead();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource damageSource, float damage) {
/* 212 */     if (isEntityInvulnerable(damageSource))
/* 213 */       return false; 
/* 214 */     if (this.isDead)
/* 215 */       return true; 
/* 216 */     if (this.world.isRemote) {
/* 217 */       return true;
/*     */     }
/* 219 */     String dmt = damageSource.getDamageType();
/* 220 */     damage = MCH_Config.applyDamageByExternal((Entity)this, damageSource, damage);
/* 221 */     if (!MCH_Multiplay.canAttackEntity(damageSource, (Entity)this)) {
/* 222 */       return false;
/*     */     }
/* 224 */     boolean isCreative = false;
/* 225 */     Entity entity = damageSource.getTrueSource();
/* 226 */     boolean isDamegeSourcePlayer = false;
/* 227 */     if (entity instanceof EntityPlayer) {
/* 228 */       isCreative = ((EntityPlayer)entity).capabilities.isCreativeMode;
/* 229 */       if (dmt.compareTo("player") == 0) {
/* 230 */         isDamegeSourcePlayer = true;
/*     */       }
/*     */       
/* 233 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "hit", 1.0F, 1.0F);
/*     */     } else {
/* 235 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "helidmg", 1.0F, 0.9F + this.rand.nextFloat() * 0.1F);
/*     */     } 
/*     */     
/* 238 */     markVelocityChanged();
/* 239 */     if (damage > 0.0F) {
/* 240 */       Entity riddenByEntity = getRiddenByEntity();
/* 241 */       if (riddenByEntity != null) {
/* 242 */         riddenByEntity.startRiding((Entity)this);
/*     */       }
/*     */       
/* 245 */       this.dropContentsWhenDead = true;
/* 246 */       setDead();
/* 247 */       if (!isDamegeSourcePlayer) {
/* 248 */         MCH_Explosion.newExplosion(this.world, null, riddenByEntity, this.posX, this.posY, this.posZ, 1.0F, 0.0F, true, true, false, false, 0);
/*     */       }
/*     */       
/* 251 */       if (!isCreative) {
/* 252 */         int kind = getKind();
/* 253 */         if (kind > 0) {
/* 254 */           dropItemWithOffset((Item)MCH_MOD.itemUavStation[kind - 1], 1, 0.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 259 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 265 */     return false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBox(Entity par1Entity) {
/* 269 */     return par1Entity.getEntityBoundingBox();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox() {
/* 273 */     return getEntityBoundingBox();
/*     */   }
/*     */   
/*     */   public double getMountedYOffset() {
/* 277 */     Entity riddenByEntity = getRiddenByEntity();
/* 278 */     if (getKind() == 2 && riddenByEntity != null) {
/* 279 */       double px = -Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.9D;
/* 280 */       double pz = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.9D;
/* 281 */       int x = (int)(this.posX + px);
/* 282 */       int y = (int)(this.posY - 0.5D);
/* 283 */       int z = (int)(this.posZ + pz);
/* 284 */       BlockPos blockpos = new BlockPos(x, y, z);
/* 285 */       IBlockState iblockstate = this.world.getBlockState(blockpos);
/* 286 */       return iblockstate.isOpaqueCube() ? -0.4D : -0.9D;
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
/*     */   public boolean canBeCollidedWith() {
/* 298 */     return !this.isDead;
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyEntityCollision(@NotNull Entity par1Entity) {}
/*     */ 
/*     */   
/*     */   public void addVelocity(double par1, double par3, double par5) {}
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setVelocity(double par1, double par3, double par5) {
/* 309 */     this.velocityX = this.motionX = par1;
/* 310 */     this.velocityY = this.motionY = par3;
/* 311 */     this.velocityZ = this.motionZ = par5;
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 315 */     super.onUpdate();
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
/* 339 */     if (this.ticksExisted >= 30 || uavStationKind != 2 || (this.world.isRemote && !this.isRequestedSyncStatus)) {
/* 340 */       this.isRequestedSyncStatus = true;
/*     */     }
/*     */     
/* 343 */     this.prevPosX = this.posX;
/* 344 */     this.prevPosY = this.posY;
/* 345 */     this.prevPosZ = this.posZ;
/* 346 */     if (getControlAircract() != null && (getControlAircract()).isDead) {
/* 347 */       setControlAircract((MCH_EntityAircraft)null);
/*     */     }
/*     */     
/* 350 */     if (getLastControlAircraft() != null && (getLastControlAircraft()).isDead) {
/* 351 */       setLastControlAircraft((MCH_EntityAircraft)null);
/*     */     }
/*     */     
/* 354 */     if (this.world.isRemote) {
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
/* 369 */     MCH_Lib.DbgLog(this.world, "MCH_EntityUavStation.setLastControlAircraft:" + ac, new Object[0]);
/* 370 */     this.lastControlAircraft = ac;
/*     */   }
/*     */   
/*     */   public MCH_EntityAircraft getAndSearchLastControlAircraft() {
/* 374 */     if (getLastControlAircraft() == null) {
/* 375 */       int id = getLastControlAircraftEntityId().intValue();
/* 376 */       if (id > 0) {
/* 377 */         Entity entity = this.world.getEntityByID(id);
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
/* 390 */     return (Integer)this.dataManager.get(LAST_AC_ID);
/*     */   }
/*     */   
/*     */   public void setLastControlAircraftEntityId(int s) {
/* 394 */     if (!this.world.isRemote) {
/* 395 */       this.dataManager.set(LAST_AC_ID, Integer.valueOf(s));
/*     */     }
/*     */   }
/*     */   
/*     */   public void searchLastControlAircraft() {
/* 400 */     if (!this.loadedLastControlAircraftGuid.isEmpty()) {
/* 401 */       List<MCH_EntityAircraft> list = this.world.getEntitiesWithinAABB(MCH_EntityAircraft.class, getCollisionBoundingBox().grow(120.0D, 120.0D, 120.0D));
/* 402 */       for (MCH_EntityAircraft ac : list) {
/* 403 */         if (ac.getCommonUniqueId().equals(this.loadedLastControlAircraftGuid)) {
/* 404 */           String n = "no info : " + ac;
/* 405 */           MCH_Lib.DbgLog(this.world, "MCH_EntityUavStation.searchLastControlAircraft:found" + n, new Object[0]);
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
/* 418 */       double yaw = MathHelper.wrapDegrees(this.aircraftYaw - this.rotationYaw);
/* 419 */       this.rotationYaw = (float)(this.rotationYaw + yaw / rpinc);
/* 420 */       this.rotationPitch = (float)(this.rotationPitch + (this.aircraftPitch - this.rotationPitch) / rpinc);
/* 421 */       setPosition(this.posX + (this.aircraftX - this.posX) / rpinc, this.posY + (this.aircraftY - this.posY) / rpinc, this.posZ + (this.aircraftZ - this.posZ) / rpinc);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 426 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 427 */       this.aircraftPosRotInc--;
/*     */     } else {
/* 429 */       setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 430 */       this.motionY *= 0.96D;
/* 431 */       this.motionX = 0.0D;
/* 432 */       this.motionZ = 0.0D;
/*     */     } 
/*     */     
/* 435 */     updateUavPosition();
/*     */   }
/*     */   
/*     */   private void onUpdate_Server() {
/* 439 */     this.motionY -= 0.03D;
/* 440 */     move(MoverType.SELF, 0.0D, this.motionY, 0.0D);
/* 441 */     this.motionY *= 0.96D;
/* 442 */     this.motionX = 0.0D;
/* 443 */     this.motionZ = 0.0D;
/* 444 */     setRotation(this.rotationYaw, this.rotationPitch);
/* 445 */     Entity riddenByEntity = getRiddenByEntity();
/* 446 */     if (riddenByEntity != null) {
/* 447 */       if (riddenByEntity.isDead) {
/* 448 */         unmountEntity(true);
/*     */       } else {
/* 450 */         ItemStack item = getStackInSlot(0);
/* 451 */         if (!item.isEmpty()) {
/* 452 */           handleItem(riddenByEntity, item);
/* 453 */           if (item.getCount() == 0) {
/* 454 */             setInventorySlotContents(0, ItemStack.EMPTY);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 460 */     if (getLastControlAircraft() == null && this.ticksExisted % 40 == 0) {
/* 461 */       searchLastControlAircraft();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPositionAndRotationDirect(double par1, double par3, double par5, float par7, float par8, int par9, boolean teleport) {
/* 466 */     this.aircraftPosRotInc = par9 + 8;
/* 467 */     this.aircraftX = par1;
/* 468 */     this.aircraftY = par3;
/* 469 */     this.aircraftZ = par5;
/* 470 */     this.aircraftYaw = par7;
/* 471 */     this.aircraftPitch = par8;
/* 472 */     this.motionX = this.velocityX;
/* 473 */     this.motionY = this.velocityY;
/* 474 */     this.motionZ = this.velocityZ;
/*     */   }
/*     */   
/*     */   public void updatePassenger(@NotNull Entity passenger) {
/* 478 */     if (isPassenger(passenger)) {
/* 479 */       double x = -Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.9D;
/* 480 */       double z = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.9D;
/* 481 */       passenger.setPosition(this.posX + x, this.posY + getMountedYOffset() + passenger.getYOffset() + 0.3499999940395355D, this.posZ + z);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void controlLastAircraft(Entity user) {
/* 486 */     if (getLastControlAircraft() != null && !(getLastControlAircraft()).isDead) {
/* 487 */       getLastControlAircraft().setUavStation(this);
/* 488 */       setControlAircract(getLastControlAircraft());
/* 489 */       W_EntityPlayer.closeScreen(user);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleItem(@Nullable Entity user, ItemStack itemStack) {
/* 494 */     if (user != null && !user.isDead && !itemStack.isEmpty() && itemStack.getCount() == 1 && 
/* 495 */       !this.world.isRemote) {
/* 496 */       MCH_EntityTank mCH_EntityTank; MCH_EntityAircraft ac = null;
/* 497 */       double x = this.posX + this.posUavX;
/* 498 */       double y = this.posY + this.posUavY;
/* 499 */       double z = this.posZ + this.posUavZ;
/* 500 */       if (y <= 1.0D) {
/* 501 */         y = 2.0D;
/*     */       }
/*     */       
/* 504 */       Item item = itemStack.getItem();
/* 505 */       if (item instanceof MCP_ItemPlane) {
/* 506 */         MCP_PlaneInfo pi = MCP_PlaneInfoManager.getFromItem(item);
/* 507 */         if (pi != null && pi.isUAV) {
/* 508 */           if (!pi.isSmallUAV && getKind() == 2) {
/* 509 */             ac = null;
/*     */           } else {
/* 511 */             MCP_EntityPlane mCP_EntityPlane = ((MCP_ItemPlane)item).createAircraft(this.world, x, y, z, itemStack);
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
/* 522 */             MCH_EntityHeli mCH_EntityHeli = ((MCH_ItemHeli)item).createAircraft(this.world, x, y, z, itemStack);
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
/* 533 */             mCH_EntityTank = ((MCH_ItemTank)item).createAircraft(this.world, x, y, z, itemStack);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 538 */       if (mCH_EntityTank != null) {
/* 539 */         ((MCH_EntityAircraft)mCH_EntityTank).rotationYaw = this.rotationYaw - 180.0F;
/* 540 */         ((MCH_EntityAircraft)mCH_EntityTank).prevRotationYaw = ((MCH_EntityAircraft)mCH_EntityTank).rotationYaw;
/* 541 */         user.rotationYaw = this.rotationYaw - 180.0F;
/* 542 */         if (this.world.getCollisionBoxes((Entity)mCH_EntityTank, mCH_EntityTank.getEntityBoundingBox().grow(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/* 543 */           itemStack.shrink(1);
/* 544 */           MCH_Lib.DbgLog(this.world, "Create UAV: %s : %s", new Object[] { item.getUnlocalizedName(), item });
/* 545 */           user.rotationYaw = this.rotationYaw - 180.0F;
/* 546 */           if (!mCH_EntityTank.isTargetDrone()) {
/* 547 */             mCH_EntityTank.setUavStation(this);
/* 548 */             setControlAircract((MCH_EntityAircraft)mCH_EntityTank);
/*     */           } 
/*     */           
/* 551 */           this.world.spawnEntity((Entity)mCH_EntityTank);
/* 552 */           if (!mCH_EntityTank.isTargetDrone()) {
/* 553 */             mCH_EntityTank.setFuel((int)(mCH_EntityTank.getMaxFuel() * 0.05F));
/* 554 */             W_EntityPlayer.closeScreen(user);
/*     */           } else {
/* 556 */             mCH_EntityTank.setFuel(mCH_EntityTank.getMaxFuel());
/*     */           } 
/*     */         } else {
/* 559 */           mCH_EntityTank.setDead();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void _setInventorySlotContents(int par1, ItemStack itemStack) {
/* 567 */     setInventorySlotContents(par1, itemStack);
/*     */   }
/*     */   
/*     */   public boolean processInitialInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
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
/* 581 */       if (player.isSneaking()) {
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
/* 593 */     if (!this.world.isRemote) {
/* 594 */       player.startRiding((Entity)this);
/* 595 */       player.openGui(MCH_MOD.instance, 0, player.world, (int)this.posX, (int)this.posY, (int)this.posZ);
/*     */     } 
/*     */     
/* 598 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 605 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 610 */     return 1;
/*     */   }
/*     */   
/*     */   public void unmountEntity(boolean unmountAllEntity) {
/* 614 */     Entity rByEntity = null;
/* 615 */     Entity riddenByEntity = getRiddenByEntity();
/* 616 */     if (riddenByEntity != null) {
/* 617 */       if (!this.world.isRemote) {
/* 618 */         rByEntity = riddenByEntity;
/* 619 */         riddenByEntity.dismountRidingEntity();
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
/* 630 */     if (this.world.isRemote) {
/* 631 */       W_EntityPlayer.closeScreen(rByEntity);
/*     */     }
/*     */     
/* 634 */     this.lastRiddenByEntity = null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 640 */     List<Entity> passengers = getPassengers();
/* 641 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mchel\\uav\MCH_EntityUavStation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */