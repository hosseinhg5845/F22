/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockContainer;
/*    */ import net.minecraft.block.material.Material;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public abstract class W_BlockContainer extends BlockContainer {
/*    */   protected W_BlockContainer(int par1, Material par2Material) {
/* 10 */     super(par2Material);
/*    */   }
/*    */   @NotNull
/*    */   public Block setLightLevel(float f) {
/* 14 */     return super.setLightLevel(f);
/*    */   }
/*    */   @NotNull
/*    */   public Block setUnlocalizedName(@NotNull String s) {
/* 18 */     return super.setUnlocalizedName(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_BlockContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */