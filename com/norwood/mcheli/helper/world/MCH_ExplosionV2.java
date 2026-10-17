/*     */ package com.norwood.mcheli.helper.world;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_DamageFactor;
/*     */ import com.norwood.mcheli.MCH_Explosion;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.flare.MCH_EntityFlare;
/*     */ import com.norwood.mcheli.particles.MCH_ParticleParam;
/*     */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityBaseBullet;
/*     */ import com.norwood.mcheli.wrapper.W_AxisAlignedBB;
/*     */ import com.norwood.mcheli.wrapper.W_Block;
/*     */ import com.norwood.mcheli.wrapper.W_Blocks;
/*     */ import com.norwood.mcheli.wrapper.W_ChunkPosition;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentProtection;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class MCH_ExplosionV2 extends Explosion {
/*  40 */   private static final Random explosionRNG = new Random();
/*  41 */   public final int field_77289_h = 16;
/*     */   public final Entity field_77283_e;
/*     */   public final double field_77284_b;
/*     */   public final double field_77285_c;
/*     */   public final double field_77282_d;
/*     */   public final float field_77280_f;
/*     */   public final boolean field_77286_a;
/*     */   public final boolean field_82755_b;
/*     */   public final World field_77287_j;
/*     */   public boolean isDestroyBlock;
/*     */   public int countSetFireEntity;
/*     */   public boolean isPlaySound;
/*     */   public boolean isInWater;
/*     */   public final EntityPlayer explodedPlayer;
/*     */   public float explosionSizeBlock;
/*  56 */   public MCH_DamageFactor damageFactor = null;
/*     */   private final MCH_Explosion.ExplosionResult result;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public MCH_ExplosionV2(World worldIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
/*  61 */     this(worldIn, (Entity)null, (Entity)null, x, y, z, size, false, true);
/*  62 */     func_180343_e().addAll(affectedPositions);
/*  63 */     this.isPlaySound = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_ExplosionV2(World worldIn, @Nullable Entity exploderIn, @Nullable Entity player, double x, double y, double z, float size, boolean flaming, boolean damagesTerrain) {
/*  69 */     super(worldIn, exploderIn, x, y, z, size, flaming, damagesTerrain);
/*  70 */     this.field_77287_j = worldIn;
/*  71 */     this.field_77283_e = exploderIn;
/*  72 */     this.explodedPlayer = (player instanceof EntityPlayer) ? (EntityPlayer)player : null;
/*  73 */     this.field_77284_b = x;
/*  74 */     this.field_77285_c = y;
/*  75 */     this.field_77282_d = z;
/*  76 */     this.field_77280_f = size;
/*  77 */     this.field_77286_a = flaming;
/*  78 */     this.field_82755_b = damagesTerrain;
/*  79 */     this.isDestroyBlock = false;
/*  80 */     this.explosionSizeBlock = size;
/*  81 */     this.countSetFireEntity = 0;
/*  82 */     this.isPlaySound = true;
/*  83 */     this.isInWater = false;
/*  84 */     this.result = new MCH_Explosion.ExplosionResult();
/*     */   }
/*     */   
/*     */   public static void playExplosionSound(World world, double x, double y, double z) {
/*  88 */     world.func_184134_a(x, y, z, SoundEvents.field_187539_bB, SoundCategory.BLOCKS, 4.0F, (1.0F + (world.field_73012_v
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  95 */         .nextFloat() - world.field_73012_v.nextFloat()) * 0.2F) * 0.7F, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void effectMODExplosion(World world, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
/* 101 */     MCH_ExplosionV2 explosion = new MCH_ExplosionV2(world, x, y, z, size, affectedPositions);
/* 102 */     explosion.func_77278_a();
/* 103 */     explosion.doExplosionB(true, false);
/*     */   }
/*     */   
/*     */   public static void effectVanillaExplosion(World world, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
/* 107 */     MCH_ExplosionV2 explosion = new MCH_ExplosionV2(world, x, y, z, size, affectedPositions);
/* 108 */     explosion.func_77278_a();
/* 109 */     explosion.doExplosionB(true, true);
/*     */   }
/*     */   
/*     */   public static void effectExplosionInWater(World world, double x, double y, double z, float size) {
/* 113 */     if (size > 0.0F) {
/* 114 */       int range = (int)(size + 0.5D);
/* 115 */       int ex = (int)(x + 0.5D);
/* 116 */       int ey = (int)(y + 0.5D);
/* 117 */       int ez = (int)(z + 0.5D);
/*     */       
/* 119 */       for (int i1 = -range; i1 <= range; i1++) {
/* 120 */         if (ey + i1 >= 1) {
/* 121 */           for (int j1 = -range; j1 <= range; j1++) {
/* 122 */             for (int k1 = -range; k1 <= range; k1++) {
/* 123 */               int d = j1 * j1 + i1 * i1 + k1 * k1;
/* 124 */               if (d < range * range && W_Block.func_149680_a(W_WorldFunc.getBlock(world, ex + j1, ey + i1, ez + k1), W_Block.getWater())) {
/* 125 */                 int n = explosionRNG.nextInt(2);
/*     */                 
/* 127 */                 for (int i = 0; i < n; i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 137 */                   MCH_ParticleParam prm = new MCH_ParticleParam(world, "splash", (ex + j1), (ey + i1), (ez + k1), j1 / range * (explosionRNG.nextFloat() - 0.2D), 1.0D - Math.sqrt((j1 * j1 + k1 * k1)) / range + explosionRNG.nextFloat() * 0.4D * range * 0.4D, k1 / range * (explosionRNG.nextFloat() - 0.2D), (explosionRNG.nextInt(range) * 3 + range));
/*     */                   
/* 139 */                   MCH_ParticlesUtil.spawnParticle(prm);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_77278_a() {
/* 150 */     HashSet<BlockPos> hashset = new HashSet<>();
/*     */     
/* 152 */     for (int i = 0; i < 16; i++) {
/* 153 */       for (int m = 0; m < 16; m++) {
/* 154 */         for (int n = 0; n < 16; n++) {
/* 155 */           if (i == 0 || i == 15 || m == 0 || m == 15 || n == 0 || n == 15) {
/* 156 */             double d3 = (i / 15.0F * 2.0F - 1.0F);
/* 157 */             double d4 = (m / 15.0F * 2.0F - 1.0F);
/* 158 */             double d5 = (n / 15.0F * 2.0F - 1.0F);
/* 159 */             double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/* 160 */             d3 /= d6;
/* 161 */             d4 /= d6;
/* 162 */             d5 /= d6;
/* 163 */             float f1 = this.explosionSizeBlock * (0.7F + this.field_77287_j.field_73012_v.nextFloat() * 0.6F);
/* 164 */             double d0 = this.field_77284_b;
/* 165 */             double d1 = this.field_77285_c;
/*     */             
/* 167 */             for (double d2 = this.field_77282_d; f1 > 0.0F; f1 -= 0.22500001F) {
/* 168 */               int l = MathHelper.func_76128_c(d0);
/* 169 */               int i1 = MathHelper.func_76128_c(d1);
/* 170 */               int j1 = MathHelper.func_76128_c(d2);
/* 171 */               int k1 = W_WorldFunc.getBlockId(this.field_77287_j, l, i1, j1);
/* 172 */               BlockPos blockpos = new BlockPos(l, i1, j1);
/* 173 */               IBlockState iblockstate = this.field_77287_j.func_180495_p(blockpos);
/* 174 */               Block block = iblockstate.func_177230_c();
/* 175 */               if (k1 > 0) {
/*     */                 float f3;
/* 177 */                 if (this.field_77283_e != null) {
/* 178 */                   f3 = W_Entity.getBlockExplosionResistance(this.field_77283_e, this, this.field_77287_j, l, i1, j1, block);
/*     */                 } else {
/* 180 */                   f3 = block.getExplosionResistance(this.field_77287_j, blockpos, this.field_77283_e, this);
/*     */                 } 
/*     */                 
/* 183 */                 if (this.isInWater) {
/* 184 */                   f3 *= this.field_77287_j.field_73012_v.nextFloat() * 0.2F + 0.2F;
/*     */                 }
/*     */                 
/* 187 */                 f1 -= (f3 + 0.3F) * 0.3F;
/*     */               } 
/*     */               
/* 190 */               if (f1 > 0.0F && (this.field_77283_e == null || W_Entity.shouldExplodeBlock(this.field_77283_e, this, this.field_77287_j, l, i1, j1, k1, f1))) {
/* 191 */                 hashset.add(blockpos);
/*     */               }
/*     */               
/* 194 */               d0 += d3 * 0.30000001192092896D;
/* 195 */               d1 += d4 * 0.30000001192092896D;
/* 196 */               d2 += d5 * 0.30000001192092896D;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     float f = this.field_77280_f * 2.0F;
/* 204 */     func_180343_e().addAll(hashset);
/* 205 */     int k = MathHelper.func_76128_c(this.field_77284_b - f - 1.0D);
/* 206 */     int j = MathHelper.func_76128_c(this.field_77284_b + f + 1.0D);
/* 207 */     int kx = MathHelper.func_76128_c(this.field_77285_c - f - 1.0D);
/* 208 */     int l1 = MathHelper.func_76128_c(this.field_77285_c + f + 1.0D);
/* 209 */     int i2 = MathHelper.func_76128_c(this.field_77282_d - f - 1.0D);
/* 210 */     int j2 = MathHelper.func_76128_c(this.field_77282_d + f + 1.0D);
/* 211 */     List<Entity> list = this.field_77287_j.func_72839_b(this.field_77283_e, W_AxisAlignedBB.getAABB(k, kx, i2, j, l1, j2));
/* 212 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(this.field_77287_j, this.field_77284_b, this.field_77285_c, this.field_77282_d);
/*     */     
/* 214 */     for (Entity entity : list) {
/* 215 */       double d7 = entity.func_70011_f(this.field_77284_b, this.field_77285_c, this.field_77282_d) / f;
/* 216 */       if (d7 <= 1.0D) {
/* 217 */         double d0 = entity.field_70165_t - this.field_77284_b;
/* 218 */         double d1 = entity.field_70163_u + entity.func_70047_e() - this.field_77285_c;
/* 219 */         double d2 = entity.field_70161_v - this.field_77282_d;
/* 220 */         double d8 = MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
/* 221 */         if (d8 != 0.0D) {
/* 222 */           d0 /= d8;
/* 223 */           d1 /= d8;
/* 224 */           d2 /= d8;
/* 225 */           double d9 = getBlockDensity(vec3, entity.func_174813_aQ());
/* 226 */           double d10 = (1.0D - d7) * d9;
/* 227 */           float damage = (int)((d10 * d10 + d10) / 2.0D * 8.0D * f + 1.0D);
/* 228 */           if (damage > 0.0F && !(entity instanceof net.minecraft.entity.item.EntityItem) && !(entity instanceof net.minecraft.entity.item.EntityExpBottle) && !(entity instanceof net.minecraft.entity.item.EntityXPOrb) && 
/*     */ 
/*     */ 
/*     */             
/* 232 */             !W_Entity.isEntityFallingBlock(entity)) {
/* 233 */             if (!(entity instanceof MCH_EntityBaseBullet) || !(this.explodedPlayer instanceof EntityPlayer)) {
/* 234 */               MCH_Lib.DbgLog(this.field_77287_j, "MCH_Explosion.doExplosionA:Damage=%.1f:HitEntity=" + entity.getClass(), new Object[] { Float.valueOf(damage) });
/* 235 */               this.result.hitEntity = true;
/* 236 */             } else if (!W_Entity.isEqual(((MCH_EntityBaseBullet)entity).shootingEntity, (Entity)this.explodedPlayer)) {
/* 237 */               this.result.hitEntity = true;
/* 238 */               MCH_Lib.DbgLog(this.field_77287_j, "MCH_Explosion.doExplosionA:Damage=%.1f:HitEntityBullet=" + entity.getClass(), new Object[] { Float.valueOf(damage) });
/*     */             } 
/*     */           }
/*     */           
/* 242 */           MCH_Lib.applyEntityHurtResistantTimeConfig(entity);
/* 243 */           DamageSource ds = DamageSource.func_94539_a(this);
/* 244 */           damage = MCH_Config.applyDamageVsEntity(entity, ds, damage);
/* 245 */           damage *= (this.damageFactor != null) ? this.damageFactor.getDamageFactor(entity) : 1.0F;
/* 246 */           W_Entity.attackEntityFrom(entity, ds, damage);
/* 247 */           double d11 = d10;
/* 248 */           if (entity instanceof EntityLivingBase) {
/* 249 */             d11 = EnchantmentProtection.func_92092_a((EntityLivingBase)entity, d10);
/*     */           }
/*     */           
/* 252 */           if (!(entity instanceof MCH_EntityBaseBullet)) {
/* 253 */             entity.field_70159_w += d0 * d11 * 0.4D;
/* 254 */             entity.field_70181_x += d1 * d11 * 0.1D;
/* 255 */             entity.field_70179_y += d2 * d11 * 0.4D;
/*     */           } 
/*     */           
/* 258 */           if (entity instanceof EntityPlayer) {
/* 259 */             func_77277_b().put((EntityPlayer)entity, W_WorldFunc.getWorldVec3(this.field_77287_j, d0 * d10, d1 * d10, d2 * d10));
/*     */           }
/*     */           
/* 262 */           if (damage > 0.0F && this.countSetFireEntity > 0) {
/* 263 */             double fireFactor = 1.0D - d8 / f;
/* 264 */             if (fireFactor > 0.0D) {
/* 265 */               entity.func_70015_d((int)(fireFactor * this.countSetFireEntity));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private double getBlockDensity(Vec3d vec3, AxisAlignedBB bb) {
/* 274 */     double d0 = 1.0D / ((bb.field_72336_d - bb.field_72340_a) * 2.0D + 1.0D);
/* 275 */     double d1 = 1.0D / ((bb.field_72337_e - bb.field_72338_b) * 2.0D + 1.0D);
/* 276 */     double d2 = 1.0D / ((bb.field_72334_f - bb.field_72339_c) * 2.0D + 1.0D);
/* 277 */     if (d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D) {
/* 278 */       int i = 0;
/* 279 */       int j = 0;
/*     */       float f;
/* 281 */       for (f = 0.0F; f <= 1.0F; f = (float)(f + d0)) {
/* 282 */         float f1; for (f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
/* 283 */           float f2; for (f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
/* 284 */             double d3 = bb.field_72340_a + (bb.field_72336_d - bb.field_72340_a) * f;
/* 285 */             double d4 = bb.field_72338_b + (bb.field_72337_e - bb.field_72338_b) * f1;
/* 286 */             double d5 = bb.field_72339_c + (bb.field_72334_f - bb.field_72339_c) * f2;
/* 287 */             if (this.field_77287_j.func_147447_a(new Vec3d(d3, d4, d5), vec3, false, true, false) == null) {
/* 288 */               i++;
/*     */             }
/*     */             
/* 291 */             j++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 296 */       return i / j;
/*     */     } 
/* 298 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77279_a(boolean spawnParticles) {
/* 303 */     doExplosionB(spawnParticles, false);
/*     */   }
/*     */   
/*     */   private void doExplosionB(boolean spawnParticles, boolean vanillaMode) {
/* 307 */     if (this.isPlaySound) {
/* 308 */       this.field_77287_j
/* 309 */         .func_184148_a(null, this.field_77284_b, this.field_77285_c, this.field_77282_d, SoundEvents.field_187539_bB, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.field_77287_j.field_73012_v
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 317 */           .nextFloat() - this.field_77287_j.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
/*     */     }
/*     */ 
/*     */     
/* 321 */     if (this.field_82755_b) {
/* 322 */       Iterator<BlockPos> iterator = func_180343_e().iterator();
/* 323 */       int cnt = 0;
/* 324 */       int flareCnt = (int)this.field_77280_f;
/*     */       
/* 326 */       while (iterator.hasNext()) {
/* 327 */         BlockPos chunkposition = iterator.next();
/* 328 */         int i = W_ChunkPosition.getChunkPosX(chunkposition);
/* 329 */         int j = W_ChunkPosition.getChunkPosY(chunkposition);
/* 330 */         int k = W_ChunkPosition.getChunkPosZ(chunkposition);
/* 331 */         int l = W_WorldFunc.getBlockId(this.field_77287_j, i, j, k);
/* 332 */         cnt++;
/* 333 */         if (spawnParticles) {
/* 334 */           if (vanillaMode) {
/* 335 */             spawnVanillaExlosionEffect(i, j, k);
/* 336 */           } else if (spawnExlosionEffect(cnt, i, j, k, (flareCnt > 0))) {
/* 337 */             flareCnt--;
/*     */           } 
/*     */         }
/*     */         
/* 341 */         if (l > 0 && this.isDestroyBlock && this.explosionSizeBlock > 0.0F && MCH_Config.Explosion_DestroyBlock.prmBool) {
/* 342 */           Block block = W_Block.getBlockById(l);
/* 343 */           if (block.func_149659_a(this)) {
/* 344 */             block.func_180653_a(this.field_77287_j, chunkposition, this.field_77287_j.func_180495_p(chunkposition), 1.0F / this.explosionSizeBlock, 0);
/*     */           }
/*     */           
/* 347 */           block.onBlockExploded(this.field_77287_j, chunkposition, this);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 352 */     if (this.field_77286_a && MCH_Config.Explosion_FlamingBlock.prmBool) {
/* 353 */       for (BlockPos chunkpositionx : func_180343_e()) {
/* 354 */         int ix = W_ChunkPosition.getChunkPosX(chunkpositionx);
/* 355 */         int jx = W_ChunkPosition.getChunkPosY(chunkpositionx);
/* 356 */         int kx = W_ChunkPosition.getChunkPosZ(chunkpositionx);
/* 357 */         int lx = W_WorldFunc.getBlockId(this.field_77287_j, ix, jx, kx);
/* 358 */         IBlockState iblockstate = this.field_77287_j.func_180495_p(chunkpositionx.func_177977_b());
/* 359 */         Block b = iblockstate.func_177230_c();
/* 360 */         if (lx == 0 && iblockstate.func_185914_p() && explosionRNG.nextInt(3) == 0) {
/* 361 */           W_WorldFunc.setBlock(this.field_77287_j, ix, jx, kx, (Block)W_Blocks.field_150480_ab);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean spawnExlosionEffect(int cnt, int i, int j, int k, boolean spawnFlare) {
/* 368 */     boolean spawnedFlare = false;
/* 369 */     double d0 = (i + this.field_77287_j.field_73012_v.nextFloat());
/* 370 */     double d1 = (j + this.field_77287_j.field_73012_v.nextFloat());
/* 371 */     double d2 = (k + this.field_77287_j.field_73012_v.nextFloat());
/* 372 */     double mx = d0 - this.field_77284_b;
/* 373 */     double my = d1 - this.field_77285_c;
/* 374 */     double mz = d2 - this.field_77282_d;
/* 375 */     double d6 = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
/* 376 */     mx /= d6;
/* 377 */     my /= d6;
/* 378 */     mz /= d6;
/* 379 */     double d7 = 0.5D / (d6 / this.field_77280_f + 0.1D);
/* 380 */     d7 *= (this.field_77287_j.field_73012_v.nextFloat() * this.field_77287_j.field_73012_v.nextFloat() + 0.3F);
/* 381 */     mx *= d7 * 0.5D;
/* 382 */     my *= d7 * 0.5D;
/* 383 */     mz *= d7 * 0.5D;
/* 384 */     double px = (d0 + this.field_77284_b) / 2.0D;
/* 385 */     double py = (d1 + this.field_77285_c) / 2.0D;
/* 386 */     double pz = (d2 + this.field_77282_d) / 2.0D;
/* 387 */     double r = Math.PI * this.field_77287_j.field_73012_v.nextInt(360) / 180.0D;
/* 388 */     if (this.field_77280_f >= 4.0F && spawnFlare) {
/* 389 */       double a = Math.min((this.field_77280_f / 12.0F), 0.6D) * (0.5F + this.field_77287_j.field_73012_v.nextFloat() * 0.5F);
/* 390 */       this.field_77287_j.func_72838_d((Entity)new MCH_EntityFlare(this.field_77287_j, px, py + 2.0D, pz, Math.sin(r) * a, (1.0D + my / 5.0D) * a, Math.cos(r) * a, 2.0F, 0));
/* 391 */       spawnedFlare = true;
/*     */     } 
/*     */     
/* 394 */     if (cnt % 4 == 0) {
/* 395 */       float bdf = Math.min(this.field_77280_f / 3.0F, 2.0F) * (0.5F + this.field_77287_j.field_73012_v.nextFloat() * 0.5F);
/* 396 */       MCH_ParticlesUtil.spawnParticleTileDust(this.field_77287_j, (int)(px + 0.5D), (int)(py - 0.5D), (int)(pz + 0.5D), px, py + 1.0D, pz, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 404 */           Math.sin(r) * bdf, 0.5D + my / 5.0D * bdf, 
/*     */           
/* 406 */           Math.cos(r) * bdf, 
/* 407 */           Math.min(this.field_77280_f / 2.0F, 3.0F) * (0.5F + this.field_77287_j.field_73012_v.nextFloat() * 0.5F));
/*     */     } 
/*     */ 
/*     */     
/* 411 */     int es = (int)Math.max(this.field_77280_f, 4.0F);
/* 412 */     if (this.field_77280_f <= 1.0F || cnt % es == 0) {
/* 413 */       if (this.field_77287_j.field_73012_v.nextBoolean()) {
/* 414 */         my *= 3.0D;
/* 415 */         mx *= 0.1D;
/* 416 */         mz *= 0.1D;
/*     */       } else {
/* 418 */         my *= 0.2D;
/* 419 */         mx *= 3.0D;
/* 420 */         mz *= 3.0D;
/*     */       } 
/*     */ 
/*     */       
/* 424 */       MCH_ParticleParam prm = new MCH_ParticleParam(this.field_77287_j, "explode", px, py, pz, mx, my, mz, (this.field_77280_f < 8.0F) ? (this.field_77280_f * 2.0F) : 16.0F);
/*     */       
/* 426 */       prm.r = prm.g = prm.b = 0.3F + this.field_77287_j.field_73012_v.nextFloat() * 0.4F;
/* 427 */       prm.r += 0.1F;
/* 428 */       prm.g += 0.05F;
/* 429 */       prm.b += 0.0F;
/* 430 */       prm.age = 10 + this.field_77287_j.field_73012_v.nextInt(30);
/* 431 */       prm.age = (int)(prm.age * Math.min(this.field_77280_f, 6.0F));
/* 432 */       prm.age = prm.age * 2 / 3;
/* 433 */       prm.diffusible = true;
/* 434 */       MCH_ParticlesUtil.spawnParticle(prm);
/*     */     } 
/*     */     
/* 437 */     return spawnedFlare;
/*     */   }
/*     */   
/*     */   private void spawnVanillaExlosionEffect(int i, int j, int k) {
/* 441 */     double d0 = (i + this.field_77287_j.field_73012_v.nextFloat());
/* 442 */     double d1 = (j + this.field_77287_j.field_73012_v.nextFloat());
/* 443 */     double d2 = (k + this.field_77287_j.field_73012_v.nextFloat());
/* 444 */     double d3 = d0 - this.field_77284_b;
/* 445 */     double d4 = d1 - this.field_77285_c;
/* 446 */     double d5 = d2 - this.field_77282_d;
/* 447 */     double d6 = MathHelper.func_76133_a(d3 * d3 + d4 * d4 + d5 * d5);
/* 448 */     d3 /= d6;
/* 449 */     d4 /= d6;
/* 450 */     d5 /= d6;
/* 451 */     double d7 = 0.5D / (d6 / this.field_77280_f + 0.1D);
/* 452 */     d7 *= (this.field_77287_j.field_73012_v.nextFloat() * this.field_77287_j.field_73012_v.nextFloat() + 0.3F);
/* 453 */     d3 *= d7;
/* 454 */     d4 *= d7;
/* 455 */     d5 *= d7;
/* 456 */     MCH_ParticlesUtil.DEF_spawnParticle("explode", (d0 + this.field_77284_b) / 2.0D, (d1 + this.field_77285_c) / 2.0D, (d2 + this.field_77282_d) / 2.0D, d3, d4, d5, 10.0F, new int[0]);
/* 457 */     MCH_ParticlesUtil.DEF_spawnParticle("smoke", d0, d1, d2, d3, d4, d5, 10.0F, new int[0]);
/*     */   }
/*     */   
/*     */   public EntityLivingBase func_94613_c() {
/* 461 */     return (this.explodedPlayer != null) ? (EntityLivingBase)this.explodedPlayer : super.func_94613_c();
/*     */   }
/*     */   
/*     */   public MCH_Explosion.ExplosionResult getResult() {
/* 465 */     return this.result;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\world\MCH_ExplosionV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */