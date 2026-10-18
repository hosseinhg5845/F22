/*    */ package com.norwood.mcheli.hud;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_Camera;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class MCH_HudItemCameraRot extends MCH_HudItem {
/*    */   private final String drawPosX;
/*    */   private final String drawPosY;
/*    */   
/*    */   public MCH_HudItemCameraRot(int fileLine, String posx, String posy) {
/* 12 */     super(fileLine);
/* 13 */     this.drawPosX = toFormula(posx);
/* 14 */     this.drawPosY = toFormula(posy);
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute() {
/* 19 */     drawCommonGunnerCamera((Entity)ac, ac.camera, colorSetting, centerX + calc(this.drawPosX), centerY + calc(this.drawPosY));
/*    */   }
/*    */   
/*    */   private void drawCommonGunnerCamera(Entity ac, MCH_Camera camera, int color, double posX, double posY) {
/* 23 */     if (camera != null) {
/* 24 */       double[] line = { posX - 21.0D, posY - 11.0D, posX + 21.0D, posY - 11.0D, posX + 21.0D, posY + 11.0D, posX - 21.0D, posY + 11.0D };
/* 25 */       drawLine(line, color, 2);
/* 26 */       line = new double[] { posX - 21.0D, posY, posX, posY, posX + 21.0D, posY, posX, posY, posX, posY - 11.0D, posX, posY, posX, posY + 11.0D, posX, posY };
/* 27 */       drawLineStipple(line, color, 1, 52428);
/* 28 */       float pitch = camera.rotationPitch;
/* 29 */       if (pitch < -30.0F) {
/* 30 */         pitch = -30.0F;
/*    */       }
/*    */       
/* 33 */       if (pitch > 70.0F) {
/* 34 */         pitch = 70.0F;
/*    */       }
/*    */       
/* 37 */       pitch -= 20.0F;
/* 38 */       pitch = (float)(pitch * 0.16D);
/* 39 */       float yaw = (float)MCH_Lib.getRotateDiff(ac.rotationYaw, camera.rotationYaw);
/* 40 */       yaw *= 2.0F;
/* 41 */       if (yaw < -50.0F) {
/* 42 */         yaw = -50.0F;
/*    */       }
/*    */       
/* 45 */       if (yaw > 50.0F) {
/* 46 */         yaw = 50.0F;
/*    */       }
/*    */       
/* 49 */       yaw = (float)(yaw * 0.34D);
/* 50 */       line = new double[] { posX + yaw - 3.0D, posY + pitch - 2.0D, posX + yaw + 3.0D, posY + pitch - 2.0D, posX + yaw + 3.0D, posY + pitch + 2.0D, posX + yaw - 3.0D, posY + pitch + 2.0D };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 60 */       drawLine(line, color, 2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemCameraRot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */