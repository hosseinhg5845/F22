/*    */ package com.norwood.mcheli.debug._v4;
/*    */ 
/*    */ import com.norwood.mcheli.weapon.MCH_EntityBullet;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityBulletTest extends MCH_EntityBullet {
/*    */   private final Vec3d firstPos;
/*    */   
/*    */   public EntityBulletTest(World par1World) {
/* 12 */     super(par1World);
/* 13 */     setName("m230");
/* 14 */     setSize(1.0F, 1.0F);
/* 15 */     this.explosionPower = 3;
/* 16 */     setPower(22);
/* 17 */     this.firstPos = Vec3d.ZERO;
/* 18 */     this.acceleration = 4.0D;
/* 19 */     this.explosionPower = 1;
/* 20 */     this.delayFuse = 100;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
/* 25 */     this.motionX = 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkValid() {
/* 30 */     double x = this.posX - this.firstPos.x;
/* 31 */     double z = this.posZ - this.firstPos.z;
/* 32 */     return (x * x + z * z < 3.38724E7D && this.posY > -10.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v4\EntityBulletTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */