/*    */ package com.norwood.mcheli.debug;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_Config;
/*    */ import com.norwood.mcheli.wrapper.W_Render;
/*    */ import net.minecraft.client.renderer.entity.Render;
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
/*    */ public class MCH_RenderTest
/*    */   extends W_Render<Entity>
/*    */ {
/*    */   protected final MCH_ModelTest model;
/*    */   private final float offsetX;
/*    */   
/*    */   public MCH_RenderTest(RenderManager renderManager, float x, float y, float z, String texture_name) {
/* 23 */     super(renderManager);
/* 24 */     this.offsetX = x;
/* 25 */     this.offsetY = y;
/* 26 */     this.offsetZ = z;
/* 27 */     this.textureName = texture_name;
/* 28 */     this.model = new MCH_ModelTest();
/*    */   }
/*    */   private final float offsetY; private final float offsetZ; private final String textureName;
/*    */   public static IRenderFactory<Entity> factory(float x, float y, float z, String texture_name) {
/* 32 */     return renderManager -> new MCH_RenderTest(renderManager, x, y, z, texture_name);
/*    */   }
/*    */   
/*    */   public void doRender(@NotNull Entity e, double posX, double posY, double posZ, float par8, float par9) {
/* 36 */     if (MCH_Config.TestMode.prmBool) {
/* 37 */       float prevYaw; GL11.glPushMatrix();
/* 38 */       GL11.glTranslated(posX + this.offsetX, posY + this.offsetY, posZ + this.offsetZ);
/* 39 */       GL11.glScalef(e.width, e.height, e.width);
/* 40 */       GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
/*    */       
/* 42 */       if (e.rotationYaw - e.prevRotationYaw < -180.0F) {
/* 43 */         prevYaw = e.prevRotationYaw - 360.0F;
/* 44 */       } else if (e.prevRotationYaw - e.rotationYaw < -180.0F) {
/* 45 */         prevYaw = e.prevRotationYaw + 360.0F;
/*    */       } else {
/* 47 */         prevYaw = e.prevRotationYaw;
/*    */       } 
/*    */       
/* 50 */       float yaw = -(prevYaw + (e.rotationYaw - prevYaw) * par9) - 180.0F;
/* 51 */       float pitch = -(e.prevRotationPitch + (e.rotationPitch - e.prevRotationPitch) * par9);
/* 52 */       GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
/* 53 */       GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/* 54 */       bindTexture("textures/" + this.textureName + ".png");
/* 55 */       this.model.renderModel(0.0D, 0.0D, 0.1F);
/* 56 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(Entity entity) {
/* 62 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\MCH_RenderTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */