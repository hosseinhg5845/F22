/*    */ package com.norwood.mcheli.multiplay;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ public class MCH_PacketIndSpotEntity extends MCH_Packet {
/* 11 */   public int targetFilter = -1;
/*    */   
/*    */   public static void send(EntityLivingBase spoter, int targetFilter) {
/* 14 */     MCH_PacketIndSpotEntity s = new MCH_PacketIndSpotEntity();
/* 15 */     s.targetFilter = targetFilter;
/* 16 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 21 */     return 536873216;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 27 */       this.targetFilter = data.readInt();
/* 28 */     } catch (Exception var3) {
/* 29 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 36 */       dos.writeInt(this.targetFilter);
/* 37 */     } catch (Exception var3) {
/* 38 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_PacketIndSpotEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */