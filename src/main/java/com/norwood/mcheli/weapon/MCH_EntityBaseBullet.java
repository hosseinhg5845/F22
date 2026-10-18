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
/*   41 */   private static final DataParameter<Integer> TARGET_ID = EntityDataManager.createKey(MCH_EntityBaseBullet.class, DataSerializers.VARINT);
/*   42 */   private static final DataParameter<String> INFO_NAME = EntityDataManager.createKey(MCH_EntityBaseBullet.class, DataSerializers.STRING);
/*   43 */   private static final DataParameter<String> BULLET_MODEL = EntityDataManager.createKey(MCH_EntityBaseBullet.class, DataSerializers.STRING);
/*   44 */   private static final DataParameter<Byte> BOMBLET_FLAG = EntityDataManager.createKey(MCH_EntityBaseBullet.class, DataSerializers.BYTE);
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
/*   69 */     setSize(1.0F, 1.0F);
/*   70 */     this.prevRotationYaw = this.rotationYaw;
/*   71 */     this.prevRotationPitch = this.rotationPitch;
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
/*   83 */     this.ignoreFrustumCheck = true;
/*   84 */     if (par1World.isRemote) {
/*   85 */       this.model = null;
/*      */     }
/*      */   }
/*      */   
/*      */   public MCH_EntityBaseBullet(World par1World, double px, double py, double pz, double mx, double my, double mz, float yaw, float pitch, double acceleration) {
/*   90 */     this(par1World);
/*   91 */     setSize(1.0F, 1.0F);
/*   92 */     setLocationAndAngles(px, py, pz, yaw, pitch);
/*   93 */     setPosition(px, py, pz);
/*   94 */     this.prevRotationYaw = yaw;
/*   95 */     this.prevRotationPitch = pitch;
/*   96 */     if (acceleration > 3.9D) {
/*   97 */       acceleration = 3.9D;
/*      */     }
/*      */     
/*  100 */     double d = MathHelper.sqrt(mx * mx + my * my + mz * mz);
/*  101 */     this.motionX = mx * acceleration / d;
/*  102 */     this.motionY = my * acceleration / d;
/*  103 */     this.motionZ = mz * acceleration / d;
/*  104 */     this.prevMotionX = this.motionX;
/*  105 */     this.prevMotionY = this.motionY;
/*  106 */     this.prevMotionZ = this.motionZ;
/*  107 */     this.acceleration = acceleration;
/*      */   }
/*      */   
/*      */   public void setLocationAndAngles(double par1, double par3, double par5, float par7, float par8) {
/*  111 */     super.setLocationAndAngles(par1, par3, par5, par7, par8);
/*  112 */     this.prevPosX2 = par1;
/*  113 */     this.prevPosY2 = par3;
/*  114 */     this.prevPosZ2 = par5;
/*      */   }
/*      */   
/*      */   public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
/*  118 */     super.setPositionAndRotation(x, y, z, yaw, pitch);
/*      */   }
/*      */   
/*      */   protected void setRotation(float yaw, float pitch) {
/*  122 */     super.setRotation(yaw, this.rotationPitch);
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/*  127 */     setPosition(x, (y + this.posY * 2.0D) / 3.0D, z);
/*  128 */     setRotation(yaw, pitch);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  133 */     super.entityInit();
/*  134 */     this.dataManager.register(TARGET_ID, Integer.valueOf(0));
/*  135 */     this.dataManager.register(INFO_NAME, "");
/*  136 */     this.dataManager.register(BULLET_MODEL, "");
/*  137 */     this.dataManager.register(BOMBLET_FLAG, Byte.valueOf((byte)0));
/*      */   }
/*      */   @NotNull
/*      */   public String getName() {
/*  141 */     return (String)this.dataManager.get(INFO_NAME);
/*      */   }
/*      */   
/*      */   public void setName(String s) {
/*  145 */     if (s != null && !s.isEmpty()) {
/*  146 */       this.weaponInfo = MCH_WeaponInfoManager.get(s);
/*  147 */       if (this.weaponInfo != null) {
/*  148 */         if (!this.world.isRemote) {
/*  149 */           this.dataManager.set(INFO_NAME, s);
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
/*  163 */     if (!this.world.isRemote) {
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
/*      */   public void setDead() {
/*  182 */     super.setDead();
/*      */   }
/*      */   
/*      */   public void setBomblet() {
/*  186 */     this.isBomblet = 1;
/*  187 */     this.sprinkleTime = 0;
/*  188 */     this.dataManager.set(BOMBLET_FLAG, Byte.valueOf((byte)1));
/*      */   }
/*      */   
/*      */   public byte getBomblet() {
/*  192 */     return ((Byte)this.dataManager.get(BOMBLET_FLAG)).byteValue();
/*      */   }
/*      */   
/*      */   public void setTargetEntity(@Nullable Entity entity) {
/*  196 */     this.targetEntity = entity;
/*  197 */     if (!this.world.isRemote) {
/*  198 */       if (this.targetEntity instanceof EntityPlayerMP) {
/*  199 */         MCH_Lib.DbgLog(this.world, "MCH_EntityBaseBullet.setTargetEntity alert" + this.targetEntity + " / " + this.targetEntity.getRidingEntity(), new Object[0]);
/*  200 */         if (this.targetEntity.getRidingEntity() != null && 
/*  201 */           !(this.targetEntity.getRidingEntity() instanceof MCH_EntityAircraft) && 
/*  202 */           !(this.targetEntity.getRidingEntity() instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat)) {
/*  203 */           W_WorldFunc.MOD_playSoundAtEntity(this.targetEntity, "alert", 2.0F, 1.0F);
/*      */         }
/*      */       } 
/*      */       
/*  207 */       if (entity != null) {
/*  208 */         this.dataManager.set(TARGET_ID, Integer.valueOf(W_Entity.getEntityId(entity)));
/*      */       } else {
/*  210 */         this.dataManager.set(TARGET_ID, Integer.valueOf(0));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getTargetEntityID() {
/*  216 */     return (this.targetEntity != null) ? W_Entity.getEntityId(this.targetEntity) : ((Integer)this.dataManager.get(TARGET_ID)).intValue();
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
/*  247 */     if (this.world.isRemote) {
/*  248 */       if (name.isEmpty() || num < 1 || num > 50) {
/*      */         return;
/*      */       }
/*      */       
/*  252 */       double x = (this.posX - this.prevPosX) / num;
/*  253 */       double y = (this.posY - this.prevPosY) / num;
/*  254 */       double z = (this.posZ - this.prevPosZ) / num;
/*  255 */       double x2 = (this.prevPosX - this.prevPosX2) / num;
/*  256 */       double y2 = (this.prevPosY - this.prevPosY2) / num;
/*  257 */       double z2 = (this.prevPosZ - this.prevPosZ2) / num;
/*  258 */       if (name.equals("explode")) {
/*  259 */         for (int i = 0; i < num; i++) {
/*  260 */           MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", (this.prevPosX + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.prevPosY + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.prevPosZ + z * i + this.prevPosZ2 + z2 * i) / 2.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  267 */           prm.size = size + this.rand.nextFloat();
/*  268 */           MCH_ParticlesUtil.spawnParticle(prm);
/*      */         } 
/*      */       } else {
/*  271 */         for (int i = 0; i < num; i++) {
/*  272 */           MCH_ParticlesUtil.DEF_spawnParticle(name, (this.prevPosX + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.prevPosY + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.prevPosZ + z * i + this.prevPosZ2 + z2 * i) / 2.0D, 0.0D, 0.0D, 0.0D, 50.0F, new int[0]);
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
/*  288 */     if (this.world.isRemote) {
/*  289 */       if (name.isEmpty() || num < 1 || num > 50) {
/*      */         return;
/*      */       }
/*      */       
/*  293 */       double x = (this.posX - this.prevPosX) / num;
/*  294 */       double y = (this.posY - this.prevPosY) / num;
/*  295 */       double z = (this.posZ - this.prevPosZ) / num;
/*  296 */       double x2 = (this.prevPosX - this.prevPosX2) / num;
/*  297 */       double y2 = (this.prevPosY - this.prevPosY2) / num;
/*  298 */       double z2 = (this.prevPosZ - this.prevPosZ2) / num;
/*      */       
/*  300 */       for (int i = 0; i < num; i++) {
/*  301 */         MCH_ParticlesUtil.DEF_spawnParticle(name, (this.prevPosX + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.prevPosY + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.prevPosZ + z * i + this.prevPosZ2 + z2 * i) / 2.0D, 0.0D, 0.0D, 0.0D, 150.0F, new int[0]);
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
/*      */   public boolean isInRangeToRenderDist(double par1) {
/*  326 */     double d1 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
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
/*  350 */     double d6 = MathHelper.sqrt(targetX * targetX + targetY * targetY + targetZ * targetZ);
/*  351 */     this.motionX = targetX * this.acceleration / d6;
/*  352 */     this.motionY = targetY * this.acceleration / d6;
/*  353 */     this.motionZ = targetZ * this.acceleration / d6;
/*      */   }
/*      */   
/*      */   public boolean usingFlareOfTarget(Entity entity) {
/*  357 */     if (getCountOnUpdate() % 3 == 0) {
/*  358 */       List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, entity.getEntityBoundingBox().grow(15.0D, 15.0D, 15.0D));
/*      */       
/*  360 */       for (Entity value : list) {
/*  361 */         if (value.getEntityData().getBoolean("FlareUsing")) {
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
/*  375 */     double tx = targetPosX - this.posX;
/*  376 */     double ty = targetPosY - this.posY;
/*  377 */     double tz = targetPosZ - this.posZ;
/*  378 */     double d = MathHelper.sqrt(tx * tx + ty * ty + tz * tz);
/*  379 */     double mx = tx * this.acceleration / d;
/*  380 */     double my = ty * this.acceleration / d;
/*  381 */     double mz = tz * this.acceleration / d;
/*  382 */     this.motionX = (this.motionX * 6.0D + mx) / 7.0D;
/*  383 */     this.motionY = (this.motionY * 6.0D + my) / 7.0D;
/*  384 */     this.motionZ = (this.motionZ * 6.0D + mz) / 7.0D;
/*  385 */     double a = (float)Math.atan2(this.motionZ, this.motionX);
/*  386 */     this.rotationYaw = (float)(a * 180.0D / Math.PI) - 90.0F;
/*  387 */     double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  388 */     this.rotationPitch = -((float)(Math.atan2(this.motionY, r) * 180.0D / Math.PI));
/*      */   }
/*      */   
/*      */   public boolean checkValid() {
/*  392 */     if (this.shootingEntity == null && this.shootingAircraft == null) {
/*  393 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  397 */     Entity shooter = (this.shootingAircraft != null && this.shootingAircraft.isDead && this.shootingEntity == null) ? this.shootingAircraft : this.shootingEntity;
/*  398 */     double x = this.posX - shooter.posX;
/*  399 */     double z = this.posZ - shooter.posZ;
/*  400 */     return (x * x + z * z < 3.38724E7D && this.posY > -10.0D);
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
/*      */   public void onUpdate() {
/*  413 */     if (this.world.isRemote && this.countOnUpdate == 0) {
/*  414 */       int tgtEttId = getTargetEntityID();
/*  415 */       if (tgtEttId > 0) {
/*  416 */         setTargetEntity(this.world.getEntityByID(tgtEttId));
/*      */       }
/*      */     } 
/*      */     
/*  420 */     if (!this.world.isRemote && getCountOnUpdate() % 20 == 19 && this.targetEntity instanceof EntityPlayerMP) {
/*  421 */       MCH_Lib.DbgLog(this.world, "MCH_EntityBaseBullet.onUpdate alert" + this.targetEntity + " / " + this.targetEntity.getRidingEntity(), new Object[0]);
/*  422 */       if (this.targetEntity.getRidingEntity() != null && 
/*  423 */         !(this.targetEntity.getRidingEntity() instanceof MCH_EntityAircraft) && 
/*  424 */         !(this.targetEntity.getRidingEntity() instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat)) {
/*  425 */         W_WorldFunc.MOD_playSoundAtEntity(this.targetEntity, "alert", 2.0F, 1.0F);
/*      */       }
/*      */     } 
/*      */     
/*  429 */     this.prevMotionX = this.motionX;
/*  430 */     this.prevMotionY = this.motionY;
/*  431 */     this.prevMotionZ = this.motionZ;
/*  432 */     this.countOnUpdate++;
/*  433 */     if (this.countOnUpdate > 10000000) {
/*  434 */       clearCountOnUpdate();
/*      */     }
/*      */     
/*  437 */     this.prevPosX2 = this.prevPosX;
/*  438 */     this.prevPosY2 = this.prevPosY;
/*  439 */     this.prevPosZ2 = this.prevPosZ;
/*  440 */     super.onUpdate();
/*  441 */     if ((this.prevMotionX != this.motionX || this.prevMotionY != this.motionY || this.prevMotionZ != this.motionZ) && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ > 0.1D) {
/*      */       
/*  443 */       double a = (float)Math.atan2(this.motionZ, this.motionX);
/*  444 */       this.rotationYaw = (float)(a * 180.0D / Math.PI) - 90.0F;
/*  445 */       double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  446 */       this.rotationPitch = -((float)(Math.atan2(this.motionY, r) * 180.0D / Math.PI));
/*      */     } 
/*      */     
/*  449 */     if (getInfo() == null) {
/*  450 */       if (this.countOnUpdate >= 2) {
/*  451 */         MCH_Lib.Log((Entity)this, "##### MCH_EntityBaseBullet onUpdate() Weapon info null %d, %s, Name=%s", new Object[] {
/*  452 */               Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName(), getName()
/*      */             });
/*  454 */         setDead();
/*      */         
/*      */         return;
/*      */       } 
/*  458 */       setName(getName());
/*  459 */       if (getInfo() == null) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/*  464 */     if ((getInfo()).bound <= 0.0F && this.onGround) {
/*  465 */       this.motionX *= 0.9D;
/*  466 */       this.motionZ *= 0.9D;
/*      */     } 
/*      */     
/*  469 */     if (this.world.isRemote && this.isBomblet < 0) {
/*  470 */       this.isBomblet = getBomblet();
/*      */     }
/*      */     
/*  473 */     if (!this.world.isRemote) {
/*  474 */       BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
/*  475 */       if ((int)this.posY <= 255 && !this.world.isBlockLoaded(blockpos)) {
/*  476 */         if ((getInfo()).delayFuse <= 0) {
/*  477 */           setDead();
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
/*  490 */           setDead();
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  495 */       if (!checkValid()) {
/*  496 */         setDead();
/*      */         
/*      */         return;
/*      */       } 
/*  500 */       if ((getInfo()).timeFuse > 0 && getCountOnUpdate() > (getInfo()).timeFuse) {
/*  501 */         onUpdateTimeout();
/*  502 */         setDead();
/*      */         
/*      */         return;
/*      */       } 
/*  506 */       if ((getInfo()).explosionAltitude > 0 && MCH_Lib.getBlockIdY((Entity)this, 3, -(getInfo()).explosionAltitude) != 0) {
/*  507 */         RayTraceResult mop = new RayTraceResult(new Vec3d(this.posX, this.posY, this.posZ), EnumFacing.DOWN, new BlockPos(this.posX, this.posY, this.posZ));
/*  508 */         onImpact(mop, 1.0F);
/*      */       } 
/*      */     } 
/*      */     
/*  512 */     if (!isInWater()) {
/*  513 */       this.motionY += getGravity();
/*      */     } else {
/*  515 */       this.motionY += getGravityInWater();
/*      */     } 
/*      */     
/*  518 */     if (!this.isDead) {
/*  519 */       onUpdateCollided();
/*      */     }
/*      */     
/*  522 */     this.posX += this.motionX * this.accelerationFactor;
/*  523 */     this.posY += this.motionY * this.accelerationFactor;
/*  524 */     this.posZ += this.motionZ * this.accelerationFactor;
/*  525 */     if (this.world.isRemote) {
/*  526 */       updateSplash();
/*      */     }
/*      */     
/*  529 */     if (isInWater()) {
/*  530 */       float f3 = 0.25F;
/*  531 */       this.world
/*  532 */         .spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f3, this.posY - this.motionY * f3, this.posZ - this.motionZ * f3, this.motionX, this.motionY, this.motionZ, new int[0]);
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
/*  543 */     setPosition(this.posX, this.posY, this.posZ);
/*      */   }
/*      */   
/*      */   public void updateSplash() {
/*  547 */     if (getInfo() != null && 
/*  548 */       (getInfo()).power > 0 && 
/*  549 */       !W_WorldFunc.isBlockWater(this.world, (int)(this.prevPosX + 0.5D), (int)(this.prevPosY + 0.5D), (int)(this.prevPosZ + 0.5D)) && 
/*  550 */       W_WorldFunc.isBlockWater(this.world, (int)(this.posX + 0.5D), (int)(this.posY + 0.5D), (int)(this.posZ + 0.5D))) {
/*  551 */       double x = this.posX - this.prevPosX;
/*  552 */       double y = this.posY - this.prevPosY;
/*  553 */       double z = this.posZ - this.prevPosZ;
/*  554 */       double d = Math.sqrt(x * x + y * y + z * z);
/*  555 */       if (d <= 0.15D) {
/*      */         return;
/*      */       }
/*      */       
/*  559 */       x /= d;
/*  560 */       y /= d;
/*  561 */       z /= d;
/*  562 */       double px = this.prevPosX;
/*  563 */       double py = this.prevPosY;
/*  564 */       double pz = this.prevPosZ;
/*      */       
/*  566 */       for (int i = 0; i <= d; i++) {
/*  567 */         px += x;
/*  568 */         py += y;
/*  569 */         pz += z;
/*  570 */         if (W_WorldFunc.isBlockWater(this.world, (int)(px + 0.5D), (int)(py + 0.5D), (int)(pz + 0.5D))) {
/*  571 */           float pwr = ((getInfo()).power < 20) ? (getInfo()).power : 20.0F;
/*  572 */           int n = this.rand.nextInt(1 + (int)pwr / 3) + (int)pwr / 2 + 1;
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
/*  584 */             MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "splash", px, py + 0.5D, pz, pwr * (this.rand.nextDouble() - 0.5D) * 0.3D, pwr * (this.rand.nextDouble() * 0.5D + 0.5D) * 1.8D, pwr * (this.rand.nextDouble() - 0.5D) * 0.3D, pwr * 5.0F);
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
/*  598 */     if (isInWater()) {
/*  599 */       if (this.explosionPowerInWater > 0) {
/*  600 */         newExplosion(this.posX, this.posY, this.posZ, this.explosionPowerInWater, this.explosionPowerInWater, true);
/*      */       }
/*  602 */     } else if (this.explosionPower > 0) {
/*  603 */       newExplosion(this.posX, this.posY, this.posZ, this.explosionPower, (getInfo()).explosionBlock, false);
/*  604 */     } else if (this.explosionPower < 0) {
/*  605 */       playExplosionSound();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onUpdateBomblet() {
/*  610 */     if (!this.world.isRemote && this.sprinkleTime > 0 && !this.isDead) {
/*  611 */       this.sprinkleTime--;
/*  612 */       if (this.sprinkleTime == 0) {
/*  613 */         for (int i = 0; i < (getInfo()).bomblet; i++) {
/*  614 */           sprinkleBomblet();
/*      */         }
/*      */         
/*  617 */         setDead();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void boundBullet(EnumFacing sideHit) {
/*  623 */     switch (sideHit) {
/*      */       case DOWN:
/*  625 */         if (this.motionY > 0.0D) {
/*  626 */           this.motionY = -this.motionY * (getInfo()).bound;
/*      */         }
/*      */         break;
/*      */       case UP:
/*  630 */         if (this.motionY < 0.0D) {
/*  631 */           this.motionY = -this.motionY * (getInfo()).bound;
/*      */         }
/*      */         break;
/*      */       case NORTH:
/*  635 */         if (this.motionZ > 0.0D) {
/*  636 */           this.motionZ = -this.motionZ * (getInfo()).bound; break;
/*      */         } 
/*  638 */         this.posZ += this.motionZ;
/*      */         break;
/*      */       
/*      */       case SOUTH:
/*  642 */         if (this.motionZ < 0.0D) {
/*  643 */           this.motionZ = -this.motionZ * (getInfo()).bound; break;
/*      */         } 
/*  645 */         this.posZ += this.motionZ;
/*      */         break;
/*      */       
/*      */       case WEST:
/*  649 */         if (this.motionX > 0.0D) {
/*  650 */           this.motionX = -this.motionX * (getInfo()).bound; break;
/*      */         } 
/*  652 */         this.posX += this.motionX;
/*      */         break;
/*      */       
/*      */       case EAST:
/*  656 */         if (this.motionX < 0.0D) {
/*  657 */           this.motionX = -this.motionX * (getInfo()).bound; break;
/*      */         } 
/*  659 */         this.posX += this.motionX;
/*      */         break;
/*      */     } 
/*      */     
/*  663 */     if ((getInfo()).bound <= 0.0F) {
/*  664 */       this.motionX *= 0.25D;
/*  665 */       this.motionY *= 0.25D;
/*  666 */       this.motionZ *= 0.25D;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onUpdateCollided() {
/*  671 */     float damageFator = 1.0F;
/*  672 */     double mx = this.motionX * this.accelerationFactor;
/*  673 */     double my = this.motionY * this.accelerationFactor;
/*  674 */     double mz = this.motionZ * this.accelerationFactor;
/*  675 */     RayTraceResult m = null;
/*      */     
/*  677 */     for (int i = 0; i < 5; i++) {
/*  678 */       Vec3d vec3 = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
/*  679 */       Vec3d vec31 = W_WorldFunc.getWorldVec3(this.world, this.posX + mx, this.posY + my, this.posZ + mz);
/*  680 */       m = W_WorldFunc.clip(this.world, vec3, vec31);
/*  681 */       boolean continueClip = false;
/*  682 */       if (this.shootingEntity != null && W_MovingObjectPosition.isHitTypeTile(m)) {
/*  683 */         Block block = W_WorldFunc.getBlock(this.world, m.getBlockPos());
/*  684 */         if (MCH_Config.bulletBreakableBlocks.contains(block)) {
/*  685 */           W_WorldFunc.destroyBlock(this.world, m.getBlockPos(), true);
/*  686 */           continueClip = true;
/*      */         } 
/*      */       } 
/*      */       
/*  690 */       if (!continueClip) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/*  695 */     Vec3d vec3x = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
/*  696 */     Vec3d vec31x = W_WorldFunc.getWorldVec3(this.world, this.posX + mx, this.posY + my, this.posZ + mz);
/*  697 */     if ((getInfo()).delayFuse > 0) {
/*  698 */       if (m != null) {
/*  699 */         boundBullet(m.sideHit);
/*  700 */         if (this.delayFuse == 0) {
/*  701 */           this.delayFuse = (getInfo()).delayFuse;
/*      */         }
/*      */       } 
/*      */     } else {
/*  705 */       if (m != null) {
/*  706 */         vec31x = W_WorldFunc.getWorldVec3(this.world, m.hitVec.x, m.hitVec.y, m.hitVec.z);
/*      */       }
/*      */       
/*  709 */       Entity entity = null;
/*  710 */       List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().expand(mx, my, mz).grow(21.0D, 21.0D, 21.0D));
/*  711 */       double d0 = 0.0D;
/*      */       
/*  713 */       for (Entity entity1 : list) {
/*  714 */         if (canBeCollidedEntity(entity1)) {
/*  715 */           float f = 0.3F;
/*  716 */           AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(f, f, f);
/*  717 */           RayTraceResult m1 = axisalignedbb.calculateIntercept(vec3x, vec31x);
/*  718 */           if (m1 != null) {
/*  719 */             double d1 = vec3x.distanceTo(m1.hitVec);
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
/*  741 */     if (!entity.canBeCollidedWith()) {
/*  742 */       return false;
/*      */     }
/*  744 */     if (entity instanceof MCH_EntityBaseBullet) { MCH_EntityBaseBullet blt = (MCH_EntityBaseBullet)entity;
/*  745 */       if (this.world.isRemote) {
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
/*  798 */     if (!this.world.isRemote) {
/*      */       
/*      */       try {
/*  801 */         if ((getInfo()).chemYield > 0) {
/*  802 */           System.out.println("chem yield detected");
/*  803 */           MCH_HBMUtil.ExplosionChaos_spawnChlorine(this.world, this.posX, this.posY + 0.5D, this.posZ, (getInfo()).chemYield, (getInfo()).chemSpeed, (getInfo()).chemType);
/*      */         } 
/*  805 */       } catch (Exception e) {
/*  806 */         MCH_Lib.Log((Entity)this, "Error in onImpact: %s", new Object[] { e.getMessage() });
/*  807 */         e.printStackTrace();
/*      */       } 
/*      */       
/*  810 */       if (m.entityHit != null) {
/*  811 */         onImpactEntity(m.entityHit, damageFactor);
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
/*  823 */           newExplosion(m.hitVec.x + dx, m.hitVec.y + dy, m.hitVec.z + dz, 1.0F, 1.0F, false);
/*      */         }
/*      */       } else {
/*  826 */         if (expPowerInWater == 0.0F) {
/*  827 */           if ((getInfo()).isFAE) {
/*  828 */             newFAExplosion(this.posX, this.posY, this.posZ, expPower, (getInfo()).explosionBlock);
/*  829 */           } else if (expPower > 0.0F) {
/*  830 */             newExplosion(m.hitVec.x + dx, m.hitVec.y + dy, m.hitVec.z + dz, expPower, (getInfo()).explosionBlock, false);
/*  831 */           } else if (expPower < 0.0F) {
/*  832 */             playExplosionSound();
/*      */           } 
/*  834 */         } else if (m.entityHit != null) {
/*  835 */           if (isInWater()) {
/*  836 */             newExplosion(m.hitVec.x + dx, m.hitVec.y + dy, m.hitVec.z + dz, expPowerInWater, expPowerInWater, true);
/*      */           } else {
/*  838 */             newExplosion(m.hitVec.x + dx, m.hitVec.y + dy, m.hitVec.z + dz, expPower, (getInfo()).explosionBlock, false);
/*      */           } 
/*  840 */         } else if (isInWater() || 
/*  841 */           MCH_Lib.isBlockInWater(this.world, m.getBlockPos().getX(), m.getBlockPos().getY(), m.getBlockPos().getZ())) {
/*  842 */           newExplosion(m
/*  843 */               .getBlockPos().getX(), m.getBlockPos().getY(), m.getBlockPos().getZ(), expPowerInWater, expPowerInWater, true);
/*      */         }
/*  845 */         else if (expPower > 0.0F) {
/*  846 */           newExplosion(m.hitVec.x + dx, m.hitVec.y + dy, m.hitVec.z + dz, expPower, (getInfo()).explosionBlock, false);
/*  847 */         } else if (expPower < 0.0F) {
/*  848 */           playExplosionSound();
/*      */         } 
/*      */         
/*  851 */         setDead();
/*      */       } 
/*  853 */     } else if (getInfo() != null && ((getInfo()).explosion == 0 || (getInfo()).modeNum >= 2) && W_MovingObjectPosition.isHitTypeTile(m)) {
/*  854 */       float p = (getInfo()).power;
/*      */       
/*  856 */       for (int i = 0; i < p / 3.0F; i++) {
/*  857 */         MCH_ParticlesUtil.spawnParticleTileCrack(this.world, m
/*      */             
/*  859 */             .getBlockPos().getX(), m
/*  860 */             .getBlockPos().getY(), m
/*  861 */             .getBlockPos().getZ(), m.hitVec.x + (this.rand
/*  862 */             .nextFloat() - 0.5D) * p / 10.0D, m.hitVec.y + 0.1D, m.hitVec.z + (this.rand
/*      */             
/*  864 */             .nextFloat() - 0.5D) * p / 10.0D, -this.motionX * p / 2.0D, (p / 2.0F), -this.motionZ * p / 2.0D);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onImpactEntity(Entity entity, float damageFactor) {
/*  874 */     if (!entity.isDead) {
/*  875 */       MCH_Lib.DbgLog(this.world, "MCH_EntityBaseBullet.onImpactEntity:Damage=%d:" + entity.getClass(), new Object[] { Integer.valueOf(getPower()) });
/*  876 */       MCH_Lib.applyEntityHurtResistantTimeConfig(entity);
/*  877 */       DamageSource ds = DamageSource.causeThrownDamage((Entity)this, this.shootingEntity);
/*  878 */       float damage = MCH_Config.applyDamageVsEntity(entity, ds, getPower() * damageFactor);
/*  879 */       damage *= (getInfo() != null) ? getInfo().getDamageFactor(entity) : 1.0F;
/*  880 */       entity.attackEntityFrom(ds, damage);
/*  881 */       if (this instanceof MCH_EntityBullet && entity instanceof net.minecraft.entity.passive.EntityVillager && this.shootingEntity != null && this.shootingEntity instanceof EntityPlayerMP && this.shootingEntity
/*      */ 
/*      */ 
/*      */         
/*  885 */         .getRidingEntity() instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat) {
/*  886 */         MCH_CriteriaTriggers.VILLAGER_HURT_BULLET.trigger((EntityPlayerMP)this.shootingEntity);
/*      */       }
/*      */       
/*  889 */       if (!entity.isDead);
/*      */     } 
/*      */ 
/*      */     
/*  893 */     notifyHitBullet();
/*      */   }
/*      */   
/*      */   public void newFAExplosion(double x, double y, double z, float exp, float expBlock) {
/*  897 */     MCH_Explosion.ExplosionResult result = MCH_Explosion.newExplosion(this.world, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, true, true, 
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
/*  908 */       result = MCH_Explosion.newExplosion(this.world, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  917 */           (this.rand.nextInt(3) == 0), true, 
/*      */           
/*  919 */           (getInfo()).flaming, true, 0, 
/*      */ 
/*      */           
/*  922 */           (getInfo() != null) ? (getInfo()).damageFactor : null);
/*      */     } else {
/*      */       
/*  925 */       result = MCH_Explosion.newExplosionInWater(this.world, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  934 */           (this.rand.nextInt(3) == 0), true, 
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
/*  945 */         this.world.spawnEntity((Entity)MCH_HBMUtil.EntityNukeExplosionMK5_statFac(this.world, (getInfo()).nukeYield, this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D));
/*      */       }
/*  947 */       MCH_HBMUtil.EntityNukeTorex_statFac(this.world, this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, (getInfo()).nukeYield);
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
/*  962 */     MCH_Explosion.playExplosionSound(this.world, this.posX, this.posY, this.posZ);
/*      */   }
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
/*  966 */     par1NBTTagCompound.setTag("direction", (NBTBase)newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
/*  967 */     par1NBTTagCompound.setString("WeaponName", getName());
/*      */   }
/*      */   
/*      */   public void readEntityFromNBT(@NotNull NBTTagCompound par1NBTTagCompound) {
/*  971 */     setDead();
/*      */   }
/*      */   
/*      */   public boolean canBeCollidedWith() {
/*  975 */     return true;
/*      */   }
/*      */   
/*      */   public float getCollisionBorderSize() {
/*  979 */     return 1.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource ds, float par2) {
/*  984 */     if (isEntityInvulnerable(ds))
/*  985 */       return false; 
/*  986 */     if (!this.world.isRemote && par2 > 0.0F && ds.getDamageType().equalsIgnoreCase("thrown")) {
/*  987 */       markVelocityChanged();
/*  988 */       Vec3d pos = new Vec3d(this.posX, this.posY, this.posZ);
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