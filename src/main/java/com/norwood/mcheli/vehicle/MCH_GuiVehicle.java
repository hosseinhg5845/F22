/*     */ package com.norwood.mcheli.vehicle;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_KeyName;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftCommonGui;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponSet;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiVehicle extends MCH_AircraftCommonGui {
/*     */   static final int COLOR1 = -14066;
/*     */   
/*     */   public MCH_GuiVehicle(Minecraft minecraft) {
/*  19 */     super(minecraft);
/*     */   }
/*     */   static final int COLOR2 = -2161656;
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  24 */     return (player.getRidingEntity() != null && player.getRidingEntity() instanceof MCH_EntityVehicle);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  29 */     if (player.getRidingEntity() != null) { Entity entity = player.getRidingEntity(); if (entity instanceof MCH_EntityVehicle) { MCH_EntityVehicle vehicle = (MCH_EntityVehicle)entity;
/*  30 */         if (!vehicle.isDestroyed()) {
/*  31 */           int seatID = vehicle.getSeatIdByEntity((Entity)player);
/*  32 */           GL11.glLineWidth(scaleFactor);
/*  33 */           if (vehicle.getCameraMode(player) == 1) {
/*  34 */             drawNightVisionNoise();
/*     */           }
/*     */           
/*  37 */           if (vehicle.getIsGunnerMode((Entity)player) && vehicle.getTVMissile() != null) {
/*  38 */             drawTvMissileNoise(vehicle, vehicle.getTVMissile());
/*     */           }
/*     */           
/*  41 */           drawDebugtInfo(vehicle);
/*  42 */           if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool) {
/*  43 */             drawHud(vehicle, player, seatID);
/*  44 */             drawKeyBind(vehicle, player);
/*     */           } 
/*     */           
/*  47 */           drawHitMarker(vehicle, 51470, seatID);
/*     */         }  }
/*     */        }
/*     */   
/*     */   }
/*     */   public void drawKeyBind(MCH_EntityVehicle vehicle, EntityPlayer player) {
/*  53 */     if (!MCH_Config.HideKeybind.prmBool) {
/*  54 */       MCH_VehicleInfo info = vehicle.getVehicleInfo();
/*  55 */       if (info != null) {
/*  56 */         int colorActive = -1342177281;
/*  57 */         int colorInactive = -1349546097;
/*  58 */         int RX = this.centerX + 120;
/*  59 */         int LX = this.centerX - 200;
/*  60 */         if (vehicle.haveFlare()) {
/*  61 */           int c = vehicle.isFlarePreparation() ? colorInactive : colorActive;
/*  62 */           String str = "Flare : " + MCH_KeyName.getDescOrName(MCH_Config.KeyFlare.prmInt);
/*  63 */           drawString(str, RX, this.centerY - 50, c);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  71 */         String msg = "Gunner " + (vehicle.getGunnerStatus() ? "ON" : "OFF") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeyFreeLook.prmInt) + " + " + MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt);
/*  72 */         drawString(msg, LX, this.centerY - 40, colorActive);
/*  73 */         if (vehicle.getSizeInventory() <= 0 || (vehicle.getTowChainEntity() != null && !(vehicle.getTowChainEntity()).isDead)) {
/*  74 */           msg = "Drop  : " + MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt);
/*  75 */           drawString(msg, RX, this.centerY - 30, colorActive);
/*     */         } 
/*     */         
/*  78 */         if (vehicle.camera.getCameraZoom() > 1.0F) {
/*  79 */           msg = "Zoom : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/*  80 */           drawString(msg, LX, this.centerY - 80, colorActive);
/*     */         } 
/*     */         
/*  83 */         MCH_WeaponSet ws = vehicle.getCurrentWeapon((Entity)player);
/*  84 */         if (vehicle.getWeaponNum() > 1) {
/*  85 */           msg = "Weapon : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchWeapon2.prmInt);
/*  86 */           drawString(msg, LX, this.centerY - 70, colorActive);
/*     */         } 
/*     */         
/*  89 */         if ((ws.getCurrentWeapon()).numMode > 0) {
/*  90 */           msg = "WeaponMode : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt);
/*  91 */           drawString(msg, LX, this.centerY - 60, colorActive);
/*     */         } 
/*     */         
/*  94 */         if (info.isEnableNightVision) {
/*  95 */           msg = "CameraMode : " + MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt);
/*  96 */           drawString(msg, LX, this.centerY - 50, colorActive);
/*     */         } 
/*     */         
/*  99 */         msg = "Dismount all : LShift";
/* 100 */         drawString(msg, LX, this.centerY - 30, colorActive);
/* 101 */         if (vehicle.getSeatNum() >= 2) {
/* 102 */           msg = "Dismount : " + MCH_KeyName.getDescOrName(MCH_Config.KeyUnmount.prmInt);
/* 103 */           drawString(msg, LX, this.centerY - 40, colorActive);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\vehicle\MCH_GuiVehicle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */