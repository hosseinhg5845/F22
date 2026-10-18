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
/*    */ public class MCH_RecipeFuel
/*    */   extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
/*    */   public boolean matches(InventoryCrafting inv, @NotNull World var2) {
/* 14 */     int jcnt = 0;
/* 15 */     int ccnt = 0;
/*    */     
/* 17 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/* 18 */       ItemStack is = inv.getStackInSlot(i);
/* 19 */       if (!is.isEmpty()) {
/* 20 */         if (is.getItem() instanceof com.norwood.mcheli.aircraft.MCH_ItemFuel) {
/* 21 */           if (is.getMetadata() == 0) {
/* 22 */             return false;
/*    */           }
/*    */           
/* 25 */           if (++jcnt > 1) {
/* 26 */             return false;
/*    */           }
/*    */         } else {
/* 29 */           if (!(is.getItem() instanceof net.minecraft.item.ItemCoal) || is.getCount() <= 0) {
/* 30 */             return false;
/*    */           }
/*    */           
/* 33 */           ccnt++;
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 38 */     return (jcnt == 1 && ccnt > 0);
/*    */   }
/*    */   @NotNull
/*    */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 42 */     ItemStack output = new ItemStack((Item)MCH_MOD.itemFuel);
/*    */     
/* 44 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/* 45 */       ItemStack is = inv.getStackInSlot(i);
/* 46 */       if (!is.isEmpty() && is.getItem() instanceof com.norwood.mcheli.aircraft.MCH_ItemFuel) {
/* 47 */         output.setItemDamage(is.getMetadata());
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 52 */     for (int ix = 0; ix < inv.getSizeInventory(); ix++) {
/* 53 */       ItemStack is = inv.getStackInSlot(ix);
/* 54 */       if (!is.isEmpty() && is.getItem() instanceof net.minecraft.item.ItemCoal) {
/* 55 */         int sp = 100;
/* 56 */         if (is.getMetadata() == 1) {
/* 57 */           sp = 75;
/*    */         }
/*    */         
/* 60 */         if (output.getMetadata() > sp) {
/* 61 */           output.setItemDamage(output.getMetadata() - sp);
/*    */         } else {
/* 63 */           output.setItemDamage(0);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 68 */     return output;
/*    */   }
/*    */   
/*    */   public boolean canFit(int width, int height) {
/* 72 */     return (width >= 3 && height >= 3);
/*    */   }
/*    */   @NotNull
/*    */   public ItemStack getRecipeOutput() {
/* 76 */     return ItemStack.EMPTY;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_RecipeFuel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */