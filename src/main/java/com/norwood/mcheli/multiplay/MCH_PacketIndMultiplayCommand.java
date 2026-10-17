/*    */ package com.norwood.mcheli.multiplay;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MCH_PacketIndMultiplayCommand extends MCH_Packet {
/* 11 */   public int CmdID = -1;
/*    */   public String CmdStr;
/*    */   
/*    */   public static void send(int cmd_id, String str) {
/* 15 */     if (cmd_id > 0) {
/* 16 */       MCH_PacketIndMultiplayCommand s = new MCH_PacketIndMultiplayCommand();
/* 17 */       s.CmdID = cmd_id;
/* 18 */       s.CmdStr = str;
/* 19 */       W_Network.sendToServer((W_PacketBase)s);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 25 */     return 536873088;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 31 */       this.CmdID = data.readInt();
/* 32 */       this.CmdStr = data.readUTF();
/* 33 */     } catch (Exception var3) {
/* 34 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 41 */       dos.writeInt(this.CmdID);
/* 42 */       dos.writeUTF(this.CmdStr);
/* 43 */     } catch (IOException var3) {
/* 44 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_PacketIndMultiplayCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */