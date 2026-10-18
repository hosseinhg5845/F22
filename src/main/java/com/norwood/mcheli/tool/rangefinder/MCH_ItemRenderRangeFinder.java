/*    */ package com.norwood.mcheli.tool.rangefinder;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_ModelManager;
/*    */ import com.norwood.mcheli.helper.client.IItemRenderer;
/*    */ import com.norwood.mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @Deprecated
/*    */ public class MCH_ItemRenderRangeFinder implements IItemRenderer {
/*    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/* 14 */     return (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.ENTITY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 21 */     return (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.ENTITY);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/*    */     float size;
/* 28 */     GL11.glPushMatrix();
/* 29 */     W_McClient.MOD_bindTexture("textures/rangefinder.png");
/*    */     
/* 31 */     switch (type) {
/*    */       case ENTITY:
/* 33 */         size = 2.2F;
/* 34 */         GL11.glScalef(size, size, size);
/* 35 */         GL11.glRotatef(-130.0F, 0.0F, 1.0F, 0.0F);
/* 36 */         GL11.glRotatef(70.0F, 1.0F, 0.0F, 0.0F);
/* 37 */         GL11.glRotatef(5.0F, 0.0F, 0.0F, 1.0F);
/* 38 */         GL11.glTranslatef(0.0F, 0.0F, -0.0F);
/* 39 */         MCH_ModelManager.render("rangefinder");
/*    */         break;
/*    */       case EQUIPPED:
/* 42 */         size = 2.2F;
/* 43 */         GL11.glScalef(size, size, size);
/* 44 */         GL11.glRotatef(-130.0F, 0.0F, 1.0F, 0.0F);
/* 45 */         GL11.glRotatef(70.0F, 1.0F, 0.0F, 0.0F);
/* 46 */         GL11.glRotatef(5.0F, 0.0F, 0.0F, 1.0F);
/* 47 */         if ((Minecraft.getMinecraft()).player.getItemInUseMaxCount() > 0) {
/* 48 */           GL11.glTranslatef(0.4F, -0.35F, -0.3F);
/*    */         } else {
/* 50 */           GL11.glTranslatef(0.2F, -0.35F, -0.3F);
/*    */         } 
/*    */         
/* 53 */         MCH_ModelManager.render("rangefinder");
/*    */         break;
/*    */       case EQUIPPED_FIRST_PERSON:
/* 56 */         if (!MCH_ItemRangeFinder.isUsingScope((EntityPlayer)(Minecraft.getMinecraft()).player)) {
/* 57 */           size = 2.2F;
/* 58 */           GL11.glScalef(size, size, size);
/* 59 */           GL11.glRotatef(-210.0F, 0.0F, 1.0F, 0.0F);
/* 60 */           GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
/* 61 */           GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
/* 62 */           GL11.glTranslatef(0.06F, 0.53F, -0.1F);
/* 63 */           MCH_ModelManager.render("rangefinder");
/*    */         } 
/*    */         break;
/*    */     } 
/* 67 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tool\rangefinder\MCH_ItemRenderRangeFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */