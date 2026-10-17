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
/*    */   public boolean func_77569_a(InventoryCrafting inv, @NotNull World var2) {
/* 14 */     int jcnt = 0;
/* 15 */     int ccnt = 0;
/*    */     
/* 17 */     for (int i = 0; i < inv.func_70302_i_(); i++) {
/* 18 */       ItemStack is = inv.func_70301_a(i);
/* 19 */       if (!is.func_190926_b()) {
/* 20 */         if (is.func_77973_b() instanceof com.norwood.mcheli.aircraft.MCH_ItemFuel) {
/* 21 */           if (is.func_77960_j() == 0) {
/* 22 */             return false;
/*    */           }
/*    */           
/* 25 */           if (++jcnt > 1) {
/* 26 */             return false;
/*    */           }
/*    */         } else {
/* 29 */           if (!(is.func_77973_b() instanceof net.minecraft.item.ItemCoal) || is.func_190916_E() <= 0) {
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
/*    */   public ItemStack func_77572_b(InventoryCrafting inv) {
/* 42 */     ItemStack output = new ItemStack((Item)MCH_MOD.itemFuel);
/*    */     
/* 44 */     for (int i = 0; i < inv.func_70302_i_(); i++) {
/* 45 */       ItemStack is = inv.func_70301_a(i);
/* 46 */       if (!is.func_190926_b() && is.func_77973_b() instanceof com.norwood.mcheli.aircraft.MCH_ItemFuel) {
/* 47 */         output.func_77964_b(is.func_77960_j());
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 52 */     for (int ix = 0; ix < inv.func_70302_i_(); ix++) {
/* 53 */       ItemStack is = inv.func_70301_a(ix);
/* 54 */       if (!is.func_190926_b() && is.func_77973_b() instanceof net.minecraft.item.ItemCoal) {
/* 55 */         int sp = 100;
/* 56 */         if (is.func_77960_j() == 1) {
/* 57 */           sp = 75;
/*    */         }
/*    */         
/* 60 */         if (output.func_77960_j() > sp) {
/* 61 */           output.func_77964_b(output.func_77960_j() - sp);
/*    */         } else {
/* 63 */           output.func_77964_b(0);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 68 */     return output;
/*    */   }
/*    */   
/*    */   public boolean func_194133_a(int width, int height) {
/* 72 */     return (width >= 3 && height >= 3);
/*    */   }
/*    */   @NotNull
/*    */   public ItemStack func_77571_b() {
/* 76 */     return ItemStack.field_190927_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_RecipeFuel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */