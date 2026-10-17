/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.EvalException;
/*    */ import com.norwood.mcheli.eval.eval.ref.Refactor;
/*    */ import com.norwood.mcheli.eval.eval.srch.SearchAdapter;
/*    */ 
/*    */ public class Search4RefactorName extends SearchAdapter {
/*    */   protected final Refactor ref;
/*    */   
/*    */   Search4RefactorName(Refactor ref) {
/* 11 */     this.ref = ref;
/*    */   }
/*    */ 
/*    */   
/*    */   public void search0(WordExpression exp) {
/* 16 */     if (exp instanceof VariableExpression) {
/* 17 */       String name = this.ref.getNewName(null, exp.getWord());
/* 18 */       if (name != null) {
/* 19 */         exp.setWord(name);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean search2_2(Col2Expression exp) {
/* 26 */     if (exp instanceof FieldExpression) {
/* 27 */       AbstractExpression exp1 = exp.expl;
/* 28 */       Object obj = exp1.getVariable();
/* 29 */       if (obj == null) {
/* 30 */         throw new EvalException(2104, toString(), exp1.string, exp1.pos, null);
/*    */       }
/* 32 */       AbstractExpression exp2 = exp.expr;
/* 33 */       String name = this.ref.getNewName(obj, exp2.getWord());
/* 34 */       if (name != null) {
/* 35 */         exp2.setWord(name);
/*    */       }
/*    */       
/* 38 */       return true;
/*    */     } 
/*    */     
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean searchFunc_2(FunctionExpression exp) {
/* 47 */     Object obj = null;
/* 48 */     if (exp.target != null) {
/* 49 */       obj = exp.target.getVariable();
/*    */     }
/*    */     
/* 52 */     String name = this.ref.getNewFuncName(obj, exp.name);
/* 53 */     if (name != null) {
/* 54 */       exp.name = name;
/*    */     }
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\Search4RefactorName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */