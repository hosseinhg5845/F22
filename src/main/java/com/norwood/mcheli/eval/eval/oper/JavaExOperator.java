/*     */ package com.norwood.mcheli.eval.eval.oper;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ public class JavaExOperator implements Operator {
/*     */   boolean inLong(Object x) {
/*   8 */     if (x instanceof Long)
/*   9 */       return true; 
/*  10 */     if (x instanceof Integer)
/*  11 */       return true; 
/*  12 */     if (x instanceof Short)
/*  13 */       return true; 
/*  14 */     if (x instanceof Byte) {
/*  15 */       return true;
/*     */     }
/*  17 */     return (x instanceof BigInteger || x instanceof BigDecimal);
/*     */   }
/*     */ 
/*     */   
/*     */   long l(Object x) {
/*  22 */     return ((Number)x).longValue();
/*     */   }
/*     */   
/*     */   boolean inDouble(Object x) {
/*  26 */     return (x instanceof Double || x instanceof Float);
/*     */   }
/*     */   
/*     */   double d(Object x) {
/*  30 */     return ((Number)x).doubleValue();
/*     */   }
/*     */   
/*     */   Object n(long n, Object x) {
/*  34 */     if (x instanceof Long)
/*  35 */       return Long.valueOf(n); 
/*  36 */     if (x instanceof Double)
/*  37 */       return Double.valueOf(n); 
/*  38 */     if (x instanceof Integer)
/*  39 */       return Integer.valueOf((int)n); 
/*  40 */     if (x instanceof Short)
/*  41 */       return Short.valueOf((short)(int)n); 
/*  42 */     if (x instanceof Byte)
/*  43 */       return Byte.valueOf((byte)(int)n); 
/*  44 */     if (x instanceof Float)
/*  45 */       return Float.valueOf((float)n); 
/*  46 */     if (x instanceof BigInteger)
/*  47 */       return BigInteger.valueOf(n); 
/*  48 */     if (x instanceof BigDecimal) {
/*  49 */       return BigDecimal.valueOf(n);
/*     */     }
/*  51 */     return (x instanceof String) ? String.valueOf(n) : Long.valueOf(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object n(long n, Object x, Object y) {
/*  56 */     if (x instanceof Byte || y instanceof Byte)
/*  57 */       return Byte.valueOf((byte)(int)n); 
/*  58 */     if (x instanceof Short || y instanceof Short)
/*  59 */       return Short.valueOf((short)(int)n); 
/*  60 */     if (x instanceof Integer || y instanceof Integer)
/*  61 */       return Integer.valueOf((int)n); 
/*  62 */     if (x instanceof Long || y instanceof Long)
/*  63 */       return Long.valueOf(n); 
/*  64 */     if (x instanceof BigInteger || y instanceof BigInteger)
/*  65 */       return BigInteger.valueOf(n); 
/*  66 */     if (x instanceof BigDecimal || y instanceof BigDecimal)
/*  67 */       return BigDecimal.valueOf(n); 
/*  68 */     if (x instanceof Float || y instanceof Float)
/*  69 */       return Float.valueOf((float)n); 
/*  70 */     if (x instanceof Double || y instanceof Double) {
/*  71 */       return Double.valueOf(n);
/*     */     }
/*  73 */     return (!(x instanceof String) && !(y instanceof String)) ? Long.valueOf(n) : String.valueOf(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object n(double n, Object x) {
/*  78 */     if (x instanceof Float) {
/*  79 */       return Float.valueOf((float)n);
/*     */     }
/*  81 */     return (x instanceof String) ? String.valueOf(n) : new Double(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object n(double n, Object x, Object y) {
/*  86 */     if (x instanceof Float || y instanceof Float)
/*  87 */       return Float.valueOf((float)n); 
/*  88 */     if (x instanceof Number || y instanceof Number) {
/*  89 */       return Double.valueOf(n);
/*     */     }
/*  91 */     return (!(x instanceof String) && !(y instanceof String)) ? new Double(n) : String.valueOf(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object nn(long n, Object x) {
/*  96 */     if (x instanceof BigDecimal) {
/*  97 */       return BigDecimal.valueOf(n);
/*     */     }
/*  99 */     return (x instanceof BigInteger) ? BigInteger.valueOf(n) : Long.valueOf(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object nn(long n, Object x, Object y) {
/* 104 */     if (x instanceof BigDecimal || y instanceof BigDecimal) {
/* 105 */       return BigDecimal.valueOf(n);
/*     */     }
/* 107 */     return (!(x instanceof BigInteger) && !(y instanceof BigInteger)) ? Long.valueOf(n) : BigInteger.valueOf(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object nn(double n, Object x) {
/* 112 */     return Double.valueOf(inLong(x) ? Long.valueOf((long)n).longValue() : (new Double(n)).doubleValue());
/*     */   }
/*     */   
/*     */   Object nn(double n, Object x, Object y) {
/* 116 */     return Double.valueOf((inLong(x) && inLong(y)) ? Long.valueOf((long)n).longValue() : (new Double(n)).doubleValue());
/*     */   }
/*     */   
/*     */   RuntimeException undefined(Object x) {
/* 120 */     String c = null;
/* 121 */     if (x != null) {
/* 122 */       c = x.getClass().getName();
/*     */     }
/*     */     
/* 125 */     return new RuntimeException("未定義単項演算：" + c);
/*     */   }
/*     */   
/*     */   RuntimeException undefined(Object x, Object y) {
/* 129 */     String c1 = null;
/* 130 */     String c2 = null;
/* 131 */     if (x != null) {
/* 132 */       c1 = x.getClass().getName();
/*     */     }
/*     */     
/* 135 */     if (y != null) {
/* 136 */       c2 = y.getClass().getName();
/*     */     }
/*     */     
/* 139 */     return new RuntimeException("未定義二項演算：" + c1 + " , " + c2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object power(Object x, Object y) {
/* 144 */     return (x == null && y == null) ? null : nn(Math.pow(d(x), d(y)), x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object signPlus(Object x) {
/* 149 */     return x;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object signMinus(Object x) {
/* 154 */     if (x == null)
/* 155 */       return null; 
/* 156 */     if (inLong(x))
/* 157 */       return n(-l(x), x); 
/* 158 */     if (inDouble(x))
/* 159 */       return n(-d(x), x); 
/* 160 */     if (x instanceof Boolean) {
/* 161 */       return x;
/*     */     }
/* 163 */     throw undefined(x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object plus(Object x, Object y) {
/* 169 */     if (x == null && y == null)
/* 170 */       return null; 
/* 171 */     if (inLong(x) && inLong(y))
/* 172 */       return nn(l(x) + l(y), x, y); 
/* 173 */     if (inDouble(x) && inDouble(y))
/* 174 */       return nn(d(x) + d(y), x, y); 
/* 175 */     if (x instanceof String || y instanceof String)
/* 176 */       return x + String.valueOf(y); 
/* 177 */     if (!(x instanceof Character) && !(y instanceof Character)) {
/* 178 */       throw undefined(x, y);
/*     */     }
/* 180 */     return x + String.valueOf(y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object minus(Object x, Object y) {
/* 186 */     if (x == null && y == null)
/* 187 */       return null; 
/* 188 */     if (inLong(x) && inLong(y))
/* 189 */       return nn(l(x) - l(y), x, y); 
/* 190 */     if (inDouble(x) && inDouble(y)) {
/* 191 */       return nn(d(x) - d(y), x, y);
/*     */     }
/* 193 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object mult(Object x, Object y) {
/* 199 */     if (x == null && y == null)
/* 200 */       return null; 
/* 201 */     if (inLong(x) && inLong(y))
/* 202 */       return nn(l(x) * l(y), x, y); 
/* 203 */     if (inDouble(x) && inDouble(y)) {
/* 204 */       return nn(d(x) * d(y), x, y);
/*     */     }
/* 206 */     String s = null;
/* 207 */     int ct = 0;
/* 208 */     boolean str = false;
/* 209 */     if (x instanceof String && y instanceof Number) {
/* 210 */       s = (String)x;
/* 211 */       ct = (int)l(y);
/* 212 */       str = true;
/* 213 */     } else if (y instanceof String && x instanceof Number) {
/* 214 */       s = (String)y;
/* 215 */       ct = (int)l(x);
/* 216 */       str = true;
/*     */     } 
/*     */     
/* 219 */     if (!str) {
/* 220 */       throw undefined(x, y);
/*     */     }
/* 222 */     StringBuilder sb = new StringBuilder(s.length() * ct);
/*     */     
/* 224 */     for (int i = 0; i < ct; i++) {
/* 225 */       sb.append(s);
/*     */     }
/*     */     
/* 228 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object div(Object x, Object y) {
/* 235 */     if (x == null && y == null)
/* 236 */       return null; 
/* 237 */     if (inLong(x) && inLong(y))
/* 238 */       return nn(l(x) / l(y), x); 
/* 239 */     if (inDouble(x) && inDouble(y))
/* 240 */       return nn(d(x) / d(y), x); 
/* 241 */     if (x instanceof String) { String s = (String)x; if (y instanceof String) { String r = (String)y;
/* 242 */         return s.split(r); }
/*     */        }
/* 244 */      throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object mod(Object x, Object y) {
/* 250 */     if (x == null && y == null)
/* 251 */       return null; 
/* 252 */     if (inLong(x) && inLong(y))
/* 253 */       return nn(l(x) % l(y), x); 
/* 254 */     if (inDouble(x) && inDouble(y)) {
/* 255 */       return nn(d(x) % d(y), x);
/*     */     }
/* 257 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bitNot(Object x) {
/* 263 */     if (x == null)
/* 264 */       return null; 
/* 265 */     if (x instanceof Number) {
/* 266 */       return n(l(x) ^ 0xFFFFFFFFFFFFFFFFL, x);
/*     */     }
/* 268 */     throw undefined(x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object shiftLeft(Object x, Object y) {
/* 274 */     if (x == null && y == null)
/* 275 */       return null; 
/* 276 */     if (inLong(x) && inLong(y))
/* 277 */       return n(l(x) << (int)l(y), x); 
/* 278 */     if (inDouble(x) && inDouble(y)) {
/* 279 */       return n(d(x) * Math.pow(2.0D, d(y)), x);
/*     */     }
/* 281 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object shiftRight(Object x, Object y) {
/* 287 */     if (x == null && y == null)
/* 288 */       return null; 
/* 289 */     if (inLong(x) && inLong(y))
/* 290 */       return n(l(x) >> (int)l(y), x); 
/* 291 */     if (inDouble(x) && inDouble(y)) {
/* 292 */       return n(d(x) / Math.pow(2.0D, d(y)), x);
/*     */     }
/* 294 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object shiftRightLogical(Object x, Object y) {
/* 300 */     if (x == null && y == null)
/* 301 */       return null; 
/* 302 */     if (x instanceof Byte && y instanceof Number)
/* 303 */       return n((l(x) & 0xFFL) >>> (int)l(y), x); 
/* 304 */     if (x instanceof Short && y instanceof Number)
/* 305 */       return n((l(x) & 0xFFFFL) >>> (int)l(y), x); 
/* 306 */     if (x instanceof Integer && y instanceof Number)
/* 307 */       return n((l(x) & 0xFFFFFFFFFFFFFFFFL) >>> (int)l(y), x); 
/* 308 */     if (inLong(x) && y instanceof Number)
/* 309 */       return n(l(x) >>> (int)l(y), x); 
/* 310 */     if (inDouble(x) && y instanceof Number) {
/* 311 */       double t = d(x);
/* 312 */       if (t < 0.0D) {
/* 313 */         t = -t;
/*     */       }
/*     */       
/* 316 */       return n(t / Math.pow(2.0D, d(y)), x);
/*     */     } 
/* 318 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bitAnd(Object x, Object y) {
/* 324 */     if (x == null && y == null)
/* 325 */       return null; 
/* 326 */     if (x instanceof Number && y instanceof Number) {
/* 327 */       return n(l(x) & l(y), x);
/*     */     }
/* 329 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bitOr(Object x, Object y) {
/* 335 */     if (x == null && y == null)
/* 336 */       return null; 
/* 337 */     if (x instanceof Number && y instanceof Number) {
/* 338 */       return n(l(x) | l(y), x);
/*     */     }
/* 340 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bitXor(Object x, Object y) {
/* 346 */     if (x == null && y == null)
/* 347 */       return null; 
/* 348 */     if (x instanceof Number && y instanceof Number) {
/* 349 */       return n(l(x) ^ l(y), x);
/*     */     }
/* 351 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object not(Object x) {
/* 357 */     if (x == null)
/* 358 */       return null; 
/* 359 */     if (x instanceof Boolean)
/* 360 */       return ((Boolean)x).booleanValue() ? Boolean.FALSE : Boolean.TRUE; 
/* 361 */     if (x instanceof Number) {
/* 362 */       return (l(x) == 0L) ? Boolean.TRUE : Boolean.FALSE;
/*     */     }
/* 364 */     throw undefined(x);
/*     */   }
/*     */ 
/*     */   
/*     */   int compare(Object x, Object y) {
/* 369 */     if (x == null && y == null)
/* 370 */       return 0; 
/* 371 */     if (x == null)
/* 372 */       return -1; 
/* 373 */     if (y == null)
/* 374 */       return 1; 
/* 375 */     if (inLong(x) && inLong(y)) {
/* 376 */       long c = l(x) - l(y);
/* 377 */       if (c == 0L) {
/* 378 */         return 0;
/*     */       }
/* 380 */       return (c < 0L) ? -1 : 1;
/*     */     } 
/* 382 */     if (x instanceof Number && y instanceof Number) {
/* 383 */       double n = d(x) - d(y);
/* 384 */       if (n == 0.0D) {
/* 385 */         return 0;
/*     */       }
/* 387 */       return (n < 0.0D) ? -1 : 1;
/*     */     } 
/*     */     
/* 390 */     Class<?> xc = x.getClass();
/* 391 */     Class<?> yc = y.getClass();
/* 392 */     if (xc.isAssignableFrom(yc) && x instanceof Comparable)
/* 393 */       return ((Comparable<Object>)x).compareTo(y); 
/* 394 */     if (yc.isAssignableFrom(xc) && y instanceof Comparable)
/* 395 */       return -((Comparable)y).compareTo((Object)x); 
/* 396 */     if (x.equals(y)) {
/* 397 */       return 0;
/*     */     }
/* 399 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object equal(Object x, Object y) {
/* 406 */     return (compare(x, y) == 0) ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object notEqual(Object x, Object y) {
/* 411 */     return (compare(x, y) != 0) ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object lessThan(Object x, Object y) {
/* 416 */     return (compare(x, y) < 0) ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object lessEqual(Object x, Object y) {
/* 421 */     return (compare(x, y) <= 0) ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object greaterThan(Object x, Object y) {
/* 426 */     return (compare(x, y) > 0) ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object greaterEqual(Object x, Object y) {
/* 431 */     return (compare(x, y) >= 0) ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bool(Object x) {
/* 436 */     if (x == null)
/* 437 */       return false; 
/* 438 */     if (x instanceof Boolean) {
/* 439 */       return ((Boolean)x).booleanValue();
/*     */     }
/* 441 */     return (x instanceof Number) ? ((((Number)x).longValue() != 0L)) : Boolean.parseBoolean(x.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object inc(Object x, int inc) {
/* 447 */     if (x == null)
/* 448 */       return null; 
/* 449 */     if (inLong(x))
/* 450 */       return n(l(x) + inc, x); 
/* 451 */     if (inDouble(x)) {
/* 452 */       return n(d(x) + inc, x);
/*     */     }
/* 454 */     throw undefined(x);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\eval\eval\oper\JavaExOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */