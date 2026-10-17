/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.Rule;
/*    */ import com.norwood.mcheli.eval.eval.ref.Refactor;
/*    */ import com.norwood.mcheli.eval.eval.repl.ReplaceAdapter;
/*    */ import com.norwood.mcheli.eval.eval.rule.ShareRuleValue;
/*    */ 
/*    */ public class Replace4RefactorGetter extends ReplaceAdapter {
/*    */   protected final Refactor ref;
/*    */   protected final ShareRuleValue rule;
/*    */   
/*    */   Replace4RefactorGetter(Refactor ref, Rule rule) {
/* 13 */     this.ref = ref;
/* 14 */     this.rule = (ShareRuleValue)rule;
/*    */   }
/*    */   
/*    */   protected AbstractExpression var(VariableExpression exp) {
/* 18 */     String name = this.ref.getNewName(null, exp.getWord());
/* 19 */     return (name == null) ? exp : this.rule.parse(name, exp.share);
/*    */   }
/*    */   
/*    */   protected AbstractExpression field(FieldExpression exp) {
/* 23 */     AbstractExpression exp1 = exp.expl;
/* 24 */     Object obj = exp1.getVariable();
/* 25 */     if (obj != null) {
/* 26 */       AbstractExpression exp2 = exp.expr;
/* 27 */       String name = this.ref.getNewName(obj, exp2.getWord());
/* 28 */       if (name != null) {
/* 29 */         exp.expr = this.rule.parse(name, exp2.share);
/*    */       }
/*    */     } 
/* 32 */     return exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace0(WordExpression exp) {
/* 37 */     return (exp instanceof VariableExpression) ? var((VariableExpression)exp) : exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace2(Col2OpeExpression exp) {
/* 42 */     return (exp instanceof FieldExpression) ? field((FieldExpression)exp) : exp;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\Replace4RefactorGetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */