/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_ClientTickHandlerBase;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Key;
/*     */ import com.norwood.mcheli.MCH_PacketIndOpenScreen;
/*     */ import com.norwood.mcheli.wrapper.W_Network;
/*     */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MCH_AircraftClientTickHandler
/*     */   extends MCH_ClientTickHandlerBase
/*     */ {
/*     */   public MCH_Key KeyUp;
/*     */   public MCH_Key KeyDown;
/*     */   public MCH_Key KeyRight;
/*     */   public MCH_Key KeyLeft;
/*     */   public MCH_Key KeyUseWeapon;
/*     */   public MCH_Key KeySwitchWeapon1;
/*     */   public MCH_Key KeySwitchWeapon2;
/*     */   public MCH_Key KeySwWeaponMode;
/*     */   public MCH_Key KeyUnmount;
/*     */   public MCH_Key KeyUnmountForce;
/*     */   
/*     */   public MCH_AircraftClientTickHandler(Minecraft minecraft, MCH_Config config) {
/*  36 */     super(minecraft);
/*  37 */     updateKeybind(config);
/*     */   }
/*     */   public MCH_Key KeyExtra; public MCH_Key KeyFlare; public MCH_Key KeyCameraMode; public MCH_Key KeyFreeLook; public MCH_Key KeyGUI; public MCH_Key KeyGearUpDown; public MCH_Key KeyPutToRack; public MCH_Key KeyDownFromRack; public MCH_Key KeyBrake; protected boolean isRiding = false; protected boolean isBeforeRiding = false;
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  42 */     this.KeyUp = new MCH_Key(MCH_Config.KeyUp.prmInt);
/*  43 */     this.KeyDown = new MCH_Key(MCH_Config.KeyDown.prmInt);
/*  44 */     this.KeyRight = new MCH_Key(MCH_Config.KeyRight.prmInt);
/*  45 */     this.KeyLeft = new MCH_Key(MCH_Config.KeyLeft.prmInt);
/*  46 */     this.KeyUseWeapon = new MCH_Key(MCH_Config.KeyUseWeapon.prmInt);
/*  47 */     this.KeySwitchWeapon1 = new MCH_Key(MCH_Config.KeySwitchWeapon1.prmInt);
/*  48 */     this.KeySwitchWeapon2 = new MCH_Key(MCH_Config.KeySwitchWeapon2.prmInt);
/*  49 */     this.KeySwWeaponMode = new MCH_Key(MCH_Config.KeySwWeaponMode.prmInt);
/*  50 */     this.KeyUnmount = new MCH_Key(MCH_Config.KeyUnmount.prmInt);
/*  51 */     this.KeyUnmountForce = new MCH_Key(42);
/*  52 */     this.KeyExtra = new MCH_Key(MCH_Config.KeyExtra.prmInt);
/*  53 */     this.KeyFlare = new MCH_Key(MCH_Config.KeyFlare.prmInt);
/*  54 */     this.KeyCameraMode = new MCH_Key(MCH_Config.KeyCameraMode.prmInt);
/*  55 */     this.KeyFreeLook = new MCH_Key(MCH_Config.KeyFreeLook.prmInt);
/*  56 */     this.KeyGUI = new MCH_Key(MCH_Config.KeyGUI.prmInt);
/*  57 */     this.KeyGearUpDown = new MCH_Key(MCH_Config.KeyGearUpDown.prmInt);
/*  58 */     this.KeyPutToRack = new MCH_Key(MCH_Config.KeyPutToRack.prmInt);
/*  59 */     this.KeyDownFromRack = new MCH_Key(MCH_Config.KeyDownFromRack.prmInt);
/*  60 */     this.KeyBrake = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void commonPlayerControlInGUI(EntityPlayer player, MCH_EntityAircraft ac, boolean isPilot, MCH_PacketPlayerControlBase pc) {}
/*     */   
/*     */   public boolean commonPlayerControl(EntityPlayer player, MCH_EntityAircraft ac, boolean isPilot, MCH_PacketPlayerControlBase pc) {
/*  67 */     if (Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/*  68 */       if (this.KeyGUI.isKeyDown() || this.KeyExtra.isKeyDown()) {
/*  69 */         MCH_PacketSeatPlayerControl psc = new MCH_PacketSeatPlayerControl();
/*  70 */         if (isPilot) {
/*  71 */           psc.switchSeat = (byte)(this.KeyGUI.isKeyDown() ? 1 : 2);
/*     */         } else {
/*  73 */           ac.keepOnRideRotation = true;
/*  74 */           psc.switchSeat = 3;
/*     */         } 
/*     */         
/*  77 */         W_Network.sendToServer((W_PacketBase)psc);
/*  78 */         return false;
/*     */       } 
/*  80 */     } else if (!isPilot && ac.getSeatNum() > 1) {
/*  81 */       MCH_PacketSeatPlayerControl psc = new MCH_PacketSeatPlayerControl();
/*  82 */       if (this.KeyGUI.isKeyDown()) {
/*  83 */         psc.switchSeat = 1;
/*  84 */         W_Network.sendToServer((W_PacketBase)psc);
/*  85 */         return false;
/*     */       } 
/*     */       
/*  88 */       if (this.KeyExtra.isKeyDown()) {
/*  89 */         psc.switchSeat = 2;
/*  90 */         W_Network.sendToServer((W_PacketBase)psc);
/*  91 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     boolean send = false;
/*  96 */     if (Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/*  97 */       if (this.KeyCameraMode.isKeyDown()) {
/*  98 */         pc.switchGunnerStatus = true;
/*  99 */         playSoundOK();
/* 100 */         send = true;
/*     */       } 
/* 102 */     } else if (this.KeyCameraMode.isKeyDown()) {
/* 103 */       if (ac.haveSearchLight()) {
/* 104 */         if (ac.canSwitchSearchLight((Entity)player)) {
/* 105 */           pc.switchSearchLight = true;
/* 106 */           playSoundOK();
/* 107 */           send = true;
/*     */         } 
/* 109 */       } else if (ac.canSwitchCameraMode()) {
/* 110 */         int beforeMode = ac.getCameraMode(player);
/* 111 */         ac.switchCameraMode(player);
/* 112 */         int mode = ac.getCameraMode(player);
/* 113 */         if (mode != beforeMode) {
/* 114 */           pc.switchCameraMode = (byte)(mode + 1);
/* 115 */           playSoundOK();
/* 116 */           send = true;
/*     */         } 
/*     */       } else {
/* 119 */         playSoundNG();
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     if (this.KeyUnmount.isKeyDown() && !ac.isDestroyed() && ac.func_70302_i_() > 0 && !isPilot) {
/* 124 */       MCH_PacketIndOpenScreen.send(3);
/*     */     }
/*     */     
/* 127 */     if (isPilot) {
/* 128 */       if (this.KeyUnmount.isKeyDown()) {
/* 129 */         pc.isUnmount = 2;
/* 130 */         send = true;
/*     */       } 
/*     */       
/* 133 */       if (this.KeyPutToRack.isKeyDown()) {
/* 134 */         ac.checkRideRack();
/* 135 */         if (ac.canRideRack()) {
/* 136 */           pc.putDownRack = 3;
/* 137 */           send = true;
/* 138 */         } else if (ac.canPutToRack()) {
/* 139 */           pc.putDownRack = 1;
/* 140 */           send = true;
/*     */         } 
/* 142 */       } else if (this.KeyDownFromRack.isKeyDown()) {
/* 143 */         if (ac.func_184187_bx() != null) {
/* 144 */           pc.isUnmount = 3;
/* 145 */           send = true;
/* 146 */         } else if (ac.canDownFromRack()) {
/* 147 */           pc.putDownRack = 2;
/* 148 */           send = true;
/*     */         } 
/*     */       } 
/*     */       
/* 152 */       if (this.KeyGearUpDown.isKeyDown() && ac.getAcInfo().haveLandingGear()) {
/* 153 */         if (ac.canFoldLandingGear()) {
/* 154 */           pc.switchGear = 1;
/* 155 */           send = true;
/* 156 */         } else if (ac.canUnfoldLandingGear()) {
/* 157 */           pc.switchGear = 2;
/* 158 */           send = true;
/*     */         } 
/*     */       }
/*     */       
/* 162 */       if (this.KeyFreeLook.isKeyDown() && ac.canSwitchFreeLook()) {
/* 163 */         pc.switchFreeLook = (byte)(ac.isFreeLookMode() ? 2 : 1);
/* 164 */         send = true;
/*     */       } 
/*     */       
/* 167 */       if (this.KeyGUI.isKeyDown()) {
/* 168 */         pc.openGui = true;
/* 169 */         send = true;
/*     */       } 
/*     */       
/* 172 */       if (ac.isRepelling()) {
/* 173 */         pc.throttleDown = ac.throttleDown = false;
/* 174 */         pc.throttleUp = ac.throttleUp = false;
/* 175 */         pc.moveRight = ac.moveRight = false;
/* 176 */         pc.moveLeft = ac.moveLeft = false;
/* 177 */       } else if (ac.hasBrake() && this.KeyBrake.isKeyPress()) {
/* 178 */         send |= this.KeyBrake.isKeyDown();
/* 179 */         pc.throttleDown = ac.throttleDown = false;
/* 180 */         pc.throttleUp = ac.throttleUp = false;
/* 181 */         double dx = ac.field_70165_t - ac.field_70169_q;
/* 182 */         double dz = ac.field_70161_v - ac.field_70166_s;
/* 183 */         double dist = dx * dx + dz * dz;
/* 184 */         if (ac.getCurrentThrottle() <= 0.03D && dist < 0.01D) {
/* 185 */           pc.moveRight = ac.moveRight = false;
/* 186 */           pc.moveLeft = ac.moveLeft = false;
/*     */         } 
/*     */         
/* 189 */         pc.useBrake = true;
/*     */       } else {
/* 191 */         send |= this.KeyBrake.isKeyUp();
/* 192 */         MCH_Key[] dKey = { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft };
/*     */         
/* 194 */         for (MCH_Key k : dKey) {
/* 195 */           if (k.isKeyDown() || k.isKeyUp()) {
/* 196 */             send = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 201 */         pc.throttleDown = ac.throttleDown = this.KeyDown.isKeyPress();
/* 202 */         pc.throttleUp = ac.throttleUp = this.KeyUp.isKeyPress();
/* 203 */         pc.moveRight = ac.moveRight = this.KeyRight.isKeyPress();
/* 204 */         pc.moveLeft = ac.moveLeft = this.KeyLeft.isKeyPress();
/*     */       } 
/*     */     } 
/*     */     
/* 208 */     if (!ac.isDestroyed() && this.KeyFlare.isKeyDown() && ac.getSeatIdByEntity((Entity)player) <= 1) {
/* 209 */       if (ac.canUseFlare() && ac.useFlare(ac.getCurrentFlareType())) {
/* 210 */         pc.useFlareType = (byte)ac.getCurrentFlareType();
/* 211 */         ac.nextFlareType();
/* 212 */         send = true;
/*     */       } else {
/* 214 */         playSoundNG();
/*     */       } 
/*     */     }
/*     */     
/* 218 */     if (!ac.isDestroyed() && !ac.isPilotReloading()) {
/* 219 */       if (!this.KeySwitchWeapon1.isKeyDown() && !this.KeySwitchWeapon2.isKeyDown() && getMouseWheel() == 0) {
/* 220 */         if (this.KeySwWeaponMode.isKeyDown()) {
/* 221 */           ac.switchCurrentWeaponMode((Entity)player);
/* 222 */         } else if (this.KeyUseWeapon.isKeyPress() && ac.useCurrentWeapon((Entity)player)) {
/* 223 */           pc.useWeapon = true;
/* 224 */           pc.useWeaponOption1 = ac.getCurrentWeapon((Entity)player).getLastUsedOptionParameter1();
/* 225 */           pc.useWeaponOption2 = ac.getCurrentWeapon((Entity)player).getLastUsedOptionParameter2();
/* 226 */           pc.useWeaponPosX = ac.field_70169_q;
/* 227 */           pc.useWeaponPosY = ac.field_70167_r;
/* 228 */           pc.useWeaponPosZ = ac.field_70166_s;
/* 229 */           send = true;
/*     */         } 
/*     */       } else {
/* 232 */         if (getMouseWheel() > 0) {
/* 233 */           pc.switchWeapon = (byte)ac.getNextWeaponID((Entity)player, -1);
/*     */         } else {
/* 235 */           pc.switchWeapon = (byte)ac.getNextWeaponID((Entity)player, 1);
/*     */         } 
/*     */         
/* 238 */         setMouseWheel(0);
/* 239 */         ac.switchWeapon((Entity)player, pc.switchWeapon);
/* 240 */         send = true;
/*     */       } 
/*     */     }
/*     */     
/* 244 */     return (send || player.field_70173_aa % 100 == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_AircraftClientTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */