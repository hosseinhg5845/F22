/*    */ package com.norwood.mcheli.hud;
/*    */ 
/*    */ public class MCH_HudItemConditional extends MCH_HudItem {
/*    */   private final boolean isEndif;
/*    */   private final String conditional;
/*    */   
/*    */   public MCH_HudItemConditional(int fileLine, boolean isEndif, String conditional) {
/*  8 */     super(fileLine);
/*  9 */     this.isEndif = isEndif;
/* 10 */     this.conditional = conditional;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute() {
/* 15 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute() {
/* 20 */     if (!this.isEndif) {
/* 21 */       this.parent.isIfFalse = (calc(this.conditional) == 0.0D);
/*    */     } else {
/* 23 */       this.parent.isIfFalse = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemConditional.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */