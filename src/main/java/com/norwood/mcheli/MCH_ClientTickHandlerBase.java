/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public abstract class MCH_ClientTickHandlerBase {
/*  13 */   public static float playerRotMinPitch = -90.0F;
/*  14 */   public static float playerRotMaxPitch = 90.0F;
/*     */   public static boolean playerRotLimitPitch = false;
/*  16 */   public static float playerRotMinYaw = -180.0F;
/*  17 */   public static float playerRotMaxYaw = 180.0F;
/*     */   public static boolean playerRotLimitYaw = false;
/*  19 */   private static int mouseWheel = 0;
/*     */   protected final Minecraft mc;
/*     */   
/*     */   public MCH_ClientTickHandlerBase(Minecraft minecraft) {
/*  23 */     this.mc = minecraft;
/*     */   }
/*     */   
/*     */   public static void setRotLimitPitch(float min, float max, Entity player) {
/*  27 */     playerRotMinPitch = min;
/*  28 */     playerRotMaxPitch = max;
/*  29 */     playerRotLimitPitch = true;
/*  30 */     if (player != null) {
/*  31 */       player.rotationPitch = MCH_Lib.RNG(player.rotationPitch, playerRotMinPitch, playerRotMaxPitch);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setRotLimitYaw(float min, float max, Entity e) {
/*  36 */     playerRotMinYaw = min;
/*  37 */     playerRotMaxYaw = max;
/*  38 */     playerRotLimitYaw = true;
/*  39 */     if (e != null) {
/*  40 */       if (e.rotationPitch < playerRotMinPitch) {
/*  41 */         e.rotationPitch = playerRotMinPitch;
/*  42 */         e.prevRotationPitch = playerRotMinPitch;
/*  43 */       } else if (e.rotationPitch > playerRotMaxPitch) {
/*  44 */         e.rotationPitch = playerRotMaxPitch;
/*  45 */         e.prevRotationPitch = playerRotMaxPitch;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void initRotLimit() {
/*  51 */     playerRotMinPitch = -90.0F;
/*  52 */     playerRotMaxPitch = 90.0F;
/*  53 */     playerRotLimitYaw = false;
/*  54 */     playerRotMinYaw = -180.0F;
/*  55 */     playerRotMaxYaw = 180.0F;
/*  56 */     playerRotLimitYaw = false;
/*     */   }
/*     */   
/*     */   public static void applyRotLimit(Entity e) {
/*  60 */     if (e != null) {
/*  61 */       if (playerRotLimitPitch) {
/*  62 */         if (e.rotationPitch < playerRotMinPitch) {
/*  63 */           e.rotationPitch = playerRotMinPitch;
/*  64 */           e.prevRotationPitch = playerRotMinPitch;
/*  65 */         } else if (e.rotationPitch > playerRotMaxPitch) {
/*  66 */           e.rotationPitch = playerRotMaxPitch;
/*  67 */           e.prevRotationPitch = playerRotMaxPitch;
/*     */         } 
/*     */       }
/*     */       
/*  71 */       if (!playerRotLimitYaw);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean updateMouseWheel(int wheel) {
/*  77 */     boolean cancelEvent = false;
/*  78 */     if (wheel != 0 && MCH_Config.SwitchWeaponWithMouseWheel.prmBool) {
/*  79 */       setMouseWheel(0);
/*  80 */       EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).player;
/*  81 */       if (entityPlayerSP != null) {
/*  82 */         MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)entityPlayerSP);
/*  83 */         if (ac != null) {
/*  84 */           int cwid = ac.getWeaponIDBySeatID(ac.getSeatIdByEntity((Entity)entityPlayerSP));
/*  85 */           int nwid = ac.getNextWeaponID((Entity)entityPlayerSP, 1);
/*  86 */           if (cwid != nwid) {
/*  87 */             setMouseWheel(wheel);
/*  88 */             cancelEvent = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return cancelEvent;
/*     */   }
/*     */   
/*     */   public static void playSoundOK() {
/*  98 */     W_McClient.playSoundClick(1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public static void playSoundNG() {
/* 102 */     W_McClient.MOD_playSoundFX("ng", 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public static void playSound(String name) {
/* 106 */     W_McClient.MOD_playSoundFX(name, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public static void playSound(String name, float vol, float pitch) {
/* 110 */     W_McClient.MOD_playSoundFX(name, vol, pitch);
/*     */   }
/*     */   
/*     */   public static int getMouseWheel() {
/* 114 */     return mouseWheel;
/*     */   }
/*     */   
/*     */   public static void setMouseWheel(int mouseWheel) {
/* 118 */     MCH_ClientTickHandlerBase.mouseWheel = mouseWheel;
/*     */   }
/*     */   
/*     */   public abstract void updateKeybind(MCH_Config paramMCH_Config);
/*     */   
/*     */   protected abstract void onTick(boolean paramBoolean);
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_ClientTickHandlerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */