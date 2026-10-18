/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_EntityBullet
/*     */   extends MCH_EntityBaseBullet {
/*     */   public MCH_EntityBullet(World par1World) {
/*  19 */     super(par1World);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityBullet(World par1World, double pX, double pY, double pZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/*  25 */     super(par1World, pX, pY, pZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  30 */     super.onUpdate();
/*  31 */     if (!this.isDead && !this.world.isRemote && getCountOnUpdate() > 1 && getInfo() != null && this.explosionPower > 0) {
/*  32 */       float pDist = (getInfo()).proximityFuseDist;
/*  33 */       if (pDist > 0.1D) {
/*  34 */         float rng = ++pDist + MathHelper.abs((getInfo()).acceleration);
/*  35 */         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(rng, rng, rng));
/*     */         
/*  37 */         for (Entity entity1 : list) {
/*  38 */           if (canBeCollidedEntity(entity1) && entity1.getDistanceSq((Entity)this) < (pDist * pDist)) {
/*  39 */             MCH_Lib.DbgLog(this.world, "MCH_EntityBullet.onUpdate:proximityFuse:" + entity1, new Object[0]);
/*  40 */             this.posX = (entity1.posX + this.posX) / 2.0D;
/*  41 */             this.posY = (entity1.posY + this.posY) / 2.0D;
/*  42 */             this.posZ = (entity1.posZ + this.posZ) / 2.0D;
/*  43 */             RayTraceResult mop = W_MovingObjectPosition.newMOP((int)this.posX, (int)this.posY, (int)this.posZ, 0, 
/*  44 */                 W_WorldFunc.getWorldVec3EntityPos((Entity)this), false);
/*     */             
/*  46 */             onImpact(mop, 1.0F);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUpdateCollided() {
/*  56 */     double mx = this.motionX * this.accelerationFactor;
/*  57 */     double my = this.motionY * this.accelerationFactor;
/*  58 */     double mz = this.motionZ * this.accelerationFactor;
/*  59 */     float damageFactor = 1.0F;
/*  60 */     RayTraceResult m = null;
/*     */     
/*  62 */     for (int i = 0; i < 5; i++) {
/*  63 */       Vec3d vec3 = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
/*  64 */       Vec3d vec31 = W_WorldFunc.getWorldVec3(this.world, this.posX + mx, this.posY + my, this.posZ + mz);
/*  65 */       m = W_WorldFunc.clip(this.world, vec3, vec31);
/*  66 */       boolean continueClip = false;
/*  67 */       if (this.shootingEntity != null && W_MovingObjectPosition.isHitTypeTile(m)) {
/*  68 */         Block block = W_WorldFunc.getBlock(this.world, m.getBlockPos());
/*  69 */         if (MCH_Config.bulletBreakableBlocks.contains(block)) {
/*  70 */           W_WorldFunc.destroyBlock(this.world, m.getBlockPos(), true);
/*  71 */           continueClip = true;
/*     */         } 
/*     */       } 
/*     */       
/*  75 */       if (!continueClip) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/*  80 */     Vec3d vec3x = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
/*  81 */     Vec3d vec31x = W_WorldFunc.getWorldVec3(this.world, this.posX + mx, this.posY + my, this.posZ + mz);
/*  82 */     if ((getInfo()).delayFuse > 0) {
/*  83 */       if (m != null) {
/*  84 */         boundBullet(m.sideHit);
/*  85 */         if (this.delayFuse == 0) {
/*  86 */           this.delayFuse = (getInfo()).delayFuse;
/*     */         }
/*     */       } 
/*     */     } else {
/*  90 */       if (m != null) {
/*  91 */         vec31x = W_WorldFunc.getWorldVec3(this.world, m.hitVec.x, m.hitVec.y, m.hitVec.z);
/*     */       }
/*     */       
/*  94 */       Entity entity = null;
/*  95 */       List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().expand(mx, my, mz).grow(21.0D, 21.0D, 21.0D));
/*  96 */       double d0 = 0.0D;
/*     */       
/*  98 */       for (Entity entity1 : list) {
/*  99 */         if (canBeCollidedEntity(entity1)) {
/* 100 */           float f = 0.3F;
/* 101 */           AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(f, f, f);
/* 102 */           RayTraceResult m1 = axisalignedbb.calculateIntercept(vec3x, vec31x);
/* 103 */           if (m1 != null) {
/* 104 */             double d1 = vec3x.distanceTo(m1.hitVec);
/* 105 */             if (d1 < d0 || d0 == 0.0D) {
/* 106 */               entity = entity1;
/* 107 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 113 */       if (entity != null) {
/* 114 */         m = new RayTraceResult(entity);
/*     */       }
/*     */       
/* 117 */       if (m != null) {
/* 118 */         onImpact(m, damageFactor);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_BulletModel getDefaultBulletModel() {
/* 125 */     return MCH_DefaultBulletModels.Bullet;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */