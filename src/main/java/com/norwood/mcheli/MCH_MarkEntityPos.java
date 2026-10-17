/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import com.norwood.mcheli.helper.entity.ITargetMarkerObject;
/*    */ import java.nio.FloatBuffer;
/*    */ import javax.annotation.Nullable;
/*    */ import org.lwjgl.BufferUtils;
/*    */ 
/*    */ public class MCH_MarkEntityPos
/*    */ {
/*    */   public final FloatBuffer pos;
/*    */   public final int type;
/*    */   private final ITargetMarkerObject target;
/*    */   
/*    */   public MCH_MarkEntityPos(int type, ITargetMarkerObject target) {
/* 15 */     this.type = type;
/* 16 */     this.pos = BufferUtils.createFloatBuffer(3);
/* 17 */     this.target = target;
/*    */   }
/*    */   
/*    */   public MCH_MarkEntityPos(int type) {
/* 21 */     this(type, null);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public ITargetMarkerObject getTarget() {
/* 26 */     return this.target;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_MarkEntityPos.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */