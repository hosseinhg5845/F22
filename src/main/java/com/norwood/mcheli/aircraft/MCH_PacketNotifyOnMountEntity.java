/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Entity;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class MCH_PacketNotifyOnMountEntity extends MCH_Packet {
/* 14 */   public int entityID_Ac = -1;
/* 15 */   public int entityID_rider = -1;
/* 16 */   public int seatID = -1;
/*    */   
/*    */   public static void send(MCH_EntityAircraft ac, Entity rider, int seatId) {
/* 19 */     if (ac != null && rider != null) {
/* 20 */       Entity pilot = ac.getRiddenByEntity();
/* 21 */       if (pilot instanceof EntityPlayer && !pilot.isDead) {
/* 22 */         MCH_PacketNotifyOnMountEntity s = new MCH_PacketNotifyOnMountEntity();
/* 23 */         s.entityID_Ac = W_Entity.getEntityId((Entity)ac);
/* 24 */         s.entityID_rider = W_Entity.getEntityId(rider);
/* 25 */         s.seatID = seatId;
/* 26 */         W_Network.sendToPlayer((W_PacketBase)s, (EntityPlayer)pilot);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 33 */     return 268439632;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 39 */       this.entityID_Ac = data.readInt();
/* 40 */       this.entityID_rider = data.readInt();
/* 41 */       this.seatID = data.readByte();
/* 42 */     } catch (Exception var3) {
/* 43 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 50 */       dos.writeInt(this.entityID_Ac);
/* 51 */       dos.writeInt(this.entityID_rider);
/* 52 */       dos.writeByte(this.seatID);
/* 53 */     } catch (IOException var3) {
/* 54 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketNotifyOnMountEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */