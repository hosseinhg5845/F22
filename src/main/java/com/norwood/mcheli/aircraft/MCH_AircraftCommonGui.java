/*     */ package com.norwood.mcheli.aircraft;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_KeyName;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.gui.MCH_Gui;
/*     */ import com.norwood.mcheli.hud.MCH_Hud;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityTvMissile;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponSet;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public abstract class MCH_AircraftCommonGui extends MCH_Gui {
/*     */   public MCH_AircraftCommonGui(Minecraft minecraft) {
/*  21 */     super(minecraft);
/*     */   }
/*     */   
/*     */   public void drawHud(MCH_EntityAircraft aircraft, EntityPlayer player, int seatId) {
/*  25 */     MCH_AircraftInfo info = aircraft.getAcInfo();
/*  26 */     if (info == null)
/*     */       return; 
/*  28 */     if (aircraft.isMissileCameraMode((Entity)player) && aircraft.getTVMissile() != null && info.hudTvMissile != null) {
/*  29 */       info.hudTvMissile.draw(aircraft, player, this.smoothCamPartialTicks);
/*     */       
/*     */       return;
/*     */     } 
/*  33 */     if (seatId >= 0 && seatId < info.hudList.size()) {
/*  34 */       MCH_Hud hud = info.hudList.get(seatId);
/*  35 */       if (hud != null) {
/*  36 */         hud.draw(aircraft, player, this.smoothCamPartialTicks);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawDebugtInfo(MCH_EntityAircraft ac) {
/*  43 */     if (MCH_Config.DebugLog);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawNightVisionNoise() {
/*  48 */     GL11.glEnable(3042);
/*  49 */     GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.3F);
/*  50 */     int srcBlend = GL11.glGetInteger(3041);
/*  51 */     int dstBlend = GL11.glGetInteger(3040);
/*  52 */     GL11.glBlendFunc(1, 1);
/*  53 */     W_McClient.MOD_bindTexture("textures/gui/alpha.png");
/*  54 */     drawTexturedModalRectRotate(0.0D, 0.0D, this.field_146294_l, this.field_146295_m, this.rand.nextInt(256), this.rand.nextInt(256), 256.0D, 256.0D, 0.0F);
/*  55 */     GL11.glBlendFunc(srcBlend, dstBlend);
/*  56 */     GL11.glDisable(3042);
/*     */   }
/*     */   
/*     */   public void drawHitMarker(int hitStrength, int maxHitStrength, int baseColor) {
/*  60 */     if (hitStrength <= 0)
/*     */       return; 
/*  62 */     int centerX = this.centerX;
/*  63 */     int centerY = this.centerY;
/*  64 */     int outerOffset = 10;
/*  65 */     int innerOffset = 5;
/*     */ 
/*     */     
/*  68 */     double[] markerLines = { (centerX - 10), (centerY - 10), (centerX - 5), (centerY - 5), (centerX - 10), (centerY + 10), (centerX - 5), (centerY + 5), (centerX + 10), (centerY - 10), (centerX + 5), (centerY - 5), (centerX + 10), (centerY + 10), (centerX + 5), (centerY + 5) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     int alpha = hitStrength * 256 / maxHitStrength;
/*  83 */     int finalColor = (int)(MCH_Config.hitMarkColorAlpha * alpha) << 24 | MCH_Config.hitMarkColorRGB;
/*     */     
/*  85 */     drawLine(markerLines, finalColor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawHitMarker(MCH_EntityAircraft ac, int color, int seatID) {
/*  90 */     drawHitMarker(ac.getHitStatus(), ac.getMaxHitStatus(), color);
/*     */   }
/*     */   
/*     */   protected void drawTvMissileNoise(MCH_EntityAircraft ac, MCH_EntityTvMissile tvmissile) {
/*  94 */     GL11.glEnable(3042);
/*  95 */     GL11.glColor4f(0.5F, 0.5F, 0.5F, 0.4F);
/*  96 */     int srcBlend = GL11.glGetInteger(3041);
/*  97 */     int dstBlend = GL11.glGetInteger(3040);
/*  98 */     GL11.glBlendFunc(1, 1);
/*  99 */     W_McClient.MOD_bindTexture("textures/gui/noise.png");
/* 100 */     drawTexturedModalRectRotate(0.0D, 0.0D, this.field_146294_l, this.field_146295_m, this.rand.nextInt(256), this.rand.nextInt(256), 256.0D, 256.0D, 0.0F);
/* 101 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 102 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawKeyBind(MCH_EntityAircraft aircraft, MCH_AircraftInfo info, EntityPlayer player, int seatID, int rightX, int leftX, int colorActive, int colorInactive) {
/* 115 */     if (seatID == 0) {
/* 116 */       drawConditionalKeyBind(aircraft.canPutToRack(), "PutRack", MCH_Config.KeyPutToRack.prmInt, leftX, -10, colorActive);
/* 117 */       drawConditionalKeyBind(aircraft.canDownFromRack(), "DownRack", MCH_Config.KeyDownFromRack.prmInt, leftX, 0, colorActive);
/* 118 */       drawConditionalKeyBind(aircraft.canRideRack(), "RideRack", MCH_Config.KeyPutToRack.prmInt, leftX, 10, colorActive);
/* 119 */       drawConditionalKeyBind((aircraft.func_184187_bx() != null), "DismountRack", MCH_Config.KeyDownFromRack.prmInt, leftX, 10, colorActive);
/*     */     } 
/*     */     
/* 122 */     boolean multipleSeats = (aircraft.getSeatNum() > 1);
/* 123 */     boolean freeLookPressed = Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt);
/* 124 */     if ((seatID > 0 && multipleSeats) || freeLookPressed) {
/* 125 */       int seatColor = (seatID == 0) ? 65328 : colorActive;
/* 126 */       String prefix = (seatID == 0) ? (MCH_KeyName.getDescOrName(MCH_Config.KeyFreeLook.prmInt) + " + ") : "";
/* 127 */       drawString("NextSeat : " + prefix + MCH_KeyName.getDescOrName(MCH_Config.KeyGUI.prmInt), rightX, this.centerY - 70, seatColor);
/* 128 */       drawString("PrevSeat : " + prefix + MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt), rightX, this.centerY - 60, seatColor);
/*     */     } 
/*     */     
/* 131 */     drawString("Gunner " + (
/* 132 */         aircraft.getGunnerStatus() ? "ON" : "OFF") + " : " + 
/* 133 */         MCH_KeyName.getDescOrName(MCH_Config.KeyFreeLook.prmInt) + " + " + 
/* 134 */         MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt), leftX, this.centerY - 40, colorActive);
/*     */ 
/*     */ 
/*     */     
/* 138 */     if (seatID >= 0 && seatID <= 1 && aircraft.haveFlare()) {
/* 139 */       int flareColor = aircraft.isFlarePreparation() ? colorInactive : colorActive;
/* 140 */       drawString("Flare : " + MCH_KeyName.getDescOrName(MCH_Config.KeyFlare.prmInt), rightX, this.centerY - 50, flareColor);
/*     */     } 
/*     */     
/* 143 */     if (seatID == 0 && info.haveLandingGear()) {
/* 144 */       if (aircraft.canFoldLandingGear()) {
/* 145 */         drawConditionalKeyBind(true, "Gear Up", MCH_Config.KeyGearUpDown.prmInt, rightX, -40, colorActive);
/* 146 */       } else if (aircraft.canUnfoldLandingGear()) {
/* 147 */         drawConditionalKeyBind(true, "Gear Down", MCH_Config.KeyGearUpDown.prmInt, rightX, -40, colorActive);
/*     */       } 
/*     */     }
/*     */     
/* 151 */     MCH_WeaponSet weaponSet = aircraft.getCurrentWeapon((Entity)player);
/* 152 */     if (aircraft.getWeaponNum() > 1) {
/* 153 */       drawConditionalKeyBind(true, "Weapon", MCH_Config.KeySwitchWeapon2.prmInt, leftX, -70, colorActive);
/*     */     }
/*     */     
/* 156 */     if ((weaponSet.getCurrentWeapon()).numMode > 0) {
/* 157 */       drawConditionalKeyBind(true, "WeaponMode", MCH_Config.KeySwWeaponMode.prmInt, leftX, -60, colorActive);
/*     */     }
/*     */     
/* 160 */     if (aircraft.canSwitchSearchLight((Entity)player)) {
/* 161 */       drawConditionalKeyBind(true, "SearchLight", MCH_Config.KeyCameraMode.prmInt, leftX, -50, colorActive);
/* 162 */     } else if (aircraft.canSwitchCameraMode(seatID)) {
/* 163 */       drawConditionalKeyBind(true, "CameraMode", MCH_Config.KeyCameraMode.prmInt, leftX, -50, colorActive);
/*     */     } 
/*     */     
/* 166 */     if (seatID == 0 && aircraft.getSeatNum() >= 1) {
/* 167 */       int dismountColor = colorActive;
/* 168 */       String dismountLabel = "Dismount";
/* 169 */       if (info.isEnableParachuting && MCH_Lib.getBlockIdY((Entity)aircraft, 3, -10) == 0) {
/* 170 */         dismountLabel = "Parachuting";
/* 171 */       } else if (aircraft.canStartRepelling()) {
/* 172 */         dismountLabel = "Repelling";
/* 173 */         dismountColor = 65280;
/*     */       } 
/* 175 */       drawConditionalKeyBind(true, dismountLabel, MCH_Config.KeyUnmount.prmInt, leftX, -30, dismountColor);
/*     */     } 
/*     */ 
/*     */     
/* 179 */     boolean canFreeLook = ((seatID == 0 && aircraft.canSwitchFreeLook()) || (seatID > 0 && aircraft.canSwitchGunnerModeOtherSeat(player)));
/* 180 */     drawConditionalKeyBind(canFreeLook, "FreeLook", MCH_Config.KeyFreeLook.prmInt, leftX, -20, colorActive);
/*     */   }
/*     */   
/*     */   private void drawConditionalKeyBind(boolean condition, String label, int key, int x, int yOffset, int color) {
/* 184 */     if (condition) {
/* 185 */       String keyName = MCH_KeyName.getDescOrName(key);
/* 186 */       drawString(label + " : " + keyName, x, this.centerY + yOffset, color);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_AircraftCommonGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */