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
/*    */   public void func_110577_a(@NotNull ResourceLocation resouce) {
/* 22 */     if (MCH_ClientCommonTickHandler.cameraMode == 2) {
/* 23 */       this.tm.func_110577_a(R);
/*    */     } else {
/* 25 */       this.tm.func_110577_a(resouce);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean func_110580_a(@NotNull ResourceLocation textureLocation, @NotNull ITickableTextureObject textureObj) {
/* 30 */     return this.tm.func_110580_a(textureLocation, textureObj);
/*    */   }
/*    */   
/*    */   public boolean func_110579_a(@NotNull ResourceLocation textureLocation, @NotNull ITextureObject textureObj) {
/* 34 */     return this.tm.func_110579_a(textureLocation, textureObj);
/*    */   }
/*    */   @NotNull
/*    */   public ITextureObject func_110581_b(@NotNull ResourceLocation textureLocation) {
/* 38 */     return this.tm.func_110581_b(textureLocation);
/*    */   }
/*    */   @NotNull
/*    */   public ResourceLocation func_110578_a(@NotNull String name, @NotNull DynamicTexture texture) {
/* 42 */     return this.tm.func_110578_a(name, texture);
/*    */   }
/*    */   
/*    */   public void func_110550_d() {
/* 46 */     this.tm.func_110550_d();
/*    */   }
/*    */   
/*    */   public void func_147645_c(@NotNull ResourceLocation textureLocation) {
/* 50 */     this.tm.func_147645_c(textureLocation);
/*    */   }
/*    */   
/*    */   public void func_110549_a(@NotNull IResourceManager resourceManager) {
/* 54 */     this.tm.func_110549_a(resourceManager);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_TextureManagerDummy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */