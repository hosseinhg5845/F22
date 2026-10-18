/*    */ package com.norwood.mcheli.debug._v3;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import com.norwood.mcheli.weapon.MCH_WeaponBase;
/*    */ import com.norwood.mcheli.weapon.MCH_WeaponSet;
/*    */ import java.util.Map;
/*    */ import javax.vecmath.Color4f;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class WeaponPointRenderer
/*    */ {
/* 22 */   private static final Color4f[] C = new Color4f[] { new Color4f(1.0F, 0.0F, 0.0F, 1.0F), new Color4f(0.0F, 1.0F, 0.0F, 1.0F), new Color4f(0.0F, 0.0F, 1.0F, 1.0F), new Color4f(1.0F, 1.0F, 0.0F, 1.0F), new Color4f(1.0F, 0.0F, 1.0F, 1.0F), new Color4f(0.0F, 1.0F, 1.0F, 1.0F), new Color4f(0.95686275F, 0.6431373F, 0.3764706F, 1.0F), new Color4f(0.5411765F, 0.16862746F, 0.42477876F, 1.0F) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void renderWeaponPoints(MCH_EntityAircraft ac, MCH_AircraftInfo info, double x, double y, double z) {
/* 34 */     int prevPointSize = GlStateManager.glGetInteger(2833);
/* 35 */     int id = 0;
/* 36 */     int prevFunc = GlStateManager.glGetInteger(2932);
/* 37 */     Map<Vec3d, Integer> poses = Maps.newHashMap();
/* 38 */     GlStateManager.disableTexture2D();
/* 39 */     GlStateManager.enableBlend();
/* 40 */     GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 41 */     GlStateManager.enableLighting();
/* 42 */     GlStateManager.depthMask(false);
/* 43 */     GlStateManager.depthFunc(519);
/* 44 */     GL11.glPointSize(20.0F);
/* 45 */     GlStateManager.pushMatrix();
/* 46 */     GlStateManager.translate(x, y, z);
/*    */     
/* 48 */     for (MCH_AircraftInfo.WeaponSet wsInfo : info.weaponSetList) {
/* 49 */       MCH_WeaponSet ws = ac.getWeaponByName(wsInfo.type);
/* 50 */       if (ws != null) {
/* 51 */         Tessellator tessellator = Tessellator.getInstance();
/* 52 */         BufferBuilder builder = tessellator.getBuffer();
/* 53 */         builder.begin(0, DefaultVertexFormats.POSITION_COLOR);
/*    */         
/* 55 */         for (int i = 0; i < ws.getWeaponNum(); i++) {
/* 56 */           MCH_WeaponBase weapon = ws.getWeapon(i);
/* 57 */           if (weapon != null) {
/* 58 */             int j = 0;
/* 59 */             if (poses.containsKey(weapon.position)) {
/* 60 */               j = ((Integer)poses.get(weapon.position)).intValue();
/* 61 */               j++;
/*    */             } 
/*    */             
/* 64 */             poses.put(weapon.position, Integer.valueOf(j));
/* 65 */             Vec3d vec3d = weapon.getShotPos((Entity)ac);
/* 66 */             Color4f c = C[id % C.length];
/* 67 */             float f = i * 0.1F;
/* 68 */             double d = j * 0.04D;
/* 69 */             builder.pos(vec3d.x, vec3d.y + d, vec3d.z).color(in(c.x + f), in(c.y + f), in(c.z + f), c.w).endVertex();
/*    */           } 
/*    */         } 
/*    */         
/* 73 */         tessellator.draw();
/* 74 */         id++;
/*    */       } 
/*    */     } 
/*    */     
/* 78 */     GlStateManager.popMatrix();
/* 79 */     GL11.glPointSize(prevPointSize);
/* 80 */     GlStateManager.depthFunc(prevFunc);
/* 81 */     GlStateManager.depthMask(true);
/* 82 */     GlStateManager.enableTexture2D();
/* 83 */     GlStateManager.disableBlend();
/*    */   }
/*    */   
/*    */   static float in(float value) {
/* 87 */     return MathHelper.clamp(value, 0.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v3\WeaponPointRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */