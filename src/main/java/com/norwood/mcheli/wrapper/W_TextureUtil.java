/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class W_TextureUtil {
/*  9 */   private static final W_TextureUtil instance = new W_TextureUtil();
/*    */   
/*    */   public static TextureParam getTextureInfo(String domain, String name) {
/* 12 */     TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
/* 13 */     ResourceLocation r = new ResourceLocation(domain, name);
/* 14 */     textureManager.bindTexture(r);
/* 15 */     TextureParam info = instance.newParam();
/* 16 */     info.width = GL11.glGetTexLevelParameteri(3553, 0, 4096);
/* 17 */     info.height = GL11.glGetTexLevelParameteri(3553, 0, 4097);
/* 18 */     return info;
/*    */   }
/*    */   
/*    */   private TextureParam newParam() {
/* 22 */     return new TextureParam(this);
/*    */   }
/*    */   
/*    */   public static class TextureParam {
/*    */     public int width;
/*    */     public int height;
/*    */     
/*    */     public TextureParam(W_TextureUtil paramW_TextureUtil) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_TextureUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */