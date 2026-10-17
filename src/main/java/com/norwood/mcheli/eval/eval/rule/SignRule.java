/*    */ package com.norwood.mcheli.eval.eval.rule;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*    */ import com.norwood.mcheli.eval.eval.exp.Col1Expression;
/*    */ import com.norwood.mcheli.eval.eval.lex.Lex;
/*    */ 
/*    */ public class SignRule extends AbstractRule {
/*    */   public SignRule(ShareRuleValue share) {
/*  9 */     super(share);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression parse(Lex lex) {
/* 14 */     if (lex.getType() == 2147483634) {
/* 15 */       String ope = lex.getOperator();
/* 16 */       if (isMyOperator(ope)) {
/* 17 */         int pos = lex.getPos();
/* 18 */         return Col1Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, parse(lex.next()));
/*    */       } 
/*    */       
/* 21 */       return this.nextRule.parse(lex);
/*    */     } 
/* 23 */     return this.nextRule.parse(lex);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\rule\SignRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */