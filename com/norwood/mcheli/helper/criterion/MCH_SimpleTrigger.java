/*    */ package com.norwood.mcheli.helper.criterion;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.Map;
/*    */ import net.minecraft.advancements.ICriterionInstance;
/*    */ import net.minecraft.advancements.ICriterionTrigger;
/*    */ import net.minecraft.advancements.PlayerAdvancements;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_SimpleTrigger implements ICriterionTrigger<MCH_SimpleListeners.SimpleInstance> {
/* 15 */   private final Map<PlayerAdvancements, MCH_SimpleListeners> listeners = Maps.newHashMap();
/*    */   private final ResourceLocation id;
/*    */   
/*    */   public MCH_SimpleTrigger(ResourceLocation id) {
/* 19 */     this.id = id;
/*    */   }
/*    */   @NotNull
/*    */   public ResourceLocation func_192163_a() {
/* 23 */     return this.id;
/*    */   }
/*    */   
/*    */   public void func_192165_a(@NotNull PlayerAdvancements playerAdvancementsIn, @NotNull ICriterionTrigger.Listener<MCH_SimpleListeners.SimpleInstance> listener) {
/* 27 */     MCH_SimpleListeners listeners = this.listeners.get(playerAdvancementsIn);
/* 28 */     if (listeners == null) {
/* 29 */       listeners = new MCH_SimpleListeners(playerAdvancementsIn);
/* 30 */       this.listeners.put(playerAdvancementsIn, listeners);
/*    */     } 
/*    */     
/* 33 */     listeners.add(listener);
/*    */   }
/*    */   
/*    */   public void func_192164_b(@NotNull PlayerAdvancements playerAdvancementsIn, @NotNull ICriterionTrigger.Listener<MCH_SimpleListeners.SimpleInstance> listener) {
/* 37 */     MCH_SimpleListeners listeners = this.listeners.get(playerAdvancementsIn);
/* 38 */     if (listeners != null) {
/* 39 */       listeners.remove(listener);
/* 40 */       if (listeners.isEmpty()) {
/* 41 */         this.listeners.remove(playerAdvancementsIn);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void func_192167_a(@NotNull PlayerAdvancements playerAdvancementsIn) {
/* 47 */     this.listeners.remove(playerAdvancementsIn);
/*    */   }
/*    */   
/*    */   public MCH_SimpleListeners.SimpleInstance deserializeInstance(@NotNull JsonObject json, @NotNull JsonDeserializationContext context) {
/* 51 */     return new MCH_SimpleListeners.SimpleInstance(this.id);
/*    */   }
/*    */   
/*    */   public void trigger(EntityPlayerMP player) {
/* 55 */     MCH_SimpleListeners listener = this.listeners.get(player.func_192039_O());
/* 56 */     if (listener != null)
/* 57 */       listener.trigger(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\criterion\MCH_SimpleTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */