/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.EvalException;
/*    */ import com.norwood.mcheli.eval.eval.ref.Refactor;
/*    */ import com.norwood.mcheli.eval.eval.repl.ReplaceAdapter;
/*    */ 
/*    */ @Deprecated
/*    */ public class Replace4RefactorName extends ReplaceAdapter {
/*    */   protected final Refactor ref;
/*    */   
/*    */   Replace4RefactorName(Refactor ref) {
/* 12 */     this.ref = ref;
/*    */   }
/*    */   
/*    */   protected void var(VariableExpression exp) {
/* 16 */     String name = this.ref.getNewName(null, exp.getWord());
/* 17 */     if (name != null) {
/* 18 */       exp.setWord(name);
/*    */     }
/*    */   }
/*    */   
/*    */   protected void field(FieldExpression exp) {
/* 23 */     AbstractExpression exp1 = exp.expl;
/* 24 */     Object obj = exp1.getVariable();
/* 25 */     if (obj == null) {
/* 26 */       throw new EvalException(2104, toString(), exp1.string, exp1.pos, null);
/*    */     }
/* 28 */     AbstractExpression exp2 = exp.expr;
/* 29 */     String name = this.ref.getNewName(obj, exp2.getWord());
/* 30 */     if (name != null) {
/* 31 */       exp2.setWord(name);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func(FunctionExpression exp) {
/* 37 */     Object obj = null;
/* 38 */     if (exp.target != null) {
/* 39 */       obj = exp.target.getVariable();
/*    */     }
/*    */     
/* 42 */     String name = this.ref.getNewFuncName(obj, exp.name);
/* 43 */     if (name != null) {
/* 44 */       exp.name = name;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace0(WordExpression exp) {
/* 50 */     if (exp instanceof VariableExpression) {
/* 51 */       var((VariableExpression)exp);
/*    */     }
/*    */     
/* 54 */     return exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace2(Col2Expression exp) {
/* 59 */     if (exp instanceof FieldExpression) {
/* 60 */       field((FieldExpression)exp);
/*    */     }
/*    */     
/* 63 */     return exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceFunc(FunctionExpression exp) {
/* 68 */     func(exp);
/* 69 */     return exp;
/*    */   }
/*    */   
/*    */   public AbstractExpression replaceVar(AbstractExpression exp) {
/* 73 */     if (exp instanceof VariableExpression) {
/* 74 */       var((VariableExpression)exp);
/* 75 */     } else if (exp instanceof FieldExpression) {
/* 76 */       field((FieldExpression)exp);
/* 77 */     } else if (exp instanceof FunctionExpression) {
/* 78 */       func((FunctionExpression)exp);
/*    */     } 
/*    */     
/* 81 */     return exp;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\Replace4RefactorName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */