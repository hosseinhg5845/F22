/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_WeaponSmoke extends MCH_WeaponBase {
/*    */   public MCH_WeaponSmoke(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  8 */     super(w, v, yaw, pitch, nm, wi);
/*  9 */     this.power = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 14 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponSmoke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */