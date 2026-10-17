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
/*  45 */   private static final DataParameter<String> TEAM_NAME = EntityDataManager.func_187226_a(MCH_EntityGunner.class, DataSerializers.field_187194_d);
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
/*  65 */     func_70107_b(x, y, z);
/*     */   }
/*     */   
/*     */   protected void func_70088_a() {
/*  69 */     super.func_70088_a();
/*  70 */     this.field_70180_af.func_187214_a(TEAM_NAME, "");
/*     */   }
/*     */   
/*     */   public String getTeamName() {
/*  74 */     return (String)this.field_70180_af.func_187225_a(TEAM_NAME);
/*     */   }
/*     */   
/*     */   public void setTeamName(String name) {
/*  78 */     this.field_70180_af.func_187227_b(TEAM_NAME, name);
/*     */   }
/*     */   
/*     */   public Team func_96124_cp() {
/*  82 */     return (Team)this.field_70170_p.func_96441_U().func_96508_e(getTeamName());
/*     */   }
/*     */   
/*     */   public boolean func_184191_r(@NotNull Entity entityIn) {
/*  86 */     return super.func_184191_r(entityIn);
/*     */   }
/*     */   @NotNull
/*     */   public ITextComponent func_145748_c_() {
/*  90 */     Team team = func_96124_cp();
/*  91 */     return (team != null) ? (ITextComponent)new TextComponentString(ScorePlayerTeam.func_96667_a(team, team.func_96661_b() + " Gunner")) : (ITextComponent)new TextComponentString("");
/*     */   }
/*     */   
/*     */   public boolean func_180431_b(DamageSource source) {
/*  95 */     return this.isCreative;
/*     */   }
/*     */   
/*     */   public void func_70645_a(@NotNull DamageSource source) {
/*  99 */     super.func_70645_a(source);
/*     */   }
/*     */   
/*     */   public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
/* 103 */     if (this.field_70170_p.field_72995_K)
/* 104 */       return false; 
/* 105 */     if (func_184187_bx() == null)
/* 106 */       return false; 
/* 107 */     if (player.field_71075_bZ.field_75098_d) {
/* 108 */       removeFromAircraft(player);
/* 109 */       return true;
/* 110 */     }  if (this.isCreative) {
/* 111 */       player.func_145747_a((ITextComponent)new TextComponentString("Creative mode only."));
/* 112 */       return false;
/* 113 */     }  if (func_96124_cp() != null && !func_184191_r((Entity)player)) {
/* 114 */       player.func_145747_a((ITextComponent)new TextComponentString("You are other team."));
/* 115 */       return false;
/*     */     } 
/* 117 */     removeFromAircraft(player);
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFromAircraft(EntityPlayer player) {
/* 123 */     if (!this.field_70170_p.field_72995_K) {
/* 124 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "wrench", 1.0F, 1.0F);
/* 125 */       func_70106_y();
/* 126 */       MCH_EntityAircraft ac = null;
/* 127 */       if (func_184187_bx() instanceof MCH_EntityAircraft) {
/* 128 */         ac = (MCH_EntityAircraft)func_184187_bx();
/* 129 */       } else if (func_184187_bx() instanceof MCH_EntitySeat) {
/* 130 */         ac = ((MCH_EntitySeat)func_184187_bx()).getParent();
/*     */       } 
/*     */       
/* 133 */       String name = "";
/* 134 */       if (ac != null && ac.getAcInfo() != null) {
/* 135 */         name = " on " + (ac.getAcInfo()).displayName + " seat " + (ac.getSeatIdByEntity((Entity)this) + 1);
/*     */       }
/*     */       
/* 138 */       player.func_145747_a((ITextComponent)new TextComponentString("Remove gunner" + name + " by " + 
/*     */             
/* 140 */             ScorePlayerTeam.func_96667_a(player.func_96124_cp(), player.func_145748_c_().func_150254_d()) + "."));
/*     */ 
/*     */       
/* 143 */       func_184210_p();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/* 148 */     super.func_70071_h_();
/* 149 */     if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
/* 150 */       if (func_184187_bx() != null && (func_184187_bx()).field_70128_L) {
/* 151 */         func_184210_p();
/*     */       }
/*     */       
/* 154 */       if (func_184187_bx() instanceof MCH_EntityAircraft) {
/* 155 */         shotTarget((MCH_EntityAircraft)func_184187_bx());
/* 156 */       } else if (func_184187_bx() instanceof MCH_EntitySeat && ((MCH_EntitySeat)func_184187_bx()).getParent() != null) {
/* 157 */         shotTarget(((MCH_EntitySeat)func_184187_bx()).getParent());
/* 158 */       } else if (this.despawnCount < 20) {
/* 159 */         this.despawnCount++;
/* 160 */       } else if (func_184187_bx() == null || this.field_70173_aa > 100) {
/* 161 */         func_70106_y();
/*     */       } 
/*     */       
/* 164 */       if (this.targetEntity == null) {
/* 165 */         if (this.idleCount == 0) {
/* 166 */           this.idleCount = (3 + this.field_70146_Z.nextInt(5)) * 20;
/* 167 */           this.idleRotation = this.field_70146_Z.nextInt(5) - 2;
/*     */         } 
/*     */         
/* 170 */         this.field_70177_z += this.idleRotation / 2.0F;
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
/* 193 */       ret = (entity != this && !(entity instanceof net.minecraft.entity.monster.EntityEnderman) && !entity.field_70128_L && !func_184191_r((Entity)entity) && entity.func_110143_aJ() > 0.0F && !ac.isMountedEntity((Entity)entity));
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 201 */       ret = (entity != this && !((EntityPlayer)entity).field_71075_bZ.field_75098_d && !entity.field_70128_L && !getTeamName().isEmpty() && !func_184191_r((Entity)entity) && entity.func_110143_aJ() > 0.0F && !ac.isMountedEntity((Entity)entity));
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
/* 217 */         if (this.targetEntity != null && (this.targetEntity.field_70128_L || ((EntityLivingBase)this.targetEntity)
/* 218 */           .func_110143_aJ() <= 0.0F) && this.switchTargetCount > 20)
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
/* 231 */             list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(rh, rv, rh), IMob.field_82192_a);
/*     */           } else {
/* 233 */             int rh = MCH_Config.RangeOfGunner_VsPlayer_Horizontal.prmInt;
/* 234 */             int rv = MCH_Config.RangeOfGunner_VsPlayer_Vertical.prmInt;
/* 235 */             list = this.field_70170_p.func_72872_a(EntityPlayer.class, func_174813_aQ().func_72314_b(rh, rv, rh));
/*     */           } 
/*     */           
/* 238 */           for (EntityLivingBase entity : list) {
/* 239 */             if (canAttackEntity(entity, ac, ws) && 
/* 240 */               checkPitch(entity, ac, pos) && (nextTarget == null || 
/* 241 */               func_70032_d((Entity)entity) < func_70032_d((Entity)nextTarget)) && 
/* 242 */               func_70685_l((Entity)entity) && 
/* 243 */               isInAttackable(entity, ac, ws, pos)) {
/* 244 */               nextTarget = entity;
/* 245 */               this.switchTargetCount = 60;
/*     */             } 
/*     */           } 
/*     */           
/* 249 */           if (nextTarget != null && this.targetEntity != nextTarget) {
/* 250 */             this.targetPrevPosX = nextTarget.field_70165_t;
/* 251 */             this.targetPrevPosY = nextTarget.field_70163_u;
/* 252 */             this.targetPrevPosZ = nextTarget.field_70161_v;
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
/* 264 */           this.field_70125_A = MathHelper.func_76142_g(this.field_70125_A);
/* 265 */           this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
/* 266 */           double dist = func_70032_d(this.targetEntity);
/* 267 */           double tick = 1.0D;
/* 268 */           if (dist >= 10.0D && (ws.getInfo()).acceleration > 1.0F) {
/* 269 */             tick = dist / (ws.getInfo()).acceleration;
/*     */           }
/*     */           
/* 272 */           if (this.targetEntity.func_184187_bx() instanceof MCH_EntitySeat || this.targetEntity.func_184187_bx() instanceof MCH_EntityAircraft) {
/* 273 */             tick -= MCH_Config.HitBoxDelayTick.prmInt;
/*     */           }
/*     */           
/* 276 */           double dx = (this.targetEntity.field_70165_t - this.targetPrevPosX) * tick;
/* 277 */           double dy = (this.targetEntity.field_70163_u - this.targetPrevPosY) * tick + this.targetEntity.field_70131_O * this.field_70146_Z.nextDouble();
/* 278 */           double dz = (this.targetEntity.field_70161_v - this.targetPrevPosZ) * tick;
/* 279 */           double d0 = this.targetEntity.field_70165_t + dx - pos.field_72450_a;
/* 280 */           double d1 = this.targetEntity.field_70163_u + dy - pos.field_72448_b;
/* 281 */           double d2 = this.targetEntity.field_70161_v + dz - pos.field_72449_c;
/* 282 */           double d3 = MathHelper.func_76133_a(d0 * d0 + d2 * d2);
/* 283 */           float yaw = MathHelper.func_76142_g((float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F);
/* 284 */           float pitch = (float)-(Math.atan2(d1, d3) * 180.0D / Math.PI);
/* 285 */           if (Math.abs(this.field_70125_A - pitch) < rotSpeed && Math.abs(this.field_70177_z - yaw) < rotSpeed) {
/* 286 */             float r = ac.isPilot((Entity)this) ? 0.1F : 0.5F;
/* 287 */             this.field_70125_A = pitch + (this.field_70146_Z.nextFloat() - 0.5F) * r - cw.fixRotationPitch;
/* 288 */             this.field_70177_z = yaw + (this.field_70146_Z.nextFloat() - 0.5F) * r;
/* 289 */             if (!this.waitCooldown || ws.currentHeat <= 0 || (ws.getInfo()).maxHeatCount <= 0) {
/* 290 */               this.waitCooldown = false;
/* 291 */               MCH_WeaponParam prm = new MCH_WeaponParam();
/* 292 */               prm.setPosition(ac.field_70165_t, ac.field_70163_u, ac.field_70161_v);
/* 293 */               prm.user = (Entity)this;
/* 294 */               prm.entity = (Entity)ac;
/* 295 */               prm.option1 = (cw instanceof com.norwood.mcheli.weapon.MCH_WeaponEntitySeeker) ? this.targetEntity.func_145782_y() : 0;
/* 296 */               if (ac.useCurrentWeapon(prm) && (ws.getInfo()).maxHeatCount > 0 && ws.currentHeat > (ws.getInfo()).maxHeatCount * 4 / 5) {
/* 297 */                 this.waitCooldown = true;
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/* 302 */           if (Math.abs(pitch - this.field_70125_A) >= rotSpeed) {
/* 303 */             this.field_70125_A += (pitch > this.field_70125_A) ? rotSpeed : -rotSpeed;
/*     */           }
/*     */           
/* 306 */           if (Math.abs(yaw - this.field_70177_z) >= rotSpeed) {
/* 307 */             if (Math.abs(yaw - this.field_70177_z) <= 180.0F) {
/* 308 */               this.field_70177_z += (yaw > this.field_70177_z) ? rotSpeed : -rotSpeed;
/*     */             } else {
/* 310 */               this.field_70177_z += (yaw > this.field_70177_z) ? -rotSpeed : rotSpeed;
/*     */             } 
/*     */           }
/*     */           
/* 314 */           this.field_70759_as = this.field_70177_z;
/* 315 */           this.targetPrevPosX = this.targetEntity.field_70165_t;
/* 316 */           this.targetPrevPosY = this.targetEntity.field_70163_u;
/* 317 */           this.targetPrevPosZ = this.targetEntity.field_70161_v;
/*     */         } else {
/* 319 */           this.field_70125_A *= 0.95F;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkPitch(EntityLivingBase entity, MCH_EntityAircraft ac, Vec3d pos) {
/*     */     try {
/* 328 */       double d0 = entity.field_70165_t - pos.field_72450_a;
/* 329 */       double d1 = entity.field_70163_u - pos.field_72448_b;
/* 330 */       double d2 = entity.field_70161_v - pos.field_72449_c;
/* 331 */       double d3 = MathHelper.func_76133_a(d0 * d0 + d2 * d2);
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
/* 367 */       ac.calcOnTurretPos((ws.getCurrentWeapon()).position).func_72441_c(ac.field_70165_t, ac.field_70163_u, ac.field_70161_v);
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
/* 381 */       v1 = v1.func_178785_b(yaw * 3.1415927F / 180.0F);
/* 382 */       Vec3d v2 = (new Vec3d(entity.field_70165_t - pos.field_72450_a, 0.0D, entity.field_70161_v - pos.field_72449_c)).func_72432_b();
/* 383 */       double dot = v1.func_72430_b(v2);
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
/* 396 */     if (func_184187_bx() == null) {
/* 397 */       return null;
/*     */     }
/* 399 */     return (func_184187_bx() instanceof MCH_EntityAircraft) ? 
/* 400 */       (MCH_EntityAircraft)func_184187_bx() : (
/* 401 */       (func_184187_bx() instanceof MCH_EntitySeat) ? ((MCH_EntitySeat)func_184187_bx()).getParent() : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70014_b(@NotNull NBTTagCompound nbt) {
/* 406 */     super.func_70014_b(nbt);
/* 407 */     nbt.func_74757_a("Creative", this.isCreative);
/* 408 */     nbt.func_74778_a("OwnerUUID", this.ownerUUID);
/* 409 */     nbt.func_74778_a("TeamName", getTeamName());
/* 410 */     nbt.func_74768_a("TargetType", this.targetType);
/*     */   }
/*     */   
/*     */   public void func_70037_a(@NotNull NBTTagCompound nbt) {
/* 414 */     super.func_70037_a(nbt);
/* 415 */     this.isCreative = nbt.func_74767_n("Creative");
/* 416 */     this.ownerUUID = nbt.func_74779_i("OwnerUUID");
/* 417 */     setTeamName(nbt.func_74779_i("TeamName"));
/* 418 */     this.targetType = nbt.func_74762_e("TargetType");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity changeDimension(int dimensionIn, @NotNull ITeleporter teleporter) {
/* 423 */     return null;
/*     */   }
/*     */   
/*     */   public void func_70106_y() {
/* 427 */     if (!this.field_70170_p.field_72995_K && !this.field_70128_L && !this.isCreative) {
/* 428 */       if (this.targetType == 0) {
/* 429 */         func_145779_a((Item)MCH_MOD.itemSpawnGunnerVsMonster, 1);
/*     */       } else {
/* 431 */         func_145779_a((Item)MCH_MOD.itemSpawnGunnerVsPlayer, 1);
/*     */       } 
/*     */     }
/*     */     
/* 435 */     super.func_70106_y();
/* 436 */     MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityGunner.setDead type=%d :" + this, new Object[] { Integer.valueOf(this.targetType) });
/*     */   }
/*     */   
/*     */   public boolean func_70097_a(@NotNull DamageSource ds, float amount) {
/* 440 */     if (ds == DamageSource.field_76380_i) {
/* 441 */       func_70106_y();
/*     */     }
/*     */     
/* 444 */     return super.func_70097_a(ds, amount);
/*     */   }
/*     */   @NotNull
/*     */   public ItemStack func_184582_a(@NotNull EntityEquipmentSlot slotIn) {
/* 448 */     return ItemStack.field_190927_a;
/*     */   }
/*     */   
/*     */   public void func_184201_a(@NotNull EntityEquipmentSlot slotIn, @NotNull ItemStack stack) {}
/*     */   
/*     */   @NotNull
/*     */   public Iterable<ItemStack> func_184193_aE() {
/* 455 */     return Collections.emptyList();
/*     */   }
/*     */   @NotNull
/*     */   public EnumHandSide func_184591_cq() {
/* 459 */     return EnumHandSide.RIGHT;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\mob\MCH_EntityGunner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */