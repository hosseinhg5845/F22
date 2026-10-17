/*    */ package com.norwood.mcheli.aircraft;
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
/*    */ public class MCH_PacketSeatListResponse extends MCH_Packet {
/* 13 */   public int entityID_AC = -1;
/* 14 */   public byte seatNum = -1;
/* 15 */   public int[] seatEntityID = new int[] { -1 };
/*    */   
/*    */   public static void sendSeatList(MCH_EntityAircraft ac, EntityPlayer player) {
/* 18 */     MCH_PacketSeatListResponse s = new MCH_PacketSeatListResponse();
/* 19 */     s.setParameter(ac);
/* 20 */     W_Network.sendToPlayer((W_PacketBase)s, player);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 25 */     return 268439569;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 31 */       this.entityID_AC = data.readInt();
/* 32 */       this.seatNum = data.readByte();
/* 33 */       if (this.seatNum > 0) {
/* 34 */         this.seatEntityID = new int[this.seatNum];
/*    */         
/* 36 */         for (int i = 0; i < this.seatNum; i++) {
/* 37 */           this.seatEntityID[i] = data.readInt();
/*    */         }
/*    */       } 
/* 40 */     } catch (Exception var3) {
/* 41 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 48 */       dos.writeInt(this.entityID_AC);
/* 49 */       if (this.seatNum > 0 && this.seatEntityID != null && this.seatEntityID.length == this.seatNum) {
/* 50 */         dos.writeByte(this.seatNum);
/*    */         
/* 52 */         for (int i = 0; i < this.seatNum; i++) {
/* 53 */           dos.writeInt(this.seatEntityID[i]);
/*    */         }
/*    */       } else {
/* 56 */         dos.writeByte(-1);
/*    */       } 
/* 58 */     } catch (IOException var3) {
/* 59 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void setParameter(MCH_EntityAircraft ac) {
/* 64 */     if (ac != null) {
/* 65 */       this.entityID_AC = W_Entity.getEntityId((Entity)ac);
/* 66 */       this.seatNum = (byte)(ac.getSeats()).length;
/* 67 */       if (this.seatNum > 0) {
/* 68 */         this.seatEntityID = new int[this.seatNum];
/*    */         
/* 70 */         for (int i = 0; i < this.seatNum; i++) {
/* 71 */           this.seatEntityID[i] = W_Entity.getEntityId((Entity)ac.getSeat(i));
/*    */         }
/*    */       } else {
/* 74 */         this.seatEntityID = new int[] { -1 };
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketSeatListResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */