/*    */ package com.norwood.mcheli.helper.info;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.norwood.mcheli.helper.MCH_Utils;
/*    */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*    */ import java.util.Map;
/*    */ import java.util.function.BiFunction;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContentFactories
/*    */ {
/* 19 */   private static final Map<String, IContentFactory> TABLE = Maps.newHashMap();
/*    */   
/*    */   static {
/* 22 */     TABLE.put("helicopters", createFactory(ContentType.HELICOPTER, com.norwood.mcheli.helicopter.MCH_HeliInfo::new));
/* 23 */     TABLE.put("planes", createFactory(ContentType.PLANE, com.norwood.mcheli.plane.MCP_PlaneInfo::new));
/* 24 */     TABLE.put("tanks", createFactory(ContentType.TANK, com.norwood.mcheli.tank.MCH_TankInfo::new));
/* 25 */     TABLE.put("vehicles", createFactory(ContentType.VEHICLE, com.norwood.mcheli.vehicle.MCH_VehicleInfo::new));
/* 26 */     TABLE.put("throwable", createFactory(ContentType.THROWABLE, com.norwood.mcheli.throwable.MCH_ThrowableInfo::new));
/* 27 */     TABLE.put("weapons", createFactory(ContentType.WEAPON, com.norwood.mcheli.weapon.MCH_WeaponInfo::new));
/* 28 */     if (MCH_Utils.isClient()) {
/* 29 */       TABLE.put("hud", createFactory(ContentType.HUD, com.norwood.mcheli.hud.MCH_Hud::new));
/*    */     }
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static IContentFactory getFactory(@Nullable String dirName) {
/* 35 */     return (dirName == null) ? null : TABLE.get(dirName);
/*    */   }
/*    */   
/*    */   private static IContentFactory createFactory(final ContentType type, final BiFunction<AddonResourceLocation, String, IContentData> function) {
/* 39 */     return new IContentFactory()
/*    */       {
/*    */         public IContentData create(AddonResourceLocation location, String filepath) {
/* 42 */           return function.apply(location, filepath);
/*    */         }
/*    */ 
/*    */         
/*    */         public ContentType getType() {
/* 47 */           return type;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\info\ContentFactories.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */