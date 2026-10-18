/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class MCH_RenderLib {
/*    */   public static void drawLine(Vec3d[] points, int color) {
/* 11 */     drawLine(points, color, 1, 1);
/*    */   }
/*    */   
/*    */   public static void drawLine(Vec3d[] points, int color, int mode, int width) {
/* 15 */     int prevWidth = GL11.glGetInteger(2849);
/* 16 */     GL11.glDisable(3553);
/* 17 */     GL11.glEnable(3042);
/* 18 */     GL11.glBlendFunc(770, 771);
/* 19 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/* 20 */     GL11.glLineWidth(width);
/* 21 */     Tessellator tessellator = Tessellator.getInstance();
/* 22 */     BufferBuilder builder = tessellator.getBuffer();
/* 23 */     builder.begin(mode, DefaultVertexFormats.POSITION);
/*    */     
/* 25 */     for (Vec3d v : points) {
/* 26 */       builder.pos(v.x, v.y, v.z).endVertex();
/*    */     }
/*    */     
/* 29 */     tessellator.draw();
/* 30 */     GL11.glEnable(3553);
/* 31 */     GL11.glDisable(3042);
/* 32 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 33 */     GL11.glLineWidth(prevWidth);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_RenderLib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */