/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ 
/*     */ public class MCH_Parts {
/*     */   public final Entity parent;
/*     */   public final EntityDataManager dataManager;
/*     */   public final int shift;
/*     */   public final DataParameter<Integer> dataKey;
/*     */   public final String partName;
/*  15 */   public float prevRotation = 0.0F;
/*  16 */   public float rotation = 0.0F;
/*  17 */   public float rotationMax = 90.0F;
/*  18 */   public float rotationInv = 1.0F;
/*  19 */   public final Sound soundStartSwichOn = new Sound(this);
/*  20 */   public final Sound soundEndSwichOn = new Sound(this);
/*  21 */   public final Sound soundSwitching = new Sound(this);
/*  22 */   public final Sound soundStartSwichOff = new Sound(this);
/*  23 */   public final Sound soundEndSwichOff = new Sound(this);
/*     */   private boolean status = false;
/*     */   
/*     */   public MCH_Parts(Entity parent, int shiftBit, DataParameter<Integer> dataKey, String name) {
/*  27 */     this.parent = parent;
/*  28 */     this.dataManager = parent.getDataManager();
/*  29 */     this.shift = shiftBit;
/*  30 */     this.dataKey = dataKey;
/*  31 */     this.status = ((getDataWatcherValue() & 1 << this.shift) != 0);
/*  32 */     this.partName = name;
/*     */   }
/*     */   
/*     */   public int getDataWatcherValue() {
/*  36 */     return ((Integer)this.dataManager.get(this.dataKey)).intValue();
/*     */   }
/*     */   
/*     */   public void setStatusServer(boolean stat) {
/*  40 */     setStatusServer(stat, true);
/*     */   }
/*     */   
/*     */   public void setStatusServer(boolean stat, boolean playSound) {
/*  44 */     if (!this.parent.world.isRemote && getStatus() != stat) {
/*  45 */       MCH_Lib.DbgLog(false, "setStatusServer(ID=%d %s :%s -> %s)", new Object[] { Integer.valueOf(this.shift), this.partName, getStatus() ? "ON" : "OFF", stat ? "ON" : "OFF" });
/*  46 */       updateDataWatcher(stat);
/*  47 */       playSound(this.soundSwitching);
/*  48 */       if (!stat) {
/*  49 */         playSound(this.soundStartSwichOff);
/*     */       } else {
/*  51 */         playSound(this.soundStartSwichOn);
/*     */       } 
/*     */       
/*  54 */       update();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void updateDataWatcher(boolean stat) {
/*  59 */     int currentStatus = getDataWatcherValue();
/*  60 */     int mask = 1 << this.shift;
/*  61 */     if (!stat) {
/*  62 */       this.dataManager.set(this.dataKey, Integer.valueOf(currentStatus & (mask ^ 0xFFFFFFFF)));
/*     */     } else {
/*  64 */       this.dataManager.set(this.dataKey, Integer.valueOf(currentStatus | mask));
/*     */     } 
/*     */     
/*  67 */     this.status = stat;
/*     */   }
/*     */   
/*     */   public boolean getStatus() {
/*  71 */     return this.status;
/*     */   }
/*     */   
/*     */   public boolean isOFF() {
/*  75 */     return (!this.status && this.rotation <= 0.02F);
/*     */   }
/*     */   
/*     */   public boolean isON() {
/*  79 */     return (this.status && this.rotation >= this.rotationMax - 0.02F);
/*     */   }
/*     */   
/*     */   public void updateStatusClient(int statFromDataWatcher) {
/*  83 */     if (this.parent.world.isRemote) {
/*  84 */       this.status = ((statFromDataWatcher & 1 << this.shift) != 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void update() {
/*  89 */     this.prevRotation = this.rotation;
/*  90 */     if (getStatus()) {
/*  91 */       if (this.rotation < this.rotationMax) {
/*  92 */         this.rotation += this.rotationInv;
/*  93 */         if (this.rotation >= this.rotationMax) {
/*  94 */           playSound(this.soundEndSwichOn);
/*     */         }
/*     */       } 
/*  97 */     } else if (this.rotation > 0.0F) {
/*  98 */       this.rotation -= this.rotationInv;
/*  99 */       if (this.rotation <= 0.0F) {
/* 100 */         playSound(this.soundEndSwichOff);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void forceSwitch(boolean onoff) {
/* 106 */     updateDataWatcher(onoff);
/* 107 */     this.rotation = this.prevRotation = this.rotationMax;
/*     */   }
/*     */   
/*     */   public float getFactor() {
/* 111 */     return (this.rotationMax > 0.0F) ? (this.rotation / this.rotationMax) : 0.0F;
/*     */   }
/*     */   
/*     */   public void playSound(Sound snd) {
/* 115 */     if (!snd.name.isEmpty() && !this.parent.world.isRemote)
/* 116 */       W_WorldFunc.MOD_playSoundAtEntity(this.parent, snd.name, snd.volume, snd.pitch); 
/*     */   }
/*     */   
/*     */   public static class Sound
/*     */   {
/* 121 */     public String name = "";
/* 122 */     public float volume = 1.0F;
/* 123 */     public float pitch = 1.0F;
/*     */ 
/*     */     
/*     */     public Sound(MCH_Parts paramMCH_Parts) {}
/*     */     
/*     */     public void setPrm(String n, float v, float p) {
/* 129 */       this.name = n;
/* 130 */       this.volume = v;
/* 131 */       this.pitch = p;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_Parts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */