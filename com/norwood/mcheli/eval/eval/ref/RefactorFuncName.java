/*    */ package com.norwood.mcheli.eval.eval.ref;
/*    */ 
/*    */ public class RefactorFuncName extends RefactorAdapter {
/*    */   protected final Class<?> targetClass;
/*    */   protected final String oldName;
/*    */   protected final String newName;
/*    */   
/*    */   public RefactorFuncName(Class<?> targetClass, String oldName, String newName) {
/*  9 */     this.targetClass = targetClass;
/* 10 */     this.oldName = oldName;
/* 11 */     this.newName = newName;
/* 12 */     if (oldName == null || newName == null) {
/* 13 */       throw new NullPointerException();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getNewFuncName(Object target, String name) {
/* 19 */     if (name.equals(this.oldName)) {
/* 20 */       if (this.targetClass == null) {
/* 21 */         if (target == null) {
/* 22 */           return this.newName;
/*    */         }
/* 24 */       } else if (target != null && this.targetClass.isAssignableFrom(target.getClass())) {
/* 25 */         return this.newName;
/*    */       } 
/*    */     }
/*    */     
/* 29 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\ref\RefactorFuncName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */