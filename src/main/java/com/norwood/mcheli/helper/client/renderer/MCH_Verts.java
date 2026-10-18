/*    */ package com.norwood.mcheli.helper.client.renderer;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*    */ 
/*    */ 
/*    */ public class MCH_Verts
/*    */ {
/* 10 */   public static final VertexFormatElement TEX_2S = new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.UV, 2);
/* 11 */   public static final VertexFormat POS_COLOR_LMAP = (new VertexFormat())
/* 12 */     .addElement(DefaultVertexFormats.POSITION_3F)
/* 13 */     .addElement(DefaultVertexFormats.COLOR_4UB)
/* 14 */     .addElement(TEX_2S);
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\renderer\MCH_Verts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */