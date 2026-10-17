/*     */ package com.norwood.mcheli.gui;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftGui;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftGuiContainer;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.block.MCH_DraftingTableGui;
/*     */ import com.norwood.mcheli.block.MCH_DraftingTableGuiContainer;
/*     */ import com.norwood.mcheli.helper.MCH_Utils;
/*     */ import com.norwood.mcheli.helper.network.PooledGuiParameter;
/*     */ import com.norwood.mcheli.multiplay.MCH_ContainerScoreboard;
/*     */ import com.norwood.mcheli.multiplay.MCH_GuiScoreboard;
/*     */ import com.norwood.mcheli.uav.MCH_ContainerUavStation;
/*     */ import com.norwood.mcheli.uav.MCH_EntityUavStation;
/*     */ import com.norwood.mcheli.uav.MCH_GuiUavStation;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.network.IGuiHandler;
/*     */ 
/*     */ public class MCH_GuiCommonHandler
/*     */   implements IGuiHandler {
/*     */   public static final int GUIID_UAV_STATION = 0;
/*     */   public static final int GUIID_AIRCRAFT = 1;
/*     */   public static final int GUIID_CONFG = 2;
/*     */   
/*     */   public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/*     */     Entity uavStation;
/*     */     MCH_EntityAircraft ac;
/*  31 */     MCH_Lib.DbgLog(world, "MCH_GuiCommonHandler.getServerGuiElement ID=%d (%d, %d, %d)", new Object[] { Integer.valueOf(id), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z) });
/*  32 */     switch (id) {
/*     */       case 0:
/*  34 */         uavStation = PooledGuiParameter.getEntity(player);
/*  35 */         PooledGuiParameter.resetEntity(player);
/*  36 */         if (uavStation instanceof MCH_EntityUavStation) {
/*  37 */           return new MCH_ContainerUavStation(player.field_71071_by, (MCH_EntityUavStation)uavStation);
/*     */         }
/*     */         break;
/*     */       case 1:
/*  41 */         ac = null;
/*  42 */         if (player.func_184187_bx() instanceof MCH_EntityAircraft) {
/*  43 */           ac = (MCH_EntityAircraft)player.func_184187_bx();
/*  44 */         } else if (player.func_184187_bx() instanceof MCH_EntityUavStation) {
/*  45 */           ac = ((MCH_EntityUavStation)player.func_184187_bx()).getControlAircract();
/*     */         } 
/*     */         
/*  48 */         if (ac != null) {
/*  49 */           return new MCH_AircraftGuiContainer(player, ac);
/*     */         }
/*     */         break;
/*     */       case 2:
/*  53 */         return new MCH_ConfigGuiContainer(player);
/*     */       
/*     */       case 4:
/*  56 */         return new MCH_DraftingTableGuiContainer(player, x, y, z);
/*     */       
/*     */       case 5:
/*  59 */         if (!MCH_Utils.getServer().func_71264_H() || MCH_Config.DebugLog) {
/*  60 */           return new MCH_ContainerScoreboard(player);
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  67 */     return null;
/*     */   } public static final int GUIID_INVENTORY = 3; public static final int GUIID_DRAFTING = 4; public static final int GUIID_MULTI_MNG = 5; public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/*     */     Entity uavStation;
/*     */     MCH_EntityAircraft ac;
/*  71 */     MCH_Lib.DbgLog(world, "MCH_GuiCommonHandler.getClientGuiElement ID=%d (%d, %d, %d)", new Object[] { Integer.valueOf(id), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z) });
/*  72 */     switch (id) {
/*     */       case 0:
/*  74 */         uavStation = PooledGuiParameter.getEntity(player);
/*  75 */         PooledGuiParameter.resetEntity(player);
/*  76 */         if (uavStation instanceof MCH_EntityUavStation) {
/*  77 */           return new MCH_GuiUavStation(player.field_71071_by, (MCH_EntityUavStation)uavStation);
/*     */         }
/*     */         break;
/*     */       case 1:
/*  81 */         ac = null;
/*  82 */         if (player.func_184187_bx() instanceof MCH_EntityAircraft) {
/*  83 */           ac = (MCH_EntityAircraft)player.func_184187_bx();
/*  84 */         } else if (player.func_184187_bx() instanceof MCH_EntityUavStation) {
/*  85 */           ac = ((MCH_EntityUavStation)player.func_184187_bx()).getControlAircract();
/*     */         } 
/*     */         
/*  88 */         if (ac != null) {
/*  89 */           return new MCH_AircraftGui(player, ac);
/*     */         }
/*     */         break;
/*     */       case 2:
/*  93 */         return new MCH_ConfigGui(player);
/*     */       
/*     */       case 4:
/*  96 */         return new MCH_DraftingTableGui(player, x, y, z);
/*     */       
/*     */       case 5:
/*  99 */         return new MCH_GuiScoreboard(player);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gui\MCH_GuiCommonHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */