/*    */ package com.norwood.mcheli.hud;
/*    */ 
/*    */ public class MCH_HudItemCall extends MCH_HudItem {
/*    */   private final String hudName;
/*    */   
/*    */   public MCH_HudItemCall(int fileLine, String name) {
/*  7 */     super(fileLine);
/*  8 */     this.hudName = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute() {
/* 13 */     MCH_Hud hud = MCH_HudManager.get(this.hudName);
/* 14 */     if (hud != null)
/* 15 */       hud.drawItems(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */