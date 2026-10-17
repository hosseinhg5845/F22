/*    */ package com.norwood.mcheli.helper;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_MOD;
/*    */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*    */ import java.io.File;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class MCH_Utils
/*    */ {
/*    */   public static ResourceLocation suffix(String name) {
/* 16 */     return new ResourceLocation("mcheli", name);
/*    */   }
/*    */   
/*    */   public static AddonResourceLocation addon(String domain, String path) {
/* 20 */     return new AddonResourceLocation(suffix(path), domain);
/*    */   }
/*    */   
/*    */   public static AddonResourceLocation buildinAddon(String path) {
/* 24 */     return new AddonResourceLocation(suffix(path), "@builtin");
/*    */   }
/*    */   
/*    */   public static File getSource() {
/* 28 */     return MCH_MOD.getSource();
/*    */   }
/*    */   
/*    */   public static boolean isClient() {
/* 32 */     return MCH_MOD.proxy.isRemote();
/*    */   }
/*    */   
/*    */   public static MinecraftServer getServer() {
/* 36 */     return FMLCommonHandler.instance().getMinecraftServerInstance();
/*    */   }
/*    */   
/*    */   public static Logger logger() {
/* 40 */     return MCH_Logger.get();
/*    */   }
/*    */   
/*    */   public static <T> boolean inArray(T[] objArray, @Nullable T target) {
/* 44 */     for (T obj : objArray) {
/* 45 */       if (Objects.equals(target, obj)) {
/* 46 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 50 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\MCH_Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */