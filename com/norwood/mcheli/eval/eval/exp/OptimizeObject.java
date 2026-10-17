/*    */ package com.norwood.mcheli.eval.eval.exp;
/*    */ 
/*    */ import com.norwood.mcheli.eval.eval.repl.ReplaceAdapter;
/*    */ 
/*    */ public class OptimizeObject extends ReplaceAdapter {
/*    */   protected boolean isConst(AbstractExpression x) {
/*  7 */     return (x instanceof NumberExpression || x instanceof StringExpression || x instanceof CharExpression);
/*    */   }
/*    */   
/*    */   protected boolean isTrue(AbstractExpression x) {
/* 11 */     return (x.evalLong() != 0L);
/*    */   }
/*    */   
/*    */   protected AbstractExpression toConst(AbstractExpression exp) {
/*    */     try {
/* 16 */       Object val = exp.evalObject();
/* 17 */       if (val instanceof String) {
/* 18 */         return StringExpression.create(exp, (String)val);
/*    */       }
/* 20 */       return (val instanceof Character) ? CharExpression.create(exp, val.toString()) : NumberExpression.create(exp, val.toString());
/*    */     }
/* 22 */     catch (Exception var3) {
/* 23 */       return exp;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace0(WordExpression exp) {
/* 29 */     return (exp instanceof VariableExpression) ? toConst(exp) : exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace1(Col1Expression exp) {
/* 34 */     if (exp instanceof ParenExpression)
/* 35 */       return exp.exp; 
/* 36 */     if (exp instanceof SignPlusExpression) {
/* 37 */       return exp.exp;
/*    */     }
/* 39 */     return isConst(exp.exp) ? toConst(exp) : exp;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression replace2(Col2Expression exp) {
/* 45 */     boolean const_l = isConst(exp.expl);
/* 46 */     boolean const_r = isConst(exp.expr);
/* 47 */     return (const_l && const_r) ? toConst(exp) : exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace2(Col2OpeExpression exp) {
/* 52 */     if (exp instanceof ArrayExpression)
/* 53 */       return isConst(exp.expr) ? toConst(exp) : exp; 
/* 54 */     if (exp instanceof FieldExpression) {
/* 55 */       return toConst(exp);
/*    */     }
/* 57 */     boolean const_l = isConst(exp.expl);
/* 58 */     if (exp instanceof AndExpression) {
/* 59 */       if (const_l) {
/* 60 */         return isTrue(exp.expl) ? exp.expr : exp.expl;
/*    */       }
/* 62 */       return exp;
/*    */     } 
/* 64 */     if (exp instanceof OrExpression) {
/* 65 */       if (const_l) {
/* 66 */         return isTrue(exp.expl) ? exp.expl : exp.expr;
/*    */       }
/* 68 */       return exp;
/*    */     } 
/* 70 */     if (exp instanceof CommaExpression) {
/* 71 */       return const_l ? exp.expr : exp;
/*    */     }
/* 73 */     return exp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression replace3(Col3Expression exp) {
/* 80 */     if (isConst(exp.exp1)) {
/* 81 */       return isTrue(exp.exp1) ? exp.exp2 : exp.exp3;
/*    */     }
/* 83 */     return exp;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\exp\OptimizeObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */