/*    */ package com.norwood.mcheli.eval.eval.rule;
/*    */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*    */ import com.norwood.mcheli.eval.eval.exp.ShareExpValue;
/*    */ import com.norwood.mcheli.eval.eval.lex.Lex;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public abstract class AbstractRule {
/* 11 */   private final Map<String, AbstractExpression> opes = new HashMap<>();
/*    */   public AbstractRule nextRule;
/*    */   public int prio;
/*    */   protected final ShareRuleValue share;
/*    */   
/*    */   public AbstractRule(ShareRuleValue share) {
/* 17 */     this.share = share;
/*    */   }
/*    */   
/*    */   public final void addExpression(AbstractExpression exp) {
/* 21 */     if (exp != null) {
/* 22 */       String ope = exp.getOperator();
/* 23 */       addOperator(ope, exp);
/* 24 */       addLexOperator(exp.getEndOperator());
/* 25 */       if (exp instanceof com.norwood.mcheli.eval.eval.exp.ParenExpression) {
/* 26 */         this.share.paren = exp;
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public final void addOperator(String ope, AbstractExpression exp) {
/* 32 */     this.opes.put(ope, exp);
/* 33 */     addLexOperator(ope);
/*    */   }
/*    */ 
/*    */   
/*    */   public final String[] getOperators() {
/* 38 */     List<String> list = new ArrayList<>(this.opes.keySet());
/*    */     
/* 40 */     return list.<String>toArray(new String[0]);
/*    */   }
/*    */   
/*    */   public final void addLexOperator(String ope) {
/* 44 */     if (ope != null) {
/* 45 */       int n = ope.length() - 1;
/* 46 */       if (this.share.opeList[n] == null) {
/* 47 */         this.share.opeList[n] = new ArrayList<>();
/*    */       }
/*    */       
/* 50 */       this.share.opeList[n].add(ope);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected final boolean isMyOperator(String ope) {
/* 55 */     return this.opes.containsKey(ope);
/*    */   }
/*    */   
/*    */   protected final AbstractExpression newExpression(String ope, ShareExpValue share) {
/*    */     try {
/* 60 */       AbstractExpression org = this.opes.get(ope);
/* 61 */       AbstractExpression n = org.dup(share);
/* 62 */       n.setPriority(this.prio);
/* 63 */       n.share = share;
/* 64 */       return n;
/* 65 */     } catch (RuntimeException var5) {
/* 66 */       throw var5;
/* 67 */     } catch (Exception var6) {
/* 68 */       throw new RuntimeException(var6);
/*    */     } 
/*    */   }
/*    */   
/*    */   public final void initPriority(int prio) {
/* 73 */     this.prio = prio;
/* 74 */     if (this.nextRule != null)
/* 75 */       this.nextRule.initPriority(prio + 1); 
/*    */   }
/*    */   
/*    */   protected abstract AbstractExpression parse(Lex paramLex);
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\rule\AbstractRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */