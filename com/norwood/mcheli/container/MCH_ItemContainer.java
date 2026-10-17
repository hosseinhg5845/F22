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
/* 24 */     func_77625_d(1);
/*    */   }
/*    */   @NotNull
/*    */   public ActionResult<ItemStack> func_77659_a(@NotNull World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
/* 28 */     ItemStack itemstack = playerIn.func_184586_b(handIn);
/* 29 */     float f = 1.0F;
/* 30 */     float f1 = playerIn.field_70127_C + (playerIn.field_70125_A - playerIn.field_70127_C) * f;
/* 31 */     float f2 = playerIn.field_70126_B + (playerIn.field_70177_z - playerIn.field_70126_B) * f;
/* 32 */     double d0 = playerIn.field_70169_q + (playerIn.field_70165_t - playerIn.field_70169_q) * f;
/* 33 */     double d1 = playerIn.field_70167_r + (playerIn.field_70163_u - playerIn.field_70167_r) * f + playerIn.func_70047_e();
/* 34 */     double d2 = playerIn.field_70166_s + (playerIn.field_70161_v - playerIn.field_70166_s) * f;
/* 35 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(worldIn, d0, d1, d2);
/* 36 */     float f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - 3.1415927F);
/* 37 */     float f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - 3.1415927F);
/* 38 */     float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
/* 39 */     float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
/* 40 */     float f7 = f4 * f5;
/* 41 */     float f8 = f3 * f5;
/* 42 */     double d3 = 5.0D;
/* 43 */     Vec3d vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
/* 44 */     RayTraceResult movingobjectposition = W_WorldFunc.clip(worldIn, vec3, vec31, true);
/* 45 */     if (movingobjectposition == null) {
/* 46 */       return ActionResult.newResult(EnumActionResult.PASS, itemstack);
/*    */     }
/* 48 */     Vec3d vec32 = playerIn.func_70676_i(f);
/* 49 */     boolean flag = false;
/* 50 */     float f9 = 1.0F;
/* 51 */     List<Entity> list = worldIn.func_72839_b((Entity)playerIn, playerIn
/* 52 */         .func_174813_aQ().func_72321_a(vec32.field_72450_a * d3, vec32.field_72448_b * d3, vec32.field_72449_c * d3).func_72314_b(f9, f9, f9));
/*    */ 
/*    */     
/* 55 */     for (Entity entity : list) {
/* 56 */       if (entity.func_70067_L()) {
/* 57 */         float f10 = entity.func_70111_Y();
/* 58 */         AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_72314_b(f10, f10, f10);
/* 59 */         if (axisalignedbb.func_72318_a(vec3)) {
/* 60 */           flag = true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 65 */     if (flag) {
/* 66 */       return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*    */     }
/* 68 */     if (W_MovingObjectPosition.isHitTypeTile(movingobjectposition)) {
/* 69 */       int ix = movingobjectposition.func_178782_a().func_177958_n();
/* 70 */       int j = movingobjectposition.func_178782_a().func_177956_o();
/* 71 */       int k = movingobjectposition.func_178782_a().func_177952_p();
/* 72 */       MCH_EntityContainer entityboat = new MCH_EntityContainer(worldIn, (ix + 0.5F), (j + 1.0F), (k + 0.5F));
/* 73 */       entityboat.field_70177_z = (((MathHelper.func_76128_c((playerIn.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
/* 74 */       if (!worldIn.func_184144_a((Entity)entityboat, entityboat.func_174813_aQ().func_72314_b(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/* 75 */         return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*    */       }
/*    */       
/* 78 */       if (!worldIn.field_72995_K) {
/* 79 */         worldIn.func_72838_d((Entity)entityboat);
/*    */       }
/*    */       
/* 82 */       if (!playerIn.field_71075_bZ.field_75098_d) {
/* 83 */         itemstack.func_190918_g(1);
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