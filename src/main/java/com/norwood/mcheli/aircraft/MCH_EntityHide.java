/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.norwood.mcheli.helper.entity.IEntitySinglePassenger;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityHide
/*     */   extends W_Entity
/*     */   implements IEntitySinglePassenger
/*     */ {
/*  41 */   private static final DataParameter<Integer> ROPE_INDEX = EntityDataManager.createKey(MCH_EntityHide.class, DataSerializers.VARINT);
/*  42 */   private static final DataParameter<Integer> AC_ID = EntityDataManager.createKey(MCH_EntityHide.class, DataSerializers.VARINT);
/*     */   private MCH_EntityAircraft ac;
/*     */   private Entity user;
/*     */   private int paraPosRotInc;
/*     */   private double paraX;
/*     */   private double paraY;
/*     */   private double paraZ;
/*     */   private double paraYaw;
/*     */   private double paraPitch;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityY;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityZ;
/*     */   
/*     */   public MCH_EntityHide(World par1World) {
/*  59 */     super(par1World);
/*  60 */     setSize(1.0F, 1.0F);
/*  61 */     this.preventEntitySpawning = true;
/*  62 */     this.user = null;
/*  63 */     this.motionX = this.motionY = this.motionZ = 0.0D;
/*     */   }
/*     */   
/*     */   public MCH_EntityHide(World par1World, double x, double y, double z) {
/*  67 */     this(par1World);
/*  68 */     this.posX = x;
/*  69 */     this.posY = y;
/*  70 */     this.posZ = z;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  75 */     super.entityInit();
/*  76 */     createRopeIndex(-1);
/*  77 */     this.dataManager.register(AC_ID, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public void setParent(MCH_EntityAircraft ac, Entity user, int ropeIdx) {
/*  81 */     this.ac = ac;
/*  82 */     setRopeIndex(ropeIdx);
/*  83 */     this.user = user;
/*     */   }
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBox(Entity par1Entity) {
/*  91 */     return par1Entity.getEntityBoundingBox();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox() {
/*  95 */     return getEntityBoundingBox();
/*     */   }
/*     */   
/*     */   public boolean canBePushed() {
/*  99 */     return true;
/*     */   }
/*     */   
/*     */   public double getMountedYOffset() {
/* 103 */     return this.height * 0.0D - 0.3D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 112 */     return !this.isDead;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(@NotNull NBTTagCompound nbt) {}
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(@NotNull NBTTagCompound nbt) {}
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 123 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean processInitialInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 127 */     return false;
/*     */   }
/*     */   
/*     */   public void createRopeIndex(int defaultValue) {
/* 131 */     this.dataManager.register(ROPE_INDEX, Integer.valueOf(defaultValue));
/*     */   }
/*     */   
/*     */   public int getRopeIndex() {
/* 135 */     return ((Integer)this.dataManager.get(ROPE_INDEX)).intValue();
/*     */   }
/*     */   
/*     */   public void setRopeIndex(int value) {
/* 139 */     this.dataManager.set(ROPE_INDEX, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setPositionAndRotationDirect(double par1, double par3, double par5, float par7, float par8, int par9, boolean teleport) {
/* 144 */     this.paraPosRotInc = par9 + 10;
/* 145 */     this.paraX = par1;
/* 146 */     this.paraY = par3;
/* 147 */     this.paraZ = par5;
/* 148 */     this.paraYaw = par7;
/* 149 */     this.paraPitch = par8;
/* 150 */     this.motionX = this.velocityX;
/* 151 */     this.motionY = this.velocityY;
/* 152 */     this.motionZ = this.velocityZ;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setVelocity(double par1, double par3, double par5) {
/* 157 */     this.velocityX = this.motionX = par1;
/* 158 */     this.velocityY = this.motionY = par3;
/* 159 */     this.velocityZ = this.motionZ = par5;
/*     */   }
/*     */   
/*     */   public void setDead() {
/* 163 */     super.setDead();
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 167 */     super.onUpdate();
/* 168 */     if (this.user != null && !this.world.isRemote) {
/* 169 */       if (this.ac != null) {
/* 170 */         this.dataManager.set(AC_ID, Integer.valueOf(this.ac.getEntityId()));
/*     */       }
/*     */       
/* 173 */       this.user.startRiding((Entity)this);
/* 174 */       this.user = null;
/*     */     } 
/*     */     
/* 177 */     if (this.ac == null && this.world.isRemote) {
/* 178 */       int id = ((Integer)this.dataManager.get(AC_ID)).intValue();
/* 179 */       if (id > 0) {
/* 180 */         Entity entity = this.world.getEntityByID(id);
/* 181 */         if (entity instanceof MCH_EntityAircraft) {
/* 182 */           this.ac = (MCH_EntityAircraft)entity;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     this.prevPosX = this.posX;
/* 188 */     this.prevPosY = this.posY;
/* 189 */     this.prevPosZ = this.posZ;
/* 190 */     this.fallDistance = 0.0F;
/* 191 */     Entity riddenByEntity = getRiddenByEntity();
/* 192 */     if (riddenByEntity != null) {
/* 193 */       riddenByEntity.fallDistance = 0.0F;
/*     */     }
/*     */     
/* 196 */     if (this.ac != null) {
/* 197 */       if (!this.ac.isRepelling()) {
/* 198 */         setDead();
/*     */       }
/*     */       
/* 201 */       int id = getRopeIndex();
/* 202 */       if (id >= 0) {
/* 203 */         Vec3d v = this.ac.getRopePos(id);
/* 204 */         this.posX = v.x;
/* 205 */         this.posZ = v.z;
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     setPosition(this.posX, this.posY, this.posZ);
/* 210 */     if (this.world.isRemote) {
/* 211 */       onUpdateClient();
/*     */     } else {
/* 213 */       onUpdateServer();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdateClient() {
/* 218 */     if (this.paraPosRotInc > 0) {
/* 219 */       double x = this.posX + (this.paraX - this.posX) / this.paraPosRotInc;
/* 220 */       double y = this.posY + (this.paraY - this.posY) / this.paraPosRotInc;
/* 221 */       double z = this.posZ + (this.paraZ - this.posZ) / this.paraPosRotInc;
/* 222 */       double yaw = MathHelper.wrapDegrees(this.paraYaw - this.rotationYaw);
/* 223 */       this.rotationYaw = (float)(this.rotationYaw + yaw / this.paraPosRotInc);
/* 224 */       this.rotationPitch = (float)(this.rotationPitch + (this.paraPitch - this.rotationPitch) / this.paraPosRotInc);
/* 225 */       this.paraPosRotInc--;
/* 226 */       setPosition(x, y, z);
/* 227 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 228 */       Entity riddenByEntity = getRiddenByEntity();
/* 229 */       if (riddenByEntity != null) {
/* 230 */         setRotation(riddenByEntity.prevRotationYaw, this.rotationPitch);
/*     */       }
/*     */     } else {
/* 233 */       setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 234 */       this.motionX *= 0.99D;
/* 235 */       this.motionY *= 0.95D;
/* 236 */       this.motionZ *= 0.99D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdateServer() {
/* 241 */     this.motionY -= this.onGround ? 0.01D : 0.03D;
/* 242 */     if (this.onGround) {
/* 243 */       onGroundAndDead();
/*     */     } else {
/* 245 */       move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 246 */       this.motionY *= 0.9D;
/* 247 */       this.motionX *= 0.95D;
/* 248 */       this.motionZ *= 0.95D;
/* 249 */       int id = getRopeIndex();
/* 250 */       if (this.ac != null && id >= 0) {
/* 251 */         Vec3d v = this.ac.getRopePos(id);
/* 252 */         if (Math.abs(this.posY - v.y) > (Math.abs(this.ac.ropesLength) + 5.0F)) {
/* 253 */           onGroundAndDead();
/*     */         }
/*     */       } 
/*     */       
/* 257 */       Entity riddenByEntity = getRiddenByEntity();
/* 258 */       if (riddenByEntity != null && riddenByEntity.isDead) {
/* 259 */         setDead();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean getCollisionBoxes(@Nullable Entity entityIn, AxisAlignedBB aabb, List<AxisAlignedBB> outList) {
/* 265 */     int i = MathHelper.floor(aabb.minX) - 1;
/* 266 */     int j = MathHelper.ceil(aabb.maxX) + 1;
/* 267 */     int k = MathHelper.floor(aabb.minY) - 1;
/* 268 */     int l = MathHelper.ceil(aabb.maxY) + 1;
/* 269 */     int i1 = MathHelper.floor(aabb.minZ) - 1;
/* 270 */     int j1 = MathHelper.ceil(aabb.maxZ) + 1;
/* 271 */     WorldBorder worldborder = this.world.getWorldBorder();
/* 272 */     boolean flag = (entityIn != null && entityIn.isOutsideBorder());
/* 273 */     boolean flag1 = (entityIn != null && this.world.isInsideWorldBorder(entityIn));
/* 274 */     IBlockState iblockstate = Blocks.STONE.getDefaultState();
/* 275 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*     */     
/*     */     try {
/* 278 */       for (int k1 = i; k1 < j; k1++) {
/* 279 */         for (int l1 = i1; l1 < j1; l1++) {
/* 280 */           boolean flag2 = (k1 == i || k1 == j - 1);
/* 281 */           boolean flag3 = (l1 == i1 || l1 == j1 - 1);
/* 282 */           if ((!flag2 || !flag3) && this.world.isBlockLoaded((BlockPos)blockpos$pooledmutableblockpos.setPos(k1, 64, l1))) {
/* 283 */             for (int i2 = k; i2 < l; i2++) {
/* 284 */               if ((!flag2 && !flag3) || i2 != l - 1) {
/* 285 */                 IBlockState iblockstate1; if (entityIn != null && flag == flag1) {
/* 286 */                   entityIn.setOutsideBorder(!flag1);
/*     */                 }
/*     */                 
/* 289 */                 blockpos$pooledmutableblockpos.setPos(k1, i2, l1);
/*     */                 
/* 291 */                 if (!worldborder.contains((BlockPos)blockpos$pooledmutableblockpos) && flag1) {
/* 292 */                   iblockstate1 = iblockstate;
/*     */                 } else {
/* 294 */                   iblockstate1 = this.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos);
/*     */                 } 
/*     */                 
/* 297 */                 iblockstate1.addCollisionBoxToList(this.world, (BlockPos)blockpos$pooledmutableblockpos, aabb, outList, entityIn, false);
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 304 */       blockpos$pooledmutableblockpos.release();
/*     */     } 
/*     */     
/* 307 */     return !outList.isEmpty();
/*     */   }
/*     */   
/*     */   public List<AxisAlignedBB> getCollidingBoundingBoxes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB) {
/* 311 */     List<AxisAlignedBB> list = new ArrayList<>();
/* 312 */     getCollisionBoxes(par1Entity, par2AxisAlignedBB, list);
/* 313 */     if (par1Entity != null) {
/* 314 */       List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB.grow(0.25D));
/*     */       
/* 316 */       for (Entity entity : list1) {
/* 317 */         if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_EntitySeat) && !(entity instanceof MCH_EntityHitBox)) {
/* 318 */           AxisAlignedBB axisalignedbb = entity.getCollisionBoundingBox();
/* 319 */           if (axisalignedbb != null && axisalignedbb.intersects(par2AxisAlignedBB)) {
/* 320 */             list.add(axisalignedbb);
/*     */           }
/*     */           
/* 323 */           axisalignedbb = par1Entity.getCollisionBox(entity);
/* 324 */           if (axisalignedbb != null && axisalignedbb.intersects(par2AxisAlignedBB)) {
/* 325 */             list.add(axisalignedbb);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 331 */     return list;
/*     */   }
/*     */   
/*     */   public void move(@NotNull MoverType type, double x, double y, double z) {
/* 335 */     this.world.profiler.startSection("move");
/* 336 */     double d2 = x;
/* 337 */     double d3 = y;
/* 338 */     double d4 = z;
/* 339 */     List<AxisAlignedBB> list1 = getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox().expand(x, y, z));
/* 340 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/* 341 */     if (y != 0.0D) {
/* 342 */       int k = 0;
/*     */       
/* 344 */       for (int l = list1.size(); k < l; k++) {
/* 345 */         y = ((AxisAlignedBB)list1.get(k)).calculateYOffset(getEntityBoundingBox(), y);
/*     */       }
/*     */       
/* 348 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*     */     } 
/*     */     
/* 351 */     boolean flag = (this.onGround || (d3 != y && d3 < 0.0D));
/* 352 */     if (x != 0.0D) {
/* 353 */       int j5 = 0;
/*     */       
/* 355 */       for (int l5 = list1.size(); j5 < l5; j5++) {
/* 356 */         x = ((AxisAlignedBB)list1.get(j5)).calculateXOffset(getEntityBoundingBox(), x);
/*     */       }
/*     */       
/* 359 */       if (x != 0.0D) {
/* 360 */         setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
/*     */       }
/*     */     } 
/*     */     
/* 364 */     if (z != 0.0D) {
/* 365 */       int k5 = 0;
/*     */       
/* 367 */       for (int i6 = list1.size(); k5 < i6; k5++) {
/* 368 */         z = ((AxisAlignedBB)list1.get(k5)).calculateZOffset(getEntityBoundingBox(), z);
/*     */       }
/*     */       
/* 371 */       if (z != 0.0D) {
/* 372 */         setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
/*     */       }
/*     */     } 
/*     */     
/* 376 */     if (this.stepHeight > 0.0F && flag && (d2 != x || d4 != z)) {
/* 377 */       double d14 = x;
/* 378 */       double d6 = y;
/* 379 */       double d7 = z;
/* 380 */       y = this.stepHeight;
/* 381 */       AxisAlignedBB axisalignedbb1 = getEntityBoundingBox();
/* 382 */       setEntityBoundingBox(axisalignedbb);
/* 383 */       List<AxisAlignedBB> list = getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox().expand(d2, y, d4));
/* 384 */       AxisAlignedBB axisalignedbb2 = getEntityBoundingBox();
/* 385 */       AxisAlignedBB axisalignedbb3 = axisalignedbb2.expand(d2, 0.0D, d4);
/* 386 */       double d8 = y;
/*     */       
/* 388 */       for (AxisAlignedBB axisAlignedBB1 : list) {
/* 389 */         d8 = axisAlignedBB1.calculateYOffset(axisalignedbb3, d8);
/*     */       }
/*     */       
/* 392 */       axisalignedbb2 = axisalignedbb2.offset(0.0D, d8, 0.0D);
/* 393 */       double d18 = d2;
/*     */       
/* 395 */       for (AxisAlignedBB element : list) {
/* 396 */         d18 = element.calculateXOffset(axisalignedbb2, d18);
/*     */       }
/*     */       
/* 399 */       axisalignedbb2 = axisalignedbb2.offset(d18, 0.0D, 0.0D);
/* 400 */       double d19 = d4;
/*     */       
/* 402 */       for (AxisAlignedBB item : list) {
/* 403 */         d19 = item.calculateZOffset(axisalignedbb2, d19);
/*     */       }
/*     */       
/* 406 */       axisalignedbb2 = axisalignedbb2.offset(0.0D, 0.0D, d19);
/* 407 */       AxisAlignedBB axisalignedbb4 = getEntityBoundingBox();
/* 408 */       double d20 = y;
/*     */       
/* 410 */       for (AxisAlignedBB value : list) {
/* 411 */         d20 = value.calculateYOffset(axisalignedbb4, d20);
/*     */       }
/*     */       
/* 414 */       axisalignedbb4 = axisalignedbb4.offset(0.0D, d20, 0.0D);
/* 415 */       double d21 = d2;
/*     */       
/* 417 */       for (AxisAlignedBB bb : list) {
/* 418 */         d21 = bb.calculateXOffset(axisalignedbb4, d21);
/*     */       }
/*     */       
/* 421 */       axisalignedbb4 = axisalignedbb4.offset(d21, 0.0D, 0.0D);
/* 422 */       double d22 = d4;
/*     */       
/* 424 */       for (AxisAlignedBB alignedBB : list) {
/* 425 */         d22 = alignedBB.calculateZOffset(axisalignedbb4, d22);
/*     */       }
/*     */       
/* 428 */       axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d22);
/* 429 */       double d23 = d18 * d18 + d19 * d19;
/* 430 */       double d9 = d21 * d21 + d22 * d22;
/* 431 */       if (d23 > d9) {
/* 432 */         x = d18;
/* 433 */         z = d19;
/* 434 */         y = -d8;
/* 435 */         setEntityBoundingBox(axisalignedbb2);
/*     */       } else {
/* 437 */         x = d21;
/* 438 */         z = d22;
/* 439 */         y = -d20;
/* 440 */         setEntityBoundingBox(axisalignedbb4);
/*     */       } 
/*     */       
/* 443 */       for (AxisAlignedBB axisAlignedBB : list) {
/* 444 */         y = axisAlignedBB.calculateYOffset(getEntityBoundingBox(), y);
/*     */       }
/*     */       
/* 447 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/* 448 */       if (d14 * d14 + d7 * d7 >= x * x + z * z) {
/* 449 */         x = d14;
/* 450 */         y = d6;
/* 451 */         z = d7;
/* 452 */         setEntityBoundingBox(axisalignedbb1);
/*     */       } 
/*     */     } 
/*     */     
/* 456 */     this.world.profiler.endSection();
/* 457 */     this.world.profiler.startSection("rest");
/* 458 */     resetPositionToBB();
/* 459 */     this.collidedHorizontally = (d2 != x || d4 != z);
/* 460 */     this.collidedVertically = (d3 != y);
/* 461 */     this.onGround = (this.collidedVertically && d3 < 0.0D);
/* 462 */     this.collided = (this.collidedHorizontally || this.collidedVertically);
/* 463 */     int j6 = MathHelper.floor(this.posX);
/* 464 */     int i1 = MathHelper.floor(this.posY - 0.20000000298023224D);
/* 465 */     int k6 = MathHelper.floor(this.posZ);
/* 466 */     BlockPos blockpos = new BlockPos(j6, i1, k6);
/* 467 */     IBlockState iblockstate = this.world.getBlockState(blockpos);
/* 468 */     if (iblockstate.getMaterial() == Material.AIR) {
/* 469 */       BlockPos blockpos1 = blockpos.down();
/* 470 */       IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
/* 471 */       Block block1 = iblockstate1.getBlock();
/* 472 */       if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/* 473 */         iblockstate = iblockstate1;
/* 474 */         blockpos = blockpos1;
/*     */       } 
/*     */     } 
/*     */     
/* 478 */     updateFallState(y, this.onGround, iblockstate, blockpos);
/* 479 */     if (d2 != x) {
/* 480 */       this.motionX = 0.0D;
/*     */     }
/*     */     
/* 483 */     if (d4 != z) {
/* 484 */       this.motionZ = 0.0D;
/*     */     }
/*     */     
/* 487 */     Block block = iblockstate.getBlock();
/* 488 */     if (d3 != y) {
/* 489 */       block.onLanded(this.world, (Entity)this);
/*     */     }
/*     */     
/*     */     try {
/* 493 */       doBlockCollisions();
/* 494 */     } catch (Throwable var45) {
/* 495 */       CrashReport crashreport = CrashReport.makeCrashReport(var45, "Checking entity block collision");
/* 496 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
/* 497 */       addEntityCrashInfo(crashreportcategory);
/* 498 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */     
/* 501 */     this.world.profiler.endSection();
/*     */   }
/*     */   
/*     */   public void onGroundAndDead() {
/* 505 */     this.posY += 0.5D;
/* 506 */     updatePassenger(getRiddenByEntity());
/* 507 */     setDead();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 513 */     List<Entity> passengers = getPassengers();
/* 514 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_EntityHide.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */