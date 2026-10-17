/*    */ package com.norwood.mcheli.throwable;
/*    */ 
/*    */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class MCH_ThrowableInfoManager {
/*    */   public static MCH_ThrowableInfo get(String name) {
/*  8 */     return (MCH_ThrowableInfo)ContentRegistries.throwable().get(name);
/*    */   }
/*    */   
/*    */   public static MCH_ThrowableInfo get(Item item) {
/* 12 */     return (MCH_ThrowableInfo)ContentRegistries.throwable().findFirst(info -> (info.item == item));
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\throwable\MCH_ThrowableInfoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */