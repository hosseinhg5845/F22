/*     */ package com.norwood.mcheli.hud;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCH_HudItemGraduation extends MCH_HudItem {
/*     */   private final String drawRot;
/*     */   private final String drawRoll;
/*     */   private final String drawPosX;
/*     */   private final String drawPosY;
/*     */   private final int type;
/*     */   
/*     */   public MCH_HudItemGraduation(int fileLine, int type, String rot, String roll, String posx, String posy) {
/*  14 */     super(fileLine);
/*  15 */     this.drawRot = toFormula(rot);
/*  16 */     this.drawRoll = toFormula(roll);
/*  17 */     this.drawPosX = toFormula(posx);
/*  18 */     this.drawPosY = toFormula(posy);
/*  19 */     this.type = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute() {
/*  24 */     GL11.glPushMatrix();
/*  25 */     int x = (int)(centerX + calc(this.drawPosX));
/*  26 */     int y = (int)(centerY + calc(this.drawPosY));
/*  27 */     GL11.glTranslated(x, y, 0.0D);
/*  28 */     GL11.glRotatef((float)calc(this.drawRoll), 0.0F, 0.0F, 1.0F);
/*  29 */     GL11.glTranslated(-x, -y, 0.0D);
/*  30 */     if (this.type == 0) {
/*  31 */       drawCommonGraduationYaw(calc(this.drawRot), colorSetting, x, y);
/*  32 */     } else if (this.type == 1) {
/*  33 */       drawCommonGraduationPitch1(calc(this.drawRot), colorSetting, x, y);
/*  34 */     } else if (this.type == 2 || this.type == 3) {
/*  35 */       drawCommonGraduationPitch2(calc(this.drawRot), colorSetting, x, y);
/*     */     } 
/*     */     
/*  38 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void drawCommonGraduationPitch2(double playerPitch, int color, int posX, int posY) {
/*  42 */     playerPitch = -playerPitch;
/*  43 */     int pitch_n = (int)playerPitch / 5 * 5;
/*  44 */     double[] line = new double[8];
/*  45 */     int start = (this.type == 2) ? 0 : 1;
/*  46 */     int end = (this.type == 2) ? 5 : 4;
/*  47 */     int INT = (this.type == 2) ? 1 : 2;
/*     */     
/*  49 */     for (int i = start; i < end; i++) {
/*  50 */       int pitch = -(-pitch_n - 10 + i * 5);
/*  51 */       double p_rest = playerPitch % 5.0D;
/*  52 */       int x = (pitch != 0) ? 50 : 100;
/*  53 */       int y = posY + (int)((-60 * INT) + p_rest * 6.0D * INT + (i * 30 * INT));
/*  54 */       line[0] = (posX - x);
/*  55 */       line[1] = (y + ((pitch > 0) ? 2 : ((pitch == 0) ? 0 : -2)));
/*  56 */       line[2] = (posX - 50);
/*  57 */       line[3] = y;
/*  58 */       line[4] = (posX + x);
/*  59 */       line[5] = line[1];
/*  60 */       line[6] = (posX + 50);
/*  61 */       line[7] = y;
/*  62 */       drawLine(line, color);
/*  63 */       line[0] = (posX - 50);
/*  64 */       line[1] = y;
/*  65 */       line[2] = (posX - 30);
/*  66 */       line[3] = y;
/*  67 */       line[4] = (posX + 50);
/*  68 */       line[5] = y;
/*  69 */       line[6] = (posX + 30);
/*  70 */       line[7] = y;
/*  71 */       if (pitch >= 0) {
/*  72 */         drawLine(line, color);
/*     */       } else {
/*  74 */         drawLineStipple(line, color, 1, 52428);
/*     */       } 
/*     */       
/*  77 */       if (pitch != 0) {
/*  78 */         drawCenteredString("" + pitch, posX - 50 - 10, y - 4, color);
/*  79 */         drawCenteredString("" + pitch, posX + 50 + 10, y - 4, color);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawCommonGraduationPitch1(double playerPitch, int color, int posX, int posY) {
/*  85 */     int pitch = (int)playerPitch % 360;
/*  86 */     int y = (int)(playerPitch * 10.0D % 10.0D);
/*  87 */     if (y < 0) {
/*  88 */       y += 10;
/*     */     }
/*     */     
/*  91 */     int posX_L = posX - 100;
/*  92 */     int posX_R = posX + 100;
/*  93 */     posY -= 80;
/*  94 */     double[] line = new double[144];
/*  95 */     int p = (playerPitch < 0.0D && y != 0) ? (pitch - 9) : (pitch - 8);
/*     */     
/*  97 */     for (int i = 0; i < line.length / 8; p++) {
/*  98 */       int olx = (p % 3 == 0) ? 15 : 5;
/*  99 */       int ilx = 0;
/* 100 */       line[i * 8] = (posX_L - olx);
/* 101 */       line[i * 8 + 1] = (posY + i * 10 - y);
/* 102 */       line[i * 8 + 2] = (posX_L + ilx);
/* 103 */       line[i * 8 + 3] = (posY + i * 10 - y);
/* 104 */       line[i * 8 + 4] = (posX_R + olx);
/* 105 */       line[i * 8 + 5] = (posY + i * 10 - y);
/* 106 */       line[i * 8 + 6] = (posX_R - ilx);
/* 107 */       line[i * 8 + 7] = (posY + i * 10 - y);
/* 108 */       i++;
/*     */     } 
/*     */     
/* 111 */     drawLine(line, color);
/* 112 */     double[] verticalLine = { (posX_L - 25), (posY - 90), posX_L, (posY - 90), posX_L, (posY + 90), (posX_L - 25), (posY + 90) };
/* 113 */     drawLine(verticalLine, color, 3);
/* 114 */     verticalLine = new double[] { (posX_R + 25), (posY - 90), posX_R, (posY - 90), posX_R, (posY + 90), (posX_R + 25), (posY + 90) };
/* 115 */     drawLine(verticalLine, color, 3);
/*     */   }
/*     */   
/*     */   private void drawCommonGraduationYaw(double playerYaw, int color, int posX, int posY) {
/* 119 */     double yaw = MCH_Lib.getRotate360(playerYaw);
/* 120 */     posX -= 90;
/* 121 */     double[] line = new double[76];
/* 122 */     int x = (int)(yaw * 10.0D) % 10;
/* 123 */     int y = (int)yaw - 9;
/*     */     
/* 125 */     for (int i = 0; i < line.length / 4; y++) {
/* 126 */       int azPosX = posX + i * 10 - x;
/* 127 */       line[i * 4] = azPosX;
/* 128 */       line[i * 4 + 1] = posY;
/* 129 */       line[i * 4 + 2] = azPosX;
/* 130 */       line[i * 4 + 3] = (posY + ((y % 3 == 0) ? 10 : 5));
/* 131 */       if (y % 45 == 0) {
/* 132 */         drawCenteredString(MCH_Lib.getAzimuthStr8(y), azPosX, posY - 10, -65536);
/* 133 */       } else if (y % 3 == 0) {
/* 134 */         int rot = y + 180;
/* 135 */         if (rot < 0) {
/* 136 */           rot += 360;
/*     */         }
/*     */         
/* 139 */         if (rot > 360) {
/* 140 */           rot -= 360;
/*     */         }
/*     */         
/* 143 */         drawCenteredString(String.format("%d", new Object[] { Integer.valueOf(rot) }), azPosX, posY - 10, color);
/*     */       } 
/*     */       
/* 146 */       i++;
/*     */     } 
/*     */     
/* 149 */     drawLine(line, color);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemGraduation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */