/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_Config;
/*    */ import com.norwood.mcheli.helper.MCH_Utils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.ItemRenderer;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class W_EntityRenderer {
/*    */   @Deprecated
/*    */   public static void setItemRenderer(Minecraft mc, ItemRenderer ir) {
/* 15 */     W_Reflection.setItemRenderer(ir);
/*    */   }
/*    */   
/*    */   public static boolean isShaderSupport() {
/* 19 */     return (OpenGlHelper.shadersSupported && !MCH_Config.DisableShader.prmBool);
/*    */   }
/*    */   
/*    */   public static void activateShader(String n) {
/* 23 */     activateShader(MCH_Utils.suffix("shaders/post/" + n + ".json"));
/*    */   }
/*    */   
/*    */   public static void activateShader(ResourceLocation r) {
/* 27 */     Minecraft mc = Minecraft.getMinecraft();
/* 28 */     mc.entityRenderer.loadShader(r);
/*    */   }
/*    */   
/*    */   public static void deactivateShader() {
/* 32 */     (Minecraft.getMinecraft()).entityRenderer.stopUseShader();
/*    */   }
/*    */   
/*    */   public static void renderEntityWithPosYaw(RenderManager rm, Entity par1Entity, double par2, double par4, double par6, float par8, float par9, boolean b) {
/* 36 */     rm.renderEntity(par1Entity, par2, par4, par6, par8, par9, b);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_EntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */