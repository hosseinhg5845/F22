/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.EvalException;
/*    */ import com.norwood.mcheli.eval.eval.lex.Lex;
/*    */ import com.norwood.mcheli.eval.util.CharUtil;
/*    */ 
/*    */ public class CharExpression extends WordExpression {
/*    */   public CharExpression(String str) {
/*  9 */     super(str);
/* 10 */     setOperator("'");
/* 11 */     setEndOperator("'");
/*    */   }
/*    */   
/*    */   protected CharExpression(CharExpression from, ShareExpValue s) {
/* 15 */     super(from, s);
/*    */   }
/*    */   
/*    */   public static AbstractExpression create(Lex lex, int prio) {
/* 19 */     String str = lex.getWord();
/* 20 */     str = CharUtil.escapeString(str, 1, str.length() - 2);
/* 21 */     AbstractExpression exp = new CharExpression(str);
/* 22 */     exp.setPos(lex.getString(), lex.getPos());
/* 23 */     exp.setPriority(prio);
/* 24 */     exp.share = lex.getShare();
/* 25 */     return exp;
/*    */   }
/*    */   
/*    */   public static CharExpression create(AbstractExpression from, String word) {
/* 29 */     CharExpression n = new CharExpression(word);
/* 30 */     n.string = from.string;
/* 31 */     n.pos = from.pos;
/* 32 */     n.prio = from.prio;
/* 33 */     n.share = from.share;
/* 34 */     return n;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 39 */     return new CharExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public long evalLong() {
/*    */     try {
/* 45 */       return this.word.charAt(0);
/* 46 */     } catch (Exception var2) {
/* 47 */       throw new EvalException(2003, this.word, this.string, this.pos, var2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/*    */     try {
/* 54 */       return this.word.charAt(0);
/* 55 */     } catch (Exception var2) {
/* 56 */       throw new EvalException(2003, this.word, this.string, this.pos, var2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 62 */     return Character.valueOf(this.word.charAt(0));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return getOperator() + this.word + 
/*    */       
/* 69 */       getEndOperator();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\CharExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */