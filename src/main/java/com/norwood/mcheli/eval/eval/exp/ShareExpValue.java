/*     */ package com.norwood.mcheli.eval.eval.exp;
/*     */ 
/*     */ import com.norwood.mcheli.eval.eval.Expression;
/*     */ import com.norwood.mcheli.eval.eval.Rule;
/*     */ import com.norwood.mcheli.eval.eval.func.Function;
/*     */ import com.norwood.mcheli.eval.eval.func.InvokeFunction;
/*     */ import com.norwood.mcheli.eval.eval.oper.JavaExOperator;
/*     */ import com.norwood.mcheli.eval.eval.oper.Operator;
/*     */ import com.norwood.mcheli.eval.eval.ref.Refactor;
/*     */ import com.norwood.mcheli.eval.eval.repl.Replace;
/*     */ import com.norwood.mcheli.eval.eval.srch.Search;
/*     */ import com.norwood.mcheli.eval.eval.var.MapVariable;
/*     */ import com.norwood.mcheli.eval.eval.var.Variable;
/*     */ 
/*     */ public class ShareExpValue
/*     */   extends Expression {
/*     */   public void setAbstractExpression(AbstractExpression ae) {
/*  18 */     this.ae = ae;
/*     */   }
/*     */   public AbstractExpression paren;
/*     */   public void initVar() {
/*  22 */     if (this.var == null) {
/*  23 */       this.var = (Variable)new MapVariable();
/*     */     }
/*     */   }
/*     */   
/*     */   public void initOper() {
/*  28 */     if (this.oper == null) {
/*  29 */       this.oper = (Operator)new JavaExOperator();
/*     */     }
/*     */   }
/*     */   
/*     */   public void initFunc() {
/*  34 */     if (this.func == null) {
/*  35 */       this.func = (Function)new InvokeFunction();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*  41 */     initVar();
/*  42 */     initFunc();
/*  43 */     return this.ae.evalLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public double evalDouble() {
/*  48 */     initVar();
/*  49 */     initFunc();
/*  50 */     return this.ae.evalDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object eval() {
/*  55 */     initVar();
/*  56 */     initOper();
/*  57 */     initFunc();
/*  58 */     return this.ae.evalObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public void optimizeLong(Variable var) {
/*  63 */     optimize(var, (Replace)new OptimizeLong());
/*     */   }
/*     */ 
/*     */   
/*     */   public void optimizeDouble(Variable var) {
/*  68 */     optimize(var, (Replace)new OptimizeDouble());
/*     */   }
/*     */ 
/*     */   
/*     */   public void optimize(Variable var, Operator oper) {
/*  73 */     Operator bak = this.oper;
/*  74 */     this.oper = oper;
/*     */     
/*     */     try {
/*  77 */       optimize(var, (Replace)new OptimizeObject());
/*     */     } finally {
/*  79 */       this.oper = bak;
/*     */     } 
/*     */   }
/*     */   protected void optimize(Variable var, Replace repl) {
/*     */     MapVariable mapVariable;
/*  84 */     Variable bak = this.var;
/*  85 */     if (var == null) {
/*  86 */       mapVariable = new MapVariable();
/*     */     }
/*     */     
/*  89 */     this.var = (Variable)mapVariable;
/*  90 */     this.repl = repl;
/*     */     
/*     */     try {
/*  93 */       this.ae = this.ae.replace();
/*     */     } finally {
/*  95 */       this.var = bak;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void search(Search srch) {
/* 101 */     if (srch == null) {
/* 102 */       throw new NullPointerException();
/*     */     }
/* 104 */     this.srch = srch;
/* 105 */     this.ae.search();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void refactorName(Refactor ref) {
/* 111 */     if (ref == null) {
/* 112 */       throw new NullPointerException();
/*     */     }
/* 114 */     this.srch = (Search)new Search4RefactorName(ref);
/* 115 */     this.ae.search();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void refactorFunc(Refactor ref, Rule rule) {
/* 121 */     if (ref == null) {
/* 122 */       throw new NullPointerException();
/*     */     }
/* 124 */     this.repl = (Replace)new Replace4RefactorGetter(ref, rule);
/* 125 */     this.ae.replace();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean same(Expression obj) {
/* 131 */     if (!(obj instanceof ShareExpValue)) {
/* 132 */       return false;
/*     */     }
/* 134 */     AbstractExpression p = ((ShareExpValue)obj).paren;
/* 135 */     return (this.paren.same(p) && super.same(obj));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression dup() {
/* 141 */     ShareExpValue n = new ShareExpValue();
/* 142 */     n.ae = this.ae.dup(n);
/* 143 */     n.paren = this.paren.dup(n);
/* 144 */     return n;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\ShareExpValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */