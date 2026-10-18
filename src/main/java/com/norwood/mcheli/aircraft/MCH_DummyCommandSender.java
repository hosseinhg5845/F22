/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.norwood.mcheli.helper.MCH_Utils;
/*    */ import net.minecraft.command.ICommandManager;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.world.World;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_DummyCommandSender implements ICommandSender {
/* 12 */   public static final MCH_DummyCommandSender instance = new MCH_DummyCommandSender();
/*    */   
/*    */   public static void execCommand(String s) {
/* 15 */     ICommandManager icommandmanager = MCH_Utils.getServer().getCommandManager();
/* 16 */     icommandmanager.executeCommand(instance, s);
/*    */   }
/*    */   @NotNull
/*    */   public String getName() {
/* 20 */     return "";
/*    */   }
/*    */   @NotNull
/*    */   public ITextComponent getDisplayName() {
/* 24 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(@NotNull ITextComponent component) {}
/*    */   
/*    */   public boolean canUseCommand(int permLevel, @NotNull String commandName) {
/* 31 */     return true;
/*    */   }
/*    */   @NotNull
/*    */   public World getEntityWorld() {
/* 35 */     return null;
/*    */   }
/*    */   
/*    */   public MinecraftServer getServer() {
/* 39 */     return MCH_Utils.getServer();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_DummyCommandSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */