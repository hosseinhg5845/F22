/*    */ package com.norwood.mcheli.weapon;
import com.norwood.mcheli.MCH_Lib;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_WeaponRocket extends MCH_WeaponBase {
/*    */   public MCH_WeaponRocket(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  9 */     super(w, v, yaw, pitch, nm, wi);
/* 10 */     this.acceleration = 4.0F;
/* 11 */     this.explosionPower = 3;
/* 12 */     this.power = 22;
/* 13 */     this.interval = 5;
/* 14 */     if (w.isRemote) {
/* 15 */       this.interval += 2;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 21 */     return super.getName() + ((getCurrentMode() == 0) ? "" : " [HEIAP]");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 26 */     if (!this.worldObj.isRemote) {
/* 27 */       playSound(prm.entity);
/* 28 */       Objects.requireNonNull(prm); Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -0.0F);
/* 29 */       MCH_EntityRocket e = new MCH_EntityRocket(this.worldObj, prm.posX, prm.posY, prm.posZ, v.x, v.y, v.z, prm.rotYaw, prm.rotPitch, this.acceleration);
/* 30 */       e.setName(this.name);
/* 31 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 32 */       if (prm.option1 == 0 && this.numMode > 1) {
/* 33 */         e.piercing = 0;
/*    */       }
/*    */       
/* 36 */       this.worldObj.spawnEntity((Entity)e);
/*    */     } else {
/* 38 */       this.optionParameter1 = getCurrentMode();
/*    */     } 
/*    */     
/* 41 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponRocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */