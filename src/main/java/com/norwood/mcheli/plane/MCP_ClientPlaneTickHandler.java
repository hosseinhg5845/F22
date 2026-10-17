/*     */ package com.norwood.mcheli.plane;
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
/*     */ public class MCP_ClientPlaneTickHandler extends MCH_AircraftClientTickHandler {
/*     */   public MCH_Key KeySwitchMode;
/*     */   public MCH_Key KeyEjectSeat;
/*     */   
/*     */   public MCP_ClientPlaneTickHandler(Minecraft minecraft, MCH_Config config) {
/*  23 */     super(minecraft, config);
/*  24 */     updateKeybind(config);
/*     */   }
/*     */   public MCH_Key KeyZoom; public MCH_Key[] Keys;
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  29 */     super.updateKeybind(config);
/*  30 */     this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
/*  31 */     this.KeyEjectSeat = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
/*  32 */     this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/*  33 */     this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeyEjectSeat, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyPutToRack, this.KeyDownFromRack };
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
/*     */   protected void update(EntityPlayer player, MCP_EntityPlane plane) {
/*  59 */     if (plane.getIsGunnerMode((Entity)player)) {
/*  60 */       MCH_SeatInfo seatInfo = plane.getSeatInfo((Entity)player);
/*  61 */       if (seatInfo != null) {
/*  62 */         setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player);
/*     */       }
/*     */     } 
/*     */     
/*  66 */     plane.updateRadar(10);
/*  67 */     plane.updateCameraRotate(player.field_70177_z, player.field_70125_A);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onTick(boolean inGUI) {
/*  72 */     for (MCH_Key k : this.Keys) {
/*  73 */       k.update();
/*     */     }
/*     */     
/*  76 */     this.isBeforeRiding = this.isRiding;
/*  77 */     EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
/*  78 */     MCP_EntityPlane plane = null;
/*  79 */     boolean isPilot = true;
/*  80 */     if (entityPlayerSP != null)
/*  81 */       if (entityPlayerSP.func_184187_bx() instanceof MCP_EntityPlane)
/*  82 */       { plane = (MCP_EntityPlane)entityPlayerSP.func_184187_bx(); }
/*  83 */       else { Entity entity = entityPlayerSP.func_184187_bx(); if (entity instanceof MCH_EntitySeat) { MCH_EntitySeat seat = (MCH_EntitySeat)entity;
/*  84 */           if (seat.getParent() instanceof MCP_EntityPlane) {
/*  85 */             isPilot = false;
/*  86 */             plane = (MCP_EntityPlane)seat.getParent();
/*     */           }  }
/*  88 */         else { entity = entityPlayerSP.func_184187_bx(); if (entity instanceof MCH_EntityUavStation) { MCH_EntityUavStation uavStation = (MCH_EntityUavStation)entity;
/*  89 */             if (uavStation.getControlAircract() instanceof MCP_EntityPlane) {
/*  90 */               plane = (MCP_EntityPlane)uavStation.getControlAircract();
/*     */             } }
/*     */            }
/*     */          }
/*     */        
/*  95 */     if (plane != null && plane.getAcInfo() != null) {
/*  96 */       update((EntityPlayer)entityPlayerSP, plane);
/*  97 */       MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
/*  98 */       viewEntityDummy.update(plane.camera);
/*  99 */       if (!inGUI) {
/* 100 */         if (!plane.isDestroyed()) {
/* 101 */           playerControl((EntityPlayer)entityPlayerSP, plane, isPilot);
/*     */         }
/*     */       } else {
/* 104 */         playerControlInGUI((EntityPlayer)entityPlayerSP, plane, isPilot);
/*     */       } 
/*     */       
/* 107 */       boolean hideHand = true;
/* 108 */       if ((!isPilot || !plane.isAlwaysCameraView()) && !plane.getIsGunnerMode((Entity)entityPlayerSP) && plane.getCameraId() <= 0) {
/* 109 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/* 110 */         if (!isPilot && plane.getCurrentWeaponID((Entity)entityPlayerSP) < 0) {
/* 111 */           hideHand = false;
/*     */         }
/*     */       } else {
/* 114 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
/*     */       } 
/*     */       
/* 117 */       if (hideHand) {
/* 118 */         MCH_Lib.disableFirstPersonItemRender(entityPlayerSP.func_184614_ca());
/*     */       }
/*     */       
/* 121 */       this.isRiding = true;
/*     */     } else {
/* 123 */       this.isRiding = false;
/*     */     } 
/*     */     
/* 126 */     if (!this.isBeforeRiding && this.isRiding) {
/* 127 */       W_Reflection.setThirdPersonDistance(plane.thirdPersonDist);
/* 128 */       MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e).func_70107_b(plane.field_70165_t, plane.field_70163_u + 0.5D, plane.field_70161_v);
/* 129 */     } else if (this.isBeforeRiding && !this.isRiding) {
/* 130 */       W_Reflection.restoreDefaultThirdPersonDistance();
/* 131 */       MCH_Lib.enableFirstPersonItemRender();
/* 132 */       MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/* 133 */       W_Reflection.setCameraRoll(0.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void playerControlInGUI(EntityPlayer player, MCP_EntityPlane plane, boolean isPilot) {
/* 138 */     commonPlayerControlInGUI(player, plane, isPilot, new MCP_PlanePacketPlayerControl());
/*     */   }
/*     */   
/*     */   protected void playerControl(EntityPlayer player, MCP_EntityPlane plane, boolean isPilot) {
/* 142 */     MCP_PlanePacketPlayerControl pc = new MCP_PlanePacketPlayerControl();
/*     */     
/* 144 */     boolean send = commonPlayerControl(player, plane, isPilot, pc);
/* 145 */     if (isPilot) {
/* 146 */       if (this.KeySwitchMode.isKeyDown()) {
/* 147 */         if (plane.getIsGunnerMode((Entity)player) && plane.canSwitchCameraPos()) {
/* 148 */           pc.switchMode = 0;
/* 149 */           plane.switchGunnerMode(false);
/* 150 */           send = true;
/* 151 */           plane.setCameraId(1);
/* 152 */         } else if (plane.getCameraId() > 0) {
/* 153 */           plane.setCameraId(plane.getCameraId() + 1);
/* 154 */           if (plane.getCameraId() >= plane.getCameraPosNum()) {
/* 155 */             plane.setCameraId(0);
/*     */           }
/* 157 */         } else if (plane.canSwitchGunnerMode()) {
/* 158 */           pc.switchMode = (byte)(plane.getIsGunnerMode((Entity)player) ? 0 : 1);
/* 159 */           plane.switchGunnerMode(!plane.getIsGunnerMode((Entity)player));
/* 160 */           send = true;
/* 161 */           plane.setCameraId(0);
/* 162 */         } else if (plane.canSwitchCameraPos()) {
/* 163 */           plane.setCameraId(1);
/*     */         } else {
/* 165 */           playSoundNG();
/*     */         } 
/*     */       }
/*     */       
/* 169 */       if (this.KeyExtra.isKeyDown()) {
/* 170 */         if (plane.canSwitchVtol()) {
/* 171 */           boolean currentMode = plane.getNozzleStat();
/* 172 */           if (!currentMode) {
/* 173 */             pc.switchVtol = 1;
/*     */           } else {
/* 175 */             pc.switchVtol = 0;
/*     */           } 
/*     */           
/* 178 */           plane.swithVtolMode(!currentMode);
/* 179 */           send = true;
/*     */         } else {
/* 181 */           playSoundNG();
/*     */         } 
/*     */       }
/* 184 */     } else if (this.KeySwitchMode.isKeyDown()) {
/* 185 */       if (plane.canSwitchGunnerModeOtherSeat(player)) {
/* 186 */         plane.switchGunnerModeOtherSeat(player);
/* 187 */         send = true;
/*     */       } else {
/* 189 */         playSoundNG();
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     if (this.KeyZoom.isKeyDown()) {
/* 194 */       boolean isUav = (plane.isUAV() && !plane.getAcInfo().haveHatch() && !plane.getPlaneInfo().haveWing());
/* 195 */       if (plane.getIsGunnerMode((Entity)player) || isUav) {
/* 196 */         plane.zoomCamera();
/* 197 */         playSound("zoom", 0.5F, 1.0F);
/* 198 */       } else if (isPilot) {
/* 199 */         if (plane.getAcInfo().haveHatch()) {
/* 200 */           if (plane.canFoldHatch()) {
/* 201 */             pc.switchHatch = 2;
/* 202 */             send = true;
/* 203 */           } else if (plane.canUnfoldHatch()) {
/* 204 */             pc.switchHatch = 1;
/* 205 */             send = true;
/*     */           } 
/* 207 */         } else if (plane.canFoldWing()) {
/* 208 */           pc.switchHatch = 2;
/* 209 */           send = true;
/* 210 */         } else if (plane.canUnfoldWing()) {
/* 211 */           pc.switchHatch = 1;
/* 212 */           send = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 217 */     if (this.KeyEjectSeat.isKeyDown() && plane.canEjectSeat((Entity)player)) {
/* 218 */       pc.ejectSeat = true;
/* 219 */       send = true;
/*     */     } 
/*     */     
/* 222 */     if (send)
/* 223 */       W_Network.sendToServer((W_PacketBase)pc); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\plane\MCP_ClientPlaneTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */