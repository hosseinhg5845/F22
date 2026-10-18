/*     */ package com.norwood.mcheli.wrapper;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public abstract class W_Entity
/*     */   extends Entity
/*     */ {
/*  24 */   protected double _renderDistanceWeight = 1.0D;
/*     */   
/*     */   public W_Entity(World par1World) {
/*  27 */     super(par1World);
/*     */   }
/*     */   
/*     */   public static boolean isEntityFallingBlock(Entity entity) {
/*  31 */     return entity instanceof net.minecraft.entity.item.EntityFallingBlock;
/*     */   }
/*     */   
/*     */   public static int getEntityId(@Nullable Entity entity) {
/*  35 */     return (entity != null) ? entity.getEntityId() : -1;
/*     */   }
/*     */   
/*     */   public static boolean isEqual(@Nullable Entity e1, @Nullable Entity e2) {
/*  39 */     int i1 = getEntityId(e1);
/*  40 */     int i2 = getEntityId(e2);
/*  41 */     return (i1 == i2);
/*     */   }
/*     */   
/*     */   public static boolean attackEntityFrom(Entity entity, DamageSource ds, float par2) {
/*  45 */     return entity.attackEntityFrom(ds, par2);
/*     */   }
/*     */   
/*     */   public static float getBlockExplosionResistance(Entity entity, Explosion par1Explosion, World par2World, int par3, int par4, int par5, Block par6Block) {
/*  49 */     if (par6Block != null) {
/*     */       try {
/*  51 */         return entity.getExplosionResistance(par1Explosion, par2World, new BlockPos(par3, par4, par5), par6Block.getDefaultState());
/*  52 */       } catch (Exception var8) {
/*  53 */         var8.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  57 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public static boolean shouldExplodeBlock(Entity entity, Explosion par1Explosion, World par2World, int par3, int par4, int par5, int par6, float par7) {
/*  61 */     return entity.canExplosionDestroyBlock(par1Explosion, par2World, new BlockPos(par3, par4, par5), W_Block.getBlockById(par6).getDefaultState(), par7);
/*     */   }
/*     */   
/*     */   public static PotionEffect getActivePotionEffect(Entity entity, Potion par1Potion) {
/*  65 */     return (entity instanceof EntityLivingBase) ? ((EntityLivingBase)entity).getActivePotionEffect(par1Potion) : null;
/*     */   }
/*     */   
/*     */   public static void removePotionEffectClient(Entity entity, Potion potion) {
/*  69 */     if (entity instanceof EntityLivingBase) {
/*  70 */       ((EntityLivingBase)entity).removePotionEffect(potion);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void removePotionEffect(Entity entity, Potion potion) {
/*  75 */     if (entity instanceof EntityLivingBase) {
/*  76 */       ((EntityLivingBase)entity).removePotionEffect(potion);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void addPotionEffect(Entity entity, PotionEffect pe) {
/*  81 */     if (entity instanceof EntityLivingBase) {
/*  82 */       ((EntityLivingBase)entity).addPotionEffect(pe);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */   
/*     */   public EntityItem dropItemWithOffset(@NotNull Item item, int par2, float par3) {
/*  90 */     return entityDropItem(new ItemStack(item, par2, 0), par3);
/*     */   }
/*     */   
/*     */   public String getEntityName() {
/*  94 */     return getEntityString();
/*     */   }
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
/*  98 */     return attackEntityFrom(par1DamageSource, par2);
/*     */   }
/*     */   
/*     */   public boolean attackEntityFrom(@NotNull DamageSource par1DamageSource, float par2) {
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public void addEntityCrashInfo(@NotNull CrashReportCategory par1CrashReportCategory) {
/* 106 */     super.addEntityCrashInfo(par1CrashReportCategory);
/*     */   }
/*     */   
/*     */   protected void doBlockCollisions() {
/* 110 */     super.doBlockCollisions();
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean isInRangeToRenderDist(double distance) {
/* 115 */     double d0 = getEntityBoundingBox().getAverageEdgeLength();
/* 116 */     if (Double.isNaN(d0)) {
/* 117 */       d0 = 1.0D;
/*     */     }
/*     */     
/* 120 */     d0 = d0 * 64.0D * this._renderDistanceWeight;
/* 121 */     return (distance < d0 * d0);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_Entity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */