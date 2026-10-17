/*    */ package com.norwood.mcheli.multiplay;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_ServerSettings;
/*    */ import com.norwood.mcheli.wrapper.W_GuiButton;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_GuiScoreboard_Main
/*    */   extends MCH_GuiScoreboard_Base {
/*    */   private W_GuiButton buttonSwitchPVP;
/*    */   
/*    */   public MCH_GuiScoreboard_Main(MCH_IGuiScoreboard switcher, EntityPlayer player) {
/* 15 */     super(switcher, player);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_73866_w_() {
/* 20 */     super.func_73866_w_();
/* 21 */     if (this.buttonSwitchPVP == null) {
/* 22 */       this.field_147003_i = 0;
/* 23 */       this.field_147009_r = 0;
/* 24 */       int WIDTH = getScoreboradWidth(this.field_146297_k) * 3 / 4;
/* 25 */       if (WIDTH < 80) {
/* 26 */         WIDTH = 80;
/*    */       }
/*    */       
/* 29 */       int LEFT = getScoreBoardLeft(this.field_146297_k, getTeamNum() + 1, 0) / 4;
/* 30 */       this.buttonSwitchPVP = new W_GuiButton(1024, LEFT, 80, WIDTH, 20, "");
/* 31 */       this.listGui.add(this.buttonSwitchPVP);
/* 32 */       W_GuiButton btn = new W_GuiButton(256, LEFT, 100, WIDTH, 20, "Team shuffle");
/* 33 */       btn.addHoverString("Shuffle all players.");
/* 34 */       this.listGui.add(btn);
/* 35 */       this.listGui.add(new W_GuiButton(512, LEFT, 120, WIDTH, 20, "New team"));
/* 36 */       btn = new W_GuiButton(768, LEFT, 140, WIDTH, 20, "Jump spawn pos");
/* 37 */       btn.addHoverString("Teleport all players -> spawn point.");
/* 38 */       this.listGui.add(btn);
/* 39 */       btn = new W_GuiButton(1280, LEFT, 160, WIDTH, 20, "Destroy All");
/* 40 */       btn.addHoverString("Destroy all aircraft and vehicle.");
/* 41 */       this.listGui.add(btn);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void func_73869_a(char c, int code) {
/* 46 */     if (code == 1) {
/* 47 */       this.field_146297_k.field_71439_g.func_71053_j();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateScreenButtons(List<GuiButton> list) {
/* 53 */     for (GuiButton o : list) {
/* 54 */       if (o.field_146127_k == 1024) {
/* 55 */         o.field_146126_j = "PVP : " + (MCH_ServerSettings.enablePVP ? "ON" : "OFF");
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void func_146284_a(@NotNull GuiButton btn) {
/* 61 */     if (btn.field_146124_l) {
/* 62 */       switch (btn.field_146127_k) {
/*    */         case 256:
/* 64 */           MCH_PacketIndMultiplayCommand.send(256, "");
/*    */           break;
/*    */         case 512:
/* 67 */           switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.CREATE_TEAM);
/*    */           break;
/*    */         case 768:
/* 70 */           MCH_PacketIndMultiplayCommand.send(512, "");
/*    */           break;
/*    */         case 1024:
/* 73 */           MCH_PacketIndMultiplayCommand.send(1024, "");
/*    */           break;
/*    */         case 1280:
/* 76 */           MCH_PacketIndMultiplayCommand.send(1280, "");
/*    */           break;
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public void drawGuiContainerForegroundLayerScreen(int x, int y) {
/* 83 */     super.drawGuiContainerForegroundLayerScreen(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float par1, int par2, int par3) {
/* 88 */     drawList(this.field_146297_k, this.field_146289_q, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_GuiScoreboard_Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */