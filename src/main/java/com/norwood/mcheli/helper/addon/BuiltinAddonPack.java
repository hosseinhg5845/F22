/*    */ package com.norwood.mcheli.helper.addon;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.norwood.mcheli.helper.MCH_Utils;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ 
/*    */ public class BuiltinAddonPack extends AddonPack {
/* 10 */   private static BuiltinAddonPack instance = null;
/*    */   
/*    */   private BuiltinAddonPack() {
/* 13 */     super("@builtin", "MCHeli-Builtin", "1.0", null, "EMB4-MCHeli", (List<String>)ImmutableList.of("EMB4", "Murachiki27"), "Builtin addon", "1", ImmutableMap.of());
/*    */   }
/*    */   
/*    */   public static BuiltinAddonPack instance() {
/* 17 */     if (instance == null) {
/* 18 */       instance = new BuiltinAddonPack();
/*    */     }
/*    */     
/* 21 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public File getFile() {
/* 26 */     return MCH_Utils.getSource();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\addon\BuiltinAddonPack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */