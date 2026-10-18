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
/*  27 */     setSize(1.0F, 1.0F);
/*  28 */     this.prevRotationYaw = this.rotationYaw;
/*  29 */     this.prevRotationPitch = this.rotationPitch;
/*  30 */     this.size = 6.0F;
/*  31 */     this.fuseCount = 0;
/*     */   }
/*     */   
/*     */   public MCH_EntityFlare(World par1World, double pX, double pY, double pZ, double mX, double mY, double mZ, float size, int fuseCount) {
/*  35 */     this(par1World);
/*  36 */     setLocationAndAngles(pX, pY, pZ, 0.0F, 0.0F);
/*  37 */     this.motionX = mX;
/*  38 */     this.motionY = mY;
/*  39 */     this.motionZ = mZ;
/*  40 */     this.size = size;
/*  41 */     this.fuseCount = fuseCount;
/*     */   }
/*     */   
/*     */   public boolean isEntityInvulnerable(@NotNull DamageSource source) {
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean isInRangeToRenderDist(double par1) {
/*  51 */     double d1 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*  52 */     d1 *= 64.0D;
/*  53 */     return (par1 < d1 * d1);
/*     */   }
/*     */   
/*     */   public void setDead() {
/*  57 */     super.setDead();
/*  58 */     if (this.fuseCount > 0 && this.world.isRemote) {
/*  59 */       this.fuseCount = 0;
/*     */       
/*  61 */       for (int i = 0; i < 20; i++) {
/*  62 */         double x = (this.rand.nextDouble() - 0.5D) * 10.0D;
/*  63 */         double y = (this.rand.nextDouble() - 0.5D) * 10.0D;
/*  64 */         double z = (this.rand.nextDouble() - 0.5D) * 10.0D;
/*  65 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", this.posX + x, this.posY + y, this.posZ + z);
/*  66 */         prm.age = 200 + this.rand.nextInt(100);
/*  67 */         prm.size = (20 + this.rand.nextInt(25));
/*  68 */         prm.motionX = (this.rand.nextDouble() - 0.5D) * 0.45D;
/*  69 */         prm.motionY = (this.rand.nextDouble() - 0.5D) * 0.01D;
/*  70 */         prm.motionZ = (this.rand.nextDouble() - 0.5D) * 0.45D;
/*  71 */         prm.a = this.rand.nextFloat() * 0.1F + 0.85F;
/*  72 */         prm.b = this.rand.nextFloat() * 0.2F + 0.5F;
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
/*     */   public void onUpdate() {
/*  97 */     if (this.fuseCount > 0 && this.ticksExisted >= this.fuseCount) {
/*  98 */       setDead();
/*  99 */     } else if (!this.world.isRemote && !this.world.isBlockLoaded(new BlockPos(this.posX, this.posY, this.posZ))) {
/* 100 */       setDead();
/* 101 */     } else if (this.ticksExisted > 300 && !this.world.isRemote) {
/* 102 */       setDead();
/*     */     } else {
/* 104 */       super.onUpdate();
/* 105 */       if (!this.world.isRemote) {
/* 106 */         onUpdateCollided();
/*     */       }
/*     */       
/* 109 */       this.posX += this.motionX;
/* 110 */       this.posY += this.motionY;
/* 111 */       this.posZ += this.motionZ;
/* 112 */       if (this.world.isRemote) {
/* 113 */         double x = (this.posX - this.prevPosX) / 2.0D;
/* 114 */         double y = (this.posY - this.prevPosY) / 2.0D;
/* 115 */         double z = (this.posZ - this.prevPosZ) / 2.0D;
/*     */         
/* 117 */         for (int i = 0; i < 2; i++) {
/* 118 */           MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", this.prevPosX + x * i, this.prevPosY + y * i, this.prevPosZ + z * i);
/* 119 */           prm.size = 6.0F + this.rand.nextFloat();
/* 120 */           if (this.size < 5.0F) {
/* 121 */             prm.a = (float)(prm.a * 0.75D);
/* 122 */             if (this.rand.nextInt(2) == 0);
/*     */           } 
/*     */ 
/*     */           
/* 126 */           if (this.fuseCount > 0) {
/* 127 */             prm.a = this.rand.nextFloat() * 0.1F + 0.85F;
/* 128 */             prm.b = this.rand.nextFloat() * 0.1F + 0.5F;
/* 129 */             prm.g = prm.b + 0.05F;
/* 130 */             prm.r = prm.b + 0.1F;
/*     */           } 
/*     */           
/* 133 */           MCH_ParticlesUtil.spawnParticle(prm);
/*     */         } 
/*     */       } 
/*     */       
/* 137 */       this.motionY += this.gravity;
/* 138 */       this.motionX *= this.airResistance;
/* 139 */       this.motionZ *= this.airResistance;
/* 140 */       if (isInWater() && !this.world.isRemote) {
/* 141 */         setDead();
/*     */       }
/*     */       
/* 144 */       if (this.onGround && !this.world.isRemote) {
/* 145 */         setDead();
/*     */       }
/*     */       
/* 148 */       setPosition(this.posX, this.posY, this.posZ);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onUpdateCollided() {
/* 153 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
/* 154 */     Vec3d vec31 = W_WorldFunc.getWorldVec3(this.world, this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 155 */     RayTraceResult mop = W_WorldFunc.clip(this.world, vec3, vec31);
/* 156 */     vec3 = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
/* 157 */     vec31 = W_WorldFunc.getWorldVec3(this.world, this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 158 */     if (mop != null) {
/* 159 */       vec31 = W_WorldFunc.getWorldVec3(this.world, mop.hitVec.x, mop.hitVec.y, mop.hitVec.z);
/* 160 */       onImpact(mop);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onImpact(RayTraceResult par1MovingObjectPosition) {
/* 165 */     if (!this.world.isRemote) {
/* 166 */       setDead();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
/* 171 */     par1NBTTagCompound.setTag("direction", (NBTBase)newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
/*     */   }
/*     */   
/*     */   public void readEntityFromNBT(@NotNull NBTTagCompound par1NBTTagCompound) {
/* 175 */     setDead();
/*     */   }
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 179 */     return true;
/*     */   }
/*     */   
/*     */   public float getCollisionBorderSize() {
/* 183 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
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