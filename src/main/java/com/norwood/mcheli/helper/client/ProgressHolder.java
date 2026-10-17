/*    */ package com.norwood.mcheli.helper.client;
/*    */ 
/*    */ import net.minecraftforge.fml.common.ProgressManager;
/*    */ 
/*    */ public class ProgressHolder
/*    */ {
/*    */   private static ProgressManager.ProgressBar currentBar;
/*    */   
/*    */   public static void push(String title, int steps) {
/* 10 */     currentBar = ProgressManager.push(title, steps);
/*    */   }
/*    */   
/*    */   public static void step(String message) {
/* 14 */     currentBar.step(message);
/*    */   }
/*    */   
/*    */   public static void pop() {
/* 18 */     ProgressManager.pop(currentBar);
/* 19 */     currentBar = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\ProgressHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */