/*    */ package com.norwood.mcheli.helper.client.renderer.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public interface IItemModelRenderer
/*    */ {
/*    */   static boolean isFirstPerson(ItemCameraTransforms.TransformType type) {
/* 14 */     return (type == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND || type == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND);
/*    */   }
/*    */   
/*    */   static boolean isThirdPerson(ItemCameraTransforms.TransformType type) {
/* 18 */     return (type == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND || type == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
/*    */   }
/*    */   
/*    */   boolean shouldRenderer(ItemStack paramItemStack, ItemCameraTransforms.TransformType paramTransformType);
/*    */   
/*    */   void renderItem(ItemStack paramItemStack, @Nullable EntityLivingBase paramEntityLivingBase, ItemCameraTransforms.TransformType paramTransformType, float paramFloat);
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\renderer\item\IItemModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */