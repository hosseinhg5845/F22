/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public abstract class W_Block extends Block {
/*    */   protected W_Block(Material materialIn) {
/*  9 */     super(materialIn);
/*    */   }
/*    */   
/*    */   public static Block getBlockFromName(@NotNull String name) {
/* 13 */     return Block.getBlockFromName(name);
/*    */   }
/*    */   
/*    */   public static Block getSnowLayer() {
/* 17 */     return W_Blocks.SNOW_LAYER;
/*    */   }
/*    */   
/*    */   public static boolean isNull(Block block) {
/* 21 */     return (block == null || block == W_Blocks.AIR);
/*    */   }
/*    */   
/*    */   public static boolean isEqual(int blockId, Block block) {
/* 25 */     return Block.isEqualTo(Block.getBlockById(blockId), block);
/*    */   }
/*    */   
/*    */   public static boolean isEqual(Block block1, Block block2) {
/* 29 */     return Block.isEqualTo(block1, block2);
/*    */   }
/*    */   
/*    */   public static Block getWater() {
/* 33 */     return (Block)W_Blocks.WATER;
/*    */   }
/*    */   @NotNull
/*    */   public static Block getBlockById(int i) {
/* 37 */     return Block.getBlockById(i);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_Block.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */