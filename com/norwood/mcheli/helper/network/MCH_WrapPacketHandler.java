/*    */ package com.norwood.mcheli.helper.network;
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.wrapper.W_NetworkRegistry;
/*    */ import com.norwood.mcheli.wrapper.W_PacketDummy;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ 
/*    */ public class MCH_WrapPacketHandler implements IMessageHandler<MCH_WrapPacketData, W_PacketDummy> {
/*    */   public W_PacketDummy onMessage(MCH_WrapPacketData message, MessageContext ctx) {
/*    */     try {
/* 14 */       ByteArrayDataInput data = message.createData();
/* 15 */       if (ctx.side.isClient()) {
/* 16 */         if (MCH_Lib.getClientPlayer() != null) {
/* 17 */           W_NetworkRegistry.packetHandler.onPacket(data, (EntityPlayer)MCH_Lib.getClientPlayer(), ctx);
/*    */         }
/*    */       } else {
/* 20 */         W_NetworkRegistry.packetHandler.onPacket(data, (EntityPlayer)(ctx.getServerHandler()).field_147369_b, ctx);
/*    */       } 
/* 22 */     } catch (Exception var4) {
/* 23 */       var4.printStackTrace();
/*    */     } 
/*    */     
/* 26 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\network\MCH_WrapPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */