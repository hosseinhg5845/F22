/*     */ package com.norwood.mcheli.eval.eval.exp;
/*     */ 
/*     */ import com.norwood.mcheli.eval.eval.EvalException;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ public abstract class AbstractExpression
/*     */ {
/*     */   public static final int TRUE = 1;
/*     */   public static final int FALSE = 0;
/*     */   public ShareExpValue share;
/*  12 */   protected String string = null;
/*  13 */   protected int pos = -1;
/*     */   
/*     */   protected int prio;
/*     */   private String ope1;
/*     */   private String ope2;
/*     */   
/*     */   protected AbstractExpression() {}
/*     */   
/*     */   protected AbstractExpression(AbstractExpression from, ShareExpValue s) {
/*  22 */     this.string = from.string;
/*  23 */     this.pos = from.pos;
/*  24 */     this.prio = from.prio;
/*  25 */     if (s != null) {
/*  26 */       this.share = s;
/*     */     } else {
/*  28 */       this.share = from.share;
/*     */     } 
/*     */     
/*  31 */     this.ope1 = from.ope1;
/*  32 */     this.ope2 = from.ope2;
/*     */   }
/*     */   
/*     */   private static boolean same(String str1, String str2) {
/*  36 */     return Objects.equals(str1, str2);
/*     */   }
/*     */   
/*     */   protected final boolean isTrue(boolean lng) {
/*  40 */     return lng ? ((evalLong() != 0L)) : ((evalDouble() != 0.0D));
/*     */   }
/*     */   
/*     */   public abstract AbstractExpression dup(ShareExpValue paramShareExpValue);
/*     */   
/*     */   public final String getOperator() {
/*  46 */     return this.ope1;
/*     */   }
/*     */   
/*     */   public final void setOperator(String ope) {
/*  50 */     this.ope1 = ope;
/*     */   }
/*     */   
/*     */   public final String getEndOperator() {
/*  54 */     return this.ope2;
/*     */   }
/*     */   
/*     */   public final void setEndOperator(String ope) {
/*  58 */     this.ope2 = ope;
/*     */   }
/*     */   
/*     */   protected String getWord() {
/*  62 */     return getOperator();
/*     */   }
/*     */   
/*     */   protected void setWord(String word) {
/*  66 */     throw new EvalException(2001, word, this.string, this.pos, null);
/*     */   }
/*     */   
/*     */   protected abstract int getCols();
/*     */   
/*     */   protected final void setPos(String string, int pos) {
/*  72 */     this.string = string;
/*  73 */     this.pos = pos;
/*     */   }
/*     */   
/*     */   protected abstract int getFirstPos();
/*     */   
/*     */   protected final int getPriority() {
/*  79 */     return this.prio;
/*     */   }
/*     */   
/*     */   public final void setPriority(int prio) {
/*  83 */     this.prio = prio;
/*     */   }
/*     */   
/*     */   protected void let(Object val, int pos) {
/*  87 */     throw new EvalException(2004, toString(), this.string, pos, null);
/*     */   }
/*     */   
/*     */   protected void let(long val, int pos) {
/*  91 */     let(Long.valueOf(val), pos);
/*     */   }
/*     */   
/*     */   protected void let(double val, int pos) {
/*  95 */     let(new Double(val), pos);
/*     */   }
/*     */   
/*     */   protected Object getVariable() {
/*  99 */     String word = toString();
/* 100 */     throw new EvalException(2002, word, this.string, this.pos, null);
/*     */   }
/*     */   
/*     */   protected void evalArgsLong(List<Long> args) {
/* 104 */     args.add(Long.valueOf(evalLong()));
/*     */   }
/*     */   
/*     */   protected void evalArgsDouble(List<Double> args) {
/* 108 */     args.add(Double.valueOf(evalDouble()));
/*     */   }
/*     */   
/*     */   protected void evalArgsObject(List<Object> args) {
/* 112 */     args.add(evalObject());
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract long evalLong();
/*     */ 
/*     */   
/*     */   public abstract double evalDouble();
/*     */   
/*     */   public abstract Object evalObject();
/*     */   
/*     */   protected abstract void search();
/*     */   
/*     */   protected abstract AbstractExpression replace();
/*     */   
/*     */   protected abstract AbstractExpression replaceVar();
/*     */   
/*     */   public abstract boolean equals(Object paramObject);
/*     */   
/*     */   public abstract int hashCode();
/*     */   
/*     */   public boolean same(AbstractExpression exp) {
/* 134 */     return (same(getOperator(), exp.getOperator()) && same(getEndOperator(), exp.getEndOperator()) && equals(exp));
/*     */   }
/*     */   
/*     */   public abstract void dump(int paramInt);
/*     */   
/*     */   public abstract String toString();
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\AbstractExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */