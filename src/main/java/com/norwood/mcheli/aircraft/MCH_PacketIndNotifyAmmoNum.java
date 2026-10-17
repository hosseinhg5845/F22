/*    */ package com.norwood.mcheli.aircraft;
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Entity;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class MCH_PacketIndNotifyAmmoNum extends MCH_Packet {
/* 12 */   public int entityID_Ac = -1;
/* 13 */   public byte weaponID = -1;
/*    */   
/*    */   public static void send(MCH_EntityAircraft ac, int wid) {
/* 16 */     MCH_PacketIndNotifyAmmoNum s = new MCH_PacketIndNotifyAmmoNum();
/* 17 */     s.entityID_Ac = W_Entity.getEntityId((Entity)ac);
/* 18 */     s.weaponID = (byte)wid;
/* 19 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 24 */     return 536875061;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 30 */       this.entityID_Ac = data.readInt();
/* 31 */       this.weaponID = data.readByte();
/* 32 */     } catch (Exception var3) {
/* 33 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 40 */       dos.writeInt(this.entityID_Ac);
/* 41 */       dos.writeByte(this.weaponID);
/* 42 */     } catch (IOException var3) {
/* 43 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketIndNotifyAmmoNum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */