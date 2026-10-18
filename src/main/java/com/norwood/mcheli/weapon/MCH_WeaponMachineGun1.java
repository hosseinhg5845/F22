/*    */ package com.norwood.mcheli.weapon;
import com.norwood.mcheli.MCH_Lib;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_WeaponMachineGun1 extends MCH_WeaponBase {
/*    */   public MCH_WeaponMachineGun1(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  9 */     super(w, v, yaw, pitch, nm, wi);
/* 10 */     this.power = 8;
/* 11 */     this.acceleration = 4.0F;
/* 12 */     this.explosionPower = 0;
/* 13 */     this.interval = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 18 */     if (!this.worldObj.isRemote) {
/* 19 */       Objects.requireNonNull(prm); Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -0.0F);
/* 20 */       MCH_EntityBullet e = new MCH_EntityBullet(this.worldObj, prm.posX, prm.posY, prm.posZ, v.x, v.y, v.z, prm.rotYaw, prm.rotPitch, this.acceleration);
/* 21 */       e.setName(this.name);
/* 22 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 23 */       e.posX += e.motionX * 0.5D;
/* 24 */       e.posY += e.motionY * 0.5D;
/* 25 */       e.posZ += e.motionZ * 0.5D;
/* 26 */       this.worldObj.spawnEntity((Entity)e);
/* 27 */       playSound(prm.entity);
/*    */     } 
/*    */     
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponMachineGun1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */