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
/* 22 */     setMaxStackSize(1);
/*    */   }
/*    */   
/*    */   public static void registerDispenseBehavior(Item item) {
/* 26 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, new MCH_ItemThrowableDispenseBehavior());
/*    */   }
/*    */   @NotNull
/*    */   public ActionResult<ItemStack> onItemRightClick(@NotNull World world, EntityPlayer player, @NotNull EnumHand handIn) {
/* 30 */     ItemStack itemstack = player.getHeldItem(handIn);
/* 31 */     player.setActiveHand(handIn);
/* 32 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */   
/*    */   public void onPlayerStoppedUsing(@NotNull ItemStack itemStack, @NotNull World world, @NotNull EntityLivingBase entityLiving, int par4) {
/* 36 */     if (entityLiving instanceof EntityPlayer) { EntityPlayer player = (EntityPlayer)entityLiving;
/* 37 */       if (!itemStack.isEmpty() && itemStack.getCount() > 0) {
/* 38 */         MCH_ThrowableInfo info = MCH_ThrowableInfoManager.get(itemStack.getItem());
/* 39 */         if (info != null) {
/* 40 */           if (!player.capabilities.isCreativeMode) {
/* 41 */             itemStack.shrink(1);
/* 42 */             if (itemStack.getCount() <= 0) {
/* 43 */               player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
/*    */             }
/*    */           } 
/*    */           
/* 47 */           world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 55 */               .nextFloat() * 0.4F + 0.8F));
/*    */           
/* 57 */           if (!world.isRemote) {
/* 58 */             float acceleration = 1.0F;
/* 59 */             par4 = itemStack.getMaxItemUseDuration() - par4;
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
/* 70 */             entity.shoot((Entity)player, player.rotationPitch, player.rotationYaw, 0.0F, acceleration, 1.0F);
/* 71 */             entity.setInfo(info);
/* 72 */             world.spawnEntity((Entity)entity);
/*    */           } 
/*    */         } 
/*    */       }  }
/*    */   
/*    */   }
/*    */   
/*    */   public int getMaxItemUseDuration(@NotNull ItemStack par1ItemStack) {
/* 80 */     return 72000;
/*    */   }
/*    */   @NotNull
/*    */   public EnumAction getItemUseAction(@NotNull ItemStack par1ItemStack) {
/* 84 */     return EnumAction.BOW;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\throwable\MCH_ItemThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */