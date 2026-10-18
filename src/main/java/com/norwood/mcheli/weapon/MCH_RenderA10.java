/*    */ package com.norwood.mcheli.weapon;
/*    */ import com.norwood.mcheli.MCH_ModelManager;
/*    */ import com.norwood.mcheli.wrapper.W_Entity;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderA10 extends MCH_RenderBulletBase<MCH_EntityA10> {
/* 13 */   public static final IRenderFactory<MCH_EntityA10> FACTORY = MCH_RenderA10::new;
/*    */   
/*    */   public MCH_RenderA10(RenderManager renderManager) {
/* 16 */     super(renderManager);
/* 17 */     this.shadowSize = 10.5F;
/*    */   }
/*    */   
/*    */   public void renderBullet(MCH_EntityA10 e, double posX, double posY, double posZ, float par8, float tickTime) {
/* 21 */     if (e instanceof MCH_EntityA10 && 
/* 22 */       e.isRender()) {
/* 23 */       GL11.glPushMatrix();
/* 24 */       GL11.glTranslated(posX, posY, posZ);
/* 25 */       float yaw = -(e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * tickTime);
/* 26 */       float pitch = -(e.prevRotationPitch + (e.rotationPitch - e.prevRotationPitch) * tickTime);
/* 27 */       GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
/* 28 */       GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/* 29 */       bindTexture("textures/bullets/a10.png");
/* 30 */       MCH_ModelManager.render("a-10");
/* 31 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityA10 entity) {
/* 37 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_RenderA10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */