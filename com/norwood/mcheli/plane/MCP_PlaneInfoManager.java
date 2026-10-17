/*    */ package com.norwood.mcheli.plane;
/*    */ import com.norwood.mcheli.MCH_BaseInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfoManager;
/*    */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*    */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class MCP_PlaneInfoManager extends MCH_AircraftInfoManager<MCP_PlaneInfo> {
/* 11 */   private static final MCP_PlaneInfoManager instance = new MCP_PlaneInfoManager();
/*    */   
/*    */   public static MCP_PlaneInfo get(String name) {
/* 14 */     return (MCP_PlaneInfo)ContentRegistries.plane().get(name);
/*    */   }
/*    */   
/*    */   public static MCP_PlaneInfoManager getInstance() {
/* 18 */     return instance;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static MCP_PlaneInfo getFromItem(@Nullable Item item) {
/* 23 */     return getInstance().getAcInfoFromItem(item);
/*    */   }
/*    */   
/*    */   public MCP_PlaneInfo newInfo(AddonResourceLocation name, String filepath) {
/* 27 */     return new MCP_PlaneInfo(name, filepath);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MCP_PlaneInfo getAcInfoFromItem(@Nullable Item item) {
/* 32 */     return (MCP_PlaneInfo)ContentRegistries.plane().findFirst(info -> (info.item == item));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean contains(String name) {
/* 37 */     return ContentRegistries.plane().contains(name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int size() {
/* 42 */     return ContentRegistries.plane().size();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\plane\MCP_PlaneInfoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */