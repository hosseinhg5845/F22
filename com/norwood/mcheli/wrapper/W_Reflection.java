/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import com.norwood.mcheli.helper.client.MCH_CameraManager;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*    */ import net.minecraft.client.renderer.EntityRenderer;
/*    */ import net.minecraft.client.renderer.ItemRenderer;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
/*    */ 
/*    */ public class W_Reflection
/*    */ {
/*    */   public static RenderManager getRenderManager(Render<?> render) {
/* 17 */     return render.func_177068_d();
/*    */   }
/*    */   
/*    */   public static void restoreDefaultThirdPersonDistance() {
/* 21 */     setThirdPersonDistance(4.0F);
/*    */   }
/*    */   
/*    */   public static float getThirdPersonDistance() {
/* 25 */     return MCH_CameraManager.getThirdPeasonCameraDistance();
/*    */   }
/*    */   
/*    */   public static void setThirdPersonDistance(float dist) {
/* 29 */     if (dist >= 0.1D) {
/* 30 */       MCH_CameraManager.setThirdPeasonCameraDistance(dist);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void setCameraRoll(float roll) {
/* 35 */     MCH_CameraManager.setCameraRoll(roll);
/*    */   }
/*    */   
/*    */   public static void restoreCameraZoom() {
/* 39 */     setCameraZoom(1.0F);
/*    */   }
/*    */   
/*    */   public static void setCameraZoom(float zoom) {
/*    */     try {
/* 44 */       Minecraft mc = Minecraft.func_71410_x();
/* 45 */       ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, mc.field_71460_t, Float.valueOf(zoom), "field_78503_V");
/* 46 */       MCH_CameraManager.setCameraZoom(zoom);
/* 47 */     } catch (Exception var2) {
/* 48 */       var2.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void setCreativeDigSpeed(int n) {
/*    */     try {
/* 54 */       Minecraft mc = Minecraft.func_71410_x();
/* 55 */       ObfuscationReflectionHelper.setPrivateValue(PlayerControllerMP.class, mc.field_71442_b, Integer.valueOf(n), "field_78781_i");
/* 56 */     } catch (Exception var2) {
/* 57 */       var2.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ItemRenderer getItemRenderer() {
/* 62 */     return (Minecraft.func_71410_x()).field_71460_t.field_78516_c;
/*    */   }
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static void setItemRenderer(ItemRenderer r) {}
/*    */   
/*    */   @Nonnull
/*    */   public static ItemStack getItemRendererMainHand() {
/*    */     try {
/* 72 */       return (ItemStack)ObfuscationReflectionHelper.getPrivateValue(ItemRenderer.class, getItemRenderer(), "field_187467_d");
/* 73 */     } catch (Exception var1) {
/* 74 */       var1.printStackTrace();
/* 75 */       return ItemStack.field_190927_a;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void setItemRendererMainHand(ItemStack itemToRender) {
/*    */     try {
/* 81 */       ObfuscationReflectionHelper.setPrivateValue(ItemRenderer.class, getItemRenderer(), itemToRender, "field_187467_d");
/* 82 */     } catch (Exception var2) {
/* 83 */       var2.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void setItemRendererMainProgress(float equippedProgress) {
/*    */     try {
/* 89 */       ObfuscationReflectionHelper.setPrivateValue(ItemRenderer.class, getItemRenderer(), Float.valueOf(equippedProgress), "field_187469_f");
/* 90 */     } catch (Exception var2) {
/* 91 */       var2.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_Reflection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */