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
/* 16 */     func_70107_b(x, y, z);
/*    */   }
/*    */   
/*    */   protected void func_70088_a() {
/* 20 */     func_189654_d(true);
/*    */   }
/*    */   
/*    */   public void func_70071_h_() {
/* 24 */     super.func_70071_h_();
/* 25 */     func_70091_d(MoverType.SELF, 1.0D, 0.0D, 0.0D);
/* 26 */     if (!this.field_70170_p.field_72995_K && this.field_70173_aa > 100)
/* 27 */       func_70106_y(); 
/*    */   }
/*    */   
/*    */   public void func_70108_f(@NotNull Entity entityIn) {}
/*    */   
/*    */   protected void func_70037_a(@NotNull NBTTagCompound compound) {}
/*    */   
/*    */   protected void func_70014_b(@NotNull NBTTagCompound compound) {}
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v4\MCH_EntityTofu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */