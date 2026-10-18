/*     */ package com.norwood.mcheli.flare;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.particles.MCH_ParticleParam;
/*     */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_Flare {
/*  18 */   private static FlareParam[] FLARE_DATA = null;
/*     */   public final World worldObj;
/*     */   public final MCH_EntityAircraft aircraft;
/*     */   public final Random rand;
/*     */   public int numFlare;
/*     */   public int tick;
/*     */   private int flareType;
/*     */   
/*     */   public MCH_Flare(World w, MCH_EntityAircraft ac) {
/*  27 */     this.worldObj = w;
/*  28 */     this.aircraft = ac;
/*  29 */     this.rand = new Random();
/*  30 */     this.tick = 0;
/*  31 */     this.numFlare = 0;
/*  32 */     this.flareType = 0;
/*  33 */     if (FLARE_DATA == null) {
/*  34 */       int delay = w.isRemote ? 50 : 0;
/*  35 */       FLARE_DATA = new FlareParam[11];
/*  36 */       FLARE_DATA[1] = new FlareParam(1, 3, 200 + delay, 100, 16);
/*  37 */       FLARE_DATA[2] = new FlareParam(3, 5, 300 + delay, 200, 16);
/*  38 */       FLARE_DATA[3] = new FlareParam(2, 3, 200 + delay, 100, 16);
/*  39 */       FLARE_DATA[4] = new FlareParam(1, 3, 200 + delay, 100, 16);
/*  40 */       FLARE_DATA[5] = new FlareParam(2, 3, 200 + delay, 100, 16);
/*  41 */       FLARE_DATA[10] = new FlareParam(8, 1, 250 + delay, 60, 1);
/*  42 */       FLARE_DATA[0] = FLARE_DATA[1];
/*  43 */       FLARE_DATA[6] = FLARE_DATA[1];
/*  44 */       FLARE_DATA[7] = FLARE_DATA[1];
/*  45 */       FLARE_DATA[8] = FLARE_DATA[1];
/*  46 */       FLARE_DATA[9] = FLARE_DATA[1];
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isInPreparation() {
/*  51 */     return (this.tick != 0);
/*     */   }
/*     */   
/*     */   public boolean isUsing() {
/*  55 */     int type = getFlareType();
/*  56 */     return (this.tick != 0 && type < FLARE_DATA.length && this.tick > (FLARE_DATA[type]).tickWait - (FLARE_DATA[type]).tickEnable);
/*     */   }
/*     */   
/*     */   public int getFlareType() {
/*  60 */     return this.flareType;
/*     */   }
/*     */   
/*     */   public void spawnParticle(String name, int num, float size) {
/*  64 */     if (this.worldObj.isRemote) {
/*  65 */       if (name.isEmpty() || num < 1 || num > 50) {
/*     */         return;
/*     */       }
/*     */       
/*  69 */       double x = (this.aircraft.posX - this.aircraft.prevPosX) / num;
/*  70 */       double y = (this.aircraft.posY - this.aircraft.prevPosY) / num;
/*  71 */       double z = (this.aircraft.posZ - this.aircraft.prevPosZ) / num;
/*     */       
/*  73 */       for (int i = 0; i < num; i++) {
/*  74 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", this.aircraft.prevPosX + x * i, this.aircraft.prevPosY + y * i, this.aircraft.prevPosZ + z * i);
/*     */ 
/*     */         
/*  77 */         prm.size = size + this.rand.nextFloat();
/*  78 */         MCH_ParticlesUtil.spawnParticle(prm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean use(int type) {
/*  84 */     boolean result = false;
/*  85 */     MCH_Lib.DbgLog(this.aircraft.world, "MCH_Flare.use type = %d", new Object[] { Integer.valueOf(type) });
/*  86 */     this.flareType = type;
/*  87 */     if (type <= 0 && type >= FLARE_DATA.length) {
/*  88 */       return false;
/*     */     }
/*  90 */     if (this.worldObj.isRemote) {
/*  91 */       if (this.tick == 0) {
/*  92 */         this.tick = (FLARE_DATA[getFlareType()]).tickWait;
/*  93 */         result = true;
/*  94 */         this.numFlare = 0;
/*  95 */         W_McClient.playSoundClick(1.0F, 1.0F);
/*     */       } 
/*     */     } else {
/*  98 */       result = true;
/*  99 */       this.numFlare = 0;
/* 100 */       this.tick = (FLARE_DATA[getFlareType()]).tickWait;
/* 101 */       this.aircraft.getEntityData().setBoolean("FlareUsing", true);
/*     */     } 
/*     */     
/* 104 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 109 */     int type = getFlareType();
/* 110 */     if (this.aircraft != null && !this.aircraft.isDead && type > 0 && type <= FLARE_DATA.length) {
/* 111 */       if (this.tick > 0) {
/* 112 */         this.tick--;
/*     */       }
/*     */       
/* 115 */       if (!this.worldObj.isRemote && this.tick > 0 && this.tick % (FLARE_DATA[type]).interval == 0 && this.numFlare < (FLARE_DATA[type]).numFlareMax) {
/* 116 */         Vec3d v = (this.aircraft.getAcInfo()).flare.pos;
/* 117 */         v = this.aircraft.getTransformedPosition(v.x, v.y, v.z, this.aircraft.prevPosX, this.aircraft.prevPosY, this.aircraft.prevPosZ);
/* 118 */         spawnFlare(v);
/*     */       } 
/*     */       
/* 121 */       if (!isUsing() && this.aircraft.getEntityData().getBoolean("FlareUsing")) {
/* 122 */         this.aircraft.getEntityData().setBoolean("FlareUsing", false);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void spawnFlare(Vec3d v) {
/* 128 */     this.numFlare++;
/* 129 */     int type = getFlareType();
/* 130 */     int num = (FLARE_DATA[type]).num;
/* 131 */     double x = v.x - this.aircraft.motionX * 2.0D;
/* 132 */     double y = v.y - this.aircraft.motionY * 2.0D - 1.0D;
/* 133 */     double z = v.z - this.aircraft.motionZ * 2.0D;
/* 134 */     this.worldObj
/* 135 */       .playSound(null, new BlockPos(x, y, z), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (this.worldObj.rand
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 141 */         .nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F);
/*     */ 
/*     */     
/* 144 */     for (int i = 0; i < num; i++) {
/* 145 */       x = v.x - this.aircraft.motionX * 2.0D;
/* 146 */       y = v.y - this.aircraft.motionY * 2.0D - 1.0D;
/* 147 */       z = v.z - this.aircraft.motionZ * 2.0D;
/* 148 */       double tx = 0.0D;
/* 149 */       double ty = this.aircraft.motionY;
/* 150 */       double tz = 0.0D;
/* 151 */       int fuseCount = 0;
/* 152 */       double r = this.aircraft.rotationYaw;
/* 153 */       if (type == 1) {
/* 154 */         tx = MathHelper.sin(this.rand.nextFloat() * 360.0F);
/* 155 */         tz = MathHelper.cos(this.rand.nextFloat() * 360.0F);
/* 156 */       } else if (type != 2 && type != 3) {
/* 157 */         if (type == 4) {
/* 158 */           r *= 0.017453292519943295D;
/* 159 */           tx = -Math.sin(r) + (this.rand.nextFloat() - 0.5D) * 1.3D;
/* 160 */           tz = Math.cos(r) + (this.rand.nextFloat() - 0.5D) * 1.3D;
/* 161 */         } else if (type == 5) {
/* 162 */           r *= 0.017453292519943295D;
/* 163 */           tx = -Math.sin(r) + (this.rand.nextFloat() - 0.5D) * 0.9D;
/* 164 */           tz = Math.cos(r) + (this.rand.nextFloat() - 0.5D) * 0.9D;
/* 165 */           tx *= 0.3D;
/* 166 */           tz *= 0.3D;
/*     */         } 
/*     */       } else {
/* 169 */         if (i == 0) {
/* 170 */           r += 90.0D;
/*     */         }
/*     */         
/* 173 */         if (i == 1) {
/* 174 */           r -= 90.0D;
/*     */         }
/*     */         
/* 177 */         if (i == 2) {
/* 178 */           r += 180.0D;
/*     */         }
/*     */         
/* 181 */         r *= 0.017453292519943295D;
/* 182 */         tx = -Math.sin(r) + (this.rand.nextFloat() - 0.5D) * 0.6D;
/* 183 */         tz = Math.cos(r) + (this.rand.nextFloat() - 0.5D) * 0.6D;
/*     */       } 
/*     */       
/* 186 */       tx += this.aircraft.motionX;
/* 187 */       ty += this.aircraft.motionY / 2.0D;
/* 188 */       tz += this.aircraft.motionZ;
/* 189 */       if (type == 10) {
/* 190 */         r += 360.0D / num / 2.0D + i * 360.0D / num;
/* 191 */         r *= 0.017453292519943295D;
/* 192 */         tx = -Math.sin(r) * 2.0D;
/* 193 */         tz = Math.cos(r) * 2.0D;
/* 194 */         ty = 0.7D;
/* 195 */         y += 2.0D;
/* 196 */         fuseCount = 10;
/*     */       } 
/*     */       
/* 199 */       MCH_EntityFlare e = new MCH_EntityFlare(this.worldObj, x, y, z, tx * 0.5D, ty * 0.5D, tz * 0.5D, 6.0F, fuseCount);
/* 200 */       e.rotationPitch = this.rand.nextFloat() * 360.0F;
/* 201 */       e.rotationYaw = this.rand.nextFloat() * 360.0F;
/* 202 */       e.prevRotationPitch = this.rand.nextFloat() * 360.0F;
/* 203 */       e.prevRotationYaw = this.rand.nextFloat() * 360.0F;
/* 204 */       if (type == 4) {
/* 205 */         e.gravity *= 0.6D;
/* 206 */         e.airResistance = 0.995D;
/*     */       } 
/*     */       
/* 209 */       this.worldObj.spawnEntity((Entity)e);
/*     */     } 
/*     */   }
/*     */   
/*     */   static class FlareParam {
/*     */     public final int num;
/*     */     public final int interval;
/*     */     public final int tickWait;
/*     */     public final int tickEnable;
/*     */     public final int numFlareMax;
/*     */     
/*     */     public FlareParam(int num, int interval, int tickWait, int tickEnable, int numFlareMax) {
/* 221 */       this.num = num;
/* 222 */       this.interval = interval;
/* 223 */       this.tickWait = tickWait;
/* 224 */       this.tickEnable = tickEnable;
/* 225 */       this.numFlareMax = numFlareMax;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\flare\MCH_Flare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */