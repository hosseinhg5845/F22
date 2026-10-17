/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_Item;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraft.world.World;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ItemFuel
/*    */   extends W_Item
/*    */ {
/*    */   public MCH_ItemFuel(int itemID) {
/* 17 */     super(itemID);
/* 18 */     func_77656_e(600);
/* 19 */     func_77625_d(1);
/* 20 */     setNoRepair();
/* 21 */     func_77664_n();
/*    */   }
/*    */   @NotNull
/*    */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, @NotNull EnumHand handIn) {
/* 25 */     ItemStack stack = player.func_184586_b(handIn);
/* 26 */     if (!world.field_72995_K && stack.func_77951_h() && !player.field_71075_bZ.field_75098_d) {
/* 27 */       refuel(stack, player, 1);
/* 28 */       refuel(stack, player, 0);
/* 29 */       return new ActionResult(EnumActionResult.SUCCESS, stack);
/*    */     } 
/* 31 */     return new ActionResult(EnumActionResult.PASS, stack);
/*    */   }
/*    */ 
/*    */   
/*    */   private void refuel(ItemStack stack, EntityPlayer player, int coalType) {
/* 36 */     NonNullList<ItemStack> nonNullList = player.field_71071_by.field_70462_a;
/*    */     
/* 38 */     for (int i = 0; i < nonNullList.size(); i++) {
/* 39 */       ItemStack is = nonNullList.get(i);
/* 40 */       if (!is.func_190926_b() && is.func_77973_b() instanceof net.minecraft.item.ItemCoal && is.func_77960_j() == coalType) {
/* 41 */         for (int j = 0; is.func_190916_E() > 0 && stack.func_77951_h() && j < 64; j++) {
/* 42 */           int damage = stack.func_77960_j() - ((coalType == 1) ? 75 : 100);
/* 43 */           if (damage < 0) {
/* 44 */             damage = 0;
/*    */           }
/*    */           
/* 47 */           stack.func_77964_b(damage);
/* 48 */           is.func_190918_g(1);
/*    */         } 
/*    */         
/* 51 */         if (is.func_190916_E() <= 0)
/* 52 */           nonNullList.set(i, ItemStack.field_190927_a); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_ItemFuel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */