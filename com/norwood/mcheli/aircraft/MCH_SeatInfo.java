/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_SeatInfo
/*    */ {
/*    */   public final Vec3d pos;
/*    */   public final boolean gunner;
/*    */   public final boolean switchgunner;
/*    */   public final boolean fixRot;
/*    */   public final float fixYaw;
/*    */   public final float fixPitch;
/*    */   public final float minPitch;
/*    */   public final float maxPitch;
/*    */   public final boolean rotSeat;
/*    */   private final MCH_AircraftInfo.CameraPosition camPos;
/*    */   public final boolean invCamPos;
/*    */   
/*    */   public MCH_SeatInfo(Vec3d p, boolean g, MCH_AircraftInfo.CameraPosition cp, boolean icp, boolean sg, boolean fr, float yaw, float pitch, float pmin, float pmax, boolean rotSeat) {
/* 31 */     this.camPos = cp;
/* 32 */     this.pos = p;
/* 33 */     this.gunner = g;
/* 34 */     this.invCamPos = icp;
/* 35 */     this.switchgunner = sg;
/* 36 */     this.fixRot = fr;
/* 37 */     this.fixYaw = yaw;
/* 38 */     this.fixPitch = pitch;
/* 39 */     this.minPitch = pmin;
/* 40 */     this.maxPitch = pmax;
/* 41 */     this.rotSeat = rotSeat;
/*    */   }
/*    */   
/*    */   public MCH_SeatInfo(Vec3d p, boolean g, MCH_AircraftInfo.CameraPosition cp, boolean icp, boolean sg, boolean fr, float yaw, float pitch, boolean rotSeat) {
/* 45 */     this(p, g, cp, icp, sg, fr, yaw, pitch, -30.0F, 70.0F, rotSeat);
/*    */   }
/*    */   
/*    */   public MCH_SeatInfo(Vec3d p, MCH_AircraftInfo.CameraPosition cp, float yaw, float pitch, boolean rotSeat) {
/* 49 */     this(p, false, cp, false, false, false, yaw, pitch, -30.0F, 70.0F, rotSeat);
/*    */   }
/*    */   
/*    */   public MCH_SeatInfo(Vec3d p, boolean rotSeat) {
/* 53 */     this(p, false, null, false, false, false, 0.0F, 0.0F, -30.0F, 70.0F, rotSeat);
/*    */   }
/*    */   
/*    */   public MCH_AircraftInfo.CameraPosition getCamPos() {
/* 57 */     return this.camPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_SeatInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */