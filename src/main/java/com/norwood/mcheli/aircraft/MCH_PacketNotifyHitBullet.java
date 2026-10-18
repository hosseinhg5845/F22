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
/*    */ public class MCH_PacketNotifyHitBullet extends MCH_Packet {
/* 13 */   public int entityID_Ac = -1;
/*    */   
/*    */   public static void send(MCH_EntityAircraft ac, EntityPlayer rider) {
/* 16 */     if (rider != null && !rider.isDead) {
/* 17 */       MCH_PacketNotifyHitBullet s = new MCH_PacketNotifyHitBullet();
/* 18 */       s.entityID_Ac = (ac != null && !ac.isDead) ? W_Entity.getEntityId((Entity)ac) : -1;
/* 19 */       W_Network.sendToPlayer((W_PacketBase)s, rider);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 25 */     return 268439602;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 31 */       this.entityID_Ac = data.readInt();
/* 32 */     } catch (Exception var3) {
/* 33 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 40 */       dos.writeInt(this.entityID_Ac);
/* 41 */     } catch (IOException var3) {
/* 42 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketNotifyHitBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */