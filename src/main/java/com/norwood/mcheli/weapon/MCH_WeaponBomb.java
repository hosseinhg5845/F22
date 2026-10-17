/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_Explosion;
/*    */ import com.norwood.mcheli.helicopter.MCH_EntityHeli;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_WeaponBomb extends MCH_WeaponBase {
/*    */   public MCH_WeaponBomb(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 11 */     super(w, v, yaw, pitch, nm, wi);
/* 12 */     this.acceleration = 0.5F;
/* 13 */     this.explosionPower = 9;
/* 14 */     this.power = 35;
/* 15 */     this.interval = -90;
/* 16 */     if (w.field_72995_K) {
/* 17 */       this.interval -= 10;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 23 */     if (getInfo() != null && (getInfo()).destruct) {
/* 24 */       Entity entity = prm.entity; if (entity instanceof MCH_EntityHeli) { MCH_EntityHeli ac = (MCH_EntityHeli)entity;
/* 25 */         if (ac.isUAV() && ac.getSeatNum() == 0) {
/* 26 */           if (!this.worldObj.field_72995_K) {
/* 27 */             MCH_Explosion.newExplosion(this.worldObj, null, prm.user, ac.field_70165_t, ac.field_70163_u, ac.field_70161_v, 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */                 
/* 34 */                 (getInfo()).explosion, 
/* 35 */                 (getInfo()).explosionBlock, true, true, 
/*    */ 
/*    */                 
/* 38 */                 (getInfo()).flaming, true, 0);
/*    */ 
/*    */ 
/*    */             
/* 42 */             playSound(prm.entity);
/*    */           } 
/*    */           
/* 45 */           ac.destruct();
/*    */         }  }
/*    */     
/* 48 */     } else if (!this.worldObj.field_72995_K) {
/* 49 */       playSound(prm.entity);
/* 50 */       MCH_EntityBomb e = new MCH_EntityBomb(this.worldObj, prm.posX, prm.posY, prm.posZ, prm.entity.field_70159_w, prm.entity.field_70181_x, prm.entity.field_70179_y, prm.entity.field_70177_z, 0.0F, this.acceleration);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 62 */       e.setName(this.name);
/* 63 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 64 */       e.field_70159_w = prm.entity.field_70159_w;
/* 65 */       e.field_70181_x = prm.entity.field_70181_x;
/* 66 */       e.field_70179_y = prm.entity.field_70179_y;
/* 67 */       this.worldObj.func_72838_d((Entity)e);
/*    */     } 
/*    */     
/* 70 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponBomb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */