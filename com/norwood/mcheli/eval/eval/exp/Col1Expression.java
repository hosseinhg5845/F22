/*     */ package com.norwood.mcheli.eval.eval.exp;
/*     */ 
/*     */ public abstract class Col1Expression
/*     */   extends AbstractExpression {
/*     */   protected AbstractExpression exp;
/*     */   
/*     */   protected Col1Expression() {}
/*     */   
/*     */   protected Col1Expression(Col1Expression from, ShareExpValue s) {
/*  10 */     super(from, s);
/*  11 */     if (from.exp != null) {
/*  12 */       this.exp = from.exp.dup(s);
/*     */     }
/*     */   }
/*     */   
/*     */   public static AbstractExpression create(AbstractExpression exp, String string, int pos, AbstractExpression x) {
/*  17 */     Col1Expression n = (Col1Expression)exp;
/*  18 */     n.setExpression(x);
/*  19 */     n.setPos(string, pos);
/*  20 */     return n;
/*     */   }
/*     */   
/*     */   public void setExpression(AbstractExpression x) {
/*  24 */     this.exp = x;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final int getCols() {
/*  29 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final int getFirstPos() {
/*  34 */     return this.exp.getFirstPos();
/*     */   }
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*  39 */     return operateLong(this.exp.evalLong());
/*     */   }
/*     */ 
/*     */   
/*     */   public double evalDouble() {
/*  44 */     return operateDouble(this.exp.evalDouble());
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract long operateLong(long paramLong);
/*     */   
/*     */   protected abstract double operateDouble(double paramDouble);
/*     */   
/*     */   protected void search() {
/*  53 */     this.share.srch.search(this);
/*  54 */     if (!this.share.srch.end() && 
/*  55 */       !this.share.srch.search1_begin(this) && 
/*  56 */       !this.share.srch.end()) {
/*  57 */       this.exp.search();
/*  58 */       if (!this.share.srch.end()) {
/*  59 */         this.share.srch.search1_end(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replace() {
/*  68 */     this.exp = this.exp.replace();
/*  69 */     return this.share.repl.replace1(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression replaceVar() {
/*  74 */     this.exp = this.exp.replaceVar();
/*  75 */     return this.share.repl.replaceVar1(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  80 */     if (obj instanceof Col1Expression) { Col1Expression e = (Col1Expression)obj;
/*  81 */       if (getClass() == e.getClass()) {
/*  82 */         if (this.exp == null) {
/*  83 */           return (e.exp == null);
/*     */         }
/*     */         
/*  86 */         if (e.exp == null) {
/*  87 */           return false;
/*     */         }
/*     */         
/*  90 */         return this.exp.equals(e.exp);
/*     */       }  }
/*     */ 
/*     */     
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  99 */     return getClass().hashCode() ^ this.exp.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public void dump(int n) {
/* 104 */     StringBuffer sb = new StringBuffer();
/*     */     
/* 106 */     for (int i = 0; i < n; i++) {
/* 107 */       sb.append(' ');
/*     */     }
/*     */     
/* 110 */     sb.append(getOperator());
/* 111 */     System.out.println(sb);
/* 112 */     if (this.exp != null) {
/* 113 */       this.exp.dump(n + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 119 */     if (this.exp == null) {
/* 120 */       return getOperator();
/*     */     }
/* 122 */     StringBuilder sb = new StringBuilder();
/* 123 */     if (this.exp.getPriority() > this.prio) {
/* 124 */       sb.append(getOperator());
/* 125 */       sb.append(this.exp);
/* 126 */     } else if (this.exp.getPriority() == this.prio) {
/* 127 */       sb.append(getOperator());
/* 128 */       sb.append(' ');
/* 129 */       sb.append(this.exp);
/*     */     } else {
/* 131 */       sb.append(getOperator());
/* 132 */       sb.append(this.share.paren.getOperator());
/* 133 */       sb.append(this.exp);
/* 134 */       sb.append(this.share.paren.getEndOperator());
/*     */     } 
/*     */     
/* 137 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\Col1Expression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */