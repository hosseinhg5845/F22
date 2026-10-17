/*    */ package com.norwood.mcheli.helper.client.renderer.item;
/*    */ 
/*    */ import com.norwood.mcheli.helper.client.MCH_ItemModelRenderers;
/*    */ import com.norwood.mcheli.helper.client.model.PooledModelParameters;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.client.model.animation.Animation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class CustomItemStackRenderer extends TileEntityItemStackRenderer {
/*    */   private static CustomItemStackRenderer instance;
/*    */   
/*    */   public static CustomItemStackRenderer getInstance() {
/* 16 */     if (instance == null) {
/* 17 */       instance = new CustomItemStackRenderer();
/*    */     }
/*    */     
/* 20 */     return instance;
/*    */   }
/*    */   
/*    */   public void func_192838_a(ItemStack p_192838_1_, float partialTicks) {
/* 24 */     IItemModelRenderer renderer = MCH_ItemModelRenderers.getRenderer(p_192838_1_.func_77973_b());
/* 25 */     if (renderer != null)
/* 26 */       renderer.renderItem(p_192838_1_, PooledModelParameters.getEntity(), PooledModelParameters.getTransformType(), Animation.getPartialTickTime()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\renderer\item\CustomItemStackRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */