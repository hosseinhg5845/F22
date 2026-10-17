/*    */ package com.norwood.mcheli.hud;
/*    */ 
/*    */ public class MCH_HudItemExit extends MCH_HudItem {
/*    */   public MCH_HudItemExit(int fileLine) {
/*  5 */     super(fileLine);
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute() {
/* 10 */     this.parent.exit = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemExit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */