/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.EvalException;
/*    */ import com.norwood.mcheli.eval.eval.lex.Lex;
/*    */ import com.norwood.mcheli.eval.util.CharUtil;
/*    */ import com.norwood.mcheli.eval.util.NumberUtil;
/*    */ 
/*    */ public class StringExpression extends WordExpression {
/*    */   public StringExpression(String str) {
/* 10 */     super(str);
/* 11 */     setOperator("\"");
/* 12 */     setEndOperator("\"");
/*    */   }
/*    */   
/*    */   protected StringExpression(StringExpression from, ShareExpValue s) {
/* 16 */     super(from, s);
/*    */   }
/*    */   
/*    */   public static AbstractExpression create(Lex lex, int prio) {
/* 20 */     String str = lex.getWord();
/* 21 */     str = CharUtil.escapeString(str, 1, str.length() - 2);
/* 22 */     AbstractExpression exp = new StringExpression(str);
/* 23 */     exp.setPos(lex.getString(), lex.getPos());
/* 24 */     exp.setPriority(prio);
/* 25 */     exp.share = lex.getShare();
/* 26 */     return exp;
/*    */   }
/*    */   
/*    */   public static StringExpression create(AbstractExpression from, String word) {
/* 30 */     StringExpression n = new StringExpression(word);
/* 31 */     n.string = from.string;
/* 32 */     n.pos = from.pos;
/* 33 */     n.prio = from.prio;
/* 34 */     n.share = from.share;
/* 35 */     return n;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 40 */     return new StringExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public long evalLong() {
/*    */     try {
/* 46 */       return NumberUtil.parseLong(this.word);
/* 47 */     } catch (Exception var6) {
/*    */       try {
/* 49 */         return Long.parseLong(this.word);
/* 50 */       } catch (Exception var5) {
/*    */         try {
/* 52 */           return (long)Double.parseDouble(this.word);
/* 53 */         } catch (Exception var4) {
/* 54 */           throw new EvalException(2003, this.word, this.string, this.pos, var4);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/*    */     try {
/* 63 */       return Double.parseDouble(this.word);
/* 64 */     } catch (Exception var4) {
/*    */       try {
/* 66 */         return NumberUtil.parseLong(this.word);
/* 67 */       } catch (Exception var3) {
/* 68 */         throw new EvalException(2003, this.word, this.string, this.pos, var4);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 75 */     return this.word;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 80 */     if (obj instanceof StringExpression) { StringExpression e = (StringExpression)obj;
/* 81 */       return this.word.equals(e.word); }
/*    */     
/* 83 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 89 */     return getOperator() + this.word + 
/*    */       
/* 91 */       getEndOperator();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\StringExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */