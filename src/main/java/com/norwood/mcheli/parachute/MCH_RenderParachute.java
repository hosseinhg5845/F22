/*    */ package com.norwood.mcheli.parachute;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_Config;
/*    */ import com.norwood.mcheli.MCH_ModelManager;
/*    */ import com.norwood.mcheli.wrapper.W_Render;
/*    */ import java.util.Random;
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
/*    */ public class MCH_RenderParachute extends W_Render<MCH_EntityParachute> {
/* 18 */   public static final IRenderFactory<MCH_EntityParachute> FACTORY = MCH_RenderParachute::new;
/* 19 */   public static final Random rand = new Random();
/*    */   
/*    */   public MCH_RenderParachute(RenderManager renderManager) {
/* 22 */     super(renderManager);
/* 23 */     this.field_76989_e = 0.5F;
/*    */   }
/*    */   
/*    */   public void doRender(@NotNull MCH_EntityParachute entity, double posX, double posY, double posZ, float par8, float tickTime) {
/* 27 */     int type = entity.getType();
/* 28 */     if (type > 0) {
/* 29 */       GL11.glPushMatrix();
/* 30 */       GL11.glEnable(2884);
/* 31 */       GL11.glTranslated(posX, posY, posZ);
/* 32 */       float prevYaw = entity.field_70126_B;
/* 33 */       if (entity.field_70177_z - prevYaw < -180.0F) {
/* 34 */         prevYaw -= 360.0F;
/* 35 */       } else if (prevYaw - entity.field_70177_z < -180.0F) {
/* 36 */         prevYaw += 360.0F;
/*    */       } 
/*    */       
/* 39 */       float yaw = prevYaw + (entity.field_70177_z - prevYaw) * tickTime;
/* 40 */       GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/* 41 */       GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/* 42 */       GL11.glEnable(3042);
/* 43 */       int srcBlend = GL11.glGetInteger(3041);
/* 44 */       int dstBlend = GL11.glGetInteger(3040);
/* 45 */       GL11.glBlendFunc(770, 771);
/* 46 */       if (MCH_Config.SmoothShading.prmBool) {
/* 47 */         GL11.glShadeModel(7425);
/*    */       }
/*    */       
/* 50 */       switch (type) {
/*    */         case 1:
/* 52 */           bindTexture("textures/parachute1.png");
/* 53 */           MCH_ModelManager.render("parachute1");
/*    */           break;
/*    */         case 2:
/* 56 */           bindTexture("textures/parachute2.png");
/* 57 */           if (entity.isOpenParachute()) {
/* 58 */             MCH_ModelManager.renderPart("parachute2", "$parachute"); break;
/*    */           } 
/* 60 */           MCH_ModelManager.renderPart("parachute2", "$seat");
/*    */           break;
/*    */         
/*    */         case 3:
/* 64 */           bindTexture("textures/parachute2.png");
/* 65 */           MCH_ModelManager.renderPart("parachute2", "$parachute");
/*    */           break;
/*    */       } 
/* 68 */       GL11.glBlendFunc(srcBlend, dstBlend);
/* 69 */       GL11.glDisable(3042);
/* 70 */       GL11.glShadeModel(7424);
/* 71 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(@NotNull MCH_EntityParachute entity) {
/* 76 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\parachute\MCH_RenderParachute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */