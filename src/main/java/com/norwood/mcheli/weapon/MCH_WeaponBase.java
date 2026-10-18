/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.Objects;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class MCH_WeaponBase
/*     */ {
/*  16 */   protected static final Random rand = new Random();
/*     */   public final World worldObj;
/*     */   public final Vec3d position;
/*     */   public final float fixRotationYaw;
/*     */   public final float fixRotationPitch;
/*     */   public final String name;
/*     */   public final MCH_WeaponInfo weaponInfo;
/*     */   public String displayName;
/*     */   public int power;
/*     */   public float acceleration;
/*     */   public int explosionPower;
/*     */   public int explosionPowerInWater;
/*     */   public int interval;
/*     */   public int delayedInterval;
/*     */   public int numMode;
/*     */   public final int lockTime;
/*     */   public int piercing;
/*     */   public int heatCount;
/*     */   public MCH_Cartridge cartridge;
/*     */   public boolean onTurret;
/*     */   public MCH_EntityAircraft aircraft;
/*     */   public int tick;
/*     */   public int optionParameter1;
/*     */   public int optionParameter2;
/*     */   public boolean canPlaySound;
/*     */   private int currentMode;
/*     */   public int nukeYield;
/*     */   
/*     */   public MCH_WeaponBase(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  45 */     this.worldObj = w;
/*  46 */     this.position = v;
/*  47 */     this.fixRotationYaw = yaw;
/*  48 */     this.fixRotationPitch = pitch;
/*  49 */     this.name = nm;
/*  50 */     this.weaponInfo = wi;
/*  51 */     this.displayName = (wi != null) ? wi.displayName : "";
/*  52 */     this.power = 0;
/*  53 */     this.acceleration = 0.0F;
/*  54 */     this.nukeYield = wi.nukeYield;
/*  55 */     this.explosionPower = 0;
/*  56 */     this.explosionPowerInWater = 0;
/*  57 */     this.interval = 1;
/*  58 */     this.numMode = 0;
/*  59 */     this.lockTime = 0;
/*  60 */     this.heatCount = 0;
/*  61 */     this.cartridge = null;
/*  62 */     this.tick = 0;
/*  63 */     this.optionParameter1 = 0;
/*  64 */     this.optionParameter2 = 0;
/*  65 */     setCurrentMode(0);
/*  66 */     this.canPlaySound = true;
/*     */   }
/*     */   
/*     */   public MCH_WeaponInfo getInfo() {
/*  70 */     return this.weaponInfo;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  74 */     return this.displayName;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean shot(MCH_WeaponParam paramMCH_WeaponParam);
/*     */   
/*     */   public void setLockChecker(MCH_IEntityLockChecker checker) {}
/*     */   
/*     */   public int getLockCount() {
/*  83 */     return 0;
/*     */   }
/*     */   
/*     */   public int getLockCountMax() {
/*  87 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLockCountMax(int n) {}
/*     */   
/*     */   public final int getNumAmmoMax() {
/*  94 */     return (getInfo()).round;
/*     */   }
/*     */   
/*     */   public int getCurrentMode() {
/*  98 */     return (getInfo() != null && (getInfo()).fixMode > 0) ? (getInfo()).fixMode : this.currentMode;
/*     */   }
/*     */   
/*     */   public void setCurrentMode(int currentMode) {
/* 102 */     this.currentMode = currentMode;
/*     */   }
/*     */   
/*     */   public final int getAllAmmoNum() {
/* 106 */     return (getInfo()).maxAmmo;
/*     */   }
/*     */   
/*     */   public final int getReloadCount() {
/* 110 */     return (getInfo()).reloadTime;
/*     */   }
/*     */   
/*     */   public final MCH_SightType getSightType() {
/* 114 */     return (getInfo()).sight;
/*     */   }
/*     */   
/*     */   public MCH_WeaponGuidanceSystem getGuidanceSystem() {
/* 118 */     return null;
/*     */   }
/*     */   
/*     */   public void update(int countWait) {
/* 122 */     if (countWait != 0) {
/* 123 */       this.tick++;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isCooldownCountReloadTime() {
/* 128 */     return false;
/*     */   }
/*     */   
/*     */   public void modifyCommonParameters() {
/* 132 */     modifyParameters();
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyParameters() {}
/*     */   
/*     */   public boolean switchMode() {
/* 139 */     if (getInfo() != null && (getInfo()).fixMode > 0) {
/* 140 */       return false;
/*     */     }
/* 142 */     int beforeMode = getCurrentMode();
/* 143 */     if (this.numMode > 0) {
/* 144 */       setCurrentMode((getCurrentMode() + 1) % this.numMode);
/*     */     } else {
/* 146 */       setCurrentMode(0);
/*     */     } 
/*     */     
/* 149 */     if (beforeMode != getCurrentMode()) {
/* 150 */       onSwitchMode();
/*     */     }
/*     */     
/* 153 */     return (beforeMode != getCurrentMode());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSwitchMode() {}
/*     */ 
/*     */   
/*     */   public boolean use(MCH_WeaponParam prm) {
/* 161 */     Vec3d v = getShotPos(prm.entity);
/* 162 */     prm.posX += v.x;
/* 163 */     prm.posY += v.y;
/* 164 */     prm.posZ += v.z;
/* 165 */     if (shot(prm)) {
/* 166 */       this.tick = 0;
/* 167 */       return true;
/*     */     } 
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d getShotPos(Entity entity) {
/* 174 */     if (entity instanceof MCH_EntityAircraft && this.onTurret) {
/* 175 */       return ((MCH_EntityAircraft)entity).calcOnTurretPos(this.position);
/*     */     }
/* 177 */     Vec3d v = new Vec3d(this.position.x, this.position.y, this.position.z);
/* 178 */     float roll = (entity instanceof MCH_EntityAircraft) ? ((MCH_EntityAircraft)entity).getRotRoll() : 0.0F;
/* 179 */     return MCH_Lib.RotVec3(v, -entity.rotationYaw, -entity.rotationPitch, -roll);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(Entity e) {
/* 184 */     playSound(e, (getInfo()).soundFileName);
/*     */   }
/*     */   
/*     */   public void playSound(Entity e, String snd) {
/* 188 */     if (!e.world.isRemote && this.canPlaySound && getInfo() != null) {
/* 189 */       float prnd = (getInfo()).soundPitchRandom;
/* 190 */       W_WorldFunc.MOD_playSoundEffect(this.worldObj, e.posX, e.posY, e.posZ, snd, 
/* 191 */           (getInfo()).soundVolume, (getInfo()).soundPitch * (1.0F - prnd) + rand.nextFloat() * prnd);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSoundClient(Entity e, float volume, float pitch) {
/* 197 */     if (e.world.isRemote && getInfo() != null) {
/* 198 */       W_McClient.MOD_playSoundFX((getInfo()).soundFileName, volume, pitch);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getLandInDistance(MCH_WeaponParam prm) {
/* 203 */     if (this.weaponInfo == null)
/* 204 */       return -1.0D; 
/* 205 */     if (this.weaponInfo.gravity >= 0.0F) {
/* 206 */       return -1.0D;
/*     */     }
/* 208 */     Objects.requireNonNull(prm); Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -0.0F);
/* 209 */     double s = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
/* 210 */     double acc = (this.acceleration < 4.0F) ? this.acceleration : 4.0D;
/* 211 */     double accFac = this.acceleration / acc;
/* 212 */     double my = v.y * this.acceleration / s;
/* 213 */     if (my > 0.0D) {
/* 214 */       double mx = v.x * this.acceleration / s;
/* 215 */       double mz = v.z * this.acceleration / s;
/* 216 */       double ls = my / this.weaponInfo.gravity;
/* 217 */       double gravity = this.weaponInfo.gravity * accFac;
/* 218 */       if (ls < -12.0D) {
/* 219 */         double f = ls / -12.0D;
/* 220 */         mx *= f;
/* 221 */         my *= f;
/* 222 */         mz *= f;
/* 223 */         gravity *= f * f * 0.95D;
/*     */       } 
/*     */       
/* 226 */       double spx = prm.posX;
/* 227 */       double spy = prm.posY + 3.0D;
/* 228 */       double spz = prm.posZ;
/*     */       
/* 230 */       for (int i = 0; i < 50; i++) {
/* 231 */         Vec3d vs = new Vec3d(spx, spy, spz);
/* 232 */         Vec3d ve = new Vec3d(spx + mx, spy + my, spz + mz);
/* 233 */         RayTraceResult mop = this.worldObj.rayTraceBlocks(vs, ve);
/* 234 */         if (mop != null && mop.typeOfHit == RayTraceResult.Type.BLOCK) {
/* 235 */           double dx = mop.getBlockPos().getX() - prm.posX;
/* 236 */           double dz = mop.getBlockPos().getZ() - prm.posZ;
/* 237 */           return Math.sqrt(dx * dx + dz * dz);
/*     */         } 
/*     */         
/* 240 */         my += gravity;
/* 241 */         spx += mx;
/* 242 */         spy += my;
/* 243 */         spz += mz;
/* 244 */         if (spy < prm.posY) {
/* 245 */           double dx = spx - prm.posX;
/* 246 */           double dz = spz - prm.posZ;
/* 247 */           return Math.sqrt(dx * dx + dz * dz);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 252 */     return -1.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */