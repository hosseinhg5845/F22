/*     */ package com.norwood.mcheli.uav;
/*     */ 
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.helicopter.MCH_HeliInfo;
/*     */ import com.norwood.mcheli.helicopter.MCH_HeliInfoManager;
/*     */ import com.norwood.mcheli.plane.MCP_PlaneInfo;
/*     */ import com.norwood.mcheli.plane.MCP_PlaneInfoManager;
/*     */ import com.norwood.mcheli.tank.MCH_TankInfo;
/*     */ import com.norwood.mcheli.tank.MCH_TankInfoManager;
/*     */ import com.norwood.mcheli.wrapper.W_GuiContainer;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.W_Network;
/*     */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCH_GuiUavStation
/*     */   extends W_GuiContainer {
/*     */   static final int BX = 20;
/*     */   static final int BY = 22;
/*     */   
/*     */   public MCH_GuiUavStation(InventoryPlayer inventoryPlayer, MCH_EntityUavStation uavStation) {
/*  27 */     super(new MCH_ContainerUavStation(inventoryPlayer, uavStation));
/*  28 */     this.uavStation = uavStation;
/*     */   }
/*     */   final MCH_EntityUavStation uavStation; private GuiButton buttonContinue;
/*     */   protected void drawGuiContainerForegroundLayer(int param1, int param2) {
/*  32 */     if (this.uavStation != null) {
/*  33 */       MCH_TankInfo mCH_TankInfo = null; ItemStack item = this.uavStation.getStackInSlot(0);
/*  34 */       MCH_AircraftInfo info = null;
/*  35 */       if (!item.isEmpty() && item.getItem() instanceof com.norwood.mcheli.plane.MCP_ItemPlane) {
/*  36 */         MCP_PlaneInfo mCP_PlaneInfo = MCP_PlaneInfoManager.getFromItem(item.getItem());
/*     */       }
/*     */       
/*  39 */       if (!item.isEmpty() && item.getItem() instanceof com.norwood.mcheli.helicopter.MCH_ItemHeli) {
/*  40 */         MCH_HeliInfo mCH_HeliInfo = MCH_HeliInfoManager.getFromItem(item.getItem());
/*     */       }
/*     */       
/*  43 */       if (!item.isEmpty() && item.getItem() instanceof com.norwood.mcheli.tank.MCH_ItemTank) {
/*  44 */         mCH_TankInfo = MCH_TankInfoManager.getFromItem(item.getItem());
/*     */       }
/*     */       
/*  47 */       if (item.isEmpty() || (mCH_TankInfo != null && ((MCH_AircraftInfo)mCH_TankInfo).isUAV)) {
/*  48 */         if (this.uavStation.getKind() <= 1) {
/*  49 */           drawString("UAV Station", 8, 6, 16777215);
/*  50 */         } else if (!item.isEmpty() && !((MCH_AircraftInfo)mCH_TankInfo).isSmallUAV) {
/*  51 */           drawString("Small UAV only", 8, 6, 16711680);
/*     */         } else {
/*  53 */           drawString("UAV Controller", 8, 6, 16777215);
/*     */         } 
/*  55 */       } else if (!item.isEmpty()) {
/*  56 */         drawString("Not UAV", 8, 6, 16711680);
/*     */       } 
/*     */       
/*  59 */       drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 16777215);
/*  60 */       drawString(String.format("X.%+2d", new Object[] { Integer.valueOf(this.uavStation.posUavX) }), 58, 15, 16777215);
/*  61 */       drawString(String.format("Y.%+2d", new Object[] { Integer.valueOf(this.uavStation.posUavY) }), 58, 37, 16777215);
/*  62 */       drawString(String.format("Z.%+2d", new Object[] { Integer.valueOf(this.uavStation.posUavZ) }), 58, 59, 16777215);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
/*  67 */     W_McClient.MOD_bindTexture("textures/gui/uav_station.png");
/*  68 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  69 */     int x = (this.width - this.xSize) / 2;
/*  70 */     int y = (this.height - this.ySize) / 2;
/*  71 */     drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
/*     */   }
/*     */   
/*     */   protected void actionPerformed(@NotNull GuiButton btn) {
/*  75 */     if (btn.enabled) {
/*  76 */       if (btn.id == 256) {
/*  77 */         if (this.uavStation != null && !this.uavStation.isDead && this.uavStation
/*     */           
/*  79 */           .getLastControlAircraft() != null && 
/*  80 */           !(this.uavStation.getLastControlAircraft()).isDead) {
/*  81 */           MCH_UavPacketStatus data = new MCH_UavPacketStatus();
/*  82 */           data.posUavX = (byte)this.uavStation.posUavX;
/*  83 */           data.posUavY = (byte)this.uavStation.posUavY;
/*  84 */           data.posUavZ = (byte)this.uavStation.posUavZ;
/*  85 */           data.continueControl = true;
/*  86 */           W_Network.sendToServer((W_PacketBase)data);
/*     */         } 
/*     */         
/*  89 */         this.buttonContinue.enabled = false;
/*     */       } else {
/*  91 */         int[] pos = { this.uavStation.posUavX, this.uavStation.posUavY, this.uavStation.posUavZ };
/*  92 */         int i = btn.id >> 4 & 0xF;
/*  93 */         int j = (btn.id & 0xF) - 1;
/*  94 */         int[] BTN = { -10, -1, 1, 10 };
/*  95 */         pos[i] = pos[i] + BTN[j];
/*  96 */         if (pos[i] < -50) {
/*  97 */           pos[i] = -50;
/*     */         }
/*     */         
/* 100 */         if (pos[i] > 50) {
/* 101 */           pos[i] = 50;
/*     */         }
/*     */         
/* 104 */         if (this.uavStation.posUavX != pos[0] || this.uavStation.posUavY != pos[1] || this.uavStation.posUavZ != pos[2]) {
/* 105 */           MCH_UavPacketStatus data = new MCH_UavPacketStatus();
/* 106 */           data.posUavX = (byte)pos[0];
/* 107 */           data.posUavY = (byte)pos[1];
/* 108 */           data.posUavZ = (byte)pos[2];
/* 109 */           W_Network.sendToServer((W_PacketBase)data);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void initGui() {
/* 116 */     super.initGui();
/* 117 */     this.buttonList.clear();
/* 118 */     int x = this.width / 2 - 5;
/* 119 */     int y = this.height / 2 - 76;
/* 120 */     String[] BTN = { "-10", "-1", "+1", "+10" };
/*     */     
/* 122 */     for (int row = 0; row < 3; row++) {
/* 123 */       for (int col = 0; col < 4; col++) {
/* 124 */         int id = row << 4 | col + 1;
/* 125 */         this.buttonList.add(new GuiButton(id, x + col * 20, y + row * 22, 20, 20, BTN[col]));
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     this.buttonContinue = new GuiButton(256, x - 80 + 3, y + 44, 50, 20, "Continue");
/* 130 */     this.buttonContinue.enabled = (this.uavStation != null && !this.uavStation.isDead && this.uavStation.getAndSearchLastControlAircraft() != null);
/*     */     
/* 132 */     this.buttonList.add(this.buttonContinue);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mchel\\uav\MCH_GuiUavStation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */