/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import com.norwood.mcheli.helper.client._IModelCustom;
/*    */ 
/*    */ public class MCH_Cartridge {
/*    */   public final String name;
/*    */   public final float acceleration;
/*    */   public final float yaw;
/*    */   public final float pitch;
/*    */   public final float bound;
/*    */   public final float gravity;
/*    */   public final float scale;
/*    */   public _IModelCustom model;
/*    */   
/*    */   public MCH_Cartridge(String nm, float a, float y, float p, float b, float g, float s) {
/* 16 */     this.name = nm;
/* 17 */     this.acceleration = a;
/* 18 */     this.yaw = y;
/* 19 */     this.pitch = p;
/* 20 */     this.bound = b;
/* 21 */     this.gravity = g;
/* 22 */     this.scale = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_Cartridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */