/*    */ package com.norwood.mcheli.helper.info;
/*    */ 
/*    */ public enum ContentType {
/*  4 */   HELICOPTER("helicopter", "helicopters"),
/*  5 */   PLANE("plane", "plane"),
/*  6 */   TANK("tank", "tanks"),
/*  7 */   VEHICLE("vehicle", "vehicles"),
/*  8 */   WEAPON("weapon", "weapons"),
/*  9 */   THROWABLE("throwable", "throwable"),
/* 10 */   HUD("hud", "hud");
/*    */   
/*    */   public final String type;
/*    */   public final String dirName;
/*    */   
/*    */   ContentType(String typeName, String dirName) {
/* 16 */     this.type = typeName;
/* 17 */     this.dirName = dirName;
/*    */   }
/*    */   
/*    */   public static boolean validateDirName(String dirName) {
/* 21 */     for (ContentType type : values()) {
/* 22 */       if (type.dirName.equals(dirName)) {
/* 23 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 27 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\info\ContentType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */