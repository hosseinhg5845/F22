/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Config;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_EntityRenderer;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MCH_PacketNotifyClientSetting extends MCH_Packet {
/*    */   public boolean dismountAll = true;
/*    */   public boolean heliAutoThrottleDown;
/*    */   public boolean planeAutoThrottleDown;
/*    */   public boolean tankAutoThrottleDown;
/*    */   public boolean shaderSupport = false;
/*    */   
/*    */   public static void send() {
/* 20 */     MCH_PacketNotifyClientSetting s = new MCH_PacketNotifyClientSetting();
/* 21 */     s.dismountAll = MCH_Config.DismountAll.prmBool;
/* 22 */     s.heliAutoThrottleDown = MCH_Config.AutoThrottleDownHeli.prmBool;
/* 23 */     s.planeAutoThrottleDown = MCH_Config.AutoThrottleDownPlane.prmBool;
/* 24 */     s.tankAutoThrottleDown = MCH_Config.AutoThrottleDownTank.prmBool;
/* 25 */     s.shaderSupport = W_EntityRenderer.isShaderSupport();
/* 26 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 31 */     return 536875072;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput di) {
/*    */     try {
/* 38 */       byte data = di.readByte();
/* 39 */       this.dismountAll = getBit(data, 0);
/* 40 */       this.heliAutoThrottleDown = getBit(data, 1);
/* 41 */       this.planeAutoThrottleDown = getBit(data, 2);
/* 42 */       this.tankAutoThrottleDown = getBit(data, 3);
/* 43 */       this.shaderSupport = getBit(data, 4);
/* 44 */     } catch (Exception var3) {
/* 45 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 52 */       byte data = 0;
/* 53 */       data = setBit(data, 0, this.dismountAll);
/* 54 */       data = setBit(data, 1, this.heliAutoThrottleDown);
/* 55 */       data = setBit(data, 2, this.planeAutoThrottleDown);
/* 56 */       data = setBit(data, 3, this.tankAutoThrottleDown);
/* 57 */       data = setBit(data, 4, this.shaderSupport);
/* 58 */       dos.writeByte(data);
/* 59 */     } catch (IOException var3) {
/* 60 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketNotifyClientSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */