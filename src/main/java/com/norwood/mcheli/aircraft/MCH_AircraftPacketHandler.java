/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.helper.MCH_Utils;
/*     */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*     */ import com.norwood.mcheli.helper.network.HandleSide;
/*     */ import com.norwood.mcheli.weapon.MCH_EntityTvMissile;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.IThreadListener;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ public class MCH_AircraftPacketHandler
/*     */ {
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacketIndRotation(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/*  25 */     if (player != null && !player.field_70170_p.field_72995_K) {
/*  26 */       MCH_PacketIndRotation req = new MCH_PacketIndRotation();
/*  27 */       req.readData(data);
/*  28 */       if (req.entityID_Ac > 0) {
/*  29 */         scheduler.func_152344_a(() -> {
/*     */               Entity e = player.field_70170_p.func_73045_a(req.entityID_Ac);
/*     */               if (e instanceof MCH_EntityAircraft) {
/*     */                 MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
/*     */                 ac.setRotRoll(req.roll);
/*     */                 if (req.rollRev) {
/*     */                   MCH_Lib.DbgLog(ac.field_70170_p, "onPacketIndRotation Error:req.rollRev y=%.2f, p=%.2f, r=%.2f", new Object[] { Float.valueOf(req.yaw), Float.valueOf(req.pitch), Float.valueOf(req.roll) });
/*     */                   if (ac.getRiddenByEntity() != null) {
/*     */                     (ac.getRiddenByEntity()).field_70177_z = req.yaw;
/*     */                     (ac.getRiddenByEntity()).field_70126_B = req.yaw;
/*     */                   } 
/*     */                   for (int sid = 0; sid < ac.getSeatNum(); sid++) {
/*     */                     Entity entity = ac.getEntityBySeatId(1 + sid);
/*     */                     if (entity != null) {
/*     */                       entity.field_70177_z += (entity.field_70177_z <= 0.0F) ? 180.0F : -180.0F;
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */                 ac.setRotYaw(req.yaw);
/*     */                 ac.setRotPitch(req.pitch);
/*     */               } 
/*     */             });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacketOnMountEntity(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/*  59 */     if (player != null && player.field_70170_p.field_72995_K) {
/*  60 */       MCH_PacketNotifyOnMountEntity req = new MCH_PacketNotifyOnMountEntity();
/*  61 */       req.readData(data);
/*  62 */       scheduler.func_152344_a(() -> {
/*     */             MCH_Lib.DbgLog(player.field_70170_p, "onPacketOnMountEntity.rcv:%d, %d, %d, %d", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)player)), Integer.valueOf(req.entityID_Ac), Integer.valueOf(req.entityID_rider), Integer.valueOf(req.seatID) });
/*     */             if (req.entityID_Ac > 0 && req.entityID_rider > 0 && req.seatID >= 0) {
/*     */               Entity e = player.field_70170_p.func_73045_a(req.entityID_Ac);
/*     */               if (e instanceof MCH_EntityAircraft) {
/*     */                 MCH_Lib.DbgLog(player.field_70170_p, "onPacketOnMountEntity:" + W_Entity.getEntityId((Entity)player), new Object[0]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @HandleSide({Side.CLIENT})
/*     */   public static void onPacketNotifyAmmoNum(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/*  89 */     if (player != null && player.field_70170_p.field_72995_K) {
/*  90 */       MCH_PacketNotifyAmmoNum status = new MCH_PacketNotifyAmmoNum();
/*  91 */       status.readData(data);
/*  92 */       if (status.entityID_Ac > 0)
/*  93 */         scheduler.func_152344_a(() -> {
/*     */               Entity e = player.field_70170_p.func_73045_a(status.entityID_Ac);
/*     */               if (e instanceof MCH_EntityAircraft) {
/*     */                 MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
/*     */                 StringBuilder msg = new StringBuilder("onPacketNotifyAmmoNum:");
/*     */                 msg.append((ac.getAcInfo() != null) ? (ac.getAcInfo()).displayName : "null").append(":");
/*     */                 if (status.all) {
/*     */                   msg.append("All=true, Num=").append(status.num);
/*     */                   int i = 0;
/*     */                   while (i < ac.getWeaponNum() && i < status.num) {
/*     */                     ac.getWeapon(i).setAmmoNum(status.ammo[i]);
/*     */                     ac.getWeapon(i).setRestAllAmmoNum(status.restAmmo[i]);
/*     */                     msg.append(", [").append(status.ammo[i]).append("/").append(status.restAmmo[i]).append("]");
/*     */                     i++;
/*     */                   } 
/*     */                   MCH_Lib.DbgLog(e.field_70170_p, msg.toString(), new Object[0]);
/*     */                 } else if (status.weaponID < ac.getWeaponNum()) {
/*     */                   msg.append("All=false, WeaponID=").append(status.weaponID).append(", ").append(status.ammo[0]).append(", ").append(status.restAmmo[0]);
/*     */                   ac.getWeapon(status.weaponID).setAmmoNum(status.ammo[0]);
/*     */                   ac.getWeapon(status.weaponID).setRestAllAmmoNum(status.restAmmo[0]);
/*     */                   MCH_Lib.DbgLog(e.field_70170_p, msg.toString(), new Object[0]);
/*     */                 } else {
/*     */                   MCH_Lib.DbgLog(e.field_70170_p, "Error:" + status.weaponID, new Object[0]);
/*     */                 } 
/*     */               } 
/*     */             }); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacketStatusRequest(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 124 */     if (!player.field_70170_p.field_72995_K) {
/* 125 */       MCH_PacketStatusRequest req = new MCH_PacketStatusRequest();
/* 126 */       req.readData(data);
/* 127 */       if (req.entityID_AC > 0) {
/* 128 */         scheduler.func_152344_a(() -> {
/*     */               Entity e = player.field_70170_p.func_73045_a(req.entityID_AC);
/*     */               if (e instanceof MCH_EntityAircraft) {
/*     */                 MCH_PacketStatusResponse.sendStatus((MCH_EntityAircraft)e, player);
/*     */               }
/*     */             });
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacketIndNotifyAmmoNum(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 140 */     if (!player.field_70170_p.field_72995_K) {
/* 141 */       MCH_PacketIndNotifyAmmoNum req = new MCH_PacketIndNotifyAmmoNum();
/* 142 */       req.readData(data);
/* 143 */       if (req.entityID_Ac > 0) {
/* 144 */         scheduler.func_152344_a(() -> {
/*     */               Entity e = player.field_70170_p.func_73045_a(req.entityID_Ac);
/*     */               if (e instanceof MCH_EntityAircraft) {
/*     */                 if (req.weaponID >= 0) {
/*     */                   MCH_PacketNotifyAmmoNum.sendAmmoNum((MCH_EntityAircraft)e, player, req.weaponID);
/*     */                 } else {
/*     */                   MCH_PacketNotifyAmmoNum.sendAllAmmoNum((MCH_EntityAircraft)e, player);
/*     */                 } 
/*     */               }
/*     */             });
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacketIndReload(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 160 */     if (!player.field_70170_p.field_72995_K) {
/* 161 */       MCH_PacketIndReload ind = new MCH_PacketIndReload();
/* 162 */       ind.readData(data);
/* 163 */       if (ind.entityID_Ac > 0)
/* 164 */         scheduler.func_152344_a(() -> {
/*     */               Entity e = player.field_70170_p.func_73045_a(ind.entityID_Ac);
/*     */               if (e instanceof MCH_EntityAircraft) {
/*     */                 MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
/*     */                 MCH_Lib.DbgLog(e.field_70170_p, "onPacketIndReload :%s", new Object[] { (ac.getAcInfo()).displayName });
/*     */                 ac.supplyAmmo(ind.weaponID);
/*     */               } 
/*     */             }); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @HandleSide({Side.CLIENT})
/*     */   public static void onPacketStatusResponse(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 177 */     if (player.field_70170_p.field_72995_K) {
/* 178 */       MCH_PacketStatusResponse status = new MCH_PacketStatusResponse();
/* 179 */       status.readData(data);
/* 180 */       if (status.entityID_AC > 0) {
/* 181 */         scheduler.func_152344_a(() -> {
/*     */               StringBuilder msg = new StringBuilder("onPacketStatusResponse:EID=" + status.entityID_AC + ":");
/*     */               Entity e = player.field_70170_p.func_73045_a(status.entityID_AC);
/*     */               if (e instanceof MCH_EntityAircraft) {
/*     */                 MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
/*     */                 if (status.seatNum > 0 && status.weaponIDs != null && status.weaponIDs.length == status.seatNum) {
/*     */                   msg.append("seatNum=").append(status.seatNum).append(":");
/*     */                   for (int i = 0; i < status.seatNum; i++) {
/*     */                     ac.updateWeaponID(i, status.weaponIDs[i]);
/*     */                     msg.append("[").append(i).append(",").append(status.weaponIDs[i]).append("]");
/*     */                   } 
/*     */                 } else {
/*     */                   msg.append("Error seatNum=").append(status.seatNum);
/*     */                 } 
/*     */               } 
/*     */               MCH_Lib.DbgLog(true, msg.toString(), new Object[0]);
/*     */             });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @HandleSide({Side.CLIENT})
/*     */   public static void onPacketNotifyWeaponID(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 205 */     if (player.field_70170_p.field_72995_K) {
/* 206 */       MCH_PacketNotifyWeaponID status = new MCH_PacketNotifyWeaponID();
/* 207 */       status.readData(data);
/* 208 */       if (status.entityID_Ac > 0)
/* 209 */         scheduler.func_152344_a(() -> {
/*     */               Entity e = player.field_70170_p.func_73045_a(status.entityID_Ac);
/*     */               if (e instanceof MCH_EntityAircraft) {
/*     */                 MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
/*     */                 if (ac.isValidSeatID(status.seatID)) {
/*     */                   ac.getWeapon(status.weaponID).setAmmoNum(status.ammo);
/*     */                   ac.getWeapon(status.weaponID).setRestAllAmmoNum(status.restAmmo);
/*     */                   MCH_Lib.DbgLog(true, "onPacketNotifyWeaponID:WeaponID=%d (%d / %d)", new Object[] { Integer.valueOf(status.weaponID), Short.valueOf(status.ammo), Short.valueOf(status.restAmmo) });
/*     */                   if (W_Lib.isClientPlayer(ac.getEntityBySeatId(status.seatID))) {
/*     */                     MCH_Lib.DbgLog(true, "onPacketNotifyWeaponID:#discard:SeatID=%d, WeaponID=%d", new Object[] { Integer.valueOf(status.seatID), Integer.valueOf(status.weaponID) });
/*     */                   } else {
/*     */                     MCH_Lib.DbgLog(true, "onPacketNotifyWeaponID:SeatID=%d, WeaponID=%d", new Object[] { Integer.valueOf(status.seatID), Integer.valueOf(status.weaponID) });
/*     */                     ac.updateWeaponID(status.seatID, status.weaponID);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             }); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @HandleSide({Side.CLIENT})
/*     */   public static void onPacketNotifyHitBullet(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 231 */     if (player.field_70170_p.field_72995_K) {
/* 232 */       MCH_PacketNotifyHitBullet status = new MCH_PacketNotifyHitBullet();
/* 233 */       status.readData(data);
/* 234 */       scheduler.func_152344_a(() -> {
/*     */             if (status.entityID_Ac <= 0) {
/*     */               MCH_MOD.proxy.hitBullet();
/*     */             } else {
/*     */               Entity e = player.field_70170_p.func_73045_a(status.entityID_Ac);
/*     */               if (e instanceof MCH_EntityAircraft) {
/*     */                 ((MCH_EntityAircraft)e).hitBullet();
/*     */               }
/*     */             } 
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacketSeatListRequest(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 249 */     if (!player.field_70170_p.field_72995_K) {
/* 250 */       MCH_PacketSeatListRequest req = new MCH_PacketSeatListRequest();
/* 251 */       req.readData(data);
/* 252 */       if (req.entityID_AC > 0) {
/* 253 */         scheduler.func_152344_a(() -> {
/*     */               Entity e = player.field_70170_p.func_73045_a(req.entityID_AC);
/*     */               if (e instanceof MCH_EntityAircraft) {
/*     */                 MCH_PacketSeatListResponse.sendSeatList((MCH_EntityAircraft)e, player);
/*     */               }
/*     */             });
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @HandleSide({Side.CLIENT})
/*     */   public static void onPacketNotifyTVMissileEntity(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 265 */     if (player.field_70170_p.field_72995_K) {
/* 266 */       MCH_PacketNotifyTVMissileEntity packet = new MCH_PacketNotifyTVMissileEntity();
/* 267 */       packet.readData(data);
/* 268 */       if (packet.entityID_Ac <= 0) {
/*     */         return;
/*     */       }
/*     */       
/* 272 */       if (packet.entityID_TVMissile <= 0) {
/*     */         return;
/*     */       }
/*     */       
/* 276 */       scheduler.func_152344_a(() -> {
/*     */             Entity e = player.field_70170_p.func_73045_a(packet.entityID_Ac);
/*     */             if (e instanceof MCH_EntityAircraft) {
/*     */               MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
/*     */               e = player.field_70170_p.func_73045_a(packet.entityID_TVMissile);
/*     */               if (e instanceof MCH_EntityTvMissile) {
/*     */                 ((MCH_EntityTvMissile)e).shootingEntity = (Entity)player;
/*     */                 ac.setTVMissile((MCH_EntityTvMissile)e);
/*     */               } 
/*     */             } 
/*     */           });
/*     */     } 
/*     */   }
/*     */   @HandleSide({Side.CLIENT})
/*     */   public static void onPacketSeatListResponse(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 291 */     if (player.field_70170_p.field_72995_K) {
/* 292 */       MCH_PacketSeatListResponse seatList = new MCH_PacketSeatListResponse();
/* 293 */       seatList.readData(data);
/* 294 */       if (seatList.entityID_AC > 0) {
/* 295 */         scheduler.func_152344_a(() -> {
/*     */               Entity e = player.field_70170_p.func_73045_a(seatList.entityID_AC);
/*     */               if (e instanceof MCH_EntityAircraft) {
/*     */                 MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
/*     */                 if (seatList.seatNum > 0 && seatList.seatNum == (ac.getSeats()).length && seatList.seatEntityID != null && seatList.seatEntityID.length == seatList.seatNum) {
/*     */                   for (int i = 0; i < seatList.seatNum; i++) {
/*     */                     Entity entity = player.field_70170_p.func_73045_a(seatList.seatEntityID[i]);
/*     */                     if (entity instanceof MCH_EntitySeat) {
/*     */                       MCH_EntitySeat seat = (MCH_EntitySeat)entity;
/*     */                       seat.seatID = i;
/*     */                       seat.parentUniqueID = ac.getCommonUniqueId();
/*     */                       ac.setSeat(i, seat);
/*     */                       seat.setParent(ac);
/*     */                     } 
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacket_PlayerControl(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 322 */     if (!player.field_70170_p.field_72995_K) {
/* 323 */       MCH_PacketSeatPlayerControl pc = new MCH_PacketSeatPlayerControl();
/* 324 */       pc.readData(data);
/* 325 */       scheduler.func_152344_a(() -> {
/*     */             MCH_EntityAircraft ac;
/*     */             Entity patt16123$temp = player.func_184187_bx();
/*     */             if (patt16123$temp instanceof MCH_EntitySeat) {
/*     */               MCH_EntitySeat seat = (MCH_EntitySeat)patt16123$temp;
/*     */               ac = seat.getParent();
/*     */             } else {
/*     */               ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
/*     */             } 
/*     */             if (ac != null) {
/*     */               if (pc.isUnmount) {
/*     */                 ac.unmountEntityFromSeat((Entity)player);
/*     */               } else if (pc.switchSeat > 0) {
/*     */                 if (pc.switchSeat == 3) {
/*     */                   player.func_184210_p();
/*     */                   ac.keepOnRideRotation = true;
/*     */                   ac.processInitialInteract(player, true, EnumHand.MAIN_HAND);
/*     */                 } 
/*     */                 if (pc.switchSeat == 1) {
/*     */                   ac.switchNextSeat((Entity)player);
/*     */                 }
/*     */                 if (pc.switchSeat == 2) {
/*     */                   ac.switchPrevSeat((Entity)player);
/*     */                 }
/*     */               } else if (pc.parachuting) {
/*     */                 ac.unmount((Entity)player);
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacket_ClientSetting(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 360 */     if (!player.field_70170_p.field_72995_K) {
/* 361 */       MCH_PacketNotifyClientSetting pc = new MCH_PacketNotifyClientSetting();
/* 362 */       pc.readData(data);
/* 363 */       scheduler.func_152344_a(() -> {
/*     */             MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
/*     */             if (ac != null) {
/*     */               int sid = ac.getSeatIdByEntity((Entity)player);
/*     */               if (sid == 0) {
/*     */                 ac.cs_dismountAll = pc.dismountAll;
/*     */                 ac.cs_heliAutoThrottleDown = pc.heliAutoThrottleDown;
/*     */                 ac.cs_planeAutoThrottleDown = pc.planeAutoThrottleDown;
/*     */                 ac.cs_tankAutoThrottleDown = pc.tankAutoThrottleDown;
/*     */               } 
/*     */               ac.camera.setShaderSupport(sid, Boolean.valueOf(pc.shaderSupport));
/*     */             } 
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacketNotifyInfoReloaded(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 382 */     if (!player.field_70170_p.field_72995_K) {
/* 383 */       MCH_PacketNotifyInfoReloaded pc = new MCH_PacketNotifyInfoReloaded();
/* 384 */       pc.readData(data);
/* 385 */       scheduler.func_152344_a(() -> {
/*     */             MCH_EntityAircraft ac;
/*     */             switch (pc.type) {
/*     */               case 0:
/*     */                 ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
/*     */                 if (ac != null && ac.getAcInfo() != null) {
/*     */                   String name = (ac.getAcInfo()).name;
/*     */                   for (WorldServer world : (MCH_Utils.getServer()).field_71305_c) {
/*     */                     List<Entity> list = world.field_72996_f;
/*     */                     for (Entity entity : list) {
/*     */                       if (entity instanceof MCH_EntityAircraft) {
/*     */                         ac = (MCH_EntityAircraft)entity;
/*     */                         if (ac.getAcInfo() != null && (ac.getAcInfo()).name.equals(name)) {
/*     */                           ac.changeType(name);
/*     */                           ac.createSeats(UUID.randomUUID().toString());
/*     */                           ac.onAcInfoReloaded();
/*     */                         } 
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */                 break;
/*     */               case 1:
/*     */                 ContentRegistries.weapon().reloadAll();
/*     */                 for (WorldServer world : (MCH_Utils.getServer()).field_71305_c) {
/*     */                   List<Entity> list = world.field_72996_f;
/*     */                   for (Entity entity : list) {
/*     */                     if (entity instanceof MCH_EntityAircraft) {
/*     */                       MCH_EntityAircraft entityAircraft = (MCH_EntityAircraft)entity;
/*     */                       if (entityAircraft.getAcInfo() != null) {
/*     */                         entityAircraft.changeType((entityAircraft.getAcInfo()).name);
/*     */                         entityAircraft.createSeats(UUID.randomUUID().toString());
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */                 break;
/*     */             } 
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_AircraftPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */