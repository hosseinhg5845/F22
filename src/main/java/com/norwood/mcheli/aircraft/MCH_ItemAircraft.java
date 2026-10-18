/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.helper.MCH_CriteriaTriggers;
/*     */ import com.norwood.mcheli.wrapper.W_EntityPlayer;
/*     */ import com.norwood.mcheli.wrapper.W_Item;
/*     */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public abstract class MCH_ItemAircraft
/*     */   extends W_Item {
/*     */   public MCH_ItemAircraft(int i) {
/*  33 */     super(i);
/*     */   }
/*     */   private static final boolean isRegistedDispenseBehavior = false;
/*     */   
/*     */   public static void registerDispenseBehavior(Item item) {
/*  38 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, new MCH_ItemAircraftDispenseBehavior());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract MCH_AircraftInfo getAircraftInfo();
/*     */   
/*     */   @Nullable
/*     */   public abstract MCH_EntityAircraft createAircraft(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, ItemStack paramItemStack);
/*     */   
/*     */   public MCH_EntityAircraft onTileClick(ItemStack itemStack, World world, float rotationYaw, int x, int y, int z) {
/*  49 */     MCH_EntityAircraft ac = createAircraft(world, (x + 0.5F), (y + 1.0F), (z + 0.5F), itemStack);
/*  50 */     if (ac == null) {
/*  51 */       return null;
/*     */     }
/*  53 */     ac.initRotationYaw((((MathHelper.floor((rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90));
/*  54 */     return !world.getCollisionBoxes((Entity)ac, ac.getEntityBoundingBox().grow(-0.1D, -0.1D, -0.1D)).isEmpty() ? null : ac;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  59 */     MCH_AircraftInfo info = getAircraftInfo();
/*  60 */     return (info != null) ? (super.toString() + "(" + info.getDirectoryName() + ":" + info.name + ")") : (super.toString() + "(null)");
/*     */   }
/*     */   @NotNull
/*     */   public ActionResult<ItemStack> onItemRightClick(@NotNull World world, EntityPlayer player, @NotNull EnumHand handIn) {
/*  64 */     ItemStack itemstack = player.getHeldItem(handIn);
/*  65 */     float f = 1.0F;
/*  66 */     float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
/*  67 */     float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
/*  68 */     double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
/*  69 */     double d1 = player.prevPosY + (player.posY - player.prevPosY) * f + 1.62D;
/*  70 */     double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
/*  71 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(world, d0, d1, d2);
/*  72 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/*  73 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/*  74 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/*  75 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/*  76 */     float f7 = f4 * f5;
/*  77 */     float f8 = f3 * f5;
/*  78 */     double d3 = 5.0D;
/*  79 */     Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
/*  80 */     RayTraceResult mop = W_WorldFunc.clip(world, vec3, vec31, true);
/*  81 */     if (mop == null) {
/*  82 */       return ActionResult.newResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*  84 */     Vec3d playerLook = player.getLook(f);
/*  85 */     boolean flag = false;
/*  86 */     float f9 = 1.0F;
/*  87 */     List<Entity> list = world.getEntitiesWithinAABBExcludingEntity((Entity)player, player
/*  88 */         .getEntityBoundingBox().expand(playerLook.x * d3, playerLook.y * d3, playerLook.z * d3).grow(f9, f9, f9));
/*     */ 
/*     */     
/*  91 */     for (Entity entity : list) {
/*  92 */       if (entity.canBeCollidedWith()) {
/*  93 */         float f10 = entity.getCollisionBorderSize();
/*  94 */         AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(f10, f10, f10);
/*  95 */         if (axisalignedbb.contains(vec3)) {
/*  96 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     if (flag) {
/* 102 */       return ActionResult.newResult(EnumActionResult.PASS, itemstack);
/*     */     }
/* 104 */     if (W_MovingObjectPosition.isHitTypeTile(mop)) {
/* 105 */       if (MCH_Config.PlaceableOnSpongeOnly.prmBool) {
/* 106 */         MCH_AircraftInfo acInfo = getAircraftInfo();
/* 107 */         if (acInfo != null && acInfo.isFloat && !acInfo.canMoveOnGround) {
/* 108 */           if (world.getBlockState(mop.getBlockPos().down()).getBlock() != Blocks.SPONGE) {
/* 109 */             return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */           }
/*     */           
/* 112 */           for (int x = -1; x <= 1; x++) {
/* 113 */             for (int z = -1; z <= 1; z++) {
/* 114 */               if (world.getBlockState(mop.getBlockPos().add(x, 0, z)).getBlock() != Blocks.WATER) {
/* 115 */                 return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } else {
/* 120 */           Block block = world.getBlockState(mop.getBlockPos()).getBlock();
/* 121 */           if (!(block instanceof net.minecraft.block.BlockSponge)) {
/* 122 */             return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 127 */       spawnAircraft(itemstack, world, player, mop.getBlockPos());
/*     */     } 
/*     */     
/* 130 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityAircraft spawnAircraft(ItemStack itemStack, World world, EntityPlayer player, BlockPos blockpos) {
/* 136 */     MCH_EntityAircraft ac = onTileClick(itemStack, world, player.rotationYaw, blockpos
/* 137 */         .getX(), blockpos.getY(), blockpos.getZ());
/*     */     
/* 139 */     if (ac != null) {
/* 140 */       if (ac.getAcInfo() != null && (ac.getAcInfo()).creativeOnly && !player.capabilities.isCreativeMode) {
/* 141 */         return null;
/*     */       }
/*     */       
/* 144 */       if (ac.isUAV()) {
/* 145 */         if (world.isRemote) {
/* 146 */           if (ac.isSmallUAV()) {
/* 147 */             W_EntityPlayer.addChatMessage(player, "Please use the UAV station OR Portable Controller");
/*     */           } else {
/* 149 */             W_EntityPlayer.addChatMessage(player, "Please use the UAV station");
/*     */           } 
/*     */         }
/*     */         
/* 153 */         ac = null;
/*     */       } else {
/* 155 */         if (!world.isRemote) {
/* 156 */           ac.getAcDataFromItem(itemStack);
/* 157 */           world.spawnEntity((Entity)ac);
/* 158 */           MCH_CriteriaTriggers.PUT_AIRCRAFT.trigger((EntityPlayerMP)player);
/*     */         } 
/*     */         
/* 161 */         if (!player.capabilities.isCreativeMode) {
/* 162 */           itemStack.shrink(1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     return ac;
/*     */   }
/*     */   
/*     */   public void rideEntity(ItemStack item, Entity target, EntityPlayer player) {
/* 171 */     if (!MCH_Config.PlaceableOnSpongeOnly.prmBool && target instanceof net.minecraft.entity.item.EntityMinecartEmpty && target.getPassengers().isEmpty()) {
/* 172 */       BlockPos blockpos = new BlockPos((int)target.posX, (int)target.posY + 2, (int)target.posZ);
/* 173 */       MCH_EntityAircraft ac = spawnAircraft(item, player.world, player, blockpos);
/* 174 */       if (!player.world.isRemote && ac != null)
/* 175 */         ac.startRiding(target); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_ItemAircraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */