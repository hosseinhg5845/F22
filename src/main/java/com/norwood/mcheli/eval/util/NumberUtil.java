/*     */ package com.norwood.mcheli.eval.util;
/*     */ 
/*     */ public class NumberUtil {
/*     */   public static long parseLong(String str) {
/*   5 */     if (str == null) {
/*   6 */       return 0L;
/*     */     }
/*     */     
/*   9 */     str = str.trim();
/*  10 */     int len = str.length();
/*  11 */     if (len == 0) {
/*  12 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/*  16 */     char lastChar = str.charAt(len - 1);
/*  17 */     if (lastChar == '.' || lastChar == 'L' || lastChar == 'l') {
/*  18 */       len--;
/*  19 */       str = str.substring(0, len);
/*     */     } 
/*     */ 
/*     */     
/*  23 */     if (len >= 3 && str.charAt(0) == '0') {
/*  24 */       switch (str.charAt(1)) { case 'B': case 'b': case 'O': case 'o': case 'X': case 'x':  }  return 
/*     */ 
/*     */ 
/*     */         
/*  28 */         parseLongDec(str, 0, len);
/*     */     } 
/*     */ 
/*     */     
/*  32 */     return parseLongDec(str, 0, len);
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLongBin(String str) {
/*  37 */     return (str == null) ? 0L : parseLongBin(str, 0, str.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long parseLongBin(String str, int pos, int len) {
/*     */     // Byte code:
/*     */     //   0: lconst_0
/*     */     //   1: lstore_3
/*     */     //   2: iload_1
/*     */     //   3: iload_2
/*     */     //   4: iadd
/*     */     //   5: istore #5
/*     */     //   7: iload_1
/*     */     //   8: iload #5
/*     */     //   10: if_icmpge -> 87
/*     */     //   13: lload_3
/*     */     //   14: ldc2_w 2
/*     */     //   17: lmul
/*     */     //   18: lstore_3
/*     */     //   19: aload_0
/*     */     //   20: iload_1
/*     */     //   21: invokevirtual charAt : (I)C
/*     */     //   24: istore #6
/*     */     //   26: lload_3
/*     */     //   27: iload #6
/*     */     //   29: lookupswitch default -> 64, 48 -> 56, 49 -> 60
/*     */     //   56: lconst_0
/*     */     //   57: goto -> 79
/*     */     //   60: lconst_1
/*     */     //   61: goto -> 79
/*     */     //   64: new java/lang/NumberFormatException
/*     */     //   67: dup
/*     */     //   68: aload_0
/*     */     //   69: iload_1
/*     */     //   70: iload #5
/*     */     //   72: invokevirtual substring : (II)Ljava/lang/String;
/*     */     //   75: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   78: athrow
/*     */     //   79: ladd
/*     */     //   80: lstore_3
/*     */     //   81: iinc #1, 1
/*     */     //   84: goto -> 7
/*     */     //   87: lload_3
/*     */     //   88: lreturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #41	-> 0
/*     */     //   #42	-> 2
/*     */     //   #44	-> 7
/*     */     //   #45	-> 13
/*     */     //   #46	-> 19
/*     */     //   #47	-> 26
/*     */     //   #48	-> 56
/*     */     //   #49	-> 60
/*     */     //   #50	-> 64
/*     */     //   #44	-> 81
/*     */     //   #54	-> 87
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   26	55	6	c	C
/*     */     //   0	89	0	str	Ljava/lang/String;
/*     */     //   0	89	1	pos	I
/*     */     //   0	89	2	len	I
/*     */     //   2	87	3	result	J
/*     */     //   7	82	5	end	I
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long parseLongOct(String str) {
/*  59 */     return (str == null) ? 0L : parseLongOct(str, 0, str.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long parseLongOct(String str, int pos, int len) {
/*     */     // Byte code:
/*     */     //   0: lconst_0
/*     */     //   1: lstore_3
/*     */     //   2: iload_1
/*     */     //   3: iload_2
/*     */     //   4: iadd
/*     */     //   5: istore #5
/*     */     //   7: iload_1
/*     */     //   8: iload #5
/*     */     //   10: if_icmpge -> 112
/*     */     //   13: lload_3
/*     */     //   14: ldc2_w 8
/*     */     //   17: lmul
/*     */     //   18: lstore_3
/*     */     //   19: aload_0
/*     */     //   20: iload_1
/*     */     //   21: invokevirtual charAt : (I)C
/*     */     //   24: istore #6
/*     */     //   26: lload_3
/*     */     //   27: iload #6
/*     */     //   29: tableswitch default -> 89, 48 -> 76, 49 -> 80, 50 -> 80, 51 -> 80, 52 -> 80, 53 -> 80, 54 -> 80, 55 -> 80
/*     */     //   76: lconst_0
/*     */     //   77: goto -> 104
/*     */     //   80: iload #6
/*     */     //   82: bipush #48
/*     */     //   84: isub
/*     */     //   85: i2l
/*     */     //   86: goto -> 104
/*     */     //   89: new java/lang/NumberFormatException
/*     */     //   92: dup
/*     */     //   93: aload_0
/*     */     //   94: iload_1
/*     */     //   95: iload #5
/*     */     //   97: invokevirtual substring : (II)Ljava/lang/String;
/*     */     //   100: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   103: athrow
/*     */     //   104: ladd
/*     */     //   105: lstore_3
/*     */     //   106: iinc #1, 1
/*     */     //   109: goto -> 7
/*     */     //   112: lload_3
/*     */     //   113: lreturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #63	-> 0
/*     */     //   #64	-> 2
/*     */     //   #66	-> 7
/*     */     //   #67	-> 13
/*     */     //   #68	-> 19
/*     */     //   #69	-> 26
/*     */     //   #70	-> 76
/*     */     //   #71	-> 80
/*     */     //   #72	-> 89
/*     */     //   #66	-> 106
/*     */     //   #76	-> 112
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   26	80	6	c	C
/*     */     //   0	114	0	str	Ljava/lang/String;
/*     */     //   0	114	1	pos	I
/*     */     //   0	114	2	len	I
/*     */     //   2	112	3	result	J
/*     */     //   7	107	5	end	I
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long parseLongDec(String str) {
/*  81 */     return (str == null) ? 0L : parseLongDec(str, 0, str.length());
/*     */   }
/*     */   
/*     */   public static long parseLongDec(String str, int pos, int len) {
/*  85 */     long ret = 0L;
/*     */     
/*  87 */     for (int i = 0; i < len; i++) {
/*  88 */       ret *= 10L;
/*  89 */       char c = str.charAt(pos++);
/*  90 */       switch (c) {
/*     */         case '1':
/*     */         case '2':
/*     */         case '3':
/*     */         case '4':
/*     */         case '5':
/*     */         case '6':
/*     */         case '7':
/*     */         case '8':
/*     */         case '9':
/* 100 */           ret += (c - 48);
/*     */           break;
/*     */         
/*     */         default:
/* 104 */           throw new NumberFormatException(str.substring(pos, len));
/*     */       } 
/*     */     
/*     */     } 
/* 108 */     return ret;
/*     */   }
/*     */   
/*     */   public static long parseLongHex(String str) {
/* 112 */     return (str == null) ? 0L : parseLongHex(str, 0, str.length());
/*     */   }
/*     */   
/*     */   public static long parseLongHex(String str, int pos, int len) {
/*     */     // Byte code:
/*     */     //   0: lconst_0
/*     */     //   1: lstore_3
/*     */     //   2: iload_1
/*     */     //   3: iload_2
/*     */     //   4: iadd
/*     */     //   5: istore #5
/*     */     //   7: iload_1
/*     */     //   8: iload #5
/*     */     //   10: if_icmpge -> 324
/*     */     //   13: lload_3
/*     */     //   14: ldc2_w 16
/*     */     //   17: lmul
/*     */     //   18: lstore_3
/*     */     //   19: aload_0
/*     */     //   20: iload_1
/*     */     //   21: invokevirtual charAt : (I)C
/*     */     //   24: istore #6
/*     */     //   26: lload_3
/*     */     //   27: iload #6
/*     */     //   29: tableswitch default -> 301, 48 -> 264, 49 -> 268, 50 -> 268, 51 -> 268, 52 -> 268, 53 -> 268, 54 -> 268, 55 -> 268, 56 -> 268, 57 -> 268, 58 -> 301, 59 -> 301, 60 -> 301, 61 -> 301, 62 -> 301, 63 -> 301, 64 -> 301, 65 -> 277, 66 -> 277, 67 -> 277, 68 -> 277, 69 -> 277, 70 -> 277, 71 -> 301, 72 -> 301, 73 -> 301, 74 -> 301, 75 -> 301, 76 -> 301, 77 -> 301, 78 -> 301, 79 -> 301, 80 -> 301, 81 -> 301, 82 -> 301, 83 -> 301, 84 -> 301, 85 -> 301, 86 -> 301, 87 -> 301, 88 -> 301, 89 -> 301, 90 -> 301, 91 -> 301, 92 -> 301, 93 -> 301, 94 -> 301, 95 -> 301, 96 -> 301, 97 -> 289, 98 -> 289, 99 -> 289, 100 -> 289, 101 -> 289, 102 -> 289
/*     */     //   264: lconst_0
/*     */     //   265: goto -> 316
/*     */     //   268: iload #6
/*     */     //   270: bipush #48
/*     */     //   272: isub
/*     */     //   273: i2l
/*     */     //   274: goto -> 316
/*     */     //   277: iload #6
/*     */     //   279: bipush #65
/*     */     //   281: isub
/*     */     //   282: bipush #10
/*     */     //   284: iadd
/*     */     //   285: i2l
/*     */     //   286: goto -> 316
/*     */     //   289: iload #6
/*     */     //   291: bipush #97
/*     */     //   293: isub
/*     */     //   294: bipush #10
/*     */     //   296: iadd
/*     */     //   297: i2l
/*     */     //   298: goto -> 316
/*     */     //   301: new java/lang/NumberFormatException
/*     */     //   304: dup
/*     */     //   305: aload_0
/*     */     //   306: iload_1
/*     */     //   307: iload #5
/*     */     //   309: invokevirtual substring : (II)Ljava/lang/String;
/*     */     //   312: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   315: athrow
/*     */     //   316: ladd
/*     */     //   317: lstore_3
/*     */     //   318: iinc #1, 1
/*     */     //   321: goto -> 7
/*     */     //   324: lload_3
/*     */     //   325: lreturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #116	-> 0
/*     */     //   #117	-> 2
/*     */     //   #119	-> 7
/*     */     //   #120	-> 13
/*     */     //   #121	-> 19
/*     */     //   #122	-> 26
/*     */     //   #123	-> 264
/*     */     //   #124	-> 268
/*     */     //   #125	-> 277
/*     */     //   #126	-> 289
/*     */     //   #127	-> 301
/*     */     //   #119	-> 318
/*     */     //   #131	-> 324
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   26	292	6	c	C
/*     */     //   0	326	0	str	Ljava/lang/String;
/*     */     //   0	326	1	pos	I
/*     */     //   0	326	2	len	I
/*     */     //   2	324	3	result	J
/*     */     //   7	319	5	end	I
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eva\\util\NumberUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */