/*    */ package com.norwood.mcheli.helper.client.model.loader;
/*    */ 
/*    */ import com.norwood.mcheli.helper.client._IModelCustom;
/*    */ import com.norwood.mcheli.helper.client._IModelCustomLoader;
/*    */ import com.norwood.mcheli.helper.client._ModelFormatException;
/*    */ import java.net.URL;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class TechneModelLoader
/*    */   implements _IModelCustomLoader, IVertexModelLoader {
/* 16 */   private static final String[] types = new String[] { "tcn" };
/*    */ 
/*    */   
/*    */   public String getType() {
/* 20 */     return "Techne model";
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getSuffixes() {
/* 25 */     return types;
/*    */   }
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public _IModelCustom loadInstance(ResourceLocation resource) throws _ModelFormatException {
/* 31 */     throw new UnsupportedOperationException("Techne model is unsupported. file:" + resource);
/*    */   }
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public _IModelCustom loadInstance(String resourceName, URL resource) throws _ModelFormatException {
/* 37 */     throw new UnsupportedOperationException("Techne model is unsupported. file:" + resource);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getExtension() {
/* 42 */     return "tcn";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public _IModelCustom load(IResourceManager resourceManager, ResourceLocation location) throws _ModelFormatException {
/* 48 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\model\loader\TechneModelLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */