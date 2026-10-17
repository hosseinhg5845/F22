/*    */ package com.norwood.mcheli.hud;
/*    */ 
/*    */ public class MCH_HudItemColor extends MCH_HudItem {
/*    */   private final String updateColor;
/*    */   
/*    */   public MCH_HudItemColor(int fileLine, String newColor) {
/*  7 */     super(fileLine);
/*  8 */     this.updateColor = newColor;
/*    */   }
/*    */   
/*    */   public static MCH_HudItemColor createByParams(int fileLine, String[] prm) {
/* 12 */     if (prm.length == 1) {
/* 13 */       return new MCH_HudItemColor(fileLine, toFormula(prm[0]));
/*    */     }
/* 15 */     return (prm.length == 4) ? 
/* 16 */       new MCH_HudItemColor(fileLine, "((" + 
/*    */         
/* 18 */         toFormula(prm[0]) + ")<<24)|((" + toFormula(prm[1]) + ")<<16)|((" + toFormula(prm[2]) + ")<<8 )|((" + toFormula(prm[3]) + ")<<0 )") : 
/*    */       
/* 20 */       null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute() {
/* 26 */     double d = calc(this.updateColor);
/* 27 */     long l = (long)d;
/* 28 */     MCH_HudItem.colorSetting = (int)l;
/* 29 */     updateVarMapItem("color", getColor());
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */