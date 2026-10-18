/*     */ package com.norwood.mcheli.weapon;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.wrapper.W_Blocks;
/*     */ import com.norwood.mcheli.wrapper.W_Item;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.ForgeEventFactory;
/*     */ 
/*     */ public class MCH_EntityDispensedItem extends MCH_EntityBaseBullet {
/*     */   public MCH_EntityDispensedItem(World par1World) {
/*  21 */     super(par1World);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityDispensedItem(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/*  27 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  32 */     super.onUpdate();
/*  33 */     if (getInfo() != null && !(getInfo()).disableSmoke) {
/*  34 */       spawnParticle((getInfo()).trajectoryParticleName, 3, 7.0F * (getInfo()).smokeSize);
/*     */     }
/*     */     
/*  37 */     if (!this.world.isRemote && getInfo() != null) {
/*  38 */       if (this.acceleration < 1.0E-4D) {
/*  39 */         this.motionX *= 0.999D;
/*  40 */         this.motionZ *= 0.999D;
/*     */       } 
/*     */       
/*  43 */       if (isInWater()) {
/*  44 */         this.motionX *= (getInfo()).velocityInWater;
/*  45 */         this.motionY *= (getInfo()).velocityInWater;
/*  46 */         this.motionZ *= (getInfo()).velocityInWater;
/*     */       } 
/*     */     } 
/*     */     
/*  50 */     onUpdateBomblet();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onImpact(RayTraceResult m, float damageFactor) {
/*  55 */     if (!this.world.isRemote) {
/*  56 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 2000.0D, 0.0D));
/*  57 */       EntityPlayer player = null;
/*  58 */       Item item = null;
/*  59 */       int itemDamage = 0;
/*  60 */       if (m != null && getInfo() != null) {
/*  61 */         if (this.shootingAircraft instanceof EntityPlayer) {
/*  62 */           player = (EntityPlayer)this.shootingAircraft;
/*     */         }
/*     */         
/*  65 */         if (this.shootingEntity instanceof EntityPlayer) {
/*  66 */           player = (EntityPlayer)this.shootingEntity;
/*     */         }
/*     */         
/*  69 */         item = (getInfo()).dispenseItem;
/*  70 */         itemDamage = (getInfo()).dispenseDamege;
/*     */       } 
/*     */       
/*  73 */       if (player != null && !player.isDead && item != null) {
/*  74 */         MCH_DummyEntityPlayer mCH_DummyEntityPlayer = new MCH_DummyEntityPlayer(this.world, player);
/*  75 */         ((EntityPlayer)mCH_DummyEntityPlayer).rotationPitch = 90.0F;
/*  76 */         int RNG = (getInfo()).dispenseRange - 1;
/*     */         
/*  78 */         for (int x = -RNG; x <= RNG; x++) {
/*  79 */           for (int y = -RNG; y <= RNG; y++) {
/*  80 */             if (y >= 0 && y < 256) {
/*  81 */               for (int z = -RNG; z <= RNG; z++) {
/*  82 */                 int dist = x * x + y * y + z * z;
/*  83 */                 if (dist <= RNG * RNG) {
/*  84 */                   if (dist <= 0.5D * RNG * RNG) {
/*  85 */                     useItemToBlock(m.getBlockPos().add(x, y, z), item, itemDamage, (EntityPlayer)mCH_DummyEntityPlayer);
/*  86 */                   } else if (this.rand.nextInt(2) == 0) {
/*  87 */                     useItemToBlock(m.getBlockPos().add(x, y, z), item, itemDamage, (EntityPlayer)mCH_DummyEntityPlayer);
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  96 */       setDead();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void useItemToBlock(BlockPos blockpos, Item item, int itemDamage, EntityPlayer dummyPlayer) {
/* 101 */     dummyPlayer.posX = blockpos.getX() + 0.5D;
/* 102 */     dummyPlayer.posY = blockpos.getY() + 2.5D;
/* 103 */     dummyPlayer.posZ = blockpos.getZ() + 0.5D;
/* 104 */     dummyPlayer.rotationYaw = this.rand.nextInt(360);
/* 105 */     IBlockState iblockstate = this.world.getBlockState(blockpos);
/* 106 */     Block block = iblockstate.getBlock();
/* 107 */     Material blockMat = iblockstate.getMaterial();
/* 108 */     if (block != W_Blocks.AIR && blockMat != Material.AIR) {
/* 109 */       if (item == W_Item.getItemByName("water_bucket")) {
/* 110 */         if (MCH_Config.Collision_DestroyBlock.prmBool) {
/* 111 */           if (blockMat == Material.FIRE) {
/* 112 */             this.world.setBlockToAir(blockpos);
/* 113 */           } else if (blockMat == Material.LAVA) {
/* 114 */             int metadata = block.getMetaFromState(iblockstate);
/* 115 */             if (metadata == 0) {
/* 116 */               this.world
/* 117 */                 .setBlockState(blockpos, 
/* 118 */                   ForgeEventFactory.fireFluidPlaceBlockEvent(this.world, blockpos, blockpos, W_Blocks.OBSIDIAN.getDefaultState()));
/*     */             }
/* 120 */             else if (metadata <= 4) {
/* 121 */               this.world
/* 122 */                 .setBlockState(blockpos, 
/* 123 */                   ForgeEventFactory.fireFluidPlaceBlockEvent(this.world, blockpos, blockpos, W_Blocks.COBBLESTONE.getDefaultState()));
/*     */             }
/*     */           
/*     */           } 
/*     */         }
/* 128 */       } else if (item.onItemUseFirst(dummyPlayer, this.world, blockpos, EnumFacing.UP, blockpos
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 133 */           .getX(), blockpos
/* 134 */           .getY(), blockpos
/* 135 */           .getZ(), EnumHand.MAIN_HAND) == EnumActionResult.PASS && item
/*     */ 
/*     */ 
/*     */         
/* 139 */         .onItemUse(dummyPlayer, this.world, blockpos, EnumHand.MAIN_HAND, EnumFacing.UP, blockpos
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 145 */           .getX(), blockpos
/* 146 */           .getY(), blockpos
/* 147 */           .getZ()) == EnumActionResult.PASS) {
/*     */ 
/*     */         
/* 150 */         item.onItemRightClick(this.world, dummyPlayer, EnumHand.MAIN_HAND);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sprinkleBomblet() {
/* 157 */     if (!this.world.isRemote) {
/*     */       
/* 159 */       MCH_EntityDispensedItem e = new MCH_EntityDispensedItem(this.world, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, this.rand.nextInt(360), 0.0F, this.acceleration);
/*     */       
/* 161 */       e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
/* 162 */       e.setName(getName());
/* 163 */       float RANDOM = (getInfo()).bombletDiff;
/* 164 */       this.motionX += ((this.rand.nextFloat() - 0.5F) * RANDOM);
/* 165 */       e.motionY = this.motionY / 2.0D + ((this.rand.nextFloat() - 0.5F) * RANDOM / 2.0F);
/* 166 */       this.motionZ += ((this.rand.nextFloat() - 0.5F) * RANDOM);
/* 167 */       e.setBomblet();
/* 168 */       this.world.spawnEntity((Entity)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_BulletModel getDefaultBulletModel() {
/* 174 */     return MCH_DefaultBulletModels.Bomb;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityDispensedItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */