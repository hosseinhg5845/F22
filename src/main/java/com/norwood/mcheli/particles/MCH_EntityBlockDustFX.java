/*    */ package com.norwood.mcheli.particles;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.particle.IParticleFactory;
/*    */ import net.minecraft.client.particle.Particle;
/*    */ import net.minecraft.client.particle.ParticleBlockDust;
/*    */ import net.minecraft.util.EnumBlockRenderType;
/*    */ import net.minecraft.world.World;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_EntityBlockDustFX
/*    */   extends ParticleBlockDust {
/*    */   protected MCH_EntityBlockDustFX(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, IBlockState state) {
/* 16 */     super(world, x, y, z, xSpeed, ySpeed, zSpeed, state);
/*    */   }
/*    */   
/*    */   public void setScale(float s) {
/* 20 */     this.particleScale = s;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     @Nullable
/*    */     public Particle createParticle(int particleID, @NotNull World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 36 */       IBlockState iblockstate = Block.getStateById(p_178902_15_[0]);
/* 37 */       return (iblockstate.getRenderType() == EnumBlockRenderType.INVISIBLE) ? 
/* 38 */         null : 
/* 39 */         (Particle)(new MCH_EntityBlockDustFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, iblockstate)).init();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\particles\MCH_EntityBlockDustFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */