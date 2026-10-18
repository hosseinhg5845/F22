/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_Entity;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_WeaponAAMissile extends MCH_WeaponEntitySeeker {
/*    */   public MCH_WeaponAAMissile(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 11 */     super(w, v, yaw, pitch, nm, wi);
/* 12 */     this.power = 12;
/* 13 */     this.acceleration = 2.5F;
/* 14 */     this.explosionPower = 4;
/* 15 */     this.interval = 5;
/* 16 */     if (w.isRemote) {
/* 17 */       this.interval += 5;
/*    */     }
/*    */     
/* 20 */     this.guidanceSystem.canLockInAir = true;
/* 21 */     this.guidanceSystem.ridableOnly = wi.ridableOnly;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCooldownCountReloadTime() {
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(int countWait) {
/* 31 */     super.update(countWait);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 36 */     boolean result = false;
/* 37 */     if (!this.worldObj.isRemote) {
/* 38 */       Entity tgtEnt = prm.user.world.getEntityByID(prm.option1);
/* 39 */       if (tgtEnt != null && !tgtEnt.isDead) {
/* 40 */         playSound(prm.entity);
/* 41 */         float yaw = prm.entity.rotationYaw + this.fixRotationYaw;
/* 42 */         float pitch = prm.entity.rotationPitch + this.fixRotationPitch;
/* 43 */         double tX = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/* 44 */         double tZ = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/* 45 */         double tY = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
/* 46 */         MCH_EntityAAMissile e = new MCH_EntityAAMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, this.acceleration);
/* 47 */         e.setName(this.name);
/* 48 */         e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 49 */         e.setTargetEntity(tgtEnt);
/* 50 */         this.worldObj.spawnEntity((Entity)e);
/* 51 */         result = true;
/*    */       } 
/* 53 */     } else if (this.guidanceSystem.lock(prm.user) && this.guidanceSystem.lastLockEntity != null) {
/* 54 */       result = true;
/* 55 */       this.optionParameter1 = W_Entity.getEntityId(this.guidanceSystem.lastLockEntity);
/*    */     } 
/*    */     
/* 58 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponAAMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */