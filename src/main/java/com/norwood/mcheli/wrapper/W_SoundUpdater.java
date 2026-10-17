/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import com.norwood.mcheli.helper.MCH_Utils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.SoundHandler;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class W_SoundUpdater {
/*    */   protected final SoundHandler theSoundHnadler;
/*    */   
/*    */   public W_SoundUpdater(Minecraft minecraft, Entity entity) {
/* 13 */     this.theSoundHnadler = minecraft.func_147118_V();
/*    */   }
/*    */   protected W_Sound es;
/*    */   public void initEntitySound(String name) {
/* 17 */     this.es = new W_Sound(MCH_Utils.suffix(name), 1.0F, 1.0F);
/* 18 */     this.es.setRepeat(true);
/*    */   }
/*    */   
/*    */   public boolean isValidSound() {
/* 22 */     return (this.es != null);
/*    */   }
/*    */   
/*    */   public void playEntitySound(String name, Entity entity, float volume, float pitch, boolean par5) {
/* 26 */     if (isValidSound()) {
/* 27 */       this.es.setSoundParam(entity, volume, pitch);
/* 28 */       this.theSoundHnadler.func_147682_a((ISound)this.es);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void stopEntitySound(Entity entity) {
/* 33 */     if (isValidSound()) {
/* 34 */       this.theSoundHnadler.func_147683_b((ISound)this.es);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isEntitySoundPlaying(Entity entity) {
/* 39 */     return (isValidSound() && this.theSoundHnadler.func_147692_c((ISound)this.es));
/*    */   }
/*    */   
/*    */   public void setEntitySoundPitch(Entity entity, float pitch) {
/* 43 */     if (isValidSound()) {
/* 44 */       this.es.setPitch(pitch);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setEntitySoundVolume(Entity entity, float volume) {
/* 49 */     if (isValidSound()) {
/* 50 */       this.es.setVolume(volume);
/*    */     }
/*    */   }
/*    */   
/*    */   public void updateSoundLocation(Entity entity) {
/* 55 */     if (isValidSound()) {
/* 56 */       this.es.setPosition(entity);
/*    */     }
/*    */   }
/*    */   
/*    */   public void updateSoundLocation(double x, double y, double z) {
/* 61 */     if (isValidSound()) {
/* 62 */       this.es.setPosition(x, y, z);
/*    */     }
/*    */   }
/*    */   
/*    */   public void _updateSoundLocation(Entity entityListener, Entity entity) {
/* 67 */     if (isValidSound())
/* 68 */       this.es.setPosition(entity); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_SoundUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */