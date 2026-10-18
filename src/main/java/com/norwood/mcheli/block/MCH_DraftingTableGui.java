/*     */ package com.norwood.mcheli.block;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_IRecipeList;
/*     */ import com.norwood.mcheli.MCH_ItemRecipe;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.aircraft.MCH_RenderAircraft;
/*     */ import com.norwood.mcheli.gui.MCH_GuiSliderVertical;
/*     */ import com.norwood.mcheli.helicopter.MCH_HeliInfoManager;
/*     */ import com.norwood.mcheli.helper.MCH_Recipes;
/*     */ import com.norwood.mcheli.plane.MCP_PlaneInfoManager;
/*     */ import com.norwood.mcheli.tank.MCH_TankInfoManager;
/*     */ import com.norwood.mcheli.vehicle.MCH_VehicleInfoManager;
/*     */ import com.norwood.mcheli.wrapper.W_GuiButton;
/*     */ import com.norwood.mcheli.wrapper.W_GuiContainer;
/*     */ import com.norwood.mcheli.wrapper.W_KeyBinding;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.modelloader.W_ModelCustom;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.Ingredient;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCH_DraftingTableGui
/*     */   extends W_GuiContainer {
/*     */   public static final int RECIPE_HELI = 0;
/*     */   public static final int RECIPE_PLANE = 1;
/*     */   public static final int RECIPE_VEHICLE = 2;
/*     */   public static final int RECIPE_TANK = 3;
/*     */   public static final int RECIPE_ITEM = 4;
/*     */   public static final int BUTTON_HELI = 10;
/*     */   public static final int BUTTON_PLANE = 11;
/*     */   public static final int BUTTON_VEHICLE = 12;
/*     */   public static final int BUTTON_TANK = 13;
/*     */   public static final int BUTTON_ITEM = 14;
/*     */   public static final int BUTTON_NEXT = 20;
/*     */   public static final int BUTTON_PREV = 21;
/*     */   public static final int BUTTON_CREATE = 30;
/*     */   public static final int BUTTON_SELECT = 40;
/*     */   public static final int BUTTON_NEXT_PAGE = 50;
/*     */   public static final int BUTTON_PREV_PAGE = 51;
/*     */   public static final int SCREEN_MAIN = 0;
/*     */   public static final int SCREEN_LIST = 1;
/*  59 */   public static float modelZoom = 1.0F;
/*  60 */   public static float modelRotX = 0.0F;
/*  61 */   public static float modelRotY = 0.0F;
/*  62 */   public static float modelPosX = 0.0F;
/*  63 */   public static float modelPosY = 0.0F;
/*     */   private final EntityPlayer thePlayer;
/*     */   public MCH_IRecipeList currentList;
/*     */   public MCH_CurrentRecipe current;
/*     */   public final List<List<GuiButton>> screenButtonList;
/*  68 */   public int screenId = 0;
/*     */   private MCH_GuiSliderVertical listSlider;
/*     */   private GuiButton buttonCreate;
/*     */   private GuiButton buttonNext;
/*     */   private GuiButton buttonPrev;
/*     */   private GuiButton buttonNextPage;
/*     */   private GuiButton buttonPrevPage;
/*     */   private int drawFace;
/*     */   private int buttonClickWait;
/*     */   
/*     */   public MCH_DraftingTableGui(EntityPlayer player, int posX, int posY, int posZ) {
/*  79 */     super(new MCH_DraftingTableGuiContainer(player, posX, posY, posZ));
/*  80 */     this.thePlayer = player;
/*  81 */     this.xSize = 400;
/*  82 */     this.ySize = 240;
/*  83 */     this.screenButtonList = new ArrayList<>();
/*  84 */     this.drawFace = 0;
/*  85 */     this.buttonClickWait = 0;
/*  86 */     MCH_Lib.DbgLog(player.world, "MCH_DraftingTableGui.MCH_DraftingTableGui", new Object[0]);
/*     */   }
/*     */   
/*     */   public static void initModelTransform() {
/*  90 */     modelRotX = 0.0F;
/*  91 */     modelRotY = 0.0F;
/*  92 */     modelPosX = 0.0F;
/*  93 */     modelPosY = 0.0F;
/*  94 */     modelZoom = 1.0F;
/*     */   }
/*     */   
/*     */   public void initGui() {
/*  98 */     super.initGui();
/*  99 */     this.buttonList.clear();
/* 100 */     this.screenButtonList.clear();
/* 101 */     this.screenButtonList.add(new ArrayList<>());
/* 102 */     this.screenButtonList.add(new ArrayList<>());
/* 103 */     List<GuiButton> list = this.screenButtonList.get(0);
/* 104 */     GuiButton btnHeli = new GuiButton(10, this.guiLeft + 20, this.guiTop + 20, 90, 20, "Helicopter List");
/* 105 */     GuiButton btnPlane = new GuiButton(11, this.guiLeft + 20, this.guiTop + 40, 90, 20, "Plane List");
/* 106 */     GuiButton btnVehicle = new GuiButton(12, this.guiLeft + 20, this.guiTop + 60, 90, 20, "Vehicle List");
/* 107 */     GuiButton btnTank = new GuiButton(13, this.guiLeft + 20, this.guiTop + 80, 90, 20, "Tank List");
/* 108 */     GuiButton btnItem = new GuiButton(14, this.guiLeft + 20, this.guiTop + 100, 90, 20, "Item List");
/* 109 */     btnHeli.enabled = (MCH_HeliInfoManager.getInstance().getRecipeListSize() > 0);
/* 110 */     btnPlane.enabled = (MCP_PlaneInfoManager.getInstance().getRecipeListSize() > 0);
/* 111 */     btnVehicle.enabled = (MCH_VehicleInfoManager.getInstance().getRecipeListSize() > 0);
/* 112 */     btnTank.enabled = (MCH_TankInfoManager.getInstance().getRecipeListSize() > 0);
/* 113 */     btnItem.enabled = (MCH_ItemRecipe.getInstance().getRecipeListSize() > 0);
/* 114 */     list.add(btnHeli);
/* 115 */     list.add(btnPlane);
/* 116 */     list.add(btnVehicle);
/* 117 */     list.add(btnTank);
/* 118 */     list.add(btnItem);
/* 119 */     this.buttonCreate = new GuiButton(30, this.guiLeft + 120, this.guiTop + 89, 50, 20, "Create");
/* 120 */     this.buttonPrev = new GuiButton(21, this.guiLeft + 120, this.guiTop + 111, 36, 20, "<<");
/* 121 */     this.buttonNext = new GuiButton(20, this.guiLeft + 155, this.guiTop + 111, 35, 20, ">>");
/* 122 */     list.add(this.buttonCreate);
/* 123 */     list.add(this.buttonPrev);
/* 124 */     list.add(this.buttonNext);
/* 125 */     this.buttonPrevPage = new GuiButton(51, this.guiLeft + 210, this.guiTop + 210, 60, 20, "Prev Page");
/* 126 */     this.buttonNextPage = new GuiButton(50, this.guiLeft + 270, this.guiTop + 210, 60, 20, "Next Page");
/* 127 */     list.add(this.buttonPrevPage);
/* 128 */     list.add(this.buttonNextPage);
/* 129 */     list = this.screenButtonList.get(1);
/* 130 */     int y = 0;
/*     */     
/* 132 */     for (int i = 0; y < 3; y++) {
/* 133 */       for (int x = 0; x < 2; i++) {
/* 134 */         int px = this.guiLeft + 30 + x * 140;
/* 135 */         int py = this.guiTop + 40 + y * 70;
/* 136 */         list.add(new GuiButton(40 + i, px, py, 45, 20, "Select"));
/* 137 */         x++;
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     this.listSlider = new MCH_GuiSliderVertical(0, this.guiLeft + 360, this.guiTop + 20, 20, 200, "", 0.0F, 0.0F, 0.0F, 1.0F);
/* 142 */     list.add(this.listSlider);
/*     */     
/* 144 */     for (List<GuiButton> guiButtons : this.screenButtonList) {
/* 145 */       list = guiButtons;
/*     */       
/* 147 */       this.buttonList.addAll(list);
/*     */     } 
/*     */     
/* 150 */     switchScreen(0);
/* 151 */     initModelTransform();
/* 152 */     modelRotX = 180.0F;
/* 153 */     modelRotY = 90.0F;
/* 154 */     if (MCH_ItemRecipe.getInstance().getRecipeListSize() > 0) {
/* 155 */       switchRecipeList((MCH_IRecipeList)MCH_ItemRecipe.getInstance());
/* 156 */     } else if (MCH_HeliInfoManager.getInstance().getRecipeListSize() > 0) {
/* 157 */       switchRecipeList((MCH_IRecipeList)MCH_HeliInfoManager.getInstance());
/* 158 */     } else if (MCP_PlaneInfoManager.getInstance().getRecipeListSize() > 0) {
/* 159 */       switchRecipeList((MCH_IRecipeList)MCP_PlaneInfoManager.getInstance());
/* 160 */     } else if (MCH_VehicleInfoManager.getInstance().getRecipeListSize() > 0) {
/* 161 */       switchRecipeList((MCH_IRecipeList)MCH_VehicleInfoManager.getInstance());
/* 162 */     } else if (MCH_TankInfoManager.getInstance().getRecipeListSize() > 0) {
/* 163 */       switchRecipeList((MCH_IRecipeList)MCH_TankInfoManager.getInstance());
/*     */     } else {
/* 165 */       switchRecipeList((MCH_IRecipeList)MCH_ItemRecipe.getInstance());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateListSliderSize(int listSize) {
/* 170 */     int s = listSize / 2;
/* 171 */     if (listSize % 2 != 0) {
/* 172 */       s++;
/*     */     }
/*     */     
/* 175 */     if (s > 3) {
/* 176 */       this.listSlider.valueMax = (s - 3);
/*     */     } else {
/* 178 */       this.listSlider.valueMax = 0.0F;
/*     */     } 
/*     */     
/* 181 */     this.listSlider.setSliderValue(0.0F);
/*     */   }
/*     */   
/*     */   public void switchScreen(int id) {
/* 185 */     this.screenId = id;
/*     */     
/* 187 */     for (GuiButton guiButton : this.buttonList) {
/* 188 */       W_GuiButton.setVisible(guiButton, false);
/*     */     }
/*     */     
/* 191 */     if (id < this.screenButtonList.size()) {
/* 192 */       for (GuiButton b : this.screenButtonList.get(id)) {
/* 193 */         W_GuiButton.setVisible(b, true);
/*     */       }
/*     */     }
/*     */     
/* 197 */     if (getScreenId() == 0 && this.current != null && this.current.getDescMaxPage() > 1) {
/* 198 */       W_GuiButton.setVisible(this.buttonNextPage, true);
/* 199 */       W_GuiButton.setVisible(this.buttonPrevPage, true);
/*     */     } else {
/* 201 */       W_GuiButton.setVisible(this.buttonNextPage, false);
/* 202 */       W_GuiButton.setVisible(this.buttonPrevPage, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCurrentRecipe(MCH_CurrentRecipe currentRecipe) {
/* 207 */     modelPosX = 0.0F;
/* 208 */     modelPosY = 0.0F;
/* 209 */     if (this.current == null || currentRecipe == null || !this.current.recipe.getRecipeOutput().isItemEqual(currentRecipe.recipe.getRecipeOutput())) {
/* 210 */       this.drawFace = 0;
/*     */     }
/*     */     
/* 213 */     this.current = currentRecipe;
/* 214 */     if (getScreenId() == 0 && this.current != null && this.current.getDescMaxPage() > 1) {
/* 215 */       W_GuiButton.setVisible(this.buttonNextPage, true);
/* 216 */       W_GuiButton.setVisible(this.buttonPrevPage, true);
/*     */     } else {
/* 218 */       W_GuiButton.setVisible(this.buttonNextPage, false);
/* 219 */       W_GuiButton.setVisible(this.buttonPrevPage, false);
/*     */     } 
/*     */     
/* 222 */     updateEnableCreateButton();
/*     */   }
/*     */   
/*     */   public MCH_IRecipeList getCurrentList() {
/* 226 */     return this.currentList;
/*     */   }
/*     */   
/*     */   public void switchRecipeList(MCH_IRecipeList list) {
/* 230 */     if (getCurrentList() != list) {
/* 231 */       setCurrentRecipe(new MCH_CurrentRecipe(list, 0));
/* 232 */       this.currentList = list;
/* 233 */       updateListSliderSize(list.getRecipeListSize());
/*     */     } else {
/* 235 */       this.listSlider.setSliderValue(this.current.index / 2.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateScreen() {
/* 240 */     super.updateScreen();
/* 241 */     if (this.buttonClickWait > 0) {
/* 242 */       this.buttonClickWait--;
/*     */     }
/*     */   }
/*     */   
/*     */   public void onGuiClosed() {
/* 247 */     super.onGuiClosed();
/* 248 */     MCH_Lib.DbgLog(this.thePlayer.world, "MCH_DraftingTableGui.onGuiClosed", new Object[0]);
/*     */   }
/*     */   
/*     */   protected void actionPerformed(@NotNull GuiButton button) throws IOException {
/* 252 */     super.actionPerformed(button);
/* 253 */     if (this.buttonClickWait <= 0 && 
/* 254 */       button.enabled) {
/* 255 */       int page, index, i; this.buttonClickWait = 3;
/* 256 */       switch (button.id) {
/*     */         case 10:
/* 258 */           initModelTransform();
/* 259 */           modelRotX = 180.0F;
/* 260 */           modelRotY = 90.0F;
/* 261 */           switchRecipeList((MCH_IRecipeList)MCH_HeliInfoManager.getInstance());
/* 262 */           switchScreen(1);
/*     */           break;
/*     */         case 11:
/* 265 */           initModelTransform();
/* 266 */           modelRotX = 90.0F;
/* 267 */           modelRotY = 180.0F;
/* 268 */           switchRecipeList((MCH_IRecipeList)MCP_PlaneInfoManager.getInstance());
/* 269 */           switchScreen(1);
/*     */           break;
/*     */         case 12:
/* 272 */           initModelTransform();
/* 273 */           modelRotX = 180.0F;
/* 274 */           modelRotY = 90.0F;
/* 275 */           switchRecipeList((MCH_IRecipeList)MCH_VehicleInfoManager.getInstance());
/* 276 */           switchScreen(1);
/*     */           break;
/*     */         case 13:
/* 279 */           initModelTransform();
/* 280 */           modelRotX = 180.0F;
/* 281 */           modelRotY = 90.0F;
/* 282 */           switchRecipeList((MCH_IRecipeList)MCH_TankInfoManager.getInstance());
/* 283 */           switchScreen(1);
/*     */           break;
/*     */         case 14:
/* 286 */           switchRecipeList((MCH_IRecipeList)MCH_ItemRecipe.getInstance());
/* 287 */           switchScreen(1);
/*     */           break;
/*     */         case 20:
/* 290 */           page = this.current.getDescCurrentPage();
/* 291 */           if (this.current.isCurrentPageTexture()) {
/* 292 */             page = 0;
/*     */           }
/* 294 */           i = (this.current.index + 1) % getCurrentList().getRecipeListSize();
/* 295 */           setCurrentRecipe(new MCH_CurrentRecipe(getCurrentList(), i));
/* 296 */           this.current.setDescCurrentPage(page);
/*     */           break;
/*     */         case 21:
/* 299 */           page = this.current.getDescCurrentPage();
/* 300 */           if (this.current.isCurrentPageTexture()) {
/* 301 */             page = 0;
/*     */           }
/* 303 */           i = this.current.index - 1;
/* 304 */           if (i < 0) {
/* 305 */             i = getCurrentList().getRecipeListSize() - 1;
/*     */           }
/* 307 */           setCurrentRecipe(new MCH_CurrentRecipe(getCurrentList(), i));
/* 308 */           this.current.setDescCurrentPage(page); break;
/*     */         case 30:
/* 310 */           MCH_DraftingTableCreatePacket.send(this.current.recipe); break;
/*     */         case 40: case 41: case 42: case 43: case 44: case 45:
/* 312 */           index = (int)this.listSlider.getSliderValue() * 2 + button.id - 40;
/* 313 */           if (index < getCurrentList().getRecipeListSize()) {
/* 314 */             setCurrentRecipe(new MCH_CurrentRecipe(getCurrentList(), index));
/* 315 */             switchScreen(0);
/*     */           } 
/*     */           break;
/*     */         case 50:
/* 319 */           if (this.current != null) {
/* 320 */             this.current.switchNextPage();
/*     */           }
/*     */           break;
/*     */         case 51:
/* 324 */           if (this.current != null) {
/* 325 */             this.current.switchPrevPage();
/*     */           }
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateEnableCreateButton() {
/* 336 */     MCH_DraftingTableGuiContainer container = (MCH_DraftingTableGuiContainer)this.inventorySlots;
/* 337 */     this.buttonCreate.enabled = false;
/* 338 */     if (!container.getSlot(container.outputSlotIndex).getHasStack()) {
/* 339 */       this.buttonCreate.enabled = MCH_Recipes.canCraft(this.thePlayer, this.current.recipe);
/*     */     }
/*     */     
/* 342 */     if (this.thePlayer.capabilities.isCreativeMode) {
/* 343 */       this.buttonCreate.enabled = true;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void keyTyped(char par1, int keycode) throws IOException {
/* 348 */     if (keycode == 1 || keycode == W_KeyBinding.getKeyCode((Minecraft.getMinecraft()).gameSettings.keyBindInventory)) {
/* 349 */       if (getScreenId() == 0) {
/* 350 */         this.mc.player.closeScreen();
/*     */       } else {
/* 352 */         switchScreen(0);
/*     */       } 
/*     */     }
/*     */     
/* 356 */     if (getScreenId() == 0) {
/* 357 */       if (keycode == 205) {
/* 358 */         actionPerformed(this.buttonNext);
/*     */       }
/*     */       
/* 361 */       if (keycode == 203) {
/* 362 */         actionPerformed(this.buttonPrev);
/*     */       }
/* 364 */     } else if (getScreenId() == 1) {
/* 365 */       if (keycode == 200) {
/* 366 */         this.listSlider.scrollDown(1.0F);
/*     */       }
/*     */       
/* 369 */       if (keycode == 208) {
/* 370 */         this.listSlider.scrollUp(1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mx, int my) {
/* 376 */     super.drawGuiContainerForegroundLayer(mx, my);
/* 377 */     this.zLevel = 0.0F;
/* 378 */     GL11.glEnable(3042);
/* 379 */     if (getScreenId() == 0) {
/* 380 */       ArrayList<String> list = new ArrayList<>();
/* 381 */       if (this.current != null) {
/* 382 */         if (this.current.isCurrentPageTexture()) {
/* 383 */           GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
/* 384 */           this.mc.getTextureManager().bindTexture(this.current.getCurrentPageTexture());
/* 385 */           drawTexturedModalRect(210, 20, 170, 190, 0, 0, 340, 380);
/* 386 */         } else if (this.current.isCurrentPageAcInfo()) {
/* 387 */           for (int i = 0; i < this.current.infoItem.size(); i++) {
/* 388 */             this.fontRenderer.drawString(this.current.infoItem.get(i), 210, 40 + 10 * i, -9491968);
/* 389 */             String data = this.current.infoData.get(i);
/* 390 */             if (!data.isEmpty()) {
/* 391 */               this.fontRenderer.drawString(data, 280, 40 + 10 * i, -9491968);
/*     */             }
/*     */           } 
/*     */         } else {
/* 395 */           W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
/* 396 */           drawTexturedModalRect(340, 215, 45, 15, 400, 60, 90, 30);
/* 397 */           if (mx >= 350 && mx <= 400 && my >= 214 && my <= 230) {
/* 398 */             boolean lb = Mouse.isButtonDown(0);
/* 399 */             boolean rb = Mouse.isButtonDown(1);
/* 400 */             boolean mb = Mouse.isButtonDown(2);
/* 401 */             list.add((lb ? (String)TextFormatting.AQUA : "") + "Mouse left button drag : Rotation model");
/* 402 */             list.add((rb ? (String)TextFormatting.AQUA : "") + "Mouse right button drag : Zoom model");
/* 403 */             list.add((mb ? (String)TextFormatting.AQUA : "") + "Mouse middle button drag : Move model");
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 408 */       drawString(this.current.displayName, 120, 20, -1);
/* 409 */       drawItemRecipe(this.current.recipe, 121, 34);
/* 410 */       if (!list.isEmpty()) {
/* 411 */         drawHoveringText(list, mx - 30, my, this.fontRenderer);
/*     */       }
/*     */     } 
/*     */     
/* 415 */     if (getScreenId() == 1) {
/* 416 */       int index = 2 * (int)this.listSlider.getSliderValue();
/* 417 */       int ix = 0;
/*     */       int r;
/* 419 */       for (r = 0; r < 3; r++) {
/* 420 */         for (int c = 0; c < 2; c++) {
/* 421 */           if (index + ix < getCurrentList().getRecipeListSize()) {
/* 422 */             int rx = 110 + 140 * c;
/* 423 */             int ry = 20 + 70 * r;
/* 424 */             String s = getCurrentList().getRecipe(index + ix).getRecipeOutput().getDisplayName();
/* 425 */             drawCenteredString(s, rx, ry, -1);
/*     */           } 
/*     */           
/* 428 */           ix++;
/*     */         } 
/*     */       } 
/*     */       
/* 432 */       W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
/* 433 */       ix = 0;
/*     */       
/* 435 */       for (r = 0; r < 3; r++) {
/* 436 */         for (int c = 0; c < 2; c++) {
/* 437 */           if (index + ix < getCurrentList().getRecipeListSize()) {
/* 438 */             int rx = 80 + 140 * c - 1;
/* 439 */             int ry = 30 + 70 * r - 1;
/* 440 */             drawTexturedModalRect(rx, ry, 400, 0, 75, 54);
/*     */           } 
/*     */           
/* 443 */           ix++;
/*     */         } 
/*     */       } 
/*     */       
/* 447 */       ix = 0;
/*     */       
/* 449 */       for (r = 0; r < 3; r++) {
/* 450 */         for (int c = 0; c < 2; c++) {
/* 451 */           if (index + ix < getCurrentList().getRecipeListSize()) {
/* 452 */             int rx = 80 + 140 * c;
/* 453 */             int ry = 30 + 70 * r;
/* 454 */             drawItemRecipe(getCurrentList().getRecipe(index + ix), rx, ry);
/*     */           } 
/*     */           
/* 457 */           ix++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleMouseClick(@NotNull Slot slotIn, int slotId, int clickedButton, @NotNull ClickType clickType) {
/* 464 */     if (getScreenId() != 1) {
/* 465 */       super.handleMouseClick(slotIn, slotId, clickedButton, clickType);
/*     */     }
/*     */   }
/*     */   
/*     */   private int getScreenId() {
/* 470 */     return this.screenId;
/*     */   }
/*     */   
/*     */   public void drawItemRecipe(IRecipe recipe, int x, int y) {
/* 474 */     if (recipe != null) {
/* 475 */       recipe.getRecipeOutput();
/* 476 */       recipe.getRecipeOutput().getItem();
/* 477 */       RenderHelper.enableGUIStandardItemLighting();
/* 478 */       NonNullList<Ingredient> ingredients = recipe.getIngredients();
/*     */       
/* 480 */       for (int i = 0; i < ingredients.size(); i++) {
/* 481 */         drawIngredient((Ingredient)ingredients.get(i), x + i % 3 * 18, y + i / 3 * 18);
/*     */       }
/*     */       
/* 484 */       drawItemStack(recipe.getRecipeOutput(), x + 54 + 3, y + 18);
/* 485 */       RenderHelper.disableStandardItemLighting();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 490 */     super.handleMouseInput();
/* 491 */     int dx = Mouse.getEventDX();
/* 492 */     int dy = Mouse.getEventDY();
/* 493 */     if (getScreenId() == 0 && Mouse.getX() > this.mc.displayWidth / 2) {
/* 494 */       if (Mouse.isButtonDown(0) && (dx != 0 || dy != 0)) {
/* 495 */         modelRotX = (float)(modelRotX - dy / 2.0D);
/* 496 */         modelRotY = (float)(modelRotY - dx / 2.0D);
/* 497 */         if (modelRotX > 360.0F) {
/* 498 */           modelRotX -= 360.0F;
/*     */         }
/*     */         
/* 501 */         if (modelRotX < -360.0F) {
/* 502 */           modelRotX += 360.0F;
/*     */         }
/*     */         
/* 505 */         if (modelRotY > 360.0F) {
/* 506 */           modelRotY -= 360.0F;
/*     */         }
/*     */         
/* 509 */         if (modelRotY < -360.0F) {
/* 510 */           modelRotY += 360.0F;
/*     */         }
/*     */       } 
/*     */       
/* 514 */       if (Mouse.isButtonDown(2) && (dx != 0 || dy != 0)) {
/* 515 */         modelPosX = (float)(modelPosX + dx / 2.0D);
/* 516 */         modelPosY = (float)(modelPosY - dy / 2.0D);
/* 517 */         if (modelRotX > 1000.0F) {
/* 518 */           modelRotX = 1000.0F;
/*     */         }
/*     */         
/* 521 */         if (modelRotX < -1000.0F) {
/* 522 */           modelRotX = -1000.0F;
/*     */         }
/*     */         
/* 525 */         if (modelRotY > 1000.0F) {
/* 526 */           modelRotY = 1000.0F;
/*     */         }
/*     */         
/* 529 */         if (modelRotY < -1000.0F) {
/* 530 */           modelRotY = -1000.0F;
/*     */         }
/*     */       } 
/*     */       
/* 534 */       if (Mouse.isButtonDown(1) && dy != 0) {
/* 535 */         modelZoom = (float)(modelZoom + dy / 100.0D);
/* 536 */         if (modelZoom < 0.1D) {
/* 537 */           modelZoom = 0.1F;
/*     */         }
/*     */         
/* 540 */         if (modelZoom > 10.0F) {
/* 541 */           modelZoom = 10.0F;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 546 */     int wheel = Mouse.getEventDWheel();
/* 547 */     if (wheel != 0) {
/* 548 */       if (getScreenId() == 1) {
/* 549 */         if (wheel > 0) {
/* 550 */           this.listSlider.scrollDown(1.0F);
/*     */         } else {
/* 552 */           this.listSlider.scrollUp(1.0F);
/*     */         } 
/* 554 */       } else if (getScreenId() == 0) {
/* 555 */         if (wheel > 0) {
/* 556 */           actionPerformed(this.buttonPrev);
/*     */         } else {
/* 558 */           actionPerformed(this.buttonNext);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 566 */     GL11.glEnable(3042);
/* 567 */     GL11.glBlendFunc(770, 771);
/* 568 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 569 */     if (getScreenId() == 0) {
/* 570 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     } else {
/* 572 */       List<Slot> inventory = this.inventorySlots.inventorySlots;
/* 573 */       this.inventorySlots.inventorySlots = new ArrayList();
/* 574 */       super.drawScreen(mouseX, mouseY, partialTicks);
/* 575 */       this.inventorySlots.inventorySlots = inventory;
/*     */     } 
/*     */     
/* 578 */     if (getScreenId() == 0 && this.current.isCurrentPageModel()) {
/* 579 */       RenderHelper.enableGUIStandardItemLighting();
/* 580 */       drawModel(partialTicks);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawModel(float partialTicks) {
/* 585 */     W_ModelCustom model = this.current.getModel();
/* 586 */     double scl = 162.0D / ((MathHelper.abs(model.size) < 0.01D) ? 0.01D : model.size);
/* 587 */     this.mc.getTextureManager().bindTexture(this.current.getModelTexture());
/* 588 */     GL11.glPushMatrix();
/* 589 */     double cx = (model.maxX - model.minX) * 0.5D + model.minX;
/* 590 */     double cy = (model.maxY - model.minY) * 0.5D + model.minY;
/* 591 */     double cz = (model.maxZ - model.minZ) * 0.5D + model.minZ;
/* 592 */     if (this.current.modelRot == 0) {
/* 593 */       GL11.glTranslated(cx * scl, cz * scl, 0.0D);
/*     */     } else {
/* 595 */       GL11.glTranslated(cz * scl, cy * scl, 0.0D);
/*     */     } 
/*     */     
/* 598 */     GL11.glTranslated(((this.guiLeft + 300) + modelPosX), ((this.guiTop + 110) + modelPosY), 550.0D);
/* 599 */     GL11.glRotated(modelRotX, 1.0D, 0.0D, 0.0D);
/* 600 */     GL11.glRotated(modelRotY, 0.0D, 1.0D, 0.0D);
/* 601 */     GL11.glScaled(scl * modelZoom, scl * modelZoom, -scl * modelZoom);
/* 602 */     GL11.glDisable(32826);
/* 603 */     GL11.glDisable(2896);
/* 604 */     GL11.glEnable(3008);
/* 605 */     GL11.glEnable(3042);
/* 606 */     int faceNum = model.getFaceNum();
/* 607 */     if (this.drawFace < faceNum * 2) {
/* 608 */       GL11.glColor4d(0.10000000149011612D, 0.10000000149011612D, 0.10000000149011612D, 1.0D);
/* 609 */       GL11.glDisable(3553);
/* 610 */       GL11.glPolygonMode(1032, 6913);
/* 611 */       float lw = GL11.glGetFloat(2849);
/* 612 */       GL11.glLineWidth(1.0F);
/* 613 */       model.renderAll(this.drawFace - faceNum, this.drawFace);
/* 614 */       MCH_RenderAircraft.renderCrawlerTrack(null, this.current.getAcInfo(), partialTicks);
/* 615 */       GL11.glLineWidth(lw);
/* 616 */       GL11.glPolygonMode(1032, 6914);
/* 617 */       GL11.glEnable(3553);
/*     */     } 
/*     */     
/* 620 */     if (this.drawFace >= faceNum) {
/* 621 */       GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
/* 622 */       model.renderAll(0, this.drawFace - faceNum);
/* 623 */       MCH_RenderAircraft.renderCrawlerTrack(null, this.current.getAcInfo(), partialTicks);
/*     */     } 
/*     */     
/* 626 */     GL11.glEnable(32826);
/* 627 */     GL11.glEnable(2896);
/* 628 */     GL11.glPopMatrix();
/* 629 */     if (this.drawFace < 10000000) {
/* 630 */       this.drawFace = (int)(this.drawFace + 20.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
/* 635 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 636 */     float z = this.zLevel;
/* 637 */     this.zLevel = 0.0F;
/* 638 */     W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
/* 639 */     if (getScreenId() == 0) {
/* 640 */       drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
/*     */     }
/*     */     
/* 643 */     if (getScreenId() == 1) {
/* 644 */       drawTexturedModalRect(this.guiLeft, this.guiTop, 0, this.ySize, this.xSize, this.ySize);
/* 645 */       List<GuiButton> list = this.screenButtonList.get(1);
/* 646 */       int index = (int)this.listSlider.getSliderValue() * 2;
/*     */       
/* 648 */       for (int i = 0; i < 6; i++) {
/* 649 */         W_GuiButton.setVisible(list.get(i), (index + i < getCurrentList().getRecipeListSize()));
/*     */       }
/*     */     } 
/*     */     
/* 653 */     this.zLevel = z;
/*     */   }
/*     */   
/*     */   public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6) {
/* 657 */     float w = 0.001953125F;
/* 658 */     float h = 0.001953125F;
/* 659 */     Tessellator tessellator = Tessellator.getInstance();
/* 660 */     BufferBuilder buffer = tessellator.getBuffer();
/* 661 */     buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 662 */     buffer.pos(par1, (par2 + par6), this.zLevel).tex((par3 * w), ((par4 + par6) * h)).endVertex();
/* 663 */     buffer.pos((par1 + par5), (par2 + par6), this.zLevel).tex(((par3 + par5) * w), ((par4 + par6) * h)).endVertex();
/* 664 */     buffer.pos((par1 + par5), par2, this.zLevel).tex(((par3 + par5) * w), (par4 * h)).endVertex();
/* 665 */     buffer.pos(par1, par2, this.zLevel).tex((par3 * w), (par4 * h)).endVertex();
/* 666 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public void drawTexturedModalRect(int dx, int dy, int dw, int dh, int u, int v, int tw, int th) {
/* 670 */     float w = 0.001953125F;
/* 671 */     float h = 0.001953125F;
/* 672 */     Tessellator tessellator = Tessellator.getInstance();
/* 673 */     BufferBuilder buffer = tessellator.getBuffer();
/* 674 */     buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 675 */     buffer.pos(dx, (dy + dh), this.zLevel).tex((u * w), ((v + th) * h)).endVertex();
/* 676 */     buffer.pos((dx + dw), (dy + dh), this.zLevel).tex(((u + tw) * w), ((v + th) * h)).endVertex();
/* 677 */     buffer.pos((dx + dw), dy, this.zLevel).tex(((u + tw) * w), (v * h)).endVertex();
/* 678 */     buffer.pos(dx, dy, this.zLevel).tex((u * w), (v * h)).endVertex();
/* 679 */     tessellator.draw();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\block\MCH_DraftingTableGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */