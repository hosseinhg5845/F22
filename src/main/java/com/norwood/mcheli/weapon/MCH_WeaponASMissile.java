/*    */ package com.norwood.mcheli.weapon;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*    */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_WeaponASMissile extends MCH_WeaponBase {
/*    */   public MCH_WeaponASMissile(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 13 */     super(w, v, yaw, pitch, nm, wi);
/* 14 */     this.acceleration = 3.0F;
/* 15 */     this.explosionPower = 9;
/* 16 */     this.power = 40;
/* 17 */     this.interval = 65186;
/* 18 */     if (w.isRemote) {
/* 19 */       this.interval -= 10;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCooldownCountReloadTime() {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(int countWait) {
/* 30 */     super.update(countWait);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 35 */     float yaw = prm.user.rotationYaw;
/* 36 */     float pitch = prm.user.rotationPitch;
/* 37 */     double tX = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/* 38 */     double tZ = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/* 39 */     double tY = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
/* 40 */     double dist = MathHelper.sqrt(tX * tX + tY * tY + tZ * tZ);
/* 41 */     if (this.worldObj.isRemote) {
/* 42 */       tX = tX * 200.0D / dist;
/* 43 */       tY = tY * 200.0D / dist;
/* 44 */       tZ = tZ * 200.0D / dist;
/*    */     } else {
/* 46 */       tX = tX * 250.0D / dist;
/* 47 */       tY = tY * 250.0D / dist;
/* 48 */       tZ = tZ * 250.0D / dist;
/*    */     } 
/*    */     
/* 51 */     Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.posX, prm.entity.posY + 1.62D, prm.entity.posZ);
/* 52 */     Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.posX + tX, prm.entity.posY + 1.62D + tY, prm.entity.posZ + tZ);
/* 53 */     RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
/* 54 */     if (W_MovingObjectPosition.isHitTypeTile(m) && 
/* 55 */       !MCH_Lib.isBlockInWater(this.worldObj, m.getBlockPos().getX(), m.getBlockPos().getY(), m.getBlockPos().getZ())) {
/* 56 */       if (!this.worldObj.isRemote) {
/* 57 */         MCH_EntityASMissile e = new MCH_EntityASMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, this.acceleration);
/* 58 */         e.setName(this.name);
/* 59 */         e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 60 */         e.targetPosX = m.hitVec.x;
/* 61 */         e.targetPosY = m.hitVec.y;
/* 62 */         e.targetPosZ = m.hitVec.z;
/* 63 */         this.worldObj.spawnEntity((Entity)e);
/* 64 */         playSound(prm.entity);
/*    */       } 
/*    */       
/* 67 */       return true;
/*    */     } 
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponASMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */