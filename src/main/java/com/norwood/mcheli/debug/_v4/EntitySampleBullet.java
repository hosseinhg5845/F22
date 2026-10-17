/*    */ package com.norwood.mcheli.debug._v4;
/*    */ 
/*    */ import com.norwood.mcheli.weapon.MCH_EntityRocket;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntitySampleBullet extends MCH_EntityRocket {
/*    */   private final Vec3d firstPos;
/*    */   
/*    */   public EntitySampleBullet(World par1World) {
/* 12 */     super(par1World, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, 0.0D, 0.0F, 0.0F, 4.0D);
/* 13 */     setName("hydra70");
/* 14 */     func_70105_a(1.0F, 1.0F);
/* 15 */     this.explosionPower = 3;
/* 16 */     setPower(22);
/* 17 */     this.firstPos = Vec3d.field_186680_a;
/* 18 */     this.accelerationFactor = 1.0D;
/* 19 */     this.delayFuse = 100;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_70037_a(NBTTagCompound par1nbtTagCompound) {
/* 24 */     this.field_70159_w = 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkValid() {
/* 29 */     double x = this.field_70165_t - this.firstPos.field_72450_a;
/* 30 */     double z = this.field_70161_v - this.firstPos.field_72449_c;
/* 31 */     return (x * x + z * z < 3.38724E7D && this.field_70163_u > -10.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v4\EntitySampleBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */