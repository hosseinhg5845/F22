/*    */ package com.norwood.mcheli.hud;
/*    */ 
/*    */ public enum MCH_HudItemStringArgs {
/*  4 */   NONE,
/*  5 */   NAME,
/*  6 */   ALTITUDE,
/*  7 */   DATE,
/*  8 */   MC_THOR,
/*  9 */   MC_TMIN,
/* 10 */   MC_TSEC,
/* 11 */   MAX_HP,
/* 12 */   HP,
/* 13 */   HP_PER,
/* 14 */   POS_X,
/* 15 */   POS_Y,
/* 16 */   POS_Z,
/* 17 */   MOTION_X,
/* 18 */   MOTION_Y,
/* 19 */   MOTION_Z,
/* 20 */   INVENTORY,
/* 21 */   WPN_NAME,
/* 22 */   WPN_AMMO,
/* 23 */   WPN_RM_AMMO,
/* 24 */   RELOAD_PER,
/* 25 */   RELOAD_SEC,
/* 26 */   MORTAR_DIST,
/* 27 */   MC_VER,
/* 28 */   MOD_VER,
/* 29 */   MOD_NAME,
/* 30 */   YAW,
/* 31 */   PITCH,
/* 32 */   ROLL,
/* 33 */   PLYR_YAW,
/* 34 */   PLYR_PITCH,
/* 35 */   TVM_POS_X,
/* 36 */   TVM_POS_Y,
/* 37 */   TVM_POS_Z,
/* 38 */   TVM_DIFF,
/* 39 */   CAM_ZOOM,
/* 40 */   UAV_DIST,
/* 41 */   KEY_GUI,
/* 42 */   THROTTLE;
/*    */   
/*    */   public static MCH_HudItemStringArgs toArgs(String name) {
/* 45 */     MCH_HudItemStringArgs a = NONE;
/*    */     
/*    */     try {
/* 48 */       a = valueOf(name.toUpperCase());
/* 49 */     } catch (Exception var6) {
/* 50 */       var6.printStackTrace();
/*    */     } 
/*    */     
/* 53 */     return a;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemStringArgs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */