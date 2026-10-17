/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_WeaponCreator
/*     */ {
/*     */   @Nullable
/*     */   public static MCH_WeaponBase createWeapon(World w, String weaponName, Vec3d v, float yaw, float pitch, MCH_IEntityLockChecker lockChecker, boolean onTurret) {
/*  11 */     MCH_WeaponInfo info = MCH_WeaponInfoManager.get(weaponName);
/*  12 */     if (info != null && !info.type.isEmpty()) {
/*  13 */       MCH_WeaponBase weapon = null;
/*  14 */       if (info.type.compareTo("machinegun1") == 0) {
/*  15 */         weapon = new MCH_WeaponMachineGun1(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  18 */       if (info.type.compareTo("machinegun2") == 0) {
/*  19 */         weapon = new MCH_WeaponMachineGun2(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  22 */       if (info.type.compareTo("tvmissile") == 0) {
/*  23 */         weapon = new MCH_WeaponTvMissile(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  26 */       if (info.type.compareTo("torpedo") == 0) {
/*  27 */         weapon = new MCH_WeaponTorpedo(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  30 */       if (info.type.compareTo("cas") == 0) {
/*  31 */         weapon = new MCH_WeaponCAS(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  34 */       if (info.type.compareTo("rocket") == 0) {
/*  35 */         weapon = new MCH_WeaponRocket(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  38 */       if (info.type.compareTo("asmissile") == 0) {
/*  39 */         weapon = new MCH_WeaponASMissile(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  42 */       if (info.type.compareTo("aamissile") == 0) {
/*  43 */         weapon = new MCH_WeaponAAMissile(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  46 */       if (info.type.compareTo("atmissile") == 0) {
/*  47 */         weapon = new MCH_WeaponATMissile(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  50 */       if (info.type.compareTo("bomb") == 0) {
/*  51 */         weapon = new MCH_WeaponBomb(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  54 */       if (info.type.compareTo("mkrocket") == 0) {
/*  55 */         weapon = new MCH_WeaponMarkerRocket(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  58 */       if (info.type.compareTo("dummy") == 0) {
/*  59 */         weapon = new MCH_WeaponDummy(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  62 */       if (info.type.compareTo("smoke") == 0) {
/*  63 */         weapon = new MCH_WeaponSmoke(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  66 */       if (info.type.compareTo("dispenser") == 0) {
/*  67 */         weapon = new MCH_WeaponDispenser(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  70 */       if (info.type.compareTo("targetingpod") == 0) {
/*  71 */         weapon = new MCH_WeaponTargetingPod(w, v, yaw, pitch, weaponName, info);
/*     */       }
/*     */       
/*  74 */       if (weapon != null) {
/*  75 */         weapon.displayName = info.displayName;
/*  76 */         weapon.power = info.power;
/*  77 */         weapon.acceleration = info.acceleration;
/*  78 */         weapon.explosionPower = info.explosion;
/*  79 */         weapon.explosionPowerInWater = info.explosionInWater;
/*  80 */         int interval = info.delay;
/*  81 */         weapon.interval = info.delay;
/*  82 */         weapon.delayedInterval = info.delay;
/*  83 */         weapon.setLockCountMax(info.lockTime);
/*  84 */         weapon.setLockChecker(lockChecker);
/*  85 */         weapon.numMode = info.modeNum;
/*  86 */         weapon.piercing = info.piercing;
/*  87 */         weapon.heatCount = info.heatCount;
/*  88 */         weapon.onTurret = onTurret;
/*  89 */         if (info.maxHeatCount > 0 && weapon.heatCount < 2) {
/*  90 */           weapon.heatCount = 2;
/*     */         }
/*     */         
/*  93 */         if (interval < 4) {
/*  94 */           interval++;
/*  95 */         } else if (interval < 7) {
/*  96 */           interval += 2;
/*  97 */         } else if (interval < 10) {
/*  98 */           interval += 3;
/*  99 */         } else if (interval < 20) {
/* 100 */           interval += 6;
/*     */         } else {
/* 102 */           interval += 10;
/* 103 */           if (interval >= 40) {
/* 104 */             interval = -interval;
/*     */           }
/*     */         } 
/*     */         
/* 108 */         weapon.delayedInterval = interval;
/* 109 */         if (w.field_72995_K) {
/* 110 */           weapon.interval = interval;
/* 111 */           weapon.heatCount++;
/* 112 */           weapon.cartridge = info.cartridge;
/*     */         } 
/*     */         
/* 115 */         weapon.modifyCommonParameters();
/*     */       } 
/*     */       
/* 118 */       return weapon;
/*     */     } 
/* 120 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponCreator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */