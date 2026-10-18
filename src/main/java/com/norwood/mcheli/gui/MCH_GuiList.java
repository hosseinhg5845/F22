/*    */ package com.norwood.mcheli.gui;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_GuiButton;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_GuiList
/*    */   extends W_GuiButton {
/*    */   public final int maxRowNum;
/*    */   public final List<MCH_GuiListItem> listItems;
/*    */   public final MCH_GuiSliderVertical scrollBar;
/*    */   public MCH_GuiListItem lastPushItem;
/*    */   
/*    */   public MCH_GuiList(int id, int maxRow, int posX, int posY, int w, int h, String name) {
/* 17 */     super(id, posX, posY, w, h, "");
/* 18 */     this.maxRowNum = (maxRow > 0) ? maxRow : 1;
/* 19 */     this.listItems = new ArrayList<>();
/* 20 */     this.scrollBar = new MCH_GuiSliderVertical(0, posX + w - 20, posY, 20, h, name, 0.0F, 0.0F, 0.0F, 1.0F);
/* 21 */     this.lastPushItem = null;
/*    */   }
/*    */   
/*    */   public void drawButton(@NotNull Minecraft mc, int x, int y, float partialTicks) {
/* 25 */     if (isVisible()) {
/* 26 */       drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -2143272896);
/* 27 */       this.scrollBar.drawButton(mc, x, y, partialTicks);
/*    */       
/* 29 */       for (int i = 0; i < this.maxRowNum && i + getStartRow() < this.listItems.size(); i++) {
/* 30 */         MCH_GuiListItem item = this.listItems.get(i + getStartRow());
/* 31 */         item.draw(mc, x, y, this.x, this.y + 5 + 20 * i, partialTicks);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void addItem(MCH_GuiListItem item) {
/* 37 */     this.listItems.add(item);
/* 38 */     int listNum = this.listItems.size();
/* 39 */     this.scrollBar.valueMax = (listNum > this.maxRowNum) ? (listNum - this.maxRowNum) : 0.0F;
/*    */   }
/*    */   
/*    */   public MCH_GuiListItem getItem(int i) {
/* 43 */     return (i < getItemNum()) ? this.listItems.get(i) : null;
/*    */   }
/*    */   
/*    */   public int getItemNum() {
/* 47 */     return this.listItems.size();
/*    */   }
/*    */   
/*    */   public void scrollUp(float a) {
/* 51 */     if (isVisible()) {
/* 52 */       this.scrollBar.scrollUp(a);
/*    */     }
/*    */   }
/*    */   
/*    */   public void scrollDown(float a) {
/* 57 */     if (isVisible()) {
/* 58 */       this.scrollBar.scrollDown(a);
/*    */     }
/*    */   }
/*    */   
/*    */   public int getStartRow() {
/* 63 */     int startRow = (int)this.scrollBar.getSliderValue();
/* 64 */     return Math.max(startRow, 0);
/*    */   }
/*    */   
/*    */   protected void mouseDragged(@NotNull Minecraft mc, int x, int y) {
/* 68 */     if (isVisible()) {
/* 69 */       this.scrollBar.mouseDragged(mc, x, y);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean mousePressed(@NotNull Minecraft mc, int x, int y) {
/* 74 */     boolean b = false;
/* 75 */     if (isVisible()) {
/* 76 */       b |= this.scrollBar.mousePressed(mc, x, y);
/*    */       
/* 78 */       for (int i = 0; i < this.maxRowNum && i + getStartRow() < this.listItems.size(); i++) {
/* 79 */         MCH_GuiListItem item = this.listItems.get(i + getStartRow());
/* 80 */         if (item.mousePressed(mc, x, y)) {
/* 81 */           this.lastPushItem = item;
/* 82 */           b = true;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 87 */     return b;
/*    */   }
/*    */   
/*    */   public void mouseReleased(int x, int y) {
/* 91 */     if (isVisible()) {
/* 92 */       this.scrollBar.mouseReleased(x, y);
/*    */       
/* 94 */       for (MCH_GuiListItem item : this.listItems)
/* 95 */         item.mouseReleased(x, y); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gui\MCH_GuiList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */