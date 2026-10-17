/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class MCH_SeatRackInfo
/*    */   extends MCH_SeatInfo
/*    */ {
/*    */   public final float range;
/*    */   public final float openParaAlt;
/*    */   public final String[] names;
/*    */   
/*    */   public MCH_SeatRackInfo(String[] entityNames, double x, double y, double z, MCH_AircraftInfo.CameraPosition ep, float rng, float paraAlt, float yaw, float pitch, boolean rotSeat) {
/* 13 */     super(new Vec3d(x, y, z), ep, yaw, pitch, rotSeat);
/* 14 */     this.range = rng;
/* 15 */     this.openParaAlt = paraAlt;
/* 16 */     this.names = entityNames;
/*    */   }
/*    */   
/*    */   public Vec3d getEntryPos() {
/* 20 */     return (getCamPos()).pos;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_SeatRackInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */