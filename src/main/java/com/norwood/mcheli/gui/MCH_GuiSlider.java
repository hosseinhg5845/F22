/*     */ package com.norwood.mcheli.gui;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Key;
/*     */ import com.norwood.mcheli.wrapper.W_GuiButton;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCH_GuiSlider
/*     */   extends W_GuiButton
/*     */ {
/*     */   public final String stringFormat;
/*     */   public final float valueMin;
/*     */   public final float valueMax;
/*     */   public final float valueStep;
/*     */   private float currentSlider;
/*     */   private boolean isMousePress;
/*     */   
/*     */   public MCH_GuiSlider(int gui_id, int posX, int posY, int sliderWidth, int sliderHeight, String string_format, float defaultSliderPos, float minVal, float maxVal, float step) {
/*  21 */     super(gui_id, posX, posY, sliderWidth, sliderHeight, "");
/*  22 */     this.stringFormat = string_format;
/*  23 */     this.valueMin = minVal;
/*  24 */     this.valueMax = maxVal;
/*  25 */     this.valueStep = step;
/*  26 */     setSliderValue(defaultSliderPos);
/*     */   }
/*     */   
/*     */   public int func_146114_a(boolean mouseOver) {
/*  30 */     return 0;
/*     */   }
/*     */   
/*     */   protected void func_146119_b(@NotNull Minecraft mc, int x, int y) {
/*  34 */     if (isVisible()) {
/*  35 */       if (this.isMousePress) {
/*  36 */         this.currentSlider = (x - this.field_146128_h + 4) / (this.field_146120_f - 8);
/*  37 */         if (this.currentSlider < 0.0F) {
/*  38 */           this.currentSlider = 0.0F;
/*     */         }
/*     */         
/*  41 */         if (this.currentSlider > 1.0F) {
/*  42 */           this.currentSlider = 1.0F;
/*     */         }
/*     */         
/*  45 */         this.currentSlider = normalizeValue(denormalizeValue(this.currentSlider));
/*  46 */         updateDisplayString();
/*     */       } 
/*     */       
/*  49 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  50 */       func_73729_b(this.field_146128_h + (int)(this.currentSlider * (this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
/*  51 */       func_73729_b(this.field_146128_h + (int)(this.currentSlider * (this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
/*  52 */       if (!MCH_Key.isKeyDown(-100)) {
/*  53 */         func_146118_a(x, y);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateDisplayString() {
/*  59 */     this.field_146126_j = String.format(this.stringFormat, new Object[] { Float.valueOf(denormalizeValue(this.currentSlider)) });
/*     */   }
/*     */   
/*     */   public float getSliderValue() {
/*  63 */     return denormalizeValue(this.currentSlider);
/*     */   }
/*     */   
/*     */   public void setSliderValue(float f) {
/*  67 */     this.currentSlider = normalizeValue(f);
/*  68 */     updateDisplayString();
/*     */   }
/*     */   
/*     */   public float getSliderValueInt(int digit) {
/*  72 */     int d = 1;
/*     */     
/*  74 */     while (digit > 0) {
/*  75 */       d *= 10;
/*  76 */       digit--;
/*     */     } 
/*     */     
/*  79 */     int n = (int)(denormalizeValue(this.currentSlider) * d);
/*  80 */     return n / d;
/*     */   }
/*     */   
/*     */   public float normalizeValue(float f) {
/*  84 */     return MathHelper.func_76131_a((snapToStepClamp(f) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public float denormalizeValue(float f) {
/*  88 */     return snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.func_76131_a(f, 0.0F, 1.0F));
/*     */   }
/*     */   
/*     */   public float snapToStepClamp(float f) {
/*  92 */     f = snapToStep(f);
/*  93 */     return MathHelper.func_76131_a(f, this.valueMin, this.valueMax);
/*     */   }
/*     */   
/*     */   protected float snapToStep(float f) {
/*  97 */     if (this.valueStep > 0.0F) {
/*  98 */       f = this.valueStep * Math.round(f / this.valueStep);
/*     */     }
/*     */     
/* 101 */     return f;
/*     */   }
/*     */   
/*     */   public boolean func_146116_c(@NotNull Minecraft mc, int x, int y) {
/* 105 */     if (super.func_146116_c(mc, x, y)) {
/* 106 */       this.currentSlider = (x - this.field_146128_h + 4) / (this.field_146120_f - 8);
/* 107 */       if (this.currentSlider < 0.0F) {
/* 108 */         this.currentSlider = 0.0F;
/*     */       }
/*     */       
/* 111 */       if (this.currentSlider > 1.0F) {
/* 112 */         this.currentSlider = 1.0F;
/*     */       }
/*     */       
/* 115 */       updateDisplayString();
/* 116 */       this.isMousePress = true;
/* 117 */       return true;
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146118_a(int mouseX, int mouseY) {
/* 124 */     this.isMousePress = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gui\MCH_GuiSlider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */