/*    */ package com.norwood.mcheli.throwable;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.wrapper.W_Item;
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.item.EnumAction;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.world.World;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ItemThrowable extends W_Item {
/*    */   public MCH_ItemThrowable(int par1) {
/* 21 */     super(par1);
/* 22 */     func_77625_d(1);
/*    */   }
/*    */   
/*    */   public static void registerDispenseBehavior(Item item) {
/* 26 */     BlockDispenser.field_149943_a.func_82595_a(item, new MCH_ItemThrowableDispenseBehavior());
/*    */   }
/*    */   @NotNull
/*    */   public ActionResult<ItemStack> func_77659_a(@NotNull World world, EntityPlayer player, @NotNull EnumHand handIn) {
/* 30 */     ItemStack itemstack = player.func_184586_b(handIn);
/* 31 */     player.func_184598_c(handIn);
/* 32 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */   
/*    */   public void func_77615_a(@NotNull ItemStack itemStack, @NotNull World world, @NotNull EntityLivingBase entityLiving, int par4) {
/* 36 */     if (entityLiving instanceof EntityPlayer) { EntityPlayer player = (EntityPlayer)entityLiving;
/* 37 */       if (!itemStack.func_190926_b() && itemStack.func_190916_E() > 0) {
/* 38 */         MCH_ThrowableInfo info = MCH_ThrowableInfoManager.get(itemStack.func_77973_b());
/* 39 */         if (info != null) {
/* 40 */           if (!player.field_71075_bZ.field_75098_d) {
/* 41 */             itemStack.func_190918_g(1);
/* 42 */             if (itemStack.func_190916_E() <= 0) {
/* 43 */               player.field_71071_by.func_70299_a(player.field_71071_by.field_70461_c, ItemStack.field_190927_a);
/*    */             }
/*    */           } 
/*    */           
/* 47 */           world.func_184148_a(null, player.field_70165_t, player.field_70163_u, player.field_70161_v, SoundEvents.field_187737_v, SoundCategory.PLAYERS, 0.5F, 0.4F / (field_77697_d
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 55 */               .nextFloat() * 0.4F + 0.8F));
/*    */           
/* 57 */           if (!world.field_72995_K) {
/* 58 */             float acceleration = 1.0F;
/* 59 */             par4 = itemStack.func_77988_m() - par4;
/* 60 */             if (par4 <= 35) {
/* 61 */               if (par4 < 5) {
/* 62 */                 par4 = 5;
/*    */               }
/*    */               
/* 65 */               acceleration = par4 / 25.0F;
/*    */             } 
/*    */             
/* 68 */             MCH_Lib.DbgLog(world, "MCH_ItemThrowable.onPlayerStoppedUsing(%d)", new Object[] { Integer.valueOf(par4) });
/* 69 */             MCH_EntityThrowable entity = new MCH_EntityThrowable(world, (EntityLivingBase)player, acceleration);
/* 70 */             entity.func_184538_a((Entity)player, player.field_70125_A, player.field_70177_z, 0.0F, acceleration, 1.0F);
/* 71 */             entity.setInfo(info);
/* 72 */             world.func_72838_d((Entity)entity);
/*    */           } 
/*    */         } 
/*    */       }  }
/*    */   
/*    */   }
/*    */   
/*    */   public int func_77626_a(@NotNull ItemStack par1ItemStack) {
/* 80 */     return 72000;
/*    */   }
/*    */   @NotNull
/*    */   public EnumAction func_77661_b(@NotNull ItemStack par1ItemStack) {
/* 84 */     return EnumAction.BOW;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\throwable\MCH_ItemThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */