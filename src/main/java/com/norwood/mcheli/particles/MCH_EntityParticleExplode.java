/*     */ package com.norwood.mcheli.particles;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityParticleExplode
/*     */   extends MCH_EntityParticleBase
/*     */ {
/*  19 */   private static final VertexFormat VERTEX_FORMAT = (new VertexFormat())
/*  20 */     .func_181721_a(DefaultVertexFormats.field_181713_m)
/*  21 */     .func_181721_a(DefaultVertexFormats.field_181715_o)
/*  22 */     .func_181721_a(DefaultVertexFormats.field_181714_n)
/*  23 */     .func_181721_a(DefaultVertexFormats.field_181716_p)
/*  24 */     .func_181721_a(DefaultVertexFormats.field_181717_q)
/*  25 */     .func_181721_a(DefaultVertexFormats.field_181718_r);
/*  26 */   private static final ResourceLocation texture = new ResourceLocation("textures/entity/explosion.png");
/*     */   private int nowCount;
/*     */   private final int endCount;
/*  29 */   private final TextureManager theRenderEngine = (Minecraft.func_71410_x()).field_71446_o;
/*     */   private final float size;
/*     */   
/*     */   public MCH_EntityParticleExplode(World w, double x, double y, double z, double size, double age, double mz) {
/*  33 */     super(w, x, y, z, 0.0D, 0.0D, 0.0D);
/*  34 */     this.endCount = 1 + (int)age;
/*  35 */     this.size = (float)size;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180434_a(@NotNull BufferBuilder buffer, @NotNull Entity entityIn, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
/*  41 */     int i = (int)((this.nowCount + p_70539_2_) * 15.0F / this.endCount);
/*  42 */     if (i <= 15) {
/*  43 */       GlStateManager.func_179147_l();
/*  44 */       int srcBlend = GlStateManager.func_187397_v(3041);
/*  45 */       int dstBlend = GlStateManager.func_187397_v(3040);
/*  46 */       GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/*  47 */       GlStateManager.func_179129_p();
/*  48 */       this.theRenderEngine.func_110577_a(texture);
/*  49 */       float f6 = (i % 4) / 4.0F;
/*  50 */       float f7 = f6 + 0.24975F;
/*  51 */       float f8 = i / 4.0F / 4.0F;
/*  52 */       float f9 = f8 + 0.24975F;
/*  53 */       float f10 = 2.0F * this.size;
/*  54 */       float f11 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * p_70539_2_ - field_70556_an);
/*  55 */       float f12 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * p_70539_2_ - field_70554_ao);
/*  56 */       float f13 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * p_70539_2_ - field_70555_ap);
/*  57 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*  58 */       RenderHelper.func_74518_a();
/*  59 */       int j = 15728880;
/*  60 */       int k = j >> 16 & 0xFFFF;
/*  61 */       int l = j & 0xFFFF;
/*  62 */       buffer.func_181668_a(7, VERTEX_FORMAT);
/*  63 */       buffer.func_181662_b((f11 - p_70539_3_ * f10 - p_70539_6_ * f10), (f12 - p_70539_4_ * f10), (f13 - p_70539_5_ * f10 - p_70539_7_ * f10))
/*  64 */         .func_187315_a(f7, f9)
/*  65 */         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
/*  66 */         .func_187314_a(k, l)
/*  67 */         .func_181663_c(0.0F, 1.0F, 0.0F)
/*  68 */         .func_181675_d();
/*  69 */       buffer.func_181662_b((f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (f12 + p_70539_4_ * f10), (f13 - p_70539_5_ * f10 + p_70539_7_ * f10))
/*  70 */         .func_187315_a(f7, f8)
/*  71 */         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
/*  72 */         .func_187314_a(k, l)
/*  73 */         .func_181663_c(0.0F, 1.0F, 0.0F)
/*  74 */         .func_181675_d();
/*  75 */       buffer.func_181662_b((f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (f12 + p_70539_4_ * f10), (f13 + p_70539_5_ * f10 + p_70539_7_ * f10))
/*  76 */         .func_187315_a(f6, f8)
/*  77 */         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
/*  78 */         .func_187314_a(k, l)
/*  79 */         .func_181663_c(0.0F, 1.0F, 0.0F)
/*  80 */         .func_181675_d();
/*  81 */       buffer.func_181662_b((f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (f12 - p_70539_4_ * f10), (f13 + p_70539_5_ * f10 - p_70539_7_ * f10))
/*  82 */         .func_187315_a(f6, f9)
/*  83 */         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
/*  84 */         .func_187314_a(k, l)
/*  85 */         .func_181663_c(0.0F, 1.0F, 0.0F)
/*  86 */         .func_181675_d();
/*  87 */       Tessellator.func_178181_a().func_78381_a();
/*  88 */       GlStateManager.func_179136_a(0.0F, 0.0F);
/*  89 */       GlStateManager.func_179145_e();
/*  90 */       GlStateManager.func_179089_o();
/*  91 */       GlStateManager.func_179112_b(srcBlend, dstBlend);
/*  92 */       GlStateManager.func_179084_k();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int func_189214_a(float p_70070_1_) {
/*  97 */     return 15728880;
/*     */   }
/*     */   
/*     */   public void func_189213_a() {
/* 101 */     this.field_187123_c = this.field_187126_f;
/* 102 */     this.field_187124_d = this.field_187127_g;
/* 103 */     this.field_187125_e = this.field_187128_h;
/* 104 */     this.nowCount++;
/* 105 */     if (this.nowCount == this.endCount) {
/* 106 */       func_187112_i();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70537_b() {
/* 112 */     return 3;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\particles\MCH_EntityParticleExplode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */