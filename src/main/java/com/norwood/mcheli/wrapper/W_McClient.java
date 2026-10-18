/*    */ package com.norwood.mcheli.wrapper;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ 
/*    */ public class W_McClient {
/*    */   public static void playSoundClick(float volume, float pitch) {
/* 12 */     playSound(SoundEvents.UI_BUTTON_CLICK, volume, pitch);
/*    */   }
/*    */   
/*    */   public static void playSound(SoundEvent sound, float volume, float pitch) {
/* 16 */     Minecraft.getMinecraft().getSoundHandler().playSound((ISound)new W_Sound(sound, volume, pitch));
/*    */   }
/*    */   
/*    */   public static void DEF_playSoundFX(String name, float volume, float pitch) {
/* 20 */     Minecraft.getMinecraft().getSoundHandler().playSound((ISound)new W_Sound(new ResourceLocation(name), volume, pitch));
/*    */   }
/*    */   
/*    */   public static void MOD_playSoundFX(String name, float volume, float pitch) {
/* 24 */     DEF_playSoundFX(W_MOD.DOMAIN + ":" + name, volume, pitch);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void addSound(String name) {}
/*    */   
/*    */   public static void DEF_bindTexture(String tex) {
/* 31 */     (Minecraft.getMinecraft()).renderEngine.bindTexture(new ResourceLocation(tex));
/*    */   }
/*    */   
/*    */   public static void MOD_bindTexture(String tex) {
/* 35 */     (Minecraft.getMinecraft()).renderEngine.bindTexture(new ResourceLocation(W_MOD.DOMAIN, tex));
/*    */   }
/*    */   
/*    */   public static boolean isGamePaused() {
/* 39 */     Minecraft mc = Minecraft.getMinecraft();
/* 40 */     return mc.isGamePaused();
/*    */   }
/*    */   
/*    */   public static Entity getRenderEntity() {
/* 44 */     return Minecraft.getMinecraft().getRenderViewEntity();
/*    */   }
/*    */   
/*    */   public static void setRenderEntity(EntityLivingBase entity) {
/* 48 */     Minecraft.getMinecraft().setRenderViewEntity((Entity)entity);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_McClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */