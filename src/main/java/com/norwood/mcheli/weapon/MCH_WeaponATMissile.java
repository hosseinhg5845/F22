/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_Entity;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_WeaponATMissile extends MCH_WeaponEntitySeeker {
/*    */   public MCH_WeaponATMissile(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 11 */     super(w, v, yaw, pitch, nm, wi);
/* 12 */     this.power = 32;
/* 13 */     this.acceleration = 2.0F;
/* 14 */     this.explosionPower = 4;
/* 15 */     this.interval = -100;
/* 16 */     if (w.isRemote) {
/* 17 */       this.interval -= 10;
/*    */     }
/*    */     
/* 20 */     this.numMode = 2;
/* 21 */     this.guidanceSystem.canLockOnGround = true;
/* 22 */     this.guidanceSystem.ridableOnly = wi.ridableOnly;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCooldownCountReloadTime() {
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 32 */     String opt = "";
/* 33 */     if (getCurrentMode() == 1) {
/* 34 */       opt = " [TA]";
/*    */     }
/*    */     
/* 37 */     return super.getName() + opt;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(int countWait) {
/* 42 */     super.update(countWait);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 47 */     return this.worldObj.isRemote ? shotClient(prm.entity, prm.user) : shotServer(prm);
/*    */   }
/*    */   
/*    */   protected boolean shotClient(Entity entity, Entity user) {
/* 51 */     boolean result = false;
/* 52 */     if (this.guidanceSystem.lock(user) && this.guidanceSystem.lastLockEntity != null) {
/* 53 */       result = true;
/* 54 */       this.optionParameter1 = W_Entity.getEntityId(this.guidanceSystem.lastLockEntity);
/*    */     } 
/*    */     
/* 57 */     this.optionParameter2 = getCurrentMode();
/* 58 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shotServer(MCH_WeaponParam prm) {
/* 63 */     Entity tgtEnt = prm.user.world.getEntityByID(prm.option1);
/* 64 */     if (tgtEnt != null && !tgtEnt.isDead) {
/* 65 */       float yaw = prm.user.rotationYaw + this.fixRotationYaw;
/* 66 */       float pitch = prm.entity.rotationPitch + this.fixRotationPitch;
/* 67 */       double tX = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/* 68 */       double tZ = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/* 69 */       double tY = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
/* 70 */       MCH_EntityATMissile e = new MCH_EntityATMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, this.acceleration);
/* 71 */       e.setName(this.name);
/* 72 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 73 */       e.setTargetEntity(tgtEnt);
/* 74 */       e.guidanceType = prm.option2;
/* 75 */       this.worldObj.spawnEntity((Entity)e);
/* 76 */       playSound(prm.entity);
/* 77 */       return true;
/*    */     } 
/* 79 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponATMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */