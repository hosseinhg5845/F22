/*    */ package com.norwood.mcheli.tool;
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Config;
/*    */ import com.norwood.mcheli.helper.network.HandleSide;
/*    */ import com.norwood.mcheli.multiplay.MCH_Multiplay;
/*    */ import com.norwood.mcheli.multiplay.MCH_PacketIndSpotEntity;
/*    */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IThreadListener;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MCH_ToolPacketHandler {
/*    */   @HandleSide({Side.SERVER})
/*    */   public static void onPacket_IndSpotEntity(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 18 */     if (!player.world.isRemote) {
/* 19 */       MCH_PacketIndSpotEntity pc = new MCH_PacketIndSpotEntity();
/* 20 */       pc.readData(data);
/* 21 */       scheduler.addScheduledTask(() -> {
/*    */             ItemStack itemStack = player.getHeldItemMainhand();
/*    */             if (!itemStack.isEmpty() && itemStack.getItem() instanceof com.norwood.mcheli.tool.rangefinder.MCH_ItemRangeFinder)
/*    */               if (pc.targetFilter == 0) {
/*    */                 if (MCH_Multiplay.markPoint(player, player.posX, player.posY + player.getEyeHeight(), player.posZ)) {
/*    */                   W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "pi", 1.0F, 1.0F);
/*    */                 } else {
/*    */                   W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "ng", 1.0F, 1.0F);
/*    */                 } 
/*    */               } else if (itemStack.getMetadata() < itemStack.getMaxDamage()) {
/*    */                 if (MCH_Config.RangeFinderConsume.prmBool)
/*    */                   itemStack.damageItem(1, (EntityLivingBase)player); 
/*    */                 int time = ((pc.targetFilter & 0xFC) == 0) ? 60 : MCH_Config.RangeFinderSpotTime.prmInt;
/*    */                 if (MCH_Multiplay.spotEntity((EntityLivingBase)player, null, player.posX, player.posY + player.getEyeHeight(), player.posZ, pc.targetFilter, MCH_Config.RangeFinderSpotDist.prmInt, time, 20.0F)) {
/*    */                   W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "pi", 1.0F, 1.0F);
/*    */                 } else {
/*    */                   W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "ng", 1.0F, 1.0F);
/*    */                 } 
/*    */               }  
/*    */           });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tool\MCH_ToolPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */