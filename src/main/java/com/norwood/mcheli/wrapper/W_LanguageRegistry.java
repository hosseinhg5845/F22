/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import com.norwood.mcheli.helper.addon.GeneratedAddonPack;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ 
/*    */ public class W_LanguageRegistry
/*    */ {
/* 13 */   private static HashMap<String, ArrayList<String>> map = new HashMap<>();
/*    */   
/*    */   public static void addName(Object objectToName, String name) {
/* 16 */     addNameForObject(objectToName, "en_us", name);
/*    */   }
/*    */   
/*    */   public static void addNameForObject(Object o, String lang, String name) {
/* 20 */     addNameForObject(o, lang, name, "", "");
/*    */   }
/*    */   
/*    */   public static void addNameForObject(Object o, String lang, String name, String key, String desc) {
/* 24 */     if (o != null) {
/* 25 */       lang = lang.toLowerCase(Locale.ROOT);
/* 26 */       if (!map.containsKey(lang)) {
/* 27 */         map.put(lang, new ArrayList<>());
/*    */       }
/*    */       
/* 30 */       if (o instanceof Item) {
/* 31 */         ((ArrayList<String>)map.get(lang)).add(((Item)o).getUnlocalizedName() + ".name=" + name);
/*    */       }
/*    */       
/* 34 */       if (o instanceof Block) {
/* 35 */         ((ArrayList<String>)map.get(lang)).add(((Block)o).getUnlocalizedName() + ".name=" + name);
/* 36 */       } else if (o instanceof net.minecraft.advancements.Advancement) {
/* 37 */         ((ArrayList<String>)map.get(lang)).add("advancement." + key + "=" + name);
/* 38 */         ((ArrayList<String>)map.get(lang)).add("advancement." + key + ".desc=" + desc);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void clear() {
/* 44 */     map.clear();
/* 45 */     map = null;
/*    */   }
/*    */   
/*    */   public static void updateGeneratedLang() {
/* 49 */     GeneratedAddonPack.instance().checkMkdirsAssets("lang");
/*    */     
/* 51 */     for (String key : map.keySet()) {
/* 52 */       ArrayList<String> list = map.get(key);
/* 53 */       GeneratedAddonPack.instance().updateAssetFile("lang/" + key + ".lang", list);
/*    */     } 
/*    */     
/* 56 */     clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_LanguageRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */