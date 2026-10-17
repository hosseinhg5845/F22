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
/*  28 */     this.field_77777_bU = 1;
/*  29 */     this.UavStationKind = kind;
/*     */   }
/*     */   
/*     */   public MCH_EntityUavStation createUavStation(World world, double x, double y, double z, int kind) {
/*  33 */     MCH_EntityUavStation uavst = new MCH_EntityUavStation(world);
/*  34 */     uavst.func_70107_b(x, y, z);
/*  35 */     uavst.field_70169_q = x;
/*  36 */     uavst.field_70167_r = y;
/*  37 */     uavst.field_70166_s = z;
/*  38 */     uavst.setKind(kind);
/*  39 */     return uavst;
/*     */   }
/*     */   @NotNull
/*     */   public ActionResult<ItemStack> func_77659_a(@NotNull World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
/*  43 */     ItemStack itemstack = playerIn.func_184586_b(handIn);
/*  44 */     float f = 1.0F;
/*  45 */     float f1 = playerIn.field_70127_C + (playerIn.field_70125_A - playerIn.field_70127_C) * f;
/*  46 */     float f2 = playerIn.field_70126_B + (playerIn.field_70177_z - playerIn.field_70126_B) * f;
/*  47 */     double d0 = playerIn.field_70169_q + (playerIn.field_70165_t - playerIn.field_70169_q) * f;
/*  48 */     double d1 = playerIn.field_70167_r + (playerIn.field_70163_u - playerIn.field_70167_r) * f + 1.62D;
/*  49 */     double d2 = playerIn.field_70166_s + (playerIn.field_70161_v - playerIn.field_70166_s) * f;
/*  50 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(worldIn, d0, d1, d2);
/*  51 */     float f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - 3.1415927F);
/*  52 */     float f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - 3.1415927F);
/*  53 */     float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
/*  54 */     float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
/*  55 */     float f7 = f4 * f5;
/*  56 */     float f8 = f3 * f5;
/*  57 */     double d3 = 5.0D;
/*  58 */     Vec3d vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
/*  59 */     RayTraceResult movingobjectposition = W_WorldFunc.clip(worldIn, vec3, vec31, true);
/*  60 */     if (movingobjectposition == null) {
/*  61 */       return ActionResult.newResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*  63 */     Vec3d vec32 = playerIn.func_70676_i(f);
/*  64 */     boolean flag = false;
/*  65 */     float f9 = 1.0F;
/*  66 */     List<Entity> list = worldIn.func_72839_b((Entity)playerIn, playerIn
/*  67 */         .func_174813_aQ().func_72321_a(vec32.field_72450_a * d3, vec32.field_72448_b * d3, vec32.field_72449_c * d3).func_72314_b(f9, f9, f9));
/*     */ 
/*     */     
/*  70 */     for (Entity entity : list) {
/*  71 */       if (entity.func_70067_L()) {
/*  72 */         float f10 = entity.func_70111_Y();
/*  73 */         AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_72314_b(f10, f10, f10);
/*  74 */         if (axisalignedbb.func_72318_a(vec3)) {
/*  75 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     if (flag) {
/*  81 */       return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */     }
/*  83 */     if (W_MovingObjectPosition.isHitTypeTile(movingobjectposition)) {
/*  84 */       int ix = movingobjectposition.func_178782_a().func_177958_n();
/*  85 */       int j = movingobjectposition.func_178782_a().func_177956_o();
/*  86 */       int k = movingobjectposition.func_178782_a().func_177952_p();
/*  87 */       MCH_EntityUavStation entityUavSt = createUavStation(worldIn, (ix + 0.5F), (j + 1.0F), (k + 0.5F), this.UavStationKind);
/*  88 */       int rot = (int)(MCH_Lib.getRotate360(playerIn.field_70177_z) + 45.0D);
/*  89 */       entityUavSt.field_70177_z = rot / 90.0F * 90.0F - 180.0F;
/*  90 */       entityUavSt.initUavPostion();
/*  91 */       if (!worldIn.func_184144_a((Entity)entityUavSt, entityUavSt.func_174813_aQ().func_72314_b(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/*  92 */         return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */       }
/*     */       
/*  95 */       if (!worldIn.field_72995_K) {
/*  96 */         worldIn.func_72838_d((Entity)entityUavSt);
/*     */       }
/*     */       
/*  99 */       if (!playerIn.field_71075_bZ.field_75098_d) {
/* 100 */         itemstack.func_190918_g(1);
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