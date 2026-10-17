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
/*     */   public void func_70071_h_() {
/*  32 */     super.func_70071_h_();
/*  33 */     if (getInfo() != null && !(getInfo()).disableSmoke) {
/*  34 */       spawnParticle((getInfo()).trajectoryParticleName, 3, 7.0F * (getInfo()).smokeSize);
/*     */     }
/*     */     
/*  37 */     if (!this.field_70170_p.field_72995_K && getInfo() != null) {
/*  38 */       if (this.acceleration < 1.0E-4D) {
/*  39 */         this.field_70159_w *= 0.999D;
/*  40 */         this.field_70179_y *= 0.999D;
/*     */       } 
/*     */       
/*  43 */       if (func_70090_H()) {
/*  44 */         this.field_70159_w *= (getInfo()).velocityInWater;
/*  45 */         this.field_70181_x *= (getInfo()).velocityInWater;
/*  46 */         this.field_70179_y *= (getInfo()).velocityInWater;
/*     */       } 
/*     */     } 
/*     */     
/*  50 */     onUpdateBomblet();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onImpact(RayTraceResult m, float damageFactor) {
/*  55 */     if (!this.field_70170_p.field_72995_K) {
/*  56 */       func_174826_a(func_174813_aQ().func_72317_d(0.0D, 2000.0D, 0.0D));
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
/*  73 */       if (player != null && !player.field_70128_L && item != null) {
/*  74 */         MCH_DummyEntityPlayer mCH_DummyEntityPlayer = new MCH_DummyEntityPlayer(this.field_70170_p, player);
/*  75 */         ((EntityPlayer)mCH_DummyEntityPlayer).field_70125_A = 90.0F;
/*  76 */         int RNG = (getInfo()).dispenseRange - 1;
/*     */         
/*  78 */         for (int x = -RNG; x <= RNG; x++) {
/*  79 */           for (int y = -RNG; y <= RNG; y++) {
/*  80 */             if (y >= 0 && y < 256) {
/*  81 */               for (int z = -RNG; z <= RNG; z++) {
/*  82 */                 int dist = x * x + y * y + z * z;
/*  83 */                 if (dist <= RNG * RNG) {
/*  84 */                   if (dist <= 0.5D * RNG * RNG) {
/*  85 */                     useItemToBlock(m.func_178782_a().func_177982_a(x, y, z), item, itemDamage, (EntityPlayer)mCH_DummyEntityPlayer);
/*  86 */                   } else if (this.field_70146_Z.nextInt(2) == 0) {
/*  87 */                     useItemToBlock(m.func_178782_a().func_177982_a(x, y, z), item, itemDamage, (EntityPlayer)mCH_DummyEntityPlayer);
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  96 */       func_70106_y();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void useItemToBlock(BlockPos blockpos, Item item, int itemDamage, EntityPlayer dummyPlayer) {
/* 101 */     dummyPlayer.field_70165_t = blockpos.func_177958_n() + 0.5D;
/* 102 */     dummyPlayer.field_70163_u = blockpos.func_177956_o() + 2.5D;
/* 103 */     dummyPlayer.field_70161_v = blockpos.func_177952_p() + 0.5D;
/* 104 */     dummyPlayer.field_70177_z = this.field_70146_Z.nextInt(360);
/* 105 */     IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
/* 106 */     Block block = iblockstate.func_177230_c();
/* 107 */     Material blockMat = iblockstate.func_185904_a();
/* 108 */     if (block != W_Blocks.field_150350_a && blockMat != Material.field_151579_a) {
/* 109 */       if (item == W_Item.getItemByName("water_bucket")) {
/* 110 */         if (MCH_Config.Collision_DestroyBlock.prmBool) {
/* 111 */           if (blockMat == Material.field_151581_o) {
/* 112 */             this.field_70170_p.func_175698_g(blockpos);
/* 113 */           } else if (blockMat == Material.field_151587_i) {
/* 114 */             int metadata = block.func_176201_c(iblockstate);
/* 115 */             if (metadata == 0) {
/* 116 */               this.field_70170_p
/* 117 */                 .func_175656_a(blockpos, 
/* 118 */                   ForgeEventFactory.fireFluidPlaceBlockEvent(this.field_70170_p, blockpos, blockpos, W_Blocks.field_150343_Z.func_176223_P()));
/*     */             }
/* 120 */             else if (metadata <= 4) {
/* 121 */               this.field_70170_p
/* 122 */                 .func_175656_a(blockpos, 
/* 123 */                   ForgeEventFactory.fireFluidPlaceBlockEvent(this.field_70170_p, blockpos, blockpos, W_Blocks.field_150347_e.func_176223_P()));
/*     */             }
/*     */           
/*     */           } 
/*     */         }
/* 128 */       } else if (item.onItemUseFirst(dummyPlayer, this.field_70170_p, blockpos, EnumFacing.UP, blockpos
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 133 */           .func_177958_n(), blockpos
/* 134 */           .func_177956_o(), blockpos
/* 135 */           .func_177952_p(), EnumHand.MAIN_HAND) == EnumActionResult.PASS && item
/*     */ 
/*     */ 
/*     */         
/* 139 */         .func_180614_a(dummyPlayer, this.field_70170_p, blockpos, EnumHand.MAIN_HAND, EnumFacing.UP, blockpos
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 145 */           .func_177958_n(), blockpos
/* 146 */           .func_177956_o(), blockpos
/* 147 */           .func_177952_p()) == EnumActionResult.PASS) {
/*     */ 
/*     */         
/* 150 */         item.func_77659_a(this.field_70170_p, dummyPlayer, EnumHand.MAIN_HAND);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sprinkleBomblet() {
/* 157 */     if (!this.field_70170_p.field_72995_K) {
/*     */       
/* 159 */       MCH_EntityDispensedItem e = new MCH_EntityDispensedItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70146_Z.nextInt(360), 0.0F, this.acceleration);
/*     */       
/* 161 */       e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
/* 162 */       e.setName(func_70005_c_());
/* 163 */       float RANDOM = (getInfo()).bombletDiff;
/* 164 */       this.field_70159_w += ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
/* 165 */       e.field_70181_x = this.field_70181_x / 2.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM / 2.0F);
/* 166 */       this.field_70179_y += ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
/* 167 */       e.setBomblet();
/* 168 */       this.field_70170_p.func_72838_d((Entity)e);
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