/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftPacketHandler;
/*    */ import com.norwood.mcheli.block.MCH_DraftingTablePacketHandler;
/*    */ import com.norwood.mcheli.command.MCH_CommandPacketHandler;
/*    */ import com.norwood.mcheli.gltd.MCH_GLTDPacketHandler;
/*    */ import com.norwood.mcheli.helicopter.MCH_HeliPacketHandler;
/*    */ import com.norwood.mcheli.lweapon.MCH_LightWeaponPacketHandler;
/*    */ import com.norwood.mcheli.multiplay.MCH_MultiplayPacketHandler;
/*    */ import com.norwood.mcheli.plane.MCP_PlanePacketHandler;
/*    */ import com.norwood.mcheli.tank.MCH_TankPacketHandler;
/*    */ import com.norwood.mcheli.tool.MCH_ToolPacketHandler;
/*    */ import com.norwood.mcheli.uav.MCH_UavPacketHandler;
/*    */ import com.norwood.mcheli.vehicle.MCH_VehiclePacketHandler;
/*    */ import com.norwood.mcheli.wrapper.W_PacketHandler;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.IThreadListener;
/*    */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ 
/*    */ public class MCH_PacketHandler
/*    */   extends W_PacketHandler {
/*    */   public void onPacket(ByteArrayDataInput data, EntityPlayer entityPlayer, MessageContext ctx) {
/* 25 */     int msgid = getMessageId(data);
/* 26 */     IThreadListener handler = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
/* 27 */     switch (msgid) { case 268437520:
/* 28 */         MCH_CommonPacketHandler.onPacketEffectExplosion(entityPlayer, data, handler); return;
/* 29 */       case 268437568: MCH_CommonPacketHandler.onPacketNotifyServerSettings(entityPlayer, data, handler); return;
/* 30 */       case 268437761: MCH_MultiplayPacketHandler.onPacket_NotifySpotedEntity(entityPlayer, data, handler); return;
/* 31 */       case 268437762: MCH_MultiplayPacketHandler.onPacket_NotifyMarkPoint(entityPlayer, data, handler); return;
/* 32 */       case 268438032: MCH_MultiplayPacketHandler.onPacket_IndClient(entityPlayer, data, handler); return;
/* 33 */       case 268438272: MCH_CommandPacketHandler.onPacketTitle(entityPlayer, data, handler); return;
/* 34 */       case 268439569: MCH_AircraftPacketHandler.onPacketSeatListResponse(entityPlayer, data, handler); return;
/* 35 */       case 268439600: MCH_AircraftPacketHandler.onPacketNotifyTVMissileEntity(entityPlayer, data, handler); return;
/* 36 */       case 268439601: MCH_AircraftPacketHandler.onPacketNotifyWeaponID(entityPlayer, data, handler); return;
/* 37 */       case 268439602: MCH_AircraftPacketHandler.onPacketNotifyHitBullet(entityPlayer, data, handler); return;
/* 38 */       case 268439604: MCH_AircraftPacketHandler.onPacketNotifyAmmoNum(entityPlayer, data, handler); return;
/* 39 */       case 268439632: MCH_AircraftPacketHandler.onPacketOnMountEntity(entityPlayer, data, handler); return;
/* 40 */       case 268439649: MCH_AircraftPacketHandler.onPacketStatusResponse(entityPlayer, data, handler); return;
/* 41 */       case 536872992: MCH_CommonPacketHandler.onPacketIndOpenScreen(entityPlayer, data, handler); return;
/* 42 */       case 536873088: MCH_MultiplayPacketHandler.onPacket_Command(entityPlayer, data, handler); return;
/* 43 */       case 536873216: MCH_ToolPacketHandler.onPacket_IndSpotEntity(entityPlayer, data, handler); return;
/* 44 */       case 536873472: MCH_MultiplayPacketHandler.onPacket_LargeData(entityPlayer, data, handler); return;
/* 45 */       case 536873473: MCH_MultiplayPacketHandler.onPacket_ModList(entityPlayer, data, handler); return;
/* 46 */       case 536873729: MCH_CommandPacketHandler.onPacketSave(entityPlayer, data, handler); return;
/* 47 */       case 536873984: MCH_CommonPacketHandler.onPacketNotifyLock(entityPlayer, data, handler); return;
/* 48 */       case 536875024: MCH_AircraftPacketHandler.onPacketSeatListRequest(entityPlayer, data, handler); return;
/* 49 */       case 536875040: MCH_AircraftPacketHandler.onPacket_PlayerControl(entityPlayer, data, handler); return;
/* 50 */       case 536875059: MCH_AircraftPacketHandler.onPacketIndReload(entityPlayer, data, handler); return;
/* 51 */       case 536875061: MCH_AircraftPacketHandler.onPacketIndNotifyAmmoNum(entityPlayer, data, handler); return;
/* 52 */       case 536875062: MCH_AircraftPacketHandler.onPacketIndRotation(entityPlayer, data, handler); return;
/* 53 */       case 536875063: MCH_AircraftPacketHandler.onPacketNotifyInfoReloaded(entityPlayer, data, handler); return;
/* 54 */       case 536875072: MCH_AircraftPacketHandler.onPacket_ClientSetting(entityPlayer, data, handler); return;
/* 55 */       case 536875104: MCH_AircraftPacketHandler.onPacketStatusRequest(entityPlayer, data, handler); return;
/* 56 */       case 536879120: MCH_HeliPacketHandler.onPacket_PlayerControl(entityPlayer, data, handler); return;
/* 57 */       case 536887312: MCH_GLTDPacketHandler.onPacket_GLTDPlayerControl(entityPlayer, data, handler); return;
/* 58 */       case 536903696: MCP_PlanePacketHandler.onPacket_PlayerControl(entityPlayer, data, handler); return;
/* 59 */       case 536936464: MCH_LightWeaponPacketHandler.onPacket_PlayerControl(entityPlayer, data, handler); return;
/* 60 */       case 537002000: MCH_VehiclePacketHandler.onPacket_PlayerControl(entityPlayer, data, handler); return;
/* 61 */       case 537133072: MCH_UavPacketHandler.onPacketUavStatus(entityPlayer, data, handler); return;
/* 62 */       case 537395216: MCH_DraftingTablePacketHandler.onPacketCreate(entityPlayer, data, handler); return;
/* 63 */       case 537919504: MCH_TankPacketHandler.onPacket_PlayerControl(entityPlayer, data, handler); return; }
/* 64 */      MCH_Lib.DbgLog(entityPlayer.world, "MCH_PacketHandler.onPacket invalid MSGID=0x%X(%d)", new Object[] { Integer.valueOf(msgid), Integer.valueOf(msgid) });
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getMessageId(ByteArrayDataInput data) {
/*    */     try {
/* 70 */       return data.readInt();
/* 71 */     } catch (Exception var3) {
/* 72 */       var3.printStackTrace();
/* 73 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_PacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */