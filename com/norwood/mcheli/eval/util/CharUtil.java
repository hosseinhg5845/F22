/*    */ package com.norwood.mcheli.eval.util;
/*    */ 
/*    */ public class CharUtil {
/*    */   public static String escapeString(String str) {
/*  5 */     return escapeString(str, 0, str.length());
/*    */   }
/*    */   
/*    */   public static String escapeString(String input, int offset, int length) {
/*  9 */     StringBuilder escaped = new StringBuilder(length);
/* 10 */     int end = offset + length;
/* 11 */     int[] advance = new int[1];
/*    */     
/* 13 */     while (offset < end) {
/* 14 */       char c = escapeChar(input, offset, end, advance);
/* 15 */       if (advance[0] <= 0)
/* 16 */         break;  escaped.append(c);
/* 17 */       offset += advance[0];
/*    */     } 
/*    */     
/* 20 */     return escaped.toString();
/*    */   } public static char escapeChar(String str, int pos, int end, int[] ret) {
/*    */     long code;
/*    */     int i;
/* 24 */     if (pos >= end) {
/* 25 */       ret[0] = 0;
/* 26 */       return Character.MIN_VALUE;
/*    */     } 
/*    */     
/* 29 */     char c = str.charAt(pos);
/* 30 */     if (c != '\\') {
/* 31 */       ret[0] = 1;
/* 32 */       return c;
/*    */     } 
/*    */     
/* 35 */     pos++;
/* 36 */     if (pos >= end) {
/* 37 */       ret[0] = 1;
/* 38 */       return '\\';
/*    */     } 
/*    */     
/* 41 */     c = str.charAt(pos);
/* 42 */     ret[0] = 2;
/*    */ 
/*    */     
/* 45 */     switch (c) { case 'b': 
/*    */       case 'f': 
/*    */       case 'n': 
/*    */       case 'r': 
/*    */       case 't':
/*    */       
/*    */       case 'u':
/* 52 */         code = 0L;
/* 53 */         for (i = 0; i < 4 && ++pos < end; i++, ret[0] = ret[0] + 1)
/* 54 */         { char hex = str.charAt(pos);
/* 55 */           int digit = Character.digit(hex, 16);
/* 56 */           if (digit == -1)
/* 57 */             break;  code = (code << 4L) + digit; } 
/*    */       case '0': case '1': case '2': case '3':
/*    */       case '4':
/*    */       case '5':
/*    */       case '6':
/*    */       case '7':
/* 63 */         code = (c - 48);
/* 64 */         for (i = 1; i < 3 && pos + 1 < end; i++) {
/* 65 */           char next = str.charAt(pos + 1);
/* 66 */           if (next < '0' || next > '7')
/* 67 */             break;  code = (code << 3L) + (next - 48);
/* 68 */           pos++;
/* 69 */           ret[0] = ret[0] + 1;
/*    */         }  }
/*    */ 
/*    */     
/* 73 */     return c;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eva\\util\CharUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */