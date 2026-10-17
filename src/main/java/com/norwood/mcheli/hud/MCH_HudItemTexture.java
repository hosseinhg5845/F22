/*    */ package com.norwood.mcheli.hud;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_TextureUtil;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class MCH_HudItemTexture
/*    */   extends MCH_HudItem
/*    */ {
/*    */   private final String name;
/*    */   private final String left;
/*    */   private final String top;
/*    */   private final String width;
/*    */   private final String height;
/*    */   private final String uLeft;
/*    */   private final String vTop;
/*    */   private final String uWidth;
/*    */   private final String vHeight;
/*    */   private final String rot;
/*    */   private int textureWidth;
/*    */   private int textureHeight;
/*    */   
/*    */   public MCH_HudItemTexture(int fileLine, String name, String left, String top, String width, String height, String uLeft, String vTop, String uWidth, String vHeight, String rot) {
/* 23 */     super(fileLine);
/* 24 */     this.name = name;
/* 25 */     this.left = toFormula(left);
/* 26 */     this.top = toFormula(top);
/* 27 */     this.width = toFormula(width);
/* 28 */     this.height = toFormula(height);
/* 29 */     this.uLeft = toFormula(uLeft);
/* 30 */     this.vTop = toFormula(vTop);
/* 31 */     this.uWidth = toFormula(uWidth);
/* 32 */     this.vHeight = toFormula(vHeight);
/* 33 */     this.rot = toFormula(rot);
/* 34 */     this.textureWidth = this.textureHeight = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute() {
/* 39 */     GL11.glEnable(3042);
/* 40 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 41 */     if (this.textureWidth == 0 || this.textureHeight == 0) {
/* 42 */       int w = 0;
/* 43 */       int h = 0;
/* 44 */       W_TextureUtil.TextureParam prm = W_TextureUtil.getTextureInfo("mcheli", "textures/gui/" + this.name + ".png");
/* 45 */       w = prm.width;
/* 46 */       h = prm.height;
/*    */       
/* 48 */       this.textureWidth = (w > 0) ? w : 256;
/* 49 */       this.textureHeight = (h > 0) ? h : 256;
/*    */     } 
/*    */     
/* 52 */     drawTexture(this.name, centerX + 
/*    */         
/* 54 */         calc(this.left), centerY + 
/* 55 */         calc(this.top), 
/* 56 */         calc(this.width), 
/* 57 */         calc(this.height), 
/* 58 */         calc(this.uLeft), 
/* 59 */         calc(this.vTop), 
/* 60 */         calc(this.uWidth), 
/* 61 */         calc(this.vHeight), 
/* 62 */         (float)calc(this.rot), this.textureWidth, this.textureHeight);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */