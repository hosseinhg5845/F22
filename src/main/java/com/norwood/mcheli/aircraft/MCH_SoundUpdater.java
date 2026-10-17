/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.norwood.mcheli.wrapper.W_SoundUpdater;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_SoundUpdater
/*     */   extends W_SoundUpdater
/*     */ {
/*     */   private final MCH_EntityAircraft theAircraft;
/*     */   private final EntityPlayerSP thePlayer;
/*     */   private boolean isMoving;
/*     */   private boolean silent;
/*  20 */   private int soundDelay = 0; private float aircraftPitch; private float aircraftVolume; private float addPitch; private boolean isFirstUpdate; private double prevDist;
/*     */   
/*     */   public MCH_SoundUpdater(Minecraft mc, MCH_EntityAircraft aircraft, EntityPlayerSP entityPlayerSP) {
/*  23 */     super(mc, (Entity)aircraft);
/*  24 */     this.theAircraft = aircraft;
/*  25 */     this.thePlayer = entityPlayerSP;
/*  26 */     this.isFirstUpdate = true;
/*     */   }
/*     */   
/*     */   public void update() {
/*  30 */     if (!this.theAircraft.getSoundName().isEmpty() && this.theAircraft.getAcInfo() != null) {
/*  31 */       if (this.isFirstUpdate) {
/*  32 */         this.isFirstUpdate = false;
/*  33 */         initEntitySound(this.theAircraft.getSoundName());
/*     */       } 
/*     */       
/*  36 */       MCH_AircraftInfo info = this.theAircraft.getAcInfo();
/*  37 */       boolean isBeforeMoving = this.isMoving;
/*  38 */       boolean isDead = this.theAircraft.field_70128_L;
/*  39 */       if (isDead || (!this.silent && this.aircraftVolume == 0.0F)) {
/*  40 */         if (isDead) {
/*  41 */           stopEntitySound((Entity)this.theAircraft);
/*     */         }
/*     */         
/*  44 */         this.silent = true;
/*  45 */         if (isDead) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/*  50 */       boolean isRide = (this.theAircraft.getSeatIdByEntity((Entity)this.thePlayer) >= 0);
/*  51 */       boolean isPlaying = isEntitySoundPlaying((Entity)this.theAircraft);
/*  52 */       if (!isPlaying && this.aircraftVolume > 0.0F) {
/*  53 */         if (this.soundDelay > 0) {
/*  54 */           this.soundDelay--;
/*     */         } else {
/*  56 */           this.soundDelay = 20;
/*  57 */           playEntitySound(this.theAircraft.getSoundName(), (Entity)this.theAircraft, this.aircraftVolume, this.aircraftPitch, true);
/*     */         } 
/*     */         
/*  60 */         this.silent = false;
/*     */       } 
/*     */       
/*  63 */       float prevVolume = this.aircraftVolume;
/*  64 */       float prevPitch = this.aircraftPitch;
/*  65 */       this.isMoving = ((info.soundVolume * this.theAircraft.getSoundVolume()) >= 0.01D);
/*  66 */       if (this.isMoving) {
/*  67 */         this.aircraftVolume = info.soundVolume * this.theAircraft.getSoundVolume();
/*  68 */         this.aircraftPitch = info.soundPitch * this.theAircraft.getSoundPitch();
/*  69 */         if (!isRide) {
/*  70 */           double dist = this.thePlayer.func_70011_f(this.theAircraft.field_70165_t, this.thePlayer.field_70163_u, this.theAircraft.field_70161_v);
/*  71 */           double pitch = this.prevDist - dist;
/*  72 */           if (Math.abs(pitch) > 0.3D) {
/*  73 */             this.addPitch = (float)(this.addPitch + pitch / 40.0D);
/*  74 */             float maxAddPitch = 0.2F;
/*  75 */             if (this.addPitch < -maxAddPitch) {
/*  76 */               this.addPitch = -maxAddPitch;
/*     */             }
/*     */             
/*  79 */             if (this.addPitch > maxAddPitch) {
/*  80 */               this.addPitch = maxAddPitch;
/*     */             }
/*     */           } 
/*     */           
/*  84 */           this.addPitch = (float)(this.addPitch * 0.9D);
/*  85 */           this.aircraftPitch += this.addPitch;
/*  86 */           this.prevDist = dist;
/*     */         } 
/*     */         
/*  89 */         if (this.aircraftPitch < 0.0F) {
/*  90 */           this.aircraftPitch = 0.0F;
/*     */         }
/*  92 */       } else if (isBeforeMoving) {
/*  93 */         this.aircraftVolume = 0.0F;
/*  94 */         this.aircraftPitch = 0.0F;
/*     */       } 
/*     */       
/*  97 */       if (!this.silent) {
/*  98 */         if (this.aircraftPitch != prevPitch) {
/*  99 */           setEntitySoundPitch((Entity)this.theAircraft, this.aircraftPitch);
/*     */         }
/*     */         
/* 102 */         if (this.aircraftVolume != prevVolume) {
/* 103 */           setEntitySoundVolume((Entity)this.theAircraft, this.aircraftVolume);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 108 */       boolean updateLocation = true;
/* 109 */       if (this.aircraftVolume > 0.0F) {
/* 110 */         if (isRide) {
/* 111 */           updateSoundLocation((Entity)this.theAircraft);
/*     */         } else {
/* 113 */           double px = this.thePlayer.field_70165_t;
/* 114 */           double py = this.thePlayer.field_70163_u;
/* 115 */           double pz = this.thePlayer.field_70161_v;
/* 116 */           double dx = this.theAircraft.field_70165_t - px;
/* 117 */           double dy = this.theAircraft.field_70163_u - py;
/* 118 */           double dz = this.theAircraft.field_70161_v - pz;
/* 119 */           double dist = info.soundRange / 16.0D;
/* 120 */           dx /= dist;
/* 121 */           dy /= dist;
/* 122 */           dz /= dist;
/* 123 */           updateSoundLocation(px + dx, py + dy, pz + dz);
/*     */         } 
/* 125 */       } else if (isEntitySoundPlaying((Entity)this.theAircraft)) {
/* 126 */         stopEntitySound((Entity)this.theAircraft);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_SoundUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */