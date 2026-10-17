/*    */ package com.norwood.mcheli.vehicle;
/*    */ import com.norwood.mcheli.MCH_BaseInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfoManager;
/*    */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*    */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class MCH_VehicleInfoManager extends MCH_AircraftInfoManager<MCH_VehicleInfo> {
/* 11 */   private static final MCH_VehicleInfoManager instance = new MCH_VehicleInfoManager();
/*    */   
/*    */   @Nullable
/*    */   public static MCH_VehicleInfo get(String name) {
/* 15 */     return (MCH_VehicleInfo)ContentRegistries.vehicle().get(name);
/*    */   }
/*    */   
/*    */   public static MCH_VehicleInfoManager getInstance() {
/* 19 */     return instance;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static MCH_VehicleInfo getFromItem(Item item) {
/* 24 */     return getInstance().getAcInfoFromItem(item);
/*    */   }
/*    */   
/*    */   public MCH_VehicleInfo newInfo(AddonResourceLocation name, String filepath) {
/* 28 */     return new MCH_VehicleInfo(name, filepath);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MCH_VehicleInfo getAcInfoFromItem(Item item) {
/* 33 */     return (MCH_VehicleInfo)ContentRegistries.vehicle().findFirst(info -> (info.item == item));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean contains(String name) {
/* 38 */     return ContentRegistries.vehicle().contains(name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int size() {
/* 43 */     return ContentRegistries.vehicle().size();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\vehicle\MCH_VehicleInfoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */