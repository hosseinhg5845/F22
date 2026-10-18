/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_RenderAircraft;
/*     */ import com.norwood.mcheli.helper.entity.ITargetMarkerObject;
/*     */ import com.norwood.mcheli.lweapon.MCH_ClientLightWeaponTickHandler;
/*     */ import com.norwood.mcheli.multiplay.MCH_GuiTargetMarker;
/*     */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*     */ import com.norwood.mcheli.tool.rangefinder.MCH_ItemRangeFinder;
/*     */ import com.norwood.mcheli.wrapper.W_ClientEventHook;
/*     */ import com.norwood.mcheli.wrapper.W_Reflection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.event.MouseEvent;
/*     */ import net.minecraftforge.client.event.RenderLivingEvent;
/*     */ import net.minecraftforge.client.event.RenderPlayerEvent;
/*     */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCH_ClientEventHook extends W_ClientEventHook {
/*  32 */   private static final ResourceLocation ir_strobe = new ResourceLocation("mcheli", "textures/ir_strobe.png");
/*  33 */   public static final List<MCH_EntityAircraft> haveSearchLightAircraft = new ArrayList<>();
/*     */   private static boolean cancelRender = true;
/*  35 */   MCH_TextureManagerDummy dummyTextureManager = null;
/*     */   
/*     */   public static void setCancelRender(boolean cancel) {
/*  38 */     cancelRender = cancel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderLivingEventSpecialsPre(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
/*  43 */     if (MCH_Config.DisableRenderLivingSpecials.prmBool) {
/*  44 */       MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)(Minecraft.getMinecraft()).player);
/*  45 */       if (ac != null && ac.isMountedEntity((Entity)event.getEntity())) {
/*  46 */         event.setCanceled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderIRStrobe(EntityLivingBase entity, RenderLivingEvent.Specials.Post<EntityLivingBase> event) {
/*  52 */     int cm = MCH_ClientCommonTickHandler.cameraMode;
/*  53 */     if (cm != 0) {
/*  54 */       int ticks = entity.ticksExisted % 20;
/*  55 */       if (ticks < 4) {
/*  56 */         float alpha = (ticks != 2 && ticks != 1) ? 0.5F : 1.0F;
/*  57 */         EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).player;
/*  58 */         if (entityPlayerSP != null && 
/*  59 */           entityPlayerSP.isOnSameTeam((Entity)entity)) {
/*  60 */           int j = 240;
/*  61 */           int k = 240;
/*  62 */           OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/*  63 */           RenderManager rm = event.getRenderer().getRenderManager();
/*  64 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  65 */           float f1 = 0.080000006F;
/*  66 */           GL11.glPushMatrix();
/*  67 */           GL11.glTranslated(event.getX(), event.getY() + (float)(entity.height * 0.75D), event.getZ());
/*  68 */           GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*  69 */           GL11.glRotatef(-rm.playerViewY, 0.0F, 1.0F, 0.0F);
/*  70 */           GL11.glRotatef(rm.playerViewX, 1.0F, 0.0F, 0.0F);
/*  71 */           GL11.glScalef(-f1, -f1, f1);
/*  72 */           GL11.glEnable(3042);
/*  73 */           OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  74 */           GL11.glEnable(3553);
/*  75 */           rm.renderEngine.bindTexture(ir_strobe);
/*  76 */           GL11.glAlphaFunc(516, 0.003921569F);
/*  77 */           Tessellator tessellator = Tessellator.getInstance();
/*  78 */           BufferBuilder builder = tessellator.getBuffer();
/*  79 */           builder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*  80 */           int i = (int)Math.max(entity.width, entity.height) * 20;
/*  81 */           builder.pos(-i, -i, 0.1D).tex(0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, alpha * ((cm == 1) ? 0.9F : 0.5F)).endVertex();
/*  82 */           builder.pos(-i, i, 0.1D).tex(0.0D, 1.0D).color(1.0F, 1.0F, 1.0F, alpha * ((cm == 1) ? 0.9F : 0.5F)).endVertex();
/*  83 */           builder.pos(i, i, 0.1D).tex(1.0D, 1.0D).color(1.0F, 1.0F, 1.0F, alpha * ((cm == 1) ? 0.9F : 0.5F)).endVertex();
/*  84 */           builder.pos(i, -i, 0.1D).tex(1.0D, 0.0D).color(1.0F, 1.0F, 1.0F, alpha * ((cm == 1) ? 0.9F : 0.5F)).endVertex();
/*  85 */           tessellator.draw();
/*  86 */           GL11.glEnable(2896);
/*  87 */           GL11.glPopMatrix();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseEvent(MouseEvent event) {
/*  96 */     if (MCH_ClientTickHandlerBase.updateMouseWheel(event.getDwheel())) {
/*  97 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderLivingEventPre(RenderLivingEvent.Pre<EntityLivingBase> event) {
/* 103 */     for (MCH_EntityAircraft ac : haveSearchLightAircraft) {
/* 104 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, ac.getSearchLightValue((Entity)event.getEntity()), 240.0F);
/*     */     }
/*     */     
/* 107 */     if (!MCH_Config.EnableModEntityRender.prmBool || !cancelRender || (
/*     */       
/* 109 */       !(event.getEntity().getRidingEntity() instanceof MCH_EntityAircraft) && !(event.getEntity().getRidingEntity() instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat))) {
/* 110 */       if (MCH_Config.EnableReplaceTextureManager.prmBool) {
/* 111 */         RenderManager rm = W_Reflection.getRenderManager((Render)event.getRenderer());
/* 112 */         if (!(rm.renderEngine instanceof MCH_TextureManagerDummy)) {
/* 113 */           if (this.dummyTextureManager == null) {
/* 114 */             this.dummyTextureManager = new MCH_TextureManagerDummy(rm.renderEngine);
/*     */           }
/*     */           
/* 117 */           rm.renderEngine = this.dummyTextureManager;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 121 */       event.setCanceled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderLivingEventPost(RenderLivingEvent.Post<EntityLivingBase> event) {
/* 127 */     MCH_RenderAircraft.renderEntityMarker((Entity)event.getEntity());
/* 128 */     if (event.getEntity() instanceof ITargetMarkerObject) {
/* 129 */       MCH_GuiTargetMarker.addMarkEntityPos(2, (ITargetMarkerObject)event
/* 130 */           .getEntity(), event.getX(), event.getY() + (event.getEntity()).height + 0.5D, event.getZ());
/*     */     } else {
/*     */       
/* 133 */       MCH_GuiTargetMarker.addMarkEntityPos(2, 
/* 134 */           ITargetMarkerObject.fromEntity((Entity)event.getEntity()), event.getX(), event.getY() + (event.getEntity()).height + 0.5D, event.getZ());
/*     */     } 
/*     */ 
/*     */     
/* 138 */     MCH_ClientLightWeaponTickHandler.markEntity((Entity)event.getEntity(), event.getX(), event.getY() + ((event.getEntity()).height / 2.0F), event.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderPlayerPre(RenderPlayerEvent.Pre event) {
/* 143 */     if (event.getEntity() != null) {
/* 144 */       Entity entity = event.getEntity().getRidingEntity(); if (entity instanceof MCH_EntityAircraft) { MCH_EntityAircraft v = (MCH_EntityAircraft)entity;
/* 145 */         if (v.getAcInfo() != null && (v.getAcInfo()).hideEntity) {
/* 146 */           event.setCanceled(true);
/*     */         } }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityJoinWorldEvent(EntityJoinWorldEvent event) {
/* 154 */     if (event.getEntity().isEntityEqual(MCH_Lib.getClientPlayer())) {
/* 155 */       MCH_Lib.DbgLog(true, "MCH_ClientEventHook.entityJoinWorldEvent : " + event.getEntity(), new Object[0]);
/* 156 */       MCH_ItemRangeFinder.mode = Minecraft.getMinecraft().isSingleplayer() ? 1 : 0;
/* 157 */       MCH_ParticlesUtil.clearMarkPoint();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_ClientEventHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */