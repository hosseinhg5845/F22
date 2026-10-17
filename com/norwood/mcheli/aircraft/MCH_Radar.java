/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_Vector2;
/*    */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_Radar
/*    */ {
/*    */   private final World worldObj;
/* 15 */   private final ArrayList<MCH_Vector2> entityList = new ArrayList<>();
/* 16 */   private final ArrayList<MCH_Vector2> enemyList = new ArrayList<>();
/*    */   
/*    */   public MCH_Radar(World world) {
/* 19 */     this.worldObj = world;
/*    */   }
/*    */   
/*    */   public ArrayList<MCH_Vector2> getEntityList() {
/* 23 */     return this.entityList;
/*    */   }
/*    */   
/*    */   public ArrayList<MCH_Vector2> getEnemyList() {
/* 27 */     return this.enemyList;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 31 */     this.entityList.clear();
/* 32 */     this.enemyList.clear();
/*    */   }
/*    */   
/*    */   public void updateXZ(Entity centerEntity, int range) {
/* 36 */     if (this.worldObj.field_72995_K) {
/* 37 */       clear();
/*    */       
/* 39 */       List<Entity> list = centerEntity.field_70170_p.func_72839_b(centerEntity, centerEntity.func_174813_aQ().func_72314_b(range, range, range));
/*    */       
/* 41 */       for (Entity entity : list) {
/* 42 */         if (entity instanceof net.minecraft.entity.EntityLiving) {
/* 43 */           double x = entity.field_70165_t - centerEntity.field_70165_t;
/* 44 */           double z = entity.field_70161_v - centerEntity.field_70161_v;
/* 45 */           if (x * x + z * z < (range * range)) {
/* 46 */             int y = 1 + (int)entity.field_70163_u;
/* 47 */             if (y < 0) {
/* 48 */               y = 1;
/*    */             }
/*    */             
/*    */             int blockCnt;
/* 52 */             for (blockCnt = 0; y < 200 && (
/* 53 */               W_WorldFunc.getBlockId(this.worldObj, (int)entity.field_70165_t, y, (int)entity.field_70161_v) == 0 || 
/* 54 */               ++blockCnt < 5); y++);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 60 */             if (blockCnt < 5) {
/* 61 */               if (entity instanceof net.minecraft.entity.monster.EntityMob) {
/* 62 */                 this.enemyList.add(new MCH_Vector2(x, z)); continue;
/*    */               } 
/* 64 */               this.entityList.add(new MCH_Vector2(x, z));
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_Radar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */