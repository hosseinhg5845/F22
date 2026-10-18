/*     */ package com.norwood.mcheli.container;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_IEntityCanRideAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_SeatRackInfo;
/*     */ import com.norwood.mcheli.wrapper.W_AxisAlignedBB;
/*     */ import com.norwood.mcheli.wrapper.W_Block;
/*     */ import com.norwood.mcheli.wrapper.W_Blocks;
/*     */ import com.norwood.mcheli.wrapper.W_EntityContainer;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityContainer extends W_EntityContainer implements MCH_IEntityCanRideAircraft {
/*  34 */   private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(MCH_EntityContainer.class, DataSerializers.VARINT); public static final float Y_OFFSET = 0.5F;
/*  35 */   private static final DataParameter<Integer> FORWARD_DIR = EntityDataManager.createKey(MCH_EntityContainer.class, DataSerializers.VARINT);
/*  36 */   private static final DataParameter<Integer> DAMAGE_TAKEN = EntityDataManager.createKey(MCH_EntityContainer.class, DataSerializers.VARINT);
/*  37 */   private double speedMultiplier = 0.07D;
/*     */   private int boatPosRotationIncrements;
/*     */   private double boatX;
/*     */   private double boatY;
/*     */   private double boatZ;
/*     */   private double boatYaw;
/*     */   private double boatPitch;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityY;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityZ;
/*     */   
/*     */   public MCH_EntityContainer(World par1World) {
/*  52 */     super(par1World);
/*  53 */     this.preventEntitySpawning = true;
/*  54 */     setSize(2.0F, 1.0F);
/*  55 */     this.stepHeight = 0.6F;
/*  56 */     this.isImmuneToFire = true;
/*  57 */     this._renderDistanceWeight = 2.0D;
/*     */   }
/*     */   
/*     */   public MCH_EntityContainer(World par1World, double par2, double par4, double par6) {
/*  61 */     this(par1World);
/*  62 */     setPosition(par2, par4 + 0.5D, par6);
/*  63 */     this.motionX = 0.0D;
/*  64 */     this.motionY = 0.0D;
/*  65 */     this.motionZ = 0.0D;
/*  66 */     this.prevPosX = par2;
/*  67 */     this.prevPosY = par4;
/*  68 */     this.prevPosZ = par6;
/*     */   }
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  77 */     this.dataManager.register(TIME_SINCE_HIT, Integer.valueOf(0));
/*  78 */     this.dataManager.register(FORWARD_DIR, Integer.valueOf(1));
/*  79 */     this.dataManager.register(DAMAGE_TAKEN, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBox(Entity par1Entity) {
/*  83 */     return par1Entity.getEntityBoundingBox();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox() {
/*  87 */     return getEntityBoundingBox();
/*     */   }
/*     */   
/*     */   public boolean canBePushed() {
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  96 */     return 54;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInvName() {
/* 101 */     return "Container " + super.getInvName();
/*     */   }
/*     */   
/*     */   public double getMountedYOffset() {
/* 105 */     return -0.3D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource ds, float damage) {
/* 110 */     if (isEntityInvulnerable(ds))
/* 111 */       return false; 
/* 112 */     if (!this.world.isRemote && !this.isDead) {
/* 113 */       damage = MCH_Config.applyDamageByExternal((Entity)this, ds, damage);
/* 114 */       if (!MCH_Multiplay.canAttackEntity(ds, (Entity)this))
/* 115 */         return false; 
/* 116 */       if (ds.getTrueSource() instanceof EntityPlayer && ds.getDamageType().equalsIgnoreCase("player")) {
/* 117 */         MCH_Lib.DbgLog(this.world, "MCH_EntityContainer.attackEntityFrom:damage=%.1f:%s", new Object[] { Float.valueOf(damage), ds.getDamageType() });
/* 118 */         W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "hit", 1.0F, 1.3F);
/* 119 */         setDamageTaken(getDamageTaken() + (int)(damage * 20.0F));
/* 120 */         setForwardDirection(-getForwardDirection());
/* 121 */         setTimeSinceHit(10);
/* 122 */         markVelocityChanged();
/* 123 */         boolean flag = (ds.getTrueSource() instanceof EntityPlayer && ((EntityPlayer)ds.getTrueSource()).capabilities.isCreativeMode);
/* 124 */         if (flag || getDamageTaken() > 40.0F) {
/* 125 */           if (!flag) {
/* 126 */             dropItemWithOffset((Item)MCH_MOD.itemContainer, 1, 0.0F);
/*     */           }
/*     */           
/* 129 */           setDead();
/*     */         } 
/*     */         
/* 132 */         return true;
/*     */       } 
/* 134 */       return false;
/*     */     } 
/*     */     
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void performHurtAnimation() {
/* 143 */     setForwardDirection(-getForwardDirection());
/* 144 */     setTimeSinceHit(10);
/* 145 */     setDamageTaken(getDamageTaken() * 11);
/*     */   }
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 149 */     return !this.isDead;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
/* 154 */     this.boatPosRotationIncrements = par9 + 10;
/* 155 */     this.boatX = par1;
/* 156 */     this.boatY = par3;
/* 157 */     this.boatZ = par5;
/* 158 */     this.boatYaw = par7;
/* 159 */     this.boatPitch = par8;
/* 160 */     this.motionX = this.velocityX;
/* 161 */     this.motionY = this.velocityY;
/* 162 */     this.motionZ = this.velocityZ;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setVelocity(double par1, double par3, double par5) {
/* 167 */     this.velocityX = this.motionX = par1;
/* 168 */     this.velocityY = this.motionY = par3;
/* 169 */     this.velocityZ = this.motionZ = par5;
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 173 */     super.onUpdate();
/* 174 */     if (getTimeSinceHit() > 0) {
/* 175 */       setTimeSinceHit(getTimeSinceHit() - 1);
/*     */     }
/*     */     
/* 178 */     if (getDamageTaken() > 0.0F) {
/* 179 */       setDamageTaken(getDamageTaken() - 1);
/*     */     }
/*     */     
/* 182 */     this.prevPosX = this.posX;
/* 183 */     this.prevPosY = this.posY;
/* 184 */     this.prevPosZ = this.posZ;
/* 185 */     byte b0 = 5;
/* 186 */     double d0 = 0.0D;
/*     */     
/* 188 */     for (int i = 0; i < b0; i++) {
/* 189 */       AxisAlignedBB boundingBox = getEntityBoundingBox();
/* 190 */       double d1 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * i / b0 - 0.125D;
/* 191 */       double d2 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (i + 1) / b0 - 0.125D;
/* 192 */       AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(boundingBox.minX, d1, boundingBox.minZ, boundingBox.maxX, d2, boundingBox.maxZ);
/* 193 */       if (this.world.isMaterialInBB(axisalignedbb, Material.WATER)) {
/* 194 */         d0 += 1.0D / b0;
/* 195 */       } else if (this.world.isMaterialInBB(axisalignedbb, Material.LAVA)) {
/* 196 */         d0 += 1.0D / b0;
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     double d3 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 201 */     if (d3 > 0.2625D);
/*     */ 
/*     */     
/* 204 */     if (this.world.isRemote) {
/* 205 */       if (this.boatPosRotationIncrements > 0) {
/* 206 */         double d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
/* 207 */         double d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
/* 208 */         double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
/* 209 */         double d10 = MathHelper.wrapDegrees(this.boatYaw - this.rotationYaw);
/* 210 */         this.rotationYaw = (float)(this.rotationYaw + d10 / this.boatPosRotationIncrements);
/* 211 */         this.rotationPitch = (float)(this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
/* 212 */         this.boatPosRotationIncrements--;
/* 213 */         setPosition(d4, d5, d11);
/* 214 */         setRotation(this.rotationYaw, this.rotationPitch);
/*     */       } else {
/* 216 */         double d4 = this.posX + this.motionX;
/* 217 */         double d5 = this.posY + this.motionY;
/* 218 */         double d11 = this.posZ + this.motionZ;
/* 219 */         setPosition(d4, d5, d11);
/* 220 */         if (this.onGround) {
/* 221 */           this.motionX *= 0.8999999761581421D;
/* 222 */           this.motionZ *= 0.8999999761581421D;
/*     */         } 
/*     */         
/* 225 */         this.motionX *= 0.99D;
/* 226 */         this.motionY *= 0.95D;
/* 227 */         this.motionZ *= 0.99D;
/*     */       } 
/*     */     } else {
/* 230 */       if (d0 < 1.0D) {
/* 231 */         double d = d0 * 2.0D - 1.0D;
/* 232 */         this.motionY += 0.04D * d;
/*     */       } else {
/* 234 */         if (this.motionY < 0.0D) {
/* 235 */           this.motionY /= 2.0D;
/*     */         }
/*     */         
/* 238 */         this.motionY += 0.007D;
/*     */       } 
/*     */       
/* 241 */       double d4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 242 */       if (d4 > 0.35D) {
/* 243 */         double d = 0.35D / d4;
/* 244 */         this.motionX *= d;
/* 245 */         this.motionZ *= d;
/* 246 */         d4 = 0.35D;
/*     */       } 
/*     */       
/* 249 */       if (d4 > d3 && this.speedMultiplier < 0.35D) {
/* 250 */         this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
/* 251 */         if (this.speedMultiplier > 0.35D) {
/* 252 */           this.speedMultiplier = 0.35D;
/*     */         }
/*     */       } else {
/* 255 */         this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
/* 256 */         if (this.speedMultiplier < 0.07D) {
/* 257 */           this.speedMultiplier = 0.07D;
/*     */         }
/*     */       } 
/*     */       
/* 261 */       if (this.onGround) {
/* 262 */         this.motionX *= 0.8999999761581421D;
/* 263 */         this.motionZ *= 0.8999999761581421D;
/*     */       } 
/*     */       
/* 266 */       move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 267 */       this.motionX *= 0.99D;
/* 268 */       this.motionY *= 0.95D;
/* 269 */       this.motionZ *= 0.99D;
/* 270 */       this.rotationPitch = 0.0F;
/* 271 */       double d5 = this.rotationYaw;
/* 272 */       double d11 = this.prevPosX - this.posX;
/* 273 */       double d10 = this.prevPosZ - this.posZ;
/* 274 */       if (d11 * d11 + d10 * d10 > 0.001D) {
/* 275 */         d5 = (float)(Math.atan2(d10, d11) * 180.0D / Math.PI);
/*     */       }
/*     */       
/* 278 */       double d12 = MathHelper.wrapDegrees(d5 - this.rotationYaw);
/* 279 */       if (d12 > 5.0D) {
/* 280 */         d12 = 5.0D;
/*     */       }
/*     */       
/* 283 */       if (d12 < -5.0D) {
/* 284 */         d12 = -5.0D;
/*     */       }
/*     */       
/* 287 */       this.rotationYaw = (float)(this.rotationYaw + d12);
/* 288 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 289 */       if (!this.world.isRemote) {
/* 290 */         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(0.2D, 0.0D, 0.2D));
/* 291 */         if (!list.isEmpty()) {
/* 292 */           for (Entity entity : list) {
/* 293 */             if (entity.canBePushed() && entity instanceof MCH_EntityContainer) {
/* 294 */               entity.applyEntityCollision((Entity)this);
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 299 */         if (MCH_Config.Collision_DestroyBlock.prmBool) {
/* 300 */           for (int lx = 0; lx < 4; lx++) {
/* 301 */             int i1 = MathHelper.floor(this.posX + ((lx % 2) - 0.5D) * 0.8D);
/* 302 */             int j1 = MathHelper.floor(this.posZ + (lx / 2.0D - 0.5D) * 0.8D);
/*     */             
/* 304 */             for (int k1 = 0; k1 < 2; k1++) {
/* 305 */               int l1 = MathHelper.floor(this.posY) + k1;
/* 306 */               if (W_WorldFunc.isEqualBlock(this.world, i1, l1, j1, W_Block.getSnowLayer())) {
/* 307 */                 this.world.setBlockToAir(new BlockPos(i1, l1, j1));
/* 308 */               } else if (W_WorldFunc.isEqualBlock(this.world, i1, l1, j1, W_Blocks.WATERLILY)) {
/* 309 */                 W_WorldFunc.destroyBlock(this.world, i1, l1, j1, true);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
/* 320 */     super.writeEntityToNBT(par1NBTTagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
/* 325 */     super.readEntityFromNBT(par1NBTTagCompound);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 330 */     return 2.0F;
/*     */   }
/*     */   
/*     */   public boolean processInitialInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 334 */     displayInventory(player);
/*     */     
/* 336 */     return true;
/*     */   }
/*     */   
/*     */   public int getDamageTaken() {
/* 340 */     return ((Integer)this.dataManager.get(DAMAGE_TAKEN)).intValue();
/*     */   }
/*     */   
/*     */   public void setDamageTaken(int par1) {
/* 344 */     this.dataManager.set(DAMAGE_TAKEN, Integer.valueOf(par1));
/*     */   }
/*     */   
/*     */   public int getTimeSinceHit() {
/* 348 */     return ((Integer)this.dataManager.get(TIME_SINCE_HIT)).intValue();
/*     */   }
/*     */   
/*     */   public void setTimeSinceHit(int par1) {
/* 352 */     this.dataManager.set(TIME_SINCE_HIT, Integer.valueOf(par1));
/*     */   }
/*     */   
/*     */   public int getForwardDirection() {
/* 356 */     return ((Integer)this.dataManager.get(FORWARD_DIR)).intValue();
/*     */   }
/*     */   
/*     */   public void setForwardDirection(int par1) {
/* 360 */     this.dataManager.set(FORWARD_DIR, Integer.valueOf(par1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canRideAircraft(MCH_EntityAircraft ac, int seatID, MCH_SeatRackInfo info) {
/* 365 */     for (String s : info.names) {
/* 366 */       if (s.equalsIgnoreCase("container")) {
/* 367 */         return (ac.getRidingEntity() == null && getRidingEntity() == null);
/*     */       }
/*     */     } 
/*     */     
/* 371 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSkipNormalRender() {
/* 376 */     return getRidingEntity() instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\container\MCH_EntityContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */