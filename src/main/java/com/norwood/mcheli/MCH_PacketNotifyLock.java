/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class MCH_PacketNotifyLock
/*    */   extends MCH_Packet
/*    */ {
/* 13 */   public int entityID = -1;
/*    */   
/*    */   public static void send(Entity target) {
/* 16 */     if (target != null) {
/* 17 */       MCH_PacketNotifyLock s = new MCH_PacketNotifyLock();
/* 18 */       s.entityID = target.func_145782_y();
/* 19 */       W_Network.sendToServer(s);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void sendToPlayer(EntityPlayer entity) {
/* 24 */     if (entity instanceof net.minecraft.entity.player.EntityPlayerMP) {
/* 25 */       MCH_PacketNotifyLock s = new MCH_PacketNotifyLock();
/* 26 */       W_Network.sendToPlayer(s, entity);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 32 */     return 536873984;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 38 */       this.entityID = data.readInt();
/* 39 */     } catch (Exception var3) {
/* 40 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 47 */       dos.writeInt(this.entityID);
/* 48 */     } catch (IOException var3) {
/* 49 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_PacketNotifyLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */