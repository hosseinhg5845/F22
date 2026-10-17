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
/*    */ public class MCH_PacketSeatListRequest extends MCH_Packet {
/* 12 */   public int entityID_AC = -1;
/*    */   
/*    */   public static void requestSeatList(MCH_EntityAircraft ac) {
/* 15 */     MCH_PacketSeatListRequest s = new MCH_PacketSeatListRequest();
/* 16 */     s.entityID_AC = W_Entity.getEntityId((Entity)ac);
/* 17 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 22 */     return 536875024;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 28 */       this.entityID_AC = data.readInt();
/* 29 */     } catch (Exception var3) {
/* 30 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 37 */       dos.writeInt(this.entityID_AC);
/* 38 */     } catch (IOException var3) {
/* 39 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketSeatListRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */