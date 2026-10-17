/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MCH_OStream extends ByteArrayOutputStream {
/*    */   public static final int SIZE = 30720;
/*  9 */   public int index = 0;
/*    */   
/*    */   public void write(DataOutputStream dos) {
/*    */     try {
/*    */       int datasize;
/* 14 */       if (this.index + 30720 <= size()) {
/* 15 */         datasize = 30720;
/*    */       } else {
/* 17 */         datasize = size() - this.index;
/*    */       } 
/*    */       
/* 20 */       dos.writeInt(this.index);
/* 21 */       dos.writeInt(datasize);
/* 22 */       dos.writeInt(size());
/* 23 */       dos.write(this.buf, this.index, datasize);
/* 24 */       this.index += datasize;
/* 25 */     } catch (IOException var3) {
/* 26 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isDataEnd() {
/* 31 */     return (this.index >= size());
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_OStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */