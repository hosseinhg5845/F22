/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public abstract class Col1AfterExpression
/*    */   extends Col1Expression {
/*    */   protected Col1AfterExpression() {}
/*    */   
/*    */   protected Col1AfterExpression(Col1Expression from, ShareExpValue s) {
/*  8 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression replace() {
/* 13 */     this.exp = this.exp.replaceVar();
/* 14 */     return this.share.repl.replaceVar1(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression replaceVar() {
/* 19 */     return replace();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 24 */     if (this.exp == null) {
/* 25 */       return getOperator();
/*    */     }
/* 27 */     StringBuilder sb = new StringBuilder();
/* 28 */     if (this.exp.getPriority() > this.prio) {
/* 29 */       sb.append(this.exp);
/* 30 */       sb.append(getOperator());
/* 31 */     } else if (this.exp.getPriority() == this.prio) {
/* 32 */       sb.append(this.exp);
/* 33 */       sb.append(' ');
/* 34 */       sb.append(getOperator());
/*    */     } else {
/* 36 */       sb.append(this.share.paren.getOperator());
/* 37 */       sb.append(this.exp);
/* 38 */       sb.append(this.share.paren.getEndOperator());
/* 39 */       sb.append(getOperator());
/*    */     } 
/*    */     
/* 42 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\Col1AfterExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */