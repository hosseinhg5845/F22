/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import net.minecraftforge.event.CommandEvent;
/*    */ import net.minecraftforge.event.entity.EntityEvent;
/*    */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*    */ import net.minecraftforge.event.entity.living.LivingAttackEvent;
/*    */ import net.minecraftforge.event.entity.living.LivingHurtEvent;
/*    */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class W_EventHook {
/*    */   @SubscribeEvent
/*    */   public void onEvent_entitySpawn(EntityJoinWorldEvent event) {
/* 14 */     entitySpawn(event);
/*    */   }
/*    */ 
/*    */   
/*    */   public void entitySpawn(EntityJoinWorldEvent event) {}
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onEvent_livingHurtEvent(LivingHurtEvent event) {
/* 22 */     livingHurtEvent(event);
/*    */   }
/*    */ 
/*    */   
/*    */   public void livingHurtEvent(LivingHurtEvent event) {}
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onEvent_livingAttackEvent(LivingAttackEvent event) {
/* 30 */     livingAttackEvent(event);
/*    */   }
/*    */ 
/*    */   
/*    */   public void livingAttackEvent(LivingAttackEvent event) {}
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onEvent_entityInteractEvent(PlayerInteractEvent.EntityInteract event) {
/* 38 */     entityInteractEvent(event);
/*    */   }
/*    */ 
/*    */   
/*    */   public void entityInteractEvent(PlayerInteractEvent.EntityInteract event) {}
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onEvent_entityCanUpdate(EntityEvent.CanUpdate event) {
/* 46 */     entityCanUpdate(event);
/*    */   }
/*    */ 
/*    */   
/*    */   public void entityCanUpdate(EntityEvent.CanUpdate event) {}
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onEvent_commandEvent(CommandEvent event) {
/* 54 */     commandEvent(event);
/*    */   }
/*    */   
/*    */   public void commandEvent(CommandEvent event) {}
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_EventHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */