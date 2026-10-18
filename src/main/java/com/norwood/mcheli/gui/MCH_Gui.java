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
/*  28 */     this.mc = minecraft;
/*  29 */     this.smoothCamPartialTicks = 0.0F;
/*  30 */     this.zLevel = -110.0F;
/*     */   }
/*     */   
/*     */   public void initGui() {
/*  34 */     super.initGui();
/*     */   }
/*     */   
/*     */   public boolean doesGuiPauseGame() {
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
/*     */   public void drawScreen(int par1, int par2, float partialTicks) {
/*  49 */     this.smoothCamPartialTicks = partialTicks;
/*  50 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
/*  51 */     scaleFactor = w_ScaledResolution.getScaleFactor();
/*  52 */     if (!this.mc.gameSettings.hideGUI) {
/*  53 */       this.width = this.mc.displayWidth / scaleFactor;
/*  54 */       this.height = this.mc.displayHeight / scaleFactor;
/*  55 */       this.centerX = this.width / 2;
/*  56 */       this.centerY = this.height / 2;
/*  57 */       GL11.glPushMatrix();
/*  58 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  59 */       if (this.mc.player != null) {
/*  60 */         drawGui((EntityPlayer)this.mc.player, (this.mc.gameSettings.thirdPersonView != 0));
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
/*  75 */     Tessellator tessellator = Tessellator.getInstance();
/*  76 */     BufferBuilder builder = tessellator.getBuffer();
/*  77 */     builder.begin(7, DefaultVertexFormats.POSITION_TEX);
/*  78 */     builder.pos(-width / 2.0D, height / 2.0D, this.zLevel).tex(uLeft * f, (vTop + vHeight) * f).endVertex();
/*  79 */     builder.pos(width / 2.0D, height / 2.0D, this.zLevel).tex((uLeft + uWidth) * f, (vTop + vHeight) * f).endVertex();
/*  80 */     builder.pos(width / 2.0D, -height / 2.0D, this.zLevel).tex((uLeft + uWidth) * f, vTop * f).endVertex();
/*  81 */     builder.pos(-width / 2.0D, -height / 2.0D, this.zLevel).tex(uLeft * f, vTop * f).endVertex();
/*  82 */     tessellator.draw();
/*  83 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedRect(double left, double top, double width, double height, double uLeft, double vTop, double uWidth, double vHeight, double textureWidth, double textureHeight) {
/*  89 */     float fx = (float)(1.0D / textureWidth);
/*  90 */     float fy = (float)(1.0D / textureHeight);
/*  91 */     Tessellator tessellator = Tessellator.getInstance();
/*  92 */     BufferBuilder builder = tessellator.getBuffer();
/*  93 */     builder.begin(7, DefaultVertexFormats.POSITION_TEX);
/*  94 */     builder.pos(left, top + height, this.zLevel).tex(uLeft * fx, (vTop + vHeight) * fy).endVertex();
/*  95 */     builder.pos(left + width, top + height, this.zLevel).tex((uLeft + uWidth) * fx, (vTop + vHeight) * fy).endVertex();
/*  96 */     builder.pos(left + width, top, this.zLevel).tex((uLeft + uWidth) * fx, vTop * fy).endVertex();
/*  97 */     builder.pos(left, top, this.zLevel).tex(uLeft * fx, vTop * fy).endVertex();
/*  98 */     tessellator.draw();
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
/* 113 */     drawString(this.mc.fontRenderer, s, x, y, color);
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
/* 127 */         drawTexturedModalRect(x + interval * i, y, 16 * (c - 48), 0, 16, 16);
/*     */       }
/*     */       
/* 130 */       if (c == '-') {
/* 131 */         drawTexturedModalRect(x + interval * i, y, 160, 0, 16, 16);
/*     */       }
/*     */     } 
/*     */     
/* 135 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 136 */     GL11.glDisable(3042);
/*     */   }
/*     */   
/*     */   public void drawCenteredString(String s, int x, int y, int color) {
/* 140 */     drawCenteredString(this.mc.fontRenderer, s, x, y, color);
/*     */   }
/*     */   
/*     */   public void drawLine(double[] line, int color, int mode) {
/* 144 */     GL11.glPushMatrix();
/* 145 */     GL11.glEnable(3042);
/* 146 */     GL11.glDisable(3553);
/* 147 */     GL11.glBlendFunc(770, 771);
/* 148 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/* 149 */     Tessellator tessellator = Tessellator.getInstance();
/* 150 */     BufferBuilder builder = tessellator.getBuffer();
/* 151 */     builder.begin(mode, DefaultVertexFormats.POSITION);
/*     */     
/* 153 */     for (int i = 0; i < line.length; i += 2) {
/* 154 */       builder.pos(line[i], line[i + 1], this.zLevel).endVertex();
/*     */     }
/*     */     
/* 157 */     tessellator.draw();
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
/* 172 */     Tessellator tessellator = Tessellator.getInstance();
/* 173 */     BufferBuilder builder = tessellator.getBuffer();
/* 174 */     builder.begin(0, DefaultVertexFormats.POSITION);
/*     */     
/* 176 */     for (int i = 0; i < points.length; i += 2) {
/* 177 */       builder.pos(points[i], points[i + 1], 0.0D).endVertex();
/*     */     }
/*     */     
/* 180 */     tessellator.draw();
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
/* 196 */     Tessellator tessellator = Tessellator.getInstance();
/* 197 */     BufferBuilder builder = tessellator.getBuffer();
/* 198 */     builder.begin(0, DefaultVertexFormats.POSITION);
/*     */     
/* 200 */     for (int i = 0; i < points.size(); i += 2) {
/* 201 */       builder.pos(((Double)points.get(i)).doubleValue(), ((Double)points.get(i + 1)).doubleValue(), 0.0D).endVertex();
/*     */     }
/*     */     
/* 204 */     tessellator.draw();
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