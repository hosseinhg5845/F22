/*    */ package com.norwood.mcheli.lweapon;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MCH_PacketLightWeaponPlayerControl
/*    */   extends MCH_Packet {
/*    */   public boolean useWeapon = false;
/* 11 */   public int useWeaponOption1 = 0;
/* 12 */   public int useWeaponOption2 = 0;
/* 13 */   public double useWeaponPosX = 0.0D;
/* 14 */   public double useWeaponPosY = 0.0D;
/* 15 */   public double useWeaponPosZ = 0.0D;
/* 16 */   public int cmpReload = 0;
/* 17 */   public int camMode = 0;
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 21 */     return 536936464;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 27 */       this.useWeapon = (data.readByte() != 0);
/* 28 */       if (this.useWeapon) {
/* 29 */         this.useWeaponOption1 = data.readInt();
/* 30 */         this.useWeaponOption2 = data.readInt();
/* 31 */         this.useWeaponPosX = data.readDouble();
/* 32 */         this.useWeaponPosY = data.readDouble();
/* 33 */         this.useWeaponPosZ = data.readDouble();
/*    */       } 
/*    */       
/* 36 */       this.cmpReload = data.readByte();
/* 37 */       this.camMode = data.readByte();
/* 38 */     } catch (Exception var3) {
/* 39 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 46 */       dos.writeByte(this.useWeapon ? 1 : 0);
/* 47 */       if (this.useWeapon) {
/* 48 */         dos.writeInt(this.useWeaponOption1);
/* 49 */         dos.writeInt(this.useWeaponOption2);
/* 50 */         dos.writeDouble(this.useWeaponPosX);
/* 51 */         dos.writeDouble(this.useWeaponPosY);
/* 52 */         dos.writeDouble(this.useWeaponPosZ);
/*    */       } 
/*    */       
/* 55 */       dos.writeByte(this.cmpReload);
/* 56 */       dos.writeByte(this.camMode);
/* 57 */     } catch (IOException var3) {
/* 58 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\lweapon\MCH_PacketLightWeaponPlayerControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */