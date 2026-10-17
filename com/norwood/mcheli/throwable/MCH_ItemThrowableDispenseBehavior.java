/*    */ package com.norwood.mcheli.throwable;
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.dispenser.IBlockSource;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ItemThrowableDispenseBehavior extends BehaviorDefaultDispenseItem {
/*    */   @NotNull
/*    */   public ItemStack func_82487_b(IBlockSource bs, ItemStack itemStack) {
/* 15 */     EnumFacing enumfacing = (EnumFacing)bs.func_189992_e().func_177229_b((IProperty)BlockDispenser.field_176441_a);
/* 16 */     double x = bs.func_82615_a() + enumfacing.func_82601_c() * 2.0D;
/* 17 */     double y = bs.func_82617_b() + enumfacing.func_96559_d() * 2.0D;
/* 18 */     double z = bs.func_82616_c() + enumfacing.func_82599_e() * 2.0D;
/* 19 */     if (itemStack.func_77973_b() instanceof MCH_ItemThrowable) {
/* 20 */       MCH_ThrowableInfo info = MCH_ThrowableInfoManager.get(itemStack.func_77973_b());
/* 21 */       if (info != null) {
/* 22 */         bs.func_82618_k()
/* 23 */           .func_184134_a(x, y, z, SoundEvents.field_187737_v, SoundCategory.BLOCKS, 0.5F, 0.4F / ((bs.func_82618_k()).field_73012_v.nextFloat() * 0.4F + 0.8F), false);
/* 24 */         if (!(bs.func_82618_k()).field_72995_K) {
/* 25 */           MCH_Lib.DbgLog(bs.func_82618_k(), "MCH_ItemThrowableDispenseBehavior.dispenseStack(%s)", new Object[] { info.name });
/* 26 */           MCH_EntityThrowable entity = new MCH_EntityThrowable(bs.func_82618_k(), x, y, z);
/* 27 */           entity.field_70159_w = (enumfacing.func_82601_c() * info.dispenseAcceleration);
/* 28 */           entity.field_70181_x = (enumfacing.func_96559_d() * info.dispenseAcceleration);
/* 29 */           entity.field_70179_y = (enumfacing.func_82599_e() * info.dispenseAcceleration);
/* 30 */           entity.setInfo(info);
/* 31 */           bs.func_82618_k().func_72838_d((Entity)entity);
/* 32 */           itemStack.func_77979_a(1);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 37 */     return itemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\throwable\MCH_ItemThrowableDispenseBehavior.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */