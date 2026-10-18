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
/*  23 */     .addElement(DefaultVertexFormats.POSITION_3F)
/*  24 */     .addElement(DefaultVertexFormats.TEX_2F)
/*  25 */     .addElement(DefaultVertexFormats.COLOR_4UB)
/*  26 */     .addElement(DefaultVertexFormats.TEX_2S)
/*  27 */     .addElement(DefaultVertexFormats.NORMAL_3B)
/*  28 */     .addElement(DefaultVertexFormats.PADDING_1B);
/*     */   
/*     */   public MCH_EntityParticleSmoke(World par1World, double x, double y, double z, double mx, double my, double mz) {
/*  31 */     super(par1World, x, y, z, mx, my, mz);
/*  32 */     this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3F + 0.7F;
/*  33 */     setParticleScale(this.rand.nextFloat() * 0.5F + 5.0F);
/*  34 */     setParticleMaxAge((int)(16.0D / (this.rand.nextFloat() * 0.8D + 0.2D)) + 2);
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/*  38 */     this.prevPosX = this.posX;
/*  39 */     this.prevPosY = this.posY;
/*  40 */     this.prevPosZ = this.posZ;
/*  41 */     if (this.particleAge < this.particleMaxAge) {
/*  42 */       setParticleTextureIndex((int)(8.0D * this.particleAge / this.particleMaxAge));
/*  43 */       this.particleAge++;
/*  44 */       if (this.diffusible && this.particleScale < this.particleMaxScale) {
/*  45 */         this.particleScale += 0.8F;
/*     */       }
/*     */       
/*  48 */       if (this.toWhite) {
/*  49 */         float mn = getMinColor();
/*  50 */         float mx = getMaxColor();
/*  51 */         float dist = mx - mn;
/*  52 */         if (dist > 0.2D) {
/*  53 */           this.particleRed += (mx - this.particleRed) * 0.016F;
/*  54 */           this.particleGreen += (mx - this.particleGreen) * 0.016F;
/*  55 */           this.particleBlue += (mx - this.particleBlue) * 0.016F;
/*     */         } 
/*     */       } 
/*     */       
/*  59 */       effectWind();
/*  60 */       if (this.particleAge / this.particleMaxAge > this.moutionYUpAge) {
/*  61 */         this.motionY += 0.02D;
/*     */       } else {
/*  63 */         this.motionY += this.gravity;
/*     */       } 
/*     */       
/*  66 */       move(this.motionX, this.motionY, this.motionZ);
/*  67 */       if (this.diffusible) {
/*  68 */         this.motionX *= 0.96D;
/*  69 */         this.motionZ *= 0.96D;
/*  70 */         this.motionY *= 0.96D;
/*     */       } else {
/*  72 */         this.motionX *= 0.9D;
/*  73 */         this.motionZ *= 0.9D;
/*     */       } 
/*     */     } else {
/*  76 */       setExpired();
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getMinColor() {
/*  81 */     return min(min(this.particleBlue, this.particleGreen), this.particleRed);
/*     */   }
/*     */   
/*     */   public float getMaxColor() {
/*  85 */     return max(max(this.particleBlue, this.particleGreen), this.particleRed);
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
/*  98 */       List<MCH_EntityAircraft> list = this.world.getEntitiesWithinAABB(MCH_EntityAircraft.class, getCollisionBoundingBox().grow(15.0D, 15.0D, 15.0D));
/*     */       
/* 100 */       for (MCH_EntityAircraft ac : list) {
/* 101 */         if (ac.getThrottle() > 0.10000000149011612D) {
/* 102 */           float dist = getDistance(ac);
/* 103 */           double vel = (23.0D - dist) * 0.009999999776482582D * ac.getThrottle();
/* 104 */           double mx = ac.posX - this.posX;
/* 105 */           double mz = ac.posZ - this.posZ;
/* 106 */           this.motionX -= mx * vel;
/* 107 */           this.motionZ -= mz * vel;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/* 115 */     return 3;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getBrightnessForRender(float p_70070_1_) {
/* 120 */     double y = this.posY;
/* 121 */     this.posY += 3000.0D;
/* 122 */     int i = super.getBrightnessForRender(p_70070_1_);
/* 123 */     this.posY = y;
/* 124 */     return i;
/*     */   }
/*     */   
/*     */   public void renderParticle(BufferBuilder buffer, @NotNull Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
/* 128 */     W_McClient.MOD_bindTexture("textures/particles/smoke.png");
/* 129 */     GlStateManager.enableBlend();
/* 130 */     int srcBlend = GlStateManager.glGetInteger(3041);
/* 131 */     int dstBlend = GlStateManager.glGetInteger(3040);
/* 132 */     GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 133 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 134 */     GlStateManager.disableLighting();
/* 135 */     GlStateManager.disableCull();
/* 136 */     float f6 = this.particleTextureIndexX / 8.0F;
/* 137 */     float f7 = f6 + 0.125F;
/* 138 */     float f8 = 0.0F;
/* 139 */     float f9 = 1.0F;
/* 140 */     float f10 = 0.1F * this.particleScale;
/* 141 */     float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
/* 142 */     float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
/* 143 */     float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
/* 144 */     int i = getBrightnessForRender(par2);
/* 145 */     int j = i >> 16 & 0xFFFF;
/* 146 */     int k = i & 0xFFFF;
/* 147 */     buffer.begin(7, VERTEX_FORMAT);
/* 148 */     buffer.pos((f11 - par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 - par5 * f10 - par7 * f10))
/* 149 */       .tex(f7, f9)
/* 150 */       .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
/* 151 */       .lightmap(j, k)
/* 152 */       .normal(0.0F, 1.0F, 0.0F)
/* 153 */       .endVertex();
/* 154 */     buffer.pos((f11 - par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 - par5 * f10 + par7 * f10))
/* 155 */       .tex(f7, f8)
/* 156 */       .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
/* 157 */       .lightmap(j, k)
/* 158 */       .normal(0.0F, 1.0F, 0.0F)
/* 159 */       .endVertex();
/* 160 */     buffer.pos((f11 + par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 + par5 * f10 + par7 * f10))
/* 161 */       .tex(f6, f8)
/* 162 */       .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
/* 163 */       .lightmap(j, k)
/* 164 */       .normal(0.0F, 1.0F, 0.0F)
/* 165 */       .endVertex();
/* 166 */     buffer.pos((f11 + par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 + par5 * f10 - par7 * f10))
/* 167 */       .tex(f6, f9)
/* 168 */       .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
/* 169 */       .lightmap(j, k)
/* 170 */       .normal(0.0F, 1.0F, 0.0F)
/* 171 */       .endVertex();
/* 172 */     Tessellator.getInstance().draw();
/* 173 */     GlStateManager.enableCull();
/* 174 */     GlStateManager.enableLighting();
/* 175 */     GlStateManager.blendFunc(srcBlend, dstBlend);
/* 176 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   private float getDistance(MCH_EntityAircraft entity) {
/* 180 */     float f = (float)(this.posX - entity.posX);
/* 181 */     float f1 = (float)(this.posY - entity.posY);
/* 182 */     float f2 = (float)(this.posZ - entity.posZ);
/* 183 */     return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\particles\MCH_EntityParticleSmoke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */