/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import com.google.common.base.Joiner;
/*    */ import com.google.common.collect.ListMultimap;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Multimaps;
/*    */ import com.norwood.mcheli.helper.MCH_Utils;
/*    */ import com.norwood.mcheli.helper.addon.GeneratedAddonPack;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ public class MCH_SoundsJson
/*    */ {
/*    */   public static void updateGenerated() {
/* 17 */     File soundsDir = new File(MCH_MOD.getSource().getPath() + "/assets/mcheli/sounds");
/* 18 */     File[] soundFiles = soundsDir.listFiles(f -> {
/*    */           String s = f.getName().toLowerCase();
/* 20 */           return (f.isFile() && s.length() >= 5 && s.substring(s.length() - 4).compareTo(".ogg") == 0);
/*    */         });
/* 22 */     ListMultimap listMultimap = Multimaps.newListMultimap(Maps.newLinkedHashMap(), Lists::newLinkedList);
/* 23 */     List<String> lines = Lists.newLinkedList();
/* 24 */     int cnt = 0;
/* 25 */     if (soundFiles != null) {
/* 26 */       for (File f : soundFiles) {
/* 27 */         String name = f.getName().toLowerCase();
/* 28 */         int ei = name.lastIndexOf(".");
/* 29 */         name = name.substring(0, ei);
/* 30 */         String key = name;
/* 31 */         char c = name.charAt(name.length() - 1);
/* 32 */         if (c >= '0' && c <= '9') {
/* 33 */           key = name.substring(0, name.length() - 1);
/*    */         }
/*    */         
/* 36 */         listMultimap.put(key, name);
/*    */       } 
/*    */       
/* 39 */       lines.add("{");
/*    */       
/* 41 */       for (String key : listMultimap.keySet()) {
/* 42 */         cnt++;
/*    */         
/* 44 */         String sounds = Joiner.on(",").join((Iterable)listMultimap.get(key).stream().map(namex -> '"' + MCH_Utils.suffix((String)namex).toString() + '"').collect(Collectors.toList()));
/* 45 */         String line = "\"" + key + "\": {\"category\": \"master\",\"sounds\": [" + sounds + "]}";
/* 46 */         if (cnt < listMultimap.keySet().size()) {
/* 47 */           line = line + ",";
/*    */         }
/*    */         
/* 50 */         lines.add(line);
/*    */       } 
/*    */       
/* 53 */       lines.add("}");
/* 54 */       lines.add("");
/*    */     } 
/*    */     
/* 57 */     GeneratedAddonPack.instance().updateAssetFile("sounds.json", lines);
/* 58 */     MCH_Utils.logger().info("Update sounds.json, %d sounds.", Integer.valueOf(cnt));
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_SoundsJson.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */