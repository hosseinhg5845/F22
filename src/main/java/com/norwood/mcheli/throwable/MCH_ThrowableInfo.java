/*     */ package com.norwood.mcheli.throwable;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_BaseInfo;
/*     */ import com.norwood.mcheli.MCH_Color;
/*     */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*     */ import com.norwood.mcheli.helper.client._IModelCustom;
/*     */ import com.norwood.mcheli.helper.info.IItemContent;
/*     */ import com.norwood.mcheli.wrapper.W_Item;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ 
/*     */ public class MCH_ThrowableInfo
/*     */   extends MCH_BaseInfo implements IItemContent {
/*     */   public final String name;
/*     */   public String displayName;
/*     */   public final HashMap<String, String> displayNameLang;
/*     */   public int itemID;
/*     */   public W_Item item;
/*     */   public List<String> recipeString;
/*     */   public final List<IRecipe> recipe;
/*     */   public boolean isShapedRecipe;
/*     */   public int power;
/*     */   public float acceleration;
/*     */   public float accelerationInWater;
/*     */   public float dispenseAcceleration;
/*     */   public int explosion;
/*     */   public int delayFuse;
/*     */   public float bound;
/*     */   public int timeFuse;
/*     */   public boolean flaming;
/*     */   public int stackSize;
/*     */   public float soundVolume;
/*     */   public float soundPitch;
/*     */   public float proximityFuseDist;
/*     */   public float accuracy;
/*     */   public int aliveTime;
/*     */   public int bomblet;
/*     */   public float bombletDiff;
/*     */   public _IModelCustom model;
/*     */   public float smokeSize;
/*     */   public int smokeNum;
/*     */   public float smokeVelocityVertical;
/*     */   public float smokeVelocityHorizontal;
/*     */   public float gravity;
/*     */   public float gravityInWater;
/*     */   public String particleName;
/*     */   public boolean disableSmoke;
/*     */   public MCH_Color smokeColor;
/*     */   
/*     */   public MCH_ThrowableInfo(AddonResourceLocation location, String path) {
/*  54 */     super(location, path);
/*  55 */     this.name = location.getResourcePath();
/*  56 */     this.displayName = location.getResourcePath();
/*  57 */     this.displayNameLang = new HashMap<>();
/*  58 */     this.itemID = 0;
/*  59 */     this.item = null;
/*  60 */     this.recipeString = new ArrayList<>();
/*  61 */     this.recipe = new ArrayList<>();
/*  62 */     this.isShapedRecipe = true;
/*  63 */     this.power = 0;
/*  64 */     this.acceleration = 1.0F;
/*  65 */     this.accelerationInWater = 1.0F;
/*  66 */     this.dispenseAcceleration = 1.0F;
/*  67 */     this.explosion = 0;
/*  68 */     this.delayFuse = 0;
/*  69 */     this.bound = 0.2F;
/*  70 */     this.timeFuse = 0;
/*  71 */     this.flaming = false;
/*  72 */     this.stackSize = 1;
/*  73 */     this.soundVolume = 1.0F;
/*  74 */     this.soundPitch = 1.0F;
/*  75 */     this.proximityFuseDist = 0.0F;
/*  76 */     this.accuracy = 0.0F;
/*  77 */     this.aliveTime = 10;
/*  78 */     this.bomblet = 0;
/*  79 */     this.bombletDiff = 0.3F;
/*  80 */     this.model = null;
/*  81 */     this.smokeSize = 10.0F;
/*  82 */     this.smokeNum = 0;
/*  83 */     this.smokeVelocityVertical = 1.0F;
/*  84 */     this.smokeVelocityHorizontal = 1.0F;
/*  85 */     this.gravity = 0.0F;
/*  86 */     this.gravityInWater = -0.04F;
/*  87 */     this.particleName = "explode";
/*  88 */     this.disableSmoke = true;
/*  89 */     this.smokeColor = new MCH_Color();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validate() throws Exception {
/*  94 */     this.timeFuse *= 20;
/*  95 */     this.aliveTime *= 20;
/*  96 */     this.delayFuse *= 20;
/*  97 */     return super.validate();
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem() {
/* 102 */     return (Item)this.item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadItemData(String item, String data) {
/* 107 */     if (item.compareTo("displayname") == 0) {
/* 108 */       this.displayName = data;
/* 109 */     } else if (item.compareTo("adddisplayname") == 0) {
/* 110 */       String[] s = data.split("\\s*,\\s*");
/* 111 */       if (s.length == 2) {
/* 112 */         this.displayNameLang.put(s[0].trim(), s[1].trim());
/*     */       }
/* 114 */     } else if (item.compareTo("itemid") == 0) {
/* 115 */       this.itemID = toInt(data, 0, 65535);
/* 116 */     } else if (item.compareTo("addrecipe") == 0 || item.compareTo("addshapelessrecipe") == 0) {
/* 117 */       this.isShapedRecipe = (item.compareTo("addrecipe") == 0);
/* 118 */       this.recipeString.add(data.toUpperCase());
/* 119 */     } else if (item.compareTo("power") == 0) {
/* 120 */       this.power = toInt(data);
/* 121 */     } else if (item.compareTo("acceleration") == 0) {
/* 122 */       this.acceleration = toFloat(data, 0.0F, 100.0F);
/* 123 */     } else if (item.compareTo("accelerationinwater") == 0) {
/* 124 */       this.accelerationInWater = toFloat(data, 0.0F, 100.0F);
/* 125 */     } else if (item.equalsIgnoreCase("DispenseAcceleration")) {
/* 126 */       this.dispenseAcceleration = toFloat(data, 0.0F, 1000.0F);
/* 127 */     } else if (item.compareTo("explosion") == 0) {
/* 128 */       this.explosion = toInt(data, 0, 50);
/* 129 */     } else if (item.equalsIgnoreCase("DelayFuse")) {
/* 130 */       this.delayFuse = toInt(data, 0, 100000);
/* 131 */     } else if (item.equalsIgnoreCase("Bound")) {
/* 132 */       this.bound = toFloat(data, 0.0F, 100000.0F);
/* 133 */     } else if (item.equalsIgnoreCase("TimeFuse")) {
/* 134 */       this.timeFuse = toInt(data, 0, 100000);
/* 135 */     } else if (item.compareTo("flaming") == 0) {
/* 136 */       this.flaming = toBool(data);
/* 137 */     } else if (item.equalsIgnoreCase("StackSize")) {
/* 138 */       this.stackSize = toInt(data, 1, 64);
/* 139 */     } else if (item.compareTo("soundvolume") == 0) {
/* 140 */       this.soundVolume = toFloat(data, 0.0F, 1000.0F);
/* 141 */     } else if (item.compareTo("soundpitch") == 0) {
/* 142 */       this.soundPitch = toFloat(data, 0.0F, 1.0F);
/* 143 */     } else if (item.compareTo("proximityfusedist") == 0) {
/* 144 */       this.proximityFuseDist = toFloat(data, 0.0F, 20.0F);
/* 145 */     } else if (item.compareTo("accuracy") == 0) {
/* 146 */       this.accuracy = toFloat(data, 0.0F, 1000.0F);
/* 147 */     } else if (item.equalsIgnoreCase("aliveTime")) {
/* 148 */       this.aliveTime = toInt(data, 0, 1000000);
/* 149 */     } else if (item.compareTo("bomblet") == 0) {
/* 150 */       this.bomblet = toInt(data, 0, 1000);
/* 151 */     } else if (item.equalsIgnoreCase("BombletDiff")) {
/* 152 */       this.bombletDiff = toFloat(data, 0.0F, 1000.0F);
/* 153 */     } else if (item.equalsIgnoreCase("SmokeSize")) {
/* 154 */       this.smokeSize = toFloat(data, 0.0F, 1000.0F);
/* 155 */     } else if (item.equalsIgnoreCase("SmokeNum")) {
/* 156 */       this.smokeNum = toInt(data, 0, 1000);
/* 157 */     } else if (item.equalsIgnoreCase("SmokeVelocityVertical")) {
/* 158 */       this.smokeVelocityVertical = toFloat(data, -100.0F, 100.0F);
/* 159 */     } else if (item.equalsIgnoreCase("SmokeVelocityHorizontal")) {
/* 160 */       this.smokeVelocityHorizontal = toFloat(data, 0.0F, 1000.0F);
/* 161 */     } else if (item.compareTo("gravity") == 0) {
/* 162 */       this.gravity = toFloat(data, -50.0F, 50.0F);
/* 163 */     } else if (item.equalsIgnoreCase("gravityInWater")) {
/* 164 */       this.gravityInWater = toFloat(data, -50.0F, 50.0F);
/* 165 */     } else if (item.compareTo("particle") == 0) {
/* 166 */       this.particleName = data.toLowerCase().trim();
/* 167 */       if (this.particleName.equalsIgnoreCase("none")) {
/* 168 */         this.particleName = "";
/*     */       }
/* 170 */     } else if (item.equalsIgnoreCase("DisableSmoke")) {
/* 171 */       this.disableSmoke = toBool(data);
/* 172 */     } else if (item.equalsIgnoreCase("SmokeColor")) {
/* 173 */       String[] s = data.split("\\s*,\\s*");
/* 174 */       if (s.length >= 3) {
/* 175 */         this
/* 176 */           .smokeColor = new MCH_Color(1.0F, 0.003921569F * toInt(s[0], 0, 255), 0.003921569F * toInt(s[1], 0, 255), 0.003921569F * toInt(s[2], 0, 255));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class RoundItem
/*     */   {
/*     */     public final int num;
/*     */     public final Item item;
/*     */     
/*     */     public RoundItem(MCH_ThrowableInfo paramMCH_ThrowableInfo, int n, Item i) {
/* 187 */       this.num = n;
/* 188 */       this.item = i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\throwable\MCH_ThrowableInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */