/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class W_Vec3 {
/*    */   public static Vec3d rotateRoll(float par1, Vec3d vOut) {
/*  8 */     float f1 = MathHelper.cos(par1);
/*  9 */     float f2 = MathHelper.sin(par1);
/* 10 */     double d0 = vOut.x * f1 + vOut.y * f2;
/* 11 */     double d1 = vOut.y * f1 - vOut.x * f2;
/* 12 */     double d2 = vOut.z;
/* 13 */     return new Vec3d(d0, d1, d2);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_Vec3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */