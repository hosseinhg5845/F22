/*    */ package com.norwood.mcheli.particles;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_EntityFX;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class MCH_EntityParticleBase extends W_EntityFX {
/*    */   public boolean isEffectedWind;
/*    */   public boolean diffusible;
/*    */   public boolean toWhite;
/*    */   public float particleMaxScale;
/*    */   public float gravity;
/*    */   public float moutionYUpAge;
/*    */   
/*    */   public MCH_EntityParticleBase(World par1World, double x, double y, double z, double mx, double my, double mz) {
/* 15 */     super(par1World, x, y, z, mx, my, mz);
/* 16 */     this.field_187129_i = mx;
/* 17 */     this.field_187130_j = my;
/* 18 */     this.field_187131_k = mz;
/* 19 */     this.isEffectedWind = false;
/* 20 */     this.particleMaxScale = this.field_70544_f;
/*    */   }
/*    */   
/*    */   public MCH_EntityParticleBase setParticleScale(float scale) {
/* 24 */     this.field_70544_f = scale;
/* 25 */     return this;
/*    */   }
/*    */   
/*    */   public void setParticleMaxAge(int age) {
/* 29 */     this.field_70547_e = age;
/*    */   }
/*    */   
/*    */   public void func_70536_a(int par1) {
/* 33 */     this.field_94054_b = par1 % 8;
/* 34 */     this.field_94055_c = par1 / 8;
/*    */   }
/*    */   
/*    */   public int func_70537_b() {
/* 38 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\particles\MCH_EntityParticleBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */