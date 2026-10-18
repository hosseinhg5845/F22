/*     */ package com.norwood.mcheli.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.norwood.mcheli.gui.MCH_Gui;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ChatLine;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiTitle
/*     */   extends MCH_Gui {
/*  21 */   private final List<ChatLine> chatLines = new ArrayList<>();
/*  22 */   private int prevPlayerTick = 0;
/*  23 */   private int restShowTick = 0;
/*  24 */   private int showTick = 0;
/*  25 */   private float colorAlpha = 0.0F;
/*  26 */   private int position = 0;
/*     */   
/*     */   public MCH_GuiTitle(Minecraft minecraft) {
/*  29 */     super(minecraft);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  34 */     super.initGui();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  39 */     if (this.restShowTick > 0 && !this.chatLines.isEmpty() && player != null && player.world != null) {
/*  40 */       if (this.prevPlayerTick != player.ticksExisted) {
/*  41 */         this.showTick++;
/*  42 */         this.restShowTick--;
/*     */       } 
/*     */       
/*  45 */       this.prevPlayerTick = player.ticksExisted;
/*     */     } 
/*     */     
/*  48 */     return (this.restShowTick > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  53 */     GL11.glLineWidth((scaleFactor * 2));
/*  54 */     GL11.glDisable(3042);
/*  55 */     if (scaleFactor <= 0) {
/*  56 */       scaleFactor = 1;
/*     */     }
/*     */     
/*  59 */     this.colorAlpha = 1.0F;
/*  60 */     if (this.restShowTick > 20 && this.showTick < 5) {
/*  61 */       this.colorAlpha = 0.2F * this.showTick;
/*     */     }
/*     */     
/*  64 */     if (this.showTick > 0 && this.restShowTick < 5) {
/*  65 */       this.colorAlpha = 0.2F * this.restShowTick;
/*     */     }
/*     */     
/*  68 */     drawChat();
/*     */   }
/*     */   
/*     */   private String formatColors(String s) {
/*  72 */     return (Minecraft.getMinecraft()).gameSettings.chatColours ? s : TextFormatting.getTextWithoutFormattingCodes(s);
/*     */   }
/*     */   
/*     */   private int calculateChatboxWidth() {
/*  76 */     short short1 = 320;
/*  77 */     byte b0 = 40;
/*  78 */     return MathHelper.floor(this.mc.gameSettings.chatWidth * (short1 - b0) + b0);
/*     */   }
/*     */   
/*     */   public void setupTitle(ITextComponent chatComponent, int showTime, int pos) {
/*  82 */     int displayTime = 20;
/*  83 */     int line = 0;
/*  84 */     this.chatLines.clear();
/*  85 */     this.position = pos;
/*  86 */     this.showTick = 0;
/*  87 */     this.restShowTick = showTime;
/*  88 */     int k = MathHelper.floor(calculateChatboxWidth() / this.mc.gameSettings.chatScale);
/*  89 */     int l = 0;
/*  90 */     TextComponentString chatcomponenttext = new TextComponentString("");
/*  91 */     ArrayList<ITextComponent> arraylist = Lists.newArrayList();
/*  92 */     ArrayList<ITextComponent> arraylist1 = Lists.newArrayList((Iterable)chatComponent);
/*     */     
/*  94 */     for (int i1 = 0; i1 < arraylist1.size(); i1++) {
/*  95 */       ITextComponent ichatcomponent1 = arraylist1.get(i1);
/*  96 */       String[] splitLine = ichatcomponent1.getUnformattedComponentText().split("\n");
/*  97 */       int lineCnt = 0;
/*     */       
/*  99 */       for (String sLine : splitLine) {
/* 100 */         String s = formatColors(ichatcomponent1.getStyle().getFormattingCode() + sLine);
/* 101 */         int j1 = this.mc.fontRenderer.getStringWidth(s);
/* 102 */         TextComponentString chatcomponenttext1 = new TextComponentString(s);
/* 103 */         chatcomponenttext1.setStyle(ichatcomponent1.getStyle().createShallowCopy());
/* 104 */         boolean flag1 = false;
/* 105 */         if (l + j1 > k) {
/* 106 */           String s1 = this.mc.fontRenderer.trimStringToWidth(s, k - l, false);
/* 107 */           String s2 = (s1.length() < s.length()) ? s.substring(s1.length()) : null;
/* 108 */           if (s2 != null) {
/* 109 */             int k1 = s1.lastIndexOf(" ");
/* 110 */             if (k1 >= 0 && this.mc.fontRenderer.getStringWidth(s.substring(0, k1)) > 0) {
/* 111 */               s1 = s.substring(0, k1);
/* 112 */               s2 = s.substring(k1);
/*     */             } 
/*     */             
/* 115 */             TextComponentString chatcomponenttext2 = new TextComponentString(s2);
/* 116 */             chatcomponenttext2.setStyle(ichatcomponent1.getStyle().createShallowCopy());
/* 117 */             arraylist1.add(i1 + 1, chatcomponenttext2);
/*     */           } 
/*     */           
/* 120 */           j1 = this.mc.fontRenderer.getStringWidth(s1);
/* 121 */           chatcomponenttext1 = new TextComponentString(s1);
/* 122 */           chatcomponenttext1.setStyle(ichatcomponent1.getStyle().createShallowCopy());
/* 123 */           flag1 = true;
/*     */         } 
/*     */         
/* 126 */         if (l + j1 <= k) {
/* 127 */           l += j1;
/* 128 */           chatcomponenttext.appendSibling((ITextComponent)chatcomponenttext1);
/*     */         } else {
/* 130 */           flag1 = true;
/*     */         } 
/*     */         
/* 133 */         if (flag1) {
/* 134 */           arraylist.add(chatcomponenttext);
/* 135 */           l = 0;
/* 136 */           chatcomponenttext = new TextComponentString("");
/*     */         } 
/*     */         
/* 139 */         if (++lineCnt < splitLine.length) {
/* 140 */           arraylist.add(chatcomponenttext);
/* 141 */           l = 0;
/* 142 */           chatcomponenttext = new TextComponentString("");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     arraylist.add(chatcomponenttext);
/*     */     
/* 149 */     for (ITextComponent ichatcomponent2 : arraylist) {
/* 150 */       this.chatLines.add(new ChatLine(displayTime, ichatcomponent2, line));
/*     */     }
/*     */     
/* 153 */     while (this.chatLines.size() > 100) {
/* 154 */       this.chatLines.remove(this.chatLines.size() - 1);
/*     */     }
/*     */   }
/*     */   
/*     */   private int calculateChatboxHeight() {
/* 159 */     short short1 = 180;
/* 160 */     byte b0 = 20;
/* 161 */     return MathHelper.floor(this.mc.gameSettings.chatHeightFocused * (short1 - b0) + b0);
/*     */   }
/*     */   
/*     */   private void drawChat() {
/* 165 */     float charAlpha = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
/* 166 */     float scale = this.mc.gameSettings.chatScale * 2.0F;
/* 167 */     GL11.glPushMatrix();
/* 168 */     switch (this.position) { case 1: 
/*     */       case 2: 
/*     */       case 3: 
/*     */       case 4: 
/*     */       default:
/* 173 */         break; }  float posY = this.mc.displayHeight / 2.0F / scaleFactor - this.chatLines.size() / 2.0F * 9.0F * scale;
/*     */ 
/*     */     
/* 176 */     GL11.glTranslatef(0.0F, posY, 0.0F);
/* 177 */     GL11.glScalef(scale, scale, 1.0F);
/*     */     
/* 179 */     for (int i = 0; i < this.chatLines.size(); i++) {
/* 180 */       ChatLine chatline = this.chatLines.get(i);
/* 181 */       if (chatline != null) {
/* 182 */         int alpha = (int)(255.0F * charAlpha * this.colorAlpha);
/* 183 */         int y = i * 9;
/* 184 */         drawRect(0, y + 9, this.mc.displayWidth, y, alpha / 2 << 24);
/* 185 */         GL11.glEnable(3042);
/* 186 */         String s = chatline.getChatComponent().getFormattedText();
/* 187 */         int sw = this.mc.displayWidth / 2 / scaleFactor - this.mc.fontRenderer.getStringWidth(s);
/* 188 */         sw = (int)(sw / scale);
/* 189 */         this.mc.fontRenderer.drawStringWithShadow(s, sw, (y + 1), 16777215 + (alpha << 24));
/* 190 */         GL11.glDisable(3008);
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     GL11.glTranslatef(-3.0F, 0.0F, 0.0F);
/* 195 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\command\MCH_GuiTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */