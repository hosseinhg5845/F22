/*    */ package com.norwood.mcheli.helper.client.model.loader;
/*    */ 
/*    */ import com.norwood.mcheli.helper.client._IModelCustom;
/*    */ import com.norwood.mcheli.helper.client._ModelFormatException;
/*    */ import com.norwood.mcheli.wrapper.modelloader.W_MetasequoiaObject;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MetasequoiaModelLoader
/*    */   implements IVertexModelLoader
/*    */ {
/*    */   public _IModelCustom load(IResourceManager resourceManager, ResourceLocation location) throws IOException, _ModelFormatException {
/* 18 */     ResourceLocation modelLocation = withExtension(location);
/* 19 */     IResource resource = resourceManager.func_110536_a(modelLocation);
/* 20 */     return (_IModelCustom)new W_MetasequoiaObject(modelLocation, resource);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getExtension() {
/* 25 */     return "mqo";
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\model\loader\MetasequoiaModelLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */