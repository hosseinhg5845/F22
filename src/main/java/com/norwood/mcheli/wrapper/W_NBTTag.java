/*    */ package com.norwood.mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagIntArray;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ 
/*    */ public class W_NBTTag {
/*    */   public static final int TAG_COMPOUND = 10;
/*    */   
/*    */   public static NBTTagCompound tagAt(NBTTagList list, int i) {
/* 11 */     return (list != null) ? list.func_150305_b(i) : null;
/*    */   }
/*    */   
/*    */   public static NBTTagList getTagList(NBTTagCompound nbt, String s, int i) {
/* 15 */     return nbt.func_150295_c(s, i);
/*    */   }
/*    */   
/*    */   public static NBTTagIntArray newTagIntArray(String s, int[] n) {
/* 19 */     return new NBTTagIntArray(n);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_NBTTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */