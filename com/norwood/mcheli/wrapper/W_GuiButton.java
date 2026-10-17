/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class W_GuiButton
/*    */   extends GuiButton {
/* 11 */   public List<String> hoverStringList = null;
/*    */   
/*    */   public W_GuiButton(int par1, int par2, int par3, int par4, int par5, String par6Str) {
/* 14 */     super(par1, par2, par3, par4, par5, par6Str);
/*    */   }
/*    */   
/*    */   public static void setVisible(GuiButton button, boolean b) {
/* 18 */     button.field_146125_m = b;
/*    */   }
/*    */   
/*    */   public void addHoverString(String s) {
/* 22 */     if (this.hoverStringList == null) {
/* 23 */       this.hoverStringList = new ArrayList<>();
/*    */     }
/*    */     
/* 26 */     this.hoverStringList.add(s);
/*    */   }
/*    */   
/*    */   public boolean isVisible() {
/* 30 */     return this.field_146125_m;
/*    */   }
/*    */   
/*    */   public void setVisible(boolean b) {
/* 34 */     this.field_146125_m = b;
/*    */   }
/*    */   
/*    */   public void enableBlend() {
/* 38 */     GL11.glEnable(3042);
/* 39 */     OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 40 */     GL11.glBlendFunc(770, 771);
/*    */   }
/*    */   
/*    */   public boolean isOnMouseOver() {
/* 44 */     return this.field_146123_n;
/*    */   }
/*    */   
/*    */   public void setOnMouseOver(boolean b) {
/* 48 */     this.field_146123_n = b;
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 52 */     return this.field_146120_f;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 56 */     return this.field_146121_g;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_GuiButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */