/*    */ package com.norwood.mcheli.multiplay;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class MCH_PacketNotifySpotedEntity
/*    */   extends MCH_Packet {
/*    */   private static final int MAX_COUNT = 30000;
/*    */   private static final int MAX_ENTITIES = 300;
/* 15 */   public int count = 0;
/* 16 */   public int num = 0;
/* 17 */   public int[] entityId = new int[0];
/*    */   
/*    */   public static void send(EntityPlayer player, int count, int[] entityIds) {
/* 20 */     if (player == null || entityIds == null || entityIds.length == 0 || count <= 0)
/*    */       return; 
/* 22 */     int limitedCount = Math.min(count, 30000);
/* 23 */     int limitedNum = Math.min(entityIds.length, 300);
/*    */     
/* 25 */     MCH_PacketNotifySpotedEntity pkt = new MCH_PacketNotifySpotedEntity();
/* 26 */     pkt.count = limitedCount;
/* 27 */     pkt.num = limitedNum;
/* 28 */     pkt.entityId = Arrays.copyOf(entityIds, limitedNum);
/*    */     
/* 30 */     W_Network.sendToPlayer((W_PacketBase)pkt, player);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 35 */     return 268437761;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 41 */       this.count = data.readUnsignedShort();
/* 42 */       this.num = Math.min(data.readUnsignedShort(), 300);
/*    */       
/* 44 */       this.entityId = new int[this.num];
/* 45 */       for (int i = 0; i < this.num; i++) {
/* 46 */         this.entityId[i] = data.readInt();
/*    */       }
/* 48 */     } catch (Exception e) {
/* 49 */       e.printStackTrace();
/* 50 */       this.count = 0;
/* 51 */       this.num = 0;
/* 52 */       this.entityId = new int[0];
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 59 */       dos.writeShort(Math.min(this.count, 30000));
/* 60 */       dos.writeShort(Math.min(this.num, 300));
/*    */       
/* 62 */       for (int i = 0; i < Math.min(this.num, this.entityId.length); i++) {
/* 63 */         dos.writeInt(this.entityId[i]);
/*    */       }
/* 65 */     } catch (Exception e) {
/* 66 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_PacketNotifySpotedEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */