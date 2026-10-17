/*    */ package com.norwood.mcheli.gui;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ConfigGuiContainer extends Container {
/*    */   public final EntityPlayer player;
/*    */   
/*    */   public MCH_ConfigGuiContainer(EntityPlayer player) {
/* 12 */     this.player = player;
/*    */   }
/*    */   
/*    */   public void func_75142_b() {
/* 16 */     super.func_75142_b();
/*    */   }
/*    */   
/*    */   public boolean func_75145_c(@NotNull EntityPlayer player) {
/* 20 */     return true;
/*    */   }
/*    */   @NotNull
/*    */   public ItemStack func_82846_b(@NotNull EntityPlayer par1EntityPlayer, int par2) {
/* 24 */     return ItemStack.field_190927_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gui\MCH_ConfigGuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */