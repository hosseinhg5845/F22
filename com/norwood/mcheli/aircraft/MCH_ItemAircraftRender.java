/*    */ package com.norwood.mcheli.aircraft;
/*    */ import com.norwood.mcheli.MCH_ModelManager;
/*    */ import com.norwood.mcheli.helper.client.IItemRenderer;
/*    */ import com.norwood.mcheli.wrapper.W_McClient;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @Deprecated
/*    */ public class MCH_ItemAircraftRender implements IItemRenderer {
/* 11 */   final float size = 0.1F;
/* 12 */   final float x = 0.1F;
/* 13 */   final float y = 0.1F;
/* 14 */   final float z = 0.1F;
/*    */ 
/*    */   
/*    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/* 18 */     if (item != null && item.func_77973_b() instanceof MCH_ItemAircraft) {
/* 19 */       MCH_AircraftInfo info = ((MCH_ItemAircraft)item.func_77973_b()).getAircraftInfo();
/* 20 */       if (info == null) {
/* 21 */         return false;
/*    */       }
/*    */       
/* 24 */       if (info.name.equalsIgnoreCase("mh-60l_dap")) {
/* 25 */         return (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.ENTITY || type == IItemRenderer.ItemRenderType.INVENTORY);
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 37 */     return (type == IItemRenderer.ItemRenderType.ENTITY || type == IItemRenderer.ItemRenderType.INVENTORY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/* 42 */     GL11.glPushMatrix();
/* 43 */     GL11.glEnable(2884);
/* 44 */     W_McClient.MOD_bindTexture("textures/helicopters/mh-60l_dap.png");
/* 45 */     switch (type) {
/*    */       case ENTITY:
/* 47 */         GL11.glEnable(32826);
/* 48 */         GL11.glEnable(2903);
/* 49 */         GL11.glScalef(0.1F, 0.1F, 0.1F);
/* 50 */         MCH_ModelManager.render("helicopters", "mh-60l_dap");
/* 51 */         GL11.glDisable(32826);
/*    */         break;
/*    */       case EQUIPPED:
/* 54 */         GL11.glEnable(32826);
/* 55 */         GL11.glEnable(2903);
/* 56 */         GL11.glTranslatef(0.0F, 0.005F, -0.165F);
/* 57 */         GL11.glScalef(0.1F, 0.1F, 0.1F);
/* 58 */         GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
/* 59 */         GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
/* 60 */         GL11.glRotatef(-50.0F, 1.0F, 0.0F, 0.0F);
/* 61 */         MCH_ModelManager.render("helicopters", "mh-60l_dap");
/* 62 */         GL11.glDisable(32826);
/*    */         break;
/*    */       case EQUIPPED_FIRST_PERSON:
/* 65 */         GL11.glEnable(32826);
/* 66 */         GL11.glEnable(2903);
/* 67 */         GL11.glTranslatef(0.3F, 0.5F, -0.5F);
/* 68 */         GL11.glScalef(0.1F, 0.1F, 0.1F);
/* 69 */         GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
/* 70 */         GL11.glRotatef(140.0F, 0.0F, 1.0F, 0.0F);
/* 71 */         GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
/* 72 */         MCH_ModelManager.render("helicopters", "mh-60l_dap");
/* 73 */         GL11.glDisable(32826);
/*    */         break;
/*    */       case INVENTORY:
/* 76 */         Objects.requireNonNull(this); Objects.requireNonNull(this); Objects.requireNonNull(this); GL11.glTranslatef(0.1F, 0.1F, 0.1F);
/* 77 */         Objects.requireNonNull(this); Objects.requireNonNull(this); Objects.requireNonNull(this); GL11.glScalef(0.1F, 0.1F, 0.1F);
/* 78 */         MCH_ModelManager.render("helicopters", "mh-60l_dap");
/*    */         break;
/*    */     } 
/*    */     
/* 82 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_ItemAircraftRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */