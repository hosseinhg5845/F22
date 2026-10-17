/*    */ package com.norwood.mcheli.helper.debug;
/*    */ import com.norwood.mcheli.MCH_ClientProxy;
/*    */ import com.norwood.mcheli.MCH_CommonProxy;
/*    */ import com.norwood.mcheli.MCH_MOD;
/*    */ import com.norwood.mcheli.helper.MCH_Logger;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class DebugBootstrap {
/* 10 */   private static final Logger LOGGER = LogManager.getLogger("Debug log");
/*    */   
/*    */   public static void init() {
/* 13 */     MCH_Logger.setLogger(LOGGER);
/* 14 */     MCH_MOD.instance = new MCH_MOD();
/* 15 */     MCH_MOD.proxy = (MCH_CommonProxy)new MCH_ClientProxy();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\debug\DebugBootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */