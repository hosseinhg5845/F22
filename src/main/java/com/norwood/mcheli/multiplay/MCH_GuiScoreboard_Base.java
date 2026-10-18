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
/*  41 */     this.mc = Minecraft.getMinecraft();
/*     */   }
/*     */   public static final int BUTTON_ID_CREATE_TEAM_PREV_C = 577; public static final int BUTTON_ID_JUMP_SPAWN_POINT = 768; public static final int BUTTON_ID_SWITCH_PVP = 1024; public static final int BUTTON_ID_DESTORY_ALL = 1280; public List<Gui> listGui; private final MCH_IGuiScoreboard screen_switcher;
/*     */   public static void setVisible(Object g, boolean v) {
/*  45 */     if (g instanceof GuiButton) {
/*  46 */       ((GuiButton)g).visible = v;
/*     */     }
/*     */     
/*  49 */     if (g instanceof GuiTextField) {
/*  50 */       ((GuiTextField)g).setVisible(v);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getScoreboradWidth(Minecraft mc) {
/*  55 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
/*  56 */     int ScaledWidth = w_ScaledResolution.getScaledWidth() - 40;
/*  57 */     int width = ScaledWidth * 3 / 4 / (mc.world.getScoreboard().getTeams().size() + 1);
/*  58 */     if (width > 150) {
/*  59 */       width = 150;
/*     */     }
/*     */     
/*  62 */     return width;
/*     */   }
/*     */   
/*     */   public static int getScoreBoardLeft(Minecraft mc, int teamNum, int teamIndex) {
/*  66 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
/*  67 */     int ScaledWidth = w_ScaledResolution.getScaledWidth();
/*  68 */     return (int)(ScaledWidth / 2.0D + (getScoreboradWidth(mc) + 10) * (-teamNum / 2.0D + teamIndex));
/*     */   }
/*     */   
/*     */   public static void drawList(Minecraft mc, FontRenderer fontRendererObj, boolean mng) {
/*  72 */     ArrayList<ScorePlayerTeam> teamList = new ArrayList<>();
/*  73 */     teamList.add(null);
/*     */     
/*  75 */     teamList.addAll(mc.world.getScoreboard().getTeams());
/*     */     
/*  77 */     teamList.sort((o1, o2) -> 
/*  78 */         (o1 == null && o2 == null) ? 0 : ((o1 == null) ? -1 : ((o2 == null) ? 1 : o1.getName().compareTo(o2.getName()))));
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
/*  97 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
/*  98 */     int ScaledHeight = w_ScaledResolution.getScaledHeight();
/*  99 */     ScoreObjective scoreobjective = mc.world.getScoreboard().getObjectiveInDisplaySlot(0);
/* 100 */     NetHandlerPlayClient nethandlerplayclient = mc.player.connection;
/* 101 */     List<NetworkPlayerInfo> list = Lists.newArrayList(nethandlerplayclient.getPlayerInfoMap());
/* 102 */     int MaxPlayers = (list.size() / 5 + 1) * 5;
/* 103 */     MaxPlayers = Math.max(MaxPlayers, 10);
/* 104 */     if (MaxPlayers > nethandlerplayclient.currentServerMaxPlayers) {
/* 105 */       MaxPlayers = nethandlerplayclient.currentServerMaxPlayers;
/*     */     }
/*     */     
/* 108 */     int width = getScoreboradWidth(mc);
/* 109 */     int listLeft = getScoreBoardLeft(mc, teamNum, teamIndex);
/* 110 */     int listTop = ScaledHeight / 2 - (MaxPlayers * 9 + 10) / 2;
/* 111 */     drawRect(listLeft - 1, listTop - 1 - 18, listLeft + width, listTop + 9 * MaxPlayers, -2147483648);
/* 112 */     String teamName = ScorePlayerTeam.formatPlayerName((Team)team, (team == null) ? "No team" : team.getName());
/* 113 */     int teamNameX = listLeft + width / 2 - fontRendererObj.getStringWidth(teamName) / 2;
/* 114 */     fontRendererObj.drawStringWithShadow(teamName, teamNameX, (listTop - 18), -1);
/* 115 */     String ff_onoff = "FriendlyFire : " + ((team == null) ? "ON" : (team.getAllowFriendlyFire() ? "ON" : "OFF"));
/* 116 */     int ff_onoffX = listLeft + width / 2 - fontRendererObj.getStringWidth(ff_onoff) / 2;
/* 117 */     fontRendererObj.drawStringWithShadow(ff_onoff, ff_onoffX, (listTop - 9), -1);
/* 118 */     int drawY = 0;
/*     */     
/* 120 */     for (int i = 0; i < MaxPlayers; i++) {
/* 121 */       int y = listTop + drawY * 9;
/* 122 */       int rectY = listTop + i * 9;
/* 123 */       drawRect(listLeft, rectY, listLeft + width - 1, rectY + 8, 553648127);
/* 124 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 125 */       GL11.glEnable(3008);
/* 126 */       if (i < list.size()) {
/* 127 */         NetworkPlayerInfo guiplayerinfo = list.get(i);
/* 128 */         String playerName = guiplayerinfo.getGameProfile().getName();
/* 129 */         ScorePlayerTeam steam = mc.world.getScoreboard().getPlayersTeam(playerName);
/* 130 */         if ((steam == null && team == null) || (steam != null && team != null && steam.isSameTeam((Team)team))) {
/* 131 */           drawY++;
/* 132 */           fontRendererObj.drawStringWithShadow(playerName, listLeft, y, -1);
/* 133 */           if (scoreobjective != null) {
/* 134 */             int j4 = listLeft + fontRendererObj.getStringWidth(playerName) + 5;
/* 135 */             int k4 = listLeft + width - 12 - 5;
/* 136 */             if (k4 - j4 > 5) {
/* 137 */               Score score = scoreobjective.getScoreboard().getOrCreateScore(guiplayerinfo.getGameProfile().getName(), scoreobjective);
/* 138 */               String s1 = TextFormatting.YELLOW + "" + score.getScorePoints();
/* 139 */               fontRendererObj.drawStringWithShadow(s1, (k4 - fontRendererObj.getStringWidth(s1)), y, 16777215);
/*     */             } 
/*     */           } 
/*     */           
/* 143 */           drawResponseTime(listLeft + width - 12, y, guiplayerinfo.getResponseTime());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public static void drawResponseTime(int x, int y, int responseTime) {
/*     */     byte b2;
/* 150 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 151 */     Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);
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
/* 171 */     Tessellator tessellator = Tessellator.getInstance();
/* 172 */     BufferBuilder builder = tessellator.getBuffer();
/* 173 */     builder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 174 */     builder.pos(x, (y + y3), zLevel).tex((x2 * 0.00390625F), ((y2 + y3) * 0.00390625F)).endVertex();
/* 175 */     builder.pos((x + x3), (y + y3), zLevel).tex(((x2 + x3) * 0.00390625F), ((y2 + y3) * 0.00390625F)).endVertex();
/* 176 */     builder.pos((x + x3), y, zLevel).tex(((x2 + x3) * 0.00390625F), (y2 * 0.00390625F)).endVertex();
/* 177 */     builder.pos(x, y, zLevel).tex((x2 * 0.00390625F), (y2 * 0.00390625F)).endVertex();
/* 178 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {}
/*     */   
/*     */   public void initGui(List<GuiButton> buttonList, GuiScreen parents) {
/* 185 */     this.listGui = new ArrayList<>();
/* 186 */     this.mc = Minecraft.getMinecraft();
/* 187 */     this.fontRenderer = this.mc.fontRenderer;
/* 188 */     this.width = parents.width;
/* 189 */     this.height = parents.height;
/* 190 */     initGui();
/*     */     
/* 192 */     for (Gui b : this.listGui) {
/* 193 */       if (b instanceof GuiButton) {
/* 194 */         buttonList.add((GuiButton)b);
/*     */       }
/*     */     } 
/*     */     
/* 198 */     this.buttonList.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreenButtons(List<GuiButton> list) {}
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {}
/*     */   
/*     */   public int getTeamNum() {
/* 208 */     return this.mc.world.getScoreboard().getTeams().size();
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
/* 229 */     keyTyped(c, code);
/*     */   }
/*     */   
/*     */   public void mouseClickedScreen(int mouseX, int mouseY, int mouseButton) throws IOException {
/*     */     try {
/* 234 */       mouseClicked(mouseX, mouseY, mouseButton);
/* 235 */     } catch (Exception var7) {
/* 236 */       if (mouseButton == 0) {
/* 237 */         for (GuiButton guibutton : this.buttonList) {
/* 238 */           if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
/* 239 */             guibutton.playPressSound(this.mc.getSoundHandler());
/* 240 */             actionPerformed(guibutton);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawGuiContainerForegroundLayerScreen(int param1, int param2) {
/* 248 */     drawGuiContainerForegroundLayer(param1, param2);
/*     */   }
/*     */   
/*     */   protected void actionPerformedScreen(GuiButton btn) throws IOException {
/* 252 */     actionPerformed(btn);
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