/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_EntityTorpedo extends MCH_EntityBaseBullet {
/*    */   public double targetPosX;
/*    */   public double targetPosY;
/*    */   public double targetPosZ;
/* 10 */   public double accelerationInWater = 2.0D;
/*    */   
/*    */   public MCH_EntityTorpedo(World par1World) {
/* 13 */     super(par1World);
/* 14 */     this.targetPosX = 0.0D;
/* 15 */     this.targetPosY = 0.0D;
/* 16 */     this.targetPosZ = 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_EntityTorpedo(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/* 22 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 27 */     super.onUpdate();
/* 28 */     if (getInfo() != null && (getInfo()).isGuidedTorpedo) {
/* 29 */       onUpdateGuided();
/*    */     } else {
/* 31 */       onUpdateNoGuided();
/*    */     } 
/*    */     
/* 34 */     if (isInWater() && getInfo() != null && !(getInfo()).disableSmoke) {
/* 35 */       spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);
/*    */     }
/*    */   }
/*    */   
/*    */   private void onUpdateNoGuided() {
/* 40 */     if (!this.world.isRemote && isInWater()) {
/* 41 */       this.motionY *= 0.800000011920929D;
/* 42 */       if (this.acceleration < this.accelerationInWater) {
/* 43 */         this.acceleration += 0.1D;
/* 44 */       } else if (this.acceleration > this.accelerationInWater + 0.20000000298023224D) {
/* 45 */         this.acceleration -= 0.1D;
/*    */       } 
/*    */       
/* 48 */       double x = this.motionX;
/* 49 */       double y = this.motionY;
/* 50 */       double z = this.motionZ;
/* 51 */       double d = MathHelper.sqrt(x * x + y * y + z * z);
/* 52 */       this.motionX = x * this.acceleration / d;
/* 53 */       this.motionY = y * this.acceleration / d;
/* 54 */       this.motionZ = z * this.acceleration / d;
/*    */     } 
/*    */     
/* 57 */     if (isInWater()) {
/* 58 */       double a = (float)Math.atan2(this.motionZ, this.motionX);
/* 59 */       this.rotationYaw = (float)(a * 180.0D / Math.PI) - 90.0F;
/*    */     } 
/*    */   }
/*    */   
/*    */   private void onUpdateGuided() {
/* 64 */     if (!this.world.isRemote && isInWater()) {
/* 65 */       if (this.acceleration < this.accelerationInWater) {
/* 66 */         this.acceleration += 0.1D;
/* 67 */       } else if (this.acceleration > this.accelerationInWater + 0.20000000298023224D) {
/* 68 */         this.acceleration -= 0.1D;
/*    */       } 
/*    */       
/* 71 */       double x = this.targetPosX - this.posX;
/* 72 */       double y = this.targetPosY - this.posY;
/* 73 */       double z = this.targetPosZ - this.posZ;
/* 74 */       double d = MathHelper.sqrt(x * x + y * y + z * z);
/* 75 */       this.motionX = x * this.acceleration / d;
/* 76 */       this.motionY = y * this.acceleration / d;
/* 77 */       this.motionZ = z * this.acceleration / d;
/*    */     } 
/*    */     
/* 80 */     if (isInWater()) {
/* 81 */       double a = (float)Math.atan2(this.motionZ, this.motionX);
/* 82 */       this.rotationYaw = (float)(a * 180.0D / Math.PI) - 90.0F;
/* 83 */       double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 84 */       this.rotationPitch = -((float)(Math.atan2(this.motionY, r) * 180.0D / Math.PI));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_BulletModel getDefaultBulletModel() {
/* 90 */     return MCH_DefaultBulletModels.Torpedo;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityTorpedo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */