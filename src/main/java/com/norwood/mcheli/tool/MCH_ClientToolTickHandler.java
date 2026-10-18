/*     */ package com.norwood.mcheli.tool;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_ClientTickHandlerBase;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Key;
/*     */ import com.norwood.mcheli.tool.rangefinder.MCH_ItemRangeFinder;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class MCH_ClientToolTickHandler extends MCH_ClientTickHandlerBase {
/*     */   public MCH_Key KeyUseItem;
/*     */   public MCH_Key KeyZoomIn;
/*     */   public MCH_Key KeyZoomOut;
/*     */   public MCH_Key KeySwitchMode;
/*     */   public MCH_Key[] Keys;
/*     */   
/*     */   public MCH_ClientToolTickHandler(Minecraft minecraft, MCH_Config config) {
/*  21 */     super(minecraft);
/*  22 */     updateKeybind(config);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  27 */     this.KeyUseItem = new MCH_Key(MCH_Config.KeyAttack.prmInt);
/*  28 */     this.KeyZoomIn = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/*  29 */     this.KeyZoomOut = new MCH_Key(MCH_Config.KeySwWeaponMode.prmInt);
/*  30 */     this.KeySwitchMode = new MCH_Key(MCH_Config.KeyFlare.prmInt);
/*  31 */     this.Keys = new MCH_Key[] { this.KeyUseItem, this.KeyZoomIn, this.KeyZoomOut, this.KeySwitchMode };
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onTick(boolean inGUI) {
/*  36 */     for (MCH_Key k : this.Keys) {
/*  37 */       k.update();
/*     */     }
/*     */     
/*  40 */     onTick_ItemWrench(inGUI, (EntityPlayer)this.mc.player);
/*  41 */     onTick_ItemRangeFinder(inGUI, (EntityPlayer)this.mc.player);
/*     */   }
/*     */   
/*     */   private void onTick_ItemRangeFinder(boolean inGUI, EntityPlayer player) {
/*  45 */     if (MCH_ItemRangeFinder.rangeFinderUseCooldown > 0) {
/*  46 */       MCH_ItemRangeFinder.rangeFinderUseCooldown--;
/*     */     }
/*     */     
/*  49 */     ItemStack itemStack = ItemStack.EMPTY;
/*  50 */     if (player != null) {
/*  51 */       itemStack = player.getHeldItemMainhand();
/*  52 */       if (!itemStack.isEmpty() && itemStack.getItem() instanceof MCH_ItemRangeFinder) {
/*  53 */         boolean usingItem = (player.getItemInUseMaxCount() > 8 && MCH_ItemRangeFinder.canUse(player));
/*  54 */         if (!MCH_ItemRangeFinder.continueUsingItem && usingItem) {
/*  55 */           MCH_ItemRangeFinder.onStartUseItem();
/*     */         }
/*     */         
/*  58 */         if (usingItem) {
/*  59 */           if (this.KeyUseItem.isKeyDown()) {
/*  60 */             ((MCH_ItemRangeFinder)itemStack.getItem()).spotEntity(player, itemStack);
/*     */           }
/*     */           
/*  63 */           if (this.KeyZoomIn.isKeyPress() && MCH_ItemRangeFinder.zoom < 10.0F) {
/*  64 */             MCH_ItemRangeFinder.zoom += MCH_ItemRangeFinder.zoom / 10.0F;
/*  65 */             if (MCH_ItemRangeFinder.zoom > 10.0F) {
/*  66 */               MCH_ItemRangeFinder.zoom = 10.0F;
/*     */             }
/*     */             
/*  69 */             W_McClient.MOD_playSoundFX("zoom", 0.05F, 1.0F);
/*  70 */             W_Reflection.setCameraZoom(MCH_ItemRangeFinder.zoom);
/*     */           } 
/*     */           
/*  73 */           if (this.KeyZoomOut.isKeyPress() && MCH_ItemRangeFinder.zoom > 1.2F) {
/*  74 */             MCH_ItemRangeFinder.zoom -= MCH_ItemRangeFinder.zoom / 10.0F;
/*  75 */             if (MCH_ItemRangeFinder.zoom < 1.2F) {
/*  76 */               MCH_ItemRangeFinder.zoom = 1.2F;
/*     */             }
/*     */             
/*  79 */             W_McClient.MOD_playSoundFX("zoom", 0.05F, 0.9F);
/*  80 */             W_Reflection.setCameraZoom(MCH_ItemRangeFinder.zoom);
/*     */           } 
/*     */           
/*  83 */           if (this.KeySwitchMode.isKeyDown()) {
/*  84 */             W_McClient.MOD_playSoundFX("lockon", 1.0F, 0.9F);
/*  85 */             MCH_ItemRangeFinder.mode = (MCH_ItemRangeFinder.mode + 1) % 3;
/*  86 */             if (this.mc.isSingleplayer() && MCH_ItemRangeFinder.mode == 0) {
/*  87 */               MCH_ItemRangeFinder.mode = 1;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     if (MCH_ItemRangeFinder.continueUsingItem && (itemStack.isEmpty() || !(itemStack.getItem() instanceof MCH_ItemRangeFinder))) {
/*  95 */       MCH_ItemRangeFinder.onStopUseItem();
/*     */     }
/*     */   }
/*     */   
/*     */   private void onTick_ItemWrench(boolean inGUI, EntityPlayer player) {
/* 100 */     if (player != null) {
/* 101 */       ItemStack itemStack = player.getHeldItemMainhand();
/* 102 */       if (!itemStack.isEmpty() && itemStack.getItem() instanceof MCH_ItemWrench) {
/* 103 */         int maxdm = itemStack.getMaxDamage();
/* 104 */         int dm = itemStack.getMetadata();
/* 105 */         if (dm <= maxdm) {
/* 106 */           ItemStack renderItemstack = W_Reflection.getItemRendererMainHand();
/* 107 */           if (renderItemstack.isEmpty() || itemStack.getItem() == renderItemstack.getItem())
/* 108 */             W_Reflection.setItemRendererMainHand(player.inventory.getCurrentItem()); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tool\MCH_ClientToolTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */