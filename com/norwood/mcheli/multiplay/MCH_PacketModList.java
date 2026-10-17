/*    */ package com.norwood.mcheli.multiplay;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class MCH_PacketModList extends MCH_Packet {
/*    */   public boolean firstData = false;
/* 14 */   public int id = 0;
/* 15 */   public int num = 0;
/* 16 */   public final List<String> list = new ArrayList<>();
/*    */   
/*    */   public static void send(EntityPlayer player, MCH_PacketModList p) {
/* 19 */     W_Network.sendToPlayer((W_PacketBase)p, player);
/*    */   }
/*    */   
/*    */   public static void send(List<String> list, int id) {
/* 23 */     MCH_PacketModList p = null;
/* 24 */     int size = 0;
/* 25 */     boolean isFirst = true;
/*    */     
/* 27 */     for (String s : list) {
/* 28 */       if (p == null) {
/* 29 */         p = new MCH_PacketModList();
/* 30 */         p.id = id;
/* 31 */         p.firstData = isFirst;
/* 32 */         isFirst = false;
/*    */       } 
/*    */       
/* 35 */       p.list.add(s);
/* 36 */       size += s.length() + 2;
/* 37 */       if (size > 1024) {
/* 38 */         p.num = p.list.size();
/* 39 */         W_Network.sendToServer((W_PacketBase)p);
/* 40 */         p = null;
/* 41 */         size = 0;
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     if (p != null) {
/* 46 */       p.num = p.list.size();
/* 47 */       W_Network.sendToServer((W_PacketBase)p);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 53 */     return 536873473;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 59 */       this.firstData = (data.readByte() == 1);
/* 60 */       this.id = data.readInt();
/* 61 */       this.num = data.readInt();
/*    */       
/* 63 */       for (int i = 0; i < this.num; i++) {
/* 64 */         this.list.add(data.readUTF());
/*    */       }
/* 66 */     } catch (Exception var3) {
/* 67 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 74 */       dos.writeByte(this.firstData ? 1 : 0);
/* 75 */       dos.writeInt(this.id);
/* 76 */       dos.writeInt(this.num);
/*    */       
/* 78 */       for (String s : this.list) {
/* 79 */         dos.writeUTF(s);
/*    */       }
/* 81 */     } catch (Exception var4) {
/* 82 */       var4.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_PacketModList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */