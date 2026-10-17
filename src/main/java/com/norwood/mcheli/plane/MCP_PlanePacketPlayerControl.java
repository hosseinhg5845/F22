/*    */ package com.norwood.mcheli.plane;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.aircraft.MCH_PacketPlayerControlBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MCP_PlanePacketPlayerControl
/*    */   extends MCH_PacketPlayerControlBase {
/* 10 */   public byte switchVtol = -1;
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 14 */     return 536903696;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/* 19 */     super.readData(data);
/*    */     
/*    */     try {
/* 22 */       this.switchVtol = data.readByte();
/* 23 */     } catch (Exception var3) {
/* 24 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/* 30 */     super.writeData(dos);
/*    */     
/*    */     try {
/* 33 */       dos.writeByte(this.switchVtol);
/* 34 */     } catch (IOException var3) {
/* 35 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\plane\MCP_PlanePacketPlayerControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */