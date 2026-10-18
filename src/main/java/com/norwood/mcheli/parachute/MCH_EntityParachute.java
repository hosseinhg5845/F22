/*     */ package com.norwood.mcheli.parachute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.helper.entity.IEntitySinglePassenger;
/*     */ import com.norwood.mcheli.particles.MCH_ParticleParam;
/*     */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*     */ import com.norwood.mcheli.wrapper.W_AxisAlignedBB;
/*     */ import com.norwood.mcheli.wrapper.W_Block;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityParachute extends W_Entity implements IEntitySinglePassenger {
/*  31 */   private static final DataParameter<Byte> TYPE = EntityDataManager.createKey(MCH_EntityParachute.class, DataSerializers.BYTE);
/*     */   public Entity user;
/*     */   public int onGroundCount;
/*  34 */   private double speedMultiplier = 0.07D;
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
/*     */   public MCH_EntityParachute(World par1World) {
/*  49 */     super(par1World);
/*  50 */     this.preventEntitySpawning = true;
/*  51 */     setSize(1.5F, 0.6F);
/*  52 */     this.user = null;
/*  53 */     this.onGroundCount = 0;
/*     */   }
/*     */   
/*     */   public MCH_EntityParachute(World par1World, double par2, double par4, double par6) {
/*  57 */     this(par1World);
/*  58 */     setPosition(par2, par4, par6);
/*  59 */     this.motionX = 0.0D;
/*  60 */     this.motionY = 0.0D;
/*  61 */     this.motionZ = 0.0D;
/*  62 */     this.prevPosX = par2;
/*  63 */     this.prevPosY = par4;
/*  64 */     this.prevPosZ = par6;
/*     */   }
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  73 */     this.dataManager.register(TYPE, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public int getType() {
/*  77 */     return ((Byte)this.dataManager.get(TYPE)).byteValue();
/*     */   }
/*     */   
/*     */   public void setType(int n) {
/*  81 */     this.dataManager.set(TYPE, Byte.valueOf((byte)n));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBox(Entity par1Entity) {
/*  85 */     return par1Entity.getEntityBoundingBox();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox() {
/*  89 */     return getEntityBoundingBox();
/*     */   }
/*     */   
/*     */   public boolean canBePushed() {
/*  93 */     return true;
/*     */   }
/*     */   
/*     */   public double getMountedYOffset() {
/*  97 */     return this.height * 0.0D - 0.30000001192092896D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 106 */     return !this.isDead;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/* 111 */     this.paraPosRotInc = posRotationIncrements + 10;
/* 112 */     this.paraX = x;
/* 113 */     this.paraY = y;
/* 114 */     this.paraZ = z;
/* 115 */     this.paraYaw = yaw;
/* 116 */     this.paraPitch = pitch;
/* 117 */     this.motionX = this.velocityX;
/* 118 */     this.motionY = this.velocityY;
/* 119 */     this.motionZ = this.velocityZ;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setVelocity(double par1, double par3, double par5) {
/* 124 */     this.velocityX = this.motionX = par1;
/* 125 */     this.velocityY = this.motionY = par3;
/* 126 */     this.velocityZ = this.motionZ = par5;
/*     */   }
/*     */   
/*     */   public void setDead() {
/* 130 */     super.setDead();
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 134 */     super.onUpdate();
/* 135 */     if (!this.world.isRemote && this.ticksExisted % 10 == 0) {
/* 136 */       MCH_Lib.DbgLog(this.world, "MCH_EntityParachute.onUpdate %d, %.3f", new Object[] { Integer.valueOf(this.ticksExisted), Double.valueOf(this.motionY) });
/*     */     }
/*     */     
/* 139 */     if (isOpenParachute() && this.motionY > -0.3D && this.ticksExisted > 20) {
/* 140 */       this.fallDistance = (float)(this.fallDistance * 0.85D);
/*     */     }
/*     */     
/* 143 */     if (!this.world.isRemote && this.user != null && this.user.getRidingEntity() == null) {
/* 144 */       this.user.startRiding((Entity)this);
/* 145 */       this.rotationYaw = this.prevRotationYaw = this.user.rotationYaw;
/* 146 */       this.user = null;
/*     */     } 
/*     */     
/* 149 */     this.prevPosX = this.posX;
/* 150 */     this.prevPosY = this.posY;
/* 151 */     this.prevPosZ = this.posZ;
/* 152 */     double d1 = (getEntityBoundingBox()).minY + ((getEntityBoundingBox()).maxY - (getEntityBoundingBox()).minY) * 0.0D / 5.0D - 0.125D;
/* 153 */     double d2 = (getEntityBoundingBox()).minY + ((getEntityBoundingBox()).maxY - (getEntityBoundingBox()).minY) / 5.0D - 0.125D;
/* 154 */     AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(
/* 155 */         (getEntityBoundingBox()).minX, d1, (getEntityBoundingBox()).minZ, (getEntityBoundingBox()).maxX, d2, (getEntityBoundingBox()).maxZ);
/*     */     
/* 157 */     if (this.world.isMaterialInBB(axisalignedbb, Material.WATER)) {
/* 158 */       onWaterSetBoat();
/* 159 */       setDead();
/*     */     } 
/*     */     
/* 162 */     if (this.world.isRemote) {
/* 163 */       onUpdateClient();
/*     */     } else {
/* 165 */       onUpdateServer();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdateClient() {
/* 170 */     if (this.paraPosRotInc > 0) {
/* 171 */       double x = this.posX + (this.paraX - this.posX) / this.paraPosRotInc;
/* 172 */       double y = this.posY + (this.paraY - this.posY) / this.paraPosRotInc;
/* 173 */       double z = this.posZ + (this.paraZ - this.posZ) / this.paraPosRotInc;
/* 174 */       double yaw = MathHelper.wrapDegrees(this.paraYaw - this.rotationYaw);
/* 175 */       this.rotationYaw = (float)(this.rotationYaw + yaw / this.paraPosRotInc);
/* 176 */       this.rotationPitch = (float)(this.rotationPitch + (this.paraPitch - this.rotationPitch) / this.paraPosRotInc);
/* 177 */       this.paraPosRotInc--;
/* 178 */       setPosition(x, y, z);
/* 179 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 180 */       if (getRiddenByEntity() != null) {
/* 181 */         setRotation((getRiddenByEntity()).prevRotationYaw, this.rotationPitch);
/*     */       }
/*     */     } else {
/* 184 */       setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 185 */       if (this.onGround);
/*     */ 
/*     */       
/* 188 */       this.motionX *= 0.99D;
/* 189 */       this.motionY *= 0.95D;
/* 190 */       this.motionZ *= 0.99D;
/*     */     } 
/*     */     
/* 193 */     if (!isOpenParachute() && this.motionY > 0.01D) {
/* 194 */       float color = 0.6F + this.rand.nextFloat() * 0.2F;
/* 195 */       double dx = this.prevPosX - this.posX;
/* 196 */       double dy = this.prevPosY - this.posY;
/* 197 */       double dz = this.prevPosZ - this.posZ;
/* 198 */       int num = 1 + (int)(MathHelper.sqrt(dx * dx + dy * dy + dz * dz) * 2.0D);
/*     */       double i;
/* 200 */       for (i = 0.0D; i < num; i++) {
/* 201 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", this.prevPosX + (this.posX - this.prevPosX) * i / num * 0.8D, this.prevPosY + (this.posY - this.prevPosY) * i / num * 0.8D, this.prevPosZ + (this.posZ - this.prevPosZ) * i / num * 0.8D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 208 */         prm.motionX = this.motionX * 0.5D + (this.rand.nextDouble() - 0.5D) * 0.5D;
/* 209 */         prm.motionX = this.motionY * -0.5D + (this.rand.nextDouble() - 0.5D) * 0.5D;
/* 210 */         prm.motionX = this.motionZ * 0.5D + (this.rand.nextDouble() - 0.5D) * 0.5D;
/* 211 */         prm.size = 5.0F;
/* 212 */         prm.setColor(0.8F + this.rand.nextFloat(), color, color, color);
/* 213 */         MCH_ParticlesUtil.spawnParticle(prm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdateServer() {
/* 219 */     double prevSpeed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 220 */     double gravity = this.onGround ? 0.01D : 0.03D;
/* 221 */     if (getType() == 2 && this.ticksExisted < 20) {
/* 222 */       gravity = 0.01D;
/*     */     }
/*     */     
/* 225 */     this.motionY -= gravity;
/* 226 */     if (isOpenParachute()) {
/* 227 */       if (W_Lib.isEntityLivingBase(getRiddenByEntity())) {
/* 228 */         double mv = W_Lib.getEntityMoveDist(getRiddenByEntity());
/* 229 */         if (!isOpenParachute()) {
/* 230 */           mv = 0.0D;
/*     */         }
/*     */         
/* 233 */         if (mv > 0.0D) {
/* 234 */           double mx = -Math.sin(((getRiddenByEntity()).rotationYaw * 3.1415927F / 180.0F));
/* 235 */           double mz = Math.cos(((getRiddenByEntity()).rotationYaw * 3.1415927F / 180.0F));
/* 236 */           this.motionX += mx * this.speedMultiplier * 0.05D;
/* 237 */           this.motionZ += mz * this.speedMultiplier * 0.05D;
/*     */         } 
/*     */       } 
/*     */       
/* 241 */       double speed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 242 */       if (speed > 0.35D) {
/* 243 */         this.motionX *= 0.35D / speed;
/* 244 */         this.motionZ *= 0.35D / speed;
/* 245 */         speed = 0.35D;
/*     */       } 
/*     */       
/* 248 */       if (speed > prevSpeed && this.speedMultiplier < 0.35D) {
/* 249 */         this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
/* 250 */         if (this.speedMultiplier > 0.35D) {
/* 251 */           this.speedMultiplier = 0.35D;
/*     */         }
/*     */       } else {
/* 254 */         this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
/* 255 */         if (this.speedMultiplier < 0.07D) {
/* 256 */           this.speedMultiplier = 0.07D;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 261 */     if (this.onGround) {
/* 262 */       this.onGroundCount++;
/* 263 */       if (this.onGroundCount > 5) {
/* 264 */         onGroundAndDead();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 269 */     move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 270 */     if (getType() == 2 && this.ticksExisted < 20) {
/* 271 */       this.motionY *= 0.95D;
/*     */     } else {
/* 273 */       this.motionY *= 0.9D;
/*     */     } 
/*     */     
/* 276 */     if (isOpenParachute()) {
/* 277 */       this.motionX *= 0.95D;
/* 278 */       this.motionZ *= 0.95D;
/*     */     } else {
/* 280 */       this.motionX *= 0.99D;
/* 281 */       this.motionZ *= 0.99D;
/*     */     } 
/*     */     
/* 284 */     this.rotationPitch = 0.0F;
/* 285 */     double yaw = this.rotationYaw;
/* 286 */     double dx = this.prevPosX - this.posX;
/* 287 */     double dz = this.prevPosZ - this.posZ;
/* 288 */     if (dx * dx + dz * dz > 0.001D) {
/* 289 */       yaw = (float)(Math.atan2(dx, dz) * 180.0D / Math.PI);
/*     */     }
/*     */     
/* 292 */     double yawDiff = MathHelper.wrapDegrees(yaw - this.rotationYaw);
/* 293 */     if (yawDiff > 20.0D) {
/* 294 */       yawDiff = 20.0D;
/*     */     }
/*     */     
/* 297 */     if (yawDiff < -20.0D) {
/* 298 */       yawDiff = -20.0D;
/*     */     }
/*     */     
/* 301 */     if (getRiddenByEntity() != null) {
/* 302 */       setRotation((getRiddenByEntity()).rotationYaw, this.rotationPitch);
/*     */     } else {
/* 304 */       this.rotationYaw = (float)(this.rotationYaw + yawDiff);
/* 305 */       setRotation(this.rotationYaw, this.rotationPitch);
/*     */     } 
/*     */     
/* 308 */     List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(0.2D, 0.0D, 0.2D));
/* 309 */     if (!list.isEmpty()) {
/* 310 */       for (Entity entity : list) {
/* 311 */         if (entity != getRiddenByEntity() && entity.canBePushed() && entity instanceof MCH_EntityParachute) {
/* 312 */           entity.applyEntityCollision((Entity)this);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 317 */     if (getRiddenByEntity() != null && (getRiddenByEntity()).isDead) {
/* 318 */       setDead();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onGroundAndDead() {
/* 323 */     this.posY++;
/* 324 */     updatePassenger(getRiddenByEntity());
/* 325 */     setDead();
/*     */   }
/*     */   
/*     */   public void onWaterSetBoat() {
/* 329 */     if (!this.world.isRemote && 
/* 330 */       getType() == 2 && 
/* 331 */       getRiddenByEntity() != null) {
/* 332 */       int px = (int)(this.posX + 0.5D);
/* 333 */       int py = (int)(this.posY + 0.5D);
/* 334 */       int pz = (int)(this.posZ + 0.5D);
/* 335 */       boolean foundBlock = false;
/*     */       
/* 337 */       for (int y = 0; y < 5 && py + y >= 0 && py + y <= 255; y++) {
/* 338 */         Block block = W_WorldFunc.getBlock(this.world, px, py - y, pz);
/* 339 */         if (block == W_Block.getWater()) {
/* 340 */           py -= y;
/* 341 */           foundBlock = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 346 */       if (foundBlock) {
/* 347 */         int countWater = 0;
/*     */         
/* 349 */         for (int yx = 0; yx < 3 && py + yx >= 0 && py + yx <= 255; yx++) {
/* 350 */           for (int x = -2; x <= 2; x++) {
/* 351 */             for (int z = -2; z <= 2; z++) {
/* 352 */               Block block = W_WorldFunc.getBlock(this.world, px + x, py - yx, pz + z);
/* 353 */               if (block == W_Block.getWater() && 
/* 354 */                 ++countWater > 37) {
/*     */                 break;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 362 */         if (countWater > 37) {
/* 363 */           EntityBoat entityboat = new EntityBoat(this.world, px, (py + 1.0F), pz);
/* 364 */           entityboat.rotationYaw = this.rotationYaw - 90.0F;
/* 365 */           this.world.spawnEntity((Entity)entityboat);
/* 366 */           getRiddenByEntity().startRiding((Entity)entityboat);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpenParachute() {
/* 375 */     return (getType() != 2 || this.motionY < -0.1D);
/*     */   }
/*     */   
/*     */   public void updatePassenger(@NotNull Entity passenger) {
/* 379 */     if (isPassenger(passenger)) {
/* 380 */       double x = -Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.1D;
/* 381 */       double z = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.1D;
/* 382 */       passenger.setPosition(this.posX + x, this.posY + getMountedYOffset() + passenger.getYOffset(), this.posZ + z);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound nbt) {
/* 387 */     nbt.setByte("ParachuteModelType", (byte)getType());
/*     */   }
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound nbt) {
/* 391 */     setType(nbt.getByte("ParachuteModelType"));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 396 */     return 4.0F;
/*     */   }
/*     */   
/*     */   public boolean processInitialInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 400 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 406 */     List<Entity> passengers = getPassengers();
/* 407 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\parachute\MCH_EntityParachute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */