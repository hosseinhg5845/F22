/*    */ package com.norwood.mcheli.vehicle;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import com.norwood.mcheli.aircraft.MCH_ItemAircraft;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_ItemVehicle extends MCH_ItemAircraft {
/*    */   public MCH_ItemVehicle(int par1) {
/* 13 */     super(par1);
/* 14 */     this.field_77777_bU = 1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MCH_AircraftInfo getAircraftInfo() {
/* 20 */     return MCH_VehicleInfoManager.getFromItem((Item)this);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MCH_EntityVehicle createAircraft(World world, double x, double y, double z, ItemStack item) {
/* 25 */     MCH_VehicleInfo info = MCH_VehicleInfoManager.getFromItem((Item)this);
/* 26 */     if (info == null) {
/* 27 */       MCH_Lib.Log(world, "##### MCH_ItemVehicle Vehicle info null %s", new Object[] { func_77658_a() });
/* 28 */       return null;
/*    */     } 
/* 30 */     MCH_EntityVehicle vehicle = new MCH_EntityVehicle(world);
/* 31 */     vehicle.func_70107_b(x, y, z);
/* 32 */     vehicle.field_70169_q = x;
/* 33 */     vehicle.field_70167_r = y;
/* 34 */     vehicle.field_70166_s = z;
/* 35 */     vehicle.camera.setPosition(x, y, z);
/* 36 */     vehicle.setTypeName(info.name);
/* 37 */     if (!world.field_72995_K) {
/* 38 */       vehicle.setTextureName(info.getTextureName());
/*    */     }
/*    */     
/* 41 */     return vehicle;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\vehicle\MCH_ItemVehicle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */