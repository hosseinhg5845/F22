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
/* 34 */     int prevPointSize = GlStateManager.func_187397_v(2833);
/* 35 */     int id = 0;
/* 36 */     int prevFunc = GlStateManager.func_187397_v(2932);
/* 37 */     Map<Vec3d, Integer> poses = Maps.newHashMap();
/* 38 */     GlStateManager.func_179090_x();
/* 39 */     GlStateManager.func_179147_l();
/* 40 */     GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 41 */     GlStateManager.func_179145_e();
/* 42 */     GlStateManager.func_179132_a(false);
/* 43 */     GlStateManager.func_179143_c(519);
/* 44 */     GL11.glPointSize(20.0F);
/* 45 */     GlStateManager.func_179094_E();
/* 46 */     GlStateManager.func_179137_b(x, y, z);
/*    */     
/* 48 */     for (MCH_AircraftInfo.WeaponSet wsInfo : info.weaponSetList) {
/* 49 */       MCH_WeaponSet ws = ac.getWeaponByName(wsInfo.type);
/* 50 */       if (ws != null) {
/* 51 */         Tessellator tessellator = Tessellator.func_178181_a();
/* 52 */         BufferBuilder builder = tessellator.func_178180_c();
/* 53 */         builder.func_181668_a(0, DefaultVertexFormats.field_181706_f);
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
/* 69 */             builder.func_181662_b(vec3d.field_72450_a, vec3d.field_72448_b + d, vec3d.field_72449_c).func_181666_a(in(c.x + f), in(c.y + f), in(c.z + f), c.w).func_181675_d();
/*    */           } 
/*    */         } 
/*    */         
/* 73 */         tessellator.func_78381_a();
/* 74 */         id++;
/*    */       } 
/*    */     } 
/*    */     
/* 78 */     GlStateManager.func_179121_F();
/* 79 */     GL11.glPointSize(prevPointSize);
/* 80 */     GlStateManager.func_179143_c(prevFunc);
/* 81 */     GlStateManager.func_179132_a(true);
/* 82 */     GlStateManager.func_179098_w();
/* 83 */     GlStateManager.func_179084_k();
/*    */   }
/*    */   
/*    */   static float in(float value) {
/* 87 */     return MathHelper.func_76131_a(value, 0.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v3\WeaponPointRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */