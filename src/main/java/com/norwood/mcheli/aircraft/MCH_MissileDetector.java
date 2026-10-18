/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_PacketNotifyLock;
/*    */ import com.norwood.mcheli.weapon.MCH_EntityBaseBullet;
/*    */ import com.norwood.mcheli.wrapper.W_Lib;
/*    */ import com.norwood.mcheli.wrapper.W_McClient;
/*    */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_MissileDetector
/*    */ {
/*    */   public static final int SEARCH_RANGE = 60;
/*    */   private final MCH_EntityAircraft ac;
/*    */   private final World world;
/*    */   private int alertCount;
/*    */   
/*    */   public MCH_MissileDetector(MCH_EntityAircraft aircraft, World w) {
/* 21 */     this.world = w;
/* 22 */     this.ac = aircraft;
/* 23 */     this.alertCount = 0;
/*    */   }
/*    */   
/*    */   public void update() {
/* 27 */     if (this.ac.haveFlare()) {
/* 28 */       if (this.alertCount > 0) {
/* 29 */         this.alertCount--;
/*    */       }
/*    */       
/* 32 */       boolean isLocked = this.ac.getEntityData().getBoolean("Tracking");
/* 33 */       if (isLocked) {
/* 34 */         this.ac.getEntityData().setBoolean("Tracking", false);
/*    */       }
/*    */       
/* 37 */       if (this.ac.getEntityData().getBoolean("LockOn")) {
/* 38 */         if (this.alertCount == 0) {
/* 39 */           this.alertCount = 10;
/* 40 */           if (this.ac.haveFlare() && !this.ac.isDestroyed()) {
/* 41 */             for (int i = 0; i < 2; i++) {
/* 42 */               Entity entity = this.ac.getEntityBySeatId(i);
/* 43 */               if (entity instanceof net.minecraft.entity.player.EntityPlayerMP) {
/* 44 */                 MCH_PacketNotifyLock.sendToPlayer((EntityPlayer)entity);
/*    */               }
/*    */             } 
/*    */           }
/*    */         } 
/*    */         
/* 50 */         this.ac.getEntityData().setBoolean("LockOn", false);
/*    */       } 
/*    */       
/* 53 */       if (!this.ac.isDestroyed()) {
/* 54 */         Entity rider = this.ac.getRiddenByEntity();
/* 55 */         if (rider == null) {
/* 56 */           rider = this.ac.getEntityBySeatId(1);
/*    */         }
/*    */         
/* 59 */         if (rider != null) {
/* 60 */           if (this.ac.isFlareUsing()) {
/* 61 */             destroyMissile();
/* 62 */           } else if (!this.ac.isUAV() && !this.world.isRemote) {
/* 63 */             if (this.alertCount == 0 && (isLocked || isLockedByMissile())) {
/* 64 */               this.alertCount = 20;
/* 65 */               W_WorldFunc.MOD_playSoundAtEntity((Entity)this.ac, "alert", 1.0F, 1.0F);
/*    */             } 
/* 67 */           } else if (this.ac.isUAV() && this.world.isRemote && this.alertCount == 0 && (isLocked || isLockedByMissile())) {
/* 68 */             this.alertCount = 20;
/* 69 */             if (W_Lib.isClientPlayer(rider)) {
/* 70 */               W_McClient.MOD_playSoundFX("alert", 1.0F, 1.0F);
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean destroyMissile() {
/* 79 */     List<MCH_EntityBaseBullet> list = this.world.getEntitiesWithinAABB(MCH_EntityBaseBullet.class, this.ac.getEntityBoundingBox().grow(60.0D, 60.0D, 60.0D));
/* 80 */     for (MCH_EntityBaseBullet msl : list) {
/* 81 */       if (msl.targetEntity != null && (this.ac.isMountedEntity(msl.targetEntity) || msl.targetEntity.equals(this.ac))) {
/* 82 */         msl.targetEntity = null;
/* 83 */         msl.setDead();
/*    */       } 
/*    */     } 
/*    */     
/* 87 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isLockedByMissile() {
/* 91 */     List<MCH_EntityBaseBullet> list = this.world.getEntitiesWithinAABB(MCH_EntityBaseBullet.class, this.ac.getEntityBoundingBox().grow(60.0D, 60.0D, 60.0D));
/* 92 */     for (MCH_EntityBaseBullet msl : list) {
/* 93 */       if (msl.targetEntity != null && (this.ac.isMountedEntity(msl.targetEntity) || msl.targetEntity.equals(this.ac))) {
/* 94 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 98 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_MissileDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */