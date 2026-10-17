/*    */ package com.norwood.mcheli.helper.client.renderer.item;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_ModelManager;
/*    */ import com.norwood.mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class BuiltInDraftingTableItemRenderer
/*    */   implements IItemModelRenderer {
/*    */   public boolean shouldRenderer(ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
/* 16 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderItem(ItemStack itemStack, EntityLivingBase entityLivingBase, ItemCameraTransforms.TransformType transformType, float partialTicks) {
/* 21 */     GL11.glPushMatrix();
/* 22 */     W_McClient.MOD_bindTexture("textures/blocks/drafting_table.png");
/* 23 */     GL11.glEnable(32826);
/* 24 */     switch (transformType) {
/*    */       case GROUND:
/* 26 */         GL11.glTranslatef(0.0F, 0.5F, 0.0F);
/* 27 */         GL11.glScalef(1.5F, 1.5F, 1.5F);
/*    */         break;
/*    */       case GUI:
/*    */       case FIXED:
/* 31 */         GL11.glTranslatef(0.0F, -0.5F, 0.0F);
/* 32 */         GL11.glScalef(0.75F, 0.75F, 0.75F);
/*    */         break;
/*    */       case THIRD_PERSON_LEFT_HAND:
/*    */       case THIRD_PERSON_RIGHT_HAND:
/* 36 */         GL11.glTranslatef(0.0F, 0.0F, 0.5F);
/* 37 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/*    */         break;
/*    */       case FIRST_PERSON_LEFT_HAND:
/*    */       case FIRST_PERSON_RIGHT_HAND:
/* 41 */         GL11.glTranslatef(0.75F, 0.0F, 0.0F);
/* 42 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 43 */         GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
/*    */         break;
/*    */     } 
/* 46 */     MCH_ModelManager.render("blocks", "drafting_table");
/* 47 */     GL11.glPopMatrix();
/* 48 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 49 */     GL11.glEnable(3042);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\renderer\item\BuiltInDraftingTableItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */