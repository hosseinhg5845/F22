/*    */ package com.norwood.mcheli.tank;
/*    */ import com.norwood.mcheli.MCH_Config;
/*    */ import com.norwood.mcheli.MCH_KeyName;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftCommonGui;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_GuiTank extends MCH_AircraftCommonGui {
/*    */   public MCH_GuiTank(Minecraft minecraft) {
/* 16 */     super(minecraft);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDrawGui(EntityPlayer player) {
/* 21 */     return MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player) instanceof MCH_EntityTank;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/* 26 */     MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
/* 27 */     if (ac instanceof MCH_EntityTank) { MCH_EntityTank tank = (MCH_EntityTank)ac; if (!ac.isDestroyed()) {
/* 28 */         int seatID = ac.getSeatIdByEntity((Entity)player);
/* 29 */         GL11.glLineWidth(scaleFactor);
/* 30 */         if (tank.getCameraMode(player) == 1) {
/* 31 */           drawNightVisionNoise();
/*    */         }
/*    */         
/* 34 */         if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool) {
/* 35 */           drawHud(ac, player, seatID);
/*    */         }
/*    */         
/* 38 */         drawDebugtInfo(tank);
/* 39 */         if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool) {
/* 40 */           if (tank.getTVMissile() == null || (!tank.getIsGunnerMode((Entity)player) && !tank.isUAV())) {
/* 41 */             drawKeybind(tank, player, seatID);
/*    */           } else {
/* 43 */             drawTvMissileNoise(tank, tank.getTVMissile());
/*    */           } 
/*    */         }
/*    */         
/* 47 */         drawHitMarker(tank, -14101432, seatID);
/*    */       }  }
/*    */   
/*    */   }
/*    */   public void drawDebugtInfo(MCH_EntityTank ac) {
/* 52 */     if (MCH_Config.DebugLog) {
/* 53 */       drawDebugtInfo(ac);
/*    */     }
/*    */   }
/*    */   
/*    */   public void drawKeybind(MCH_EntityTank tank, EntityPlayer player, int seatID) {
/* 58 */     if (!MCH_Config.HideKeybind.prmBool) {
/* 59 */       MCH_TankInfo info = tank.getTankInfo();
/* 60 */       if (info != null) {
/* 61 */         int colorActive = -1342177281;
/* 62 */         int colorInactive = -1349546097;
/* 63 */         int RX = this.centerX + 120;
/* 64 */         int LX = this.centerX - 200;
/* 65 */         drawKeyBind(tank, info, player, seatID, RX, LX, colorActive, colorInactive);
/* 66 */         if (seatID == 0 && tank.hasBrake()) {
/* 67 */           String msg = "Brake : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchHovering.prmInt);
/* 68 */           drawString(msg, RX, this.centerY - 30, colorActive);
/*    */         } 
/*    */         
/* 71 */         if (seatID > 0 && tank.canSwitchGunnerModeOtherSeat(player)) {
/* 72 */           String msg = (tank.getIsGunnerMode((Entity)player) ? "Normal" : "Camera") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt);
/* 73 */           drawString(msg, RX, this.centerY - 40, colorActive);
/*    */         } 
/*    */         
/* 76 */         if (tank.getIsGunnerMode((Entity)player) && info.cameraZoom > 1) {
/* 77 */           String msg = "Zoom : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/* 78 */           drawString(msg, LX, this.centerY - 80, colorActive);
/* 79 */         } else if (seatID == 0 && (tank.canFoldHatch() || tank.canUnfoldHatch())) {
/* 80 */           String msg = "OpenHatch : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/* 81 */           drawString(msg, LX, this.centerY - 80, colorActive);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tank\MCH_GuiTank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */