/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_EntityATMissile extends MCH_EntityBaseBullet {
/*  8 */   public int guidanceType = 0;
/*    */   
/*    */   public MCH_EntityATMissile(World par1World) {
/* 11 */     super(par1World);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_EntityATMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/* 17 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_70071_h_() {
/* 22 */     super.func_70071_h_();
/* 23 */     if (getInfo() != null && !(getInfo()).disableSmoke && this.field_70173_aa >= (getInfo()).trajectoryParticleStartTick) {
/* 24 */       spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);
/*    */     }
/*    */     
/* 27 */     if (!this.field_70170_p.field_72995_K) {
/* 28 */       if (this.shootingEntity != null && this.targetEntity != null && !this.targetEntity.field_70128_L) {
/* 29 */         if (usingFlareOfTarget(this.targetEntity)) {
/* 30 */           func_70106_y();
/*    */           
/*    */           return;
/*    */         } 
/* 34 */         onUpdateMotion();
/*    */       } else {
/* 36 */         func_70106_y();
/*    */       } 
/*    */     }
/*    */     
/* 40 */     double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
/* 41 */     this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
/* 42 */     double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 43 */     this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
/*    */   }
/*    */   
/*    */   public void onUpdateMotion() {
/* 47 */     double x = this.targetEntity.field_70165_t - this.field_70165_t;
/* 48 */     double y = this.targetEntity.field_70163_u - this.field_70163_u;
/* 49 */     double z = this.targetEntity.field_70161_v - this.field_70161_v;
/* 50 */     double d = x * x + y * y + z * z;
/* 51 */     if (d > 2250000.0D || this.targetEntity.field_70128_L) {
/* 52 */       func_70106_y();
/* 53 */     } else if ((getInfo()).proximityFuseDist >= 0.1F && d < (getInfo()).proximityFuseDist) {
/* 54 */       RayTraceResult mop = new RayTraceResult(this.targetEntity);
/* 55 */       mop.field_72308_g = null;
/* 56 */       onImpact(mop, 1.0F);
/*    */     } else {
/* 58 */       int rigidityTime = (getInfo()).rigidityTime;
/* 59 */       float af = (getCountOnUpdate() < rigidityTime + (getInfo()).trajectoryParticleStartTick) ? 0.5F : 1.0F;
/* 60 */       if (getCountOnUpdate() > rigidityTime) {
/* 61 */         if (this.guidanceType == 1) {
/* 62 */           if (getCountOnUpdate() <= rigidityTime + 20) {
/* 63 */             guidanceToTarget(this.targetEntity.field_70165_t, this.shootingEntity.field_70163_u + 150.0D, this.targetEntity.field_70161_v, af);
/* 64 */           } else if (getCountOnUpdate() <= rigidityTime + 30) {
/* 65 */             guidanceToTarget(this.targetEntity.field_70165_t, this.shootingEntity.field_70163_u, this.targetEntity.field_70161_v, af);
/*    */           } else {
/* 67 */             if (getCountOnUpdate() == rigidityTime + 35) {
/* 68 */               setPower((int)(getPower() * 1.2F));
/* 69 */               if (this.explosionPower > 0) {
/* 70 */                 this.explosionPower++;
/*    */               }
/*    */             } 
/*    */             
/* 74 */             guidanceToTarget(this.targetEntity.field_70165_t, this.targetEntity.field_70163_u, this.targetEntity.field_70161_v, af);
/*    */           } 
/*    */         } else {
/* 77 */           d = MathHelper.func_76133_a(d);
/* 78 */           this.field_70159_w = x * this.acceleration / d * af;
/* 79 */           this.field_70181_x = y * this.acceleration / d * af;
/* 80 */           this.field_70179_y = z * this.acceleration / d * af;
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_BulletModel getDefaultBulletModel() {
/* 88 */     return MCH_DefaultBulletModels.ATMissile;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityATMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */