/*     */ package com.norwood.mcheli.gui;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Key;
/*     */ import com.norwood.mcheli.wrapper.W_GuiButton;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCH_GuiSliderVertical
/*     */   extends W_GuiButton
/*     */ {
/*     */   public final float valueMin;
/*     */   public float valueMax;
/*     */   public final float valueStep;
/*     */   private float currentSlider;
/*     */   private boolean isMousePress;
/*     */   
/*     */   public MCH_GuiSliderVertical(int gui_id, int posX, int posY, int sliderWidth, int sliderHeight, String string, float defaultSliderPos, float minVal, float maxVal, float step) {
/*  22 */     super(gui_id, posX, posY, sliderWidth, sliderHeight, string);
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
/*     */   public void scrollUp(float a) {
/*  34 */     if (isVisible() && !this.isMousePress) {
/*  35 */       setSliderValue(getSliderValue() + this.valueStep * a);
/*     */     }
/*     */   }
/*     */   
/*     */   public void scrollDown(float a) {
/*  40 */     if (isVisible() && !this.isMousePress) {
/*  41 */       setSliderValue(getSliderValue() - this.valueStep * a);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void func_146119_b(@NotNull Minecraft mc, int x, int y) {
/*  46 */     if (isVisible()) {
/*  47 */       if (this.isMousePress) {
/*  48 */         this.currentSlider = (y - this.field_146129_i + 4) / (this.field_146121_g - 8);
/*  49 */         if (this.currentSlider < 0.0F) {
/*  50 */           this.currentSlider = 0.0F;
/*     */         }
/*     */         
/*  53 */         if (this.currentSlider > 1.0F) {
/*  54 */           this.currentSlider = 1.0F;
/*     */         }
/*     */         
/*  57 */         this.currentSlider = normalizeValue(denormalizeValue(this.currentSlider));
/*     */       } 
/*     */       
/*  60 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  61 */       func_73729_b(this.field_146128_h, this.field_146129_i + (int)(this.currentSlider * (this.field_146121_g - 8)), 66, 0, 20, 4);
/*  62 */       func_73729_b(this.field_146128_h, this.field_146129_i + (int)(this.currentSlider * (this.field_146121_g - 8)) + 4, 66, 196, 20, 4);
/*  63 */       if (!MCH_Key.isKeyDown(-100)) {
/*  64 */         func_146118_a(x, y);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getSliderValue() {
/*  70 */     return denormalizeValue(this.currentSlider);
/*     */   }
/*     */   
/*     */   public void setSliderValue(float f) {
/*  74 */     this.currentSlider = normalizeValue(f);
/*     */   }
/*     */   
/*     */   public float getSliderValueInt(int digit) {
/*  78 */     int d = 1;
/*     */     
/*  80 */     while (digit > 0) {
/*  81 */       d *= 10;
/*  82 */       digit--;
/*     */     } 
/*     */     
/*  85 */     int n = (int)(denormalizeValue(this.currentSlider) * d);
/*  86 */     return n / d;
/*     */   }
/*     */   
/*     */   public float normalizeValue(float f) {
/*  90 */     return MathHelper.func_76131_a((snapToStepClamp(f) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public float denormalizeValue(float f) {
/*  94 */     return snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.func_76131_a(f, 0.0F, 1.0F));
/*     */   }
/*     */   
/*     */   public float snapToStepClamp(float f) {
/*  98 */     f = snapToStep(f);
/*  99 */     return MathHelper.func_76131_a(f, this.valueMin, this.valueMax);
/*     */   }
/*     */   
/*     */   protected float snapToStep(float f) {
/* 103 */     if (this.valueStep > 0.0F) {
/* 104 */       f = this.valueStep * Math.round(f / this.valueStep);
/*     */     }
/*     */     
/* 107 */     return f;
/*     */   }
/*     */   
/*     */   public boolean func_146116_c(@NotNull Minecraft mc, int x, int y) {
/* 111 */     if (super.func_146116_c(mc, x, y)) {
/* 112 */       this.currentSlider = (y - this.field_146129_i + 4) / (this.field_146121_g - 8);
/* 113 */       if (this.currentSlider < 0.0F) {
/* 114 */         this.currentSlider = 0.0F;
/*     */       }
/*     */       
/* 117 */       if (this.currentSlider > 1.0F) {
/* 118 */         this.currentSlider = 1.0F;
/*     */       }
/*     */       
/* 121 */       this.isMousePress = true;
/* 122 */       return true;
/*     */     } 
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146118_a(int mouseX, int mouseY) {
/* 129 */     this.isMousePress = false;
/*     */   }
/*     */   
/*     */   public void func_191745_a(@NotNull Minecraft mc, int x, int y, float partialTicks) {
/* 133 */     if (isVisible()) {
/* 134 */       FontRenderer fontrenderer = mc.field_71466_p;
/* 135 */       mc.func_110434_K().func_110577_a(new ResourceLocation("mcheli", "textures/gui/widgets.png"));
/* 136 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 137 */       setOnMouseOver((x >= this.field_146128_h && y >= this.field_146129_i && x < this.field_146128_h + this.field_146120_f && y < this.field_146129_i + this.field_146121_g));
/* 138 */       int k = func_146114_a(isOnMouseOver());
/* 139 */       enableBlend();
/* 140 */       func_73729_b(this.field_146128_h, this.field_146129_i, 46 + k * 20, 0, this.field_146120_f, this.field_146121_g / 2);
/* 141 */       func_73729_b(this.field_146128_h, this.field_146129_i + this.field_146121_g / 2, 46 + k * 20, 200 - this.field_146121_g / 2, this.field_146120_f, this.field_146121_g / 2);
/* 142 */       func_146119_b(mc, x, y);
/* 143 */       int l = 14737632;
/* 144 */       if (this.packedFGColour != 0) {
/* 145 */         l = this.packedFGColour;
/* 146 */       } else if (!this.field_146124_l) {
/* 147 */         l = 10526880;
/* 148 */       } else if (isOnMouseOver()) {
/* 149 */         l = 16777120;
/*     */       } 
/*     */       
/* 152 */       func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, l);
/* 153 */       mc.func_110434_K().func_110577_a(field_146122_a);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gui\MCH_GuiSliderVertical.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */