/*     */ package com.norwood.mcheli.gui;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.norwood.mcheli.MCH_ClientCommonTickHandler;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_PacketNotifyInfoReloaded;
/*     */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*     */ import com.norwood.mcheli.multiplay.MCH_GuiTargetMarker;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponInfo;
/*     */ import com.norwood.mcheli.wrapper.W_GuiButton;
/*     */ import com.norwood.mcheli.wrapper.W_GuiContainer;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCH_ConfigGui
/*     */   extends W_GuiContainer {
/*     */   public static final int BUTTON_RENDER = 50;
/*     */   public static final int BUTTON_KEY_BINDING = 51;
/*     */   public static final int BUTTON_PREV_CONTROL = 52;
/*     */   public static final int BUTTON_DEVELOP = 55;
/*     */   public static final int BUTTON_KEY_LIST = 53;
/*     */   public static final int BUTTON_KEY_RESET_ALL = 54;
/*     */   public static final int BUTTON_KEY_LIST_BASE = 200;
/*     */   public static final int BUTTON_KEY_RESET_BASE = 300;
/*     */   public static final int BUTTON_DEV_RELOAD_AC = 400;
/*     */   public static final int BUTTON_DEV_RELOAD_WEAPON = 401;
/*     */   public static final int BUTTON_DEV_RELOAD_HUD = 402;
/*     */   public static final int BUTTON_SAVE_CLOSE = 100;
/*     */   public static final int BUTTON_CANCEL = 101;
/*     */   public static final int SCREEN_CONTROLS = 0;
/*     */   public static final int SCREEN_RENDER = 1;
/*     */   public static final int SCREEN_KEY_BIND = 2;
/*     */   public static final int SCREEN_DEVELOP = 3;
/*     */   private final EntityPlayer thePlayer;
/*     */   public List<W_GuiButton> listControlButtons;
/*     */   public List<W_GuiButton> listRenderButtons;
/*     */   public List<W_GuiButton> listKeyBindingButtons;
/*     */   public List<W_GuiButton> listDevelopButtons;
/*     */   public MCH_GuiList keyBindingList;
/*     */   public int waitKeyButtonId;
/*     */   public int waitKeyAcceptCount;
/*  60 */   public int currentScreenId = 0;
/*     */   private MCH_GuiOnOffButton buttonMouseInv;
/*     */   private MCH_GuiOnOffButton buttonStickModeHeli;
/*     */   private MCH_GuiOnOffButton buttonStickModePlane;
/*     */   private MCH_GuiOnOffButton buttonHideKeyBind;
/*     */   private MCH_GuiOnOffButton buttonShowHUDTP;
/*     */   private MCH_GuiOnOffButton buttonSmoothShading;
/*     */   private MCH_GuiOnOffButton buttonShowEntityMarker;
/*     */   private MCH_GuiOnOffButton buttonMarkThroughWall;
/*     */   private MCH_GuiOnOffButton buttonReplaceCamera;
/*     */   private MCH_GuiOnOffButton buttonNewExplosion;
/*     */   private MCH_GuiSlider sliderEntityMarkerSize;
/*     */   private MCH_GuiSlider sliderBlockMarkerSize;
/*     */   private MCH_GuiSlider sliderSensitivity;
/*     */   private MCH_GuiSlider[] sliderHitMark;
/*     */   private MCH_GuiOnOffButton buttonTestMode;
/*     */   private MCH_GuiOnOffButton buttonThrottleHeli;
/*     */   private MCH_GuiOnOffButton buttonThrottlePlane;
/*     */   private MCH_GuiOnOffButton buttonThrottleTank;
/*     */   private MCH_GuiOnOffButton buttonFlightSimMode;
/*     */   private MCH_GuiOnOffButton buttonSwitchWeaponWheel;
/*     */   private W_GuiButton buttonReloadAircraftInfo;
/*     */   private W_GuiButton buttonReloadWeaponInfo;
/*     */   private W_GuiButton buttonReloadAllHUD;
/*     */   private MCH_GuiSlider __sliderTextureAlpha;
/*  85 */   private int ignoreButtonCounter = 0;
/*     */   
/*     */   public MCH_ConfigGui(EntityPlayer player) {
/*  88 */     super(new MCH_ConfigGuiContainer(player));
/*  89 */     this.thePlayer = player;
/*  90 */     this.xSize = 330;
/*  91 */     this.ySize = 200;
/*     */   }
/*     */   
/*     */   public void initGui() {
/*  95 */     super.initGui();
/*  96 */     this.buttonList.clear();
/*  97 */     int x1 = this.guiLeft + 10;
/*  98 */     int x2 = this.guiLeft + 10 + 150 + 10;
/*  99 */     int y = this.guiTop;
/* 100 */     this.listControlButtons = new ArrayList<>();
/* 101 */     this.buttonMouseInv = new MCH_GuiOnOffButton(0, x1, y + 25, 150, 20, "Invert Mouse : ");
/* 102 */     this.sliderSensitivity = new MCH_GuiSlider(0, x1, y + 50, 150, 20, "Sensitivity : %.1f", 0.0F, 0.0F, 30.0F, 0.1F);
/* 103 */     this.buttonFlightSimMode = new MCH_GuiOnOffButton(0, x1, y + 75, 150, 20, "Mouse Flight Sim Mode : ");
/* 104 */     this.buttonSwitchWeaponWheel = new MCH_GuiOnOffButton(0, x1, y + 100, 150, 20, "Switch Weapon Wheel : ");
/* 105 */     this.listControlButtons.add(new W_GuiButton(50, x1, y + 125, 150, 20, "Render Settings >>"));
/* 106 */     this.listControlButtons.add(new W_GuiButton(51, x1, y + 150, 150, 20, "Key Binding >>"));
/* 107 */     this.listControlButtons.add(new W_GuiButton(55, x2, y + 150, 150, 20, "Development >>"));
/* 108 */     this.buttonTestMode = new MCH_GuiOnOffButton(0, x1, y + 175, 150, 20, "Test Mode : ");
/* 109 */     this.buttonStickModeHeli = new MCH_GuiOnOffButton(0, x2, y + 25, 150, 20, "Stick Mode Heli : ");
/* 110 */     this.buttonStickModePlane = new MCH_GuiOnOffButton(0, x2, y + 50, 150, 20, "Stick Mode Plane : ");
/* 111 */     this.buttonThrottleHeli = new MCH_GuiOnOffButton(0, x2, y + 75, 150, 20, "Throttle Down Heli : ");
/* 112 */     this.buttonThrottlePlane = new MCH_GuiOnOffButton(0, x2, y + 100, 150, 20, "Throttle Down Plane : ");
/* 113 */     this.buttonThrottleTank = new MCH_GuiOnOffButton(0, x2, y + 125, 150, 20, "Throttle Down Tank : ");
/* 114 */     this.listControlButtons.add(this.buttonMouseInv);
/* 115 */     this.listControlButtons.add(this.buttonStickModeHeli);
/* 116 */     this.listControlButtons.add(this.buttonStickModePlane);
/* 117 */     this.listControlButtons.add(this.sliderSensitivity);
/* 118 */     this.listControlButtons.add(this.buttonThrottleHeli);
/* 119 */     this.listControlButtons.add(this.buttonThrottlePlane);
/* 120 */     this.listControlButtons.add(this.buttonThrottleTank);
/* 121 */     this.listControlButtons.add(this.buttonTestMode);
/* 122 */     this.listControlButtons.add(this.buttonFlightSimMode);
/* 123 */     this.listControlButtons.add(this.buttonSwitchWeaponWheel);
/*     */     
/* 125 */     this.buttonList.addAll(this.listControlButtons);
/*     */     
/* 127 */     this.listRenderButtons = new ArrayList<>();
/* 128 */     this.buttonShowHUDTP = new MCH_GuiOnOffButton(0, x1, y + 25, 150, 20, "Show HUD Third Person : ");
/* 129 */     this.buttonHideKeyBind = new MCH_GuiOnOffButton(0, x1, y + 50, 150, 20, "Hide Key Binding : ");
/* 130 */     this.sliderHitMark = new MCH_GuiSlider[] { new MCH_GuiSlider(0, x1, y + 125, 75, 20, "Alpha:%.0f", 0.0F, 0.0F, 255.0F, 16.0F), new MCH_GuiSlider(0, x1 + 75, y + 75, 75, 20, "Red:%.0f", 0.0F, 0.0F, 255.0F, 16.0F), new MCH_GuiSlider(0, x1 + 75, y + 100, 75, 20, "Green:%.0f", 0.0F, 0.0F, 255.0F, 16.0F), new MCH_GuiSlider(0, x1 + 75, y + 125, 75, 20, "Blue:%.0f", 0.0F, 0.0F, 255.0F, 16.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     this.buttonReplaceCamera = new MCH_GuiOnOffButton(0, x1, y + 150, 150, 20, "Change Camera Pos : ");
/* 137 */     this.listRenderButtons.add(new W_GuiButton(52, x1, y + 175, 90, 20, "Controls <<"));
/* 138 */     this.buttonSmoothShading = new MCH_GuiOnOffButton(0, x2, y + 25, 150, 20, "Smooth Shading : ");
/* 139 */     this.buttonShowEntityMarker = new MCH_GuiOnOffButton(0, x2, y + 50, 150, 20, "Show Entity Maker : ");
/* 140 */     this.sliderEntityMarkerSize = new MCH_GuiSlider(0, x2 + 30, y + 75, 120, 20, "Entity Marker Size:%.0f", 10.0F, 0.0F, 30.0F, 1.0F);
/* 141 */     this.sliderBlockMarkerSize = new MCH_GuiSlider(0, x2 + 60, y + 100, 90, 20, "Block Marker Size:%.0f", 10.0F, 0.0F, 20.0F, 1.0F);
/* 142 */     this.buttonMarkThroughWall = new MCH_GuiOnOffButton(0, x2 + 30, y + 100, 120, 20, "Mark Through Wall : ");
/* 143 */     this.buttonNewExplosion = new MCH_GuiOnOffButton(0, x2, y + 150, 150, 20, "Default Explosion : ");
/* 144 */     this.listRenderButtons.add(this.buttonShowHUDTP);
/*     */     
/* 146 */     Collections.addAll(this.listRenderButtons, (W_GuiButton[])this.sliderHitMark);
/*     */     
/* 148 */     this.listRenderButtons.add(this.buttonSmoothShading);
/* 149 */     this.listRenderButtons.add(this.buttonHideKeyBind);
/* 150 */     this.listRenderButtons.add(this.buttonShowEntityMarker);
/* 151 */     this.listRenderButtons.add(this.buttonReplaceCamera);
/* 152 */     this.listRenderButtons.add(this.buttonNewExplosion);
/* 153 */     this.listRenderButtons.add(this.sliderEntityMarkerSize);
/* 154 */     this.listRenderButtons.add(this.sliderBlockMarkerSize);
/*     */     
/* 156 */     this.buttonList.addAll(this.listRenderButtons);
/*     */     
/* 158 */     this.listKeyBindingButtons = new ArrayList<>();
/* 159 */     this.waitKeyButtonId = 0;
/* 160 */     this.waitKeyAcceptCount = 0;
/* 161 */     this.keyBindingList = new MCH_GuiList(53, 7, x1, y + 25 - 2, 310, 150, "");
/* 162 */     this.listKeyBindingButtons.add(this.keyBindingList);
/* 163 */     this.listKeyBindingButtons.add(new W_GuiButton(52, x1, y + 175, 90, 20, "Controls <<"));
/* 164 */     this.listKeyBindingButtons.add(new W_GuiButton(54, x1 + 90, y + 175, 60, 20, "Reset All"));
/* 165 */     MCH_GuiListItemKeyBind[] listKeyBindItems = { new MCH_GuiListItemKeyBind(200, 300, x1, "Up", MCH_Config.KeyUp), new MCH_GuiListItemKeyBind(201, 301, x1, "Down", MCH_Config.KeyDown), new MCH_GuiListItemKeyBind(202, 302, x1, "Right", MCH_Config.KeyRight), new MCH_GuiListItemKeyBind(203, 303, x1, "Left", MCH_Config.KeyLeft), new MCH_GuiListItemKeyBind(204, 304, x1, "Switch Gunner", MCH_Config.KeySwitchMode), new MCH_GuiListItemKeyBind(205, 305, x1, "Switch Hovering", MCH_Config.KeySwitchHovering), new MCH_GuiListItemKeyBind(206, 306, x1, "Switch Weapon1", MCH_Config.KeySwitchWeapon1), new MCH_GuiListItemKeyBind(207, 307, x1, "Switch Weapon2", MCH_Config.KeySwitchWeapon2), new MCH_GuiListItemKeyBind(208, 308, x1, "Switch Weapon Mode", MCH_Config.KeySwWeaponMode), new MCH_GuiListItemKeyBind(209, 309, x1, "Zoom / Fold Wing", MCH_Config.KeyZoom), new MCH_GuiListItemKeyBind(210, 310, x1, "Camera Mode", MCH_Config.KeyCameraMode), new MCH_GuiListItemKeyBind(211, 311, x1, "Unmount Mobs", MCH_Config.KeyUnmount), new MCH_GuiListItemKeyBind(212, 312, x1, "Flare", MCH_Config.KeyFlare), new MCH_GuiListItemKeyBind(213, 313, x1, "Vtol / Drop / Fold Blade", MCH_Config.KeyExtra), new MCH_GuiListItemKeyBind(214, 314, x1, "Third Person Distance Up", MCH_Config.KeyCameraDistUp), new MCH_GuiListItemKeyBind(215, 315, x1, "Third Person Distance Down", MCH_Config.KeyCameraDistDown), new MCH_GuiListItemKeyBind(216, 316, x1, "Switch Free Look", MCH_Config.KeyFreeLook), new MCH_GuiListItemKeyBind(217, 317, x1, "Open GUI", MCH_Config.KeyGUI), new MCH_GuiListItemKeyBind(218, 318, x1, "Gear Up Down", MCH_Config.KeyGearUpDown), new MCH_GuiListItemKeyBind(219, 319, x1, "Put entity in the rack", MCH_Config.KeyPutToRack), new MCH_GuiListItemKeyBind(220, 320, x1, "Drop entity from the rack", MCH_Config.KeyDownFromRack), new MCH_GuiListItemKeyBind(221, 321, x1, "[MP]Score board", MCH_Config.KeyScoreboard), new MCH_GuiListItemKeyBind(222, 322, x1, "[MP][OP]Multiplay manager", MCH_Config.KeyMultiplayManager) };
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
/*     */ 
/*     */     
/* 191 */     for (MCH_GuiListItemKeyBind item : listKeyBindItems) {
/* 192 */       this.keyBindingList.addItem(item);
/*     */     }
/*     */     
/* 195 */     this.buttonList.addAll(this.listKeyBindingButtons);
/*     */     
/* 197 */     this.listDevelopButtons = new ArrayList<>();
/* 198 */     if (Minecraft.getMinecraft().isSingleplayer()) {
/* 199 */       this.buttonReloadAircraftInfo = new W_GuiButton(400, x1, y + 50, 150, 20, "Reload aircraft setting");
/* 200 */       this.buttonReloadWeaponInfo = new W_GuiButton(401, x1, y + 75, 150, 20, "Reload All Weapons");
/* 201 */       this.buttonReloadAllHUD = new W_GuiButton(402, x1, y + 100, 150, 20, "Reload All HUD");
/* 202 */       this.__sliderTextureAlpha = new MCH_GuiSlider(432, x1, y + 125, 150, 20, "Texture Alpha:%.0f", 1.0F, 0.0F, 255.0F, 1.0F);
/* 203 */       this.listDevelopButtons.add(this.buttonReloadAircraftInfo);
/* 204 */       this.listDevelopButtons.add(this.buttonReloadWeaponInfo);
/* 205 */       this.listDevelopButtons.add(this.buttonReloadAllHUD);
/* 206 */       this.listDevelopButtons.add(this.__sliderTextureAlpha);
/*     */     } 
/*     */     
/* 209 */     this.listDevelopButtons.add(new W_GuiButton(52, x1, y + 175, 90, 20, "Controls <<"));
/*     */     
/* 211 */     this.buttonList.addAll(this.listDevelopButtons);
/*     */     
/* 213 */     this.buttonList.add(new GuiButton(100, x2, y + 175, 80, 20, "Save & Close"));
/* 214 */     this.buttonList.add(new GuiButton(101, x2 + 90, y + 175, 60, 20, "Cancel"));
/* 215 */     switchScreen(0);
/* 216 */     applySwitchScreen();
/* 217 */     getAllStatusFromConfig();
/*     */   }
/*     */   
/*     */   public boolean canButtonClick() {
/* 221 */     return (this.ignoreButtonCounter <= 0);
/*     */   }
/*     */   
/*     */   public void getAllStatusFromConfig() {
/* 225 */     this.buttonMouseInv.setOnOff(MCH_Config.InvertMouse.prmBool);
/* 226 */     this.buttonStickModeHeli.setOnOff(MCH_Config.MouseControlStickModeHeli.prmBool);
/* 227 */     this.buttonStickModePlane.setOnOff(MCH_Config.MouseControlStickModePlane.prmBool);
/* 228 */     this.sliderSensitivity.setSliderValue((float)MCH_Config.MouseSensitivity.prmDouble);
/* 229 */     this.buttonShowHUDTP.setOnOff(MCH_Config.DisplayHUDThirdPerson.prmBool);
/* 230 */     this.buttonSmoothShading.setOnOff(MCH_Config.SmoothShading.prmBool);
/* 231 */     this.buttonHideKeyBind.setOnOff(MCH_Config.HideKeybind.prmBool);
/* 232 */     this.buttonShowEntityMarker.setOnOff(MCH_Config.DisplayEntityMarker.prmBool);
/* 233 */     this.buttonMarkThroughWall.setOnOff(MCH_Config.DisplayMarkThroughWall.prmBool);
/* 234 */     this.sliderEntityMarkerSize.setSliderValue((float)MCH_Config.EntityMarkerSize.prmDouble);
/* 235 */     this.sliderBlockMarkerSize.setSliderValue((float)MCH_Config.BlockMarkerSize.prmDouble);
/* 236 */     this.buttonReplaceCamera.setOnOff(MCH_Config.ReplaceRenderViewEntity.prmBool);
/* 237 */     this.buttonNewExplosion.setOnOff(MCH_Config.DefaultExplosionParticle.prmBool);
/* 238 */     this.sliderHitMark[0].setSliderValue(MCH_Config.hitMarkColorAlpha * 255.0F);
/* 239 */     this.sliderHitMark[1].setSliderValue((MCH_Config.hitMarkColorRGB >> 16 & 0xFF));
/* 240 */     this.sliderHitMark[2].setSliderValue((MCH_Config.hitMarkColorRGB >> 8 & 0xFF));
/* 241 */     this.sliderHitMark[3].setSliderValue((MCH_Config.hitMarkColorRGB >> 0 & 0xFF));
/* 242 */     this.buttonThrottleHeli.setOnOff(MCH_Config.AutoThrottleDownHeli.prmBool);
/* 243 */     this.buttonThrottlePlane.setOnOff(MCH_Config.AutoThrottleDownPlane.prmBool);
/* 244 */     this.buttonThrottleTank.setOnOff(MCH_Config.AutoThrottleDownTank.prmBool);
/* 245 */     this.buttonTestMode.setOnOff(MCH_Config.TestMode.prmBool);
/* 246 */     this.buttonFlightSimMode.setOnOff(MCH_Config.MouseControlFlightSimMode.prmBool);
/* 247 */     this.buttonSwitchWeaponWheel.setOnOff(MCH_Config.SwitchWeaponWithMouseWheel.prmBool);
/* 248 */     if (this.__sliderTextureAlpha != null) {
/* 249 */       this.__sliderTextureAlpha.setSliderValue((float)MCH_Config.__TextureAlpha.prmDouble * 255.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveAndApplyConfig() {
/* 254 */     MCH_Config.InvertMouse.setPrm(this.buttonMouseInv.getOnOff());
/* 255 */     MCH_Config.MouseControlStickModeHeli.setPrm(this.buttonStickModeHeli.getOnOff());
/* 256 */     MCH_Config.MouseControlStickModePlane.setPrm(this.buttonStickModePlane.getOnOff());
/* 257 */     MCH_Config.MouseControlFlightSimMode.setPrm(this.buttonFlightSimMode.getOnOff());
/* 258 */     MCH_Config.SwitchWeaponWithMouseWheel.setPrm(this.buttonSwitchWeaponWheel.getOnOff());
/* 259 */     MCH_Config.MouseSensitivity.setPrm(this.sliderSensitivity.getSliderValueInt(1));
/* 260 */     MCH_Config.DisplayHUDThirdPerson.setPrm(this.buttonShowHUDTP.getOnOff());
/* 261 */     MCH_Config.SmoothShading.setPrm(this.buttonSmoothShading.getOnOff());
/* 262 */     MCH_Config.HideKeybind.setPrm(this.buttonHideKeyBind.getOnOff());
/* 263 */     MCH_Config.DisplayEntityMarker.setPrm(this.buttonShowEntityMarker.getOnOff());
/* 264 */     MCH_Config.DisplayMarkThroughWall.setPrm(this.buttonMarkThroughWall.getOnOff());
/* 265 */     MCH_Config.EntityMarkerSize.setPrm(this.sliderEntityMarkerSize.getSliderValueInt(1));
/* 266 */     MCH_Config.BlockMarkerSize.setPrm(this.sliderBlockMarkerSize.getSliderValueInt(1));
/* 267 */     MCH_Config.ReplaceRenderViewEntity.setPrm(this.buttonReplaceCamera.getOnOff());
/* 268 */     MCH_Config.DefaultExplosionParticle.setPrm(this.buttonNewExplosion.getOnOff());
/* 269 */     float a = this.sliderHitMark[0].getSliderValue();
/* 270 */     int r = (int)this.sliderHitMark[1].getSliderValue();
/* 271 */     int g = (int)this.sliderHitMark[2].getSliderValue();
/* 272 */     int b = (int)this.sliderHitMark[3].getSliderValue();
/* 273 */     MCH_Config.hitMarkColorAlpha = a / 255.0F;
/* 274 */     MCH_Config.hitMarkColorRGB = r << 16 | g << 8 | b;
/* 275 */     MCH_Config.HitMarkColor.setPrm(String.format("%d, %d, %d, %d", new Object[] { Integer.valueOf((int)a), Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b) }));
/* 276 */     boolean b1 = MCH_Config.AutoThrottleDownHeli.prmBool;
/* 277 */     boolean b2 = MCH_Config.AutoThrottleDownPlane.prmBool;
/* 278 */     MCH_Config.AutoThrottleDownHeli.setPrm(this.buttonThrottleHeli.getOnOff());
/* 279 */     MCH_Config.AutoThrottleDownPlane.setPrm(this.buttonThrottlePlane.getOnOff());
/* 280 */     MCH_Config.AutoThrottleDownTank.setPrm(this.buttonThrottleTank.getOnOff());
/* 281 */     if (b1 != MCH_Config.AutoThrottleDownHeli.prmBool || b2 != MCH_Config.AutoThrottleDownPlane.prmBool) {
/* 282 */       sendClientSettings();
/*     */     }
/*     */     
/* 285 */     for (int i = 0; i < this.keyBindingList.getItemNum(); i++) {
/* 286 */       ((MCH_GuiListItemKeyBind)this.keyBindingList.getItem(i)).applyKeycode();
/*     */     }
/*     */     
/* 289 */     MCH_ClientCommonTickHandler.instance.updatekeybind(MCH_MOD.config);
/* 290 */     MCH_Config.TestMode.setPrm(this.buttonTestMode.getOnOff());
/* 291 */     if (this.__sliderTextureAlpha != null) {
/* 292 */       MCH_Config.__TextureAlpha.setPrm(this.__sliderTextureAlpha.getSliderValue() / 255.0D);
/*     */     }
/*     */     
/* 295 */     MCH_MOD.config.write();
/*     */   }
/*     */   
/*     */   public void switchScreen(int screenID) {
/* 299 */     this.waitKeyButtonId = 0;
/* 300 */     this.currentScreenId = screenID;
/*     */     
/* 302 */     for (W_GuiButton b : this.listControlButtons) {
/* 303 */       b.setVisible(false);
/*     */     }
/*     */     
/* 306 */     for (W_GuiButton b : this.listRenderButtons) {
/* 307 */       b.setVisible(false);
/*     */     }
/*     */     
/* 310 */     for (W_GuiButton b : this.listKeyBindingButtons) {
/* 311 */       b.setVisible(false);
/*     */     }
/*     */     
/* 314 */     for (W_GuiButton b : this.listDevelopButtons) {
/* 315 */       b.setVisible(false);
/*     */     }
/*     */     
/* 318 */     this.ignoreButtonCounter = 3;
/*     */   }
/*     */   
/*     */   public void applySwitchScreen() {
/* 322 */     switch (this.currentScreenId) {
/*     */       case 1:
/* 324 */         for (W_GuiButton b : this.listRenderButtons) {
/* 325 */           b.setVisible(true);
/*     */         }
/*     */         return;
/*     */       case 2:
/* 329 */         for (W_GuiButton b : this.listKeyBindingButtons) {
/* 330 */           b.setVisible(true);
/*     */         }
/*     */         return;
/*     */       case 3:
/* 334 */         for (W_GuiButton b : this.listDevelopButtons) {
/* 335 */           b.setVisible(true);
/*     */         }
/*     */         return;
/*     */     } 
/* 339 */     for (W_GuiButton b : this.listControlButtons) {
/* 340 */       b.setVisible(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendClientSettings() {
/* 347 */     if (this.mc.player != null) {
/* 348 */       MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)this.mc.player);
/* 349 */       if (ac != null) {
/* 350 */         int seatId = ac.getSeatIdByEntity((Entity)this.mc.player);
/* 351 */         if (seatId == 0) {
/* 352 */           ac.updateClientSettings(seatId);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void keyTyped(char a, int code) throws IOException {
/* 359 */     if (this.waitKeyButtonId != 0) {
/* 360 */       if (code != 1) {
/* 361 */         super.keyTyped(a, code);
/*     */       }
/*     */       
/* 364 */       acceptKeycode(code);
/* 365 */       this.waitKeyButtonId = 0;
/*     */     } else {
/* 367 */       super.keyTyped(a, code);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void mouseClicked(int par1, int par2, int par3) throws IOException {
/* 372 */     super.mouseClicked(par1, par2, par3);
/* 373 */     if (this.waitKeyButtonId != 0 && this.waitKeyAcceptCount == 0) {
/* 374 */       acceptKeycode(par3 - 100);
/* 375 */       this.waitKeyButtonId = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void acceptKeycode(int code) {
/* 380 */     if (code != 1 && this.mc.currentScreen instanceof MCH_ConfigGui) {
/* 381 */       MCH_GuiListItemKeyBind kb = (MCH_GuiListItemKeyBind)this.keyBindingList.getItem(this.waitKeyButtonId - 200);
/* 382 */       if (kb != null) {
/* 383 */         kb.setKeycode(code);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 389 */     super.handleMouseInput();
/* 390 */     if (this.waitKeyButtonId == 0) {
/* 391 */       int var16 = Mouse.getEventDWheel();
/* 392 */       if (var16 != 0) {
/* 393 */         if (var16 > 0) {
/* 394 */           this.keyBindingList.scrollDown(2.0F);
/*     */         } else {
/* 396 */           this.keyBindingList.scrollUp(2.0F);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateScreen() {
/* 403 */     super.updateScreen();
/* 404 */     if (this.waitKeyAcceptCount > 0) {
/* 405 */       this.waitKeyAcceptCount--;
/*     */     }
/*     */     
/* 408 */     if (this.ignoreButtonCounter > 0) {
/* 409 */       this.ignoreButtonCounter--;
/* 410 */       if (this.ignoreButtonCounter == 0) {
/* 411 */         applySwitchScreen();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onGuiClosed() {
/* 417 */     super.onGuiClosed(); } protected void actionPerformed(@NotNull GuiButton button) { try {
/*     */       MCH_GuiListItem item; int ixx;
/*     */       List<Entity> list;
/*     */       Set<String> reloaded;
/*     */       MCH_EntityAircraft ac;
/* 422 */       super.actionPerformed(button);
/* 423 */       if (!button.enabled) {
/*     */         return;
/*     */       }
/*     */       
/* 427 */       if (this.waitKeyButtonId != 0) {
/*     */         return;
/*     */       }
/*     */       
/* 431 */       if (!canButtonClick()) {
/*     */         return;
/*     */       }
/*     */       
/* 435 */       switch (button.id) {
/*     */         case 50:
/* 437 */           switchScreen(1);
/*     */           break;
/*     */         case 51:
/* 440 */           switchScreen(2);
/*     */           break;
/*     */         case 52:
/* 443 */           switchScreen(0);
/*     */           break;
/*     */         case 53:
/* 446 */           item = this.keyBindingList.lastPushItem;
/* 447 */           if (item != null) {
/* 448 */             MCH_GuiListItemKeyBind kb = (MCH_GuiListItemKeyBind)item;
/* 449 */             if (kb.lastPushButton != null) {
/* 450 */               int kb_num = this.keyBindingList.getItemNum();
/* 451 */               if (kb.lastPushButton.id >= 200 && kb.lastPushButton.id < 200 + kb_num) {
/* 452 */                 this.waitKeyButtonId = kb.lastPushButton.id;
/* 453 */                 this.waitKeyAcceptCount = 5;
/* 454 */               } else if (kb.lastPushButton.id >= 300 && kb.lastPushButton.id < 300 + kb_num) {
/* 455 */                 kb.resetKeycode();
/*     */               } 
/*     */               
/* 458 */               kb.lastPushButton = null;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         case 54:
/* 463 */           for (ixx = 0; ixx < this.keyBindingList.getItemNum(); ixx++) {
/* 464 */             ((MCH_GuiListItemKeyBind)this.keyBindingList.getItem(ixx)).resetKeycode();
/*     */           }
/*     */           break;
/*     */         case 55:
/* 468 */           switchScreen(3);
/*     */           break;
/*     */         case 100:
/* 471 */           saveAndApplyConfig();
/* 472 */           this.mc.player.closeScreen();
/*     */           break;
/*     */         case 101:
/* 475 */           this.mc.player.closeScreen();
/*     */           break;
/*     */         case 401:
/* 478 */           MCH_Lib.DbgLog(true, "MCH_BaseInfo.reload all weapon info.", new Object[0]);
/* 479 */           ContentRegistries.get(MCH_WeaponInfo.class).reloadAll();
/* 480 */           MCH_PacketNotifyInfoReloaded.sendRealodAllWeapon();
/* 481 */           list = this.mc.world.loadedEntityList;
/* 482 */           reloaded = Sets.newHashSet();
/*     */           
/* 484 */           for (Entity value : list) {
/* 485 */             if (value instanceof MCH_EntityAircraft) { MCH_EntityAircraft mCH_EntityAircraft = (MCH_EntityAircraft)value;
/* 486 */               if (mCH_EntityAircraft.getAcInfo() != null && !reloaded.contains((mCH_EntityAircraft.getAcInfo()).name)) {
/* 487 */                 ContentRegistries.get(mCH_EntityAircraft.getAcInfo().getClass()).reload((mCH_EntityAircraft.getAcInfo()).name);
/* 488 */                 mCH_EntityAircraft.changeType((mCH_EntityAircraft.getAcInfo()).name);
/* 489 */                 mCH_EntityAircraft.onAcInfoReloaded();
/* 490 */                 reloaded.add((mCH_EntityAircraft.getAcInfo()).name);
/*     */               }  }
/*     */           
/*     */           } 
/*     */           
/* 495 */           this.mc.player.closeScreen();
/*     */           break;
/*     */         case 402:
/* 498 */           MCH_MOD.proxy.reloadHUD();
/*     */         case 400:
/* 500 */           ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)this.thePlayer);
/* 501 */           if (ac != null && ac.getAcInfo() != null) {
/* 502 */             String name = (ac.getAcInfo()).name;
/* 503 */             MCH_Lib.DbgLog(true, "MCH_BaseInfo.reload : " + name, new Object[0]);
/* 504 */             ContentRegistries.get(ac.getAcInfo().getClass()).reload(name);
/* 505 */             List<Entity> entityList = this.mc.world.loadedEntityList;
/*     */             
/* 507 */             for (Entity entity : entityList) {
/* 508 */               if (entity instanceof MCH_EntityAircraft) {
/* 509 */                 ac = (MCH_EntityAircraft)entity;
/* 510 */                 if (ac.getAcInfo() != null && (ac.getAcInfo()).name.equals(name)) {
/* 511 */                   ac.changeType(name);
/* 512 */                   ac.onAcInfoReloaded();
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 517 */             MCH_PacketNotifyInfoReloaded.sendRealodAc();
/*     */           } 
/*     */           
/* 520 */           this.mc.player.closeScreen(); break;
/*     */       } 
/* 522 */     } catch (Exception var7) {
/* 523 */       var7.printStackTrace();
/*     */     }  }
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 528 */     return true;
/*     */   }
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int par1, int par2) {
/* 532 */     super.drawGuiContainerForegroundLayer(par1, par2);
/* 533 */     drawString("MC Helicopter MOD Options", 10, 10, 16777215);
/* 534 */     if (this.currentScreenId == 0) {
/* 535 */       drawString("< Controls >", 170, 10, 16777215);
/* 536 */     } else if (this.currentScreenId == 1) {
/* 537 */       drawString("< Render >", 170, 10, 16777215);
/* 538 */       drawString("Hit Mark", 10, 75, 16777215);
/* 539 */       int color = 0;
/* 540 */       color |= (int)this.sliderHitMark[0].getSliderValue() << 24;
/* 541 */       color |= (int)this.sliderHitMark[1].getSliderValue() << 16;
/* 542 */       color |= (int)this.sliderHitMark[2].getSliderValue() << 8;
/* 543 */       color |= (int)this.sliderHitMark[3].getSliderValue() << 0;
/* 544 */       drawSampleHitMark(40, 105, color);
/* 545 */       double size = this.sliderEntityMarkerSize.getSliderValue();
/* 546 */       double x = 170.0D + (30.0D - size) / 2.0D;
/* 547 */       double y = (this.sliderEntityMarkerSize.y - this.sliderEntityMarkerSize.getHeight());
/* 548 */       double[] ls = { x + size, y, x, y, x + size / 2.0D, y + size };
/* 549 */       drawLine(ls, -65536, 4);
/* 550 */       size = this.sliderBlockMarkerSize.getSliderValue();
/* 551 */       x = 185.0D;
/* 552 */       y = this.sliderBlockMarkerSize.y;
/* 553 */       color = -65536;
/* 554 */       GL11.glPushMatrix();
/* 555 */       GL11.glEnable(3042);
/* 556 */       GL11.glDisable(3553);
/* 557 */       GL11.glBlendFunc(770, 771);
/* 558 */       GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/* 559 */       Tessellator tessellator = Tessellator.getInstance();
/* 560 */       BufferBuilder builder = tessellator.getBuffer();
/* 561 */       builder.begin(1, DefaultVertexFormats.POSITION_COLOR);
/* 562 */       MCH_GuiTargetMarker.drawRhombus(builder, 15, x, y, this.zLevel, size, color);
/* 563 */       tessellator.draw();
/* 564 */       GL11.glEnable(3553);
/* 565 */       GL11.glDisable(3042);
/* 566 */       GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 567 */       GL11.glPopMatrix();
/* 568 */     } else if (this.currentScreenId == 2) {
/* 569 */       drawString("< Key Binding >", 170, 10, 16777215);
/* 570 */       if (this.waitKeyButtonId != 0) {
/* 571 */         drawRect(30, 30, this.xSize - 30, this.ySize - 30, -533712848);
/* 572 */         String msg = "Please ant key or mouse button.";
/* 573 */         int w = getStringWidth(msg);
/* 574 */         drawString(msg, (this.xSize - w) / 2, this.ySize / 2 - 4, 16777215);
/*     */       } 
/* 576 */     } else if (this.currentScreenId == 3) {
/* 577 */       drawString("< Development >", 170, 10, 16777215);
/* 578 */       drawString("Single player only!", 10, 30, 16711680);
/* 579 */       if (this.buttonReloadAircraftInfo != null && this.buttonReloadAircraftInfo.isOnMouseOver()) {
/* 580 */         drawString("The following items are not reload.", 170, 30, 16777215);
/* 581 */         String[] ignoreItems = MCH_AircraftInfo.getCannotReloadItem();
/* 582 */         int y = 10;
/*     */         
/* 584 */         for (String s : ignoreItems) {
/* 585 */           drawString("  " + s, 170, 30 + y, 16777215);
/* 586 */           y += 10;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
/* 593 */     W_McClient.MOD_bindTexture("textures/gui/config.png");
/* 594 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 595 */     int x = (this.width - this.xSize) / 2;
/* 596 */     int y = (this.height - this.ySize) / 2;
/* 597 */     drawTexturedModalRectRotate(x, y, this.xSize, this.ySize, 0.0D, 0.0D, this.xSize, this.ySize, 0.0F, 512.0D, 256.0D);
/*     */   }
/*     */   
/*     */   public void drawSampleHitMark(int x, int y, int color) {
/* 601 */     int IVX = 10;
/* 602 */     int IVY = 10;
/* 603 */     int SZX = 5;
/* 604 */     int SZY = 5;
/* 605 */     double[] ls = { (x - IVX), (y - IVY), (x - SZX), (y - SZY), (x - IVX), (y + IVY), (x - SZX), (y + SZY), (x + IVX), (y - IVY), (x + SZX), (y - SZY), (x + IVX), (y + IVY), (x + SZX), (y + SZY) };
/*     */ 
/*     */     
/* 608 */     drawLine(ls, color, 1);
/*     */   }
/*     */   
/*     */   public void drawLine(double[] line, int color, int mode) {
/* 612 */     GL11.glPushMatrix();
/* 613 */     GL11.glEnable(3042);
/* 614 */     GL11.glDisable(3553);
/* 615 */     GL11.glBlendFunc(770, 771);
/* 616 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/* 617 */     Tessellator tessellator = Tessellator.getInstance();
/* 618 */     BufferBuilder buffer = tessellator.getBuffer();
/* 619 */     buffer.begin(mode, DefaultVertexFormats.POSITION);
/*     */     
/* 621 */     for (int i = 0; i < line.length; i += 2) {
/* 622 */       buffer.pos(line[i], line[i + 1], this.zLevel).endVertex();
/*     */     }
/*     */     
/* 625 */     tessellator.draw();
/* 626 */     GL11.glEnable(3553);
/* 627 */     GL11.glDisable(3042);
/* 628 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 629 */     GL11.glPopMatrix();
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
/*     */   public void drawTexturedModalRectRotate(double left, double top, double width, double height, double uLeft, double vTop, double uWidth, double vHeight, float rot, double texWidth, double texHeight) {
/* 645 */     GL11.glPushMatrix();
/* 646 */     GL11.glTranslated(left + width / 2.0D, top + height / 2.0D, 0.0D);
/* 647 */     GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
/* 648 */     float fw = (float)(1.0D / texWidth);
/* 649 */     float fh = (float)(1.0D / texHeight);
/* 650 */     Tessellator tessellator = Tessellator.getInstance();
/* 651 */     BufferBuilder buffer = tessellator.getBuffer();
/* 652 */     buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 653 */     buffer.pos(-width / 2.0D, height / 2.0D, this.zLevel).tex(uLeft * fw, (vTop + vHeight) * fh).endVertex();
/* 654 */     buffer.pos(width / 2.0D, height / 2.0D, this.zLevel).tex((uLeft + uWidth) * fw, (vTop + vHeight) * fh).endVertex();
/* 655 */     buffer.pos(width / 2.0D, -height / 2.0D, this.zLevel).tex((uLeft + uWidth) * fw, vTop * fh).endVertex();
/* 656 */     buffer.pos(-width / 2.0D, -height / 2.0D, this.zLevel).tex(uLeft * fw, vTop * fh).endVertex();
/* 657 */     tessellator.draw();
/* 658 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gui\MCH_ConfigGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */