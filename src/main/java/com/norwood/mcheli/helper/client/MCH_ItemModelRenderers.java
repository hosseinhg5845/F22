/*    */ package com.norwood.mcheli.helper.client;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.norwood.mcheli.MCH_MOD;
/*    */ import com.norwood.mcheli.helper.client.model.MCH_BakedModel;
/*    */ import com.norwood.mcheli.helper.client.renderer.item.CustomItemStackRenderer;
/*    */ import com.norwood.mcheli.helper.client.renderer.item.IItemModelRenderer;
/*    */ import com.norwood.mcheli.helper.info.ContentRegistries;
/*    */ import com.norwood.mcheli.helper.info.IItemContent;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraftforge.client.event.ModelBakeEvent;
/*    */ import net.minecraftforge.client.event.ModelRegistryEvent;
/*    */ import net.minecraftforge.client.model.ModelLoader;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(modid = "mcheli", value = {Side.CLIENT})
/*    */ public class MCH_ItemModelRenderers
/*    */ {
/* 29 */   private static final Map<ModelResourceLocation, IItemModelRenderer> renderers = Maps.newHashMap();
/*    */   
/*    */   @SubscribeEvent
/*    */   static void onModelRegistryEvent(ModelRegistryEvent event) {
/* 33 */     registerModelLocation(Item.getItemFromBlock((Block)MCH_MOD.blockDraftingTable));
/* 34 */     ModelLoader.setCustomStateMapper((Block)MCH_MOD.blockDraftingTable, blockIn -> Maps.newHashMap());
/* 35 */     ModelLoader.setCustomStateMapper((Block)MCH_MOD.blockDraftingTableLit, blockIn -> Maps.newHashMap());
/* 36 */     registerModelLocation((Item)MCH_MOD.itemSpawnGunnerVsMonster);
/* 37 */     registerModelLocation((Item)MCH_MOD.itemSpawnGunnerVsPlayer);
/* 38 */     registerModelLocation((Item)MCH_MOD.itemRangeFinder);
/* 39 */     registerModelLocation((Item)MCH_MOD.itemWrench);
/* 40 */     registerModelLocation((Item)MCH_MOD.itemFuel);
/* 41 */     registerModelLocation((Item)MCH_MOD.itemGLTD);
/* 42 */     registerModelLocation((Item)MCH_MOD.itemChain);
/* 43 */     registerModelLocation((Item)MCH_MOD.itemParachute);
/* 44 */     registerModelLocation((Item)MCH_MOD.itemContainer);
/*    */     
/* 46 */     for (int i = 0; i < MCH_MOD.itemUavStation.length; i++) {
/* 47 */       registerModelLocation((Item)MCH_MOD.itemUavStation[i]);
/*    */     }
/*    */     
/* 50 */     registerModelLocation((Item)MCH_MOD.invisibleItem);
/* 51 */     registerModelLocation((Item)MCH_MOD.itemStingerBullet);
/* 52 */     registerModelLocation((Item)MCH_MOD.itemJavelinBullet);
/* 53 */     registerModelLocation((Item)MCH_MOD.itemStinger);
/* 54 */     registerModelLocation((Item)MCH_MOD.itemJavelin);
/* 55 */     ContentRegistries.heli().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
/* 56 */     ContentRegistries.plane().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
/* 57 */     ContentRegistries.tank().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
/* 58 */     ContentRegistries.vehicle().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
/* 59 */     ContentRegistries.throwable().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   static void onBakedModelEvent(ModelBakeEvent event) {
/* 64 */     for (Map.Entry<ModelResourceLocation, IItemModelRenderer> entry : renderers.entrySet()) {
/* 65 */       IBakedModel bakedmodel = (IBakedModel)event.getModelRegistry().getObject(entry.getKey());
/* 66 */       if (bakedmodel != null) {
/* 67 */         event.getModelRegistry().putObject(entry.getKey(), new MCH_BakedModel(bakedmodel, entry.getValue()));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void registerRenderer(Item item, IItemModelRenderer renderer) {
/* 73 */     item.setTileEntityItemStackRenderer((TileEntityItemStackRenderer)CustomItemStackRenderer.getInstance());
/* 74 */     renderers.put(getInventoryModel(item), renderer);
/*    */   }
/*    */   
/*    */   public static void registerModelLocation(Item item) {
/* 78 */     registerModelLocation(item, 0);
/*    */   }
/*    */   
/*    */   public static void registerModelLocation(Item item, int meta) {
/* 82 */     ModelLoader.setCustomModelResourceLocation(item, meta, getInventoryModel(item));
/*    */   }
/*    */   
/*    */   public static void registerLegacyModelLocation(IItemContent content) {
/* 86 */     ModelLoader.setCustomModelResourceLocation(content.getItem(), 0, new ModelResourceLocation(content.getItem().getRegistryName(), "mcheli_legacy"));
/*    */   }
/*    */   
/*    */   private static ModelResourceLocation getInventoryModel(Item item) {
/* 90 */     return new ModelResourceLocation(item.getRegistryName(), "inventory");
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static IItemModelRenderer getRenderer(Item item) {
/* 95 */     return renderers.get(getInventoryModel(item));
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\MCH_ItemModelRenderers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */