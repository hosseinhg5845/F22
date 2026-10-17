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
/*    */ public class MCH_PacketIndRotation extends MCH_Packet {
/* 12 */   public int entityID_Ac = -1;
/* 13 */   public float yaw = 0.0F;
/* 14 */   public float pitch = 0.0F;
/* 15 */   public float roll = 0.0F;
/*    */   public boolean rollRev = false;
/*    */   
/*    */   public static void send(MCH_EntityAircraft ac) {
/* 19 */     if (ac != null) {
/* 20 */       MCH_PacketIndRotation s = new MCH_PacketIndRotation();
/* 21 */       s.entityID_Ac = W_Entity.getEntityId((Entity)ac);
/* 22 */       s.yaw = ac.getRotYaw();
/* 23 */       s.pitch = ac.getRotPitch();
/* 24 */       s.roll = ac.getRotRoll();
/* 25 */       s.rollRev = ac.aircraftRollRev;
/* 26 */       W_Network.sendToServer((W_PacketBase)s);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 32 */     return 536875062;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 38 */       this.entityID_Ac = data.readInt();
/* 39 */       this.yaw = data.readFloat();
/* 40 */       this.pitch = data.readFloat();
/* 41 */       this.roll = data.readFloat();
/* 42 */       this.rollRev = (data.readByte() != 0);
/* 43 */     } catch (Exception var3) {
/* 44 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 51 */       dos.writeInt(this.entityID_Ac);
/* 52 */       dos.writeFloat(this.yaw);
/* 53 */       dos.writeFloat(this.pitch);
/* 54 */       dos.writeFloat(this.roll);
/* 55 */       dos.writeByte(this.rollRev ? 1 : 0);
/* 56 */     } catch (IOException var3) {
/* 57 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketIndRotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */