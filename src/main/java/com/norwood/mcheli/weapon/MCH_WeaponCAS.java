/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_WeaponCAS extends MCH_WeaponBase {
/*     */   public int direction;
/*     */   public Entity user;
/*     */   private double targetPosX;
/*     */   private double targetPosY;
/*     */   private double targetPosZ;
/*     */   private int startTick;
/*     */   private int cntAtk;
/*     */   private Entity shooter;
/*     */   
/*     */   public MCH_WeaponCAS(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  23 */     super(w, v, yaw, pitch, nm, wi);
/*  24 */     this.acceleration = 4.0F;
/*  25 */     this.explosionPower = 2;
/*  26 */     this.power = 32;
/*  27 */     this.interval = 65236;
/*  28 */     if (w.field_72995_K) {
/*  29 */       this.interval -= 10;
/*     */     }
/*     */     
/*  32 */     this.targetPosX = 0.0D;
/*  33 */     this.targetPosY = 0.0D;
/*  34 */     this.targetPosZ = 0.0D;
/*  35 */     this.direction = 0;
/*  36 */     this.startTick = 0;
/*  37 */     this.cntAtk = 3;
/*  38 */     this.shooter = null;
/*  39 */     this.user = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(int countWait) {
/*  44 */     super.update(countWait);
/*  45 */     if (!this.worldObj.field_72995_K && this.cntAtk < 3 && countWait != 0 && this.tick == this.startTick) {
/*  46 */       double x = 0.0D;
/*  47 */       double z = 0.0D;
/*  48 */       if (this.cntAtk >= 1) {
/*  49 */         double sign = (this.cntAtk == 1) ? 1.0D : -1.0D;
/*  50 */         if (this.direction == 0 || this.direction == 2) {
/*  51 */           x = rand.nextDouble() * 10.0D * sign;
/*  52 */           z = (rand.nextDouble() - 0.5D) * 10.0D;
/*     */         } 
/*     */         
/*  55 */         if (this.direction == 1 || this.direction == 3) {
/*  56 */           z = rand.nextDouble() * 10.0D * sign;
/*  57 */           x = (rand.nextDouble() - 0.5D) * 10.0D;
/*     */         } 
/*     */       } 
/*     */       
/*  61 */       spawnA10(this.targetPosX + x, this.targetPosY + 20.0D, this.targetPosZ + z);
/*  62 */       this.startTick = this.tick + 45;
/*  63 */       this.cntAtk++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyParameters() {
/*  69 */     if (this.interval > 65286) {
/*  70 */       this.interval = 65286;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTargetPosition(double x, double y, double z) {
/*  75 */     this.targetPosX = x;
/*  76 */     this.targetPosY = y;
/*  77 */     this.targetPosZ = z;
/*     */   }
/*     */   
/*     */   public void spawnA10(double x, double y, double z) {
/*  81 */     double mX = 0.0D;
/*  82 */     double mY = 0.0D;
/*  83 */     double mZ = 0.0D;
/*  84 */     if (this.direction == 0) {
/*  85 */       mZ += 3.0D;
/*  86 */       z -= 90.0D;
/*     */     } 
/*     */     
/*  89 */     if (this.direction == 1) {
/*  90 */       mX -= 3.0D;
/*  91 */       x += 90.0D;
/*     */     } 
/*     */     
/*  94 */     if (this.direction == 2) {
/*  95 */       mZ -= 3.0D;
/*  96 */       z += 90.0D;
/*     */     } 
/*     */     
/*  99 */     if (this.direction == 3) {
/* 100 */       mX += 3.0D;
/* 101 */       x -= 90.0D;
/*     */     } 
/*     */     
/* 104 */     MCH_EntityA10 a10 = new MCH_EntityA10(this.worldObj, x, y, z);
/* 105 */     a10.setWeaponName(this.name);
/* 106 */     a10.field_70126_B = a10.field_70177_z = (90 * this.direction);
/* 107 */     a10.field_70159_w = mX;
/* 108 */     a10.field_70181_x = mY;
/* 109 */     a10.field_70179_y = mZ;
/* 110 */     a10.direction = this.direction;
/* 111 */     a10.shootingEntity = this.user;
/* 112 */     a10.shootingAircraft = this.shooter;
/* 113 */     a10.explosionPower = this.explosionPower;
/* 114 */     a10.power = this.power;
/* 115 */     a10.acceleration = this.acceleration;
/* 116 */     this.worldObj.func_72838_d((Entity)a10);
/* 117 */     W_WorldFunc.MOD_playSoundEffect(this.worldObj, x, y, z, "a-10_snd", 150.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shot(MCH_WeaponParam prm) {
/* 122 */     float yaw = prm.user.field_70177_z;
/* 123 */     float pitch = prm.user.field_70125_A;
/* 124 */     double tX = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 125 */     double tZ = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 126 */     double tY = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/* 127 */     double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/* 128 */     if (this.worldObj.field_72995_K) {
/* 129 */       tX = tX * 80.0D / dist;
/* 130 */       tY = tY * 80.0D / dist;
/* 131 */       tZ = tZ * 80.0D / dist;
/*     */     } else {
/* 133 */       tX = tX * 150.0D / dist;
/* 134 */       tY = tY * 150.0D / dist;
/* 135 */       tZ = tZ * 150.0D / dist;
/*     */     } 
/*     */     
/* 138 */     Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.field_70165_t, prm.entity.field_70163_u + 2.0D, prm.entity.field_70161_v);
/* 139 */     Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.field_70165_t + tX, prm.entity.field_70163_u + tY + 2.0D, prm.entity.field_70161_v + tZ);
/* 140 */     RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
/* 141 */     if (W_MovingObjectPosition.isHitTypeTile(m)) {
/* 142 */       this.targetPosX = m.field_72307_f.field_72450_a;
/* 143 */       this.targetPosY = m.field_72307_f.field_72448_b;
/* 144 */       this.targetPosZ = m.field_72307_f.field_72449_c;
/* 145 */       this.direction = (int)MCH_Lib.getRotate360((yaw + 45.0F)) / 90;
/* 146 */       this.direction += rand.nextBoolean() ? -1 : 1;
/* 147 */       this.direction %= 4;
/* 148 */       if (this.direction < 0) {
/* 149 */         this.direction += 4;
/*     */       }
/*     */       
/* 152 */       this.user = prm.user;
/* 153 */       this.shooter = prm.entity;
/* 154 */       if (prm.entity != null) {
/* 155 */         playSoundClient(prm.entity, 1.0F, 1.0F);
/*     */       }
/*     */       
/* 158 */       this.startTick = 50;
/* 159 */       this.cntAtk = 0;
/* 160 */       return true;
/*     */     } 
/* 162 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shot(Entity user, double px, double py, double pz, int option1, int option2) {
/* 167 */     float yaw = user.field_70177_z;
/* 168 */     float pitch = user.field_70125_A;
/* 169 */     double tX = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 170 */     double tZ = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 171 */     double tY = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/* 172 */     double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/* 173 */     if (this.worldObj.field_72995_K) {
/* 174 */       tX = tX * 80.0D / dist;
/* 175 */       tY = tY * 80.0D / dist;
/* 176 */       tZ = tZ * 80.0D / dist;
/*     */     } else {
/* 178 */       tX = tX * 120.0D / dist;
/* 179 */       tY = tY * 120.0D / dist;
/* 180 */       tZ = tZ * 120.0D / dist;
/*     */     } 
/*     */     
/* 183 */     Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, px, py, pz);
/* 184 */     Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, px + tX, py + tY, pz + tZ);
/* 185 */     RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
/* 186 */     if (W_MovingObjectPosition.isHitTypeTile(m)) {
/* 187 */       if (this.worldObj.field_72995_K) {
/* 188 */         double dx = m.field_72307_f.field_72450_a - px;
/* 189 */         double dz = m.field_72307_f.field_72449_c - pz;
/* 190 */         if (Math.sqrt(dx * dx + dz * dz) < 20.0D) {
/* 191 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 195 */       this.targetPosX = m.field_72307_f.field_72450_a;
/* 196 */       this.targetPosY = m.field_72307_f.field_72448_b;
/* 197 */       this.targetPosZ = m.field_72307_f.field_72449_c;
/* 198 */       this.direction = (int)MCH_Lib.getRotate360((yaw + 45.0F)) / 90;
/* 199 */       this.direction += rand.nextBoolean() ? -1 : 1;
/* 200 */       this.direction %= 4;
/* 201 */       if (this.direction < 0) {
/* 202 */         this.direction += 4;
/*     */       }
/*     */       
/* 205 */       this.user = user;
/* 206 */       this.shooter = null;
/* 207 */       this.tick = 0;
/* 208 */       this.startTick = 50;
/* 209 */       this.cntAtk = 0;
/* 210 */       return true;
/*     */     } 
/* 212 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponCAS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */