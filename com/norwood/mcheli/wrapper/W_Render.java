/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_Config;
/*    */ import java.nio.FloatBuffer;
/*    */ import net.minecraft.client.renderer.GLAllocation;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public abstract class W_Render<T extends Entity>
/*    */   extends Render<T> {
/* 16 */   protected static final ResourceLocation TEX_DEFAULT = new ResourceLocation(W_MOD.DOMAIN, "textures/default.png");
/* 17 */   private static final FloatBuffer colorBuffer = GLAllocation.func_74529_h(16);
/*    */   public int srcBlend;
/*    */   public int dstBlend;
/*    */   
/*    */   protected W_Render(RenderManager renderManager) {
/* 22 */     super(renderManager);
/*    */   }
/*    */   
/*    */   public static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_) {
/* 26 */     colorBuffer.clear();
/* 27 */     colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
/* 28 */     colorBuffer.flip();
/* 29 */     return colorBuffer;
/*    */   }
/*    */   
/*    */   protected void bindTexture(String path) {
/* 33 */     func_110776_a(new ResourceLocation(W_MOD.DOMAIN, path));
/*    */   }
/*    */   
/*    */   protected ResourceLocation func_110775_a(@NotNull T entity) {
/* 37 */     return TEX_DEFAULT;
/*    */   }
/*    */   
/*    */   public void setCommonRenderParam(boolean smoothShading, int lighting) {
/* 41 */     if (smoothShading && MCH_Config.SmoothShading.prmBool) {
/* 42 */       GL11.glShadeModel(7425);
/*    */     }
/*    */     
/* 45 */     GL11.glAlphaFunc(516, 0.001F);
/* 46 */     GL11.glEnable(2884);
/* 47 */     int j = lighting % 65536;
/* 48 */     int k = lighting / 65536;
/* 49 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j, k);
/* 50 */     GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/* 51 */     GL11.glEnable(3042);
/* 52 */     this.srcBlend = GL11.glGetInteger(3041);
/* 53 */     this.dstBlend = GL11.glGetInteger(3040);
/* 54 */     GL11.glBlendFunc(770, 771);
/*    */   }
/*    */   
/*    */   public void restoreCommonRenderParam() {
/* 58 */     GL11.glBlendFunc(this.srcBlend, this.dstBlend);
/* 59 */     GL11.glDisable(3042);
/* 60 */     GL11.glShadeModel(7424);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_Render.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */