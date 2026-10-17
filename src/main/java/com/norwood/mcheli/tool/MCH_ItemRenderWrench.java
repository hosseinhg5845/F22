/*    */ package com.norwood.mcheli.tool;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_ModelManager;
/*    */ import com.norwood.mcheli.helper.client.IItemRenderer;
/*    */ import com.norwood.mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @Deprecated
/*    */ public class MCH_ItemRenderWrench
/*    */   implements IItemRenderer {
/*    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/* 14 */     return (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 19 */     return (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
/*    */   }
/*    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/*    */     float size;
/*    */     int useFrame;
/* 24 */     GL11.glPushMatrix();
/* 25 */     W_McClient.MOD_bindTexture("textures/wrench.png");
/*    */     
/* 27 */     switch (type) {
/*    */       case ENTITY:
/* 29 */         size = 2.2F;
/* 30 */         GL11.glScalef(size, size, size);
/* 31 */         GL11.glRotatef(-130.0F, 0.0F, 1.0F, 0.0F);
/* 32 */         GL11.glRotatef(-40.0F, 1.0F, 0.0F, 0.0F);
/* 33 */         GL11.glTranslatef(0.1F, 0.5F, -0.1F);
/*    */         break;
/*    */       case EQUIPPED:
/* 36 */         useFrame = MCH_ItemWrench.getUseAnimCount(item) - 8;
/* 37 */         if (useFrame < 0) {
/* 38 */           useFrame = -useFrame;
/*    */         }
/*    */         
/* 41 */         size = 2.2F;
/* 42 */         if (data.length >= 2) { Object object = data[1]; if (object instanceof EntityPlayer) { EntityPlayer player = (EntityPlayer)object;
/* 43 */             if (player.func_184605_cv() > 0) {
/* 44 */               float x = 0.8567F;
/* 45 */               float y = -0.0298F;
/* 46 */               float z = 0.0F;
/* 47 */               GL11.glTranslatef(-x, -y, -z);
/* 48 */               GL11.glRotatef((useFrame + 20), 1.0F, 0.0F, 0.0F);
/* 49 */               GL11.glTranslatef(x, y, z);
/*    */             }  }
/*    */            }
/*    */         
/* 53 */         GL11.glScalef(size, size, size);
/* 54 */         GL11.glRotatef(-200.0F, 0.0F, 1.0F, 0.0F);
/* 55 */         GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
/* 56 */         GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
/* 57 */         GL11.glTranslatef(-0.2F, 0.5F, -0.1F);
/*    */         break;
/*    */     } 
/* 60 */     MCH_ModelManager.render("wrench");
/* 61 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tool\MCH_ItemRenderWrench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */