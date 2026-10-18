/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class W_Particle {
/*    */   public static BlockParticleParam getParticleTileCrackName(World w, int blockX, int blockY, int blockZ) {
/* 11 */     IBlockState iblockstate = w.getBlockState(new BlockPos(blockX, blockY, blockZ));
/* 12 */     return (iblockstate.getMaterial() != Material.AIR) ? 
/* 13 */       new BlockParticleParam("blockcrack", Block.getStateId(iblockstate)) : 
/* 14 */       BlockParticleParam.EMPTY;
/*    */   }
/*    */   
/*    */   public static BlockParticleParam getParticleTileDustName(World w, int blockX, int blockY, int blockZ) {
/* 18 */     IBlockState iblockstate = w.getBlockState(new BlockPos(blockX, blockY, blockZ));
/* 19 */     return (iblockstate.getMaterial() != Material.AIR) ? 
/* 20 */       new BlockParticleParam("blockdust", Block.getStateId(iblockstate)) : 
/* 21 */       BlockParticleParam.EMPTY;
/*    */   }
/*    */   
/*    */   public static class BlockParticleParam {
/* 25 */     public static final BlockParticleParam EMPTY = new BlockParticleParam();
/*    */     public final String name;
/*    */     public final int stateId;
/*    */     private final boolean empty;
/*    */     
/*    */     public BlockParticleParam(String name, int stateId) {
/* 31 */       this.name = name;
/* 32 */       this.stateId = stateId;
/* 33 */       this.empty = false;
/*    */     }
/*    */     
/*    */     private BlockParticleParam() {
/* 37 */       this.name = "";
/* 38 */       this.stateId = 0;
/* 39 */       this.empty = true;
/*    */     }
/*    */     
/*    */     public boolean isEmpty() {
/* 43 */       return this.empty;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_Particle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */