/*    */ package com.norwood.mcheli.gui;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_ConfigPrm;
/*    */ import com.norwood.mcheli.MCH_KeyName;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ 
/*    */ public class MCH_GuiListItemKeyBind extends MCH_GuiListItem {
/*    */   public final int defaultKeycode;
/*    */   public final String displayString;
/*    */   public final GuiButton button;
/*    */   public final GuiButton buttonReset;
/*    */   public int keycode;
/*    */   public final MCH_ConfigPrm config;
/*    */   public GuiButton lastPushButton;
/*    */   
/*    */   public MCH_GuiListItemKeyBind(int id, int idReset, int posX, String dispStr, MCH_ConfigPrm prm) {
/* 18 */     this.displayString = dispStr;
/* 19 */     this.defaultKeycode = prm.prmIntDefault;
/* 20 */     this.button = new GuiButton(id, posX + 160, 0, 70, 20, "");
/* 21 */     this.buttonReset = new GuiButton(idReset, posX + 240, 0, 40, 20, "Reset");
/* 22 */     this.config = prm;
/* 23 */     this.lastPushButton = null;
/* 24 */     setKeycode(prm.prmInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseReleased(int x, int y) {
/* 29 */     this.button.func_146118_a(x, y);
/* 30 */     this.buttonReset.func_146118_a(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mousePressed(Minecraft mc, int x, int y) {
/* 35 */     if (this.button.func_146116_c(mc, x, y)) {
/* 36 */       this.lastPushButton = this.button;
/* 37 */       return true;
/* 38 */     }  if (this.buttonReset.func_146116_c(mc, x, y)) {
/* 39 */       this.lastPushButton = this.buttonReset;
/* 40 */       return true;
/*    */     } 
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(Minecraft mc, int mouseX, int mouseY, int posX, int posY, float partialTicks) {
/* 48 */     int y = 6;
/* 49 */     this.button.func_73731_b(mc.field_71466_p, this.displayString, posX + 10, posY + y, -1);
/* 50 */     this.button.field_146129_i = posY;
/* 51 */     this.button.func_191745_a(mc, mouseX, mouseY, partialTicks);
/* 52 */     this.buttonReset.field_146124_l = (this.keycode != this.defaultKeycode);
/* 53 */     this.buttonReset.field_146129_i = posY;
/* 54 */     this.buttonReset.func_191745_a(mc, mouseX, mouseY, partialTicks);
/*    */   }
/*    */   
/*    */   public void applyKeycode() {
/* 58 */     this.config.setPrm(this.keycode);
/*    */   }
/*    */   
/*    */   public void resetKeycode() {
/* 62 */     setKeycode(this.defaultKeycode);
/*    */   }
/*    */   
/*    */   public void setKeycode(int k) {
/* 66 */     if (k != 0 && !MCH_KeyName.getDescOrName(k).isEmpty()) {
/* 67 */       this.keycode = k;
/* 68 */       this.button.field_146126_j = MCH_KeyName.getDescOrName(k);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gui\MCH_GuiListItemKeyBind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */