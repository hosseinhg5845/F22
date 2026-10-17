/*    */ package com.norwood.mcheli.command;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MCH_PacketCommandSave extends MCH_Packet {
/* 11 */   public String str = "";
/*    */   
/*    */   public static void send(String cmd) {
/* 14 */     MCH_PacketCommandSave s = new MCH_PacketCommandSave();
/* 15 */     s.str = cmd;
/* 16 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 21 */     return 536873729;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 27 */       this.str = data.readUTF();
/* 28 */     } catch (Exception var3) {
/* 29 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 36 */       dos.writeUTF(this.str);
/* 37 */     } catch (IOException var3) {
/* 38 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\command\MCH_PacketCommandSave.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */