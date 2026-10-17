/*    */ package com.norwood.mcheli.eval.eval.srch;
import com.norwood.mcheli.eval.eval.exp.WordExpression;
/*    */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*    */ import com.norwood.mcheli.eval.eval.exp.Col1Expression;
/*    */ import com.norwood.mcheli.eval.eval.exp.Col2Expression;
/*    */ import com.norwood.mcheli.eval.eval.exp.Col3Expression;
/*    */ import com.norwood.mcheli.eval.eval.exp.FunctionExpression;
/*    */ 
/*    */ public class SearchAdapter implements Search {
/*    */   public boolean end() {
/* 10 */     return this.end;
/*    */   }
/*    */   protected boolean end = false;
/*    */   protected void setEnd() {
/* 14 */     this.end = true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void search(AbstractExpression exp) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void search0(WordExpression exp) {}
/*    */ 
/*    */   
/*    */   public boolean search1_begin(Col1Expression exp) {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void search1_end(Col1Expression exp) {}
/*    */ 
/*    */   
/*    */   public boolean search2_begin(Col2Expression exp) {
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean search2_2(Col2Expression exp) {
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void search2_end(Col2Expression exp) {}
/*    */ 
/*    */   
/*    */   public boolean search3_begin(Col3Expression exp) {
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean search3_2(Col3Expression exp3) {
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean search3_3(Col3Expression exp) {
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void search3_end(Col3Expression exp) {}
/*    */ 
/*    */   
/*    */   public boolean searchFunc_begin(FunctionExpression exp) {
/* 69 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean searchFunc_2(FunctionExpression exp) {
/* 74 */     return false;
/*    */   }
/*    */   
/*    */   public void searchFunc_end(FunctionExpression exp) {}
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\srch\SearchAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */