/*    */ package com.norwood.mcheli.helper.debug;
/*    */ 
/*    */ public class DebugException extends RuntimeException {
/*    */   private static final long serialVersionUID = 12023042301000021L;
/*    */   
/*    */   public DebugException(String msg) {
/*  7 */     super(msg);
/*    */   }
/*    */   
/*    */   public DebugException(String msg, Throwable cause) {
/* 11 */     super(msg, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\debug\DebugException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */