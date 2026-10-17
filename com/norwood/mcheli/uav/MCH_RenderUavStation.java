/*    */ package com.norwood.mcheli.uav;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.MCH_ModelManager;
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
/*    */ public class MCH_RenderUavStation extends W_Render<MCH_EntityUavStation> {
/* 16 */   public static final IRenderFactory<MCH_EntityUavStation> FACTORY = MCH_RenderUavStation::new;
/* 17 */   public static final String[] MODEL_NAME = new String[] { "uav_station", "uav_portable_controller" };
/* 18 */   public static final String[] TEX_NAME_ON = new String[] { "uav_station_on", "uav_portable_controller_on" };
/* 19 */   public static final String[] TEX_NAME_OFF = new String[] { "uav_station", "uav_portable_controller" };
/*    */   
/*    */   public MCH_RenderUavStation(RenderManager renderManager) {
/* 22 */     super(renderManager);
/* 23 */     this.field_76989_e = 1.0F;
/*    */   }
/*    */   
/*    */   public void doRender(@NotNull MCH_EntityUavStation entity, double posX, double posY, double posZ, float par8, float tickTime) {
/* 27 */     if (entity.getKind() > 0) {
/* 28 */       int kind = entity.getKind() - 1;
/* 29 */       GL11.glPushMatrix();
/* 30 */       GL11.glTranslated(posX, posY + 0.3499999940395355D, posZ);
/* 31 */       GL11.glEnable(2884);
/* 32 */       GL11.glRotatef(entity.field_70177_z, 0.0F, -1.0F, 0.0F);
/* 33 */       GL11.glRotatef(entity.field_70125_A, 1.0F, 0.0F, 0.0F);
/* 34 */       GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/* 35 */       GL11.glEnable(3042);
/* 36 */       int srcBlend = GL11.glGetInteger(3041);
/* 37 */       int dstBlend = GL11.glGetInteger(3040);
/* 38 */       GL11.glBlendFunc(770, 771);
/* 39 */       if (kind == 0) {
/* 40 */         if (entity.getControlAircract() != null && entity.getRiddenByEntity() != null) {
/* 41 */           bindTexture("textures/" + TEX_NAME_ON[kind] + ".png");
/*    */         } else {
/* 43 */           bindTexture("textures/" + TEX_NAME_OFF[kind] + ".png");
/*    */         } 
/*    */         
/* 46 */         MCH_ModelManager.render(MODEL_NAME[kind]);
/*    */       } else {
/* 48 */         if (entity.rotCover > 0.95F) {
/* 49 */           bindTexture("textures/" + TEX_NAME_ON[kind] + ".png");
/*    */         } else {
/* 51 */           bindTexture("textures/" + TEX_NAME_OFF[kind] + ".png");
/*    */         } 
/*    */         
/* 54 */         renderPortableController(entity, MODEL_NAME[kind], tickTime);
/*    */       } 
/*    */       
/* 57 */       GL11.glBlendFunc(srcBlend, dstBlend);
/* 58 */       GL11.glDisable(3042);
/* 59 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void renderPortableController(MCH_EntityUavStation uavSt, String name, float tickTime) {
/* 64 */     MCH_ModelManager.renderPart(name, "$body");
/* 65 */     float rot = MCH_Lib.smooth(uavSt.rotCover, uavSt.prevRotCover, tickTime);
/* 66 */     renderRotPart(name, "$cover", rot * 60.0F, 0.0D, -0.1812D, -0.3186D);
/* 67 */     renderRotPart(name, "$laptop_cover", rot * 95.0F, 0.0D, -0.1808D, -0.0422D);
/* 68 */     renderRotPart(name, "$display", rot * -85.0F, 0.0D, -0.1807D, 0.2294D);
/*    */   }
/*    */   
/*    */   private void renderRotPart(String modelName, String partName, float rot, double x, double y, double z) {
/* 72 */     GL11.glPushMatrix();
/* 73 */     GL11.glTranslated(x, y, z);
/* 74 */     GL11.glRotatef(rot, -1.0F, 0.0F, 0.0F);
/* 75 */     GL11.glTranslated(-x, -y, -z);
/* 76 */     MCH_ModelManager.renderPart(modelName, partName);
/* 77 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(@NotNull MCH_EntityUavStation entity) {
/* 81 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mchel\\uav\MCH_RenderUavStation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */