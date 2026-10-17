/*    */ package com.norwood.mcheli.weapon;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*    */ import com.norwood.mcheli.uav.MCH_EntityUavStation;
/*    */ import com.norwood.mcheli.wrapper.W_Entity;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderTvMissile extends MCH_RenderBulletBase<MCH_EntityBaseBullet> {
/* 17 */   public static final IRenderFactory<MCH_EntityBaseBullet> FACTORY = MCH_RenderTvMissile::new;
/*    */   
/*    */   public MCH_RenderTvMissile(RenderManager renderManager) {
/* 20 */     super(renderManager);
/* 21 */     this.field_76989_e = 0.5F;
/*    */   }
/*    */   
/*    */   public void renderBullet(MCH_EntityBaseBullet entity, double posX, double posY, double posZ, float par8, float par9) {
/* 25 */     MCH_EntityAircraft ac = null;
/* 26 */     Entity ridingEntity = (Minecraft.func_71410_x()).field_71439_g.func_184187_bx();
/* 27 */     if (ridingEntity instanceof MCH_EntityAircraft) {
/* 28 */       ac = (MCH_EntityAircraft)ridingEntity;
/* 29 */     } else if (ridingEntity instanceof MCH_EntitySeat) {
/* 30 */       ac = ((MCH_EntitySeat)ridingEntity).getParent();
/* 31 */     } else if (ridingEntity instanceof MCH_EntityUavStation) {
/* 32 */       ac = ((MCH_EntityUavStation)ridingEntity).getControlAircract();
/*    */     } 
/*    */     
/* 35 */     if ((ac == null || ac.isRenderBullet((Entity)entity, (Entity)(Minecraft.func_71410_x()).field_71439_g)) && 
/* 36 */       entity instanceof MCH_EntityBaseBullet) {
/* 37 */       GL11.glPushMatrix();
/* 38 */       GL11.glTranslated(posX, posY, posZ);
/* 39 */       GL11.glRotatef(-entity.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 40 */       GL11.glRotatef(-entity.field_70125_A, -1.0F, 0.0F, 0.0F);
/* 41 */       renderModel(entity);
/* 42 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityBaseBullet entity) {
/* 48 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_RenderTvMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */