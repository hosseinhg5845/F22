/*    */ package com.norwood.mcheli.helper.client;
/*    */ 
/*    */ import com.norwood.mcheli.helper.MCH_Utils;
/*    */ import com.norwood.mcheli.helper.client.model.loader.IVertexModelLoader;
/*    */ import com.norwood.mcheli.helper.client.model.loader.MetasequoiaModelLoader;
/*    */ import com.norwood.mcheli.helper.client.model.loader.TechneModelLoader;
/*    */ import com.norwood.mcheli.helper.client.model.loader.WavefrontModelLoader;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_Models
/*    */ {
/* 19 */   private static final IVertexModelLoader objLoader = (IVertexModelLoader)new WavefrontModelLoader();
/* 20 */   private static final IVertexModelLoader mqoLoader = (IVertexModelLoader)new MetasequoiaModelLoader();
/* 21 */   private static final IVertexModelLoader tcnLoader = (IVertexModelLoader)new TechneModelLoader();
/*    */   
/*    */   public static _IModelCustom loadModel(String name) throws IllegalArgumentException, _ModelFormatException {
/* 24 */     ResourceLocation resource = MCH_Utils.suffix("models/" + name);
/* 25 */     IResourceManager resourceManager = Minecraft.func_71410_x().func_110442_L();
/* 26 */     IVertexModelLoader[] loaders = { objLoader, mqoLoader, tcnLoader };
/* 27 */     _IModelCustom model = null;
/*    */     
/* 29 */     for (IVertexModelLoader loader : loaders) {
/*    */       try {
/* 31 */         model = loader.load(resourceManager, resource);
/* 32 */       } catch (FileNotFoundException var10) {
/* 33 */         MCH_Utils.logger().debug("model file not found '{}' at .{}", resource, loader.getExtension());
/* 34 */       } catch (IOException var11) {
/* 35 */         MCH_Utils.logger().error("load model error '{}' at .{}", resource, loader.getExtension(), var11);
/* 36 */         return null;
/*    */       } 
/*    */       
/* 39 */       if (model != null) {
/*    */         break;
/*    */       }
/*    */     } 
/*    */     
/* 44 */     return model;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\MCH_Models.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */