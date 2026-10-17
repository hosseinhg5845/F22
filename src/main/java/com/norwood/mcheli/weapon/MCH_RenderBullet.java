/*    */ package com.norwood.mcheli.weapon;
/*    */ import com.norwood.mcheli.wrapper.W_Entity;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderBullet extends MCH_RenderBulletBase<MCH_EntityBaseBullet> {
/* 12 */   public static final IRenderFactory<MCH_EntityBaseBullet> FACTORY = MCH_RenderBullet::new;
/*    */   
/*    */   protected MCH_RenderBullet(RenderManager renderManager) {
/* 15 */     super(renderManager);
/*    */   }
/*    */   
/*    */   public void renderBullet(MCH_EntityBaseBullet entity, double posX, double posY, double posZ, float yaw, float tickTime) {
/* 19 */     GL11.glPushMatrix();
/* 20 */     GL11.glTranslated(posX, posY, posZ);
/* 21 */     GL11.glRotatef(-entity.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 22 */     GL11.glRotatef(entity.field_70125_A, 1.0F, 0.0F, 0.0F);
/* 23 */     renderModel(entity);
/* 24 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityBaseBullet entity) {
/* 28 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_RenderBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */