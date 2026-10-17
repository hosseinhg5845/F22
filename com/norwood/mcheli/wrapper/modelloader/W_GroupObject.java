/*    */ package com.norwood.mcheli.wrapper.modelloader;
/*    */ 
/*    */ import com.google.common.base.Joiner;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class W_GroupObject
/*    */ {
/*    */   public final String name;
/* 15 */   public final ArrayList<W_Face> faces = new ArrayList<>();
/*    */   public int glDrawingMode;
/*    */   
/*    */   public W_GroupObject() {
/* 19 */     this("");
/*    */   }
/*    */   
/*    */   public W_GroupObject(String name) {
/* 23 */     this(name, -1);
/*    */   }
/*    */   
/*    */   public W_GroupObject(String name, int glDrawingMode) {
/* 27 */     this.name = name;
/* 28 */     this.glDrawingMode = glDrawingMode;
/*    */   }
/*    */   
/*    */   public void render() {
/* 32 */     if (!this.faces.isEmpty()) {
/* 33 */       Tessellator tessellator = Tessellator.func_178181_a();
/* 34 */       BufferBuilder builder = tessellator.func_178180_c();
/* 35 */       builder.func_181668_a(this.glDrawingMode, DefaultVertexFormats.field_181710_j);
/* 36 */       render(tessellator);
/* 37 */       tessellator.func_78381_a();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void render(Tessellator tessellator) {
/* 42 */     if (!this.faces.isEmpty()) {
/* 43 */       for (W_Face face : this.faces) {
/* 44 */         face.addFaceForRender(tessellator);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 51 */     return "W_GroupObject[size=" + this.faces.size() + ",values=[" + Joiner.on('\n').join(this.faces) + "]]";
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\modelloader\W_GroupObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */