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
/* 19 */     if (!item.func_190926_b()) {
/* 20 */       item.func_77973_b();
/* 21 */       FontRenderer font = item.func_77973_b().getFontRenderer(item);
/* 22 */       if (font == null) {
/* 23 */         font = this.field_146289_q;
/*    */       }
/*    */       
/* 26 */       GL11.glEnable(2929);
/* 27 */       GL11.glEnable(2896);
/* 28 */       this.field_146296_j.func_180450_b(item, x, y);
/* 29 */       this.field_146296_j.func_180453_a(font, item, x, y, null);
/* 30 */       this.field_73735_i = 0.0F;
/* 31 */       this.field_146296_j.field_77023_b = 0.0F;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void drawIngredient(Ingredient ingredient, int x, int y) {
/* 36 */     if (ingredient != Ingredient.field_193370_a) {
/* 37 */       ItemStack[] itemstacks = ingredient.func_193365_a();
/* 38 */       int index = MathHelper.func_76141_d(this.time / 20.0F) % itemstacks.length;
/* 39 */       drawItemStack(itemstacks[index], x, y);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void drawString(String s, int x, int y, int color) {
/* 44 */     func_73731_b(this.field_146289_q, s, x, y, color);
/*    */   }
/*    */   
/*    */   public void drawCenteredString(String s, int x, int y, int color) {
/* 48 */     func_73732_a(this.field_146289_q, s, x, y, color);
/*    */   }
/*    */   
/*    */   public int getStringWidth(String s) {
/* 52 */     return this.field_146289_q.func_78256_a(s);
/*    */   }
/*    */   
/*    */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/* 56 */     this.time += partialTicks;
/* 57 */     super.func_73863_a(mouseX, mouseY, partialTicks);
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