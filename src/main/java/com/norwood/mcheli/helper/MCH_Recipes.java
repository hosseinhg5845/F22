/*    */ package com.norwood.mcheli.helper;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import com.norwood.mcheli.MCH_ItemRecipe;
/*    */ import java.util.Set;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ import net.minecraft.item.crafting.Ingredient;
/*    */ import net.minecraft.item.crafting.ShapedRecipes;
/*    */ import net.minecraftforge.common.crafting.CraftingHelper;
/*    */ import net.minecraftforge.event.RegistryEvent;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(modid = "mcheli")
/*    */ public class MCH_Recipes
/*    */ {
/* 22 */   private static final Set<IRecipe> registryWrapper = Sets.newLinkedHashSet();
/*    */   
/*    */   @SubscribeEvent
/*    */   static void onRecipeRegisterEvent(RegistryEvent.Register<IRecipe> event) {
/* 26 */     MCH_ItemRecipe.registerItemRecipe(event.getRegistry());
/*    */     
/* 28 */     for (IRecipe recipe : registryWrapper) {
/* 29 */       event.getRegistry().register(recipe);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void register(String name, IRecipe recipe) {
/* 34 */     registryWrapper.add((IRecipe)recipe.setRegistryName(MCH_Utils.suffix(name)));
/*    */   }
/*    */   
/*    */   public static ShapedRecipes addShapedRecipe(String name, ItemStack output, Object... params) {
/* 38 */     CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(params);
/* 39 */     ShapedRecipes recipe = new ShapedRecipes("", primer.width, primer.height, primer.input, output);
/* 40 */     register(name, (IRecipe)recipe);
/* 41 */     return recipe;
/*    */   }
/*    */   
/*    */   public static boolean canCraft(EntityPlayer player, IRecipe recipe) {
/* 45 */     for (Ingredient ingredient : recipe.getIngredients()) {
/* 46 */       if (ingredient != Ingredient.EMPTY) {
/* 47 */         boolean flag = false;
/*    */         
/* 49 */         for (ItemStack itemstack : player.inventory.mainInventory) {
/* 50 */           if (ingredient.apply(itemstack)) {
/* 51 */             flag = true;
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/* 56 */         if (!flag) {
/* 57 */           return false;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     return true;
/*    */   }
/*    */   
/*    */   public static boolean consumeInventory(EntityPlayer player, IRecipe recipe) {
/* 66 */     for (Ingredient ingredient : recipe.getIngredients()) {
/* 67 */       if (ingredient != Ingredient.EMPTY) {
/* 68 */         int i = 0;
/* 69 */         boolean flag = false;
/*    */         
/* 71 */         for (ItemStack itemstack : player.inventory.mainInventory) {
/* 72 */           if (ingredient.apply(itemstack)) {
/* 73 */             player.inventory.decrStackSize(i, 1);
/* 74 */             flag = true;
/*    */             
/*    */             break;
/*    */           } 
/* 78 */           i++;
/*    */         } 
/*    */         
/* 81 */         if (!flag) {
/* 82 */           return false;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 87 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\MCH_Recipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */