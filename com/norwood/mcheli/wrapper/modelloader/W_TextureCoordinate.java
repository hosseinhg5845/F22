/*    */ package com.norwood.mcheli.wrapper.modelloader;
/*    */ 
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class W_TextureCoordinate {
/*    */   public final float u;
/*    */   public final float v;
/*    */   public final float w;
/*    */   
/*    */   public W_TextureCoordinate(float u, float v) {
/* 13 */     this(u, v, 0.0F);
/*    */   }
/*    */   
/*    */   public W_TextureCoordinate(float u, float v, float w) {
/* 17 */     this.u = u;
/* 18 */     this.v = v;
/* 19 */     this.w = w;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\modelloader\W_TextureCoordinate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */