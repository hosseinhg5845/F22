/*     */ package com.norwood.mcheli.hud;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_ClientCommonTickHandler;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_LowPassFilterFloat;
/*     */ import com.norwood.mcheli.MCH_Vector2;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.eval.eval.ExpRuleFactory;
/*     */ import com.norwood.mcheli.eval.eval.Expression;
/*     */ import com.norwood.mcheli.eval.eval.var.MapVariable;
/*     */ import com.norwood.mcheli.eval.eval.var.Variable;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityTvMissile;
/*     */ import com.norwood.mcheli.weapon.MCH_SightType;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponBase;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponInfo;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponSet;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.W_OpenGlHelper;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public abstract class MCH_HudItem extends Gui {
/*     */   public static Minecraft mc;
/*     */   public static EntityPlayer player;
/*     */   public static MCH_EntityAircraft ac;
/*  39 */   public static int colorSetting = -16777216; public static double width; public static double height; public static int scaleFactor;
/*  40 */   protected static double centerX = 0.0D;
/*  41 */   protected static double centerY = 0.0D;
/*  42 */   protected static Random rand = new Random();
/*  43 */   protected static int altitudeUpdateCount = 0;
/*  44 */   protected static int Altitude = 0;
/*     */   protected static float prevRadarRot;
/*  46 */   protected static String WeaponName = "";
/*  47 */   protected static String WeaponAmmo = "";
/*  48 */   protected static String WeaponAllAmmo = "";
/*  49 */   protected static MCH_WeaponSet CurrentWeapon = null;
/*  50 */   protected static float ReloadPer = 0.0F;
/*  51 */   protected static float ReloadSec = 0.0F;
/*  52 */   protected static float MortarDist = 0.0F;
/*  53 */   protected static final MCH_LowPassFilterFloat StickX_LPF = new MCH_LowPassFilterFloat(4);
/*  54 */   protected static final MCH_LowPassFilterFloat StickY_LPF = new MCH_LowPassFilterFloat(4);
/*     */   protected static double StickX;
/*     */   protected static double StickY;
/*     */   protected static double TVM_PosX;
/*     */   protected static double TVM_PosY;
/*     */   protected static double TVM_PosZ;
/*     */   protected static double TVM_Diff;
/*     */   protected static double UAV_Dist;
/*     */   protected static int countFuelWarn;
/*     */   protected static ArrayList<MCH_Vector2> EntityList;
/*     */   protected static ArrayList<MCH_Vector2> EnemyList;
/*  65 */   protected static Map<Object, Object> varMap = null;
/*     */   protected static float partialTicks;
/*  67 */   private static final MCH_HudItemExit dummy = new MCH_HudItemExit(0);
/*     */   public final int fileLine;
/*     */   protected MCH_Hud parent;
/*     */   
/*     */   public MCH_HudItem(int fileLine) {
/*  72 */     this.fileLine = fileLine;
/*  73 */     this.field_73735_i = -110.0F;
/*     */   }
/*     */   
/*     */   public static void update() {
/*  77 */     MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)player);
/*  78 */     updateRadar(ac);
/*  79 */     updateStick();
/*  80 */     updateAltitude(ac);
/*  81 */     updateTvMissile(ac);
/*  82 */     updateUAV(ac);
/*  83 */     updateWeapon(ac, ws);
/*  84 */     updateVarMap(ac, ws);
/*     */   }
/*     */   
/*     */   public static String toFormula(String s) {
/*  88 */     return s.toLowerCase().replaceAll("#", "0x").replace("\t", " ").replace(" ", "");
/*     */   }
/*     */   
/*     */   public static double calc(String s) {
/*  92 */     Expression exp = ExpRuleFactory.getDefaultRule().parse(s);
/*  93 */     exp.setVariable((Variable)new MapVariable(varMap));
/*  94 */     return exp.evalDouble();
/*     */   }
/*     */   
/*     */   public static long calcLong(String s) {
/*  98 */     Expression exp = ExpRuleFactory.getDefaultRule().parse(s);
/*  99 */     exp.setVariable((Variable)new MapVariable(varMap));
/* 100 */     return exp.evalLong();
/*     */   }
/*     */   
/*     */   public static void drawRect(double par0, double par1, double par2, double par3, int par4) {
/* 104 */     if (par0 < par2) {
/* 105 */       double j1 = par0;
/* 106 */       par0 = par2;
/* 107 */       par2 = j1;
/*     */     } 
/*     */     
/* 110 */     if (par1 < par3) {
/* 111 */       double j1 = par1;
/* 112 */       par1 = par3;
/* 113 */       par3 = j1;
/*     */     } 
/*     */     
/* 116 */     float f3 = (par4 >> 24 & 0xFF) / 255.0F;
/* 117 */     float f = (par4 >> 16 & 0xFF) / 255.0F;
/* 118 */     float f1 = (par4 >> 8 & 0xFF) / 255.0F;
/* 119 */     float f2 = (par4 & 0xFF) / 255.0F;
/* 120 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 121 */     BufferBuilder builder = tessellator.func_178180_c();
/* 122 */     GL11.glEnable(3042);
/* 123 */     GL11.glDisable(3553);
/* 124 */     W_OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 125 */     GL11.glColor4f(f, f1, f2, f3);
/* 126 */     builder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 127 */     builder.func_181662_b(par0, par3, 0.0D).func_181675_d();
/* 128 */     builder.func_181662_b(par2, par3, 0.0D).func_181675_d();
/* 129 */     builder.func_181662_b(par2, par1, 0.0D).func_181675_d();
/* 130 */     builder.func_181662_b(par0, par1, 0.0D).func_181675_d();
/* 131 */     tessellator.func_78381_a();
/* 132 */     GL11.glEnable(3553);
/* 133 */     GL11.glDisable(3042);
/*     */   }
/*     */   
/*     */   public static void updateVarMap(MCH_EntityAircraft ac, MCH_WeaponSet ws) {
/* 137 */     if (varMap == null) {
/* 138 */       varMap = new LinkedHashMap<>();
/*     */     }
/*     */     
/* 141 */     updateVarMapItem("color", getColor());
/* 142 */     updateVarMapItem("center_x", centerX);
/* 143 */     updateVarMapItem("center_y", centerY);
/* 144 */     updateVarMapItem("width", width);
/* 145 */     updateVarMapItem("height", height);
/* 146 */     updateVarMapItem("time", (player.field_70170_p.func_72820_D() % 24000L));
/* 147 */     updateVarMapItem("test_mode", MCH_Config.TestMode.prmBool ? 1.0D : 0.0D);
/* 148 */     updateVarMapItem("plyr_yaw", MathHelper.func_76142_g(player.field_70177_z));
/* 149 */     updateVarMapItem("plyr_pitch", player.field_70125_A);
/* 150 */     updateVarMapItem("yaw", MathHelper.func_76142_g(ac.getRotYaw()));
/* 151 */     updateVarMapItem("pitch", ac.getRotPitch());
/* 152 */     updateVarMapItem("roll", MathHelper.func_76142_g(ac.getRotRoll()));
/* 153 */     updateVarMapItem("altitude", Altitude);
/* 154 */     updateVarMapItem("sea_alt", getSeaAltitude(ac));
/* 155 */     updateVarMapItem("have_radar", ac.isEntityRadarMounted() ? 1.0D : 0.0D);
/* 156 */     updateVarMapItem("radar_rot", getRadarRot(ac));
/* 157 */     updateVarMapItem("hp", ac.getHP());
/* 158 */     updateVarMapItem("max_hp", ac.getMaxHP());
/* 159 */     updateVarMapItem("hp_rto", (ac.getMaxHP() > 0) ? (ac.getHP() / ac.getMaxHP()) : 0.0D);
/* 160 */     updateVarMapItem("throttle", ac.getCurrentThrottle());
/* 161 */     updateVarMapItem("pos_x", ac.field_70165_t);
/* 162 */     updateVarMapItem("pos_y", ac.field_70163_u);
/* 163 */     updateVarMapItem("pos_z", ac.field_70161_v);
/* 164 */     updateVarMapItem("motion_x", ac.field_70159_w);
/* 165 */     updateVarMapItem("motion_y", ac.field_70181_x);
/* 166 */     updateVarMapItem("motion_z", ac.field_70179_y);
/* 167 */     updateVarMapItem("speed", Math.sqrt(ac.field_70159_w * ac.field_70159_w + ac.field_70181_x * ac.field_70181_x + ac.field_70179_y * ac.field_70179_y));
/* 168 */     updateVarMapItem("fuel", ac.getFuelP());
/* 169 */     updateVarMapItem("low_fuel", isLowFuel(ac));
/* 170 */     updateVarMapItem("stick_x", StickX);
/* 171 */     updateVarMapItem("stick_y", StickY);
/* 172 */     updateVarMap_Weapon(ws);
/* 173 */     updateVarMapItem("vtol_stat", getVtolStat(ac));
/* 174 */     updateVarMapItem("free_look", getFreeLook(ac, player));
/* 175 */     updateVarMapItem("gunner_mode", ac.getIsGunnerMode((Entity)player) ? 1.0D : 0.0D);
/* 176 */     updateVarMapItem("cam_mode", ac.getCameraMode(player));
/* 177 */     updateVarMapItem("cam_zoom", ac.camera.getCameraZoom());
/* 178 */     updateVarMapItem("auto_pilot", getAutoPilot(ac, player));
/* 179 */     updateVarMapItem("have_flare", ac.haveFlare() ? 1.0D : 0.0D);
/* 180 */     updateVarMapItem("can_flare", ac.canUseFlare() ? 1.0D : 0.0D);
/* 181 */     updateVarMapItem("inventory", ac.func_70302_i_());
/* 182 */     updateVarMapItem("hovering", (ac instanceof com.norwood.mcheli.helicopter.MCH_EntityHeli && ac.isHoveringMode()) ? 1.0D : 0.0D);
/* 183 */     updateVarMapItem("is_uav", ac.isUAV() ? 1.0D : 0.0D);
/* 184 */     updateVarMapItem("uav_fs", getUAV_Fs(ac));
/*     */   }
/*     */   
/*     */   public static void updateVarMapItem(String key, double value) {
/* 188 */     varMap.put(key, Double.valueOf(value));
/*     */   }
/*     */   
/*     */   public static void drawVarMap() {
/* 192 */     if (MCH_Config.TestMode.prmBool) {
/* 193 */       int i = 0;
/* 194 */       int x = (int)(-300.0D + centerX);
/* 195 */       int y = (int)(-100.0D + centerY);
/*     */       
/* 197 */       for (Object keyObj : varMap.keySet()) {
/* 198 */         String key = (String)keyObj;
/* 199 */         dummy.drawString(key, x, y, 52992);
/* 200 */         Double d = (Double)varMap.get(key);
/* 201 */         String fmt = key.equalsIgnoreCase("color") ? String.format(": 0x%08X", new Object[] { Integer.valueOf(d.intValue()) }) : String.format(": %.2f", new Object[] { d });
/* 202 */         dummy.drawString(fmt, x + 50, y, 52992);
/* 203 */         i++;
/* 204 */         y += 8;
/* 205 */         if (i == varMap.size() / 2) {
/* 206 */           x = (int)(200.0D + centerX);
/* 207 */           y = (int)(-100.0D + centerY);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static double getUAV_Fs(MCH_EntityAircraft ac) {
/* 214 */     double uav_fs = 0.0D;
/* 215 */     if (ac.isUAV() && ac.getUavStation() != null) {
/* 216 */       double dx = ac.field_70165_t - (ac.getUavStation()).field_70165_t;
/* 217 */       double dz = ac.field_70161_v - (ac.getUavStation()).field_70161_v;
/* 218 */       float dist = (float)Math.sqrt(dx * dx + dz * dz);
/* 219 */       if (dist > 120.0F) {
/* 220 */         dist = 120.0F;
/*     */       }
/*     */       
/* 223 */       uav_fs = (1.0F - dist / 120.0F);
/*     */     } 
/*     */     
/* 226 */     return uav_fs;
/*     */   }
/*     */   
/*     */   private static void updateVarMap_Weapon(MCH_WeaponSet ws) {
/* 230 */     int reloading = 0;
/* 231 */     double wpn_heat = 0.0D;
/* 232 */     int is_heat_wpn = 0;
/* 233 */     int sight_type = 0;
/* 234 */     double lock = 0.0D;
/* 235 */     float rel_time = 0.0F;
/* 236 */     int display_mortar_dist = 0;
/* 237 */     if (ws != null) {
/* 238 */       MCH_WeaponBase wb = ws.getCurrentWeapon();
/* 239 */       MCH_WeaponInfo wi = wb.getInfo();
/* 240 */       if (wi == null) {
/*     */         return;
/*     */       }
/*     */       
/* 244 */       is_heat_wpn = (wi.maxHeatCount > 0) ? 1 : 0;
/* 245 */       reloading = ws.isInPreparation() ? 1 : 0;
/* 246 */       display_mortar_dist = wi.displayMortarDistance ? 1 : 0;
/* 247 */       if (wi.delay > wi.reloadTime) {
/* 248 */         rel_time = ws.countWait / ((wi.delay > 0) ? wi.delay : true);
/* 249 */         if (rel_time < 0.0F) {
/* 250 */           rel_time = -rel_time;
/*     */         }
/*     */         
/* 253 */         if (rel_time > 1.0F) {
/* 254 */           rel_time = 1.0F;
/*     */         }
/*     */       } else {
/* 257 */         rel_time = ws.countReloadWait / ((wi.reloadTime > 0) ? wi.reloadTime : true);
/*     */       } 
/*     */       
/* 260 */       if (wi.maxHeatCount > 0) {
/* 261 */         double hpp = ws.currentHeat / wi.maxHeatCount;
/* 262 */         wpn_heat = Math.min(hpp, 1.0D);
/*     */       } 
/*     */       
/* 265 */       int cntLockMax = wb.getLockCountMax();
/* 266 */       MCH_SightType sight = wb.getSightType();
/* 267 */       if (sight == MCH_SightType.LOCK && cntLockMax > 0) {
/* 268 */         lock = wb.getLockCount() / cntLockMax;
/* 269 */         sight_type = 2;
/*     */       } 
/*     */       
/* 272 */       if (sight == MCH_SightType.ROCKET) {
/* 273 */         sight_type = 1;
/*     */       }
/*     */     } 
/*     */     
/* 277 */     updateVarMapItem("reloading", reloading);
/* 278 */     updateVarMapItem("reload_time", rel_time);
/* 279 */     updateVarMapItem("wpn_heat", wpn_heat);
/* 280 */     updateVarMapItem("is_heat_wpn", is_heat_wpn);
/* 281 */     updateVarMapItem("sight_type", sight_type);
/* 282 */     updateVarMapItem("lock", lock);
/* 283 */     updateVarMapItem("dsp_mt_dist", display_mortar_dist);
/* 284 */     updateVarMapItem("mt_dist", MortarDist);
/*     */   }
/*     */   
/*     */   public static int isLowFuel(MCH_EntityAircraft ac) {
/* 288 */     int is_low_fuel = 0;
/* 289 */     if (countFuelWarn <= 0) {
/* 290 */       countFuelWarn = 280;
/*     */     }
/*     */     
/* 293 */     countFuelWarn--;
/* 294 */     if (countFuelWarn < 160 && ac.getMaxFuel() > 0 && ac.getFuelP() < 0.1F && !ac.isInfinityFuel((Entity)player, false)) {
/* 295 */       is_low_fuel = 1;
/*     */     }
/*     */     
/* 298 */     return is_low_fuel;
/*     */   }
/*     */   
/*     */   public static double getSeaAltitude(MCH_EntityAircraft ac) {
/* 302 */     double a = ac.field_70163_u - ac.field_70170_p.func_72919_O();
/* 303 */     return Math.max(a, 0.0D);
/*     */   }
/*     */   
/*     */   public static float getRadarRot(MCH_EntityAircraft ac) {
/* 307 */     float rot = ac.getRadarRotate();
/* 308 */     float prevRot = prevRadarRot;
/* 309 */     if (rot < prevRot) {
/* 310 */       rot += 360.0F;
/*     */     }
/*     */     
/* 313 */     prevRadarRot = ac.getRadarRotate();
/* 314 */     return MCH_Lib.smooth(rot, prevRot, partialTicks);
/*     */   }
/*     */   
/*     */   public static int getVtolStat(MCH_EntityAircraft ac) {
/* 318 */     return (ac instanceof com.norwood.mcheli.plane.MCP_EntityPlane) ? ac.getVtolMode() : 0;
/*     */   }
/*     */   
/*     */   public static int getFreeLook(MCH_EntityAircraft ac, EntityPlayer player) {
/* 322 */     return (ac.isPilot((Entity)player) && ac.canSwitchFreeLook() && ac.isFreeLookMode()) ? 1 : 0;
/*     */   }
/*     */   
/*     */   public static int getAutoPilot(MCH_EntityAircraft ac, EntityPlayer player) {
/* 326 */     return (ac instanceof com.norwood.mcheli.plane.MCP_EntityPlane && ac.isPilot((Entity)player) && ac.getIsGunnerMode((Entity)player)) ? 1 : 0;
/*     */   }
/*     */   
/*     */   public static double getColor() {
/* 330 */     long l = colorSetting;
/* 331 */     l &= 0xFFFFFFFFFFFFFFFFL;
/* 332 */     return l;
/*     */   }
/*     */   
/*     */   private static void updateStick() {
/* 336 */     StickX_LPF.put((float)(MCH_ClientCommonTickHandler.getCurrentStickX() / MCH_ClientCommonTickHandler.getMaxStickLength()));
/* 337 */     StickY_LPF.put((float)(-MCH_ClientCommonTickHandler.getCurrentStickY() / MCH_ClientCommonTickHandler.getMaxStickLength()));
/* 338 */     StickX = StickX_LPF.getAvg();
/* 339 */     StickY = StickY_LPF.getAvg();
/*     */   }
/*     */   
/*     */   private static void updateRadar(MCH_EntityAircraft ac) {
/* 343 */     EntityList = ac.getRadarEntityList();
/* 344 */     EnemyList = ac.getRadarEnemyList();
/*     */   }
/*     */   
/*     */   private static void updateAltitude(MCH_EntityAircraft ac) {
/* 348 */     if (altitudeUpdateCount <= 0) {
/* 349 */       int heliY = (int)ac.field_70163_u;
/* 350 */       if (heliY > 256) {
/* 351 */         heliY = 256;
/*     */       }
/*     */       
/* 354 */       for (int i = 0; i < 256 && heliY - i > 0; i++) {
/* 355 */         int id = W_WorldFunc.getBlockId(ac.field_70170_p, (int)ac.field_70165_t, heliY - i, (int)ac.field_70161_v);
/* 356 */         if (id != 0) {
/* 357 */           Altitude = i;
/* 358 */           if (ac.field_70163_u > 256.0D) {
/* 359 */             Altitude = (int)(Altitude + ac.field_70163_u - 256.0D);
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 365 */       altitudeUpdateCount = 30;
/*     */     } else {
/* 367 */       altitudeUpdateCount--;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void updateWeapon(MCH_EntityAircraft ac, MCH_WeaponSet ws) {
/* 372 */     if (ac.getWeaponNum() > 0 && 
/* 373 */       ws != null) {
/* 374 */       CurrentWeapon = ws;
/* 375 */       WeaponName = ac.isPilotReloading() ? "-- Reloading --" : ws.getName();
/* 376 */       if (ws.getAmmoNumMax() > 0) {
/* 377 */         WeaponAmmo = ac.isPilotReloading() ? "----" : String.format("%4d", new Object[] { Integer.valueOf(ws.getAmmoNum()) });
/* 378 */         WeaponAllAmmo = ac.isPilotReloading() ? "----" : String.format("%4d", new Object[] { Integer.valueOf(ws.getRestAllAmmoNum()) });
/*     */       } else {
/* 380 */         WeaponAmmo = "";
/* 381 */         WeaponAllAmmo = "";
/*     */       } 
/*     */       
/* 384 */       MCH_WeaponInfo wi = ws.getInfo();
/* 385 */       if (wi.displayMortarDistance) {
/* 386 */         MortarDist = (float)ac.getLandInDistance((Entity)player);
/*     */       } else {
/* 388 */         MortarDist = -1.0F;
/*     */       } 
/*     */       
/* 391 */       if (wi.delay > wi.reloadTime) {
/* 392 */         ReloadSec = (ws.countWait >= 0) ? ws.countWait : -ws.countWait;
/* 393 */         ReloadPer = ws.countWait / ((wi.delay > 0) ? wi.delay : true);
/* 394 */         if (ReloadPer < 0.0F) {
/* 395 */           ReloadPer = -ReloadPer;
/*     */         }
/*     */         
/* 398 */         if (ReloadPer > 1.0F) {
/* 399 */           ReloadPer = 1.0F;
/*     */         }
/*     */       } else {
/* 402 */         ReloadSec = ws.countReloadWait;
/* 403 */         ReloadPer = ws.countReloadWait / ((wi.reloadTime > 0) ? wi.reloadTime : true);
/*     */       } 
/*     */       
/* 406 */       ReloadSec /= 20.0F;
/* 407 */       ReloadPer = (1.0F - ReloadPer) * 100.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateUAV(MCH_EntityAircraft ac) {
/* 413 */     if (ac.isUAV() && ac.getUavStation() != null) {
/* 414 */       double dx = ac.field_70165_t - (ac.getUavStation()).field_70165_t;
/* 415 */       double dz = ac.field_70161_v - (ac.getUavStation()).field_70161_v;
/* 416 */       UAV_Dist = (float)Math.sqrt(dx * dx + dz * dz);
/*     */     } else {
/* 418 */       UAV_Dist = 0.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void updateTvMissile(MCH_EntityAircraft ac) {
/* 423 */     MCH_EntityTvMissile mCH_EntityTvMissile = ac.getTVMissile();
/* 424 */     if (mCH_EntityTvMissile != null) {
/* 425 */       TVM_PosX = ((Entity)mCH_EntityTvMissile).field_70165_t;
/* 426 */       TVM_PosY = ((Entity)mCH_EntityTvMissile).field_70163_u;
/* 427 */       TVM_PosZ = ((Entity)mCH_EntityTvMissile).field_70161_v;
/* 428 */       double dx = ((Entity)mCH_EntityTvMissile).field_70165_t - ac.field_70165_t;
/* 429 */       double dy = ((Entity)mCH_EntityTvMissile).field_70163_u - ac.field_70163_u;
/* 430 */       double dz = ((Entity)mCH_EntityTvMissile).field_70161_v - ac.field_70161_v;
/* 431 */       TVM_Diff = Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */     } else {
/* 433 */       TVM_PosX = 0.0D;
/* 434 */       TVM_PosY = 0.0D;
/* 435 */       TVM_PosZ = 0.0D;
/* 436 */       TVM_Diff = 0.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void execute();
/*     */   
/*     */   public boolean canExecute() {
/* 443 */     return !this.parent.isIfFalse;
/*     */   }
/*     */   
/*     */   public void drawCenteredString(String s, int x, int y, int color) {
/* 447 */     func_73732_a(mc.field_71466_p, s, x, y, color);
/*     */   }
/*     */   
/*     */   public void drawString(String s, int x, int y, int color) {
/* 451 */     func_73731_b(mc.field_71466_p, s, x, y, color);
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
/*     */   public void drawTexture(String name, double left, double top, double width, double height, double uLeft, double vTop, double uWidth, double vHeight, float rot, int textureWidth, int textureHeight) {
/* 468 */     W_McClient.MOD_bindTexture("textures/gui/" + name + ".png");
/* 469 */     GL11.glPushMatrix();
/* 470 */     GL11.glTranslated(left + width / 2.0D, top + height / 2.0D, 0.0D);
/* 471 */     GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
/* 472 */     float fx = (float)(1.0D / textureWidth);
/* 473 */     float fy = (float)(1.0D / textureHeight);
/* 474 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 475 */     BufferBuilder builder = tessellator.func_178180_c();
/* 476 */     builder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 477 */     builder.func_181662_b(-width / 2.0D, height / 2.0D, this.field_73735_i).func_187315_a(uLeft * fx, (vTop + vHeight) * fy).func_181675_d();
/* 478 */     builder.func_181662_b(width / 2.0D, height / 2.0D, this.field_73735_i).func_187315_a((uLeft + uWidth) * fx, (vTop + vHeight) * fy).func_181675_d();
/* 479 */     builder.func_181662_b(width / 2.0D, -height / 2.0D, this.field_73735_i).func_187315_a((uLeft + uWidth) * fx, vTop * fy).func_181675_d();
/* 480 */     builder.func_181662_b(-width / 2.0D, -height / 2.0D, this.field_73735_i).func_187315_a(uLeft * fx, vTop * fy).func_181675_d();
/* 481 */     tessellator.func_78381_a();
/* 482 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void drawLine(double[] line, int color) {
/* 486 */     drawLine(line, color, 1);
/*     */   }
/*     */   
/*     */   public void drawLine(double[] line, int color, int mode) {
/* 490 */     GL11.glPushMatrix();
/* 491 */     GL11.glEnable(3042);
/* 492 */     GL11.glDisable(3553);
/* 493 */     GL11.glBlendFunc(770, 771);
/* 494 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/* 495 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 496 */     BufferBuilder builder = tessellator.func_178180_c();
/* 497 */     builder.func_181668_a(mode, DefaultVertexFormats.field_181705_e);
/*     */     
/* 499 */     for (int i = 0; i < line.length; i += 2) {
/* 500 */       builder.func_181662_b(line[i], line[i + 1], this.field_73735_i).func_181675_d();
/*     */     }
/*     */     
/* 503 */     tessellator.func_78381_a();
/* 504 */     GL11.glEnable(3553);
/* 505 */     GL11.glDisable(3042);
/* 506 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 507 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void drawLineStipple(double[] line, int color, int factor, int pattern) {
/* 511 */     GL11.glEnable(2852);
/* 512 */     GL11.glLineStipple(factor * scaleFactor, (short)pattern);
/* 513 */     drawLine(line, color);
/* 514 */     GL11.glDisable(2852);
/*     */   }
/*     */   
/*     */   public void drawPoints(ArrayList<Double> points, int color, int pointWidth) {
/* 518 */     int prevWidth = GL11.glGetInteger(2833);
/* 519 */     GL11.glPushMatrix();
/* 520 */     GL11.glEnable(3042);
/* 521 */     GL11.glDisable(3553);
/* 522 */     GL11.glBlendFunc(770, 771);
/* 523 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/* 524 */     GL11.glPointSize(pointWidth);
/* 525 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 526 */     BufferBuilder builder = tessellator.func_178180_c();
/* 527 */     builder.func_181668_a(0, DefaultVertexFormats.field_181705_e);
/*     */     
/* 529 */     for (int i = 0; i < points.size(); i += 2) {
/* 530 */       builder.func_181662_b(((Double)points.get(i)).doubleValue(), ((Double)points.get(i + 1)).doubleValue(), 0.0D).func_181675_d();
/*     */     }
/*     */     
/* 533 */     tessellator.func_78381_a();
/* 534 */     GL11.glEnable(3553);
/* 535 */     GL11.glDisable(3042);
/* 536 */     GL11.glPopMatrix();
/* 537 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 538 */     GL11.glPointSize(prevWidth);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */