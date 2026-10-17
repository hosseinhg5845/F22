/*     */ package com.norwood.mcheli.eval.eval.exp;
/*     */ 
/*     */ import com.norwood.mcheli.eval.eval.EvalException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class FunctionExpression
/*     */   extends Col1Expression {
/*     */   protected AbstractExpression target;
/*     */   String name;
/*     */   
/*     */   public FunctionExpression() {
/*  13 */     setOperator("(");
/*  14 */     setEndOperator(")");
/*     */   }
/*     */   
/*     */   public FunctionExpression(AbstractExpression obj, String word) {
/*  18 */     this();
/*  19 */     this.target = obj;
/*  20 */     this.name = word;
/*     */   }
/*     */   
/*     */   protected FunctionExpression(FunctionExpression from, ShareExpValue s) {
/*  24 */     super(from, s);
/*  25 */     if (from.target != null) {
/*  26 */       this.target = from.target.dup(s);
/*     */     }
/*     */     
/*  29 */     this.name = from.name;
/*     */   }
/*     */   
/*     */   public static AbstractExpression create(AbstractExpression x, AbstractExpression args, int prio, ShareExpValue share) {
/*     */     AbstractExpression obj;
/*  34 */     if (x instanceof VariableExpression) {
/*  35 */       obj = null;
/*     */     } else {
/*  37 */       FieldExpression fieldExpression; if (x instanceof FieldExpression) { fieldExpression = (FieldExpression)x; }
/*  38 */       else { throw new EvalException(1101, x.toString(), x.string, x.pos, null); }
/*     */ 
/*     */       
/*  41 */       obj = fieldExpression.expl;
/*  42 */       x = fieldExpression.expr;
/*     */     } 
/*     */     
/*  45 */     String name = x.getWord();
/*  46 */     FunctionExpression f = new FunctionExpression(obj, name);
/*  47 */     f.setExpression(args);
/*  48 */     f.setPos(x.string, x.pos);
/*  49 */     f.setPriority(prio);
/*  50 */     f.share = share;
/*  51 */     return f;
/*     */   }
/*     */   
/*     */   private static boolean equals(AbstractExpression e1, AbstractExpression e2) {
/*  55 */     if (e1 == null) {
/*  56 */       return (e2 == null);
/*     */     }
/*  58 */     return e1.equals(e2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractExpression dup(ShareExpValue s) {
/*  64 */     return new FunctionExpression(this, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*  69 */     Object obj = null;
/*  70 */     if (this.target != null) {
/*  71 */       obj = this.target.getVariable();
/*     */     }
/*     */     
/*  74 */     List<Long> args = evalArgsLong();
/*     */     
/*     */     try {
/*  77 */       Long[] arr = new Long[args.size()];
/*  78 */       return this.share.func.evalLong(obj, this.name, args.<Long>toArray(arr));
/*  79 */     } catch (EvalException var4) {
/*  80 */       throw var4;
/*  81 */     } catch (Throwable var5) {
/*  82 */       throw new EvalException(2401, this.name, this.string, this.pos, var5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double evalDouble() {
/*  88 */     Object obj = null;
/*  89 */     if (this.target != null) {
/*  90 */       obj = this.target.getVariable();
/*     */     }
/*     */     
/*  93 */     List<Double> args = evalArgsDouble();
/*     */     
/*     */     try {
/*  96 */       Double[] arr = new Double[args.size()];
/*  97 */       return this.share.func.evalDouble(obj, this.name, args.<Double>toArray(arr));
/*  98 */     } catch (EvalException var4) {
/*  99 */       throw var4;
/* 100 */     } catch (Throwable var5) {
/* 101 */       throw new EvalException(2401, this.name, this.string, this.pos, var5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object evalObject() {
/* 107 */     Object obj = null;
/* 108 */     if (this.target != null) {
/* 109 */       obj = this.target.getVariable();
/*     */     }
/*     */     
/* 112 */     List<Object> args = evalArgsObject();
/*     */     
/*     */     try {
/* 115 */       Object[] arr = new Object[args.size()];
/* 116 */       return this.share.func.evalObject(obj, this.name, args.toArray(arr));
/* 117 */     } catch (EvalException var4) {
/* 118 */       throw var4;
/* 119 */     } catch (Throwable var5) {
/* 120 */       throw new EvalException(2401, this.name, this.string, this.pos, var5);
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<Long> evalArgsLong() {
/* 125 */     List<Long> args = new ArrayList<>();
/* 126 */     if (this.exp != null) {
/* 127 */       this.exp.evalArgsLong(args);
/*     */     }
/*     */     
/* 130 */     return args;
/*     */   }
/*     */   
/*     */   private List<Double> evalArgsDouble() {
/* 134 */     List<Double> args = new ArrayList<>();
/* 135 */     if (this.exp != null) {
/* 136 */       this.exp.evalArgsDouble(args);
/*     */     }
/*     */     
/* 139 */     return args;
/*     */   }
/*     */   
/*     */   private List<Object> evalArgsObject() {
/* 143 */     List<Object> args = new ArrayList();
/* 144 */     if (this.exp != null) {
/* 145 */       this.exp.evalArgsObject(args);
/*     */     }
/*     */     
/* 148 */     return args;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getVariable() {
/* 153 */     return evalObject();
/*     */   }
/*     */ 
/*     */   
/*     */   protected long operateLong(long val) {
/* 158 */     throw new RuntimeException("この関数が呼ばれてはいけない。サブクラスで実装要");
/*     */   }
/*     */ 
/*     */   
/*     */   protected double operateDouble(double val) {
/* 163 */     throw new RuntimeException("この関数が呼ばれてはいけない。サブクラスで実装要");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void search() {
/* 168 */     this.share.srch.search(this);
/* 169 */     if (!this.share.srch.end() && 
/* 170 */       !this.share.srch.searchFunc_begin(this) && 
/* 171 */       !this.share.srch.end()) {
/* 172 */       if (this.target != null) {
/* 173 */         this.target.search();
/* 174 */         if (this.share.srch.end()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 179 */       if (!this.share.srch.searchFunc_2(this) && 
/* 180 */         !this.share.srch.end()) {
/* 181 */         if (this.exp != null) {
/* 182 */           this.exp.search();
/* 183 */           if (this.share.srch.end()) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */         
/* 188 */         this.share.srch.searchFunc_end(this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replace() {
/* 198 */     if (this.target != null) {
/* 199 */       this.target = this.target.replace();
/*     */     }
/*     */     
/* 202 */     if (this.exp != null) {
/* 203 */       this.exp = this.exp.replace();
/*     */     }
/*     */     
/* 206 */     return this.share.repl.replaceFunc(this);
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     FunctionExpression e;
/* 211 */     if (obj instanceof FunctionExpression) { e = (FunctionExpression)obj; }
/* 212 */     else { return false; }
/*     */     
/* 214 */     return (this.name.equals(e.name) && equals(this.target, e.target) && equals(this.exp, e.exp));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 220 */     int t = (this.target != null) ? this.target.hashCode() : 0;
/* 221 */     int a = (this.exp != null) ? this.exp.hashCode() : 0;
/* 222 */     return this.name.hashCode() ^ t ^ a * 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 227 */     StringBuilder sb = new StringBuilder();
/* 228 */     if (this.target != null) {
/* 229 */       sb.append(this.target);
/* 230 */       sb.append('.');
/*     */     } 
/*     */     
/* 233 */     sb.append(this.name);
/* 234 */     sb.append('(');
/* 235 */     if (this.exp != null) {
/* 236 */       sb.append(this.exp);
/*     */     }
/*     */     
/* 239 */     sb.append(')');
/* 240 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\FunctionExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */