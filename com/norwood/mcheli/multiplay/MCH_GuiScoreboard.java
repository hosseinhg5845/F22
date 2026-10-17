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
/*  31 */       ((GuiButton)g).field_146125_m = v;
/*     */     }
/*     */     
/*  34 */     if (g instanceof GuiTextField) {
/*  35 */       ((GuiTextField)g).func_146189_e(v);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawList(Minecraft mc, FontRenderer fontRendererObj, boolean mng) {
/*  40 */     MCH_GuiScoreboard_Base.drawList(mc, fontRendererObj, mng);
/*     */   }
/*     */   
/*     */   public void func_73866_w_() {
/*  44 */     Keyboard.enableRepeatEvents(true);
/*  45 */     super.func_73866_w_();
/*  46 */     this.field_146292_n.clear();
/*  47 */     this.field_146293_o.clear();
/*  48 */     this.field_147003_i = 0;
/*  49 */     this.field_147009_r = 0;
/*  50 */     this.listScreen = new HashMap<>();
/*  51 */     this.listScreen.put(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN, new MCH_GuiScoreboard_Main(this, this.thePlayer));
/*  52 */     this.listScreen.put(MCH_GuiScoreboard_Base.SCREEN_ID.CREATE_TEAM, new MCH_GuiScoreboard_CreateTeam(this, this.thePlayer));
/*     */     
/*  54 */     for (MCH_GuiScoreboard_Base s : this.listScreen.values()) {
/*  55 */       s.initGui(this.field_146292_n, (GuiScreen)this);
/*     */     }
/*     */     
/*  58 */     this.lastTeamNum = this.field_146297_k.field_71441_e.func_96441_U().func_96525_g().size();
/*  59 */     switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
/*     */   }
/*     */   
/*     */   public void func_73876_c() {
/*  63 */     super.func_73876_c();
/*  64 */     int nowTeamNum = this.field_146297_k.field_71441_e.func_96441_U().func_96525_g().size();
/*  65 */     if (this.lastTeamNum != nowTeamNum) {
/*  66 */       this.lastTeamNum = nowTeamNum;
/*  67 */       func_73866_w_();
/*     */     } 
/*     */     
/*  70 */     for (MCH_GuiScoreboard_Base s : this.listScreen.values()) {
/*     */       try {
/*  72 */         s.updateScreenButtons(this.field_146292_n);
/*  73 */         s.func_73876_c();
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
/*     */   protected void func_73869_a(char c, int code) throws IOException {
/*  94 */     getCurrentScreen().keyTypedScreen(c, code);
/*     */   }
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
/*     */     try {
/*  99 */       for (MCH_GuiScoreboard_Base s : this.listScreen.values()) {
/* 100 */         s.mouseClickedScreen(mouseX, mouseY, mouseButton);
/*     */       }
/*     */       
/* 103 */       super.func_73864_a(mouseX, mouseY, mouseButton);
/* 104 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146284_a(@NotNull GuiButton btn) throws IOException {
/* 109 */     if (btn.field_146124_l) {
/* 110 */       getCurrentScreen().actionPerformedScreen(btn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146276_q_() {}
/*     */   
/*     */   public void func_146278_c(int tint) {
/* 118 */     GL11.glDisable(2896);
/* 119 */     GL11.glDisable(2912);
/* 120 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void func_146979_b(int x, int y) {
/* 124 */     getCurrentScreen().drawGuiContainerForegroundLayerScreen(x, y);
/*     */     
/* 126 */     for (Object o : this.field_146292_n) {
/* 127 */       if (o instanceof W_GuiButton) { W_GuiButton btn = (W_GuiButton)o;
/* 128 */         if (btn.isOnMouseOver() && btn.hoverStringList != null) {
/* 129 */           drawHoveringText(btn.hoverStringList, x, y, this.field_146289_q);
/*     */           break;
/*     */         }  }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_146976_a(float par1, int par2, int par3) {
/* 137 */     getCurrentScreen().func_146976_a(par1, par2, par3);
/*     */   }
/*     */   
/*     */   public void func_146280_a(@NotNull Minecraft mc, int width, int height) {
/* 141 */     super.func_146280_a(mc, width, height);
/*     */     
/* 143 */     for (MCH_GuiScoreboard_Base s : this.listScreen.values())
/* 144 */       s.func_146280_a(mc, width, height); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_GuiScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */