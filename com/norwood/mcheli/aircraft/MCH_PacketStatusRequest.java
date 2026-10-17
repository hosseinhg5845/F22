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
/*    */ public class MCH_PacketStatusRequest extends MCH_Packet {
/* 12 */   public int entityID_AC = -1;
/*    */   
/*    */   public static void requestStatus(MCH_EntityAircraft ac) {
/* 15 */     if (ac.field_70170_p.field_72995_K) {
/* 16 */       MCH_PacketStatusRequest s = new MCH_PacketStatusRequest();
/* 17 */       s.entityID_AC = W_Entity.getEntityId((Entity)ac);
/* 18 */       W_Network.sendToServer((W_PacketBase)s);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 24 */     return 536875104;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 30 */       this.entityID_AC = data.readInt();
/* 31 */     } catch (Exception var3) {
/* 32 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 39 */       dos.writeInt(this.entityID_AC);
/* 40 */     } catch (IOException var3) {
/* 41 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketStatusRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */