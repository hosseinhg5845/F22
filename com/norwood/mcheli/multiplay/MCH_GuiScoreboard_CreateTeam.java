/*     */ package com.norwood.mcheli.multiplay;
/*     */ 
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.W_ScaledResolution;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCH_GuiScoreboard_CreateTeam
/*     */   extends MCH_GuiScoreboard_Base
/*     */ {
/*  16 */   private static final String[] colorNames = new String[] { "RESET", "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean friendlyFire = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiButton buttonCreateTeamOK;
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiButton buttonCreateTeamFF;
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiTextField editCreateTeamName;
/*     */ 
/*     */ 
/*     */   
/*  38 */   private int lastTeamColor = 0;
/*     */   
/*     */   public MCH_GuiScoreboard_CreateTeam(MCH_IGuiScoreboard switcher, EntityPlayer player) {
/*  41 */     super(switcher, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  46 */     super.func_73866_w_();
/*  47 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/*  48 */     int factor = (w_ScaledResolution.func_78325_e() > 0) ? w_ScaledResolution.func_78325_e() : 1;
/*  49 */     this.field_147003_i = 0;
/*  50 */     this.field_147009_r = 0;
/*  51 */     int x = this.field_146297_k.field_71443_c / 2 / factor;
/*  52 */     int y = this.field_146297_k.field_71440_d / 2 / factor;
/*  53 */     GuiButton buttonCTNextC = new GuiButton(576, x + 40, y - 20, 40, 20, ">");
/*  54 */     GuiButton buttonCTPrevC = new GuiButton(577, x - 80, y - 20, 40, 20, "<");
/*  55 */     this.buttonCreateTeamFF = new GuiButton(560, x - 80, y + 20, 160, 20, "");
/*  56 */     this.buttonCreateTeamOK = new GuiButton(528, x - 80, y + 60, 80, 20, "OK");
/*  57 */     GuiButton buttonCTCancel = new GuiButton(544, x, y + 60, 80, 20, "Cancel");
/*  58 */     this.editCreateTeamName = new GuiTextField(599, this.field_146289_q, x - 80, y - 55, 160, 20);
/*  59 */     this.editCreateTeamName.func_146180_a("");
/*  60 */     this.editCreateTeamName.func_146193_g(-1);
/*  61 */     this.editCreateTeamName.func_146203_f(16);
/*  62 */     this.editCreateTeamName.func_146195_b(true);
/*  63 */     this.listGui.add(buttonCTNextC);
/*  64 */     this.listGui.add(buttonCTPrevC);
/*  65 */     this.listGui.add(this.buttonCreateTeamFF);
/*  66 */     this.listGui.add(this.buttonCreateTeamOK);
/*  67 */     this.listGui.add(buttonCTCancel);
/*  68 */     this.listGui.add(this.editCreateTeamName);
/*     */   }
/*     */   
/*     */   public void func_73876_c() {
/*  72 */     String teamName = this.editCreateTeamName.func_146179_b();
/*  73 */     this.buttonCreateTeamOK.field_146124_l = (!teamName.isEmpty() && teamName.length() <= 16);
/*  74 */     this.editCreateTeamName.func_146178_a();
/*  75 */     this.buttonCreateTeamFF.field_146126_j = "Friendly Fire : " + (friendlyFire ? "ON" : "OFF");
/*     */   }
/*     */ 
/*     */   
/*     */   public void acviveScreen() {
/*  80 */     this.editCreateTeamName.func_146180_a("");
/*  81 */     this.editCreateTeamName.func_146195_b(true);
/*     */   }
/*     */   
/*     */   protected void func_73869_a(char c, int code) {
/*  85 */     if (code == 1) {
/*  86 */       switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
/*     */     } else {
/*  88 */       this.editCreateTeamName.func_146201_a(c, code);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  93 */     this.editCreateTeamName.func_146192_a(mouseX, mouseY, mouseButton);
/*  94 */     super.func_73864_a(mouseX, mouseY, mouseButton);
/*     */   }
/*     */   
/*     */   protected void func_146284_a(@NotNull GuiButton btn) {
/*  98 */     if (btn.field_146124_l) {
/*  99 */       String teamName; switch (btn.field_146127_k) {
/*     */         case 528:
/* 101 */           teamName = this.editCreateTeamName.func_146179_b();
/* 102 */           if (!teamName.isEmpty() && teamName.length() <= 16) {
/* 103 */             MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams add " + teamName);
/* 104 */             MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams option " + teamName + " color " + colorNames[this.lastTeamColor]);
/* 105 */             MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams option " + teamName + " friendlyfire " + friendlyFire);
/*     */           } 
/*     */           
/* 108 */           switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
/*     */           break;
/*     */         case 544:
/* 111 */           switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
/*     */           break;
/*     */         case 560:
/* 114 */           friendlyFire = !friendlyFire;
/*     */           break;
/*     */         case 576:
/* 117 */           this.lastTeamColor++;
/* 118 */           if (this.lastTeamColor >= colorNames.length) {
/* 119 */             this.lastTeamColor = 0;
/*     */           }
/*     */           break;
/*     */         case 577:
/* 123 */           this.lastTeamColor--;
/* 124 */           if (this.lastTeamColor < 0) {
/* 125 */             this.lastTeamColor = colorNames.length - 1;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_146976_a(float par1, int par2, int par3) {
/* 133 */     drawList(this.field_146297_k, this.field_146289_q, true);
/* 134 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/* 135 */     int factor = (w_ScaledResolution.func_78325_e() > 0) ? w_ScaledResolution.func_78325_e() : 1;
/* 136 */     W_McClient.MOD_bindTexture("textures/gui/mp_new_team.png");
/* 137 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 138 */     int x = (this.field_146297_k.field_71443_c / factor - 222) / 2;
/* 139 */     int y = (this.field_146297_k.field_71440_d / factor - 200) / 2;
/* 140 */     func_73729_b(x, y, 0, 0, 222, 200);
/* 141 */     x = this.field_146297_k.field_71443_c / 2 / factor;
/* 142 */     y = this.field_146297_k.field_71440_d / 2 / factor;
/* 143 */     drawCenteredString("Create team", x, y - 85, -1);
/* 144 */     drawCenteredString("Team name", x, y - 70, -1);
/* 145 */     TextFormatting ecf = TextFormatting.func_96300_b(colorNames[this.lastTeamColor]);
/* 146 */     drawCenteredString(ecf + "Team Color" + ecf, x, y - 13, -1);
/* 147 */     this.editCreateTeamName.func_146194_f();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_GuiScoreboard_CreateTeam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */