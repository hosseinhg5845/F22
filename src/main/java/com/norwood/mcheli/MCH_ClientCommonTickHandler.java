import com.norwood.mcheli.wrapper.W_TickHandler;
/*     */ package com.norwood.mcheli;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.aircraft.MCH_ClientSeatTickHandler;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */ import com.norwood.mcheli.aircraft.MCH_SeatInfo;
/*     */ import com.norwood.mcheli.command.MCH_GuiTitle;
/*     */ import com.norwood.mcheli.gltd.MCH_ClientGLTDTickHandler;
/*     */ import com.norwood.mcheli.gltd.MCH_EntityGLTD;
/*     */ import com.norwood.mcheli.gltd.MCH_GuiGLTD;
/*     */ import com.norwood.mcheli.gui.MCH_Gui;
/*     */ import com.norwood.mcheli.helicopter.MCH_ClientHeliTickHandler;
/*     */ import com.norwood.mcheli.helicopter.MCH_GuiHeli;
/*     */ import com.norwood.mcheli.helper.client.MCH_CameraManager;
/*     */ import com.norwood.mcheli.lweapon.MCH_ClientLightWeaponTickHandler;
/*     */ import com.norwood.mcheli.lweapon.MCH_GuiLightWeapon;
/*     */ import com.norwood.mcheli.mob.MCH_GuiSpawnGunner;
/*     */ import com.norwood.mcheli.multiplay.MCH_GuiScoreboard;
/*     */ import com.norwood.mcheli.multiplay.MCH_GuiTargetMarker;
/*     */ import com.norwood.mcheli.multiplay.MCH_MultiplayClient;
/*     */ import com.norwood.mcheli.plane.MCP_ClientPlaneTickHandler;
/*     */ import com.norwood.mcheli.tank.MCH_ClientTankTickHandler;
/*     */ import com.norwood.mcheli.tank.MCH_GuiTank;
/*     */ import com.norwood.mcheli.tool.MCH_ClientToolTickHandler;
/*     */ import com.norwood.mcheli.tool.MCH_GuiWrench;
/*     */ import com.norwood.mcheli.tool.rangefinder.MCH_GuiRangeFinder;
/*     */ import com.norwood.mcheli.uav.MCH_EntityUavStation;
/*     */ import com.norwood.mcheli.vehicle.MCH_ClientVehicleTickHandler;
/*     */ import com.norwood.mcheli.vehicle.MCH_EntityVehicle;
/*     */ import com.norwood.mcheli.vehicle.MCH_GuiVehicle;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponSet;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.W_Reflection;
/*     */ import com.norwood.mcheli.wrapper.W_Vec3;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_ClientCommonTickHandler extends W_TickHandler {
/*  50 */   public static int cameraMode = 0; public static MCH_ClientCommonTickHandler instance;
/*  51 */   public static MCH_EntityAircraft ridingAircraft = null;
/*     */   public static boolean isDrawScoreboard = false;
/*  53 */   public static int sendLDCount = 0;
/*     */   public static boolean isLocked = false;
/*  55 */   public static int lockedSoundCount = 0;
/*     */   private static double prevMouseDeltaX;
/*     */   private static double prevMouseDeltaY;
/*  58 */   private static double mouseDeltaX = 0.0D;
/*  59 */   private static double mouseDeltaY = 0.0D;
/*  60 */   private static double mouseRollDeltaX = 0.0D;
/*  61 */   private static double mouseRollDeltaY = 0.0D;
/*     */   private static boolean isRideAircraft = false;
/*  63 */   private static float prevTick = 0.0F;
/*     */   public final MCH_GuiCommon gui_Common;
/*     */   public final MCH_Gui gui_Heli;
/*     */   public final MCH_Gui gui_Plane;
/*     */   public final MCH_Gui gui_Tank;
/*     */   public final MCH_Gui gui_GLTD;
/*     */   public final MCH_Gui gui_Vehicle;
/*     */   public final MCH_Gui gui_LWeapon;
/*     */   public final MCH_Gui gui_Wrench;
/*     */   public final MCH_Gui gui_EMarker;
/*     */   public final MCH_Gui gui_SwnGnr;
/*     */   public final MCH_Gui gui_RngFndr;
/*     */   public final MCH_Gui gui_Title;
/*     */   public final MCH_Gui[] guis;
/*     */   public final MCH_Gui[] guiTicks;
/*     */   public final MCH_ClientTickHandlerBase[] ticks;
/*     */   public MCH_Key[] Keys;
/*     */   public MCH_Key KeyCamDistUp;
/*     */   public MCH_Key KeyCamDistDown;
/*     */   public MCH_Key KeyScoreboard;
/*     */   public MCH_Key KeyMultiplayManager;
/*     */   int debugcnt;
/*     */   
/*     */   public MCH_ClientCommonTickHandler(Minecraft minecraft, MCH_Config config) {
/*  87 */     super(minecraft);
/*  88 */     this.gui_Common = new MCH_GuiCommon(minecraft);
/*  89 */     this.gui_Heli = (MCH_Gui)new MCH_GuiHeli(minecraft);
/*  90 */     this.gui_Plane = (MCH_Gui)new MCP_GuiPlane(minecraft);
/*  91 */     this.gui_Tank = (MCH_Gui)new MCH_GuiTank(minecraft);
/*  92 */     this.gui_GLTD = (MCH_Gui)new MCH_GuiGLTD(minecraft);
/*  93 */     this.gui_Vehicle = (MCH_Gui)new MCH_GuiVehicle(minecraft);
/*  94 */     this.gui_LWeapon = (MCH_Gui)new MCH_GuiLightWeapon(minecraft);
/*  95 */     this.gui_Wrench = (MCH_Gui)new MCH_GuiWrench(minecraft);
/*  96 */     this.gui_SwnGnr = (MCH_Gui)new MCH_GuiSpawnGunner(minecraft);
/*  97 */     this.gui_RngFndr = (MCH_Gui)new MCH_GuiRangeFinder(minecraft);
/*  98 */     this.gui_EMarker = (MCH_Gui)new MCH_GuiTargetMarker(minecraft);
/*  99 */     this.gui_Title = (MCH_Gui)new MCH_GuiTitle(minecraft);
/* 100 */     this.guis = new MCH_Gui[] { this.gui_RngFndr, this.gui_LWeapon, this.gui_Heli, this.gui_Plane, this.gui_Tank, this.gui_GLTD, this.gui_Vehicle };
/* 101 */     this.guiTicks = new MCH_Gui[] { (MCH_Gui)this.gui_Common, this.gui_Heli, this.gui_Plane, this.gui_Tank, this.gui_GLTD, this.gui_Vehicle, this.gui_LWeapon, this.gui_Wrench, this.gui_SwnGnr, this.gui_RngFndr, this.gui_EMarker, this.gui_Title };
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
/* 115 */     this.ticks = new MCH_ClientTickHandlerBase[] { (MCH_ClientTickHandlerBase)new MCH_ClientHeliTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCP_ClientPlaneTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientTankTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientGLTDTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientVehicleTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientLightWeaponTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientSeatTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientToolTickHandler(minecraft, config) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     updatekeybind(config);
/*     */   }
/*     */   
/*     */   public static double getCurrentStickX() {
/* 129 */     return mouseRollDeltaX;
/*     */   }
/*     */   
/*     */   public static double getCurrentStickY() {
/* 133 */     double inv = 1.0D;
/* 134 */     if ((Minecraft.func_71410_x()).field_71474_y.field_74338_d) {
/* 135 */       inv = -inv;
/*     */     }
/*     */     
/* 138 */     if (MCH_Config.InvertMouse.prmBool) {
/* 139 */       inv = -inv;
/*     */     }
/*     */     
/* 142 */     return mouseRollDeltaY * inv;
/*     */   }
/*     */   
/*     */   public static double getMaxStickLength() {
/* 146 */     return 40.0D;
/*     */   }
/*     */   
/*     */   public void updatekeybind(MCH_Config config) {
/* 150 */     this.KeyCamDistUp = new MCH_Key(MCH_Config.KeyCameraDistUp.prmInt);
/* 151 */     this.KeyCamDistDown = new MCH_Key(MCH_Config.KeyCameraDistDown.prmInt);
/* 152 */     this.KeyScoreboard = new MCH_Key(MCH_Config.KeyScoreboard.prmInt);
/* 153 */     this.KeyMultiplayManager = new MCH_Key(MCH_Config.KeyMultiplayManager.prmInt);
/* 154 */     this.Keys = new MCH_Key[] { this.KeyCamDistUp, this.KeyCamDistDown, this.KeyScoreboard, this.KeyMultiplayManager };
/*     */     
/* 156 */     for (MCH_ClientTickHandlerBase t : this.ticks) {
/* 157 */       t.updateKeybind(config);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getLabel() {
/* 162 */     return null;
/*     */   }
/*     */   
/*     */   public void onTick() {
/* 166 */     MCH_ClientTickHandlerBase.initRotLimit();
/*     */     
/* 168 */     for (MCH_Key k : this.Keys) {
/* 169 */       k.update();
/*     */     }
/*     */     
/* 172 */     EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
/* 173 */     if (entityPlayerSP != null && this.mc.field_71462_r == null) {
/* 174 */       if (MCH_ServerSettings.enableCamDistChange && (this.KeyCamDistUp.isKeyDown() || this.KeyCamDistDown.isKeyDown())) {
/* 175 */         int camdist = (int)W_Reflection.getThirdPersonDistance();
/* 176 */         if (this.KeyCamDistUp.isKeyDown() && camdist < 72) {
/* 177 */           camdist += 4;
/* 178 */           if (camdist > 72) {
/* 179 */             camdist = 72;
/*     */           }
/*     */           
/* 182 */           W_Reflection.setThirdPersonDistance(camdist);
/* 183 */         } else if (this.KeyCamDistDown.isKeyDown()) {
/* 184 */           camdist -= 4;
/* 185 */           if (camdist < 4) {
/* 186 */             camdist = 4;
/*     */           }
/*     */           
/* 189 */           W_Reflection.setThirdPersonDistance(camdist);
/*     */         } 
/*     */       } 
/*     */       
/* 193 */       if (this.mc.field_71462_r == null && (!this.mc.func_71356_B() || MCH_Config.DebugLog)) {
/* 194 */         isDrawScoreboard = this.KeyScoreboard.isKeyPress();
/* 195 */         if (!isDrawScoreboard && this.KeyMultiplayManager.isKeyDown()) {
/* 196 */           MCH_PacketIndOpenScreen.send(5);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     if (sendLDCount < 10) {
/* 202 */       sendLDCount++;
/*     */     } else {
/* 204 */       MCH_MultiplayClient.sendImageData();
/* 205 */       sendLDCount = 0;
/*     */     } 
/*     */     
/* 208 */     boolean inOtherGui = (this.mc.field_71462_r != null);
/*     */     
/* 210 */     for (MCH_ClientTickHandlerBase t : this.ticks) {
/* 211 */       t.onTick(inOtherGui);
/*     */     }
/*     */     
/* 214 */     for (MCH_Gui g : this.guiTicks) {
/* 215 */       g.onTick();
/*     */     }
/*     */     
/* 218 */     MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)entityPlayerSP);
/* 219 */     if (entityPlayerSP != null && ac != null && !ac.isDestroyed()) {
/* 220 */       if (isLocked && lockedSoundCount == 0) {
/* 221 */         isLocked = false;
/* 222 */         lockedSoundCount = 20;
/* 223 */         MCH_ClientTickHandlerBase.playSound("locked");
/*     */       } 
/*     */       
/* 226 */       MCH_CameraManager.setRidingAircraft(ac);
/*     */     } else {
/* 228 */       lockedSoundCount = 0;
/* 229 */       isLocked = false;
/* 230 */       MCH_CameraManager.setRidingAircraft(ac);
/*     */     } 
/*     */     
/* 233 */     if (lockedSoundCount > 0) {
/* 234 */       lockedSoundCount--;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTickPre() {
/* 240 */     if (this.mc.field_71439_g != null && this.mc.field_71441_e != null) {
/* 241 */       onTick();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTickPost() {
/* 247 */     if (this.mc.field_71439_g != null && this.mc.field_71441_e != null) {
/* 248 */       MCH_GuiTargetMarker.onClientTick();
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateMouseDelta(boolean stickMode, float partialTicks) {
/* 253 */     prevMouseDeltaX = mouseDeltaX;
/* 254 */     prevMouseDeltaY = mouseDeltaY;
/* 255 */     mouseDeltaX = 0.0D;
/* 256 */     mouseDeltaY = 0.0D;
/* 257 */     if (this.mc.field_71415_G && Display.isActive() && this.mc.field_71462_r == null) {
/* 258 */       if (stickMode) {
/* 259 */         if (Math.abs(mouseRollDeltaX) < getMaxStickLength() * 0.2D) {
/* 260 */           mouseRollDeltaX *= (1.0F - 0.15F * partialTicks);
/*     */         }
/*     */         
/* 263 */         if (Math.abs(mouseRollDeltaY) < getMaxStickLength() * 0.2D) {
/* 264 */           mouseRollDeltaY *= (1.0F - 0.15F * partialTicks);
/*     */         }
/*     */       } 
/*     */       
/* 268 */       this.mc.field_71417_B.func_74374_c();
/* 269 */       float f1 = this.mc.field_71474_y.field_74341_c * 0.6F + 0.2F;
/* 270 */       float f2 = f1 * f1 * f1 * 8.0F;
/* 271 */       double ms = MCH_Config.MouseSensitivity.prmDouble * 0.1D;
/* 272 */       mouseDeltaX = ms * this.mc.field_71417_B.field_74377_a * f2;
/* 273 */       mouseDeltaY = ms * this.mc.field_71417_B.field_74375_b * f2;
/* 274 */       byte inv = 1;
/* 275 */       if (this.mc.field_71474_y.field_74338_d) {
/* 276 */         inv = -1;
/*     */       }
/*     */       
/* 279 */       if (MCH_Config.InvertMouse.prmBool) {
/* 280 */         inv = (byte)(inv * -1);
/*     */       }
/*     */       
/* 283 */       mouseRollDeltaX += mouseDeltaX;
/* 284 */       mouseRollDeltaY += mouseDeltaY * inv;
/* 285 */       double dist = mouseRollDeltaX * mouseRollDeltaX + mouseRollDeltaY * mouseRollDeltaY;
/* 286 */       if (dist > 1.0D) {
/* 287 */         dist = MathHelper.func_76133_a(dist);
/* 288 */         double d = Math.min(dist, getMaxStickLength());
/*     */         
/* 290 */         mouseRollDeltaX /= dist;
/* 291 */         mouseRollDeltaY /= dist;
/* 292 */         mouseRollDeltaX *= d;
/* 293 */         mouseRollDeltaY *= d;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderTickPre(float partialTicks) {
/* 300 */     MCH_GuiTargetMarker.clearMarkEntityPos();
/* 301 */     if (!MCH_ServerSettings.enableDebugBoundingBox) {
/* 302 */       Minecraft.func_71410_x().func_175598_ae().func_178629_b(false);
/*     */     }
/*     */     
/* 305 */     MCH_ClientEventHook.haveSearchLightAircraft.clear();
/* 306 */     if (this.mc != null && this.mc.field_71441_e != null) {
/* 307 */       for (Object o : (Minecraft.func_71410_x()).field_71441_e.field_72996_f) {
/* 308 */         if (o instanceof MCH_EntityAircraft && ((MCH_EntityAircraft)o).haveSearchLight()) {
/* 309 */           MCH_ClientEventHook.haveSearchLightAircraft.add((MCH_EntityAircraft)o);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 314 */     if (!W_McClient.isGamePaused()) {
/* 315 */       EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
/* 316 */       if (entityPlayerSP != null) {
/* 317 */         ItemStack currentItemstack = entityPlayerSP.func_184586_b(EnumHand.MAIN_HAND);
/* 318 */         if (currentItemstack.func_77973_b() instanceof com.norwood.mcheli.tool.MCH_ItemWrench && entityPlayerSP.func_184605_cv() > 0) {
/* 319 */           W_Reflection.setItemRendererMainProgress(1.0F);
/*     */         }
/*     */         
/* 322 */         ridingAircraft = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)entityPlayerSP);
/* 323 */         if (ridingAircraft != null)
/* 324 */         { cameraMode = ridingAircraft.getCameraMode((EntityPlayer)entityPlayerSP); }
/* 325 */         else { Entity entity = entityPlayerSP.func_184187_bx(); if (entity instanceof MCH_EntityGLTD) { MCH_EntityGLTD gltd = (MCH_EntityGLTD)entity;
/* 326 */             cameraMode = gltd.camera.getMode(0); }
/*     */           else
/* 328 */           { cameraMode = 0; }
/*     */            }
/*     */         
/* 331 */         MCH_EntityAircraft ac = null;
/* 332 */         if (entityPlayerSP.func_184187_bx() instanceof com.norwood.mcheli.helicopter.MCH_EntityHeli || entityPlayerSP
/* 333 */           .func_184187_bx() instanceof com.norwood.mcheli.plane.MCP_EntityPlane || entityPlayerSP
/* 334 */           .func_184187_bx() instanceof com.norwood.mcheli.tank.MCH_EntityTank)
/* 335 */         { ac = (MCH_EntityAircraft)entityPlayerSP.func_184187_bx(); }
/* 336 */         else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityUavStation)
/* 337 */         { ac = ((MCH_EntityUavStation)entityPlayerSP.func_184187_bx()).getControlAircract(); }
/* 338 */         else { Entity entity = entityPlayerSP.func_184187_bx(); if (entity instanceof MCH_EntityVehicle) { MCH_EntityVehicle vehicle = (MCH_EntityVehicle)entity;
/* 339 */             vehicle.setupAllRiderRenderPosition(partialTicks, (EntityPlayer)entityPlayerSP); }
/*     */            }
/*     */         
/* 342 */         boolean stickMode = false;
/* 343 */         if (ac instanceof com.norwood.mcheli.helicopter.MCH_EntityHeli) {
/* 344 */           stickMode = MCH_Config.MouseControlStickModeHeli.prmBool;
/*     */         }
/*     */         
/* 347 */         if (ac instanceof com.norwood.mcheli.plane.MCP_EntityPlane) {
/* 348 */           stickMode = MCH_Config.MouseControlStickModePlane.prmBool;
/*     */         }
/*     */         
/* 351 */         for (int i = 0; i < 10 && prevTick > partialTicks; i++) {
/* 352 */           prevTick--;
/*     */         }
/*     */         
/* 355 */         if (ac != null && ac.canMouseRot()) {
/* 356 */           if (!isRideAircraft) {
/* 357 */             ac.onInteractFirst((EntityPlayer)entityPlayerSP);
/*     */           }
/*     */           
/* 360 */           isRideAircraft = true;
/* 361 */           updateMouseDelta(stickMode, partialTicks);
/* 362 */           boolean fixRot = false;
/* 363 */           float fixYaw = 0.0F;
/* 364 */           float fixPitch = 0.0F;
/* 365 */           MCH_SeatInfo seatInfo = ac.getSeatInfo((Entity)entityPlayerSP);
/* 366 */           if (seatInfo != null && seatInfo.fixRot && ac.getIsGunnerMode((Entity)entityPlayerSP) && !ac.isGunnerLookMode((EntityPlayer)entityPlayerSP)) {
/* 367 */             fixRot = true;
/* 368 */             fixYaw = seatInfo.fixYaw;
/* 369 */             fixPitch = seatInfo.fixPitch;
/* 370 */             mouseRollDeltaX *= 0.0D;
/* 371 */             mouseRollDeltaY *= 0.0D;
/* 372 */             mouseDeltaX *= 0.0D;
/* 373 */             mouseDeltaY *= 0.0D;
/* 374 */           } else if (ac.isPilot((Entity)entityPlayerSP)) {
/* 375 */             MCH_AircraftInfo.CameraPosition cp = ac.getCameraPosInfo();
/* 376 */             if (cp != null) {
/* 377 */               fixYaw = cp.yaw;
/* 378 */               fixPitch = cp.pitch;
/*     */             } 
/*     */           } 
/*     */           
/* 382 */           if (ac.getAcInfo() == null) {
/* 383 */             entityPlayerSP.func_70082_c((float)mouseDeltaX, (float)mouseDeltaY);
/*     */           } else {
/* 385 */             ac.setAngles((Entity)entityPlayerSP, fixRot, fixYaw, fixPitch, (float)(mouseDeltaX + prevMouseDeltaX) / 2.0F, (float)(mouseDeltaY + prevMouseDeltaY) / 2.0F, (float)mouseRollDeltaX, (float)mouseRollDeltaY, partialTicks - prevTick);
/*     */           } 
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
/* 398 */           ac.setupAllRiderRenderPosition(partialTicks, (EntityPlayer)entityPlayerSP);
/* 399 */           double dist = MathHelper.func_76133_a(mouseRollDeltaX * mouseRollDeltaX + mouseRollDeltaY * mouseRollDeltaY);
/* 400 */           if (!stickMode || dist < getMaxStickLength() * 0.1D) {
/* 401 */             mouseRollDeltaX *= 0.95D;
/* 402 */             mouseRollDeltaY *= 0.95D;
/*     */           } 
/*     */           
/* 405 */           float roll = MathHelper.func_76142_g(ac.getRotRoll());
/* 406 */           float yaw = MathHelper.func_76142_g(ac.getRotYaw() - ((EntityPlayer)entityPlayerSP).field_70177_z);
/* 407 */           roll *= MathHelper.func_76134_b((float)(yaw * Math.PI / 180.0D));
/* 408 */           if (ac.getTVMissile() != null && W_Lib.isClientPlayer((ac.getTVMissile()).shootingEntity) && ac.getIsGunnerMode((Entity)entityPlayerSP)) {
/* 409 */             roll = 0.0F;
/*     */           }
/*     */           
/* 412 */           W_Reflection.setCameraRoll(roll);
/* 413 */           correctViewEntityDummy((Entity)entityPlayerSP);
/*     */         } else {
/* 415 */           MCH_EntitySeat seat = (entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) ? (MCH_EntitySeat)entityPlayerSP.func_184187_bx() : null;
/* 416 */           if (seat != null && seat.getParent() != null) {
/* 417 */             updateMouseDelta(stickMode, partialTicks);
/* 418 */             ac = seat.getParent();
/* 419 */             boolean fixRotx = false;
/* 420 */             MCH_SeatInfo seatInfox = ac.getSeatInfo((Entity)entityPlayerSP);
/* 421 */             if (seatInfox != null && seatInfox.fixRot && ac.getIsGunnerMode((Entity)entityPlayerSP) && !ac.isGunnerLookMode((EntityPlayer)entityPlayerSP)) {
/* 422 */               fixRotx = true;
/* 423 */               mouseRollDeltaX *= 0.0D;
/* 424 */               mouseRollDeltaY *= 0.0D;
/* 425 */               mouseDeltaX *= 0.0D;
/* 426 */               mouseDeltaY *= 0.0D;
/*     */             } 
/*     */             
/* 429 */             Vec3d v = new Vec3d(mouseDeltaX, mouseRollDeltaY, 0.0D);
/* 430 */             v = W_Vec3.rotateRoll((float)((ac.calcRotRoll(partialTicks) / 180.0F) * Math.PI), v);
/* 431 */             MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)entityPlayerSP);
/* 432 */             mouseDeltaY *= (ws != null && ws.getInfo() != null) ? (ws.getInfo()).cameraRotationSpeedPitch : 1.0D;
/* 433 */             entityPlayerSP.func_70082_c((float)mouseDeltaX, (float)mouseDeltaY);
/* 434 */             float y = ac.getRotYaw();
/* 435 */             float p = ac.getRotPitch();
/* 436 */             float r = ac.getRotRoll();
/* 437 */             ac.setRotYaw(ac.calcRotYaw(partialTicks));
/* 438 */             ac.setRotPitch(ac.calcRotPitch(partialTicks));
/* 439 */             ac.setRotRoll(ac.calcRotRoll(partialTicks));
/* 440 */             float revRoll = 0.0F;
/* 441 */             if (fixRotx) {
/* 442 */               ((EntityPlayer)entityPlayerSP).field_70177_z = ac.getRotYaw() + seatInfox.fixYaw;
/* 443 */               ((EntityPlayer)entityPlayerSP).field_70125_A = ac.getRotPitch() + seatInfox.fixPitch;
/* 444 */               if (((EntityPlayer)entityPlayerSP).field_70125_A > 90.0F) {
/* 445 */                 ((EntityPlayer)entityPlayerSP).field_70127_C -= (((EntityPlayer)entityPlayerSP).field_70125_A - 90.0F) * 2.0F;
/* 446 */                 ((EntityPlayer)entityPlayerSP).field_70125_A -= (((EntityPlayer)entityPlayerSP).field_70125_A - 90.0F) * 2.0F;
/* 447 */                 ((EntityPlayer)entityPlayerSP).field_70126_B += 180.0F;
/* 448 */                 ((EntityPlayer)entityPlayerSP).field_70177_z += 180.0F;
/* 449 */                 revRoll = 180.0F;
/* 450 */               } else if (((EntityPlayer)entityPlayerSP).field_70125_A < -90.0F) {
/* 451 */                 ((EntityPlayer)entityPlayerSP).field_70127_C -= (((EntityPlayer)entityPlayerSP).field_70125_A - 90.0F) * 2.0F;
/* 452 */                 ((EntityPlayer)entityPlayerSP).field_70125_A -= (((EntityPlayer)entityPlayerSP).field_70125_A - 90.0F) * 2.0F;
/* 453 */                 ((EntityPlayer)entityPlayerSP).field_70126_B += 180.0F;
/* 454 */                 ((EntityPlayer)entityPlayerSP).field_70177_z += 180.0F;
/* 455 */                 revRoll = 180.0F;
/*     */               } 
/*     */             } 
/*     */             
/* 459 */             ac.setupAllRiderRenderPosition(partialTicks, (EntityPlayer)entityPlayerSP);
/* 460 */             ac.setRotYaw(y);
/* 461 */             ac.setRotPitch(p);
/* 462 */             ac.setRotRoll(r);
/* 463 */             mouseRollDeltaX *= 0.9D;
/* 464 */             mouseRollDeltaY *= 0.9D;
/* 465 */             float roll = MathHelper.func_76142_g(ac.getRotRoll());
/* 466 */             float yaw = MathHelper.func_76142_g(ac.getRotYaw() - ((EntityPlayer)entityPlayerSP).field_70177_z);
/* 467 */             roll *= MathHelper.func_76134_b((float)(yaw * Math.PI / 180.0D));
/* 468 */             if (ac.getTVMissile() != null && W_Lib.isClientPlayer((ac.getTVMissile()).shootingEntity) && ac.getIsGunnerMode((Entity)entityPlayerSP)) {
/* 469 */               roll = 0.0F;
/*     */             }
/*     */             
/* 472 */             W_Reflection.setCameraRoll(roll + revRoll);
/* 473 */             correctViewEntityDummy((Entity)entityPlayerSP);
/*     */           } else {
/* 475 */             if (isRideAircraft) {
/* 476 */               W_Reflection.setCameraRoll(0.0F);
/* 477 */               isRideAircraft = false;
/*     */             } 
/*     */             
/* 480 */             mouseRollDeltaX = 0.0D;
/* 481 */             mouseRollDeltaY = 0.0D;
/*     */           } 
/*     */         } 
/*     */         
/* 485 */         if (ac != null) {
/* 486 */           if (ac.getSeatIdByEntity((Entity)entityPlayerSP) == 0 && !ac.isDestroyed()) {
/* 487 */             ac.lastRiderYaw = ((EntityPlayer)entityPlayerSP).field_70177_z;
/* 488 */             ac.prevLastRiderYaw = ((EntityPlayer)entityPlayerSP).field_70126_B;
/* 489 */             ac.lastRiderPitch = ((EntityPlayer)entityPlayerSP).field_70125_A;
/* 490 */             ac.prevLastRiderPitch = ((EntityPlayer)entityPlayerSP).field_70127_C;
/*     */           } 
/*     */           
/* 493 */           ac.updateWeaponsRotation();
/*     */         } 
/*     */         
/* 496 */         MCH_ViewEntityDummy mCH_ViewEntityDummy = MCH_ViewEntityDummy.getInstance(((EntityPlayer)entityPlayerSP).field_70170_p);
/* 497 */         if (mCH_ViewEntityDummy != null) {
/* 498 */           ((Entity)mCH_ViewEntityDummy).field_70177_z = ((EntityPlayer)entityPlayerSP).field_70177_z;
/* 499 */           ((Entity)mCH_ViewEntityDummy).field_70126_B = ((EntityPlayer)entityPlayerSP).field_70126_B;
/* 500 */           if (ac != null) {
/* 501 */             MCH_WeaponSet wi = ac.getCurrentWeapon((Entity)entityPlayerSP);
/* 502 */             if (wi != null && wi.getInfo() != null && (wi.getInfo()).fixCameraPitch) {
/* 503 */               ((Entity)mCH_ViewEntityDummy).field_70125_A = ((Entity)mCH_ViewEntityDummy).field_70127_C = 0.0F;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 508 */         prevTick = partialTicks;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void correctViewEntityDummy(Entity entity) {
/* 514 */     MCH_ViewEntityDummy mCH_ViewEntityDummy = MCH_ViewEntityDummy.getInstance(entity.field_70170_p);
/* 515 */     if (mCH_ViewEntityDummy != null) {
/* 516 */       if (((Entity)mCH_ViewEntityDummy).field_70177_z - ((Entity)mCH_ViewEntityDummy).field_70126_B > 180.0F) {
/* 517 */         ((Entity)mCH_ViewEntityDummy).field_70126_B += 360.0F;
/* 518 */       } else if (((Entity)mCH_ViewEntityDummy).field_70177_z - ((Entity)mCH_ViewEntityDummy).field_70126_B < -180.0F) {
/* 519 */         ((Entity)mCH_ViewEntityDummy).field_70126_B -= 360.0F;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlayerTickPre(EntityPlayer player) {
/* 526 */     if (player.field_70170_p.field_72995_K) {
/* 527 */       ItemStack currentItemstack = player.func_184586_b(EnumHand.MAIN_HAND);
/* 528 */       if (!currentItemstack.func_190926_b() && currentItemstack
/* 529 */         .func_77973_b() instanceof com.norwood.mcheli.tool.MCH_ItemWrench && player
/* 530 */         .func_184605_cv() > 0 && player
/* 531 */         .func_184607_cu() != currentItemstack) {
/* 532 */         int maxdm = currentItemstack.func_77958_k();
/* 533 */         int dm = currentItemstack.func_77960_j();
/* 534 */         if (dm <= maxdm && dm > 0) {
/* 535 */           player.func_184598_c(EnumHand.MAIN_HAND);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderTickPost(float partialTicks) {
/* 543 */     if (this.mc.field_71439_g != null) {
/* 544 */       MCH_ClientTickHandlerBase.applyRotLimit((Entity)this.mc.field_71439_g);
/* 545 */       MCH_ViewEntityDummy mCH_ViewEntityDummy = MCH_ViewEntityDummy.getInstance(this.mc.field_71439_g.field_70170_p);
/* 546 */       if (mCH_ViewEntityDummy != null) {
/* 547 */         ((Entity)mCH_ViewEntityDummy).field_70125_A = this.mc.field_71439_g.field_70125_A;
/* 548 */         ((Entity)mCH_ViewEntityDummy).field_70177_z = this.mc.field_71439_g.field_70177_z;
/* 549 */         ((Entity)mCH_ViewEntityDummy).field_70127_C = this.mc.field_71439_g.field_70127_C;
/* 550 */         ((Entity)mCH_ViewEntityDummy).field_70126_B = this.mc.field_71439_g.field_70126_B;
/*     */       } 
/*     */     } 
/*     */     
/* 554 */     if (this.mc.field_71462_r == null || this.mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat || this.mc.field_71462_r
/*     */       
/* 556 */       .getClass().toString().contains("GuiDriveableController")) {
/* 557 */       for (MCH_Gui gui : this.guis) {
/* 558 */         if (drawGui(gui, partialTicks)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 563 */       drawGui((MCH_Gui)this.gui_Common, partialTicks);
/* 564 */       drawGui(this.gui_Wrench, partialTicks);
/* 565 */       drawGui(this.gui_SwnGnr, partialTicks);
/* 566 */       drawGui(this.gui_EMarker, partialTicks);
/* 567 */       if (isDrawScoreboard) {
/* 568 */         MCH_GuiScoreboard.drawList(this.mc, this.mc.field_71466_p, false);
/*     */       }
/*     */       
/* 571 */       drawGui(this.gui_Title, partialTicks);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean drawGui(MCH_Gui gui, float partialTicks) {
/* 576 */     if (gui.isDrawGui((EntityPlayer)this.mc.field_71439_g)) {
/* 577 */       gui.func_73863_a(0, 0, partialTicks);
/* 578 */       return true;
/*     */     } 
/* 580 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_ClientCommonTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */