/*    */ package com.norwood.mcheli.helper.entity;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public interface ITargetMarkerObject
/*    */ {
/*    */   static ITargetMarkerObject fromEntity(Entity target) {
/* 11 */     return new EntityWrapper(target);
/*    */   }
/*    */   
/*    */   double getX();
/*    */   
/*    */   double getY();
/*    */   
/*    */   double getZ();
/*    */   
/*    */   @Nullable
/*    */   default Entity getEntity() {
/* 22 */     return null;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public static class EntityWrapper implements ITargetMarkerObject {
/*    */     private final Entity target;
/*    */     
/*    */     public EntityWrapper(Entity entity) {
/* 30 */       this.target = entity;
/*    */     }
/*    */ 
/*    */     
/*    */     public double getX() {
/* 35 */       return this.target.field_70165_t;
/*    */     }
/*    */ 
/*    */     
/*    */     public double getY() {
/* 40 */       return this.target.field_70163_u;
/*    */     }
/*    */ 
/*    */     
/*    */     public double getZ() {
/* 45 */       return this.target.field_70161_v;
/*    */     }
/*    */ 
/*    */     
/*    */     public Entity getEntity() {
/* 50 */       return this.target;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\entity\ITargetMarkerObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */