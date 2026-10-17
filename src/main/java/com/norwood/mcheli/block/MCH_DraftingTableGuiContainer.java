/*     */ package com.norwood.mcheli.block;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_IRecipeList;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.helper.MCH_Recipes;
/*     */ import com.norwood.mcheli.wrapper.W_Block;
/*     */ import com.norwood.mcheli.wrapper.W_EntityPlayer;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryCraftResult;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_DraftingTableGuiContainer
/*     */   extends Container {
/*     */   public final EntityPlayer player;
/*     */   public final int posX;
/*     */   public final int posY;
/*     */   public final int posZ;
/*     */   public final int outputSlotIndex;
/*  28 */   private final IInventory outputSlot = (IInventory)new InventoryCraftResult();
/*     */   
/*     */   public MCH_DraftingTableGuiContainer(EntityPlayer player, int posX, int posY, int posZ) {
/*  31 */     this.player = player;
/*  32 */     this.posX = posX;
/*  33 */     this.posY = posY;
/*  34 */     this.posZ = posZ;
/*     */     
/*  36 */     for (int y = 0; y < 3; y++) {
/*  37 */       for (int i = 0; i < 9; i++) {
/*  38 */         func_75146_a(new Slot((IInventory)player.field_71071_by, 9 + i + y * 9, 30 + i * 18, 140 + y * 18));
/*     */       }
/*     */     } 
/*     */     
/*  42 */     for (int x = 0; x < 9; x++) {
/*  43 */       func_75146_a(new Slot((IInventory)player.field_71071_by, x, 30 + x * 18, 198));
/*     */     }
/*     */     
/*  46 */     this.outputSlotIndex = this.field_75153_a.size();
/*  47 */     Slot a = new Slot(this.outputSlot, this.outputSlotIndex, 178, 90) {
/*     */         public boolean func_75214_a(@NotNull ItemStack stack) {
/*  49 */           return false;
/*     */         }
/*     */       };
/*  52 */     func_75146_a(a);
/*  53 */     MCH_Lib.DbgLog(player.field_70170_p, "MCH_DraftingTableGuiContainer.MCH_DraftingTableGuiContainer", new Object[0]);
/*     */   }
/*     */   
/*     */   public void func_75142_b() {
/*  57 */     super.func_75142_b();
/*     */   }
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer player) {
/*  61 */     Block block = W_WorldFunc.getBlock(player.field_70170_p, this.posX, this.posY, this.posZ);
/*  62 */     return ((W_Block.isEqual(block, (Block)MCH_MOD.blockDraftingTable) || W_Block.isEqual(block, (Block)MCH_MOD.blockDraftingTableLit)) && player.func_70092_e(this.posX, this.posY, this.posZ) <= 144.0D);
/*     */   }
/*     */   @NotNull
/*     */   public ItemStack func_82846_b(@NotNull EntityPlayer player, int slotIndex) {
/*  66 */     ItemStack itemstack = ItemStack.field_190927_a;
/*  67 */     Slot slot = this.field_75151_b.get(slotIndex);
/*  68 */     if (slot != null && slot.func_75216_d()) {
/*  69 */       ItemStack itemstack1 = slot.func_75211_c();
/*  70 */       itemstack = itemstack1.func_77946_l();
/*  71 */       if (slotIndex != this.outputSlotIndex) {
/*  72 */         return ItemStack.field_190927_a;
/*     */       }
/*     */       
/*  75 */       if (!func_75135_a(itemstack1, 0, 36, true)) {
/*  76 */         return ItemStack.field_190927_a;
/*     */       }
/*     */       
/*  79 */       slot.func_75220_a(itemstack1, itemstack);
/*  80 */       if (itemstack1.func_190916_E() == 0) {
/*  81 */         slot.func_75215_d(ItemStack.field_190927_a);
/*     */       } else {
/*  83 */         slot.func_75218_e();
/*     */       } 
/*     */       
/*  86 */       if (itemstack1.func_190916_E() == itemstack.func_190916_E()) {
/*  87 */         return ItemStack.field_190927_a;
/*     */       }
/*     */       
/*  90 */       slot.func_190901_a(player, itemstack1);
/*     */     } 
/*     */     
/*  93 */     return itemstack;
/*     */   }
/*     */   
/*     */   public void func_75134_a(@NotNull EntityPlayer player) {
/*  97 */     super.func_75134_a(player);
/*  98 */     if (!player.field_70170_p.field_72995_K) {
/*  99 */       ItemStack itemstack = func_75139_a(this.outputSlotIndex).func_75211_c();
/* 100 */       if (!itemstack.func_190926_b()) {
/* 101 */         W_EntityPlayer.dropPlayerItemWithRandomChoice(player, itemstack, false, false);
/*     */       }
/*     */     } 
/*     */     
/* 105 */     MCH_Lib.DbgLog(player.field_70170_p, "MCH_DraftingTableGuiContainer.onContainerClosed", new Object[0]);
/*     */   }
/*     */   
/*     */   public void createRecipeItem(@Nullable IRecipe recipe) {
/* 109 */     boolean isCreativeMode = this.player.field_71075_bZ.field_75098_d;
/* 110 */     if (func_75139_a(this.outputSlotIndex).func_75216_d() && !isCreativeMode) {
/* 111 */       MCH_Lib.DbgLog(this.player.field_70170_p, "MCH_DraftingTableGuiContainer.createRecipeItem:OutputSlot is not empty", new Object[0]);
/* 112 */     } else if (recipe == null) {
/* 113 */       MCH_Lib.DbgLog(this.player.field_70170_p, "Error:MCH_DraftingTableGuiContainer.createRecipeItem:recipe is null : ", new Object[0]);
/*     */     } else {
/* 115 */       boolean result = false;
/* 116 */       if (isCreativeMode || MCH_Recipes.canCraft(this.player, recipe)) {
/* 117 */         if (!isCreativeMode) {
/* 118 */           MCH_Recipes.consumeInventory(this.player, recipe);
/*     */         }
/*     */         
/* 121 */         func_75139_a(this.outputSlotIndex).func_75215_d(recipe.func_77571_b().func_77946_l());
/* 122 */         result = true;
/*     */       } 
/*     */       
/* 125 */       MCH_Lib.DbgLog(this.player.field_70170_p, "MCH_DraftingTableGuiContainer:Result=" + result + ":Recipe=" + recipe.getRegistryName(), new Object[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int searchRecipeFromList(MCH_IRecipeList list, ItemStack item) {
/* 130 */     for (int i = 0; i < list.getRecipeListSize(); i++) {
/* 131 */       if (list.getRecipe(i).func_77571_b().func_77969_a(item)) {
/* 132 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 136 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\block\MCH_DraftingTableGuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */