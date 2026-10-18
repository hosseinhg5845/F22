/*     */ package com.norwood.mcheli.chain;
/*     */ 
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityChain
/*     */   extends W_Entity {
/*  25 */   private static final DataParameter<Integer> TOWED_ID = EntityDataManager.createKey(MCH_EntityChain.class, DataSerializers.VARINT);
/*  26 */   private static final DataParameter<Integer> TOW_ID = EntityDataManager.createKey(MCH_EntityChain.class, DataSerializers.VARINT);
/*     */   public Entity towEntity;
/*     */   public Entity towedEntity;
/*     */   public String towEntityUUID;
/*     */   public String towedEntityUUID;
/*     */   private int isServerTowEntitySearchCount;
/*     */   private int chainLength;
/*     */   private boolean isTowing;
/*     */   
/*     */   public MCH_EntityChain(World world) {
/*  36 */     super(world);
/*  37 */     this.preventEntitySpawning = true;
/*  38 */     setSize(1.0F, 1.0F);
/*  39 */     this.towEntity = null;
/*  40 */     this.towedEntity = null;
/*  41 */     this.towEntityUUID = "";
/*  42 */     this.towedEntityUUID = "";
/*  43 */     this.isTowing = false;
/*  44 */     this.ignoreFrustumCheck = true;
/*  45 */     setChainLength(4);
/*  46 */     this.isServerTowEntitySearchCount = 50;
/*     */   }
/*     */   
/*     */   public MCH_EntityChain(World par1World, double par2, double par4, double par6) {
/*  50 */     this(par1World);
/*  51 */     setPosition(par2, par4, par6);
/*  52 */     this.motionX = 0.0D;
/*  53 */     this.motionY = 0.0D;
/*  54 */     this.motionZ = 0.0D;
/*  55 */     this.prevPosX = par2;
/*  56 */     this.prevPosY = par4;
/*  57 */     this.prevPosZ = par6;
/*     */   }
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  66 */     this.dataManager.register(TOWED_ID, Integer.valueOf(0));
/*  67 */     this.dataManager.register(TOW_ID, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBox(Entity par1Entity) {
/*  71 */     return par1Entity.getEntityBoundingBox();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox() {
/*  75 */     return null;
/*     */   }
/*     */   
/*     */   public boolean canBePushed() {
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource d, float par2) {
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   public int getChainLength() {
/*  88 */     return this.chainLength;
/*     */   }
/*     */   
/*     */   public void setChainLength(int n) {
/*  92 */     if (n > 15) {
/*  93 */       n = 15;
/*     */     }
/*     */     
/*  96 */     if (n < 3) {
/*  97 */       n = 3;
/*     */     }
/*     */     
/* 100 */     this.chainLength = n;
/*     */   }
/*     */   
/*     */   public void setDead() {
/* 104 */     super.setDead();
/* 105 */     playDisconnectTowingEntity();
/* 106 */     this.isTowing = false;
/* 107 */     this.towEntity = null;
/* 108 */     this.towedEntity = null;
/*     */   }
/*     */   
/*     */   public boolean isTowingEntity() {
/* 112 */     return (this.isTowing && !this.isDead && this.towedEntity != null && this.towEntity != null);
/*     */   }
/*     */   
/*     */   public void setTowEntity(Entity towedEntity, Entity towEntity) {
/* 116 */     if (towedEntity != null && towEntity != null && !towedEntity.isDead && !towEntity.isDead && !W_Entity.isEqual(towedEntity, towEntity)) {
/* 117 */       this.isTowing = true;
/* 118 */       this.towedEntity = towedEntity;
/* 119 */       this.towEntity = towEntity;
/* 120 */       if (!this.world.isRemote) {
/* 121 */         this.dataManager.set(TOWED_ID, Integer.valueOf(W_Entity.getEntityId(towedEntity)));
/* 122 */         this.dataManager.set(TOW_ID, Integer.valueOf(W_Entity.getEntityId(towedEntity)));
/* 123 */         this.isServerTowEntitySearchCount = 0;
/*     */       } 
/*     */       
/* 126 */       if (towEntity instanceof MCH_EntityAircraft) {
/* 127 */         ((MCH_EntityAircraft)towEntity).setTowChainEntity(this);
/*     */       }
/*     */       
/* 130 */       if (towedEntity instanceof MCH_EntityAircraft) {
/* 131 */         ((MCH_EntityAircraft)towedEntity).setTowedChainEntity(this);
/*     */       }
/*     */     } else {
/* 134 */       this.isTowing = false;
/* 135 */       this.towedEntity = null;
/* 136 */       this.towEntity = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void searchTowingEntity() {
/* 141 */     if ((this.towedEntity == null || this.towEntity == null) && !this.towedEntityUUID.isEmpty() && !this.towEntityUUID.isEmpty()) {
/* 142 */       List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(32.0D, 32.0D, 32.0D));
/* 143 */       for (Entity entity : list) {
/* 144 */         String uuid = entity.getPersistentID().toString();
/* 145 */         if (this.towedEntity == null && uuid.compareTo(this.towedEntityUUID) == 0) {
/* 146 */           this.towedEntity = entity; continue;
/* 147 */         }  if (this.towEntity == null && uuid.compareTo(this.towEntityUUID) == 0) {
/* 148 */           this.towEntity = entity; continue;
/* 149 */         }  if (this.towEntity != null && this.towedEntity != null) {
/* 150 */           setTowEntity(this.towedEntity, this.towEntity);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 158 */     super.onUpdate();
/* 159 */     if (this.towedEntity == null || this.towedEntity.isDead || this.towEntity == null || this.towEntity.isDead) {
/* 160 */       this.towedEntity = null;
/* 161 */       this.towEntity = null;
/* 162 */       this.isTowing = false;
/*     */     } 
/*     */     
/* 165 */     if (this.towedEntity != null) {
/* 166 */       this.towedEntity.fallDistance = 0.0F;
/*     */     }
/*     */     
/* 169 */     this.prevPosX = this.posX;
/* 170 */     this.prevPosY = this.posY;
/* 171 */     this.prevPosZ = this.posZ;
/* 172 */     if (this.world.isRemote) {
/* 173 */       onUpdate_Client();
/*     */     } else {
/* 175 */       onUpdate_Server();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdate_Client() {
/* 180 */     if (!isTowingEntity()) {
/* 181 */       setTowEntity(this.world.getEntityByID(((Integer)this.dataManager.get(TOWED_ID)).intValue()), this.world.getEntityByID(((Integer)this.dataManager.get(TOW_ID)).intValue()));
/*     */     }
/*     */     
/* 184 */     double d4 = this.posX + this.motionX;
/* 185 */     double d5 = this.posY + this.motionY;
/* 186 */     double d11 = this.posZ + this.motionZ;
/* 187 */     setPosition(d4, d5, d11);
/* 188 */     if (this.onGround) {
/* 189 */       this.motionX *= 0.5D;
/* 190 */       this.motionY *= 0.5D;
/* 191 */       this.motionZ *= 0.5D;
/*     */     } 
/*     */     
/* 194 */     this.motionX *= 0.99D;
/* 195 */     this.motionY *= 0.95D;
/* 196 */     this.motionZ *= 0.99D;
/*     */   }
/*     */   
/*     */   public void onUpdate_Server() {
/* 200 */     if (this.isServerTowEntitySearchCount > 0) {
/* 201 */       searchTowingEntity();
/* 202 */       if (this.towEntity != null && this.towedEntity != null) {
/* 203 */         this.isServerTowEntitySearchCount = 0;
/*     */       } else {
/* 205 */         this.isServerTowEntitySearchCount--;
/*     */       } 
/* 207 */     } else if (this.towEntity == null || this.towedEntity == null) {
/* 208 */       setDead();
/*     */     } 
/*     */     
/* 211 */     this.motionY -= 0.01D;
/* 212 */     if (!this.isTowing) {
/* 213 */       this.motionX *= 0.8D;
/* 214 */       this.motionY *= 0.8D;
/* 215 */       this.motionZ *= 0.8D;
/*     */     } 
/*     */     
/* 218 */     move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 219 */     if (isTowingEntity()) {
/* 220 */       setPosition(this.towEntity.posX, this.towEntity.posY + 2.0D, this.towEntity.posZ);
/* 221 */       updateTowingEntityPosRot();
/*     */     } 
/*     */     
/* 224 */     this.motionX *= 0.99D;
/* 225 */     this.motionY *= 0.95D;
/* 226 */     this.motionZ *= 0.99D;
/*     */   }
/*     */   
/*     */   public void updateTowingEntityPosRot() {
/* 230 */     double dx = this.towedEntity.posX - this.towEntity.posX;
/* 231 */     double dy = this.towedEntity.posY - this.towEntity.posY;
/* 232 */     double dz = this.towedEntity.posZ - this.towEntity.posZ;
/* 233 */     double dist = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
/* 234 */     float DIST = getChainLength();
/* 235 */     float MAX_DIST = (getChainLength() + 2);
/* 236 */     if (dist > DIST) {
/* 237 */       this.towedEntity.rotationYaw = (float)(Math.atan2(dz, dx) * 180.0D / Math.PI) + 90.0F;
/* 238 */       this.towedEntity.prevRotationYaw = this.towedEntity.rotationYaw;
/* 239 */       double tmp = dist - DIST;
/* 240 */       float accl = 0.001F;
/* 241 */       this.towedEntity.motionX -= dx * accl / tmp;
/* 242 */       this.towedEntity.motionY -= dy * accl / tmp;
/* 243 */       this.towedEntity.motionZ -= dz * accl / tmp;
/* 244 */       if (dist > MAX_DIST) {
/* 245 */         this.towedEntity
/* 246 */           .setPosition(this.towEntity.posX + dx * MAX_DIST / dist, this.towEntity.posY + dy * MAX_DIST / dist, this.towEntity.posZ + dz * MAX_DIST / dist);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void playDisconnectTowingEntity() {
/* 252 */     W_WorldFunc.MOD_playSoundEffect(this.world, this.posX, this.posY, this.posZ, "chain_ct", 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
/* 256 */     if (this.isTowing && this.towEntity != null && !this.towEntity.isDead && this.towedEntity != null && !this.towedEntity.isDead) {
/* 257 */       nbt.setString("TowEntityUUID", this.towEntity.getPersistentID().toString());
/* 258 */       nbt.setString("TowedEntityUUID", this.towedEntity.getPersistentID().toString());
/* 259 */       nbt.setInteger("ChainLength", getChainLength());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound nbt) {
/* 264 */     this.towEntityUUID = nbt.getString("TowEntityUUID");
/* 265 */     this.towedEntityUUID = nbt.getString("TowedEntityUUID");
/* 266 */     setChainLength(nbt.getInteger("ChainLength"));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 271 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean processInitialInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 275 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\chain\MCH_EntityChain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */