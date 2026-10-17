/*     */ package com.norwood.mcheli.vehicle;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Key;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_ViewEntityDummy;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftClientTickHandler;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
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
/*     */ public class MCH_ClientVehicleTickHandler extends MCH_AircraftClientTickHandler {
/*     */   public MCH_Key KeySwitchMode;
/*     */   public MCH_Key KeySwitchHovering;
/*     */   
/*     */   public MCH_ClientVehicleTickHandler(Minecraft minecraft, MCH_Config config) {
/*  23 */     super(minecraft, config);
/*  24 */     updateKeybind(config);
/*     */   }
/*     */   public MCH_Key KeyZoom; public MCH_Key KeyExtra; public MCH_Key[] Keys;
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  29 */     super.updateKeybind(config);
/*  30 */     this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
/*  31 */     this.KeySwitchHovering = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
/*  32 */     this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/*  33 */     this.KeyExtra = new MCH_Key(MCH_Config.KeyExtra.prmInt);
/*  34 */     this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeySwitchHovering, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyGUI };
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
/*     */   protected void update(EntityPlayer player, MCH_EntityVehicle vehicle, MCH_VehicleInfo info) {
/*  56 */     if (info != null) {
/*  57 */       setRotLimitPitch(info.minRotationPitch, info.maxRotationPitch, (Entity)player);
/*     */     }
/*     */     
/*  60 */     vehicle.updateCameraRotate(player.field_70177_z, player.field_70125_A);
/*  61 */     vehicle.updateRadar(5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onTick(boolean inGUI) {
/*  66 */     for (MCH_Key k : this.Keys) {
/*  67 */       k.update();
/*     */     }
/*     */     
/*  70 */     this.isBeforeRiding = this.isRiding;
/*  71 */     EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
/*  72 */     MCH_EntityVehicle vehicle = null;
/*  73 */     boolean isPilot = true;
/*  74 */     if (entityPlayerSP != null) {
/*  75 */       if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityVehicle)
/*  76 */       { vehicle = (MCH_EntityVehicle)entityPlayerSP.func_184187_bx(); }
/*  77 */       else { Entity entity = entityPlayerSP.func_184187_bx(); if (entity instanceof MCH_EntitySeat) { MCH_EntitySeat seat = (MCH_EntitySeat)entity;
/*  78 */           if (seat.getParent() instanceof MCH_EntityVehicle) {
/*  79 */             isPilot = false;
/*  80 */             vehicle = (MCH_EntityVehicle)seat.getParent();
/*     */           }  }
/*     */          }
/*     */     
/*     */     }
/*  85 */     if (vehicle != null && vehicle.getAcInfo() != null) {
/*  86 */       MCH_Lib.disableFirstPersonItemRender(entityPlayerSP.func_184614_ca());
/*  87 */       update((EntityPlayer)entityPlayerSP, vehicle, vehicle.getVehicleInfo());
/*  88 */       MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
/*  89 */       viewEntityDummy.update(vehicle.camera);
/*  90 */       if (!inGUI) {
/*  91 */         if (!vehicle.isDestroyed()) {
/*  92 */           playerControl((EntityPlayer)entityPlayerSP, vehicle, isPilot);
/*     */         }
/*     */       } else {
/*  95 */         playerControlInGUI((EntityPlayer)entityPlayerSP, vehicle, isPilot);
/*     */       } 
/*     */       
/*  98 */       MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
/*  99 */       this.isRiding = true;
/*     */     } else {
/* 101 */       this.isRiding = false;
/*     */     } 
/*     */     
/* 104 */     if (!this.isBeforeRiding && this.isRiding) {
/* 105 */       W_Reflection.setThirdPersonDistance(vehicle.thirdPersonDist);
/* 106 */     } else if (this.isBeforeRiding && !this.isRiding) {
/* 107 */       W_Reflection.restoreDefaultThirdPersonDistance();
/* 108 */       MCH_Lib.enableFirstPersonItemRender();
/* 109 */       MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void playerControlInGUI(EntityPlayer player, MCH_EntityVehicle vehicle, boolean isPilot) {
/* 114 */     commonPlayerControlInGUI(player, vehicle, isPilot, new MCH_PacketVehiclePlayerControl());
/*     */   }
/*     */   
/*     */   protected void playerControl(EntityPlayer player, MCH_EntityVehicle vehicle, boolean isPilot) {
/* 118 */     MCH_PacketVehiclePlayerControl pc = new MCH_PacketVehiclePlayerControl();
/*     */     
/* 120 */     boolean send = commonPlayerControl(player, vehicle, isPilot, pc);
/* 121 */     if (this.KeyExtra.isKeyDown()) {
/* 122 */       if (vehicle.getTowChainEntity() != null) {
/* 123 */         playSoundOK();
/* 124 */         pc.unhitchChainId = W_Entity.getEntityId((Entity)vehicle.getTowChainEntity());
/* 125 */         send = true;
/*     */       } else {
/* 127 */         playSoundNG();
/*     */       } 
/*     */     }
/*     */     
/* 131 */     if (this.KeySwitchHovering.isKeyDown() || this.KeySwitchMode.isKeyDown());
/*     */ 
/*     */     
/* 134 */     if (this.KeyZoom.isKeyDown()) {
/* 135 */       if (vehicle.canZoom()) {
/* 136 */         vehicle.zoomCamera();
/* 137 */         playSound("zoom", 0.5F, 1.0F);
/* 138 */       } else if (vehicle.getAcInfo().haveHatch()) {
/* 139 */         if (vehicle.canFoldHatch()) {
/* 140 */           pc.switchHatch = 2;
/* 141 */           send = true;
/* 142 */         } else if (vehicle.canUnfoldHatch()) {
/* 143 */           pc.switchHatch = 1;
/* 144 */           send = true;
/*     */         } else {
/* 146 */           playSoundNG();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 151 */     if (send)
/* 152 */       W_Network.sendToServer((W_PacketBase)pc); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\vehicle\MCH_ClientVehicleTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */