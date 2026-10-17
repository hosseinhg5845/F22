/*    */ package com.norwood.mcheli.helper;
/*    */ 
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class MCH_Logger {
/*    */   private static Logger logger;
/*    */   
/*    */   public static void setLogger(Logger loggerIn) {
/*  9 */     if (logger == null) {
/* 10 */       logger = loggerIn;
/*    */     }
/*    */   }
/*    */   
/*    */   public static Logger get() {
/* 15 */     return logger;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\MCH_Logger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */