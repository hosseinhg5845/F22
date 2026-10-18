/*    */ package com.norwood.mcheli.gltd;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_RenderLib;
/*    */ import com.norwood.mcheli.helper.client._IModelCustom;
/*    */ import com.norwood.mcheli.wrapper.W_Lib;
/*    */ import com.norwood.mcheli.wrapper.W_Render;
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.culling.ICamera;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
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
/*    */ public class MCH_RenderGLTD extends W_Render<MCH_EntityGLTD> {
/* 22 */   public static final IRenderFactory<MCH_EntityGLTD> FACTORY = MCH_RenderGLTD::new;
/* 23 */   public static final Random rand = new Random();
/*    */   public static _IModelCustom model;
/*    */   
/*    */   public MCH_RenderGLTD(RenderManager renderManager) {
/* 27 */     super(renderManager);
/* 28 */     this.shadowSize = 0.5F;
/*    */   }
/*    */   
/*    */   public void doRender(MCH_EntityGLTD entity, double posX, double posY, double posZ, float par8, float tickTime) {
/* 32 */     GL11.glPushMatrix();
/* 33 */     GL11.glTranslated(posX, posY + 0.25D, posZ);
/* 34 */     setCommonRenderParam(true, entity.getBrightnessForRender());
/* 35 */     bindTexture("textures/gltd.png");
/* 36 */     Minecraft mc = Minecraft.getMinecraft();
/* 37 */     boolean isNotRenderHead = false;
/* 38 */     if (entity.getRiddenByEntity() != null) {
/* 39 */       entity.isUsedPlayer = true;
/* 40 */       entity.renderRotaionYaw = (entity.getRiddenByEntity()).rotationYaw;
/* 41 */       entity.renderRotaionPitch = (entity.getRiddenByEntity()).rotationPitch;
/* 42 */       isNotRenderHead = (mc.gameSettings.thirdPersonView == 0 && W_Lib.isClientPlayer(entity.getRiddenByEntity()));
/*    */     } 
/*    */     
/* 45 */     if (entity.isUsedPlayer) {
/* 46 */       GL11.glPushMatrix();
/* 47 */       GL11.glRotatef(-entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/* 48 */       model.renderPart("$body");
/* 49 */       GL11.glPopMatrix();
/*    */     } else {
/* 51 */       GL11.glRotatef(-entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/* 52 */       model.renderPart("$body");
/*    */     } 
/*    */     
/* 55 */     GL11.glTranslatef(0.0F, 0.45F, 0.0F);
/* 56 */     if (entity.isUsedPlayer) {
/* 57 */       GL11.glRotatef(entity.renderRotaionYaw, 0.0F, -1.0F, 0.0F);
/* 58 */       GL11.glRotatef(entity.renderRotaionPitch, 1.0F, 0.0F, 0.0F);
/*    */     } 
/*    */     
/* 61 */     GL11.glTranslatef(0.0F, -0.45F, 0.0F);
/* 62 */     if (!isNotRenderHead) {
/* 63 */       model.renderPart("$head");
/*    */     }
/*    */     
/* 66 */     GL11.glTranslatef(0.0F, 0.45F, 0.0F);
/* 67 */     restoreCommonRenderParam();
/* 68 */     GL11.glDisable(2896);
/* 69 */     Vec3d[] v = { new Vec3d(0.0D, 0.2D, 0.0D), new Vec3d(0.0D, 0.2D, 100.0D) };
/* 70 */     int a = rand.nextInt(64);
/* 71 */     MCH_RenderLib.drawLine(v, 0x6080FF80 | a << 24);
/* 72 */     GL11.glEnable(2896);
/* 73 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   public boolean shouldRender(@NotNull MCH_EntityGLTD livingEntity, @NotNull ICamera camera, double camX, double camY, double camZ) {
/* 77 */     return true;
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(@NotNull MCH_EntityGLTD entity) {
/* 81 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gltd\MCH_RenderGLTD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */