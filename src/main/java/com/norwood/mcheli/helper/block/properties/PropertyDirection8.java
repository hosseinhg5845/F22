/*    */ package com.norwood.mcheli.helper.block.properties;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Collections2;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.norwood.mcheli.helper.block.EnumDirection8;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.block.properties.PropertyEnum;
/*    */ 
/*    */ public class PropertyDirection8
/*    */   extends PropertyEnum<EnumDirection8> {
/*    */   protected PropertyDirection8(String name, Collection<EnumDirection8> allowedValues) {
/* 14 */     super(name, EnumDirection8.class, allowedValues);
/*    */   }
/*    */   
/*    */   public static PropertyDirection8 create(String name) {
/* 18 */     return create(name, Predicates.alwaysTrue());
/*    */   }
/*    */   
/*    */   public static PropertyDirection8 create(String name, Predicate<EnumDirection8> filter) {
/* 22 */     return create(name, Collections2.filter(Lists.newArrayList((Object[])EnumDirection8.values()), filter));
/*    */   }
/*    */   
/*    */   public static PropertyDirection8 create(String name, Collection<EnumDirection8> allowedValues) {
/* 26 */     return new PropertyDirection8(name, allowedValues);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\block\properties\PropertyDirection8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */