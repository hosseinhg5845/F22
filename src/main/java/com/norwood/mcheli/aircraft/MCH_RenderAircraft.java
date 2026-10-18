/*      */ package com.norwood.mcheli.aircraft;
/*      */ 
/*      */ import com.norwood.mcheli.MCH_ClientCommonTickHandler;
/*      */ import com.norwood.mcheli.MCH_ClientEventHook;
/*      */ import com.norwood.mcheli.MCH_Config;
/*      */ import com.norwood.mcheli.MCH_Lib;
/*      */ import com.norwood.mcheli.debug._v3.WeaponPointRenderer;
/*      */ import com.norwood.mcheli.gui.MCH_Gui;
/*      */ import com.norwood.mcheli.helper.MCH_ColorInt;
/*      */ import com.norwood.mcheli.helper.MCH_Utils;
/*      */ import com.norwood.mcheli.helper.client._IModelCustom;
/*      */ import com.norwood.mcheli.helper.client.renderer.MCH_Verts;
/*      */ import com.norwood.mcheli.helper.entity.ITargetMarkerObject;
/*      */ import com.norwood.mcheli.lweapon.MCH_ClientLightWeaponTickHandler;
/*      */ import com.norwood.mcheli.multiplay.MCH_GuiTargetMarker;
/*      */ import com.norwood.mcheli.uav.MCH_EntityUavStation;
/*      */ import com.norwood.mcheli.weapon.MCH_WeaponGuidanceSystem;
/*      */ import com.norwood.mcheli.weapon.MCH_WeaponSet;
/*      */ import com.norwood.mcheli.wrapper.W_Entity;
/*      */ import com.norwood.mcheli.wrapper.W_EntityRenderer;
/*      */ import com.norwood.mcheli.wrapper.W_Lib;
/*      */ import com.norwood.mcheli.wrapper.W_Render;
/*      */ import com.norwood.mcheli.wrapper.modelloader.W_ModelCustom;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.renderer.BufferBuilder;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ public abstract class MCH_RenderAircraft<T extends MCH_EntityAircraft> extends W_Render<T> {
/*      */   public static boolean renderingEntity = false;
/*   46 */   public static _IModelCustom debugModel = null;
/*      */   
/*      */   protected MCH_RenderAircraft(RenderManager renderManager) {
/*   49 */     super(renderManager);
/*      */   }
/*      */   
/*      */   public static boolean shouldSkipRender(Entity entity) {
/*   53 */     if (entity instanceof MCH_IEntityCanRideAircraft) { MCH_IEntityCanRideAircraft e = (MCH_IEntityCanRideAircraft)entity;
/*   54 */       if (e.isSkipNormalRender()) {
/*   55 */         return !renderingEntity;
/*      */       } }
/*   57 */     else if ((entity
/*   58 */       .getClass().toString().indexOf("flansmod.common.driveables.EntityPlane") > 0 || entity
/*   59 */       .getClass().toString().indexOf("flansmod.common.driveables.EntityVehicle") > 0) && entity
/*      */       
/*   61 */       .getRidingEntity() instanceof MCH_EntitySeat)
/*   62 */     { return !renderingEntity; }
/*      */ 
/*      */     
/*   65 */     return false;
/*      */   }
/*      */   
/*      */   public static void renderLight(double x, double y, double z, float tickTime, MCH_EntityAircraft ac, MCH_AircraftInfo info) {
/*   69 */     if (ac.haveSearchLight() && 
/*   70 */       ac.isSearchLightON()) {
/*   71 */       Entity entity = ac.getEntityBySeatId(1);
/*   72 */       if (entity != null) {
/*   73 */         ac.lastSearchLightYaw = entity.rotationYaw;
/*   74 */         ac.lastSearchLightPitch = entity.rotationPitch;
/*      */       } else {
/*   76 */         entity = ac.getEntityBySeatId(0);
/*   77 */         if (entity != null) {
/*   78 */           ac.lastSearchLightYaw = entity.rotationYaw;
/*   79 */           ac.lastSearchLightPitch = entity.rotationPitch;
/*      */         } 
/*      */       } 
/*      */       
/*   83 */       float yaw = ac.lastSearchLightYaw;
/*   84 */       float pitch = ac.lastSearchLightPitch;
/*   85 */       RenderHelper.disableStandardItemLighting();
/*   86 */       GL11.glDisable(3553);
/*   87 */       GL11.glShadeModel(7425);
/*   88 */       GL11.glEnable(3042);
/*   89 */       GL11.glBlendFunc(770, 1);
/*   90 */       GL11.glDisable(3008);
/*   91 */       GL11.glDisable(2884);
/*   92 */       GL11.glDepthMask(false);
/*   93 */       float rot = ac.prevRotYawWheel + (ac.rotYawWheel - ac.prevRotYawWheel) * tickTime;
/*      */       
/*   95 */       for (MCH_AircraftInfo.SearchLight sl : info.searchLights) {
/*   96 */         GL11.glPushMatrix();
/*   97 */         GL11.glTranslated(sl.pos.x, sl.pos.y, sl.pos.z);
/*   98 */         if (!sl.fixDir) {
/*   99 */           GL11.glRotatef(yaw - ac.getRotYaw() + sl.yaw, 0.0F, -1.0F, 0.0F);
/*  100 */           GL11.glRotatef(pitch + 90.0F - ac.getRotPitch() + sl.pitch, 1.0F, 0.0F, 0.0F);
/*      */         } else {
/*  102 */           float stRot = 0.0F;
/*  103 */           if (sl.steering) {
/*  104 */             stRot = -rot * sl.stRot;
/*      */           }
/*      */           
/*  107 */           GL11.glRotatef(0.0F + sl.yaw + stRot, 0.0F, -1.0F, 0.0F);
/*  108 */           GL11.glRotatef(90.0F + sl.pitch, 1.0F, 0.0F, 0.0F);
/*      */         } 
/*      */         
/*  111 */         float height = sl.height;
/*  112 */         float width = sl.width / 2.0F;
/*  113 */         Tessellator tessellator = Tessellator.getInstance();
/*  114 */         BufferBuilder builder = tessellator.getBuffer();
/*  115 */         builder.begin(6, DefaultVertexFormats.POSITION_COLOR);
/*  116 */         MCH_ColorInt cs = new MCH_ColorInt(sl.colorStart);
/*  117 */         MCH_ColorInt ce = new MCH_ColorInt(sl.colorEnd);
/*  118 */         builder.pos(0.0D, 0.0D, 0.0D).color(cs.r, cs.g, cs.b, cs.a).endVertex();
/*      */         
/*  120 */         for (int i = 0; i < 25; i++) {
/*  121 */           float angle = (float)(15.0D * i / 180.0D * Math.PI);
/*  122 */           builder.pos((MathHelper.sin(angle) * width), height, (MathHelper.cos(angle) * width)).color(ce.r, ce.g, ce.b, ce.a).endVertex();
/*      */         } 
/*      */         
/*  125 */         tessellator.draw();
/*  126 */         GL11.glPopMatrix();
/*      */       } 
/*      */       
/*  129 */       GL11.glDepthMask(true);
/*  130 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  131 */       GL11.glEnable(3553);
/*  132 */       GL11.glEnable(3008);
/*  133 */       GL11.glBlendFunc(770, 771);
/*  134 */       RenderHelper.enableStandardItemLighting();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void Test_Material(int light, float a, float b, float c) {
/*  140 */     GL11.glMaterial(1032, light, setColorBuffer(a, b, c, 1.0F));
/*      */   }
/*      */   
/*      */   public static void Test_Light(int light, float a, float b, float c) {
/*  144 */     GL11.glLight(16384, light, setColorBuffer(a, b, c, 1.0F));
/*  145 */     GL11.glLight(16385, light, setColorBuffer(a, b, c, 1.0F));
/*      */   }
/*      */   
/*      */   public static void renderBody(@Nullable _IModelCustom model) {
/*  149 */     if (model != null) {
/*  150 */       if (model instanceof W_ModelCustom) {
/*  151 */         if (((W_ModelCustom)model).containsPart("$body")) {
/*  152 */           model.renderPart("$body");
/*      */         } else {
/*  154 */           model.renderAll();
/*      */         } 
/*      */       } else {
/*  157 */         model.renderAll();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static void renderPart(@Nullable _IModelCustom model, @Nullable _IModelCustom modelBody, String partName) {
/*  163 */     if (model != null) {
/*  164 */       model.renderAll();
/*  165 */     } else if (modelBody instanceof W_ModelCustom && ((W_ModelCustom)modelBody).containsPart("$" + partName)) {
/*  166 */       modelBody.renderPart("$" + partName);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderLightHatch(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  171 */     if (!info.lightHatchList.isEmpty()) {
/*  172 */       float rot = ac.prevRotLightHatch + (ac.rotLightHatch - ac.prevRotLightHatch) * tickTime;
/*      */       
/*  174 */       for (MCH_AircraftInfo.Hatch t : info.lightHatchList) {
/*  175 */         GL11.glPushMatrix();
/*  176 */         GL11.glTranslated(t.pos.x, t.pos.y, t.pos.z);
/*  177 */         GL11.glRotated((rot * t.maxRot), t.rot.x, t.rot.y, t.rot.z);
/*  178 */         GL11.glTranslated(-t.pos.x, -t.pos.y, -t.pos.z);
/*  179 */         renderPart(t.model, info.model, t.modelName);
/*  180 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderSteeringWheel(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  186 */     if (!info.partSteeringWheel.isEmpty()) {
/*  187 */       float rot = ac.prevRotYawWheel + (ac.rotYawWheel - ac.prevRotYawWheel) * tickTime;
/*      */       
/*  189 */       for (MCH_AircraftInfo.PartWheel t : info.partSteeringWheel) {
/*  190 */         GL11.glPushMatrix();
/*  191 */         GL11.glTranslated(t.pos.x, t.pos.y, t.pos.z);
/*  192 */         GL11.glRotated((rot * t.rotDir), t.rot.x, t.rot.y, t.rot.z);
/*  193 */         GL11.glTranslated(-t.pos.x, -t.pos.y, -t.pos.z);
/*  194 */         renderPart(t.model, info.model, t.modelName);
/*  195 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderWheel(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  201 */     if (!info.partWheel.isEmpty()) {
/*  202 */       float yaw = ac.prevRotYawWheel + (ac.rotYawWheel - ac.prevRotYawWheel) * tickTime;
/*      */       
/*  204 */       for (MCH_AircraftInfo.PartWheel t : info.partWheel) {
/*  205 */         GL11.glPushMatrix();
/*  206 */         GL11.glTranslated(t.pos2.x, t.pos2.y, t.pos2.z);
/*  207 */         GL11.glRotated((yaw * t.rotDir), t.rot.x, t.rot.y, t.rot.z);
/*  208 */         GL11.glTranslated(-t.pos2.x, -t.pos2.y, -t.pos2.z);
/*  209 */         GL11.glTranslated(t.pos.x, t.pos.y, t.pos.z);
/*  210 */         GL11.glRotatef(ac.prevRotWheel + (ac.rotWheel - ac.prevRotWheel) * tickTime, 1.0F, 0.0F, 0.0F);
/*  211 */         GL11.glTranslated(-t.pos.x, -t.pos.y, -t.pos.z);
/*  212 */         renderPart(t.model, info.model, t.modelName);
/*  213 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderRotPart(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  219 */     if (ac.haveRotPart()) {
/*  220 */       for (int i = 0; i < ac.rotPartRotation.length; i++) {
/*  221 */         float rot = ac.rotPartRotation[i];
/*  222 */         float prevRot = ac.prevRotPartRotation[i];
/*  223 */         if (prevRot > rot) {
/*  224 */           rot += 360.0F;
/*      */         }
/*      */         
/*  227 */         rot = MCH_Lib.smooth(rot, prevRot, tickTime);
/*  228 */         MCH_AircraftInfo.RotPart h = info.partRotPart.get(i);
/*  229 */         GL11.glPushMatrix();
/*  230 */         GL11.glTranslated(h.pos.x, h.pos.y, h.pos.z);
/*  231 */         GL11.glRotatef(rot, (float)h.rot.x, (float)h.rot.y, (float)h.rot.z);
/*  232 */         GL11.glTranslated(-h.pos.x, -h.pos.y, -h.pos.z);
/*  233 */         renderPart(h.model, info.model, h.modelName);
/*  234 */         GL11.glPopMatrix();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void renderWeapon(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*      */     // Byte code:
/*      */     //   0: aconst_null
/*      */     //   1: astore_3
/*      */     //   2: aload_0
/*      */     //   3: invokevirtual getRiddenByEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   6: astore #4
/*      */     //   8: iconst_0
/*      */     //   9: istore #5
/*      */     //   11: aload_1
/*      */     //   12: getfield partWeapon : Ljava/util/List;
/*      */     //   15: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   20: astore #6
/*      */     //   22: aload #6
/*      */     //   24: invokeinterface hasNext : ()Z
/*      */     //   29: ifne -> 33
/*      */     //   32: return
/*      */     //   33: aload #6
/*      */     //   35: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   40: checkcast com/norwood/mcheli/aircraft/MCH_AircraftInfo$PartWeapon
/*      */     //   43: astore #7
/*      */     //   45: aload_0
/*      */     //   46: aload #7
/*      */     //   48: getfield name : [Ljava/lang/String;
/*      */     //   51: iconst_0
/*      */     //   52: aaload
/*      */     //   53: invokevirtual getWeaponByName : (Ljava/lang/String;)Lcom/norwood/mcheli/weapon/MCH_WeaponSet;
/*      */     //   56: astore #8
/*      */     //   58: aload #8
/*      */     //   60: aload_3
/*      */     //   61: if_acmpeq -> 70
/*      */     //   64: iconst_0
/*      */     //   65: istore #5
/*      */     //   67: aload #8
/*      */     //   69: astore_3
/*      */     //   70: fconst_0
/*      */     //   71: fstore #9
/*      */     //   73: fconst_0
/*      */     //   74: fstore #10
/*      */     //   76: fconst_0
/*      */     //   77: fstore #11
/*      */     //   79: fconst_0
/*      */     //   80: fstore #12
/*      */     //   82: aload #7
/*      */     //   84: getfield hideGM : Z
/*      */     //   87: ifeq -> 184
/*      */     //   90: invokestatic isFirstPerson : ()Z
/*      */     //   93: ifne -> 99
/*      */     //   96: goto -> 184
/*      */     //   99: aload #8
/*      */     //   101: ifnonnull -> 117
/*      */     //   104: aload_0
/*      */     //   105: invokestatic getClientPlayer : ()Lnet/minecraft/entity/Entity;
/*      */     //   108: invokevirtual isMountedEntity : (Lnet/minecraft/entity/Entity;)Z
/*      */     //   111: ifeq -> 184
/*      */     //   114: goto -> 22
/*      */     //   117: iconst_0
/*      */     //   118: istore #13
/*      */     //   120: aload #7
/*      */     //   122: getfield name : [Ljava/lang/String;
/*      */     //   125: astore #14
/*      */     //   127: aload #14
/*      */     //   129: arraylength
/*      */     //   130: istore #15
/*      */     //   132: iconst_0
/*      */     //   133: istore #16
/*      */     //   135: iload #16
/*      */     //   137: iload #15
/*      */     //   139: if_icmpge -> 173
/*      */     //   142: aload #14
/*      */     //   144: iload #16
/*      */     //   146: aaload
/*      */     //   147: astore #17
/*      */     //   149: aload_0
/*      */     //   150: aload #17
/*      */     //   152: invokevirtual getWeaponUserByWeaponName : (Ljava/lang/String;)Lnet/minecraft/entity/Entity;
/*      */     //   155: invokestatic isClientPlayer : (Lnet/minecraft/entity/Entity;)Z
/*      */     //   158: ifeq -> 167
/*      */     //   161: iconst_1
/*      */     //   162: istore #13
/*      */     //   164: goto -> 173
/*      */     //   167: iinc #16, 1
/*      */     //   170: goto -> 135
/*      */     //   173: iload #13
/*      */     //   175: ifne -> 181
/*      */     //   178: goto -> 184
/*      */     //   181: goto -> 22
/*      */     //   184: invokestatic glPushMatrix : ()V
/*      */     //   187: aload #7
/*      */     //   189: getfield turret : Z
/*      */     //   192: ifeq -> 279
/*      */     //   195: aload_1
/*      */     //   196: getfield turretPosition : Lnet/minecraft/util/math/Vec3d;
/*      */     //   199: getfield x : D
/*      */     //   202: aload_1
/*      */     //   203: getfield turretPosition : Lnet/minecraft/util/math/Vec3d;
/*      */     //   206: getfield y : D
/*      */     //   209: aload_1
/*      */     //   210: getfield turretPosition : Lnet/minecraft/util/math/Vec3d;
/*      */     //   213: getfield z : D
/*      */     //   216: invokestatic glTranslated : (DDD)V
/*      */     //   219: aload_0
/*      */     //   220: invokevirtual getLastRiderYaw : ()F
/*      */     //   223: aload_0
/*      */     //   224: invokevirtual getRotYaw : ()F
/*      */     //   227: fsub
/*      */     //   228: aload_0
/*      */     //   229: getfield prevLastRiderYaw : F
/*      */     //   232: aload_0
/*      */     //   233: getfield prevRotationYaw : F
/*      */     //   236: fsub
/*      */     //   237: fload_2
/*      */     //   238: invokestatic smooth : (FFF)F
/*      */     //   241: fstore #9
/*      */     //   243: fload #9
/*      */     //   245: fconst_0
/*      */     //   246: ldc -1.0
/*      */     //   248: fconst_0
/*      */     //   249: invokestatic glRotatef : (FFFF)V
/*      */     //   252: aload_1
/*      */     //   253: getfield turretPosition : Lnet/minecraft/util/math/Vec3d;
/*      */     //   256: getfield x : D
/*      */     //   259: dneg
/*      */     //   260: aload_1
/*      */     //   261: getfield turretPosition : Lnet/minecraft/util/math/Vec3d;
/*      */     //   264: getfield y : D
/*      */     //   267: dneg
/*      */     //   268: aload_1
/*      */     //   269: getfield turretPosition : Lnet/minecraft/util/math/Vec3d;
/*      */     //   272: getfield z : D
/*      */     //   275: dneg
/*      */     //   276: invokestatic glTranslated : (DDD)V
/*      */     //   279: aload #7
/*      */     //   281: getfield pos : Lnet/minecraft/util/math/Vec3d;
/*      */     //   284: getfield x : D
/*      */     //   287: aload #7
/*      */     //   289: getfield pos : Lnet/minecraft/util/math/Vec3d;
/*      */     //   292: getfield y : D
/*      */     //   295: aload #7
/*      */     //   297: getfield pos : Lnet/minecraft/util/math/Vec3d;
/*      */     //   300: getfield z : D
/*      */     //   303: invokestatic glTranslated : (DDD)V
/*      */     //   306: aload #7
/*      */     //   308: getfield yaw : Z
/*      */     //   311: ifeq -> 462
/*      */     //   314: aload #8
/*      */     //   316: ifnull -> 348
/*      */     //   319: aload #8
/*      */     //   321: getfield rotationYaw : F
/*      */     //   324: aload #8
/*      */     //   326: getfield defaultRotationYaw : F
/*      */     //   329: fsub
/*      */     //   330: fstore #9
/*      */     //   332: aload #8
/*      */     //   334: getfield prevRotationYaw : F
/*      */     //   337: aload #8
/*      */     //   339: getfield defaultRotationYaw : F
/*      */     //   342: fsub
/*      */     //   343: fstore #10
/*      */     //   345: goto -> 402
/*      */     //   348: aload #4
/*      */     //   350: ifnull -> 380
/*      */     //   353: aload #4
/*      */     //   355: getfield rotationYaw : F
/*      */     //   358: aload_0
/*      */     //   359: invokevirtual getRotYaw : ()F
/*      */     //   362: fsub
/*      */     //   363: fstore #9
/*      */     //   365: aload #4
/*      */     //   367: getfield prevRotationYaw : F
/*      */     //   370: aload_0
/*      */     //   371: getfield prevRotationYaw : F
/*      */     //   374: fsub
/*      */     //   375: fstore #10
/*      */     //   377: goto -> 402
/*      */     //   380: aload_0
/*      */     //   381: invokevirtual getLastRiderYaw : ()F
/*      */     //   384: aload_0
/*      */     //   385: getfield rotationYaw : F
/*      */     //   388: fsub
/*      */     //   389: fstore #9
/*      */     //   391: aload_0
/*      */     //   392: getfield prevLastRiderYaw : F
/*      */     //   395: aload_0
/*      */     //   396: getfield prevRotationYaw : F
/*      */     //   399: fsub
/*      */     //   400: fstore #10
/*      */     //   402: fload #9
/*      */     //   404: fload #10
/*      */     //   406: fsub
/*      */     //   407: ldc_w 180.0
/*      */     //   410: fcmpl
/*      */     //   411: ifle -> 425
/*      */     //   414: fload #10
/*      */     //   416: ldc_w 360.0
/*      */     //   419: fadd
/*      */     //   420: fstore #10
/*      */     //   422: goto -> 445
/*      */     //   425: fload #9
/*      */     //   427: fload #10
/*      */     //   429: fsub
/*      */     //   430: ldc_w -180.0
/*      */     //   433: fcmpg
/*      */     //   434: ifge -> 445
/*      */     //   437: fload #10
/*      */     //   439: ldc_w 360.0
/*      */     //   442: fsub
/*      */     //   443: fstore #10
/*      */     //   445: fload #10
/*      */     //   447: fload #9
/*      */     //   449: fload #10
/*      */     //   451: fsub
/*      */     //   452: fload_2
/*      */     //   453: fmul
/*      */     //   454: fadd
/*      */     //   455: fconst_0
/*      */     //   456: ldc -1.0
/*      */     //   458: fconst_0
/*      */     //   459: invokestatic glRotatef : (FFFF)V
/*      */     //   462: aload #7
/*      */     //   464: getfield turret : Z
/*      */     //   467: ifeq -> 514
/*      */     //   470: aload_0
/*      */     //   471: invokevirtual getLastRiderYaw : ()F
/*      */     //   474: aload_0
/*      */     //   475: invokevirtual getRotYaw : ()F
/*      */     //   478: fsub
/*      */     //   479: aload_0
/*      */     //   480: getfield prevLastRiderYaw : F
/*      */     //   483: aload_0
/*      */     //   484: getfield prevRotationYaw : F
/*      */     //   487: fsub
/*      */     //   488: fload_2
/*      */     //   489: invokestatic smooth : (FFF)F
/*      */     //   492: fstore #9
/*      */     //   494: fload #9
/*      */     //   496: aload #8
/*      */     //   498: getfield rotationTurretYaw : F
/*      */     //   501: fsub
/*      */     //   502: fstore #9
/*      */     //   504: fload #9
/*      */     //   506: fneg
/*      */     //   507: fconst_0
/*      */     //   508: ldc -1.0
/*      */     //   510: fconst_0
/*      */     //   511: invokestatic glRotatef : (FFFF)V
/*      */     //   514: iconst_0
/*      */     //   515: istore #9
/*      */     //   517: aload #8
/*      */     //   519: ifnull -> 597
/*      */     //   522: aload #8
/*      */     //   524: getfield defaultRotationYaw : F
/*      */     //   527: f2i
/*      */     //   528: ifeq -> 597
/*      */     //   531: aload #8
/*      */     //   533: getfield defaultRotationYaw : F
/*      */     //   536: invokestatic wrapDegrees : (F)F
/*      */     //   539: fstore #10
/*      */     //   541: fload #10
/*      */     //   543: ldc_w 45.0
/*      */     //   546: fcmpl
/*      */     //   547: iflt -> 559
/*      */     //   550: fload #10
/*      */     //   552: ldc_w 135.0
/*      */     //   555: fcmpg
/*      */     //   556: ifle -> 577
/*      */     //   559: fload #10
/*      */     //   561: ldc_w -45.0
/*      */     //   564: fcmpg
/*      */     //   565: ifgt -> 581
/*      */     //   568: fload #10
/*      */     //   570: ldc_w -135.0
/*      */     //   573: fcmpl
/*      */     //   574: iflt -> 581
/*      */     //   577: iconst_1
/*      */     //   578: goto -> 582
/*      */     //   581: iconst_0
/*      */     //   582: istore #9
/*      */     //   584: aload #8
/*      */     //   586: getfield defaultRotationYaw : F
/*      */     //   589: fneg
/*      */     //   590: fconst_0
/*      */     //   591: ldc -1.0
/*      */     //   593: fconst_0
/*      */     //   594: invokestatic glRotatef : (FFFF)V
/*      */     //   597: aload #7
/*      */     //   599: getfield pitch : Z
/*      */     //   602: ifeq -> 692
/*      */     //   605: aload #8
/*      */     //   607: ifnull -> 627
/*      */     //   610: aload #8
/*      */     //   612: getfield rotationPitch : F
/*      */     //   615: fstore #10
/*      */     //   617: aload #8
/*      */     //   619: getfield prevRotationPitch : F
/*      */     //   622: fstore #11
/*      */     //   624: goto -> 661
/*      */     //   627: aload #4
/*      */     //   629: ifnull -> 649
/*      */     //   632: aload #4
/*      */     //   634: getfield rotationPitch : F
/*      */     //   637: fstore #10
/*      */     //   639: aload #4
/*      */     //   641: getfield prevRotationPitch : F
/*      */     //   644: fstore #11
/*      */     //   646: goto -> 661
/*      */     //   649: aload_0
/*      */     //   650: invokevirtual getLastRiderPitch : ()F
/*      */     //   653: fstore #10
/*      */     //   655: aload_0
/*      */     //   656: getfield prevLastRiderPitch : F
/*      */     //   659: fstore #11
/*      */     //   661: iload #9
/*      */     //   663: ifeq -> 676
/*      */     //   666: fload #10
/*      */     //   668: fneg
/*      */     //   669: fstore #10
/*      */     //   671: fload #11
/*      */     //   673: fneg
/*      */     //   674: fstore #11
/*      */     //   676: fload #11
/*      */     //   678: fload #10
/*      */     //   680: fload #11
/*      */     //   682: fsub
/*      */     //   683: fload_2
/*      */     //   684: fmul
/*      */     //   685: fadd
/*      */     //   686: fconst_1
/*      */     //   687: fconst_0
/*      */     //   688: fconst_0
/*      */     //   689: invokestatic glRotatef : (FFFF)V
/*      */     //   692: aload #8
/*      */     //   694: ifnull -> 837
/*      */     //   697: aload #7
/*      */     //   699: getfield recoilBuf : F
/*      */     //   702: fconst_0
/*      */     //   703: fcmpl
/*      */     //   704: ifeq -> 837
/*      */     //   707: aload #8
/*      */     //   709: getfield recoilBuf : [Lcom/norwood/mcheli/weapon/MCH_WeaponSet$Recoil;
/*      */     //   712: iconst_0
/*      */     //   713: aaload
/*      */     //   714: astore #10
/*      */     //   716: aload #7
/*      */     //   718: getfield name : [Ljava/lang/String;
/*      */     //   721: arraylength
/*      */     //   722: iconst_1
/*      */     //   723: if_icmple -> 802
/*      */     //   726: aload #7
/*      */     //   728: getfield name : [Ljava/lang/String;
/*      */     //   731: astore #11
/*      */     //   733: aload #11
/*      */     //   735: arraylength
/*      */     //   736: istore #12
/*      */     //   738: iconst_0
/*      */     //   739: istore #13
/*      */     //   741: iload #13
/*      */     //   743: iload #12
/*      */     //   745: if_icmpge -> 802
/*      */     //   748: aload #11
/*      */     //   750: iload #13
/*      */     //   752: aaload
/*      */     //   753: astore #14
/*      */     //   755: aload_0
/*      */     //   756: aload #14
/*      */     //   758: invokevirtual getWeaponByName : (Ljava/lang/String;)Lcom/norwood/mcheli/weapon/MCH_WeaponSet;
/*      */     //   761: astore #15
/*      */     //   763: aload #15
/*      */     //   765: ifnull -> 796
/*      */     //   768: aload #15
/*      */     //   770: getfield recoilBuf : [Lcom/norwood/mcheli/weapon/MCH_WeaponSet$Recoil;
/*      */     //   773: iconst_0
/*      */     //   774: aaload
/*      */     //   775: getfield recoilBuf : F
/*      */     //   778: aload #10
/*      */     //   780: getfield recoilBuf : F
/*      */     //   783: fcmpl
/*      */     //   784: ifle -> 796
/*      */     //   787: aload #15
/*      */     //   789: getfield recoilBuf : [Lcom/norwood/mcheli/weapon/MCH_WeaponSet$Recoil;
/*      */     //   792: iconst_0
/*      */     //   793: aaload
/*      */     //   794: astore #10
/*      */     //   796: iinc #13, 1
/*      */     //   799: goto -> 741
/*      */     //   802: aload #10
/*      */     //   804: getfield prevRecoilBuf : F
/*      */     //   807: aload #10
/*      */     //   809: getfield recoilBuf : F
/*      */     //   812: aload #10
/*      */     //   814: getfield prevRecoilBuf : F
/*      */     //   817: fsub
/*      */     //   818: fload_2
/*      */     //   819: fmul
/*      */     //   820: fadd
/*      */     //   821: fstore #11
/*      */     //   823: dconst_0
/*      */     //   824: dconst_0
/*      */     //   825: aload #7
/*      */     //   827: getfield recoilBuf : F
/*      */     //   830: fload #11
/*      */     //   832: fmul
/*      */     //   833: f2d
/*      */     //   834: invokestatic glTranslated : (DDD)V
/*      */     //   837: aload #8
/*      */     //   839: ifnull -> 915
/*      */     //   842: aload #8
/*      */     //   844: getfield defaultRotationYaw : F
/*      */     //   847: fconst_0
/*      */     //   848: ldc -1.0
/*      */     //   850: fconst_0
/*      */     //   851: invokestatic glRotatef : (FFFF)V
/*      */     //   854: aload #7
/*      */     //   856: getfield rotBarrel : Z
/*      */     //   859: ifeq -> 915
/*      */     //   862: aload #8
/*      */     //   864: getfield prevRotBarrel : F
/*      */     //   867: aload #8
/*      */     //   869: getfield rotBarrel : F
/*      */     //   872: aload #8
/*      */     //   874: getfield prevRotBarrel : F
/*      */     //   877: fsub
/*      */     //   878: fload_2
/*      */     //   879: fmul
/*      */     //   880: fadd
/*      */     //   881: fstore #10
/*      */     //   883: fload #10
/*      */     //   885: aload #7
/*      */     //   887: getfield rot : Lnet/minecraft/util/math/Vec3d;
/*      */     //   890: getfield x : D
/*      */     //   893: d2f
/*      */     //   894: aload #7
/*      */     //   896: getfield rot : Lnet/minecraft/util/math/Vec3d;
/*      */     //   899: getfield y : D
/*      */     //   902: d2f
/*      */     //   903: aload #7
/*      */     //   905: getfield rot : Lnet/minecraft/util/math/Vec3d;
/*      */     //   908: getfield z : D
/*      */     //   911: d2f
/*      */     //   912: invokestatic glRotatef : (FFFF)V
/*      */     //   915: aload #7
/*      */     //   917: getfield pos : Lnet/minecraft/util/math/Vec3d;
/*      */     //   920: getfield x : D
/*      */     //   923: dneg
/*      */     //   924: aload #7
/*      */     //   926: getfield pos : Lnet/minecraft/util/math/Vec3d;
/*      */     //   929: getfield y : D
/*      */     //   932: dneg
/*      */     //   933: aload #7
/*      */     //   935: getfield pos : Lnet/minecraft/util/math/Vec3d;
/*      */     //   938: getfield z : D
/*      */     //   941: dneg
/*      */     //   942: invokestatic glTranslated : (DDD)V
/*      */     //   945: aload #7
/*      */     //   947: getfield isMissile : Z
/*      */     //   950: ifeq -> 964
/*      */     //   953: aload_0
/*      */     //   954: aload #8
/*      */     //   956: iload #5
/*      */     //   958: invokevirtual isWeaponNotCooldown : (Lcom/norwood/mcheli/weapon/MCH_WeaponSet;I)Z
/*      */     //   961: ifne -> 1036
/*      */     //   964: aload #7
/*      */     //   966: getfield model : Lcom/norwood/mcheli/helper/client/_IModelCustom;
/*      */     //   969: aload_1
/*      */     //   970: getfield model : Lcom/norwood/mcheli/helper/client/_IModelCustom;
/*      */     //   973: aload #7
/*      */     //   975: getfield modelName : Ljava/lang/String;
/*      */     //   978: invokestatic renderPart : (Lcom/norwood/mcheli/helper/client/_IModelCustom;Lcom/norwood/mcheli/helper/client/_IModelCustom;Ljava/lang/String;)V
/*      */     //   981: aload #7
/*      */     //   983: getfield child : Ljava/util/List;
/*      */     //   986: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   991: astore #10
/*      */     //   993: aload #10
/*      */     //   995: invokeinterface hasNext : ()Z
/*      */     //   1000: ifeq -> 1036
/*      */     //   1003: aload #10
/*      */     //   1005: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1010: checkcast com/norwood/mcheli/aircraft/MCH_AircraftInfo$PartWeaponChild
/*      */     //   1013: astore #11
/*      */     //   1015: invokestatic glPushMatrix : ()V
/*      */     //   1018: aload_0
/*      */     //   1019: aload_1
/*      */     //   1020: aload #11
/*      */     //   1022: aload #8
/*      */     //   1024: aload #4
/*      */     //   1026: fload_2
/*      */     //   1027: invokestatic renderWeaponChild : (Lcom/norwood/mcheli/aircraft/MCH_EntityAircraft;Lcom/norwood/mcheli/aircraft/MCH_AircraftInfo;Lcom/norwood/mcheli/aircraft/MCH_AircraftInfo$PartWeaponChild;Lcom/norwood/mcheli/weapon/MCH_WeaponSet;Lnet/minecraft/entity/Entity;F)V
/*      */     //   1030: invokestatic glPopMatrix : ()V
/*      */     //   1033: goto -> 993
/*      */     //   1036: invokestatic glPopMatrix : ()V
/*      */     //   1039: iinc #5, 1
/*      */     //   1042: goto -> 22
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #240	-> 0
/*      */     //   #241	-> 2
/*      */     //   #242	-> 8
/*      */     //   #243	-> 11
/*      */     //   #249	-> 22
/*      */     //   #250	-> 32
/*      */     //   #253	-> 33
/*      */     //   #254	-> 45
/*      */     //   #255	-> 58
/*      */     //   #256	-> 64
/*      */     //   #257	-> 67
/*      */     //   #260	-> 70
/*      */     //   #261	-> 73
/*      */     //   #262	-> 76
/*      */     //   #263	-> 79
/*      */     //   #264	-> 82
/*      */     //   #265	-> 96
/*      */     //   #268	-> 99
/*      */     //   #269	-> 104
/*      */     //   #270	-> 114
/*      */     //   #274	-> 117
/*      */     //   #276	-> 120
/*      */     //   #277	-> 149
/*      */     //   #278	-> 161
/*      */     //   #279	-> 164
/*      */     //   #276	-> 167
/*      */     //   #283	-> 173
/*      */     //   #284	-> 178
/*      */     //   #287	-> 181
/*      */     //   #289	-> 184
/*      */     //   #290	-> 187
/*      */     //   #291	-> 195
/*      */     //   #292	-> 219
/*      */     //   #293	-> 243
/*      */     //   #294	-> 252
/*      */     //   #297	-> 279
/*      */     //   #298	-> 306
/*      */     //   #301	-> 314
/*      */     //   #302	-> 319
/*      */     //   #303	-> 332
/*      */     //   #304	-> 348
/*      */     //   #305	-> 353
/*      */     //   #306	-> 365
/*      */     //   #308	-> 380
/*      */     //   #309	-> 391
/*      */     //   #312	-> 402
/*      */     //   #313	-> 414
/*      */     //   #314	-> 425
/*      */     //   #315	-> 437
/*      */     //   #318	-> 445
/*      */     //   #321	-> 462
/*      */     //   #322	-> 470
/*      */     //   #323	-> 494
/*      */     //   #324	-> 504
/*      */     //   #327	-> 514
/*      */     //   #328	-> 517
/*      */     //   #329	-> 531
/*      */     //   #330	-> 541
/*      */     //   #331	-> 584
/*      */     //   #334	-> 597
/*      */     //   #337	-> 605
/*      */     //   #338	-> 610
/*      */     //   #339	-> 617
/*      */     //   #340	-> 627
/*      */     //   #341	-> 632
/*      */     //   #342	-> 639
/*      */     //   #344	-> 649
/*      */     //   #345	-> 655
/*      */     //   #348	-> 661
/*      */     //   #349	-> 666
/*      */     //   #350	-> 671
/*      */     //   #353	-> 676
/*      */     //   #356	-> 692
/*      */     //   #357	-> 707
/*      */     //   #358	-> 716
/*      */     //   #359	-> 726
/*      */     //   #360	-> 755
/*      */     //   #361	-> 763
/*      */     //   #362	-> 787
/*      */     //   #359	-> 796
/*      */     //   #367	-> 802
/*      */     //   #368	-> 823
/*      */     //   #371	-> 837
/*      */     //   #372	-> 842
/*      */     //   #373	-> 854
/*      */     //   #374	-> 862
/*      */     //   #375	-> 883
/*      */     //   #379	-> 915
/*      */     //   #380	-> 945
/*      */     //   #381	-> 964
/*      */     //   #383	-> 981
/*      */     //   #384	-> 1015
/*      */     //   #385	-> 1018
/*      */     //   #386	-> 1030
/*      */     //   #387	-> 1033
/*      */     //   #390	-> 1036
/*      */     //   #391	-> 1039
/*      */     //   #392	-> 1042
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   149	18	17	s	Ljava/lang/String;
/*      */     //   120	61	13	hide	Z
/*      */     //   73	108	9	rotYaw	F
/*      */     //   76	105	10	prevYaw	F
/*      */     //   79	102	11	rotPitch	F
/*      */     //   82	99	12	prevPitch	F
/*      */     //   243	36	9	ty	F
/*      */     //   332	16	9	var20	F
/*      */     //   345	3	10	var21	F
/*      */     //   365	15	9	var20	F
/*      */     //   377	3	10	var21	F
/*      */     //   391	71	9	var20	F
/*      */     //   402	60	10	var21	F
/*      */     //   494	20	9	ty	F
/*      */     //   541	56	10	t	F
/*      */     //   617	10	10	var22	F
/*      */     //   624	3	11	var23	F
/*      */     //   639	10	10	var22	F
/*      */     //   646	3	11	var23	F
/*      */     //   655	37	10	var22	F
/*      */     //   661	31	11	var23	F
/*      */     //   763	33	15	tws	Lcom/norwood/mcheli/weapon/MCH_WeaponSet;
/*      */     //   755	41	14	wnm	Ljava/lang/String;
/*      */     //   716	121	10	r	Lcom/norwood/mcheli/weapon/MCH_WeaponSet$Recoil;
/*      */     //   823	14	11	recoilBuf	F
/*      */     //   883	32	10	rotBrl	F
/*      */     //   1015	18	11	wc	Lcom/norwood/mcheli/aircraft/MCH_AircraftInfo$PartWeaponChild;
/*      */     //   45	997	7	w	Lcom/norwood/mcheli/aircraft/MCH_AircraftInfo$PartWeapon;
/*      */     //   58	984	8	ws	Lcom/norwood/mcheli/weapon/MCH_WeaponSet;
/*      */     //   517	525	9	rev_sign	Z
/*      */     //   0	1045	0	ac	Lcom/norwood/mcheli/aircraft/MCH_EntityAircraft;
/*      */     //   0	1045	1	info	Lcom/norwood/mcheli/aircraft/MCH_AircraftInfo;
/*      */     //   0	1045	2	tickTime	F
/*      */     //   2	1043	3	beforeWs	Lcom/norwood/mcheli/weapon/MCH_WeaponSet;
/*      */     //   8	1037	4	e	Lnet/minecraft/entity/Entity;
/*      */     //   11	1034	5	weaponIndex	I
/*      */     //   22	1023	6	var6	Ljava/util/Iterator;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   22	1023	6	var6	Ljava/util/Iterator<Lcom/norwood/mcheli/aircraft/MCH_AircraftInfo$PartWeapon;>;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void renderWeaponChild(MCH_EntityAircraft ac, MCH_AircraftInfo info, MCH_AircraftInfo.PartWeaponChild w, MCH_WeaponSet ws, Entity e, float tickTime) {
/*  402 */     GL11.glTranslated(w.pos.x, w.pos.y, w.pos.z);
/*  403 */     if (w.yaw) {
/*  404 */       float rotYaw, prevYaw; if (ws != null) {
/*  405 */         rotYaw = ws.rotationYaw - ws.defaultRotationYaw;
/*  406 */         prevYaw = ws.prevRotationYaw - ws.defaultRotationYaw;
/*  407 */       } else if (e != null) {
/*  408 */         rotYaw = e.rotationYaw - ac.getRotYaw();
/*  409 */         prevYaw = e.prevRotationYaw - ac.prevRotationYaw;
/*      */       } else {
/*  411 */         rotYaw = ac.getLastRiderYaw() - ac.rotationYaw;
/*  412 */         prevYaw = ac.prevLastRiderYaw - ac.prevRotationYaw;
/*      */       } 
/*      */       
/*  415 */       if (rotYaw - prevYaw > 180.0F) {
/*  416 */         prevYaw += 360.0F;
/*  417 */       } else if (rotYaw - prevYaw < -180.0F) {
/*  418 */         prevYaw -= 360.0F;
/*      */       } 
/*      */       
/*  421 */       GL11.glRotatef(prevYaw + (rotYaw - prevYaw) * tickTime, 0.0F, -1.0F, 0.0F);
/*      */     } 
/*      */     
/*  424 */     boolean rev_sign = false;
/*  425 */     if (ws != null && (int)ws.defaultRotationYaw != 0) {
/*  426 */       float t = MathHelper.wrapDegrees(ws.defaultRotationYaw);
/*  427 */       rev_sign = ((t >= 45.0F && t <= 135.0F) || (t <= -45.0F && t >= -135.0F));
/*  428 */       GL11.glRotatef(-ws.defaultRotationYaw, 0.0F, -1.0F, 0.0F);
/*      */     } 
/*      */     
/*  431 */     if (w.pitch) {
/*  432 */       float rotPitch, prevPitch; if (ws != null) {
/*  433 */         rotPitch = ws.rotationPitch;
/*  434 */         prevPitch = ws.prevRotationPitch;
/*  435 */       } else if (e != null) {
/*  436 */         rotPitch = e.rotationPitch;
/*  437 */         prevPitch = e.prevRotationPitch;
/*      */       } else {
/*  439 */         rotPitch = ac.getLastRiderPitch();
/*  440 */         prevPitch = ac.prevLastRiderPitch;
/*      */       } 
/*      */       
/*  443 */       if (rev_sign) {
/*  444 */         rotPitch = -rotPitch;
/*  445 */         prevPitch = -prevPitch;
/*      */       } 
/*      */       
/*  448 */       GL11.glRotatef(prevPitch + (rotPitch - prevPitch) * tickTime, 1.0F, 0.0F, 0.0F);
/*      */     } 
/*      */     
/*  451 */     if (ws != null && w.recoilBuf != 0.0F) {
/*  452 */       MCH_WeaponSet.Recoil r = ws.recoilBuf[0];
/*  453 */       if (w.name.length > 1) {
/*  454 */         for (String wnm : w.name) {
/*  455 */           MCH_WeaponSet tws = ac.getWeaponByName(wnm);
/*  456 */           if (tws != null && (tws.recoilBuf[0]).recoilBuf > r.recoilBuf) {
/*  457 */             r = tws.recoilBuf[0];
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  462 */       float recoilBuf = r.prevRecoilBuf + (r.recoilBuf - r.prevRecoilBuf) * tickTime;
/*  463 */       GL11.glTranslated(0.0D, 0.0D, (-w.recoilBuf * recoilBuf));
/*      */     } 
/*      */     
/*  466 */     if (ws != null) {
/*  467 */       GL11.glRotatef(ws.defaultRotationYaw, 0.0F, -1.0F, 0.0F);
/*      */     }
/*      */     
/*  470 */     GL11.glTranslated(-w.pos.x, -w.pos.y, -w.pos.z);
/*  471 */     renderPart(w.model, info.model, w.modelName);
/*      */   }
/*      */   
/*      */   public static void renderTrackRoller(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  475 */     if (!info.partTrackRoller.isEmpty()) {
/*  476 */       float[] rot = ac.rotTrackRoller;
/*  477 */       float[] prevRot = ac.prevRotTrackRoller;
/*      */       
/*  479 */       for (MCH_AircraftInfo.TrackRoller t : info.partTrackRoller) {
/*  480 */         GL11.glPushMatrix();
/*  481 */         GL11.glTranslated(t.pos.x, t.pos.y, t.pos.z);
/*  482 */         GL11.glRotatef(prevRot[t.side] + (rot[t.side] - prevRot[t.side]) * tickTime, 1.0F, 0.0F, 0.0F);
/*  483 */         GL11.glTranslated(-t.pos.x, -t.pos.y, -t.pos.z);
/*  484 */         renderPart(t.model, info.model, t.modelName);
/*  485 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderCrawlerTrack(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  491 */     if (!info.partCrawlerTrack.isEmpty()) {
/*  492 */       int prevWidth = GL11.glGetInteger(2833);
/*  493 */       Tessellator tessellator = Tessellator.getInstance();
/*  494 */       BufferBuilder builder = tessellator.getBuffer();
/*      */       
/*  496 */       for (MCH_AircraftInfo.CrawlerTrack c : info.partCrawlerTrack) {
/*  497 */         GL11.glPointSize(c.len * 20.0F);
/*  498 */         if (MCH_Config.TestMode.prmBool) {
/*  499 */           GL11.glDisable(3553);
/*  500 */           GL11.glDisable(3042);
/*  501 */           builder.begin(0, DefaultVertexFormats.POSITION_COLOR);
/*      */           
/*  503 */           for (int j = 0; j < c.cx.length; j++) {
/*  504 */             builder.pos(c.z, c.cx[j], c.cy[j]).color((int)(255.0F / c.cx.length * j), 80, 255 - (int)(255.0F / c.cx.length * j), 255).endVertex();
/*      */           }
/*      */           
/*  507 */           tessellator.draw();
/*      */         } 
/*      */         
/*  510 */         GL11.glEnable(3553);
/*  511 */         GL11.glEnable(3042);
/*  512 */         int L = c.lp.size() - 1;
/*  513 */         double rc = (ac != null) ? ac.rotCrawlerTrack[c.side] : 0.0D;
/*  514 */         double pc = (ac != null) ? ac.prevRotCrawlerTrack[c.side] : 0.0D;
/*      */         
/*  516 */         for (int i = 0; i < L; i++) {
/*  517 */           MCH_AircraftInfo.CrawlerTrackPrm cp = c.lp.get(i);
/*  518 */           MCH_AircraftInfo.CrawlerTrackPrm np = c.lp.get((i + 1) % L);
/*  519 */           double x1 = cp.x;
/*  520 */           double x2 = np.x;
/*  521 */           double r1 = cp.r;
/*  522 */           double y1 = cp.y;
/*  523 */           double y2 = np.y;
/*  524 */           double r2 = np.r;
/*  525 */           if (r2 - r1 < -180.0D) {
/*  526 */             r2 += 360.0D;
/*      */           }
/*      */           
/*  529 */           if (r2 - r1 > 180.0D) {
/*  530 */             r2 -= 360.0D;
/*      */           }
/*      */           
/*  533 */           double sx = x1 + (x2 - x1) * rc;
/*  534 */           double sy = y1 + (y2 - y1) * rc;
/*  535 */           double sr = r1 + (r2 - r1) * rc;
/*  536 */           double ex = x1 + (x2 - x1) * pc;
/*  537 */           double ey = y1 + (y2 - y1) * pc;
/*  538 */           double er = r1 + (r2 - r1) * pc;
/*  539 */           double x = sx + (ex - sx) * pc;
/*  540 */           double y = sy + (ey - sy) * pc;
/*  541 */           double r = sr + (er - sr) * pc;
/*  542 */           GL11.glPushMatrix();
/*  543 */           GL11.glTranslated(0.0D, x, y);
/*  544 */           GL11.glRotatef((float)r, -1.0F, 0.0F, 0.0F);
/*  545 */           renderPart(c.model, info.model, c.modelName);
/*  546 */           GL11.glPopMatrix();
/*      */         } 
/*      */       } 
/*      */       
/*  550 */       GL11.glEnable(3042);
/*  551 */       GL11.glPointSize(prevWidth);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderHatch(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  556 */     if (info.haveHatch() && ac.partHatch != null) {
/*  557 */       float rot = ac.getHatchRotation();
/*  558 */       float prevRot = ac.getPrevHatchRotation();
/*      */       
/*  560 */       for (MCH_AircraftInfo.Hatch h : info.hatchList) {
/*  561 */         GL11.glPushMatrix();
/*  562 */         if (h.isSlide) {
/*  563 */           float r = ac.partHatch.rotation / ac.partHatch.rotationMax;
/*  564 */           float pr = ac.partHatch.prevRotation / ac.partHatch.rotationMax;
/*  565 */           float f = pr + (r - pr) * tickTime;
/*  566 */           GL11.glTranslated(h.pos.x * f, h.pos.y * f, h.pos.z * f);
/*      */         } else {
/*  568 */           GL11.glTranslated(h.pos.x, h.pos.y, h.pos.z);
/*  569 */           GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * h.maxRotFactor, (float)h.rot.x, (float)h.rot.y, (float)h.rot.z);
/*  570 */           GL11.glTranslated(-h.pos.x, -h.pos.y, -h.pos.z);
/*      */         } 
/*      */         
/*  573 */         renderPart(h.model, info.model, h.modelName);
/*  574 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderThrottle(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  580 */     if (info.havePartThrottle()) {
/*  581 */       float throttle = MCH_Lib.smooth((float)ac.getCurrentThrottle(), (float)ac.getPrevCurrentThrottle(), tickTime);
/*      */       
/*  583 */       for (MCH_AircraftInfo.Throttle h : info.partThrottle) {
/*  584 */         GL11.glPushMatrix();
/*  585 */         GL11.glTranslated(h.pos.x, h.pos.y, h.pos.z);
/*  586 */         GL11.glRotatef(throttle * h.rot2, (float)h.rot.x, (float)h.rot.y, (float)h.rot.z);
/*  587 */         GL11.glTranslated(-h.pos.x, -h.pos.y, -h.pos.z);
/*  588 */         GL11.glTranslated(h.slide.x * throttle, h.slide.y * throttle, h.slide.z * throttle);
/*  589 */         renderPart(h.model, info.model, h.modelName);
/*  590 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderWeaponBay(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  596 */     for (int i = 0; i < info.partWeaponBay.size(); i++) {
/*  597 */       MCH_AircraftInfo.WeaponBay w = info.partWeaponBay.get(i);
/*  598 */       MCH_EntityAircraft.WeaponBay ws = ac.weaponBays[i];
/*  599 */       GL11.glPushMatrix();
/*  600 */       if (w.isSlide) {
/*  601 */         float r = ws.rot / 90.0F;
/*  602 */         float pr = ws.prevRot / 90.0F;
/*  603 */         float f = pr + (r - pr) * tickTime;
/*  604 */         GL11.glTranslated(w.pos.x * f, w.pos.y * f, w.pos.z * f);
/*      */       } else {
/*  606 */         GL11.glTranslated(w.pos.x, w.pos.y, w.pos.z);
/*  607 */         GL11.glRotatef((ws.prevRot + (ws.rot - ws.prevRot) * tickTime) * w.maxRotFactor, (float)w.rot.x, (float)w.rot.y, (float)w.rot.z);
/*  608 */         GL11.glTranslated(-w.pos.x, -w.pos.y, -w.pos.z);
/*      */       } 
/*      */       
/*  611 */       renderPart(w.model, info.model, w.modelName);
/*  612 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderCamera(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  617 */     if (info.havePartCamera()) {
/*  618 */       float rotYaw = ac.camera.partRotationYaw;
/*  619 */       float prevRotYaw = ac.camera.prevPartRotationYaw;
/*  620 */       float rotPitch = ac.camera.partRotationPitch;
/*  621 */       float prevRotPitch = ac.camera.prevPartRotationPitch;
/*  622 */       float yaw = prevRotYaw + (rotYaw - prevRotYaw) * tickTime - ac.getRotYaw();
/*  623 */       float pitch = prevRotPitch + (rotPitch - prevRotPitch) * tickTime - ac.getRotPitch();
/*      */       
/*  625 */       for (MCH_AircraftInfo.Camera c : info.cameraList) {
/*  626 */         GL11.glPushMatrix();
/*  627 */         GL11.glTranslated(c.pos.x, c.pos.y, c.pos.z);
/*  628 */         if (c.yawSync) {
/*  629 */           GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*      */         }
/*      */         
/*  632 */         if (c.pitchSync) {
/*  633 */           GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/*  636 */         GL11.glTranslated(-c.pos.x, -c.pos.y, -c.pos.z);
/*  637 */         renderPart(c.model, info.model, c.modelName);
/*  638 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderCanopy(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  644 */     if (info.haveCanopy() && ac.partCanopy != null) {
/*  645 */       float rot = ac.getCanopyRotation();
/*  646 */       float prevRot = ac.getPrevCanopyRotation();
/*      */       
/*  648 */       for (MCH_AircraftInfo.Canopy c : info.canopyList) {
/*  649 */         GL11.glPushMatrix();
/*  650 */         if (c.isSlide) {
/*  651 */           float r = ac.partCanopy.rotation / ac.partCanopy.rotationMax;
/*  652 */           float pr = ac.partCanopy.prevRotation / ac.partCanopy.rotationMax;
/*  653 */           float f = pr + (r - pr) * tickTime;
/*  654 */           GL11.glTranslated(c.pos.x * f, c.pos.y * f, c.pos.z * f);
/*      */         } else {
/*  656 */           GL11.glTranslated(c.pos.x, c.pos.y, c.pos.z);
/*  657 */           GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * c.maxRotFactor, (float)c.rot.x, (float)c.rot.y, (float)c.rot.z);
/*  658 */           GL11.glTranslated(-c.pos.x, -c.pos.y, -c.pos.z);
/*      */         } 
/*      */         
/*  661 */         renderPart(c.model, info.model, c.modelName);
/*  662 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderLandingGear(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  668 */     if (info.haveLandingGear() && ac.partLandingGear != null) {
/*  669 */       float rot = ac.getLandingGearRotation();
/*  670 */       float prevRot = ac.getPrevLandingGearRotation();
/*  671 */       float revR = 90.0F - rot;
/*  672 */       float revPr = 90.0F - prevRot;
/*  673 */       float rot1 = prevRot + (rot - prevRot) * tickTime;
/*  674 */       float rot1Rev = revPr + (revR - revPr) * tickTime;
/*  675 */       float rotHatch = 90.0F * MathHelper.sin(rot1 * 2.0F * 3.1415927F / 180.0F) * 3.0F;
/*  676 */       if (rotHatch > 90.0F) {
/*  677 */         rotHatch = 90.0F;
/*      */       }
/*      */       
/*  680 */       for (MCH_AircraftInfo.LandingGear n : info.landingGear) {
/*  681 */         GL11.glPushMatrix();
/*  682 */         GL11.glTranslated(n.pos.x, n.pos.y, n.pos.z);
/*  683 */         if (!n.reverse) {
/*  684 */           if (!n.hatch) {
/*  685 */             GL11.glRotatef(rot1 * n.maxRotFactor, (float)n.rot.x, (float)n.rot.y, (float)n.rot.z);
/*      */           } else {
/*  687 */             GL11.glRotatef(rotHatch * n.maxRotFactor, (float)n.rot.x, (float)n.rot.y, (float)n.rot.z);
/*      */           } 
/*      */         } else {
/*  690 */           GL11.glRotatef(rot1Rev * n.maxRotFactor, (float)n.rot.x, (float)n.rot.y, (float)n.rot.z);
/*      */         } 
/*      */         
/*  693 */         if (n.enableRot2) {
/*  694 */           if (!n.reverse) {
/*  695 */             GL11.glRotatef(rot1 * n.maxRotFactor2, (float)n.rot2.x, (float)n.rot2.y, (float)n.rot2.z);
/*      */           } else {
/*  697 */             GL11.glRotatef(rot1Rev * n.maxRotFactor2, (float)n.rot2.x, (float)n.rot2.y, (float)n.rot2.z);
/*      */           } 
/*      */         }
/*      */         
/*  701 */         GL11.glTranslated(-n.pos.x, -n.pos.y, -n.pos.z);
/*  702 */         if (n.slide != null) {
/*  703 */           float f = rot / 90.0F;
/*  704 */           if (n.reverse) {
/*  705 */             f = 1.0F - f;
/*      */           }
/*      */           
/*  708 */           GL11.glTranslated(f * n.slide.x, f * n.slide.y, f * n.slide.z);
/*      */         } 
/*      */         
/*  711 */         renderPart(n.model, info.model, n.modelName);
/*  712 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderEntityMarker(Entity entity) {
/*  718 */     EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).player;
/*  719 */     if (entityPlayerSP != null && 
/*  720 */       !W_Entity.isEqual((Entity)entityPlayerSP, entity)) {
/*  721 */       MCH_EntityAircraft ac = null;
/*  722 */       if (entityPlayerSP.getRidingEntity() instanceof MCH_EntityAircraft) {
/*  723 */         ac = (MCH_EntityAircraft)entityPlayerSP.getRidingEntity();
/*  724 */       } else if (entityPlayerSP.getRidingEntity() instanceof MCH_EntitySeat) {
/*  725 */         ac = ((MCH_EntitySeat)entityPlayerSP.getRidingEntity()).getParent();
/*  726 */       } else if (entityPlayerSP.getRidingEntity() instanceof MCH_EntityUavStation) {
/*  727 */         ac = ((MCH_EntityUavStation)entityPlayerSP.getRidingEntity()).getControlAircract();
/*      */       } 
/*      */       
/*  730 */       if (ac != null && 
/*  731 */         !W_Entity.isEqual((Entity)ac, entity)) {
/*  732 */         MCH_WeaponGuidanceSystem gs = ac.getCurrentWeapon((Entity)entityPlayerSP).getCurrentWeapon().getGuidanceSystem();
/*  733 */         if (gs != null && gs.canLockEntity(entity)) {
/*  734 */           RenderManager rm = Minecraft.getMinecraft().getRenderManager();
/*  735 */           double dist = entity.getDistanceSq(rm.renderViewEntity);
/*  736 */           double x = entity.posX - TileEntityRendererDispatcher.staticPlayerX;
/*  737 */           double y = entity.posY - TileEntityRendererDispatcher.staticPlayerY;
/*  738 */           double z = entity.posZ - TileEntityRendererDispatcher.staticPlayerZ;
/*  739 */           if (dist < 10000.0D) {
/*  740 */             GL11.glPushMatrix();
/*  741 */             GL11.glTranslatef((float)x, (float)y + entity.height + 0.5F, (float)z);
/*  742 */             GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*  743 */             GL11.glRotatef(-rm.playerViewY, 0.0F, 1.0F, 0.0F);
/*  744 */             GL11.glRotatef(rm.playerViewX, 1.0F, 0.0F, 0.0F);
/*  745 */             GL11.glScalef(-0.02666667F, -0.02666667F, 0.02666667F);
/*  746 */             GL11.glDisable(2896);
/*  747 */             GL11.glTranslatef(0.0F, 9.374999F, 0.0F);
/*  748 */             GL11.glDepthMask(false);
/*  749 */             GL11.glEnable(3042);
/*  750 */             GL11.glBlendFunc(770, 771);
/*  751 */             GL11.glDisable(3553);
/*  752 */             int prevWidth = GL11.glGetInteger(2849);
/*  753 */             float size = Math.max(entity.width, entity.height) * 20.0F;
/*  754 */             if (entity instanceof MCH_EntityAircraft) {
/*  755 */               size *= 2.0F;
/*      */             }
/*      */             
/*  758 */             Tessellator tessellator = Tessellator.getInstance();
/*  759 */             BufferBuilder builder = tessellator.getBuffer();
/*  760 */             builder.begin(2, MCH_Verts.POS_COLOR_LMAP);
/*  761 */             boolean isLockEntity = gs.isLockingEntity(entity);
/*  762 */             if (isLockEntity) {
/*  763 */               GL11.glLineWidth(MCH_Gui.scaleFactor * 1.5F);
/*  764 */               builder.pos((-size - 1.0F), 0.0D, 0.0D).color(1.0F, 0.0F, 0.0F, 1.0F).lightmap(0, 240).endVertex();
/*  765 */               builder.pos((-size - 1.0F), (size * 2.0F), 0.0D).color(1.0F, 0.0F, 0.0F, 1.0F).lightmap(0, 240).endVertex();
/*  766 */               builder.pos((size + 1.0F), (size * 2.0F), 0.0D).color(1.0F, 0.0F, 0.0F, 1.0F).lightmap(0, 240).endVertex();
/*  767 */               builder.pos((size + 1.0F), 0.0D, 0.0D).color(1.0F, 0.0F, 0.0F, 1.0F).lightmap(0, 240).endVertex();
/*      */             } else {
/*  769 */               GL11.glLineWidth(MCH_Gui.scaleFactor);
/*  770 */               builder.pos((-size - 1.0F), 0.0D, 0.0D).color(1.0F, 0.3F, 0.0F, 8.0F).lightmap(0, 240).endVertex();
/*  771 */               builder.pos((-size - 1.0F), (size * 2.0F), 0.0D).color(1.0F, 0.3F, 0.0F, 8.0F).lightmap(0, 240).endVertex();
/*  772 */               builder.pos((size + 1.0F), (size * 2.0F), 0.0D).color(1.0F, 0.3F, 0.0F, 8.0F).lightmap(0, 240).endVertex();
/*  773 */               builder.pos((size + 1.0F), 0.0D, 0.0D).color(1.0F, 0.3F, 0.0F, 8.0F).lightmap(0, 240).endVertex();
/*      */             } 
/*      */             
/*  776 */             tessellator.draw();
/*  777 */             GL11.glPopMatrix();
/*  778 */             if (!ac.isUAV() && isLockEntity && (Minecraft.getMinecraft()).gameSettings.thirdPersonView == 0) {
/*  779 */               GL11.glPushMatrix();
/*  780 */               builder.begin(1, MCH_Verts.POS_COLOR_LMAP);
/*  781 */               GL11.glLineWidth(1.0F);
/*  782 */               builder.pos(x, y + (entity.height / 2.0F), z).color(1.0F, 0.0F, 0.0F, 1.0F).lightmap(0, 240).endVertex();
/*  783 */               builder.pos(ac.lastTickPosX - TileEntityRendererDispatcher.staticPlayerX, ac.lastTickPosY - TileEntityRendererDispatcher.staticPlayerY - 1.0D, ac.lastTickPosZ - TileEntityRendererDispatcher.staticPlayerZ)
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  788 */                 .color(1.0F, 0.0F, 0.0F, 1.0F)
/*  789 */                 .lightmap(0, 240)
/*  790 */                 .endVertex();
/*  791 */               tessellator.draw();
/*  792 */               GL11.glPopMatrix();
/*      */             } 
/*      */             
/*  795 */             GL11.glLineWidth(prevWidth);
/*  796 */             GL11.glEnable(3553);
/*  797 */             GL11.glDepthMask(true);
/*  798 */             GL11.glEnable(2896);
/*  799 */             GL11.glDisable(3042);
/*  800 */             GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void renderRope(MCH_EntityAircraft ac, MCH_AircraftInfo info, double x, double y, double z, float tickTime) {
/*  810 */     GL11.glPushMatrix();
/*  811 */     Tessellator tessellator = Tessellator.getInstance();
/*  812 */     BufferBuilder builder = tessellator.getBuffer();
/*  813 */     if (ac.isRepelling()) {
/*  814 */       GL11.glDisable(3553);
/*  815 */       GL11.glDisable(2896);
/*      */       
/*  817 */       for (int i = 0; i < info.repellingHooks.size(); i++) {
/*  818 */         builder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*  819 */         builder.pos(((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.x, ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.y, ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.z).color(0, 0, 0, 255).endVertex();
/*  820 */         builder.pos(((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.x, ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.y + ac.ropesLength, ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.z)
/*  821 */           .color(0, 0, 0, 255)
/*  822 */           .endVertex();
/*  823 */         tessellator.draw();
/*      */       } 
/*      */       
/*  826 */       GL11.glEnable(2896);
/*  827 */       GL11.glEnable(3553);
/*      */     } 
/*      */     
/*  830 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public void doRender(T entity, double posX, double posY, double posZ, float par8, float tickTime) {
/*  834 */     MCH_AircraftInfo info = entity.getAcInfo();
/*  835 */     if (info != null) {
/*  836 */       GL11.glPushMatrix();
/*  837 */       float yaw = calcRot(entity.getRotYaw(), ((MCH_EntityAircraft)entity).prevRotationYaw, tickTime);
/*  838 */       float pitch = entity.calcRotPitch(tickTime);
/*  839 */       float roll = calcRot(entity.getRotRoll(), ((MCH_EntityAircraft)entity).prevRotationRoll, tickTime);
/*  840 */       if (MCH_Config.EnableModEntityRender.prmBool) {
/*  841 */         renderRiddenEntity((MCH_EntityAircraft)entity, tickTime, yaw, pitch + info.entityPitch, roll + info.entityRoll, info.entityWidth, info.entityHeight);
/*      */       }
/*      */       
/*  844 */       if (!shouldSkipRender((Entity)entity)) {
/*  845 */         setCommonRenderParam(info.smoothShading, entity.getBrightnessForRender());
/*  846 */         if (entity.isDestroyed()) {
/*  847 */           GL11.glColor4f(0.15F, 0.15F, 0.15F, 1.0F);
/*      */         } else {
/*  849 */           GL11.glColor4f(0.75F, 0.75F, 0.75F, (float)MCH_Config.__TextureAlpha.prmDouble);
/*      */         } 
/*      */         
/*  852 */         renderAircraft((MCH_EntityAircraft)entity, posX, posY, posZ, yaw, pitch, roll, tickTime);
/*  853 */         renderCommonPart((MCH_EntityAircraft)entity, info, posX, posY, posZ, tickTime);
/*  854 */         renderLight(posX, posY, posZ, tickTime, (MCH_EntityAircraft)entity, info);
/*  855 */         restoreCommonRenderParam();
/*      */       } 
/*      */       
/*  858 */       GL11.glPopMatrix();
/*  859 */       MCH_GuiTargetMarker.addMarkEntityPos(1, (ITargetMarkerObject)entity, posX, posY + info.markerHeight, posZ);
/*  860 */       MCH_ClientLightWeaponTickHandler.markEntity((Entity)entity, posX, posY, posZ);
/*  861 */       renderEntityMarker((Entity)entity);
/*  862 */       if (MCH_Config.TestMode.prmBool) {
/*  863 */         WeaponPointRenderer.renderWeaponPoints((MCH_EntityAircraft)entity, info, posX, posY, posZ);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean shouldRender(@NotNull T livingEntity, @NotNull ICamera camera, double camX, double camY, double camZ) {
/*  869 */     return true;
/*      */   }
/*      */   
/*      */   public void doRenderShadowAndFire(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
/*  873 */     if (entity.canRenderOnFire()) {
/*  874 */       renderEntityOnFire(entity, x, y, z, partialTicks);
/*      */     }
/*      */   }
/*      */   
/*      */   private void renderEntityOnFire(Entity entity, double x, double y, double z, float tick) {
/*  879 */     GL11.glDisable(2896);
/*  880 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/*  881 */     TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_0");
/*  882 */     TextureAtlasSprite textureatlassprite1 = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_1");
/*  883 */     GL11.glPushMatrix();
/*  884 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*  885 */     float f1 = entity.width * 1.4F;
/*  886 */     GL11.glScalef(f1 * 2.0F, f1 * 2.0F, f1 * 2.0F);
/*  887 */     Tessellator tessellator = Tessellator.getInstance();
/*  888 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  889 */     float f2 = 1.5F;
/*  890 */     float f3 = 0.0F;
/*  891 */     float f4 = entity.height / f1;
/*  892 */     float f5 = (float)(entity.posY + (entity.getEntityBoundingBox()).minY);
/*  893 */     GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/*  894 */     GL11.glTranslatef(0.0F, 0.0F, -0.3F + (int)f4 * 0.02F);
/*  895 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  896 */     float f6 = 0.0F;
/*  897 */     int i = 0;
/*  898 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/*      */     
/*  900 */     while (f4 > 0.0F) {
/*  901 */       TextureAtlasSprite textureatlassprite2 = (i % 2 == 0) ? textureatlassprite : textureatlassprite1;
/*  902 */       bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  903 */       float f7 = textureatlassprite2.getMinU();
/*  904 */       float f8 = textureatlassprite2.getMinV();
/*  905 */       float f9 = textureatlassprite2.getMaxU();
/*  906 */       float f10 = textureatlassprite2.getMaxV();
/*  907 */       if (i / 2 % 2 == 0) {
/*  908 */         float f11 = f9;
/*  909 */         f9 = f7;
/*  910 */         f7 = f11;
/*      */       } 
/*      */       
/*  913 */       bufferbuilder.pos((f2 - f3), (0.0F - f5), f6).tex(f9, f10).endVertex();
/*  914 */       bufferbuilder.pos((-f2 - f3), (0.0F - f5), f6).tex(f7, f10).endVertex();
/*  915 */       bufferbuilder.pos((-f2 - f3), (1.4F - f5), f6).tex(f7, f8).endVertex();
/*  916 */       bufferbuilder.pos((f2 - f3), (1.4F - f5), f6).tex(f9, f8).endVertex();
/*  917 */       f4 -= 0.45F;
/*  918 */       f5 -= 0.45F;
/*  919 */       f2 *= 0.9F;
/*  920 */       f6 += 0.03F;
/*  921 */       i++;
/*      */     } 
/*      */     
/*  924 */     tessellator.draw();
/*  925 */     GL11.glPopMatrix();
/*  926 */     GL11.glEnable(2896);
/*      */   }
/*      */   
/*      */   protected void bindTexture(String path, MCH_EntityAircraft ac) {
/*  930 */     if (ac == MCH_ClientCommonTickHandler.ridingAircraft) {
/*  931 */       int bk = MCH_ClientCommonTickHandler.cameraMode;
/*  932 */       MCH_ClientCommonTickHandler.cameraMode = 0;
/*  933 */       bindTexture(MCH_Utils.suffix(path));
/*  934 */       MCH_ClientCommonTickHandler.cameraMode = bk;
/*      */     } else {
/*  936 */       bindTexture(MCH_Utils.suffix(path));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderRiddenEntity(MCH_EntityAircraft ac, float tickTime, float yaw, float pitch, float roll, float width, float height) {
/*  941 */     MCH_ClientEventHook.setCancelRender(false);
/*  942 */     GL11.glPushMatrix();
/*  943 */     renderEntitySimple(ac, ac.getRiddenByEntity(), tickTime, yaw, pitch, roll, width, height);
/*      */     
/*  945 */     for (MCH_EntitySeat s : ac.getSeats()) {
/*  946 */       if (s != null) {
/*  947 */         renderEntitySimple(ac, s.getRiddenByEntity(), tickTime, yaw, pitch, roll, width, height);
/*      */       }
/*      */     } 
/*      */     
/*  951 */     GL11.glPopMatrix();
/*  952 */     MCH_ClientEventHook.setCancelRender(true);
/*      */   }
/*      */   
/*      */   public void renderEntitySimple(MCH_EntityAircraft ac, Entity entity, float tickTime, float yaw, float pitch, float roll, float width, float height) {
/*  956 */     if (entity != null) {
/*  957 */       boolean isPilot = ac.isPilot(entity);
/*  958 */       boolean isClientPlayer = W_Lib.isClientPlayer(entity);
/*  959 */       if (!isClientPlayer || !W_Lib.isFirstPerson() || (isPilot && ac.getCameraId() > 0)) {
/*  960 */         GL11.glPushMatrix();
/*  961 */         if (entity.ticksExisted == 0) {
/*  962 */           entity.lastTickPosX = entity.posX;
/*  963 */           entity.lastTickPosY = entity.posY;
/*  964 */           entity.lastTickPosZ = entity.posZ;
/*      */         } 
/*      */         
/*  967 */         double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * tickTime;
/*  968 */         double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * tickTime;
/*  969 */         double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * tickTime;
/*  970 */         float f1 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * tickTime;
/*  971 */         int i = entity.getBrightnessForRender();
/*  972 */         if (entity.isBurning()) {
/*  973 */           i = 15728880;
/*      */         }
/*      */         
/*  976 */         int j = i % 65536;
/*  977 */         int k = i / 65536;
/*  978 */         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/*  979 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  980 */         double dx = x - TileEntityRendererDispatcher.staticPlayerX;
/*  981 */         double dy = y - TileEntityRendererDispatcher.staticPlayerY;
/*  982 */         double dz = z - TileEntityRendererDispatcher.staticPlayerZ;
/*  983 */         GL11.glTranslated(dx, dy, dz);
/*  984 */         GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*  985 */         GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*  986 */         GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
/*  987 */         GL11.glScaled(width, height, width);
/*  988 */         GL11.glRotatef(-yaw, 0.0F, -1.0F, 0.0F);
/*  989 */         GL11.glTranslated(-dx, -dy, -dz);
/*  990 */         boolean bk = renderingEntity;
/*  991 */         renderingEntity = true;
/*  992 */         Entity ridingEntity = entity.getRidingEntity();
/*  993 */         if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_IEntityCanRideAircraft)) {
/*  994 */           entity.dismountRidingEntity();
/*      */         }
/*      */         
/*  997 */         EntityLivingBase entityLiving = (entity instanceof EntityLivingBase) ? (EntityLivingBase)entity : null;
/*  998 */         float bkPitch = 0.0F;
/*  999 */         float bkPrevPitch = 0.0F;
/* 1000 */         if (isPilot && entityLiving != null) {
/* 1001 */           entityLiving.renderYawOffset = ac.getRotYaw();
/* 1002 */           entityLiving.prevRenderYawOffset = ac.getRotYaw();
/* 1003 */           if (ac.getCameraId() > 0) {
/* 1004 */             entityLiving.rotationYawHead = ac.getRotYaw();
/* 1005 */             entityLiving.prevRotationYawHead = ac.getRotYaw();
/* 1006 */             bkPitch = entityLiving.rotationPitch;
/* 1007 */             bkPrevPitch = entityLiving.prevRotationPitch;
/* 1008 */             entityLiving.rotationPitch = ac.getRotPitch();
/* 1009 */             entityLiving.prevRotationPitch = ac.getRotPitch();
/*      */           } 
/*      */         } 
/*      */         
/* 1013 */         W_EntityRenderer.renderEntityWithPosYaw(this.renderManager, entity, dx, dy, dz, f1, tickTime, false);
/* 1014 */         if (isPilot && entityLiving != null && ac.getCameraId() > 0) {
/* 1015 */           entityLiving.rotationPitch = bkPitch;
/* 1016 */           entityLiving.prevRotationPitch = bkPrevPitch;
/*      */         } 
/*      */         
/* 1019 */         entity.startRiding(ridingEntity);
/* 1020 */         renderingEntity = bk;
/* 1021 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float calcRot(float rot, float prevRot, float tickTime) {
/* 1029 */     rot = MathHelper.wrapDegrees(rot);
/* 1030 */     prevRot = MathHelper.wrapDegrees(prevRot);
/* 1031 */     if (rot - prevRot < -180.0F) {
/* 1032 */       prevRot -= 360.0F;
/* 1033 */     } else if (prevRot - rot < -180.0F) {
/* 1034 */       prevRot += 360.0F;
/*      */     } 
/*      */     
/* 1037 */     return prevRot + (rot - prevRot) * tickTime;
/*      */   }
/*      */   
/*      */   public void renderDebugHitBox(MCH_EntityAircraft e, double x, double y, double z, float yaw, float pitch) {
/* 1041 */     if (MCH_Config.TestMode.prmBool && debugModel != null) {
/* 1042 */       GL11.glPushMatrix();
/* 1043 */       GL11.glTranslated(x, y, z);
/* 1044 */       GL11.glScalef(e.width, e.height, e.width);
/* 1045 */       bindTexture("textures/hit_box.png");
/* 1046 */       debugModel.renderAll();
/* 1047 */       GL11.glPopMatrix();
/* 1048 */       GL11.glPushMatrix();
/* 1049 */       GL11.glTranslated(x, y, z);
/*      */       
/* 1051 */       for (MCH_BoundingBox bb : e.extraBoundingBox) {
/* 1052 */         GL11.glPushMatrix();
/* 1053 */         GL11.glTranslated(bb.rotatedOffset.x, bb.rotatedOffset.y, bb.rotatedOffset.z);
/* 1054 */         GL11.glPushMatrix();
/* 1055 */         GL11.glScalef(bb.width, bb.height, bb.width);
/* 1056 */         bindTexture("textures/bounding_box.png");
/* 1057 */         debugModel.renderAll();
/* 1058 */         GL11.glPopMatrix();
/* 1059 */         drawHitBoxDetail(bb);
/* 1060 */         GL11.glPopMatrix();
/*      */       } 
/*      */       
/* 1063 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void drawHitBoxDetail(MCH_BoundingBox bb) {
/* 1068 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1069 */     float f1 = 0.080000006F;
/* 1070 */     String s = String.format("%.2f", new Object[] { Float.valueOf(bb.damegeFactor) });
/* 1071 */     GL11.glPushMatrix();
/* 1072 */     GL11.glTranslatef(0.0F, 0.5F + (float)(bb.offsetY * 0.0D + bb.height), 0.0F);
/* 1073 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 1074 */     GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 1075 */     GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 1076 */     GL11.glScalef(-f1, -f1, f1);
/* 1077 */     GL11.glDisable(2896);
/* 1078 */     GL11.glEnable(3042);
/* 1079 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 1080 */     GL11.glDisable(3553);
/* 1081 */     FontRenderer fontrenderer = getFontRendererFromRenderManager();
/* 1082 */     Tessellator tessellator = Tessellator.getInstance();
/* 1083 */     BufferBuilder builder = tessellator.getBuffer();
/* 1084 */     builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 1085 */     int i = fontrenderer.getStringWidth(s) / 2;
/* 1086 */     builder.pos((-i - 1), -1.0D, 0.1D).color(0.0F, 0.0F, 0.0F, 0.4F).endVertex();
/* 1087 */     builder.pos((-i - 1), 8.0D, 0.1D).color(0.0F, 0.0F, 0.0F, 0.4F).endVertex();
/* 1088 */     builder.pos((i + 1), 8.0D, 0.1D).color(0.0F, 0.0F, 0.0F, 0.4F).endVertex();
/* 1089 */     builder.pos((i + 1), -1.0D, 0.1D).color(0.0F, 0.0F, 0.0F, 0.4F).endVertex();
/* 1090 */     tessellator.draw();
/* 1091 */     GL11.glEnable(3553);
/* 1092 */     GL11.glDepthMask(false);
/* 1093 */     int color = (bb.damegeFactor > 1.0F) ? 16711680 : ((bb.damegeFactor < 1.0F) ? 65535 : 16777215);
/* 1094 */     fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 0xC0000000 | color);
/* 1095 */     GL11.glDepthMask(true);
/* 1096 */     GL11.glEnable(2896);
/* 1097 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1098 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public void renderDebugPilotSeat(MCH_EntityAircraft e, double x, double y, double z, float yaw, float pitch, float roll) {
/* 1102 */     if (MCH_Config.TestMode.prmBool && debugModel != null) {
/* 1103 */       GL11.glPushMatrix();
/* 1104 */       MCH_SeatInfo seat = e.getSeatInfo(0);
/* 1105 */       GL11.glTranslated(x, y, z);
/* 1106 */       GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/* 1107 */       GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/* 1108 */       GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
/* 1109 */       GL11.glTranslated(seat.pos.x, seat.pos.y, seat.pos.z);
/* 1110 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 1111 */       bindTexture("textures/seat_pilot.png");
/* 1112 */       debugModel.renderAll();
/* 1113 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderCommonPart(MCH_EntityAircraft ac, MCH_AircraftInfo info, double x, double y, double z, float tickTime) {
/* 1118 */     renderRope(ac, info, x, y, z, tickTime);
/* 1119 */     renderWeapon(ac, info, tickTime);
/* 1120 */     renderRotPart(ac, info, tickTime);
/* 1121 */     renderHatch(ac, info, tickTime);
/* 1122 */     renderTrackRoller(ac, info, tickTime);
/* 1123 */     renderCrawlerTrack(ac, info, tickTime);
/* 1124 */     renderSteeringWheel(ac, info, tickTime);
/* 1125 */     renderLightHatch(ac, info, tickTime);
/* 1126 */     renderWheel(ac, info, tickTime);
/* 1127 */     renderThrottle(ac, info, tickTime);
/* 1128 */     renderCamera(ac, info, tickTime);
/* 1129 */     renderLandingGear(ac, info, tickTime);
/* 1130 */     renderWeaponBay(ac, info, tickTime);
/* 1131 */     renderCanopy(ac, info, tickTime);
/*      */   }
/*      */   
/*      */   public abstract void renderAircraft(MCH_EntityAircraft paramMCH_EntityAircraft, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*      */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_RenderAircraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */