/*    */ package com.norwood.mcheli.container;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_Item;
/*    */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*    */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ItemContainer
/*    */   extends W_Item {
/*    */   public MCH_ItemContainer(int par1) {
/* 23 */     super(par1);
/* 24 */     setMaxStackSize(1);
/*    */   }
/*    */   @NotNull
/*    */   public ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
/* 28 */     ItemStack itemstack = playerIn.getHeldItem(handIn);
/* 29 */     float f = 1.0F;
/* 30 */     float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * f;
/* 31 */     float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * f;
/* 32 */     double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * f;
/* 33 */     double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) * f + playerIn.getEyeHeight();
/* 34 */     double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ) * f;
/* 35 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(worldIn, d0, d1, d2);
/* 36 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 37 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 38 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 39 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/* 40 */     float f7 = f4 * f5;
/* 41 */     float f8 = f3 * f5;
/* 42 */     double d3 = 5.0D;
/* 43 */     Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
/* 44 */     RayTraceResult movingobjectposition = W_WorldFunc.clip(worldIn, vec3, vec31, true);
/* 45 */     if (movingobjectposition == null) {
/* 46 */       return ActionResult.newResult(EnumActionResult.PASS, itemstack);
/*    */     }
/* 48 */     Vec3d vec32 = playerIn.getLook(f);
/* 49 */     boolean flag = false;
/* 50 */     float f9 = 1.0F;
/* 51 */     List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)playerIn, playerIn
/* 52 */         .getEntityBoundingBox().expand(vec32.x * d3, vec32.y * d3, vec32.z * d3).grow(f9, f9, f9));
/*    */ 
/*    */     
/* 55 */     for (Entity entity : list) {
/* 56 */       if (entity.canBeCollidedWith()) {
/* 57 */         float f10 = entity.getCollisionBorderSize();
/* 58 */         AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(f10, f10, f10);
/* 59 */         if (axisalignedbb.contains(vec3)) {
/* 60 */           flag = true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 65 */     if (flag) {
/* 66 */       return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*    */     }
/* 68 */     if (W_MovingObjectPosition.isHitTypeTile(movingobjectposition)) {
/* 69 */       int ix = movingobjectposition.getBlockPos().getX();
/* 70 */       int j = movingobjectposition.getBlockPos().getY();
/* 71 */       int k = movingobjectposition.getBlockPos().getZ();
/* 72 */       MCH_EntityContainer entityboat = new MCH_EntityContainer(worldIn, (ix + 0.5F), (j + 1.0F), (k + 0.5F));
/* 73 */       entityboat.rotationYaw = (((MathHelper.floor((playerIn.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
/* 74 */       if (!worldIn.getCollisionBoxes((Entity)entityboat, entityboat.getEntityBoundingBox().grow(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/* 75 */         return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*    */       }
/*    */       
/* 78 */       if (!worldIn.isRemote) {
/* 79 */         worldIn.spawnEntity((Entity)entityboat);
/*    */       }
/*    */       
/* 82 */       if (!playerIn.capabilities.isCreativeMode) {
/* 83 */         itemstack.shrink(1);
/*    */       }
/*    */     } 
/*    */     
/* 87 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\container\MCH_ItemContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */