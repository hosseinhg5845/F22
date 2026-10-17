/*    */ package com.norwood.mcheli.helper.client.renderer.item;
/*    */ 
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class BuiltInInvisibleItemRenderer
/*    */   implements IItemModelRenderer {
/*    */   public boolean shouldRenderer(ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
/* 13 */     return (IItemModelRenderer.isFirstPerson(transformType) || IItemModelRenderer.isThirdPerson(transformType));
/*    */   }
/*    */   
/*    */   public void renderItem(ItemStack itemStack, EntityLivingBase entityLivingBase, ItemCameraTransforms.TransformType transformType, float partialTicks) {}
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\renderer\item\BuiltInInvisibleItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */