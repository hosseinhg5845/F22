/*    */ package com.norwood.mcheli.weapon;
/*    */ 
/*    */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*    */ import com.norwood.mcheli.wrapper.W_Item;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_WeaponInfoManager
/*    */ {
/*    */   public static void setRoundItems() {
/* 15 */     for (MCH_WeaponInfo w : ContentRegistries.weapon().values()) {
/* 16 */       for (MCH_WeaponInfo.RoundItem r : w.roundItems) {
/* 17 */         Item item = W_Item.getItemByName(r.itemName);
/* 18 */         r.itemStack = new ItemStack(item, 1, r.damage);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static MCH_WeaponInfo get(String name) {
/* 25 */     return (MCH_WeaponInfo)ContentRegistries.weapon().get(name);
/*    */   }
/*    */   
/*    */   public static boolean contains(String name) {
/* 29 */     return ContentRegistries.weapon().contains(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_WeaponInfoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */