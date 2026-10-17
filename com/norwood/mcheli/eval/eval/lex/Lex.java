/*     */ package com.norwood.mcheli.eval.eval.lex;
/*     */ 
/*     */ import com.norwood.mcheli.eval.eval.exp.AbstractExpression;
/*     */ import com.norwood.mcheli.eval.eval.exp.ShareExpValue;
/*     */ import com.norwood.mcheli.eval.util.CharUtil;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ public class Lex {
/*     */   public static final int TYPE_WORD = 2147483632;
/*     */   public static final int TYPE_NUM = 2147483633;
/*     */   public static final int TYPE_OPE = 2147483634;
/*     */   public static final int TYPE_STRING = 2147483635;
/*     */   public static final int TYPE_CHAR = 2147483636;
/*     */   public static final int TYPE_EOF = 2147483647;
/*     */   public static final int TYPE_ERR = -1;
/*     */   protected final List<String>[] opeList;
/*     */   protected final String string;
/*  19 */   protected int pos = 0;
/*  20 */   protected int len = 0;
/*  21 */   protected int type = -1;
/*     */   protected String ope;
/*     */   protected final ShareExpValue expShare;
/*  24 */   protected final String SPC_CHAR = " \t\r\n";
/*  25 */   protected final String NUMBER_CHAR = "._";
/*     */   
/*     */   protected Lex(String str, List<String>[] lists, AbstractExpression paren, ShareExpValue exp) {
/*  28 */     this.string = str;
/*  29 */     this.opeList = lists;
/*  30 */     this.expShare = exp;
/*  31 */     if (this.expShare.paren == null) {
/*  32 */       this.expShare.paren = paren;
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean isSpace(int pos) {
/*  37 */     if (pos >= this.string.length()) {
/*  38 */       return true;
/*     */     }
/*  40 */     char c = this.string.charAt(pos);
/*  41 */     Objects.requireNonNull(this); return (" \t\r\n".indexOf(c) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isNumberTop(int pos) {
/*  46 */     if (pos >= this.string.length()) {
/*  47 */       return false;
/*     */     }
/*  49 */     char c = this.string.charAt(pos);
/*  50 */     return ('0' <= c && c <= '9');
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isSpecialNumber(int pos) {
/*  55 */     if (pos >= this.string.length()) {
/*  56 */       return false;
/*     */     }
/*  58 */     char c = this.string.charAt(pos);
/*  59 */     Objects.requireNonNull(this); return ("._".indexOf(c) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String isOperator(int pos) {
/*  64 */     for (int i = this.opeList.length - 1; i >= 0; i--) {
/*  65 */       if (pos + i < this.string.length()) {
/*  66 */         List<String> list = this.opeList[i];
/*  67 */         if (list != null)
/*     */         {
/*  69 */           label21: for (String ope : list) {
/*  70 */             for (int k = 0; k <= i; k++) {
/*  71 */               char c = this.string.charAt(pos + k);
/*  72 */               char o = ope.charAt(k);
/*  73 */               if (c != o) {
/*     */                 continue label21;
/*     */               }
/*     */             } 
/*     */             
/*  78 */             return ope;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     return null;
/*     */   }
/*     */   
/*     */   protected boolean isStringTop(int pos) {
/*  88 */     if (pos >= this.string.length()) {
/*  89 */       return false;
/*     */     }
/*  91 */     char c = this.string.charAt(pos);
/*  92 */     return (c == '"');
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isStringEnd(int pos) {
/*  97 */     return isStringTop(pos);
/*     */   }
/*     */   
/*     */   protected boolean isCharTop(int pos) {
/* 101 */     if (pos >= this.string.length()) {
/* 102 */       return false;
/*     */     }
/* 104 */     char c = this.string.charAt(pos);
/* 105 */     return (c == '\'');
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCharEnd(int pos) {
/* 110 */     return isCharTop(pos);
/*     */   }
/*     */   
/*     */   public void check() {
/* 114 */     while (isSpace(this.pos)) {
/* 115 */       if (this.pos >= this.string.length()) {
/* 116 */         this.type = Integer.MAX_VALUE;
/* 117 */         this.len = 0;
/*     */         
/*     */         return;
/*     */       } 
/* 121 */       this.pos++;
/*     */     } 
/*     */     
/* 124 */     if (isStringTop(this.pos)) {
/* 125 */       processString();
/* 126 */     } else if (isCharTop(this.pos)) {
/* 127 */       processChar();
/*     */     } else {
/* 129 */       String ope = isOperator(this.pos);
/* 130 */       if (ope != null) {
/* 131 */         this.type = 2147483634;
/* 132 */         this.ope = ope;
/* 133 */         this.len = ope.length();
/*     */       } else {
/* 135 */         boolean number = isNumberTop(this.pos);
/* 136 */         this.type = number ? 2147483633 : 2147483632;
/* 137 */         this.len = 1;
/*     */         
/* 139 */         while (!isSpace(this.pos + this.len) && ((number && isSpecialNumber(this.pos + this.len)) || isOperator(this.pos + this.len) == null)) {
/* 140 */           this.len++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processString() {
/* 147 */     int[] ret = new int[1];
/* 148 */     this.type = 2147483635;
/* 149 */     this.len = 1;
/*     */     
/*     */     do {
/* 152 */       this.len += getCharLen(this.pos + this.len, ret);
/* 153 */       if (this.pos + this.len >= this.string.length()) {
/* 154 */         this.type = Integer.MAX_VALUE;
/*     */         break;
/*     */       } 
/* 157 */     } while (!isStringEnd(this.pos + this.len));
/*     */     
/* 159 */     this.len++;
/*     */   }
/*     */   
/*     */   protected void processChar() {
/* 163 */     int[] ret = new int[1];
/* 164 */     this.type = 2147483636;
/* 165 */     this.len = 1;
/*     */     
/*     */     do {
/* 168 */       this.len += getCharLen(this.pos + this.len, ret);
/* 169 */       if (this.pos + this.len >= this.string.length()) {
/* 170 */         this.type = Integer.MAX_VALUE;
/*     */         break;
/*     */       } 
/* 173 */     } while (!isCharEnd(this.pos + this.len));
/*     */     
/* 175 */     this.len++;
/*     */   }
/*     */   
/*     */   protected int getCharLen(int pos, int[] ret) {
/* 179 */     CharUtil.escapeChar(this.string, pos, this.string.length(), ret);
/* 180 */     return ret[0];
/*     */   }
/*     */   
/*     */   public Lex next() {
/* 184 */     this.pos += this.len;
/* 185 */     check();
/* 186 */     return this;
/*     */   }
/*     */   
/*     */   public int getType() {
/* 190 */     return this.type;
/*     */   }
/*     */   
/*     */   public String getOperator() {
/* 194 */     return this.ope;
/*     */   }
/*     */   
/*     */   public boolean isOperator(String ope) {
/* 198 */     return (this.type == 2147483634 && this.ope.equals(ope));
/*     */   }
/*     */   
/*     */   public String getWord() {
/* 202 */     return this.string.substring(this.pos, this.pos + this.len);
/*     */   }
/*     */   
/*     */   public String getString() {
/* 206 */     return this.string;
/*     */   }
/*     */   
/*     */   public int getPos() {
/* 210 */     return this.pos;
/*     */   }
/*     */   
/*     */   public ShareExpValue getShare() {
/* 214 */     return this.expShare;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\lex\Lex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */