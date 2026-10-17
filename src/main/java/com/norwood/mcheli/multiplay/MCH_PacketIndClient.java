/*    */ package com.norwood.mcheli.multiplay;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class MCH_PacketIndClient extends MCH_Packet {
/* 12 */   public int CmdID = -1;
/*    */   public String CmdStr;
/*    */   
/*    */   public static void send(EntityPlayer player, int cmd_id, String str) {
/* 16 */     if (cmd_id > 0) {
/* 17 */       MCH_PacketIndClient s = new MCH_PacketIndClient();
/* 18 */       s.CmdID = cmd_id;
/* 19 */       s.CmdStr = str;
/* 20 */       W_Network.sendToPlayer((W_PacketBase)s, player);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 26 */     return 268438032;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 32 */       this.CmdID = data.readInt();
/* 33 */       this.CmdStr = data.readUTF();
/* 34 */     } catch (Exception var3) {
/* 35 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 42 */       dos.writeInt(this.CmdID);
/* 43 */       dos.writeUTF(this.CmdStr);
/* 44 */     } catch (IOException var3) {
/* 45 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_PacketIndClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */