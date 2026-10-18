/*    */ package com.norwood.mcheli.helper;
/*    */ import com.norwood.mcheli.helper.criterion.MCH_SimpleTrigger;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.ICriterionTrigger;
/*    */ 
/*    */ public class MCH_CriteriaTriggers {
/*  7 */   public static final MCH_SimpleTrigger PUT_AIRCRAFT = create("put_aircraft");
/*  8 */   public static final MCH_SimpleTrigger SUPPLY_AMMO = create("supply_ammo");
/*  9 */   public static final MCH_SimpleTrigger SUPPLY_FUEL = create("supply_fuel");
/* 10 */   public static final MCH_SimpleTrigger RELIEF_SUPPLIES = create("relief_supplies");
/* 11 */   public static final MCH_SimpleTrigger RIDING_VALKYRIES = create("riding_valkyries");
/* 12 */   public static final MCH_SimpleTrigger VILLAGER_HURT_BULLET = create("villager_hurt_bullet");
/*    */   
/*    */   public static void registerTriggers() {
/* 15 */     CriteriaTriggers.register((ICriterionTrigger)PUT_AIRCRAFT);
/* 16 */     CriteriaTriggers.register((ICriterionTrigger)SUPPLY_AMMO);
/* 17 */     CriteriaTriggers.register((ICriterionTrigger)SUPPLY_FUEL);
/* 18 */     CriteriaTriggers.register((ICriterionTrigger)RELIEF_SUPPLIES);
/* 19 */     CriteriaTriggers.register((ICriterionTrigger)RIDING_VALKYRIES);
/* 20 */     CriteriaTriggers.register((ICriterionTrigger)VILLAGER_HURT_BULLET);
/*    */   }
/*    */   
/*    */   private static MCH_SimpleTrigger create(String shortName) {
/* 24 */     return new MCH_SimpleTrigger(MCH_Utils.suffix(shortName));
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\MCH_CriteriaTriggers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */