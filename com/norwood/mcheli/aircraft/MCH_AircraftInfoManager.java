/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_IRecipeList;
/*    */ import com.norwood.mcheli.MCH_InfoManagerBase;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ 
/*    */ public abstract class MCH_AircraftInfoManager<T extends MCH_AircraftInfo>
/*    */   extends MCH_InfoManagerBase<T> implements MCH_IRecipeList {
/* 12 */   private final List<IRecipe> listItemRecipe = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public int getRecipeListSize() {
/* 16 */     return this.listItemRecipe.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipe getRecipe(int index) {
/* 21 */     return this.listItemRecipe.get(index);
/*    */   }
/*    */   
/*    */   public void addRecipe(IRecipe recipe, int count, String name, String recipeString) {
/* 25 */     if (recipe != null) {
/* 26 */       this.listItemRecipe.add(recipe);
/*    */     } else {
/* 28 */       throw new RuntimeException("[mcheli]Recipe Parameter Error! recipe" + count + " : " + name + ".txt : " + recipe + " : " + recipeString);
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract MCH_AircraftInfo getAcInfoFromItem(Item paramItem);
/*    */   
/*    */   public MCH_AircraftInfo getAcInfoFromItem(IRecipe recipe) {
/* 35 */     return (recipe != null) ? getAcInfoFromItem(recipe.func_77571_b().func_77973_b()) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_AircraftInfoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */