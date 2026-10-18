/*    */ package com.norwood.mcheli.weapon;
import com.norwood.mcheli.MCH_Lib;
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
/* 14 */     if (w.isRemote) {
/* 15 */       this.interval -= 10;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 21 */     if (!this.worldObj.isRemote) {
/* 22 */       playSound(prm.entity);
/* 23 */       Objects.requireNonNull(prm); Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -0.0F);
/* 24 */       MCH_EntityDispensedItem e = new MCH_EntityDispensedItem(this.worldObj, prm.posX, prm.posY, prm.posZ, v.x, v.y, v.z, prm.rotYaw, prm.rotPitch, this.acceleration);
/*    */ 
/*    */       
/* 27 */       e.setName(this.name);
/* 28 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 29 */       e.motionX = prm.entity.motionX + e.motionX * 0.5D;
/* 30 */       e.motionY = prm.entity.motionY + e.motionY * 0.5D;
/* 31 */       e.motionZ = prm.entity.motionZ + e.motionZ * 0.5D;
/* 32 */       e.posX += e.motionX * 0.5D;
/* 33 */       e.posY += e.motionY * 0.5D;
/* 34 */       e.posZ += e.motionZ * 0.5D;
/* 35 */       this.worldObj.spawnEntity((Entity)e);
/*    */     } 
/*    */     
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */