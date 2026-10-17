/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import com.norwood.mcheli.helper.network.MCH_WrapPacketData;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraftforge.fml.common.network.NetworkRegistry;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*    */ 
/*    */ public class W_Network {
/* 12 */   static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("MCHeli_CH");
/*    */   
/*    */   public static void sendToServer(W_PacketBase pkt) {
/* 15 */     INSTANCE.sendToServer((IMessage)new MCH_WrapPacketData(pkt));
/*    */   }
/*    */   
/*    */   public static void sendToPlayer(W_PacketBase pkt, EntityPlayer player) {
/* 19 */     if (player instanceof EntityPlayerMP) {
/* 20 */       INSTANCE.sendTo((IMessage)new MCH_WrapPacketData(pkt), (EntityPlayerMP)player);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void sendToAllAround(W_PacketBase pkt, Entity sender, double renge) {
/* 25 */     NetworkRegistry.TargetPoint t = new NetworkRegistry.TargetPoint(sender.field_71093_bK, sender.field_70165_t, sender.field_70163_u, sender.field_70161_v, renge);
/* 26 */     INSTANCE.sendToAllAround((IMessage)new MCH_WrapPacketData(pkt), t);
/*    */   }
/*    */   
/*    */   public static void sendToAllPlayers(W_PacketBase pkt) {
/* 30 */     INSTANCE.sendToAll((IMessage)new MCH_WrapPacketData(pkt));
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_Network.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */