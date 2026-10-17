/*     */ package com.norwood.mcheli.eval.eval;
/*     */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*     */ import com.norwood.mcheli.eval.eval.rule.AbstractRule;
/*     */ import com.norwood.mcheli.eval.eval.rule.Col1AfterRule;
/*     */ import com.norwood.mcheli.eval.eval.rule.Col2RightJoinRule;
/*     */ import com.norwood.mcheli.eval.eval.rule.Col2Rule;
/*     */ import com.norwood.mcheli.eval.eval.rule.ShareRuleValue;
/*     */ 
/*     */ public class ExpRuleFactory {
/*     */   private static ExpRuleFactory me;
/*     */   protected final Rule rule;
/*     */   
/*     */   public ExpRuleFactory() {
/*  14 */     ShareRuleValue share = new ShareRuleValue();
/*  15 */     share.lexFactory = getLexFactory();
/*  16 */     init(share);
/*  17 */     this.rule = (Rule)share;
/*     */   }
/*     */   protected AbstractRule topRule; protected LexFactory defaultLexFactory;
/*     */   public static ExpRuleFactory getInstance() {
/*  21 */     if (me == null) {
/*  22 */       me = new ExpRuleFactory();
/*     */     }
/*     */     
/*  25 */     return me;
/*     */   }
/*     */   
/*     */   public static Rule getDefaultRule() {
/*  29 */     return getInstance().getRule();
/*     */   }
/*     */   
/*     */   public static Rule getJavaRule() {
/*  33 */     return JavaRuleFactory.getInstance().getRule();
/*     */   }
/*     */   
/*     */   public Rule getRule() {
/*  37 */     return this.rule;
/*     */   }
/*     */   
/*     */   protected void init(ShareRuleValue share) {
/*  41 */     AbstractRule rule = null;
/*  42 */     rule = add(rule, createCommaRule(share));
/*  43 */     rule = add(rule, createLetRule(share));
/*  44 */     rule = add(rule, createIfRule(share));
/*  45 */     rule = add(rule, createOrRule(share));
/*  46 */     rule = add(rule, createAndRule(share));
/*  47 */     rule = add(rule, createBitOrRule(share));
/*  48 */     rule = add(rule, createBitXorRule(share));
/*  49 */     rule = add(rule, createBitAndRule(share));
/*  50 */     rule = add(rule, createEqualRule(share));
/*  51 */     rule = add(rule, createGreaterRule(share));
/*  52 */     rule = add(rule, createShiftRule(share));
/*  53 */     rule = add(rule, createPlusRule(share));
/*  54 */     rule = add(rule, createMultRule(share));
/*  55 */     rule = add(rule, createSignRule(share));
/*  56 */     rule = add(rule, createPowerRule(share));
/*  57 */     rule = add(rule, createCol1AfterRule(share));
/*  58 */     rule = add(rule, createPrimaryRule(share));
/*  59 */     this.topRule.initPriority(1);
/*  60 */     share.topRule = this.topRule;
/*  61 */     initFuncArgRule(share);
/*     */   }
/*     */   
/*     */   protected void initFuncArgRule(ShareRuleValue share) {
/*  65 */     AbstractRule argRule = share.funcArgRule = createFuncArgRule(share);
/*  66 */     String[] a_opes = argRule.getOperators();
/*  67 */     String[] t_opes = this.topRule.getOperators();
/*  68 */     boolean match = false;
/*     */ 
/*     */     
/*  71 */     label21: for (String aOpe : a_opes) {
/*  72 */       for (String tOpe : t_opes) {
/*  73 */         if (aOpe.equals(tOpe)) {
/*  74 */           match = true;
/*     */           
/*     */           break label21;
/*     */         } 
/*     */       } 
/*     */     } 
/*  80 */     if (match) {
/*  81 */       argRule.nextRule = this.topRule.nextRule;
/*     */     } else {
/*  83 */       argRule.nextRule = this.topRule;
/*     */     } 
/*     */     
/*  86 */     argRule.prio = this.topRule.prio;
/*     */   }
/*     */   
/*     */   protected final AbstractRule add(AbstractRule rule, AbstractRule r) {
/*  90 */     if (r == null) {
/*  91 */       return rule;
/*     */     }
/*  93 */     if (this.topRule == null) {
/*  94 */       this.topRule = r;
/*     */     }
/*     */     
/*  97 */     if (rule != null) {
/*  98 */       rule.nextRule = r;
/*     */     }
/*     */     
/* 101 */     return r;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createCommaRule(ShareRuleValue share) {
/* 106 */     Col2Rule col2Rule = new Col2Rule(share);
/* 107 */     col2Rule.addExpression(createCommaExpression());
/* 108 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createCommaExpression() {
/* 112 */     return (AbstractExpression)new CommaExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createLetRule(ShareRuleValue share) {
/* 116 */     Col2RightJoinRule col2RightJoinRule = new Col2RightJoinRule(share);
/* 117 */     col2RightJoinRule.addExpression(createLetExpression());
/* 118 */     col2RightJoinRule.addExpression(createLetMultExpression());
/* 119 */     col2RightJoinRule.addExpression(createLetDivExpression());
/* 120 */     col2RightJoinRule.addExpression(createLetModExpression());
/* 121 */     col2RightJoinRule.addExpression(createLetPlusExpression());
/* 122 */     col2RightJoinRule.addExpression(createLetMinusExpression());
/* 123 */     col2RightJoinRule.addExpression(createLetShiftLeftExpression());
/* 124 */     col2RightJoinRule.addExpression(createLetShiftRightExpression());
/* 125 */     col2RightJoinRule.addExpression(createLetShiftRightLogicalExpression());
/* 126 */     col2RightJoinRule.addExpression(createLetAndExpression());
/* 127 */     col2RightJoinRule.addExpression(createLetOrExpression());
/* 128 */     col2RightJoinRule.addExpression(createLetXorExpression());
/* 129 */     col2RightJoinRule.addExpression(createLetPowerExpression());
/* 130 */     return (AbstractRule)col2RightJoinRule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetExpression() {
/* 134 */     return (AbstractExpression)new LetExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetMultExpression() {
/* 138 */     return (AbstractExpression)new LetMultExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetDivExpression() {
/* 142 */     return (AbstractExpression)new LetDivExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetModExpression() {
/* 146 */     return (AbstractExpression)new LetModExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetPlusExpression() {
/* 150 */     return (AbstractExpression)new LetPlusExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetMinusExpression() {
/* 154 */     return (AbstractExpression)new LetMinusExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetShiftLeftExpression() {
/* 158 */     return (AbstractExpression)new LetShiftLeftExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetShiftRightExpression() {
/* 162 */     return (AbstractExpression)new LetShiftRightExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetShiftRightLogicalExpression() {
/* 166 */     return (AbstractExpression)new LetShiftRightLogicalExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetAndExpression() {
/* 170 */     return (AbstractExpression)new LetAndExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetOrExpression() {
/* 174 */     return (AbstractExpression)new LetOrExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetXorExpression() {
/* 178 */     return (AbstractExpression)new LetXorExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLetPowerExpression() {
/* 182 */     return (AbstractExpression)new LetPowerExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createIfRule(ShareRuleValue share) {
/* 186 */     IfRule me = new IfRule(share);
/* 187 */     me.addExpression(me.cond = createIfExpression());
/* 188 */     return (AbstractRule)me;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createIfExpression() {
/* 192 */     return (AbstractExpression)new IfExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createOrRule(ShareRuleValue share) {
/* 196 */     Col2Rule col2Rule = new Col2Rule(share);
/* 197 */     col2Rule.addExpression(createOrExpression());
/* 198 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createOrExpression() {
/* 202 */     return (AbstractExpression)new OrExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createAndRule(ShareRuleValue share) {
/* 206 */     Col2Rule col2Rule = new Col2Rule(share);
/* 207 */     col2Rule.addExpression(createAndExpression());
/* 208 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createAndExpression() {
/* 212 */     return (AbstractExpression)new AndExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createBitOrRule(ShareRuleValue share) {
/* 216 */     Col2Rule col2Rule = new Col2Rule(share);
/* 217 */     col2Rule.addExpression(createBitOrExpression());
/* 218 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createBitOrExpression() {
/* 222 */     return (AbstractExpression)new BitOrExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createBitXorRule(ShareRuleValue share) {
/* 226 */     Col2Rule col2Rule = new Col2Rule(share);
/* 227 */     col2Rule.addExpression(createBitXorExpression());
/* 228 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createBitXorExpression() {
/* 232 */     return (AbstractExpression)new BitXorExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createBitAndRule(ShareRuleValue share) {
/* 236 */     Col2Rule col2Rule = new Col2Rule(share);
/* 237 */     col2Rule.addExpression(createBitAndExpression());
/* 238 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createBitAndExpression() {
/* 242 */     return (AbstractExpression)new BitAndExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createEqualRule(ShareRuleValue share) {
/* 246 */     Col2Rule col2Rule = new Col2Rule(share);
/* 247 */     col2Rule.addExpression(createEqualExpression());
/* 248 */     col2Rule.addExpression(createNotEqualExpression());
/* 249 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createEqualExpression() {
/* 253 */     return (AbstractExpression)new EqualExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createNotEqualExpression() {
/* 257 */     return (AbstractExpression)new NotEqualExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createGreaterRule(ShareRuleValue share) {
/* 261 */     Col2Rule col2Rule = new Col2Rule(share);
/* 262 */     col2Rule.addExpression(createLessThanExpression());
/* 263 */     col2Rule.addExpression(createLessEqualExpression());
/* 264 */     col2Rule.addExpression(createGreaterThanExpression());
/* 265 */     col2Rule.addExpression(createGreaterEqualExpression());
/* 266 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLessThanExpression() {
/* 270 */     return (AbstractExpression)new LessThanExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createLessEqualExpression() {
/* 274 */     return (AbstractExpression)new LessEqualExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createGreaterThanExpression() {
/* 278 */     return (AbstractExpression)new GreaterThanExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createGreaterEqualExpression() {
/* 282 */     return (AbstractExpression)new GreaterEqualExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createShiftRule(ShareRuleValue share) {
/* 286 */     Col2Rule col2Rule = new Col2Rule(share);
/* 287 */     col2Rule.addExpression(createShiftLeftExpression());
/* 288 */     col2Rule.addExpression(createShiftRightExpression());
/* 289 */     col2Rule.addExpression(createShiftRightLogicalExpression());
/* 290 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createShiftLeftExpression() {
/* 294 */     return (AbstractExpression)new ShiftLeftExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createShiftRightExpression() {
/* 298 */     return (AbstractExpression)new ShiftRightExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createShiftRightLogicalExpression() {
/* 302 */     return (AbstractExpression)new ShiftRightLogicalExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createPlusRule(ShareRuleValue share) {
/* 306 */     Col2Rule col2Rule = new Col2Rule(share);
/* 307 */     col2Rule.addExpression(createPlusExpression());
/* 308 */     col2Rule.addExpression(createMinusExpression());
/* 309 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createPlusExpression() {
/* 313 */     return (AbstractExpression)new PlusExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createMinusExpression() {
/* 317 */     return (AbstractExpression)new MinusExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createMultRule(ShareRuleValue share) {
/* 321 */     Col2Rule col2Rule = new Col2Rule(share);
/* 322 */     col2Rule.addExpression(createMultExpression());
/* 323 */     col2Rule.addExpression(createDivExpression());
/* 324 */     col2Rule.addExpression(createModExpression());
/* 325 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createMultExpression() {
/* 329 */     return (AbstractExpression)new MultExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createDivExpression() {
/* 333 */     return (AbstractExpression)new DivExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createModExpression() {
/* 337 */     return (AbstractExpression)new ModExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createSignRule(ShareRuleValue share) {
/* 341 */     SignRule signRule = new SignRule(share);
/* 342 */     signRule.addExpression(createSignPlusExpression());
/* 343 */     signRule.addExpression(createSignMinusExpression());
/* 344 */     signRule.addExpression(createBitNotExpression());
/* 345 */     signRule.addExpression(createNotExpression());
/* 346 */     signRule.addExpression(createIncBeforeExpression());
/* 347 */     signRule.addExpression(createDecBeforeExpression());
/* 348 */     return (AbstractRule)signRule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createSignPlusExpression() {
/* 352 */     return (AbstractExpression)new SignPlusExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createSignMinusExpression() {
/* 356 */     return (AbstractExpression)new SignMinusExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createBitNotExpression() {
/* 360 */     return (AbstractExpression)new BitNotExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createNotExpression() {
/* 364 */     return (AbstractExpression)new NotExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createIncBeforeExpression() {
/* 368 */     return (AbstractExpression)new IncBeforeExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createDecBeforeExpression() {
/* 372 */     return (AbstractExpression)new DecBeforeExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createPowerRule(ShareRuleValue share) {
/* 376 */     Col2Rule col2Rule = new Col2Rule(share);
/* 377 */     col2Rule.addExpression(createPowerExpression());
/* 378 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createPowerExpression() {
/* 382 */     return (AbstractExpression)new PowerExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createCol1AfterRule(ShareRuleValue share) {
/* 386 */     Col1AfterRule me = new Col1AfterRule(share);
/* 387 */     me.addExpression(me.func = createFunctionExpression());
/* 388 */     me.addExpression(me.array = createArrayExpression());
/* 389 */     me.addExpression(createIncAfterExpression());
/* 390 */     me.addExpression(createDecAfterExpression());
/* 391 */     me.addExpression(me.field = createFieldExpression());
/* 392 */     return (AbstractRule)me;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createFunctionExpression() {
/* 396 */     return (AbstractExpression)new FunctionExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createArrayExpression() {
/* 400 */     return (AbstractExpression)new ArrayExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createIncAfterExpression() {
/* 404 */     return (AbstractExpression)new IncAfterExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createDecAfterExpression() {
/* 408 */     return (AbstractExpression)new DecAfterExpression();
/*     */   }
/*     */   
/*     */   protected AbstractExpression createFieldExpression() {
/* 412 */     return (AbstractExpression)new FieldExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createPrimaryRule(ShareRuleValue share) {
/* 416 */     PrimaryRule primaryRule = new PrimaryRule(share);
/* 417 */     primaryRule.addExpression(createParenExpression());
/* 418 */     return (AbstractRule)primaryRule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createParenExpression() {
/* 422 */     return (AbstractExpression)new ParenExpression();
/*     */   }
/*     */   
/*     */   protected AbstractRule createFuncArgRule(ShareRuleValue share) {
/* 426 */     Col2Rule col2Rule = new Col2Rule(share);
/* 427 */     col2Rule.addExpression(createFuncArgExpression());
/* 428 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */   
/*     */   protected AbstractExpression createFuncArgExpression() {
/* 432 */     return (AbstractExpression)new FuncArgExpression();
/*     */   }
/*     */   
/*     */   protected LexFactory getLexFactory() {
/* 436 */     if (this.defaultLexFactory == null) {
/* 437 */       this.defaultLexFactory = new LexFactory();
/*     */     }
/*     */     
/* 440 */     return this.defaultLexFactory;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\ExpRuleFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */