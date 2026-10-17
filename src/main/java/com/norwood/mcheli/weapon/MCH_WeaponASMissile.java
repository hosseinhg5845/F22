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
/* 18 */     if (w.field_72995_K) {
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
/* 35 */     float yaw = prm.user.field_70177_z;
/* 36 */     float pitch = prm.user.field_70125_A;
/* 37 */     double tX = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 38 */     double tZ = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 39 */     double tY = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/* 40 */     double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/* 41 */     if (this.worldObj.field_72995_K) {
/* 42 */       tX = tX * 200.0D / dist;
/* 43 */       tY = tY * 200.0D / dist;
/* 44 */       tZ = tZ * 200.0D / dist;
/*    */     } else {
/* 46 */       tX = tX * 250.0D / dist;
/* 47 */       tY = tY * 250.0D / dist;
/* 48 */       tZ = tZ * 250.0D / dist;
/*    */     } 
/*    */     
/* 51 */     Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.field_70165_t, prm.entity.field_70163_u + 1.62D, prm.entity.field_70161_v);
/* 52 */     Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.field_70165_t + tX, prm.entity.field_70163_u + 1.62D + tY, prm.entity.field_70161_v + tZ);
/* 53 */     RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
/* 54 */     if (W_MovingObjectPosition.isHitTypeTile(m) && 
/* 55 */       !MCH_Lib.isBlockInWater(this.worldObj, m.func_178782_a().func_177958_n(), m.func_178782_a().func_177956_o(), m.func_178782_a().func_177952_p())) {
/* 56 */       if (!this.worldObj.field_72995_K) {
/* 57 */         MCH_EntityASMissile e = new MCH_EntityASMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, this.acceleration);
/* 58 */         e.setName(this.name);
/* 59 */         e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 60 */         e.targetPosX = m.field_72307_f.field_72450_a;
/* 61 */         e.targetPosY = m.field_72307_f.field_72448_b;
/* 62 */         e.targetPosZ = m.field_72307_f.field_72449_c;
/* 63 */         this.worldObj.func_72838_d((Entity)e);
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