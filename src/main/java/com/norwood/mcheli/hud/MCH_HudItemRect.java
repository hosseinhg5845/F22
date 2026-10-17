/*    */ package com.norwood.mcheli.hud;
/*    */ 
/*    */ public class MCH_HudItemRect extends MCH_HudItem {
/*    */   private final String left;
/*    */   private final String top;
/*    */   private final String width;
/*    */   private final String height;
/*    */   
/*    */   public MCH_HudItemRect(int fileLine, String left, String top, String width, String height) {
/* 10 */     super(fileLine);
/* 11 */     this.left = toFormula(left);
/* 12 */     this.top = toFormula(top);
/* 13 */     this.width = toFormula(width);
/* 14 */     this.height = toFormula(height);
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute() {
/* 19 */     double x2 = centerX + calc(this.left);
/* 20 */     double y2 = centerY + calc(this.top);
/* 21 */     double x1 = x2 + (int)calc(this.width);
/* 22 */     double y1 = y2 + (int)calc(this.height);
/* 23 */     drawRect(x1, y1, x2, y2, colorSetting);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemRect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */