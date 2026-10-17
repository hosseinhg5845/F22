/*    */ package com.norwood.mcheli.hud;
/*    */ 
/*    */ public class MCH_HudItemLine extends MCH_HudItem {
/*    */   private final String[] pos;
/*    */   
/*    */   public MCH_HudItemLine(int fileLine, String[] position) {
/*  7 */     super(fileLine);
/*  8 */     this.pos = new String[position.length];
/*    */     
/* 10 */     for (int i = 0; i < position.length; i++) {
/* 11 */       this.pos[i] = position[i].toLowerCase();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute() {
/* 17 */     double[] lines = new double[this.pos.length];
/*    */     
/* 19 */     for (int i = 0; i < lines.length; i += 2) {
/* 20 */       lines[i] = centerX + calc(this.pos[i]);
/* 21 */       lines[i + 1] = centerY + calc(this.pos[i + 1]);
/*    */     } 
/*    */     
/* 24 */     drawLine(lines, colorSetting, 3);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */