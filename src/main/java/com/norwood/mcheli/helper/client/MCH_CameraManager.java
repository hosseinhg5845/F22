/*    */ package com.norwood.mcheli.helper.client;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_ViewEntityDummy;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import com.norwood.mcheli.tool.rangefinder.MCH_ItemRangeFinder;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.client.event.EntityViewRenderEvent;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(modid = "mcheli", value = {Side.CLIENT})
/*    */ public class MCH_CameraManager
/*    */ {
/*    */   private static final float DEF_THIRD_CAMERA_DIST = 4.0F;
/* 23 */   private static final Minecraft mc = Minecraft.func_71410_x();
/* 24 */   private static float cameraRoll = 0.0F;
/* 25 */   private static float cameraDistance = 4.0F;
/* 26 */   private static float cameraZoom = 1.0F;
/* 27 */   private static MCH_EntityAircraft ridingAircraft = null;
/*    */   
/*    */   @SubscribeEvent
/*    */   static void onCameraSetupEvent(EntityViewRenderEvent.CameraSetup event) {
/* 31 */     float f = event.getEntity().func_70047_e();
/* 32 */     if (mc.field_71474_y.field_74320_O > 0) {
/* 33 */       if (mc.field_71474_y.field_74320_O == 2) {
/* 34 */         GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
/*    */       }
/*    */       
/* 37 */       GlStateManager.func_179109_b(0.0F, 0.0F, -(cameraDistance - 4.0F));
/* 38 */       if (mc.field_71474_y.field_74320_O == 2) {
/* 39 */         GlStateManager.func_179114_b(-180.0F, 0.0F, 1.0F, 0.0F);
/*    */       }
/*    */     } 
/*    */     
/* 43 */     MCH_EntityAircraft ridingEntity = ridingAircraft;
/* 44 */     if (ridingEntity != null && ridingEntity.canSwitchFreeLook() && ridingEntity.isPilot((Entity)mc.field_71439_g)) {
/* 45 */       GlStateManager.func_179109_b(0.0F, -f, 0.0F);
/* 46 */       GlStateManager.func_179114_b(cameraRoll, 0.0F, 0.0F, 1.0F);
/* 47 */       if (ridingEntity.isOverridePlayerPitch()) {
/* 48 */         GlStateManager.func_179114_b(ridingEntity.field_70125_A, 1.0F, 0.0F, 0.0F);
/* 49 */         GlStateManager.func_179114_b(ridingEntity.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 50 */         event.setPitch(event.getPitch() - ridingEntity.field_70125_A);
/* 51 */         event.setYaw(event.getYaw() - ridingEntity.field_70177_z);
/*    */       } 
/*    */       
/* 54 */       GlStateManager.func_179109_b(0.0F, f, 0.0F);
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   static void onFOVModifierEvent(EntityViewRenderEvent.FOVModifier event) {
/* 60 */     MCH_ViewEntityDummy viewer = MCH_ViewEntityDummy.getInstance((World)mc.field_71441_e);
/* 61 */     if (viewer == event.getEntity() || MCH_ItemRangeFinder.isUsingScope((EntityPlayer)mc.field_71439_g)) {
/* 62 */       event.setFOV(event.getFOV() * 1.0F / cameraZoom);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void setCameraRoll(float roll) {
/* 67 */     roll = MathHelper.func_76142_g(roll);
/* 68 */     cameraRoll = roll;
/*    */   }
/*    */   
/*    */   public static void setCameraZoom(float zoom) {
/* 72 */     cameraZoom = zoom;
/*    */   }
/*    */   
/*    */   public static float getThirdPeasonCameraDistance() {
/* 76 */     return cameraDistance;
/*    */   }
/*    */   
/*    */   public static void setThirdPeasonCameraDistance(float distance) {
/* 80 */     distance = MathHelper.func_76131_a(distance, 4.0F, 60.0F);
/* 81 */     cameraDistance = distance;
/*    */   }
/*    */   
/*    */   public static void setRidingAircraft(@Nullable MCH_EntityAircraft aircraft) {
/* 85 */     ridingAircraft = aircraft;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\MCH_CameraManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */