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
/* 21 */     this.field_77777_bU = 1;
/*    */   }
/*    */   @NotNull
/*    */   public ActionResult<ItemStack> func_77659_a(@NotNull World par2World, EntityPlayer par3EntityPlayer, @NotNull EnumHand handIn) {
/* 25 */     ItemStack itemstack = par3EntityPlayer.func_184586_b(handIn);
/* 26 */     float f = 1.0F;
/* 27 */     float f1 = par3EntityPlayer.field_70127_C + (par3EntityPlayer.field_70125_A - par3EntityPlayer.field_70127_C) * f;
/* 28 */     float f2 = par3EntityPlayer.field_70126_B + (par3EntityPlayer.field_70177_z - par3EntityPlayer.field_70126_B) * f;
/* 29 */     double d0 = par3EntityPlayer.field_70169_q + (par3EntityPlayer.field_70165_t - par3EntityPlayer.field_70169_q) * f;
/* 30 */     double d1 = par3EntityPlayer.field_70167_r + (par3EntityPlayer.field_70163_u - par3EntityPlayer.field_70167_r) * f + par3EntityPlayer.func_70047_e();
/* 31 */     double d2 = par3EntityPlayer.field_70166_s + (par3EntityPlayer.field_70161_v - par3EntityPlayer.field_70166_s) * f;
/* 32 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(par2World, d0, d1, d2);
/* 33 */     float f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - 3.1415927F);
/* 34 */     float f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - 3.1415927F);
/* 35 */     float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
/* 36 */     float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
/* 37 */     float f7 = f4 * f5;
/* 38 */     float f8 = f3 * f5;
/* 39 */     double d3 = 5.0D;
/* 40 */     Vec3d vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
/* 41 */     RayTraceResult movingobjectposition = W_WorldFunc.clip(par2World, vec3, vec31, true);
/* 42 */     if (movingobjectposition == null) {
/* 43 */       return ActionResult.newResult(EnumActionResult.PASS, itemstack);
/*    */     }
/* 45 */     Vec3d vec32 = par3EntityPlayer.func_70676_i(f);
/* 46 */     boolean flag = false;
/* 47 */     float f9 = 1.0F;
/* 48 */     List<Entity> list = par2World.func_72839_b((Entity)par3EntityPlayer, par3EntityPlayer
/* 49 */         .func_174813_aQ().func_72321_a(vec32.field_72450_a * d3, vec32.field_72448_b * d3, vec32.field_72449_c * d3).func_72314_b(f9, f9, f9));
/*    */ 
/*    */     
/* 52 */     for (Entity entity : list) {
/* 53 */       if (entity.func_70067_L()) {
/* 54 */         float f10 = entity.func_70111_Y();
/* 55 */         AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_72314_b(f10, f10, f10);
/* 56 */         if (axisalignedbb.func_72318_a(vec3)) {
/* 57 */           flag = true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     if (flag) {
/* 63 */       return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*    */     }
/* 65 */     if (W_MovingObjectPosition.isHitTypeTile(movingobjectposition)) {
/* 66 */       BlockPos blockpos = movingobjectposition.func_178782_a();
/* 67 */       int ix = blockpos.func_177958_n();
/* 68 */       int j = blockpos.func_177956_o();
/* 69 */       int k = blockpos.func_177952_p();
/* 70 */       MCH_EntityGLTD entityboat = new MCH_EntityGLTD(par2World, (ix + 0.5F), (j + 1.0F), (k + 0.5F));
/* 71 */       entityboat.field_70177_z = par3EntityPlayer.field_70177_z;
/* 72 */       if (!par2World.func_184144_a((Entity)entityboat, entityboat.func_174813_aQ().func_72314_b(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/* 73 */         return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*    */       }
/*    */       
/* 76 */       if (!par2World.field_72995_K) {
/* 77 */         par2World.func_72838_d((Entity)entityboat);
/*    */       }
/*    */       
/* 80 */       if (!par3EntityPlayer.field_71075_bZ.field_75098_d) {
/* 81 */         itemstack.func_190918_g(1);
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