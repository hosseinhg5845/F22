/*     */ package com.norwood.mcheli.throwable;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Explosion;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.particles.MCH_ParticleParam;
/*     */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.registry.IThrowableEntity;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityThrowable
/*     */   extends EntityThrowable implements IThrowableEntity {
/*  26 */   private static final DataParameter<String> INFO_NAME = EntityDataManager.createKey(MCH_EntityThrowable.class, DataSerializers.STRING);
/*     */   public double boundPosX;
/*     */   public double boundPosY;
/*     */   public double boundPosZ;
/*     */   public RayTraceResult lastOnImpact;
/*     */   public int noInfoCount;
/*     */   private int countOnUpdate;
/*     */   private MCH_ThrowableInfo throwableInfo;
/*     */   
/*     */   public MCH_EntityThrowable(World par1World) {
/*  36 */     super(par1World);
/*  37 */     init();
/*     */   }
/*     */   
/*     */   public MCH_EntityThrowable(World par1World, EntityLivingBase par2EntityLivingBase, float acceleration) {
/*  41 */     super(par1World, par2EntityLivingBase);
/*  42 */     this.motionX *= acceleration;
/*  43 */     this.motionY *= acceleration;
/*  44 */     this.motionZ *= acceleration;
/*  45 */     init();
/*     */   }
/*     */   
/*     */   public MCH_EntityThrowable(World par1World, double par2, double par4, double par6) {
/*  49 */     super(par1World, par2, par4, par6);
/*  50 */     init();
/*     */   }
/*     */   
/*     */   public MCH_EntityThrowable(World worldIn, double x, double y, double z, float yaw, float pitch) {
/*  54 */     this(worldIn);
/*  55 */     setSize(0.25F, 0.25F);
/*  56 */     setLocationAndAngles(x, y, z, yaw, pitch);
/*  57 */     this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  58 */     this.posY -= 0.10000000149011612D;
/*  59 */     this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  60 */     setPosition(this.posX, this.posY, this.posZ);
/*  61 */     shoot((Entity)null, pitch, yaw, 0.0F, 1.5F, 1.0F);
/*     */   }
/*     */   
/*     */   public void init() {
/*  65 */     this.lastOnImpact = null;
/*  66 */     this.countOnUpdate = 0;
/*  67 */     setInfo((MCH_ThrowableInfo)null);
/*  68 */     this.noInfoCount = 0;
/*  69 */     this.dataManager.register(INFO_NAME, "");
/*     */   }
/*     */   
/*     */   public void shoot(@NotNull Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
/*  73 */     float f = 0.4F;
/*  74 */     this.motionX = (-MathHelper.sin(rotationYawIn / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitchIn / 180.0F * 3.1415927F) * f);
/*  75 */     this.motionZ = (MathHelper.cos(rotationYawIn / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitchIn / 180.0F * 3.1415927F) * f);
/*  76 */     this.motionY = (-MathHelper.sin((rotationPitchIn + pitchOffset) / 180.0F * 3.1415927F) * f);
/*  77 */     shoot(this.motionX, this.motionY, this.motionZ, velocity, 1.0F);
/*     */   }
/*     */   
/*     */   public void setDead() {
/*  81 */     String s = (getInfo() != null) ? (getInfo()).name : "null";
/*  82 */     MCH_Lib.DbgLog(this.world, "MCH_EntityThrowable.setDead(%s)", new Object[] { s });
/*  83 */     super.setDead();
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/*  87 */     this.boundPosX = this.posX;
/*  88 */     this.boundPosY = this.posY;
/*  89 */     this.boundPosZ = this.posZ;
/*  90 */     if (getInfo() != null) {
/*  91 */       Block block = W_WorldFunc.getBlock(this.world, (int)(this.posX + 0.5D), (int)this.posY, (int)(this.posZ + 0.5D));
/*  92 */       Material mat = W_WorldFunc.getBlockMaterial(this.world, (int)(this.posX + 0.5D), (int)this.posY, (int)(this.posZ + 0.5D));
/*     */ 
/*     */       
/*  95 */       if (mat == Material.WATER) {
/*  96 */         this.motionY += (getInfo()).gravityInWater;
/*     */       } else {
/*  98 */         this.motionY += (getInfo()).gravity;
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     super.onUpdate();
/* 103 */     if (this.lastOnImpact != null) {
/* 104 */       boundBullet(this.lastOnImpact);
/* 105 */       setPosition(this.boundPosX + this.motionX, this.boundPosY + this.motionY, this.boundPosZ + this.motionZ);
/* 106 */       this.lastOnImpact = null;
/*     */     } 
/*     */     
/* 109 */     this.countOnUpdate++;
/* 110 */     if (this.countOnUpdate >= 2147483632) {
/* 111 */       setDead();
/*     */     } else {
/* 113 */       if (getInfo() == null) {
/* 114 */         String s = (String)this.dataManager.get(INFO_NAME);
/* 115 */         if (!s.isEmpty()) {
/* 116 */           setInfo(MCH_ThrowableInfoManager.get(s));
/*     */         }
/*     */         
/* 119 */         if (getInfo() == null) {
/* 120 */           this.noInfoCount++;
/* 121 */           if (this.noInfoCount > 10) {
/* 122 */             setDead();
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 129 */       if (!this.isDead) {
/* 130 */         if (!this.world.isRemote) {
/* 131 */           if (this.countOnUpdate == (getInfo()).timeFuse && (getInfo()).explosion > 0) {
/* 132 */             MCH_Explosion.newExplosion(this.world, null, null, this.posX, this.posY, this.posZ, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 139 */                 (getInfo()).explosion, 
/* 140 */                 (getInfo()).explosion, true, true, false, true, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 147 */             setDead();
/*     */             
/*     */             return;
/*     */           } 
/* 151 */           if (this.countOnUpdate >= (getInfo()).aliveTime) {
/* 152 */             setDead();
/*     */           }
/* 154 */         } else if (this.countOnUpdate >= (getInfo()).timeFuse && (getInfo()).explosion <= 0) {
/* 155 */           for (int i = 0; i < (getInfo()).smokeNum; i++) {
/* 156 */             float r = (getInfo()).smokeColor.r * 0.9F + this.rand.nextFloat() * 0.1F;
/* 157 */             float g = (getInfo()).smokeColor.g * 0.9F + this.rand.nextFloat() * 0.1F;
/* 158 */             float b = (getInfo()).smokeColor.b * 0.9F + this.rand.nextFloat() * 0.1F;
/* 159 */             if ((getInfo()).smokeColor.r == (getInfo()).smokeColor.g) {
/* 160 */               g = r;
/*     */             }
/*     */             
/* 163 */             if ((getInfo()).smokeColor.r == (getInfo()).smokeColor.b) {
/* 164 */               b = r;
/*     */             }
/*     */             
/* 167 */             if ((getInfo()).smokeColor.g == (getInfo()).smokeColor.b) {
/* 168 */               b = g;
/*     */             }
/*     */             
/* 171 */             spawnParticle("explode", 4, 
/*     */ 
/*     */                 
/* 174 */                 (getInfo()).smokeSize + this.rand.nextFloat() * (getInfo()).smokeSize / 3.0F, r, g, b, 
/*     */ 
/*     */ 
/*     */                 
/* 178 */                 (getInfo()).smokeVelocityHorizontal * (this.rand.nextFloat() - 0.5F), 
/* 179 */                 (getInfo()).smokeVelocityVertical * this.rand.nextFloat(), 
/* 180 */                 (getInfo()).smokeVelocityHorizontal * (this.rand.nextFloat() - 0.5F));
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnParticle(String name, int num, float size, float r, float g, float b, float mx, float my, float mz) {
/* 189 */     if (this.world.isRemote) {
/* 190 */       if (name.isEmpty() || num < 1) {
/*     */         return;
/*     */       }
/*     */       
/* 194 */       double x = (this.posX - this.prevPosX) / num;
/* 195 */       double y = (this.posY - this.prevPosY) / num;
/* 196 */       double z = (this.posZ - this.prevPosZ) / num;
/*     */       
/* 198 */       for (int i = 0; i < num; i++) {
/* 199 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", this.prevPosX + x * i, 1.0D + this.prevPosY + y * i, this.prevPosZ + z * i);
/*     */ 
/*     */         
/* 202 */         prm.setMotion(mx, my, mz);
/* 203 */         prm.size = size;
/* 204 */         prm.setColor(1.0F, r, g, b);
/* 205 */         prm.isEffectWind = true;
/* 206 */         prm.toWhite = true;
/* 207 */         MCH_ParticlesUtil.spawnParticle(prm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected float getGravityVelocity() {
/* 213 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public void boundBullet(RayTraceResult m) {
/* 217 */     if (m.sideHit != null) {
/* 218 */       float bound = (getInfo()).bound;
/* 219 */       switch (m.sideHit) {
/*     */         case DOWN:
/*     */         case UP:
/* 222 */           this.motionX *= 0.8999999761581421D;
/* 223 */           this.motionZ *= 0.8999999761581421D;
/* 224 */           this.boundPosY = m.hitVec.y;
/* 225 */           if ((m.sideHit != EnumFacing.DOWN || this.motionY <= 0.0D) && (m.sideHit != EnumFacing.UP || this.motionY >= 0.0D)) {
/* 226 */             this.motionY = 0.0D; break;
/*     */           } 
/* 228 */           this.motionY = -this.motionY * bound;
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 232 */           if (this.motionZ > 0.0D) {
/* 233 */             this.motionZ = -this.motionZ * bound;
/*     */           }
/*     */           break;
/*     */         case SOUTH:
/* 237 */           if (this.motionZ < 0.0D) {
/* 238 */             this.motionZ = -this.motionZ * bound;
/*     */           }
/*     */           break;
/*     */         case WEST:
/* 242 */           if (this.motionX > 0.0D) {
/* 243 */             this.motionX = -this.motionX * bound;
/*     */           }
/*     */           break;
/*     */         case EAST:
/* 247 */           if (this.motionX < 0.0D)
/* 248 */             this.motionX = -this.motionX * bound; 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onImpact(@NotNull RayTraceResult m) {
/* 255 */     if (getInfo() != null) {
/* 256 */       this.lastOnImpact = m;
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MCH_ThrowableInfo getInfo() {
/* 262 */     return this.throwableInfo;
/*     */   }
/*     */   
/*     */   public void setInfo(MCH_ThrowableInfo info) {
/* 266 */     this.throwableInfo = info;
/* 267 */     if (info != null && !this.world.isRemote) {
/* 268 */       this.dataManager.set(INFO_NAME, info.name);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setThrower(Entity entity) {
/* 273 */     if (entity instanceof EntityLivingBase)
/* 274 */       this.thrower = (EntityLivingBase)entity; 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\throwable\MCH_EntityThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */