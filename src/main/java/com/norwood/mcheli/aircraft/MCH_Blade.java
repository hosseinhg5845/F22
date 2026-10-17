/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ 
/*     */ public class MCH_Blade {
/*     */   private float baseRotation;
/*   7 */   private float rotation = 0.0F;
/*   8 */   private float prevRotation = 0.0F;
/*     */   private float foldSpeed;
/*     */   private float foldRotation;
/*     */   
/*     */   public MCH_Blade(float baseRotation) {
/*  13 */     this.baseRotation = baseRotation;
/*  14 */     this.foldSpeed = 3.0F;
/*  15 */     this.foldRotation = 5.0F;
/*     */   }
/*     */   
/*     */   public float getRotation() {
/*  19 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public void setRotation(float rotation) {
/*  23 */     this.rotation = (float)MCH_Lib.getRotate360(rotation);
/*     */   }
/*     */   
/*     */   public float getPrevRotation() {
/*  27 */     return this.prevRotation;
/*     */   }
/*     */   
/*     */   public void setPrevRotation(float rotation) {
/*  31 */     this.prevRotation = (float)MCH_Lib.getRotate360(rotation);
/*     */   }
/*     */   
/*     */   public float getBaseRotation() {
/*  35 */     return this.baseRotation;
/*     */   }
/*     */   
/*     */   public MCH_Blade setBaseRotation(float rotation) {
/*  39 */     if (rotation >= 0.0D) {
/*  40 */       this.baseRotation = rotation;
/*     */     }
/*     */     
/*  43 */     return this;
/*     */   }
/*     */   
/*     */   public MCH_Blade setFoldSpeed(float speed) {
/*  47 */     if (speed > 0.1D) {
/*  48 */       this.foldSpeed = speed;
/*     */     }
/*     */     
/*  51 */     return this;
/*     */   }
/*     */   
/*     */   public MCH_Blade setFoldRotation(float rotation) {
/*  55 */     if (rotation > this.foldSpeed * 2.0F) {
/*  56 */       this.foldRotation = rotation;
/*     */     }
/*     */     
/*  59 */     return this;
/*     */   }
/*     */   
/*     */   public void forceFold() {
/*  63 */     if (this.rotation > this.foldRotation && this.rotation < 360.0F - this.foldRotation) {
/*  64 */       if (this.rotation < 180.0F) {
/*  65 */         this.rotation = this.foldRotation;
/*     */       } else {
/*  67 */         this.rotation = 360.0F - this.foldRotation;
/*     */       } 
/*     */       
/*  70 */       this.rotation %= 360.0F;
/*  71 */       this.prevRotation = this.rotation;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean folding() {
/*  76 */     boolean isCmpFolding = false;
/*  77 */     if (this.rotation > this.foldRotation && this.rotation < 360.0F - this.foldRotation) {
/*  78 */       if (this.rotation < 180.0F) {
/*  79 */         this.rotation -= this.foldSpeed;
/*     */       } else {
/*  81 */         this.rotation += this.foldSpeed;
/*     */       } 
/*     */       
/*  84 */       this.rotation %= 360.0F;
/*     */     } else {
/*  86 */       isCmpFolding = true;
/*     */     } 
/*     */     
/*  89 */     return isCmpFolding;
/*     */   }
/*     */   
/*     */   public boolean unfolding(float rot) {
/*  93 */     boolean isCmpUnfolding = false;
/*  94 */     float targetRot = (float)MCH_Lib.getRotate360((rot + this.baseRotation));
/*  95 */     float prevRot = this.rotation;
/*  96 */     if (targetRot <= 180.0F) {
/*  97 */       this.rotation = (float)MCH_Lib.getRotate360((this.rotation + this.foldSpeed));
/*  98 */       if (this.rotation >= targetRot && prevRot <= targetRot) {
/*  99 */         this.rotation = targetRot;
/* 100 */         isCmpUnfolding = true;
/*     */       } 
/*     */     } else {
/* 103 */       this.rotation = (float)MCH_Lib.getRotate360((this.rotation - this.foldSpeed));
/* 104 */       if (this.rotation <= targetRot && prevRot >= targetRot) {
/* 105 */         this.rotation = targetRot;
/* 106 */         isCmpUnfolding = true;
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     return isCmpUnfolding;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_Blade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */