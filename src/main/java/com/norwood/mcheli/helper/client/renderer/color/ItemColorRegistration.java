/*    */ package com.norwood.mcheli.helper.client.renderer.color;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_MOD;
/*    */ import com.norwood.mcheli.mob.MCH_ItemSpawnGunner;
/*    */ import net.minecraft.client.renderer.color.ItemColors;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraftforge.client.event.ColorHandlerEvent;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(modid = "mcheli", value = {Side.CLIENT})
/*    */ public class ItemColorRegistration
/*    */ {
/*    */   @SubscribeEvent
/*    */   static void onRegisterItemColor(ColorHandlerEvent.Item event) {
/* 18 */     ItemColors itemColors = event.getItemColors();
/* 19 */     itemColors.registerItemColorHandler(MCH_ItemSpawnGunner::getColorFromItemStack, new Item[] { (Item)MCH_MOD.itemSpawnGunnerVsMonster, (Item)MCH_MOD.itemSpawnGunnerVsPlayer });
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\renderer\color\ItemColorRegistration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */