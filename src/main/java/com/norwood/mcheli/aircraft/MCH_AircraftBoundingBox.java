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
/*  12 */     this(ac, ac.func_174813_aQ());
/*     */   }
/*     */   
/*     */   public MCH_AircraftBoundingBox(MCH_EntityAircraft ac, AxisAlignedBB aabb) {
/*  16 */     super(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c, aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f);
/*  17 */     this.ac = ac;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB NewAABB(double x1, double y1, double z1, double x2, double y2, double z2) {
/*  21 */     return new MCH_AircraftBoundingBox(this.ac, new AxisAlignedBB(x1, y1, z1, x2, y2, z2));
/*     */   }
/*     */   
/*     */   public double getDistanceSquareBetween(AxisAlignedBB box1, AxisAlignedBB box2) {
/*  25 */     double centerX1 = (box1.field_72340_a + box1.field_72336_d) / 2.0D;
/*  26 */     double centerY1 = (box1.field_72338_b + box1.field_72337_e) / 2.0D;
/*  27 */     double centerZ1 = (box1.field_72339_c + box1.field_72334_f) / 2.0D;
/*     */     
/*  29 */     double centerX2 = (box2.field_72340_a + box2.field_72336_d) / 2.0D;
/*  30 */     double centerY2 = (box2.field_72338_b + box2.field_72337_e) / 2.0D;
/*  31 */     double centerZ2 = (box2.field_72339_c + box2.field_72334_f) / 2.0D;
/*     */     
/*  33 */     double dx = centerX1 - centerX2;
/*  34 */     double dy = centerY1 - centerY2;
/*  35 */     double dz = centerZ1 - centerZ2;
/*     */     
/*  37 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_72326_a(@NotNull AxisAlignedBB aabb) {
/*  42 */     boolean ret = false;
/*  43 */     double dist = 1.0E7D;
/*  44 */     this.ac.lastBBDamageFactor = 1.0F;
/*  45 */     if (super.func_72326_a(aabb)) {
/*  46 */       dist = getDistanceSquareBetween(aabb, this);
/*  47 */       ret = true;
/*     */     } 
/*     */     
/*  50 */     for (MCH_BoundingBox bb : this.ac.extraBoundingBox) {
/*  51 */       if (bb.getBoundingBox().func_72326_a(aabb)) {
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
/*     */   public AxisAlignedBB func_72314_b(double x, double y, double z) {
/*  65 */     double newMinX = this.field_72340_a - x;
/*  66 */     double newMinY = this.field_72338_b - y;
/*  67 */     double newMinZ = this.field_72339_c - z;
/*  68 */     double newMaxX = this.field_72336_d + x;
/*  69 */     double newMaxY = this.field_72337_e + y;
/*  70 */     double newMaxZ = this.field_72334_f + z;
/*     */     
/*  72 */     return NewAABB(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public AxisAlignedBB func_111270_a(AxisAlignedBB other) {
/*  77 */     double d0 = Math.min(this.field_72340_a, other.field_72340_a);
/*  78 */     double d1 = Math.min(this.field_72338_b, other.field_72338_b);
/*  79 */     double d2 = Math.min(this.field_72339_c, other.field_72339_c);
/*  80 */     double d3 = Math.max(this.field_72336_d, other.field_72336_d);
/*  81 */     double d4 = Math.max(this.field_72337_e, other.field_72337_e);
/*  82 */     double d5 = Math.max(this.field_72334_f, other.field_72334_f);
/*  83 */     return NewAABB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */   @NotNull
/*     */   public AxisAlignedBB func_72321_a(double x, double y, double z) {
/*  87 */     double minX = this.field_72340_a + Math.min(0.0D, x);
/*  88 */     double maxX = this.field_72336_d + Math.max(0.0D, x);
/*  89 */     double minY = this.field_72338_b + Math.min(0.0D, y);
/*  90 */     double maxY = this.field_72337_e + Math.max(0.0D, y);
/*  91 */     double minZ = this.field_72339_c + Math.min(0.0D, z);
/*  92 */     double maxZ = this.field_72334_f + Math.max(0.0D, z);
/*     */     
/*  94 */     return NewAABB(minX, minY, minZ, maxX, maxY, maxZ);
/*     */   }
/*     */   @NotNull
/*     */   public AxisAlignedBB func_191195_a(double x, double y, double z) {
/*  98 */     double d3 = this.field_72340_a + x;
/*  99 */     double d4 = this.field_72338_b + y;
/* 100 */     double d5 = this.field_72339_c + z;
/* 101 */     double d6 = this.field_72336_d - x;
/* 102 */     double d7 = this.field_72337_e - y;
/* 103 */     double d8 = this.field_72334_f - z;
/* 104 */     return NewAABB(d3, d4, d5, d6, d7, d8);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB copy() {
/* 108 */     return NewAABB(this.field_72340_a, this.field_72338_b, this.field_72339_c, this.field_72336_d, this.field_72337_e, this.field_72334_f);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getOffsetBoundingBox(double x, double y, double z) {
/* 112 */     return NewAABB(this.field_72340_a + x, this.field_72338_b + y, this.field_72339_c + z, this.field_72336_d + x, this.field_72337_e + y, this.field_72334_f + z);
/*     */   }
/*     */   
/*     */   public RayTraceResult func_72327_a(@NotNull Vec3d start, @NotNull Vec3d end) {
/* 116 */     this.ac.lastBBDamageFactor = 1.0F;
/*     */     
/* 118 */     RayTraceResult closestHit = super.func_72327_a(start, end);
/* 119 */     double closestDistance = (closestHit != null) ? start.func_72438_d(closestHit.field_72307_f) : Double.MAX_VALUE;
/*     */     
/* 121 */     for (MCH_BoundingBox extraBox : this.ac.extraBoundingBox) {
/* 122 */       RayTraceResult extraHit = extraBox.getBoundingBox().func_72327_a(start, end);
/* 123 */       if (extraHit != null) {
/* 124 */         double extraDistance = start.func_72438_d(extraHit.field_72307_f);
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