/*    */ package com.norwood.mcheli.helper.client.model;
/*    */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*    */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.client.model.ICustomModelLoader;
/*    */ import net.minecraftforge.client.model.IModel;
/*    */ import net.minecraftforge.client.model.ModelLoaderRegistry;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public enum LegacyModelLoader implements ICustomModelLoader {
/*    */   static final String TEMPLATE;
/* 13 */   INSTANCE; public static final String VARIANT = "mcheli_legacy";
/*    */   
/*    */   static {
/* 16 */     TEMPLATE = "{'parent':'item/generated','textures':{'layer0':'__item__'}}".replaceAll("'", "\"");
/*    */   }
/*    */   public void func_110549_a(@NotNull IResourceManager resourceManager) {}
/*    */   
/*    */   public boolean accepts(@NotNull ResourceLocation modelLocation) {
/*    */     ModelResourceLocation location;
/* 22 */     if (modelLocation instanceof ModelResourceLocation) { location = (ModelResourceLocation)modelLocation; }
/* 23 */     else { return false; }
/*    */     
/* 25 */     return (location.func_110624_b().equals("mcheli") && location.func_177518_c().equals("mcheli_legacy"));
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public IModel loadModel(ResourceLocation modelLocation) throws Exception {
/* 30 */     String path = modelLocation.func_110624_b() + ":items/" + modelLocation.func_110623_a();
/* 31 */     ModelBlock modelblock = ModelBlock.func_178294_a(TEMPLATE.replaceAll("__item__", path));
/* 32 */     modelblock.field_178315_d = ModelLoaderRegistry.getModel(modelblock.func_178305_e()).asVanillaModel().get();
/* 33 */     return new MCH_WrapperItemLayerModel(modelblock);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\model\LegacyModelLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */