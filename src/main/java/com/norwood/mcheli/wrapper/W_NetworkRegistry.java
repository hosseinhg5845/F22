/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import com.norwood.mcheli.helper.network.MCH_WrapPacketData;
/*    */ import com.norwood.mcheli.helper.network.MCH_WrapPacketHandler;
/*    */ import net.minecraftforge.fml.common.network.IGuiHandler;
/*    */ import net.minecraftforge.fml.common.network.NetworkRegistry;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class W_NetworkRegistry
/*    */ {
/*    */   public static void registerChannel(W_PacketHandler handler, String name) {
/* 13 */     packetHandler = handler;
/* 14 */     W_Network.INSTANCE.registerMessage((IMessageHandler)new MCH_WrapPacketHandler(), MCH_WrapPacketData.class, 0, Side.SERVER);
/* 15 */     W_Network.INSTANCE.registerMessage((IMessageHandler)new MCH_WrapPacketHandler(), MCH_WrapPacketData.class, 0, Side.CLIENT);
/*    */   } public static W_PacketHandler packetHandler;
/*    */   public static void registerGuiHandler(Object mod, IGuiHandler handler) {
/* 18 */     NetworkRegistry.INSTANCE.registerGuiHandler(mod, handler);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_NetworkRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */