/*    */ package com.norwood.mcheli.helper;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ import net.minecraftforge.event.RegistryEvent;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
/*    */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*    */ 
/*    */ 
/*    */ @ObjectHolder("mcheli")
/*    */ @EventBusSubscriber(modid = "mcheli")
/*    */ public class MCH_Items
/*    */ {
/* 21 */   private static final Set<Item> registryWrapper = Sets.newLinkedHashSet();
/*    */   
/*    */   @SubscribeEvent
/*    */   static void onItemRegistryEvent(RegistryEvent.Register<Item> event) {
/* 25 */     for (Item item : registryWrapper) {
/* 26 */       event.getRegistry().register(item);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void register(Item item, String name) {
/* 31 */     registryWrapper.add((Item)item.setRegistryName(MCH_Utils.suffix(name)));
/*    */   }
/*    */   
/*    */   public static void registerBlock(Block block) {
/* 35 */     registryWrapper.add((Item)(new ItemBlock(block)).setRegistryName(block.getRegistryName()));
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static Item get(String name) {
/* 40 */     return (Item)ForgeRegistries.ITEMS.getValue(MCH_Utils.suffix(name));
/*    */   }
/*    */   
/*    */   public static String getName(Item item) {
/* 44 */     return ForgeRegistries.ITEMS.getKey(item).toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\MCH_Items.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */