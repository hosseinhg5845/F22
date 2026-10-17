/*    */ package com.norwood.mcheli.plane;
/*    */ import com.norwood.mcheli.MCH_Config;
/*    */ import com.norwood.mcheli.MCH_KeyName;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftCommonGui;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCP_GuiPlane extends MCH_AircraftCommonGui {
/*    */   public MCP_GuiPlane(Minecraft minecraft) {
/* 17 */     super(minecraft);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDrawGui(EntityPlayer player) {
/* 22 */     return MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player) instanceof MCP_EntityPlane;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/* 27 */     MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
/* 28 */     if (ac instanceof MCP_EntityPlane) { MCP_EntityPlane plane = (MCP_EntityPlane)ac; if (!ac.isDestroyed()) {
/* 29 */         int seatID = ac.getSeatIdByEntity((Entity)player);
/* 30 */         GL11.glLineWidth(scaleFactor);
/* 31 */         if (plane.getCameraMode(player) == 1) {
/* 32 */           drawNightVisionNoise();
/*    */         }
/*    */         
/* 35 */         if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool) {
/* 36 */           if (seatID == 0 && plane.getIsGunnerMode((Entity)player)) {
/* 37 */             drawHud(ac, player, 1);
/*    */           } else {
/* 39 */             drawHud(ac, player, seatID);
/*    */           } 
/*    */         }
/*    */         
/* 43 */         drawDebugtInfo(plane);
/* 44 */         if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool) {
/* 45 */           if (plane.getTVMissile() == null || (!plane.getIsGunnerMode((Entity)player) && !plane.isUAV())) {
/* 46 */             drawKeybind(plane, player, seatID);
/*    */           } else {
/* 48 */             drawTvMissileNoise(plane, plane.getTVMissile());
/*    */           } 
/*    */         }
/*    */         
/* 52 */         drawHitMarker(plane, -14101432, seatID);
/*    */       }  }
/*    */   
/*    */   }
/*    */   public void drawKeybind(MCP_EntityPlane plane, EntityPlayer player, int seatID) {
/* 57 */     if (!MCH_Config.HideKeybind.prmBool) {
/* 58 */       MCP_PlaneInfo info = plane.getPlaneInfo();
/* 59 */       if (info != null) {
/* 60 */         int colorActive = -1342177281;
/* 61 */         int colorInactive = -1349546097;
/* 62 */         int RX = this.centerX + 120;
/* 63 */         int LX = this.centerX - 200;
/* 64 */         drawKeyBind(plane, info, player, seatID, RX, LX, colorActive, colorInactive);
/* 65 */         if (seatID == 0 && info.isEnableGunnerMode && !Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/* 66 */           int c = plane.isHoveringMode() ? colorInactive : colorActive;
/* 67 */           String msg = (plane.getIsGunnerMode((Entity)player) ? "Normal" : "Gunner") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt);
/* 68 */           drawString(msg, RX, this.centerY - 70, c);
/*    */         } 
/*    */         
/* 71 */         if (seatID > 0 && plane.canSwitchGunnerModeOtherSeat(player)) {
/* 72 */           String msg = (plane.getIsGunnerMode((Entity)player) ? "Normal" : "Camera") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt);
/* 73 */           drawString(msg, RX, this.centerY - 40, colorActive);
/*    */         } 
/*    */         
/* 76 */         if (seatID == 0 && info.isEnableVtol && !Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/* 77 */           int stat = plane.getVtolMode();
/* 78 */           if (stat != 1) {
/* 79 */             String msg = ((stat == 0) ? "VTOL : " : "Normal : ") + MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt);
/* 80 */             drawString(msg, RX, this.centerY - 60, colorActive);
/*    */           } 
/*    */         } 
/*    */         
/* 84 */         if (plane.canEjectSeat((Entity)player)) {
/* 85 */           String msg = "Eject seat: " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchHovering.prmInt);
/* 86 */           drawString(msg, RX, this.centerY - 30, colorActive);
/*    */         } 
/*    */         
/* 89 */         if (plane.getIsGunnerMode((Entity)player) && info.cameraZoom > 1) {
/* 90 */           String msg = "Zoom : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/* 91 */           drawString(msg, LX, this.centerY - 80, colorActive);
/* 92 */         } else if (seatID == 0) {
/* 93 */           if (plane.canFoldWing() || plane.canUnfoldWing()) {
/* 94 */             String msg = "FoldWing : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/* 95 */             drawString(msg, LX, this.centerY - 80, colorActive);
/* 96 */           } else if (plane.canFoldHatch() || plane.canUnfoldHatch()) {
/* 97 */             String msg = "OpenHatch : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/* 98 */             drawString(msg, LX, this.centerY - 80, colorActive);
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\plane\MCP_GuiPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */