/*   */ package com.norwood.mcheli.wrapper;
/*   */ 
/*   */ import net.minecraftforge.common.MinecraftForge;
/*   */ import net.minecraftforge.fml.relauncher.Side;
/*   */ 
/*   */ public class W_TickRegistry {
/*   */   public static void registerTickHandler(W_TickHandler handler, Side side) {
/* 8 */     MinecraftForge.EVENT_BUS.register(handler);
/*   */   }
/*   */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_TickRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */