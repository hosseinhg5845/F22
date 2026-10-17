/*    */ package com.norwood.mcheli.chain;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.MCH_ModelManager;
/*    */ import com.norwood.mcheli.wrapper.W_Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderChain extends W_Render<MCH_EntityChain> {
/* 18 */   public static final IRenderFactory<MCH_EntityChain> FACTORY = MCH_RenderChain::new;
/*    */   
/*    */   public MCH_RenderChain(RenderManager renderManager) {
/* 21 */     super(renderManager);
/*    */   }
/*    */   
/*    */   public void doRender(@NotNull MCH_EntityChain e, double posX, double posY, double posZ, float par8, float par9) {
/* 25 */     if (e.towedEntity != null && e.towEntity != null) {
/* 26 */       GL11.glPushMatrix();
/* 27 */       GL11.glEnable(2884);
/* 28 */       GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
/* 29 */       GL11.glTranslated(e.towedEntity.field_70142_S - TileEntityRendererDispatcher.field_147554_b, e.towedEntity.field_70137_T - TileEntityRendererDispatcher.field_147555_c, e.towedEntity.field_70136_U - TileEntityRendererDispatcher.field_147552_d);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 34 */       bindTexture("textures/chain.png");
/* 35 */       double dx = e.towEntity.field_70142_S - e.towedEntity.field_70142_S;
/* 36 */       double dy = e.towEntity.field_70137_T - e.towedEntity.field_70137_T;
/* 37 */       double dz = e.towEntity.field_70136_U - e.towedEntity.field_70136_U;
/* 38 */       double diff = Math.sqrt(dx * dx + dy * dy + dz * dz);
/* 39 */       double x = dx * 0.949999988079071D / diff;
/* 40 */       double y = dy * 0.949999988079071D / diff;
/*    */       
/* 42 */       for (double z = dz * 0.949999988079071D / diff; diff > 0.949999988079071D; diff -= 0.949999988079071D) {
/* 43 */         GL11.glTranslated(x, y, z);
/* 44 */         GL11.glPushMatrix();
/* 45 */         Vec3d v = MCH_Lib.getYawPitchFromVec(x, y, z);
/* 46 */         GL11.glRotatef((float)v.field_72448_b, 0.0F, -1.0F, 0.0F);
/* 47 */         GL11.glRotatef((float)v.field_72449_c, 0.0F, 0.0F, 1.0F);
/* 48 */         MCH_ModelManager.render("chain");
/* 49 */         GL11.glPopMatrix();
/*    */       } 
/*    */       
/* 52 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(@NotNull MCH_EntityChain entity) {
/* 57 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\chain\MCH_RenderChain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */