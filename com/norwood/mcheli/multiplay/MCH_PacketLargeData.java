/*    */ package com.norwood.mcheli.multiplay;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class MCH_PacketLargeData extends MCH_Packet {
/* 10 */   public int imageDataIndex = -1;
/* 11 */   public int imageDataSize = 0;
/* 12 */   public int imageDataTotalSize = 0;
/*    */   public byte[] buf;
/*    */   
/*    */   public static void send() {
/* 16 */     MCH_PacketLargeData p = new MCH_PacketLargeData();
/* 17 */     W_Network.sendToServer((W_PacketBase)p);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 22 */     return 536873472;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 28 */       this.imageDataIndex = data.readInt();
/* 29 */       this.imageDataSize = data.readInt();
/* 30 */       this.imageDataTotalSize = data.readInt();
/* 31 */       this.buf = new byte[this.imageDataSize];
/* 32 */       data.readFully(this.buf);
/* 33 */     } catch (Exception var3) {
/* 34 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 41 */       MCH_MultiplayClient.readImageData(dos);
/* 42 */     } catch (Exception var3) {
/* 43 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_PacketLargeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */