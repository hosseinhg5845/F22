/*    */ package com.norwood.mcheli.command;
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_MOD;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import com.norwood.mcheli.helper.network.HandleSide;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.IThreadListener;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MCH_CommandPacketHandler {
/*    */   @HandleSide({Side.CLIENT})
/*    */   public static void onPacketTitle(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 14 */     if (player != null && player.world.isRemote) {
/* 15 */       MCH_PacketTitle req = new MCH_PacketTitle();
/* 16 */       req.readData(data);
/* 17 */       scheduler.addScheduledTask(() -> MCH_MOD.proxy.printChatMessage(req.chatComponent, req.showTime, req.position));
/*    */     } 
/*    */   }
/*    */   
/*    */   @HandleSide({Side.SERVER})
/*    */   public static void onPacketSave(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 23 */     if (player != null && !player.world.isRemote) {
/* 24 */       MCH_PacketCommandSave req = new MCH_PacketCommandSave();
/* 25 */       req.readData(data);
/* 26 */       scheduler.addScheduledTask(() -> {
/*    */             MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
/*    */             if (ac != null)
/*    */               ac.setCommand(req.str, player); 
/*    */           });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\command\MCH_CommandPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */