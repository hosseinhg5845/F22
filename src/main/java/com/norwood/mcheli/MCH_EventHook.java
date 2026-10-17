/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */ import com.norwood.mcheli.aircraft.MCH_ItemAircraft;
/*     */ import com.norwood.mcheli.chain.MCH_ItemChain;
/*     */ import com.norwood.mcheli.command.MCH_Command;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityBaseBullet;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_EntityPlayer;
/*     */ import com.norwood.mcheli.wrapper.W_EventHook;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraftforge.event.CommandEvent;
/*     */ import net.minecraftforge.event.entity.EntityEvent;
/*     */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingAttackEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingHurtEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*     */ 
/*     */ public class MCH_EventHook
/*     */   extends W_EventHook
/*     */ {
/*     */   public void commandEvent(CommandEvent event) {
/*  30 */     MCH_Command.onCommandEvent(event);
/*     */   }
/*     */ 
/*     */   
/*     */   public void entitySpawn(EntityJoinWorldEvent event) {
/*  35 */     Entity entity = event.getEntity();
/*     */ 
/*     */     
/*  38 */     if (W_Lib.isEntityLivingBase(entity) && !W_EntityPlayer.isPlayer(entity)) {
/*  39 */       MCH_MOD.proxy.setRenderEntityDistanceWeight(MCH_Config.MobRenderDistanceWeight.prmDouble);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  44 */     if (entity instanceof MCH_EntityAircraft) { MCH_EntityAircraft aircraft = (MCH_EntityAircraft)entity;
/*  45 */       if (!aircraft.field_70170_p.field_72995_K && !aircraft.isCreatedSeats()) {
/*  46 */         aircraft.createSeats(UUID.randomUUID().toString());
/*     */       }
/*     */       
/*     */       return; }
/*     */ 
/*     */     
/*  52 */     if (W_EntityPlayer.isPlayer(entity)) {
/*  53 */       correctInvalidRotation(entity);
/*     */       
/*  55 */       if (!entity.field_70170_p.field_72995_K && entity instanceof EntityPlayerMP) {
/*  56 */         MCH_Lib.DbgLog(false, "EntityJoinWorldEvent: " + entity, new Object[0]);
/*  57 */         MCH_PacketNotifyServerSettings.send((EntityPlayerMP)entity);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void correctInvalidRotation(Entity e) {
/*  65 */     boolean invalidPitch = (Float.isNaN(e.field_70125_A) || Float.isNaN(e.field_70127_C) || Float.isInfinite(e.field_70125_A) || Float.isInfinite(e.field_70127_C));
/*     */     
/*  67 */     if (invalidPitch) {
/*  68 */       MCH_Lib.Log(e, "### EntityJoinWorldEvent Error: Player invalid rotation pitch (" + e.field_70125_A + ")", new Object[0]);
/*  69 */       e.field_70125_A = 0.0F;
/*  70 */       e.field_70127_C = 0.0F;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  75 */     boolean invalidYaw = (Float.isNaN(e.field_70177_z) || Float.isNaN(e.field_70126_B) || Float.isInfinite(e.field_70177_z) || Float.isInfinite(e.field_70126_B));
/*     */     
/*  77 */     if (invalidYaw) {
/*  78 */       MCH_Lib.Log(e, "### EntityJoinWorldEvent Error: Player invalid rotation yaw (" + e.field_70177_z + ")", new Object[0]);
/*  79 */       e.field_70177_z = 0.0F;
/*  80 */       e.field_70126_B = 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void livingAttackEvent(LivingAttackEvent event) {
/*  87 */     MCH_EntityAircraft ac = getRiddenAircraft(event.getEntity());
/*  88 */     if (ac != null && 
/*  89 */       ac.getAcInfo() != null && 
/*  90 */       !ac.isDestroyed() && 
/*  91 */       (ac.getAcInfo()).damageFactor <= 0.0F) {
/*  92 */       Entity attackEntity = event.getSource().func_76346_g();
/*  93 */       if (attackEntity == null) {
/*  94 */         event.setCanceled(true);
/*  95 */       } else if (W_Entity.isEqual(attackEntity, event.getEntity())) {
/*  96 */         event.setCanceled(true);
/*  97 */       } else if (ac.isMountedEntity(attackEntity)) {
/*  98 */         event.setCanceled(true);
/*     */       } else {
/* 100 */         MCH_EntityAircraft atkac = getRiddenAircraft(attackEntity);
/* 101 */         if (W_Entity.isEqual((Entity)atkac, (Entity)ac)) {
/* 102 */           event.setCanceled(true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void livingHurtEvent(LivingHurtEvent event) {
/* 113 */     Entity entity = event.getEntity();
/* 114 */     MCH_EntityAircraft aircraft = getRiddenAircraft(entity);
/*     */     
/* 116 */     if (aircraft == null || aircraft.isDestroyed() || aircraft.getAcInfo() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 120 */     Entity attacker = event.getSource().func_76346_g();
/* 121 */     float damage = event.getAmount();
/* 122 */     float factor = (aircraft.getAcInfo()).damageFactor;
/*     */     
/* 124 */     boolean selfDamage = (attacker == null || W_Entity.isEqual(attacker, entity));
/* 125 */     boolean isRiderAttacking = aircraft.isMountedEntity(attacker);
/* 126 */     boolean isSameAircraft = W_Entity.isEqual((Entity)getRiddenAircraft(attacker), (Entity)aircraft);
/*     */     
/* 128 */     if (isRiderAttacking || isSameAircraft) {
/* 129 */       event.setCanceled(true);
/* 130 */       event.setAmount(0.0F);
/*     */       
/*     */       return;
/*     */     } 
/* 134 */     aircraft.func_70097_a(event.getSource(), damage * 2.0F);
/* 135 */     event.setAmount(damage * factor);
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityAircraft getRiddenAircraft(Entity entity) {
/* 140 */     MCH_EntityAircraft ac = null;
/* 141 */     Entity ridden = entity.func_184187_bx();
/* 142 */     if (ridden instanceof MCH_EntityAircraft) {
/* 143 */       ac = (MCH_EntityAircraft)ridden;
/* 144 */     } else if (ridden instanceof MCH_EntitySeat) {
/* 145 */       ac = ((MCH_EntitySeat)ridden).getParent();
/*     */     } 
/*     */     
/* 148 */     if (ac == null) {
/* 149 */       List<MCH_EntityAircraft> list = entity.field_70170_p.func_72872_a(MCH_EntityAircraft.class, entity.func_174813_aQ().func_72314_b(50.0D, 50.0D, 50.0D));
/* 150 */       for (MCH_EntityAircraft tmp : list) {
/* 151 */         if (tmp.isMountedEntity(entity)) {
/* 152 */           return tmp;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 157 */     return ac;
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInteractEvent(PlayerInteractEvent.EntityInteract event) {
/* 162 */     ItemStack item = event.getEntityPlayer().func_184586_b(EnumHand.MAIN_HAND);
/* 163 */     if (!item.func_190926_b()) {
/* 164 */       if (item.func_77973_b() instanceof MCH_ItemChain) {
/* 165 */         MCH_ItemChain.interactEntity(item, event.getTarget(), event.getEntityPlayer(), (event.getEntityPlayer()).field_70170_p);
/* 166 */         event.setCanceled(true);
/* 167 */       } else if (item.func_77973_b() instanceof MCH_ItemAircraft) {
/* 168 */         ((MCH_ItemAircraft)item.func_77973_b()).rideEntity(item, event.getTarget(), event.getEntityPlayer());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityCanUpdate(EntityEvent.CanUpdate event) {
/* 175 */     Entity entity = event.getEntity(); if (entity instanceof MCH_EntityBaseBullet) { MCH_EntityBaseBullet bullet = (MCH_EntityBaseBullet)entity;
/* 176 */       bullet.func_70106_y(); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_EventHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */