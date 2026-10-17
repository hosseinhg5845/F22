/*    */ package com.norwood.mcheli.helper.client.model;
/*    */ 
/*    */ import com.norwood.mcheli.helper.client.renderer.item.IItemModelRenderer;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import javax.vecmath.Matrix4f;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverrideList;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.apache.commons.lang3.tuple.Pair;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_BakedModel
/*    */   implements IBakedModel
/*    */ {
/*    */   private final IBakedModel bakedModel;
/*    */   private final IItemModelRenderer renderer;
/*    */   private final ItemOverrideList overrides;
/*    */   
/*    */   public MCH_BakedModel(IBakedModel bakedModel, IItemModelRenderer renderer) {
/* 28 */     this.bakedModel = bakedModel;
/* 29 */     this.renderer = renderer;
/* 30 */     this.overrides = new MCH_ItemOverrideList(bakedModel);
/*    */   }
/*    */   @NotNull
/*    */   public List<BakedQuad> func_188616_a(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
/* 34 */     return this.bakedModel.func_188616_a(state, side, rand);
/*    */   }
/*    */   
/*    */   public boolean func_177555_b() {
/* 38 */     return this.bakedModel.func_177555_b();
/*    */   }
/*    */   
/*    */   public boolean func_177556_c() {
/* 42 */     return this.bakedModel.func_177556_c();
/*    */   }
/*    */   
/*    */   public boolean func_188618_c() {
/* 46 */     return (this.renderer.shouldRenderer(PooledModelParameters.getTargetRendererStack(), PooledModelParameters.getTransformType()) || this.bakedModel.func_188618_c());
/*    */   }
/*    */   @NotNull
/*    */   public TextureAtlasSprite func_177554_e() {
/* 50 */     return this.bakedModel.func_177554_e();
/*    */   }
/*    */   @Deprecated
/*    */   @NotNull
/*    */   public ItemCameraTransforms func_177552_f() {
/* 55 */     return this.bakedModel.func_177552_f();
/*    */   }
/*    */   @NotNull
/*    */   public ItemOverrideList func_188617_f() {
/* 59 */     return this.overrides;
/*    */   }
/*    */   
/*    */   public boolean isAmbientOcclusion(@NotNull IBlockState state) {
/* 63 */     return this.bakedModel.isAmbientOcclusion(state);
/*    */   }
/*    */   @NotNull
/*    */   public Pair<? extends IBakedModel, Matrix4f> handlePerspective(@NotNull ItemCameraTransforms.TransformType cameraTransformType) {
/* 67 */     PooledModelParameters.setTransformType(cameraTransformType);
/* 68 */     Pair<? extends IBakedModel, Matrix4f> pair = this.bakedModel.handlePerspective(cameraTransformType);
/* 69 */     return Pair.of(new MCH_BakedModel((IBakedModel)pair.getLeft(), this.renderer), pair.getRight());
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\model\MCH_BakedModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */