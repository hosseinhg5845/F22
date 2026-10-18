/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import com.norwood.mcheli.helper.MCH_SoundEvents;
/*    */ import net.minecraft.client.audio.MovingSound;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ 
/*    */ public class W_Sound extends MovingSound {
/*    */   protected W_Sound(ResourceLocation r, float volume, float pitch, double x, double y, double z) {
/* 12 */     super(MCH_SoundEvents.getSound(r), SoundCategory.MASTER);
/* 13 */     setVolumeAndPitch(volume, pitch);
/* 14 */     setPosition(x, y, z);
/*    */   }
/*    */   
/*    */   protected W_Sound(ResourceLocation r, float volume, float pitch) {
/* 18 */     this(MCH_SoundEvents.getSound(r), volume, pitch);
/*    */   }
/*    */   
/*    */   protected W_Sound(SoundEvent soundEvent, float volume, float pitch) {
/* 22 */     super(soundEvent, SoundCategory.MASTER);
/* 23 */     setVolumeAndPitch(volume, pitch);
/* 24 */     Entity entity = W_McClient.getRenderEntity();
/* 25 */     if (entity != null) {
/* 26 */       setPosition(entity.posX, entity.posY, entity.posZ);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setRepeat(boolean b) {
/* 31 */     this.repeat = b;
/*    */   }
/*    */   
/*    */   public void setSoundParam(Entity e, float v, float p) {
/* 35 */     setPosition(e);
/* 36 */     setVolumeAndPitch(v, p);
/*    */   }
/*    */   
/*    */   public void setVolumeAndPitch(float v, float p) {
/* 40 */     setVolume(v);
/* 41 */     setPitch(p);
/*    */   }
/*    */   
/*    */   public void setVolume(float v) {
/* 45 */     this.volume = v;
/*    */   }
/*    */   
/*    */   public void setPitch(float p) {
/* 49 */     this.pitch = p;
/*    */   }
/*    */   
/*    */   public void setPosition(double x, double y, double z) {
/* 53 */     this.xPosF = (float)x;
/* 54 */     this.yPosF = (float)y;
/* 55 */     this.zPosF = (float)z;
/*    */   }
/*    */   
/*    */   public void setPosition(Entity e) {
/* 59 */     setPosition(e.posX, e.posY, e.posZ);
/*    */   }
/*    */   
/*    */   public void update() {}
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_Sound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */