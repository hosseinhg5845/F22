/*    */ package com.norwood.mcheli.debug._v1;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class PrintStreamWrapper
/*    */ {
/*    */   private final PrintStream ps;
/*    */   
/*    */   private PrintStreamWrapper(PrintStream stream) {
/* 13 */     this.ps = stream;
/* 14 */     this.stack = new ArrayDeque<>();
/* 15 */     this.nestStr = "  ";
/*    */   }
/*    */   private final Deque<String> stack; private String nestStr;
/*    */   public static PrintStreamWrapper create(PrintStream stream) {
/* 19 */     return new PrintStreamWrapper(stream);
/*    */   }
/*    */   
/*    */   public void setNestStr(String nestStr) {
/* 23 */     this.nestStr = nestStr;
/*    */   }
/*    */   
/*    */   public PrintStreamWrapper push(String label) {
/* 27 */     println(label);
/* 28 */     return push();
/*    */   }
/*    */   
/*    */   public PrintStreamWrapper push() {
/* 32 */     this.stack.addLast(this.nestStr);
/* 33 */     return this;
/*    */   }
/*    */   
/*    */   public PrintStreamWrapper pop() {
/* 37 */     this.stack.removeLast();
/* 38 */     return this;
/*    */   }
/*    */   
/*    */   private void printNest() {
/* 42 */     Objects.requireNonNull(this.ps); this.stack.forEach(this.ps::print);
/*    */   }
/*    */   
/*    */   public void println(Object o) {
/* 46 */     printNest();
/* 47 */     this.ps.println(o);
/*    */   }
/*    */   
/*    */   public void println(String s) {
/* 51 */     printNest();
/* 52 */     this.ps.println(s);
/*    */   }
/*    */   
/*    */   public void println() {
/* 56 */     printNest();
/* 57 */     this.ps.println();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v1\PrintStreamWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */