/*    */ package com.norwood.mcheli.debug._v2;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.norwood.mcheli.helper.debug.DebugBootstrap;
/*    */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*    */ import com.norwood.mcheli.helper.info.IContentData;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ContentTest
/*    */ {
/*    */   public static void main(String[] args) {
/* 13 */     DebugBootstrap.init();
/* 14 */     File debugDir = new File("./run/");
/* 15 */     List<IContentData> contents = Lists.newLinkedList();
/* 16 */     System.out.println(debugDir.getAbsolutePath());
/* 17 */     ContentRegistries.loadContents(debugDir);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v2\ContentTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */