/*   */ package com.norwood.mcheli.wrapper;
/*   */ 
/*   */ import net.minecraft.client.entity.EntityPlayerSP;
/*   */ import net.minecraft.entity.Entity;
/*   */ 
/*   */ public class W_EntityPlayerSP {
/*   */   public static void closeScreen(Entity p) {
/* 8 */     if (p instanceof EntityPlayerSP)
/* 9 */       ((EntityPlayerSP)p).closeScreen(); 
/*   */   }
/*   */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_EntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */