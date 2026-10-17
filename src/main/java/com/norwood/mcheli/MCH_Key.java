/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_KeyBinding;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ public class MCH_Key {
/*    */   public final int key;
/*    */   private boolean isPress;
/*    */   private boolean isBeforePress;
/*    */   
/*    */   public MCH_Key(int k) {
/* 14 */     this.key = k;
/* 15 */     this.isPress = false;
/* 16 */     this.isBeforePress = false;
/*    */   }
/*    */   
/*    */   public static boolean isKeyDown(int key) {
/* 20 */     if (key > 0) {
/* 21 */       return Keyboard.isKeyDown(key);
/*    */     }
/* 23 */     return (key < 0 && Mouse.isButtonDown(key + 100));
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isKeyDown(KeyBinding keyBind) {
/* 28 */     return isKeyDown(W_KeyBinding.getKeyCode(keyBind));
/*    */   }
/*    */   
/*    */   public boolean isKeyDown() {
/* 32 */     return (!this.isBeforePress && this.isPress);
/*    */   }
/*    */   
/*    */   public boolean isKeyPress() {
/* 36 */     return this.isPress;
/*    */   }
/*    */   
/*    */   public boolean isKeyUp() {
/* 40 */     return (this.isBeforePress && !this.isPress);
/*    */   }
/*    */   
/*    */   public void update() {
/* 44 */     if (this.key == 0)
/*    */       return; 
/* 46 */     this.isBeforePress = this.isPress;
/*    */     
/* 48 */     if (this.key >= 0) {
/* 49 */       this.isPress = Keyboard.isKeyDown(this.key);
/*    */     } else {
/* 51 */       int mouseButton = this.key + 100;
/* 52 */       this.isPress = Mouse.isButtonDown(mouseButton);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */