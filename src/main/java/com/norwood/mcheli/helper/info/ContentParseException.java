/*    */ package com.norwood.mcheli.helper.info;
/*    */ 
/*    */ public class ContentParseException extends RuntimeException {
/*    */   private static final long serialVersionUID = 4338814389788695295L;
/*    */   private final int lineNo;
/*    */   
/*    */   public ContentParseException(int lineNo) {
/*  8 */     this.lineNo = lineNo;
/*    */   }
/*    */   
/*    */   public ContentParseException(String msg, int lineNo) {
/* 12 */     super(msg);
/* 13 */     this.lineNo = lineNo;
/*    */   }
/*    */   
/*    */   public ContentParseException(Throwable cause, int lineNo) {
/* 17 */     super(cause);
/* 18 */     this.lineNo = lineNo;
/*    */   }
/*    */   
/*    */   public int getLineNo() {
/* 22 */     return this.lineNo;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\info\ContentParseException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */