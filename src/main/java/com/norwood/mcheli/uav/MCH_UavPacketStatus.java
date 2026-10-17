/*    */ package com.norwood.mcheli.uav;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MCH_UavPacketStatus
/*    */   extends MCH_Packet {
/* 10 */   public byte posUavX = 0;
/* 11 */   public byte posUavY = 0;
/* 12 */   public byte posUavZ = 0;
/*    */   
/*    */   public boolean continueControl = false;
/*    */   
/*    */   public int getMessageID() {
/* 17 */     return 537133072;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 23 */       this.posUavX = data.readByte();
/* 24 */       this.posUavY = data.readByte();
/* 25 */       this.posUavZ = data.readByte();
/* 26 */       this.continueControl = (data.readByte() != 0);
/* 27 */     } catch (Exception var3) {
/* 28 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 35 */       dos.writeByte(this.posUavX);
/* 36 */       dos.writeByte(this.posUavY);
/* 37 */       dos.writeByte(this.posUavZ);
/* 38 */       dos.writeByte(this.continueControl ? 1 : 0);
/* 39 */     } catch (IOException var3) {
/* 40 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mchel\\uav\MCH_UavPacketStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */