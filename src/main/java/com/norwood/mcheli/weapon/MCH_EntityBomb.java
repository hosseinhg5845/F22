/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_Lib;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_EntityBomb
/*    */   extends MCH_EntityBaseBullet {
/*    */   public MCH_EntityBomb(World par1World) {
/* 15 */     super(par1World);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_EntityBomb(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/* 21 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 26 */     super.onUpdate();
/* 27 */     if (!this.world.isRemote && getInfo() != null) {
/* 28 */       this.motionX *= 0.999D;
/* 29 */       this.motionZ *= 0.999D;
/* 30 */       if (isInWater()) {
/* 31 */         this.motionX *= (getInfo()).velocityInWater;
/* 32 */         this.motionY *= (getInfo()).velocityInWater;
/* 33 */         this.motionZ *= (getInfo()).velocityInWater;
/*    */       } 
/*    */       
/* 36 */       float dist = (getInfo()).proximityFuseDist;
/* 37 */       if (dist > 0.1F && getCountOnUpdate() % 10 == 0) {
/* 38 */         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(dist, dist, dist));
/* 39 */         for (Entity entity : list) {
/* 40 */           if (W_Lib.isEntityLivingBase(entity) && canBeCollidedEntity(entity)) {
/* 41 */             RayTraceResult m = new RayTraceResult(new Vec3d(this.posX, this.posY, this.posZ), EnumFacing.DOWN, new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D));
/*    */ 
/*    */             
/* 44 */             onImpact(m, 1.0F);
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 51 */     onUpdateBomblet();
/*    */   }
/*    */ 
/*    */   
/*    */   public void sprinkleBomblet() {
/* 56 */     if (!this.world.isRemote) {
/*    */       
/* 58 */       MCH_EntityBomb e = new MCH_EntityBomb(this.world, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, this.rand.nextInt(360), 0.0F, this.acceleration);
/*    */       
/* 60 */       e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
/* 61 */       e.setName(getName());
/* 62 */       float RANDOM = (getInfo()).bombletDiff;
/* 63 */       this.motionX += ((this.rand.nextFloat() - 0.5F) * RANDOM);
/* 64 */       e.motionY = this.motionY / 2.0D + ((this.rand.nextFloat() - 0.5F) * RANDOM / 2.0F);
/* 65 */       this.motionZ += ((this.rand.nextFloat() - 0.5F) * RANDOM);
/* 66 */       e.setBomblet();
/* 67 */       this.world.spawnEntity((Entity)e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_BulletModel getDefaultBulletModel() {
/* 73 */     return MCH_DefaultBulletModels.Bomb;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityBomb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */