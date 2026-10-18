/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class W_Item
/*    */   extends Item {
/*    */   public W_Item(int par1) {}
/*    */   
/*    */   public W_Item() {}
/*    */   
/*    */   public static int getIdFromItem(@NotNull Item i) {
/* 17 */     return REGISTRY.getIDForObject(i);
/*    */   }
/*    */   @NotNull
/*    */   public static Item getItemById(int i) {
/* 21 */     return Item.getItemById(i);
/*    */   }
/*    */   
/*    */   public static Item getItemByName(String nm) {
/* 25 */     return (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(nm));
/*    */   }
/*    */   
/*    */   public static String getNameForItem(Item item) {
/* 29 */     return ForgeRegistries.ITEMS.getKey((IForgeRegistryEntry)item).toString();
/*    */   }
/*    */   @NotNull
/*    */   public static Item getItemFromBlock(@NotNull Block block) {
/* 33 */     return Item.getItemFromBlock(block);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_Item.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */