/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import com.norwood.mcheli.helper.MCH_SoundEvents;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class W_WorldFunc
/*    */ {
/*    */   public static void DEF_playSoundEffect(World w, double x, double y, double z, String name, float volume, float pitch) {
/* 17 */     MCH_SoundEvents.playSound(w, x, y, z, name, volume, pitch);
/*    */   }
/*    */   
/*    */   public static void MOD_playSoundEffect(World w, double x, double y, double z, String name, float volume, float pitch) {
/* 21 */     DEF_playSoundEffect(w, x, y, z, W_MOD.DOMAIN + ":" + name, volume, pitch);
/*    */   }
/*    */   
/*    */   private static void playSoundAtEntity(Entity e, String name, float volume, float pitch) {
/* 25 */     e.playSound(MCH_SoundEvents.getSound(name), volume, pitch);
/*    */   }
/*    */   
/*    */   public static void MOD_playSoundAtEntity(Entity e, String name, float volume, float pitch) {
/* 29 */     playSoundAtEntity(e, W_MOD.DOMAIN + ":" + name, volume, pitch);
/*    */   }
/*    */   
/*    */   public static int getBlockId(World w, int x, int y, int z) {
/* 33 */     return Block.getIdFromBlock(getBlock(w, x, y, z));
/*    */   }
/*    */   
/*    */   public static Block getBlock(World w, int x, int y, int z) {
/* 37 */     return getBlock(w, new BlockPos(x, y, z));
/*    */   }
/*    */   
/*    */   public static Block getBlock(World w, BlockPos blockpos) {
/* 41 */     return w.getBlockState(blockpos).getBlock();
/*    */   }
/*    */   
/*    */   public static Material getBlockMaterial(World w, int x, int y, int z) {
/* 45 */     return w.getBlockState(new BlockPos(x, y, z)).getMaterial();
/*    */   }
/*    */   
/*    */   public static boolean isBlockWater(World w, int x, int y, int z) {
/* 49 */     return isEqualBlock(w, x, y, z, W_Block.getWater());
/*    */   }
/*    */   
/*    */   public static boolean isEqualBlock(World w, int x, int y, int z, Block block) {
/* 53 */     return Block.isEqualTo(getBlock(w, x, y, z), block);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static RayTraceResult clip(World w, Vec3d par1Vec3, Vec3d par2Vec3) {
/* 58 */     return w.rayTraceBlocks(par1Vec3, par2Vec3);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static RayTraceResult clip(World w, Vec3d par1Vec3, Vec3d par2Vec3, boolean b) {
/* 63 */     return w.rayTraceBlocks(par1Vec3, par2Vec3, b);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static RayTraceResult clip(World w, Vec3d par1Vec3, Vec3d par2Vec3, boolean b1, boolean b2, boolean b3) {
/* 68 */     return w.rayTraceBlocks(par1Vec3, par2Vec3, b1, b2, b3);
/*    */   }
/*    */   
/*    */   public static boolean setBlock(World w, int a, int b, int c, Block d) {
/* 72 */     return setBlock(w, new BlockPos(a, b, c), d);
/*    */   }
/*    */   
/*    */   public static boolean setBlock(World w, BlockPos blockpos, Block d) {
/* 76 */     return w.setBlockState(blockpos, d.getDefaultState());
/*    */   }
/*    */   
/*    */   public static void setBlock(World w, int x, int y, int z, IBlockState blockstate, int j) {
/* 80 */     w.setBlockState(new BlockPos(x, y, x), blockstate, j);
/*    */   }
/*    */   
/*    */   public static boolean destroyBlock(World w, int x, int y, int z, boolean par4) {
/* 84 */     return destroyBlock(w, new BlockPos(x, y, z), par4);
/*    */   }
/*    */   
/*    */   public static boolean destroyBlock(World w, BlockPos blockpos, boolean dropBlock) {
/* 88 */     return w.destroyBlock(blockpos, dropBlock);
/*    */   }
/*    */   
/*    */   public static Vec3d getWorldVec3(World w, double x, double y, double z) {
/* 92 */     return new Vec3d(x, y, z);
/*    */   }
/*    */   
/*    */   public static Vec3d getWorldVec3EntityPos(Entity e) {
/* 96 */     return getWorldVec3(e.world, e.posX, e.posY, e.posZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_WorldFunc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */