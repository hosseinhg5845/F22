/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ public class MCH_Rotor {
/*    */   public MCH_Blade[] blades;
/*    */   private int numBlade;
/*    */   private int invRot;
/*    */   private boolean isFoldBlade;
/*    */   private boolean isFoldBladeTarget;
/*    */   private final boolean haveFoldBladeFunc;
/*    */   
/*    */   public MCH_Rotor(int numBlade, int invRot, int foldSpeed, float posx, float posy, float posz, float rotx, float roty, float rotz, boolean canFoldBlade) {
/* 12 */     setupBlade(numBlade, invRot, foldSpeed);
/* 13 */     this.isFoldBlade = false;
/* 14 */     this.isFoldBladeTarget = false;
/* 15 */     this.haveFoldBladeFunc = canFoldBlade;
/* 16 */     setPostion(posx, posy, posz);
/* 17 */     setRotation(rotx, roty, rotz);
/*    */   }
/*    */   
/*    */   public MCH_Rotor setPostion(float x, float y, float z) {
/* 21 */     return this;
/*    */   }
/*    */   
/*    */   public MCH_Rotor setRotation(float x, float y, float z) {
/* 25 */     return this;
/*    */   }
/*    */   
/*    */   public int getBladeNum() {
/* 29 */     return this.numBlade;
/*    */   }
/*    */   
/*    */   public void setupBlade(int num, int invRot, int foldSpeed) {
/* 33 */     this.invRot = (invRot > 0) ? invRot : 1;
/* 34 */     this.numBlade = (num > 0) ? num : 1;
/* 35 */     this.blades = new MCH_Blade[this.numBlade];
/*    */     
/* 37 */     for (int i = 0; i < this.numBlade; i++) {
/* 38 */       this.blades[i] = new MCH_Blade((i * this.invRot));
/* 39 */       this.blades[i].setFoldRotation((5 + i * 3)).setFoldSpeed(foldSpeed);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isFoldingOrUnfolding() {
/* 44 */     return (this.isFoldBlade != this.isFoldBladeTarget);
/*    */   }
/*    */   
/*    */   public float getBladeRotaion(int bladeIndex) {
/* 48 */     return (bladeIndex >= this.numBlade) ? 0.0F : this.blades[bladeIndex].getRotation();
/*    */   }
/*    */   
/*    */   public void startUnfold() {
/* 52 */     this.isFoldBladeTarget = false;
/*    */   }
/*    */   
/*    */   public void startFold() {
/* 56 */     if (this.haveFoldBladeFunc) {
/* 57 */       this.isFoldBladeTarget = true;
/*    */     }
/*    */   }
/*    */   
/*    */   public void forceFold() {
/* 62 */     if (this.haveFoldBladeFunc) {
/* 63 */       this.isFoldBladeTarget = true;
/* 64 */       this.isFoldBlade = true;
/*    */       
/* 66 */       for (MCH_Blade b : this.blades) {
/* 67 */         b.forceFold();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void update(float rot) {
/* 73 */     boolean isCmpFoldUnfold = true;
/*    */     
/* 75 */     for (MCH_Blade b : this.blades) {
/* 76 */       b.setPrevRotation(b.getRotation());
/* 77 */       if (!this.isFoldBlade) {
/* 78 */         if (!this.isFoldBladeTarget) {
/* 79 */           b.setRotation(rot + b.getBaseRotation());
/* 80 */           isCmpFoldUnfold = false;
/* 81 */         } else if (!b.folding()) {
/* 82 */           isCmpFoldUnfold = false;
/*    */         } 
/* 84 */       } else if (this.isFoldBladeTarget) {
/* 85 */         isCmpFoldUnfold = false;
/* 86 */       } else if (!b.unfolding(rot)) {
/* 87 */         isCmpFoldUnfold = false;
/*    */       } 
/*    */     } 
/*    */     
/* 91 */     if (isCmpFoldUnfold)
/* 92 */       this.isFoldBlade = this.isFoldBladeTarget; 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_Rotor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */