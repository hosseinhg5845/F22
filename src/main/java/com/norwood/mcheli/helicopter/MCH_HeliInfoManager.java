/*    */ package com.norwood.mcheli.helicopter;
/*    */ import com.norwood.mcheli.MCH_BaseInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfoManager;
/*    */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*    */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class MCH_HeliInfoManager extends MCH_AircraftInfoManager<MCH_HeliInfo> {
/* 11 */   private static final MCH_HeliInfoManager instance = new MCH_HeliInfoManager();
/*    */   
/*    */   public static MCH_HeliInfoManager getInstance() {
/* 14 */     return instance;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static MCH_HeliInfo get(String name) {
/* 19 */     return (MCH_HeliInfo)ContentRegistries.heli().get(name);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static MCH_HeliInfo getFromItem(Item item) {
/* 24 */     return getInstance().getAcInfoFromItem(item);
/*    */   }
/*    */   
/*    */   public MCH_HeliInfo newInfo(AddonResourceLocation name, String filepath) {
/* 28 */     return new MCH_HeliInfo(name, filepath);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MCH_HeliInfo getAcInfoFromItem(Item item) {
/* 33 */     return (MCH_HeliInfo)ContentRegistries.heli().findFirst(info -> (info.item == item));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean contains(String name) {
/* 38 */     return ContentRegistries.heli().contains(name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int size() {
/* 43 */     return ContentRegistries.heli().size();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helicopter\MCH_HeliInfoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */