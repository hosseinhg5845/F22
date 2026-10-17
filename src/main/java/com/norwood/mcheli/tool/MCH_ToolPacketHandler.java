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
/* 18 */     if (!player.field_70170_p.field_72995_K) {
/* 19 */       MCH_PacketIndSpotEntity pc = new MCH_PacketIndSpotEntity();
/* 20 */       pc.readData(data);
/* 21 */       scheduler.func_152344_a(() -> {
/*    */             ItemStack itemStack = player.func_184614_ca();
/*    */             if (!itemStack.func_190926_b() && itemStack.func_77973_b() instanceof com.norwood.mcheli.tool.rangefinder.MCH_ItemRangeFinder)
/*    */               if (pc.targetFilter == 0) {
/*    */                 if (MCH_Multiplay.markPoint(player, player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v)) {
/*    */                   W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "pi", 1.0F, 1.0F);
/*    */                 } else {
/*    */                   W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "ng", 1.0F, 1.0F);
/*    */                 } 
/*    */               } else if (itemStack.func_77960_j() < itemStack.func_77958_k()) {
/*    */                 if (MCH_Config.RangeFinderConsume.prmBool)
/*    */                   itemStack.func_77972_a(1, (EntityLivingBase)player); 
/*    */                 int time = ((pc.targetFilter & 0xFC) == 0) ? 60 : MCH_Config.RangeFinderSpotTime.prmInt;
/*    */                 if (MCH_Multiplay.spotEntity((EntityLivingBase)player, null, player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v, pc.targetFilter, MCH_Config.RangeFinderSpotDist.prmInt, time, 20.0F)) {
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