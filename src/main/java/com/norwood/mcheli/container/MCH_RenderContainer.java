/*    */ package com.norwood.mcheli.container;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.MCH_ModelManager;
/*    */ import com.norwood.mcheli.aircraft.MCH_RenderAircraft;
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
/*    */ public class MCH_RenderContainer extends W_Render<MCH_EntityContainer> {
/* 19 */   public static final IRenderFactory<MCH_EntityContainer> FACTORY = MCH_RenderContainer::new;
/* 20 */   public static final Random rand = new Random();
/*    */   
/*    */   public MCH_RenderContainer(RenderManager renderManager) {
/* 23 */     super(renderManager);
/* 24 */     this.field_76989_e = 0.5F;
/*    */   }
/*    */   
/*    */   public void doRender(@NotNull MCH_EntityContainer entity, double posX, double posY, double posZ, float par8, float tickTime) {
/* 28 */     if (!MCH_RenderAircraft.shouldSkipRender((Entity)entity)) {
/* 29 */       GL11.glPushMatrix();
/* 30 */       GL11.glEnable(2884);
/* 31 */       GL11.glTranslated(posX, posY - 0.2D + 0.5D, posZ);
/* 32 */       float yaw = MCH_Lib.smoothRot(entity.field_70177_z, entity.field_70126_B, tickTime);
/* 33 */       float pitch = MCH_Lib.smoothRot(entity.field_70125_A, entity.field_70127_C, tickTime);
/* 34 */       GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/* 35 */       GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/* 36 */       GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/* 37 */       bindTexture("textures/container.png");
/* 38 */       MCH_ModelManager.render("container");
/* 39 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(@NotNull MCH_EntityContainer entity) {
/* 44 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\container\MCH_RenderContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */