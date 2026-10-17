/*    */ package com.norwood.mcheli.tool;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import com.norwood.mcheli.gui.MCH_Gui;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_GuiWrench extends MCH_Gui {
/*    */   public MCH_GuiWrench(Minecraft minecraft) {
/* 14 */     super(minecraft);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_73866_w_() {
/* 19 */     super.func_73866_w_();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDrawGui(EntityPlayer player) {
/* 24 */     return (player != null && player.field_70170_p != null && 
/*    */       
/* 26 */       !player.func_184614_ca().func_190926_b() && player
/* 27 */       .func_184614_ca().func_77973_b() instanceof MCH_ItemWrench);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/* 32 */     if (!isThirdPersonView) {
/* 33 */       GL11.glLineWidth(scaleFactor);
/* 34 */       if (isDrawGui(player)) {
/* 35 */         GL11.glDisable(3042);
/* 36 */         MCH_EntityAircraft ac = ((MCH_ItemWrench)player.func_184614_ca().func_77973_b()).getMouseOverAircraft((EntityLivingBase)player);
/* 37 */         if (ac != null && ac.getMaxHP() > 0) {
/* 38 */           int color = ((ac.getHP() / ac.getMaxHP()) > 0.3D) ? -14101432 : -2161656;
/* 39 */           drawHP(color, -15433180, ac.getHP(), ac.getMaxHP());
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   void drawHP(int color, int colorBG, int hp, int hpmax) {
/* 46 */     int posX = this.centerX;
/* 47 */     int posY = this.centerY + 20;
/* 48 */     func_73734_a(posX - 20, posY + 20 + 1, posX - 20 + 40, posY + 20 + 1 + 1 + 3 + 1, colorBG);
/* 49 */     if (hp > hpmax) {
/* 50 */       hp = hpmax;
/*    */     }
/*    */     
/* 53 */     float hpp = hp / hpmax;
/* 54 */     func_73734_a(posX - 20 + 1, posY + 20 + 1 + 1, posX - 20 + 1 + (int)(38.0D * hpp), posY + 20 + 1 + 1 + 3, color);
/* 55 */     int hppn = (int)(hpp * 100.0F);
/* 56 */     if (hp < hpmax && hppn >= 100) {
/* 57 */       hppn = 99;
/*    */     }
/*    */     
/* 60 */     drawCenteredString(String.format("%d %%", new Object[] { Integer.valueOf(hppn) }), posX, posY + 30, color);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tool\MCH_GuiWrench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */