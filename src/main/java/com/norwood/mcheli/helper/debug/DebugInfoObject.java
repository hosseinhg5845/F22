/*   */ package com.norwood.mcheli.helper.debug;
/*   */ 
/*   */ import com.norwood.mcheli.debug._v1.PrintStreamWrapper;
/*   */ 
/*   */ public interface DebugInfoObject {
/*   */   void printInfo(PrintStreamWrapper paramPrintStreamWrapper);
/*   */   
/*   */   default void printInfo() {
/* 9 */     printInfo(PrintStreamWrapper.create(System.out));
/*   */   }
/*   */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\debug\DebugInfoObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */