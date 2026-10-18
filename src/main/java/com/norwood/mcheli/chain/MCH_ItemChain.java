/*     */ package com.norwood.mcheli.chain;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_Item;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ItemChain
/*     */   extends W_Item
/*     */ {
/*     */   public MCH_ItemChain(int par1) {
/*  26 */     super(par1);
/*  27 */     setMaxStackSize(1);
/*     */   }
/*     */   
/*     */   public static void interactEntity(ItemStack item, @Nullable Entity entity, EntityPlayer player, World world) {
/*  31 */     if (!world.isRemote && entity != null && !entity.isDead) {
/*  32 */       if (entity instanceof net.minecraft.entity.item.EntityItem) {
/*     */         return;
/*     */       }
/*     */       
/*  36 */       if (entity instanceof MCH_EntityChain) {
/*     */         return;
/*     */       }
/*     */       
/*  40 */       if (entity instanceof com.norwood.mcheli.aircraft.MCH_EntityHitBox) {
/*     */         return;
/*     */       }
/*     */       
/*  44 */       if (entity instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat) {
/*     */         return;
/*     */       }
/*     */       
/*  48 */       if (entity instanceof com.norwood.mcheli.uav.MCH_EntityUavStation) {
/*     */         return;
/*     */       }
/*     */       
/*  52 */       if (entity instanceof com.norwood.mcheli.parachute.MCH_EntityParachute) {
/*     */         return;
/*     */       }
/*     */       
/*  56 */       if (W_Lib.isEntityLivingBase(entity)) {
/*     */         return;
/*     */       }
/*     */       
/*  60 */       if (MCH_Config.FixVehicleAtPlacedPoint.prmBool && entity instanceof com.norwood.mcheli.vehicle.MCH_EntityVehicle) {
/*     */         return;
/*     */       }
/*     */       
/*  64 */       MCH_EntityChain towingChain = getTowedEntityChain(entity);
/*  65 */       if (towingChain != null) {
/*  66 */         towingChain.setDead();
/*     */         
/*     */         return;
/*     */       } 
/*  70 */       Entity entityTowed = getTowedEntity(item, world);
/*  71 */       if (entityTowed == null) {
/*  72 */         playConnectTowedEntity(entity);
/*  73 */         setTowedEntity(item, entity);
/*     */       } else {
/*  75 */         if (W_Entity.isEqual(entityTowed, entity)) {
/*     */           return;
/*     */         }
/*     */         
/*  79 */         double diff = entity.getDistance(entityTowed);
/*  80 */         if (diff < 2.0D || diff > 16.0D) {
/*     */           return;
/*     */         }
/*     */         
/*  84 */         MCH_EntityChain chain = new MCH_EntityChain(world, (entityTowed.posX + entity.posX) / 2.0D, (entityTowed.posY + entity.posY) / 2.0D, (entityTowed.posZ + entity.posZ) / 2.0D);
/*     */ 
/*     */         
/*  87 */         chain.setChainLength((int)diff);
/*  88 */         chain.setTowEntity(entityTowed, entity);
/*  89 */         chain.prevPosX = chain.posX;
/*  90 */         chain.prevPosY = chain.posY;
/*  91 */         chain.prevPosZ = chain.posZ;
/*  92 */         world.spawnEntity((Entity)chain);
/*  93 */         playConnectTowingEntity(entity);
/*  94 */         setTowedEntity(item, (Entity)null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void playConnectTowingEntity(Entity e) {
/* 100 */     W_WorldFunc.MOD_playSoundEffect(e.world, e.posX, e.posY, e.posZ, "chain_ct", 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public static void playConnectTowedEntity(Entity e) {
/* 104 */     W_WorldFunc.MOD_playSoundEffect(e.world, e.posX, e.posY, e.posZ, "chain", 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static MCH_EntityChain getTowedEntityChain(Entity entity) {
/* 109 */     List<MCH_EntityChain> list = entity.world.getEntitiesWithinAABB(MCH_EntityChain.class, entity.getEntityBoundingBox().grow(25.0D, 25.0D, 25.0D));
/* 110 */     return null;
/*     */   }
/*     */   
/*     */   public static void setTowedEntity(ItemStack item, @Nullable Entity entity) {
/* 114 */     NBTTagCompound nbt = item.getTagCompound();
/* 115 */     if (nbt == null) {
/* 116 */       nbt = new NBTTagCompound();
/* 117 */       item.setTagCompound(nbt);
/*     */     } 
/*     */     
/* 120 */     if (entity != null && !entity.isDead) {
/* 121 */       nbt.setInteger("TowedEntityId", W_Entity.getEntityId(entity));
/* 122 */       nbt.setString("TowedEntityUUID", entity.getPersistentID().toString());
/*     */     } else {
/* 124 */       nbt.setInteger("TowedEntityId", 0);
/* 125 */       nbt.setString("TowedEntityUUID", "");
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Entity getTowedEntity(ItemStack item, World world) {
/* 131 */     NBTTagCompound nbt = item.getTagCompound();
/* 132 */     if (nbt == null) {
/* 133 */       nbt = new NBTTagCompound();
/* 134 */       item.setTagCompound(nbt);
/* 135 */     } else if (nbt.hasKey("TowedEntityId") && nbt.hasKey("TowedEntityUUID")) {
/* 136 */       int id = nbt.getInteger("TowedEntityId");
/* 137 */       String uuid = nbt.getString("TowedEntityUUID");
/* 138 */       Entity entity = world.getEntityByID(id);
/* 139 */       if (entity != null && !entity.isDead && uuid.compareTo(entity.getPersistentID().toString()) == 0) {
/* 140 */         return entity;
/*     */       }
/*     */     } 
/*     */     
/* 144 */     return null;
/*     */   }
/*     */   
/*     */   public void onCreated(@NotNull ItemStack par1ItemStack, @NotNull World par2World, @NotNull EntityPlayer par3EntityPlayer) {}
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\chain\MCH_ItemChain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */