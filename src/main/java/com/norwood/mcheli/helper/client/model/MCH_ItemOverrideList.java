/*    */ package com.norwood.mcheli.helper.client.model;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.Collections;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverride;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverrideList;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ItemOverrideList
/*    */   extends ItemOverrideList {
/*    */   private final IBakedModel bakedModel;
/*    */   
/*    */   public MCH_ItemOverrideList(IBakedModel bakedModel) {
/* 20 */     super(Collections.emptyList());
/* 21 */     this.bakedModel = bakedModel;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   @Deprecated
/*    */   public ResourceLocation applyOverride(@NotNull ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
/* 27 */     return this.bakedModel.getOverrides().applyOverride(stack, worldIn, entityIn);
/*    */   }
/*    */   @NotNull
/*    */   public IBakedModel handleItemState(@NotNull IBakedModel originalModel, @NotNull ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
/* 31 */     PooledModelParameters.setItemAndUser(stack, entity);
/* 32 */     return this.bakedModel.getOverrides().handleItemState(originalModel, stack, world, entity);
/*    */   }
/*    */   @NotNull
/*    */   public ImmutableList<ItemOverride> getOverrides() {
/* 36 */     return this.bakedModel.getOverrides().getOverrides();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\model\MCH_ItemOverrideList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */