/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_PacketIndOpenScreen;
/*     */ import com.norwood.mcheli.command.MCH_PacketCommandSave;
/*     */ import com.norwood.mcheli.multiplay.MCH_PacketIndMultiplayCommand;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponInfo;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponSet;
/*     */ import com.norwood.mcheli.wrapper.W_GuiContainer;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCH_AircraftGui
/*     */   extends W_GuiContainer
/*     */ {
/*     */   public static final int BUTTON_RELOAD = 1;
/*     */   public static final int BUTTON_NEXT = 2;
/*     */   public static final int BUTTON_PREV = 3;
/*     */   public static final int BUTTON_CLOSE = 4;
/*     */   public static final int BUTTON_CONFIG = 5;
/*     */   public static final int BUTTON_INVENTORY = 6;
/*     */   private final EntityPlayer thePlayer;
/*     */   private final MCH_EntityAircraft aircraft;
/*     */   private GuiButton buttonReload;
/*     */   private GuiButton buttonNext;
/*     */   private GuiButton buttonPrev;
/*     */   private GuiButton buttonInventory;
/*     */   private int currentWeaponId;
/*     */   private int reloadWait;
/*     */   private GuiTextField editCommand;
/*     */   
/*     */   public MCH_AircraftGui(EntityPlayer player, MCH_EntityAircraft ac) {
/*  37 */     super(new MCH_AircraftGuiContainer(player, ac));
/*  38 */     this.aircraft = ac;
/*  39 */     this.thePlayer = player;
/*  40 */     this.xSize = 210;
/*  41 */     this.ySize = 236;
/*  42 */     this.buttonReload = null;
/*  43 */     this.currentWeaponId = 0;
/*     */   }
/*     */   
/*     */   public void initGui() {
/*  47 */     super.initGui();
/*  48 */     this.buttonList.clear();
/*  49 */     this.buttonReload = new GuiButton(1, this.guiLeft + 85, this.guiTop + 40, 50, 20, "Reload");
/*  50 */     this.buttonNext = new GuiButton(3, this.guiLeft + 140, this.guiTop + 40, 20, 20, "<<");
/*  51 */     this.buttonPrev = new GuiButton(2, this.guiLeft + 160, this.guiTop + 40, 20, 20, ">>");
/*  52 */     this.buttonReload.enabled = canReload(this.thePlayer);
/*  53 */     this.buttonNext.enabled = (this.aircraft.getWeaponNum() >= 2);
/*  54 */     this.buttonPrev.enabled = (this.aircraft.getWeaponNum() >= 2);
/*  55 */     this.buttonInventory = new GuiButton(6, this.guiLeft + 210 - 30 - 60, this.guiTop + 90, 80, 20, "Inventory");
/*  56 */     this.buttonList.add(new GuiButton(5, this.guiLeft + 210 - 30 - 60, this.guiTop + 110, 80, 20, "MOD Options"));
/*  57 */     this.buttonList.add(new GuiButton(4, this.guiLeft + 210 - 30 - 20, this.guiTop + 10, 40, 20, "Close"));
/*  58 */     this.buttonList.add(this.buttonReload);
/*  59 */     this.buttonList.add(this.buttonNext);
/*  60 */     this.buttonList.add(this.buttonPrev);
/*  61 */     if (this.aircraft.getSizeInventory() > 0) {
/*  62 */       this.buttonList.add(this.buttonInventory);
/*     */     }
/*     */     
/*  65 */     this.editCommand = new GuiTextField(0, this.fontRenderer, this.guiLeft + 25, this.guiTop + 215, 160, 15);
/*  66 */     this.editCommand.setText(this.aircraft.getCommand());
/*  67 */     this.editCommand.setMaxStringLength(512);
/*  68 */     this.currentWeaponId = 0;
/*  69 */     this.reloadWait = 10;
/*     */   }
/*     */   
/*     */   public void closeScreen() {
/*  73 */     MCH_PacketCommandSave.send(this.editCommand.getText());
/*  74 */     this.mc.player.closeScreen();
/*     */   }
/*     */   
/*     */   public boolean canReload(EntityPlayer player) {
/*  78 */     return this.aircraft.canPlayerSupplyAmmo(player, this.currentWeaponId);
/*     */   }
/*     */   
/*     */   public void updateScreen() {
/*  82 */     super.updateScreen();
/*  83 */     if (this.reloadWait > 0) {
/*  84 */       this.reloadWait--;
/*  85 */       if (this.reloadWait == 0) {
/*  86 */         this.buttonReload.enabled = canReload(this.thePlayer);
/*  87 */         this.reloadWait = 20;
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     this.editCommand.updateCursorCounter();
/*     */   }
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  95 */     this.editCommand.mouseClicked(mouseX, mouseY, mouseButton);
/*  96 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */   
/*     */   public void onGuiClosed() {
/* 100 */     super.onGuiClosed();
/*     */   }
/*     */   
/*     */   protected void actionPerformed(@NotNull GuiButton button) throws IOException {
/* 104 */     super.actionPerformed(button);
/* 105 */     if (button.enabled)
/* 106 */       switch (button.id) {
/*     */         case 1:
/* 108 */           this.buttonReload.enabled = canReload(this.thePlayer);
/* 109 */           if (this.buttonReload.enabled) {
/* 110 */             MCH_PacketIndReload.send(this.aircraft, this.currentWeaponId);
/* 111 */             this.aircraft.supplyAmmo(this.currentWeaponId);
/* 112 */             this.reloadWait = 3;
/* 113 */             this.buttonReload.enabled = false;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 117 */           this.currentWeaponId++;
/* 118 */           if (this.currentWeaponId >= this.aircraft.getWeaponNum()) {
/* 119 */             this.currentWeaponId = 0;
/*     */           }
/*     */           
/* 122 */           this.buttonReload.enabled = canReload(this.thePlayer);
/*     */           break;
/*     */         case 3:
/* 125 */           this.currentWeaponId--;
/* 126 */           if (this.currentWeaponId < 0) {
/* 127 */             this.currentWeaponId = this.aircraft.getWeaponNum() - 1;
/*     */           }
/*     */           
/* 130 */           this.buttonReload.enabled = canReload(this.thePlayer);
/*     */           break;
/*     */         case 4:
/* 133 */           closeScreen();
/*     */           break;
/*     */         case 5:
/* 136 */           MCH_PacketIndOpenScreen.send(2);
/*     */           break;
/*     */         case 6:
/* 139 */           MCH_PacketIndOpenScreen.send(3);
/*     */           break;
/*     */       }  
/*     */   }
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int par1, int par2) {
/* 145 */     super.drawGuiContainerForegroundLayer(par1, par2);
/* 146 */     drawString(this.aircraft.getGuiInventory().getInventoryName(), 10, 10, 16777215);
/* 147 */     if (this.aircraft.getNumEjectionSeat() > 0) {
/* 148 */       drawString("Parachute", 9, 95, 16777215);
/*     */     }
/*     */     
/* 151 */     if (this.aircraft.getWeaponNum() > 0) {
/* 152 */       MCH_WeaponSet ws = this.aircraft.getWeapon(this.currentWeaponId);
/* 153 */       if (ws != null && !(ws.getFirstWeapon() instanceof com.norwood.mcheli.weapon.MCH_WeaponDummy)) {
/* 154 */         drawString(ws.getName(), 79, 30, 16777215);
/* 155 */         int rest = ws.getRestAllAmmoNum() + ws.getAmmoNum();
/* 156 */         int color = (rest == ws.getAllAmmoNum()) ? 2675784 : ((rest == 0) ? 16711680 : 16777215);
/* 157 */         String s = String.format("%4d/%4d", new Object[] { Integer.valueOf(rest), Integer.valueOf(ws.getAllAmmoNum()) });
/* 158 */         drawString(s, 145, 70, color);
/* 159 */         int itemPosX = 90;
/*     */         
/* 161 */         for (MCH_WeaponInfo.RoundItem r : (ws.getInfo()).roundItems) {
/* 162 */           drawString("" + r.num, itemPosX, 80, 16777215);
/* 163 */           itemPosX += 20;
/*     */         } 
/*     */         
/* 166 */         int var11 = 85;
/*     */         
/* 168 */         for (MCH_WeaponInfo.RoundItem r : (ws.getInfo()).roundItems) {
/* 169 */           drawItemStack(r.itemStack, var11, 62);
/* 170 */           var11 += 20;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 174 */       drawString("None", 79, 45, 16777215);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void keyTyped(char c, int code) {
/* 179 */     if (code == 1) {
/* 180 */       closeScreen();
/* 181 */     } else if (code == 28) {
/* 182 */       String s = this.editCommand.getText().trim();
/* 183 */       if (s.startsWith("/")) {
/* 184 */         s = s.substring(1);
/*     */       }
/*     */       
/* 187 */       if (!s.isEmpty()) {
/* 188 */         MCH_PacketIndMultiplayCommand.send(768, s);
/*     */       }
/*     */     } else {
/* 191 */       this.editCommand.textboxKeyTyped(c, code);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
/* 196 */     W_McClient.MOD_bindTexture("textures/gui/gui.png");
/* 197 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 198 */     int x = (this.width - this.xSize) / 2;
/* 199 */     int y = (this.height - this.ySize) / 2;
/* 200 */     drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
/*     */     
/* 202 */     for (int i = 0; i < this.aircraft.getNumEjectionSeat(); i++) {
/* 203 */       drawTexturedModalRect(x + 10 + 18 * i - 1, y + 105 - 1, 215, 55, 18, 18);
/*     */     }
/*     */     
/* 206 */     int ff = (int)(this.aircraft.getFuelP() * 50.0F);
/* 207 */     if (ff >= 99) {
/* 208 */       ff = 100;
/*     */     }
/*     */     
/* 211 */     drawTexturedModalRect(x + 57, y + 30 + 50 - ff, 215, 0, 12, ff);
/* 212 */     ff = (int)((this.aircraft.getFuelP() * 100.0F) + 0.5D);
/* 213 */     int color = (ff > 20) ? -14101432 : 16711680;
/* 214 */     drawString(String.format("%3d", new Object[] { Integer.valueOf(ff) }) + "%", x + 30, y + 65, color);
/* 215 */     this.editCommand.drawTextBox();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_AircraftGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */