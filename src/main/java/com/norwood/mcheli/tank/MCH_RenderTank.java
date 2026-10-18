/*     */ package com.norwood.mcheli.tank;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_RenderAircraft;
/*     */ import com.norwood.mcheli.helper.MCH_ColorInt;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_RenderTank extends MCH_RenderAircraft<MCH_EntityTank> {
/*  20 */   public static final IRenderFactory<MCH_EntityTank> FACTORY = MCH_RenderTank::new;
/*     */   
/*     */   public MCH_RenderTank(RenderManager renderManager) {
/*  23 */     super(renderManager);
/*  24 */     this.shadowSize = 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAircraft(MCH_EntityAircraft entity, double posX, double posY, double posZ, float yaw, float pitch, float roll, float tickTime) {
/*  30 */     if (entity instanceof MCH_EntityTank) { MCH_EntityTank tank = (MCH_EntityTank)entity;
/*  31 */       MCH_TankInfo tankInfo = tank.getTankInfo();
/*  32 */       if (tankInfo != null) {
/*  33 */         posY += 0.3499999940395355D;
/*  34 */         renderWheel(tank, posX, posY, posZ);
/*  35 */         renderDebugHitBox(tank, posX, posY, posZ, yaw, pitch);
/*  36 */         renderDebugPilotSeat(tank, posX, posY, posZ, yaw, pitch, roll);
/*  37 */         GL11.glTranslated(posX, posY, posZ);
/*  38 */         GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*  39 */         GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*  40 */         GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
/*  41 */         bindTexture("textures/tanks/" + tank.getTextureName() + ".png", tank);
/*  42 */         renderBody(tankInfo.model);
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   public void renderWheel(MCH_EntityTank tank, double posX, double posY, double posZ) {
/*  48 */     if (MCH_Config.TestMode.prmBool && 
/*  49 */       debugModel != null) {
/*  50 */       GL11.glColor4f(0.75F, 0.75F, 0.75F, 0.5F);
/*     */       
/*  52 */       for (MCH_EntityWheel w : tank.WheelMng.wheels) {
/*  53 */         GL11.glPushMatrix();
/*  54 */         GL11.glTranslated(w.posX - tank.posX + posX, w.posY - tank.posY + posY + 0.25D, w.posZ - tank.posZ + posZ);
/*  55 */         GL11.glScalef(w.width, w.height / 2.0F, w.width);
/*  56 */         bindTexture("textures/seat_pilot.png");
/*  57 */         debugModel.renderAll();
/*  58 */         GL11.glPopMatrix();
/*     */       } 
/*     */       
/*  61 */       GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/*  62 */       Tessellator tessellator = Tessellator.getInstance();
/*  63 */       BufferBuilder builder = tessellator.getBuffer();
/*  64 */       builder.begin(1, DefaultVertexFormats.POSITION_COLOR);
/*  65 */       Vec3d wp = tank.getTransformedPosition(tank.WheelMng.weightedCenter);
/*  66 */       wp = wp.subtract(tank.posX, tank.posY, tank.posZ);
/*     */       
/*  68 */       for (int i = 0; i < tank.WheelMng.wheels.length / 2; i++) {
/*  69 */         MCH_ColorInt cint = new MCH_ColorInt(((i & 0x4) > 0) ? 16711680 : 0, ((i & 0x2) > 0) ? 65280 : 0, ((i & 0x1) > 0) ? 255 : 0, 192);
/*  70 */         MCH_EntityWheel w1 = tank.WheelMng.wheels[i * 2];
/*  71 */         MCH_EntityWheel w2 = tank.WheelMng.wheels[i * 2 + 1];
/*  72 */         if (w1.isPlus) {
/*  73 */           builder.pos(w2.posX - tank.posX + posX, w2.posY - tank.posY + posY, w2.posZ - tank.posZ + posZ)
/*  74 */             .color(cint.r, cint.g, cint.b, cint.a)
/*  75 */             .endVertex();
/*  76 */           builder.pos(w1.posX - tank.posX + posX, w1.posY - tank.posY + posY, w1.posZ - tank.posZ + posZ)
/*  77 */             .color(cint.r, cint.g, cint.b, cint.a)
/*  78 */             .endVertex();
/*  79 */           builder.pos(w1.posX - tank.posX + posX, w1.posY - tank.posY + posY, w1.posZ - tank.posZ + posZ)
/*  80 */             .color(cint.r, cint.g, cint.b, cint.a)
/*  81 */             .endVertex();
/*  82 */           builder.pos(posX + wp.x, posY + wp.y, posZ + wp.z).color(cint.r, cint.g, cint.b, cint.a).endVertex();
/*  83 */           builder.pos(posX + wp.x, posY + wp.y, posZ + wp.z).color(cint.r, cint.g, cint.b, cint.a).endVertex();
/*  84 */           builder.pos(w2.posX - tank.posX + posX, w2.posY - tank.posY + posY, w2.posZ - tank.posZ + posZ)
/*  85 */             .color(cint.r, cint.g, cint.b, cint.a)
/*  86 */             .endVertex();
/*     */         } else {
/*  88 */           builder.pos(w1.posX - tank.posX + posX, w1.posY - tank.posY + posY, w1.posZ - tank.posZ + posZ)
/*  89 */             .color(cint.r, cint.g, cint.b, cint.a)
/*  90 */             .endVertex();
/*  91 */           builder.pos(w2.posX - tank.posX + posX, w2.posY - tank.posY + posY, w2.posZ - tank.posZ + posZ)
/*  92 */             .color(cint.r, cint.g, cint.b, cint.a)
/*  93 */             .endVertex();
/*  94 */           builder.pos(w2.posX - tank.posX + posX, w2.posY - tank.posY + posY, w2.posZ - tank.posZ + posZ)
/*  95 */             .color(cint.r, cint.g, cint.b, cint.a)
/*  96 */             .endVertex();
/*  97 */           builder.pos(posX + wp.x, posY + wp.y, posZ + wp.z).color(cint.r, cint.g, cint.b, cint.a).endVertex();
/*  98 */           builder.pos(posX + wp.x, posY + wp.y, posZ + wp.z).color(cint.r, cint.g, cint.b, cint.a).endVertex();
/*  99 */           builder.pos(w1.posX - tank.posX + posX, w1.posY - tank.posY + posY, w1.posZ - tank.posZ + posZ)
/* 100 */             .color(cint.r, cint.g, cint.b, cint.a)
/* 101 */             .endVertex();
/*     */         } 
/*     */       } 
/*     */       
/* 105 */       tessellator.draw();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(MCH_EntityTank entity) {
/* 111 */     return TEX_DEFAULT;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tank\MCH_RenderTank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */