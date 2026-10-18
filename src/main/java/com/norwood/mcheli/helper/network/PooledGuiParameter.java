/*    */ package com.norwood.mcheli.helper.network;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class PooledGuiParameter
/*    */ {
/*    */   private static Entity clientEntity;
/*    */   private static Entity serverEntity;
/*    */   
/*    */   public static void setEntity(EntityPlayer player, @Nullable Entity target) {
/* 13 */     if (player.world.isRemote) {
/* 14 */       clientEntity = target;
/*    */     } else {
/* 16 */       serverEntity = target;
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static Entity getEntity(EntityPlayer player) {
/* 22 */     return player.world.isRemote ? clientEntity : serverEntity;
/*    */   }
/*    */   
/*    */   public static void resetEntity(EntityPlayer player) {
/* 26 */     setEntity(player, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\network\PooledGuiParameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */