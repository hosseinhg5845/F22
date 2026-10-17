/*    */ package com.norwood.mcheli.plane;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import com.norwood.mcheli.aircraft.MCH_ItemAircraft;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCP_ItemPlane extends MCH_ItemAircraft {
/*    */   public MCP_ItemPlane(int par1) {
/* 13 */     super(par1);
/* 14 */     this.field_77777_bU = 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_AircraftInfo getAircraftInfo() {
/* 19 */     return MCP_PlaneInfoManager.getFromItem((Item)this);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MCP_EntityPlane createAircraft(World world, double x, double y, double z, ItemStack itemStack) {
/* 24 */     MCP_PlaneInfo info = MCP_PlaneInfoManager.getFromItem((Item)this);
/* 25 */     if (info == null) {
/* 26 */       MCH_Lib.Log(world, "##### MCP_EntityPlane Plane info null %s", new Object[] { func_77658_a() });
/* 27 */       return null;
/*    */     } 
/* 29 */     MCP_EntityPlane plane = new MCP_EntityPlane(world);
/* 30 */     plane.func_70107_b(x, y, z);
/* 31 */     plane.field_70169_q = x;
/* 32 */     plane.field_70167_r = y;
/* 33 */     plane.field_70166_s = z;
/* 34 */     plane.camera.setPosition(x, y, z);
/* 35 */     plane.setTypeName(info.name);
/* 36 */     if (!world.field_72995_K) {
/* 37 */       plane.setTextureName(info.getTextureName());
/*    */     }
/*    */     
/* 40 */     return plane;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\plane\MCP_ItemPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */