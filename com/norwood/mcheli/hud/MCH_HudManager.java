/*    */ package com.norwood.mcheli.hud;
/*    */ 
/*    */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_HudManager
/*    */ {
/*    */   @Nullable
/*    */   public static MCH_Hud get(String name) {
/* 13 */     return (MCH_Hud)ContentRegistries.hud().get(name);
/*    */   }
/*    */   
/*    */   public static boolean contains(String name) {
/* 17 */     return ContentRegistries.hud().contains(name);
/*    */   }
/*    */   
/*    */   public static int size() {
/* 21 */     return ContentRegistries.hud().size();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_HudManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */