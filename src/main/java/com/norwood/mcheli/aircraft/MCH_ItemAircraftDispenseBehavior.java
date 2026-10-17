/*    */ package com.norwood.mcheli.aircraft;
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
/*    */   public ItemStack func_82487_b(IBlockSource bs, ItemStack itemStack) {
/* 13 */     EnumFacing enumfacing = (EnumFacing)bs.func_189992_e().func_177229_b((IProperty)BlockDispenser.field_176441_a);
/* 14 */     double x = bs.func_82615_a() + enumfacing.func_82601_c() * 2.0D;
/* 15 */     double y = bs.func_82617_b() + enumfacing.func_96559_d() * 2.0D;
/* 16 */     double z = bs.func_82616_c() + enumfacing.func_82599_e() * 2.0D;
/* 17 */     if (itemStack.func_77973_b() instanceof MCH_ItemAircraft) {
/* 18 */       MCH_EntityAircraft ac = ((MCH_ItemAircraft)itemStack.func_77973_b()).onTileClick(itemStack, bs.func_82618_k(), 0.0F, (int)x, (int)y, (int)z);
/* 19 */       if (ac != null && ac.getAcInfo() != null && !(ac.getAcInfo()).creativeOnly && !ac.isUAV()) {
/* 20 */         if (!(bs.func_82618_k()).field_72995_K) {
/* 21 */           ac.getAcDataFromItem(itemStack);
/* 22 */           bs.func_82618_k().func_72838_d((Entity)ac);
/*    */         } 
/*    */         
/* 25 */         itemStack.func_77979_a(1);
/* 26 */         MCH_Lib.DbgLog(bs.func_82618_k(), "dispenseStack:x=%.1f,y=%.1f,z=%.1f;dir=%s:item=" + itemStack.func_82833_r(), new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), enumfacing.toString() });
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