/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import com.norwood.mcheli.helper.MCH_Utils;
/*    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.ITickableTextureObject;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_TextureManagerDummy extends TextureManager {
/* 13 */   public static final ResourceLocation R = MCH_Utils.suffix("textures/test.png");
/*    */   private final TextureManager tm;
/*    */   
/*    */   public MCH_TextureManagerDummy(TextureManager t) {
/* 17 */     super(null);
/* 18 */     this.tm = t;
/*    */   }
/*    */   
/*    */   public void bindTexture(@NotNull ResourceLocation resouce) {
/* 22 */     if (MCH_ClientCommonTickHandler.cameraMode == 2) {
/* 23 */       this.tm.bindTexture(R);
/*    */     } else {
/* 25 */       this.tm.bindTexture(resouce);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean loadTickableTexture(@NotNull ResourceLocation textureLocation, @NotNull ITickableTextureObject textureObj) {
/* 30 */     return this.tm.loadTickableTexture(textureLocation, textureObj);
/*    */   }
/*    */   
/*    */   public boolean loadTexture(@NotNull ResourceLocation textureLocation, @NotNull ITextureObject textureObj) {
/* 34 */     return this.tm.loadTexture(textureLocation, textureObj);
/*    */   }
/*    */   @NotNull
/*    */   public ITextureObject getTexture(@NotNull ResourceLocation textureLocation) {
/* 38 */     return this.tm.getTexture(textureLocation);
/*    */   }
/*    */   @NotNull
/*    */   public ResourceLocation getDynamicTextureLocation(@NotNull String name, @NotNull DynamicTexture texture) {
/* 42 */     return this.tm.getDynamicTextureLocation(name, texture);
/*    */   }
/*    */   
/*    */   public void tick() {
/* 46 */     this.tm.tick();
/*    */   }
/*    */   
/*    */   public void deleteTexture(@NotNull ResourceLocation textureLocation) {
/* 50 */     this.tm.deleteTexture(textureLocation);
/*    */   }
/*    */   
/*    */   public void onResourceManagerReload(@NotNull IResourceManager resourceManager) {
/* 54 */     this.tm.onResourceManagerReload(resourceManager);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_TextureManagerDummy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */