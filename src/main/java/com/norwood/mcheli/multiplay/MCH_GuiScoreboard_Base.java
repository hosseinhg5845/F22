/*     */ package com.norwood.mcheli.multiplay;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.norwood.mcheli.wrapper.W_GuiContainer;
/*     */ import com.norwood.mcheli.wrapper.W_ScaledResolution;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public abstract class MCH_GuiScoreboard_Base
/*     */   extends W_GuiContainer
/*     */ {
/*     */   public static final int BUTTON_ID_SHUFFLE = 256;
/*     */   public static final int BUTTON_ID_CREATE_TEAM = 512;
/*     */   public static final int BUTTON_ID_CREATE_TEAM_OK = 528;
/*     */   public static final int BUTTON_ID_CREATE_TEAM_CANCEL = 544;
/*     */   public static final int BUTTON_ID_CREATE_TEAM_FF = 560;
/*     */   public static final int BUTTON_ID_CREATE_TEAM_NEXT_C = 576;
/*     */   
/*     */   public MCH_GuiScoreboard_Base(MCH_IGuiScoreboard switcher, EntityPlayer player) {
/*  39 */     super(new MCH_ContainerScoreboard(player));
/*  40 */     this.screen_switcher = switcher;
/*  41 */     this.field_146297_k = Minecraft.func_71410_x();
/*     */   }
/*     */   public static final int BUTTON_ID_CREATE_TEAM_PREV_C = 577; public static final int BUTTON_ID_JUMP_SPAWN_POINT = 768; public static final int BUTTON_ID_SWITCH_PVP = 1024; public static final int BUTTON_ID_DESTORY_ALL = 1280; public List<Gui> listGui; private final MCH_IGuiScoreboard screen_switcher;
/*     */   public static void setVisible(Object g, boolean v) {
/*  45 */     if (g instanceof GuiButton) {
/*  46 */       ((GuiButton)g).field_146125_m = v;
/*     */     }
/*     */     
/*  49 */     if (g instanceof GuiTextField) {
/*  50 */       ((GuiTextField)g).func_146189_e(v);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getScoreboradWidth(Minecraft mc) {
/*  55 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
/*  56 */     int ScaledWidth = w_ScaledResolution.func_78326_a() - 40;
/*  57 */     int width = ScaledWidth * 3 / 4 / (mc.field_71441_e.func_96441_U().func_96525_g().size() + 1);
/*  58 */     if (width > 150) {
/*  59 */       width = 150;
/*     */     }
/*     */     
/*  62 */     return width;
/*     */   }
/*     */   
/*     */   public static int getScoreBoardLeft(Minecraft mc, int teamNum, int teamIndex) {
/*  66 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
/*  67 */     int ScaledWidth = w_ScaledResolution.func_78326_a();
/*  68 */     return (int)(ScaledWidth / 2.0D + (getScoreboradWidth(mc) + 10) * (-teamNum / 2.0D + teamIndex));
/*     */   }
/*     */   
/*     */   public static void drawList(Minecraft mc, FontRenderer fontRendererObj, boolean mng) {
/*  72 */     ArrayList<ScorePlayerTeam> teamList = new ArrayList<>();
/*  73 */     teamList.add(null);
/*     */     
/*  75 */     teamList.addAll(mc.field_71441_e.func_96441_U().func_96525_g());
/*     */     
/*  77 */     teamList.sort((o1, o2) -> 
/*  78 */         (o1 == null && o2 == null) ? 0 : ((o1 == null) ? -1 : ((o2 == null) ? 1 : o1.func_96661_b().compareTo(o2.func_96661_b()))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     for (int i = 0; i < teamList.size(); i++) {
/*  88 */       if (mng) {
/*  89 */         drawPlayersList(mc, fontRendererObj, teamList.get(i), 1 + i, 1 + teamList.size());
/*     */       } else {
/*  91 */         drawPlayersList(mc, fontRendererObj, teamList.get(i), i, teamList.size());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawPlayersList(Minecraft mc, FontRenderer fontRendererObj, ScorePlayerTeam team, int teamIndex, int teamNum) {
/*  97 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
/*  98 */     int ScaledHeight = w_ScaledResolution.func_78328_b();
/*  99 */     ScoreObjective scoreobjective = mc.field_71441_e.func_96441_U().func_96539_a(0);
/* 100 */     NetHandlerPlayClient nethandlerplayclient = mc.field_71439_g.field_71174_a;
/* 101 */     List<NetworkPlayerInfo> list = Lists.newArrayList(nethandlerplayclient.func_175106_d());
/* 102 */     int MaxPlayers = (list.size() / 5 + 1) * 5;
/* 103 */     MaxPlayers = Math.max(MaxPlayers, 10);
/* 104 */     if (MaxPlayers > nethandlerplayclient.field_147304_c) {
/* 105 */       MaxPlayers = nethandlerplayclient.field_147304_c;
/*     */     }
/*     */     
/* 108 */     int width = getScoreboradWidth(mc);
/* 109 */     int listLeft = getScoreBoardLeft(mc, teamNum, teamIndex);
/* 110 */     int listTop = ScaledHeight / 2 - (MaxPlayers * 9 + 10) / 2;
/* 111 */     func_73734_a(listLeft - 1, listTop - 1 - 18, listLeft + width, listTop + 9 * MaxPlayers, -2147483648);
/* 112 */     String teamName = ScorePlayerTeam.func_96667_a((Team)team, (team == null) ? "No team" : team.func_96661_b());
/* 113 */     int teamNameX = listLeft + width / 2 - fontRendererObj.func_78256_a(teamName) / 2;
/* 114 */     fontRendererObj.func_175063_a(teamName, teamNameX, (listTop - 18), -1);
/* 115 */     String ff_onoff = "FriendlyFire : " + ((team == null) ? "ON" : (team.func_96665_g() ? "ON" : "OFF"));
/* 116 */     int ff_onoffX = listLeft + width / 2 - fontRendererObj.func_78256_a(ff_onoff) / 2;
/* 117 */     fontRendererObj.func_175063_a(ff_onoff, ff_onoffX, (listTop - 9), -1);
/* 118 */     int drawY = 0;
/*     */     
/* 120 */     for (int i = 0; i < MaxPlayers; i++) {
/* 121 */       int y = listTop + drawY * 9;
/* 122 */       int rectY = listTop + i * 9;
/* 123 */       func_73734_a(listLeft, rectY, listLeft + width - 1, rectY + 8, 553648127);
/* 124 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 125 */       GL11.glEnable(3008);
/* 126 */       if (i < list.size()) {
/* 127 */         NetworkPlayerInfo guiplayerinfo = list.get(i);
/* 128 */         String playerName = guiplayerinfo.func_178845_a().getName();
/* 129 */         ScorePlayerTeam steam = mc.field_71441_e.func_96441_U().func_96509_i(playerName);
/* 130 */         if ((steam == null && team == null) || (steam != null && team != null && steam.func_142054_a((Team)team))) {
/* 131 */           drawY++;
/* 132 */           fontRendererObj.func_175063_a(playerName, listLeft, y, -1);
/* 133 */           if (scoreobjective != null) {
/* 134 */             int j4 = listLeft + fontRendererObj.func_78256_a(playerName) + 5;
/* 135 */             int k4 = listLeft + width - 12 - 5;
/* 136 */             if (k4 - j4 > 5) {
/* 137 */               Score score = scoreobjective.func_96682_a().func_96529_a(guiplayerinfo.func_178845_a().getName(), scoreobjective);
/* 138 */               String s1 = TextFormatting.YELLOW + "" + score.func_96652_c();
/* 139 */               fontRendererObj.func_175063_a(s1, (k4 - fontRendererObj.func_78256_a(s1)), y, 16777215);
/*     */             } 
/*     */           } 
/*     */           
/* 143 */           drawResponseTime(listLeft + width - 12, y, guiplayerinfo.func_178853_c());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public static void drawResponseTime(int x, int y, int responseTime) {
/*     */     byte b2;
/* 150 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 151 */     Minecraft.func_71410_x().func_110434_K().func_110577_a(field_110324_m);
/*     */     
/* 153 */     if (responseTime < 0) {
/* 154 */       b2 = 5;
/* 155 */     } else if (responseTime < 150) {
/* 156 */       b2 = 0;
/* 157 */     } else if (responseTime < 300) {
/* 158 */       b2 = 1;
/* 159 */     } else if (responseTime < 600) {
/* 160 */       b2 = 2;
/* 161 */     } else if (responseTime < 1000) {
/* 162 */       b2 = 3;
/*     */     } else {
/* 164 */       b2 = 4;
/*     */     } 
/*     */     
/* 167 */     static_drawTexturedModalRect(x, y, 0, 176 + b2 * 8, 10, 8, 0.0D);
/*     */   }
/*     */   
/*     */   public static void static_drawTexturedModalRect(int x, int y, int x2, int y2, int x3, int y3, double zLevel) {
/* 171 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 172 */     BufferBuilder builder = tessellator.func_178180_c();
/* 173 */     builder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 174 */     builder.func_181662_b(x, (y + y3), zLevel).func_187315_a((x2 * 0.00390625F), ((y2 + y3) * 0.00390625F)).func_181675_d();
/* 175 */     builder.func_181662_b((x + x3), (y + y3), zLevel).func_187315_a(((x2 + x3) * 0.00390625F), ((y2 + y3) * 0.00390625F)).func_181675_d();
/* 176 */     builder.func_181662_b((x + x3), y, zLevel).func_187315_a(((x2 + x3) * 0.00390625F), (y2 * 0.00390625F)).func_181675_d();
/* 177 */     builder.func_181662_b(x, y, zLevel).func_187315_a((x2 * 0.00390625F), (y2 * 0.00390625F)).func_181675_d();
/* 178 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {}
/*     */   
/*     */   public void initGui(List<GuiButton> buttonList, GuiScreen parents) {
/* 185 */     this.listGui = new ArrayList<>();
/* 186 */     this.field_146297_k = Minecraft.func_71410_x();
/* 187 */     this.field_146289_q = this.field_146297_k.field_71466_p;
/* 188 */     this.field_146294_l = parents.field_146294_l;
/* 189 */     this.field_146295_m = parents.field_146295_m;
/* 190 */     func_73866_w_();
/*     */     
/* 192 */     for (Gui b : this.listGui) {
/* 193 */       if (b instanceof GuiButton) {
/* 194 */         buttonList.add((GuiButton)b);
/*     */       }
/*     */     } 
/*     */     
/* 198 */     this.field_146292_n.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreenButtons(List<GuiButton> list) {}
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {}
/*     */   
/*     */   public int getTeamNum() {
/* 208 */     return this.field_146297_k.field_71441_e.func_96441_U().func_96525_g().size();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void acviveScreen() {}
/*     */   
/*     */   public void onSwitchScreen() {
/* 215 */     for (Gui b : this.listGui) {
/* 216 */       setVisible(b, true);
/*     */     }
/*     */     
/* 219 */     acviveScreen();
/*     */   }
/*     */   
/*     */   public void leaveScreen() {
/* 223 */     for (Gui b : this.listGui) {
/* 224 */       setVisible(b, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void keyTypedScreen(char c, int code) throws IOException {
/* 229 */     func_73869_a(c, code);
/*     */   }
/*     */   
/*     */   public void mouseClickedScreen(int mouseX, int mouseY, int mouseButton) throws IOException {
/*     */     try {
/* 234 */       func_73864_a(mouseX, mouseY, mouseButton);
/* 235 */     } catch (Exception var7) {
/* 236 */       if (mouseButton == 0) {
/* 237 */         for (GuiButton guibutton : this.field_146292_n) {
/* 238 */           if (guibutton.func_146116_c(this.field_146297_k, mouseX, mouseY)) {
/* 239 */             guibutton.func_146113_a(this.field_146297_k.func_147118_V());
/* 240 */             func_146284_a(guibutton);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawGuiContainerForegroundLayerScreen(int param1, int param2) {
/* 248 */     func_146979_b(param1, param2);
/*     */   }
/*     */   
/*     */   protected void actionPerformedScreen(GuiButton btn) throws IOException {
/* 252 */     func_146284_a(btn);
/*     */   }
/*     */   
/*     */   public void switchScreen(SCREEN_ID id) {
/* 256 */     this.screen_switcher.switchScreen(id);
/*     */   }
/*     */   
/*     */   public enum SCREEN_ID {
/* 260 */     MAIN,
/* 261 */     CREATE_TEAM;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_GuiScoreboard_Base.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */