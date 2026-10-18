/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_EntityRenderer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.ItemRenderer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_ItemRendererDummy
/*    */   extends ItemRenderer
/*    */ {
/*    */   protected static Minecraft mc;
/*    */   protected static ItemRenderer backupItemRenderer;
/*    */   protected static MCH_ItemRendererDummy instance;
/*    */   
/*    */   public MCH_ItemRendererDummy(Minecraft par1Minecraft) {
/* 19 */     super(par1Minecraft);
/* 20 */     mc = par1Minecraft;
/*    */   }
/*    */   
/*    */   public static void enableDummyItemRenderer() {
/* 24 */     if (instance == null) {
/* 25 */       instance = new MCH_ItemRendererDummy(Minecraft.getMinecraft());
/*    */     }
/*    */     
/* 28 */     if (!(mc.entityRenderer.itemRenderer instanceof MCH_ItemRendererDummy)) {
/* 29 */       backupItemRenderer = mc.entityRenderer.itemRenderer;
/*    */     }
/*    */     
/* 32 */     W_EntityRenderer.setItemRenderer(mc, instance);
/*    */   }
/*    */   
/*    */   public static void disableDummyItemRenderer() {
/* 36 */     if (backupItemRenderer != null) {
/* 37 */       W_EntityRenderer.setItemRenderer(mc, backupItemRenderer);
/*    */     }
/*    */   }
/*    */   
/*    */   public void renderItemInFirstPerson(float par1) {
/* 42 */     if (mc.player == null) {
/* 43 */       super.renderItemInFirstPerson(par1);
/* 44 */     } else if (!(mc.player.getRidingEntity() instanceof com.norwood.mcheli.aircraft.MCH_EntityAircraft) && 
/* 45 */       !(mc.player.getRidingEntity() instanceof com.norwood.mcheli.uav.MCH_EntityUavStation) && 
/* 46 */       !(mc.player.getRidingEntity() instanceof com.norwood.mcheli.gltd.MCH_EntityGLTD)) {
/* 47 */       super.renderItemInFirstPerson(par1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_ItemRendererDummy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */