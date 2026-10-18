/*    */ package com.norwood.mcheli.throwable;
import com.norwood.mcheli.MCH_Lib;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
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
/*    */   public ItemStack dispenseStack(IBlockSource bs, ItemStack itemStack) {
/* 15 */     EnumFacing enumfacing = (EnumFacing)bs.getBlockState().getValue((IProperty)BlockDispenser.FACING);
/* 16 */     double x = bs.getX() + enumfacing.getFrontOffsetX() * 2.0D;
/* 17 */     double y = bs.getY() + enumfacing.getFrontOffsetY() * 2.0D;
/* 18 */     double z = bs.getZ() + enumfacing.getFrontOffsetZ() * 2.0D;
/* 19 */     if (itemStack.getItem() instanceof MCH_ItemThrowable) {
/* 20 */       MCH_ThrowableInfo info = MCH_ThrowableInfoManager.get(itemStack.getItem());
/* 21 */       if (info != null) {
/* 22 */         bs.getWorld()
/* 23 */           .playSound(x, y, z, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.BLOCKS, 0.5F, 0.4F / ((bs.getWorld()).rand.nextFloat() * 0.4F + 0.8F), false);
/* 24 */         if (!(bs.getWorld()).isRemote) {
/* 25 */           MCH_Lib.DbgLog(bs.getWorld(), "MCH_ItemThrowableDispenseBehavior.dispenseStack(%s)", new Object[] { info.name });
/* 26 */           MCH_EntityThrowable entity = new MCH_EntityThrowable(bs.getWorld(), x, y, z);
/* 27 */           entity.motionX = (enumfacing.getFrontOffsetX() * info.dispenseAcceleration);
/* 28 */           entity.motionY = (enumfacing.getFrontOffsetY() * info.dispenseAcceleration);
/* 29 */           entity.motionZ = (enumfacing.getFrontOffsetZ() * info.dispenseAcceleration);
/* 30 */           entity.setInfo(info);
/* 31 */           bs.getWorld().spawnEntity((Entity)entity);
/* 32 */           itemStack.splitStack(1);
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