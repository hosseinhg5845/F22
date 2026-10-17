/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.EvalException;
/*    */ import com.norwood.mcheli.eval.eval.lex.Lex;
/*    */ 
/*    */ public class VariableExpression extends WordExpression {
/*    */   public VariableExpression(String str) {
/*  8 */     super(str);
/*    */   }
/*    */   
/*    */   protected VariableExpression(VariableExpression from, ShareExpValue s) {
/* 12 */     super(from, s);
/*    */   }
/*    */   
/*    */   public static AbstractExpression create(Lex lex, int prio) {
/* 16 */     AbstractExpression exp = new VariableExpression(lex.getWord());
/* 17 */     exp.setPos(lex.getString(), lex.getPos());
/* 18 */     exp.setPriority(prio);
/* 19 */     exp.share = lex.getShare();
/* 20 */     return exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 25 */     return new VariableExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public long evalLong() {
/*    */     try {
/* 31 */       return this.share.var.evalLong(getVarValue());
/* 32 */     } catch (EvalException var2) {
/* 33 */       throw var2;
/* 34 */     } catch (Exception var3) {
/* 35 */       throw new EvalException(2003, this.word, this.string, this.pos, var3);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/*    */     try {
/* 42 */       return this.share.var.evalDouble(getVarValue());
/* 43 */     } catch (EvalException var2) {
/* 44 */       throw var2;
/* 45 */     } catch (Exception var3) {
/* 46 */       throw new EvalException(2003, this.word, this.string, this.pos, var3);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 52 */     return getVarValue();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void let(Object val, int pos) {
/* 57 */     String name = getWord();
/*    */     
/*    */     try {
/* 60 */       this.share.var.setValue(name, val);
/* 61 */     } catch (EvalException var5) {
/* 62 */       throw var5;
/* 63 */     } catch (Exception var6) {
/* 64 */       throw new EvalException(2102, name, this.string, pos, var6);
/*    */     } 
/*    */   }
/*    */   private Object getVarValue() {
/*    */     Object val;
/* 69 */     String word = getWord();
/*    */ 
/*    */     
/*    */     try {
/* 73 */       val = this.share.var.getObject(word);
/* 74 */     } catch (EvalException var4) {
/* 75 */       throw var4;
/* 76 */     } catch (Exception var5) {
/* 77 */       throw new EvalException(2101, word, this.string, this.pos, var5);
/*    */     } 
/*    */     
/* 80 */     if (val == null) {
/* 81 */       throw new EvalException(2103, word, this.string, this.pos, null);
/*    */     }
/* 83 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object getVariable() {
/*    */     try {
/* 90 */       return this.share.var.getObject(this.word);
/* 91 */     } catch (EvalException var2) {
/* 92 */       throw var2;
/* 93 */     } catch (Exception var3) {
/* 94 */       throw new EvalException(2002, this.word, this.string, this.pos, null);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\VariableExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */