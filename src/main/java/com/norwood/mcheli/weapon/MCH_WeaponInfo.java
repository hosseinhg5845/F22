/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_BaseInfo;
/*     */ import com.norwood.mcheli.MCH_Color;
/*     */ import com.norwood.mcheli.MCH_DamageFactor;
/*     */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*     */ import com.norwood.mcheli.wrapper.W_Item;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_WeaponInfo
/*     */   extends MCH_BaseInfo
/*     */ {
/*  22 */   public static Random rand = new Random();
/*     */   
/*     */   public final String name;
/*     */   
/*     */   public String explosionType;
/*     */   
/*     */   public int nukeYield;
/*     */   
/*     */   public int chemYield;
/*     */   public double chemSpeed;
/*     */   public int chemType;
/*     */   public boolean nukeEffectOnly;
/*     */   public String displayName;
/*     */   public String type;
/*     */   public int power;
/*     */   public float acceleration;
/*     */   public float accelerationInWater;
/*     */   public int explosion;
/*     */   public int explosionBlock;
/*     */   public int explosionInWater;
/*     */   public int explosionAltitude;
/*     */   public int delayFuse;
/*     */   public float bound;
/*     */   public int timeFuse;
/*     */   public boolean flaming;
/*     */   public MCH_SightType sight;
/*     */   public float[] zoom;
/*     */   public int delay;
/*     */   public int reloadTime;
/*     */   public int round;
/*     */   public int suppliedNum;
/*     */   public int maxAmmo;
/*     */   public final List<RoundItem> roundItems;
/*     */   public int soundDelay;
/*     */   public float soundVolume;
/*     */   public float soundPitch;
/*     */   public float soundPitchRandom;
/*     */   public final int soundPattern;
/*     */   public int lockTime;
/*     */   public boolean ridableOnly;
/*     */   public float proximityFuseDist;
/*     */   public int rigidityTime;
/*     */   public float accuracy;
/*     */   public int bomblet;
/*     */   public int bombletSTime;
/*     */   public float bombletDiff;
/*     */   public int modeNum;
/*     */   public int fixMode;
/*     */   public int piercing;
/*     */   public int heatCount;
/*     */   public int maxHeatCount;
/*     */   public boolean isFAE;
/*     */   public boolean isGuidedTorpedo;
/*     */   public float gravity;
/*     */   public float gravityInWater;
/*     */   public float velocityInWater;
/*     */   public boolean destruct;
/*     */   public String trajectoryParticleName;
/*     */   public int trajectoryParticleStartTick;
/*     */   public boolean disableSmoke;
/*     */   public MCH_Cartridge cartridge;
/*     */   public MCH_Color color;
/*     */   public MCH_Color colorInWater;
/*     */   public String soundFileName;
/*     */   public float smokeSize;
/*     */   public int smokeNum;
/*     */   public int smokeMaxAge;
/*     */   public Item dispenseItem;
/*     */   public int dispenseDamege;
/*     */   public int dispenseRange;
/*     */   public int recoilBufCount;
/*     */   public int recoilBufCountSpeed;
/*     */   public float length;
/*     */   public float radius;
/*     */   public float angle;
/*     */   public boolean displayMortarDistance;
/*     */   public boolean fixCameraPitch;
/*     */   public float cameraRotationSpeedPitch;
/*     */   public int target;
/*     */   public int markTime;
/*     */   public float recoil;
/*     */   public String bulletModelName;
/*     */   public MCH_BulletModel bulletModel;
/*     */   public String bombletModelName;
/*     */   public MCH_BulletModel bombletModel;
/*     */   public MCH_DamageFactor damageFactor;
/*     */   public String group;
/*     */   public List<MuzzleFlash> listMuzzleFlash;
/*     */   public List<MuzzleFlash> listMuzzleFlashSmoke;
/* 111 */   public int flakParticlesCrack = 10;
/*     */ 
/*     */ 
/*     */   
/* 115 */   public int numParticlesFlak = 3;
/*     */ 
/*     */ 
/*     */   
/* 119 */   public float flakParticlesDiff = 0.3F;
/*     */   public String hitSound;
/* 121 */   public String hitSoundIron = "hit_metal";
/*     */ 
/*     */   
/*     */   public float hitSoundRange;
/*     */   
/*     */   public boolean hitSoundEnable = false;
/*     */   
/*     */   public boolean entityHitSoundEnable = false;
/*     */   
/*     */   public boolean isHeatSeekerMissile = true;
/*     */   
/*     */   public boolean isRadarMissile = false;
/*     */   
/* 134 */   public int maxDegreeOfMissile = 60;
/*     */   
/* 136 */   public int tickEndHoming = -1;
/*     */ 
/*     */ 
/*     */   
/* 140 */   public int maxLockOnRange = 300;
/*     */ 
/*     */ 
/*     */   
/* 144 */   public int maxLockOnAngle = 10;
/*     */ 
/*     */ 
/*     */   
/* 148 */   public float pdHDNMaxDegree = 1000.0F;
/*     */ 
/*     */ 
/*     */   
/* 152 */   public int pdHDNMaxDegreeLockOutCount = 10;
/*     */ 
/*     */ 
/*     */   
/* 156 */   public int antiFlareCount = -1;
/*     */ 
/*     */ 
/*     */   
/* 160 */   public int lockMinHeight = 25;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean passiveRadar = false;
/*     */ 
/*     */ 
/*     */   
/* 169 */   public int passiveRadarLockOutCount = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean laserGuidance = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasLaserGuidancePod = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enableOffAxis = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   public double turningFactor = 0.5D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enableChunkLoader = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean activeRadar = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public int scanInterval = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 209 */   public int weaponSwitchCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 214 */   public String weaponSwitchSound = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 219 */   public float recoilPitch = 0.0F;
/*     */ 
/*     */ 
/*     */   
/* 223 */   public float recoilYaw = 0.0F;
/*     */ 
/*     */ 
/*     */   
/* 227 */   public float recoilPitchRange = 0.0F;
/*     */ 
/*     */ 
/*     */   
/* 231 */   public float recoilYawRange = 0.0F;
/*     */ 
/*     */ 
/*     */   
/* 235 */   public float recoilRecoverFactor = 0.8F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   public float speedFactor = 0.0F;
/*     */ 
/*     */ 
/*     */   
/* 244 */   public int speedFactorStartTick = 0;
/*     */ 
/*     */ 
/*     */   
/* 248 */   public int speedFactorEndTick = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean speedDependsAircraft = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canLockMissile = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enableBVR = false;
/*     */ 
/*     */ 
/*     */   
/* 264 */   public int minRangeBVR = 300;
/*     */   
/*     */   public MCH_WeaponInfo(AddonResourceLocation location, String path) {
/* 267 */     super(location, path);
/* 268 */     this.name = location.getResourcePath();
/* 269 */     this.displayName = this.name;
/* 270 */     this.type = "";
/* 271 */     this.power = 0;
/* 272 */     this.acceleration = 1.0F;
/* 273 */     this.accelerationInWater = 1.0F;
/* 274 */     this.explosion = 0;
/* 275 */     this.explosionBlock = -1;
/* 276 */     this.explosionInWater = 0;
/* 277 */     this.explosionAltitude = 0;
/* 278 */     this.delayFuse = 0;
/* 279 */     this.timeFuse = 0;
/* 280 */     this.flaming = false;
/* 281 */     this.sight = MCH_SightType.NONE;
/* 282 */     this.zoom = new float[] { 1.0F };
/* 283 */     this.delay = 10;
/* 284 */     this.reloadTime = 30;
/* 285 */     this.round = 0;
/* 286 */     this.suppliedNum = 1;
/* 287 */     this.roundItems = new ArrayList<>();
/* 288 */     this.maxAmmo = 0;
/* 289 */     this.soundDelay = 0;
/* 290 */     this.soundPattern = 0;
/* 291 */     this.soundVolume = 1.0F;
/* 292 */     this.soundPitch = 1.0F;
/* 293 */     this.soundPitchRandom = 0.1F;
/* 294 */     this.lockTime = 30;
/* 295 */     this.ridableOnly = false;
/* 296 */     this.proximityFuseDist = 0.0F;
/* 297 */     this.rigidityTime = 7;
/* 298 */     this.accuracy = 0.0F;
/* 299 */     this.bomblet = 0;
/* 300 */     this.bombletSTime = 10;
/* 301 */     this.bombletDiff = 0.3F;
/* 302 */     this.modeNum = 0;
/* 303 */     this.fixMode = 0;
/* 304 */     this.piercing = 0;
/* 305 */     this.heatCount = 0;
/* 306 */     this.maxHeatCount = 0;
/* 307 */     this.bulletModelName = "";
/* 308 */     this.bombletModelName = "";
/* 309 */     this.bulletModel = null;
/* 310 */     this.bombletModel = null;
/* 311 */     this.isFAE = false;
/* 312 */     this.isGuidedTorpedo = false;
/* 313 */     this.gravity = 0.0F;
/* 314 */     this.gravityInWater = 0.0F;
/* 315 */     this.velocityInWater = 0.999F;
/* 316 */     this.destruct = false;
/* 317 */     this.trajectoryParticleName = "explode";
/* 318 */     this.trajectoryParticleStartTick = 0;
/* 319 */     this.cartridge = null;
/* 320 */     this.disableSmoke = false;
/* 321 */     this.color = new MCH_Color();
/* 322 */     this.colorInWater = new MCH_Color();
/* 323 */     this.soundFileName = this.name + "_snd";
/* 324 */     this.smokeMaxAge = 100;
/* 325 */     this.smokeNum = 1;
/* 326 */     this.smokeSize = 2.0F;
/* 327 */     this.dispenseItem = null;
/* 328 */     this.dispenseDamege = 0;
/* 329 */     this.dispenseRange = 1;
/* 330 */     this.recoilBufCount = 2;
/* 331 */     this.recoilBufCountSpeed = 3;
/* 332 */     this.length = 0.0F;
/* 333 */     this.radius = 0.0F;
/* 334 */     this.target = 1;
/* 335 */     this.recoil = 0.0F;
/* 336 */     this.damageFactor = null;
/* 337 */     this.group = "";
/* 338 */     this.listMuzzleFlash = null;
/* 339 */     this.listMuzzleFlashSmoke = null;
/* 340 */     this.displayMortarDistance = false;
/* 341 */     this.fixCameraPitch = false;
/* 342 */     this.cameraRotationSpeedPitch = 1.0F;
/* 343 */     this.nukeYield = 0;
/* 344 */     this.chemYield = 0;
/* 345 */     this.chemSpeed = 1.25D;
/* 346 */     this.chemType = 0;
/*     */   }
/*     */   
/*     */   public void checkData() {
/* 350 */     if (this.explosionBlock < 0) {
/* 351 */       this.explosionBlock = this.explosion;
/*     */     }
/*     */     
/* 354 */     if (this.fixMode >= this.modeNum) {
/* 355 */       this.fixMode = 0;
/*     */     }
/*     */     
/* 358 */     if (this.round <= 0) {
/* 359 */       this.round = this.maxAmmo;
/*     */     }
/*     */     
/* 362 */     if (this.round > this.maxAmmo) {
/* 363 */       this.round = this.maxAmmo;
/*     */     }
/*     */     
/* 366 */     if (this.explosion <= 0) {
/* 367 */       this.isFAE = false;
/*     */     }
/*     */     
/* 370 */     if (this.delayFuse <= 0) {
/* 371 */       this.bound = 0.0F;
/*     */     }
/*     */     
/* 374 */     if (this.isFAE) {
/* 375 */       this.explosionInWater = 0;
/*     */     }
/*     */     
/* 378 */     if (this.bomblet > 0 && this.bombletSTime < 1) {
/* 379 */       this.bombletSTime = 1;
/*     */     }
/*     */     
/* 382 */     if (this.destruct) {
/* 383 */       this.delay = 1000000;
/*     */     }
/*     */     
/* 386 */     this.angle = (float)(Math.atan2(this.radius, this.length) * 180.0D / Math.PI);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadItemData(String item, String data) {
/* 391 */     if (item.compareTo("displayname") == 0) {
/* 392 */       this.displayName = data;
/* 393 */     } else if (item.compareTo("type") == 0) {
/* 394 */       this.type = data.toLowerCase();
/* 395 */       if (this.type.equalsIgnoreCase("bomb") || this.type.equalsIgnoreCase("dispenser")) {
/* 396 */         this.gravity = -0.03F;
/* 397 */         this.gravityInWater = -0.03F;
/*     */       } 
/* 399 */     } else if (item.compareTo("group") == 0) {
/* 400 */       this.group = data.toLowerCase().trim();
/* 401 */     } else if (item.compareTo("power") == 0) {
/* 402 */       this.power = toInt(data);
/* 403 */     } else if (item.equalsIgnoreCase("sound")) {
/* 404 */       this.soundFileName = data.toLowerCase().trim();
/* 405 */     } else if (item.compareTo("acceleration") == 0) {
/* 406 */       this.acceleration = toFloat(data, 0.0F, 100.0F);
/* 407 */     } else if (item.compareTo("accelerationinwater") == 0) {
/* 408 */       this.accelerationInWater = toFloat(data, 0.0F, 100.0F);
/* 409 */     } else if (item.compareTo("gravity") == 0) {
/* 410 */       this.gravity = toFloat(data, -50.0F, 50.0F);
/* 411 */     } else if (item.compareTo("gravityinwater") == 0) {
/* 412 */       this.gravityInWater = toFloat(data, -50.0F, 50.0F);
/* 413 */     } else if (item.equalsIgnoreCase("VelocityInWater")) {
/* 414 */       this.velocityInWater = toFloat(data);
/* 415 */     } else if (item.compareTo("explosion") == 0) {
/* 416 */       this.explosion = toInt(data, 0, 50);
/* 417 */     } else if (item.equalsIgnoreCase("explosionBlock")) {
/* 418 */       this.explosionBlock = toInt(data, 0, 50);
/* 419 */     } else if (item.compareTo("explosioninwater") == 0) {
/* 420 */       this.explosionInWater = toInt(data, 0, 50);
/* 421 */     } else if (item.equalsIgnoreCase("ExplosionAltitude")) {
/* 422 */       this.explosionAltitude = toInt(data, 0, 100);
/* 423 */     } else if (item.equalsIgnoreCase("TimeFuse")) {
/* 424 */       this.timeFuse = toInt(data, 0, 100000);
/* 425 */     } else if (item.equalsIgnoreCase("DelayFuse")) {
/* 426 */       this.delayFuse = toInt(data, 0, 100000);
/* 427 */     } else if (item.equalsIgnoreCase("Bound")) {
/* 428 */       this.bound = toFloat(data, 0.0F, 100000.0F);
/* 429 */     } else if (item.compareTo("flaming") == 0) {
/* 430 */       this.flaming = toBool(data);
/* 431 */     } else if (item.equalsIgnoreCase("DisplayMortarDistance")) {
/* 432 */       this.displayMortarDistance = toBool(data);
/* 433 */     } else if (item.equalsIgnoreCase("FixCameraPitch")) {
/* 434 */       this.fixCameraPitch = toBool(data);
/* 435 */     } else if (item.equalsIgnoreCase("CameraRotationSpeedPitch")) {
/* 436 */       this.cameraRotationSpeedPitch = toFloat(data, 0.0F, 100.0F);
/* 437 */     } else if (item.compareTo("sight") == 0) {
/* 438 */       data = data.toLowerCase();
/* 439 */       if (data.compareTo("movesight") == 0) {
/* 440 */         this.sight = MCH_SightType.ROCKET;
/*     */       }
/*     */       
/* 443 */       if (data.compareTo("missilesight") == 0) {
/* 444 */         this.sight = MCH_SightType.LOCK;
/*     */       }
/* 446 */     } else if (item.equalsIgnoreCase("Zoom")) {
/* 447 */       String[] s = splitParam(data);
/* 448 */       if (s.length > 0) {
/* 449 */         this.zoom = new float[s.length];
/*     */         
/* 451 */         for (int i = 0; i < s.length; i++) {
/* 452 */           this.zoom[i] = toFloat(s[i], 0.1F, 10.0F);
/*     */         }
/*     */       } 
/* 455 */     } else if (item.compareTo("delay") == 0) {
/* 456 */       this.delay = toInt(data, 0, 100000);
/* 457 */     } else if (item.compareTo("reloadtime") == 0) {
/* 458 */       this.reloadTime = toInt(data, 3, 1000);
/* 459 */     } else if (item.compareTo("round") == 0) {
/* 460 */       this.round = toInt(data, 1, 30000);
/* 461 */     } else if (item.equalsIgnoreCase("MaxAmmo")) {
/* 462 */       this.maxAmmo = toInt(data, 0, 30000);
/* 463 */     } else if (item.equalsIgnoreCase("SuppliedNum")) {
/* 464 */       this.suppliedNum = toInt(data, 1, 30000);
/* 465 */     } else if (item.equalsIgnoreCase("Item")) {
/* 466 */       String[] s = data.split("\\s*,\\s*");
/* 467 */       if (s.length >= 2 && !s[1].isEmpty() && this.roundItems.size() < 3) {
/* 468 */         int n = toInt(s[0], 1, 64);
/* 469 */         if (n > 0) {
/* 470 */           int damage = (s.length >= 3) ? toInt(s[2], 0, 100000000) : 0;
/* 471 */           this.roundItems.add(new RoundItem(n, s[1].toLowerCase().trim(), damage));
/*     */         } 
/*     */       } 
/* 474 */     } else if (item.compareTo("sounddelay") == 0) {
/* 475 */       this.soundDelay = toInt(data, 0, 1000);
/* 476 */     } else if (item.compareTo("soundpattern") != 0) {
/* 477 */       if (item.compareTo("soundvolume") == 0) {
/* 478 */         this.soundVolume = toFloat(data, 0.0F, 1000.0F);
/* 479 */       } else if (item.compareTo("soundpitch") == 0) {
/* 480 */         this.soundPitch = toFloat(data, 0.0F, 1.0F);
/* 481 */       } else if (item.equalsIgnoreCase("SoundPitchRandom")) {
/* 482 */         this.soundPitchRandom = toFloat(data, 0.0F, 1.0F);
/* 483 */       } else if (item.compareTo("locktime") == 0) {
/* 484 */         this.lockTime = toInt(data, 2, 1000);
/* 485 */       } else if (item.equalsIgnoreCase("RidableOnly")) {
/* 486 */         this.ridableOnly = toBool(data);
/* 487 */       } else if (item.compareTo("proximityfusedist") == 0) {
/* 488 */         this.proximityFuseDist = toFloat(data, 0.0F, 2000.0F);
/* 489 */       } else if (item.equalsIgnoreCase("RigidityTime")) {
/* 490 */         this.rigidityTime = toInt(data, 0, 1000000);
/* 491 */       } else if (item.compareTo("accuracy") == 0) {
/* 492 */         this.accuracy = toFloat(data, 0.0F, 1000.0F);
/* 493 */       } else if (item.compareTo("bomblet") == 0) {
/* 494 */         this.bomblet = toInt(data, 0, 1000);
/* 495 */       } else if (item.compareTo("bombletstime") == 0) {
/* 496 */         this.bombletSTime = toInt(data, 0, 1000);
/* 497 */       } else if (item.equalsIgnoreCase("BombletDiff")) {
/* 498 */         this.bombletDiff = toFloat(data, 0.0F, 1000.0F);
/* 499 */       } else if (item.equalsIgnoreCase("RecoilBufCount")) {
/* 500 */         String[] s = splitParam(data);
/* 501 */         if (s.length >= 1) {
/* 502 */           this.recoilBufCount = toInt(s[0], 1, 10000);
/*     */         }
/*     */         
/* 505 */         if (s.length >= 2 && this.recoilBufCount > 2) {
/* 506 */           this.recoilBufCountSpeed = toInt(s[1], 1, 10000) - 1;
/* 507 */           if (this.recoilBufCountSpeed > this.recoilBufCount / 2) {
/* 508 */             this.recoilBufCountSpeed = this.recoilBufCount / 2;
/*     */           }
/*     */         } 
/* 511 */       } else if (item.compareTo("modenum") == 0) {
/* 512 */         this.modeNum = toInt(data, 0, 1000);
/* 513 */       } else if (item.equalsIgnoreCase("FixMode")) {
/* 514 */         this.fixMode = toInt(data, 0, 10);
/* 515 */       } else if (item.compareTo("piercing") == 0) {
/* 516 */         this.piercing = toInt(data, 0, 100000);
/* 517 */       } else if (item.compareTo("heatcount") == 0) {
/* 518 */         this.heatCount = toInt(data, 0, 100000);
/* 519 */       } else if (item.compareTo("maxheatcount") == 0) {
/* 520 */         this.maxHeatCount = toInt(data, 0, 100000);
/* 521 */       } else if (item.compareTo("modelbullet") == 0) {
/* 522 */         this.bulletModelName = data.toLowerCase().trim();
/* 523 */       } else if (item.equalsIgnoreCase("ModelBomblet")) {
/* 524 */         this.bombletModelName = data.toLowerCase().trim();
/* 525 */       } else if (item.compareTo("fae") == 0) {
/* 526 */         this.isFAE = toBool(data);
/* 527 */       } else if (item.compareTo("guidedtorpedo") == 0) {
/* 528 */         this.isGuidedTorpedo = toBool(data);
/* 529 */       } else if (item.compareTo("destruct") == 0) {
/* 530 */         this.destruct = toBool(data);
/* 531 */       } else if (item.equalsIgnoreCase("AddMuzzleFlash")) {
/* 532 */         String[] sx = splitParam(data);
/* 533 */         if (sx.length >= 7) {
/* 534 */           if (this.listMuzzleFlash == null) {
/* 535 */             this.listMuzzleFlash = new ArrayList<>();
/*     */           }
/*     */           
/* 538 */           this.listMuzzleFlash
/* 539 */             .add(new MuzzleFlash(
/*     */                 
/* 541 */                 toFloat(sx[0]), 
/* 542 */                 toFloat(sx[1]), 0.0F, 
/*     */                 
/* 544 */                 toInt(sx[2]), 
/* 545 */                 toFloat(sx[3]) / 255.0F, 
/* 546 */                 toFloat(sx[4]) / 255.0F, 
/* 547 */                 toFloat(sx[5]) / 255.0F, 
/* 548 */                 toFloat(sx[6]) / 255.0F, 1));
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 553 */       else if (item.equalsIgnoreCase("AddMuzzleFlashSmoke")) {
/* 554 */         String[] sx = splitParam(data);
/* 555 */         if (sx.length >= 9) {
/* 556 */           if (this.listMuzzleFlashSmoke == null) {
/* 557 */             this.listMuzzleFlashSmoke = new ArrayList<>();
/*     */           }
/*     */           
/* 560 */           this.listMuzzleFlashSmoke
/* 561 */             .add(new MuzzleFlash(
/*     */                 
/* 563 */                 toFloat(sx[0]), 
/* 564 */                 toFloat(sx[2]), 
/* 565 */                 toFloat(sx[3]), 
/* 566 */                 toInt(sx[4]), 
/* 567 */                 toFloat(sx[5]) / 255.0F, 
/* 568 */                 toFloat(sx[6]) / 255.0F, 
/* 569 */                 toFloat(sx[7]) / 255.0F, 
/* 570 */                 toFloat(sx[8]) / 255.0F, 
/* 571 */                 toInt(sx[1], 1, 1000)));
/*     */         }
/*     */       
/*     */       }
/* 575 */       else if (item.equalsIgnoreCase("TrajectoryParticle")) {
/* 576 */         this.trajectoryParticleName = data.toLowerCase().trim();
/* 577 */         if (this.trajectoryParticleName.equalsIgnoreCase("none")) {
/* 578 */           this.trajectoryParticleName = "";
/*     */         }
/* 580 */       } else if (item.equalsIgnoreCase("TrajectoryParticleStartTick")) {
/* 581 */         this.trajectoryParticleStartTick = toInt(data, 0, 10000);
/* 582 */       } else if (item.equalsIgnoreCase("DisableSmoke")) {
/* 583 */         this.disableSmoke = toBool(data);
/* 584 */       } else if (item.equalsIgnoreCase("SetCartridge")) {
/* 585 */         String[] sx = data.split("\\s*,\\s*");
/* 586 */         if (sx.length > 0 && !sx[0].isEmpty()) {
/* 587 */           float ac = (sx.length >= 2) ? toFloat(sx[1]) : 0.0F;
/* 588 */           float yw = (sx.length >= 3) ? toFloat(sx[2]) : 0.0F;
/* 589 */           float pt = (sx.length >= 4) ? toFloat(sx[3]) : 0.0F;
/* 590 */           float sc = (sx.length >= 5) ? toFloat(sx[4]) : 1.0F;
/* 591 */           float gr = (sx.length >= 6) ? toFloat(sx[5]) : -0.04F;
/* 592 */           float bo = (sx.length >= 7) ? toFloat(sx[6]) : 0.5F;
/* 593 */           this.cartridge = new MCH_Cartridge(sx[0].toLowerCase(), ac, yw, pt, bo, gr, sc);
/*     */         } 
/* 595 */       } else if (item.equalsIgnoreCase("BulletColorInWater") || item.equalsIgnoreCase("BulletColor") || item.equalsIgnoreCase("SmokeColor")) {
/* 596 */         String[] sx = data.split("\\s*,\\s*");
/* 597 */         if (sx.length >= 4) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 602 */           MCH_Color c = new MCH_Color(0.003921569F * toInt(sx[0], 0, 255), 0.003921569F * toInt(sx[1], 0, 255), 0.003921569F * toInt(sx[2], 0, 255), 0.003921569F * toInt(sx[3], 0, 255));
/*     */           
/* 604 */           if (item.equalsIgnoreCase("BulletColorInWater")) {
/* 605 */             this.colorInWater = c;
/*     */           } else {
/* 607 */             this.color = c;
/*     */           } 
/*     */         } 
/* 610 */       } else if (item.equalsIgnoreCase("nukeYield")) {
/* 611 */         this.nukeYield = toInt(data, 0, 100000);
/* 612 */       } else if (item.equalsIgnoreCase("chemYield")) {
/* 613 */         this.chemYield = toInt(data, 0, 100000);
/* 614 */       } else if (item.equalsIgnoreCase("chemSpeed")) {
/* 615 */         this.chemSpeed = toDouble(data);
/* 616 */       } else if (item.equalsIgnoreCase("chemType")) {
/* 617 */         this.chemType = toInt(data, 0, 3);
/* 618 */       } else if (item.equalsIgnoreCase("NukeEffectOnly")) {
/* 619 */         this.nukeEffectOnly = toBool(data);
/* 620 */       } else if (item.equalsIgnoreCase("MaxDegreeOfMissile")) {
/* 621 */         this.maxDegreeOfMissile = toInt(data, 0, 100000);
/* 622 */       } else if (item.equalsIgnoreCase("TickEndHoming")) {
/* 623 */         this.tickEndHoming = toInt(data, -1, 100000);
/* 624 */       } else if (item.equalsIgnoreCase("FlakParticlesCrack")) {
/* 625 */         this.flakParticlesCrack = toInt(data, 0, 300);
/* 626 */       } else if (item.equalsIgnoreCase("ParticlesFlak")) {
/* 627 */         this.numParticlesFlak = toInt(data, 0, 100);
/* 628 */       } else if (item.equalsIgnoreCase("FlakParticlesDiff")) {
/* 629 */         this.flakParticlesDiff = toFloat(data);
/* 630 */       } else if (item.equalsIgnoreCase("IsRadarMissile")) {
/* 631 */         this.isRadarMissile = toBool(data);
/* 632 */       } else if (item.equalsIgnoreCase("IsHeatSeekerMissile")) {
/* 633 */         this.isHeatSeekerMissile = toBool(data);
/* 634 */       } else if (item.equalsIgnoreCase("MaxLockOnRange")) {
/* 635 */         this.maxLockOnRange = toInt(data, 0, 2000);
/* 636 */       } else if (item.equalsIgnoreCase("MaxLockOnAngle")) {
/* 637 */         this.maxLockOnAngle = toInt(data, 0, 200);
/* 638 */       } else if (item.equalsIgnoreCase("PDHDNMaxDegree")) {
/* 639 */         this.pdHDNMaxDegree = toFloat(data, -1.0F, 90.0F);
/* 640 */       } else if (item.equalsIgnoreCase("PDHDNMaxDegreeLockOutCount")) {
/* 641 */         this.pdHDNMaxDegreeLockOutCount = toInt(data, 0, 200);
/* 642 */       } else if (item.equalsIgnoreCase("AntiFlareCount")) {
/* 643 */         this.antiFlareCount = toInt(data, -1, 200);
/* 644 */       } else if (item.equalsIgnoreCase("LockMinHeight")) {
/* 645 */         this.lockMinHeight = toInt(data, -1, 100);
/* 646 */       } else if (item.equalsIgnoreCase("PassiveRadar")) {
/* 647 */         this.passiveRadar = toBool(data);
/* 648 */       } else if (item.equalsIgnoreCase("PassiveRadarLockOutCount")) {
/* 649 */         this.passiveRadarLockOutCount = toInt(data, 0, 200);
/* 650 */       } else if (item.equalsIgnoreCase("LaserGuidance")) {
/* 651 */         this.laserGuidance = toBool(data);
/* 652 */       } else if (item.equalsIgnoreCase("HasLaserGuidancePod")) {
/* 653 */         this.hasLaserGuidancePod = toBool(data);
/* 654 */       } else if (item.equalsIgnoreCase("ActiveRadar")) {
/* 655 */         this.activeRadar = toBool(data);
/* 656 */       } else if (item.equalsIgnoreCase("EnableOffAxis")) {
/* 657 */         this.enableOffAxis = toBool(data);
/* 658 */       } else if (item.equalsIgnoreCase("TurningFactor")) {
/* 659 */         this.turningFactor = toDouble(data);
/* 660 */       } else if (item.equalsIgnoreCase("EnableChunkLoader")) {
/* 661 */         this.enableChunkLoader = toBool(data);
/* 662 */       } else if (item.equalsIgnoreCase("ScanInterval")) {
/* 663 */         this.scanInterval = toInt(data);
/* 664 */       } else if (item.equalsIgnoreCase("WeaponSwitchCount")) {
/* 665 */         this.weaponSwitchCount = toInt(data);
/* 666 */       } else if (item.equalsIgnoreCase("WeaponSwitchSound")) {
/* 667 */         this.weaponSwitchSound = data.toLowerCase().trim();
/* 668 */       } else if (item.equalsIgnoreCase("RecoilPitch")) {
/* 669 */         this.recoilPitch = toFloat(data);
/* 670 */       } else if (item.equalsIgnoreCase("RecoilYaw")) {
/* 671 */         this.recoilYaw = toFloat(data);
/* 672 */       } else if (item.equalsIgnoreCase("RecoilPitchRange")) {
/* 673 */         this.recoilPitchRange = toFloat(data);
/* 674 */       } else if (item.equalsIgnoreCase("RecoilYawRange")) {
/* 675 */         this.recoilYawRange = toFloat(data);
/* 676 */       } else if (item.equalsIgnoreCase("RecoilRecoverFactor")) {
/* 677 */         this.recoilRecoverFactor = toFloat(data);
/* 678 */       } else if (item.equalsIgnoreCase("SpeedFactor")) {
/* 679 */         this.speedFactor = toFloat(data);
/* 680 */       } else if (item.equalsIgnoreCase("SpeedFactorStartTick")) {
/* 681 */         this.speedFactorStartTick = toInt(data);
/* 682 */       } else if (item.equalsIgnoreCase("SpeedFactorEndTick")) {
/* 683 */         this.speedFactorEndTick = toInt(data);
/* 684 */       } else if (item.equalsIgnoreCase("SpeedDependsAircraft")) {
/* 685 */         this.speedDependsAircraft = toBool(data);
/* 686 */       } else if (item.equalsIgnoreCase("CanLockMissile")) {
/* 687 */         this.canLockMissile = toBool(data);
/* 688 */       } else if (item.equalsIgnoreCase("EnableBVR")) {
/* 689 */         this.enableBVR = toBool(data);
/* 690 */       } else if (item.equalsIgnoreCase("MinRangeBVR")) {
/* 691 */         this.minRangeBVR = toInt(data);
/*     */       
/*     */       }
/* 694 */       else if (item.equalsIgnoreCase("SmokeSize")) {
/* 695 */         this.smokeSize = toFloat(data, 0.0F, 100.0F);
/* 696 */       } else if (item.equalsIgnoreCase("SmokeNum")) {
/* 697 */         this.smokeNum = toInt(data, 1, 100);
/* 698 */       } else if (item.equalsIgnoreCase("SmokeMaxAge")) {
/* 699 */         this.smokeMaxAge = toInt(data, 2, 1000);
/* 700 */       } else if (item.equalsIgnoreCase("DispenseItem")) {
/* 701 */         String[] sx = data.split("\\s*,\\s*");
/* 702 */         if (sx.length >= 2) {
/* 703 */           this.dispenseDamege = toInt(sx[1], 0, 100000000);
/*     */         }
/*     */         
/* 706 */         this.dispenseItem = W_Item.getItemByName(sx[0]);
/* 707 */       } else if (item.equalsIgnoreCase("DispenseRange")) {
/* 708 */         this.dispenseRange = toInt(data, 1, 100);
/* 709 */       } else if (item.equalsIgnoreCase("Length")) {
/* 710 */         this.length = toInt(data, 1, 300);
/* 711 */       } else if (item.equalsIgnoreCase("Radius")) {
/* 712 */         this.radius = toInt(data, 1, 1000);
/* 713 */       } else if (item.equalsIgnoreCase("Target")) {
/* 714 */         if (data.contains("block")) {
/* 715 */           this.target = 64;
/*     */         } else {
/* 717 */           this.target = 0;
/* 718 */           this.target |= data.contains("planes") ? 32 : 0;
/* 719 */           this.target |= data.contains("helicopters") ? 16 : 0;
/* 720 */           this.target |= data.contains("vehicles") ? 8 : 0;
/* 721 */           this.target |= data.contains("tanks") ? 8 : 0;
/* 722 */           this.target |= data.contains("players") ? 4 : 0;
/* 723 */           this.target |= data.contains("monsters") ? 2 : 0;
/* 724 */           this.target |= data.contains("others") ? 1 : 0;
/*     */         } 
/* 726 */       } else if (item.equalsIgnoreCase("MarkTime")) {
/* 727 */         this.markTime = toInt(data, 1, 30000) + 1;
/* 728 */       } else if (item.equalsIgnoreCase("Recoil")) {
/* 729 */         this.recoil = toFloat(data, 0.0F, 100.0F);
/* 730 */       } else if (item.equalsIgnoreCase("DamageFactor")) {
/* 731 */         String[] sx = splitParam(data);
/* 732 */         if (sx.length >= 2) {
/* 733 */           Class<? extends Entity> c = null;
/* 734 */           String className = sx[0].toLowerCase();
/* 735 */           switch (className) { case "player": 
/*     */             case "heli": case "helicopter": 
/*     */             case "plane": 
/*     */             case "tank": 
/*     */             case "vehicle": 
/*     */             default:
/* 741 */               break; }  c = c;
/*     */ 
/*     */           
/* 744 */           if (c != null) {
/* 745 */             if (this.damageFactor == null) {
/* 746 */               this.damageFactor = new MCH_DamageFactor();
/*     */             }
/*     */             
/* 749 */             this.damageFactor.add(c, toFloat(sx[1], 0.0F, 1000000.0F));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getDamageFactor(Entity e) {
/* 757 */     return (this.damageFactor != null) ? this.damageFactor.getDamageFactor(e) : 1.0F;
/*     */   }
/*     */   
/*     */   public String getWeaponTypeName() {
/* 761 */     if (this.type.equalsIgnoreCase("MachineGun1"))
/* 762 */       return "MachineGun"; 
/* 763 */     if (this.type.equalsIgnoreCase("MachineGun2"))
/* 764 */       return "MachineGun"; 
/* 765 */     if (this.type.equalsIgnoreCase("Torpedo"))
/* 766 */       return "Torpedo"; 
/* 767 */     if (this.type.equalsIgnoreCase("CAS"))
/* 768 */       return "CAS"; 
/* 769 */     if (this.type.equalsIgnoreCase("Rocket"))
/* 770 */       return "Rocket"; 
/* 771 */     if (this.type.equalsIgnoreCase("ASMissile"))
/* 772 */       return "AS Missile"; 
/* 773 */     if (this.type.equalsIgnoreCase("AAMissile"))
/* 774 */       return "AA Missile"; 
/* 775 */     if (this.type.equalsIgnoreCase("TVMissile"))
/* 776 */       return "TV Missile"; 
/* 777 */     if (this.type.equalsIgnoreCase("ATMissile"))
/* 778 */       return "AT Missile"; 
/* 779 */     if (this.type.equalsIgnoreCase("Bomb"))
/* 780 */       return "Bomb"; 
/* 781 */     if (this.type.equalsIgnoreCase("MkRocket"))
/* 782 */       return "Mk Rocket"; 
/* 783 */     if (this.type.equalsIgnoreCase("Dummy"))
/* 784 */       return "Dummy"; 
/* 785 */     if (this.type.equalsIgnoreCase("Smoke"))
/* 786 */       return "Smoke"; 
/* 787 */     if (this.type.equalsIgnoreCase("Dispenser")) {
/* 788 */       return "Dispenser";
/*     */     }
/* 790 */     return this.type.equalsIgnoreCase("TargetingPod") ? "Targeting Pod" : "";
/*     */   }
/*     */   
/*     */   public static class MuzzleFlash
/*     */   {
/*     */     public final float dist;
/*     */     public final float size;
/*     */     public final float range;
/*     */     public final int age;
/*     */     public final float a;
/*     */     public final float r;
/*     */     public final float g;
/*     */     public final float b;
/*     */     public final int num;
/*     */     
/*     */     public MuzzleFlash(float dist, float size, float range, int age, float a, float r, float g, float b, int num) {
/* 806 */       this.dist = dist;
/* 807 */       this.size = size;
/* 808 */       this.range = range;
/* 809 */       this.age = age;
/* 810 */       this.a = a;
/* 811 */       this.r = r;
/* 812 */       this.g = g;
/* 813 */       this.b = b;
/* 814 */       this.num = num;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RoundItem {
/*     */     public final int num;
/*     */     public final String itemName;
/*     */     public final int damage;
/* 822 */     public ItemStack itemStack = ItemStack.EMPTY;
/*     */     
/*     */     public RoundItem(int n, String name, int damage) {
/* 825 */       this.num = n;
/* 826 */       this.itemName = name;
/* 827 */       this.damage = damage;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */