/*    */ package com.norwood.mcheli.wrapper;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.SoundManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class W_SoundManagerFunc {
/*    */   public static void DEF_playEntitySound(SoundManager sm, String name, Entity entity, float volume, float pitch, boolean par5) {
/*  9 */     sm.playSound((ISound)new W_Sound(new ResourceLocation(name), volume, pitch, entity.posX, entity.posY, entity.posZ));
/*    */   }
/*    */   
/*    */   public static void MOD_playEntitySound(SoundManager sm, String name, Entity entity, float volume, float pitch, boolean par5) {
/* 13 */     DEF_playEntitySound(sm, W_MOD.DOMAIN + ":" + name, entity, volume, pitch, par5);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_SoundManagerFunc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */