/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.Ingredient;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public abstract class W_GuiContainer extends GuiContainer {
/*    */   private float time;
/*    */   
/*    */   public W_GuiContainer(Container par1Container) {
/* 15 */     super(par1Container);
/*    */   }
/*    */   
/*    */   public void drawItemStack(ItemStack item, int x, int y) {
/* 19 */     if (!item.isEmpty()) {
/* 20 */       item.getItem();
/* 21 */       FontRenderer font = item.getItem().getFontRenderer(item);
/* 22 */       if (font == null) {
/* 23 */         font = this.fontRenderer;
/*    */       }
/*    */       
/* 26 */       GL11.glEnable(2929);
/* 27 */       GL11.glEnable(2896);
/* 28 */       this.itemRender.renderItemAndEffectIntoGUI(item, x, y);
/* 29 */       this.itemRender.renderItemOverlayIntoGUI(font, item, x, y, null);
/* 30 */       this.zLevel = 0.0F;
/* 31 */       this.itemRender.zLevel = 0.0F;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void drawIngredient(Ingredient ingredient, int x, int y) {
/* 36 */     if (ingredient != Ingredient.EMPTY) {
/* 37 */       ItemStack[] itemstacks = ingredient.getMatchingStacks();
/* 38 */       int index = MathHelper.floor(this.time / 20.0F) % itemstacks.length;
/* 39 */       drawItemStack(itemstacks[index], x, y);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void drawString(String s, int x, int y, int color) {
/* 44 */     drawString(this.fontRenderer, s, x, y, color);
/*    */   }
/*    */   
/*    */   public void drawCenteredString(String s, int x, int y, int color) {
/* 48 */     drawCenteredString(this.fontRenderer, s, x, y, color);
/*    */   }
/*    */   
/*    */   public int getStringWidth(String s) {
/* 52 */     return this.fontRenderer.getStringWidth(s);
/*    */   }
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 56 */     this.time += partialTicks;
/* 57 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */   
/*    */   public float getAnimationTime() {
/* 61 */     return this.time;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_GuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */