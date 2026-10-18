/*     */ package com.norwood.mcheli.helicopter;
import com.norwood.mcheli.wrapper.W_PacketBase;
import com.norwood.mcheli.aircraft.MCH_AircraftClientTickHandler;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Key;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_ViewEntityDummy;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */ import com.norwood.mcheli.aircraft.MCH_SeatInfo;
/*     */ import com.norwood.mcheli.uav.MCH_EntityUavStation;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_Network;
/*     */ import com.norwood.mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_ClientHeliTickHandler extends MCH_AircraftClientTickHandler {
/*     */   public MCH_Key KeySwitchMode;
/*     */   public MCH_Key KeySwitchHovering;
/*     */   
/*     */   public MCH_ClientHeliTickHandler(Minecraft minecraft, MCH_Config config) {
/*  24 */     super(minecraft, config);
/*  25 */     updateKeybind(config);
/*     */   }
/*     */   public MCH_Key KeyZoom; public MCH_Key[] Keys;
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  30 */     super.updateKeybind(config);
/*  31 */     this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
/*  32 */     this.KeySwitchHovering = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
/*  33 */     this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/*  34 */     this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeySwitchHovering, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyPutToRack, this.KeyDownFromRack };
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
/*     */   protected void update(EntityPlayer player, MCH_EntityHeli heli, boolean isPilot) {
/*  60 */     if (heli.getIsGunnerMode((Entity)player)) {
/*  61 */       MCH_SeatInfo seatInfo = heli.getSeatInfo((Entity)player);
/*  62 */       if (seatInfo != null) {
/*  63 */         setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player);
/*     */       }
/*     */     } 
/*     */     
/*  67 */     heli.updateCameraRotate(player.rotationYaw, player.rotationPitch);
/*  68 */     heli.updateRadar(5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onTick(boolean inGUI) {
/*  73 */     for (MCH_Key k : this.Keys) {
/*  74 */       k.update();
/*     */     }
/*     */     
/*  77 */     this.isBeforeRiding = this.isRiding;
/*  78 */     EntityPlayerSP entityPlayerSP = this.mc.player;
/*  79 */     MCH_EntityHeli heli = null;
/*  80 */     boolean isPilot = true;
/*  81 */     if (entityPlayerSP != null)
/*  82 */       if (entityPlayerSP.getRidingEntity() instanceof MCH_EntityHeli)
/*  83 */       { heli = (MCH_EntityHeli)entityPlayerSP.getRidingEntity(); }
/*  84 */       else { Entity entity = entityPlayerSP.getRidingEntity(); if (entity instanceof MCH_EntitySeat) { MCH_EntitySeat seat = (MCH_EntitySeat)entity;
/*  85 */           if (seat.getParent() instanceof MCH_EntityHeli) {
/*  86 */             isPilot = false;
/*  87 */             heli = (MCH_EntityHeli)seat.getParent();
/*     */           }  }
/*  89 */         else { entity = entityPlayerSP.getRidingEntity(); if (entity instanceof MCH_EntityUavStation) { MCH_EntityUavStation uavStation = (MCH_EntityUavStation)entity;
/*  90 */             if (uavStation.getControlAircract() instanceof MCH_EntityHeli) {
/*  91 */               heli = (MCH_EntityHeli)uavStation.getControlAircract();
/*     */             } }
/*     */            }
/*     */          }
/*     */        
/*  96 */     if (heli != null && heli.getAcInfo() != null) {
/*  97 */       update((EntityPlayer)entityPlayerSP, heli, isPilot);
/*  98 */       MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.world);
/*  99 */       viewEntityDummy.update(heli.camera);
/* 100 */       if (!inGUI) {
/* 101 */         if (!heli.isDestroyed()) {
/* 102 */           playerControl((EntityPlayer)entityPlayerSP, heli, isPilot);
/*     */         }
/*     */       } else {
/* 105 */         playerControlInGUI((EntityPlayer)entityPlayerSP, heli, isPilot);
/*     */       } 
/*     */       
/* 108 */       boolean hideHand = true;
/* 109 */       if ((!isPilot || !heli.isAlwaysCameraView()) && !heli.getIsGunnerMode((Entity)entityPlayerSP)) {
/* 110 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/* 111 */         if (!isPilot && heli.getCurrentWeaponID((Entity)entityPlayerSP) < 0) {
/* 112 */           hideHand = false;
/*     */         }
/*     */       } else {
/* 115 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
/*     */       } 
/*     */       
/* 118 */       if (hideHand) {
/* 119 */         MCH_Lib.disableFirstPersonItemRender(entityPlayerSP.getHeldItemMainhand());
/*     */       }
/*     */       
/* 122 */       this.isRiding = true;
/*     */     } else {
/* 124 */       this.isRiding = false;
/*     */     } 
/*     */     
/* 127 */     if (!this.isBeforeRiding && this.isRiding) {
/* 128 */       W_Reflection.setThirdPersonDistance(heli.thirdPersonDist);
/* 129 */     } else if (this.isBeforeRiding && !this.isRiding) {
/* 130 */       W_Reflection.restoreDefaultThirdPersonDistance();
/* 131 */       W_Reflection.setCameraRoll(0.0F);
/* 132 */       MCH_Lib.enableFirstPersonItemRender();
/* 133 */       MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void playerControlInGUI(EntityPlayer player, MCH_EntityHeli heli, boolean isPilot) {
/* 138 */     commonPlayerControlInGUI(player, heli, isPilot, new MCH_HeliPacketPlayerControl());
/*     */   }
/*     */   
/*     */   protected void playerControl(EntityPlayer player, MCH_EntityHeli heli, boolean isPilot) {
/* 142 */     MCH_HeliPacketPlayerControl pc = new MCH_HeliPacketPlayerControl();
/*     */     
/* 144 */     boolean send = commonPlayerControl(player, heli, isPilot, pc);
/* 145 */     if (isPilot) {
/* 146 */       if (this.KeyExtra.isKeyDown()) {
/* 147 */         if (heli.getTowChainEntity() != null) {
/* 148 */           playSoundOK();
/* 149 */           pc.unhitchChainId = W_Entity.getEntityId((Entity)heli.getTowChainEntity());
/* 150 */           send = true;
/* 151 */         } else if (heli.canSwitchFoldBlades()) {
/* 152 */           if (heli.isFoldBlades()) {
/* 153 */             heli.unfoldBlades();
/* 154 */             pc.switchFold = 0;
/*     */           } else {
/* 156 */             heli.foldBlades();
/* 157 */             pc.switchFold = 1;
/*     */           } 
/*     */           
/* 160 */           send = true;
/* 161 */           playSoundOK();
/*     */         } else {
/* 163 */           playSoundNG();
/*     */         } 
/*     */       }
/*     */       
/* 167 */       if (this.KeySwitchHovering.isKeyDown()) {
/* 168 */         if (heli.canSwitchHoveringMode()) {
/* 169 */           pc.switchMode = (byte)(heli.isHoveringMode() ? 2 : 3);
/* 170 */           heli.switchHoveringMode(!heli.isHoveringMode());
/* 171 */           send = true;
/*     */         } else {
/* 173 */           playSoundNG();
/*     */         } 
/* 175 */       } else if (this.KeySwitchMode.isKeyDown()) {
/* 176 */         if (heli.canSwitchGunnerMode()) {
/* 177 */           pc.switchMode = (byte)(heli.getIsGunnerMode((Entity)player) ? 0 : 1);
/* 178 */           heli.switchGunnerMode(!heli.getIsGunnerMode((Entity)player));
/* 179 */           send = true;
/*     */         } else {
/* 181 */           playSoundNG();
/*     */         } 
/*     */       } 
/* 184 */     } else if (this.KeySwitchMode.isKeyDown()) {
/* 185 */       if (heli.canSwitchGunnerModeOtherSeat(player)) {
/* 186 */         heli.switchGunnerModeOtherSeat(player);
/* 187 */         send = true;
/*     */       } else {
/* 189 */         playSoundNG();
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     if (this.KeyZoom.isKeyDown()) {
/* 194 */       boolean isUav = (heli.isUAV() && !heli.getAcInfo().haveHatch());
/* 195 */       if (heli.getIsGunnerMode((Entity)player) || isUav) {
/* 196 */         heli.zoomCamera();
/* 197 */         playSound("zoom", 0.5F, 1.0F);
/* 198 */       } else if (isPilot && heli.getAcInfo().haveHatch()) {
/* 199 */         if (heli.canFoldHatch()) {
/* 200 */           pc.switchHatch = 2;
/* 201 */           send = true;
/* 202 */         } else if (heli.canUnfoldHatch()) {
/* 203 */           pc.switchHatch = 1;
/* 204 */           send = true;
/*     */         } else {
/* 206 */           playSoundNG();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     if (send)
/* 212 */       W_Network.sendToServer((W_PacketBase)pc); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helicopter\MCH_ClientHeliTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */