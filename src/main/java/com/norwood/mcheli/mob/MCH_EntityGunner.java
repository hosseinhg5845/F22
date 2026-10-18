/*     */ package com.norwood.mcheli.mob;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */ import com.norwood.mcheli.aircraft.MCH_SeatInfo;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponBase;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponParam;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponSet;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.IMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ITeleporter;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ public class MCH_EntityGunner
/*     */   extends EntityLivingBase
/*     */ {
/*  45 */   private static final DataParameter<String> TEAM_NAME = EntityDataManager.createKey(MCH_EntityGunner.class, DataSerializers.STRING);
/*     */   public boolean isCreative = false;
/*  47 */   public String ownerUUID = "";
/*  48 */   public int targetType = 0;
/*  49 */   public int despawnCount = 0;
/*  50 */   public int switchTargetCount = 0;
/*  51 */   public Entity targetEntity = null;
/*  52 */   public double targetPrevPosX = 0.0D;
/*  53 */   public double targetPrevPosY = 0.0D;
/*  54 */   public double targetPrevPosZ = 0.0D;
/*     */   public boolean waitCooldown = false;
/*  56 */   public int idleCount = 0;
/*  57 */   public int idleRotation = 0;
/*     */   
/*     */   public MCH_EntityGunner(World world) {
/*  60 */     super(world);
/*     */   }
/*     */   
/*     */   public MCH_EntityGunner(World world, double x, double y, double z) {
/*  64 */     this(world);
/*  65 */     setPosition(x, y, z);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  69 */     super.entityInit();
/*  70 */     this.dataManager.register(TEAM_NAME, "");
/*     */   }
/*     */   
/*     */   public String getTeamName() {
/*  74 */     return (String)this.dataManager.get(TEAM_NAME);
/*     */   }
/*     */   
/*     */   public void setTeamName(String name) {
/*  78 */     this.dataManager.set(TEAM_NAME, name);
/*     */   }
/*     */   
/*     */   public Team getTeam() {
/*  82 */     return (Team)this.world.getScoreboard().getTeam(getTeamName());
/*     */   }
/*     */   
/*     */   public boolean isOnSameTeam(@NotNull Entity entityIn) {
/*  86 */     return super.isOnSameTeam(entityIn);
/*     */   }
/*     */   @NotNull
/*     */   public ITextComponent getDisplayName() {
/*  90 */     Team team = getTeam();
/*  91 */     return (team != null) ? (ITextComponent)new TextComponentString(ScorePlayerTeam.formatPlayerName(team, team.getName() + " Gunner")) : (ITextComponent)new TextComponentString("");
/*     */   }
/*     */   
/*     */   public boolean isEntityInvulnerable(DamageSource source) {
/*  95 */     return this.isCreative;
/*     */   }
/*     */   
/*     */   public void onDeath(@NotNull DamageSource source) {
/*  99 */     super.onDeath(source);
/*     */   }
/*     */   
/*     */   public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
/* 103 */     if (this.world.isRemote)
/* 104 */       return false; 
/* 105 */     if (getRidingEntity() == null)
/* 106 */       return false; 
/* 107 */     if (player.capabilities.isCreativeMode) {
/* 108 */       removeFromAircraft(player);
/* 109 */       return true;
/* 110 */     }  if (this.isCreative) {
/* 111 */       player.sendMessage((ITextComponent)new TextComponentString("Creative mode only."));
/* 112 */       return false;
/* 113 */     }  if (getTeam() != null && !isOnSameTeam((Entity)player)) {
/* 114 */       player.sendMessage((ITextComponent)new TextComponentString("You are other team."));
/* 115 */       return false;
/*     */     } 
/* 117 */     removeFromAircraft(player);
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFromAircraft(EntityPlayer player) {
/* 123 */     if (!this.world.isRemote) {
/* 124 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "wrench", 1.0F, 1.0F);
/* 125 */       setDead();
/* 126 */       MCH_EntityAircraft ac = null;
/* 127 */       if (getRidingEntity() instanceof MCH_EntityAircraft) {
/* 128 */         ac = (MCH_EntityAircraft)getRidingEntity();
/* 129 */       } else if (getRidingEntity() instanceof MCH_EntitySeat) {
/* 130 */         ac = ((MCH_EntitySeat)getRidingEntity()).getParent();
/*     */       } 
/*     */       
/* 133 */       String name = "";
/* 134 */       if (ac != null && ac.getAcInfo() != null) {
/* 135 */         name = " on " + (ac.getAcInfo()).displayName + " seat " + (ac.getSeatIdByEntity((Entity)this) + 1);
/*     */       }
/*     */       
/* 138 */       player.sendMessage((ITextComponent)new TextComponentString("Remove gunner" + name + " by " + 
/*     */             
/* 140 */             ScorePlayerTeam.formatPlayerName(player.getTeam(), player.getDisplayName().getFormattedText()) + "."));
/*     */ 
/*     */       
/* 143 */       dismountRidingEntity();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 148 */     super.onUpdate();
/* 149 */     if (!this.world.isRemote && !this.isDead) {
/* 150 */       if (getRidingEntity() != null && (getRidingEntity()).isDead) {
/* 151 */         dismountRidingEntity();
/*     */       }
/*     */       
/* 154 */       if (getRidingEntity() instanceof MCH_EntityAircraft) {
/* 155 */         shotTarget((MCH_EntityAircraft)getRidingEntity());
/* 156 */       } else if (getRidingEntity() instanceof MCH_EntitySeat && ((MCH_EntitySeat)getRidingEntity()).getParent() != null) {
/* 157 */         shotTarget(((MCH_EntitySeat)getRidingEntity()).getParent());
/* 158 */       } else if (this.despawnCount < 20) {
/* 159 */         this.despawnCount++;
/* 160 */       } else if (getRidingEntity() == null || this.ticksExisted > 100) {
/* 161 */         setDead();
/*     */       } 
/*     */       
/* 164 */       if (this.targetEntity == null) {
/* 165 */         if (this.idleCount == 0) {
/* 166 */           this.idleCount = (3 + this.rand.nextInt(5)) * 20;
/* 167 */           this.idleRotation = this.rand.nextInt(5) - 2;
/*     */         } 
/*     */         
/* 170 */         this.rotationYaw += this.idleRotation / 2.0F;
/*     */       } else {
/* 172 */         this.idleCount = 60;
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     if (this.switchTargetCount > 0) {
/* 177 */       this.switchTargetCount--;
/*     */     }
/*     */     
/* 180 */     if (this.idleCount > 0) {
/* 181 */       this.idleCount--;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canAttackEntity(EntityLivingBase entity, MCH_EntityAircraft ac, MCH_WeaponSet ws) {
/*     */     boolean ret;
/* 187 */     if (this.targetType == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       ret = (entity != this && !(entity instanceof net.minecraft.entity.monster.EntityEnderman) && !entity.isDead && !isOnSameTeam((Entity)entity) && entity.getHealth() > 0.0F && !ac.isMountedEntity((Entity)entity));
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 201 */       ret = (entity != this && !((EntityPlayer)entity).capabilities.isCreativeMode && !entity.isDead && !getTeamName().isEmpty() && !isOnSameTeam((Entity)entity) && entity.getHealth() > 0.0F && !ac.isMountedEntity((Entity)entity));
/*     */     } 
/*     */     
/* 204 */     if (ret && ws.getCurrentWeapon().getGuidanceSystem() != null) {
/* 205 */       ret = ws.getCurrentWeapon().getGuidanceSystem().canLockEntity((Entity)entity);
/*     */     }
/*     */     
/* 208 */     return ret;
/*     */   }
/*     */   
/*     */   public void shotTarget(MCH_EntityAircraft ac) {
/* 212 */     if (!ac.isDestroyed() && 
/* 213 */       ac.getGunnerStatus()) {
/* 214 */       MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)this);
/* 215 */       if (ws != null && ws.getInfo() != null && ws.getCurrentWeapon() != null) {
/* 216 */         MCH_WeaponBase cw = ws.getCurrentWeapon();
/* 217 */         if (this.targetEntity != null && (this.targetEntity.isDead || ((EntityLivingBase)this.targetEntity)
/* 218 */           .getHealth() <= 0.0F) && this.switchTargetCount > 20)
/*     */         {
/* 220 */           this.switchTargetCount = 20;
/*     */         }
/*     */         
/* 223 */         Vec3d pos = getGunnerWeaponPos(ac, ws);
/* 224 */         if (this.switchTargetCount <= 0) {
/* 225 */           List<? extends EntityLivingBase> list; this.switchTargetCount = 20;
/* 226 */           EntityLivingBase nextTarget = null;
/*     */           
/* 228 */           if (this.targetType == 0) {
/* 229 */             int rh = MCH_Config.RangeOfGunner_VsMonster_Horizontal.prmInt;
/* 230 */             int rv = MCH_Config.RangeOfGunner_VsMonster_Vertical.prmInt;
/* 231 */             list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(rh, rv, rh), IMob.MOB_SELECTOR);
/*     */           } else {
/* 233 */             int rh = MCH_Config.RangeOfGunner_VsPlayer_Horizontal.prmInt;
/* 234 */             int rv = MCH_Config.RangeOfGunner_VsPlayer_Vertical.prmInt;
/* 235 */             list = this.world.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().grow(rh, rv, rh));
/*     */           } 
/*     */           
/* 238 */           for (EntityLivingBase entity : list) {
/* 239 */             if (canAttackEntity(entity, ac, ws) && 
/* 240 */               checkPitch(entity, ac, pos) && (nextTarget == null || 
/* 241 */               getDistance((Entity)entity) < getDistance((Entity)nextTarget)) && 
/* 242 */               canEntityBeSeen((Entity)entity) && 
/* 243 */               isInAttackable(entity, ac, ws, pos)) {
/* 244 */               nextTarget = entity;
/* 245 */               this.switchTargetCount = 60;
/*     */             } 
/*     */           } 
/*     */           
/* 249 */           if (nextTarget != null && this.targetEntity != nextTarget) {
/* 250 */             this.targetPrevPosX = nextTarget.posX;
/* 251 */             this.targetPrevPosY = nextTarget.posY;
/* 252 */             this.targetPrevPosZ = nextTarget.posZ;
/*     */           } 
/*     */           
/* 255 */           this.targetEntity = (Entity)nextTarget;
/*     */         } 
/*     */         
/* 258 */         if (this.targetEntity != null) {
/* 259 */           float rotSpeed = 10.0F;
/* 260 */           if (ac.isPilot((Entity)this)) {
/* 261 */             rotSpeed = (ac.getAcInfo()).cameraRotationSpeed / 10.0F;
/*     */           }
/*     */           
/* 264 */           this.rotationPitch = MathHelper.wrapDegrees(this.rotationPitch);
/* 265 */           this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
/* 266 */           double dist = getDistance(this.targetEntity);
/* 267 */           double tick = 1.0D;
/* 268 */           if (dist >= 10.0D && (ws.getInfo()).acceleration > 1.0F) {
/* 269 */             tick = dist / (ws.getInfo()).acceleration;
/*     */           }
/*     */           
/* 272 */           if (this.targetEntity.getRidingEntity() instanceof MCH_EntitySeat || this.targetEntity.getRidingEntity() instanceof MCH_EntityAircraft) {
/* 273 */             tick -= MCH_Config.HitBoxDelayTick.prmInt;
/*     */           }
/*     */           
/* 276 */           double dx = (this.targetEntity.posX - this.targetPrevPosX) * tick;
/* 277 */           double dy = (this.targetEntity.posY - this.targetPrevPosY) * tick + this.targetEntity.height * this.rand.nextDouble();
/* 278 */           double dz = (this.targetEntity.posZ - this.targetPrevPosZ) * tick;
/* 279 */           double d0 = this.targetEntity.posX + dx - pos.x;
/* 280 */           double d1 = this.targetEntity.posY + dy - pos.y;
/* 281 */           double d2 = this.targetEntity.posZ + dz - pos.z;
/* 282 */           double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/* 283 */           float yaw = MathHelper.wrapDegrees((float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F);
/* 284 */           float pitch = (float)-(Math.atan2(d1, d3) * 180.0D / Math.PI);
/* 285 */           if (Math.abs(this.rotationPitch - pitch) < rotSpeed && Math.abs(this.rotationYaw - yaw) < rotSpeed) {
/* 286 */             float r = ac.isPilot((Entity)this) ? 0.1F : 0.5F;
/* 287 */             this.rotationPitch = pitch + (this.rand.nextFloat() - 0.5F) * r - cw.fixRotationPitch;
/* 288 */             this.rotationYaw = yaw + (this.rand.nextFloat() - 0.5F) * r;
/* 289 */             if (!this.waitCooldown || ws.currentHeat <= 0 || (ws.getInfo()).maxHeatCount <= 0) {
/* 290 */               this.waitCooldown = false;
/* 291 */               MCH_WeaponParam prm = new MCH_WeaponParam();
/* 292 */               prm.setPosition(ac.posX, ac.posY, ac.posZ);
/* 293 */               prm.user = (Entity)this;
/* 294 */               prm.entity = (Entity)ac;
/* 295 */               prm.option1 = (cw instanceof com.norwood.mcheli.weapon.MCH_WeaponEntitySeeker) ? this.targetEntity.getEntityId() : 0;
/* 296 */               if (ac.useCurrentWeapon(prm) && (ws.getInfo()).maxHeatCount > 0 && ws.currentHeat > (ws.getInfo()).maxHeatCount * 4 / 5) {
/* 297 */                 this.waitCooldown = true;
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/* 302 */           if (Math.abs(pitch - this.rotationPitch) >= rotSpeed) {
/* 303 */             this.rotationPitch += (pitch > this.rotationPitch) ? rotSpeed : -rotSpeed;
/*     */           }
/*     */           
/* 306 */           if (Math.abs(yaw - this.rotationYaw) >= rotSpeed) {
/* 307 */             if (Math.abs(yaw - this.rotationYaw) <= 180.0F) {
/* 308 */               this.rotationYaw += (yaw > this.rotationYaw) ? rotSpeed : -rotSpeed;
/*     */             } else {
/* 310 */               this.rotationYaw += (yaw > this.rotationYaw) ? -rotSpeed : rotSpeed;
/*     */             } 
/*     */           }
/*     */           
/* 314 */           this.rotationYawHead = this.rotationYaw;
/* 315 */           this.targetPrevPosX = this.targetEntity.posX;
/* 316 */           this.targetPrevPosY = this.targetEntity.posY;
/* 317 */           this.targetPrevPosZ = this.targetEntity.posZ;
/*     */         } else {
/* 319 */           this.rotationPitch *= 0.95F;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkPitch(EntityLivingBase entity, MCH_EntityAircraft ac, Vec3d pos) {
/*     */     try {
/* 328 */       double d0 = entity.posX - pos.x;
/* 329 */       double d1 = entity.posY - pos.y;
/* 330 */       double d2 = entity.posZ - pos.z;
/* 331 */       double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/* 332 */       float pitch = (float)-(Math.atan2(d1, d3) * 180.0D / Math.PI);
/* 333 */       MCH_AircraftInfo ai = ac.getAcInfo();
/* 334 */       if (ac instanceof com.norwood.mcheli.vehicle.MCH_EntityVehicle && ac.isPilot((Entity)this) && Math.abs(ai.minRotationPitch) + Math.abs(ai.maxRotationPitch) > 0.0F) {
/* 335 */         if (pitch < ai.minRotationPitch) {
/* 336 */           return false;
/*     */         }
/*     */         
/* 339 */         if (pitch > ai.maxRotationPitch) {
/* 340 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 344 */       MCH_WeaponBase cw = ac.getCurrentWeapon((Entity)this).getCurrentWeapon();
/* 345 */       if (!(cw instanceof com.norwood.mcheli.weapon.MCH_WeaponEntitySeeker)) {
/* 346 */         MCH_AircraftInfo.Weapon wi = ai.getWeaponById(ac.getCurrentWeaponID((Entity)this));
/* 347 */         if (Math.abs(wi.minPitch) + Math.abs(wi.maxPitch) > 0.0F) {
/* 348 */           if (pitch < wi.minPitch) {
/* 349 */             return false;
/*     */           }
/*     */           
/* 352 */           if (pitch > wi.maxPitch) {
/* 353 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/* 357 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 360 */     return true;
/*     */   }
/*     */   
/*     */   public Vec3d getGunnerWeaponPos(MCH_EntityAircraft ac, MCH_WeaponSet ws) {
/* 364 */     MCH_SeatInfo seatInfo = ac.getSeatInfo((Entity)this);
/* 365 */     return ((seatInfo == null || !seatInfo.rotSeat) && !(ac instanceof com.norwood.mcheli.vehicle.MCH_EntityVehicle)) ? 
/* 366 */       ac.getTransformedPosition((ws.getCurrentWeapon()).position) : 
/* 367 */       ac.calcOnTurretPos((ws.getCurrentWeapon()).position).addVector(ac.posX, ac.posY, ac.posZ);
/*     */   }
/*     */   
/*     */   private boolean isInAttackable(EntityLivingBase entity, MCH_EntityAircraft ac, MCH_WeaponSet ws, Vec3d pos) {
/* 371 */     if (ac instanceof com.norwood.mcheli.vehicle.MCH_EntityVehicle) {
/* 372 */       return true;
/*     */     }
/*     */     try {
/* 375 */       if (ac.getCurrentWeapon((Entity)this).getCurrentWeapon() instanceof com.norwood.mcheli.weapon.MCH_WeaponEntitySeeker) {
/* 376 */         return true;
/*     */       }
/* 378 */       MCH_AircraftInfo.Weapon wi = ac.getAcInfo().getWeaponById(ac.getCurrentWeaponID((Entity)this));
/* 379 */       Vec3d v1 = new Vec3d(0.0D, 0.0D, 1.0D);
/* 380 */       float yaw = -ac.getRotYaw() + (wi.maxYaw + wi.minYaw) / 2.0F - wi.defaultYaw;
/* 381 */       v1 = v1.rotateYaw(yaw * 3.1415927F / 180.0F);
/* 382 */       Vec3d v2 = (new Vec3d(entity.posX - pos.x, 0.0D, entity.posZ - pos.z)).normalize();
/* 383 */       double dot = v1.dotProduct(v2);
/* 384 */       double rad = Math.acos(dot);
/* 385 */       double deg = rad * 180.0D / Math.PI;
/* 386 */       return (deg < (Math.abs(wi.maxYaw - wi.minYaw) / 2.0F));
/*     */     }
/* 388 */     catch (Exception var15) {
/* 389 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MCH_EntityAircraft getAc() {
/* 396 */     if (getRidingEntity() == null) {
/* 397 */       return null;
/*     */     }
/* 399 */     return (getRidingEntity() instanceof MCH_EntityAircraft) ? 
/* 400 */       (MCH_EntityAircraft)getRidingEntity() : (
/* 401 */       (getRidingEntity() instanceof MCH_EntitySeat) ? ((MCH_EntitySeat)getRidingEntity()).getParent() : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
/* 406 */     super.writeEntityToNBT(nbt);
/* 407 */     nbt.setBoolean("Creative", this.isCreative);
/* 408 */     nbt.setString("OwnerUUID", this.ownerUUID);
/* 409 */     nbt.setString("TeamName", getTeamName());
/* 410 */     nbt.setInteger("TargetType", this.targetType);
/*     */   }
/*     */   
/*     */   public void readEntityFromNBT(@NotNull NBTTagCompound nbt) {
/* 414 */     super.readEntityFromNBT(nbt);
/* 415 */     this.isCreative = nbt.getBoolean("Creative");
/* 416 */     this.ownerUUID = nbt.getString("OwnerUUID");
/* 417 */     setTeamName(nbt.getString("TeamName"));
/* 418 */     this.targetType = nbt.getInteger("TargetType");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity changeDimension(int dimensionIn, @NotNull ITeleporter teleporter) {
/* 423 */     return null;
/*     */   }
/*     */   
/*     */   public void setDead() {
/* 427 */     if (!this.world.isRemote && !this.isDead && !this.isCreative) {
/* 428 */       if (this.targetType == 0) {
/* 429 */         dropItem((Item)MCH_MOD.itemSpawnGunnerVsMonster, 1);
/*     */       } else {
/* 431 */         dropItem((Item)MCH_MOD.itemSpawnGunnerVsPlayer, 1);
/*     */       } 
/*     */     }
/*     */     
/* 435 */     super.setDead();
/* 436 */     MCH_Lib.DbgLog(this.world, "MCH_EntityGunner.setDead type=%d :" + this, new Object[] { Integer.valueOf(this.targetType) });
/*     */   }
/*     */   
/*     */   public boolean attackEntityFrom(@NotNull DamageSource ds, float amount) {
/* 440 */     if (ds == DamageSource.OUT_OF_WORLD) {
/* 441 */       setDead();
/*     */     }
/*     */     
/* 444 */     return super.attackEntityFrom(ds, amount);
/*     */   }
/*     */   @NotNull
/*     */   public ItemStack getItemStackFromSlot(@NotNull EntityEquipmentSlot slotIn) {
/* 448 */     return ItemStack.EMPTY;
/*     */   }
/*     */   
/*     */   public void setItemStackToSlot(@NotNull EntityEquipmentSlot slotIn, @NotNull ItemStack stack) {}
/*     */   
/*     */   @NotNull
/*     */   public Iterable<ItemStack> getArmorInventoryList() {
/* 455 */     return Collections.emptyList();
/*     */   }
/*     */   @NotNull
/*     */   public EnumHandSide getPrimaryHand() {
/* 459 */     return EnumHandSide.RIGHT;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\mob\MCH_EntityGunner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */