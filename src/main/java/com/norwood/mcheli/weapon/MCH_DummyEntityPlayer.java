/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.ChatMessageComponent;
/*    */ import com.norwood.mcheli.wrapper.W_EntityPlayer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.world.World;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_DummyEntityPlayer extends W_EntityPlayer {
/*    */   public MCH_DummyEntityPlayer(World worldIn, EntityPlayer player) {
/* 13 */     super(worldIn, player);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145747_a(@NotNull ITextComponent var1) {}
/*    */   
/*    */   public boolean func_70003_b(int var1, @NotNull String var2) {
/* 20 */     return false;
/*    */   }
/*    */   @NotNull
/*    */   public Entity func_174793_f() {
/* 24 */     return super.func_174793_f();
/*    */   }
/*    */   
/*    */   public boolean func_175149_v() {
/* 28 */     return false;
/*    */   }
/*    */   
/*    */   public boolean func_184812_l_() {
/* 32 */     return false;
/*    */   }
/*    */   
/*    */   public void sendChatToPlayer(ChatMessageComponent chatmessagecomponent) {}
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_DummyEntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */