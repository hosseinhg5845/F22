/*    */ package com.norwood.mcheli.block;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_Config;
/*    */ import com.norwood.mcheli.MCH_ModelManager;
/*    */ import com.norwood.mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class MCH_DraftingTableRenderer
/*    */   extends TileEntitySpecialRenderer<MCH_DraftingTableTileEntity>
/*    */ {
/*    */   @SideOnly(Side.CLIENT)
/*    */   public static DraftingTableStackRenderer getStackRenderer() {
/* 22 */     if (stackRenderer == null) {
/* 23 */       stackRenderer = new DraftingTableStackRenderer();
/*    */     }
/*    */     
/* 26 */     return stackRenderer;
/*    */   } @SideOnly(Side.CLIENT)
/*    */   private static DraftingTableStackRenderer stackRenderer;
/*    */   public void render(@NotNull MCH_DraftingTableTileEntity tile, double posX, double posY, double posZ, float partialTicks, int destroyStage, float alpha) {
/* 30 */     GL11.glPushMatrix();
/* 31 */     GL11.glEnable(2884);
/* 32 */     GL11.glTranslated(posX + 0.5D, posY, posZ + 0.5D);
/* 33 */     float yaw = getYawAngle(tile);
/* 34 */     GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
/* 35 */     RenderHelper.func_74519_b();
/* 36 */     GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/* 37 */     GL11.glEnable(3042);
/* 38 */     int srcBlend = GL11.glGetInteger(3041);
/* 39 */     int dstBlend = GL11.glGetInteger(3040);
/* 40 */     GL11.glBlendFunc(770, 771);
/* 41 */     if (MCH_Config.SmoothShading.prmBool) {
/* 42 */       GL11.glShadeModel(7425);
/*    */     }
/*    */     
/* 45 */     W_McClient.MOD_bindTexture("textures/blocks/drafting_table.png");
/* 46 */     MCH_ModelManager.render("blocks", "drafting_table");
/* 47 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 48 */     GL11.glDisable(3042);
/* 49 */     GL11.glShadeModel(7424);
/* 50 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   private float getYawAngle(MCH_DraftingTableTileEntity tile) {
/* 54 */     return tile.func_145830_o() ? (-tile.func_145832_p() * 45.0F + 180.0F) : 0.0F;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public static class DraftingTableStackRenderer extends TileEntityItemStackRenderer {
/* 59 */     private final MCH_DraftingTableTileEntity draftingTable = new MCH_DraftingTableTileEntity();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void func_192838_a(@NotNull ItemStack p_192838_1_, float partialTicks) {
/* 65 */       TileEntityRendererDispatcher.field_147556_a.func_192855_a(this.draftingTable, 0.0D, 0.0D, 0.0D, partialTicks, 0.0F);
/*    */     }
/*    */     
/*    */     private DraftingTableStackRenderer() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\block\MCH_DraftingTableRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */