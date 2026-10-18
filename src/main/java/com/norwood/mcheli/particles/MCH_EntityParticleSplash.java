/*     */ package com.norwood.mcheli.particles;
/*     */ 
/*     */ import com.norwood.mcheli.wrapper.W_Block;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityParticleSplash extends MCH_EntityParticleBase {
/*     */   public MCH_EntityParticleSplash(World par1World, double x, double y, double z, double mx, double my, double mz) {
/*  15 */     super(par1World, x, y, z, mx, my, mz);
/*  16 */     this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3F + 0.7F;
/*  17 */     setParticleScale(this.rand.nextFloat() * 0.5F + 5.0F);
/*  18 */     setParticleMaxAge((int)(80.0D / (this.rand.nextFloat() * 0.8D + 0.2D)) + 2);
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/*  22 */     this.prevPosX = this.posX;
/*  23 */     this.prevPosY = this.posY;
/*  24 */     this.prevPosZ = this.posZ;
/*  25 */     if (this.particleAge < this.particleMaxAge) {
/*  26 */       setParticleTextureIndex((int)(8.0D * this.particleAge / this.particleMaxAge));
/*  27 */       this.particleAge++;
/*     */     } else {
/*  29 */       setExpired();
/*     */     } 
/*     */     
/*  32 */     this.motionY -= 0.05999999865889549D;
/*  33 */     Block block = W_WorldFunc.getBlock(this.world, (int)(this.posX + 0.5D), (int)(this.posY + 0.5D), (int)(this.posZ + 0.5D));
/*  34 */     boolean beforeInWater = W_Block.isEqualTo(block, W_Block.getWater());
/*  35 */     move(this.motionX, this.motionY, this.motionZ);
/*  36 */     block = W_WorldFunc.getBlock(this.world, (int)(this.posX + 0.5D), (int)(this.posY + 0.5D), (int)(this.posZ + 0.5D));
/*  37 */     boolean nowInWater = W_Block.isEqualTo(block, W_Block.getWater());
/*  38 */     if (this.motionY < -0.6D && !beforeInWater && nowInWater) {
/*  39 */       double p = -this.motionY * 10.0D;
/*     */       
/*  41 */       for (int i = 0; i < p; i++) {
/*  42 */         this.world
/*  43 */           .spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + 0.5D + (this.rand
/*     */             
/*  45 */             .nextDouble() - 0.5D) * 2.0D, this.posY + this.rand
/*  46 */             .nextDouble(), this.posZ + 0.5D + (this.rand
/*  47 */             .nextDouble() - 0.5D) * 2.0D, (this.rand
/*  48 */             .nextDouble() - 0.5D) * 2.0D, 4.0D, (this.rand
/*     */             
/*  50 */             .nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */         
/*  52 */         this.world
/*  53 */           .spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + 0.5D + (this.rand
/*     */             
/*  55 */             .nextDouble() - 0.5D) * 2.0D, this.posY - this.rand
/*  56 */             .nextDouble(), this.posZ + 0.5D + (this.rand
/*  57 */             .nextDouble() - 0.5D) * 2.0D, (this.rand
/*  58 */             .nextDouble() - 0.5D) * 2.0D, -0.5D, (this.rand
/*     */             
/*  60 */             .nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     
/*  63 */     } else if (this.onGround) {
/*  64 */       setExpired();
/*     */     } 
/*     */     
/*  67 */     this.motionX *= 0.9D;
/*  68 */     this.motionZ *= 0.9D;
/*     */   }
/*     */   
/*     */   public void renderParticle(BufferBuilder buffer, @NotNull Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
/*  72 */     W_McClient.MOD_bindTexture("textures/particles/smoke.png");
/*  73 */     float f6 = this.particleTextureIndexX / 8.0F;
/*  74 */     float f7 = f6 + 0.125F;
/*  75 */     float f8 = 0.0F;
/*  76 */     float f9 = 1.0F;
/*  77 */     float f10 = 0.1F * this.particleScale;
/*  78 */     float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
/*  79 */     float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
/*  80 */     float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
/*  81 */     float f14 = 1.0F;
/*  82 */     int i = getBrightnessForRender(par2);
/*  83 */     int j = i >> 16 & 0xFFFF;
/*  84 */     int k = i & 0xFFFF;
/*  85 */     buffer.pos((f11 - par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 - par5 * f10 - par7 * f10))
/*  86 */       .tex(f7, f9)
/*  87 */       .color(this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha)
/*  88 */       .lightmap(j, k)
/*  89 */       .endVertex();
/*  90 */     buffer.pos((f11 - par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 - par5 * f10 + par7 * f10))
/*  91 */       .tex(f7, f8)
/*  92 */       .color(this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha)
/*  93 */       .lightmap(j, k)
/*  94 */       .endVertex();
/*  95 */     buffer.pos((f11 + par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 + par5 * f10 + par7 * f10))
/*  96 */       .tex(f6, f8)
/*  97 */       .color(this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha)
/*  98 */       .lightmap(j, k)
/*  99 */       .endVertex();
/* 100 */     buffer.pos((f11 + par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 + par5 * f10 - par7 * f10))
/* 101 */       .tex(f6, f9)
/* 102 */       .color(this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha)
/* 103 */       .lightmap(j, k)
/* 104 */       .endVertex();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\particles\MCH_EntityParticleSplash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */