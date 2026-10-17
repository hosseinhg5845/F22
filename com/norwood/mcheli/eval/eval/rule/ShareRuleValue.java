/*     */ package com.norwood.mcheli.eval.eval.rule;
/*     */ 
/*     */ import com.norwood.mcheli.eval.eval.EvalException;
/*     */ import com.norwood.mcheli.eval.eval.Expression;
/*     */ import com.norwood.mcheli.eval.eval.Rule;
/*     */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*     */ import com.norwood.mcheli.eval.eval.exp.ShareExpValue;
/*     */ import com.norwood.mcheli.eval.eval.lex.Lex;
/*     */ import com.norwood.mcheli.eval.eval.lex.LexFactory;
/*     */ import com.norwood.mcheli.eval.eval.oper.Operator;
/*     */ import com.norwood.mcheli.eval.eval.ref.Refactor;
/*     */ import com.norwood.mcheli.eval.eval.srch.Search;
/*     */ import com.norwood.mcheli.eval.eval.var.Variable;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ public class ShareRuleValue extends Rule {
/*     */   public AbstractRule topRule;
/*     */   public AbstractRule funcArgRule;
/*     */   public LexFactory lexFactory;
/*     */   public AbstractExpression paren;
/*  22 */   protected final List<String>[] opeList = (List<String>[])new List[4];
/*     */ 
/*     */   
/*     */   public Expression parse(String str) {
/*  26 */     if (str == null)
/*  27 */       return null; 
/*  28 */     if (str.trim().isEmpty()) {
/*  29 */       return new EmptyExpression();
/*     */     }
/*  31 */     ShareExpValue exp = new ShareExpValue();
/*  32 */     AbstractExpression x = parse(str, exp);
/*  33 */     exp.setAbstractExpression(x);
/*  34 */     return (Expression)exp;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractExpression parse(String str, ShareExpValue exp) {
/*  39 */     if (str == null) {
/*  40 */       return null;
/*     */     }
/*  42 */     Lex lex = this.lexFactory.create(str, (List[])this.opeList, this, exp);
/*  43 */     lex.check();
/*  44 */     AbstractExpression x = this.topRule.parse(lex);
/*  45 */     if (lex.getType() != Integer.MAX_VALUE) {
/*  46 */       throw new EvalException(1005, lex);
/*     */     }
/*  48 */     return x;
/*     */   }
/*     */ 
/*     */   
/*     */   class EmptyExpression
/*     */     extends Expression
/*     */   {
/*     */     public long evalLong() {
/*  56 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/*     */     public double evalDouble() {
/*  61 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object eval() {
/*  66 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void optimizeLong(Variable var) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void optimizeDouble(Variable var) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void optimize(Variable var, Operator oper) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void search(Search srch) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void refactorName(Refactor ref) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void refactorFunc(Refactor ref, Rule rule) {}
/*     */ 
/*     */     
/*     */     public Expression dup() {
/*  95 */       Objects.requireNonNull(ShareRuleValue.this); return new EmptyExpression();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean same(Expression obj) {
/* 100 */       return obj instanceof EmptyExpression;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 105 */       return "";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\rule\ShareRuleValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */