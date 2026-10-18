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
/*    */   public void sendMessage(@NotNull ITextComponent var1) {}
/*    */   
/*    */   public boolean canUseCommand(int var1, @NotNull String var2) {
/* 20 */     return false;
/*    */   }
/*    */   @NotNull
/*    */   public Entity getCommandSenderEntity() {
/* 24 */     return super.getCommandSenderEntity();
/*    */   }
/*    */   
/*    */   public boolean isSpectator() {
/* 28 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isCreative() {
/* 32 */     return false;
/*    */   }
/*    */   
/*    */   public void sendChatToPlayer(ChatMessageComponent chatmessagecomponent) {}
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_DummyEntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */