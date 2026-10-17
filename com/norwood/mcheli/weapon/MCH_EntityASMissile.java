/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_EntityASMissile
/*     */   extends MCH_EntityBaseBullet {
/*     */   public double targetPosX;
/*     */   
/*     */   public MCH_EntityASMissile(World par1World) {
/*  15 */     super(par1World);
/*  16 */     this.targetPosX = 0.0D;
/*  17 */     this.targetPosY = 0.0D;
/*  18 */     this.targetPosZ = 0.0D;
/*     */   }
/*     */   public double targetPosY;
/*     */   public double targetPosZ;
/*     */   
/*     */   public MCH_EntityASMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/*  24 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getGravity() {
/*  29 */     return (getBomblet() == 1) ? -0.03F : super.getGravity();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getGravityInWater() {
/*  34 */     return (getBomblet() == 1) ? -0.03F : super.getGravityInWater();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  39 */     super.func_70071_h_();
/*  40 */     if (getInfo() != null && !(getInfo()).disableSmoke && getBomblet() == 0) {
/*  41 */       spawnParticle((getInfo()).trajectoryParticleName, 3, 10.0F * (getInfo()).smokeSize * 0.5F);
/*     */     }
/*     */     
/*  44 */     if (getInfo() != null && !this.field_70170_p.field_72995_K && this.isBomblet != 1) {
/*  45 */       Block block = W_WorldFunc.getBlock(this.field_70170_p, (int)this.targetPosX, (int)this.targetPosY, (int)this.targetPosZ);
/*  46 */       if (block.func_149703_v()) {
/*  47 */         double dist = func_70011_f(this.targetPosX, this.targetPosY, this.targetPosZ);
/*  48 */         if (dist < (getInfo()).proximityFuseDist) {
/*  49 */           if ((getInfo()).bomblet > 0) {
/*  50 */             for (int i = 0; i < (getInfo()).bomblet; i++) {
/*  51 */               sprinkleBomblet();
/*     */             }
/*     */           } else {
/*  54 */             RayTraceResult mop = new RayTraceResult((Entity)this);
/*  55 */             onImpact(mop, 1.0F);
/*     */           } 
/*     */           
/*  58 */           func_70106_y();
/*  59 */         } else if (getGravity() == 0.0D) {
/*  60 */           double up = 0.0D;
/*  61 */           if (getCountOnUpdate() < 10) {
/*  62 */             up = 20.0D;
/*     */           }
/*     */           
/*  65 */           double x = this.targetPosX - this.field_70165_t;
/*  66 */           double y = this.targetPosY + up - this.field_70163_u;
/*  67 */           double z = this.targetPosZ - this.field_70161_v;
/*  68 */           double d = MathHelper.func_76133_a(x * x + y * y + z * z);
/*  69 */           this.field_70159_w = x * this.acceleration / d;
/*  70 */           this.field_70181_x = y * this.acceleration / d;
/*  71 */           this.field_70179_y = z * this.acceleration / d;
/*     */         } else {
/*  73 */           double x = this.targetPosX - this.field_70165_t;
/*  74 */           double y = this.targetPosY - this.field_70163_u;
/*  75 */           y *= 0.3D;
/*  76 */           double z = this.targetPosZ - this.field_70161_v;
/*  77 */           double d = MathHelper.func_76133_a(x * x + y * y + z * z);
/*  78 */           this.field_70159_w = x * this.acceleration / d;
/*  79 */           this.field_70179_y = z * this.acceleration / d;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
/*  85 */     this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
/*  86 */     double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*  87 */     this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
/*  88 */     onUpdateBomblet();
/*     */   }
/*     */ 
/*     */   
/*     */   public void sprinkleBomblet() {
/*  93 */     if (!this.field_70170_p.field_72995_K) {
/*     */       
/*  95 */       MCH_EntityASMissile e = new MCH_EntityASMissile(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70146_Z.nextInt(360), 0.0F, this.acceleration);
/*     */       
/*  97 */       e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
/*  98 */       e.setName(func_70005_c_());
/*  99 */       float RANDOM = (getInfo()).bombletDiff;
/* 100 */       e.field_70159_w = this.field_70159_w * 0.5D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
/* 101 */       e.field_70181_x = this.field_70181_x * 0.5D / 2.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM / 2.0F);
/* 102 */       e.field_70179_y = this.field_70179_y * 0.5D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
/* 103 */       e.setBomblet();
/* 104 */       this.field_70170_p.func_72838_d((Entity)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_BulletModel getDefaultBulletModel() {
/* 110 */     return MCH_DefaultBulletModels.ASMissile;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityASMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */