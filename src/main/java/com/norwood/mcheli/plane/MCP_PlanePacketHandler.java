/*     */ package com.norwood.mcheli.plane;
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */ import com.norwood.mcheli.helper.network.HandleSide;
/*     */ import com.norwood.mcheli.uav.MCH_EntityUavStation;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponParam;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.IThreadListener;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ public class MCP_PlanePacketHandler {
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacket_PlayerControl(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/*  15 */     if (!player.world.isRemote) {
/*  16 */       MCP_PlanePacketPlayerControl pc = new MCP_PlanePacketPlayerControl();
/*  17 */       pc.readData(data);
/*  18 */       scheduler.addScheduledTask(() -> {
/*     */             MCP_EntityPlane plane = null;
/*     */             if (player.getRidingEntity() instanceof MCP_EntityPlane) {
/*     */               plane = (MCP_EntityPlane)player.getRidingEntity();
/*     */             } else if (player.getRidingEntity() instanceof MCH_EntitySeat) {
/*     */               if (((MCH_EntitySeat)player.getRidingEntity()).getParent() instanceof MCP_EntityPlane)
/*     */                 plane = (MCP_EntityPlane)((MCH_EntitySeat)player.getRidingEntity()).getParent(); 
/*     */             } else {
/*     */               Entity patt1407$temp = player.getRidingEntity();
/*     */               if (patt1407$temp instanceof MCH_EntityUavStation) {
/*     */                 MCH_EntityUavStation uavStation = (MCH_EntityUavStation)patt1407$temp;
/*     */                 if (uavStation.getControlAircract() instanceof MCP_EntityPlane)
/*     */                   plane = (MCP_EntityPlane)uavStation.getControlAircract(); 
/*     */               } 
/*     */             } 
/*     */             if (plane != null)
/*     */               if (pc.isUnmount == 1) {
/*     */                 plane.unmountEntity();
/*     */               } else if (pc.isUnmount == 2) {
/*     */                 plane.unmountCrew();
/*     */               } else if (pc.ejectSeat) {
/*     */                 plane.ejectSeat((Entity)player);
/*     */               } else {
/*     */                 if (pc.switchVtol == 0)
/*     */                   plane.swithVtolMode(false); 
/*     */                 if (pc.switchVtol == 1)
/*     */                   plane.swithVtolMode(true); 
/*     */                 if (pc.switchMode == 0)
/*     */                   plane.switchGunnerMode(false); 
/*     */                 if (pc.switchMode == 1)
/*     */                   plane.switchGunnerMode(true); 
/*     */                 if (pc.switchMode == 2)
/*     */                   plane.switchHoveringMode(false); 
/*     */                 if (pc.switchMode == 3)
/*     */                   plane.switchHoveringMode(true); 
/*     */                 if (pc.switchSearchLight)
/*     */                   plane.setSearchLight(!plane.isSearchLightON()); 
/*     */                 if (pc.switchCameraMode > 0)
/*     */                   plane.switchCameraMode(player, pc.switchCameraMode - 1); 
/*     */                 if (pc.switchWeapon >= 0)
/*     */                   plane.switchWeapon((Entity)player, pc.switchWeapon); 
/*     */                 if (pc.useWeapon) {
/*     */                   MCH_WeaponParam prm = new MCH_WeaponParam();
/*     */                   prm.entity = (Entity)plane;
/*     */                   prm.user = (Entity)player;
/*     */                   prm.setPosAndRot(pc.useWeaponPosX, pc.useWeaponPosY, pc.useWeaponPosZ, 0.0F, 0.0F);
/*     */                   prm.option1 = pc.useWeaponOption1;
/*     */                   prm.option2 = pc.useWeaponOption2;
/*     */                   plane.useCurrentWeapon(prm);
/*     */                 } 
/*     */                 if (plane.isPilot((Entity)player)) {
/*     */                   plane.throttleUp = pc.throttleUp;
/*     */                   plane.throttleDown = pc.throttleDown;
/*     */                   plane.moveLeft = pc.moveLeft;
/*     */                   plane.moveRight = pc.moveRight;
/*     */                 } 
/*     */                 if (pc.useFlareType > 0)
/*     */                   plane.useFlare(pc.useFlareType); 
/*     */                 if (pc.openGui)
/*     */                   plane.openGui(player); 
/*     */                 if (pc.switchHatch > 0)
/*     */                   if (plane.getAcInfo().haveHatch()) {
/*     */                     plane.foldHatch((pc.switchHatch == 2));
/*     */                   } else {
/*     */                     plane.foldWing((pc.switchHatch == 2));
/*     */                   }  
/*     */                 if (pc.switchFreeLook > 0)
/*     */                   plane.switchFreeLookMode((pc.switchFreeLook == 1)); 
/*     */                 if (pc.switchGear == 1)
/*     */                   plane.foldLandingGear(); 
/*     */                 if (pc.switchGear == 2)
/*     */                   plane.unfoldLandingGear(); 
/*     */                 if (pc.putDownRack == 1)
/*     */                   plane.mountEntityToRack(); 
/*     */                 if (pc.putDownRack == 2)
/*     */                   plane.unmountEntityFromRack(); 
/*     */                 if (pc.putDownRack == 3)
/*     */                   plane.rideRack(); 
/*     */                 if (pc.isUnmount == 3)
/*     */                   plane.unmountAircraft(); 
/*     */                 if (pc.switchGunnerStatus)
/*     */                   plane.setGunnerStatus(!plane.getGunnerStatus()); 
/*     */               }  
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\plane\MCP_PlanePacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */