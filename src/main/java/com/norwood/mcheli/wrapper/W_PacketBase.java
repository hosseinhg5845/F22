/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.google.common.io.ByteStreams;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ 
/*    */ public class W_PacketBase implements IMessage {
/*    */   ByteArrayDataInput data;
/*    */   
/*    */   public byte[] createData() {
/* 12 */     return null;
/*    */   }
/*    */   
/*    */   public void fromBytes(ByteBuf buf) {
/* 16 */     byte[] dst = new byte[(buf.array()).length - 1];
/* 17 */     buf.getBytes(0, dst);
/* 18 */     this.data = ByteStreams.newDataInput(dst);
/*    */   }
/*    */   
/*    */   public void toBytes(ByteBuf buf) {
/* 22 */     buf.writeBytes(createData());
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_PacketBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */