/*    */ package com.norwood.mcheli.weapon;
import com.norwood.mcheli.MCH_Lib;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_WeaponMachineGun2 extends MCH_WeaponBase {
/*    */   public MCH_WeaponMachineGun2(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  9 */     super(w, v, yaw, pitch, nm, wi);
/* 10 */     this.power = 16;
/* 11 */     this.acceleration = 4.0F;
/* 12 */     this.explosionPower = 1;
/* 13 */     this.interval = 2;
/* 14 */     this.numMode = 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyParameters() {
/* 19 */     if (this.explosionPower == 0) {
/* 20 */       this.numMode = 0;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 26 */     return super.getName() + ((getCurrentMode() == 0) ? "" : " [HE]");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 31 */     if (!this.worldObj.isRemote) {
/* 32 */       Objects.requireNonNull(prm); Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -0.0F);
/* 33 */       MCH_EntityBullet e = new MCH_EntityBullet(this.worldObj, prm.posX, prm.posY, prm.posZ, v.x, v.y, v.z, prm.rotYaw, prm.rotPitch, this.acceleration);
/* 34 */       e.setName(this.name);
/* 35 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 36 */       if ((getInfo()).modeNum < 2) {
/* 37 */         e.explosionPower = this.explosionPower;
/*    */       } else {
/* 39 */         e.explosionPower = (prm.option1 == 0) ? -this.explosionPower : this.explosionPower;
/*    */       } 
/*    */       
/* 42 */       e.posX += e.motionX * 0.5D;
/* 43 */       e.posY += e.motionY * 0.5D;
/* 44 */       e.posZ += e.motionZ * 0.5D;
/* 45 */       this.worldObj.spawnEntity((Entity)e);
/* 46 */       playSound(prm.entity);
/*    */     } else {
/* 48 */       this.optionParameter1 = getCurrentMode();
/*    */     } 
/*    */     
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponMachineGun2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */