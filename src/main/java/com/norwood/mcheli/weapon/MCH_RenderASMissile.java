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
/*    */ public class MCH_RenderASMissile extends MCH_RenderBulletBase<MCH_EntityBaseBullet> {
/* 12 */   public static final IRenderFactory<MCH_EntityBaseBullet> FACTORY = MCH_RenderASMissile::new;
/*    */   
/*    */   public MCH_RenderASMissile(RenderManager renderManager) {
/* 15 */     super(renderManager);
/* 16 */     this.field_76989_e = 0.5F;
/*    */   }
/*    */   
/*    */   public void renderBullet(MCH_EntityBaseBullet entity, double posX, double posY, double posZ, float yaw, float partialTickTime) {
/* 20 */     if (entity instanceof MCH_EntityBaseBullet) {
/* 21 */       GL11.glPushMatrix();
/* 22 */       GL11.glTranslated(posX, posY, posZ);
/* 23 */       GL11.glRotatef(-entity.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 24 */       GL11.glRotatef(-entity.field_70125_A, -1.0F, 0.0F, 0.0F);
/* 25 */       renderModel(entity);
/* 26 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityBaseBullet entity) {
/* 31 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_RenderASMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */