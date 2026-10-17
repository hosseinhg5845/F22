/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MCH_PacketSeatPlayerControl
/*    */   extends MCH_Packet {
/*    */   public boolean isUnmount = false;
/* 11 */   public byte switchSeat = 0;
/*    */   
/*    */   public boolean parachuting;
/*    */   
/*    */   public int getMessageID() {
/* 16 */     return 536875040;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 22 */       byte bf = data.readByte();
/* 23 */       this.isUnmount = ((bf >> 3 & 0x1) != 0);
/* 24 */       this.switchSeat = (byte)(bf >> 1 & 0x3);
/* 25 */       this.parachuting = ((bf >> 0 & 0x1) != 0);
/* 26 */     } catch (Exception var3) {
/* 27 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 34 */       byte bf = (byte)((this.isUnmount ? 8 : 0) | this.switchSeat << 1 | (this.parachuting ? 1 : 0));
/* 35 */       dos.writeByte(bf);
/* 36 */     } catch (IOException var3) {
/* 37 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketSeatPlayerControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */