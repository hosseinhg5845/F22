/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_AircraftBoundingBox extends AxisAlignedBB {
/*     */   private final MCH_EntityAircraft ac;
/*     */   
/*     */   protected MCH_AircraftBoundingBox(MCH_EntityAircraft ac) {
/*  12 */     this(ac, ac.getEntityBoundingBox());
/*     */   }
/*     */   
/*     */   public MCH_AircraftBoundingBox(MCH_EntityAircraft ac, AxisAlignedBB aabb) {
/*  16 */     super(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
/*  17 */     this.ac = ac;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB NewAABB(double x1, double y1, double z1, double x2, double y2, double z2) {
/*  21 */     return new MCH_AircraftBoundingBox(this.ac, new AxisAlignedBB(x1, y1, z1, x2, y2, z2));
/*     */   }
/*     */   
/*     */   public double getDistanceSquareBetween(AxisAlignedBB box1, AxisAlignedBB box2) {
/*  25 */     double centerX1 = (box1.minX + box1.maxX) / 2.0D;
/*  26 */     double centerY1 = (box1.minY + box1.maxY) / 2.0D;
/*  27 */     double centerZ1 = (box1.minZ + box1.maxZ) / 2.0D;
/*     */     
/*  29 */     double centerX2 = (box2.minX + box2.maxX) / 2.0D;
/*  30 */     double centerY2 = (box2.minY + box2.maxY) / 2.0D;
/*  31 */     double centerZ2 = (box2.minZ + box2.maxZ) / 2.0D;
/*     */     
/*  33 */     double dx = centerX1 - centerX2;
/*  34 */     double dy = centerY1 - centerY2;
/*  35 */     double dz = centerZ1 - centerZ2;
/*     */     
/*  37 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean intersects(@NotNull AxisAlignedBB aabb) {
/*  42 */     boolean ret = false;
/*  43 */     double dist = 1.0E7D;
/*  44 */     this.ac.lastBBDamageFactor = 1.0F;
/*  45 */     if (super.intersects(aabb)) {
/*  46 */       dist = getDistanceSquareBetween(aabb, this);
/*  47 */       ret = true;
/*     */     } 
/*     */     
/*  50 */     for (MCH_BoundingBox bb : this.ac.extraBoundingBox) {
/*  51 */       if (bb.getBoundingBox().intersects(aabb)) {
/*  52 */         double dist2 = getDistanceSquareBetween(aabb, this);
/*  53 */         if (dist2 < dist) {
/*  54 */           dist = dist2;
/*  55 */           this.ac.lastBBDamageFactor = bb.damegeFactor;
/*     */         } 
/*     */         
/*  58 */         ret = true;
/*     */       } 
/*     */     } 
/*     */     
/*  62 */     return ret;
/*     */   } @NotNull
/*     */   public AxisAlignedBB grow(double x, double y, double z) {
/*  65 */     double newMinX = this.minX - x;
/*  66 */     double newMinY = this.minY - y;
/*  67 */     double newMinZ = this.minZ - z;
/*  68 */     double newMaxX = this.maxX + x;
/*  69 */     double newMaxY = this.maxY + y;
/*  70 */     double newMaxZ = this.maxZ + z;
/*     */     
/*  72 */     return NewAABB(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public AxisAlignedBB union(AxisAlignedBB other) {
/*  77 */     double d0 = Math.min(this.minX, other.minX);
/*  78 */     double d1 = Math.min(this.minY, other.minY);
/*  79 */     double d2 = Math.min(this.minZ, other.minZ);
/*  80 */     double d3 = Math.max(this.maxX, other.maxX);
/*  81 */     double d4 = Math.max(this.maxY, other.maxY);
/*  82 */     double d5 = Math.max(this.maxZ, other.maxZ);
/*  83 */     return NewAABB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */   @NotNull
/*     */   public AxisAlignedBB expand(double x, double y, double z) {
/*  87 */     double minX = this.minX + Math.min(0.0D, x);
/*  88 */     double maxX = this.maxX + Math.max(0.0D, x);
/*  89 */     double minY = this.minY + Math.min(0.0D, y);
/*  90 */     double maxY = this.maxY + Math.max(0.0D, y);
/*  91 */     double minZ = this.minZ + Math.min(0.0D, z);
/*  92 */     double maxZ = this.maxZ + Math.max(0.0D, z);
/*     */     
/*  94 */     return NewAABB(minX, minY, minZ, maxX, maxY, maxZ);
/*     */   }
/*     */   @NotNull
/*     */   public AxisAlignedBB contract(double x, double y, double z) {
/*  98 */     double d3 = this.minX + x;
/*  99 */     double d4 = this.minY + y;
/* 100 */     double d5 = this.minZ + z;
/* 101 */     double d6 = this.maxX - x;
/* 102 */     double d7 = this.maxY - y;
/* 103 */     double d8 = this.maxZ - z;
/* 104 */     return NewAABB(d3, d4, d5, d6, d7, d8);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB copy() {
/* 108 */     return NewAABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getOffsetBoundingBox(double x, double y, double z) {
/* 112 */     return NewAABB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
/*     */   }
/*     */   
/*     */   public RayTraceResult calculateIntercept(@NotNull Vec3d start, @NotNull Vec3d end) {
/* 116 */     this.ac.lastBBDamageFactor = 1.0F;
/*     */     
/* 118 */     RayTraceResult closestHit = super.calculateIntercept(start, end);
/* 119 */     double closestDistance = (closestHit != null) ? start.distanceTo(closestHit.hitVec) : Double.MAX_VALUE;
/*     */     
/* 121 */     for (MCH_BoundingBox extraBox : this.ac.extraBoundingBox) {
/* 122 */       RayTraceResult extraHit = extraBox.getBoundingBox().calculateIntercept(start, end);
/* 123 */       if (extraHit != null) {
/* 124 */         double extraDistance = start.distanceTo(extraHit.hitVec);
/* 125 */         if (extraDistance < closestDistance) {
/* 126 */           closestHit = extraHit;
/* 127 */           closestDistance = extraDistance;
/* 128 */           this.ac.lastBBDamageFactor = extraBox.damegeFactor;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     return closestHit;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_AircraftBoundingBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */