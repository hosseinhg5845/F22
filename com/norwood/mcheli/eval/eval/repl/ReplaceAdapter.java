/*    */ package com.norwood.mcheli.eval.eval.repl;
/*    */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*    */ import com.norwood.mcheli.eval.eval.exp.Col2Expression;
/*    */ import com.norwood.mcheli.eval.eval.exp.Col2OpeExpression;
/*    */ 
/*    */ public class ReplaceAdapter implements Replace {
/*    */   public AbstractExpression replace0(WordExpression exp) {
/*  8 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace1(Col1Expression exp) {
/* 13 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace2(Col2Expression exp) {
/* 18 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace2(Col2OpeExpression exp) {
/* 23 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace3(Col3Expression exp) {
/* 28 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceVar0(WordExpression exp) {
/* 33 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceVar1(Col1Expression exp) {
/* 38 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceVar2(Col2Expression exp) {
/* 43 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceVar2(Col2OpeExpression exp) {
/* 48 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceVar3(Col3Expression exp) {
/* 53 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceFunc(FunctionExpression exp) {
/* 58 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceLet(Col2Expression exp) {
/* 63 */     return (AbstractExpression)exp;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\repl\ReplaceAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */