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
/*  20 */     .addElement(DefaultVertexFormats.POSITION_3F)
/*  21 */     .addElement(DefaultVertexFormats.TEX_2F)
/*  22 */     .addElement(DefaultVertexFormats.COLOR_4UB)
/*  23 */     .addElement(DefaultVertexFormats.TEX_2S)
/*  24 */     .addElement(DefaultVertexFormats.NORMAL_3B)
/*  25 */     .addElement(DefaultVertexFormats.PADDING_1B);
/*  26 */   private static final ResourceLocation texture = new ResourceLocation("textures/entity/explosion.png");
/*     */   private int nowCount;
/*     */   private final int endCount;
/*  29 */   private final TextureManager theRenderEngine = (Minecraft.getMinecraft()).renderEngine;
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
/*     */   public void renderParticle(@NotNull BufferBuilder buffer, @NotNull Entity entityIn, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
/*  41 */     int i = (int)((this.nowCount + p_70539_2_) * 15.0F / this.endCount);
/*  42 */     if (i <= 15) {
/*  43 */       GlStateManager.enableBlend();
/*  44 */       int srcBlend = GlStateManager.glGetInteger(3041);
/*  45 */       int dstBlend = GlStateManager.glGetInteger(3040);
/*  46 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/*  47 */       GlStateManager.disableCull();
/*  48 */       this.theRenderEngine.bindTexture(texture);
/*  49 */       float f6 = (i % 4) / 4.0F;
/*  50 */       float f7 = f6 + 0.24975F;
/*  51 */       float f8 = i / 4.0F / 4.0F;
/*  52 */       float f9 = f8 + 0.24975F;
/*  53 */       float f10 = 2.0F * this.size;
/*  54 */       float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * p_70539_2_ - interpPosX);
/*  55 */       float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * p_70539_2_ - interpPosY);
/*  56 */       float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * p_70539_2_ - interpPosZ);
/*  57 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  58 */       RenderHelper.disableStandardItemLighting();
/*  59 */       int j = 15728880;
/*  60 */       int k = j >> 16 & 0xFFFF;
/*  61 */       int l = j & 0xFFFF;
/*  62 */       buffer.begin(7, VERTEX_FORMAT);
/*  63 */       buffer.pos((f11 - p_70539_3_ * f10 - p_70539_6_ * f10), (f12 - p_70539_4_ * f10), (f13 - p_70539_5_ * f10 - p_70539_7_ * f10))
/*  64 */         .tex(f7, f9)
/*  65 */         .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
/*  66 */         .lightmap(k, l)
/*  67 */         .normal(0.0F, 1.0F, 0.0F)
/*  68 */         .endVertex();
/*  69 */       buffer.pos((f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (f12 + p_70539_4_ * f10), (f13 - p_70539_5_ * f10 + p_70539_7_ * f10))
/*  70 */         .tex(f7, f8)
/*  71 */         .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
/*  72 */         .lightmap(k, l)
/*  73 */         .normal(0.0F, 1.0F, 0.0F)
/*  74 */         .endVertex();
/*  75 */       buffer.pos((f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (f12 + p_70539_4_ * f10), (f13 + p_70539_5_ * f10 + p_70539_7_ * f10))
/*  76 */         .tex(f6, f8)
/*  77 */         .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
/*  78 */         .lightmap(k, l)
/*  79 */         .normal(0.0F, 1.0F, 0.0F)
/*  80 */         .endVertex();
/*  81 */       buffer.pos((f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (f12 - p_70539_4_ * f10), (f13 + p_70539_5_ * f10 - p_70539_7_ * f10))
/*  82 */         .tex(f6, f9)
/*  83 */         .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
/*  84 */         .lightmap(k, l)
/*  85 */         .normal(0.0F, 1.0F, 0.0F)
/*  86 */         .endVertex();
/*  87 */       Tessellator.getInstance().draw();
/*  88 */       GlStateManager.doPolygonOffset(0.0F, 0.0F);
/*  89 */       GlStateManager.enableLighting();
/*  90 */       GlStateManager.enableCull();
/*  91 */       GlStateManager.blendFunc(srcBlend, dstBlend);
/*  92 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getBrightnessForRender(float p_70070_1_) {
/*  97 */     return 15728880;
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 101 */     this.prevPosX = this.posX;
/* 102 */     this.prevPosY = this.posY;
/* 103 */     this.prevPosZ = this.posZ;
/* 104 */     this.nowCount++;
/* 105 */     if (this.nowCount == this.endCount) {
/* 106 */       setExpired();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/* 112 */     return 3;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\particles\MCH_EntityParticleExplode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */