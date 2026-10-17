/*      */ package com.norwood.mcheli.weapon;
/*      */ 
/*      */ import com.norwood.mcheli.MCH_Config;
/*      */ import com.norwood.mcheli.MCH_Explosion;
/*      */ import com.norwood.mcheli.MCH_HBMUtil;
/*      */ import com.norwood.mcheli.MCH_Lib;
/*      */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*      */ import com.norwood.mcheli.aircraft.MCH_PacketNotifyHitBullet;
/*      */ import com.norwood.mcheli.helper.MCH_CriteriaTriggers;
/*      */ import com.norwood.mcheli.particles.MCH_ParticleParam;
/*      */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*      */ import com.norwood.mcheli.wrapper.W_Entity;
/*      */ import com.norwood.mcheli.wrapper.W_EntityPlayer;
/*      */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*      */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraftforge.fml.relauncher.Side;
/*      */ import net.minecraftforge.fml.relauncher.SideOnly;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ 
/*      */ public abstract class MCH_EntityBaseBullet extends W_Entity {
/*   41 */   private static final DataParameter<Integer> TARGET_ID = EntityDataManager.func_187226_a(MCH_EntityBaseBullet.class, DataSerializers.field_187192_b);
/*   42 */   private static final DataParameter<String> INFO_NAME = EntityDataManager.func_187226_a(MCH_EntityBaseBullet.class, DataSerializers.field_187194_d);
/*   43 */   private static final DataParameter<String> BULLET_MODEL = EntityDataManager.func_187226_a(MCH_EntityBaseBullet.class, DataSerializers.field_187194_d);
/*   44 */   private static final DataParameter<Byte> BOMBLET_FLAG = EntityDataManager.func_187226_a(MCH_EntityBaseBullet.class, DataSerializers.field_187191_a);
/*      */   public Entity shootingEntity;
/*      */   public Entity shootingAircraft;
/*      */   public int explosionPower;
/*      */   public int explosionPowerInWater;
/*      */   public double acceleration;
/*      */   public double accelerationFactor;
/*      */   public Entity targetEntity;
/*      */   public int piercing;
/*      */   public int delayFuse;
/*      */   public int sprinkleTime;
/*      */   public byte isBomblet;
/*      */   public double prevPosX2;
/*      */   public double prevPosY2;
/*      */   public double prevPosZ2;
/*      */   public double prevMotionX;
/*      */   public double prevMotionY;
/*      */   public double prevMotionZ;
/*   62 */   private int countOnUpdate = 0;
/*      */   private int power;
/*      */   private MCH_WeaponInfo weaponInfo;
/*      */   private MCH_BulletModel model;
/*      */   
/*      */   public MCH_EntityBaseBullet(World par1World) {
/*   68 */     super(par1World);
/*   69 */     func_70105_a(1.0F, 1.0F);
/*   70 */     this.field_70126_B = this.field_70177_z;
/*   71 */     this.field_70127_C = this.field_70125_A;
/*   72 */     this.targetEntity = null;
/*   73 */     setPower(1);
/*   74 */     this.acceleration = 1.0D;
/*   75 */     this.accelerationFactor = 1.0D;
/*   76 */     this.piercing = 0;
/*   77 */     this.explosionPower = 0;
/*   78 */     this.explosionPowerInWater = 0;
/*   79 */     this.delayFuse = 0;
/*   80 */     this.sprinkleTime = 0;
/*   81 */     this.isBomblet = -1;
/*   82 */     this.weaponInfo = null;
/*   83 */     this.field_70158_ak = true;
/*   84 */     if (par1World.field_72995_K) {
/*   85 */       this.model = null;
/*      */     }
/*      */   }
/*      */   
/*      */   public MCH_EntityBaseBullet(World par1World, double px, double py, double pz, double mx, double my, double mz, float yaw, float pitch, double acceleration) {
/*   90 */     this(par1World);
/*   91 */     func_70105_a(1.0F, 1.0F);
/*   92 */     func_70012_b(px, py, pz, yaw, pitch);
/*   93 */     func_70107_b(px, py, pz);
/*   94 */     this.field_70126_B = yaw;
/*   95 */     this.field_70127_C = pitch;
/*   96 */     if (acceleration > 3.9D) {
/*   97 */       acceleration = 3.9D;
/*      */     }
/*      */     
/*  100 */     double d = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
/*  101 */     this.field_70159_w = mx * acceleration / d;
/*  102 */     this.field_70181_x = my * acceleration / d;
/*  103 */     this.field_70179_y = mz * acceleration / d;
/*  104 */     this.prevMotionX = this.field_70159_w;
/*  105 */     this.prevMotionY = this.field_70181_x;
/*  106 */     this.prevMotionZ = this.field_70179_y;
/*  107 */     this.acceleration = acceleration;
/*      */   }
/*      */   
/*      */   public void func_70012_b(double par1, double par3, double par5, float par7, float par8) {
/*  111 */     super.func_70012_b(par1, par3, par5, par7, par8);
/*  112 */     this.prevPosX2 = par1;
/*  113 */     this.prevPosY2 = par3;
/*  114 */     this.prevPosZ2 = par5;
/*      */   }
/*      */   
/*      */   public void func_70080_a(double x, double y, double z, float yaw, float pitch) {
/*  118 */     super.func_70080_a(x, y, z, yaw, pitch);
/*      */   }
/*      */   
/*      */   protected void func_70101_b(float yaw, float pitch) {
/*  122 */     super.func_70101_b(yaw, this.field_70125_A);
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void func_180426_a(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/*  127 */     func_70107_b(x, (y + this.field_70163_u * 2.0D) / 3.0D, z);
/*  128 */     func_70101_b(yaw, pitch);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_70088_a() {
/*  133 */     super.func_70088_a();
/*  134 */     this.field_70180_af.func_187214_a(TARGET_ID, Integer.valueOf(0));
/*  135 */     this.field_70180_af.func_187214_a(INFO_NAME, "");
/*  136 */     this.field_70180_af.func_187214_a(BULLET_MODEL, "");
/*  137 */     this.field_70180_af.func_187214_a(BOMBLET_FLAG, Byte.valueOf((byte)0));
/*      */   }
/*      */   @NotNull
/*      */   public String func_70005_c_() {
/*  141 */     return (String)this.field_70180_af.func_187225_a(INFO_NAME);
/*      */   }
/*      */   
/*      */   public void setName(String s) {
/*  145 */     if (s != null && !s.isEmpty()) {
/*  146 */       this.weaponInfo = MCH_WeaponInfoManager.get(s);
/*  147 */       if (this.weaponInfo != null) {
/*  148 */         if (!this.field_70170_p.field_72995_K) {
/*  149 */           this.field_70180_af.func_187227_b(INFO_NAME, s);
/*      */         }
/*      */         
/*  152 */         onSetWeasponInfo();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MCH_WeaponInfo getInfo() {
/*  159 */     return this.weaponInfo;
/*      */   }
/*      */   
/*      */   public void onSetWeasponInfo() {
/*  163 */     if (!this.field_70170_p.field_72995_K) {
/*  164 */       this.isBomblet = 0;
/*      */     }
/*      */     
/*  167 */     if ((getInfo()).bomblet > 0) {
/*  168 */       this.sprinkleTime = (getInfo()).bombletSTime;
/*      */     }
/*      */     
/*  171 */     this.piercing = (getInfo()).piercing;
/*  172 */     if (this instanceof MCH_EntityBullet) {
/*  173 */       if ((getInfo()).acceleration > 4.0F) {
/*  174 */         this.accelerationFactor = ((getInfo()).acceleration / 4.0F);
/*      */       }
/*  176 */     } else if (this instanceof MCH_EntityRocket && this.isBomblet == 0 && (getInfo()).acceleration > 4.0F) {
/*  177 */       this.accelerationFactor = ((getInfo()).acceleration / 4.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void func_70106_y() {
/*  182 */     super.func_70106_y();
/*      */   }
/*      */   
/*      */   public void setBomblet() {
/*  186 */     this.isBomblet = 1;
/*  187 */     this.sprinkleTime = 0;
/*  188 */     this.field_70180_af.func_187227_b(BOMBLET_FLAG, Byte.valueOf((byte)1));
/*      */   }
/*      */   
/*      */   public byte getBomblet() {
/*  192 */     return ((Byte)this.field_70180_af.func_187225_a(BOMBLET_FLAG)).byteValue();
/*      */   }
/*      */   
/*      */   public void setTargetEntity(@Nullable Entity entity) {
/*  196 */     this.targetEntity = entity;
/*  197 */     if (!this.field_70170_p.field_72995_K) {
/*  198 */       if (this.targetEntity instanceof EntityPlayerMP) {
/*  199 */         MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBaseBullet.setTargetEntity alert" + this.targetEntity + " / " + this.targetEntity.func_184187_bx(), new Object[0]);
/*  200 */         if (this.targetEntity.func_184187_bx() != null && 
/*  201 */           !(this.targetEntity.func_184187_bx() instanceof MCH_EntityAircraft) && 
/*  202 */           !(this.targetEntity.func_184187_bx() instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat)) {
/*  203 */           W_WorldFunc.MOD_playSoundAtEntity(this.targetEntity, "alert", 2.0F, 1.0F);
/*      */         }
/*      */       } 
/*      */       
/*  207 */       if (entity != null) {
/*  208 */         this.field_70180_af.func_187227_b(TARGET_ID, Integer.valueOf(W_Entity.getEntityId(entity)));
/*      */       } else {
/*  210 */         this.field_70180_af.func_187227_b(TARGET_ID, Integer.valueOf(0));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getTargetEntityID() {
/*  216 */     return (this.targetEntity != null) ? W_Entity.getEntityId(this.targetEntity) : ((Integer)this.field_70180_af.func_187225_a(TARGET_ID)).intValue();
/*      */   }
/*      */   
/*      */   public MCH_BulletModel getBulletModel() {
/*  220 */     if (getInfo() == null)
/*  221 */       return null; 
/*  222 */     if (this.isBomblet < 0) {
/*  223 */       return null;
/*      */     }
/*  225 */     if (this.model == null) {
/*  226 */       if (this.isBomblet == 1) {
/*  227 */         this.model = (getInfo()).bombletModel;
/*      */       } else {
/*  229 */         this.model = (getInfo()).bulletModel;
/*      */       } 
/*      */       
/*  232 */       if (this.model == null) {
/*  233 */         this.model = getDefaultBulletModel();
/*      */       }
/*      */     } 
/*      */     
/*  237 */     return this.model;
/*      */   }
/*      */ 
/*      */   
/*      */   public abstract MCH_BulletModel getDefaultBulletModel();
/*      */ 
/*      */   
/*      */   public void sprinkleBomblet() {}
/*      */   
/*      */   public void spawnParticle(String name, int num, float size) {
/*  247 */     if (this.field_70170_p.field_72995_K) {
/*  248 */       if (name.isEmpty() || num < 1 || num > 50) {
/*      */         return;
/*      */       }
/*      */       
/*  252 */       double x = (this.field_70165_t - this.field_70169_q) / num;
/*  253 */       double y = (this.field_70163_u - this.field_70167_r) / num;
/*  254 */       double z = (this.field_70161_v - this.field_70166_s) / num;
/*  255 */       double x2 = (this.field_70169_q - this.prevPosX2) / num;
/*  256 */       double y2 = (this.field_70167_r - this.prevPosY2) / num;
/*  257 */       double z2 = (this.field_70166_s - this.prevPosZ2) / num;
/*  258 */       if (name.equals("explode")) {
/*  259 */         for (int i = 0; i < num; i++) {
/*  260 */           MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", (this.field_70169_q + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.field_70167_r + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.field_70166_s + z * i + this.prevPosZ2 + z2 * i) / 2.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  267 */           prm.size = size + this.field_70146_Z.nextFloat();
/*  268 */           MCH_ParticlesUtil.spawnParticle(prm);
/*      */         } 
/*      */       } else {
/*  271 */         for (int i = 0; i < num; i++) {
/*  272 */           MCH_ParticlesUtil.DEF_spawnParticle(name, (this.field_70169_q + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.field_70167_r + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.field_70166_s + z * i + this.prevPosZ2 + z2 * i) / 2.0D, 0.0D, 0.0D, 0.0D, 50.0F, new int[0]);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void DEF_spawnParticle(String name, int num, float size) {
/*  288 */     if (this.field_70170_p.field_72995_K) {
/*  289 */       if (name.isEmpty() || num < 1 || num > 50) {
/*      */         return;
/*      */       }
/*      */       
/*  293 */       double x = (this.field_70165_t - this.field_70169_q) / num;
/*  294 */       double y = (this.field_70163_u - this.field_70167_r) / num;
/*  295 */       double z = (this.field_70161_v - this.field_70166_s) / num;
/*  296 */       double x2 = (this.field_70169_q - this.prevPosX2) / num;
/*  297 */       double y2 = (this.field_70167_r - this.prevPosY2) / num;
/*  298 */       double z2 = (this.field_70166_s - this.prevPosZ2) / num;
/*      */       
/*  300 */       for (int i = 0; i < num; i++) {
/*  301 */         MCH_ParticlesUtil.DEF_spawnParticle(name, (this.field_70169_q + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.field_70167_r + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.field_70166_s + z * i + this.prevPosZ2 + z2 * i) / 2.0D, 0.0D, 0.0D, 0.0D, 150.0F, new int[0]);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCountOnUpdate() {
/*  316 */     return this.countOnUpdate;
/*      */   }
/*      */   
/*      */   public void clearCountOnUpdate() {
/*  320 */     this.countOnUpdate = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public boolean func_70112_a(double par1) {
/*  326 */     double d1 = func_174813_aQ().func_72320_b() * 4.0D;
/*  327 */     d1 *= 64.0D;
/*  328 */     return (par1 < d1 * d1);
/*      */   }
/*      */   
/*      */   public void setParameterFromWeapon(MCH_WeaponBase w, Entity entity, Entity user) {
/*  332 */     this.explosionPower = w.explosionPower;
/*  333 */     this.explosionPowerInWater = w.explosionPowerInWater;
/*  334 */     setPower(w.power);
/*  335 */     this.piercing = w.piercing;
/*  336 */     this.shootingAircraft = entity;
/*  337 */     this.shootingEntity = user;
/*      */   }
/*      */   
/*      */   public void setParameterFromWeapon(MCH_EntityBaseBullet b, Entity entity, Entity user) {
/*  341 */     this.explosionPower = b.explosionPower;
/*  342 */     this.explosionPowerInWater = b.explosionPowerInWater;
/*  343 */     setPower(b.getPower());
/*  344 */     this.piercing = b.piercing;
/*  345 */     this.shootingAircraft = entity;
/*  346 */     this.shootingEntity = user;
/*      */   }
/*      */   
/*      */   public void setMotion(double targetX, double targetY, double targetZ) {
/*  350 */     double d6 = MathHelper.func_76133_a(targetX * targetX + targetY * targetY + targetZ * targetZ);
/*  351 */     this.field_70159_w = targetX * this.acceleration / d6;
/*  352 */     this.field_70181_x = targetY * this.acceleration / d6;
/*  353 */     this.field_70179_y = targetZ * this.acceleration / d6;
/*      */   }
/*      */   
/*      */   public boolean usingFlareOfTarget(Entity entity) {
/*  357 */     if (getCountOnUpdate() % 3 == 0) {
/*  358 */       List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, entity.func_174813_aQ().func_72314_b(15.0D, 15.0D, 15.0D));
/*      */       
/*  360 */       for (Entity value : list) {
/*  361 */         if (value.getEntityData().func_74767_n("FlareUsing")) {
/*  362 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  367 */     return false;
/*      */   }
/*      */   
/*      */   public void guidanceToTarget(double targetPosX, double targetPosY, double targetPosZ) {
/*  371 */     guidanceToTarget(targetPosX, targetPosY, targetPosZ, 1.0F);
/*      */   }
/*      */   
/*      */   public void guidanceToTarget(double targetPosX, double targetPosY, double targetPosZ, float accelerationFactor) {
/*  375 */     double tx = targetPosX - this.field_70165_t;
/*  376 */     double ty = targetPosY - this.field_70163_u;
/*  377 */     double tz = targetPosZ - this.field_70161_v;
/*  378 */     double d = MathHelper.func_76133_a(tx * tx + ty * ty + tz * tz);
/*  379 */     double mx = tx * this.acceleration / d;
/*  380 */     double my = ty * this.acceleration / d;
/*  381 */     double mz = tz * this.acceleration / d;
/*  382 */     this.field_70159_w = (this.field_70159_w * 6.0D + mx) / 7.0D;
/*  383 */     this.field_70181_x = (this.field_70181_x * 6.0D + my) / 7.0D;
/*  384 */     this.field_70179_y = (this.field_70179_y * 6.0D + mz) / 7.0D;
/*  385 */     double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
/*  386 */     this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
/*  387 */     double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*  388 */     this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
/*      */   }
/*      */   
/*      */   public boolean checkValid() {
/*  392 */     if (this.shootingEntity == null && this.shootingAircraft == null) {
/*  393 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  397 */     Entity shooter = (this.shootingAircraft != null && this.shootingAircraft.field_70128_L && this.shootingEntity == null) ? this.shootingAircraft : this.shootingEntity;
/*  398 */     double x = this.field_70165_t - shooter.field_70165_t;
/*  399 */     double z = this.field_70161_v - shooter.field_70161_v;
/*  400 */     return (x * x + z * z < 3.38724E7D && this.field_70163_u > -10.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getGravity() {
/*  405 */     return (getInfo() != null) ? (getInfo()).gravity : 0.0F;
/*      */   }
/*      */   
/*      */   public float getGravityInWater() {
/*  409 */     return (getInfo() != null) ? (getInfo()).gravityInWater : 0.0F;
/*      */   }
/*      */   
/*      */   public void func_70071_h_() {
/*  413 */     if (this.field_70170_p.field_72995_K && this.countOnUpdate == 0) {
/*  414 */       int tgtEttId = getTargetEntityID();
/*  415 */       if (tgtEttId > 0) {
/*  416 */         setTargetEntity(this.field_70170_p.func_73045_a(tgtEttId));
/*      */       }
/*      */     } 
/*      */     
/*  420 */     if (!this.field_70170_p.field_72995_K && getCountOnUpdate() % 20 == 19 && this.targetEntity instanceof EntityPlayerMP) {
/*  421 */       MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBaseBullet.onUpdate alert" + this.targetEntity + " / " + this.targetEntity.func_184187_bx(), new Object[0]);
/*  422 */       if (this.targetEntity.func_184187_bx() != null && 
/*  423 */         !(this.targetEntity.func_184187_bx() instanceof MCH_EntityAircraft) && 
/*  424 */         !(this.targetEntity.func_184187_bx() instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat)) {
/*  425 */         W_WorldFunc.MOD_playSoundAtEntity(this.targetEntity, "alert", 2.0F, 1.0F);
/*      */       }
/*      */     } 
/*      */     
/*  429 */     this.prevMotionX = this.field_70159_w;
/*  430 */     this.prevMotionY = this.field_70181_x;
/*  431 */     this.prevMotionZ = this.field_70179_y;
/*  432 */     this.countOnUpdate++;
/*  433 */     if (this.countOnUpdate > 10000000) {
/*  434 */       clearCountOnUpdate();
/*      */     }
/*      */     
/*  437 */     this.prevPosX2 = this.field_70169_q;
/*  438 */     this.prevPosY2 = this.field_70167_r;
/*  439 */     this.prevPosZ2 = this.field_70166_s;
/*  440 */     super.func_70071_h_();
/*  441 */     if ((this.prevMotionX != this.field_70159_w || this.prevMotionY != this.field_70181_x || this.prevMotionZ != this.field_70179_y) && this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y > 0.1D) {
/*      */       
/*  443 */       double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
/*  444 */       this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
/*  445 */       double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*  446 */       this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
/*      */     } 
/*      */     
/*  449 */     if (getInfo() == null) {
/*  450 */       if (this.countOnUpdate >= 2) {
/*  451 */         MCH_Lib.Log((Entity)this, "##### MCH_EntityBaseBullet onUpdate() Weapon info null %d, %s, Name=%s", new Object[] {
/*  452 */               Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName(), func_70005_c_()
/*      */             });
/*  454 */         func_70106_y();
/*      */         
/*      */         return;
/*      */       } 
/*  458 */       setName(func_70005_c_());
/*  459 */       if (getInfo() == null) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/*  464 */     if ((getInfo()).bound <= 0.0F && this.field_70122_E) {
/*  465 */       this.field_70159_w *= 0.9D;
/*  466 */       this.field_70179_y *= 0.9D;
/*      */     } 
/*      */     
/*  469 */     if (this.field_70170_p.field_72995_K && this.isBomblet < 0) {
/*  470 */       this.isBomblet = getBomblet();
/*      */     }
/*      */     
/*  473 */     if (!this.field_70170_p.field_72995_K) {
/*  474 */       BlockPos blockpos = new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*  475 */       if ((int)this.field_70163_u <= 255 && !this.field_70170_p.func_175667_e(blockpos)) {
/*  476 */         if ((getInfo()).delayFuse <= 0) {
/*  477 */           func_70106_y();
/*      */           
/*      */           return;
/*      */         } 
/*  481 */         if (this.delayFuse == 0) {
/*  482 */           this.delayFuse = (getInfo()).delayFuse;
/*      */         }
/*      */       } 
/*      */       
/*  486 */       if (this.delayFuse > 0) {
/*  487 */         this.delayFuse--;
/*  488 */         if (this.delayFuse == 0) {
/*  489 */           onUpdateTimeout();
/*  490 */           func_70106_y();
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  495 */       if (!checkValid()) {
/*  496 */         func_70106_y();
/*      */         
/*      */         return;
/*      */       } 
/*  500 */       if ((getInfo()).timeFuse > 0 && getCountOnUpdate() > (getInfo()).timeFuse) {
/*  501 */         onUpdateTimeout();
/*  502 */         func_70106_y();
/*      */         
/*      */         return;
/*      */       } 
/*  506 */       if ((getInfo()).explosionAltitude > 0 && MCH_Lib.getBlockIdY((Entity)this, 3, -(getInfo()).explosionAltitude) != 0) {
/*  507 */         RayTraceResult mop = new RayTraceResult(new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v), EnumFacing.DOWN, new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v));
/*  508 */         onImpact(mop, 1.0F);
/*      */       } 
/*      */     } 
/*      */     
/*  512 */     if (!func_70090_H()) {
/*  513 */       this.field_70181_x += getGravity();
/*      */     } else {
/*  515 */       this.field_70181_x += getGravityInWater();
/*      */     } 
/*      */     
/*  518 */     if (!this.field_70128_L) {
/*  519 */       onUpdateCollided();
/*      */     }
/*      */     
/*  522 */     this.field_70165_t += this.field_70159_w * this.accelerationFactor;
/*  523 */     this.field_70163_u += this.field_70181_x * this.accelerationFactor;
/*  524 */     this.field_70161_v += this.field_70179_y * this.accelerationFactor;
/*  525 */     if (this.field_70170_p.field_72995_K) {
/*  526 */       updateSplash();
/*      */     }
/*      */     
/*  529 */     if (func_70090_H()) {
/*  530 */       float f3 = 0.25F;
/*  531 */       this.field_70170_p
/*  532 */         .func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t - this.field_70159_w * f3, this.field_70163_u - this.field_70181_x * f3, this.field_70161_v - this.field_70179_y * f3, this.field_70159_w, this.field_70181_x, this.field_70179_y, new int[0]);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  543 */     func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*      */   }
/*      */   
/*      */   public void updateSplash() {
/*  547 */     if (getInfo() != null && 
/*  548 */       (getInfo()).power > 0 && 
/*  549 */       !W_WorldFunc.isBlockWater(this.field_70170_p, (int)(this.field_70169_q + 0.5D), (int)(this.field_70167_r + 0.5D), (int)(this.field_70166_s + 0.5D)) && 
/*  550 */       W_WorldFunc.isBlockWater(this.field_70170_p, (int)(this.field_70165_t + 0.5D), (int)(this.field_70163_u + 0.5D), (int)(this.field_70161_v + 0.5D))) {
/*  551 */       double x = this.field_70165_t - this.field_70169_q;
/*  552 */       double y = this.field_70163_u - this.field_70167_r;
/*  553 */       double z = this.field_70161_v - this.field_70166_s;
/*  554 */       double d = Math.sqrt(x * x + y * y + z * z);
/*  555 */       if (d <= 0.15D) {
/*      */         return;
/*      */       }
/*      */       
/*  559 */       x /= d;
/*  560 */       y /= d;
/*  561 */       z /= d;
/*  562 */       double px = this.field_70169_q;
/*  563 */       double py = this.field_70167_r;
/*  564 */       double pz = this.field_70166_s;
/*      */       
/*  566 */       for (int i = 0; i <= d; i++) {
/*  567 */         px += x;
/*  568 */         py += y;
/*  569 */         pz += z;
/*  570 */         if (W_WorldFunc.isBlockWater(this.field_70170_p, (int)(px + 0.5D), (int)(py + 0.5D), (int)(pz + 0.5D))) {
/*  571 */           float pwr = ((getInfo()).power < 20) ? (getInfo()).power : 20.0F;
/*  572 */           int n = this.field_70146_Z.nextInt(1 + (int)pwr / 3) + (int)pwr / 2 + 1;
/*  573 */           pwr *= 0.03F;
/*      */           
/*  575 */           for (int j = 0; j < n; j++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  584 */             MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "splash", px, py + 0.5D, pz, pwr * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D, pwr * (this.field_70146_Z.nextDouble() * 0.5D + 0.5D) * 1.8D, pwr * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D, pwr * 5.0F);
/*      */ 
/*      */             
/*  587 */             MCH_ParticlesUtil.spawnParticle(prm);
/*      */           } 
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdateTimeout() {
/*  598 */     if (func_70090_H()) {
/*  599 */       if (this.explosionPowerInWater > 0) {
/*  600 */         newExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.explosionPowerInWater, this.explosionPowerInWater, true);
/*      */       }
/*  602 */     } else if (this.explosionPower > 0) {
/*  603 */       newExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.explosionPower, (getInfo()).explosionBlock, false);
/*  604 */     } else if (this.explosionPower < 0) {
/*  605 */       playExplosionSound();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onUpdateBomblet() {
/*  610 */     if (!this.field_70170_p.field_72995_K && this.sprinkleTime > 0 && !this.field_70128_L) {
/*  611 */       this.sprinkleTime--;
/*  612 */       if (this.sprinkleTime == 0) {
/*  613 */         for (int i = 0; i < (getInfo()).bomblet; i++) {
/*  614 */           sprinkleBomblet();
/*      */         }
/*      */         
/*  617 */         func_70106_y();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void boundBullet(EnumFacing sideHit) {
/*  623 */     switch (sideHit) {
/*      */       case DOWN:
/*  625 */         if (this.field_70181_x > 0.0D) {
/*  626 */           this.field_70181_x = -this.field_70181_x * (getInfo()).bound;
/*      */         }
/*      */         break;
/*      */       case UP:
/*  630 */         if (this.field_70181_x < 0.0D) {
/*  631 */           this.field_70181_x = -this.field_70181_x * (getInfo()).bound;
/*      */         }
/*      */         break;
/*      */       case NORTH:
/*  635 */         if (this.field_70179_y > 0.0D) {
/*  636 */           this.field_70179_y = -this.field_70179_y * (getInfo()).bound; break;
/*      */         } 
/*  638 */         this.field_70161_v += this.field_70179_y;
/*      */         break;
/*      */       
/*      */       case SOUTH:
/*  642 */         if (this.field_70179_y < 0.0D) {
/*  643 */           this.field_70179_y = -this.field_70179_y * (getInfo()).bound; break;
/*      */         } 
/*  645 */         this.field_70161_v += this.field_70179_y;
/*      */         break;
/*      */       
/*      */       case WEST:
/*  649 */         if (this.field_70159_w > 0.0D) {
/*  650 */           this.field_70159_w = -this.field_70159_w * (getInfo()).bound; break;
/*      */         } 
/*  652 */         this.field_70165_t += this.field_70159_w;
/*      */         break;
/*      */       
/*      */       case EAST:
/*  656 */         if (this.field_70159_w < 0.0D) {
/*  657 */           this.field_70159_w = -this.field_70159_w * (getInfo()).bound; break;
/*      */         } 
/*  659 */         this.field_70165_t += this.field_70159_w;
/*      */         break;
/*      */     } 
/*      */     
/*  663 */     if ((getInfo()).bound <= 0.0F) {
/*  664 */       this.field_70159_w *= 0.25D;
/*  665 */       this.field_70181_x *= 0.25D;
/*  666 */       this.field_70179_y *= 0.25D;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onUpdateCollided() {
/*  671 */     float damageFator = 1.0F;
/*  672 */     double mx = this.field_70159_w * this.accelerationFactor;
/*  673 */     double my = this.field_70181_x * this.accelerationFactor;
/*  674 */     double mz = this.field_70179_y * this.accelerationFactor;
/*  675 */     RayTraceResult m = null;
/*      */     
/*  677 */     for (int i = 0; i < 5; i++) {
/*  678 */       Vec3d vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*  679 */       Vec3d vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + mx, this.field_70163_u + my, this.field_70161_v + mz);
/*  680 */       m = W_WorldFunc.clip(this.field_70170_p, vec3, vec31);
/*  681 */       boolean continueClip = false;
/*  682 */       if (this.shootingEntity != null && W_MovingObjectPosition.isHitTypeTile(m)) {
/*  683 */         Block block = W_WorldFunc.getBlock(this.field_70170_p, m.func_178782_a());
/*  684 */         if (MCH_Config.bulletBreakableBlocks.contains(block)) {
/*  685 */           W_WorldFunc.destroyBlock(this.field_70170_p, m.func_178782_a(), true);
/*  686 */           continueClip = true;
/*      */         } 
/*      */       } 
/*      */       
/*  690 */       if (!continueClip) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/*  695 */     Vec3d vec3x = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*  696 */     Vec3d vec31x = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + mx, this.field_70163_u + my, this.field_70161_v + mz);
/*  697 */     if ((getInfo()).delayFuse > 0) {
/*  698 */       if (m != null) {
/*  699 */         boundBullet(m.field_178784_b);
/*  700 */         if (this.delayFuse == 0) {
/*  701 */           this.delayFuse = (getInfo()).delayFuse;
/*      */         }
/*      */       } 
/*      */     } else {
/*  705 */       if (m != null) {
/*  706 */         vec31x = W_WorldFunc.getWorldVec3(this.field_70170_p, m.field_72307_f.field_72450_a, m.field_72307_f.field_72448_b, m.field_72307_f.field_72449_c);
/*      */       }
/*      */       
/*  709 */       Entity entity = null;
/*  710 */       List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72321_a(mx, my, mz).func_72314_b(21.0D, 21.0D, 21.0D));
/*  711 */       double d0 = 0.0D;
/*      */       
/*  713 */       for (Entity entity1 : list) {
/*  714 */         if (canBeCollidedEntity(entity1)) {
/*  715 */           float f = 0.3F;
/*  716 */           AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_72314_b(f, f, f);
/*  717 */           RayTraceResult m1 = axisalignedbb.func_72327_a(vec3x, vec31x);
/*  718 */           if (m1 != null) {
/*  719 */             double d1 = vec3x.func_72438_d(m1.field_72307_f);
/*  720 */             if (d1 < d0 || d0 == 0.0D) {
/*  721 */               entity = entity1;
/*  722 */               d0 = d1;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  728 */       if (entity != null) {
/*  729 */         m = new RayTraceResult(entity);
/*      */       }
/*      */       
/*  732 */       if (m != null) {
/*  733 */         onImpact(m, damageFator);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean canBeCollidedEntity(Entity entity) {
/*  739 */     if (entity instanceof com.norwood.mcheli.chain.MCH_EntityChain)
/*  740 */       return false; 
/*  741 */     if (!entity.func_70067_L()) {
/*  742 */       return false;
/*      */     }
/*  744 */     if (entity instanceof MCH_EntityBaseBullet) { MCH_EntityBaseBullet blt = (MCH_EntityBaseBullet)entity;
/*  745 */       if (this.field_70170_p.field_72995_K) {
/*  746 */         return false;
/*      */       }
/*      */       
/*  749 */       if (W_Entity.isEqual(blt.shootingAircraft, this.shootingAircraft)) {
/*  750 */         return false;
/*      */       }
/*      */       
/*  753 */       if (W_Entity.isEqual(blt.shootingEntity, this.shootingEntity)) {
/*  754 */         return false;
/*      */       } }
/*      */ 
/*      */     
/*  758 */     if (entity instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat)
/*  759 */       return false; 
/*  760 */     if (entity instanceof com.norwood.mcheli.aircraft.MCH_EntityHitBox)
/*  761 */       return false; 
/*  762 */     if (W_Entity.isEqual(entity, this.shootingEntity)) {
/*  763 */       return false;
/*      */     }
/*  765 */     if (this.shootingAircraft instanceof MCH_EntityAircraft) {
/*  766 */       if (W_Entity.isEqual(entity, this.shootingAircraft)) {
/*  767 */         return false;
/*      */       }
/*      */       
/*  770 */       if (((MCH_EntityAircraft)this.shootingAircraft).isMountedEntity(entity)) {
/*  771 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  775 */     for (String s : MCH_Config.IgnoreBulletHitList) {
/*  776 */       if (entity.getClass().getName().toLowerCase().contains(s.toLowerCase())) {
/*  777 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  781 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void notifyHitBullet() {
/*  787 */     if (this.shootingAircraft instanceof MCH_EntityAircraft && W_EntityPlayer.isPlayer(this.shootingEntity)) {
/*  788 */       MCH_PacketNotifyHitBullet.send((MCH_EntityAircraft)this.shootingAircraft, (EntityPlayer)this.shootingEntity);
/*      */     }
/*      */     
/*  791 */     if (W_EntityPlayer.isPlayer(this.shootingEntity)) {
/*  792 */       MCH_PacketNotifyHitBullet.send(null, (EntityPlayer)this.shootingEntity);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onImpact(RayTraceResult m, float damageFactor) {
/*  798 */     if (!this.field_70170_p.field_72995_K) {
/*      */       
/*      */       try {
/*  801 */         if ((getInfo()).chemYield > 0) {
/*  802 */           System.out.println("chem yield detected");
/*  803 */           MCH_HBMUtil.ExplosionChaos_spawnChlorine(this.field_70170_p, this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v, (getInfo()).chemYield, (getInfo()).chemSpeed, (getInfo()).chemType);
/*      */         } 
/*  805 */       } catch (Exception e) {
/*  806 */         MCH_Lib.Log((Entity)this, "Error in onImpact: %s", new Object[] { e.getMessage() });
/*  807 */         e.printStackTrace();
/*      */       } 
/*      */       
/*  810 */       if (m.field_72308_g != null) {
/*  811 */         onImpactEntity(m.field_72308_g, damageFactor);
/*  812 */         this.piercing = 0;
/*      */       } 
/*      */       
/*  815 */       float expPower = this.explosionPower * damageFactor;
/*  816 */       float expPowerInWater = this.explosionPowerInWater * damageFactor;
/*  817 */       double dx = 0.0D;
/*  818 */       double dy = 0.0D;
/*  819 */       double dz = 0.0D;
/*  820 */       if (this.piercing > 0) {
/*  821 */         this.piercing--;
/*  822 */         if (expPower > 0.0F) {
/*  823 */           newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, 1.0F, 1.0F, false);
/*      */         }
/*      */       } else {
/*  826 */         if (expPowerInWater == 0.0F) {
/*  827 */           if ((getInfo()).isFAE) {
/*  828 */             newFAExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, expPower, (getInfo()).explosionBlock);
/*  829 */           } else if (expPower > 0.0F) {
/*  830 */             newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPower, (getInfo()).explosionBlock, false);
/*  831 */           } else if (expPower < 0.0F) {
/*  832 */             playExplosionSound();
/*      */           } 
/*  834 */         } else if (m.field_72308_g != null) {
/*  835 */           if (func_70090_H()) {
/*  836 */             newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPowerInWater, expPowerInWater, true);
/*      */           } else {
/*  838 */             newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPower, (getInfo()).explosionBlock, false);
/*      */           } 
/*  840 */         } else if (func_70090_H() || 
/*  841 */           MCH_Lib.isBlockInWater(this.field_70170_p, m.func_178782_a().func_177958_n(), m.func_178782_a().func_177956_o(), m.func_178782_a().func_177952_p())) {
/*  842 */           newExplosion(m
/*  843 */               .func_178782_a().func_177958_n(), m.func_178782_a().func_177956_o(), m.func_178782_a().func_177952_p(), expPowerInWater, expPowerInWater, true);
/*      */         }
/*  845 */         else if (expPower > 0.0F) {
/*  846 */           newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPower, (getInfo()).explosionBlock, false);
/*  847 */         } else if (expPower < 0.0F) {
/*  848 */           playExplosionSound();
/*      */         } 
/*      */         
/*  851 */         func_70106_y();
/*      */       } 
/*  853 */     } else if (getInfo() != null && ((getInfo()).explosion == 0 || (getInfo()).modeNum >= 2) && W_MovingObjectPosition.isHitTypeTile(m)) {
/*  854 */       float p = (getInfo()).power;
/*      */       
/*  856 */       for (int i = 0; i < p / 3.0F; i++) {
/*  857 */         MCH_ParticlesUtil.spawnParticleTileCrack(this.field_70170_p, m
/*      */             
/*  859 */             .func_178782_a().func_177958_n(), m
/*  860 */             .func_178782_a().func_177956_o(), m
/*  861 */             .func_178782_a().func_177952_p(), m.field_72307_f.field_72450_a + (this.field_70146_Z
/*  862 */             .nextFloat() - 0.5D) * p / 10.0D, m.field_72307_f.field_72448_b + 0.1D, m.field_72307_f.field_72449_c + (this.field_70146_Z
/*      */             
/*  864 */             .nextFloat() - 0.5D) * p / 10.0D, -this.field_70159_w * p / 2.0D, (p / 2.0F), -this.field_70179_y * p / 2.0D);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onImpactEntity(Entity entity, float damageFactor) {
/*  874 */     if (!entity.field_70128_L) {
/*  875 */       MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBaseBullet.onImpactEntity:Damage=%d:" + entity.getClass(), new Object[] { Integer.valueOf(getPower()) });
/*  876 */       MCH_Lib.applyEntityHurtResistantTimeConfig(entity);
/*  877 */       DamageSource ds = DamageSource.func_76356_a((Entity)this, this.shootingEntity);
/*  878 */       float damage = MCH_Config.applyDamageVsEntity(entity, ds, getPower() * damageFactor);
/*  879 */       damage *= (getInfo() != null) ? getInfo().getDamageFactor(entity) : 1.0F;
/*  880 */       entity.func_70097_a(ds, damage);
/*  881 */       if (this instanceof MCH_EntityBullet && entity instanceof net.minecraft.entity.passive.EntityVillager && this.shootingEntity != null && this.shootingEntity instanceof EntityPlayerMP && this.shootingEntity
/*      */ 
/*      */ 
/*      */         
/*  885 */         .func_184187_bx() instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat) {
/*  886 */         MCH_CriteriaTriggers.VILLAGER_HURT_BULLET.trigger((EntityPlayerMP)this.shootingEntity);
/*      */       }
/*      */       
/*  889 */       if (!entity.field_70128_L);
/*      */     } 
/*      */ 
/*      */     
/*  893 */     notifyHitBullet();
/*      */   }
/*      */   
/*      */   public void newFAExplosion(double x, double y, double z, float exp, float expBlock) {
/*  897 */     MCH_Explosion.ExplosionResult result = MCH_Explosion.newExplosion(this.field_70170_p, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, true, true, 
/*  898 */         (getInfo()).flaming, false, 15);
/*      */     
/*  900 */     if (result != null && result.hitEntity) {
/*  901 */       notifyHitBullet();
/*      */     }
/*      */   }
/*      */   
/*      */   public void newExplosion(double x, double y, double z, float exp, float expBlock, boolean inWater) {
/*      */     MCH_Explosion.ExplosionResult result;
/*  907 */     if (!inWater) {
/*  908 */       result = MCH_Explosion.newExplosion(this.field_70170_p, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  917 */           (this.field_70146_Z.nextInt(3) == 0), true, 
/*      */           
/*  919 */           (getInfo()).flaming, true, 0, 
/*      */ 
/*      */           
/*  922 */           (getInfo() != null) ? (getInfo()).damageFactor : null);
/*      */     } else {
/*      */       
/*  925 */       result = MCH_Explosion.newExplosionInWater(this.field_70170_p, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  934 */           (this.field_70146_Z.nextInt(3) == 0), true, 
/*      */           
/*  936 */           (getInfo()).flaming, true, 0, 
/*      */ 
/*      */           
/*  939 */           (getInfo() != null) ? (getInfo()).damageFactor : null);
/*      */     } 
/*      */ 
/*      */     
/*  943 */     if ((getInfo()).nukeYield > 0) {
/*  944 */       if (!(getInfo()).nukeEffectOnly) {
/*  945 */         this.field_70170_p.func_72838_d((Entity)MCH_HBMUtil.EntityNukeExplosionMK5_statFac(this.field_70170_p, (getInfo()).nukeYield, this.field_70165_t + 0.5D, this.field_70163_u + 0.5D, this.field_70161_v + 0.5D));
/*      */       }
/*  947 */       MCH_HBMUtil.EntityNukeTorex_statFac(this.field_70170_p, this.field_70165_t + 0.5D, this.field_70163_u + 0.5D, this.field_70161_v + 0.5D, (getInfo()).nukeYield);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  956 */     if (result != null && result.hitEntity) {
/*  957 */       notifyHitBullet();
/*      */     }
/*      */   }
/*      */   
/*      */   public void playExplosionSound() {
/*  962 */     MCH_Explosion.playExplosionSound(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*      */   }
/*      */   
/*      */   public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/*  966 */     par1NBTTagCompound.func_74782_a("direction", (NBTBase)func_70087_a(new double[] { this.field_70159_w, this.field_70181_x, this.field_70179_y }));
/*  967 */     par1NBTTagCompound.func_74778_a("WeaponName", func_70005_c_());
/*      */   }
/*      */   
/*      */   public void func_70037_a(@NotNull NBTTagCompound par1NBTTagCompound) {
/*  971 */     func_70106_y();
/*      */   }
/*      */   
/*      */   public boolean func_70067_L() {
/*  975 */     return true;
/*      */   }
/*      */   
/*      */   public float func_70111_Y() {
/*  979 */     return 1.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_70097_a(DamageSource ds, float par2) {
/*  984 */     if (func_180431_b(ds))
/*  985 */       return false; 
/*  986 */     if (!this.field_70170_p.field_72995_K && par2 > 0.0F && ds.func_76355_l().equalsIgnoreCase("thrown")) {
/*  987 */       func_70018_K();
/*  988 */       Vec3d pos = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*  989 */       RayTraceResult m = new RayTraceResult(pos, EnumFacing.DOWN, new BlockPos(pos));
/*  990 */       onImpact(m, 1.0F);
/*  991 */       return true;
/*      */     } 
/*  993 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public float getShadowSize() {
/*  999 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public float getBrightness(float par1) {
/* 1003 */     return 1.0F;
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public int getBrightnessForRender(float par1) {
/* 1008 */     return 15728880;
/*      */   }
/*      */   
/*      */   public int getPower() {
/* 1012 */     return this.power;
/*      */   }
/*      */   
/*      */   public void setPower(int power) {
/* 1016 */     this.power = power;
/*      */   }
/*      */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityBaseBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */