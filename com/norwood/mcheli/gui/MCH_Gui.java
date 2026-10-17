/*     */ package com.norwood.mcheli.gui;
/*     */ 
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.W_ScaledResolution;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public abstract class MCH_Gui
/*     */   extends GuiScreen
/*     */ {
/*     */   public static int scaleFactor;
/*  22 */   protected int centerX = 0;
/*  23 */   protected int centerY = 0;
/*  24 */   protected final Random rand = new Random();
/*     */   protected float smoothCamPartialTicks;
/*     */   
/*     */   public MCH_Gui(Minecraft minecraft) {
/*  28 */     this.field_146297_k = minecraft;
/*  29 */     this.smoothCamPartialTicks = 0.0F;
/*  30 */     this.field_73735_i = -110.0F;
/*     */   }
/*     */   
/*     */   public void func_73866_w_() {
/*  34 */     super.func_73866_w_();
/*     */   }
/*     */   
/*     */   public boolean func_73868_f() {
/*  38 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {}
/*     */   
/*     */   public abstract boolean isDrawGui(EntityPlayer paramEntityPlayer);
/*     */   
/*     */   public abstract void drawGui(EntityPlayer paramEntityPlayer, boolean paramBoolean);
/*     */   
/*     */   public void func_73863_a(int par1, int par2, float partialTicks) {
/*  49 */     this.smoothCamPartialTicks = partialTicks;
/*  50 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/*  51 */     scaleFactor = w_ScaledResolution.func_78325_e();
/*  52 */     if (!this.field_146297_k.field_71474_y.field_74319_N) {
/*  53 */       this.field_146294_l = this.field_146297_k.field_71443_c / scaleFactor;
/*  54 */       this.field_146295_m = this.field_146297_k.field_71440_d / scaleFactor;
/*  55 */       this.centerX = this.field_146294_l / 2;
/*  56 */       this.centerY = this.field_146295_m / 2;
/*  57 */       GL11.glPushMatrix();
/*  58 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  59 */       if (this.field_146297_k.field_71439_g != null) {
/*  60 */         drawGui((EntityPlayer)this.field_146297_k.field_71439_g, (this.field_146297_k.field_71474_y.field_74320_O != 0));
/*     */       }
/*     */       
/*  63 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  64 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRectRotate(double left, double top, double width, double height, double uLeft, double vTop, double uWidth, double vHeight, float rot) {
/*  71 */     GL11.glPushMatrix();
/*  72 */     GL11.glTranslated(left + width / 2.0D, top + height / 2.0D, 0.0D);
/*  73 */     GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
/*  74 */     float f = 0.00390625F;
/*  75 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  76 */     BufferBuilder builder = tessellator.func_178180_c();
/*  77 */     builder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*  78 */     builder.func_181662_b(-width / 2.0D, height / 2.0D, this.field_73735_i).func_187315_a(uLeft * f, (vTop + vHeight) * f).func_181675_d();
/*  79 */     builder.func_181662_b(width / 2.0D, height / 2.0D, this.field_73735_i).func_187315_a((uLeft + uWidth) * f, (vTop + vHeight) * f).func_181675_d();
/*  80 */     builder.func_181662_b(width / 2.0D, -height / 2.0D, this.field_73735_i).func_187315_a((uLeft + uWidth) * f, vTop * f).func_181675_d();
/*  81 */     builder.func_181662_b(-width / 2.0D, -height / 2.0D, this.field_73735_i).func_187315_a(uLeft * f, vTop * f).func_181675_d();
/*  82 */     tessellator.func_78381_a();
/*  83 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedRect(double left, double top, double width, double height, double uLeft, double vTop, double uWidth, double vHeight, double textureWidth, double textureHeight) {
/*  89 */     float fx = (float)(1.0D / textureWidth);
/*  90 */     float fy = (float)(1.0D / textureHeight);
/*  91 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  92 */     BufferBuilder builder = tessellator.func_178180_c();
/*  93 */     builder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*  94 */     builder.func_181662_b(left, top + height, this.field_73735_i).func_187315_a(uLeft * fx, (vTop + vHeight) * fy).func_181675_d();
/*  95 */     builder.func_181662_b(left + width, top + height, this.field_73735_i).func_187315_a((uLeft + uWidth) * fx, (vTop + vHeight) * fy).func_181675_d();
/*  96 */     builder.func_181662_b(left + width, top, this.field_73735_i).func_187315_a((uLeft + uWidth) * fx, vTop * fy).func_181675_d();
/*  97 */     builder.func_181662_b(left, top, this.field_73735_i).func_187315_a(uLeft * fx, vTop * fy).func_181675_d();
/*  98 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public void drawLineStipple(double[] line, int color, int factor, int pattern) {
/* 102 */     GL11.glEnable(2852);
/* 103 */     GL11.glLineStipple(factor, (short)pattern);
/* 104 */     drawLine(line, color);
/* 105 */     GL11.glDisable(2852);
/*     */   }
/*     */   
/*     */   public void drawLine(double[] line, int color) {
/* 109 */     drawLine(line, color, 1);
/*     */   }
/*     */   
/*     */   public void drawString(String s, int x, int y, int color) {
/* 113 */     func_73731_b(this.field_146297_k.field_71466_p, s, x, y, color);
/*     */   }
/*     */   
/*     */   public void drawDigit(String s, int x, int y, int interval, int color) {
/* 117 */     GL11.glEnable(3042);
/* 118 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
/* 119 */     int srcBlend = GL11.glGetInteger(3041);
/* 120 */     int dstBlend = GL11.glGetInteger(3040);
/* 121 */     GL11.glBlendFunc(770, 771);
/* 122 */     W_McClient.MOD_bindTexture("textures/gui/digit.png");
/*     */     
/* 124 */     for (int i = 0; i < s.length(); i++) {
/* 125 */       char c = s.charAt(i);
/* 126 */       if (c >= '0' && c <= '9') {
/* 127 */         func_73729_b(x + interval * i, y, 16 * (c - 48), 0, 16, 16);
/*     */       }
/*     */       
/* 130 */       if (c == '-') {
/* 131 */         func_73729_b(x + interval * i, y, 160, 0, 16, 16);
/*     */       }
/*     */     } 
/*     */     
/* 135 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 136 */     GL11.glDisable(3042);
/*     */   }
/*     */   
/*     */   public void drawCenteredString(String s, int x, int y, int color) {
/* 140 */     func_73732_a(this.field_146297_k.field_71466_p, s, x, y, color);
/*     */   }
/*     */   
/*     */   public void drawLine(double[] line, int color, int mode) {
/* 144 */     GL11.glPushMatrix();
/* 145 */     GL11.glEnable(3042);
/* 146 */     GL11.glDisable(3553);
/* 147 */     GL11.glBlendFunc(770, 771);
/* 148 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/* 149 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 150 */     BufferBuilder builder = tessellator.func_178180_c();
/* 151 */     builder.func_181668_a(mode, DefaultVertexFormats.field_181705_e);
/*     */     
/* 153 */     for (int i = 0; i < line.length; i += 2) {
/* 154 */       builder.func_181662_b(line[i], line[i + 1], this.field_73735_i).func_181675_d();
/*     */     }
/*     */     
/* 157 */     tessellator.func_78381_a();
/* 158 */     GL11.glEnable(3553);
/* 159 */     GL11.glDisable(3042);
/* 160 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 161 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void drawPoints(double[] points, int color, int pointWidth) {
/* 165 */     int prevWidth = GL11.glGetInteger(2833);
/* 166 */     GL11.glPushMatrix();
/* 167 */     GL11.glEnable(3042);
/* 168 */     GL11.glDisable(3553);
/* 169 */     GL11.glBlendFunc(770, 771);
/* 170 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/* 171 */     GL11.glPointSize(pointWidth);
/* 172 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 173 */     BufferBuilder builder = tessellator.func_178180_c();
/* 174 */     builder.func_181668_a(0, DefaultVertexFormats.field_181705_e);
/*     */     
/* 176 */     for (int i = 0; i < points.length; i += 2) {
/* 177 */       builder.func_181662_b(points[i], points[i + 1], 0.0D).func_181675_d();
/*     */     }
/*     */     
/* 180 */     tessellator.func_78381_a();
/* 181 */     GL11.glEnable(3553);
/* 182 */     GL11.glDisable(3042);
/* 183 */     GL11.glPopMatrix();
/* 184 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 185 */     GL11.glPointSize(prevWidth);
/*     */   }
/*     */   
/*     */   public void drawPoints(ArrayList<Double> points, int color, int pointWidth) {
/* 189 */     int prevWidth = GL11.glGetInteger(2833);
/* 190 */     GL11.glPushMatrix();
/* 191 */     GL11.glEnable(3042);
/* 192 */     GL11.glDisable(3553);
/* 193 */     GL11.glBlendFunc(770, 771);
/* 194 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/* 195 */     GL11.glPointSize(pointWidth);
/* 196 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 197 */     BufferBuilder builder = tessellator.func_178180_c();
/* 198 */     builder.func_181668_a(0, DefaultVertexFormats.field_181705_e);
/*     */     
/* 200 */     for (int i = 0; i < points.size(); i += 2) {
/* 201 */       builder.func_181662_b(((Double)points.get(i)).doubleValue(), ((Double)points.get(i + 1)).doubleValue(), 0.0D).func_181675_d();
/*     */     }
/*     */     
/* 204 */     tessellator.func_78381_a();
/* 205 */     GL11.glEnable(3553);
/* 206 */     GL11.glDisable(3042);
/* 207 */     GL11.glPopMatrix();
/* 208 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 209 */     GL11.glPointSize(prevWidth);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gui\MCH_Gui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */