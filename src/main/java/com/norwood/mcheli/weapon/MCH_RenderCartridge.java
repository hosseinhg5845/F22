/*    */ package com.norwood.mcheli.weapon;
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
/*    */ public class MCH_RenderCartridge extends W_Render<MCH_EntityCartridge> {
/* 14 */   public static final IRenderFactory<MCH_EntityCartridge> FACTORY = MCH_RenderCartridge::new;
/*    */   
/*    */   public MCH_RenderCartridge(RenderManager renderManager) {
/* 17 */     super(renderManager);
/* 18 */     this.field_76989_e = 0.0F;
/*    */   }
/*    */   
/*    */   public void doRender(MCH_EntityCartridge entity, double posX, double posY, double posZ, float par8, float tickTime) {
/* 22 */     MCH_EntityCartridge cartridge = null;
/* 23 */     if (entity.model != null && !entity.texture_name.isEmpty()) {
/* 24 */       GL11.glPushMatrix();
/* 25 */       GL11.glTranslated(posX, posY, posZ);
/* 26 */       GL11.glScalef(entity.getScale(), entity.getScale(), entity.getScale());
/* 27 */       float prevYaw = entity.field_70126_B;
/* 28 */       if (entity.field_70177_z - prevYaw < -180.0F) {
/* 29 */         prevYaw -= 360.0F;
/* 30 */       } else if (prevYaw - entity.field_70177_z < -180.0F) {
/* 31 */         prevYaw += 360.0F;
/*    */       } 
/*    */       
/* 34 */       float yaw = -(prevYaw + (entity.field_70177_z - prevYaw) * tickTime);
/* 35 */       float pitch = entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * tickTime;
/* 36 */       GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
/* 37 */       GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/* 38 */       bindTexture("textures/bullets/" + entity.texture_name + ".png");
/* 39 */       entity.model.renderAll();
/* 40 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(@NotNull MCH_EntityCartridge entity) {
/* 45 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_RenderCartridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */