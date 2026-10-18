/*    */ package com.norwood.mcheli.weapon;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_WeaponMarkerRocket extends MCH_WeaponBase {
/*    */   public MCH_WeaponMarkerRocket(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  9 */     super(w, v, yaw, pitch, nm, wi);
/* 10 */     this.acceleration = 3.0F;
/* 11 */     this.explosionPower = 0;
/* 12 */     this.power = 0;
/* 13 */     this.interval = 60;
/* 14 */     if (w.isRemote) {
/* 15 */       this.interval += 10;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 21 */     if (!this.worldObj.isRemote) {
/* 22 */       playSound(prm.entity);
/* 23 */       Objects.requireNonNull(prm); Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -0.0F);
/* 24 */       MCH_EntityMarkerRocket e = new MCH_EntityMarkerRocket(this.worldObj, prm.posX, prm.posY, prm.posZ, v.x, v.y, v.z, prm.rotYaw, prm.rotPitch, this.acceleration);
/*    */ 
/*    */       
/* 27 */       e.setName(this.name);
/* 28 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 29 */       e.setMarkerStatus(1);
/* 30 */       this.worldObj.spawnEntity((Entity)e);
/*    */     } else {
/* 32 */       this.optionParameter1 = getCurrentMode();
/*    */     } 
/*    */     
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponMarkerRocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */