/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_EntityTvMissile extends MCH_EntityBaseBullet {
/*    */   public boolean isSpawnParticle = true;
/*    */   
/*    */   public MCH_EntityTvMissile(World par1World) {
/* 12 */     super(par1World);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_EntityTvMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/* 18 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 23 */     super.onUpdate();
/* 24 */     if (this.isSpawnParticle && getInfo() != null && !(getInfo()).disableSmoke) {
/* 25 */       spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);
/*    */     }
/*    */     
/* 28 */     if (this.shootingEntity != null) {
/* 29 */       double x = this.posX - this.shootingEntity.posX;
/* 30 */       double y = this.posY - this.shootingEntity.posY;
/* 31 */       double z = this.posZ - this.shootingEntity.posZ;
/* 32 */       if (x * x + y * y + z * z > 1440000.0D) {
/* 33 */         setDead();
/*    */       }
/*    */       
/* 36 */       if (!this.world.isRemote && !this.isDead) {
/* 37 */         onUpdateMotion();
/*    */       }
/* 39 */     } else if (!this.world.isRemote) {
/* 40 */       setDead();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onUpdateMotion() {
/* 45 */     Entity e = this.shootingEntity;
/* 46 */     if (e != null && !e.isDead) {
/* 47 */       MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl(e);
/* 48 */       if (ac != null && ac.getTVMissile() == this) {
/* 49 */         float yaw = e.rotationYaw;
/* 50 */         float pitch = e.rotationPitch;
/* 51 */         double tX = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/* 52 */         double tZ = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/* 53 */         double tY = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
/* 54 */         setMotion(tX, tY, tZ);
/* 55 */         setRotation(yaw, pitch);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_BulletModel getDefaultBulletModel() {
/* 62 */     return MCH_DefaultBulletModels.ATMissile;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityTvMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */