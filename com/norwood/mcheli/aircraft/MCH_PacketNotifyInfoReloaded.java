/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MCH_PacketNotifyInfoReloaded extends MCH_Packet {
/* 11 */   public int type = -1;
/*    */   
/*    */   public static void sendRealodAc() {
/* 14 */     MCH_PacketNotifyInfoReloaded s = new MCH_PacketNotifyInfoReloaded();
/* 15 */     s.type = 0;
/* 16 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */   
/*    */   public static void sendRealodAllWeapon() {
/* 20 */     MCH_PacketNotifyInfoReloaded s = new MCH_PacketNotifyInfoReloaded();
/* 21 */     s.type = 1;
/* 22 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 27 */     return 536875063;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 33 */       this.type = data.readInt();
/* 34 */     } catch (Exception var3) {
/* 35 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 42 */       dos.writeInt(this.type);
/* 43 */     } catch (IOException var3) {
/* 44 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketNotifyInfoReloaded.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */