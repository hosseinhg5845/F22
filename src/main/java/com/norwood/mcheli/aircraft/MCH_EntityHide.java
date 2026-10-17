/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.norwood.mcheli.helper.entity.IEntitySinglePassenger;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityHide
/*     */   extends W_Entity
/*     */   implements IEntitySinglePassenger
/*     */ {
/*  41 */   private static final DataParameter<Integer> ROPE_INDEX = EntityDataManager.func_187226_a(MCH_EntityHide.class, DataSerializers.field_187192_b);
/*  42 */   private static final DataParameter<Integer> AC_ID = EntityDataManager.func_187226_a(MCH_EntityHide.class, DataSerializers.field_187192_b);
/*     */   private MCH_EntityAircraft ac;
/*     */   private Entity user;
/*     */   private int paraPosRotInc;
/*     */   private double paraX;
/*     */   private double paraY;
/*     */   private double paraZ;
/*     */   private double paraYaw;
/*     */   private double paraPitch;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityY;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityZ;
/*     */   
/*     */   public MCH_EntityHide(World par1World) {
/*  59 */     super(par1World);
/*  60 */     func_70105_a(1.0F, 1.0F);
/*  61 */     this.field_70156_m = true;
/*  62 */     this.user = null;
/*  63 */     this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
/*     */   }
/*     */   
/*     */   public MCH_EntityHide(World par1World, double x, double y, double z) {
/*  67 */     this(par1World);
/*  68 */     this.field_70165_t = x;
/*  69 */     this.field_70163_u = y;
/*  70 */     this.field_70161_v = z;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  75 */     super.func_70088_a();
/*  76 */     createRopeIndex(-1);
/*  77 */     this.field_70180_af.func_187214_a(AC_ID, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public void setParent(MCH_EntityAircraft ac, Entity user, int ropeIdx) {
/*  81 */     this.ac = ac;
/*  82 */     setRopeIndex(ropeIdx);
/*  83 */     this.user = user;
/*     */   }
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/*  91 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/*  95 */     return func_174813_aQ();
/*     */   }
/*     */   
/*     */   public boolean func_70104_M() {
/*  99 */     return true;
/*     */   }
/*     */   
/*     */   public double func_70042_X() {
/* 103 */     return this.field_70131_O * 0.0D - 0.3D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   public boolean func_70067_L() {
/* 112 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(@NotNull NBTTagCompound nbt) {}
/*     */ 
/*     */   
/*     */   protected void func_70037_a(@NotNull NBTTagCompound nbt) {}
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 123 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean func_184230_a(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 127 */     return false;
/*     */   }
/*     */   
/*     */   public void createRopeIndex(int defaultValue) {
/* 131 */     this.field_70180_af.func_187214_a(ROPE_INDEX, Integer.valueOf(defaultValue));
/*     */   }
/*     */   
/*     */   public int getRopeIndex() {
/* 135 */     return ((Integer)this.field_70180_af.func_187225_a(ROPE_INDEX)).intValue();
/*     */   }
/*     */   
/*     */   public void setRopeIndex(int value) {
/* 139 */     this.field_70180_af.func_187227_b(ROPE_INDEX, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_180426_a(double par1, double par3, double par5, float par7, float par8, int par9, boolean teleport) {
/* 144 */     this.paraPosRotInc = par9 + 10;
/* 145 */     this.paraX = par1;
/* 146 */     this.paraY = par3;
/* 147 */     this.paraZ = par5;
/* 148 */     this.paraYaw = par7;
/* 149 */     this.paraPitch = par8;
/* 150 */     this.field_70159_w = this.velocityX;
/* 151 */     this.field_70181_x = this.velocityY;
/* 152 */     this.field_70179_y = this.velocityZ;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double par1, double par3, double par5) {
/* 157 */     this.velocityX = this.field_70159_w = par1;
/* 158 */     this.velocityY = this.field_70181_x = par3;
/* 159 */     this.velocityZ = this.field_70179_y = par5;
/*     */   }
/*     */   
/*     */   public void func_70106_y() {
/* 163 */     super.func_70106_y();
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/* 167 */     super.func_70071_h_();
/* 168 */     if (this.user != null && !this.field_70170_p.field_72995_K) {
/* 169 */       if (this.ac != null) {
/* 170 */         this.field_70180_af.func_187227_b(AC_ID, Integer.valueOf(this.ac.func_145782_y()));
/*     */       }
/*     */       
/* 173 */       this.user.func_184220_m((Entity)this);
/* 174 */       this.user = null;
/*     */     } 
/*     */     
/* 177 */     if (this.ac == null && this.field_70170_p.field_72995_K) {
/* 178 */       int id = ((Integer)this.field_70180_af.func_187225_a(AC_ID)).intValue();
/* 179 */       if (id > 0) {
/* 180 */         Entity entity = this.field_70170_p.func_73045_a(id);
/* 181 */         if (entity instanceof MCH_EntityAircraft) {
/* 182 */           this.ac = (MCH_EntityAircraft)entity;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     this.field_70169_q = this.field_70165_t;
/* 188 */     this.field_70167_r = this.field_70163_u;
/* 189 */     this.field_70166_s = this.field_70161_v;
/* 190 */     this.field_70143_R = 0.0F;
/* 191 */     Entity riddenByEntity = getRiddenByEntity();
/* 192 */     if (riddenByEntity != null) {
/* 193 */       riddenByEntity.field_70143_R = 0.0F;
/*     */     }
/*     */     
/* 196 */     if (this.ac != null) {
/* 197 */       if (!this.ac.isRepelling()) {
/* 198 */         func_70106_y();
/*     */       }
/*     */       
/* 201 */       int id = getRopeIndex();
/* 202 */       if (id >= 0) {
/* 203 */         Vec3d v = this.ac.getRopePos(id);
/* 204 */         this.field_70165_t = v.field_72450_a;
/* 205 */         this.field_70161_v = v.field_72449_c;
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 210 */     if (this.field_70170_p.field_72995_K) {
/* 211 */       onUpdateClient();
/*     */     } else {
/* 213 */       onUpdateServer();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdateClient() {
/* 218 */     if (this.paraPosRotInc > 0) {
/* 219 */       double x = this.field_70165_t + (this.paraX - this.field_70165_t) / this.paraPosRotInc;
/* 220 */       double y = this.field_70163_u + (this.paraY - this.field_70163_u) / this.paraPosRotInc;
/* 221 */       double z = this.field_70161_v + (this.paraZ - this.field_70161_v) / this.paraPosRotInc;
/* 222 */       double yaw = MathHelper.func_76138_g(this.paraYaw - this.field_70177_z);
/* 223 */       this.field_70177_z = (float)(this.field_70177_z + yaw / this.paraPosRotInc);
/* 224 */       this.field_70125_A = (float)(this.field_70125_A + (this.paraPitch - this.field_70125_A) / this.paraPosRotInc);
/* 225 */       this.paraPosRotInc--;
/* 226 */       func_70107_b(x, y, z);
/* 227 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/* 228 */       Entity riddenByEntity = getRiddenByEntity();
/* 229 */       if (riddenByEntity != null) {
/* 230 */         func_70101_b(riddenByEntity.field_70126_B, this.field_70125_A);
/*     */       }
/*     */     } else {
/* 233 */       func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 234 */       this.field_70159_w *= 0.99D;
/* 235 */       this.field_70181_x *= 0.95D;
/* 236 */       this.field_70179_y *= 0.99D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdateServer() {
/* 241 */     this.field_70181_x -= this.field_70122_E ? 0.01D : 0.03D;
/* 242 */     if (this.field_70122_E) {
/* 243 */       onGroundAndDead();
/*     */     } else {
/* 245 */       func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 246 */       this.field_70181_x *= 0.9D;
/* 247 */       this.field_70159_w *= 0.95D;
/* 248 */       this.field_70179_y *= 0.95D;
/* 249 */       int id = getRopeIndex();
/* 250 */       if (this.ac != null && id >= 0) {
/* 251 */         Vec3d v = this.ac.getRopePos(id);
/* 252 */         if (Math.abs(this.field_70163_u - v.field_72448_b) > (Math.abs(this.ac.ropesLength) + 5.0F)) {
/* 253 */           onGroundAndDead();
/*     */         }
/*     */       } 
/*     */       
/* 257 */       Entity riddenByEntity = getRiddenByEntity();
/* 258 */       if (riddenByEntity != null && riddenByEntity.field_70128_L) {
/* 259 */         func_70106_y();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean getCollisionBoxes(@Nullable Entity entityIn, AxisAlignedBB aabb, List<AxisAlignedBB> outList) {
/* 265 */     int i = MathHelper.func_76128_c(aabb.field_72340_a) - 1;
/* 266 */     int j = MathHelper.func_76143_f(aabb.field_72336_d) + 1;
/* 267 */     int k = MathHelper.func_76128_c(aabb.field_72338_b) - 1;
/* 268 */     int l = MathHelper.func_76143_f(aabb.field_72337_e) + 1;
/* 269 */     int i1 = MathHelper.func_76128_c(aabb.field_72339_c) - 1;
/* 270 */     int j1 = MathHelper.func_76143_f(aabb.field_72334_f) + 1;
/* 271 */     WorldBorder worldborder = this.field_70170_p.func_175723_af();
/* 272 */     boolean flag = (entityIn != null && entityIn.func_174832_aS());
/* 273 */     boolean flag1 = (entityIn != null && this.field_70170_p.func_191503_g(entityIn));
/* 274 */     IBlockState iblockstate = Blocks.field_150348_b.func_176223_P();
/* 275 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185346_s();
/*     */     
/*     */     try {
/* 278 */       for (int k1 = i; k1 < j; k1++) {
/* 279 */         for (int l1 = i1; l1 < j1; l1++) {
/* 280 */           boolean flag2 = (k1 == i || k1 == j - 1);
/* 281 */           boolean flag3 = (l1 == i1 || l1 == j1 - 1);
/* 282 */           if ((!flag2 || !flag3) && this.field_70170_p.func_175667_e((BlockPos)blockpos$pooledmutableblockpos.func_181079_c(k1, 64, l1))) {
/* 283 */             for (int i2 = k; i2 < l; i2++) {
/* 284 */               if ((!flag2 && !flag3) || i2 != l - 1) {
/* 285 */                 IBlockState iblockstate1; if (entityIn != null && flag == flag1) {
/* 286 */                   entityIn.func_174821_h(!flag1);
/*     */                 }
/*     */                 
/* 289 */                 blockpos$pooledmutableblockpos.func_181079_c(k1, i2, l1);
/*     */                 
/* 291 */                 if (!worldborder.func_177746_a((BlockPos)blockpos$pooledmutableblockpos) && flag1) {
/* 292 */                   iblockstate1 = iblockstate;
/*     */                 } else {
/* 294 */                   iblockstate1 = this.field_70170_p.func_180495_p((BlockPos)blockpos$pooledmutableblockpos);
/*     */                 } 
/*     */                 
/* 297 */                 iblockstate1.func_185908_a(this.field_70170_p, (BlockPos)blockpos$pooledmutableblockpos, aabb, outList, entityIn, false);
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 304 */       blockpos$pooledmutableblockpos.func_185344_t();
/*     */     } 
/*     */     
/* 307 */     return !outList.isEmpty();
/*     */   }
/*     */   
/*     */   public List<AxisAlignedBB> getCollidingBoundingBoxes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB) {
/* 311 */     List<AxisAlignedBB> list = new ArrayList<>();
/* 312 */     getCollisionBoxes(par1Entity, par2AxisAlignedBB, list);
/* 313 */     if (par1Entity != null) {
/* 314 */       List<Entity> list1 = this.field_70170_p.func_72839_b(par1Entity, par2AxisAlignedBB.func_186662_g(0.25D));
/*     */       
/* 316 */       for (Entity entity : list1) {
/* 317 */         if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_EntitySeat) && !(entity instanceof MCH_EntityHitBox)) {
/* 318 */           AxisAlignedBB axisalignedbb = entity.func_70046_E();
/* 319 */           if (axisalignedbb != null && axisalignedbb.func_72326_a(par2AxisAlignedBB)) {
/* 320 */             list.add(axisalignedbb);
/*     */           }
/*     */           
/* 323 */           axisalignedbb = par1Entity.func_70114_g(entity);
/* 324 */           if (axisalignedbb != null && axisalignedbb.func_72326_a(par2AxisAlignedBB)) {
/* 325 */             list.add(axisalignedbb);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 331 */     return list;
/*     */   }
/*     */   
/*     */   public void func_70091_d(@NotNull MoverType type, double x, double y, double z) {
/* 335 */     this.field_70170_p.field_72984_F.func_76320_a("move");
/* 336 */     double d2 = x;
/* 337 */     double d3 = y;
/* 338 */     double d4 = z;
/* 339 */     List<AxisAlignedBB> list1 = getCollidingBoundingBoxes((Entity)this, func_174813_aQ().func_72321_a(x, y, z));
/* 340 */     AxisAlignedBB axisalignedbb = func_174813_aQ();
/* 341 */     if (y != 0.0D) {
/* 342 */       int k = 0;
/*     */       
/* 344 */       for (int l = list1.size(); k < l; k++) {
/* 345 */         y = ((AxisAlignedBB)list1.get(k)).func_72323_b(func_174813_aQ(), y);
/*     */       }
/*     */       
/* 348 */       func_174826_a(func_174813_aQ().func_72317_d(0.0D, y, 0.0D));
/*     */     } 
/*     */     
/* 351 */     boolean flag = (this.field_70122_E || (d3 != y && d3 < 0.0D));
/* 352 */     if (x != 0.0D) {
/* 353 */       int j5 = 0;
/*     */       
/* 355 */       for (int l5 = list1.size(); j5 < l5; j5++) {
/* 356 */         x = ((AxisAlignedBB)list1.get(j5)).func_72316_a(func_174813_aQ(), x);
/*     */       }
/*     */       
/* 359 */       if (x != 0.0D) {
/* 360 */         func_174826_a(func_174813_aQ().func_72317_d(x, 0.0D, 0.0D));
/*     */       }
/*     */     } 
/*     */     
/* 364 */     if (z != 0.0D) {
/* 365 */       int k5 = 0;
/*     */       
/* 367 */       for (int i6 = list1.size(); k5 < i6; k5++) {
/* 368 */         z = ((AxisAlignedBB)list1.get(k5)).func_72322_c(func_174813_aQ(), z);
/*     */       }
/*     */       
/* 371 */       if (z != 0.0D) {
/* 372 */         func_174826_a(func_174813_aQ().func_72317_d(0.0D, 0.0D, z));
/*     */       }
/*     */     } 
/*     */     
/* 376 */     if (this.field_70138_W > 0.0F && flag && (d2 != x || d4 != z)) {
/* 377 */       double d14 = x;
/* 378 */       double d6 = y;
/* 379 */       double d7 = z;
/* 380 */       y = this.field_70138_W;
/* 381 */       AxisAlignedBB axisalignedbb1 = func_174813_aQ();
/* 382 */       func_174826_a(axisalignedbb);
/* 383 */       List<AxisAlignedBB> list = getCollidingBoundingBoxes((Entity)this, func_174813_aQ().func_72321_a(d2, y, d4));
/* 384 */       AxisAlignedBB axisalignedbb2 = func_174813_aQ();
/* 385 */       AxisAlignedBB axisalignedbb3 = axisalignedbb2.func_72321_a(d2, 0.0D, d4);
/* 386 */       double d8 = y;
/*     */       
/* 388 */       for (AxisAlignedBB axisAlignedBB1 : list) {
/* 389 */         d8 = axisAlignedBB1.func_72323_b(axisalignedbb3, d8);
/*     */       }
/*     */       
/* 392 */       axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, d8, 0.0D);
/* 393 */       double d18 = d2;
/*     */       
/* 395 */       for (AxisAlignedBB element : list) {
/* 396 */         d18 = element.func_72316_a(axisalignedbb2, d18);
/*     */       }
/*     */       
/* 399 */       axisalignedbb2 = axisalignedbb2.func_72317_d(d18, 0.0D, 0.0D);
/* 400 */       double d19 = d4;
/*     */       
/* 402 */       for (AxisAlignedBB item : list) {
/* 403 */         d19 = item.func_72322_c(axisalignedbb2, d19);
/*     */       }
/*     */       
/* 406 */       axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, 0.0D, d19);
/* 407 */       AxisAlignedBB axisalignedbb4 = func_174813_aQ();
/* 408 */       double d20 = y;
/*     */       
/* 410 */       for (AxisAlignedBB value : list) {
/* 411 */         d20 = value.func_72323_b(axisalignedbb4, d20);
/*     */       }
/*     */       
/* 414 */       axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, d20, 0.0D);
/* 415 */       double d21 = d2;
/*     */       
/* 417 */       for (AxisAlignedBB bb : list) {
/* 418 */         d21 = bb.func_72316_a(axisalignedbb4, d21);
/*     */       }
/*     */       
/* 421 */       axisalignedbb4 = axisalignedbb4.func_72317_d(d21, 0.0D, 0.0D);
/* 422 */       double d22 = d4;
/*     */       
/* 424 */       for (AxisAlignedBB alignedBB : list) {
/* 425 */         d22 = alignedBB.func_72322_c(axisalignedbb4, d22);
/*     */       }
/*     */       
/* 428 */       axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, 0.0D, d22);
/* 429 */       double d23 = d18 * d18 + d19 * d19;
/* 430 */       double d9 = d21 * d21 + d22 * d22;
/* 431 */       if (d23 > d9) {
/* 432 */         x = d18;
/* 433 */         z = d19;
/* 434 */         y = -d8;
/* 435 */         func_174826_a(axisalignedbb2);
/*     */       } else {
/* 437 */         x = d21;
/* 438 */         z = d22;
/* 439 */         y = -d20;
/* 440 */         func_174826_a(axisalignedbb4);
/*     */       } 
/*     */       
/* 443 */       for (AxisAlignedBB axisAlignedBB : list) {
/* 444 */         y = axisAlignedBB.func_72323_b(func_174813_aQ(), y);
/*     */       }
/*     */       
/* 447 */       func_174826_a(func_174813_aQ().func_72317_d(0.0D, y, 0.0D));
/* 448 */       if (d14 * d14 + d7 * d7 >= x * x + z * z) {
/* 449 */         x = d14;
/* 450 */         y = d6;
/* 451 */         z = d7;
/* 452 */         func_174826_a(axisalignedbb1);
/*     */       } 
/*     */     } 
/*     */     
/* 456 */     this.field_70170_p.field_72984_F.func_76319_b();
/* 457 */     this.field_70170_p.field_72984_F.func_76320_a("rest");
/* 458 */     func_174829_m();
/* 459 */     this.field_70123_F = (d2 != x || d4 != z);
/* 460 */     this.field_70124_G = (d3 != y);
/* 461 */     this.field_70122_E = (this.field_70124_G && d3 < 0.0D);
/* 462 */     this.field_70132_H = (this.field_70123_F || this.field_70124_G);
/* 463 */     int j6 = MathHelper.func_76128_c(this.field_70165_t);
/* 464 */     int i1 = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
/* 465 */     int k6 = MathHelper.func_76128_c(this.field_70161_v);
/* 466 */     BlockPos blockpos = new BlockPos(j6, i1, k6);
/* 467 */     IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
/* 468 */     if (iblockstate.func_185904_a() == Material.field_151579_a) {
/* 469 */       BlockPos blockpos1 = blockpos.func_177977_b();
/* 470 */       IBlockState iblockstate1 = this.field_70170_p.func_180495_p(blockpos1);
/* 471 */       Block block1 = iblockstate1.func_177230_c();
/* 472 */       if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/* 473 */         iblockstate = iblockstate1;
/* 474 */         blockpos = blockpos1;
/*     */       } 
/*     */     } 
/*     */     
/* 478 */     func_184231_a(y, this.field_70122_E, iblockstate, blockpos);
/* 479 */     if (d2 != x) {
/* 480 */       this.field_70159_w = 0.0D;
/*     */     }
/*     */     
/* 483 */     if (d4 != z) {
/* 484 */       this.field_70179_y = 0.0D;
/*     */     }
/*     */     
/* 487 */     Block block = iblockstate.func_177230_c();
/* 488 */     if (d3 != y) {
/* 489 */       block.func_176216_a(this.field_70170_p, (Entity)this);
/*     */     }
/*     */     
/*     */     try {
/* 493 */       func_145775_I();
/* 494 */     } catch (Throwable var45) {
/* 495 */       CrashReport crashreport = CrashReport.func_85055_a(var45, "Checking entity block collision");
/* 496 */       CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
/* 497 */       func_85029_a(crashreportcategory);
/* 498 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */     
/* 501 */     this.field_70170_p.field_72984_F.func_76319_b();
/*     */   }
/*     */   
/*     */   public void onGroundAndDead() {
/* 505 */     this.field_70163_u += 0.5D;
/* 506 */     func_184232_k(getRiddenByEntity());
/* 507 */     func_70106_y();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 513 */     List<Entity> passengers = func_184188_bt();
/* 514 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_EntityHide.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */