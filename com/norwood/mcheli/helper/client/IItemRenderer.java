/*    */ package com.norwood.mcheli.helper.client;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @Deprecated
/*    */ @SideOnly(Side.CLIENT)
/*    */ public interface IItemRenderer {
/*    */   boolean handleRenderType(ItemStack paramItemStack, ItemRenderType paramItemRenderType);
/*    */   
/*    */   boolean shouldUseRenderHelper(ItemRenderType paramItemRenderType, ItemStack paramItemStack, ItemRendererHelper paramItemRendererHelper);
/*    */   
/*    */   void renderItem(ItemRenderType paramItemRenderType, ItemStack paramItemStack, Object... paramVarArgs);
/*    */   
/*    */   @Deprecated
/*    */   public enum ItemRenderType {
/* 18 */     ENTITY,
/* 19 */     EQUIPPED,
/* 20 */     EQUIPPED_FIRST_PERSON,
/* 21 */     INVENTORY,
/* 22 */     FIRST_PERSON_MAP;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public enum ItemRendererHelper {
/* 27 */     ENTITY_ROTATION,
/* 28 */     ENTITY_BOBBING,
/* 29 */     EQUIPPED_BLOCK,
/* 30 */     BLOCK_3D,
/* 31 */     INVENTORY_BLOCK;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\IItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */