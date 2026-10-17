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
/*  26 */   private static final DataParameter<String> INFO_NAME = EntityDataManager.func_187226_a(MCH_EntityThrowable.class, DataSerializers.field_187194_d);
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
/*  42 */     this.field_70159_w *= acceleration;
/*  43 */     this.field_70181_x *= acceleration;
/*  44 */     this.field_70179_y *= acceleration;
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
/*  55 */     func_70105_a(0.25F, 0.25F);
/*  56 */     func_70012_b(x, y, z, yaw, pitch);
/*  57 */     this.field_70165_t -= (MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.1415927F) * 0.16F);
/*  58 */     this.field_70163_u -= 0.10000000149011612D;
/*  59 */     this.field_70161_v -= (MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.1415927F) * 0.16F);
/*  60 */     func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*  61 */     func_184538_a((Entity)null, pitch, yaw, 0.0F, 1.5F, 1.0F);
/*     */   }
/*     */   
/*     */   public void init() {
/*  65 */     this.lastOnImpact = null;
/*  66 */     this.countOnUpdate = 0;
/*  67 */     setInfo((MCH_ThrowableInfo)null);
/*  68 */     this.noInfoCount = 0;
/*  69 */     this.field_70180_af.func_187214_a(INFO_NAME, "");
/*     */   }
/*     */   
/*     */   public void func_184538_a(@NotNull Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
/*  73 */     float f = 0.4F;
/*  74 */     this.field_70159_w = (-MathHelper.func_76126_a(rotationYawIn / 180.0F * 3.1415927F) * MathHelper.func_76134_b(rotationPitchIn / 180.0F * 3.1415927F) * f);
/*  75 */     this.field_70179_y = (MathHelper.func_76134_b(rotationYawIn / 180.0F * 3.1415927F) * MathHelper.func_76134_b(rotationPitchIn / 180.0F * 3.1415927F) * f);
/*  76 */     this.field_70181_x = (-MathHelper.func_76126_a((rotationPitchIn + pitchOffset) / 180.0F * 3.1415927F) * f);
/*  77 */     func_70186_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, velocity, 1.0F);
/*     */   }
/*     */   
/*     */   public void func_70106_y() {
/*  81 */     String s = (getInfo() != null) ? (getInfo()).name : "null";
/*  82 */     MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityThrowable.setDead(%s)", new Object[] { s });
/*  83 */     super.func_70106_y();
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/*  87 */     this.boundPosX = this.field_70165_t;
/*  88 */     this.boundPosY = this.field_70163_u;
/*  89 */     this.boundPosZ = this.field_70161_v;
/*  90 */     if (getInfo() != null) {
/*  91 */       Block block = W_WorldFunc.getBlock(this.field_70170_p, (int)(this.field_70165_t + 0.5D), (int)this.field_70163_u, (int)(this.field_70161_v + 0.5D));
/*  92 */       Material mat = W_WorldFunc.getBlockMaterial(this.field_70170_p, (int)(this.field_70165_t + 0.5D), (int)this.field_70163_u, (int)(this.field_70161_v + 0.5D));
/*     */ 
/*     */       
/*  95 */       if (mat == Material.field_151586_h) {
/*  96 */         this.field_70181_x += (getInfo()).gravityInWater;
/*     */       } else {
/*  98 */         this.field_70181_x += (getInfo()).gravity;
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     super.func_70071_h_();
/* 103 */     if (this.lastOnImpact != null) {
/* 104 */       boundBullet(this.lastOnImpact);
/* 105 */       func_70107_b(this.boundPosX + this.field_70159_w, this.boundPosY + this.field_70181_x, this.boundPosZ + this.field_70179_y);
/* 106 */       this.lastOnImpact = null;
/*     */     } 
/*     */     
/* 109 */     this.countOnUpdate++;
/* 110 */     if (this.countOnUpdate >= 2147483632) {
/* 111 */       func_70106_y();
/*     */     } else {
/* 113 */       if (getInfo() == null) {
/* 114 */         String s = (String)this.field_70180_af.func_187225_a(INFO_NAME);
/* 115 */         if (!s.isEmpty()) {
/* 116 */           setInfo(MCH_ThrowableInfoManager.get(s));
/*     */         }
/*     */         
/* 119 */         if (getInfo() == null) {
/* 120 */           this.noInfoCount++;
/* 121 */           if (this.noInfoCount > 10) {
/* 122 */             func_70106_y();
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 129 */       if (!this.field_70128_L) {
/* 130 */         if (!this.field_70170_p.field_72995_K) {
/* 131 */           if (this.countOnUpdate == (getInfo()).timeFuse && (getInfo()).explosion > 0) {
/* 132 */             MCH_Explosion.newExplosion(this.field_70170_p, null, null, this.field_70165_t, this.field_70163_u, this.field_70161_v, 
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
/* 147 */             func_70106_y();
/*     */             
/*     */             return;
/*     */           } 
/* 151 */           if (this.countOnUpdate >= (getInfo()).aliveTime) {
/* 152 */             func_70106_y();
/*     */           }
/* 154 */         } else if (this.countOnUpdate >= (getInfo()).timeFuse && (getInfo()).explosion <= 0) {
/* 155 */           for (int i = 0; i < (getInfo()).smokeNum; i++) {
/* 156 */             float r = (getInfo()).smokeColor.r * 0.9F + this.field_70146_Z.nextFloat() * 0.1F;
/* 157 */             float g = (getInfo()).smokeColor.g * 0.9F + this.field_70146_Z.nextFloat() * 0.1F;
/* 158 */             float b = (getInfo()).smokeColor.b * 0.9F + this.field_70146_Z.nextFloat() * 0.1F;
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
/* 174 */                 (getInfo()).smokeSize + this.field_70146_Z.nextFloat() * (getInfo()).smokeSize / 3.0F, r, g, b, 
/*     */ 
/*     */ 
/*     */                 
/* 178 */                 (getInfo()).smokeVelocityHorizontal * (this.field_70146_Z.nextFloat() - 0.5F), 
/* 179 */                 (getInfo()).smokeVelocityVertical * this.field_70146_Z.nextFloat(), 
/* 180 */                 (getInfo()).smokeVelocityHorizontal * (this.field_70146_Z.nextFloat() - 0.5F));
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnParticle(String name, int num, float size, float r, float g, float b, float mx, float my, float mz) {
/* 189 */     if (this.field_70170_p.field_72995_K) {
/* 190 */       if (name.isEmpty() || num < 1) {
/*     */         return;
/*     */       }
/*     */       
/* 194 */       double x = (this.field_70165_t - this.field_70169_q) / num;
/* 195 */       double y = (this.field_70163_u - this.field_70167_r) / num;
/* 196 */       double z = (this.field_70161_v - this.field_70166_s) / num;
/*     */       
/* 198 */       for (int i = 0; i < num; i++) {
/* 199 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + x * i, 1.0D + this.field_70167_r + y * i, this.field_70166_s + z * i);
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
/*     */   protected float func_70185_h() {
/* 213 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public void boundBullet(RayTraceResult m) {
/* 217 */     if (m.field_178784_b != null) {
/* 218 */       float bound = (getInfo()).bound;
/* 219 */       switch (m.field_178784_b) {
/*     */         case DOWN:
/*     */         case UP:
/* 222 */           this.field_70159_w *= 0.8999999761581421D;
/* 223 */           this.field_70179_y *= 0.8999999761581421D;
/* 224 */           this.boundPosY = m.field_72307_f.field_72448_b;
/* 225 */           if ((m.field_178784_b != EnumFacing.DOWN || this.field_70181_x <= 0.0D) && (m.field_178784_b != EnumFacing.UP || this.field_70181_x >= 0.0D)) {
/* 226 */             this.field_70181_x = 0.0D; break;
/*     */           } 
/* 228 */           this.field_70181_x = -this.field_70181_x * bound;
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 232 */           if (this.field_70179_y > 0.0D) {
/* 233 */             this.field_70179_y = -this.field_70179_y * bound;
/*     */           }
/*     */           break;
/*     */         case SOUTH:
/* 237 */           if (this.field_70179_y < 0.0D) {
/* 238 */             this.field_70179_y = -this.field_70179_y * bound;
/*     */           }
/*     */           break;
/*     */         case WEST:
/* 242 */           if (this.field_70159_w > 0.0D) {
/* 243 */             this.field_70159_w = -this.field_70159_w * bound;
/*     */           }
/*     */           break;
/*     */         case EAST:
/* 247 */           if (this.field_70159_w < 0.0D)
/* 248 */             this.field_70159_w = -this.field_70159_w * bound; 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_70184_a(@NotNull RayTraceResult m) {
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
/* 267 */     if (info != null && !this.field_70170_p.field_72995_K) {
/* 268 */       this.field_70180_af.func_187227_b(INFO_NAME, info.name);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setThrower(Entity entity) {
/* 273 */     if (entity instanceof EntityLivingBase)
/* 274 */       this.field_70192_c = (EntityLivingBase)entity; 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\throwable\MCH_EntityThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */