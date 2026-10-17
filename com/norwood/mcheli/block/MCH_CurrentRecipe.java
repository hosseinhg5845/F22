/*     */ package com.norwood.mcheli.block;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_IRecipeList;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.MCH_ModelManager;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfoManager;
/*     */ import com.norwood.mcheli.plane.MCP_PlaneInfo;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponInfo;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponInfoManager;
/*     */ import com.norwood.mcheli.wrapper.modelloader.W_ModelCustom;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class MCH_CurrentRecipe
/*     */ {
/*     */   public final IRecipe recipe;
/*     */   public final int index;
/*     */   public final String displayName;
/*     */   public final List<ResourceLocation> descTexture;
/*     */   private final MCH_AircraftInfo acInfo;
/*     */   public List<String> infoItem;
/*     */   public List<String> infoData;
/*     */   public int modelRot;
/*     */   private int descMaxPage;
/*     */   private int descPage;
/*     */   private W_ModelCustom model;
/*     */   private ResourceLocation modelTexture;
/*     */   
/*     */   public MCH_CurrentRecipe(MCH_IRecipeList list, int idx) {
/*  36 */     if (list.getRecipeListSize() > 0) {
/*  37 */       this.recipe = list.getRecipe(idx);
/*     */     } else {
/*  39 */       this.recipe = null;
/*     */     } 
/*     */     
/*  42 */     this.index = idx;
/*  43 */     this.displayName = (this.recipe != null) ? this.recipe.func_77571_b().func_82833_r() : "None";
/*  44 */     this.descTexture = getDescTexture(this.recipe);
/*  45 */     this.descPage = 0;
/*  46 */     this.descMaxPage = this.descTexture.size();
/*  47 */     MCH_AircraftInfo info = null;
/*  48 */     if (list instanceof MCH_AircraftInfoManager) {
/*  49 */       info = ((MCH_AircraftInfoManager)list).getAcInfoFromItem(this.recipe);
/*  50 */       if (info != null) {
/*  51 */         this.descMaxPage++;
/*  52 */         String dir = info.getDirectoryName();
/*  53 */         String name = info.name;
/*  54 */         this.model = MCH_ModelManager.get(dir, name);
/*  55 */         if (this.model != null) {
/*  56 */           this.modelTexture = new ResourceLocation("mcheli", "textures/" + dir + "/" + name + ".png");
/*  57 */           this.descMaxPage++;
/*  58 */           if (list instanceof com.norwood.mcheli.plane.MCP_PlaneInfoManager) {
/*  59 */             this.modelRot = 0;
/*     */           } else {
/*  61 */             this.modelRot = 1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  67 */     getAcInfoText(info);
/*  68 */     this.acInfo = info;
/*     */   }
/*     */   
/*     */   private void getAcInfoText(MCH_AircraftInfo info) {
/*  72 */     this.infoItem = new ArrayList<>();
/*  73 */     this.infoData = new ArrayList<>();
/*  74 */     if (info != null) {
/*  75 */       getAcInfoTextSub("Name", info.getItemStack().func_82833_r());
/*  76 */       getAcInfoTextSub("HP", "" + info.maxHp);
/*  77 */       int seatNum = !info.isUAV ? info.getNumSeat() : (info.getNumSeat() - 1);
/*  78 */       getAcInfoTextSub("Num of Seat", "" + seatNum);
/*  79 */       getAcInfoTextSub("GunnerMode", info.isEnableGunnerMode ? "YES" : "NO");
/*  80 */       getAcInfoTextSub("NightVision", info.isEnableNightVision ? "YES" : "NO");
/*  81 */       getAcInfoTextSub("Radar", info.isEnableEntityRadar ? "YES" : "NO");
/*  82 */       getAcInfoTextSub("Inventory", "" + info.inventorySize);
/*  83 */       if (info instanceof MCP_PlaneInfo) { MCP_PlaneInfo pinfo = (MCP_PlaneInfo)info;
/*  84 */         getAcInfoTextSub("VTOL", pinfo.isEnableVtol ? "YES" : "NO"); }
/*     */ 
/*     */       
/*  87 */       if (info.getWeaponNum() > 0) {
/*  88 */         getAcInfoTextSub("Armed----------------");
/*     */         
/*  90 */         for (int i = 0; i < info.getWeaponNum(); i++) {
/*  91 */           String type = (info.getWeaponSetById(i)).type;
/*  92 */           MCH_WeaponInfo winfo = MCH_WeaponInfoManager.get(type);
/*  93 */           if (winfo != null) {
/*  94 */             getAcInfoTextSub(winfo.getWeaponTypeName(), winfo.displayName);
/*     */           } else {
/*  96 */             getAcInfoTextSub("ERROR", "Not found weapon " + (i + 1));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getAcInfoTextSub(String item, String data) {
/* 104 */     this.infoItem.add(item + " :");
/* 105 */     this.infoData.add(data);
/*     */   }
/*     */   
/*     */   private void getAcInfoTextSub(String item) {
/* 109 */     this.infoItem.add(item);
/* 110 */     this.infoData.add("");
/*     */   }
/*     */   
/*     */   public void switchNextPage() {
/* 114 */     if (this.descMaxPage >= 2) {
/* 115 */       this.descPage = (this.descPage + 1) % this.descMaxPage;
/*     */     } else {
/* 117 */       this.descPage = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void switchPrevPage() {
/* 122 */     this.descPage--;
/* 123 */     if (this.descPage < 0 && this.descMaxPage >= 2) {
/* 124 */       this.descPage = this.descMaxPage - 1;
/*     */     } else {
/* 126 */       this.descPage = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getDescCurrentPage() {
/* 131 */     return this.descPage;
/*     */   }
/*     */   
/*     */   public void setDescCurrentPage(int page) {
/* 135 */     if (this.descMaxPage > 0) {
/* 136 */       this.descPage = (page < this.descMaxPage) ? page : (this.descMaxPage - 1);
/*     */     } else {
/* 138 */       this.descPage = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getDescMaxPage() {
/* 143 */     return this.descMaxPage;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getCurrentPageTexture() {
/* 148 */     return (this.descPage < this.descTexture.size()) ? this.descTexture.get(this.descPage) : null;
/*     */   }
/*     */   
/*     */   public W_ModelCustom getModel() {
/* 152 */     return this.model;
/*     */   }
/*     */   
/*     */   public ResourceLocation getModelTexture() {
/* 156 */     return this.modelTexture;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MCH_AircraftInfo getAcInfo() {
/* 161 */     return this.acInfo;
/*     */   }
/*     */   
/*     */   public boolean isCurrentPageTexture() {
/* 165 */     return (this.descPage >= 0 && this.descPage < this.descTexture.size());
/*     */   }
/*     */   
/*     */   public boolean isCurrentPageModel() {
/* 169 */     return (getAcInfo() != null && getModel() != null && this.descPage == this.descTexture.size());
/*     */   }
/*     */   
/*     */   public boolean isCurrentPageAcInfo() {
/* 173 */     return (getAcInfo() != null && this.descPage == this.descMaxPage - 1);
/*     */   }
/*     */   
/*     */   private List<ResourceLocation> getDescTexture(@Nullable IRecipe r) {
/* 177 */     List<ResourceLocation> list = new ArrayList<>();
/* 178 */     if (r != null) {
/* 179 */       for (int i = 0; i < 20; i++) {
/* 180 */         String itemName = r.func_77571_b().func_77977_a();
/* 181 */         if (itemName.startsWith("tile.")) {
/* 182 */           itemName = itemName.substring(5);
/*     */         }
/*     */         
/* 185 */         if (itemName.contains(":")) {
/* 186 */           itemName = itemName.substring(itemName.indexOf(":") + 1);
/*     */         }
/*     */         
/* 189 */         itemName = "textures/drafting_table_desc/" + itemName + "#" + i + ".png";
/* 190 */         File filePng = new File(MCH_MOD.sourcePath, "/assets/mcheli/" + itemName);
/* 191 */         if (filePng.exists()) {
/* 192 */           list.add(new ResourceLocation("mcheli", itemName));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 197 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\block\MCH_CurrentRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */