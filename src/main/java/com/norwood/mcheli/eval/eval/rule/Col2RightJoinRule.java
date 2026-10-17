/*    */ package com.norwood.mcheli.eval.eval.rule;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*    */ import com.norwood.mcheli.eval.eval.exp.Col2Expression;
/*    */ import com.norwood.mcheli.eval.eval.lex.Lex;
/*    */ 
/*    */ public class Col2RightJoinRule extends AbstractRule {
/*    */   public Col2RightJoinRule(ShareRuleValue share) {
/*  9 */     super(share);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression parse(Lex lex) {
/* 14 */     AbstractExpression x = this.nextRule.parse(lex);
/* 15 */     if (lex.getType() == 2147483634) {
/* 16 */       String ope = lex.getOperator();
/* 17 */       if (isMyOperator(ope)) {
/* 18 */         int pos = lex.getPos();
/* 19 */         AbstractExpression y = parse(lex.next());
/* 20 */         x = Col2Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
/*    */       } 
/*    */       
/* 23 */       return x;
/*    */     } 
/* 25 */     return x;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\rule\Col2RightJoinRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */