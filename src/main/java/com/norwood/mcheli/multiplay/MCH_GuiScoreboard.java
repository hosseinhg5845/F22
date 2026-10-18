/*     */ package com.norwood.mcheli.multiplay;
/*     */ 
/*     */ import com.norwood.mcheli.wrapper.W_GuiButton;
/*     */ import com.norwood.mcheli.wrapper.W_GuiContainer;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCH_GuiScoreboard extends W_GuiContainer implements MCH_IGuiScoreboard {
/*     */   public final EntityPlayer thePlayer;
/*     */   private MCH_GuiScoreboard_Base.SCREEN_ID screenID;
/*     */   private Map<MCH_GuiScoreboard_Base.SCREEN_ID, MCH_GuiScoreboard_Base> listScreen;
/*  22 */   private int lastTeamNum = 0;
/*     */   
/*     */   public MCH_GuiScoreboard(EntityPlayer player) {
/*  25 */     super(new MCH_ContainerScoreboard(player));
/*  26 */     this.thePlayer = player;
/*     */   }
/*     */   
/*     */   public static void setVisible(Object g, boolean v) {
/*  30 */     if (g instanceof GuiButton) {
/*  31 */       ((GuiButton)g).visible = v;
/*     */     }
/*     */     
/*  34 */     if (g instanceof GuiTextField) {
/*  35 */       ((GuiTextField)g).setVisible(v);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawList(Minecraft mc, FontRenderer fontRendererObj, boolean mng) {
/*  40 */     MCH_GuiScoreboard_Base.drawList(mc, fontRendererObj, mng);
/*     */   }
/*     */   
/*     */   public void initGui() {
/*  44 */     Keyboard.enableRepeatEvents(true);
/*  45 */     super.initGui();
/*  46 */     this.buttonList.clear();
/*  47 */     this.labelList.clear();
/*  48 */     this.guiLeft = 0;
/*  49 */     this.guiTop = 0;
/*  50 */     this.listScreen = new HashMap<>();
/*  51 */     this.listScreen.put(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN, new MCH_GuiScoreboard_Main(this, this.thePlayer));
/*  52 */     this.listScreen.put(MCH_GuiScoreboard_Base.SCREEN_ID.CREATE_TEAM, new MCH_GuiScoreboard_CreateTeam(this, this.thePlayer));
/*     */     
/*  54 */     for (MCH_GuiScoreboard_Base s : this.listScreen.values()) {
/*  55 */       s.initGui(this.buttonList, (GuiScreen)this);
/*     */     }
/*     */     
/*  58 */     this.lastTeamNum = this.mc.world.getScoreboard().getTeams().size();
/*  59 */     switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
/*     */   }
/*     */   
/*     */   public void updateScreen() {
/*  63 */     super.updateScreen();
/*  64 */     int nowTeamNum = this.mc.world.getScoreboard().getTeams().size();
/*  65 */     if (this.lastTeamNum != nowTeamNum) {
/*  66 */       this.lastTeamNum = nowTeamNum;
/*  67 */       initGui();
/*     */     } 
/*     */     
/*  70 */     for (MCH_GuiScoreboard_Base s : this.listScreen.values()) {
/*     */       try {
/*  72 */         s.updateScreenButtons(this.buttonList);
/*  73 */         s.updateScreen();
/*  74 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID id) {
/*  81 */     for (MCH_GuiScoreboard_Base b : this.listScreen.values()) {
/*  82 */       b.leaveScreen();
/*     */     }
/*     */     
/*  85 */     this.screenID = id;
/*  86 */     getCurrentScreen().onSwitchScreen();
/*     */   }
/*     */   
/*     */   private MCH_GuiScoreboard_Base getCurrentScreen() {
/*  90 */     return this.listScreen.get(this.screenID);
/*     */   }
/*     */   
/*     */   protected void keyTyped(char c, int code) throws IOException {
/*  94 */     getCurrentScreen().keyTypedScreen(c, code);
/*     */   }
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/*     */     try {
/*  99 */       for (MCH_GuiScoreboard_Base s : this.listScreen.values()) {
/* 100 */         s.mouseClickedScreen(mouseX, mouseY, mouseButton);
/*     */       }
/*     */       
/* 103 */       super.mouseClicked(mouseX, mouseY, mouseButton);
/* 104 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(@NotNull GuiButton btn) throws IOException {
/* 109 */     if (btn.enabled) {
/* 110 */       getCurrentScreen().actionPerformedScreen(btn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawDefaultBackground() {}
/*     */   
/*     */   public void drawBackground(int tint) {
/* 118 */     GL11.glDisable(2896);
/* 119 */     GL11.glDisable(2912);
/* 120 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int x, int y) {
/* 124 */     getCurrentScreen().drawGuiContainerForegroundLayerScreen(x, y);
/*     */     
/* 126 */     for (Object o : this.buttonList) {
/* 127 */       if (o instanceof W_GuiButton) { W_GuiButton btn = (W_GuiButton)o;
/* 128 */         if (btn.isOnMouseOver() && btn.hoverStringList != null) {
/* 129 */           drawHoveringText(btn.hoverStringList, x, y, this.fontRenderer);
/*     */           break;
/*     */         }  }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
/* 137 */     getCurrentScreen().drawGuiContainerBackgroundLayer(par1, par2, par3);
/*     */   }
/*     */   
/*     */   public void setWorldAndResolution(@NotNull Minecraft mc, int width, int height) {
/* 141 */     super.setWorldAndResolution(mc, width, height);
/*     */     
/* 143 */     for (MCH_GuiScoreboard_Base s : this.listScreen.values())
/* 144 */       s.setWorldAndResolution(mc, width, height); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_GuiScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */