/*     */ package com.norwood.mcheli.tank;
import com.norwood.mcheli.wrapper.W_PacketBase;
import com.norwood.mcheli.aircraft.MCH_AircraftClientTickHandler;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Key;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_ViewEntityDummy;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */ import com.norwood.mcheli.aircraft.MCH_SeatInfo;
/*     */ import com.norwood.mcheli.uav.MCH_EntityUavStation;
/*     */ import com.norwood.mcheli.wrapper.W_Network;
/*     */ import com.norwood.mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_ClientTankTickHandler extends MCH_AircraftClientTickHandler {
/*     */   public MCH_Key KeySwitchMode;
/*     */   
/*     */   public MCH_ClientTankTickHandler(Minecraft minecraft, MCH_Config config) {
/*  22 */     super(minecraft, config);
/*  23 */     updateKeybind(config);
/*     */   }
/*     */   public MCH_Key KeyZoom; public MCH_Key[] Keys;
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  28 */     super.updateKeybind(config);
/*  29 */     this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
/*  30 */     this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/*  31 */     this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyBrake, this.KeyPutToRack, this.KeyDownFromRack };
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
/*     */   protected void update(EntityPlayer player, MCH_EntityTank tank) {
/*  57 */     if (tank.getIsGunnerMode((Entity)player)) {
/*  58 */       MCH_SeatInfo seatInfo = tank.getSeatInfo((Entity)player);
/*  59 */       if (seatInfo != null) {
/*  60 */         setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player);
/*     */       }
/*     */     } 
/*     */     
/*  64 */     tank.updateRadar(10);
/*  65 */     tank.updateCameraRotate(player.rotationYaw, player.rotationPitch);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onTick(boolean inGUI) {
/*  70 */     for (MCH_Key k : this.Keys) {
/*  71 */       k.update();
/*     */     }
/*     */     
/*  74 */     this.isBeforeRiding = this.isRiding;
/*  75 */     EntityPlayerSP entityPlayerSP = this.mc.player;
/*  76 */     MCH_EntityTank tank = null;
/*  77 */     boolean isPilot = true;
/*  78 */     if (entityPlayerSP != null)
/*  79 */       if (entityPlayerSP.getRidingEntity() instanceof MCH_EntityTank)
/*  80 */       { tank = (MCH_EntityTank)entityPlayerSP.getRidingEntity(); }
/*  81 */       else { Entity entity = entityPlayerSP.getRidingEntity(); if (entity instanceof MCH_EntitySeat) { MCH_EntitySeat seat = (MCH_EntitySeat)entity;
/*  82 */           if (seat.getParent() instanceof MCH_EntityTank) {
/*  83 */             isPilot = false;
/*  84 */             tank = (MCH_EntityTank)seat.getParent();
/*     */           }  }
/*  86 */         else { entity = entityPlayerSP.getRidingEntity(); if (entity instanceof MCH_EntityUavStation) { MCH_EntityUavStation uavStation = (MCH_EntityUavStation)entity;
/*  87 */             if (uavStation.getControlAircract() instanceof MCH_EntityTank) {
/*  88 */               tank = (MCH_EntityTank)uavStation.getControlAircract();
/*     */             } }
/*     */            }
/*     */          }
/*     */        
/*  93 */     if (tank != null && tank.getAcInfo() != null) {
/*  94 */       update((EntityPlayer)entityPlayerSP, tank);
/*  95 */       MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.world);
/*  96 */       viewEntityDummy.update(tank.camera);
/*  97 */       if (!inGUI) {
/*  98 */         if (!tank.isDestroyed()) {
/*  99 */           playerControl((EntityPlayer)entityPlayerSP, tank, isPilot);
/*     */         }
/*     */       } else {
/* 102 */         playerControlInGUI((EntityPlayer)entityPlayerSP, tank, isPilot);
/*     */       } 
/*     */       
/* 105 */       boolean hideHand = true;
/* 106 */       if ((!isPilot || !tank.isAlwaysCameraView()) && !tank.getIsGunnerMode((Entity)entityPlayerSP) && tank.getCameraId() <= 0) {
/* 107 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/* 108 */         if (!isPilot && tank.getCurrentWeaponID((Entity)entityPlayerSP) < 0) {
/* 109 */           hideHand = false;
/*     */         }
/*     */       } else {
/* 112 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
/*     */       } 
/*     */       
/* 115 */       if (hideHand) {
/* 116 */         MCH_Lib.disableFirstPersonItemRender(entityPlayerSP.getHeldItemMainhand());
/*     */       }
/*     */       
/* 119 */       this.isRiding = true;
/*     */     } else {
/* 121 */       this.isRiding = false;
/*     */     } 
/*     */     
/* 124 */     if (!this.isBeforeRiding && this.isRiding) {
/* 125 */       W_Reflection.setThirdPersonDistance(tank.thirdPersonDist);
/* 126 */       MCH_ViewEntityDummy.getInstance((World)this.mc.world).setPosition(tank.posX, tank.posY + 0.5D, tank.posZ);
/* 127 */     } else if (this.isBeforeRiding && !this.isRiding) {
/* 128 */       W_Reflection.restoreDefaultThirdPersonDistance();
/* 129 */       MCH_Lib.enableFirstPersonItemRender();
/* 130 */       MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/* 131 */       W_Reflection.setCameraRoll(0.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void playerControlInGUI(EntityPlayer player, MCH_EntityTank tank, boolean isPilot) {
/* 136 */     commonPlayerControlInGUI(player, tank, isPilot, new MCH_TankPacketPlayerControl());
/*     */   }
/*     */   
/*     */   protected void playerControl(EntityPlayer player, MCH_EntityTank tank, boolean isPilot) {
/* 140 */     MCH_TankPacketPlayerControl pc = new MCH_TankPacketPlayerControl();
/*     */     
/* 142 */     boolean send = commonPlayerControl(player, tank, isPilot, pc);
/* 143 */     if ((tank.getAcInfo()).defaultFreelook && pc.switchFreeLook > 0) {
/* 144 */       pc.switchFreeLook = 0;
/*     */     }
/*     */     
/* 147 */     if (isPilot) {
/* 148 */       if (this.KeySwitchMode.isKeyDown()) {
/* 149 */         if (tank.getIsGunnerMode((Entity)player) && tank.canSwitchCameraPos()) {
/* 150 */           pc.switchMode = 0;
/* 151 */           tank.switchGunnerMode(false);
/* 152 */           send = true;
/* 153 */           tank.setCameraId(1);
/* 154 */         } else if (tank.getCameraId() > 0) {
/* 155 */           tank.setCameraId(tank.getCameraId() + 1);
/* 156 */           if (tank.getCameraId() >= tank.getCameraPosNum()) {
/* 157 */             tank.setCameraId(0);
/*     */           }
/* 159 */         } else if (tank.canSwitchGunnerMode()) {
/* 160 */           pc.switchMode = (byte)(tank.getIsGunnerMode((Entity)player) ? 0 : 1);
/* 161 */           tank.switchGunnerMode(!tank.getIsGunnerMode((Entity)player));
/* 162 */           send = true;
/* 163 */           tank.setCameraId(0);
/* 164 */         } else if (tank.canSwitchCameraPos()) {
/* 165 */           tank.setCameraId(1);
/*     */         } else {
/* 167 */           playSoundNG();
/*     */         } 
/*     */       }
/* 170 */     } else if (this.KeySwitchMode.isKeyDown()) {
/* 171 */       if (tank.canSwitchGunnerModeOtherSeat(player)) {
/* 172 */         tank.switchGunnerModeOtherSeat(player);
/* 173 */         send = true;
/*     */       } else {
/* 175 */         playSoundNG();
/*     */       } 
/*     */     } 
/*     */     
/* 179 */     if (this.KeyZoom.isKeyDown()) {
/* 180 */       boolean isUav = (tank.isUAV() && !tank.getAcInfo().haveHatch());
/* 181 */       if (tank.getIsGunnerMode((Entity)player) || isUav) {
/* 182 */         tank.zoomCamera();
/* 183 */         playSound("zoom", 0.5F, 1.0F);
/* 184 */       } else if (isPilot && tank.getAcInfo().haveHatch()) {
/* 185 */         if (tank.canFoldHatch()) {
/* 186 */           pc.switchHatch = 2;
/* 187 */           send = true;
/* 188 */         } else if (tank.canUnfoldHatch()) {
/* 189 */           pc.switchHatch = 1;
/* 190 */           send = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 195 */     if (send)
/* 196 */       W_Network.sendToServer((W_PacketBase)pc); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tank\MCH_ClientTankTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */