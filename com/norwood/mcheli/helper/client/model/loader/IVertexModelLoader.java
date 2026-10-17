/*    */ package com.norwood.mcheli.helper.client.model.loader;
/*    */ 
/*    */ import com.norwood.mcheli.helper.client._IModelCustom;
/*    */ import com.norwood.mcheli.helper.client._ModelFormatException;
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public interface IVertexModelLoader
/*    */ {
/*    */   String getExtension();
/*    */   
/*    */   @Nullable
/*    */   _IModelCustom load(IResourceManager paramIResourceManager, ResourceLocation paramResourceLocation) throws IOException, _ModelFormatException;
/*    */   
/*    */   default ResourceLocation withExtension(ResourceLocation location) {
/* 21 */     return new ResourceLocation(location.func_110624_b(), location.func_110623_a() + "." + getExtension());
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\model\loader\IVertexModelLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */