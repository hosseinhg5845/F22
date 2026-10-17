/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MCH_Queue<T> {
/*    */   private int current;
/*    */   private final List<T> list;
/*    */   
/*    */   public MCH_Queue(int filterLength, T initVal) {
/* 11 */     if (filterLength <= 0) {
/* 12 */       filterLength = 1;
/*    */     }
/*    */     
/* 15 */     this.list = new ArrayList<>();
/*    */     
/* 17 */     for (int i = 0; i < filterLength; i++) {
/* 18 */       this.list.add(initVal);
/*    */     }
/*    */     
/* 21 */     this.current = 0;
/*    */   }
/*    */   
/*    */   public void clear(T clearVal) {
/* 25 */     for (int i = 0; i < size(); i++) {
/* 26 */       this.list.set(i, clearVal);
/*    */     }
/*    */   }
/*    */   
/*    */   public void put(T t) {
/* 31 */     this.list.set(this.current, t);
/* 32 */     this.current++;
/* 33 */     this.current %= size();
/*    */   }
/*    */   
/*    */   private int getIndex(int offset) {
/* 37 */     offset %= size();
/* 38 */     int index = this.current + offset;
/* 39 */     return (index < 0) ? (index + size()) : (index % size());
/*    */   }
/*    */   
/*    */   public T oldest() {
/* 43 */     return this.list.get(getIndex(1));
/*    */   }
/*    */   
/*    */   public T get(int i) {
/* 47 */     return this.list.get(i);
/*    */   }
/*    */   
/*    */   public int size() {
/* 51 */     return this.list.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_Queue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */