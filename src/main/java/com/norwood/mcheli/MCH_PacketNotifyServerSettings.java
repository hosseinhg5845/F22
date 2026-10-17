/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.helper.MCH_Utils;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ 
/*    */ public class MCH_PacketNotifyServerSettings extends MCH_Packet {
/*    */   public boolean enableCamDistChange = true;
/*    */   public boolean enableEntityMarker = true;
/*    */   public boolean enablePVP = true;
/* 16 */   public double stingerLockRange = 120.0D;
/*    */   public boolean enableDebugBoundingBox = true;
/*    */   public boolean enableRotationLimit = false;
/* 19 */   public byte pitchLimitMax = 10;
/* 20 */   public byte pitchLimitMin = 10;
/* 21 */   public byte rollLimit = 35;
/*    */   
/*    */   public static void send(@Nullable EntityPlayerMP player) {
/* 24 */     MCH_PacketNotifyServerSettings s = new MCH_PacketNotifyServerSettings();
/* 25 */     s.enableCamDistChange = !MCH_Config.DisableCameraDistChange.prmBool;
/* 26 */     s.enableEntityMarker = MCH_Config.DisplayEntityMarker.prmBool;
/* 27 */     s.enablePVP = MCH_Utils.getServer().func_71219_W();
/* 28 */     s.stingerLockRange = MCH_Config.StingerLockRange.prmDouble;
/* 29 */     s.enableDebugBoundingBox = MCH_Config.EnableDebugBoundingBox.prmBool;
/* 30 */     s.enableRotationLimit = MCH_Config.EnableRotationLimit.prmBool;
/* 31 */     s.pitchLimitMax = (byte)MCH_Config.PitchLimitMax.prmInt;
/* 32 */     s.pitchLimitMin = (byte)MCH_Config.PitchLimitMin.prmInt;
/* 33 */     s.rollLimit = (byte)MCH_Config.RollLimit.prmInt;
/* 34 */     if (player != null) {
/* 35 */       W_Network.sendToPlayer(s, (EntityPlayer)player);
/*    */     } else {
/* 37 */       W_Network.sendToAllPlayers(s);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void sendAll() {
/* 42 */     send((EntityPlayerMP)null);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 47 */     return 268437568;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 53 */       byte b = data.readByte();
/* 54 */       this.enableCamDistChange = getBit(b, 0);
/* 55 */       this.enableEntityMarker = getBit(b, 1);
/* 56 */       this.enablePVP = getBit(b, 2);
/* 57 */       this.enableDebugBoundingBox = getBit(b, 3);
/* 58 */       this.enableRotationLimit = getBit(b, 4);
/* 59 */       this.stingerLockRange = data.readFloat();
/* 60 */       this.pitchLimitMax = data.readByte();
/* 61 */       this.pitchLimitMin = data.readByte();
/* 62 */       this.rollLimit = data.readByte();
/* 63 */     } catch (Exception var3) {
/* 64 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 71 */       byte b = 0;
/* 72 */       b = setBit(b, 0, this.enableCamDistChange);
/* 73 */       b = setBit(b, 1, this.enableEntityMarker);
/* 74 */       b = setBit(b, 2, this.enablePVP);
/* 75 */       b = setBit(b, 3, this.enableDebugBoundingBox);
/* 76 */       b = setBit(b, 4, this.enableRotationLimit);
/* 77 */       dos.writeByte(b);
/* 78 */       dos.writeFloat((float)this.stingerLockRange);
/* 79 */       dos.writeByte(this.pitchLimitMax);
/* 80 */       dos.writeByte(this.pitchLimitMin);
/* 81 */       dos.writeByte(this.rollLimit);
/* 82 */     } catch (IOException var3) {
/* 83 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_PacketNotifyServerSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */