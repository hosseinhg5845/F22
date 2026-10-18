/*    */ package com.norwood.mcheli.gui;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_GuiButton;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_GuiOnOffButton extends W_GuiButton {
/*    */   private final String dispOnOffString;
/*    */   private boolean statOnOff;
/*    */   
/*    */   public MCH_GuiOnOffButton(int par1, int par2, int par3, int par4, int par5, String par6Str) {
/* 12 */     super(par1, par2, par3, par4, par5, "");
/* 13 */     this.dispOnOffString = par6Str;
/* 14 */     setOnOff(false);
/*    */   }
/*    */   
/*    */   public boolean getOnOff() {
/* 18 */     return this.statOnOff;
/*    */   }
/*    */   
/*    */   public void setOnOff(boolean b) {
/* 22 */     this.statOnOff = b;
/* 23 */     this.displayString = this.dispOnOffString + (getOnOff() ? "ON" : "OFF");
/*    */   }
/*    */   
/*    */   public void switchOnOff() {
/* 27 */     setOnOff(!getOnOff());
/*    */   }
/*    */   
/*    */   public boolean mousePressed(@NotNull Minecraft mc, int x, int y) {
/* 31 */     if (super.mousePressed(mc, x, y)) {
/* 32 */       switchOnOff();
/* 33 */       return true;
/*    */     } 
/* 35 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gui\MCH_GuiOnOffButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */