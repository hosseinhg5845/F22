/*     */ package com.norwood.mcheli.eval.eval.exp;
/*     */ 
/*     */ public abstract class Col3Expression
/*     */   extends AbstractExpression {
/*     */   protected AbstractExpression exp1;
/*     */   protected AbstractExpression exp2;
/*     */   protected AbstractExpression exp3;
/*     */   
/*     */   protected Col3Expression() {}
/*     */   
/*     */   protected Col3Expression(Col3Expression from, ShareExpValue s) {
/*  12 */     super(from, s);
/*  13 */     if (from.exp1 != null) {
/*  14 */       this.exp1 = from.exp1.dup(s);
/*     */     }
/*     */     
/*  17 */     if (from.exp2 != null) {
/*  18 */       this.exp2 = from.exp2.dup(s);
/*     */     }
/*     */     
/*  21 */     if (from.exp3 != null) {
/*  22 */       this.exp3 = from.exp3.dup(s);
/*     */     }
/*     */   }
/*     */   
/*     */   public static AbstractExpression create(AbstractExpression exp, String string, int pos, AbstractExpression x, AbstractExpression y, AbstractExpression z) {
/*  27 */     Col3Expression n = (Col3Expression)exp;
/*  28 */     n.setExpression(x, y, z);
/*  29 */     n.setPos(string, pos);
/*  30 */     return n;
/*     */   }
/*     */   
/*     */   public final void setExpression(AbstractExpression x, AbstractExpression y, AbstractExpression z) {
/*  34 */     this.exp1 = x;
/*  35 */     this.exp2 = y;
/*  36 */     this.exp3 = z;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final int getCols() {
/*  41 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getFirstPos() {
/*  46 */     return this.exp1.getFirstPos();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void search() {
/*  51 */     this.share.srch.search(this);
/*  52 */     if (!this.share.srch.end() && 
/*  53 */       !this.share.srch.search3_begin(this) && 
/*  54 */       !this.share.srch.end()) {
/*  55 */       this.exp1.search();
/*  56 */       if (!this.share.srch.end() && 
/*  57 */         !this.share.srch.search3_2(this) && 
/*  58 */         !this.share.srch.end()) {
/*  59 */         this.exp2.search();
/*  60 */         if (!this.share.srch.end() && 
/*  61 */           !this.share.srch.search3_3(this) && 
/*  62 */           !this.share.srch.end()) {
/*  63 */           this.exp3.search();
/*  64 */           if (!this.share.srch.end()) {
/*  65 */             this.share.srch.search3_end(this);
/*     */           }
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
/*     */ 
/*     */   
/*     */   protected AbstractExpression replace() {
/*  80 */     this.exp1 = this.exp1.replace();
/*  81 */     this.exp2 = this.exp2.replace();
/*  82 */     this.exp3 = this.exp3.replace();
/*  83 */     return this.share.repl.replace3(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression replaceVar() {
/*  88 */     this.exp1 = this.exp1.replace();
/*  89 */     this.exp2 = this.exp2.replaceVar();
/*  90 */     this.exp3 = this.exp3.replaceVar();
/*  91 */     return this.share.repl.replaceVar3(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  96 */     if (obj instanceof Col3Expression) { Col3Expression e = (Col3Expression)obj;
/*  97 */       if (getClass() == e.getClass()) {
/*  98 */         return (this.exp1.equals(e.exp1) && this.exp2.equals(e.exp2) && this.exp3.equals(e.exp3));
/*     */       } }
/*     */ 
/*     */     
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     return getClass().hashCode() ^ this.exp1.hashCode() ^ this.exp2.hashCode() * 2 ^ this.exp3.hashCode() * 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dump(int n) {
/* 112 */     StringBuffer sb = new StringBuffer();
/*     */     
/* 114 */     for (int i = 0; i < n; i++) {
/* 115 */       sb.append(' ');
/*     */     }
/*     */     
/* 118 */     sb.append(getOperator());
/* 119 */     System.out.println(sb);
/* 120 */     this.exp1.dump(n + 1);
/* 121 */     this.exp2.dump(n + 1);
/* 122 */     this.exp3.dump(n + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 127 */     StringBuilder sb = new StringBuilder();
/* 128 */     if (this.exp1.getPriority() > this.prio && this.exp1.getCols() < 2) {
/* 129 */       sb.append(this.exp1.toString());
/*     */     } else {
/* 131 */       sb.append(this.share.paren.getOperator());
/* 132 */       sb.append(this.exp1.toString());
/* 133 */       sb.append(this.share.paren.getEndOperator());
/*     */     } 
/*     */     
/* 136 */     sb.append(' ');
/* 137 */     sb.append(getOperator());
/* 138 */     sb.append(' ');
/* 139 */     if (this.exp2.getPriority() > this.prio && this.exp2.getCols() < 2) {
/* 140 */       sb.append(this.exp2.toString());
/*     */     } else {
/* 142 */       sb.append(this.share.paren.getOperator());
/* 143 */       sb.append(this.exp2.toString());
/* 144 */       sb.append(this.share.paren.getEndOperator());
/*     */     } 
/*     */     
/* 147 */     sb.append(' ');
/* 148 */     sb.append(getEndOperator());
/* 149 */     sb.append(' ');
/* 150 */     if (this.exp3.getPriority() > this.prio && this.exp3.getCols() < 2) {
/* 151 */       sb.append(this.exp3.toString());
/*     */     } else {
/* 153 */       sb.append(this.share.paren.getOperator());
/* 154 */       sb.append(this.exp3.toString());
/* 155 */       sb.append(this.share.paren.getEndOperator());
/*     */     } 
/*     */     
/* 158 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\Col3Expression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */