/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */ import com.norwood.mcheli.helper.network.HandleSide;
/*     */ import com.norwood.mcheli.lweapon.MCH_ClientLightWeaponTickHandler;
/*     */ import com.norwood.mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.IThreadListener;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ public class MCH_CommonPacketHandler
/*     */ {
/*     */   @HandleSide({Side.CLIENT})
/*     */   public static void onPacketEffectExplosion(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/*  18 */     if (player.world.isRemote) {
/*  19 */       MCH_PacketEffectExplosion pkt = new MCH_PacketEffectExplosion();
/*  20 */       pkt.readData(data);
/*  21 */       scheduler.addScheduledTask(() -> {
/*     */             Entity exploder = null;
/*     */             if (player.getDistanceSq(pkt.prm.posX, pkt.prm.posY, pkt.prm.posZ) <= 40000.0D) {
/*     */               if (!pkt.prm.inWater) {
/*     */                 if (!MCH_Config.DefaultExplosionParticle.prmBool) {
/*     */                   MCH_Explosion.effectExplosion(player.world, exploder, pkt.prm.posX, pkt.prm.posY, pkt.prm.posZ, pkt.prm.size, true, pkt.prm.getAffectedBlockPositions());
/*     */                 } else {
/*     */                   MCH_Explosion.DEF_effectExplosion(player.world, exploder, pkt.prm.posX, pkt.prm.posY, pkt.prm.posZ, pkt.prm.size, true, pkt.prm.getAffectedBlockPositions());
/*     */                 } 
/*     */               } else {
/*     */                 MCH_Explosion.effectExplosionInWater(player.world, exploder, pkt.prm.posX, pkt.prm.posY, pkt.prm.posZ, pkt.prm.size, true);
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacketIndOpenScreen(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/*  46 */     if (!player.world.isRemote) {
/*  47 */       MCH_PacketIndOpenScreen pkt = new MCH_PacketIndOpenScreen();
/*  48 */       pkt.readData(data);
/*  49 */       scheduler.addScheduledTask(() -> {
/*     */             if (pkt.guiID == 3) {
/*     */               MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
/*     */               if (ac != null) {
/*     */                 ac.displayInventory(player);
/*     */               }
/*     */             } else {
/*     */               player.openGui(MCH_MOD.instance, pkt.guiID, player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
/*     */             } 
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @HandleSide({Side.CLIENT})
/*     */   public static void onPacketNotifyServerSettings(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/*  68 */     if (player.world.isRemote) {
/*  69 */       MCH_PacketNotifyServerSettings pkt = new MCH_PacketNotifyServerSettings();
/*  70 */       pkt.readData(data);
/*  71 */       scheduler.addScheduledTask(() -> {
/*     */             MCH_Lib.DbgLog(false, "onPacketNotifyServerSettings:" + player, new Object[0]);
/*     */             if (!pkt.enableCamDistChange) {
/*     */               W_Reflection.setThirdPersonDistance(4.0F);
/*     */             }
/*     */             MCH_ServerSettings.enableCamDistChange = pkt.enableCamDistChange;
/*     */             MCH_ServerSettings.enableEntityMarker = pkt.enableEntityMarker;
/*     */             MCH_ServerSettings.enablePVP = pkt.enablePVP;
/*     */             MCH_ServerSettings.stingerLockRange = pkt.stingerLockRange;
/*     */             MCH_ServerSettings.enableDebugBoundingBox = pkt.enableDebugBoundingBox;
/*     */             MCH_ServerSettings.enableRotationLimit = pkt.enableRotationLimit;
/*     */             MCH_ServerSettings.pitchLimitMax = pkt.pitchLimitMax;
/*     */             MCH_ServerSettings.pitchLimitMin = pkt.pitchLimitMin;
/*     */             MCH_ServerSettings.rollLimit = pkt.rollLimit;
/*     */             MCH_ClientLightWeaponTickHandler.lockRange = MCH_ServerSettings.stingerLockRange;
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @HandleSide({Side.CLIENT, Side.SERVER})
/*     */   public static void onPacketNotifyLock(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/*  93 */     MCH_PacketNotifyLock pkt = new MCH_PacketNotifyLock();
/*  94 */     pkt.readData(data);
/*  95 */     if (!player.world.isRemote) {
/*  96 */       if (pkt.entityID >= 0) {
/*  97 */         scheduler.addScheduledTask(() -> {
/*     */               Entity target = player.world.getEntityByID(pkt.entityID);
/*     */               
/*     */               if (target != null) {
/*     */                 MCH_EntityAircraft ac;
/*     */                 if (target instanceof MCH_EntityAircraft) {
/*     */                   ac = (MCH_EntityAircraft)target;
/*     */                 } else if (target instanceof MCH_EntitySeat) {
/*     */                   ac = ((MCH_EntitySeat)target).getParent();
/*     */                 } else {
/*     */                   ac = MCH_EntityAircraft.getAircraft_RiddenOrControl(target);
/*     */                 } 
/*     */                 if (ac != null && ac.haveFlare() && !ac.isDestroyed()) {
/*     */                   for (int i = 0; i < 2; i++) {
/*     */                     Entity entity = ac.getEntityBySeatId(i);
/*     */                     if (entity instanceof net.minecraft.entity.player.EntityPlayerMP) {
/*     */                       MCH_PacketNotifyLock.sendToPlayer((EntityPlayer)entity);
/*     */                     }
/*     */                   } 
/*     */                 } else if (target.getRidingEntity() != null && target instanceof net.minecraft.entity.player.EntityPlayerMP) {
/*     */                   MCH_PacketNotifyLock.sendToPlayer((EntityPlayer)target);
/*     */                 } 
/*     */               } 
/*     */             });
/*     */       }
/*     */     } else {
/* 123 */       scheduler.addScheduledTask(() -> MCH_MOD.proxy.clientLocked());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_CommonPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */