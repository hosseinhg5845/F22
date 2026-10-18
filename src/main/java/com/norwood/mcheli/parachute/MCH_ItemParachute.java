/*    */ package com.norwood.mcheli.parachute;
/*    */ import com.norwood.mcheli.wrapper.W_Item;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ItemParachute extends W_Item {
/*    */   public MCH_ItemParachute(int par1) {
/* 14 */     super(par1);
/* 15 */     this.maxStackSize = 1;
/*    */   }
/*    */   @NotNull
/*    */   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @NotNull EnumHand handIn) {
/* 19 */     ItemStack itemstack = player.getHeldItem(handIn);
/* 20 */     if (!world.isRemote && player.getRidingEntity() == null && !player.onGround) {
/* 21 */       double x = player.posX + 0.5D;
/* 22 */       double y = player.posY + 3.5D;
/* 23 */       double z = player.posZ + 0.5D;
/* 24 */       MCH_EntityParachute entity = new MCH_EntityParachute(world, x, y, z);
/* 25 */       entity.rotationYaw = player.rotationYaw;
/* 26 */       entity.motionX = player.motionX;
/* 27 */       entity.motionY = player.motionY;
/* 28 */       entity.motionZ = player.motionZ;
/* 29 */       entity.fallDistance = player.fallDistance;
/* 30 */       player.fallDistance = 0.0F;
/* 31 */       entity.user = (Entity)player;
/* 32 */       entity.setType(1);
/* 33 */       world.spawnEntity((Entity)entity);
/*    */     } 
/*    */     
/* 36 */     if (!player.capabilities.isCreativeMode) {
/* 37 */       itemstack.shrink(1);
/*    */     }
/*    */     
/* 40 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\parachute\MCH_ItemParachute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */