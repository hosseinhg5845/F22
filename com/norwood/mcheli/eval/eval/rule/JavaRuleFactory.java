/*    */ package com.norwood.mcheli.eval.eval.rule;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.ExpRuleFactory;
/*    */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*    */ 
/*    */ public class JavaRuleFactory extends ExpRuleFactory {
/*    */   private static JavaRuleFactory me;
/*    */   
/*    */   public static ExpRuleFactory getInstance() {
/* 10 */     if (me == null) {
/* 11 */       me = new JavaRuleFactory();
/*    */     }
/*    */     
/* 14 */     return me;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractRule createCommaRule(ShareRuleValue share) {
/* 19 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractRule createPowerRule(ShareRuleValue share) {
/* 24 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression createLetPowerExpression() {
/* 29 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\rule\JavaRuleFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */