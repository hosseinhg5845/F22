/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class W_MovingObjectPosition
/*    */ {
/*    */   public static boolean isHitTypeEntity(RayTraceResult m) {
/* 11 */     return (m != null && m.typeOfHit == RayTraceResult.Type.ENTITY);
/*    */   }
/*    */   
/*    */   public static boolean isHitTypeTile(RayTraceResult m) {
/* 15 */     return (m != null && m.typeOfHit == RayTraceResult.Type.BLOCK);
/*    */   }
/*    */   
/*    */   public static RayTraceResult newMOP(int p1, int p2, int p3, int p4, Vec3d p5, boolean p6) {
/* 19 */     return new RayTraceResult(p5, EnumFacing.getFront(p4), new BlockPos(p1, p2, p3));
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_MovingObjectPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */