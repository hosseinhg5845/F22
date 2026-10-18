/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_RecipeReloadRangeFinder
/*    */   extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
/*    */   public boolean matches(InventoryCrafting inv, @NotNull World var2) {
/* 14 */     int jcnt = 0;
/* 15 */     int ccnt = 0;
/*    */     
/* 17 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/* 18 */       ItemStack is = inv.getStackInSlot(i);
/* 19 */       if (!is.isEmpty()) {
/* 20 */         if (is.getItem() instanceof com.norwood.mcheli.tool.rangefinder.MCH_ItemRangeFinder) {
/* 21 */           if (is.getMetadata() == 0) {
/* 22 */             return false;
/*    */           }
/*    */           
/* 25 */           if (++jcnt > 1) {
/* 26 */             return false;
/*    */           }
/*    */         } else {
/* 29 */           if (!(is.getItem() instanceof net.minecraft.item.ItemRedstone) || is.getCount() <= 0) {
/* 30 */             return false;
/*    */           }
/*    */           
/* 33 */           if (++ccnt > 1) {
/* 34 */             return false;
/*    */           }
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 40 */     return (jcnt == 1 && ccnt > 0);
/*    */   }
/*    */   @NotNull
/*    */   public ItemStack getCraftingResult(@NotNull InventoryCrafting inv) {
/* 44 */     return new ItemStack((Item)MCH_MOD.itemRangeFinder);
/*    */   }
/*    */   
/*    */   public boolean canFit(int width, int height) {
/* 48 */     return (width >= 2 && height >= 2);
/*    */   }
/*    */   @NotNull
/*    */   public ItemStack getRecipeOutput() {
/* 52 */     return ItemStack.EMPTY;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_RecipeReloadRangeFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */