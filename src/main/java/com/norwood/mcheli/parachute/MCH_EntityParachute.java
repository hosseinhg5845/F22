import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
/*     */ package com.norwood.mcheli.parachute;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.helper.entity.IEntitySinglePassenger;
/*     */ import com.norwood.mcheli.particles.MCH_ParticleParam;
/*     */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*     */ import com.norwood.mcheli.wrapper.W_AxisAlignedBB;
/*     */ import com.norwood.mcheli.wrapper.W_Block;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.item.EntityBoat;
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
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityParachute extends W_Entity implements IEntitySinglePassenger {
/*  31 */   private static final DataParameter<Byte> TYPE = EntityDataManager.func_187226_a(MCH_EntityParachute.class, DataSerializers.field_187191_a);
/*     */   public Entity user;
/*     */   public int onGroundCount;
/*  34 */   private double speedMultiplier = 0.07D;
/*     */   private int paraPosRotInc;
/*     */   private double paraX;
/*     */   private double paraY;
/*     */   private double paraZ;
/*     */   private double paraYaw;
/*     */   private double paraPitch;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityY;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityZ;
/*     */   
/*     */   public MCH_EntityParachute(World par1World) {
/*  49 */     super(par1World);
/*  50 */     this.field_70156_m = true;
/*  51 */     func_70105_a(1.5F, 0.6F);
/*  52 */     this.user = null;
/*  53 */     this.onGroundCount = 0;
/*     */   }
/*     */   
/*     */   public MCH_EntityParachute(World par1World, double par2, double par4, double par6) {
/*  57 */     this(par1World);
/*  58 */     func_70107_b(par2, par4, par6);
/*  59 */     this.field_70159_w = 0.0D;
/*  60 */     this.field_70181_x = 0.0D;
/*  61 */     this.field_70179_y = 0.0D;
/*  62 */     this.field_70169_q = par2;
/*  63 */     this.field_70167_r = par4;
/*  64 */     this.field_70166_s = par6;
/*     */   }
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  73 */     this.field_70180_af.func_187214_a(TYPE, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public int getType() {
/*  77 */     return ((Byte)this.field_70180_af.func_187225_a(TYPE)).byteValue();
/*     */   }
/*     */   
/*     */   public void setType(int n) {
/*  81 */     this.field_70180_af.func_187227_b(TYPE, Byte.valueOf((byte)n));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/*  85 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/*  89 */     return func_174813_aQ();
/*     */   }
/*     */   
/*     */   public boolean func_70104_M() {
/*  93 */     return true;
/*     */   }
/*     */   
/*     */   public double func_70042_X() {
/*  97 */     return this.field_70131_O * 0.0D - 0.30000001192092896D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public boolean func_70067_L() {
/* 106 */     return !this.field_70128_L;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_180426_a(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/* 111 */     this.paraPosRotInc = posRotationIncrements + 10;
/* 112 */     this.paraX = x;
/* 113 */     this.paraY = y;
/* 114 */     this.paraZ = z;
/* 115 */     this.paraYaw = yaw;
/* 116 */     this.paraPitch = pitch;
/* 117 */     this.field_70159_w = this.velocityX;
/* 118 */     this.field_70181_x = this.velocityY;
/* 119 */     this.field_70179_y = this.velocityZ;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double par1, double par3, double par5) {
/* 124 */     this.velocityX = this.field_70159_w = par1;
/* 125 */     this.velocityY = this.field_70181_x = par3;
/* 126 */     this.velocityZ = this.field_70179_y = par5;
/*     */   }
/*     */   
/*     */   public void func_70106_y() {
/* 130 */     super.func_70106_y();
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/* 134 */     super.func_70071_h_();
/* 135 */     if (!this.field_70170_p.field_72995_K && this.field_70173_aa % 10 == 0) {
/* 136 */       MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityParachute.onUpdate %d, %.3f", new Object[] { Integer.valueOf(this.field_70173_aa), Double.valueOf(this.field_70181_x) });
/*     */     }
/*     */     
/* 139 */     if (isOpenParachute() && this.field_70181_x > -0.3D && this.field_70173_aa > 20) {
/* 140 */       this.field_70143_R = (float)(this.field_70143_R * 0.85D);
/*     */     }
/*     */     
/* 143 */     if (!this.field_70170_p.field_72995_K && this.user != null && this.user.func_184187_bx() == null) {
/* 144 */       this.user.func_184220_m((Entity)this);
/* 145 */       this.field_70177_z = this.field_70126_B = this.user.field_70177_z;
/* 146 */       this.user = null;
/*     */     } 
/*     */     
/* 149 */     this.field_70169_q = this.field_70165_t;
/* 150 */     this.field_70167_r = this.field_70163_u;
/* 151 */     this.field_70166_s = this.field_70161_v;
/* 152 */     double d1 = (func_174813_aQ()).field_72338_b + ((func_174813_aQ()).field_72337_e - (func_174813_aQ()).field_72338_b) * 0.0D / 5.0D - 0.125D;
/* 153 */     double d2 = (func_174813_aQ()).field_72338_b + ((func_174813_aQ()).field_72337_e - (func_174813_aQ()).field_72338_b) / 5.0D - 0.125D;
/* 154 */     AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(
/* 155 */         (func_174813_aQ()).field_72340_a, d1, (func_174813_aQ()).field_72339_c, (func_174813_aQ()).field_72336_d, d2, (func_174813_aQ()).field_72334_f);
/*     */     
/* 157 */     if (this.field_70170_p.func_72875_a(axisalignedbb, Material.field_151586_h)) {
/* 158 */       onWaterSetBoat();
/* 159 */       func_70106_y();
/*     */     } 
/*     */     
/* 162 */     if (this.field_70170_p.field_72995_K) {
/* 163 */       onUpdateClient();
/*     */     } else {
/* 165 */       onUpdateServer();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdateClient() {
/* 170 */     if (this.paraPosRotInc > 0) {
/* 171 */       double x = this.field_70165_t + (this.paraX - this.field_70165_t) / this.paraPosRotInc;
/* 172 */       double y = this.field_70163_u + (this.paraY - this.field_70163_u) / this.paraPosRotInc;
/* 173 */       double z = this.field_70161_v + (this.paraZ - this.field_70161_v) / this.paraPosRotInc;
/* 174 */       double yaw = MathHelper.func_76138_g(this.paraYaw - this.field_70177_z);
/* 175 */       this.field_70177_z = (float)(this.field_70177_z + yaw / this.paraPosRotInc);
/* 176 */       this.field_70125_A = (float)(this.field_70125_A + (this.paraPitch - this.field_70125_A) / this.paraPosRotInc);
/* 177 */       this.paraPosRotInc--;
/* 178 */       func_70107_b(x, y, z);
/* 179 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/* 180 */       if (getRiddenByEntity() != null) {
/* 181 */         func_70101_b((getRiddenByEntity()).field_70126_B, this.field_70125_A);
/*     */       }
/*     */     } else {
/* 184 */       func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 185 */       if (this.field_70122_E);
/*     */ 
/*     */       
/* 188 */       this.field_70159_w *= 0.99D;
/* 189 */       this.field_70181_x *= 0.95D;
/* 190 */       this.field_70179_y *= 0.99D;
/*     */     } 
/*     */     
/* 193 */     if (!isOpenParachute() && this.field_70181_x > 0.01D) {
/* 194 */       float color = 0.6F + this.field_70146_Z.nextFloat() * 0.2F;
/* 195 */       double dx = this.field_70169_q - this.field_70165_t;
/* 196 */       double dy = this.field_70167_r - this.field_70163_u;
/* 197 */       double dz = this.field_70166_s - this.field_70161_v;
/* 198 */       int num = 1 + (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) * 2.0D);
/*     */       double i;
/* 200 */       for (i = 0.0D; i < num; i++) {
/* 201 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + (this.field_70165_t - this.field_70169_q) * i / num * 0.8D, this.field_70167_r + (this.field_70163_u - this.field_70167_r) * i / num * 0.8D, this.field_70166_s + (this.field_70161_v - this.field_70166_s) * i / num * 0.8D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 208 */         prm.motionX = this.field_70159_w * 0.5D + (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D;
/* 209 */         prm.motionX = this.field_70181_x * -0.5D + (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D;
/* 210 */         prm.motionX = this.field_70179_y * 0.5D + (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D;
/* 211 */         prm.size = 5.0F;
/* 212 */         prm.setColor(0.8F + this.field_70146_Z.nextFloat(), color, color, color);
/* 213 */         MCH_ParticlesUtil.spawnParticle(prm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdateServer() {
/* 219 */     double prevSpeed = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 220 */     double gravity = this.field_70122_E ? 0.01D : 0.03D;
/* 221 */     if (getType() == 2 && this.field_70173_aa < 20) {
/* 222 */       gravity = 0.01D;
/*     */     }
/*     */     
/* 225 */     this.field_70181_x -= gravity;
/* 226 */     if (isOpenParachute()) {
/* 227 */       if (W_Lib.isEntityLivingBase(getRiddenByEntity())) {
/* 228 */         double mv = W_Lib.getEntityMoveDist(getRiddenByEntity());
/* 229 */         if (!isOpenParachute()) {
/* 230 */           mv = 0.0D;
/*     */         }
/*     */         
/* 233 */         if (mv > 0.0D) {
/* 234 */           double mx = -Math.sin(((getRiddenByEntity()).field_70177_z * 3.1415927F / 180.0F));
/* 235 */           double mz = Math.cos(((getRiddenByEntity()).field_70177_z * 3.1415927F / 180.0F));
/* 236 */           this.field_70159_w += mx * this.speedMultiplier * 0.05D;
/* 237 */           this.field_70179_y += mz * this.speedMultiplier * 0.05D;
/*     */         } 
/*     */       } 
/*     */       
/* 241 */       double speed = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 242 */       if (speed > 0.35D) {
/* 243 */         this.field_70159_w *= 0.35D / speed;
/* 244 */         this.field_70179_y *= 0.35D / speed;
/* 245 */         speed = 0.35D;
/*     */       } 
/*     */       
/* 248 */       if (speed > prevSpeed && this.speedMultiplier < 0.35D) {
/* 249 */         this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
/* 250 */         if (this.speedMultiplier > 0.35D) {
/* 251 */           this.speedMultiplier = 0.35D;
/*     */         }
/*     */       } else {
/* 254 */         this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
/* 255 */         if (this.speedMultiplier < 0.07D) {
/* 256 */           this.speedMultiplier = 0.07D;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 261 */     if (this.field_70122_E) {
/* 262 */       this.onGroundCount++;
/* 263 */       if (this.onGroundCount > 5) {
/* 264 */         onGroundAndDead();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 269 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 270 */     if (getType() == 2 && this.field_70173_aa < 20) {
/* 271 */       this.field_70181_x *= 0.95D;
/*     */     } else {
/* 273 */       this.field_70181_x *= 0.9D;
/*     */     } 
/*     */     
/* 276 */     if (isOpenParachute()) {
/* 277 */       this.field_70159_w *= 0.95D;
/* 278 */       this.field_70179_y *= 0.95D;
/*     */     } else {
/* 280 */       this.field_70159_w *= 0.99D;
/* 281 */       this.field_70179_y *= 0.99D;
/*     */     } 
/*     */     
/* 284 */     this.field_70125_A = 0.0F;
/* 285 */     double yaw = this.field_70177_z;
/* 286 */     double dx = this.field_70169_q - this.field_70165_t;
/* 287 */     double dz = this.field_70166_s - this.field_70161_v;
/* 288 */     if (dx * dx + dz * dz > 0.001D) {
/* 289 */       yaw = (float)(Math.atan2(dx, dz) * 180.0D / Math.PI);
/*     */     }
/*     */     
/* 292 */     double yawDiff = MathHelper.func_76138_g(yaw - this.field_70177_z);
/* 293 */     if (yawDiff > 20.0D) {
/* 294 */       yawDiff = 20.0D;
/*     */     }
/*     */     
/* 297 */     if (yawDiff < -20.0D) {
/* 298 */       yawDiff = -20.0D;
/*     */     }
/*     */     
/* 301 */     if (getRiddenByEntity() != null) {
/* 302 */       func_70101_b((getRiddenByEntity()).field_70177_z, this.field_70125_A);
/*     */     } else {
/* 304 */       this.field_70177_z = (float)(this.field_70177_z + yawDiff);
/* 305 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */     } 
/*     */     
/* 308 */     List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72314_b(0.2D, 0.0D, 0.2D));
/* 309 */     if (!list.isEmpty()) {
/* 310 */       for (Entity entity : list) {
/* 311 */         if (entity != getRiddenByEntity() && entity.func_70104_M() && entity instanceof MCH_EntityParachute) {
/* 312 */           entity.func_70108_f((Entity)this);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 317 */     if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L) {
/* 318 */       func_70106_y();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onGroundAndDead() {
/* 323 */     this.field_70163_u++;
/* 324 */     func_184232_k(getRiddenByEntity());
/* 325 */     func_70106_y();
/*     */   }
/*     */   
/*     */   public void onWaterSetBoat() {
/* 329 */     if (!this.field_70170_p.field_72995_K && 
/* 330 */       getType() == 2 && 
/* 331 */       getRiddenByEntity() != null) {
/* 332 */       int px = (int)(this.field_70165_t + 0.5D);
/* 333 */       int py = (int)(this.field_70163_u + 0.5D);
/* 334 */       int pz = (int)(this.field_70161_v + 0.5D);
/* 335 */       boolean foundBlock = false;
/*     */       
/* 337 */       for (int y = 0; y < 5 && py + y >= 0 && py + y <= 255; y++) {
/* 338 */         Block block = W_WorldFunc.getBlock(this.field_70170_p, px, py - y, pz);
/* 339 */         if (block == W_Block.getWater()) {
/* 340 */           py -= y;
/* 341 */           foundBlock = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 346 */       if (foundBlock) {
/* 347 */         int countWater = 0;
/*     */         
/* 349 */         for (int yx = 0; yx < 3 && py + yx >= 0 && py + yx <= 255; yx++) {
/* 350 */           for (int x = -2; x <= 2; x++) {
/* 351 */             for (int z = -2; z <= 2; z++) {
/* 352 */               Block block = W_WorldFunc.getBlock(this.field_70170_p, px + x, py - yx, pz + z);
/* 353 */               if (block == W_Block.getWater() && 
/* 354 */                 ++countWater > 37) {
/*     */                 break;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 362 */         if (countWater > 37) {
/* 363 */           EntityBoat entityboat = new EntityBoat(this.field_70170_p, px, (py + 1.0F), pz);
/* 364 */           entityboat.field_70177_z = this.field_70177_z - 90.0F;
/* 365 */           this.field_70170_p.func_72838_d((Entity)entityboat);
/* 366 */           getRiddenByEntity().func_184220_m((Entity)entityboat);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpenParachute() {
/* 375 */     return (getType() != 2 || this.field_70181_x < -0.1D);
/*     */   }
/*     */   
/*     */   public void func_184232_k(@NotNull Entity passenger) {
/* 379 */     if (func_184196_w(passenger)) {
/* 380 */       double x = -Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.1D;
/* 381 */       double z = Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.1D;
/* 382 */       passenger.func_70107_b(this.field_70165_t + x, this.field_70163_u + func_70042_X() + passenger.func_70033_W(), this.field_70161_v + z);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound nbt) {
/* 387 */     nbt.func_74774_a("ParachuteModelType", (byte)getType());
/*     */   }
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbt) {
/* 391 */     setType(nbt.func_74771_c("ParachuteModelType"));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 396 */     return 4.0F;
/*     */   }
/*     */   
/*     */   public boolean func_184230_a(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 400 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 406 */     List<Entity> passengers = func_184188_bt();
/* 407 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\parachute\MCH_EntityParachute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */