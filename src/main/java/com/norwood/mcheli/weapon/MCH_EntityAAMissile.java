/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_EntityAAMissile extends MCH_EntityBaseBullet {
/*    */   public MCH_EntityAAMissile(World par1World) {
/*  8 */     super(par1World);
/*  9 */     this.targetEntity = null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_EntityAAMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/* 15 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 20 */     super.onUpdate();
/* 21 */     if (getCountOnUpdate() > 4 && getInfo() != null && !(getInfo()).disableSmoke) {
/* 22 */       spawnParticle((getInfo()).trajectoryParticleName, 3, 7.0F * (getInfo()).smokeSize * 0.5F);
/*    */     }
/*    */     
/* 25 */     if (!this.world.isRemote && getInfo() != null) {
/* 26 */       if (this.shootingEntity != null && this.targetEntity != null && !this.targetEntity.isDead) {
/* 27 */         double x = this.posX - this.targetEntity.posX;
/* 28 */         double y = this.posY - this.targetEntity.posY;
/* 29 */         double z = this.posZ - this.targetEntity.posZ;
/* 30 */         double d = x * x + y * y + z * z;
/* 31 */         if (d > 3422500.0D) {
/* 32 */           setDead();
/* 33 */         } else if (getCountOnUpdate() > (getInfo()).rigidityTime) {
/* 34 */           if (usingFlareOfTarget(this.targetEntity)) {
/* 35 */             setDead();
/*    */             
/*    */             return;
/*    */           } 
/* 39 */           if ((getInfo()).proximityFuseDist >= 0.1F && d < (getInfo()).proximityFuseDist) {
/* 40 */             RayTraceResult mop = new RayTraceResult(this.targetEntity);
/* 41 */             this.posX = (this.targetEntity.posX + this.posX) / 2.0D;
/* 42 */             this.posY = (this.targetEntity.posY + this.posY) / 2.0D;
/* 43 */             this.posZ = (this.targetEntity.posZ + this.posZ) / 2.0D;
/* 44 */             onImpact(mop, 1.0F);
/*    */           } else {
/* 46 */             guidanceToTarget(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ);
/*    */           } 
/*    */         } 
/*    */       } else {
/* 50 */         setDead();
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_BulletModel getDefaultBulletModel() {
/* 57 */     return MCH_DefaultBulletModels.AAMissile;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityAAMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */