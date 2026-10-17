/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public abstract class WordExpression extends AbstractExpression {
/*    */   protected String word;
/*    */   
/*    */   protected WordExpression(String str) {
/*  7 */     this.word = str;
/*    */   }
/*    */   
/*    */   protected WordExpression(WordExpression from, ShareExpValue s) {
/* 11 */     super(from, s);
/* 12 */     this.word = from.word;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getWord() {
/* 17 */     return this.word;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setWord(String word) {
/* 22 */     this.word = word;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getCols() {
/* 27 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getFirstPos() {
/* 32 */     return this.pos;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void search() {
/* 37 */     this.share.srch.search(this);
/* 38 */     if (!this.share.srch.end()) {
/* 39 */       this.share.srch.search0(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression replace() {
/* 45 */     return this.share.repl.replace0(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression replaceVar() {
/* 50 */     return this.share.repl.replaceVar0(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 55 */     if (obj instanceof WordExpression) { WordExpression e = (WordExpression)obj;
/* 56 */       if (getClass() == e.getClass()) {
/* 57 */         return this.word.equals(e.word);
/*    */       } }
/*    */ 
/*    */     
/* 61 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 66 */     return this.word.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public void dump(int n) {
/* 71 */     StringBuffer sb = new StringBuffer();
/*    */     
/* 73 */     for (int i = 0; i < n; i++) {
/* 74 */       sb.append(' ');
/*    */     }
/*    */     
/* 77 */     sb.append(this.word);
/* 78 */     System.out.println(sb);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 83 */     return this.word;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\WordExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */