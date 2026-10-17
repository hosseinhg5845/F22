/*    */ package com.norwood.mcheli.helicopter;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.aircraft.MCH_PacketPlayerControlBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MCH_HeliPacketPlayerControl
/*    */   extends MCH_PacketPlayerControlBase {
/* 10 */   public byte switchFold = -1;
/* 11 */   public int unhitchChainId = -1;
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 15 */     return 536879120;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/* 20 */     super.readData(data);
/*    */     
/*    */     try {
/* 23 */       this.switchFold = data.readByte();
/* 24 */       this.unhitchChainId = data.readInt();
/* 25 */     } catch (Exception var3) {
/* 26 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/* 32 */     super.writeData(dos);
/*    */     
/*    */     try {
/* 35 */       dos.writeByte(this.switchFold);
/* 36 */       dos.writeInt(this.unhitchChainId);
/* 37 */     } catch (IOException var3) {
/* 38 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helicopter\MCH_HeliPacketPlayerControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */