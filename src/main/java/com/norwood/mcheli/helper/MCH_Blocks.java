/*    */ package com.norwood.mcheli.helper;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraftforge.event.RegistryEvent;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(modid = "mcheli")
/*    */ public class MCH_Blocks
/*    */ {
/* 15 */   private static final Set<Block> registryWrapper = Sets.newLinkedHashSet();
/*    */   
/*    */   @SubscribeEvent
/*    */   static void onBlockRegisterEvent(RegistryEvent.Register<Block> event) {
/* 19 */     for (Block block : registryWrapper) {
/* 20 */       event.getRegistry().register((IForgeRegistryEntry)block);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void register(Block block, String name) {
/* 25 */     registryWrapper.add((Block)block.setRegistryName(MCH_Utils.suffix(name)));
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\MCH_Blocks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */