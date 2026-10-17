/*    */ package com.norwood.mcheli.wrapper.modelloader;
/*    */ 
/*    */ import com.norwood.mcheli.helper.client._IModelCustom;
/*    */ import com.norwood.mcheli.helper.client._IModelCustomLoader;
/*    */ import com.norwood.mcheli.helper.client._ModelFormatException;
/*    */ import java.net.URL;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class W_ObjModelLoader
/*    */   implements _IModelCustomLoader {
/* 11 */   private static final String[] types = new String[] { "obj" };
/*    */ 
/*    */   
/*    */   public String getType() {
/* 15 */     return "OBJ model";
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getSuffixes() {
/* 20 */     return types;
/*    */   }
/*    */ 
/*    */   
/*    */   public _IModelCustom loadInstance(ResourceLocation resource) throws _ModelFormatException {
/* 25 */     return new W_WavefrontObject(resource);
/*    */   }
/*    */ 
/*    */   
/*    */   public _IModelCustom loadInstance(String resourceName, URL resource) throws _ModelFormatException {
/* 30 */     return new W_WavefrontObject(resourceName, resource);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\modelloader\W_ObjModelLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */