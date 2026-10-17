/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.EvalException;
/*    */ import com.norwood.mcheli.eval.eval.lex.Lex;
/*    */ import com.norwood.mcheli.eval.util.NumberUtil;
/*    */ 
/*    */ public class NumberExpression extends WordExpression {
/*    */   public NumberExpression(String str) {
/*  9 */     super(str);
/*    */   }
/*    */   
/*    */   protected NumberExpression(NumberExpression from, ShareExpValue s) {
/* 13 */     super(from, s);
/*    */   }
/*    */   
/*    */   public static AbstractExpression create(Lex lex, int prio) {
/* 17 */     AbstractExpression exp = new NumberExpression(lex.getWord());
/* 18 */     exp.setPos(lex.getString(), lex.getPos());
/* 19 */     exp.setPriority(prio);
/* 20 */     exp.share = lex.getShare();
/* 21 */     return exp;
/*    */   }
/*    */   
/*    */   public static NumberExpression create(AbstractExpression from, String word) {
/* 25 */     NumberExpression n = new NumberExpression(word);
/* 26 */     n.string = from.string;
/* 27 */     n.pos = from.pos;
/* 28 */     n.prio = from.prio;
/* 29 */     n.share = from.share;
/* 30 */     return n;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 35 */     return new NumberExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public long evalLong() {
/*    */     try {
/* 41 */       return NumberUtil.parseLong(this.word);
/* 42 */     } catch (Exception var6) {
/*    */       try {
/* 44 */         return Long.parseLong(this.word);
/* 45 */       } catch (Exception var5) {
/*    */         try {
/* 47 */           return (long)Double.parseDouble(this.word);
/* 48 */         } catch (Exception var4) {
/* 49 */           throw new EvalException(2003, this.word, this.string, this.pos, var4);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/*    */     try {
/* 58 */       return Double.parseDouble(this.word);
/* 59 */     } catch (Exception var4) {
/*    */       try {
/* 61 */         return NumberUtil.parseLong(this.word);
/* 62 */       } catch (Exception var3) {
/* 63 */         throw new EvalException(2003, this.word, this.string, this.pos, var4);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/*    */     try {
/* 71 */       return Long.valueOf(NumberUtil.parseLong(this.word));
/* 72 */     } catch (Exception var6) {
/*    */       try {
/* 74 */         return Long.valueOf(this.word);
/* 75 */       } catch (Exception var5) {
/*    */         try {
/* 77 */           return Double.valueOf(this.word);
/* 78 */         } catch (Exception var4) {
/* 79 */           throw new EvalException(2003, this.word, this.string, this.pos, var4);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\NumberExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */