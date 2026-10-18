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
/*  38 */         addSlotToContainer(new Slot((IInventory)player.inventory, 9 + i + y * 9, 30 + i * 18, 140 + y * 18));
/*     */       }
/*     */     } 
/*     */     
/*  42 */     for (int x = 0; x < 9; x++) {
/*  43 */       addSlotToContainer(new Slot((IInventory)player.inventory, x, 30 + x * 18, 198));
/*     */     }
/*     */     
/*  46 */     this.outputSlotIndex = this.inventoryItemStacks.size();
/*  47 */     Slot a = new Slot(this.outputSlot, this.outputSlotIndex, 178, 90) {
/*     */         public boolean isItemValid(@NotNull ItemStack stack) {
/*  49 */           return false;
/*     */         }
/*     */       };
/*  52 */     addSlotToContainer(a);
/*  53 */     MCH_Lib.DbgLog(player.world, "MCH_DraftingTableGuiContainer.MCH_DraftingTableGuiContainer", new Object[0]);
/*     */   }
/*     */   
/*     */   public void detectAndSendChanges() {
/*  57 */     super.detectAndSendChanges();
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer player) {
/*  61 */     Block block = W_WorldFunc.getBlock(player.world, this.posX, this.posY, this.posZ);
/*  62 */     return ((W_Block.isEqual(block, (Block)MCH_MOD.blockDraftingTable) || W_Block.isEqual(block, (Block)MCH_MOD.blockDraftingTableLit)) && player.getDistanceSq(this.posX, this.posY, this.posZ) <= 144.0D);
/*     */   }
/*     */   @NotNull
/*     */   public ItemStack transferStackInSlot(@NotNull EntityPlayer player, int slotIndex) {
/*  66 */     ItemStack itemstack = ItemStack.EMPTY;
/*  67 */     Slot slot = this.inventorySlots.get(slotIndex);
/*  68 */     if (slot != null && slot.getHasStack()) {
/*  69 */       ItemStack itemstack1 = slot.getStack();
/*  70 */       itemstack = itemstack1.copy();
/*  71 */       if (slotIndex != this.outputSlotIndex) {
/*  72 */         return ItemStack.EMPTY;
/*     */       }
/*     */       
/*  75 */       if (!mergeItemStack(itemstack1, 0, 36, true)) {
/*  76 */         return ItemStack.EMPTY;
/*     */       }
/*     */       
/*  79 */       slot.onSlotChange(itemstack1, itemstack);
/*  80 */       if (itemstack1.getCount() == 0) {
/*  81 */         slot.putStack(ItemStack.EMPTY);
/*     */       } else {
/*  83 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/*  86 */       if (itemstack1.getCount() == itemstack.getCount()) {
/*  87 */         return ItemStack.EMPTY;
/*     */       }
/*     */       
/*  90 */       slot.onTake(player, itemstack1);
/*     */     } 
/*     */     
/*  93 */     return itemstack;
/*     */   }
/*     */   
/*     */   public void onContainerClosed(@NotNull EntityPlayer player) {
/*  97 */     super.onContainerClosed(player);
/*  98 */     if (!player.world.isRemote) {
/*  99 */       ItemStack itemstack = getSlot(this.outputSlotIndex).getStack();
/* 100 */       if (!itemstack.isEmpty()) {
/* 101 */         W_EntityPlayer.dropPlayerItemWithRandomChoice(player, itemstack, false, false);
/*     */       }
/*     */     } 
/*     */     
/* 105 */     MCH_Lib.DbgLog(player.world, "MCH_DraftingTableGuiContainer.onContainerClosed", new Object[0]);
/*     */   }
/*     */   
/*     */   public void createRecipeItem(@Nullable IRecipe recipe) {
/* 109 */     boolean isCreativeMode = this.player.capabilities.isCreativeMode;
/* 110 */     if (getSlot(this.outputSlotIndex).getHasStack() && !isCreativeMode) {
/* 111 */       MCH_Lib.DbgLog(this.player.world, "MCH_DraftingTableGuiContainer.createRecipeItem:OutputSlot is not empty", new Object[0]);
/* 112 */     } else if (recipe == null) {
/* 113 */       MCH_Lib.DbgLog(this.player.world, "Error:MCH_DraftingTableGuiContainer.createRecipeItem:recipe is null : ", new Object[0]);
/*     */     } else {
/* 115 */       boolean result = false;
/* 116 */       if (isCreativeMode || MCH_Recipes.canCraft(this.player, recipe)) {
/* 117 */         if (!isCreativeMode) {
/* 118 */           MCH_Recipes.consumeInventory(this.player, recipe);
/*     */         }
/*     */         
/* 121 */         getSlot(this.outputSlotIndex).putStack(recipe.getRecipeOutput().copy());
/* 122 */         result = true;
/*     */       } 
/*     */       
/* 125 */       MCH_Lib.DbgLog(this.player.world, "MCH_DraftingTableGuiContainer:Result=" + result + ":Recipe=" + recipe.getRegistryName(), new Object[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int searchRecipeFromList(MCH_IRecipeList list, ItemStack item) {
/* 130 */     for (int i = 0; i < list.getRecipeListSize(); i++) {
/* 131 */       if (list.getRecipe(i).getRecipeOutput().isItemEqual(item)) {
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