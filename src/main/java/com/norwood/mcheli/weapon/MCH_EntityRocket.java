/*    */ package com.norwood.mcheli.weapon;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_EntityRocket extends MCH_EntityBaseBullet {
/*    */   public MCH_EntityRocket(World par1World) {
/*  7 */     super(par1World);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_EntityRocket(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/* 13 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 18 */     super.onUpdate();
/* 19 */     onUpdateBomblet();
/* 20 */     if (this.isBomblet <= 0 && getInfo() != null && !(getInfo()).disableSmoke) {
/* 21 */       spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void sprinkleBomblet() {
/* 27 */     if (!this.world.isRemote) {
/* 28 */       MCH_EntityRocket e = new MCH_EntityRocket(this.world, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, this.rotationYaw, this.rotationPitch, this.acceleration);
/*    */ 
/*    */       
/* 31 */       e.setName(getName());
/* 32 */       e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
/* 33 */       float MOTION = (getInfo()).bombletDiff;
/* 34 */       e.motionX += (this.rand.nextFloat() - 0.5D) * MOTION;
/* 35 */       e.motionY += (this.rand.nextFloat() - 0.5D) * MOTION;
/* 36 */       e.motionZ += (this.rand.nextFloat() - 0.5D) * MOTION;
/* 37 */       e.setBomblet();
/* 38 */       this.world.spawnEntity((Entity)e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_BulletModel getDefaultBulletModel() {
/* 44 */     return MCH_DefaultBulletModels.Rocket;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityRocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */