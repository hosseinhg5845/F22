/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_PacketNotifyLock;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.uav.MCH_EntityUavStation;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class MCH_WeaponGuidanceSystem
/*     */ {
/*     */   public Entity lastLockEntity;
/*     */   public boolean canLockInWater;
/*     */   public boolean canLockOnGround;
/*     */   public boolean canLockInAir;
/*     */   public boolean ridableOnly;
/*     */   public double lockRange;
/*     */   public int lockAngle;
/*     */   public MCH_IEntityLockChecker checker;
/*     */   protected World worldObj;
/*     */   private Entity targetEntity;
/*     */   private int lockCount;
/*     */   private int lockSoundCount;
/*     */   private int continueLockCount;
/*     */   private int lockCountMax;
/*     */   private int prevLockCount;
/*     */   
/*     */   public MCH_WeaponGuidanceSystem() {
/*  38 */     this(null);
/*     */   }
/*     */   
/*     */   public MCH_WeaponGuidanceSystem(World w) {
/*  42 */     this.worldObj = w;
/*  43 */     this.targetEntity = null;
/*  44 */     this.lastLockEntity = null;
/*  45 */     this.lockCount = 0;
/*  46 */     this.continueLockCount = 0;
/*  47 */     this.lockCountMax = 1;
/*  48 */     this.prevLockCount = 0;
/*  49 */     this.canLockInWater = false;
/*  50 */     this.canLockOnGround = false;
/*  51 */     this.canLockInAir = false;
/*  52 */     this.ridableOnly = false;
/*  53 */     this.lockRange = 50.0D;
/*  54 */     this.lockAngle = 10;
/*  55 */     this.checker = null;
/*     */   }
/*     */   
/*     */   public static boolean isEntityOnGround(@Nullable Entity entity) {
/*  59 */     if (entity != null && !entity.field_70128_L) {
/*  60 */       if (entity.field_70122_E) {
/*  61 */         return true;
/*     */       }
/*     */       
/*  64 */       for (int i = 0; i < 12; i++) {
/*  65 */         int x = (int)(entity.field_70165_t + 0.5D);
/*  66 */         int y = (int)(entity.field_70163_u + 0.5D) - i;
/*  67 */         int z = (int)(entity.field_70161_v + 0.5D);
/*  68 */         int blockId = W_WorldFunc.getBlockId(entity.field_70170_p, x, y, z);
/*  69 */         if (blockId != 0 && !W_WorldFunc.isBlockWater(entity.field_70170_p, x, y, z)) {
/*  70 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     return false;
/*     */   }
/*     */   
/*     */   public static float getEntityStealth(@Nullable Entity entity) {
/*  79 */     if (entity instanceof MCH_EntityAircraft) {
/*  80 */       return ((MCH_EntityAircraft)entity).getStealth();
/*     */     }
/*  82 */     return (entity != null && entity.func_184187_bx() instanceof MCH_EntityAircraft) ? ((MCH_EntityAircraft)entity.func_184187_bx()).getStealth() : 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean inLockRange(Entity entity, float rotationYaw, float rotationPitch, Entity target, float lockAng) {
/*  87 */     double dx = target.field_70165_t - entity.field_70165_t;
/*  88 */     double dy = target.field_70163_u + (target.field_70131_O / 2.0F) - entity.field_70163_u;
/*  89 */     double dz = target.field_70161_v - entity.field_70161_v;
/*  90 */     float entityYaw = (float)MCH_Lib.getRotate360(rotationYaw);
/*  91 */     float targetYaw = (float)MCH_Lib.getRotate360(Math.atan2(dz, dx) * 180.0D / Math.PI);
/*  92 */     float diffYaw = (float)MCH_Lib.getRotate360((targetYaw - entityYaw - 90.0F));
/*  93 */     double dxz = Math.sqrt(dx * dx + dz * dz);
/*  94 */     float targetPitch = -((float)(Math.atan2(dy, dxz) * 180.0D / Math.PI));
/*  95 */     float diffPitch = targetPitch - rotationPitch;
/*  96 */     return ((diffYaw < lockAng || diffYaw > 360.0F - lockAng) && Math.abs(diffPitch) < lockAng);
/*     */   }
/*     */   
/*     */   public void setWorld(World w) {
/* 100 */     this.worldObj = w;
/*     */   }
/*     */   
/*     */   public int getLockCountMax() {
/* 104 */     float stealth = getEntityStealth(this.targetEntity);
/* 105 */     return (int)(this.lockCountMax + this.lockCountMax * stealth);
/*     */   }
/*     */   
/*     */   public void setLockCountMax(int i) {
/* 109 */     this.lockCountMax = (i > 0) ? i : 1;
/*     */   }
/*     */   
/*     */   public int getLockCount() {
/* 113 */     return this.lockCount;
/*     */   }
/*     */   
/*     */   public boolean isLockingEntity(Entity entity) {
/* 117 */     return (getLockCount() > 0 && this.targetEntity != null && !this.targetEntity.field_70128_L && W_Entity.isEqual(entity, this.targetEntity));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity getLockingEntity() {
/* 122 */     return (getLockCount() > 0 && this.targetEntity != null && !this.targetEntity.field_70128_L) ? this.targetEntity : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity getTargetEntity() {
/* 127 */     return this.targetEntity;
/*     */   }
/*     */   
/*     */   public boolean isLockComplete() {
/* 131 */     return (getLockCount() == getLockCountMax() && this.lastLockEntity != null);
/*     */   }
/*     */   
/*     */   public void update() {
/* 135 */     if (this.worldObj != null && this.worldObj.field_72995_K) {
/* 136 */       if (this.lockCount != this.prevLockCount) {
/* 137 */         this.prevLockCount = this.lockCount;
/*     */       } else {
/* 139 */         this.lockCount = this.prevLockCount = 0;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean lock(Entity user) {
/* 145 */     return lock(user, true);
/*     */   }
/*     */   
/*     */   public boolean lock(Entity user, boolean isLockContinue) {
/* 149 */     if (!this.worldObj.field_72995_K) {
/* 150 */       return false;
/*     */     }
/* 152 */     boolean result = false;
/* 153 */     if (this.lockCount == 0) {
/*     */       
/* 155 */       List<Entity> list = this.worldObj.func_72839_b(user, user.func_174813_aQ().func_72314_b(this.lockRange, this.lockRange, this.lockRange));
/* 156 */       Entity tgtEnt = null;
/* 157 */       double dist = this.lockRange * this.lockRange * 2.0D;
/*     */       
/* 159 */       for (Entity entity : list) {
/* 160 */         if (canLockEntity(entity)) {
/* 161 */           double dx = entity.field_70165_t - user.field_70165_t;
/* 162 */           double dy = entity.field_70163_u - user.field_70163_u;
/* 163 */           double dz = entity.field_70161_v - user.field_70161_v;
/* 164 */           double d = dx * dx + dy * dy + dz * dz;
/* 165 */           Entity entityLocker = getLockEntity(user);
/* 166 */           float stealth = 1.0F - getEntityStealth(entity);
/* 167 */           double range = this.lockRange * stealth;
/* 168 */           float angle = this.lockAngle * (stealth / 2.0F + 0.5F);
/* 169 */           if (d < range * range && d < dist && inLockRange(entityLocker, user.field_70177_z, user.field_70125_A, entity, angle)) {
/* 170 */             Vec3d v1 = W_WorldFunc.getWorldVec3(this.worldObj, entityLocker.field_70165_t, entityLocker.field_70163_u, entityLocker.field_70161_v);
/* 171 */             Vec3d v2 = W_WorldFunc.getWorldVec3(this.worldObj, entity.field_70165_t, entity.field_70163_u + (entity.field_70131_O / 2.0F), entity.field_70161_v);
/* 172 */             RayTraceResult m = W_WorldFunc.clip(this.worldObj, v1, v2, false, true, false);
/* 173 */             if (m == null || W_MovingObjectPosition.isHitTypeEntity(m)) {
/* 174 */               tgtEnt = entity;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 180 */       this.targetEntity = tgtEnt;
/* 181 */       if (tgtEnt != null) {
/* 182 */         this.lockCount++;
/*     */       }
/* 184 */     } else if (this.targetEntity != null && !this.targetEntity.field_70128_L) {
/* 185 */       boolean canLock = (this.canLockInWater || !this.targetEntity.func_70090_H());
/*     */       
/* 187 */       boolean ong = isEntityOnGround(this.targetEntity);
/* 188 */       if (!this.canLockOnGround && ong) {
/* 189 */         canLock = false;
/*     */       }
/*     */       
/* 192 */       if (!this.canLockInAir && !ong) {
/* 193 */         canLock = false;
/*     */       }
/*     */       
/* 196 */       if (canLock) {
/* 197 */         double dx = this.targetEntity.field_70165_t - user.field_70165_t;
/* 198 */         double dy = this.targetEntity.field_70163_u - user.field_70163_u;
/* 199 */         double dz = this.targetEntity.field_70161_v - user.field_70161_v;
/* 200 */         float stealth = 1.0F - getEntityStealth(this.targetEntity);
/* 201 */         double range = this.lockRange * stealth;
/* 202 */         if (dx * dx + dy * dy + dz * dz < range * range) {
/* 203 */           if (this.worldObj.field_72995_K && this.lockSoundCount == 1) {
/* 204 */             MCH_PacketNotifyLock.send(getTargetEntity());
/*     */           }
/*     */           
/* 207 */           this.lockSoundCount = (this.lockSoundCount + 1) % 15;
/* 208 */           Entity entityLocker = getLockEntity(user);
/* 209 */           if (inLockRange(entityLocker, user.field_70177_z, user.field_70125_A, this.targetEntity, this.lockAngle)) {
/* 210 */             if (this.lockCount < getLockCountMax()) {
/* 211 */               this.lockCount++;
/*     */             }
/* 213 */           } else if (this.continueLockCount > 0) {
/* 214 */             this.continueLockCount--;
/* 215 */             if (this.continueLockCount <= 0 && this.lockCount > 0) {
/* 216 */               this.lockCount--;
/*     */             }
/*     */           } else {
/* 219 */             this.continueLockCount = 0;
/* 220 */             this.lockCount--;
/*     */           } 
/*     */           
/* 223 */           if (this.lockCount >= getLockCountMax()) {
/* 224 */             if (this.continueLockCount <= 0) {
/* 225 */               this.continueLockCount = getLockCountMax() / 3;
/* 226 */               if (this.continueLockCount > 20) {
/* 227 */                 this.continueLockCount = 20;
/*     */               }
/*     */             } 
/*     */             
/* 231 */             result = true;
/* 232 */             this.lastLockEntity = this.targetEntity;
/* 233 */             if (isLockContinue) {
/* 234 */               this.prevLockCount = this.lockCount - 1;
/*     */             } else {
/* 236 */               clearLock();
/*     */             } 
/*     */           } 
/*     */         } else {
/* 240 */           clearLock();
/*     */         } 
/*     */       } else {
/* 243 */         clearLock();
/*     */       } 
/*     */     } else {
/* 246 */       clearLock();
/*     */     } 
/*     */     
/* 249 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearLock() {
/* 254 */     this.targetEntity = null;
/* 255 */     this.lockCount = 0;
/* 256 */     this.continueLockCount = 0;
/* 257 */     this.lockSoundCount = 0;
/*     */   }
/*     */   
/*     */   public Entity getLockEntity(Entity entity) {
/* 261 */     Entity entity1 = entity.func_184187_bx(); if (entity1 instanceof MCH_EntityUavStation) { MCH_EntityUavStation us = (MCH_EntityUavStation)entity1;
/* 262 */       if (us.getControlAircract() != null) {
/* 263 */         return (Entity)us.getControlAircract();
/*     */       } }
/*     */ 
/*     */     
/* 267 */     return entity;
/*     */   }
/*     */   
/*     */   public boolean canLockEntity(Entity entity) {
/* 271 */     if (this.ridableOnly && entity instanceof net.minecraft.entity.player.EntityPlayer && entity.func_184187_bx() == null) {
/* 272 */       return false;
/*     */     }
/* 274 */     String className = entity.getClass().getName();
/* 275 */     if (className.contains("EntityCamera"))
/* 276 */       return false; 
/* 277 */     if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_EntityAircraft))
/* 278 */       return false; 
/* 279 */     if (!this.canLockInWater && entity.func_70090_H())
/* 280 */       return false; 
/* 281 */     if (this.checker != null && !this.checker.canLockEntity(entity)) {
/* 282 */       return false;
/*     */     }
/* 284 */     boolean ong = isEntityOnGround(entity);
/* 285 */     return ((this.canLockOnGround || !ong) && (this.canLockInAir || ong));
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponGuidanceSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */