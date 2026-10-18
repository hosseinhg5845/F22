/*    */ package com.norwood.mcheli.tank;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import com.norwood.mcheli.aircraft.MCH_ItemAircraft;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_ItemTank extends MCH_ItemAircraft {
/*    */   public MCH_ItemTank(int par1) {
/* 13 */     super(par1);
/* 14 */     this.maxStackSize = 1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MCH_AircraftInfo getAircraftInfo() {
/* 20 */     return MCH_TankInfoManager.getFromItem((Item)this);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MCH_EntityTank createAircraft(World world, double x, double y, double z, ItemStack itemStack) {
/* 25 */     MCH_TankInfo info = MCH_TankInfoManager.getFromItem((Item)this);
/* 26 */     if (info == null) {
/* 27 */       MCH_Lib.Log(world, "##### MCH_EntityTank Tank info null %s", new Object[] { getUnlocalizedName() });
/* 28 */       return null;
/*    */     } 
/* 30 */     MCH_EntityTank tank = new MCH_EntityTank(world);
/* 31 */     tank.setPosition(x, y, z);
/* 32 */     tank.prevPosX = x;
/* 33 */     tank.prevPosY = y;
/* 34 */     tank.prevPosZ = z;
/* 35 */     tank.camera.setPosition(x, y, z);
/* 36 */     tank.setTypeName(info.name);
/* 37 */     if (!world.isRemote) {
/* 38 */       tank.setTextureName(info.getTextureName());
/*    */     }
/*    */     
/* 41 */     return tank;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tank\MCH_ItemTank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */