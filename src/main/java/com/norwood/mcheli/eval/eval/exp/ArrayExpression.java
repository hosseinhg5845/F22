/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.EvalException;
/*    */ 
/*    */ public class ArrayExpression extends Col2OpeExpression {
/*    */   public ArrayExpression() {
/*  7 */     setOperator("[");
/*  8 */     setEndOperator("]");
/*    */   }
/*    */   
/*    */   protected ArrayExpression(ArrayExpression from, ShareExpValue s) {
/* 12 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 17 */     return new ArrayExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public long evalLong() {
/*    */     try {
/* 23 */       return this.share.var.evalLong(getVariable());
/* 24 */     } catch (EvalException var2) {
/* 25 */       throw var2;
/* 26 */     } catch (Exception var3) {
/* 27 */       throw new EvalException(2201, toString(), this.string, this.pos, var3);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/*    */     try {
/* 34 */       return this.share.var.evalDouble(getVariable());
/* 35 */     } catch (EvalException var2) {
/* 36 */       throw var2;
/* 37 */     } catch (Exception var3) {
/* 38 */       throw new EvalException(2201, toString(), this.string, this.pos, var3);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 44 */     return getVariable();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object getVariable() {
/* 49 */     Object obj = this.expl.getVariable();
/* 50 */     if (obj == null) {
/* 51 */       throw new EvalException(2104, this.expl.toString(), this.string, this.pos, null);
/*    */     }
/* 53 */     int index = (int)this.expr.evalLong();
/*    */     
/*    */     try {
/* 56 */       return this.share.var.getObject(obj, index);
/* 57 */     } catch (EvalException var4) {
/* 58 */       throw var4;
/* 59 */     } catch (Exception var5) {
/* 60 */       throw new EvalException(2201, toString(), this.string, this.pos, var5);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void let(Object val, int pos) {
/* 67 */     Object obj = this.expl.getVariable();
/* 68 */     if (obj == null) {
/* 69 */       throw new EvalException(2104, this.expl.toString(), this.string, pos, null);
/*    */     }
/* 71 */     int index = (int)this.expr.evalLong();
/*    */     
/*    */     try {
/* 74 */       this.share.var.setValue(obj, index, val);
/* 75 */     } catch (EvalException var6) {
/* 76 */       throw var6;
/* 77 */     } catch (Exception var7) {
/* 78 */       throw new EvalException(2202, toString(), this.string, pos, var7);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression replaceVar() {
/* 85 */     this.expl = this.expl.replaceVar();
/* 86 */     this.expr = this.expr.replace();
/* 87 */     return this.share.repl.replaceVar2(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 92 */     return this.expl.toString() + '[' + this.expr
/*    */       
/* 94 */       .toString() + ']';
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\ArrayExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */