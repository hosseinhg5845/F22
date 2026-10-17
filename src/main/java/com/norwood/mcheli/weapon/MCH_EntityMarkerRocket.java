/*     */ package com.norwood.mcheli.weapon;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.particles.MCH_ParticleParam;
/*     */ import com.norwood.mcheli.wrapper.W_Blocks;
/*     */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_EntityMarkerRocket extends MCH_EntityBaseBullet {
/*  18 */   private static final DataParameter<Byte> MARKER_STATUS = EntityDataManager.func_187226_a(MCH_EntityMarkerRocket.class, DataSerializers.field_187191_a);
/*     */   public int countDown;
/*     */   
/*     */   public MCH_EntityMarkerRocket(World par1World) {
/*  22 */     super(par1World);
/*  23 */     setMarkerStatus(0);
/*  24 */     this.countDown = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityMarkerRocket(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/*  30 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*  31 */     setMarkerStatus(0);
/*  32 */     this.countDown = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  37 */     super.func_70088_a();
/*  38 */     this.field_70180_af.func_187214_a(MARKER_STATUS, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public int getMarkerStatus() {
/*  42 */     return ((Byte)this.field_70180_af.func_187225_a(MARKER_STATUS)).byteValue();
/*     */   }
/*     */   
/*     */   public void setMarkerStatus(int n) {
/*  46 */     if (!this.field_70170_p.field_72995_K) {
/*  47 */       this.field_70180_af.func_187227_b(MARKER_STATUS, Byte.valueOf((byte)n));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  58 */     int status = getMarkerStatus();
/*  59 */     if (this.field_70170_p.field_72995_K) {
/*  60 */       if (getInfo() == null) {
/*  61 */         super.func_70071_h_();
/*     */       }
/*     */       
/*  64 */       if (getInfo() != null && !(getInfo()).disableSmoke && status != 0) {
/*  65 */         if (status == 1) {
/*  66 */           super.func_70071_h_();
/*  67 */           spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);
/*     */         } else {
/*  69 */           float gb = this.field_70146_Z.nextFloat() * 0.3F;
/*  70 */           spawnParticle("explode", 5, (10 + this.field_70146_Z
/*     */ 
/*     */               
/*  73 */               .nextInt(4)), this.field_70146_Z
/*  74 */               .nextFloat() * 0.2F + 0.8F, gb, gb, (this.field_70146_Z
/*     */ 
/*     */               
/*  77 */               .nextFloat() - 0.5F) * 0.7F, 0.3F + this.field_70146_Z
/*  78 */               .nextFloat() * 0.3F, (this.field_70146_Z
/*  79 */               .nextFloat() - 0.5F) * 0.7F);
/*     */         }
/*     */       
/*     */       }
/*  83 */     } else if (status == 0 || func_70090_H()) {
/*  84 */       func_70106_y();
/*  85 */     } else if (status == 1) {
/*  86 */       super.func_70071_h_();
/*  87 */     } else if (this.countDown > 0) {
/*  88 */       this.countDown--;
/*  89 */       if (this.countDown == 40) {
/*  90 */         int num = 6 + this.field_70146_Z.nextInt(2);
/*     */         
/*  92 */         for (int i = 0; i < num; i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  97 */           MCH_EntityBomb e = new MCH_EntityBomb(this.field_70170_p, this.field_70165_t + ((this.field_70146_Z.nextFloat() - 0.5F) * 15.0F), (260.0F + this.field_70146_Z.nextFloat() * 10.0F + (i * 30)), this.field_70161_v + ((this.field_70146_Z.nextFloat() - 0.5F) * 15.0F), 0.0D, -0.5D, 0.0D, 0.0F, 90.0F, 4.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 105 */           e.setName(func_70005_c_());
/* 106 */           e.explosionPower = 3 + this.field_70146_Z.nextInt(2);
/* 107 */           e.explosionPowerInWater = 0;
/* 108 */           e.setPower(30);
/* 109 */           e.piercing = 0;
/* 110 */           e.shootingAircraft = this.shootingAircraft;
/* 111 */           e.shootingEntity = this.shootingEntity;
/* 112 */           this.field_70170_p.func_72838_d((Entity)e);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 116 */       func_70106_y();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void spawnParticle(String name, int num, float size, float r, float g, float b, float mx, float my, float mz) {
/* 121 */     if (this.field_70170_p.field_72995_K) {
/* 122 */       if (name.isEmpty() || num < 1 || num > 50) {
/*     */         return;
/*     */       }
/*     */       
/* 126 */       double x = (this.field_70165_t - this.field_70169_q) / num;
/* 127 */       double y = (this.field_70163_u - this.field_70167_r) / num;
/* 128 */       double z = (this.field_70161_v - this.field_70166_s) / num;
/*     */       
/* 130 */       for (int i = 0; i < num; i++) {
/* 131 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + x * i, this.field_70167_r + y * i, this.field_70166_s + z * i);
/* 132 */         prm.motionX = mx;
/* 133 */         prm.motionY = my;
/* 134 */         prm.motionZ = mz;
/* 135 */         prm.size = size + this.field_70146_Z.nextFloat();
/* 136 */         prm.setColor(1.0F, r, g, b);
/* 137 */         prm.isEffectWind = true;
/* 138 */         MCH_ParticlesUtil.spawnParticle(prm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onImpact(RayTraceResult m, float damageFactor) {
/* 145 */     if (!this.field_70170_p.field_72995_K && 
/* 146 */       m.field_72308_g == null && !W_MovingObjectPosition.isHitTypeEntity(m)) {
/* 147 */       BlockPos blockpos = m.func_178782_a();
/* 148 */       blockpos = blockpos.func_177972_a(m.field_178784_b);
/* 149 */       if (this.field_70170_p.func_175623_d(blockpos)) {
/* 150 */         if (MCH_Config.Explosion_FlamingBlock.prmBool) {
/* 151 */           W_WorldFunc.setBlock(this.field_70170_p, blockpos, (Block)W_Blocks.field_150480_ab);
/*     */         }
/*     */         
/* 154 */         int noAirBlockCount = 0;
/*     */         
/* 156 */         for (int i = 1; i < 256 && (
/* 157 */           this.field_70170_p.func_175623_d(blockpos.func_177981_b(i)) || 
/* 158 */           ++noAirBlockCount < 5); i++);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 164 */         if (noAirBlockCount < 5) {
/* 165 */           setMarkerStatus(2);
/* 166 */           func_70107_b(blockpos.func_177958_n() + 0.5D, blockpos.func_177956_o() + 1.1D, blockpos.func_177952_p() + 0.5D);
/* 167 */           this.field_70169_q = this.field_70165_t;
/* 168 */           this.field_70167_r = this.field_70163_u;
/* 169 */           this.field_70166_s = this.field_70161_v;
/* 170 */           this.countDown = 100;
/*     */         } else {
/* 172 */           func_70106_y();
/*     */         } 
/*     */       } else {
/* 175 */         func_70106_y();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_BulletModel getDefaultBulletModel() {
/* 183 */     return MCH_DefaultBulletModels.Rocket;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityMarkerRocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */