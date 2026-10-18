/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_PacketNotifyTVMissileEntity;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_WeaponTvMissile extends MCH_WeaponBase {
/*     */   protected MCH_EntityTvMissile lastShotTvMissile;
/*     */   protected Entity lastShotEntity;
/*     */   protected boolean isTVGuided;
/*     */   
/*     */   public MCH_WeaponTvMissile(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  17 */     super(w, v, yaw, pitch, nm, wi);
/*  18 */     this.power = 32;
/*  19 */     this.acceleration = 2.0F;
/*  20 */     this.explosionPower = 4;
/*  21 */     this.interval = -100;
/*  22 */     if (w.isRemote) {
/*  23 */       this.interval -= 10;
/*     */     }
/*     */     
/*  26 */     this.numMode = 2;
/*  27 */     this.lastShotEntity = null;
/*  28 */     this.lastShotTvMissile = null;
/*  29 */     this.isTVGuided = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  34 */     String opt = "";
/*  35 */     if (getCurrentMode() == 0) {
/*  36 */       opt = " [TV]";
/*     */     }
/*     */     
/*  39 */     if (getCurrentMode() == 2) {
/*  40 */       opt = " [TA]";
/*     */     }
/*     */     
/*  43 */     return super.getName() + opt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(int countWait) {
/*  48 */     super.update(countWait);
/*  49 */     if (!this.worldObj.isRemote) {
/*  50 */       if (this.isTVGuided && this.tick <= 9) {
/*  51 */         if (this.tick % 3 == 0 && this.lastShotTvMissile != null && !this.lastShotTvMissile.isDead && this.lastShotEntity != null && !this.lastShotEntity.isDead)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*  56 */           MCH_PacketNotifyTVMissileEntity.send(W_Entity.getEntityId(this.lastShotEntity), W_Entity.getEntityId((Entity)this.lastShotTvMissile));
/*     */         }
/*     */         
/*  59 */         if (this.tick == 9) {
/*  60 */           this.lastShotEntity = null;
/*  61 */           this.lastShotTvMissile = null;
/*     */         } 
/*     */       } 
/*     */       
/*  65 */       if (this.tick <= 2 && this.lastShotEntity instanceof MCH_EntityAircraft) {
/*  66 */         ((MCH_EntityAircraft)this.lastShotEntity).setTVMissile(this.lastShotTvMissile);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shot(MCH_WeaponParam prm) {
/*  73 */     return this.worldObj.isRemote ? shotClient(prm.entity, prm.user) : shotServer(prm);
/*     */   }
/*     */   
/*     */   protected boolean shotClient(Entity entity, Entity user) {
/*  77 */     this.optionParameter2 = 0;
/*  78 */     this.optionParameter1 = getCurrentMode();
/*  79 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean shotServer(MCH_WeaponParam prm) {
/*  83 */     float yaw = prm.user.rotationYaw + this.fixRotationYaw;
/*  84 */     float pitch = prm.user.rotationPitch + this.fixRotationPitch;
/*  85 */     double tX = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/*  86 */     double tZ = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/*  87 */     double tY = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
/*  88 */     this.isTVGuided = (prm.option1 == 0);
/*  89 */     float acr = this.acceleration;
/*  90 */     if (!this.isTVGuided) {
/*  91 */       acr = (float)(acr * 1.5D);
/*     */     }
/*     */     
/*  94 */     MCH_EntityTvMissile e = new MCH_EntityTvMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, acr);
/*  95 */     e.setName(this.name);
/*  96 */     e.setParameterFromWeapon(this, prm.entity, prm.user);
/*  97 */     this.lastShotEntity = prm.entity;
/*  98 */     this.lastShotTvMissile = e;
/*  99 */     this.worldObj.spawnEntity((Entity)e);
/* 100 */     playSound(prm.entity);
/* 101 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponTvMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */