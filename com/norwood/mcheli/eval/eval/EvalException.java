/*     */ package com.norwood.mcheli.eval.eval;
/*     */ 
/*     */ import com.norwood.mcheli.eval.eval.lex.Lex;
/*     */ 
/*     */ public class EvalException extends RuntimeException {
/*     */   public static final int PARSE_NOT_FOUND_END_OP = 1001;
/*     */   public static final int PARSE_INVALID_OP = 1002;
/*     */   public static final int PARSE_INVALID_CHAR = 1003;
/*     */   public static final int PARSE_END_OF_STR = 1004;
/*     */   public static final int PARSE_STILL_EXIST = 1005;
/*     */   public static final int PARSE_NOT_FUNC = 1101;
/*     */   public static final int EXP_FORBIDDEN_CALL = 2001;
/*     */   public static final int EXP_NOT_VARIABLE = 2002;
/*     */   public static final int EXP_NOT_NUMBER = 2003;
/*     */   public static final int EXP_NOT_LET = 2004;
/*     */   public static final int EXP_NOT_VAR_VALUE = 2101;
/*     */   public static final int EXP_NOT_LET_VAR = 2102;
/*     */   public static final int EXP_NOT_DEF_VAR = 2103;
/*     */   public static final int EXP_NOT_DEF_OBJ = 2104;
/*     */   public static final int EXP_NOT_ARR_VALUE = 2201;
/*     */   public static final int EXP_NOT_LET_ARR = 2202;
/*     */   public static final int EXP_NOT_FLD_VALUE = 2301;
/*     */   public static final int EXP_NOT_LET_FIELD = 2302;
/*     */   public static final int EXP_FUNC_CALL_ERROR = 2401;
/*     */   private static final long serialVersionUID = 4174576689426433715L;
/*     */   protected final int msg_code;
/*     */   protected String[] msg_opt;
/*     */   protected String string;
/*  29 */   protected int pos = -1;
/*     */   protected String word;
/*     */   
/*     */   public EvalException(int msg, Lex lex) {
/*  33 */     this(msg, null, lex);
/*     */   }
/*     */   
/*     */   public EvalException(int msg, String[] opt, Lex lex) {
/*  37 */     this.msg_code = msg;
/*  38 */     this.msg_opt = opt;
/*  39 */     if (lex != null) {
/*  40 */       this.string = lex.getString();
/*  41 */       this.pos = lex.getPos();
/*  42 */       this.word = lex.getWord();
/*     */     } 
/*     */   }
/*     */   
/*     */   public EvalException(int msg, String word, String string, int pos, Throwable e) {
/*  47 */     while (e != null && e.getClass() == RuntimeException.class && e.getCause() != null) {
/*  48 */       e = e.getCause();
/*     */     }
/*     */     
/*  51 */     if (e != null) {
/*  52 */       initCause(e);
/*     */     }
/*     */     
/*  55 */     this.msg_code = msg;
/*  56 */     this.string = string;
/*  57 */     this.pos = pos;
/*  58 */     this.word = word;
/*     */   }
/*     */   
/*     */   public static String getErrCodeMessage(int code) {
/*  62 */     switch (code) { case 1001: case 1002: case 1003: case 1004: case 1005: case 1101: case 2001: case 2002: case 2003: case 2004: case 2101: case 2102: case 2103: case 2104: case 2201: case 2202: case 2301: case 2302: case 2401:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  82 */       "エラーが発生しました。";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getErrorCode() {
/*  87 */     return this.msg_code;
/*     */   }
/*     */   
/*     */   public String[] getOption() {
/*  91 */     return this.msg_opt;
/*     */   }
/*     */   
/*     */   public String getWord() {
/*  95 */     return this.word;
/*     */   }
/*     */   
/*     */   public String getString() {
/*  99 */     return this.string;
/*     */   }
/*     */   
/*     */   public int getPos() {
/* 103 */     return this.pos;
/*     */   }
/*     */   
/*     */   public String getDefaultFormat(String msgFmt) {
/* 107 */     StringBuilder fmt = new StringBuilder(128);
/* 108 */     fmt.append(msgFmt);
/* 109 */     boolean bWord = false;
/* 110 */     if (this.word != null && !this.word.isEmpty()) {
/* 111 */       bWord = !this.word.equals(this.string);
/*     */     }
/*     */     
/* 114 */     if (bWord) {
/* 115 */       fmt.append(" word=「%w」");
/*     */     }
/*     */     
/* 118 */     if (this.pos >= 0) {
/* 119 */       fmt.append(" pos=%p");
/*     */     }
/*     */     
/* 122 */     if (this.string != null) {
/* 123 */       fmt.append(" string=「%s」");
/*     */     }
/*     */     
/* 126 */     if (getCause() != null) {
/* 127 */       fmt.append(" cause by %e");
/*     */     }
/*     */     
/* 130 */     return fmt.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 135 */     String msg = getErrCodeMessage(this.msg_code);
/* 136 */     String fmt = getDefaultFormat(msg);
/* 137 */     return toString(fmt);
/*     */   }
/*     */   
/*     */   public String toString(String fmt) {
/* 141 */     StringBuilder sb = new StringBuilder(256);
/* 142 */     int len = fmt.length();
/*     */     
/* 144 */     for (int i = 0; i < len; i++) {
/* 145 */       char c = fmt.charAt(i);
/* 146 */       if (c != '%') {
/* 147 */         sb.append(c);
/*     */       } else {
/* 149 */         int n; if (i + 1 >= len) {
/* 150 */           sb.append(c);
/*     */           
/*     */           break;
/*     */         } 
/* 154 */         c = fmt.charAt(++i);
/* 155 */         switch (c) {
/*     */           case '%':
/* 157 */             sb.append('%');
/*     */             break;
/*     */           case '0':
/*     */           case '1':
/*     */           case '2':
/*     */           case '3':
/*     */           case '4':
/*     */           case '5':
/*     */           case '6':
/*     */           case '7':
/*     */           case '8':
/*     */           case '9':
/* 169 */             n = c - 48;
/* 170 */             if (this.msg_opt != null && n < this.msg_opt.length) {
/* 171 */               sb.append(this.msg_opt[n]);
/*     */             }
/*     */             break;
/*     */           case 'c':
/* 175 */             sb.append(this.msg_code);
/*     */             break;
/*     */           case 'e':
/* 178 */             sb.append(getCause());
/*     */             break;
/*     */           case 'p':
/* 181 */             sb.append(this.pos);
/*     */             break;
/*     */           case 's':
/* 184 */             sb.append(this.string);
/*     */             break;
/*     */           case 'w':
/* 187 */             sb.append(this.word);
/*     */             break;
/*     */           default:
/* 190 */             sb.append('%');
/* 191 */             sb.append(c);
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 196 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\EvalException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */