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
/*    */ public class MCH_PacketIndReload extends MCH_Packet {
/* 12 */   public int entityID_Ac = -1;
/* 13 */   public int weaponID = -1;
/*    */   
/*    */   public static void send(MCH_EntityAircraft ac, int weaponId) {
/* 16 */     if (ac != null) {
/* 17 */       MCH_PacketIndReload s = new MCH_PacketIndReload();
/* 18 */       s.entityID_Ac = W_Entity.getEntityId((Entity)ac);
/* 19 */       s.weaponID = weaponId;
/* 20 */       W_Network.sendToServer((W_PacketBase)s);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 26 */     return 536875059;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 32 */       this.entityID_Ac = data.readInt();
/* 33 */       this.weaponID = data.readByte();
/* 34 */     } catch (Exception var3) {
/* 35 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 42 */       dos.writeInt(this.entityID_Ac);
/* 43 */       dos.writeByte(this.weaponID);
/* 44 */     } catch (IOException var3) {
/* 45 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketIndReload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */