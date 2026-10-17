/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class MCH_WeaponParam {
/*  6 */   public Entity entity = null;
/*  7 */   public Entity user = null;
/*  8 */   public double posX = 0.0D;
/*  9 */   public double posY = 0.0D;
/* 10 */   public double posZ = 0.0D;
/* 11 */   public float rotYaw = 0.0F;
/* 12 */   public float rotPitch = 0.0F;
/* 13 */   public final float rotRoll = 0.0F;
/* 14 */   public int option1 = 0;
/* 15 */   public int option2 = 0;
/*    */   public boolean isInfinity = false;
/*    */   public boolean isTurret = false;
/*    */   public boolean result;
/*    */   public boolean reload;
/*    */   
/*    */   public void setPosAndRot(double x, double y, double z, float yaw, float pitch) {
/* 22 */     setPosition(x, y, z);
/* 23 */     setRotation(yaw, pitch);
/*    */   }
/*    */   
/*    */   public void setPosition(double x, double y, double z) {
/* 27 */     this.posX = x;
/* 28 */     this.posY = y;
/* 29 */     this.posZ = z;
/*    */   }
/*    */   
/*    */   public void setRotation(float y, float p) {
/* 33 */     this.rotYaw = y;
/* 34 */     this.rotPitch = p;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */