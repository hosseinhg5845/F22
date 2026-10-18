/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_MOD;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ 
/*    */ public class W_Lib
/*    */ {
/*    */   public static boolean isEntityLivingBase(Entity entity) {
/* 12 */     return entity instanceof EntityLivingBase;
/*    */   }
/*    */   
/*    */   public static EntityLivingBase castEntityLivingBase(Object entity) {
/* 16 */     return (EntityLivingBase)entity;
/*    */   }
/*    */   
/*    */   public static Class<EntityLivingBase> getEntityLivingBaseClass() {
/* 20 */     return EntityLivingBase.class;
/*    */   }
/*    */   
/*    */   public static double getEntityMoveDist(@Nullable Entity entity) {
/* 24 */     if (entity == null) {
/* 25 */       return 0.0D;
/*    */     }
/* 27 */     return (entity instanceof EntityLivingBase) ? ((EntityLivingBase)entity).moveForward : 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isClientPlayer(@Nullable Entity entity) {
/* 32 */     return (entity instanceof net.minecraft.entity.player.EntityPlayer && entity.world.isRemote && W_Entity.isEqual(MCH_MOD.proxy.getClientPlayer(), entity));
/*    */   }
/*    */   
/*    */   public static boolean isFirstPerson() {
/* 36 */     return MCH_MOD.proxy.isFirstPerson();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_Lib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */