/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.uav.MCH_EntityUavStation;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_AircraftGuiContainer extends Container {
/*     */   public final EntityPlayer player;
/*     */   public final MCH_EntityAircraft aircraft;
/*     */   
/*     */   public MCH_AircraftGuiContainer(EntityPlayer player, MCH_EntityAircraft ac) {
/*  17 */     this.player = player;
/*  18 */     this.aircraft = ac;
/*  19 */     MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
/*  20 */     addSlotToContainer(new Slot(iv, 0, 10, 30));
/*  21 */     addSlotToContainer(new Slot(iv, 1, 10, 48));
/*  22 */     addSlotToContainer(new Slot(iv, 2, 10, 66));
/*  23 */     int num = this.aircraft.getNumEjectionSeat();
/*     */     
/*  25 */     for (int i = 0; i < num; i++) {
/*  26 */       addSlotToContainer(new Slot(iv, 3 + i, 10 + 18 * i, 105));
/*     */     }
/*     */     
/*  29 */     for (int y = 0; y < 3; y++) {
/*  30 */       for (int j = 0; j < 9; j++) {
/*  31 */         addSlotToContainer(new Slot((IInventory)player.inventory, 9 + j + y * 9, 25 + j * 18, 135 + y * 18));
/*     */       }
/*     */     } 
/*     */     
/*  35 */     for (int x = 0; x < 9; x++) {
/*  36 */       addSlotToContainer(new Slot((IInventory)player.inventory, x, 25 + x * 18, 195));
/*     */     }
/*     */   }
/*     */   
/*     */   public int getInventoryStartIndex() {
/*  41 */     return (this.aircraft == null) ? 3 : (3 + this.aircraft.getNumEjectionSeat());
/*     */   }
/*     */   
/*     */   public void detectAndSendChanges() {
/*  45 */     super.detectAndSendChanges();
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(@NotNull EntityPlayer player) {
/*  49 */     if (this.aircraft.getGuiInventory().isUsableByPlayer(player)) {
/*  50 */       return true;
/*     */     }
/*  52 */     if (this.aircraft.isUAV()) {
/*  53 */       MCH_EntityUavStation us = this.aircraft.getUavStation();
/*  54 */       if (us != null) {
/*  55 */         double x = us.posX + us.posUavX;
/*  56 */         double z = us.posZ + us.posUavZ;
/*  57 */         return (this.aircraft.posX < x + 10.0D && this.aircraft.posX > x - 10.0D && this.aircraft.posZ < z + 10.0D && this.aircraft.posZ > z - 10.0D);
/*     */       } 
/*     */     } 
/*     */     
/*  61 */     return false;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ItemStack transferStackInSlot(@NotNull EntityPlayer player, int slotIndex) {
/*  66 */     MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
/*  67 */     Slot slot = this.inventorySlots.get(slotIndex);
/*  68 */     if (slot == null) {
/*  69 */       return null;
/*     */     }
/*  71 */     ItemStack itemStack = slot.getStack();
/*  72 */     MCH_Lib.DbgLog(player.world, "transferStackInSlot : %d :" + itemStack, new Object[] { Integer.valueOf(slotIndex) });
/*  73 */     if (itemStack.isEmpty()) {
/*  74 */       return ItemStack.EMPTY;
/*     */     }
/*  76 */     if (slotIndex < getInventoryStartIndex()) {
/*  77 */       for (int i = getInventoryStartIndex(); i < this.inventorySlots.size(); i++) {
/*  78 */         Slot playerSlot = this.inventorySlots.get(i);
/*  79 */         if (playerSlot.getStack().isEmpty()) {
/*  80 */           playerSlot.putStack(itemStack);
/*  81 */           slot.putStack(ItemStack.EMPTY);
/*  82 */           return itemStack;
/*     */         } 
/*     */       } 
/*  85 */     } else if (itemStack.getItem() instanceof MCH_ItemFuel) {
/*  86 */       for (int ix = 0; ix < 3; ix++) {
/*  87 */         if (iv.getFuelSlotItemStack(ix).isEmpty()) {
/*  88 */           iv.setInventorySlotContents(ix, itemStack);
/*  89 */           slot.putStack(ItemStack.EMPTY);
/*  90 */           return itemStack;
/*     */         } 
/*     */       } 
/*  93 */     } else if (itemStack.getItem() instanceof com.norwood.mcheli.parachute.MCH_ItemParachute) {
/*  94 */       int num = this.aircraft.getNumEjectionSeat();
/*     */       
/*  96 */       for (int ixx = 0; ixx < num; ixx++) {
/*  97 */         if (iv.getParachuteSlotItemStack(ixx).isEmpty()) {
/*  98 */           iv.setInventorySlotContents(3 + ixx, itemStack);
/*  99 */           slot.putStack(ItemStack.EMPTY);
/* 100 */           return itemStack;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(@NotNull EntityPlayer player) {
/* 111 */     super.onContainerClosed(player);
/* 112 */     if (!player.world.isRemote) {
/* 113 */       MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
/*     */       
/* 115 */       for (int i = 0; i < 3; i++) {
/* 116 */         ItemStack is = iv.getFuelSlotItemStack(i);
/* 117 */         if (!is.isEmpty() && !(is.getItem() instanceof MCH_ItemFuel)) {
/* 118 */           dropPlayerItem(player, i);
/*     */         }
/*     */       } 
/*     */       
/* 122 */       for (int ix = 0; ix < 2; ix++) {
/* 123 */         ItemStack is = iv.getParachuteSlotItemStack(ix);
/* 124 */         if (!is.isEmpty() && !(is.getItem() instanceof com.norwood.mcheli.parachute.MCH_ItemParachute)) {
/* 125 */           dropPlayerItem(player, 3 + ix);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dropPlayerItem(EntityPlayer player, int slotID) {
/* 132 */     if (!player.world.isRemote) {
/* 133 */       ItemStack itemstack = this.aircraft.getGuiInventory().removeStackFromSlot(slotID);
/* 134 */       if (!itemstack.isEmpty())
/* 135 */         player.dropItem(itemstack, false); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_AircraftGuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */