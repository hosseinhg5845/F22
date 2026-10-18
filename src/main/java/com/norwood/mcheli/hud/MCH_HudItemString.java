/*     */ package com.norwood.mcheli.hud;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_KeyName;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import java.util.Date;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class MCH_HudItemString
/*     */   extends MCH_HudItem {
/*     */   private final String posX;
/*     */   private final String posY;
/*     */   private final String format;
/*     */   private final MCH_HudItemStringArgs[] args;
/*     */   private final boolean isCenteredString;
/*     */   
/*     */   public MCH_HudItemString(int fileLine, String posx, String posy, String fmt, String[] arg, boolean centered) {
/*  19 */     super(fileLine);
/*  20 */     this.posX = posx.toLowerCase();
/*  21 */     this.posY = posy.toLowerCase();
/*  22 */     this.format = fmt;
/*  23 */     int len = (arg.length < 3) ? 0 : (arg.length - 3);
/*  24 */     this.args = new MCH_HudItemStringArgs[len];
/*     */     
/*  26 */     for (int i = 0; i < len; i++) {
/*  27 */       this.args[i] = MCH_HudItemStringArgs.toArgs(arg[3 + i]);
/*     */     }
/*     */     
/*  30 */     this.isCenteredString = centered;
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute() {
/*  35 */     int x = (int)(centerX + calc(this.posX));
/*  36 */     int y = (int)(centerY + calc(this.posY));
/*  37 */     int worldTime = (int)((ac.world.getWorldTime() + 6000L) % 24000L);
/*  38 */     Date date = new Date();
/*  39 */     Object[] prm = new Object[this.args.length];
/*  40 */     double hp_per = (ac.getMaxHP() > 0) ? (ac.getHP() / ac.getMaxHP()) : 0.0D;
/*     */     
/*  42 */     for (int i = 0; i < prm.length; i++) {
/*  43 */       switch (this.args[i]) {
/*     */         case NAME:
/*  45 */           prm[i] = (ac.getAcInfo()).displayName;
/*     */           break;
/*     */         case ALTITUDE:
/*  48 */           prm[i] = Integer.valueOf(Altitude);
/*     */           break;
/*     */         case DATE:
/*  51 */           prm[i] = date;
/*     */           break;
/*     */         case MC_THOR:
/*  54 */           prm[i] = Integer.valueOf(worldTime / 1000);
/*     */           break;
/*     */         case MC_TMIN:
/*  57 */           prm[i] = Integer.valueOf(worldTime % 1000 * 36 / 10 / 60);
/*     */           break;
/*     */         case MC_TSEC:
/*  60 */           prm[i] = Integer.valueOf(worldTime % 1000 * 36 / 10 % 60);
/*     */           break;
/*     */         case MAX_HP:
/*  63 */           prm[i] = Integer.valueOf(ac.getMaxHP());
/*     */           break;
/*     */         case HP:
/*  66 */           prm[i] = Integer.valueOf(ac.getHP());
/*     */           break;
/*     */         case HP_PER:
/*  69 */           prm[i] = Double.valueOf(hp_per * 100.0D);
/*     */           break;
/*     */         case POS_X:
/*  72 */           prm[i] = Double.valueOf(ac.posX);
/*     */           break;
/*     */         case POS_Y:
/*  75 */           prm[i] = Double.valueOf(ac.posY);
/*     */           break;
/*     */         case POS_Z:
/*  78 */           prm[i] = Double.valueOf(ac.posZ);
/*     */           break;
/*     */         case MOTION_X:
/*  81 */           prm[i] = Double.valueOf(ac.motionX);
/*     */           break;
/*     */         case MOTION_Y:
/*  84 */           prm[i] = Double.valueOf(ac.motionY);
/*     */           break;
/*     */         case MOTION_Z:
/*  87 */           prm[i] = Double.valueOf(ac.motionZ);
/*     */           break;
/*     */         case INVENTORY:
/*  90 */           prm[i] = Integer.valueOf(ac.getSizeInventory());
/*     */           break;
/*     */         case WPN_NAME:
/*  93 */           prm[i] = WeaponName;
/*  94 */           if (CurrentWeapon == null) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case WPN_AMMO:
/*  99 */           prm[i] = WeaponAmmo;
/* 100 */           if (CurrentWeapon == null) {
/*     */             return;
/*     */           }
/*     */           
/* 104 */           if (CurrentWeapon.getAmmoNumMax() <= 0) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case WPN_RM_AMMO:
/* 109 */           prm[i] = WeaponAllAmmo;
/* 110 */           if (CurrentWeapon == null) {
/*     */             return;
/*     */           }
/*     */           
/* 114 */           if (CurrentWeapon.getAmmoNumMax() <= 0) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case RELOAD_PER:
/* 119 */           prm[i] = Float.valueOf(ReloadPer);
/* 120 */           if (CurrentWeapon == null) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case RELOAD_SEC:
/* 125 */           prm[i] = Float.valueOf(ReloadSec);
/* 126 */           if (CurrentWeapon == null) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case MORTAR_DIST:
/* 131 */           prm[i] = Float.valueOf(MortarDist);
/* 132 */           if (CurrentWeapon == null) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case MC_VER:
/* 137 */           prm[i] = "1.12.2";
/*     */           break;
/*     */         case MOD_VER:
/* 140 */           prm[i] = MCH_MOD.VER;
/*     */           break;
/*     */         case MOD_NAME:
/* 143 */           prm[i] = "MC Helicopter MOD";
/*     */           break;
/*     */         case YAW:
/* 146 */           prm[i] = Double.valueOf(MCH_Lib.getRotate360((ac.getRotYaw() + 180.0F)));
/*     */           break;
/*     */         case PITCH:
/* 149 */           prm[i] = Float.valueOf(-ac.getRotPitch());
/*     */           break;
/*     */         case ROLL:
/* 152 */           prm[i] = Float.valueOf(MathHelper.wrapDegrees(ac.getRotRoll()));
/*     */           break;
/*     */         case PLYR_YAW:
/* 155 */           prm[i] = Double.valueOf(MCH_Lib.getRotate360((player.rotationYaw + 180.0F)));
/*     */           break;
/*     */         case PLYR_PITCH:
/* 158 */           prm[i] = Float.valueOf(-player.rotationPitch);
/*     */           break;
/*     */         case TVM_POS_X:
/* 161 */           prm[i] = Double.valueOf(TVM_PosX);
/*     */           break;
/*     */         case TVM_POS_Y:
/* 164 */           prm[i] = Double.valueOf(TVM_PosY);
/*     */           break;
/*     */         case TVM_POS_Z:
/* 167 */           prm[i] = Double.valueOf(TVM_PosZ);
/*     */           break;
/*     */         case TVM_DIFF:
/* 170 */           prm[i] = Double.valueOf(TVM_Diff);
/*     */           break;
/*     */         case CAM_ZOOM:
/* 173 */           prm[i] = Float.valueOf(ac.camera.getCameraZoom());
/*     */           break;
/*     */         case UAV_DIST:
/* 176 */           prm[i] = Double.valueOf(UAV_Dist);
/*     */           break;
/*     */         case KEY_GUI:
/* 179 */           prm[i] = MCH_KeyName.getDescOrName(MCH_Config.KeyGUI.prmInt);
/*     */           break;
/*     */         case THROTTLE:
/* 182 */           prm[i] = Double.valueOf(ac.getCurrentThrottle() * 100.0D);
/*     */           break;
/*     */       } 
/*     */     } 
/* 186 */     if (this.isCenteredString) {
/* 187 */       drawCenteredString(String.format(this.format, prm), x, y, colorSetting);
/*     */     } else {
/* 189 */       drawString(String.format(this.format, prm), x, y, colorSetting);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */