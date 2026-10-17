/*    */ package com.norwood.mcheli.weapon;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_WeaponDispenser extends MCH_WeaponBase {
/*    */   public MCH_WeaponDispenser(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  9 */     super(w, v, yaw, pitch, nm, wi);
/* 10 */     this.acceleration = 0.5F;
/* 11 */     this.explosionPower = 0;
/* 12 */     this.power = 0;
/* 13 */     this.interval = -90;
/* 14 */     if (w.field_72995_K) {
/* 15 */       this.interval -= 10;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 21 */     if (!this.worldObj.field_72995_K) {
/* 22 */       playSound(prm.entity);
/* 23 */       Objects.requireNonNull(prm); Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -0.0F);
/* 24 */       MCH_EntityDispensedItem e = new MCH_EntityDispensedItem(this.worldObj, prm.posX, prm.posY, prm.posZ, v.field_72450_a, v.field_72448_b, v.field_72449_c, prm.rotYaw, prm.rotPitch, this.acceleration);
/*    */ 
/*    */       
/* 27 */       e.setName(this.name);
/* 28 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 29 */       e.field_70159_w = prm.entity.field_70159_w + e.field_70159_w * 0.5D;
/* 30 */       e.field_70181_x = prm.entity.field_70181_x + e.field_70181_x * 0.5D;
/* 31 */       e.field_70179_y = prm.entity.field_70179_y + e.field_70179_y * 0.5D;
/* 32 */       e.field_70165_t += e.field_70159_w * 0.5D;
/* 33 */       e.field_70163_u += e.field_70181_x * 0.5D;
/* 34 */       e.field_70161_v += e.field_70179_y * 0.5D;
/* 35 */       this.worldObj.func_72838_d((Entity)e);
/*    */     } 
/*    */     
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */