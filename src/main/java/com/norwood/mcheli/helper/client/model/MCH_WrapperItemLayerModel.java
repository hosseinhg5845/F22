/*    */ package com.norwood.mcheli.helper.client.model;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.client.model.IModel;
/*    */ import net.minecraftforge.client.model.ItemLayerModel;
/*    */ import net.minecraftforge.client.model.PerspectiveMapWrapper;
/*    */ import net.minecraftforge.client.model.SimpleModelState;
/*    */ import net.minecraftforge.common.model.IModelState;
/*    */ import net.minecraftforge.common.model.TRSRTransformation;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_WrapperItemLayerModel
/*    */   implements IModel
/*    */ {
/*    */   private final ItemLayerModel model;
/*    */   private final ModelBlock raw;
/*    */   
/*    */   public MCH_WrapperItemLayerModel(ModelBlock modelBlock) {
/* 30 */     this.raw = modelBlock;
/* 31 */     this.model = new ItemLayerModel(modelBlock);
/*    */   }
/*    */   @NotNull
/*    */   public Collection<ResourceLocation> getTextures() {
/* 35 */     return this.model.getTextures();
/*    */   }
/*    */   @NotNull
/*    */   public IModel retexture(@NotNull ImmutableMap<String, String> textures) {
/* 39 */     return (IModel)this.model.retexture(textures);
/*    */   }
/*    */   @NotNull
/*    */   public IBakedModel bake(@NotNull IModelState state, @NotNull VertexFormat format, @NotNull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
/* 43 */     ItemCameraTransforms transforms = this.raw.getAllTransforms();
/* 44 */     Map<ItemCameraTransforms.TransformType, TRSRTransformation> tMap = Maps.newEnumMap(ItemCameraTransforms.TransformType.class);
/* 45 */     tMap.putAll((Map<? extends ItemCameraTransforms.TransformType, ? extends TRSRTransformation>)PerspectiveMapWrapper.getTransforms(transforms));
/* 46 */     tMap.putAll((Map<? extends ItemCameraTransforms.TransformType, ? extends TRSRTransformation>)PerspectiveMapWrapper.getTransforms(state));
/* 47 */     SimpleModelState simpleModelState = new SimpleModelState(ImmutableMap.copyOf(tMap), state.apply(Optional.empty()));
/* 48 */     return this.model.bake((IModelState)simpleModelState, format, bakedTextureGetter);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\model\MCH_WrapperItemLayerModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */