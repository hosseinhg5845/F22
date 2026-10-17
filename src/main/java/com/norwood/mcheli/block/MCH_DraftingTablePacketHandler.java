/*    */ package com.norwood.mcheli.block;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.helper.network.HandleSide;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.IThreadListener;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MCH_DraftingTablePacketHandler {
/*    */   @HandleSide({Side.SERVER})
/*    */   public static void onPacketCreate(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 13 */     if (!player.field_70170_p.field_72995_K) {
/* 14 */       MCH_DraftingTableCreatePacket packet = new MCH_DraftingTableCreatePacket();
/* 15 */       packet.readData(data);
/* 16 */       scheduler.func_152344_a(() -> {
/*    */             boolean openScreen = player.field_71070_bA instanceof MCH_DraftingTableGuiContainer;
/*    */             MCH_Lib.DbgLog(false, "MCH_DraftingTablePacketHandler.onPacketCreate : " + openScreen, new Object[0]);
/*    */             if (openScreen)
/*    */               ((MCH_DraftingTableGuiContainer)player.field_71070_bA).createRecipeItem(packet.recipe); 
/*    */           });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\block\MCH_DraftingTablePacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */