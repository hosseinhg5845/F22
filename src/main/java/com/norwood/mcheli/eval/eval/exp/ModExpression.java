/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ public class ModExpression extends Col2Expression {
/*    */   public ModExpression() {
/*  5 */     setOperator("%");
/*    */   }
/*    */   
/*    */   protected ModExpression(ModExpression from, ShareExpValue s) {
/*  9 */     super(from, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 14 */     return new ModExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 19 */     return vl % vr;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 24 */     return vl % vr;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 29 */     return this.share.oper.mod(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\ModExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */