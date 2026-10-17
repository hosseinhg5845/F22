/*    */ package com.norwood.mcheli.multiplay;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ContainerScoreboard extends Container {
/*    */   public final EntityPlayer thePlayer;
/*    */   
/*    */   public MCH_ContainerScoreboard(EntityPlayer player) {
/* 11 */     this.thePlayer = player;
/*    */   }
/*    */   
/*    */   public boolean func_75145_c(@NotNull EntityPlayer player) {
/* 15 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_ContainerScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */