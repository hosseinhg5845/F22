/*    */ package com.norwood.mcheli.throwable;
/*    */ import com.norwood.mcheli.wrapper.W_Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderThrowable extends W_Render<MCH_EntityThrowable> {
/* 14 */   public static final IRenderFactory<MCH_EntityThrowable> FACTORY = MCH_RenderThrowable::new;
/*    */   
/*    */   public MCH_RenderThrowable(RenderManager renderManager) {
/* 17 */     super(renderManager);
/* 18 */     this.field_76989_e = 0.0F;
/*    */   }
/*    */   
/*    */   public void doRender(MCH_EntityThrowable entity, double posX, double posY, double posZ, float par8, float tickTime) {
/* 22 */     MCH_ThrowableInfo info = entity.getInfo();
/* 23 */     if (info != null) {
/* 24 */       GL11.glPushMatrix();
/* 25 */       GL11.glTranslated(posX, posY, posZ);
/* 26 */       GL11.glRotatef(entity.field_70177_z, 0.0F, -1.0F, 0.0F);
/* 27 */       GL11.glRotatef(entity.field_70125_A, 1.0F, 0.0F, 0.0F);
/* 28 */       setCommonRenderParam(true, entity.func_70070_b());
/* 29 */       if (info.model != null) {
/* 30 */         bindTexture("textures/throwable/" + info.name + ".png");
/* 31 */         info.model.renderAll();
/*    */       } 
/*    */       
/* 34 */       restoreCommonRenderParam();
/* 35 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(@NotNull MCH_EntityThrowable entity) {
/* 40 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\throwable\MCH_RenderThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */