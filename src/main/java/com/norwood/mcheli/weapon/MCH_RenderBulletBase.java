/*    */ package com.norwood.mcheli.weapon;
/*    */ import com.norwood.mcheli.MCH_Color;
/*    */ import com.norwood.mcheli.wrapper.W_Block;
/*    */ import com.norwood.mcheli.wrapper.W_Entity;
/*    */ import com.norwood.mcheli.wrapper.W_Render;
/*    */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public abstract class MCH_RenderBulletBase<T extends W_Entity> extends W_Render<T> {
/*    */   protected MCH_RenderBulletBase(RenderManager renderManager) {
/* 15 */     super(renderManager);
/*    */   }
/*    */   
/*    */   public void doRender(@NotNull T e, double var2, double var4, double var6, float var8, float var9) {
/* 19 */     if (e instanceof MCH_EntityBaseBullet && ((MCH_EntityBaseBullet)e).getInfo() != null) {
/* 20 */       MCH_Color c = (((MCH_EntityBaseBullet)e).getInfo()).color;
/*    */       
/* 22 */       for (int y = 0; y < 3; y++) {
/* 23 */         Block b = W_WorldFunc.getBlock(((W_Entity)e).field_70170_p, (int)(((W_Entity)e).field_70165_t + 0.5D), (int)(((W_Entity)e).field_70163_u + 1.5D - y), (int)(((W_Entity)e).field_70161_v + 0.5D));
/* 24 */         if (b == W_Block.getWater()) {
/* 25 */           c = (((MCH_EntityBaseBullet)e).getInfo()).colorInWater;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 30 */       GL11.glColor4f(c.r, c.g, c.b, c.a);
/*    */     } else {
/* 32 */       GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/*    */     } 
/*    */     
/* 35 */     GL11.glAlphaFunc(516, 0.001F);
/* 36 */     GL11.glEnable(2884);
/* 37 */     GL11.glEnable(3042);
/* 38 */     int srcBlend = GL11.glGetInteger(3041);
/* 39 */     int dstBlend = GL11.glGetInteger(3040);
/* 40 */     GL11.glBlendFunc(770, 771);
/* 41 */     renderBullet(e, var2, var4, var6, var8, var9);
/* 42 */     GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/* 43 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 44 */     GL11.glDisable(3042);
/*    */   }
/*    */   
/*    */   public void renderModel(MCH_EntityBaseBullet e) {
/* 48 */     MCH_BulletModel model = e.getBulletModel();
/* 49 */     if (model != null) {
/* 50 */       bindTexture("textures/bullets/" + model.name + ".png");
/* 51 */       model.model.renderAll();
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract void renderBullet(T paramT, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2);
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_RenderBulletBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */