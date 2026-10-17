/*     */ package com.norwood.mcheli.eval.eval.exp;
/*     */ 
/*     */ public abstract class Col2Expression
/*     */   extends AbstractExpression {
/*     */   public AbstractExpression expl;
/*     */   public AbstractExpression expr;
/*     */   
/*     */   protected Col2Expression() {}
/*     */   
/*     */   protected Col2Expression(Col2Expression from, ShareExpValue s) {
/*  11 */     super(from, s);
/*  12 */     if (from.expl != null) {
/*  13 */       this.expl = from.expl.dup(s);
/*     */     }
/*     */     
/*  16 */     if (from.expr != null) {
/*  17 */       this.expr = from.expr.dup(s);
/*     */     }
/*     */   }
/*     */   
/*     */   public static AbstractExpression create(AbstractExpression exp, String string, int pos, AbstractExpression x, AbstractExpression y) {
/*  22 */     Col2Expression n = (Col2Expression)exp;
/*  23 */     n.setExpression(x, y);
/*  24 */     n.setPos(string, pos);
/*  25 */     return n;
/*     */   }
/*     */   
/*     */   public final void setExpression(AbstractExpression x, AbstractExpression y) {
/*  29 */     this.expl = x;
/*  30 */     this.expr = y;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final int getCols() {
/*  35 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final int getFirstPos() {
/*  40 */     return this.expl.getFirstPos();
/*     */   }
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*  45 */     return operateLong(this.expl.evalLong(), this.expr.evalLong());
/*     */   }
/*     */ 
/*     */   
/*     */   public double evalDouble() {
/*  50 */     return operateDouble(this.expl.evalDouble(), this.expr.evalDouble());
/*     */   }
/*     */ 
/*     */   
/*     */   public Object evalObject() {
/*  55 */     return operateObject(this.expl.evalObject(), this.expr.evalObject());
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract long operateLong(long paramLong1, long paramLong2);
/*     */   
/*     */   protected abstract double operateDouble(double paramDouble1, double paramDouble2);
/*     */   
/*     */   protected abstract Object operateObject(Object paramObject1, Object paramObject2);
/*     */   
/*     */   protected void search() {
/*  66 */     this.share.srch.search(this);
/*  67 */     if (!this.share.srch.end() && 
/*  68 */       !this.share.srch.search2_begin(this) && 
/*  69 */       !this.share.srch.end()) {
/*  70 */       this.expl.search();
/*  71 */       if (!this.share.srch.end() && 
/*  72 */         !this.share.srch.search2_2(this) && 
/*  73 */         !this.share.srch.end()) {
/*  74 */         this.expr.search();
/*  75 */         if (!this.share.srch.end()) {
/*  76 */           this.share.srch.search2_end(this);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replace() {
/*  88 */     this.expl = this.expl.replace();
/*  89 */     this.expr = this.expr.replace();
/*  90 */     return this.share.repl.replace2(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression replaceVar() {
/*  95 */     this.expl = this.expl.replaceVar();
/*  96 */     this.expr = this.expr.replaceVar();
/*  97 */     return this.share.repl.replaceVar2(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 102 */     if (obj instanceof Col2Expression) { Col2Expression e = (Col2Expression)obj;
/* 103 */       if (getClass() == e.getClass()) {
/* 104 */         return (this.expl.equals(e.expl) && this.expr.equals(e.expr));
/*     */       } }
/*     */ 
/*     */     
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return getClass().hashCode() ^ this.expl.hashCode() ^ this.expr.hashCode() * 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dump(int n) {
/* 118 */     StringBuffer sb = new StringBuffer();
/*     */     
/* 120 */     for (int i = 0; i < n; i++) {
/* 121 */       sb.append(' ');
/*     */     }
/*     */     
/* 124 */     sb.append(getOperator());
/* 125 */     System.out.println(sb);
/* 126 */     this.expl.dump(n + 1);
/* 127 */     this.expr.dump(n + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 132 */     StringBuilder sb = new StringBuilder();
/* 133 */     if (this.expl.getPriority() < this.prio) {
/* 134 */       sb.append(this.share.paren.getOperator());
/* 135 */       sb.append(this.expl);
/* 136 */       sb.append(this.share.paren.getEndOperator());
/*     */     } else {
/* 138 */       sb.append(this.expl);
/*     */     } 
/*     */     
/* 141 */     sb.append(toStringLeftSpace());
/* 142 */     sb.append(getOperator());
/* 143 */     sb.append(' ');
/* 144 */     if (this.expr.getPriority() < this.prio) {
/* 145 */       sb.append(this.share.paren.getOperator());
/* 146 */       sb.append(this.expr);
/* 147 */       sb.append(this.share.paren.getEndOperator());
/*     */     } else {
/* 149 */       sb.append(this.expr);
/*     */     } 
/*     */     
/* 152 */     return sb.toString();
/*     */   }
/*     */   
/*     */   protected String toStringLeftSpace() {
/* 156 */     return " ";
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\Col2Expression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */