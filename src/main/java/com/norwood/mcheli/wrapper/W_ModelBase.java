/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import com.norwood.mcheli.helper.client._IModelCustom;
/*    */ import com.norwood.mcheli.helper.client._IModelCustomLoader;
/*    */ import com.norwood.mcheli.helper.client._ModelFormatException;
/*    */ import com.norwood.mcheli.helper.client.model.loader.TechneModelLoader;
/*    */ import com.norwood.mcheli.wrapper.modelloader.W_MqoModelLoader;
/*    */ import com.norwood.mcheli.wrapper.modelloader.W_ObjModelLoader;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public abstract class W_ModelBase extends ModelBase {
/* 13 */   private static final _IModelCustomLoader objLoader = (_IModelCustomLoader)new W_ObjModelLoader();
/* 14 */   private static final _IModelCustomLoader mqoLoader = (_IModelCustomLoader)new W_MqoModelLoader();
/* 15 */   private static final _IModelCustomLoader tcnLoader = (_IModelCustomLoader)new TechneModelLoader();
/*    */   
/*    */   public static _IModelCustom loadModel(String name) throws IllegalArgumentException, _ModelFormatException {
/* 18 */     ResourceLocation resource = new ResourceLocation("mcheli", name);
/* 19 */     String path = resource.getResourcePath();
/* 20 */     int i = path.lastIndexOf('.');
/* 21 */     if (i == -1)
/* 22 */       throw new IllegalArgumentException("The resource name is not valid"); 
/* 23 */     if (path.substring(i).equalsIgnoreCase(".mqo")) {
/* 24 */       return mqoLoader.loadInstance(resource);
/*    */     }
/* 26 */     return path.substring(i).equalsIgnoreCase(".obj") ? objLoader.loadInstance(resource) : tcnLoader.loadInstance(resource);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_ModelBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */