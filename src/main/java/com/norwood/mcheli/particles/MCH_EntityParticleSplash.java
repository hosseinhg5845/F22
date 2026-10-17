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
/*  16 */     this.field_70552_h = this.field_70553_i = this.field_70551_j = this.field_187136_p.nextFloat() * 0.3F + 0.7F;
/*  17 */     setParticleScale(this.field_187136_p.nextFloat() * 0.5F + 5.0F);
/*  18 */     setParticleMaxAge((int)(80.0D / (this.field_187136_p.nextFloat() * 0.8D + 0.2D)) + 2);
/*     */   }
/*     */   
/*     */   public void func_189213_a() {
/*  22 */     this.field_187123_c = this.field_187126_f;
/*  23 */     this.field_187124_d = this.field_187127_g;
/*  24 */     this.field_187125_e = this.field_187128_h;
/*  25 */     if (this.field_70546_d < this.field_70547_e) {
/*  26 */       func_70536_a((int)(8.0D * this.field_70546_d / this.field_70547_e));
/*  27 */       this.field_70546_d++;
/*     */     } else {
/*  29 */       func_187112_i();
/*     */     } 
/*     */     
/*  32 */     this.field_187130_j -= 0.05999999865889549D;
/*  33 */     Block block = W_WorldFunc.getBlock(this.field_187122_b, (int)(this.field_187126_f + 0.5D), (int)(this.field_187127_g + 0.5D), (int)(this.field_187128_h + 0.5D));
/*  34 */     boolean beforeInWater = W_Block.func_149680_a(block, W_Block.getWater());
/*  35 */     func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
/*  36 */     block = W_WorldFunc.getBlock(this.field_187122_b, (int)(this.field_187126_f + 0.5D), (int)(this.field_187127_g + 0.5D), (int)(this.field_187128_h + 0.5D));
/*  37 */     boolean nowInWater = W_Block.func_149680_a(block, W_Block.getWater());
/*  38 */     if (this.field_187130_j < -0.6D && !beforeInWater && nowInWater) {
/*  39 */       double p = -this.field_187130_j * 10.0D;
/*     */       
/*  41 */       for (int i = 0; i < p; i++) {
/*  42 */         this.field_187122_b
/*  43 */           .func_175688_a(EnumParticleTypes.WATER_SPLASH, this.field_187126_f + 0.5D + (this.field_187136_p
/*     */             
/*  45 */             .nextDouble() - 0.5D) * 2.0D, this.field_187127_g + this.field_187136_p
/*  46 */             .nextDouble(), this.field_187128_h + 0.5D + (this.field_187136_p
/*  47 */             .nextDouble() - 0.5D) * 2.0D, (this.field_187136_p
/*  48 */             .nextDouble() - 0.5D) * 2.0D, 4.0D, (this.field_187136_p
/*     */             
/*  50 */             .nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */         
/*  52 */         this.field_187122_b
/*  53 */           .func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_187126_f + 0.5D + (this.field_187136_p
/*     */             
/*  55 */             .nextDouble() - 0.5D) * 2.0D, this.field_187127_g - this.field_187136_p
/*  56 */             .nextDouble(), this.field_187128_h + 0.5D + (this.field_187136_p
/*  57 */             .nextDouble() - 0.5D) * 2.0D, (this.field_187136_p
/*  58 */             .nextDouble() - 0.5D) * 2.0D, -0.5D, (this.field_187136_p
/*     */             
/*  60 */             .nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     
/*  63 */     } else if (this.field_187132_l) {
/*  64 */       func_187112_i();
/*     */     } 
/*     */     
/*  67 */     this.field_187129_i *= 0.9D;
/*  68 */     this.field_187131_k *= 0.9D;
/*     */   }
/*     */   
/*     */   public void func_180434_a(BufferBuilder buffer, @NotNull Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
/*  72 */     W_McClient.MOD_bindTexture("textures/particles/smoke.png");
/*  73 */     float f6 = this.field_94054_b / 8.0F;
/*  74 */     float f7 = f6 + 0.125F;
/*  75 */     float f8 = 0.0F;
/*  76 */     float f9 = 1.0F;
/*  77 */     float f10 = 0.1F * this.field_70544_f;
/*  78 */     float f11 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * par2 - field_70556_an);
/*  79 */     float f12 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * par2 - field_70554_ao);
/*  80 */     float f13 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * par2 - field_70555_ap);
/*  81 */     float f14 = 1.0F;
/*  82 */     int i = func_189214_a(par2);
/*  83 */     int j = i >> 16 & 0xFFFF;
/*  84 */     int k = i & 0xFFFF;
/*  85 */     buffer.func_181662_b((f11 - par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 - par5 * f10 - par7 * f10))
/*  86 */       .func_187315_a(f7, f9)
/*  87 */       .func_181666_a(this.field_70552_h * f14, this.field_70553_i * f14, this.field_70551_j * f14, this.field_82339_as)
/*  88 */       .func_187314_a(j, k)
/*  89 */       .func_181675_d();
/*  90 */     buffer.func_181662_b((f11 - par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 - par5 * f10 + par7 * f10))
/*  91 */       .func_187315_a(f7, f8)
/*  92 */       .func_181666_a(this.field_70552_h * f14, this.field_70553_i * f14, this.field_70551_j * f14, this.field_82339_as)
/*  93 */       .func_187314_a(j, k)
/*  94 */       .func_181675_d();
/*  95 */     buffer.func_181662_b((f11 + par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 + par5 * f10 + par7 * f10))
/*  96 */       .func_187315_a(f6, f8)
/*  97 */       .func_181666_a(this.field_70552_h * f14, this.field_70553_i * f14, this.field_70551_j * f14, this.field_82339_as)
/*  98 */       .func_187314_a(j, k)
/*  99 */       .func_181675_d();
/* 100 */     buffer.func_181662_b((f11 + par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 + par5 * f10 - par7 * f10))
/* 101 */       .func_187315_a(f6, f9)
/* 102 */       .func_181666_a(this.field_70552_h * f14, this.field_70553_i * f14, this.field_70551_j * f14, this.field_82339_as)
/* 103 */       .func_187314_a(j, k)
/* 104 */       .func_181675_d();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\particles\MCH_EntityParticleSplash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */