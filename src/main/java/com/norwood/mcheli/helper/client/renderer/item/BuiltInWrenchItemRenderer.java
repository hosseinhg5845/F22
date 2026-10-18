/*    */ package com.norwood.mcheli.helper.client.renderer.item;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_ModelManager;
/*    */ import com.norwood.mcheli.tool.MCH_ItemWrench;
/*    */ import com.norwood.mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class BuiltInWrenchItemRenderer
/*    */   implements IItemModelRenderer {
/*    */   public boolean shouldRenderer(ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
/* 18 */     return (IItemModelRenderer.isFirstPerson(transformType) || IItemModelRenderer.isThirdPerson(transformType));
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderItem(ItemStack itemStack, EntityLivingBase entity, ItemCameraTransforms.TransformType transformType, float partialTicks) {
/* 23 */     GL11.glPushMatrix();
/* 24 */     W_McClient.MOD_bindTexture("textures/wrench.png");
/* 25 */     if (IItemModelRenderer.isFirstPerson(transformType) && entity.isHandActive() && entity.getActiveHand() == EnumHand.MAIN_HAND) {
/* 26 */       float f = MCH_ItemWrench.getUseAnimSmooth(itemStack, partialTicks);
/* 27 */       GL11.glRotatef(65.0F, 0.0F, 0.0F, 1.0F);
/* 28 */       GL11.glRotatef(f + 20.0F, 1.0F, 0.0F, 0.0F);
/*    */     } 
/*    */     
/* 31 */     MCH_ModelManager.render("wrench");
/* 32 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\renderer\item\BuiltInWrenchItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */