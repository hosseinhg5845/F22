/*    */ package com.norwood.mcheli.helper.criterion;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import net.minecraft.advancements.ICriterionTrigger;
/*    */ import net.minecraft.advancements.PlayerAdvancements;
/*    */ import net.minecraft.advancements.critereon.AbstractCriterionInstance;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class MCH_SimpleListeners
/*    */ {
/* 12 */   private final Set<ICriterionTrigger.Listener<SimpleInstance>> listeners = Sets.newHashSet();
/*    */   private final PlayerAdvancements playerAdvancements;
/*    */   
/*    */   public MCH_SimpleListeners(PlayerAdvancements playerAdvancements) {
/* 16 */     this.playerAdvancements = playerAdvancements;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 20 */     return this.listeners.isEmpty();
/*    */   }
/*    */   
/*    */   public void add(ICriterionTrigger.Listener<SimpleInstance> listener) {
/* 24 */     this.listeners.add(listener);
/*    */   }
/*    */   
/*    */   public void remove(ICriterionTrigger.Listener<SimpleInstance> listener) {
/* 28 */     this.listeners.remove(listener);
/*    */   }
/*    */   
/*    */   public void trigger() {
/* 32 */     for (ICriterionTrigger.Listener<SimpleInstance> listener : this.listeners)
/* 33 */       listener.func_192159_a(this.playerAdvancements); 
/*    */   }
/*    */   
/*    */   public static class SimpleInstance
/*    */     extends AbstractCriterionInstance {
/*    */     public SimpleInstance(ResourceLocation criterionIn) {
/* 39 */       super(criterionIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\criterion\MCH_SimpleListeners.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */