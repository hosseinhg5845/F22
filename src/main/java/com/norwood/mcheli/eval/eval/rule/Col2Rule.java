/*    */ package com.norwood.mcheli.eval.eval.rule;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*    */ import com.norwood.mcheli.eval.eval.exp.Col2Expression;
/*    */ import com.norwood.mcheli.eval.eval.lex.Lex;
/*    */ 
/*    */ public class Col2Rule extends AbstractRule {
/*    */   public Col2Rule(ShareRuleValue share) {
/*  9 */     super(share);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression parse(Lex lex) {
/* 14 */     AbstractExpression x = this.nextRule.parse(lex);
/*    */ 
/*    */     
/* 17 */     while (lex.getType() == 2147483634) {
/* 18 */       String ope = lex.getOperator();
/* 19 */       if (!isMyOperator(ope)) {
/* 20 */         return x;
/*    */       }
/*    */       
/* 23 */       int pos = lex.getPos();
/* 24 */       AbstractExpression y = this.nextRule.parse(lex.next());
/* 25 */       x = Col2Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
/*    */     } 
/* 27 */     return x;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\rule\Col2Rule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */