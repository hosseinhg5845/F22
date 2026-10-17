/*     */ package com.norwood.mcheli.weapon;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_WeaponTorpedo extends MCH_WeaponBase {
/*     */   public MCH_WeaponTorpedo(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  13 */     super(w, v, yaw, pitch, nm, wi);
/*  14 */     this.acceleration = 0.5F;
/*  15 */     this.explosionPower = 8;
/*  16 */     this.power = 35;
/*  17 */     this.interval = -100;
/*  18 */     if (w.field_72995_K) {
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
/*  33 */     if (!this.worldObj.field_72995_K) {
/*  34 */       float yaw = prm.rotYaw;
/*  35 */       float pitch = prm.rotPitch;
/*  36 */       double mx = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/*  37 */       double mz = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/*  38 */       double my = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/*  39 */       mx = mx * (getInfo()).acceleration + prm.entity.field_70159_w;
/*  40 */       my = my * (getInfo()).acceleration + prm.entity.field_70181_x;
/*  41 */       mz = mz * (getInfo()).acceleration + prm.entity.field_70179_y;
/*  42 */       this.acceleration = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
/*  43 */       MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, mx, my, mz, yaw, 0.0F, this.acceleration);
/*  44 */       e.setName(this.name);
/*  45 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/*  46 */       e.field_70159_w = mx;
/*  47 */       e.field_70181_x = my;
/*  48 */       e.field_70179_y = mz;
/*  49 */       e.accelerationInWater = (getInfo() != null) ? (getInfo()).accelerationInWater : 1.0D;
/*  50 */       this.worldObj.func_72838_d((Entity)e);
/*  51 */       playSound(prm.entity);
/*     */     } 
/*  53 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean shotGuided(MCH_WeaponParam prm) {
/*  57 */     float yaw = prm.user.field_70177_z;
/*  58 */     float pitch = prm.user.field_70125_A;
/*  59 */     Objects.requireNonNull(prm); Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -yaw, -pitch, -0.0F);
/*  60 */     double tX = v.field_72450_a;
/*  61 */     double tZ = v.field_72449_c;
/*  62 */     double tY = v.field_72448_b;
/*  63 */     double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/*  64 */     if (this.worldObj.field_72995_K) {
/*  65 */       tX = tX * 100.0D / dist;
/*  66 */       tY = tY * 100.0D / dist;
/*  67 */       tZ = tZ * 100.0D / dist;
/*     */     } else {
/*  69 */       tX = tX * 150.0D / dist;
/*  70 */       tY = tY * 150.0D / dist;
/*  71 */       tZ = tZ * 150.0D / dist;
/*     */     } 
/*     */     
/*  74 */     Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.field_70165_t, prm.user.field_70163_u, prm.user.field_70161_v);
/*  75 */     Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.field_70165_t + tX, prm.user.field_70163_u + tY, prm.user.field_70161_v + tZ);
/*  76 */     RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
/*  77 */     if (W_MovingObjectPosition.isHitTypeTile(m) && 
/*  78 */       MCH_Lib.isBlockInWater(this.worldObj, m.func_178782_a().func_177958_n(), m.func_178782_a().func_177956_o(), m.func_178782_a().func_177952_p())) {
/*  79 */       if (!this.worldObj.field_72995_K) {
/*  80 */         double mx = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/*  81 */         double mz = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/*  82 */         double my = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/*  83 */         mx = mx * (getInfo()).acceleration + prm.entity.field_70159_w;
/*  84 */         my = my * (getInfo()).acceleration + prm.entity.field_70181_x;
/*  85 */         mz = mz * (getInfo()).acceleration + prm.entity.field_70179_y;
/*  86 */         this.acceleration = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
/*  87 */         MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, prm.entity.field_70159_w, prm.entity.field_70181_x, prm.entity.field_70179_y, yaw, 0.0F, this.acceleration);
/*     */ 
/*     */         
/*  90 */         e.setName(this.name);
/*  91 */         e.setParameterFromWeapon(this, prm.entity, prm.user);
/*  92 */         e.targetPosX = m.field_72307_f.field_72450_a;
/*  93 */         e.targetPosY = m.field_72307_f.field_72448_b;
/*  94 */         e.targetPosZ = m.field_72307_f.field_72449_c;
/*  95 */         e.field_70159_w = mx;
/*  96 */         e.field_70181_x = my;
/*  97 */         e.field_70179_y = mz;
/*  98 */         e.accelerationInWater = (getInfo() != null) ? (getInfo()).accelerationInWater : 1.0D;
/*  99 */         this.worldObj.func_72838_d((Entity)e);
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