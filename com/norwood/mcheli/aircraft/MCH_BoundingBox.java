/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_Config;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class MCH_BoundingBox
/*    */ {
/*    */   public final double offsetX;
/*    */   public final double offsetY;
/*    */   public final double offsetZ;
/*    */   public final float width;
/*    */   public final float height;
/*    */   public final float damegeFactor;
/*    */   public Vec3d rotatedOffset;
/* 19 */   public final List<Vec3d> pos = new ArrayList<>();
/*    */   private AxisAlignedBB boundingBox;
/*    */   
/*    */   public MCH_BoundingBox(double x, double y, double z, float w, float h, float df) {
/* 23 */     this.offsetX = x;
/* 24 */     this.offsetY = y;
/* 25 */     this.offsetZ = z;
/* 26 */     this.width = w;
/* 27 */     this.height = h;
/* 28 */     this.damegeFactor = df;
/* 29 */     this.boundingBox = new AxisAlignedBB(x - (w / 2.0F), y - (h / 2.0F), z - (w / 2.0F), x + (w / 2.0F), y + (h / 2.0F), z + (w / 2.0F));
/* 30 */     updatePosition(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public void add(double x, double y, double z) {
/* 34 */     this.pos.add(0, new Vec3d(x, y, z));
/*    */     
/* 36 */     while (this.pos.size() > MCH_Config.HitBoxDelayTick.prmInt + 2) {
/* 37 */       this.pos.remove(MCH_Config.HitBoxDelayTick.prmInt + 2);
/*    */     }
/*    */   }
/*    */   
/*    */   public MCH_BoundingBox copy() {
/* 42 */     return new MCH_BoundingBox(this.offsetX, this.offsetY, this.offsetZ, this.width, this.height, this.damegeFactor);
/*    */   }
/*    */   
/*    */   public void updatePosition(double posX, double posY, double posZ, float yaw, float pitch, float roll) {
/* 46 */     Vec3d v = new Vec3d(this.offsetX, this.offsetY, this.offsetZ);
/* 47 */     this.rotatedOffset = MCH_Lib.RotVec3(v, -yaw, -pitch, -roll);
/* 48 */     add(posX + this.rotatedOffset.field_72450_a, posY + this.rotatedOffset.field_72448_b, posZ + this.rotatedOffset.field_72449_c);
/* 49 */     int index = MCH_Config.HitBoxDelayTick.prmInt;
/* 50 */     Vec3d cp = (index < this.pos.size()) ? this.pos.get(index) : this.pos.get(this.pos.size() - 1);
/* 51 */     Vec3d pp = (index + 1 < this.pos.size()) ? this.pos.get(index + 1) : this.pos.get(this.pos.size() - 1);
/* 52 */     double sx = (this.width + Math.abs(cp.field_72450_a - pp.field_72450_a)) / 2.0D;
/* 53 */     double sy = (this.height + Math.abs(cp.field_72448_b - pp.field_72448_b)) / 2.0D;
/* 54 */     double sz = (this.width + Math.abs(cp.field_72449_c - pp.field_72449_c)) / 2.0D;
/* 55 */     double x = (cp.field_72450_a + pp.field_72450_a) / 2.0D;
/* 56 */     double y = (cp.field_72448_b + pp.field_72448_b) / 2.0D;
/* 57 */     double z = (cp.field_72449_c + pp.field_72449_c) / 2.0D;
/* 58 */     this.boundingBox = new AxisAlignedBB(x - sx, y - sy, z - sz, x + sx, y + sy, z + sz);
/*    */   }
/*    */   
/*    */   public AxisAlignedBB getBoundingBox() {
/* 62 */     return this.boundingBox;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_BoundingBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */