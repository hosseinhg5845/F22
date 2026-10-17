/*     */ package com.norwood.mcheli.multiplay;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.helper.MCH_Utils;
/*     */ import com.norwood.mcheli.helper.entity.IEntitySinglePassenger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.server.CommandScoreboard;
/*     */ import net.minecraft.command.server.CommandTeleport;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.PlayerList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_Multiplay {
/*  31 */   public static final MCH_TargetType[][] ENTITY_SPOT_TABLE = new MCH_TargetType[][] { { MCH_TargetType.NONE, MCH_TargetType.NONE }, { MCH_TargetType.OTHER_MOB, MCH_TargetType.OTHER_MOB }, { MCH_TargetType.MONSTER, MCH_TargetType.MONSTER }, { MCH_TargetType.NONE, MCH_TargetType.NO_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.SAME_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.OTHER_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.NONE }, { MCH_TargetType.NONE, MCH_TargetType.NO_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.SAME_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.OTHER_TEAM_PLAYER } };
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
/*     */   public static boolean canSpotEntityWithFilter(int filter, Entity entity) {
/*  45 */     if (entity instanceof com.norwood.mcheli.plane.MCP_EntityPlane)
/*  46 */       return ((filter & 0x20) != 0); 
/*  47 */     if (entity instanceof com.norwood.mcheli.helicopter.MCH_EntityHeli)
/*  48 */       return ((filter & 0x10) != 0); 
/*  49 */     if (entity instanceof com.norwood.mcheli.vehicle.MCH_EntityVehicle || entity instanceof com.norwood.mcheli.tank.MCH_EntityTank)
/*  50 */       return ((filter & 0x8) != 0); 
/*  51 */     if (entity instanceof EntityPlayer)
/*  52 */       return ((filter & 0x4) != 0); 
/*  53 */     if (!(entity instanceof EntityLivingBase)) {
/*  54 */       return false;
/*     */     }
/*  56 */     return isMonster(entity) ? (((filter & 0x2) != 0)) : (((filter & 0x1) != 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isMonster(Entity entity) {
/*  61 */     return entity.getClass().toString().toLowerCase().contains("monster");
/*     */   }
/*     */   public static MCH_TargetType canSpotEntity(Entity user, double posX, double posY, double posZ, Entity target, boolean checkSee) {
/*     */     EntityLivingBase spotter;
/*  65 */     if (user instanceof EntityLivingBase) { spotter = (EntityLivingBase)user; }
/*  66 */     else { return MCH_TargetType.NONE; }
/*     */     
/*  68 */     int col = (spotter.func_96124_cp() == null) ? 0 : 1;
/*  69 */     int row = 0;
/*  70 */     if (target instanceof EntityLivingBase) {
/*  71 */       if (!isMonster(target)) {
/*  72 */         row = 1;
/*     */       } else {
/*  74 */         row = 2;
/*     */       } 
/*     */     }
/*     */     
/*  78 */     if (spotter.func_96124_cp() != null) {
/*  79 */       if (target instanceof EntityPlayer) { EntityPlayer player = (EntityPlayer)target;
/*  80 */         player.func_96124_cp();
/*  81 */         if (spotter.func_184191_r((Entity)player)) {
/*  82 */           row = 4;
/*     */         } else {
/*  84 */           row = 5;
/*     */         }  }
/*  86 */       else if (target instanceof MCH_EntityAircraft) { MCH_EntityAircraft ac = (MCH_EntityAircraft)target;
/*  87 */         EntityPlayer rideEntity = ac.getFirstMountPlayer();
/*  88 */         if (rideEntity == null) {
/*  89 */           row = 6;
/*     */         } else {
/*  91 */           rideEntity.func_96124_cp();
/*  92 */           if (spotter.func_184191_r((Entity)rideEntity)) {
/*  93 */             row = 8;
/*     */           } else {
/*  95 */             row = 9;
/*     */           } 
/*     */         }  }
/*     */     
/*  99 */     } else if (target instanceof EntityPlayer || target instanceof MCH_EntityAircraft) {
/* 100 */       row = 0;
/*     */     } 
/*     */     
/* 103 */     MCH_TargetType ret = ENTITY_SPOT_TABLE[row][col];
/* 104 */     if (checkSee && ret != MCH_TargetType.NONE) {
/* 105 */       Vec3d vs = new Vec3d(posX, posY, posZ);
/* 106 */       Vec3d ve = new Vec3d(target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v);
/* 107 */       RayTraceResult mop = target.field_70170_p.func_72933_a(vs, ve);
/* 108 */       if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 109 */         ret = MCH_TargetType.NONE;
/*     */       }
/*     */     } 
/*     */     
/* 113 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canAttackEntity(DamageSource ds, Entity target) {
/* 118 */     return canAttackEntity(ds.func_76346_g(), target);
/*     */   }
/*     */   
/*     */   public static boolean canAttackEntity(Entity attacker, Entity target) {
/* 122 */     if (attacker != null && target != null) {
/* 123 */       EntityPlayer attackPlayer = null;
/* 124 */       EntityPlayer targetPlayer = null;
/* 125 */       if (attacker instanceof EntityPlayer) {
/* 126 */         attackPlayer = (EntityPlayer)attacker;
/*     */       }
/*     */       
/* 129 */       if (target instanceof EntityPlayer) {
/* 130 */         targetPlayer = (EntityPlayer)target;
/* 131 */       } else if (target instanceof IEntitySinglePassenger && ((IEntitySinglePassenger)target).getRiddenByEntity() instanceof EntityPlayer) {
/* 132 */         targetPlayer = (EntityPlayer)((IEntitySinglePassenger)target).getRiddenByEntity();
/*     */       } 
/*     */       
/* 135 */       if (target instanceof MCH_EntityAircraft) { MCH_EntityAircraft ac = (MCH_EntityAircraft)target;
/* 136 */         if (ac.getRiddenByEntity() instanceof EntityPlayer) {
/* 137 */           targetPlayer = (EntityPlayer)ac.getRiddenByEntity();
/*     */         } }
/*     */ 
/*     */       
/* 141 */       return (attackPlayer == null || targetPlayer == null || attackPlayer.func_96122_a(targetPlayer));
/*     */     } 
/*     */     
/* 144 */     return true;
/*     */   }
/*     */   
/*     */   public static void jumpSpawnPoint(EntityPlayer player) {
/* 148 */     MCH_Lib.DbgLog(false, "JumpSpawnPoint", new Object[0]);
/* 149 */     CommandTeleport cmd = new CommandTeleport();
/* 150 */     if (cmd.func_184882_a(MCH_Utils.getServer(), (ICommandSender)player)) {
/* 151 */       MinecraftServer minecraftServer = MCH_Utils.getServer();
/*     */       
/* 153 */       for (String playerName : minecraftServer.func_184103_al().func_72369_d()) {
/*     */         try {
/* 155 */           EntityPlayerMP jumpPlayer = CommandTeleport.func_184888_a(minecraftServer, (ICommandSender)player, playerName);
/* 156 */           BlockPos cc = null;
/* 157 */           if (jumpPlayer.field_71093_bK == player.field_71093_bK) {
/* 158 */             cc = jumpPlayer.getBedLocation(jumpPlayer.field_71093_bK);
/* 159 */             cc = EntityPlayer.func_180467_a((World)minecraftServer.func_71218_a(jumpPlayer.field_71093_bK), cc, true);
/*     */             
/* 161 */             if (cc == null) {
/* 162 */               cc = jumpPlayer.field_70170_p.field_73011_w.getRandomizedSpawnPoint();
/*     */             }
/*     */           } 
/*     */           
/* 166 */           if (cc != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 171 */             String[] cmdStr = { playerName, String.format("%.1f", new Object[] { Double.valueOf(cc.func_177958_n() + 0.5D) }), String.format("%.1f", new Object[] { Double.valueOf(cc.func_177956_o() + 0.1D) }), String.format("%.1f", new Object[] { Double.valueOf(cc.func_177952_p() + 0.5D) }) };
/*     */             
/* 173 */             cmd.func_184881_a(minecraftServer, (ICommandSender)player, cmdStr);
/*     */           } 
/* 175 */         } catch (CommandException var10) {
/* 176 */           var10.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void shuffleTeam(EntityPlayer player) {
/* 183 */     Collection<ScorePlayerTeam> teams = player.field_70170_p.func_96441_U().func_96525_g();
/* 184 */     int teamNum = teams.size();
/* 185 */     MCH_Lib.DbgLog(false, "ShuffleTeam:%d teams ----------", new Object[] { Integer.valueOf(teamNum) });
/* 186 */     if (teamNum > 0) {
/* 187 */       CommandScoreboard cmd = new CommandScoreboard();
/* 188 */       if (cmd.func_184882_a(MCH_Utils.getServer(), (ICommandSender)player)) {
/* 189 */         List<String> list = Arrays.asList(MCH_Utils.getServer().func_184103_al().func_72369_d());
/* 190 */         Collections.shuffle(list);
/* 191 */         ArrayList<String> listTeam = new ArrayList<>();
/*     */         
/* 193 */         for (ScorePlayerTeam o : teams) {
/* 194 */           listTeam.add(o.func_96661_b());
/*     */         }
/*     */         
/* 197 */         Collections.shuffle(listTeam);
/* 198 */         int i = 0;
/*     */         
/* 200 */         for (int j = 0; i < list.size(); i++) {
/* 201 */           listTeam.set(j, (String)listTeam.get(j) + " " + (String)list.get(i));
/* 202 */           if (++j >= teamNum) {
/* 203 */             j = 0;
/*     */           }
/*     */         } 
/*     */         
/* 207 */         for (String s : listTeam) {
/* 208 */           String exe_cmd = "teams join " + s;
/* 209 */           String[] process_cmd = exe_cmd.split(" ");
/* 210 */           if (process_cmd.length > 3) {
/* 211 */             MCH_Lib.DbgLog(false, "ShuffleTeam:" + exe_cmd, new Object[0]);
/*     */             
/*     */             try {
/* 214 */               cmd.func_184881_a(MCH_Utils.getServer(), (ICommandSender)player, process_cmd);
/* 215 */             } catch (CommandException var11) {
/* 216 */               var11.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
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
/*     */   public static boolean spotEntity(EntityLivingBase player, @Nullable MCH_EntityAircraft ac, double posX, double posY, double posZ, int targetFilter, float spotLength, int markTime, float angle) {
/* 235 */     boolean ret = false;
/* 236 */     if (!player.field_70170_p.field_72995_K) {
/* 237 */       float acRoll = 0.0F;
/* 238 */       if (ac != null) {
/* 239 */         acRoll = ac.getRotRoll();
/*     */       }
/*     */       
/* 242 */       Vec3d vv = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -player.field_70177_z, -player.field_70125_A, -acRoll);
/* 243 */       double tx = vv.field_72450_a;
/* 244 */       double tz = vv.field_72449_c;
/*     */       
/* 246 */       List<Entity> list = player.field_70170_p.func_72839_b((Entity)player, player.func_174813_aQ().func_72314_b(spotLength, spotLength, spotLength));
/* 247 */       List<Integer> entityList = new ArrayList<>();
/* 248 */       Vec3d pos = new Vec3d(posX, posY, posZ);
/*     */       
/* 250 */       for (Entity entity : list) {
/* 251 */         if (canSpotEntityWithFilter(targetFilter, entity)) {
/* 252 */           MCH_TargetType stopType = canSpotEntity((Entity)player, posX, posY, posZ, entity, true);
/* 253 */           if (stopType != MCH_TargetType.NONE && stopType != MCH_TargetType.SAME_TEAM_PLAYER) {
/* 254 */             double dist = entity.func_70092_e(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c);
/* 255 */             if (dist > 1.0D && dist < (spotLength * spotLength)) {
/* 256 */               double cx = entity.field_70165_t - pos.field_72450_a;
/* 257 */               double cy = entity.field_70163_u - pos.field_72448_b;
/* 258 */               double cz = entity.field_70161_v - pos.field_72449_c;
/* 259 */               double h = MCH_Lib.getPosAngle(tx, tz, cx, cz);
/* 260 */               double v = Math.atan2(cy, Math.sqrt(cx * cx + cz * cz)) * 180.0D / Math.PI;
/* 261 */               v = Math.abs(v + player.field_70125_A);
/* 262 */               if (h < (angle * 2.0F) && v < (angle * 2.0F)) {
/* 263 */                 entityList.add(Integer.valueOf(entity.func_145782_y()));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 270 */       if (!entityList.isEmpty()) {
/* 271 */         int[] entityId = new int[entityList.size()];
/*     */         
/* 273 */         for (int ix = 0; ix < entityId.length; ix++) {
/* 274 */           entityId[ix] = ((Integer)entityList.get(ix)).intValue();
/*     */         }
/*     */         
/* 277 */         sendSpotedEntityListToSameTeam(player, markTime, entityId);
/* 278 */         ret = true;
/*     */       } else {
/* 280 */         ret = false;
/*     */       } 
/*     */     } 
/*     */     
/* 284 */     return ret;
/*     */   }
/*     */   
/*     */   public static void sendSpotedEntityListToSameTeam(EntityLivingBase player, int count, int[] entityId) {
/* 288 */     PlayerList svCnf = MCH_Utils.getServer().func_184103_al();
/*     */     
/* 290 */     for (EntityPlayer notifyPlayer : svCnf.func_181057_v()) {
/* 291 */       if (player == notifyPlayer || player.func_184191_r((Entity)notifyPlayer)) {
/* 292 */         MCH_PacketNotifySpotedEntity.send(notifyPlayer, count, entityId);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean markPoint(EntityPlayer player, double posX, double posY, double posZ) {
/* 298 */     Vec3d vs = new Vec3d(posX, posY, posZ);
/* 299 */     Vec3d ve = MCH_Lib.Rot2Vec3(player.field_70177_z, player.field_70125_A);
/* 300 */     ve = vs.func_72441_c(ve.field_72450_a * 300.0D, ve.field_72448_b * 300.0D, ve.field_72449_c * 300.0D);
/* 301 */     RayTraceResult mop = player.field_70170_p.func_72901_a(vs, ve, true);
/* 302 */     if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 303 */       sendMarkPointToSameTeam(player, mop.func_178782_a().func_177958_n(), mop.func_178782_a().func_177956_o() + 2, mop.func_178782_a().func_177952_p());
/* 304 */       return true;
/*     */     } 
/* 306 */     sendMarkPointToSameTeam(player, 0, 1000, 0);
/* 307 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendMarkPointToSameTeam(EntityPlayer player, int x, int y, int z) {
/* 312 */     PlayerList svCnf = MCH_Utils.getServer().func_184103_al();
/*     */     
/* 314 */     for (EntityPlayer notifyPlayer : svCnf.func_181057_v()) {
/* 315 */       if (player == notifyPlayer || player.func_184191_r((Entity)notifyPlayer))
/* 316 */         MCH_PacketNotifyMarkPoint.send(notifyPlayer, x, y, z); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_Multiplay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */