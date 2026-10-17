/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_WeaponSet
/*     */ {
/*  15 */   private static final Random rand = new Random();
/*     */   private final String name;
/*     */   public float rotationYaw;
/*     */   public float rotationPitch;
/*     */   public float prevRotationYaw;
/*     */   public float prevRotationPitch;
/*     */   public float defaultRotationYaw;
/*     */   public float rotationTurretYaw;
/*     */   public float rotBay;
/*     */   public float prevRotBay;
/*     */   public final Recoil[] recoilBuf;
/*     */   public int currentHeat;
/*     */   public int cooldownSpeed;
/*     */   public int countWait;
/*     */   public int countReloadWait;
/*     */   public int soundWait;
/*     */   public float rotBarrelSpd;
/*     */   public float rotBarrel;
/*     */   public float prevRotBarrel;
/*     */   protected final MCH_WeaponBase[] weapons;
/*     */   protected int numAmmo;
/*     */   protected int numRestAllAmmo;
/*     */   protected final int[] lastUsedCount;
/*     */   private int currentWeaponIndex;
/*  39 */   private int lastUsedOptionParameter1 = 0;
/*  40 */   private int lastUsedOptionParameter2 = 0;
/*     */   
/*     */   public MCH_WeaponSet(MCH_WeaponBase[] weapon) {
/*  43 */     this.name = (weapon[0]).name;
/*  44 */     this.weapons = weapon;
/*  45 */     this.currentWeaponIndex = 0;
/*  46 */     this.countWait = 0;
/*  47 */     this.countReloadWait = 0;
/*  48 */     this.lastUsedCount = new int[weapon.length];
/*  49 */     this.rotationYaw = 0.0F;
/*  50 */     this.prevRotationYaw = 0.0F;
/*  51 */     this.rotationPitch = 0.0F;
/*  52 */     this.prevRotationPitch = 0.0F;
/*  53 */     setAmmoNum(0);
/*  54 */     setRestAllAmmoNum(0);
/*  55 */     this.currentHeat = 0;
/*  56 */     this.soundWait = 0;
/*  57 */     this.cooldownSpeed = 1;
/*  58 */     this.rotBarrelSpd = 0.0F;
/*  59 */     this.rotBarrel = 0.0F;
/*  60 */     this.prevRotBarrel = 0.0F;
/*  61 */     this.recoilBuf = new Recoil[weapon.length];
/*     */     
/*  63 */     for (int i = 0; i < this.recoilBuf.length; i++) {
/*  64 */       this.recoilBuf[i] = new Recoil(this, (weapon[i].getInfo()).recoilBufCount, (weapon[i].getInfo()).recoilBufCountSpeed);
/*     */     }
/*     */     
/*  67 */     this.defaultRotationYaw = 0.0F;
/*     */   }
/*     */   
/*     */   public MCH_WeaponSet(MCH_WeaponBase weapon) {
/*  71 */     this(new MCH_WeaponBase[] { weapon });
/*     */   }
/*     */   
/*     */   public boolean isEqual(String s) {
/*  75 */     return this.name.equalsIgnoreCase(s);
/*     */   }
/*     */   
/*     */   public int getAmmoNum() {
/*  79 */     return this.numAmmo;
/*     */   }
/*     */   
/*     */   public void setAmmoNum(int n) {
/*  83 */     this.numAmmo = n;
/*     */   }
/*     */   
/*     */   public int getAmmoNumMax() {
/*  87 */     return getFirstWeapon().getNumAmmoMax();
/*     */   }
/*     */   
/*     */   public int getRestAllAmmoNum() {
/*  91 */     return this.numRestAllAmmo;
/*     */   }
/*     */   
/*     */   public void setRestAllAmmoNum(int n) {
/*  95 */     int debugBefore = this.numRestAllAmmo;
/*  96 */     int m = (getInfo()).maxAmmo - getAmmoNum();
/*  97 */     this.numRestAllAmmo = Math.min(n, m);
/*  98 */     MCH_Lib.DbgLog((getFirstWeapon()).worldObj, "MCH_WeaponSet.setRestAllAmmoNum:%s %d->%d (%d)", new Object[] { getName(), Integer.valueOf(debugBefore), Integer.valueOf(this.numRestAllAmmo), Integer.valueOf(n) });
/*     */   }
/*     */   
/*     */   public int getAllAmmoNum() {
/* 102 */     return getFirstWeapon().getAllAmmoNum();
/*     */   }
/*     */   
/*     */   public void supplyRestAllAmmo() {
/* 106 */     int m = (getInfo()).maxAmmo;
/* 107 */     if (getRestAllAmmoNum() + getAmmoNum() < m) {
/* 108 */       setRestAllAmmoNum(getRestAllAmmoNum() + getAmmoNum() + (getInfo()).suppliedNum);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isInPreparation() {
/* 113 */     return (this.countWait < 0 || this.countReloadWait > 0);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 117 */     MCH_WeaponBase w = getCurrentWeapon();
/* 118 */     return (w != null) ? w.getName() : "";
/*     */   }
/*     */   
/*     */   public boolean canUse() {
/* 122 */     return (this.countWait == 0);
/*     */   }
/*     */   
/*     */   public boolean isLongDelayWeapon() {
/* 126 */     return ((getInfo()).delay > 4);
/*     */   }
/*     */   
/*     */   public void reload() {
/* 130 */     MCH_WeaponBase crtWpn = getCurrentWeapon();
/* 131 */     if (getAmmoNumMax() > 0 && getAmmoNum() < getAmmoNumMax() && crtWpn.getReloadCount() > 0) {
/* 132 */       this.countReloadWait = crtWpn.getReloadCount();
/* 133 */       if (crtWpn.worldObj.field_72995_K) {
/* 134 */         setAmmoNum(0);
/*     */       }
/*     */       
/* 137 */       if (!crtWpn.worldObj.field_72995_K) {
/* 138 */         this.countReloadWait -= 20;
/* 139 */         if (this.countReloadWait <= 0) {
/* 140 */           this.countReloadWait = 1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reloadMag() {
/* 147 */     int restAmmo = getRestAllAmmoNum();
/* 148 */     int nAmmo = getAmmoNumMax() - getAmmoNum();
/* 149 */     if (nAmmo > 0) {
/* 150 */       if (nAmmo > restAmmo) {
/* 151 */         nAmmo = restAmmo;
/*     */       }
/*     */       
/* 154 */       setAmmoNum(getAmmoNum() + nAmmo);
/* 155 */       setRestAllAmmoNum(getRestAllAmmoNum() - nAmmo);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void switchMode() {
/* 160 */     boolean isChanged = false;
/*     */     
/* 162 */     for (MCH_WeaponBase w : this.weapons) {
/* 163 */       if (w != null) {
/* 164 */         isChanged = (w.switchMode() || isChanged);
/*     */       }
/*     */     } 
/*     */     
/* 168 */     if (isChanged) {
/* 169 */       int cntSwitch = 15;
/* 170 */       if (this.countWait >= -cntSwitch) {
/* 171 */         if (this.countWait > cntSwitch) {
/* 172 */           this.countWait = -this.countWait;
/*     */         } else {
/* 174 */           this.countWait = -cntSwitch;
/*     */         } 
/*     */       }
/*     */       
/* 178 */       if ((getCurrentWeapon()).worldObj.field_72995_K) {
/* 179 */         W_McClient.playSoundClick(1.0F, 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onSwitchWeapon(boolean isRemote, boolean isCreative) {
/* 185 */     int cntSwitch = 15;
/* 186 */     if (isRemote) {
/* 187 */       cntSwitch += 10;
/*     */     }
/*     */     
/* 190 */     if (this.countWait >= -cntSwitch) {
/* 191 */       if (this.countWait > cntSwitch) {
/* 192 */         this.countWait = -this.countWait;
/*     */       } else {
/* 194 */         this.countWait = -cntSwitch;
/*     */       } 
/*     */     }
/*     */     
/* 198 */     this.currentWeaponIndex = 0;
/* 199 */     if (isCreative) {
/* 200 */       setAmmoNum(getAmmoNumMax());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isUsed(int index) {
/* 205 */     MCH_WeaponBase w = getFirstWeapon();
/* 206 */     if (w != null && index < this.lastUsedCount.length) {
/* 207 */       int cnt = this.lastUsedCount[index];
/* 208 */       return ((w.interval >= 4 && cnt > w.interval / 2) || cnt >= 4);
/*     */     } 
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(Entity shooter, boolean isSelected, boolean isUsed) {
/* 215 */     if (getCurrentWeapon().getInfo() != null) {
/* 216 */       if (this.countReloadWait > 0) {
/* 217 */         this.countReloadWait--;
/* 218 */         if (this.countReloadWait == 0) {
/* 219 */           reloadMag();
/*     */         }
/*     */       } 
/*     */       
/* 223 */       for (int i = 0; i < this.lastUsedCount.length; i++) {
/* 224 */         if (this.lastUsedCount[i] > 0) {
/* 225 */           if (this.lastUsedCount[i] == 4) {
/* 226 */             if (0 == getCurrentWeaponIndex() && canUse() && (getAmmoNum() > 0 || getAllAmmoNum() <= 0)) {
/* 227 */               this.lastUsedCount[i] = this.lastUsedCount[i] - 1;
/*     */             }
/*     */           } else {
/* 230 */             this.lastUsedCount[i] = this.lastUsedCount[i] - 1;
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 235 */       if (this.currentHeat > 0) {
/* 236 */         if (this.currentHeat < (getCurrentWeapon().getInfo()).maxHeatCount) {
/* 237 */           this.cooldownSpeed++;
/*     */         }
/*     */         
/* 240 */         this.currentHeat -= this.cooldownSpeed / 20 + 1;
/* 241 */         if (this.currentHeat < 0) {
/* 242 */           this.currentHeat = 0;
/*     */         }
/*     */       } 
/*     */       
/* 246 */       if (this.countWait > 0) {
/* 247 */         this.countWait--;
/*     */       }
/*     */       
/* 250 */       if (this.countWait < 0) {
/* 251 */         this.countWait++;
/*     */       }
/*     */       
/* 254 */       this.prevRotationYaw = this.rotationYaw;
/* 255 */       this.prevRotationPitch = this.rotationPitch;
/* 256 */       if (this.weapons != null) {
/* 257 */         for (MCH_WeaponBase w : this.weapons) {
/* 258 */           if (w != null) {
/* 259 */             w.update(this.countWait);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 264 */       if (this.soundWait > 0) {
/* 265 */         this.soundWait--;
/*     */       }
/*     */       
/* 268 */       if (isUsed && this.rotBarrelSpd < 75.0F) {
/* 269 */         this.rotBarrelSpd += (25 + rand.nextInt(3));
/* 270 */         if (this.rotBarrelSpd > 74.0F) {
/* 271 */           this.rotBarrelSpd = 74.0F;
/*     */         }
/*     */       } 
/*     */       
/* 275 */       this.prevRotBarrel = this.rotBarrel;
/* 276 */       this.rotBarrel += this.rotBarrelSpd;
/* 277 */       if (this.rotBarrel >= 360.0F) {
/* 278 */         this.rotBarrel -= 360.0F;
/* 279 */         this.prevRotBarrel -= 360.0F;
/*     */       } 
/*     */       
/* 282 */       if (this.rotBarrelSpd > 0.0F) {
/* 283 */         this.rotBarrelSpd--;
/* 284 */         if (this.rotBarrelSpd < 0.0F) {
/* 285 */           this.rotBarrelSpd = 0.0F;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateWeapon(Entity shooter, boolean isUsed, int index) {
/* 292 */     MCH_WeaponBase crtWpn = getWeapon(index);
/* 293 */     if (isUsed && shooter.field_70170_p.field_72995_K && crtWpn != null && crtWpn.cartridge != null) {
/* 294 */       Vec3d v = crtWpn.getShotPos(shooter);
/* 295 */       float yaw = shooter.field_70177_z;
/* 296 */       float pitch = shooter.field_70125_A;
/* 297 */       if (!(shooter instanceof com.norwood.mcheli.vehicle.MCH_EntityVehicle) || shooter.func_184207_aI());
/*     */ 
/*     */       
/* 300 */       MCH_EntityCartridge.spawnCartridge(shooter.field_70170_p, crtWpn.cartridge, shooter.field_70165_t + v.field_72450_a, shooter.field_70163_u + v.field_72448_b, shooter.field_70161_v + v.field_72449_c, shooter.field_70159_w, shooter.field_70181_x, shooter.field_70179_y, yaw + this.rotationYaw, pitch + this.rotationPitch);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     if (index < this.recoilBuf.length) {
/* 315 */       Recoil r = this.recoilBuf[index];
/* 316 */       r.prevRecoilBuf = r.recoilBuf;
/* 317 */       if (isUsed && r.recoilBufCount <= 0) {
/* 318 */         r.recoilBufCount = r.recoilBufCountMax;
/*     */       }
/*     */       
/* 321 */       if (r.recoilBufCount > 0) {
/* 322 */         if (r.recoilBufCountMax <= 1) {
/* 323 */           r.recoilBuf = 1.0F;
/* 324 */         } else if (r.recoilBufCountMax == 2) {
/* 325 */           r.recoilBuf = (r.recoilBufCount == 2) ? 1.0F : 0.6F;
/*     */         } else {
/* 327 */           if (r.recoilBufCount > r.recoilBufCountMax / 2) {
/* 328 */             r.recoilBufCount -= r.recoilBufCountSpeed;
/*     */           }
/*     */           
/* 331 */           float rb = r.recoilBufCount / r.recoilBufCountMax;
/* 332 */           r.recoilBuf = MathHelper.func_76126_a(rb * 3.1415927F);
/*     */         } 
/*     */         
/* 335 */         r.recoilBufCount--;
/*     */       } else {
/* 337 */         r.recoilBuf = 0.0F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean use(MCH_WeaponParam prm) {
/* 343 */     MCH_WeaponBase crtWpn = getCurrentWeapon();
/* 344 */     if (crtWpn != null && crtWpn.getInfo() != null) {
/* 345 */       MCH_WeaponInfo info = crtWpn.getInfo();
/* 346 */       if ((getAmmoNumMax() <= 0 || getAmmoNum() > 0) && (info.maxHeatCount <= 0 || this.currentHeat < info.maxHeatCount)) {
/* 347 */         crtWpn.canPlaySound = (this.soundWait == 0);
/* 348 */         prm.rotYaw = (prm.entity != null) ? prm.entity.field_70177_z : 0.0F;
/* 349 */         prm.rotPitch = (prm.entity != null) ? prm.entity.field_70125_A : 0.0F;
/* 350 */         prm.rotYaw += this.rotationYaw + crtWpn.fixRotationYaw;
/* 351 */         prm.rotPitch += this.rotationPitch + crtWpn.fixRotationPitch;
/* 352 */         if (info.accuracy > 0.0F) {
/* 353 */           prm.rotYaw += (rand.nextFloat() - 0.5F) * info.accuracy;
/* 354 */           prm.rotPitch += (rand.nextFloat() - 0.5F) * info.accuracy;
/*     */         } 
/*     */         
/* 357 */         prm.rotYaw = MathHelper.func_76142_g(prm.rotYaw);
/* 358 */         prm.rotPitch = MathHelper.func_76142_g(prm.rotPitch);
/* 359 */         if (crtWpn.use(prm)) {
/* 360 */           if (info.maxHeatCount > 0) {
/* 361 */             this.cooldownSpeed = 1;
/* 362 */             this.currentHeat += crtWpn.heatCount;
/* 363 */             if (this.currentHeat >= info.maxHeatCount) {
/* 364 */               this.currentHeat += 30;
/*     */             }
/*     */           } 
/*     */           
/* 368 */           if (info.soundDelay > 0 && this.soundWait == 0) {
/* 369 */             this.soundWait = info.soundDelay;
/*     */           }
/*     */           
/* 372 */           this.lastUsedOptionParameter1 = crtWpn.optionParameter1;
/* 373 */           this.lastUsedOptionParameter2 = crtWpn.optionParameter2;
/* 374 */           this.lastUsedCount[this.currentWeaponIndex] = (crtWpn.interval > 0) ? crtWpn.interval : -crtWpn.interval;
/* 375 */           if (crtWpn.isCooldownCountReloadTime() && crtWpn.getReloadCount() - 10 > this.lastUsedCount[this.currentWeaponIndex]) {
/* 376 */             this.lastUsedCount[this.currentWeaponIndex] = crtWpn.getReloadCount() - 10;
/*     */           }
/*     */           
/* 379 */           this.currentWeaponIndex = (this.currentWeaponIndex + 1) % this.weapons.length;
/* 380 */           this.countWait = (prm.user instanceof net.minecraft.entity.player.EntityPlayer) ? crtWpn.interval : crtWpn.delayedInterval;
/* 381 */           this.countReloadWait = 0;
/* 382 */           if (getAmmoNum() > 0) {
/* 383 */             setAmmoNum(getAmmoNum() - 1);
/*     */           }
/*     */           
/* 386 */           if (getAmmoNum() <= 0) {
/* 387 */             if (prm.isInfinity && getRestAllAmmoNum() < getAmmoNumMax()) {
/* 388 */               setRestAllAmmoNum(getAmmoNumMax());
/*     */             }
/*     */             
/* 391 */             reload();
/* 392 */             prm.reload = true;
/* 393 */             if (prm.user instanceof com.norwood.mcheli.mob.MCH_EntityGunner) {
/* 394 */               this.countWait += ((this.countWait >= 0) ? 1 : -1) * crtWpn.getReloadCount();
/*     */             }
/*     */           } 
/*     */           
/* 398 */           prm.result = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 403 */     return prm.result;
/*     */   }
/*     */   
/*     */   public void waitAndReloadByOther(boolean reload) {
/* 407 */     MCH_WeaponBase crtWpn = getCurrentWeapon();
/* 408 */     if (crtWpn != null && crtWpn.getInfo() != null) {
/* 409 */       this.countWait = crtWpn.interval;
/* 410 */       this.countReloadWait = 0;
/* 411 */       if (reload && getAmmoNumMax() > 0 && crtWpn.getReloadCount() > 0) {
/* 412 */         this.countReloadWait = crtWpn.getReloadCount();
/* 413 */         if (!crtWpn.worldObj.field_72995_K) {
/* 414 */           this.countReloadWait -= 20;
/* 415 */           if (this.countReloadWait <= 0) {
/* 416 */             this.countReloadWait = 1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getLastUsedOptionParameter1() {
/* 424 */     return this.lastUsedOptionParameter1;
/*     */   }
/*     */   
/*     */   public int getLastUsedOptionParameter2() {
/* 428 */     return this.lastUsedOptionParameter2;
/*     */   }
/*     */   
/*     */   public MCH_WeaponBase getFirstWeapon() {
/* 432 */     return getWeapon(0);
/*     */   }
/*     */   
/*     */   public int getCurrentWeaponIndex() {
/* 436 */     return this.currentWeaponIndex;
/*     */   }
/*     */   
/*     */   public MCH_WeaponBase getCurrentWeapon() {
/* 440 */     return getWeapon(this.currentWeaponIndex);
/*     */   }
/*     */   
/*     */   public MCH_WeaponBase getWeapon(int idx) {
/* 444 */     return (this.weapons != null && this.weapons.length > 0 && idx < this.weapons.length) ? this.weapons[idx] : null;
/*     */   }
/*     */   
/*     */   public int getWeaponNum() {
/* 448 */     return (this.weapons != null) ? this.weapons.length : 0;
/*     */   }
/*     */   
/*     */   public MCH_WeaponInfo getInfo() {
/* 452 */     return getFirstWeapon().getInfo();
/*     */   }
/*     */   
/*     */   public double getLandInDistance(MCH_WeaponParam prm) {
/* 456 */     double ret = -1.0D;
/* 457 */     MCH_WeaponBase crtWpn = getCurrentWeapon();
/* 458 */     if (crtWpn != null && crtWpn.getInfo() != null) {
/* 459 */       prm.rotYaw = (prm.entity != null) ? prm.entity.field_70177_z : 0.0F;
/* 460 */       prm.rotPitch = (prm.entity != null) ? prm.entity.field_70125_A : 0.0F;
/* 461 */       prm.rotYaw += this.rotationYaw + crtWpn.fixRotationYaw;
/* 462 */       prm.rotPitch += this.rotationPitch + crtWpn.fixRotationPitch;
/* 463 */       prm.rotYaw = MathHelper.func_76142_g(prm.rotYaw);
/* 464 */       prm.rotPitch = MathHelper.func_76142_g(prm.rotPitch);
/* 465 */       return crtWpn.getLandInDistance(prm);
/*     */     } 
/* 467 */     return ret;
/*     */   }
/*     */   
/*     */   public static class Recoil
/*     */   {
/*     */     public final int recoilBufCountMax;
/*     */     public final int recoilBufCountSpeed;
/*     */     public int recoilBufCount;
/*     */     public float recoilBuf;
/*     */     public float prevRecoilBuf;
/*     */     
/*     */     public Recoil(MCH_WeaponSet paramMCH_WeaponSet, int max, int spd) {
/* 479 */       this.recoilBufCountMax = max;
/* 480 */       this.recoilBufCountSpeed = spd;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */