/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftCommonGui;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_GuiCommon extends MCH_AircraftCommonGui {
/* 12 */   public int hitCount = 0;
/*    */   
/*    */   public MCH_GuiCommon(Minecraft minecraft) {
/* 15 */     super(minecraft);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDrawGui(EntityPlayer player) {
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/* 25 */     GL11.glLineWidth(scaleFactor);
/* 26 */     drawHitMarker(this.hitCount, 15, -805306369);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 31 */     super.onTick();
/* 32 */     if (this.hitCount > 0) {
/* 33 */       this.hitCount--;
/*    */     }
/*    */   }
/*    */   
/*    */   public void hitBullet() {
/* 38 */     this.hitCount = 15;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_GuiCommon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */