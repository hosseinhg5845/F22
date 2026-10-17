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
/* 15 */     ICommandManager icommandmanager = MCH_Utils.getServer().func_71187_D();
/* 16 */     icommandmanager.func_71556_a(instance, s);
/*    */   }
/*    */   @NotNull
/*    */   public String func_70005_c_() {
/* 20 */     return "";
/*    */   }
/*    */   @NotNull
/*    */   public ITextComponent func_145748_c_() {
/* 24 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145747_a(@NotNull ITextComponent component) {}
/*    */   
/*    */   public boolean func_70003_b(int permLevel, @NotNull String commandName) {
/* 31 */     return true;
/*    */   }
/*    */   @NotNull
/*    */   public World func_130014_f_() {
/* 35 */     return null;
/*    */   }
/*    */   
/*    */   public MinecraftServer func_184102_h() {
/* 39 */     return MCH_Utils.getServer();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_DummyCommandSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */