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
/* 15 */     this.field_77777_bU = 1;
/*    */   }
/*    */   @NotNull
/*    */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, @NotNull EnumHand handIn) {
/* 19 */     ItemStack itemstack = player.func_184586_b(handIn);
/* 20 */     if (!world.field_72995_K && player.func_184187_bx() == null && !player.field_70122_E) {
/* 21 */       double x = player.field_70165_t + 0.5D;
/* 22 */       double y = player.field_70163_u + 3.5D;
/* 23 */       double z = player.field_70161_v + 0.5D;
/* 24 */       MCH_EntityParachute entity = new MCH_EntityParachute(world, x, y, z);
/* 25 */       entity.field_70177_z = player.field_70177_z;
/* 26 */       entity.field_70159_w = player.field_70159_w;
/* 27 */       entity.field_70181_x = player.field_70181_x;
/* 28 */       entity.field_70179_y = player.field_70179_y;
/* 29 */       entity.field_70143_R = player.field_70143_R;
/* 30 */       player.field_70143_R = 0.0F;
/* 31 */       entity.user = (Entity)player;
/* 32 */       entity.setType(1);
/* 33 */       world.func_72838_d((Entity)entity);
/*    */     } 
/*    */     
/* 36 */     if (!player.field_71075_bZ.field_75098_d) {
/* 37 */       itemstack.func_190918_g(1);
/*    */     }
/*    */     
/* 40 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\parachute\MCH_ItemParachute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */