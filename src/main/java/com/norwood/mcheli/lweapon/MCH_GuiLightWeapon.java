/*     */ package com.norwood.mcheli.lweapon;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_KeyName;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.gui.MCH_Gui;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponGuidanceSystem;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiLightWeapon
/*     */   extends MCH_Gui
/*     */ {
/*     */   public MCH_GuiLightWeapon(Minecraft minecraft) {
/*  25 */     super(minecraft);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  30 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  35 */     if (MCH_ItemLightWeaponBase.isHeld(player)) {
/*  36 */       Entity re = player.func_184187_bx();
/*  37 */       return (!(re instanceof com.norwood.mcheli.aircraft.MCH_EntityAircraft) && !(re instanceof com.norwood.mcheli.gltd.MCH_EntityGLTD));
/*     */     } 
/*     */     
/*  40 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  45 */     if (!isThirdPersonView) {
/*  46 */       GL11.glLineWidth(scaleFactor);
/*  47 */       if (isDrawGui(player)) {
/*  48 */         MCH_WeaponGuidanceSystem gs = MCH_ClientLightWeaponTickHandler.gs;
/*  49 */         if (gs != null && MCH_ClientLightWeaponTickHandler.weapon != null && MCH_ClientLightWeaponTickHandler.weapon.getInfo() != null) {
/*  50 */           PotionEffect pe = player.func_70660_b(MobEffects.field_76439_r);
/*  51 */           if (pe != null) {
/*  52 */             drawNightVisionNoise();
/*     */           }
/*     */           
/*  55 */           GL11.glEnable(3042);
/*  56 */           GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/*  57 */           int srcBlend = GL11.glGetInteger(3041);
/*  58 */           int dstBlend = GL11.glGetInteger(3040);
/*  59 */           GL11.glBlendFunc(770, 771);
/*  60 */           double dist = 0.0D;
/*  61 */           if (gs.getTargetEntity() != null) {
/*  62 */             double dx = (gs.getTargetEntity()).field_70165_t - player.field_70165_t;
/*  63 */             double dz = (gs.getTargetEntity()).field_70161_v - player.field_70161_v;
/*  64 */             dist = Math.sqrt(dx * dx + dz * dz);
/*     */           } 
/*     */           
/*  67 */           boolean canFire = (MCH_ClientLightWeaponTickHandler.weaponMode == 0 || dist >= 40.0D || gs.getLockCount() <= 0);
/*  68 */           if ("fgm148".equalsIgnoreCase(MCH_ItemLightWeaponBase.getName(player.func_184614_ca()))) {
/*  69 */             drawGuiFGM148(player, gs, canFire, player.func_184614_ca());
/*  70 */             drawKeyBind(-805306369, true);
/*     */           } else {
/*  72 */             GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  73 */             W_McClient.MOD_bindTexture("textures/gui/stinger.png");
/*  74 */             double size = 512.0D;
/*     */             
/*  76 */             while (size < this.field_146294_l || size < this.field_146295_m) {
/*  77 */               size *= 2.0D;
/*     */             }
/*     */             
/*  80 */             drawTexturedModalRectRotate(-(size - this.field_146294_l) / 2.0D, -(size - this.field_146295_m) / 2.0D - 20.0D, size, size, 0.0D, 0.0D, 256.0D, 256.0D, 0.0F);
/*  81 */             drawKeyBind(-805306369, false);
/*     */           } 
/*     */           
/*  84 */           GL11.glBlendFunc(srcBlend, dstBlend);
/*  85 */           GL11.glDisable(3042);
/*  86 */           drawLock(-14101432, -2161656, gs.getLockCount(), gs.getLockCountMax());
/*  87 */           drawRange(player, gs, canFire, -14101432, -2161656);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawNightVisionNoise() {
/*  94 */     GL11.glEnable(3042);
/*  95 */     GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.3F);
/*  96 */     int srcBlend = GL11.glGetInteger(3041);
/*  97 */     int dstBlend = GL11.glGetInteger(3040);
/*  98 */     GL11.glBlendFunc(1, 1);
/*  99 */     W_McClient.MOD_bindTexture("textures/gui/alpha.png");
/* 100 */     drawTexturedModalRectRotate(0.0D, 0.0D, this.field_146294_l, this.field_146295_m, this.rand.nextInt(256), this.rand.nextInt(256), 256.0D, 256.0D, 0.0F);
/* 101 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 102 */     GL11.glDisable(3042);
/*     */   }
/*     */   
/*     */   void drawLock(int color, int colorLock, int cntLock, int cntMax) {
/* 106 */     int posX = this.centerX;
/* 107 */     int posY = this.centerY + 20;
/* 108 */     func_73734_a(posX - 20, posY + 20 + 1, posX - 20 + 40, posY + 20 + 1 + 1 + 3 + 1, color);
/* 109 */     float lock = cntLock / cntMax;
/* 110 */     func_73734_a(posX - 20 + 1, posY + 20 + 1 + 1, posX - 20 + 1 + (int)(38.0D * lock), posY + 20 + 1 + 1 + 3, -2161656);
/*     */   }
/*     */   
/*     */   void drawRange(EntityPlayer player, MCH_WeaponGuidanceSystem gs, boolean canFire, int color1, int color2) {
/* 114 */     String msgLockDist = "[--.--]";
/* 115 */     int color = color2;
/* 116 */     if (gs.getLockCount() > 0) {
/* 117 */       Entity target = gs.getLockingEntity();
/* 118 */       if (target != null) {
/* 119 */         double dx = target.field_70165_t - player.field_70165_t;
/* 120 */         double dz = target.field_70161_v - player.field_70161_v;
/* 121 */         msgLockDist = String.format("[%.2f]", new Object[] { Double.valueOf(Math.sqrt(dx * dx + dz * dz)) });
/* 122 */         color = canFire ? color1 : color2;
/* 123 */         if (!MCH_Config.HideKeybind.prmBool && gs.isLockComplete()) {
/* 124 */           String k = MCH_KeyName.getDescOrName(MCH_Config.KeyAttack.prmInt);
/* 125 */           drawCenteredString("Shot : " + k, this.centerX, this.centerY + 65, -805306369);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     drawCenteredString(msgLockDist, this.centerX, this.centerY + 50, color);
/*     */   }
/*     */   void drawGuiFGM148(EntityPlayer player, MCH_WeaponGuidanceSystem gs, boolean canFire, ItemStack itemStack) {
/*     */     double y, h;
/* 134 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 135 */     double fac = Math.min(this.field_146294_l / 800.0D, this.field_146295_m / 700.0D);
/* 136 */     int size = (int)(1024.0D * fac);
/* 137 */     size = size / 64 * 64;
/* 138 */     fac = size / 1024.0D;
/* 139 */     double left = -(size - this.field_146294_l) / 2.0D;
/* 140 */     double top = -(size - this.field_146295_m) / 2.0D - 20.0D;
/* 141 */     double right = left + size;
/* 142 */     double bottom = top + size;
/* 143 */     Vec3d pos = MCH_ClientLightWeaponTickHandler.getMartEntityPos();
/* 144 */     if (gs.getLockCount() > 0) {
/* 145 */       int scale = (scaleFactor > 0) ? scaleFactor : 2;
/* 146 */       if (pos == null) {
/* 147 */         pos = new Vec3d(this.field_146294_l / 2.0D * scale, this.field_146295_m / 2.0D * scale, 0.0D);
/*     */       }
/*     */       
/* 150 */       double IX = 280.0D * fac;
/* 151 */       double IY = 370.0D * fac;
/* 152 */       double cx = pos.field_72450_a / scale;
/* 153 */       double cy = this.field_146295_m - pos.field_72448_b / scale;
/* 154 */       double sx = MCH_Lib.RNG(cx, left + IX, right - IX);
/* 155 */       double sy = MCH_Lib.RNG(cy, top + IY, bottom - IY);
/* 156 */       if (gs.getLockCount() >= gs.getLockCountMax() / 2) {
/* 157 */         drawLine(new double[] { -1.0D, sy, (this.field_146294_l + 1), sy, sx, -1.0D, sx, (this.field_146295_m + 1) }, -1593835521);
/*     */       }
/*     */       
/* 160 */       if (player.field_70173_aa % 6 >= 3) {
/* 161 */         pos = MCH_ClientLightWeaponTickHandler.getMartEntityBBPos();
/* 162 */         if (pos == null) {
/* 163 */           pos = new Vec3d((this.field_146294_l / 2.0D - 65.0D) * scale, (this.field_146295_m / 2.0D + 50.0D) * scale, 0.0D);
/*     */         }
/*     */         
/* 166 */         double bx = pos.field_72450_a / scale;
/* 167 */         double by = this.field_146295_m - pos.field_72448_b / scale;
/* 168 */         double dx = Math.abs(cx - bx);
/* 169 */         double dy = Math.abs(cy - by);
/* 170 */         double p = 1.0D - gs.getLockCount() / gs.getLockCountMax();
/* 171 */         dx = MCH_Lib.RNG(dx, 25.0D, 70.0D);
/* 172 */         dy = MCH_Lib.RNG(dy, 15.0D, 70.0D);
/* 173 */         dx += (70.0D - dx) * p;
/* 174 */         dy += (70.0D - dy) * p;
/* 175 */         int lx = 10;
/* 176 */         int ly = 6;
/* 177 */         drawLine(new double[] { sx - dx, sy - dy + ly, sx - dx, sy - dy, sx - dx + lx, sy - dy }, -1593835521, 3);
/* 178 */         drawLine(new double[] { sx + dx, sy - dy + ly, sx + dx, sy - dy, sx + dx - lx, sy - dy }, -1593835521, 3);
/* 179 */         dy /= 6.0D;
/* 180 */         drawLine(new double[] { sx - dx, sy + dy - ly, sx - dx, sy + dy, sx - dx + lx, sy + dy }, -1593835521, 3);
/* 181 */         drawLine(new double[] { sx + dx, sy + dy - ly, sx + dx, sy + dy, sx + dx - lx, sy + dy }, -1593835521, 3);
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     func_73734_a(-1, -1, (int)left + 1, this.field_146295_m + 1, -16777216);
/* 186 */     func_73734_a((int)right - 1, -1, this.field_146294_l + 1, this.field_146295_m + 1, -16777216);
/* 187 */     func_73734_a(-1, -1, this.field_146294_l + 1, (int)top + 1, -16777216);
/* 188 */     func_73734_a(-1, (int)bottom - 1, this.field_146294_l + 1, this.field_146295_m + 1, -16777216);
/* 189 */     GL11.glEnable(3042);
/* 190 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 191 */     W_McClient.MOD_bindTexture("textures/gui/javelin.png");
/* 192 */     drawTexturedModalRectRotate(left, top, size, size, 0.0D, 0.0D, 256.0D, 256.0D, 0.0F);
/* 193 */     W_McClient.MOD_bindTexture("textures/gui/javelin2.png");
/* 194 */     PotionEffect pe = player.func_70660_b(MobEffects.field_76439_r);
/* 195 */     if (pe == null) {
/* 196 */       double d1 = 247.0D;
/* 197 */       y = 211.0D;
/* 198 */       double d2 = 380.0D;
/* 199 */       h = 350.0D;
/* 200 */       drawTexturedRect(left + d1 * fac, top + y * fac, (d2 - d1) * fac, (h - y) * fac, d1, y, d2 - d1, h - y, 1024.0D, 1024.0D);
/*     */     } 
/*     */     
/* 203 */     if (player.func_184612_cw() <= 60) {
/* 204 */       double d1 = 130.0D;
/* 205 */       y = 334.0D;
/* 206 */       double d2 = 257.0D;
/* 207 */       h = 455.0D;
/* 208 */       drawTexturedRect(left + d1 * fac, top + y * fac, (d2 - d1) * fac, (h - y) * fac, d1, y, d2 - d1, h - y, 1024.0D, 1024.0D);
/*     */     } 
/*     */     
/* 211 */     if (MCH_ClientLightWeaponTickHandler.selectedZoom == 0) {
/* 212 */       double d1 = 387.0D;
/* 213 */       y = 211.0D;
/* 214 */       double d2 = 510.0D;
/* 215 */       h = 350.0D;
/* 216 */       drawTexturedRect(left + d1 * fac, top + y * fac, (d2 - d1) * fac, (h - y) * fac, d1, y, d2 - d1, h - y, 1024.0D, 1024.0D);
/*     */     } 
/*     */     
/* 219 */     if (MCH_ClientLightWeaponTickHandler.selectedZoom == (MCH_ClientLightWeaponTickHandler.weapon.getInfo()).zoom.length - 1) {
/* 220 */       double d1 = 511.0D;
/* 221 */       y = 211.0D;
/* 222 */       double d2 = 645.0D;
/* 223 */       h = 350.0D;
/* 224 */       drawTexturedRect(left + d1 * fac, top + y * fac, (d2 - d1) * fac, (h - y) * fac, d1, y, d2 - d1, h - y, 1024.0D, 1024.0D);
/*     */     } 
/*     */     
/* 227 */     if (gs.getLockCount() > 0) {
/* 228 */       double d1 = 643.0D;
/* 229 */       y = 211.0D;
/* 230 */       double d2 = 775.0D;
/* 231 */       h = 350.0D;
/* 232 */       drawTexturedRect(left + d1 * fac, top + y * fac, (d2 - d1) * fac, (h - y) * fac, d1, y, d2 - d1, h - y, 1024.0D, 1024.0D);
/*     */     } 
/*     */     
/* 235 */     double x = 768.0D;
/*     */     
/* 237 */     double w = 890.0D;
/*     */     
/* 239 */     if (MCH_ClientLightWeaponTickHandler.weaponMode == 1) {
/* 240 */       y = 340.0D;
/* 241 */       h = 455.0D;
/*     */     } else {
/* 243 */       y = 456.0D;
/* 244 */       h = 565.0D;
/*     */     } 
/* 246 */     drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
/*     */     
/* 248 */     if (!canFire) {
/* 249 */       double var53 = 379.0D;
/* 250 */       double var64 = 670.0D;
/* 251 */       double var75 = 511.0D;
/* 252 */       double var86 = 810.0D;
/* 253 */       drawTexturedRect(left + var53 * fac, top + var64 * fac, (var75 - var53) * fac, (var86 - var64) * fac, var53, var64, var75 - var53, var86 - var64, 1024.0D, 1024.0D);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 258 */     if (itemStack.func_77960_j() >= itemStack.func_77958_k()) {
/* 259 */       double var54 = 512.0D;
/* 260 */       double var65 = 670.0D;
/* 261 */       double var76 = 645.0D;
/* 262 */       double var87 = 810.0D;
/* 263 */       drawTexturedRect(left + var54 * fac, top + var65 * fac, (var76 - var54) * fac, (var87 - var65) * fac, var54, var65, var76 - var54, var87 - var65, 1024.0D, 1024.0D);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 268 */     if (gs.getLockCount() < gs.getLockCountMax()) {
/* 269 */       double var55 = 646.0D;
/* 270 */       double var66 = 670.0D;
/* 271 */       double var77 = 776.0D;
/* 272 */       double var88 = 810.0D;
/* 273 */       drawTexturedRect(left + var55 * fac, top + var66 * fac, (var77 - var55) * fac, (var88 - var66) * fac, var55, var66, var77 - var55, var88 - var66, 1024.0D, 1024.0D);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 278 */     if (pe != null) {
/* 279 */       double var56 = 768.0D;
/* 280 */       double var67 = 562.0D;
/* 281 */       double var78 = 890.0D;
/* 282 */       double var89 = 694.0D;
/* 283 */       drawTexturedRect(left + var56 * fac, top + var67 * fac, (var78 - var56) * fac, (var89 - var67) * fac, var56, var67, var78 - var56, var89 - var67, 1024.0D, 1024.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawKeyBind(int color, boolean canSwitchMode) {
/* 290 */     int OffX = this.centerX + 55;
/* 291 */     int OffY = this.centerY + 40;
/* 292 */     drawString("CAM MODE :", OffX, OffY + 10, color);
/* 293 */     drawString("ZOOM      :", OffX, OffY + 20, color);
/* 294 */     if (canSwitchMode) {
/* 295 */       drawString("MODE      :", OffX, OffY + 30, color);
/*     */     }
/*     */     
/* 298 */     OffX += 60;
/* 299 */     drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt), OffX, OffY + 10, color);
/* 300 */     drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt), OffX, OffY + 20, color);
/* 301 */     if (canSwitchMode)
/* 302 */       drawString(MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt), OffX, OffY + 30, color); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\lweapon\MCH_GuiLightWeapon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */