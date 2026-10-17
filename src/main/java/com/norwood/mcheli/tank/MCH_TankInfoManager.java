/*    */ package com.norwood.mcheli.tank;
/*    */ import com.norwood.mcheli.MCH_BaseInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfoManager;
/*    */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*    */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class MCH_TankInfoManager extends MCH_AircraftInfoManager<MCH_TankInfo> {
/* 11 */   private static final MCH_TankInfoManager instance = new MCH_TankInfoManager();
/*    */   
/*    */   @Nullable
/*    */   public static MCH_TankInfo get(String name) {
/* 15 */     return (MCH_TankInfo)ContentRegistries.tank().get(name);
/*    */   }
/*    */   
/*    */   public static MCH_TankInfoManager getInstance() {
/* 19 */     return instance;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static MCH_TankInfo getFromItem(Item item) {
/* 24 */     return getInstance().getAcInfoFromItem(item);
/*    */   }
/*    */   
/*    */   public MCH_TankInfo newInfo(AddonResourceLocation name, String filepath) {
/* 28 */     return new MCH_TankInfo(name, filepath);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MCH_TankInfo getAcInfoFromItem(Item item) {
/* 33 */     return (MCH_TankInfo)ContentRegistries.tank().findFirst(info -> (info.item == item));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean contains(String name) {
/* 38 */     return ContentRegistries.tank().contains(name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int size() {
/* 43 */     return ContentRegistries.tank().size();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tank\MCH_TankInfoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */