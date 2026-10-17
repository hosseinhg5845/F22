/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class MCH_EntityA10
/*     */   extends W_Entity {
/*  20 */   private static final DataParameter<String> WEAPON_NAME = EntityDataManager.func_187226_a(MCH_EntityA10.class, DataSerializers.field_187194_d);
/*  21 */   static int snd_num = 0;
/*  22 */   public final int DESPAWN_COUNT = 70;
/*  23 */   public int despawnCount = 0;
/*     */   public Entity shootingAircraft;
/*     */   public Entity shootingEntity;
/*  26 */   public int shotCount = 0;
/*  27 */   public int direction = 0;
/*     */   public int power;
/*     */   public float acceleration;
/*     */   public int explosionPower;
/*     */   public final boolean isFlaming;
/*     */   public String name;
/*     */   public MCH_WeaponInfo weaponInfo;
/*     */   
/*     */   public MCH_EntityA10(World world) {
/*  36 */     super(world);
/*  37 */     this.field_70158_ak = true;
/*  38 */     this.field_70156_m = false;
/*  39 */     func_70105_a(5.0F, 3.0F);
/*  40 */     this.field_70159_w = 0.0D;
/*  41 */     this.field_70181_x = 0.0D;
/*  42 */     this.field_70179_y = 0.0D;
/*  43 */     this.power = 32;
/*  44 */     this.acceleration = 4.0F;
/*  45 */     this.explosionPower = 1;
/*  46 */     this.isFlaming = false;
/*  47 */     this.shootingEntity = null;
/*  48 */     this.shootingAircraft = null;
/*  49 */     this.field_70178_ae = true;
/*  50 */     this._renderDistanceWeight *= 10.0D;
/*     */   }
/*     */   
/*     */   public MCH_EntityA10(World world, double x, double y, double z) {
/*  54 */     this(world);
/*  55 */     this.field_70142_S = this.field_70169_q = this.field_70165_t = x;
/*  56 */     this.field_70137_T = this.field_70167_r = this.field_70163_u = y;
/*  57 */     this.field_70136_U = this.field_70166_s = this.field_70161_v = z;
/*     */   }
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  66 */     this.field_70180_af.func_187214_a(WEAPON_NAME, "");
/*     */   }
/*     */   
/*     */   public String getWeaponName() {
/*  70 */     return (String)this.field_70180_af.func_187225_a(WEAPON_NAME);
/*     */   }
/*     */   
/*     */   public void setWeaponName(String s) {
/*  74 */     if (s != null && !s.isEmpty()) {
/*  75 */       this.weaponInfo = MCH_WeaponInfoManager.get(s);
/*  76 */       if (this.weaponInfo != null && !this.field_70170_p.field_72995_K) {
/*  77 */         this.field_70180_af.func_187227_b(WEAPON_NAME, s);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MCH_WeaponInfo getInfo() {
/*  84 */     return this.weaponInfo;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/*  88 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/*  92 */     return func_174813_aQ();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/*  97 */     return false;
/*     */   }
/*     */   
/*     */   public void func_70106_y() {
/* 101 */     super.func_70106_y();
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/* 105 */     super.func_70071_h_();
/* 106 */     if (!this.field_70128_L) {
/* 107 */       this.despawnCount++;
/*     */     }
/*     */     
/* 110 */     if (this.weaponInfo == null) {
/* 111 */       setWeaponName(getWeaponName());
/* 112 */       if (this.weaponInfo == null) {
/* 113 */         func_70106_y();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 118 */     if (this.field_70170_p.field_72995_K) {
/* 119 */       onUpdate_Client();
/*     */     } else {
/* 121 */       onUpdate_Server();
/*     */     } 
/*     */     
/* 124 */     if (!this.field_70128_L) {
/* 125 */       if (this.despawnCount <= 20) {
/* 126 */         this.field_70181_x = -0.3D;
/*     */       } else {
/* 128 */         func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 129 */         this.field_70181_x += 0.02D;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isRender() {
/* 135 */     return (this.despawnCount > 20);
/*     */   }
/*     */   
/*     */   private void onUpdate_Client() {
/* 139 */     this.shotCount += 4;
/*     */   }
/*     */   
/*     */   private void onUpdate_Server() {
/* 143 */     if (!this.field_70128_L) {
/* 144 */       if (this.despawnCount > 70) {
/* 145 */         func_70106_y();
/* 146 */       } else if (this.despawnCount > 0 && this.shotCount < 40) {
/* 147 */         for (int i = 0; i < 2; i++) {
/* 148 */           shotGAU8(true, this.shotCount);
/* 149 */           this.shotCount++;
/*     */         } 
/*     */         
/* 152 */         if (this.shotCount == 38) {
/* 153 */           W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "gau-8_snd", 150.0F, 1.0F);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void shotGAU8(boolean playSound, int cnt) {
/* 160 */     float yaw = (90 * this.direction);
/* 161 */     float pitch = 30.0F;
/* 162 */     double x = this.field_70165_t;
/* 163 */     double y = this.field_70163_u;
/* 164 */     double z = this.field_70161_v;
/* 165 */     double tX = this.field_70146_Z.nextDouble() - 0.5D;
/* 166 */     double tY = -2.6D;
/* 167 */     double tZ = this.field_70146_Z.nextDouble() - 0.5D;
/* 168 */     if (this.direction == 0) {
/* 169 */       tZ += 10.0D;
/* 170 */       z += cnt * 0.6D;
/*     */     } 
/*     */     
/* 173 */     if (this.direction == 1) {
/* 174 */       tX -= 10.0D;
/* 175 */       x -= cnt * 0.6D;
/*     */     } 
/*     */     
/* 178 */     if (this.direction == 2) {
/* 179 */       tZ -= 10.0D;
/* 180 */       z -= cnt * 0.6D;
/*     */     } 
/*     */     
/* 183 */     if (this.direction == 3) {
/* 184 */       tX += 10.0D;
/* 185 */       x += cnt * 0.6D;
/*     */     } 
/*     */     
/* 188 */     double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/* 189 */     tX = tX * 4.0D / dist;
/* 190 */     tY = tY * 4.0D / dist;
/* 191 */     tZ = tZ * 4.0D / dist;
/* 192 */     MCH_EntityBullet e = new MCH_EntityBullet(this.field_70170_p, x, y, z, tX, tY, tZ, yaw, pitch, this.acceleration);
/* 193 */     e.setName(getWeaponName());
/* 194 */     e.explosionPower = (this.shotCount % 4 == 0) ? this.explosionPower : 0;
/* 195 */     e.setPower(this.power);
/* 196 */     e.shootingEntity = this.shootingEntity;
/* 197 */     e.shootingAircraft = this.shootingAircraft;
/* 198 */     this.field_70170_p.func_72838_d((Entity)e);
/*     */   }
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 202 */     par1NBTTagCompound.func_74778_a("WeaponName", getWeaponName());
/*     */   }
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 206 */     this.despawnCount = 200;
/* 207 */     if (par1NBTTagCompound.func_74764_b("WeaponName")) {
/* 208 */       setWeaponName(par1NBTTagCompound.func_74779_i("WeaponName"));
/*     */     }
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 214 */     return 10.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityA10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */