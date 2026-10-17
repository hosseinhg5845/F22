/*    */ package com.norwood.mcheli.flare;
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
/*    */ public class MCH_RenderFlare extends W_Render<MCH_EntityFlare> {
/* 14 */   public static final IRenderFactory<MCH_EntityFlare> FACTORY = MCH_RenderFlare::new;
/* 15 */   protected final MCH_ModelFlare model = new MCH_ModelFlare();
/*    */   
/*    */   public MCH_RenderFlare(RenderManager renderManager) {
/* 18 */     super(renderManager);
/*    */   }
/*    */   
/*    */   public void doRender(MCH_EntityFlare entity, double posX, double posY, double posZ, float yaw, float partialTickTime) {
/* 22 */     GL11.glPushMatrix();
/* 23 */     GL11.glEnable(2884);
/* 24 */     GL11.glTranslated(posX, posY, posZ);
/* 25 */     GL11.glRotatef(-entity.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 26 */     GL11.glRotatef(entity.field_70125_A, 1.0F, 0.0F, 0.0F);
/* 27 */     GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);
/* 28 */     GL11.glColor4f(1.0F, 1.0F, 0.5F, 1.0F);
/* 29 */     bindTexture("textures/flare.png");
/* 30 */     this.model.renderModel(0.0D, 0.0D, 0.0625F);
/* 31 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(@NotNull MCH_EntityFlare entity) {
/* 35 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\flare\MCH_RenderFlare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */