/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ 
/*    */ 
/*    */ public abstract class W_TickHandler
/*    */   implements ITickHandler
/*    */ {
/*    */   protected final Minecraft mc;
/*    */   
/*    */   public W_TickHandler(Minecraft m) {
/* 15 */     this.mc = m;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPlayerTickPre(EntityPlayer player) {}
/*    */ 
/*    */   
/*    */   public void onPlayerTickPost(EntityPlayer player) {}
/*    */ 
/*    */   
/*    */   public void onRenderTickPre(float partialTicks) {}
/*    */ 
/*    */   
/*    */   public void onRenderTickPost(float partialTicks) {}
/*    */ 
/*    */   
/*    */   public void onTickPre() {}
/*    */ 
/*    */   
/*    */   public void onTickPost() {}
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
/* 38 */     if (event.phase == TickEvent.Phase.START) {
/* 39 */       onPlayerTickPre(event.player);
/*    */     }
/*    */     
/* 42 */     if (event.phase == TickEvent.Phase.END) {
/* 43 */       onPlayerTickPost(event.player);
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onClientTickEvent(TickEvent.ClientTickEvent event) {
/* 49 */     if (event.phase == TickEvent.Phase.START) {
/* 50 */       onTickPre();
/*    */     }
/*    */     
/* 53 */     if (event.phase == TickEvent.Phase.END) {
/* 54 */       onTickPost();
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRenderTickEvent(TickEvent.RenderTickEvent event) {
/* 60 */     if (event.phase == TickEvent.Phase.START) {
/* 61 */       onRenderTickPre(event.renderTickTime);
/*    */     }
/*    */     
/* 64 */     if (event.phase == TickEvent.Phase.END)
/* 65 */       onRenderTickPost(event.renderTickTime); 
/*    */   }
/*    */   
/*    */   enum TickType
/*    */   {
/* 70 */     RENDER,
/* 71 */     CLIENT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_TickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */