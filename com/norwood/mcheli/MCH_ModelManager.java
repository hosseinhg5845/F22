/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import com.norwood.mcheli.helper.client.MCH_Models;
/*     */ import com.norwood.mcheli.helper.client._IModelCustom;
/*     */ import com.norwood.mcheli.wrapper.W_ModelBase;
/*     */ import com.norwood.mcheli.wrapper.modelloader.W_ModelCustom;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_ModelManager extends W_ModelBase {
/*  17 */   private static final MCH_ModelManager instance = new MCH_ModelManager();
/*  18 */   private static final HashMap<String, _IModelCustom> map = new HashMap<>();
/*     */   private static final ModelRenderer defaultModel;
/*     */   private static boolean forceReloadMode = false;
/*  21 */   private static final Random rand = new Random();
/*     */   
/*     */   static {
/*  24 */     defaultModel = new ModelRenderer((ModelBase)instance, 0, 0);
/*  25 */     defaultModel.func_78790_a(-5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setForceReloadMode(boolean b) {
/*  32 */     forceReloadMode = b;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static _IModelCustom load(String path, String name) {
/*  37 */     return (name != null && !name.isEmpty()) ? load(path + "/" + name) : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static _IModelCustom load(String name) {
/*  42 */     if (name != null && !name.isEmpty()) {
/*  43 */       _IModelCustom model, obj = map.get(name);
/*  44 */       if (obj != null) {
/*  45 */         if (!forceReloadMode) {
/*  46 */           return obj;
/*     */         }
/*     */         
/*  49 */         map.remove(name);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  55 */         model = MCH_Models.loadModel(name);
/*  56 */       } catch (Exception var4) {
/*  57 */         var4.printStackTrace();
/*  58 */         model = null;
/*     */       } 
/*     */       
/*  61 */       if (model != null) {
/*  62 */         map.put(name, model);
/*  63 */         return model;
/*     */       } 
/*  65 */       return null;
/*     */     } 
/*     */     
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void render(String path, String name) {
/*  73 */     render(path + "/" + name);
/*     */   }
/*     */   
/*     */   public static void render(String name) {
/*  77 */     _IModelCustom model = map.get(name);
/*  78 */     if (model != null) {
/*  79 */       model.renderAll();
/*  80 */     } else if (defaultModel == null) {
/*     */     
/*     */     } 
/*     */   }
/*     */   public static void renderPart(String name, String partName) {
/*  85 */     _IModelCustom model = map.get(name);
/*  86 */     if (model != null) {
/*  87 */       model.renderPart(partName);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void renderLine(String path, String name, int startLine, int maxLine) {
/*  92 */     _IModelCustom model = map.get(path + "/" + name);
/*  93 */     if (model instanceof W_ModelCustom) {
/*  94 */       ((W_ModelCustom)model).renderAllLine(startLine, maxLine);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void render(String path, String name, int startFace, int maxFace) {
/*  99 */     _IModelCustom model = map.get(path + "/" + name);
/* 100 */     if (model instanceof W_ModelCustom) {
/* 101 */       ((W_ModelCustom)model).renderAll(startFace, maxFace);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getVertexNum(String path, String name) {
/* 106 */     _IModelCustom model = map.get(path + "/" + name);
/* 107 */     return (model instanceof W_ModelCustom) ? ((W_ModelCustom)model).getVertexNum() : 0;
/*     */   }
/*     */   
/*     */   public static W_ModelCustom get(String path, String name) {
/* 111 */     _IModelCustom model = map.get(path + "/" + name);
/* 112 */     return (model instanceof W_ModelCustom) ? (W_ModelCustom)model : null;
/*     */   }
/*     */   
/*     */   public static W_ModelCustom getRandome() {
/* 116 */     int size = map.size();
/*     */     
/* 118 */     for (int i = 0; i < 10; i++) {
/* 119 */       int idx = 0;
/* 120 */       int index = rand.nextInt(size);
/*     */       
/* 122 */       for (_IModelCustom model : map.values()) {
/* 123 */         if (idx >= index && model instanceof W_ModelCustom) {
/* 124 */           return (W_ModelCustom)model;
/*     */         }
/*     */         
/* 127 */         idx++;
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean containsModel(String path, String name) {
/* 135 */     return containsModel(path + "/" + name);
/*     */   }
/*     */   
/*     */   public static boolean containsModel(String name) {
/* 139 */     return map.containsKey(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_ModelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */