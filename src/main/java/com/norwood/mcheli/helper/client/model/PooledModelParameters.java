/*    */ package com.norwood.mcheli.helper.client.model;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class PooledModelParameters
/*    */ {
/*    */   private static EntityLivingBase heldItemUser;
/* 11 */   private static ItemStack rendererTargetItem = ItemStack.EMPTY;
/* 12 */   private static ItemCameraTransforms.TransformType transformType = ItemCameraTransforms.TransformType.NONE;
/*    */   
/*    */   static void setItemAndUser(ItemStack itemstack, @Nullable EntityLivingBase user) {
/* 15 */     rendererTargetItem = itemstack;
/* 16 */     heldItemUser = user;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static EntityLivingBase getEntity() {
/* 21 */     return heldItemUser;
/*    */   }
/*    */   
/*    */   public static ItemStack getTargetRendererStack() {
/* 25 */     return rendererTargetItem;
/*    */   }
/*    */   
/*    */   public static ItemCameraTransforms.TransformType getTransformType() {
/* 29 */     return transformType;
/*    */   }
/*    */   
/*    */   static void setTransformType(ItemCameraTransforms.TransformType type) {
/* 33 */     transformType = type;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\model\PooledModelParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */