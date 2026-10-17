/*    */ package com.norwood.mcheli.multiplay;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_PacketNotifyMarkPoint
/*    */   extends MCH_Packet
/*    */ {
/* 16 */   public int px = this.pz = 0;
/* 17 */   public int py = 0;
/*    */   public int pz;
/*    */   
/*    */   public static void send(EntityPlayer player, int x, int y, int z) {
/* 21 */     MCH_PacketNotifyMarkPoint pkt = new MCH_PacketNotifyMarkPoint();
/* 22 */     pkt.px = x;
/* 23 */     pkt.py = y;
/* 24 */     pkt.pz = z;
/* 25 */     W_Network.sendToPlayer((W_PacketBase)pkt, player);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 30 */     return 268437762;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 36 */       this.px = data.readInt();
/* 37 */       this.py = data.readInt();
/* 38 */       this.pz = data.readInt();
/* 39 */     } catch (Exception var3) {
/* 40 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 47 */       dos.writeInt(this.px);
/* 48 */       dos.writeInt(this.py);
/* 49 */       dos.writeInt(this.pz);
/* 50 */     } catch (Exception var3) {
/* 51 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_PacketNotifyMarkPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */