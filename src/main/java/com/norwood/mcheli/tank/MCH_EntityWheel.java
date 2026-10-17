/*     */ package com.norwood.mcheli.tank;
/*     */ 
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityWheel
/*     */   extends W_Entity
/*     */ {
/*     */   public Vec3d pos;
/*     */   boolean isPlus;
/*     */   private MCH_EntityAircraft parents;
/*     */   
/*     */   public MCH_EntityWheel(World w) {
/*  39 */     super(w);
/*  40 */     func_70105_a(1.0F, 1.0F);
/*  41 */     this.field_70138_W = 1.5F;
/*  42 */     this.field_70178_ae = true;
/*  43 */     this.isPlus = false;
/*     */   }
/*     */   
/*     */   public void setWheelPos(Vec3d pos, Vec3d weightedCenter) {
/*  47 */     this.pos = pos;
/*  48 */     this.isPlus = (pos.field_72449_c >= weightedCenter.field_72449_c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void travelToDimension(int dimensionId) {}
/*     */   
/*     */   public MCH_EntityAircraft getParents() {
/*  55 */     return this.parents;
/*     */   }
/*     */   
/*     */   public void setParents(MCH_EntityAircraft parents) {
/*  59 */     this.parents = parents;
/*     */   }
/*     */   
/*     */   protected void func_70037_a(@NotNull NBTTagCompound compound) {
/*  63 */     func_70106_y();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(@NotNull NBTTagCompound compound) {}
/*     */   
/*     */   public void func_70091_d(@NotNull MoverType type, double x, double y, double z) {
/*  70 */     this.field_70170_p.field_72984_F.func_76320_a("move");
/*  71 */     double d2 = x;
/*  72 */     double d3 = y;
/*  73 */     double d4 = z;
/*  74 */     List<AxisAlignedBB> list1 = getCollisionBoxes((Entity)this, func_174813_aQ().func_72321_a(x, y, z));
/*  75 */     AxisAlignedBB axisalignedbb = func_174813_aQ();
/*  76 */     if (y != 0.0D) {
/*  77 */       for (AxisAlignedBB axisAlignedBB : list1) {
/*  78 */         y = axisAlignedBB.func_72323_b(func_174813_aQ(), y);
/*     */       }
/*     */       
/*  81 */       func_174826_a(func_174813_aQ().func_72317_d(0.0D, y, 0.0D));
/*     */     } 
/*     */     
/*  84 */     boolean flag = (this.field_70122_E || (d3 != y && d3 < 0.0D));
/*  85 */     if (x != 0.0D) {
/*  86 */       for (AxisAlignedBB axisAlignedBB : list1) {
/*  87 */         x = axisAlignedBB.func_72316_a(func_174813_aQ(), x);
/*     */       }
/*     */       
/*  90 */       if (x != 0.0D) {
/*  91 */         func_174826_a(func_174813_aQ().func_72317_d(x, 0.0D, 0.0D));
/*     */       }
/*     */     } 
/*     */     
/*  95 */     if (z != 0.0D) {
/*  96 */       for (AxisAlignedBB axisAlignedBB : list1) {
/*  97 */         z = axisAlignedBB.func_72322_c(func_174813_aQ(), z);
/*     */       }
/*     */       
/* 100 */       if (z != 0.0D) {
/* 101 */         func_174826_a(func_174813_aQ().func_72317_d(0.0D, 0.0D, z));
/*     */       }
/*     */     } 
/*     */     
/* 105 */     if (this.field_70138_W > 0.0F && flag && (d2 != x || d4 != z)) {
/* 106 */       double d14 = x;
/* 107 */       double d6 = y;
/* 108 */       double d7 = z;
/* 109 */       AxisAlignedBB axisalignedbb1 = func_174813_aQ();
/* 110 */       func_174826_a(axisalignedbb);
/* 111 */       y = this.field_70138_W;
/* 112 */       List<AxisAlignedBB> list = getCollisionBoxes((Entity)this, func_174813_aQ().func_72321_a(d2, y, d4));
/* 113 */       AxisAlignedBB axisalignedbb2 = func_174813_aQ();
/* 114 */       AxisAlignedBB axisalignedbb3 = axisalignedbb2.func_72321_a(d2, 0.0D, d4);
/* 115 */       double d8 = y;
/*     */       
/* 117 */       for (AxisAlignedBB axisAlignedBB1 : list) {
/* 118 */         d8 = axisAlignedBB1.func_72323_b(axisalignedbb3, d8);
/*     */       }
/*     */       
/* 121 */       axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, d8, 0.0D);
/* 122 */       double d18 = d2;
/*     */       
/* 124 */       for (AxisAlignedBB element : list) {
/* 125 */         d18 = element.func_72316_a(axisalignedbb2, d18);
/*     */       }
/*     */       
/* 128 */       axisalignedbb2 = axisalignedbb2.func_72317_d(d18, 0.0D, 0.0D);
/* 129 */       double d19 = d4;
/*     */       
/* 131 */       for (AxisAlignedBB item : list) {
/* 132 */         d19 = item.func_72322_c(axisalignedbb2, d19);
/*     */       }
/*     */       
/* 135 */       axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, 0.0D, d19);
/* 136 */       AxisAlignedBB axisalignedbb4 = func_174813_aQ();
/* 137 */       double d20 = y;
/*     */       
/* 139 */       for (AxisAlignedBB value : list) {
/* 140 */         d20 = value.func_72323_b(axisalignedbb4, d20);
/*     */       }
/*     */       
/* 143 */       axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, d20, 0.0D);
/* 144 */       double d21 = d2;
/*     */       
/* 146 */       for (AxisAlignedBB bb : list) {
/* 147 */         d21 = bb.func_72316_a(axisalignedbb4, d21);
/*     */       }
/*     */       
/* 150 */       axisalignedbb4 = axisalignedbb4.func_72317_d(d21, 0.0D, 0.0D);
/* 151 */       double d22 = d4;
/*     */       
/* 153 */       for (AxisAlignedBB alignedBB : list) {
/* 154 */         d22 = alignedBB.func_72322_c(axisalignedbb4, d22);
/*     */       }
/*     */       
/* 157 */       axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, 0.0D, d22);
/* 158 */       double d23 = d18 * d18 + d19 * d19;
/* 159 */       double d9 = d21 * d21 + d22 * d22;
/* 160 */       if (d23 > d9) {
/* 161 */         x = d18;
/* 162 */         z = d19;
/* 163 */         y = -d8;
/* 164 */         func_174826_a(axisalignedbb2);
/*     */       } else {
/* 166 */         x = d21;
/* 167 */         z = d22;
/* 168 */         y = -d20;
/* 169 */         func_174826_a(axisalignedbb4);
/*     */       } 
/*     */       
/* 172 */       for (AxisAlignedBB axisAlignedBB : list) {
/* 173 */         y = axisAlignedBB.func_72323_b(func_174813_aQ(), y);
/*     */       }
/*     */       
/* 176 */       func_174826_a(func_174813_aQ().func_72317_d(0.0D, y, 0.0D));
/* 177 */       if (d14 * d14 + d7 * d7 >= x * x + z * z) {
/* 178 */         x = d14;
/* 179 */         y = d6;
/* 180 */         z = d7;
/* 181 */         func_174826_a(axisalignedbb1);
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     this.field_70170_p.field_72984_F.func_76319_b();
/* 186 */     this.field_70170_p.field_72984_F.func_76320_a("rest");
/* 187 */     func_174829_m();
/* 188 */     this.field_70123_F = (d2 != x || d4 != z);
/* 189 */     this.field_70124_G = (d3 != y);
/* 190 */     this.field_70122_E = (this.field_70124_G && d3 < 0.0D);
/* 191 */     this.field_70132_H = (this.field_70123_F || this.field_70124_G);
/* 192 */     int j6 = MathHelper.func_76128_c(this.field_70165_t);
/* 193 */     int i1 = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
/* 194 */     int k6 = MathHelper.func_76128_c(this.field_70161_v);
/* 195 */     BlockPos blockpos = new BlockPos(j6, i1, k6);
/* 196 */     IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
/* 197 */     if (iblockstate.func_185904_a() == Material.field_151579_a) {
/* 198 */       BlockPos blockpos1 = blockpos.func_177977_b();
/* 199 */       IBlockState iblockstate1 = this.field_70170_p.func_180495_p(blockpos1);
/* 200 */       Block block1 = iblockstate1.func_177230_c();
/* 201 */       if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/* 202 */         iblockstate = iblockstate1;
/* 203 */         blockpos = blockpos1;
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     func_184231_a(y, this.field_70122_E, iblockstate, blockpos);
/* 208 */     if (d2 != x) {
/* 209 */       this.field_70159_w = 0.0D;
/*     */     }
/*     */     
/* 212 */     if (d4 != z) {
/* 213 */       this.field_70179_y = 0.0D;
/*     */     }
/*     */     
/* 216 */     Block block = iblockstate.func_177230_c();
/* 217 */     if (d3 != y) {
/* 218 */       block.func_176216_a(this.field_70170_p, (Entity)this);
/*     */     }
/*     */     
/*     */     try {
/* 222 */       func_145775_I();
/* 223 */     } catch (Throwable var45) {
/* 224 */       CrashReport crashreport = CrashReport.func_85055_a(var45, "Checking entity block collision");
/* 225 */       CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
/* 226 */       func_85029_a(crashreportcategory);
/* 227 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */     
/* 230 */     this.field_70170_p.field_72984_F.func_76319_b();
/*     */   }
/*     */   
/*     */   public List<AxisAlignedBB> getCollisionBoxes(Entity entityIn, AxisAlignedBB aabb) {
/* 234 */     ArrayList<AxisAlignedBB> collidingBoundingBoxes = new ArrayList<>();
/* 235 */     getCollisionBoxes(entityIn, aabb, collidingBoundingBoxes);
/* 236 */     double d0 = 0.25D;
/* 237 */     List<Entity> list = entityIn.field_70170_p.func_72839_b(entityIn, aabb.func_72314_b(d0, d0, d0));
/*     */     
/* 239 */     for (Entity entity : list) {
/* 240 */       if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat) && !(entity instanceof com.norwood.mcheli.aircraft.MCH_EntityHitBox) && entity != this.parents) {
/* 241 */         AxisAlignedBB axisalignedbb1 = entity.func_70046_E();
/* 242 */         if (axisalignedbb1 != null && axisalignedbb1.func_72326_a(aabb)) {
/* 243 */           collidingBoundingBoxes.add(axisalignedbb1);
/*     */         }
/*     */         
/* 246 */         axisalignedbb1 = entityIn.func_70114_g(entity);
/* 247 */         if (axisalignedbb1 != null && axisalignedbb1.func_72326_a(aabb)) {
/* 248 */           collidingBoundingBoxes.add(axisalignedbb1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 253 */     return collidingBoundingBoxes;
/*     */   }
/*     */   
/*     */   private boolean getCollisionBoxes(Entity entityIn, AxisAlignedBB aabb, List<AxisAlignedBB> outList) {
/* 257 */     int i = MathHelper.func_76128_c(aabb.field_72340_a) - 1;
/* 258 */     int j = MathHelper.func_76143_f(aabb.field_72336_d) + 1;
/* 259 */     int k = MathHelper.func_76128_c(aabb.field_72338_b) - 1;
/* 260 */     int l = MathHelper.func_76143_f(aabb.field_72337_e) + 1;
/* 261 */     int i1 = MathHelper.func_76128_c(aabb.field_72339_c) - 1;
/* 262 */     int j1 = MathHelper.func_76143_f(aabb.field_72334_f) + 1;
/* 263 */     WorldBorder worldborder = entityIn.field_70170_p.func_175723_af();
/* 264 */     boolean flag = entityIn.func_174832_aS();
/* 265 */     boolean flag1 = entityIn.field_70170_p.func_191503_g(entityIn);
/* 266 */     IBlockState iblockstate = Blocks.field_150348_b.func_176223_P();
/* 267 */     BlockPos.PooledMutableBlockPos blockpos = BlockPos.PooledMutableBlockPos.func_185346_s();
/*     */     
/*     */     try {
/* 270 */       for (int k1 = i; k1 < j; k1++) {
/* 271 */         for (int l1 = i1; l1 < j1; l1++) {
/* 272 */           boolean flag2 = (k1 == i || k1 == j - 1);
/* 273 */           boolean flag3 = (l1 == i1 || l1 == j1 - 1);
/* 274 */           if ((!flag2 || !flag3) && entityIn.field_70170_p.func_175667_e((BlockPos)blockpos.func_181079_c(k1, 64, l1))) {
/* 275 */             for (int i2 = k; i2 < l; i2++) {
/* 276 */               if ((!flag2 && !flag3) || i2 != l - 1) {
/* 277 */                 IBlockState iblockstate1; if (flag == flag1) {
/* 278 */                   entityIn.func_174821_h(!flag1);
/*     */                 }
/*     */                 
/* 281 */                 blockpos.func_181079_c(k1, i2, l1);
/*     */                 
/* 283 */                 if (!worldborder.func_177746_a((BlockPos)blockpos) && flag1) {
/* 284 */                   iblockstate1 = iblockstate;
/*     */                 } else {
/* 286 */                   iblockstate1 = entityIn.field_70170_p.func_180495_p((BlockPos)blockpos);
/*     */                 } 
/*     */                 
/* 289 */                 iblockstate1.func_185908_a(entityIn.field_70170_p, (BlockPos)blockpos, aabb, outList, entityIn, false);
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 296 */       blockpos.func_185344_t();
/*     */     } 
/*     */     
/* 299 */     return !outList.isEmpty();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tank\MCH_EntityWheel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */