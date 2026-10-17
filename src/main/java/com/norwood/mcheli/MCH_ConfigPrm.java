/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ public class MCH_ConfigPrm {
/*     */   public final int type;
/*     */   public final String name;
/*   6 */   public int prmInt = 0;
/*   7 */   public String prmString = "";
/*     */   public boolean prmBool = false;
/*   9 */   public double prmDouble = 0.0D;
/*  10 */   public String desc = "";
/*  11 */   public int prmIntDefault = 0;
/*  12 */   public String validVer = "";
/*     */   
/*     */   public MCH_ConfigPrm(String parameterName, int defaultParameter) {
/*  15 */     this.prmInt = defaultParameter;
/*  16 */     this.prmIntDefault = defaultParameter;
/*  17 */     this.type = 0;
/*  18 */     this.name = parameterName;
/*     */   }
/*     */   
/*     */   public MCH_ConfigPrm(String parameterName, String defaultParameter) {
/*  22 */     this.prmString = defaultParameter;
/*  23 */     this.type = 1;
/*  24 */     this.name = parameterName;
/*     */   }
/*     */   
/*     */   public MCH_ConfigPrm(String parameterName, boolean defaultParameter) {
/*  28 */     this.prmBool = defaultParameter;
/*  29 */     this.type = 2;
/*  30 */     this.name = parameterName;
/*     */   }
/*     */   
/*     */   public MCH_ConfigPrm(String parameterName, double defaultParameter) {
/*  34 */     this.prmDouble = defaultParameter;
/*  35 */     this.type = 3;
/*  36 */     this.name = parameterName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  41 */     if (this.type == 0)
/*  42 */       return String.valueOf(this.prmInt); 
/*  43 */     if (this.type == 1)
/*  44 */       return this.prmString; 
/*  45 */     if (this.type == 2) {
/*  46 */       return String.valueOf(this.prmBool);
/*     */     }
/*  48 */     return (this.type == 3) ? String.format("%.2f", new Object[] { Double.valueOf(this.prmDouble) }).replace(',', '.') : "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean compare(String s) {
/*  53 */     return this.name.equalsIgnoreCase(s);
/*     */   }
/*     */   
/*     */   public boolean isValidVer(String configVer) {
/*  57 */     if (configVer.length() >= 5 && this.validVer.length() >= 5) {
/*  58 */       String[] configVerSplit = configVer.split("\\.");
/*  59 */       String[] validVerSplit = this.validVer.split("\\.");
/*  60 */       if (configVerSplit.length == 3 && validVerSplit.length == 3) {
/*  61 */         for (int i = 0; i < 3; i++) {
/*  62 */           int n1 = Integer.parseInt(configVerSplit[i].replaceAll("[a-zA-Z-_]", "").trim());
/*  63 */           int n2 = Integer.parseInt(validVerSplit[i].replaceAll("[a-zA-Z-_]", "").trim());
/*  64 */           if (n1 > n2) {
/*  65 */             return true;
/*     */           }
/*     */           
/*  68 */           if (n1 < n2) {
/*  69 */             return false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  75 */     return true;
/*     */   }
/*     */   
/*     */   public void setPrm(int n) {
/*  79 */     if (this.type == 0) {
/*  80 */       this.prmInt = n;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPrm(String s) {
/*  85 */     if (this.type == 0) {
/*  86 */       this.prmInt = Integer.parseInt(s);
/*     */     }
/*     */     
/*  89 */     if (this.type == 1) {
/*  90 */       this.prmString = s;
/*     */     }
/*     */     
/*  93 */     if (this.type == 2) {
/*  94 */       s = s.toLowerCase();
/*  95 */       if (s.compareTo("true") == 0) {
/*  96 */         this.prmBool = true;
/*     */       }
/*     */       
/*  99 */       if (s.compareTo("false") == 0) {
/* 100 */         this.prmBool = false;
/*     */       }
/*     */     } 
/*     */     
/* 104 */     if (this.type == 3 && !s.isEmpty()) {
/* 105 */       this.prmDouble = MCH_Lib.parseDouble(s);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPrm(boolean b) {
/* 110 */     if (this.type == 2) {
/* 111 */       this.prmBool = b;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPrm(double f) {
/* 116 */     if (this.type == 3)
/* 117 */       this.prmDouble = f; 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_ConfigPrm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */