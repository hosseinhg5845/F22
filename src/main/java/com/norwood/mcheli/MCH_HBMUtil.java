/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_HBMUtil
/*     */ {
/*     */   private static Class<?> nukeExplosionMK5Class;
/*     */   private static Class<?> nukeTorexClass;
/*     */   private static Class<?> explosionChaosClass;
/*     */   private static Class<?> explosionCreatorClass;
/*     */   private static Class<?> explosionNT;
/*     */   
/*     */   static {
/*     */     try {
/*  20 */       nukeExplosionMK5Class = Class.forName("com.hbm.entity.logic.EntityNukeExplosionMK5");
/*  21 */       nukeTorexClass = Class.forName("com.hbm.entity.effect.EntityNukeTorex");
/*  22 */       explosionChaosClass = Class.forName("com.hbm.explosion.ExplosionChaos");
/*  23 */       explosionCreatorClass = Class.forName("com.hbm.particle.helper.ExplosionCreator");
/*  24 */       explosionNT = Class.forName("com.hbm.explosion.ExplosionNT");
/*  25 */     } catch (ClassNotFoundException e) {
/*  26 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object EntityNukeExplosionMK5_statFac(World world, int r, double posX, double posY, double posZ) {
/*     */     try {
/*  32 */       if (nukeExplosionMK5Class != null) {
/*  33 */         Method statFacMethod = nukeExplosionMK5Class.getMethod("statFac", new Class[] { World.class, int.class, double.class, double.class, double.class });
/*  34 */         return statFacMethod.invoke(null, new Object[] { world, Integer.valueOf(r), Double.valueOf(posX), Double.valueOf(posY), Double.valueOf(posZ) });
/*     */       } 
/*  36 */     } catch (Exception e) {
/*  37 */       e.printStackTrace();
/*     */     } 
/*  39 */     return null;
/*     */   }
/*     */   
/*     */   public static void EntityNukeTorex_statFac(World world, double posX, double posY, double posZ, float nukeYield) {
/*     */     try {
/*  44 */       if (nukeTorexClass != null) {
/*  45 */         Method statFacMethod = nukeTorexClass.getMethod("statFac", new Class[] { World.class, double.class, double.class, double.class, float.class });
/*  46 */         statFacMethod.invoke(null, new Object[] { world, Double.valueOf(posX), Double.valueOf(posY), Double.valueOf(posZ), Float.valueOf(nukeYield) });
/*     */       } 
/*  48 */     } catch (Exception e) {
/*  49 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void ExplosionChaos_spawnChlorine(World world, double posX, double posY, double posZ, float chemYield, double chemSpeed, int chemType) {
/*     */     try {
/*  55 */       System.out.println("spawn chlorine method");
/*  56 */       if (explosionChaosClass != null) {
/*  57 */         Method spawnChlorineMethod = explosionChaosClass.getMethod("spawnChlorine", new Class[] { World.class, double.class, double.class, double.class, int.class, double.class, int.class });
/*     */         
/*  59 */         spawnChlorineMethod.invoke(null, new Object[] { world, Double.valueOf(posX), Double.valueOf(posY), Double.valueOf(posZ), Integer.valueOf((int)chemYield), Double.valueOf(chemSpeed), Integer.valueOf(chemType) });
/*  60 */         System.out.println("Chlorine effect spawned successfully");
/*     */       }
/*     */     
/*     */     }
/*  64 */     catch (Exception e) {
/*  65 */       System.out.println("Failed to spawn chlorine effect: " + e.getMessage());
/*  66 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void ExplosionCreator_composeEffectStandard(World world, double posX, double posY, double posZ, int explosionBlockSize) {
/*     */     try {
/*  72 */       if (explosionCreatorClass != null) {
/*     */         Method spawnChlorineMethod;
/*  74 */         if (explosionBlockSize < 50) {
/*  75 */           spawnChlorineMethod = explosionCreatorClass.getMethod("composeEffectSmall", new Class[] { World.class, double.class, double.class, double.class });
/*  76 */         } else if (explosionBlockSize < 100) {
/*  77 */           spawnChlorineMethod = explosionCreatorClass.getMethod("composeEffectStandard", new Class[] { World.class, double.class, double.class, double.class });
/*     */         } else {
/*     */           
/*  80 */           spawnChlorineMethod = explosionCreatorClass.getMethod("composeEffectLarge", new Class[] { World.class, double.class, double.class, double.class });
/*     */         } 
/*  82 */         spawnChlorineMethod.invoke(null, new Object[] { world, Double.valueOf(posX), Double.valueOf(posY), Double.valueOf(posZ) });
/*     */       } 
/*  84 */     } catch (Exception e) {
/*  85 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object ExplosionNT_instance_init(World world, Entity entity, double posX, double posY, double posZ, float explosionPower) {
/*     */     try {
/*  91 */       if (explosionNT != null) {
/*  92 */         Class<?>[] explosionNTParamTypes = new Class[] { World.class, Entity.class, double.class, double.class, double.class, float.class };
/*  93 */         Constructor<?> explosionNTConstructor = explosionNT.getConstructor(explosionNTParamTypes);
/*  94 */         return explosionNTConstructor.newInstance(new Object[] { world, entity, Double.valueOf(posX), Double.valueOf(posY), Double.valueOf(posZ), Float.valueOf(explosionPower) });
/*     */       } 
/*  96 */     } catch (Exception e) {
/*  97 */       e.printStackTrace();
/*     */     } 
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public static void ExplosionNT_instance_overrideResolutionAndExplode(Object explosionNTInstance, int resolution) {
/*     */     try {
/* 104 */       if (explosionNTInstance != null) {
/* 105 */         Method overrideResolutionMethod = explosionNTInstance.getClass().getMethod("overrideResolution", new Class[] { int.class });
/* 106 */         overrideResolutionMethod.invoke(explosionNTInstance, new Object[] { Integer.valueOf(resolution) });
/* 107 */         Method explodeMethod = explosionNTInstance.getClass().getMethod("explode", new Class[0]);
/* 108 */         explodeMethod.invoke(explosionNTInstance, new Object[0]);
/*     */       } 
/* 110 */     } catch (Exception e) {
/* 111 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   public static void ExplosionNT_instance_addAttrib(Object explosionNTInstance, String attrib) {
/*     */     try {
/* 116 */       if (explosionNTInstance != null) {
/* 117 */         Class<?> exAttribClass = Class.forName("com.hbm.explosion.ExplosionNT$ExAttrib");
/* 118 */         Object Attrib = Enum.valueOf(exAttribClass, attrib);
/* 119 */         Method addAttribMethod = explosionNT.getMethod("addAttrib", new Class[] { exAttribClass });
/* 120 */         addAttribMethod.invoke(explosionNTInstance, new Object[] { Attrib });
/*     */       } 
/* 122 */     } catch (Exception e) {
/* 123 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_HBMUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */