/*    */ package com.norwood.mcheli.helper;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.RegistryEvent;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(modid = "mcheli")
/*    */ public class MCH_SoundEvents
/*    */ {
/* 22 */   private static final Set<ResourceLocation> registryWrapper = Sets.newLinkedHashSet();
/*    */   
/*    */   @SubscribeEvent
/*    */   static void onSoundEventRegisterEvent(RegistryEvent.Register<SoundEvent> event) {
/* 26 */     for (ResourceLocation soundLocation : registryWrapper) {
/* 27 */       event.getRegistry().register((new SoundEvent(soundLocation)).setRegistryName(soundLocation));
/*    */     }
/*    */   }
/*    */   
/*    */   public static void registerSoundEventName(String name) {
/* 32 */     registerSoundEventName(MCH_Utils.suffix(name));
/*    */   }
/*    */   
/*    */   public static void registerSoundEventName(ResourceLocation name) {
/* 36 */     registryWrapper.add(name);
/*    */   }
/*    */   
/*    */   public static void playSound(World w, double x, double y, double z, String name, float volume, float pitch) {
/* 40 */     SoundEvent sound = getSound(name);
/* 41 */     w.func_184148_a(null, x, y, z, sound, SoundCategory.MASTER, volume, pitch);
/*    */   }
/*    */   
/*    */   public static SoundEvent getSound(String name) {
/* 45 */     SoundEvent sound = getSound(new ResourceLocation(name));
/* 46 */     return Objects.<SoundEvent>requireNonNull(sound);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static SoundEvent getSound(ResourceLocation location) {
/* 51 */     SoundEvent sound = (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(location);
/* 52 */     if (sound == null) {
/* 53 */       MCH_Lib.Log("[WARNING] Sound event does not found. event name= " + location, new Object[0]);
/*    */     }
/*    */     
/* 56 */     return sound;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\MCH_SoundEvents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */