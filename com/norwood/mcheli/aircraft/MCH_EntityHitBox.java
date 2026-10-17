/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_Entity;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_EntityHitBox extends W_Entity {
/*    */   public MCH_EntityAircraft parent;
/*    */   public int debugId;
/*    */   
/*    */   public MCH_EntityHitBox(World world) {
/* 20 */     super(world);
/* 21 */     func_70105_a(1.0F, 1.0F);
/* 22 */     this.field_70159_w = 0.0D;
/* 23 */     this.field_70181_x = 0.0D;
/* 24 */     this.field_70179_y = 0.0D;
/* 25 */     this.parent = null;
/* 26 */     this.field_70158_ak = true;
/* 27 */     this.field_70178_ae = true;
/*    */   }
/*    */   
/*    */   public MCH_EntityHitBox(World world, MCH_EntityAircraft ac, float w, float h) {
/* 31 */     this(world);
/* 32 */     func_70107_b(ac.field_70165_t, ac.field_70163_u + 1.0D, ac.field_70161_v);
/* 33 */     this.field_70169_q = ac.field_70165_t;
/* 34 */     this.field_70167_r = ac.field_70163_u + 1.0D;
/* 35 */     this.field_70166_s = ac.field_70161_v;
/* 36 */     this.parent = ac;
/* 37 */     func_70105_a(w, h);
/*    */   }
/*    */   
/*    */   protected boolean func_70041_e_() {
/* 41 */     return false;
/*    */   }
/*    */   
/*    */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/* 45 */     return par1Entity.func_174813_aQ();
/*    */   }
/*    */   
/*    */   public AxisAlignedBB func_70046_E() {
/* 49 */     return func_174813_aQ();
/*    */   }
/*    */   
/*    */   public double func_70042_X() {
/* 53 */     return -0.3D;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/* 58 */     return (this.parent != null && this.parent.func_70097_a(par1DamageSource, par2));
/*    */   }
/*    */   
/*    */   public boolean func_70067_L() {
/* 62 */     return !this.field_70128_L;
/*    */   }
/*    */   
/*    */   public void func_70106_y() {
/* 66 */     super.func_70106_y();
/*    */   }
/*    */   
/*    */   public void func_70071_h_() {
/* 70 */     super.func_70071_h_();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_70014_b(@NotNull NBTTagCompound par1NBTTagCompound) {}
/*    */ 
/*    */   
/*    */   protected void func_70037_a(@NotNull NBTTagCompound par1NBTTagCompound) {}
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public float getShadowSize() {
/* 81 */     return 0.0F;
/*    */   }
/*    */   
/*    */   public boolean func_184230_a(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 85 */     return (this.parent != null && this.parent.func_184230_a(player, hand));
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_EntityHitBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */