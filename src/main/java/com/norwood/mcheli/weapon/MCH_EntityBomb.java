/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_Lib;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MCH_EntityBomb
/*    */   extends MCH_EntityBaseBullet {
/*    */   public MCH_EntityBomb(World par1World) {
/* 15 */     super(par1World);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_EntityBomb(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/* 21 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_70071_h_() {
/* 26 */     super.func_70071_h_();
/* 27 */     if (!this.field_70170_p.field_72995_K && getInfo() != null) {
/* 28 */       this.field_70159_w *= 0.999D;
/* 29 */       this.field_70179_y *= 0.999D;
/* 30 */       if (func_70090_H()) {
/* 31 */         this.field_70159_w *= (getInfo()).velocityInWater;
/* 32 */         this.field_70181_x *= (getInfo()).velocityInWater;
/* 33 */         this.field_70179_y *= (getInfo()).velocityInWater;
/*    */       } 
/*    */       
/* 36 */       float dist = (getInfo()).proximityFuseDist;
/* 37 */       if (dist > 0.1F && getCountOnUpdate() % 10 == 0) {
/* 38 */         List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72314_b(dist, dist, dist));
/* 39 */         for (Entity entity : list) {
/* 40 */           if (W_Lib.isEntityLivingBase(entity) && canBeCollidedEntity(entity)) {
/* 41 */             RayTraceResult m = new RayTraceResult(new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v), EnumFacing.DOWN, new BlockPos(this.field_70165_t + 0.5D, this.field_70163_u + 0.5D, this.field_70161_v + 0.5D));
/*    */ 
/*    */             
/* 44 */             onImpact(m, 1.0F);
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 51 */     onUpdateBomblet();
/*    */   }
/*    */ 
/*    */   
/*    */   public void sprinkleBomblet() {
/* 56 */     if (!this.field_70170_p.field_72995_K) {
/*    */       
/* 58 */       MCH_EntityBomb e = new MCH_EntityBomb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70146_Z.nextInt(360), 0.0F, this.acceleration);
/*    */       
/* 60 */       e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
/* 61 */       e.setName(func_70005_c_());
/* 62 */       float RANDOM = (getInfo()).bombletDiff;
/* 63 */       this.field_70159_w += ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
/* 64 */       e.field_70181_x = this.field_70181_x / 2.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM / 2.0F);
/* 65 */       this.field_70179_y += ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
/* 66 */       e.setBomblet();
/* 67 */       this.field_70170_p.func_72838_d((Entity)e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_BulletModel getDefaultBulletModel() {
/* 73 */     return MCH_DefaultBulletModels.Bomb;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityBomb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */