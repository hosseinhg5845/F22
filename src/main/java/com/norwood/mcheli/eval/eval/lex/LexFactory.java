/*    */ package com.norwood.mcheli.eval.eval.lex;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.exp.ShareExpValue;
/*    */ import com.norwood.mcheli.eval.eval.rule.ShareRuleValue;
/*    */ import java.util.List;
/*    */ 
/*    */ public class LexFactory
/*    */ {
/*    */   public Lex create(String str, List<String>[] opeList, ShareRuleValue share, ShareExpValue exp) {
/* 10 */     return new Lex(str, opeList, share.paren, exp);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\lex\LexFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */