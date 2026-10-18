/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_EntityRenderer;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_Camera
/*     */ {
/*     */   public static final int MODE_NORMAL = 0;
/*     */   public static final int MODE_NIGHTVISION = 1;
/*     */   public static final int MODE_THERMALVISION = 2;
/*     */   private final World worldObj;
/*     */   public double posX;
/*     */   public double posY;
/*     */   public double posZ;
/*     */   public float rotationYaw;
/*     */   public float rotationPitch;
/*     */   public float prevRotationYaw;
/*     */   public float prevRotationPitch;
/*     */   public float partRotationYaw;
/*     */   public float partRotationPitch;
/*     */   public float prevPartRotationYaw;
/*     */   public float prevPartRotationPitch;
/*     */   private float zoom;
/*     */   private final int[] mode;
/*     */   private final boolean[] canUseShader;
/*     */   private final int[] lastMode;
/*     */   private int lastZoomDir;
/*     */   
/*     */   public MCH_Camera(World w, Entity p) {
/*  36 */     this.worldObj = w;
/*  37 */     this.mode = new int[] { 0, 0 };
/*  38 */     this.zoom = 1.0F;
/*  39 */     this.lastMode = new int[getUserMax()];
/*  40 */     this.lastZoomDir = 0;
/*  41 */     this.canUseShader = new boolean[getUserMax()];
/*     */   }
/*     */   
/*     */   public MCH_Camera(World w, Entity p, double x, double y, double z) {
/*  45 */     this(w, p);
/*  46 */     setPosition(x, y, z);
/*  47 */     setCameraZoom(1.0F);
/*     */   }
/*     */   
/*     */   public int getUserMax() {
/*  51 */     return this.mode.length;
/*     */   }
/*     */   
/*     */   public void initCamera(int uid, @Nullable Entity viewer) {
/*  55 */     setCameraZoom(1.0F);
/*  56 */     setMode(uid, 0);
/*  57 */     updateViewer(uid, viewer);
/*     */   }
/*     */   
/*     */   public void setMode(int uid, int m) {
/*  61 */     if (isValidUid(uid)) {
/*  62 */       this.mode[uid] = (m < 0) ? 0 : (m % getModeNum(uid));
/*  63 */       switch (this.mode[uid]) {
/*     */         case 0:
/*     */         case 1:
/*  66 */           if (this.worldObj.isRemote) {
/*  67 */             W_EntityRenderer.deactivateShader();
/*     */           }
/*     */           break;
/*     */         case 2:
/*  71 */           if (this.worldObj.isRemote)
/*  72 */             W_EntityRenderer.activateShader("pencil"); 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setShaderSupport(int uid, Boolean b) {
/*  79 */     if (isValidUid(uid)) {
/*  80 */       setMode(uid, 0);
/*  81 */       this.canUseShader[uid] = b.booleanValue();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isValidUid(int uid) {
/*  86 */     return (uid >= 0 && uid < getUserMax());
/*     */   }
/*     */   
/*     */   public int getModeNum(int uid) {
/*  90 */     if (!isValidUid(uid)) {
/*  91 */       return 2;
/*     */     }
/*  93 */     return this.canUseShader[uid] ? 3 : 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMode(int uid) {
/*  98 */     return isValidUid(uid) ? this.mode[uid] : 0;
/*     */   }
/*     */   
/*     */   public String getModeName(int uid) {
/* 102 */     if (getMode(uid) == 1) {
/* 103 */       return "NIGHT VISION";
/*     */     }
/* 105 */     return (getMode(uid) == 2) ? "THERMAL VISION" : "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateViewer(int uid, @Nullable Entity viewer) {
/* 110 */     if (isValidUid(uid) && viewer != null) {
/* 111 */       if (W_Lib.isEntityLivingBase(viewer) && !viewer.isDead) {
/* 112 */         if (getMode(uid) == 0 && this.lastMode[uid] != 0) {
/* 113 */           PotionEffect pe = W_Entity.getActivePotionEffect(viewer, MobEffects.NIGHT_VISION);
/* 114 */           if (pe != null && pe.getDuration() > 0 && pe.getDuration() < 500) {
/* 115 */             if (viewer.world.isRemote) {
/* 116 */               W_Entity.removePotionEffectClient(viewer, MobEffects.NIGHT_VISION);
/*     */             } else {
/* 118 */               W_Entity.removePotionEffect(viewer, MobEffects.NIGHT_VISION);
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 123 */         if (getMode(uid) == 1 || getMode(uid) == 2) {
/* 124 */           PotionEffect pe = W_Entity.getActivePotionEffect(viewer, MobEffects.NIGHT_VISION);
/* 125 */           if ((pe == null || pe.getDuration() < 500) && !viewer.world.isRemote) {
/* 126 */             W_Entity.addPotionEffect(viewer, new PotionEffect(MobEffects.NIGHT_VISION, 250, 0, true, false));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 131 */       this.lastMode[uid] = getMode(uid);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setPosition(double x, double y, double z) {
/* 136 */     this.posX = x;
/* 137 */     this.posY = y;
/* 138 */     this.posZ = z;
/*     */   }
/*     */   
/*     */   public float getCameraZoom() {
/* 142 */     return this.zoom;
/*     */   }
/*     */   
/*     */   public void setCameraZoom(float z) {
/* 146 */     float prevZoom = this.zoom;
/* 147 */     this.zoom = Math.max(z, 1.0F);
/* 148 */     this.lastZoomDir = Float.compare(this.zoom, prevZoom);
/*     */   }
/*     */   
/*     */   public int getLastZoomDir() {
/* 152 */     return this.lastZoomDir;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_Camera.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */