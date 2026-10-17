/*     */ package com.norwood.mcheli.particles;
/*     */ 
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ public class MCH_EntityParticleSmoke
/*     */   extends MCH_EntityParticleBase
/*     */ {
/*  22 */   private static final VertexFormat VERTEX_FORMAT = (new VertexFormat())
/*  23 */     .func_181721_a(DefaultVertexFormats.field_181713_m)
/*  24 */     .func_181721_a(DefaultVertexFormats.field_181715_o)
/*  25 */     .func_181721_a(DefaultVertexFormats.field_181714_n)
/*  26 */     .func_181721_a(DefaultVertexFormats.field_181716_p)
/*  27 */     .func_181721_a(DefaultVertexFormats.field_181717_q)
/*  28 */     .func_181721_a(DefaultVertexFormats.field_181718_r);
/*     */   
/*     */   public MCH_EntityParticleSmoke(World par1World, double x, double y, double z, double mx, double my, double mz) {
/*  31 */     super(par1World, x, y, z, mx, my, mz);
/*  32 */     this.field_70552_h = this.field_70553_i = this.field_70551_j = this.field_187136_p.nextFloat() * 0.3F + 0.7F;
/*  33 */     setParticleScale(this.field_187136_p.nextFloat() * 0.5F + 5.0F);
/*  34 */     setParticleMaxAge((int)(16.0D / (this.field_187136_p.nextFloat() * 0.8D + 0.2D)) + 2);
/*     */   }
/*     */   
/*     */   public void func_189213_a() {
/*  38 */     this.field_187123_c = this.field_187126_f;
/*  39 */     this.field_187124_d = this.field_187127_g;
/*  40 */     this.field_187125_e = this.field_187128_h;
/*  41 */     if (this.field_70546_d < this.field_70547_e) {
/*  42 */       func_70536_a((int)(8.0D * this.field_70546_d / this.field_70547_e));
/*  43 */       this.field_70546_d++;
/*  44 */       if (this.diffusible && this.field_70544_f < this.particleMaxScale) {
/*  45 */         this.field_70544_f += 0.8F;
/*     */       }
/*     */       
/*  48 */       if (this.toWhite) {
/*  49 */         float mn = getMinColor();
/*  50 */         float mx = getMaxColor();
/*  51 */         float dist = mx - mn;
/*  52 */         if (dist > 0.2D) {
/*  53 */           this.field_70552_h += (mx - this.field_70552_h) * 0.016F;
/*  54 */           this.field_70553_i += (mx - this.field_70553_i) * 0.016F;
/*  55 */           this.field_70551_j += (mx - this.field_70551_j) * 0.016F;
/*     */         } 
/*     */       } 
/*     */       
/*  59 */       effectWind();
/*  60 */       if (this.field_70546_d / this.field_70547_e > this.moutionYUpAge) {
/*  61 */         this.field_187130_j += 0.02D;
/*     */       } else {
/*  63 */         this.field_187130_j += this.gravity;
/*     */       } 
/*     */       
/*  66 */       func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
/*  67 */       if (this.diffusible) {
/*  68 */         this.field_187129_i *= 0.96D;
/*  69 */         this.field_187131_k *= 0.96D;
/*  70 */         this.field_187130_j *= 0.96D;
/*     */       } else {
/*  72 */         this.field_187129_i *= 0.9D;
/*  73 */         this.field_187131_k *= 0.9D;
/*     */       } 
/*     */     } else {
/*  76 */       func_187112_i();
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getMinColor() {
/*  81 */     return min(min(this.field_70551_j, this.field_70553_i), this.field_70552_h);
/*     */   }
/*     */   
/*     */   public float getMaxColor() {
/*  85 */     return max(max(this.field_70551_j, this.field_70553_i), this.field_70552_h);
/*     */   }
/*     */   
/*     */   public float min(float a, float b) {
/*  89 */     return Math.min(a, b);
/*     */   }
/*     */   
/*     */   public float max(float a, float b) {
/*  93 */     return Math.max(a, b);
/*     */   }
/*     */   
/*     */   public void effectWind() {
/*  97 */     if (this.isEffectedWind) {
/*  98 */       List<MCH_EntityAircraft> list = this.field_187122_b.func_72872_a(MCH_EntityAircraft.class, getCollisionBoundingBox().func_72314_b(15.0D, 15.0D, 15.0D));
/*     */       
/* 100 */       for (MCH_EntityAircraft ac : list) {
/* 101 */         if (ac.getThrottle() > 0.10000000149011612D) {
/* 102 */           float dist = getDistance(ac);
/* 103 */           double vel = (23.0D - dist) * 0.009999999776482582D * ac.getThrottle();
/* 104 */           double mx = ac.field_70165_t - this.field_187126_f;
/* 105 */           double mz = ac.field_70161_v - this.field_187128_h;
/* 106 */           this.field_187129_i -= mx * vel;
/* 107 */           this.field_187131_k -= mz * vel;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70537_b() {
/* 115 */     return 3;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_189214_a(float p_70070_1_) {
/* 120 */     double y = this.field_187127_g;
/* 121 */     this.field_187127_g += 3000.0D;
/* 122 */     int i = super.func_189214_a(p_70070_1_);
/* 123 */     this.field_187127_g = y;
/* 124 */     return i;
/*     */   }
/*     */   
/*     */   public void func_180434_a(BufferBuilder buffer, @NotNull Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
/* 128 */     W_McClient.MOD_bindTexture("textures/particles/smoke.png");
/* 129 */     GlStateManager.func_179147_l();
/* 130 */     int srcBlend = GlStateManager.func_187397_v(3041);
/* 131 */     int dstBlend = GlStateManager.func_187397_v(3040);
/* 132 */     GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 133 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 134 */     GlStateManager.func_179140_f();
/* 135 */     GlStateManager.func_179129_p();
/* 136 */     float f6 = this.field_94054_b / 8.0F;
/* 137 */     float f7 = f6 + 0.125F;
/* 138 */     float f8 = 0.0F;
/* 139 */     float f9 = 1.0F;
/* 140 */     float f10 = 0.1F * this.field_70544_f;
/* 141 */     float f11 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * par2 - field_70556_an);
/* 142 */     float f12 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * par2 - field_70554_ao);
/* 143 */     float f13 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * par2 - field_70555_ap);
/* 144 */     int i = func_189214_a(par2);
/* 145 */     int j = i >> 16 & 0xFFFF;
/* 146 */     int k = i & 0xFFFF;
/* 147 */     buffer.func_181668_a(7, VERTEX_FORMAT);
/* 148 */     buffer.func_181662_b((f11 - par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 - par5 * f10 - par7 * f10))
/* 149 */       .func_187315_a(f7, f9)
/* 150 */       .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
/* 151 */       .func_187314_a(j, k)
/* 152 */       .func_181663_c(0.0F, 1.0F, 0.0F)
/* 153 */       .func_181675_d();
/* 154 */     buffer.func_181662_b((f11 - par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 - par5 * f10 + par7 * f10))
/* 155 */       .func_187315_a(f7, f8)
/* 156 */       .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
/* 157 */       .func_187314_a(j, k)
/* 158 */       .func_181663_c(0.0F, 1.0F, 0.0F)
/* 159 */       .func_181675_d();
/* 160 */     buffer.func_181662_b((f11 + par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 + par5 * f10 + par7 * f10))
/* 161 */       .func_187315_a(f6, f8)
/* 162 */       .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
/* 163 */       .func_187314_a(j, k)
/* 164 */       .func_181663_c(0.0F, 1.0F, 0.0F)
/* 165 */       .func_181675_d();
/* 166 */     buffer.func_181662_b((f11 + par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 + par5 * f10 - par7 * f10))
/* 167 */       .func_187315_a(f6, f9)
/* 168 */       .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
/* 169 */       .func_187314_a(j, k)
/* 170 */       .func_181663_c(0.0F, 1.0F, 0.0F)
/* 171 */       .func_181675_d();
/* 172 */     Tessellator.func_178181_a().func_78381_a();
/* 173 */     GlStateManager.func_179089_o();
/* 174 */     GlStateManager.func_179145_e();
/* 175 */     GlStateManager.func_179112_b(srcBlend, dstBlend);
/* 176 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   private float getDistance(MCH_EntityAircraft entity) {
/* 180 */     float f = (float)(this.field_187126_f - entity.field_70165_t);
/* 181 */     float f1 = (float)(this.field_187127_g - entity.field_70163_u);
/* 182 */     float f2 = (float)(this.field_187128_h - entity.field_70161_v);
/* 183 */     return MathHelper.func_76129_c(f * f + f1 * f1 + f2 * f2);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\particles\MCH_EntityParticleSmoke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */