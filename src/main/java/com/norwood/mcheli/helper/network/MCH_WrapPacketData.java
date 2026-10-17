/*    */ package com.norwood.mcheli.helper.network;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.google.common.io.ByteStreams;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ 
/*    */ public class MCH_WrapPacketData implements IMessage {
/*    */   private byte[] data;
/*    */   
/*    */   public MCH_WrapPacketData() {
/* 13 */     this.data = new byte[0];
/*    */   }
/*    */   
/*    */   public MCH_WrapPacketData(W_PacketBase packet) {
/* 17 */     this.data = packet.createData();
/*    */   }
/*    */   
/*    */   public void fromBytes(ByteBuf buf) {
/* 21 */     this.data = new byte[buf.capacity()];
/* 22 */     buf.getBytes(0, this.data);
/*    */   }
/*    */   
/*    */   public void toBytes(ByteBuf buf) {
/* 26 */     buf.writeBytes(this.data);
/*    */   }
/*    */   
/*    */   public ByteArrayDataInput createData() {
/* 30 */     return ByteStreams.newDataInput(this.data);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\network\MCH_WrapPacketData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */