/*    */ package com.norwood.mcheli.debug._v4;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.MoverType;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_EntityTofu extends Entity {
/*    */   public MCH_EntityTofu(World worldIn) {
/* 11 */     super(worldIn);
/*    */   }
/*    */   
/*    */   public MCH_EntityTofu(World world, double x, double y, double z) {
/* 15 */     this(world);
/* 16 */     setPosition(x, y, z);
/*    */   }
/*    */   
/*    */   protected void entityInit() {
/* 20 */     setNoGravity(true);
/*    */   }
/*    */   
/*    */   public void onUpdate() {
/* 24 */     super.onUpdate();
/* 25 */     move(MoverType.SELF, 1.0D, 0.0D, 0.0D);
/* 26 */     if (!this.world.isRemote && this.ticksExisted > 100)
/* 27 */       setDead(); 
/*    */   }
/*    */   
/*    */   public void applyEntityCollision(@NotNull Entity entityIn) {}
/*    */   
/*    */   protected void readEntityFromNBT(@NotNull NBTTagCompound compound) {}
/*    */   
/*    */   protected void writeEntityToNBT(@NotNull NBTTagCompound compound) {}
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v4\MCH_EntityTofu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */