/*     */ package com.norwood.mcheli.particles;
import net.minecraft.client.particle.ParticleManager;
/*     */ 
/*     */ import com.norwood.mcheli.wrapper.W_Particle;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.IParticleFactory;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
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
/*     */ public class MCH_ParticlesUtil
/*     */ {
/*  27 */   public static MCH_EntityParticleMarkPoint markPoint = null;
/*     */   
/*     */   public static void spawnParticleExplode(World w, double x, double y, double z, float size, float r, float g, float b, float a, int age) {
/*  30 */     MCH_EntityParticleExplode epe = new MCH_EntityParticleExplode(w, x, y, z, size, age, 0.0D);
/*  31 */     epe.setParticleMaxAge(age);
/*  32 */     epe.setRBGColorF(r, g, b);
/*  33 */     epe.setAlphaF(a);
/*  34 */     (FMLClientHandler.instance().getClient()).effectRenderer.addEffect((Particle)epe);
/*     */   }
/*     */   
/*     */   public static void spawnParticleTileCrack(World w, int blockX, int blockY, int blockZ, double x, double y, double z, double mx, double my, double mz) {
/*  38 */     W_Particle.BlockParticleParam name = W_Particle.getParticleTileCrackName(w, blockX, blockY, blockZ);
/*  39 */     if (!name.isEmpty()) {
/*  40 */       DEF_spawnParticle(name.name, x, y, z, mx, my, mz, 20.0F, new int[] { name.stateId });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean spawnParticleTileDust(World w, int blockX, int blockY, int blockZ, double x, double y, double z, double mx, double my, double mz, float scale) {
/*  47 */     boolean ret = false;
/*  48 */     int[][] offset = { { 0, 0, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { 1, 0, 0 }, { -1, 0, 0 } };
/*  49 */     int len = offset.length;
/*     */     
/*  51 */     for (int[] ints : offset) {
/*  52 */       W_Particle.BlockParticleParam name = W_Particle.getParticleTileDustName(w, blockX + ints[0], blockY + ints[1], blockZ + ints[2]);
/*  53 */       if (!name.isEmpty()) {
/*  54 */         Particle e = DEF_spawnParticle(name.name, x, y, z, mx, my, mz, 20.0F, new int[] { name.stateId });
/*  55 */         if (e instanceof MCH_EntityBlockDustFX) {
/*  56 */           ((MCH_EntityBlockDustFX)e).setScale(scale * 2.0F);
/*  57 */           ret = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  63 */     return ret;
/*     */   }
/*     */   
/*     */   public static Particle DEF_spawnParticle(String s, double x, double y, double z, double mx, double my, double mz, float dist, int... params) {
/*  67 */     Particle e = doSpawnParticle(s, x, y, z, mx, my, mz, params);
/*  68 */     if (e != null);
/*     */ 
/*     */     
/*  71 */     return e;
/*     */   }
/*     */   
/*     */   public static Particle doSpawnParticle(String type, double x, double y, double z, double mx, double my, double mz, int... params) {
/*  75 */     Minecraft mc = Minecraft.getMinecraft();
/*  76 */     if (mc.getRenderViewEntity() != null && mc.effectRenderer != null) {
/*  77 */       int i = mc.gameSettings.particleSetting;
/*  78 */       if (i == 1 && mc.world.rand.nextInt(3) == 0) {
/*  79 */         i = 2;
/*     */       }
/*     */       
/*  82 */       double d6 = (mc.getRenderViewEntity()).posX - x;
/*  83 */       double d7 = (mc.getRenderViewEntity()).posY - y;
/*  84 */       double d8 = (mc.getRenderViewEntity()).posZ - z;
/*  85 */       Particle entityfx = null;
/*  86 */       if (type.equalsIgnoreCase("hugeexplosion")) {
/*  87 */         entityfx = create(net.minecraft.client.particle.ParticleExplosionHuge.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/*  88 */         mc.effectRenderer.addEffect(entityfx);
/*  89 */       } else if (type.equalsIgnoreCase("largeexplode")) {
/*  90 */         entityfx = create(net.minecraft.client.particle.ParticleExplosionLarge.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/*  91 */         mc.effectRenderer.addEffect(entityfx);
/*  92 */       } else if (type.equalsIgnoreCase("fireworksSpark")) {
/*  93 */         entityfx = create(net.minecraft.client.particle.ParticleFirework.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/*  94 */         mc.effectRenderer.addEffect(entityfx);
/*     */       } 
/*     */       
/*  97 */       if (entityfx != null) {
/*  98 */         return entityfx;
/*     */       }
/* 100 */       double d9 = 300.0D;
/* 101 */       if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9)
/* 102 */         return null; 
/* 103 */       if (i > 1) {
/* 104 */         return null;
/*     */       }
/* 106 */       if (type.equalsIgnoreCase("bubble")) {
/* 107 */         entityfx = create(net.minecraft.client.particle.ParticleBubble.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 108 */       } else if (type.equalsIgnoreCase("suspended")) {
/* 109 */         entityfx = create(net.minecraft.client.particle.ParticleSuspend.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 110 */       } else if (type.equalsIgnoreCase("depthsuspend")) {
/* 111 */         entityfx = create(net.minecraft.client.particle.ParticleSuspendedTown.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 112 */       } else if (type.equalsIgnoreCase("townaura")) {
/* 113 */         entityfx = create(net.minecraft.client.particle.ParticleSuspendedTown.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 114 */       } else if (type.equalsIgnoreCase("crit")) {
/* 115 */         entityfx = create(net.minecraft.client.particle.ParticleCrit.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 116 */       } else if (type.equalsIgnoreCase("magicCrit")) {
/* 117 */         entityfx = create(net.minecraft.client.particle.ParticleCrit.MagicFactory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 118 */       } else if (type.equalsIgnoreCase("smoke")) {
/* 119 */         entityfx = create(net.minecraft.client.particle.ParticleSmokeNormal.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 120 */       } else if (type.equalsIgnoreCase("mobSpell")) {
/* 121 */         entityfx = create(net.minecraft.client.particle.ParticleSpell.MobFactory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 122 */       } else if (type.equalsIgnoreCase("mobSpellAmbient")) {
/* 123 */         entityfx = create(net.minecraft.client.particle.ParticleSpell.AmbientMobFactory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 124 */       } else if (type.equalsIgnoreCase("spell")) {
/* 125 */         entityfx = create(net.minecraft.client.particle.ParticleSpell.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 126 */       } else if (type.equalsIgnoreCase("instantSpell")) {
/* 127 */         entityfx = create(net.minecraft.client.particle.ParticleSpell.InstantFactory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 128 */       } else if (type.equalsIgnoreCase("witchMagic")) {
/* 129 */         entityfx = create(net.minecraft.client.particle.ParticleSpell.WitchFactory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 130 */       } else if (type.equalsIgnoreCase("note")) {
/* 131 */         entityfx = create(net.minecraft.client.particle.ParticleNote.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 132 */       } else if (type.equalsIgnoreCase("portal")) {
/* 133 */         entityfx = create(net.minecraft.client.particle.ParticlePortal.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 134 */       } else if (type.equalsIgnoreCase("enchantmenttable")) {
/* 135 */         entityfx = create(net.minecraft.client.particle.ParticleEnchantmentTable.EnchantmentTable::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 136 */       } else if (type.equalsIgnoreCase("explode")) {
/* 137 */         entityfx = create(net.minecraft.client.particle.ParticleExplosion.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 138 */       } else if (type.equalsIgnoreCase("flame")) {
/* 139 */         entityfx = create(net.minecraft.client.particle.ParticleFlame.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 140 */       } else if (type.equalsIgnoreCase("lava")) {
/* 141 */         entityfx = create(net.minecraft.client.particle.ParticleLava.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 142 */       } else if (type.equalsIgnoreCase("footstep")) {
/* 143 */         entityfx = create(net.minecraft.client.particle.ParticleFootStep.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 144 */       } else if (type.equalsIgnoreCase("splash")) {
/* 145 */         entityfx = create(net.minecraft.client.particle.ParticleSplash.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 146 */       } else if (type.equalsIgnoreCase("wake")) {
/* 147 */         entityfx = create(net.minecraft.client.particle.ParticleWaterWake.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 148 */       } else if (type.equalsIgnoreCase("largesmoke")) {
/* 149 */         entityfx = create(net.minecraft.client.particle.ParticleSmokeLarge.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 150 */       } else if (type.equalsIgnoreCase("cloud")) {
/* 151 */         entityfx = create(net.minecraft.client.particle.ParticleCloud.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 152 */       } else if (type.equalsIgnoreCase("reddust")) {
/* 153 */         entityfx = create(net.minecraft.client.particle.ParticleRedstone.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 154 */       } else if (type.equalsIgnoreCase("snowballpoof")) {
/* 155 */         entityfx = create(net.minecraft.client.particle.ParticleBreaking.SnowballFactory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 156 */       } else if (type.equalsIgnoreCase("dripWater")) {
/* 157 */         entityfx = create(net.minecraft.client.particle.ParticleDrip.WaterFactory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 158 */       } else if (type.equalsIgnoreCase("dripLava")) {
/* 159 */         entityfx = create(net.minecraft.client.particle.ParticleDrip.LavaFactory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 160 */       } else if (type.equalsIgnoreCase("snowshovel")) {
/* 161 */         entityfx = create(net.minecraft.client.particle.ParticleSnowShovel.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 162 */       } else if (type.equalsIgnoreCase("slime")) {
/* 163 */         entityfx = create(net.minecraft.client.particle.ParticleBreaking.SlimeFactory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 164 */       } else if (type.equalsIgnoreCase("heart")) {
/* 165 */         entityfx = create(net.minecraft.client.particle.ParticleHeart.Factory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 166 */       } else if (type.equalsIgnoreCase("angryVillager")) {
/* 167 */         entityfx = create(net.minecraft.client.particle.ParticleHeart.AngryVillagerFactory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 168 */       } else if (type.equalsIgnoreCase("happyVillager")) {
/* 169 */         entityfx = create(net.minecraft.client.particle.ParticleSuspendedTown.HappyVillagerFactory::new, (World)mc.world, x, y, z, mx, my, mz, new int[0]);
/* 170 */       } else if (type.startsWith("iconcrack")) {
/* 171 */         entityfx = create(net.minecraft.client.particle.ParticleBreaking.Factory::new, (World)mc.world, x, y, z, mx, my, mz, params);
/* 172 */       } else if (type.startsWith("blockcrack")) {
/* 173 */         entityfx = create(net.minecraft.client.particle.ParticleDigging.Factory::new, (World)mc.world, x, y, z, mx, my, mz, params);
/* 174 */       } else if (type.startsWith("blockdust")) {
/* 175 */         entityfx = create(net.minecraft.client.particle.ParticleBlockDust.Factory::new, (World)mc.world, x, y, z, mx, my, mz, params);
/*     */       } 
/*     */       
/* 178 */       if (entityfx != null) {
/* 179 */         mc.effectRenderer.addEffect(entityfx);
/*     */       }
/*     */       
/* 182 */       return entityfx;
/*     */     } 
/*     */ 
/*     */     
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void spawnParticle(MCH_ParticleParam p) {
/* 191 */     if (p.world.isRemote) {
/*     */       MCH_EntityParticleBase entityFX;
/* 193 */       if (p.name.equalsIgnoreCase("Splash")) {
/* 194 */         entityFX = new MCH_EntityParticleSplash(p.world, p.posX, p.posY, p.posZ, p.motionX, p.motionY, p.motionZ);
/*     */       } else {
/* 196 */         entityFX = new MCH_EntityParticleSmoke(p.world, p.posX, p.posY, p.posZ, p.motionX, p.motionY, p.motionZ);
/*     */       } 
/*     */       
/* 199 */       entityFX.setRBGColorF(p.r, p.g, p.b);
/* 200 */       entityFX.setAlphaF(p.a);
/* 201 */       if (p.age > 0) {
/* 202 */         entityFX.setParticleMaxAge(p.age);
/*     */       }
/*     */       
/* 205 */       entityFX.moutionYUpAge = p.motionYUpAge;
/* 206 */       entityFX.gravity = p.gravity;
/* 207 */       entityFX.isEffectedWind = p.isEffectWind;
/* 208 */       entityFX.diffusible = p.diffusible;
/* 209 */       entityFX.toWhite = p.toWhite;
/* 210 */       if (p.diffusible) {
/* 211 */         entityFX.setParticleScale(p.size * 0.2F);
/* 212 */         entityFX.particleMaxScale = p.size * 2.0F;
/*     */       } else {
/* 214 */         entityFX.setParticleScale(p.size);
/*     */       } 
/*     */       
/* 217 */       (FMLClientHandler.instance().getClient()).effectRenderer.addEffect((Particle)entityFX);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void spawnMarkPoint(EntityPlayer player, double x, double y, double z) {
/* 222 */     clearMarkPoint();
/* 223 */     markPoint = new MCH_EntityParticleMarkPoint(player.world, x, y, z, player.getTeam());
/* 224 */     (FMLClientHandler.instance().getClient()).effectRenderer.addEffect((Particle)markPoint);
/*     */   }
/*     */   
/*     */   public static void clearMarkPoint() {
/* 228 */     if (markPoint != null) {
/* 229 */       markPoint.setExpired();
/* 230 */       markPoint = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Particle create(Supplier<IParticleFactory> factoryFunc, World world, double x, double y, double z, double mx, double my, double mz, int... param) {
/* 237 */     return ((IParticleFactory)factoryFunc.get()).createParticle(-1, world, x, y, z, mx, my, mz, param);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\particles\MCH_ParticlesUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */