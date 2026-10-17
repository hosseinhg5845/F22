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
/*  81 */     this.field_146999_f = 400;
/*  82 */     this.field_147000_g = 240;
/*  83 */     this.screenButtonList = new ArrayList<>();
/*  84 */     this.drawFace = 0;
/*  85 */     this.buttonClickWait = 0;
/*  86 */     MCH_Lib.DbgLog(player.field_70170_p, "MCH_DraftingTableGui.MCH_DraftingTableGui", new Object[0]);
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
/*     */   public void func_73866_w_() {
/*  98 */     super.func_73866_w_();
/*  99 */     this.field_146292_n.clear();
/* 100 */     this.screenButtonList.clear();
/* 101 */     this.screenButtonList.add(new ArrayList<>());
/* 102 */     this.screenButtonList.add(new ArrayList<>());
/* 103 */     List<GuiButton> list = this.screenButtonList.get(0);
/* 104 */     GuiButton btnHeli = new GuiButton(10, this.field_147003_i + 20, this.field_147009_r + 20, 90, 20, "Helicopter List");
/* 105 */     GuiButton btnPlane = new GuiButton(11, this.field_147003_i + 20, this.field_147009_r + 40, 90, 20, "Plane List");
/* 106 */     GuiButton btnVehicle = new GuiButton(12, this.field_147003_i + 20, this.field_147009_r + 60, 90, 20, "Vehicle List");
/* 107 */     GuiButton btnTank = new GuiButton(13, this.field_147003_i + 20, this.field_147009_r + 80, 90, 20, "Tank List");
/* 108 */     GuiButton btnItem = new GuiButton(14, this.field_147003_i + 20, this.field_147009_r + 100, 90, 20, "Item List");
/* 109 */     btnHeli.field_146124_l = (MCH_HeliInfoManager.getInstance().getRecipeListSize() > 0);
/* 110 */     btnPlane.field_146124_l = (MCP_PlaneInfoManager.getInstance().getRecipeListSize() > 0);
/* 111 */     btnVehicle.field_146124_l = (MCH_VehicleInfoManager.getInstance().getRecipeListSize() > 0);
/* 112 */     btnTank.field_146124_l = (MCH_TankInfoManager.getInstance().getRecipeListSize() > 0);
/* 113 */     btnItem.field_146124_l = (MCH_ItemRecipe.getInstance().getRecipeListSize() > 0);
/* 114 */     list.add(btnHeli);
/* 115 */     list.add(btnPlane);
/* 116 */     list.add(btnVehicle);
/* 117 */     list.add(btnTank);
/* 118 */     list.add(btnItem);
/* 119 */     this.buttonCreate = new GuiButton(30, this.field_147003_i + 120, this.field_147009_r + 89, 50, 20, "Create");
/* 120 */     this.buttonPrev = new GuiButton(21, this.field_147003_i + 120, this.field_147009_r + 111, 36, 20, "<<");
/* 121 */     this.buttonNext = new GuiButton(20, this.field_147003_i + 155, this.field_147009_r + 111, 35, 20, ">>");
/* 122 */     list.add(this.buttonCreate);
/* 123 */     list.add(this.buttonPrev);
/* 124 */     list.add(this.buttonNext);
/* 125 */     this.buttonPrevPage = new GuiButton(51, this.field_147003_i + 210, this.field_147009_r + 210, 60, 20, "Prev Page");
/* 126 */     this.buttonNextPage = new GuiButton(50, this.field_147003_i + 270, this.field_147009_r + 210, 60, 20, "Next Page");
/* 127 */     list.add(this.buttonPrevPage);
/* 128 */     list.add(this.buttonNextPage);
/* 129 */     list = this.screenButtonList.get(1);
/* 130 */     int y = 0;
/*     */     
/* 132 */     for (int i = 0; y < 3; y++) {
/* 133 */       for (int x = 0; x < 2; i++) {
/* 134 */         int px = this.field_147003_i + 30 + x * 140;
/* 135 */         int py = this.field_147009_r + 40 + y * 70;
/* 136 */         list.add(new GuiButton(40 + i, px, py, 45, 20, "Select"));
/* 137 */         x++;
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     this.listSlider = new MCH_GuiSliderVertical(0, this.field_147003_i + 360, this.field_147009_r + 20, 20, 200, "", 0.0F, 0.0F, 0.0F, 1.0F);
/* 142 */     list.add(this.listSlider);
/*     */     
/* 144 */     for (List<GuiButton> guiButtons : this.screenButtonList) {
/* 145 */       list = guiButtons;
/*     */       
/* 147 */       this.field_146292_n.addAll(list);
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
/* 187 */     for (GuiButton guiButton : this.field_146292_n) {
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
/* 209 */     if (this.current == null || currentRecipe == null || !this.current.recipe.func_77571_b().func_77969_a(currentRecipe.recipe.func_77571_b())) {
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
/*     */   public void func_73876_c() {
/* 240 */     super.func_73876_c();
/* 241 */     if (this.buttonClickWait > 0) {
/* 242 */       this.buttonClickWait--;
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_146281_b() {
/* 247 */     super.func_146281_b();
/* 248 */     MCH_Lib.DbgLog(this.thePlayer.field_70170_p, "MCH_DraftingTableGui.onGuiClosed", new Object[0]);
/*     */   }
/*     */   
/*     */   protected void func_146284_a(@NotNull GuiButton button) throws IOException {
/* 252 */     super.func_146284_a(button);
/* 253 */     if (this.buttonClickWait <= 0 && 
/* 254 */       button.field_146124_l) {
/* 255 */       int page, index, i; this.buttonClickWait = 3;
/* 256 */       switch (button.field_146127_k) {
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
/* 312 */           index = (int)this.listSlider.getSliderValue() * 2 + button.field_146127_k - 40;
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
/* 336 */     MCH_DraftingTableGuiContainer container = (MCH_DraftingTableGuiContainer)this.field_147002_h;
/* 337 */     this.buttonCreate.field_146124_l = false;
/* 338 */     if (!container.func_75139_a(container.outputSlotIndex).func_75216_d()) {
/* 339 */       this.buttonCreate.field_146124_l = MCH_Recipes.canCraft(this.thePlayer, this.current.recipe);
/*     */     }
/*     */     
/* 342 */     if (this.thePlayer.field_71075_bZ.field_75098_d) {
/* 343 */       this.buttonCreate.field_146124_l = true;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void func_73869_a(char par1, int keycode) throws IOException {
/* 348 */     if (keycode == 1 || keycode == W_KeyBinding.getKeyCode((Minecraft.func_71410_x()).field_71474_y.field_151445_Q)) {
/* 349 */       if (getScreenId() == 0) {
/* 350 */         this.field_146297_k.field_71439_g.func_71053_j();
/*     */       } else {
/* 352 */         switchScreen(0);
/*     */       } 
/*     */     }
/*     */     
/* 356 */     if (getScreenId() == 0) {
/* 357 */       if (keycode == 205) {
/* 358 */         func_146284_a(this.buttonNext);
/*     */       }
/*     */       
/* 361 */       if (keycode == 203) {
/* 362 */         func_146284_a(this.buttonPrev);
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
/*     */   protected void func_146979_b(int mx, int my) {
/* 376 */     super.func_146979_b(mx, my);
/* 377 */     this.field_73735_i = 0.0F;
/* 378 */     GL11.glEnable(3042);
/* 379 */     if (getScreenId() == 0) {
/* 380 */       ArrayList<String> list = new ArrayList<>();
/* 381 */       if (this.current != null) {
/* 382 */         if (this.current.isCurrentPageTexture()) {
/* 383 */           GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
/* 384 */           this.field_146297_k.func_110434_K().func_110577_a(this.current.getCurrentPageTexture());
/* 385 */           drawTexturedModalRect(210, 20, 170, 190, 0, 0, 340, 380);
/* 386 */         } else if (this.current.isCurrentPageAcInfo()) {
/* 387 */           for (int i = 0; i < this.current.infoItem.size(); i++) {
/* 388 */             this.field_146289_q.func_78276_b(this.current.infoItem.get(i), 210, 40 + 10 * i, -9491968);
/* 389 */             String data = this.current.infoData.get(i);
/* 390 */             if (!data.isEmpty()) {
/* 391 */               this.field_146289_q.func_78276_b(data, 280, 40 + 10 * i, -9491968);
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
/* 411 */         drawHoveringText(list, mx - 30, my, this.field_146289_q);
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
/* 424 */             String s = getCurrentList().getRecipe(index + ix).func_77571_b().func_82833_r();
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
/* 440 */             func_73729_b(rx, ry, 400, 0, 75, 54);
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
/*     */   protected void func_184098_a(@NotNull Slot slotIn, int slotId, int clickedButton, @NotNull ClickType clickType) {
/* 464 */     if (getScreenId() != 1) {
/* 465 */       super.func_184098_a(slotIn, slotId, clickedButton, clickType);
/*     */     }
/*     */   }
/*     */   
/*     */   private int getScreenId() {
/* 470 */     return this.screenId;
/*     */   }
/*     */   
/*     */   public void drawItemRecipe(IRecipe recipe, int x, int y) {
/* 474 */     if (recipe != null) {
/* 475 */       recipe.func_77571_b();
/* 476 */       recipe.func_77571_b().func_77973_b();
/* 477 */       RenderHelper.func_74520_c();
/* 478 */       NonNullList<Ingredient> ingredients = recipe.func_192400_c();
/*     */       
/* 480 */       for (int i = 0; i < ingredients.size(); i++) {
/* 481 */         drawIngredient((Ingredient)ingredients.get(i), x + i % 3 * 18, y + i / 3 * 18);
/*     */       }
/*     */       
/* 484 */       drawItemStack(recipe.func_77571_b(), x + 54 + 3, y + 18);
/* 485 */       RenderHelper.func_74518_a();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_146274_d() throws IOException {
/* 490 */     super.func_146274_d();
/* 491 */     int dx = Mouse.getEventDX();
/* 492 */     int dy = Mouse.getEventDY();
/* 493 */     if (getScreenId() == 0 && Mouse.getX() > this.field_146297_k.field_71443_c / 2) {
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
/* 556 */           func_146284_a(this.buttonPrev);
/*     */         } else {
/* 558 */           func_146284_a(this.buttonNext);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/* 566 */     GL11.glEnable(3042);
/* 567 */     GL11.glBlendFunc(770, 771);
/* 568 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 569 */     if (getScreenId() == 0) {
/* 570 */       super.func_73863_a(mouseX, mouseY, partialTicks);
/*     */     } else {
/* 572 */       List<Slot> inventory = this.field_147002_h.field_75151_b;
/* 573 */       this.field_147002_h.field_75151_b = new ArrayList();
/* 574 */       super.func_73863_a(mouseX, mouseY, partialTicks);
/* 575 */       this.field_147002_h.field_75151_b = inventory;
/*     */     } 
/*     */     
/* 578 */     if (getScreenId() == 0 && this.current.isCurrentPageModel()) {
/* 579 */       RenderHelper.func_74520_c();
/* 580 */       drawModel(partialTicks);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawModel(float partialTicks) {
/* 585 */     W_ModelCustom model = this.current.getModel();
/* 586 */     double scl = 162.0D / ((MathHelper.func_76135_e(model.size) < 0.01D) ? 0.01D : model.size);
/* 587 */     this.field_146297_k.func_110434_K().func_110577_a(this.current.getModelTexture());
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
/* 598 */     GL11.glTranslated(((this.field_147003_i + 300) + modelPosX), ((this.field_147009_r + 110) + modelPosY), 550.0D);
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
/*     */   protected void func_146976_a(float var1, int var2, int var3) {
/* 635 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 636 */     float z = this.field_73735_i;
/* 637 */     this.field_73735_i = 0.0F;
/* 638 */     W_McClient.MOD_bindTexture("textures/gui/drafting_table.png");
/* 639 */     if (getScreenId() == 0) {
/* 640 */       func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*     */     }
/*     */     
/* 643 */     if (getScreenId() == 1) {
/* 644 */       func_73729_b(this.field_147003_i, this.field_147009_r, 0, this.field_147000_g, this.field_146999_f, this.field_147000_g);
/* 645 */       List<GuiButton> list = this.screenButtonList.get(1);
/* 646 */       int index = (int)this.listSlider.getSliderValue() * 2;
/*     */       
/* 648 */       for (int i = 0; i < 6; i++) {
/* 649 */         W_GuiButton.setVisible(list.get(i), (index + i < getCurrentList().getRecipeListSize()));
/*     */       }
/*     */     } 
/*     */     
/* 653 */     this.field_73735_i = z;
/*     */   }
/*     */   
/*     */   public void func_73729_b(int par1, int par2, int par3, int par4, int par5, int par6) {
/* 657 */     float w = 0.001953125F;
/* 658 */     float h = 0.001953125F;
/* 659 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 660 */     BufferBuilder buffer = tessellator.func_178180_c();
/* 661 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 662 */     buffer.func_181662_b(par1, (par2 + par6), this.field_73735_i).func_187315_a((par3 * w), ((par4 + par6) * h)).func_181675_d();
/* 663 */     buffer.func_181662_b((par1 + par5), (par2 + par6), this.field_73735_i).func_187315_a(((par3 + par5) * w), ((par4 + par6) * h)).func_181675_d();
/* 664 */     buffer.func_181662_b((par1 + par5), par2, this.field_73735_i).func_187315_a(((par3 + par5) * w), (par4 * h)).func_181675_d();
/* 665 */     buffer.func_181662_b(par1, par2, this.field_73735_i).func_187315_a((par3 * w), (par4 * h)).func_181675_d();
/* 666 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public void drawTexturedModalRect(int dx, int dy, int dw, int dh, int u, int v, int tw, int th) {
/* 670 */     float w = 0.001953125F;
/* 671 */     float h = 0.001953125F;
/* 672 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 673 */     BufferBuilder buffer = tessellator.func_178180_c();
/* 674 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 675 */     buffer.func_181662_b(dx, (dy + dh), this.field_73735_i).func_187315_a((u * w), ((v + th) * h)).func_181675_d();
/* 676 */     buffer.func_181662_b((dx + dw), (dy + dh), this.field_73735_i).func_187315_a(((u + tw) * w), ((v + th) * h)).func_181675_d();
/* 677 */     buffer.func_181662_b((dx + dw), dy, this.field_73735_i).func_187315_a(((u + tw) * w), (v * h)).func_181675_d();
/* 678 */     buffer.func_181662_b(dx, dy, this.field_73735_i).func_187315_a((u * w), (v * h)).func_181675_d();
/* 679 */     tessellator.func_78381_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\block\MCH_DraftingTableGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */