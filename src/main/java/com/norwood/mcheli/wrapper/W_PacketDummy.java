package com.norwood.mcheli.wrapper;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class W_PacketDummy implements IMessage {
  public void fromBytes(ByteBuf buf) {}
  
  public void toBytes(ByteBuf buf) {}
}


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_PacketDummy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */