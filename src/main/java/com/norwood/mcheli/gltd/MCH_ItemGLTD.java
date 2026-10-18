/*    */ package com.norwood.mcheli.gltd;
import com.norwood.mcheli.wrapper.W_Item;
/*    */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ItemGLTD extends W_Item {
/*    */   public MCH_ItemGLTD(int par1) {
/* 20 */     super(par1);
/* 21 */     this.maxStackSize = 1;
/*    */   }
/*    */   @NotNull
/*    */   public ActionResult<ItemStack> onItemRightClick(@NotNull World par2World, EntityPlayer par3EntityPlayer, @NotNull EnumHand handIn) {
/* 25 */     ItemStack itemstack = par3EntityPlayer.getHeldItem(handIn);
/* 26 */     float f = 1.0F;
/* 27 */     float f1 = par3EntityPlayer.prevRotationPitch + (par3EntityPlayer.rotationPitch - par3EntityPlayer.prevRotationPitch) * f;
/* 28 */     float f2 = par3EntityPlayer.prevRotationYaw + (par3EntityPlayer.rotationYaw - par3EntityPlayer.prevRotationYaw) * f;
/* 29 */     double d0 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * f;
/* 30 */     double d1 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * f + par3EntityPlayer.getEyeHeight();
/* 31 */     double d2 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * f;
/* 32 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(par2World, d0, d1, d2);
/* 33 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 34 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 35 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 36 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/* 37 */     float f7 = f4 * f5;
/* 38 */     float f8 = f3 * f5;
/* 39 */     double d3 = 5.0D;
/* 40 */     Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
/* 41 */     RayTraceResult movingobjectposition = W_WorldFunc.clip(par2World, vec3, vec31, true);
/* 42 */     if (movingobjectposition == null) {
/* 43 */       return ActionResult.newResult(EnumActionResult.PASS, itemstack);
/*    */     }
/* 45 */     Vec3d vec32 = par3EntityPlayer.getLook(f);
/* 46 */     boolean flag = false;
/* 47 */     float f9 = 1.0F;
/* 48 */     List<Entity> list = par2World.getEntitiesWithinAABBExcludingEntity((Entity)par3EntityPlayer, par3EntityPlayer
/* 49 */         .getEntityBoundingBox().expand(vec32.x * d3, vec32.y * d3, vec32.z * d3).grow(f9, f9, f9));
/*    */ 
/*    */     
/* 52 */     for (Entity entity : list) {
/* 53 */       if (entity.canBeCollidedWith()) {
/* 54 */         float f10 = entity.getCollisionBorderSize();
/* 55 */         AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(f10, f10, f10);
/* 56 */         if (axisalignedbb.contains(vec3)) {
/* 57 */           flag = true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     if (flag) {
/* 63 */       return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*    */     }
/* 65 */     if (W_MovingObjectPosition.isHitTypeTile(movingobjectposition)) {
/* 66 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/* 67 */       int ix = blockpos.getX();
/* 68 */       int j = blockpos.getY();
/* 69 */       int k = blockpos.getZ();
/* 70 */       MCH_EntityGLTD entityboat = new MCH_EntityGLTD(par2World, (ix + 0.5F), (j + 1.0F), (k + 0.5F));
/* 71 */       entityboat.rotationYaw = par3EntityPlayer.rotationYaw;
/* 72 */       if (!par2World.getCollisionBoxes((Entity)entityboat, entityboat.getEntityBoundingBox().grow(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/* 73 */         return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*    */       }
/*    */       
/* 76 */       if (!par2World.isRemote) {
/* 77 */         par2World.spawnEntity((Entity)entityboat);
/*    */       }
/*    */       
/* 80 */       if (!par3EntityPlayer.capabilities.isCreativeMode) {
/* 81 */         itemstack.shrink(1);
/*    */       }
/*    */     } 
/*    */     
/* 85 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gltd\MCH_ItemGLTD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */