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
/*    */ 
/*    */ public class MCH_PacketNotifyWeaponID extends MCH_Packet {
/* 13 */   public int entityID_Ac = -1;
/* 14 */   public int seatID = -1;
/* 15 */   public int weaponID = -1;
/* 16 */   public short ammo = 0;
/* 17 */   public short restAmmo = 0;
/*    */   
/*    */   public static void send(Entity sender, int sid, int wid, int ammo, int rest_ammo) {
/* 20 */     MCH_PacketNotifyWeaponID s = new MCH_PacketNotifyWeaponID();
/* 21 */     s.entityID_Ac = W_Entity.getEntityId(sender);
/* 22 */     s.seatID = sid;
/* 23 */     s.weaponID = wid;
/* 24 */     s.ammo = (short)ammo;
/* 25 */     s.restAmmo = (short)rest_ammo;
/* 26 */     W_Network.sendToAllAround((W_PacketBase)s, sender, 150.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 31 */     return 268439601;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 37 */       this.entityID_Ac = data.readInt();
/* 38 */       this.seatID = data.readByte();
/* 39 */       this.weaponID = data.readByte();
/* 40 */       this.ammo = data.readShort();
/* 41 */       this.restAmmo = data.readShort();
/* 42 */     } catch (Exception var3) {
/* 43 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 50 */       dos.writeInt(this.entityID_Ac);
/* 51 */       dos.writeByte(this.seatID);
/* 52 */       dos.writeByte(this.weaponID);
/* 53 */       dos.writeShort(this.ammo);
/* 54 */       dos.writeShort(this.restAmmo);
/* 55 */     } catch (IOException var3) {
/* 56 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketNotifyWeaponID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */