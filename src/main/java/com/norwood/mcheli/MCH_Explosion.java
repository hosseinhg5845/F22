/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import com.norwood.mcheli.helper.world.MCH_ExplosionV2;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_Explosion
/*     */ {
/*     */   public static ExplosionResult newExplosion(World w, @Nullable Entity entityExploded, @Nullable Entity player, double x, double y, double z, float size, float sizeBlock, boolean playSound, boolean isSmoking, boolean isFlaming, boolean isDestroyBlock, int countSetFireEntity) {
/*  28 */     return newExplosion(w, entityExploded, player, x, y, z, size, sizeBlock, playSound, isSmoking, isFlaming, isDestroyBlock, countSetFireEntity, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ExplosionResult newExplosion(World w, @Nullable Entity entityExploded, @Nullable Entity player, double x, double y, double z, float size, float sizeBlock, boolean playSound, boolean isSmoking, boolean isFlaming, boolean isDestroyBlock, int countSetFireEntity, MCH_DamageFactor df) {
/*  47 */     if (w.isRemote) {
/*  48 */       return null;
/*     */     }
/*  50 */     MCH_ExplosionV2 exp = new MCH_ExplosionV2(w, entityExploded, player, x, y, z, size, isFlaming, w.getGameRules().getBoolean("mobGriefing"));
/*  51 */     exp.isDestroyBlock = isDestroyBlock;
/*  52 */     exp.explosionSizeBlock = sizeBlock;
/*  53 */     exp.countSetFireEntity = countSetFireEntity;
/*  54 */     exp.isPlaySound = playSound;
/*  55 */     exp.isInWater = false;
/*  56 */     exp.damageFactor = df;
/*  57 */     exp.doExplosionA();
/*  58 */     exp.doExplosionB(false);
/*  59 */     MCH_PacketEffectExplosion.ExplosionParam param = MCH_PacketEffectExplosion.create();
/*  60 */     param.exploderID = W_Entity.getEntityId(entityExploded);
/*  61 */     param.posX = x;
/*  62 */     param.posY = y;
/*  63 */     param.posZ = z;
/*  64 */     param.size = size;
/*  65 */     param.inWater = false;
/*  66 */     param.setAffectedPositions(exp.getAffectedBlockPositions());
/*  67 */     MCH_PacketEffectExplosion.send(param);
/*  68 */     return exp.getResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static ExplosionResult newExplosionInWater(World w, @Nullable Entity entityExploded, @Nullable Entity player, double x, double y, double z, float size, float sizeBlock, boolean playSound, boolean isSmoking, boolean isFlaming, boolean isDestroyBlock, int countSetFireEntity, MCH_DamageFactor df) {
/*  89 */     if (w.isRemote) {
/*  90 */       return null;
/*     */     }
/*  92 */     MCH_ExplosionV2 exp = new MCH_ExplosionV2(w, entityExploded, player, x, y, z, size, isFlaming, w.getGameRules().getBoolean("mobGriefing"));
/*  93 */     exp.isDestroyBlock = isDestroyBlock;
/*  94 */     exp.explosionSizeBlock = sizeBlock;
/*  95 */     exp.countSetFireEntity = countSetFireEntity;
/*  96 */     exp.isPlaySound = playSound;
/*  97 */     exp.isInWater = true;
/*  98 */     exp.damageFactor = df;
/*  99 */     exp.doExplosionA();
/* 100 */     exp.doExplosionB(false);
/* 101 */     MCH_PacketEffectExplosion.ExplosionParam param = MCH_PacketEffectExplosion.create();
/* 102 */     param.exploderID = W_Entity.getEntityId(entityExploded);
/* 103 */     param.posX = x;
/* 104 */     param.posY = y;
/* 105 */     param.posZ = z;
/* 106 */     param.size = size;
/* 107 */     param.inWater = true;
/* 108 */     param.setAffectedPositions(exp.getAffectedBlockPositions());
/* 109 */     MCH_PacketEffectExplosion.send(param);
/* 110 */     return exp.getResult();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void playExplosionSound(World w, double x, double y, double z) {
/* 115 */     MCH_ExplosionV2.playExplosionSound(w, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void effectExplosion(World world, Entity exploder, double explosionX, double explosionY, double explosionZ, float explosionSize, boolean isSmoking, List<BlockPos> affectedPositions) {
/* 128 */     MCH_ExplosionV2.effectMODExplosion(world, explosionX, explosionY, explosionZ, explosionSize, affectedPositions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void DEF_effectExplosion(World world, Entity exploder, double explosionX, double explosionY, double explosionZ, float explosionSize, boolean isSmoking, List<BlockPos> affectedPositions) {
/* 141 */     MCH_ExplosionV2.effectVanillaExplosion(world, explosionX, explosionY, explosionZ, explosionSize, affectedPositions);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void effectExplosionInWater(World world, Entity exploder, double explosionX, double explosionY, double explosionZ, float explosionSize, boolean isSmoking) {
/* 147 */     MCH_ExplosionV2.effectExplosionInWater(world, explosionX, explosionY, explosionZ, explosionSize);
/*     */   }
/*     */   
/*     */   public static class ExplosionResult {
/*     */     public boolean hitEntity = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_Explosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */