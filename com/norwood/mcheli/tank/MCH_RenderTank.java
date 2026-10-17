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
/*  24 */     this.field_76989_e = 2.0F;
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
/*  54 */         GL11.glTranslated(w.field_70165_t - tank.field_70165_t + posX, w.field_70163_u - tank.field_70163_u + posY + 0.25D, w.field_70161_v - tank.field_70161_v + posZ);
/*  55 */         GL11.glScalef(w.field_70130_N, w.field_70131_O / 2.0F, w.field_70130_N);
/*  56 */         bindTexture("textures/seat_pilot.png");
/*  57 */         debugModel.renderAll();
/*  58 */         GL11.glPopMatrix();
/*     */       } 
/*     */       
/*  61 */       GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/*  62 */       Tessellator tessellator = Tessellator.func_178181_a();
/*  63 */       BufferBuilder builder = tessellator.func_178180_c();
/*  64 */       builder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
/*  65 */       Vec3d wp = tank.getTransformedPosition(tank.WheelMng.weightedCenter);
/*  66 */       wp = wp.func_178786_a(tank.field_70165_t, tank.field_70163_u, tank.field_70161_v);
/*     */       
/*  68 */       for (int i = 0; i < tank.WheelMng.wheels.length / 2; i++) {
/*  69 */         MCH_ColorInt cint = new MCH_ColorInt(((i & 0x4) > 0) ? 16711680 : 0, ((i & 0x2) > 0) ? 65280 : 0, ((i & 0x1) > 0) ? 255 : 0, 192);
/*  70 */         MCH_EntityWheel w1 = tank.WheelMng.wheels[i * 2];
/*  71 */         MCH_EntityWheel w2 = tank.WheelMng.wheels[i * 2 + 1];
/*  72 */         if (w1.isPlus) {
/*  73 */           builder.func_181662_b(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ)
/*  74 */             .func_181669_b(cint.r, cint.g, cint.b, cint.a)
/*  75 */             .func_181675_d();
/*  76 */           builder.func_181662_b(w1.field_70165_t - tank.field_70165_t + posX, w1.field_70163_u - tank.field_70163_u + posY, w1.field_70161_v - tank.field_70161_v + posZ)
/*  77 */             .func_181669_b(cint.r, cint.g, cint.b, cint.a)
/*  78 */             .func_181675_d();
/*  79 */           builder.func_181662_b(w1.field_70165_t - tank.field_70165_t + posX, w1.field_70163_u - tank.field_70163_u + posY, w1.field_70161_v - tank.field_70161_v + posZ)
/*  80 */             .func_181669_b(cint.r, cint.g, cint.b, cint.a)
/*  81 */             .func_181675_d();
/*  82 */           builder.func_181662_b(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c).func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/*  83 */           builder.func_181662_b(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c).func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/*  84 */           builder.func_181662_b(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ)
/*  85 */             .func_181669_b(cint.r, cint.g, cint.b, cint.a)
/*  86 */             .func_181675_d();
/*     */         } else {
/*  88 */           builder.func_181662_b(w1.field_70165_t - tank.field_70165_t + posX, w1.field_70163_u - tank.field_70163_u + posY, w1.field_70161_v - tank.field_70161_v + posZ)
/*  89 */             .func_181669_b(cint.r, cint.g, cint.b, cint.a)
/*  90 */             .func_181675_d();
/*  91 */           builder.func_181662_b(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ)
/*  92 */             .func_181669_b(cint.r, cint.g, cint.b, cint.a)
/*  93 */             .func_181675_d();
/*  94 */           builder.func_181662_b(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ)
/*  95 */             .func_181669_b(cint.r, cint.g, cint.b, cint.a)
/*  96 */             .func_181675_d();
/*  97 */           builder.func_181662_b(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c).func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/*  98 */           builder.func_181662_b(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c).func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/*  99 */           builder.func_181662_b(w1.field_70165_t - tank.field_70165_t + posX, w1.field_70163_u - tank.field_70163_u + posY, w1.field_70161_v - tank.field_70161_v + posZ)
/* 100 */             .func_181669_b(cint.r, cint.g, cint.b, cint.a)
/* 101 */             .func_181675_d();
/*     */         } 
/*     */       } 
/*     */       
/* 105 */       tessellator.func_78381_a();
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