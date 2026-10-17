/*    */ package com.norwood.mcheli.uav;
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.helper.network.HandleSide;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.IThreadListener;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MCH_UavPacketHandler {
/*    */   @HandleSide({Side.SERVER})
/*    */   public static void onPacketUavStatus(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 12 */     if (!player.field_70170_p.field_72995_K) {
/* 13 */       MCH_UavPacketStatus status = new MCH_UavPacketStatus();
/* 14 */       status.readData(data);
/* 15 */       scheduler.func_152344_a(() -> {
/*    */             if (player.func_184187_bx() instanceof MCH_EntityUavStation) {
/*    */               ((MCH_EntityUavStation)player.func_184187_bx()).setUavPosition(status.posUavX, status.posUavY, status.posUavZ);
/*    */               if (status.continueControl)
/*    */                 ((MCH_EntityUavStation)player.func_184187_bx()).controlLastAircraft((Entity)player); 
/*    */             } 
/*    */           });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mchel\\uav\MCH_UavPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */