/*     */ package com.norwood.mcheli.weapon;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
import java.util.Objects;
/*     */ 
/*     */ public class MCH_WeaponTorpedo extends MCH_WeaponBase {
/*     */   public MCH_WeaponTorpedo(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  13 */     super(w, v, yaw, pitch, nm, wi);
/*  14 */     this.acceleration = 0.5F;
/*  15 */     this.explosionPower = 8;
/*  16 */     this.power = 35;
/*  17 */     this.interval = -100;
/*  18 */     if (w.isRemote) {
/*  19 */       this.interval -= 10;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shot(MCH_WeaponParam prm) {
/*  25 */     if (getInfo() != null) {
/*  26 */       return (getInfo()).isGuidedTorpedo ? shotGuided(prm) : shotNoGuided(prm);
/*     */     }
/*  28 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shotNoGuided(MCH_WeaponParam prm) {
/*  33 */     if (!this.worldObj.isRemote) {
/*  34 */       float yaw = prm.rotYaw;
/*  35 */       float pitch = prm.rotPitch;
/*  36 */       double mx = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/*  37 */       double mz = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/*  38 */       double my = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
/*  39 */       mx = mx * (getInfo()).acceleration + prm.entity.motionX;
/*  40 */       my = my * (getInfo()).acceleration + prm.entity.motionY;
/*  41 */       mz = mz * (getInfo()).acceleration + prm.entity.motionZ;
/*  42 */       this.acceleration = MathHelper.sqrt(mx * mx + my * my + mz * mz);
/*  43 */       MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, mx, my, mz, yaw, 0.0F, this.acceleration);
/*  44 */       e.setName(this.name);
/*  45 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/*  46 */       e.motionX = mx;
/*  47 */       e.motionY = my;
/*  48 */       e.motionZ = mz;
/*  49 */       e.accelerationInWater = (getInfo() != null) ? (getInfo()).accelerationInWater : 1.0D;
/*  50 */       this.worldObj.spawnEntity((Entity)e);
/*  51 */       playSound(prm.entity);
/*     */     } 
/*  53 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean shotGuided(MCH_WeaponParam prm) {
/*  57 */     float yaw = prm.user.rotationYaw;
/*  58 */     float pitch = prm.user.rotationPitch;
/*  59 */     Objects.requireNonNull(prm); Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -yaw, -pitch, -0.0F);
/*  60 */     double tX = v.x;
/*  61 */     double tZ = v.z;
/*  62 */     double tY = v.y;
/*  63 */     double dist = MathHelper.sqrt(tX * tX + tY * tY + tZ * tZ);
/*  64 */     if (this.worldObj.isRemote) {
/*  65 */       tX = tX * 100.0D / dist;
/*  66 */       tY = tY * 100.0D / dist;
/*  67 */       tZ = tZ * 100.0D / dist;
/*     */     } else {
/*  69 */       tX = tX * 150.0D / dist;
/*  70 */       tY = tY * 150.0D / dist;
/*  71 */       tZ = tZ * 150.0D / dist;
/*     */     } 
/*     */     
/*  74 */     Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.posX, prm.user.posY, prm.user.posZ);
/*  75 */     Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.posX + tX, prm.user.posY + tY, prm.user.posZ + tZ);
/*  76 */     RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
/*  77 */     if (W_MovingObjectPosition.isHitTypeTile(m) && 
/*  78 */       MCH_Lib.isBlockInWater(this.worldObj, m.getBlockPos().getX(), m.getBlockPos().getY(), m.getBlockPos().getZ())) {
/*  79 */       if (!this.worldObj.isRemote) {
/*  80 */         double mx = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/*  81 */         double mz = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/*  82 */         double my = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
/*  83 */         mx = mx * (getInfo()).acceleration + prm.entity.motionX;
/*  84 */         my = my * (getInfo()).acceleration + prm.entity.motionY;
/*  85 */         mz = mz * (getInfo()).acceleration + prm.entity.motionZ;
/*  86 */         this.acceleration = MathHelper.sqrt(mx * mx + my * my + mz * mz);
/*  87 */         MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, prm.entity.motionX, prm.entity.motionY, prm.entity.motionZ, yaw, 0.0F, this.acceleration);
/*     */ 
/*     */         
/*  90 */         e.setName(this.name);
/*  91 */         e.setParameterFromWeapon(this, prm.entity, prm.user);
/*  92 */         e.targetPosX = m.hitVec.x;
/*  93 */         e.targetPosY = m.hitVec.y;
/*  94 */         e.targetPosZ = m.hitVec.z;
/*  95 */         e.motionX = mx;
/*  96 */         e.motionY = my;
/*  97 */         e.motionZ = mz;
/*  98 */         e.accelerationInWater = (getInfo() != null) ? (getInfo()).accelerationInWater : 1.0D;
/*  99 */         this.worldObj.spawnEntity((Entity)e);
/* 100 */         playSound(prm.entity);
/*     */       } 
/*     */       
/* 103 */       return true;
/*     */     } 
/* 105 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponTorpedo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */