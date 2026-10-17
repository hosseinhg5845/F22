/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class MCH_WeaponEntitySeeker extends MCH_WeaponBase {
/*    */   public MCH_IEntityLockChecker entityLockChecker;
/*    */   public final MCH_WeaponGuidanceSystem guidanceSystem;
/*    */   
/*    */   public MCH_WeaponEntitySeeker(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 11 */     super(w, v, yaw, pitch, nm, wi);
/* 12 */     this.guidanceSystem = new MCH_WeaponGuidanceSystem(w);
/* 13 */     this.guidanceSystem.lockRange = 200.0D;
/* 14 */     this.guidanceSystem.lockAngle = 5;
/* 15 */     this.guidanceSystem.setLockCountMax(25);
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_WeaponGuidanceSystem getGuidanceSystem() {
/* 20 */     return this.guidanceSystem;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLockCount() {
/* 25 */     return this.guidanceSystem.getLockCount();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLockCountMax() {
/* 30 */     return this.guidanceSystem.getLockCountMax();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLockCountMax(int n) {
/* 35 */     this.guidanceSystem.setLockCountMax(n);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLockChecker(MCH_IEntityLockChecker checker) {
/* 40 */     this.guidanceSystem.checker = checker;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(int countWait) {
/* 45 */     super.update(countWait);
/* 46 */     this.guidanceSystem.update();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponEntitySeeker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */