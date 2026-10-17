/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import com.norwood.mcheli.helper.MCH_Utils;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_WeaponDummy extends MCH_WeaponBase {
/*  8 */   static final MCH_WeaponInfo dummy = new MCH_WeaponInfo(MCH_Utils.buildinAddon("none"), "none");
/*    */   
/*    */   public MCH_WeaponDummy(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 11 */     super(w, v, yaw, pitch, !nm.isEmpty() ? nm : "none", (wi != null) ? wi : dummy);
/*    */   }
/*    */   
/*    */   public int getUseInterval() {
/* 15 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 20 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponDummy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */