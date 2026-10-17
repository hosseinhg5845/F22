/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MCH_PacketNotifyTVMissileEntity extends MCH_Packet {
/* 11 */   public int entityID_Ac = -1;
/* 12 */   public int entityID_TVMissile = -1;
/*    */   
/*    */   public static void send(int heliEntityID, int tvMissileEntityID) {
/* 15 */     MCH_PacketNotifyTVMissileEntity s = new MCH_PacketNotifyTVMissileEntity();
/* 16 */     s.entityID_Ac = heliEntityID;
/* 17 */     s.entityID_TVMissile = tvMissileEntityID;
/* 18 */     W_Network.sendToAllPlayers((W_PacketBase)s);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 23 */     return 268439600;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 29 */       this.entityID_Ac = data.readInt();
/* 30 */       this.entityID_TVMissile = data.readInt();
/* 31 */     } catch (Exception var3) {
/* 32 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 39 */       dos.writeInt(this.entityID_Ac);
/* 40 */       dos.writeInt(this.entityID_TVMissile);
/* 41 */     } catch (IOException var3) {
/* 42 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketNotifyTVMissileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */