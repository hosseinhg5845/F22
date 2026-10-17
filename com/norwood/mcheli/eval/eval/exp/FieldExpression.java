/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.EvalException;
/*    */ 
/*    */ public class FieldExpression extends Col2OpeExpression {
/*    */   public FieldExpression() {
/*  7 */     setOperator(".");
/*    */   }
/*    */   
/*    */   protected FieldExpression(FieldExpression from, ShareExpValue s) {
/* 11 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 16 */     return new FieldExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public long evalLong() {
/*    */     try {
/* 22 */       return this.share.var.evalLong(getVariable());
/* 23 */     } catch (EvalException var2) {
/* 24 */       throw var2;
/* 25 */     } catch (Exception var3) {
/* 26 */       throw new EvalException(2003, toString(), this.string, this.pos, var3);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/*    */     try {
/* 33 */       return this.share.var.evalDouble(getVariable());
/* 34 */     } catch (EvalException var2) {
/* 35 */       throw var2;
/* 36 */     } catch (Exception var3) {
/* 37 */       throw new EvalException(2003, toString(), this.string, this.pos, var3);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 43 */     return getVariable();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object getVariable() {
/* 48 */     Object obj = this.expl.getVariable();
/* 49 */     if (obj == null) {
/* 50 */       throw new EvalException(2104, this.expl.toString(), this.string, this.pos, null);
/*    */     }
/* 52 */     String word = this.expr.getWord();
/*    */     
/*    */     try {
/* 55 */       return this.share.var.getObject(obj, word);
/* 56 */     } catch (EvalException var4) {
/* 57 */       throw var4;
/* 58 */     } catch (Exception var5) {
/* 59 */       throw new EvalException(2301, toString(), this.string, this.pos, var5);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void let(Object val, int pos) {
/* 66 */     Object obj = this.expl.getVariable();
/* 67 */     if (obj == null) {
/* 68 */       throw new EvalException(2104, this.expl.toString(), this.string, pos, null);
/*    */     }
/* 70 */     String word = this.expr.getWord();
/*    */     
/*    */     try {
/* 73 */       this.share.var.setValue(obj, word, val);
/* 74 */     } catch (EvalException var6) {
/* 75 */       throw var6;
/* 76 */     } catch (Exception var7) {
/* 77 */       throw new EvalException(2302, toString(), this.string, pos, var7);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression replace() {
/* 84 */     this.expl = this.expl.replaceVar();
/* 85 */     return this.share.repl.replace2(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression replaceVar() {
/* 90 */     this.expl = this.expl.replaceVar();
/* 91 */     return this.share.repl.replaceVar2(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 96 */     return this.expl.toString() + '.' + this.expr
/*    */       
/* 98 */       .toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\FieldExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */