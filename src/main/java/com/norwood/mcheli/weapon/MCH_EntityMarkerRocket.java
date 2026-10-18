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
/*  18 */   private static final DataParameter<Byte> MARKER_STATUS = EntityDataManager.createKey(MCH_EntityMarkerRocket.class, DataSerializers.BYTE);
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
/*     */   protected void entityInit() {
/*  37 */     super.entityInit();
/*  38 */     this.dataManager.register(MARKER_STATUS, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public int getMarkerStatus() {
/*  42 */     return ((Byte)this.dataManager.get(MARKER_STATUS)).byteValue();
/*     */   }
/*     */   
/*     */   public void setMarkerStatus(int n) {
/*  46 */     if (!this.world.isRemote) {
/*  47 */       this.dataManager.set(MARKER_STATUS, Byte.valueOf((byte)n));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  58 */     int status = getMarkerStatus();
/*  59 */     if (this.world.isRemote) {
/*  60 */       if (getInfo() == null) {
/*  61 */         super.onUpdate();
/*     */       }
/*     */       
/*  64 */       if (getInfo() != null && !(getInfo()).disableSmoke && status != 0) {
/*  65 */         if (status == 1) {
/*  66 */           super.onUpdate();
/*  67 */           spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);
/*     */         } else {
/*  69 */           float gb = this.rand.nextFloat() * 0.3F;
/*  70 */           spawnParticle("explode", 5, (10 + this.rand
/*     */ 
/*     */               
/*  73 */               .nextInt(4)), this.rand
/*  74 */               .nextFloat() * 0.2F + 0.8F, gb, gb, (this.rand
/*     */ 
/*     */               
/*  77 */               .nextFloat() - 0.5F) * 0.7F, 0.3F + this.rand
/*  78 */               .nextFloat() * 0.3F, (this.rand
/*  79 */               .nextFloat() - 0.5F) * 0.7F);
/*     */         }
/*     */       
/*     */       }
/*  83 */     } else if (status == 0 || isInWater()) {
/*  84 */       setDead();
/*  85 */     } else if (status == 1) {
/*  86 */       super.onUpdate();
/*  87 */     } else if (this.countDown > 0) {
/*  88 */       this.countDown--;
/*  89 */       if (this.countDown == 40) {
/*  90 */         int num = 6 + this.rand.nextInt(2);
/*     */         
/*  92 */         for (int i = 0; i < num; i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  97 */           MCH_EntityBomb e = new MCH_EntityBomb(this.world, this.posX + ((this.rand.nextFloat() - 0.5F) * 15.0F), (260.0F + this.rand.nextFloat() * 10.0F + (i * 30)), this.posZ + ((this.rand.nextFloat() - 0.5F) * 15.0F), 0.0D, -0.5D, 0.0D, 0.0F, 90.0F, 4.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 105 */           e.setName(getName());
/* 106 */           e.explosionPower = 3 + this.rand.nextInt(2);
/* 107 */           e.explosionPowerInWater = 0;
/* 108 */           e.setPower(30);
/* 109 */           e.piercing = 0;
/* 110 */           e.shootingAircraft = this.shootingAircraft;
/* 111 */           e.shootingEntity = this.shootingEntity;
/* 112 */           this.world.spawnEntity((Entity)e);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 116 */       setDead();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void spawnParticle(String name, int num, float size, float r, float g, float b, float mx, float my, float mz) {
/* 121 */     if (this.world.isRemote) {
/* 122 */       if (name.isEmpty() || num < 1 || num > 50) {
/*     */         return;
/*     */       }
/*     */       
/* 126 */       double x = (this.posX - this.prevPosX) / num;
/* 127 */       double y = (this.posY - this.prevPosY) / num;
/* 128 */       double z = (this.posZ - this.prevPosZ) / num;
/*     */       
/* 130 */       for (int i = 0; i < num; i++) {
/* 131 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", this.prevPosX + x * i, this.prevPosY + y * i, this.prevPosZ + z * i);
/* 132 */         prm.motionX = mx;
/* 133 */         prm.motionY = my;
/* 134 */         prm.motionZ = mz;
/* 135 */         prm.size = size + this.rand.nextFloat();
/* 136 */         prm.setColor(1.0F, r, g, b);
/* 137 */         prm.isEffectWind = true;
/* 138 */         MCH_ParticlesUtil.spawnParticle(prm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onImpact(RayTraceResult m, float damageFactor) {
/* 145 */     if (!this.world.isRemote && 
/* 146 */       m.entityHit == null && !W_MovingObjectPosition.isHitTypeEntity(m)) {
/* 147 */       BlockPos blockpos = m.getBlockPos();
/* 148 */       blockpos = blockpos.offset(m.sideHit);
/* 149 */       if (this.world.isAirBlock(blockpos)) {
/* 150 */         if (MCH_Config.Explosion_FlamingBlock.prmBool) {
/* 151 */           W_WorldFunc.setBlock(this.world, blockpos, (Block)W_Blocks.FIRE);
/*     */         }
/*     */         
/* 154 */         int noAirBlockCount = 0;
/*     */         
/* 156 */         for (int i = 1; i < 256 && (
/* 157 */           this.world.isAirBlock(blockpos.up(i)) || 
/* 158 */           ++noAirBlockCount < 5); i++);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 164 */         if (noAirBlockCount < 5) {
/* 165 */           setMarkerStatus(2);
/* 166 */           setPosition(blockpos.getX() + 0.5D, blockpos.getY() + 1.1D, blockpos.getZ() + 0.5D);
/* 167 */           this.prevPosX = this.posX;
/* 168 */           this.prevPosY = this.posY;
/* 169 */           this.prevPosZ = this.posZ;
/* 170 */           this.countDown = 100;
/*     */         } else {
/* 172 */           setDead();
/*     */         } 
/*     */       } else {
/* 175 */         setDead();
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