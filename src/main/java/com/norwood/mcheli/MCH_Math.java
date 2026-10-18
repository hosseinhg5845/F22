/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class MCH_Math
/*     */ {
/*     */   public static final float PI = 3.1415927F;
/*  10 */   public static final MCH_Math instance = new MCH_Math();
/*     */   
/*     */   public static FVector3D newVec3D() {
/*  13 */     return instance.privateNewVec3D(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public static FVector3D newVec3D(float x, float y, float z) {
/*  17 */     return instance.privateNewVec3D(x, y, z);
/*     */   }
/*     */   
/*     */   public static FQuat newQuat() {
/*  21 */     return instance.privateNewQuat();
/*     */   }
/*     */   
/*     */   public static FMatrix newMatrix() {
/*  25 */     return instance.privateNewMatrix();
/*     */   }
/*     */   
/*     */   public static FQuat EulerToQuatTestNG(float yaw, float pitch, float roll) {
/*  29 */     FVector3D axis = newVec3D();
/*  30 */     float rot = VecNormalize(axis);
/*  31 */     FQuat dqtn = newQuat();
/*  32 */     QuatRotation(dqtn, rot, axis.x, axis.y, axis.z);
/*  33 */     return dqtn;
/*     */   }
/*     */   
/*     */   public static FMatrix EulerToMatrix(float yaw, float pitch, float roll) {
/*  37 */     FMatrix m = newMatrix();
/*  38 */     MatTurnZ(m, roll / 180.0F * 3.1415927F);
/*  39 */     MatTurnX(m, pitch / 180.0F * 3.1415927F);
/*  40 */     MatTurnY(m, yaw / 180.0F * 3.1415927F);
/*  41 */     return m;
/*     */   }
/*     */   
/*     */   public static FQuat EulerToQuat(float yaw, float pitch, float roll) {
/*  45 */     FQuat dqtn = newQuat();
/*  46 */     MatrixToQuat(dqtn, EulerToMatrix(yaw, pitch, roll));
/*  47 */     return dqtn;
/*     */   }
/*     */   
/*     */   public static FVector3D QuatToEuler(FQuat q) {
/*  51 */     FMatrix m = QuatToMatrix(q);
/*  52 */     return MatrixToEuler(m);
/*     */   }
/*     */   
/*     */   public static FVector3D MatrixToEuler(FMatrix m) {
/*  56 */     float xx = m.m00;
/*  57 */     float xy = m.m01;
/*  58 */     float xz = m.m02;
/*  59 */     float yy = m.m11;
/*  60 */     float zx = m.m20;
/*  61 */     float zy = m.m21;
/*  62 */     float zz = m.m22;
/*  63 */     float b = (float)-Math.asin(zy);
/*  64 */     float cosB = Cos(b);
/*     */ 
/*     */     
/*  67 */     if (Math.abs(cosB) >= 1.0E-4D) {
/*  68 */       c = Atan2(zx, zz);
/*  69 */       float xy_cos = xy / cosB;
/*  70 */       if (xy_cos > 1.0F) {
/*  71 */         xy_cos = 1.0F;
/*  72 */       } else if (xy_cos < -1.0F) {
/*  73 */         xy_cos = -1.0F;
/*     */       } 
/*     */       
/*  76 */       a = (float)Math.asin(xy_cos);
/*  77 */       if (Float.isNaN(a)) {
/*  78 */         a = 0.0F;
/*     */       }
/*     */     } else {
/*  81 */       c = Atan2(-xz, xx);
/*  82 */       a = 0.0F;
/*     */     } 
/*     */     
/*  85 */     float a = (float)(a * 57.29577791868205D);
/*  86 */     b = (float)(b * 57.29577791868205D);
/*  87 */     float c = (float)(c * 57.29577791868205D);
/*  88 */     if (yy < 0.0F) {
/*  89 */       a = 180.0F - a;
/*     */     }
/*     */     
/*  92 */     return newVec3D(-b, -c, -a);
/*     */   }
/*     */   
/*     */   public static float SIGN(float x) {
/*  96 */     return (x >= 0.0F) ? 1.0F : -1.0F;
/*     */   }
/*     */   
/*     */   public static float NORM(float a, float b, float c, float d) {
/* 100 */     return (float)Math.sqrt((a * a + b * b + c * c + d * d));
/*     */   }
/*     */   
/*     */   public static void QuatNormalize(FQuat q) {
/* 104 */     float r = NORM(q.w, q.x, q.y, q.z);
/* 105 */     if (MathHelper.abs(r) > 1.0E-4D) {
/* 106 */       q.w /= r;
/* 107 */       q.x /= r;
/* 108 */       q.y /= r;
/* 109 */       q.z /= r;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean MatrixToQuat(FQuat q, FMatrix m) {
/* 114 */     q.w = (m.m00 + m.m11 + m.m22 + 1.0F) / 4.0F;
/* 115 */     q.x = (m.m00 - m.m11 - m.m22 + 1.0F) / 4.0F;
/* 116 */     q.y = (-m.m00 + m.m11 - m.m22 + 1.0F) / 4.0F;
/* 117 */     q.z = (-m.m00 - m.m11 + m.m22 + 1.0F) / 4.0F;
/* 118 */     if (q.w < 0.0F) {
/* 119 */       q.w = 0.0F;
/*     */     }
/*     */     
/* 122 */     if (q.x < 0.0F) {
/* 123 */       q.x = 0.0F;
/*     */     }
/*     */     
/* 126 */     if (q.y < 0.0F) {
/* 127 */       q.y = 0.0F;
/*     */     }
/*     */     
/* 130 */     if (q.z < 0.0F) {
/* 131 */       q.z = 0.0F;
/*     */     }
/*     */     
/* 134 */     q.w = (float)Math.sqrt(q.w);
/* 135 */     q.x = (float)Math.sqrt(q.x);
/* 136 */     q.y = (float)Math.sqrt(q.y);
/* 137 */     q.z = (float)Math.sqrt(q.z);
/* 138 */     if (q.w >= q.x && q.w >= q.y && q.w >= q.z) {
/* 139 */       q.w *= 1.0F;
/* 140 */       q.x *= SIGN(m.m21 - m.m12);
/* 141 */       q.y *= SIGN(m.m02 - m.m20);
/* 142 */       q.z *= SIGN(m.m10 - m.m01);
/* 143 */     } else if (q.x >= q.w && q.x >= q.y && q.x >= q.z) {
/* 144 */       q.w *= SIGN(m.m21 - m.m12);
/* 145 */       q.x *= 1.0F;
/* 146 */       q.y *= SIGN(m.m10 + m.m01);
/* 147 */       q.z *= SIGN(m.m02 + m.m20);
/* 148 */     } else if (q.y >= q.w && q.y >= q.x && q.y >= q.z) {
/* 149 */       q.w *= SIGN(m.m02 - m.m20);
/* 150 */       q.x *= SIGN(m.m10 + m.m01);
/* 151 */       q.y *= 1.0F;
/* 152 */       q.z *= SIGN(m.m21 + m.m12);
/*     */     } else {
/*     */       
/* 155 */       q.w *= SIGN(m.m10 - m.m01);
/* 156 */       q.x *= SIGN(m.m20 + m.m02);
/* 157 */       q.y *= SIGN(m.m21 + m.m12);
/* 158 */       q.z *= 1.0F;
/*     */     } 
/*     */     
/* 161 */     correctQuat(q);
/* 162 */     float r = NORM(q.w, q.x, q.y, q.z);
/* 163 */     q.w /= r;
/* 164 */     q.x /= r;
/* 165 */     q.y /= r;
/* 166 */     q.z /= r;
/* 167 */     correctQuat(q);
/* 168 */     return true;
/*     */   }
/*     */   
/*     */   public static void correctQuat(FQuat q) {
/* 172 */     if (Float.isNaN(q.w) || Float.isInfinite(q.w)) {
/* 173 */       q.w = 0.0F;
/*     */     }
/*     */     
/* 176 */     if (Float.isNaN(q.x) || Float.isInfinite(q.x)) {
/* 177 */       q.x = 0.0F;
/*     */     }
/*     */     
/* 180 */     if (Float.isNaN(q.y) || Float.isInfinite(q.y)) {
/* 181 */       q.y = 0.0F;
/*     */     }
/*     */     
/* 184 */     if (Float.isNaN(q.z) || Float.isInfinite(q.z)) {
/* 185 */       q.z = 0.0F;
/*     */     }
/*     */   }
/*     */   
/*     */   public static FQuat motionTest(int x, int y, FQuat prevQtn) {
/* 190 */     FVector3D axis = newVec3D();
/* 191 */     FQuat dqtn = newQuat();
/* 192 */     axis.x = 6.2831855F * y / 200.0F;
/* 193 */     axis.y = 6.2831855F * x / 200.0F;
/* 194 */     axis.z = 0.0F;
/* 195 */     float rot = VecNormalize(axis);
/* 196 */     QuatRotation(dqtn, rot, axis.x, axis.y, axis.z);
/* 197 */     return QuatMult(dqtn, prevQtn);
/*     */   }
/*     */   
/*     */   public static float Sin(float rad) {
/* 201 */     return (float)Math.sin(rad);
/*     */   }
/*     */   
/*     */   public static float Cos(float rad) {
/* 205 */     return (float)Math.cos(rad);
/*     */   }
/*     */   
/*     */   public static float Tan(float rad) {
/* 209 */     return (float)Math.tan(rad);
/*     */   }
/*     */   
/*     */   public static float Floor(float x) {
/* 213 */     return (float)Math.floor(x);
/*     */   }
/*     */   
/*     */   public static float Atan(float x) {
/* 217 */     return (float)Math.atan(x);
/*     */   }
/*     */   
/*     */   public static float Atan2(float y, float x) {
/* 221 */     return (float)Math.atan2(y, x);
/*     */   }
/*     */   
/*     */   public static float Fabs(float x) {
/* 225 */     return (x >= 0.0F) ? x : -x;
/*     */   }
/*     */   
/*     */   public static float InvSqrt(float x) {
/* 229 */     return 1.0F / (float)Math.sqrt(x);
/*     */   }
/*     */   
/*     */   public static float Pow(float a, float b) {
/* 233 */     return (float)Math.pow(a, b);
/*     */   }
/*     */   
/*     */   public static float VecNormalize(FVector3D lpV) {
/* 237 */     float len2 = lpV.x * lpV.x + lpV.y * lpV.y + lpV.z * lpV.z;
/* 238 */     float length = (float)Math.sqrt(len2);
/* 239 */     if (length == 0.0F) {
/* 240 */       return 0.0F;
/*     */     }
/* 242 */     float invLength = 1.0F / length;
/* 243 */     lpV.x *= invLength;
/* 244 */     lpV.y *= invLength;
/* 245 */     lpV.z *= invLength;
/* 246 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float Vec2DNormalize(FVector2D lpV) {
/* 251 */     float len2 = lpV.x * lpV.x + lpV.y * lpV.y;
/* 252 */     float length = (float)Math.sqrt(len2);
/* 253 */     if (length == 0.0F) {
/* 254 */       return 0.0F;
/*     */     }
/* 256 */     float invLength = 1.0F / length;
/* 257 */     lpV.x *= invLength;
/* 258 */     lpV.y *= invLength;
/* 259 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FVector3D MatVector(FMatrix lpM, FVector3D lpV) {
/* 264 */     FVector3D lpS = newVec3D();
/* 265 */     float x = lpV.x;
/* 266 */     float y = lpV.y;
/* 267 */     float z = lpV.z;
/* 268 */     lpS.x = lpM.m00 * x + lpM.m01 * y + lpM.m02 * z + lpM.m03;
/* 269 */     lpS.y = lpM.m10 * x + lpM.m11 * y + lpM.m12 * z + lpM.m13;
/* 270 */     lpS.z = lpM.m20 * x + lpM.m21 * y + lpM.m22 * z + lpM.m23;
/* 271 */     return lpS;
/*     */   }
/*     */   
/*     */   public static FVector3D MatDirection(FMatrix lpM, FVector3D lpDir) {
/* 275 */     FVector3D lpSDir = newVec3D();
/* 276 */     float x = lpDir.x;
/* 277 */     float y = lpDir.y;
/* 278 */     float z = lpDir.z;
/* 279 */     lpSDir.x = lpM.m00 * x + lpM.m01 * y + lpM.m02 * z;
/* 280 */     lpSDir.y = lpM.m10 * x + lpM.m11 * y + lpM.m12 * z;
/* 281 */     lpSDir.z = lpM.m20 * x + lpM.m21 * y + lpM.m22 * z;
/* 282 */     return lpSDir;
/*     */   }
/*     */   
/*     */   public static void MatIdentity(FMatrix lpM) {
/* 286 */     lpM.m01 = lpM.m02 = lpM.m03 = lpM.m10 = lpM.m12 = lpM.m13 = lpM.m20 = lpM.m21 = lpM.m23 = lpM.m30 = lpM.m31 = lpM.m32 = 0.0F;
/* 287 */     lpM.m00 = lpM.m11 = lpM.m22 = lpM.m33 = 1.0F;
/*     */   }
/*     */   
/*     */   public static void MatCopy(FMatrix lpMa, FMatrix lpMb) {
/* 291 */     lpMa.m00 = lpMb.m00;
/* 292 */     lpMa.m10 = lpMb.m10;
/* 293 */     lpMa.m20 = lpMb.m20;
/* 294 */     lpMa.m30 = lpMb.m30;
/* 295 */     lpMa.m01 = lpMb.m01;
/* 296 */     lpMa.m11 = lpMb.m11;
/* 297 */     lpMa.m21 = lpMb.m21;
/* 298 */     lpMa.m31 = lpMb.m31;
/* 299 */     lpMa.m02 = lpMb.m02;
/* 300 */     lpMa.m12 = lpMb.m12;
/* 301 */     lpMa.m22 = lpMb.m22;
/* 302 */     lpMa.m32 = lpMb.m32;
/* 303 */     lpMa.m03 = lpMb.m03;
/* 304 */     lpMa.m13 = lpMb.m13;
/* 305 */     lpMa.m23 = lpMb.m23;
/* 306 */     lpMa.m33 = lpMb.m33;
/*     */   }
/*     */   
/*     */   public static void MatTranslate(FMatrix m, float x, float y, float z) {
/* 310 */     float m30 = m.m30;
/* 311 */     float m31 = m.m31;
/* 312 */     float m32 = m.m32;
/* 313 */     float m33 = m.m33;
/* 314 */     m.m00 += m30 * x;
/* 315 */     m.m01 += m31 * x;
/* 316 */     m.m02 += m32 * x;
/* 317 */     m.m03 += m33 * x;
/* 318 */     m.m10 += m30 * y;
/* 319 */     m.m11 += m31 * y;
/* 320 */     m.m12 += m32 * y;
/* 321 */     m.m13 += m33 * y;
/* 322 */     m.m20 += m30 * z;
/* 323 */     m.m21 += m31 * z;
/* 324 */     m.m22 += m32 * z;
/* 325 */     m.m23 += m33 * z;
/*     */   }
/*     */   
/*     */   public static void MatMove(FMatrix m, float x, float y, float z) {
/* 329 */     m.m03 += m.m00 * x + m.m01 * y + m.m02 * z;
/* 330 */     m.m13 += m.m10 * x + m.m11 * y + m.m12 * z;
/* 331 */     m.m23 += m.m20 * x + m.m21 * y + m.m22 * z;
/* 332 */     m.m33 += m.m30 * x + m.m31 * y + m.m32 * z;
/*     */   }
/*     */   
/*     */   public static void MatRotateX(FMatrix m, float rad) {
/* 336 */     if (rad > 6.2831855F || rad < -6.2831855F) {
/* 337 */       rad -= 6.2831855F * (int)(rad / 6.2831855F);
/*     */     }
/*     */     
/* 340 */     float cosA = Cos(rad);
/* 341 */     float sinA = Sin(rad);
/* 342 */     float tmp1 = m.m10;
/* 343 */     float tmp2 = m.m20;
/* 344 */     m.m10 = cosA * tmp1 - sinA * tmp2;
/* 345 */     m.m20 = sinA * tmp1 + cosA * tmp2;
/* 346 */     tmp1 = m.m11;
/* 347 */     tmp2 = m.m21;
/* 348 */     m.m11 = cosA * tmp1 - sinA * tmp2;
/* 349 */     m.m21 = sinA * tmp1 + cosA * tmp2;
/* 350 */     tmp1 = m.m12;
/* 351 */     tmp2 = m.m22;
/* 352 */     m.m12 = cosA * tmp1 - sinA * tmp2;
/* 353 */     m.m22 = sinA * tmp1 + cosA * tmp2;
/* 354 */     tmp1 = m.m13;
/* 355 */     tmp2 = m.m23;
/* 356 */     m.m13 = cosA * tmp1 - sinA * tmp2;
/* 357 */     m.m23 = sinA * tmp1 + cosA * tmp2;
/*     */   }
/*     */   
/*     */   public static void MatRotateY(FMatrix m, float rad) {
/* 361 */     if (rad > 6.2831855F || rad < -6.2831855F) {
/* 362 */       rad -= 6.2831855F * (int)(rad / 6.2831855F);
/*     */     }
/*     */     
/* 365 */     float cosA = Cos(rad);
/* 366 */     float sinA = Sin(rad);
/* 367 */     float tmp1 = m.m00;
/* 368 */     float tmp2 = m.m20;
/* 369 */     m.m00 = cosA * tmp1 + sinA * tmp2;
/* 370 */     m.m20 = -sinA * tmp1 + cosA * tmp2;
/* 371 */     tmp1 = m.m01;
/* 372 */     tmp2 = m.m21;
/* 373 */     m.m01 = cosA * tmp1 + sinA * tmp2;
/* 374 */     m.m21 = -sinA * tmp1 + cosA * tmp2;
/* 375 */     tmp1 = m.m02;
/* 376 */     tmp2 = m.m22;
/* 377 */     m.m02 = cosA * tmp1 + sinA * tmp2;
/* 378 */     m.m22 = -sinA * tmp1 + cosA * tmp2;
/* 379 */     tmp1 = m.m03;
/* 380 */     tmp2 = m.m23;
/* 381 */     m.m03 = cosA * tmp1 + sinA * tmp2;
/* 382 */     m.m23 = -sinA * tmp1 + cosA * tmp2;
/*     */   }
/*     */   
/*     */   public static void MatRotateZ(FMatrix m, float rad) {
/* 386 */     if (rad > 6.2831855F || rad < -6.2831855F) {
/* 387 */       rad -= 6.2831855F * (int)(rad / 6.2831855F);
/*     */     }
/*     */     
/* 390 */     float cosA = Cos(rad);
/* 391 */     float sinA = Sin(rad);
/* 392 */     float tmp1 = m.m00;
/* 393 */     float tmp2 = m.m10;
/* 394 */     m.m00 = cosA * tmp1 - sinA * tmp2;
/* 395 */     m.m10 = sinA * tmp1 + cosA * tmp2;
/* 396 */     tmp1 = m.m01;
/* 397 */     tmp2 = m.m11;
/* 398 */     m.m01 = cosA * tmp1 - sinA * tmp2;
/* 399 */     m.m11 = sinA * tmp1 + cosA * tmp2;
/* 400 */     tmp1 = m.m02;
/* 401 */     tmp2 = m.m12;
/* 402 */     m.m02 = cosA * tmp1 - sinA * tmp2;
/* 403 */     m.m12 = sinA * tmp1 + cosA * tmp2;
/* 404 */     tmp1 = m.m03;
/* 405 */     tmp2 = m.m13;
/* 406 */     m.m03 = cosA * tmp1 - sinA * tmp2;
/* 407 */     m.m13 = sinA * tmp1 + cosA * tmp2;
/*     */   }
/*     */   
/*     */   public static void MatTurnX(FMatrix m, float rad) {
/* 411 */     if (rad > 6.2831855F || rad < -6.2831855F) {
/* 412 */       rad -= 6.2831855F * (int)(rad / 6.2831855F);
/*     */     }
/*     */     
/* 415 */     float cosA = Cos(rad);
/* 416 */     float sinA = Sin(rad);
/* 417 */     float tmp1 = m.m01;
/* 418 */     float tmp2 = m.m02;
/* 419 */     m.m01 = cosA * tmp1 + sinA * tmp2;
/* 420 */     m.m02 = -sinA * tmp1 + cosA * tmp2;
/* 421 */     tmp1 = m.m11;
/* 422 */     tmp2 = m.m12;
/* 423 */     m.m11 = cosA * tmp1 + sinA * tmp2;
/* 424 */     m.m12 = -sinA * tmp1 + cosA * tmp2;
/* 425 */     tmp1 = m.m21;
/* 426 */     tmp2 = m.m22;
/* 427 */     m.m21 = cosA * tmp1 + sinA * tmp2;
/* 428 */     m.m22 = -sinA * tmp1 + cosA * tmp2;
/* 429 */     tmp1 = m.m31;
/* 430 */     tmp2 = m.m32;
/* 431 */     m.m31 = cosA * tmp1 + sinA * tmp2;
/* 432 */     m.m32 = -sinA * tmp1 + cosA * tmp2;
/*     */   }
/*     */   
/*     */   public static void MatTurnY(FMatrix m, float rad) {
/* 436 */     if (rad > 6.2831855F || rad < -6.2831855F) {
/* 437 */       rad -= 6.2831855F * (int)(rad / 6.2831855F);
/*     */     }
/*     */     
/* 440 */     float cosA = Cos(rad);
/* 441 */     float sinA = Sin(rad);
/* 442 */     float tmp1 = m.m00;
/* 443 */     float tmp2 = m.m02;
/* 444 */     m.m00 = cosA * tmp1 - sinA * tmp2;
/* 445 */     m.m02 = sinA * tmp1 + cosA * tmp2;
/* 446 */     tmp1 = m.m10;
/* 447 */     tmp2 = m.m12;
/* 448 */     m.m10 = cosA * tmp1 - sinA * tmp2;
/* 449 */     m.m12 = sinA * tmp1 + cosA * tmp2;
/* 450 */     tmp1 = m.m20;
/* 451 */     tmp2 = m.m22;
/* 452 */     m.m20 = cosA * tmp1 - sinA * tmp2;
/* 453 */     m.m22 = sinA * tmp1 + cosA * tmp2;
/* 454 */     tmp1 = m.m30;
/* 455 */     tmp2 = m.m32;
/* 456 */     m.m30 = cosA * tmp1 - sinA * tmp2;
/* 457 */     m.m32 = sinA * tmp1 + cosA * tmp2;
/*     */   }
/*     */   
/*     */   public static void MatTurnZ(FMatrix m, float rad) {
/* 461 */     if (rad > 6.2831855F || rad < -6.2831855F) {
/* 462 */       rad -= 6.2831855F * (int)(rad / 6.2831855F);
/*     */     }
/*     */     
/* 465 */     float cosA = Cos(rad);
/* 466 */     float sinA = Sin(rad);
/* 467 */     float tmp1 = m.m00;
/* 468 */     float tmp2 = m.m01;
/* 469 */     m.m00 = cosA * tmp1 + sinA * tmp2;
/* 470 */     m.m01 = -sinA * tmp1 + cosA * tmp2;
/* 471 */     tmp1 = m.m10;
/* 472 */     tmp2 = m.m11;
/* 473 */     m.m10 = cosA * tmp1 + sinA * tmp2;
/* 474 */     m.m11 = -sinA * tmp1 + cosA * tmp2;
/* 475 */     tmp1 = m.m20;
/* 476 */     tmp2 = m.m21;
/* 477 */     m.m20 = cosA * tmp1 + sinA * tmp2;
/* 478 */     m.m21 = -sinA * tmp1 + cosA * tmp2;
/* 479 */     tmp1 = m.m30;
/* 480 */     tmp2 = m.m31;
/* 481 */     m.m30 = cosA * tmp1 + sinA * tmp2;
/* 482 */     m.m31 = -sinA * tmp1 + cosA * tmp2;
/*     */   }
/*     */   
/*     */   public static void MatScale(FMatrix lpM, float scalex, float scaley, float scalez) {
/* 486 */     lpM.m00 = scalex * lpM.m00;
/* 487 */     lpM.m01 = scalex * lpM.m01;
/* 488 */     lpM.m02 = scalex * lpM.m02;
/* 489 */     lpM.m03 = scalex * lpM.m03;
/* 490 */     lpM.m10 = scaley * lpM.m10;
/* 491 */     lpM.m11 = scaley * lpM.m11;
/* 492 */     lpM.m12 = scaley * lpM.m12;
/* 493 */     lpM.m13 = scaley * lpM.m13;
/* 494 */     lpM.m20 = scalez * lpM.m20;
/* 495 */     lpM.m21 = scalez * lpM.m21;
/* 496 */     lpM.m22 = scalez * lpM.m22;
/* 497 */     lpM.m23 = scalez * lpM.m23;
/*     */   }
/*     */   
/*     */   public static void MatSize(FMatrix lpM, float scalex, float scaley, float scalez) {
/* 501 */     lpM.m00 = scalex * lpM.m00;
/* 502 */     lpM.m01 = scaley * lpM.m01;
/* 503 */     lpM.m02 = scalez * lpM.m02;
/* 504 */     lpM.m10 = scalex * lpM.m10;
/* 505 */     lpM.m11 = scaley * lpM.m11;
/* 506 */     lpM.m12 = scalez * lpM.m12;
/* 507 */     lpM.m20 = scalex * lpM.m20;
/* 508 */     lpM.m21 = scaley * lpM.m21;
/* 509 */     lpM.m22 = scalez * lpM.m22;
/* 510 */     lpM.m30 = scalex * lpM.m30;
/* 511 */     lpM.m31 = scaley * lpM.m31;
/* 512 */     lpM.m32 = scalez * lpM.m32;
/*     */   }
/*     */   
/*     */   public static FQuat QuatMult(FQuat lpP, FQuat lpQ) {
/* 516 */     FQuat lpR = newQuat();
/* 517 */     float pw = lpP.w;
/* 518 */     float px = lpP.x;
/* 519 */     float py = lpP.y;
/* 520 */     float pz = lpP.z;
/* 521 */     float qw = lpQ.w;
/* 522 */     float qx = lpQ.x;
/* 523 */     float qy = lpQ.y;
/* 524 */     float qz = lpQ.z;
/* 525 */     lpR.w = pw * qw - px * qx - py * qy - pz * qz;
/* 526 */     lpR.x = pw * qx + px * qw + py * qz - pz * qy;
/* 527 */     lpR.y = pw * qy - px * qz + py * qw + pz * qx;
/* 528 */     lpR.z = pw * qz + px * qy - py * qx + pz * qw;
/* 529 */     return lpR;
/*     */   }
/*     */   
/*     */   public static void QuatAdd(FQuat q_out, FQuat q) {
/* 533 */     q_out.w += q.w;
/* 534 */     q_out.x += q.x;
/* 535 */     q_out.y += q.y;
/* 536 */     q_out.z += q.z;
/*     */   }
/*     */   
/*     */   public static FMatrix QuatToMatrix(FQuat lpQ) {
/* 540 */     FMatrix lpM = newMatrix();
/* 541 */     float qw = lpQ.w;
/* 542 */     float qx = lpQ.x;
/* 543 */     float qy = lpQ.y;
/* 544 */     float qz = lpQ.z;
/* 545 */     float x2 = 2.0F * qx * qx;
/* 546 */     float y2 = 2.0F * qy * qy;
/* 547 */     float z2 = 2.0F * qz * qz;
/* 548 */     float xy = 2.0F * qx * qy;
/* 549 */     float yz = 2.0F * qy * qz;
/* 550 */     float zx = 2.0F * qz * qx;
/* 551 */     float wx = 2.0F * qw * qx;
/* 552 */     float wy = 2.0F * qw * qy;
/* 553 */     float wz = 2.0F * qw * qz;
/* 554 */     lpM.m00 = 1.0F - y2 - z2;
/* 555 */     lpM.m01 = xy - wz;
/* 556 */     lpM.m02 = zx + wy;
/* 557 */     lpM.m03 = 0.0F;
/* 558 */     lpM.m10 = xy + wz;
/* 559 */     lpM.m11 = 1.0F - z2 - x2;
/* 560 */     lpM.m12 = yz - wx;
/* 561 */     lpM.m13 = 0.0F;
/* 562 */     lpM.m20 = zx - wy;
/* 563 */     lpM.m21 = yz + wx;
/* 564 */     lpM.m22 = 1.0F - x2 - y2;
/* 565 */     lpM.m23 = 0.0F;
/* 566 */     lpM.m30 = lpM.m31 = lpM.m32 = 0.0F;
/* 567 */     lpM.m33 = 1.0F;
/* 568 */     return lpM;
/*     */   }
/*     */   
/*     */   public static void QuatRotation(FQuat lpQ, float rad, float ax, float ay, float az) {
/* 572 */     float hrad = 0.5F * rad;
/* 573 */     float s = Sin(hrad);
/* 574 */     lpQ.w = Cos(hrad);
/* 575 */     lpQ.x = s * ax;
/* 576 */     lpQ.y = s * ay;
/* 577 */     lpQ.z = s * az;
/*     */   }
/*     */   
/*     */   public static void QuatIdentity(FQuat lpQ) {
/* 581 */     lpQ.w = 1.0F;
/* 582 */     lpQ.x = 0.0F;
/* 583 */     lpQ.y = 0.0F;
/* 584 */     lpQ.z = 0.0F;
/*     */   }
/*     */   
/*     */   public static void QuatCopy(FQuat lpTo, FQuat lpFrom) {
/* 588 */     lpTo.w = lpFrom.w;
/* 589 */     lpTo.x = lpFrom.x;
/* 590 */     lpTo.y = lpFrom.y;
/* 591 */     lpTo.z = lpFrom.z;
/*     */   }
/*     */   
/*     */   public FVector3D privateNewVec3D(float x, float y, float z) {
/* 595 */     FVector3D v = new FVector3D(this);
/* 596 */     v.x = x;
/* 597 */     v.y = y;
/* 598 */     v.z = z;
/* 599 */     return v;
/*     */   }
/*     */   
/*     */   private FQuat privateNewQuat() {
/* 603 */     FQuat q = new FQuat(this);
/* 604 */     QuatIdentity(q);
/* 605 */     return new FQuat(this);
/*     */   }
/*     */   
/*     */   private FMatrix privateNewMatrix() {
/* 609 */     FMatrix m = new FMatrix(this);
/* 610 */     MatIdentity(m);
/* 611 */     return m;
/*     */   }
/*     */   
/*     */   public static class FMatrix
/*     */   {
/*     */     float m00;
/*     */     float m10;
/*     */     float m20;
/*     */     float m30;
/*     */     float m01;
/*     */     float m11;
/*     */     float m21;
/*     */     float m31;
/*     */     float m02;
/*     */     float m12;
/*     */     float m22;
/*     */     float m32;
/*     */     float m03;
/*     */     float m13;
/*     */     float m23;
/*     */     float m33;
/*     */     
/*     */     public FMatrix(MCH_Math paramMCH_Math) {}
/*     */     
/*     */     public FloatBuffer toFloatBuffer() {
/* 636 */       ByteBuffer bb = ByteBuffer.allocateDirect(64);
/* 637 */       FloatBuffer fb = bb.asFloatBuffer();
/* 638 */       fb.put(this.m00);
/* 639 */       fb.put(this.m10);
/* 640 */       fb.put(this.m20);
/* 641 */       fb.put(this.m30);
/* 642 */       fb.put(this.m01);
/* 643 */       fb.put(this.m11);
/* 644 */       fb.put(this.m21);
/* 645 */       fb.put(this.m31);
/* 646 */       fb.put(this.m02);
/* 647 */       fb.put(this.m12);
/* 648 */       fb.put(this.m22);
/* 649 */       fb.put(this.m32);
/* 650 */       fb.put(this.m03);
/* 651 */       fb.put(this.m13);
/* 652 */       fb.put(this.m23);
/* 653 */       fb.put(this.m33);
/* 654 */       float f = fb.get(0);
/* 655 */       f = fb.get(1);
/* 656 */       fb.position(0);
/* 657 */       return fb;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FQuat {
/*     */     public float w;
/*     */     public float x;
/*     */     public float y;
/*     */     public float z;
/*     */     
/*     */     public FQuat(MCH_Math paramMCH_Math) {}
/*     */   }
/*     */   
/*     */   public static class FVector2D {
/*     */     public float x;
/*     */     public float y;
/*     */     
/*     */     public FVector2D(MCH_Math paramMCH_Math) {}
/*     */   }
/*     */   
/*     */   public static class FVector3D {
/*     */     public float x;
/*     */     public float y;
/*     */     public float z;
/*     */     
/*     */     public FVector3D(MCH_Math paramMCH_Math) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_Math.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */