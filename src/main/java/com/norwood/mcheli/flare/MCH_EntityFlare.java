/*     */ package com.norwood.mcheli.flare;
/*     */ import com.norwood.mcheli.particles.MCH_ParticleParam;
/*     */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityFlare extends W_Entity implements IEntityAdditionalSpawnData {
/*  20 */   public double gravity = -0.013D;
/*  21 */   public double airResistance = 0.992D;
/*     */   public float size;
/*     */   public int fuseCount;
/*     */   
/*     */   public MCH_EntityFlare(World par1World) {
/*  26 */     super(par1World);
/*  27 */     func_70105_a(1.0F, 1.0F);
/*  28 */     this.field_70126_B = this.field_70177_z;
/*  29 */     this.field_70127_C = this.field_70125_A;
/*  30 */     this.size = 6.0F;
/*  31 */     this.fuseCount = 0;
/*     */   }
/*     */   
/*     */   public MCH_EntityFlare(World par1World, double pX, double pY, double pZ, double mX, double mY, double mZ, float size, int fuseCount) {
/*  35 */     this(par1World);
/*  36 */     func_70012_b(pX, pY, pZ, 0.0F, 0.0F);
/*  37 */     this.field_70159_w = mX;
/*  38 */     this.field_70181_x = mY;
/*  39 */     this.field_70179_y = mZ;
/*  40 */     this.size = size;
/*  41 */     this.fuseCount = fuseCount;
/*     */   }
/*     */   
/*     */   public boolean func_180431_b(@NotNull DamageSource source) {
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_70112_a(double par1) {
/*  51 */     double d1 = func_174813_aQ().func_72320_b() * 4.0D;
/*  52 */     d1 *= 64.0D;
/*  53 */     return (par1 < d1 * d1);
/*     */   }
/*     */   
/*     */   public void func_70106_y() {
/*  57 */     super.func_70106_y();
/*  58 */     if (this.fuseCount > 0 && this.field_70170_p.field_72995_K) {
/*  59 */       this.fuseCount = 0;
/*     */       
/*  61 */       for (int i = 0; i < 20; i++) {
/*  62 */         double x = (this.field_70146_Z.nextDouble() - 0.5D) * 10.0D;
/*  63 */         double y = (this.field_70146_Z.nextDouble() - 0.5D) * 10.0D;
/*  64 */         double z = (this.field_70146_Z.nextDouble() - 0.5D) * 10.0D;
/*  65 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v + z);
/*  66 */         prm.age = 200 + this.field_70146_Z.nextInt(100);
/*  67 */         prm.size = (20 + this.field_70146_Z.nextInt(25));
/*  68 */         prm.motionX = (this.field_70146_Z.nextDouble() - 0.5D) * 0.45D;
/*  69 */         prm.motionY = (this.field_70146_Z.nextDouble() - 0.5D) * 0.01D;
/*  70 */         prm.motionZ = (this.field_70146_Z.nextDouble() - 0.5D) * 0.45D;
/*  71 */         prm.a = this.field_70146_Z.nextFloat() * 0.1F + 0.85F;
/*  72 */         prm.b = this.field_70146_Z.nextFloat() * 0.2F + 0.5F;
/*  73 */         prm.g = prm.b + 0.05F;
/*  74 */         prm.r = prm.b + 0.1F;
/*  75 */         MCH_ParticlesUtil.spawnParticle(prm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeSpawnData(ByteBuf buffer) {
/*     */     try {
/*  82 */       buffer.writeByte(this.fuseCount);
/*  83 */     } catch (Exception var3) {
/*  84 */       var3.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void readSpawnData(ByteBuf additionalData) {
/*     */     try {
/*  90 */       this.fuseCount = additionalData.readByte();
/*  91 */     } catch (Exception var3) {
/*  92 */       var3.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/*  97 */     if (this.fuseCount > 0 && this.field_70173_aa >= this.fuseCount) {
/*  98 */       func_70106_y();
/*  99 */     } else if (!this.field_70170_p.field_72995_K && !this.field_70170_p.func_175667_e(new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v))) {
/* 100 */       func_70106_y();
/* 101 */     } else if (this.field_70173_aa > 300 && !this.field_70170_p.field_72995_K) {
/* 102 */       func_70106_y();
/*     */     } else {
/* 104 */       super.func_70071_h_();
/* 105 */       if (!this.field_70170_p.field_72995_K) {
/* 106 */         onUpdateCollided();
/*     */       }
/*     */       
/* 109 */       this.field_70165_t += this.field_70159_w;
/* 110 */       this.field_70163_u += this.field_70181_x;
/* 111 */       this.field_70161_v += this.field_70179_y;
/* 112 */       if (this.field_70170_p.field_72995_K) {
/* 113 */         double x = (this.field_70165_t - this.field_70169_q) / 2.0D;
/* 114 */         double y = (this.field_70163_u - this.field_70167_r) / 2.0D;
/* 115 */         double z = (this.field_70161_v - this.field_70166_s) / 2.0D;
/*     */         
/* 117 */         for (int i = 0; i < 2; i++) {
/* 118 */           MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + x * i, this.field_70167_r + y * i, this.field_70166_s + z * i);
/* 119 */           prm.size = 6.0F + this.field_70146_Z.nextFloat();
/* 120 */           if (this.size < 5.0F) {
/* 121 */             prm.a = (float)(prm.a * 0.75D);
/* 122 */             if (this.field_70146_Z.nextInt(2) == 0);
/*     */           } 
/*     */ 
/*     */           
/* 126 */           if (this.fuseCount > 0) {
/* 127 */             prm.a = this.field_70146_Z.nextFloat() * 0.1F + 0.85F;
/* 128 */             prm.b = this.field_70146_Z.nextFloat() * 0.1F + 0.5F;
/* 129 */             prm.g = prm.b + 0.05F;
/* 130 */             prm.r = prm.b + 0.1F;
/*     */           } 
/*     */           
/* 133 */           MCH_ParticlesUtil.spawnParticle(prm);
/*     */         } 
/*     */       } 
/*     */       
/* 137 */       this.field_70181_x += this.gravity;
/* 138 */       this.field_70159_w *= this.airResistance;
/* 139 */       this.field_70179_y *= this.airResistance;
/* 140 */       if (func_70090_H() && !this.field_70170_p.field_72995_K) {
/* 141 */         func_70106_y();
/*     */       }
/*     */       
/* 144 */       if (this.field_70122_E && !this.field_70170_p.field_72995_K) {
/* 145 */         func_70106_y();
/*     */       }
/*     */       
/* 148 */       func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onUpdateCollided() {
/* 153 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 154 */     Vec3d vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 155 */     RayTraceResult mop = W_WorldFunc.clip(this.field_70170_p, vec3, vec31);
/* 156 */     vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 157 */     vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 158 */     if (mop != null) {
/* 159 */       vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, mop.field_72307_f.field_72450_a, mop.field_72307_f.field_72448_b, mop.field_72307_f.field_72449_c);
/* 160 */       onImpact(mop);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onImpact(RayTraceResult par1MovingObjectPosition) {
/* 165 */     if (!this.field_70170_p.field_72995_K) {
/* 166 */       func_70106_y();
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 171 */     par1NBTTagCompound.func_74782_a("direction", (NBTBase)func_70087_a(new double[] { this.field_70159_w, this.field_70181_x, this.field_70179_y }));
/*     */   }
/*     */   
/*     */   public void func_70037_a(@NotNull NBTTagCompound par1NBTTagCompound) {
/* 175 */     func_70106_y();
/*     */   }
/*     */   
/*     */   public boolean func_70067_L() {
/* 179 */     return true;
/*     */   }
/*     */   
/*     */   public float func_70111_Y() {
/* 183 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/* 188 */     return false;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 193 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\flare\MCH_EntityFlare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */