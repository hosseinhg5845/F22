/*    */ package com.norwood.mcheli.gltd;
/*    */ 
/*    */ import com.norwood.mcheli.helper.client.IItemRenderer;
/*    */ import com.norwood.mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @Deprecated
/*    */ public class MCH_ItemGLTDRender
/*    */   implements IItemRenderer {
/*    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/* 12 */     return (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.ENTITY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 19 */     return (type == IItemRenderer.ItemRenderType.ENTITY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/* 24 */     GL11.glPushMatrix();
/* 25 */     GL11.glEnable(2884);
/* 26 */     W_McClient.MOD_bindTexture("textures/gltd.png");
/* 27 */     switch (type) {
/*    */       case ENTITY:
/* 29 */         GL11.glEnable(32826);
/* 30 */         GL11.glEnable(2903);
/* 31 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 32 */         MCH_RenderGLTD.model.renderAll();
/* 33 */         GL11.glDisable(32826);
/*    */         break;
/*    */       case EQUIPPED:
/* 36 */         GL11.glEnable(32826);
/* 37 */         GL11.glEnable(2903);
/* 38 */         GL11.glTranslatef(0.0F, 0.005F, -0.165F);
/* 39 */         GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
/* 40 */         GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
/* 41 */         MCH_RenderGLTD.model.renderAll();
/* 42 */         GL11.glDisable(32826);
/*    */         break;
/*    */       case EQUIPPED_FIRST_PERSON:
/* 45 */         GL11.glEnable(32826);
/* 46 */         GL11.glEnable(2903);
/* 47 */         GL11.glTranslatef(0.3F, 0.5F, -0.5F);
/* 48 */         GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 49 */         GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
/* 50 */         GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
/* 51 */         GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
/* 52 */         MCH_RenderGLTD.model.renderAll();
/* 53 */         GL11.glDisable(32826);
/*    */         break;
/*    */     } 
/* 56 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gltd\MCH_ItemGLTDRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */