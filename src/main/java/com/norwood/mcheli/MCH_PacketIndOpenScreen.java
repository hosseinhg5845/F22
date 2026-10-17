/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MCH_PacketIndOpenScreen
/*    */   extends MCH_Packet {
/* 10 */   public int guiID = -1;
/*    */   
/*    */   public static void send(int gui_id) {
/* 13 */     if (gui_id >= 0) {
/* 14 */       MCH_PacketIndOpenScreen s = new MCH_PacketIndOpenScreen();
/* 15 */       s.guiID = gui_id;
/* 16 */       W_Network.sendToServer(s);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 22 */     return 536872992;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 28 */       this.guiID = data.readInt();
/* 29 */     } catch (Exception var3) {
/* 30 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 37 */       dos.writeInt(this.guiID);
/* 38 */     } catch (IOException var3) {
/* 39 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_PacketIndOpenScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */