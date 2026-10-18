/*     */ package com.norwood.mcheli.uav;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_Item;
/*     */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_ItemUavStation
/*     */   extends W_Item {
/*     */   public static final int UAV_STATION_KIND_NUM = 2;
/*     */   public final int UavStationKind;
/*     */   
/*     */   public MCH_ItemUavStation(int par1, int kind) {
/*  27 */     super(par1);
/*  28 */     this.maxStackSize = 1;
/*  29 */     this.UavStationKind = kind;
/*     */   }
/*     */   
/*     */   public MCH_EntityUavStation createUavStation(World world, double x, double y, double z, int kind) {
/*  33 */     MCH_EntityUavStation uavst = new MCH_EntityUavStation(world);
/*  34 */     uavst.setPosition(x, y, z);
/*  35 */     uavst.prevPosX = x;
/*  36 */     uavst.prevPosY = y;
/*  37 */     uavst.prevPosZ = z;
/*  38 */     uavst.setKind(kind);
/*  39 */     return uavst;
/*     */   }
/*     */   @NotNull
/*     */   public ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
/*  43 */     ItemStack itemstack = playerIn.getHeldItem(handIn);
/*  44 */     float f = 1.0F;
/*  45 */     float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * f;
/*  46 */     float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * f;
/*  47 */     double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * f;
/*  48 */     double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) * f + 1.62D;
/*  49 */     double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ) * f;
/*  50 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(worldIn, d0, d1, d2);
/*  51 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/*  52 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/*  53 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/*  54 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/*  55 */     float f7 = f4 * f5;
/*  56 */     float f8 = f3 * f5;
/*  57 */     double d3 = 5.0D;
/*  58 */     Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
/*  59 */     RayTraceResult movingobjectposition = W_WorldFunc.clip(worldIn, vec3, vec31, true);
/*  60 */     if (movingobjectposition == null) {
/*  61 */       return ActionResult.newResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*  63 */     Vec3d vec32 = playerIn.getLook(f);
/*  64 */     boolean flag = false;
/*  65 */     float f9 = 1.0F;
/*  66 */     List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)playerIn, playerIn
/*  67 */         .getEntityBoundingBox().expand(vec32.x * d3, vec32.y * d3, vec32.z * d3).grow(f9, f9, f9));
/*     */ 
/*     */     
/*  70 */     for (Entity entity : list) {
/*  71 */       if (entity.canBeCollidedWith()) {
/*  72 */         float f10 = entity.getCollisionBorderSize();
/*  73 */         AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(f10, f10, f10);
/*  74 */         if (axisalignedbb.contains(vec3)) {
/*  75 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     if (flag) {
/*  81 */       return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */     }
/*  83 */     if (W_MovingObjectPosition.isHitTypeTile(movingobjectposition)) {
/*  84 */       int ix = movingobjectposition.getBlockPos().getX();
/*  85 */       int j = movingobjectposition.getBlockPos().getY();
/*  86 */       int k = movingobjectposition.getBlockPos().getZ();
/*  87 */       MCH_EntityUavStation entityUavSt = createUavStation(worldIn, (ix + 0.5F), (j + 1.0F), (k + 0.5F), this.UavStationKind);
/*  88 */       int rot = (int)(MCH_Lib.getRotate360(playerIn.rotationYaw) + 45.0D);
/*  89 */       entityUavSt.rotationYaw = rot / 90.0F * 90.0F - 180.0F;
/*  90 */       entityUavSt.initUavPostion();
/*  91 */       if (!worldIn.getCollisionBoxes((Entity)entityUavSt, entityUavSt.getEntityBoundingBox().grow(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/*  92 */         return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */       }
/*     */       
/*  95 */       if (!worldIn.isRemote) {
/*  96 */         worldIn.spawnEntity((Entity)entityUavSt);
/*     */       }
/*     */       
/*  99 */       if (!playerIn.capabilities.isCreativeMode) {
/* 100 */         itemstack.shrink(1);
/*     */       }
/*     */     } 
/*     */     
/* 104 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mchel\\uav\MCH_ItemUavStation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */