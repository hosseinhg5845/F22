/*    */ package com.norwood.mcheli.eval.eval;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*    */ import com.norwood.mcheli.eval.eval.func.Function;
/*    */ import com.norwood.mcheli.eval.eval.oper.Operator;
/*    */ import com.norwood.mcheli.eval.eval.ref.Refactor;
/*    */ import com.norwood.mcheli.eval.eval.repl.Replace;
/*    */ import com.norwood.mcheli.eval.eval.srch.Search;
/*    */ import com.norwood.mcheli.eval.eval.var.Variable;
/*    */ 
/*    */ public abstract class Expression {
/*    */   public Variable var;
/*    */   public Function func;
/*    */   public Operator oper;
/*    */   public Search srch;
/*    */   public Replace repl;
/*    */   protected AbstractExpression ae;
/*    */   
/*    */   public static Expression parse(String str) {
/* 20 */     return ExpRuleFactory.getDefaultRule().parse(str);
/*    */   }
/*    */   
/*    */   public void setVariable(Variable var) {
/* 24 */     this.var = var;
/*    */   }
/*    */   
/*    */   public void setFunction(Function func) {
/* 28 */     this.func = func;
/*    */   }
/*    */   
/*    */   public void setOperator(Operator oper) {
/* 32 */     this.oper = oper;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract long evalLong();
/*    */   
/*    */   public abstract double evalDouble();
/*    */   
/*    */   public abstract Object eval();
/*    */   
/*    */   public abstract void optimizeLong(Variable paramVariable);
/*    */   
/*    */   public abstract void optimizeDouble(Variable paramVariable);
/*    */   
/*    */   public abstract void optimize(Variable paramVariable, Operator paramOperator);
/*    */   
/*    */   public abstract void search(Search paramSearch);
/*    */   
/*    */   public abstract void refactorName(Refactor paramRefactor);
/*    */   
/*    */   public abstract void refactorFunc(Refactor paramRefactor, Rule paramRule);
/*    */   
/*    */   public abstract Expression dup();
/*    */   
/*    */   public boolean equals(Object obj) {
/* 57 */     if (obj instanceof Expression) {
/* 58 */       AbstractExpression e = ((Expression)obj).ae;
/* 59 */       if (this.ae == null && e == null) {
/* 60 */         return true;
/*    */       }
/* 62 */       return (this.ae != null && this.ae.equals(e));
/*    */     } 
/*    */     
/* 65 */     return super.equals(obj);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 71 */     return (this.ae == null) ? 0 : this.ae.hashCode();
/*    */   }
/*    */   
/*    */   public boolean same(Expression obj) {
/* 75 */     AbstractExpression e = obj.ae;
/* 76 */     return (this.ae == null) ? ((e == null)) : this.ae.same(e);
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 80 */     return (this.ae == null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 85 */     return (this.ae == null) ? "" : this.ae.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\Expression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */