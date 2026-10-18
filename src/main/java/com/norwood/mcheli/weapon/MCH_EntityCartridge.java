/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.helper.client._IModelCustom;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityCartridge
/*     */   extends W_Entity
/*     */ {
/*     */   public final String texture_name;
/*     */   public final _IModelCustom model;
/*     */   private final float bound;
/*     */   private final float gravity;
/*     */   
/*     */   public MCH_EntityCartridge(World par1World, MCH_Cartridge c, double x, double y, double z, double mx, double my, double mz) {
/*  28 */     super(par1World);
/*  29 */     setPositionAndRotation(x, y, z, 0.0F, 0.0F);
/*  30 */     this.motionX = mx;
/*  31 */     this.motionY = my;
/*  32 */     this.motionZ = mz;
/*  33 */     this.texture_name = c.name;
/*  34 */     this.model = c.model;
/*  35 */     this.bound = c.bound;
/*  36 */     this.gravity = c.gravity;
/*  37 */     this.scale = c.scale;
/*  38 */     this.countOnUpdate = 0;
/*     */   }
/*     */   private final float scale; public float targetYaw; public float targetPitch;
/*     */   private int countOnUpdate;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void spawnCartridge(World world, MCH_Cartridge cartridge, double x, double y, double z, double mx, double my, double mz, float yaw, float pitch) {
/*  45 */     if (cartridge != null) {
/*     */       
/*  47 */       MCH_EntityCartridge entityFX = new MCH_EntityCartridge(world, cartridge, x, y, z, mx + (world.rand.nextFloat() - 0.5D) * 0.07D, my, mz + (world.rand.nextFloat() - 0.5D) * 0.07D);
/*     */       
/*  49 */       entityFX.prevRotationYaw = yaw;
/*  50 */       entityFX.rotationYaw = yaw;
/*  51 */       entityFX.targetYaw = yaw;
/*  52 */       entityFX.prevRotationPitch = pitch;
/*  53 */       entityFX.rotationPitch = pitch;
/*  54 */       entityFX.targetPitch = pitch;
/*  55 */       float cy = yaw + cartridge.yaw;
/*  56 */       float cp = pitch + cartridge.pitch;
/*  57 */       double tX = (-MathHelper.sin(cy / 180.0F * 3.1415927F) * MathHelper.cos(cp / 180.0F * 3.1415927F));
/*  58 */       double tZ = (MathHelper.cos(cy / 180.0F * 3.1415927F) * MathHelper.cos(cp / 180.0F * 3.1415927F));
/*  59 */       double tY = -MathHelper.sin(cp / 180.0F * 3.1415927F);
/*  60 */       double d = MathHelper.sqrt(tX * tX + tY * tY + tZ * tZ);
/*  61 */       if (Math.abs(d) > 0.001D) {
/*  62 */         entityFX.motionX += tX * cartridge.acceleration / d;
/*  63 */         entityFX.motionY += tY * cartridge.acceleration / d;
/*  64 */         entityFX.motionZ += tZ * cartridge.acceleration / d;
/*     */       } 
/*     */       
/*  67 */       world.spawnEntity((Entity)entityFX);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getScale() {
/*  72 */     return this.scale;
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/*  76 */     this.prevPosX = this.posX;
/*  77 */     this.prevPosY = this.posY;
/*  78 */     this.prevPosZ = this.posZ;
/*  79 */     this.prevRotationYaw = this.rotationYaw;
/*  80 */     this.prevRotationPitch = this.rotationPitch;
/*  81 */     if (this.countOnUpdate < MCH_Config.AliveTimeOfCartridge.prmInt) {
/*  82 */       this.countOnUpdate++;
/*     */     } else {
/*  84 */       setDead();
/*     */     } 
/*     */     
/*  87 */     this.motionX *= 0.98D;
/*  88 */     this.motionZ *= 0.98D;
/*  89 */     this.motionY += this.gravity;
/*  90 */     move();
/*     */   }
/*     */   
/*     */   public void rotation() {
/*  94 */     if (this.rotationYaw < this.targetYaw - 3.0F) {
/*  95 */       this.rotationYaw += 10.0F;
/*  96 */       if (this.rotationYaw > this.targetYaw) {
/*  97 */         this.rotationYaw = this.targetYaw;
/*     */       }
/*  99 */     } else if (this.rotationYaw > this.targetYaw + 3.0F) {
/* 100 */       this.rotationYaw -= 10.0F;
/* 101 */       if (this.rotationYaw < this.targetYaw) {
/* 102 */         this.rotationYaw = this.targetYaw;
/*     */       }
/*     */     } 
/*     */     
/* 106 */     if (this.rotationPitch < this.targetPitch) {
/* 107 */       this.rotationPitch += 10.0F;
/* 108 */       if (this.rotationPitch > this.targetPitch) {
/* 109 */         this.rotationPitch = this.targetPitch;
/*     */       }
/* 111 */     } else if (this.rotationPitch > this.targetPitch) {
/* 112 */       this.rotationPitch -= 10.0F;
/* 113 */       if (this.rotationPitch < this.targetPitch) {
/* 114 */         this.rotationPitch = this.targetPitch;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void move() {
/* 120 */     Vec3d vec1 = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
/* 121 */     Vec3d vec2 = W_WorldFunc.getWorldVec3(this.world, this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 122 */     RayTraceResult m = W_WorldFunc.clip(this.world, vec1, vec2);
/* 123 */     double d = Math.max(Math.abs(this.motionX), Math.abs(this.motionY));
/* 124 */     d = Math.max(d, Math.abs(this.motionZ));
/* 125 */     if (W_MovingObjectPosition.isHitTypeTile(m)) {
/* 126 */       setPosition(m.hitVec.x, m.hitVec.y, m.hitVec.z);
/* 127 */       this.motionX += d * (this.rand.nextFloat() - 0.5F) * 0.10000000149011612D;
/* 128 */       this.motionY += d * (this.rand.nextFloat() - 0.5F) * 0.10000000149011612D;
/* 129 */       this.motionZ += d * (this.rand.nextFloat() - 0.5F) * 0.10000000149011612D;
/* 130 */       if (d > 0.10000000149011612D) {
/* 131 */         this.targetYaw += (float)(d * (this.rand.nextFloat() - 0.5F) * 720.0D);
/* 132 */         this.targetPitch = (float)(d * (this.rand.nextFloat() - 0.5F) * 720.0D);
/*     */       } else {
/* 134 */         this.targetPitch = 0.0F;
/*     */       } 
/*     */       
/* 137 */       switch (m.sideHit) {
/*     */         case DOWN:
/* 139 */           if (this.motionY > 0.0D) {
/* 140 */             this.motionY = -this.motionY * this.bound;
/*     */           }
/*     */           break;
/*     */         case UP:
/* 144 */           if (this.motionY < 0.0D) {
/* 145 */             this.motionY = -this.motionY * this.bound;
/*     */           }
/*     */           
/* 148 */           this.targetPitch *= 0.3F;
/*     */           break;
/*     */         case NORTH:
/* 151 */           if (this.motionZ > 0.0D) {
/* 152 */             this.motionZ = -this.motionZ * this.bound; break;
/*     */           } 
/* 154 */           this.posZ += this.motionZ;
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 158 */           if (this.motionZ < 0.0D) {
/* 159 */             this.motionZ = -this.motionZ * this.bound; break;
/*     */           } 
/* 161 */           this.posZ += this.motionZ;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 165 */           if (this.motionX > 0.0D) {
/* 166 */             this.motionX = -this.motionX * this.bound; break;
/*     */           } 
/* 168 */           this.posX += this.motionX;
/*     */           break;
/*     */         
/*     */         case EAST:
/* 172 */           if (this.motionX < 0.0D) {
/* 173 */             this.motionX = -this.motionX * this.bound; break;
/*     */           } 
/* 175 */           this.posX += this.motionX;
/*     */           break;
/*     */       } 
/*     */     } else {
/* 179 */       this.posX += this.motionX;
/* 180 */       this.posY += this.motionY;
/* 181 */       this.posZ += this.motionZ;
/* 182 */       if (d > 0.05000000074505806D)
/* 183 */         rotation(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void readEntityFromNBT(@NotNull NBTTagCompound var1) {}
/*     */   
/*     */   protected void writeEntityToNBT(@NotNull NBTTagCompound var1) {}
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityCartridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */