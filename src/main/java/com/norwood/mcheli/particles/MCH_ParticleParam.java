/*    */ package com.norwood.mcheli.particles;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_ParticleParam {
/*    */   public final World world;
/*    */   public final String name;
/*    */   public double posX;
/*    */   public double posY;
/*    */   public double posZ;
/* 11 */   public double motionX = 0.0D;
/* 12 */   public double motionY = 0.0D;
/* 13 */   public double motionZ = 0.0D;
/* 14 */   public float size = 1.0F;
/* 15 */   public float a = 1.0F;
/* 16 */   public float r = 1.0F;
/* 17 */   public float g = 1.0F;
/* 18 */   public float b = 1.0F;
/*    */   public boolean isEffectWind = false;
/* 20 */   public int age = 0;
/*    */   public boolean diffusible = false;
/*    */   public boolean toWhite = false;
/* 23 */   public float gravity = 0.0F;
/* 24 */   public float motionYUpAge = 2.0F;
/*    */   
/*    */   public MCH_ParticleParam(World w, String name, double x, double y, double z) {
/* 27 */     this.world = w;
/* 28 */     this.name = name;
/* 29 */     this.posX = x;
/* 30 */     this.posY = y;
/* 31 */     this.posZ = z;
/*    */   }
/*    */   
/*    */   public MCH_ParticleParam(World w, String name, double x, double y, double z, double mx, double my, double mz, float size) {
/* 35 */     this(w, name, x, y, z);
/* 36 */     this.motionX = mx;
/* 37 */     this.motionY = my;
/* 38 */     this.motionZ = mz;
/* 39 */     this.size = size;
/*    */   }
/*    */   
/*    */   public void setColor(float a, float r, float g, float b) {
/* 43 */     this.a = a;
/* 44 */     this.r = r;
/* 45 */     this.g = g;
/* 46 */     this.b = b;
/*    */   }
/*    */   
/*    */   public void setMotion(double x, double y, double z) {
/* 50 */     this.motionX = x;
/* 51 */     this.motionY = y;
/* 52 */     this.motionZ = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\particles\MCH_ParticleParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */