/*     */ package com.norwood.mcheli.helicopter;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_KeyName;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftCommonGui;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityTvMissile;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiHeli extends MCH_AircraftCommonGui {
/*     */   public MCH_GuiHeli(Minecraft minecraft) {
/*  19 */     super(minecraft);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  24 */     return MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player) instanceof MCH_EntityHeli;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  29 */     MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
/*  30 */     if (ac instanceof MCH_EntityHeli) { MCH_EntityHeli heli = (MCH_EntityHeli)ac; if (!ac.isDestroyed()) {
/*  31 */         int seatID = ac.getSeatIdByEntity((Entity)player);
/*  32 */         GL11.glLineWidth(scaleFactor);
/*  33 */         if (heli.getCameraMode(player) == 1) {
/*  34 */           drawNightVisionNoise();
/*     */         }
/*     */         
/*  37 */         if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool) {
/*  38 */           if (seatID == 0 && heli.getIsGunnerMode((Entity)player)) {
/*  39 */             drawHud(ac, player, 1);
/*     */           } else {
/*  41 */             drawHud(ac, player, seatID);
/*     */           } 
/*     */         }
/*     */         
/*  45 */         drawDebugtInfo(heli);
/*  46 */         if (!heli.getIsGunnerMode((Entity)player)) {
/*  47 */           if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool) {
/*  48 */             drawKeyBind(heli, player, seatID);
/*     */           }
/*     */           
/*  51 */           drawHitMarker(heli, -14101432, seatID);
/*     */         } else {
/*  53 */           if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool) {
/*  54 */             MCH_EntityTvMissile tvmissile = heli.getTVMissile();
/*  55 */             if (!heli.isMissileCameraMode((Entity)player)) {
/*  56 */               drawKeyBind(heli, player, seatID);
/*  57 */             } else if (tvmissile != null) {
/*  58 */               drawTvMissileNoise(heli, tvmissile);
/*     */             } 
/*     */           } 
/*     */           
/*  62 */           drawHitMarker(heli, -805306369, seatID);
/*     */         } 
/*     */       }  }
/*     */   
/*     */   }
/*     */   public void drawKeyBind(MCH_EntityHeli heli, EntityPlayer player, int seatID) {
/*  68 */     if (!MCH_Config.HideKeybind.prmBool) {
/*  69 */       MCH_HeliInfo info = heli.getHeliInfo();
/*  70 */       if (info != null) {
/*  71 */         int colorActive = -1342177281;
/*  72 */         int colorInactive = -1349546097;
/*  73 */         int RX = this.centerX + 120;
/*  74 */         int LX = this.centerX - 200;
/*  75 */         drawKeyBind(heli, info, player, seatID, RX, LX, colorActive, colorInactive);
/*  76 */         if (seatID == 0 && info.isEnableGunnerMode && !Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/*  77 */           int c = heli.isHoveringMode() ? colorInactive : colorActive;
/*  78 */           String msg = (heli.getIsGunnerMode((Entity)player) ? "Normal" : "Gunner") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt);
/*  79 */           drawString(msg, RX, this.centerY - 70, c);
/*     */         } 
/*     */         
/*  82 */         if (seatID > 0 && heli.canSwitchGunnerModeOtherSeat(player)) {
/*  83 */           String msg = (heli.getIsGunnerMode((Entity)player) ? "Normal" : "Camera") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt);
/*  84 */           drawString(msg, RX, this.centerY - 40, colorActive);
/*     */         } 
/*     */         
/*  87 */         if (seatID == 0 && !Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/*  88 */           int c = heli.getIsGunnerMode((Entity)player) ? colorInactive : colorActive;
/*  89 */           String msg = (heli.getIsGunnerMode((Entity)player) ? "Normal" : "Hovering") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchHovering.prmInt);
/*  90 */           drawString(msg, RX, this.centerY - 60, c);
/*     */         } 
/*     */         
/*  93 */         if (seatID == 0) {
/*  94 */           if (heli.getTowChainEntity() != null && !(heli.getTowChainEntity()).isDead) {
/*  95 */             String msg = "Drop  : " + MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt);
/*  96 */             drawString(msg, RX, this.centerY - 30, colorActive);
/*  97 */           } else if (info.isEnableFoldBlade && 
/*  98 */             MCH_Lib.getBlockIdY(heli.world, heli.posX, heli.posY, heli.posZ, 1, -2, true) > 0 && heli
/*  99 */             .getCurrentThrottle() <= 0.01D) {
/* 100 */             String msg = "FoldBlade  : " + MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt);
/* 101 */             drawString(msg, RX, this.centerY - 30, colorActive);
/*     */           } 
/*     */         }
/*     */         
/* 105 */         if ((heli.getIsGunnerMode((Entity)player) || heli.isUAV()) && info.cameraZoom > 1) {
/* 106 */           String msg = "Zoom : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/* 107 */           drawString(msg, LX, this.centerY - 80, colorActive);
/* 108 */         } else if (seatID == 0 && (heli.canFoldHatch() || heli.canUnfoldHatch())) {
/* 109 */           String msg = "OpenHatch : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/* 110 */           drawString(msg, LX, this.centerY - 80, colorActive);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helicopter\MCH_GuiHeli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */