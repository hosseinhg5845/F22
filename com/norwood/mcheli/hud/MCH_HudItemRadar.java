/*    */ package com.norwood.mcheli.hud;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.MCH_Vector2;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class MCH_HudItemRadar
/*    */   extends MCH_HudItem {
/*    */   private final String rot;
/*    */   private final String left;
/*    */   private final String top;
/*    */   private final String width;
/*    */   private final String height;
/*    */   private final boolean isEntityRadar;
/*    */   
/*    */   public MCH_HudItemRadar(int fileLine, boolean isEntityRadar, String rot, String left, String top, String width, String height) {
/* 17 */     super(fileLine);
/* 18 */     this.isEntityRadar = isEntityRadar;
/* 19 */     this.rot = toFormula(rot);
/* 20 */     this.left = toFormula(left);
/* 21 */     this.top = toFormula(top);
/* 22 */     this.width = toFormula(width);
/* 23 */     this.height = toFormula(height);
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute() {
/* 28 */     if (this.isEntityRadar) {
/* 29 */       if (EntityList != null && !EntityList.isEmpty()) {
/* 30 */         drawEntityList(EntityList, (float)calc(this.rot), centerX + calc(this.left), centerY + calc(this.top), calc(this.width), calc(this.height));
/*    */       }
/* 32 */     } else if (EnemyList != null && !EnemyList.isEmpty()) {
/* 33 */       drawEntityList(EnemyList, (float)calc(this.rot), centerX + calc(this.left), centerY + calc(this.top), calc(this.width), calc(this.height));
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void drawEntityList(ArrayList<MCH_Vector2> src, float r, double left, double top, double w, double h) {
/* 38 */     double w1 = -w / 2.0D;
/* 39 */     double w2 = w / 2.0D;
/* 40 */     double h1 = -h / 2.0D;
/* 41 */     double h2 = h / 2.0D;
/* 42 */     double w_factor = w / 64.0D;
/* 43 */     double h_factor = h / 64.0D;
/* 44 */     double[] list = new double[src.size() * 2];
/* 45 */     int idx = 0;
/*    */     
/* 47 */     for (MCH_Vector2 v : src) {
/* 48 */       list[idx] = v.x / 2.0D * w_factor;
/* 49 */       list[idx + 1] = v.y / 2.0D * h_factor;
/* 50 */       idx += 2;
/*    */     } 
/*    */     
/* 53 */     MCH_Lib.rotatePoints(list, r);
/* 54 */     ArrayList<Double> drawList = new ArrayList<>();
/*    */     
/* 56 */     for (int i = 0; i + 1 < list.length; i += 2) {
/* 57 */       if (list[i] > w1 && list[i] < w2 && list[i + 1] > h1 && list[i + 1] < h2) {
/* 58 */         drawList.add(Double.valueOf(list[i] + left + w / 2.0D));
/* 59 */         drawList.add(Double.valueOf(list[i + 1] + top + h / 2.0D));
/*    */       } 
/*    */     } 
/*    */     
/* 63 */     drawPoints(drawList, colorSetting, scaleFactor * 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudItemRadar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */