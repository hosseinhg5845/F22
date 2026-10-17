/*     */ package com.norwood.mcheli.plane;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_RenderAircraft;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCP_RenderPlane extends MCH_RenderAircraft<MCP_EntityPlane> {
/*  15 */   public static final IRenderFactory<MCP_EntityPlane> FACTORY = MCP_RenderPlane::new;
/*     */   
/*     */   public MCP_RenderPlane(RenderManager renderManager) {
/*  18 */     super(renderManager);
/*  19 */     this.field_76989_e = 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAircraft(MCH_EntityAircraft entity, double posX, double posY, double posZ, float yaw, float pitch, float roll, float tickTime) {
/*  25 */     if (entity instanceof MCP_EntityPlane) { MCP_EntityPlane plane = (MCP_EntityPlane)entity;
/*  26 */       MCP_PlaneInfo planeInfo = plane.getPlaneInfo();
/*  27 */       if (planeInfo != null) {
/*  28 */         posY += 0.3499999940395355D;
/*  29 */         renderDebugHitBox(plane, posX, posY, posZ, yaw, pitch);
/*  30 */         renderDebugPilotSeat(plane, posX, posY, posZ, yaw, pitch, roll);
/*  31 */         GL11.glTranslated(posX, posY, posZ);
/*  32 */         GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*  33 */         GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*  34 */         GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
/*  35 */         bindTexture("textures/planes/" + plane.getTextureName() + ".png", plane);
/*  36 */         if (planeInfo.haveNozzle() && plane.partNozzle != null) {
/*  37 */           renderNozzle(plane, planeInfo, tickTime);
/*     */         }
/*     */         
/*  40 */         if (planeInfo.haveWing() && plane.partWing != null) {
/*  41 */           renderWing(plane, planeInfo, tickTime);
/*     */         }
/*     */         
/*  44 */         if (planeInfo.haveRotor() && plane.partNozzle != null) {
/*  45 */           renderRotor(plane, planeInfo, tickTime);
/*     */         }
/*     */         
/*  48 */         renderBody(planeInfo.model);
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   public void renderRotor(MCP_EntityPlane plane, MCP_PlaneInfo planeInfo, float tickTime) {
/*  54 */     float rot = plane.getNozzleRotation();
/*  55 */     float prevRot = plane.getPrevNozzleRotation();
/*     */     
/*  57 */     for (MCP_PlaneInfo.Rotor r : planeInfo.rotorList) {
/*  58 */       GL11.glPushMatrix();
/*  59 */       GL11.glTranslated(r.pos.field_72450_a, r.pos.field_72448_b, r.pos.field_72449_c);
/*  60 */       GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * r.maxRotFactor, (float)r.rot.field_72450_a, (float)r.rot.field_72448_b, (float)r.rot.field_72449_c);
/*  61 */       GL11.glTranslated(-r.pos.field_72450_a, -r.pos.field_72448_b, -r.pos.field_72449_c);
/*  62 */       renderPart(r.model, planeInfo.model, r.modelName);
/*     */       
/*  64 */       for (MCP_PlaneInfo.Blade b : r.blades) {
/*  65 */         float br = plane.prevRotationRotor;
/*  66 */         br += (plane.rotationRotor - plane.prevRotationRotor) * tickTime;
/*  67 */         GL11.glPushMatrix();
/*  68 */         GL11.glTranslated(b.pos.field_72450_a, b.pos.field_72448_b, b.pos.field_72449_c);
/*  69 */         GL11.glRotatef(br, (float)b.rot.field_72450_a, (float)b.rot.field_72448_b, (float)b.rot.field_72449_c);
/*  70 */         GL11.glTranslated(-b.pos.field_72450_a, -b.pos.field_72448_b, -b.pos.field_72449_c);
/*     */         
/*  72 */         for (int i = 0; i < b.numBlade; i++) {
/*  73 */           GL11.glTranslated(b.pos.field_72450_a, b.pos.field_72448_b, b.pos.field_72449_c);
/*  74 */           GL11.glRotatef(b.rotBlade, (float)b.rot.field_72450_a, (float)b.rot.field_72448_b, (float)b.rot.field_72449_c);
/*  75 */           GL11.glTranslated(-b.pos.field_72450_a, -b.pos.field_72448_b, -b.pos.field_72449_c);
/*  76 */           renderPart(b.model, planeInfo.model, b.modelName);
/*     */         } 
/*     */         
/*  79 */         GL11.glPopMatrix();
/*     */       } 
/*     */       
/*  82 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderWing(MCP_EntityPlane plane, MCP_PlaneInfo planeInfo, float tickTime) {
/*  87 */     float rot = plane.getWingRotation();
/*  88 */     float prevRot = plane.getPrevWingRotation();
/*     */     
/*  90 */     for (MCP_PlaneInfo.Wing w : planeInfo.wingList) {
/*  91 */       GL11.glPushMatrix();
/*  92 */       GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
/*  93 */       GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * w.maxRotFactor, (float)w.rot.field_72450_a, (float)w.rot.field_72448_b, (float)w.rot.field_72449_c);
/*  94 */       GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
/*  95 */       renderPart(w.model, planeInfo.model, w.modelName);
/*  96 */       if (w.pylonList != null) {
/*  97 */         for (MCP_PlaneInfo.Pylon p : w.pylonList) {
/*  98 */           GL11.glPushMatrix();
/*  99 */           GL11.glTranslated(p.pos.field_72450_a, p.pos.field_72448_b, p.pos.field_72449_c);
/* 100 */           GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * p.maxRotFactor, (float)p.rot.field_72450_a, (float)p.rot.field_72448_b, (float)p.rot.field_72449_c);
/* 101 */           GL11.glTranslated(-p.pos.field_72450_a, -p.pos.field_72448_b, -p.pos.field_72449_c);
/* 102 */           renderPart(p.model, planeInfo.model, p.modelName);
/* 103 */           GL11.glPopMatrix();
/*     */         } 
/*     */       }
/*     */       
/* 107 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderNozzle(MCP_EntityPlane plane, MCP_PlaneInfo planeInfo, float tickTime) {
/* 112 */     float rot = plane.getNozzleRotation();
/* 113 */     float prevRot = plane.getPrevNozzleRotation();
/*     */     
/* 115 */     for (MCH_AircraftInfo.DrawnPart n : planeInfo.nozzles) {
/* 116 */       GL11.glPushMatrix();
/* 117 */       GL11.glTranslated(n.pos.field_72450_a, n.pos.field_72448_b, n.pos.field_72449_c);
/* 118 */       GL11.glRotatef(prevRot + (rot - prevRot) * tickTime, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
/* 119 */       GL11.glTranslated(-n.pos.field_72450_a, -n.pos.field_72448_b, -n.pos.field_72449_c);
/* 120 */       renderPart(n.model, planeInfo.model, n.modelName);
/* 121 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ResourceLocation getEntityTexture(MCP_EntityPlane entity) {
/* 126 */     return TEX_DEFAULT;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\plane\MCP_RenderPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */