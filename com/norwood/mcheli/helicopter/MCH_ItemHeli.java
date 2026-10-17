/*    */ package com.norwood.mcheli.helicopter;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import com.norwood.mcheli.aircraft.MCH_ItemAircraft;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_ItemHeli extends MCH_ItemAircraft {
/*    */   public MCH_ItemHeli(int par1) {
/* 13 */     super(par1);
/* 14 */     this.field_77777_bU = 1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MCH_AircraftInfo getAircraftInfo() {
/* 20 */     return MCH_HeliInfoManager.getFromItem((Item)this);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MCH_EntityHeli createAircraft(World world, double x, double y, double z, ItemStack itemStack) {
/* 25 */     MCH_HeliInfo info = MCH_HeliInfoManager.getFromItem((Item)this);
/* 26 */     if (info == null) {
/* 27 */       MCH_Lib.Log(world, "##### MCH_ItemHeli Heli info null %s", new Object[] { func_77658_a() });
/* 28 */       return null;
/*    */     } 
/* 30 */     MCH_EntityHeli heli = new MCH_EntityHeli(world);
/* 31 */     heli.func_70107_b(x, y, z);
/* 32 */     heli.field_70169_q = x;
/* 33 */     heli.field_70167_r = y;
/* 34 */     heli.field_70166_s = z;
/* 35 */     heli.camera.setPosition(x, y, z);
/* 36 */     heli.setTypeName(info.name);
/* 37 */     if (!world.field_72995_K) {
/* 38 */       heli.setTextureName(info.getTextureName());
/*    */     }
/*    */     
/* 41 */     return heli;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helicopter\MCH_ItemHeli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */