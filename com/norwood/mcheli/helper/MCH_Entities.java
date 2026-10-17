/*    */ package com.norwood.mcheli.helper;
/*    */ 
/*    */ import net.minecraftforge.fml.common.registry.EntityRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_Entities
/*    */ {
/*    */   public static <T extends net.minecraft.entity.Entity> void register(Class<T> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
/* 10 */     EntityRegistry.registerModEntity(MCH_Utils.suffix(entityName), entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\MCH_Entities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */