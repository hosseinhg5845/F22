/*    */ package com.norwood.mcheli.hud;
/*    */ 
/*    */ public class MCH_HudItemLineStipple extends MCH_HudItem {
/*    */   private final String pat;
/*    */   private final String fac;
/*    */   private final String[] pos;
/*    */   
/*    */   public MCH_HudItemLineStipple(int fileLine, String[] position) {
/*  9 */     super(fileLine);
/* 10 */     this.pat = position[0];
/* 11 */     this.fac = position[1];
/* 12 */     this.pos = new String[position.length - 2];
/*    */     
/* 14 */     for (int i = 0; i < position.length - 2; i++) {
/* 15 */       this.pos[i] = toFormula(position[2 + i]);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute() {
/* 21 */     double[] lines = new double[this.pos.length];
/*    */     
/* 23 */     for (int i = 0; i < lines.length; i += 2) {
/* 24 */       lines[i] = centerX + calc(this.pos[i]);
/* 25 */       lines[i + 1] = centerY + calc(this.pos[i + 1]);
/*    */     } 
/*    */     
/* 28 */     drawLineStipple(lines, colorSetting, (int)calc(this.fac), (int)calc(this.pat));
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemLineStipple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */