/*    */ package com.norwood.mcheli.aircraft;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.dispenser.IBlockSource;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ItemAircraftDispenseBehavior extends BehaviorDefaultDispenseItem {
/*    */   @NotNull
/*    */   public ItemStack dispenseStack(IBlockSource bs, ItemStack itemStack) {
/* 13 */     EnumFacing enumfacing = (EnumFacing)bs.getBlockState().getValue((IProperty)BlockDispenser.FACING);
/* 14 */     double x = bs.getX() + enumfacing.getFrontOffsetX() * 2.0D;
/* 15 */     double y = bs.getY() + enumfacing.getFrontOffsetY() * 2.0D;
/* 16 */     double z = bs.getZ() + enumfacing.getFrontOffsetZ() * 2.0D;
/* 17 */     if (itemStack.getItem() instanceof MCH_ItemAircraft) {
/* 18 */       MCH_EntityAircraft ac = ((MCH_ItemAircraft)itemStack.getItem()).onTileClick(itemStack, bs.getWorld(), 0.0F, (int)x, (int)y, (int)z);
/* 19 */       if (ac != null && ac.getAcInfo() != null && !(ac.getAcInfo()).creativeOnly && !ac.isUAV()) {
/* 20 */         if (!(bs.getWorld()).isRemote) {
/* 21 */           ac.getAcDataFromItem(itemStack);
/* 22 */           bs.getWorld().spawnEntity((Entity)ac);
/*    */         } 
/*    */         
/* 25 */         itemStack.splitStack(1);
/* 26 */         MCH_Lib.DbgLog(bs.getWorld(), "dispenseStack:x=%.1f,y=%.1f,z=%.1f;dir=%s:item=" + itemStack.getDisplayName(), new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), enumfacing.toString() });
/*    */       } 
/*    */     } 
/*    */     
/* 30 */     return itemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_ItemAircraftDispenseBehavior.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */