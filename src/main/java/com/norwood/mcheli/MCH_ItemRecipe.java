/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfoManager;
/*     */ import com.norwood.mcheli.helicopter.MCH_HeliInfo;
/*     */ import com.norwood.mcheli.helicopter.MCH_HeliInfoManager;
/*     */ import com.norwood.mcheli.helper.MCH_Recipes;
/*     */ import com.norwood.mcheli.helper.MCH_Utils;
/*     */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*     */ import com.norwood.mcheli.plane.MCP_PlaneInfo;
/*     */ import com.norwood.mcheli.plane.MCP_PlaneInfoManager;
/*     */ import com.norwood.mcheli.tank.MCH_TankInfo;
/*     */ import com.norwood.mcheli.tank.MCH_TankInfoManager;
/*     */ import com.norwood.mcheli.throwable.MCH_ThrowableInfo;
/*     */ import com.norwood.mcheli.vehicle.MCH_VehicleInfo;
/*     */ import com.norwood.mcheli.vehicle.MCH_VehicleInfoManager;
/*     */ import com.norwood.mcheli.wrapper.W_Block;
/*     */ import com.norwood.mcheli.wrapper.W_Item;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.Ingredient;
/*     */ import net.minecraft.item.crafting.ShapedRecipes;
/*     */ import net.minecraft.item.crafting.ShapelessRecipes;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraftforge.registries.IForgeRegistry;
/*     */ 
/*     */ public class MCH_ItemRecipe implements MCH_IRecipeList {
/*  37 */   private static final MCH_ItemRecipe instance = new MCH_ItemRecipe();
/*  38 */   private static final List<IRecipe> commonItemRecipe = new ArrayList<>();
/*     */   
/*     */   public static MCH_ItemRecipe getInstance() {
/*  41 */     return instance;
/*     */   }
/*     */   
/*     */   private static void addRecipeList(IRecipe recipe) {
/*  45 */     if (recipe != null) {
/*  46 */       commonItemRecipe.add(recipe);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void registerCommonItemRecipe(IForgeRegistry<IRecipe> registry) {
/*  51 */     commonItemRecipe.clear();
/*  52 */     MCH_Recipes.register("charge_fuel", new MCH_RecipeFuel());
/*  53 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fuel")));
/*  54 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("gltd")));
/*  55 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("chain")));
/*  56 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("parachute")));
/*  57 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("container")));
/*     */     
/*  59 */     for (int i = 0; i < MCH_MOD.itemUavStation.length; i++) {
/*  60 */       addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("uav_station" + ((i > 0) ? ("" + (i + 1)) : ""))));
/*     */     }
/*     */     
/*  63 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("wrench")));
/*  64 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("range_finder")));
/*  65 */     MCH_Recipes.register("charge_power_range_finder", new MCH_RecipeReloadRangeFinder());
/*  66 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fim92")));
/*  67 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fim92_bullet")));
/*  68 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fgm148")));
/*  69 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fgm148_bullet")));
/*  70 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("spawn_gunner_vs_monster")));
/*  71 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("spawn_gunner_vs_player")));
/*  72 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("drafting_table")));
/*     */   }
/*     */   
/*     */   public static void registerItemRecipe(IForgeRegistry<IRecipe> registry) {
/*  76 */     registerCommonItemRecipe(registry);
/*     */     
/*  78 */     for (MCH_HeliInfo info : ContentRegistries.heli().values()) {
/*  79 */       addRecipeAndRegisterList((MCH_AircraftInfo)info, (Item)info.item, (MCH_AircraftInfoManager<MCH_AircraftInfo>)MCH_HeliInfoManager.getInstance());
/*     */     }
/*     */     
/*  82 */     for (MCP_PlaneInfo info : ContentRegistries.plane().values()) {
/*  83 */       addRecipeAndRegisterList((MCH_AircraftInfo)info, (Item)info.item, (MCH_AircraftInfoManager<MCH_AircraftInfo>)MCP_PlaneInfoManager.getInstance());
/*     */     }
/*     */     
/*  86 */     for (MCH_TankInfo info : ContentRegistries.tank().values()) {
/*  87 */       addRecipeAndRegisterList((MCH_AircraftInfo)info, (Item)info.item, (MCH_AircraftInfoManager<MCH_AircraftInfo>)MCH_TankInfoManager.getInstance());
/*     */     }
/*     */     
/*  90 */     for (MCH_VehicleInfo info : ContentRegistries.vehicle().values()) {
/*  91 */       addRecipeAndRegisterList((MCH_AircraftInfo)info, (Item)info.item, (MCH_AircraftInfoManager<MCH_AircraftInfo>)MCH_VehicleInfoManager.getInstance());
/*     */     }
/*     */     
/*  94 */     for (MCH_ThrowableInfo info : ContentRegistries.throwable().values()) {
/*  95 */       for (String s : info.recipeString) {
/*  96 */         if (s.length() >= 3) {
/*  97 */           IRecipe recipe = addRecipe(info.name, (Item)info.item, s, info.isShapedRecipe);
/*  98 */           if (recipe != null) {
/*  99 */             info.recipe.add(recipe);
/* 100 */             addRecipeList(recipe);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 105 */       info.recipeString = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static <T extends MCH_AircraftInfo> void addRecipeAndRegisterList(MCH_AircraftInfo info, Item item, MCH_AircraftInfoManager<T> im) {
/* 110 */     int count = 0;
/*     */     
/* 112 */     for (String s : info.recipeString) {
/* 113 */       count++;
/* 114 */       if (s.length() >= 3) {
/* 115 */         IRecipe recipe = addRecipe(info.name, item, s, info.isShapedRecipe);
/* 116 */         if (recipe != null) {
/* 117 */           info.recipe.add(recipe);
/* 118 */           im.addRecipe(recipe, count, info.name, s);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     info.recipeString = null;
/*     */   }
/*     */   
/*     */   public static IRecipe addRecipe(String name, Item item, String data) {
/* 127 */     return addShapedRecipe(name, item, data);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static IRecipe addRecipe(String name, Item item, String data, boolean isShaped) {
/* 132 */     return isShaped ? addShapedRecipe(name, item, data) : addShapelessRecipe(name, item, data);
/*     */   }
/*     */   @Nullable
/*     */   public static IRecipe addShapedRecipe(String name, Item item, String data) {
/*     */     ShapedRecipes r;
/* 137 */     ArrayList<Object> rcp = new ArrayList();
/* 138 */     String[] s = data.split("\\s*,\\s*");
/* 139 */     if (s.length < 3) {
/* 140 */       return null;
/*     */     }
/* 142 */     int start = 0;
/* 143 */     int createNum = 1;
/* 144 */     if (isNumber(s[0])) {
/* 145 */       start = 1;
/* 146 */       createNum = Integer.parseInt(s[0]);
/* 147 */       if (createNum <= 0) {
/* 148 */         createNum = 1;
/*     */       }
/*     */     } 
/*     */     
/* 152 */     Set<Integer> needShortChars = Sets.newHashSet();
/* 153 */     int idx = start;
/*     */     
/* 155 */     for (int i = start; i < 3 + start; i++) {
/* 156 */       if (!s[idx].isEmpty() && s[idx].charAt(0) == '"' && s[idx].charAt(s[idx].length() - 1) == '"') {
/* 157 */         String ingredientStr = s[idx].substring(1, s[idx].length() - 1);
/* 158 */         Objects.requireNonNull(needShortChars); ingredientStr.toUpperCase().chars().forEach(needShortChars::add);
/* 159 */         rcp.add(s[idx].subSequence(1, s[idx].length() - 1));
/* 160 */         idx++;
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     if (idx == 0) {
/* 165 */       return null;
/*     */     }
/* 167 */     boolean isChar = true;
/*     */     
/* 169 */     for (boolean flag = false; idx < s.length; idx++) {
/* 170 */       if (s[idx].isEmpty()) {
/* 171 */         return null;
/*     */       }
/*     */       
/* 174 */       if (isChar) {
/* 175 */         if (s[idx].length() != 1) {
/* 176 */           return null;
/*     */         }
/*     */         
/* 179 */         char c = s[idx].toUpperCase().charAt(0);
/* 180 */         if (c < 'A' || c > 'Z') {
/* 181 */           return null;
/*     */         }
/*     */         
/* 184 */         if (!needShortChars.contains(Integer.valueOf(c))) {
/* 185 */           MCH_Utils.logger().warn("Key defines symbols that aren't used in pattern: [{}], item:{}", Character.valueOf(c), name);
/* 186 */           flag = true;
/*     */         } 
/*     */         
/* 189 */         if (!flag) {
/* 190 */           rcp.add(Character.valueOf(c));
/*     */         }
/*     */       } else {
/* 193 */         if (!flag) {
/* 194 */           String nm = s[idx].trim().toLowerCase();
/* 195 */           int dmg = 0;
/* 196 */           if (idx + 1 < s.length && isNumber(s[idx + 1])) {
/* 197 */             dmg = Integer.parseInt(s[++idx]);
/*     */           }
/*     */           
/* 200 */           if (isNumber(nm)) {
/* 201 */             return null;
/*     */           }
/*     */           
/* 204 */           rcp.add(new ItemStack(W_Item.getItemByName(nm), 1, dmg));
/*     */         } 
/*     */         
/* 207 */         flag = false;
/*     */       } 
/*     */       
/* 210 */       isChar = !isChar;
/*     */     } 
/*     */     
/* 213 */     Object[] recipe = new Object[rcp.size()];
/*     */     
/* 215 */     for (int ix = 0; ix < recipe.length; ix++) {
/* 216 */       recipe[ix] = rcp.get(ix);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 221 */       r = MCH_Recipes.addShapedRecipe(name, new ItemStack(item, createNum), recipe);
/* 222 */     } catch (Exception var14) {
/* 223 */       MCH_Utils.logger().warn("{}, name:{}", var14.getMessage(), name);
/* 224 */       return null;
/*     */     } 
/*     */     
/* 227 */     for (int j = 0; j < r.recipeItems.size(); j++) {
/* 228 */       if (r.recipeItems.get(j) != Ingredient.EMPTY) {
/* 229 */         Arrays.<ItemStack>stream(((Ingredient)r.recipeItems.get(j)).getMatchingStacks()).anyMatch(stack -> {
/*     */               stack.getItem();
/*     */               
/*     */               return false;
/*     */             });
/*     */       }
/*     */     } 
/* 236 */     return (IRecipe)r;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static IRecipe addShapelessRecipe(String name, Item item, String data) {
/* 243 */     ArrayList<Object> rcp = new ArrayList();
/* 244 */     String[] s = data.split("\\s*,\\s*");
/* 245 */     if (s.length < 1) {
/* 246 */       return null;
/*     */     }
/* 248 */     int start = 0;
/* 249 */     int createNum = 1;
/* 250 */     isNumber(s[0]);
/*     */     
/* 252 */     for (int idx = start; idx < s.length; idx++) {
/* 253 */       if (s[idx].isEmpty()) {
/* 254 */         return null;
/*     */       }
/*     */       
/* 257 */       String nm = s[idx].trim().toLowerCase();
/* 258 */       int dmg = 0;
/* 259 */       if (idx + 1 < s.length && isNumber(s[idx + 1])) {
/* 260 */         dmg = Integer.parseInt(s[++idx]);
/*     */       }
/*     */       
/* 263 */       if (isNumber(nm)) {
/* 264 */         int n = Integer.parseInt(nm);
/* 265 */         if (n <= 255) {
/* 266 */           rcp.add(new ItemStack(W_Block.getBlockById(n), 1, dmg));
/* 267 */         } else if (n <= 511) {
/* 268 */           rcp.add(new ItemStack(W_Item.getItemById(n), 1, dmg));
/* 269 */         } else if (n <= 2255) {
/* 270 */           rcp.add(new ItemStack(W_Block.getBlockById(n), 1, dmg));
/* 271 */         } else if (n <= 2267) {
/* 272 */           rcp.add(new ItemStack(W_Item.getItemById(n), 1, dmg));
/* 273 */         } else if (n <= 4095) {
/* 274 */           rcp.add(new ItemStack(W_Block.getBlockById(n), 1, dmg));
/* 275 */         } else if (n <= 31999) {
/* 276 */           rcp.add(new ItemStack(W_Item.getItemById(n + 256), 1, dmg));
/*     */         } 
/*     */       } else {
/* 279 */         rcp.add(new ItemStack(W_Item.getItemByName(nm), 1, dmg));
/*     */       } 
/*     */     } 
/*     */     
/* 283 */     Object[] recipe = new Object[rcp.size()];
/*     */     
/* 285 */     for (int i = 0; i < recipe.length; i++) {
/* 286 */       recipe[i] = rcp.get(i);
/*     */     }
/*     */     
/* 289 */     ShapelessRecipes r = getShapelessRecipe(new ItemStack(item, createNum), recipe);
/*     */     
/* 291 */     for (int j = 0; j < r.recipeItems.size(); j++) {
/* 292 */       Ingredient ingredient = (Ingredient)r.recipeItems.get(j);
/* 293 */       Arrays.<ItemStack>stream(ingredient.getMatchingStacks()).anyMatch(stack -> {
/*     */             stack.getItem();
/*     */             
/*     */             return false;
/*     */           });
/*     */     } 
/* 299 */     MCH_Recipes.register(name, (IRecipe)r);
/* 300 */     return (IRecipe)r;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ShapelessRecipes getShapelessRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj) {
/* 305 */     NonNullList<Ingredient> list = NonNullList.create();
/*     */     
/* 307 */     for (Object object1 : par2ArrayOfObj) {
/* 308 */       if (object1 instanceof ItemStack) {
/* 309 */         list.add(Ingredient.fromStacks(new ItemStack[] { ((ItemStack)object1).copy() }));
/* 310 */       } else if (object1 instanceof Item) {
/* 311 */         list.add(Ingredient.fromStacks(new ItemStack[] { new ItemStack((Item)object1) }));
/*     */       } else {
/* 313 */         if (!(object1 instanceof Block)) {
/* 314 */           throw new RuntimeException("Invalid shapeless recipy!");
/*     */         }
/*     */         
/* 317 */         list.add(Ingredient.fromStacks(new ItemStack[] { new ItemStack((Block)object1) }));
/*     */       } 
/*     */     } 
/*     */     
/* 321 */     return new ShapelessRecipes("", par1ItemStack, list);
/*     */   }
/*     */   
/*     */   public static boolean isNumber(@Nullable String s) {
/* 325 */     if (s != null && !s.isEmpty()) {
/* 326 */       byte[] buf = s.getBytes();
/*     */       
/* 328 */       for (byte b : buf) {
/* 329 */         if (b < 48 || b > 57) {
/* 330 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 334 */       return true;
/*     */     } 
/* 336 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeListSize() {
/* 342 */     return commonItemRecipe.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public IRecipe getRecipe(int index) {
/* 347 */     return commonItemRecipe.get(index);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_ItemRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */