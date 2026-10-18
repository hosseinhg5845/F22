/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_EntityASMissile
/*     */   extends MCH_EntityBaseBullet {
/*     */   public double targetPosX;
/*     */   
/*     */   public MCH_EntityASMissile(World par1World) {
/*  15 */     super(par1World);
/*  16 */     this.targetPosX = 0.0D;
/*  17 */     this.targetPosY = 0.0D;
/*  18 */     this.targetPosZ = 0.0D;
/*     */   }
/*     */   public double targetPosY;
/*     */   public double targetPosZ;
/*     */   
/*     */   public MCH_EntityASMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/*  24 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getGravity() {
/*  29 */     return (getBomblet() == 1) ? -0.03F : super.getGravity();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getGravityInWater() {
/*  34 */     return (getBomblet() == 1) ? -0.03F : super.getGravityInWater();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  39 */     super.onUpdate();
/*  40 */     if (getInfo() != null && !(getInfo()).disableSmoke && getBomblet() == 0) {
/*  41 */       spawnParticle((getInfo()).trajectoryParticleName, 3, 10.0F * (getInfo()).smokeSize * 0.5F);
/*     */     }
/*     */     
/*  44 */     if (getInfo() != null && !this.world.isRemote && this.isBomblet != 1) {
/*  45 */       Block block = W_WorldFunc.getBlock(this.world, (int)this.targetPosX, (int)this.targetPosY, (int)this.targetPosZ);
/*  46 */       if (block.isCollidable()) {
/*  47 */         double dist = getDistance(this.targetPosX, this.targetPosY, this.targetPosZ);
/*  48 */         if (dist < (getInfo()).proximityFuseDist) {
/*  49 */           if ((getInfo()).bomblet > 0) {
/*  50 */             for (int i = 0; i < (getInfo()).bomblet; i++) {
/*  51 */               sprinkleBomblet();
/*     */             }
/*     */           } else {
/*  54 */             RayTraceResult mop = new RayTraceResult((Entity)this);
/*  55 */             onImpact(mop, 1.0F);
/*     */           } 
/*     */           
/*  58 */           setDead();
/*  59 */         } else if (getGravity() == 0.0D) {
/*  60 */           double up = 0.0D;
/*  61 */           if (getCountOnUpdate() < 10) {
/*  62 */             up = 20.0D;
/*     */           }
/*     */           
/*  65 */           double x = this.targetPosX - this.posX;
/*  66 */           double y = this.targetPosY + up - this.posY;
/*  67 */           double z = this.targetPosZ - this.posZ;
/*  68 */           double d = MathHelper.sqrt(x * x + y * y + z * z);
/*  69 */           this.motionX = x * this.acceleration / d;
/*  70 */           this.motionY = y * this.acceleration / d;
/*  71 */           this.motionZ = z * this.acceleration / d;
/*     */         } else {
/*  73 */           double x = this.targetPosX - this.posX;
/*  74 */           double y = this.targetPosY - this.posY;
/*  75 */           y *= 0.3D;
/*  76 */           double z = this.targetPosZ - this.posZ;
/*  77 */           double d = MathHelper.sqrt(x * x + y * y + z * z);
/*  78 */           this.motionX = x * this.acceleration / d;
/*  79 */           this.motionZ = z * this.acceleration / d;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     double a = (float)Math.atan2(this.motionZ, this.motionX);
/*  85 */     this.rotationYaw = (float)(a * 180.0D / Math.PI) - 90.0F;
/*  86 */     double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  87 */     this.rotationPitch = -((float)(Math.atan2(this.motionY, r) * 180.0D / Math.PI));
/*  88 */     onUpdateBomblet();
/*     */   }
/*     */ 
/*     */   
/*     */   public void sprinkleBomblet() {
/*  93 */     if (!this.world.isRemote) {
/*     */       
/*  95 */       MCH_EntityASMissile e = new MCH_EntityASMissile(this.world, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, this.rand.nextInt(360), 0.0F, this.acceleration);
/*     */       
/*  97 */       e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
/*  98 */       e.setName(getName());
/*  99 */       float RANDOM = (getInfo()).bombletDiff;
/* 100 */       e.motionX = this.motionX * 0.5D + ((this.rand.nextFloat() - 0.5F) * RANDOM);
/* 101 */       e.motionY = this.motionY * 0.5D / 2.0D + ((this.rand.nextFloat() - 0.5F) * RANDOM / 2.0F);
/* 102 */       e.motionZ = this.motionZ * 0.5D + ((this.rand.nextFloat() - 0.5F) * RANDOM);
/* 103 */       e.setBomblet();
/* 104 */       this.world.spawnEntity((Entity)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_BulletModel getDefaultBulletModel() {
/* 110 */     return MCH_DefaultBulletModels.ASMissile;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityASMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */