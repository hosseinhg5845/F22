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
/*  40 */     setSize(1.0F, 1.0F);
/*  41 */     this.stepHeight = 1.5F;
/*  42 */     this.isImmuneToFire = true;
/*  43 */     this.isPlus = false;
/*     */   }
/*     */   
/*     */   public void setWheelPos(Vec3d pos, Vec3d weightedCenter) {
/*  47 */     this.pos = pos;
/*  48 */     this.isPlus = (pos.z >= weightedCenter.z);
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
/*     */   protected void readEntityFromNBT(@NotNull NBTTagCompound compound) {
/*  63 */     setDead();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(@NotNull NBTTagCompound compound) {}
/*     */   
/*     */   public void move(@NotNull MoverType type, double x, double y, double z) {
/*  70 */     this.world.profiler.startSection("move");
/*  71 */     double d2 = x;
/*  72 */     double d3 = y;
/*  73 */     double d4 = z;
/*  74 */     List<AxisAlignedBB> list1 = getCollisionBoxes((Entity)this, getEntityBoundingBox().expand(x, y, z));
/*  75 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*  76 */     if (y != 0.0D) {
/*  77 */       for (AxisAlignedBB axisAlignedBB : list1) {
/*  78 */         y = axisAlignedBB.calculateYOffset(getEntityBoundingBox(), y);
/*     */       }
/*     */       
/*  81 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*     */     } 
/*     */     
/*  84 */     boolean flag = (this.onGround || (d3 != y && d3 < 0.0D));
/*  85 */     if (x != 0.0D) {
/*  86 */       for (AxisAlignedBB axisAlignedBB : list1) {
/*  87 */         x = axisAlignedBB.calculateXOffset(getEntityBoundingBox(), x);
/*     */       }
/*     */       
/*  90 */       if (x != 0.0D) {
/*  91 */         setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
/*     */       }
/*     */     } 
/*     */     
/*  95 */     if (z != 0.0D) {
/*  96 */       for (AxisAlignedBB axisAlignedBB : list1) {
/*  97 */         z = axisAlignedBB.calculateZOffset(getEntityBoundingBox(), z);
/*     */       }
/*     */       
/* 100 */       if (z != 0.0D) {
/* 101 */         setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
/*     */       }
/*     */     } 
/*     */     
/* 105 */     if (this.stepHeight > 0.0F && flag && (d2 != x || d4 != z)) {
/* 106 */       double d14 = x;
/* 107 */       double d6 = y;
/* 108 */       double d7 = z;
/* 109 */       AxisAlignedBB axisalignedbb1 = getEntityBoundingBox();
/* 110 */       setEntityBoundingBox(axisalignedbb);
/* 111 */       y = this.stepHeight;
/* 112 */       List<AxisAlignedBB> list = getCollisionBoxes((Entity)this, getEntityBoundingBox().expand(d2, y, d4));
/* 113 */       AxisAlignedBB axisalignedbb2 = getEntityBoundingBox();
/* 114 */       AxisAlignedBB axisalignedbb3 = axisalignedbb2.expand(d2, 0.0D, d4);
/* 115 */       double d8 = y;
/*     */       
/* 117 */       for (AxisAlignedBB axisAlignedBB1 : list) {
/* 118 */         d8 = axisAlignedBB1.calculateYOffset(axisalignedbb3, d8);
/*     */       }
/*     */       
/* 121 */       axisalignedbb2 = axisalignedbb2.offset(0.0D, d8, 0.0D);
/* 122 */       double d18 = d2;
/*     */       
/* 124 */       for (AxisAlignedBB element : list) {
/* 125 */         d18 = element.calculateXOffset(axisalignedbb2, d18);
/*     */       }
/*     */       
/* 128 */       axisalignedbb2 = axisalignedbb2.offset(d18, 0.0D, 0.0D);
/* 129 */       double d19 = d4;
/*     */       
/* 131 */       for (AxisAlignedBB item : list) {
/* 132 */         d19 = item.calculateZOffset(axisalignedbb2, d19);
/*     */       }
/*     */       
/* 135 */       axisalignedbb2 = axisalignedbb2.offset(0.0D, 0.0D, d19);
/* 136 */       AxisAlignedBB axisalignedbb4 = getEntityBoundingBox();
/* 137 */       double d20 = y;
/*     */       
/* 139 */       for (AxisAlignedBB value : list) {
/* 140 */         d20 = value.calculateYOffset(axisalignedbb4, d20);
/*     */       }
/*     */       
/* 143 */       axisalignedbb4 = axisalignedbb4.offset(0.0D, d20, 0.0D);
/* 144 */       double d21 = d2;
/*     */       
/* 146 */       for (AxisAlignedBB bb : list) {
/* 147 */         d21 = bb.calculateXOffset(axisalignedbb4, d21);
/*     */       }
/*     */       
/* 150 */       axisalignedbb4 = axisalignedbb4.offset(d21, 0.0D, 0.0D);
/* 151 */       double d22 = d4;
/*     */       
/* 153 */       for (AxisAlignedBB alignedBB : list) {
/* 154 */         d22 = alignedBB.calculateZOffset(axisalignedbb4, d22);
/*     */       }
/*     */       
/* 157 */       axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d22);
/* 158 */       double d23 = d18 * d18 + d19 * d19;
/* 159 */       double d9 = d21 * d21 + d22 * d22;
/* 160 */       if (d23 > d9) {
/* 161 */         x = d18;
/* 162 */         z = d19;
/* 163 */         y = -d8;
/* 164 */         setEntityBoundingBox(axisalignedbb2);
/*     */       } else {
/* 166 */         x = d21;
/* 167 */         z = d22;
/* 168 */         y = -d20;
/* 169 */         setEntityBoundingBox(axisalignedbb4);
/*     */       } 
/*     */       
/* 172 */       for (AxisAlignedBB axisAlignedBB : list) {
/* 173 */         y = axisAlignedBB.calculateYOffset(getEntityBoundingBox(), y);
/*     */       }
/*     */       
/* 176 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/* 177 */       if (d14 * d14 + d7 * d7 >= x * x + z * z) {
/* 178 */         x = d14;
/* 179 */         y = d6;
/* 180 */         z = d7;
/* 181 */         setEntityBoundingBox(axisalignedbb1);
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     this.world.profiler.endSection();
/* 186 */     this.world.profiler.startSection("rest");
/* 187 */     resetPositionToBB();
/* 188 */     this.collidedHorizontally = (d2 != x || d4 != z);
/* 189 */     this.collidedVertically = (d3 != y);
/* 190 */     this.onGround = (this.collidedVertically && d3 < 0.0D);
/* 191 */     this.collided = (this.collidedHorizontally || this.collidedVertically);
/* 192 */     int j6 = MathHelper.floor(this.posX);
/* 193 */     int i1 = MathHelper.floor(this.posY - 0.20000000298023224D);
/* 194 */     int k6 = MathHelper.floor(this.posZ);
/* 195 */     BlockPos blockpos = new BlockPos(j6, i1, k6);
/* 196 */     IBlockState iblockstate = this.world.getBlockState(blockpos);
/* 197 */     if (iblockstate.getMaterial() == Material.AIR) {
/* 198 */       BlockPos blockpos1 = blockpos.down();
/* 199 */       IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
/* 200 */       Block block1 = iblockstate1.getBlock();
/* 201 */       if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/* 202 */         iblockstate = iblockstate1;
/* 203 */         blockpos = blockpos1;
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     updateFallState(y, this.onGround, iblockstate, blockpos);
/* 208 */     if (d2 != x) {
/* 209 */       this.motionX = 0.0D;
/*     */     }
/*     */     
/* 212 */     if (d4 != z) {
/* 213 */       this.motionZ = 0.0D;
/*     */     }
/*     */     
/* 216 */     Block block = iblockstate.getBlock();
/* 217 */     if (d3 != y) {
/* 218 */       block.onLanded(this.world, (Entity)this);
/*     */     }
/*     */     
/*     */     try {
/* 222 */       doBlockCollisions();
/* 223 */     } catch (Throwable var45) {
/* 224 */       CrashReport crashreport = CrashReport.makeCrashReport(var45, "Checking entity block collision");
/* 225 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
/* 226 */       addEntityCrashInfo(crashreportcategory);
/* 227 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */     
/* 230 */     this.world.profiler.endSection();
/*     */   }
/*     */   
/*     */   public List<AxisAlignedBB> getCollisionBoxes(Entity entityIn, AxisAlignedBB aabb) {
/* 234 */     ArrayList<AxisAlignedBB> collidingBoundingBoxes = new ArrayList<>();
/* 235 */     getCollisionBoxes(entityIn, aabb, collidingBoundingBoxes);
/* 236 */     double d0 = 0.25D;
/* 237 */     List<Entity> list = entityIn.world.getEntitiesWithinAABBExcludingEntity(entityIn, aabb.grow(d0, d0, d0));
/*     */     
/* 239 */     for (Entity entity : list) {
/* 240 */       if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat) && !(entity instanceof com.norwood.mcheli.aircraft.MCH_EntityHitBox) && entity != this.parents) {
/* 241 */         AxisAlignedBB axisalignedbb1 = entity.getCollisionBoundingBox();
/* 242 */         if (axisalignedbb1 != null && axisalignedbb1.intersects(aabb)) {
/* 243 */           collidingBoundingBoxes.add(axisalignedbb1);
/*     */         }
/*     */         
/* 246 */         axisalignedbb1 = entityIn.getCollisionBox(entity);
/* 247 */         if (axisalignedbb1 != null && axisalignedbb1.intersects(aabb)) {
/* 248 */           collidingBoundingBoxes.add(axisalignedbb1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 253 */     return collidingBoundingBoxes;
/*     */   }
/*     */   
/*     */   private boolean getCollisionBoxes(Entity entityIn, AxisAlignedBB aabb, List<AxisAlignedBB> outList) {
/* 257 */     int i = MathHelper.floor(aabb.minX) - 1;
/* 258 */     int j = MathHelper.ceil(aabb.maxX) + 1;
/* 259 */     int k = MathHelper.floor(aabb.minY) - 1;
/* 260 */     int l = MathHelper.ceil(aabb.maxY) + 1;
/* 261 */     int i1 = MathHelper.floor(aabb.minZ) - 1;
/* 262 */     int j1 = MathHelper.ceil(aabb.maxZ) + 1;
/* 263 */     WorldBorder worldborder = entityIn.world.getWorldBorder();
/* 264 */     boolean flag = entityIn.isOutsideBorder();
/* 265 */     boolean flag1 = entityIn.world.isInsideWorldBorder(entityIn);
/* 266 */     IBlockState iblockstate = Blocks.STONE.getDefaultState();
/* 267 */     BlockPos.PooledMutableBlockPos blockpos = BlockPos.PooledMutableBlockPos.retain();
/*     */     
/*     */     try {
/* 270 */       for (int k1 = i; k1 < j; k1++) {
/* 271 */         for (int l1 = i1; l1 < j1; l1++) {
/* 272 */           boolean flag2 = (k1 == i || k1 == j - 1);
/* 273 */           boolean flag3 = (l1 == i1 || l1 == j1 - 1);
/* 274 */           if ((!flag2 || !flag3) && entityIn.world.isBlockLoaded((BlockPos)blockpos.setPos(k1, 64, l1))) {
/* 275 */             for (int i2 = k; i2 < l; i2++) {
/* 276 */               if ((!flag2 && !flag3) || i2 != l - 1) {
/* 277 */                 IBlockState iblockstate1; if (flag == flag1) {
/* 278 */                   entityIn.setOutsideBorder(!flag1);
/*     */                 }
/*     */                 
/* 281 */                 blockpos.setPos(k1, i2, l1);
/*     */                 
/* 283 */                 if (!worldborder.contains((BlockPos)blockpos) && flag1) {
/* 284 */                   iblockstate1 = iblockstate;
/*     */                 } else {
/* 286 */                   iblockstate1 = entityIn.world.getBlockState((BlockPos)blockpos);
/*     */                 } 
/*     */                 
/* 289 */                 iblockstate1.addCollisionBoxToList(entityIn.world, (BlockPos)blockpos, aabb, outList, entityIn, false);
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 296 */       blockpos.release();
/*     */     } 
/*     */     
/* 299 */     return !outList.isEmpty();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tank\MCH_EntityWheel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */