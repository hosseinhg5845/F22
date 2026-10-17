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
/*  20 */     func_75146_a(new Slot(iv, 0, 10, 30));
/*  21 */     func_75146_a(new Slot(iv, 1, 10, 48));
/*  22 */     func_75146_a(new Slot(iv, 2, 10, 66));
/*  23 */     int num = this.aircraft.getNumEjectionSeat();
/*     */     
/*  25 */     for (int i = 0; i < num; i++) {
/*  26 */       func_75146_a(new Slot(iv, 3 + i, 10 + 18 * i, 105));
/*     */     }
/*     */     
/*  29 */     for (int y = 0; y < 3; y++) {
/*  30 */       for (int j = 0; j < 9; j++) {
/*  31 */         func_75146_a(new Slot((IInventory)player.field_71071_by, 9 + j + y * 9, 25 + j * 18, 135 + y * 18));
/*     */       }
/*     */     } 
/*     */     
/*  35 */     for (int x = 0; x < 9; x++) {
/*  36 */       func_75146_a(new Slot((IInventory)player.field_71071_by, x, 25 + x * 18, 195));
/*     */     }
/*     */   }
/*     */   
/*     */   public int getInventoryStartIndex() {
/*  41 */     return (this.aircraft == null) ? 3 : (3 + this.aircraft.getNumEjectionSeat());
/*     */   }
/*     */   
/*     */   public void func_75142_b() {
/*  45 */     super.func_75142_b();
/*     */   }
/*     */   
/*     */   public boolean func_75145_c(@NotNull EntityPlayer player) {
/*  49 */     if (this.aircraft.getGuiInventory().func_70300_a(player)) {
/*  50 */       return true;
/*     */     }
/*  52 */     if (this.aircraft.isUAV()) {
/*  53 */       MCH_EntityUavStation us = this.aircraft.getUavStation();
/*  54 */       if (us != null) {
/*  55 */         double x = us.field_70165_t + us.posUavX;
/*  56 */         double z = us.field_70161_v + us.posUavZ;
/*  57 */         return (this.aircraft.field_70165_t < x + 10.0D && this.aircraft.field_70165_t > x - 10.0D && this.aircraft.field_70161_v < z + 10.0D && this.aircraft.field_70161_v > z - 10.0D);
/*     */       } 
/*     */     } 
/*     */     
/*  61 */     return false;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ItemStack func_82846_b(@NotNull EntityPlayer player, int slotIndex) {
/*  66 */     MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
/*  67 */     Slot slot = this.field_75151_b.get(slotIndex);
/*  68 */     if (slot == null) {
/*  69 */       return null;
/*     */     }
/*  71 */     ItemStack itemStack = slot.func_75211_c();
/*  72 */     MCH_Lib.DbgLog(player.field_70170_p, "transferStackInSlot : %d :" + itemStack, new Object[] { Integer.valueOf(slotIndex) });
/*  73 */     if (itemStack.func_190926_b()) {
/*  74 */       return ItemStack.field_190927_a;
/*     */     }
/*  76 */     if (slotIndex < getInventoryStartIndex()) {
/*  77 */       for (int i = getInventoryStartIndex(); i < this.field_75151_b.size(); i++) {
/*  78 */         Slot playerSlot = this.field_75151_b.get(i);
/*  79 */         if (playerSlot.func_75211_c().func_190926_b()) {
/*  80 */           playerSlot.func_75215_d(itemStack);
/*  81 */           slot.func_75215_d(ItemStack.field_190927_a);
/*  82 */           return itemStack;
/*     */         } 
/*     */       } 
/*  85 */     } else if (itemStack.func_77973_b() instanceof MCH_ItemFuel) {
/*  86 */       for (int ix = 0; ix < 3; ix++) {
/*  87 */         if (iv.getFuelSlotItemStack(ix).func_190926_b()) {
/*  88 */           iv.func_70299_a(ix, itemStack);
/*  89 */           slot.func_75215_d(ItemStack.field_190927_a);
/*  90 */           return itemStack;
/*     */         } 
/*     */       } 
/*  93 */     } else if (itemStack.func_77973_b() instanceof com.norwood.mcheli.parachute.MCH_ItemParachute) {
/*  94 */       int num = this.aircraft.getNumEjectionSeat();
/*     */       
/*  96 */       for (int ixx = 0; ixx < num; ixx++) {
/*  97 */         if (iv.getParachuteSlotItemStack(ixx).func_190926_b()) {
/*  98 */           iv.func_70299_a(3 + ixx, itemStack);
/*  99 */           slot.func_75215_d(ItemStack.field_190927_a);
/* 100 */           return itemStack;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75134_a(@NotNull EntityPlayer player) {
/* 111 */     super.func_75134_a(player);
/* 112 */     if (!player.field_70170_p.field_72995_K) {
/* 113 */       MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
/*     */       
/* 115 */       for (int i = 0; i < 3; i++) {
/* 116 */         ItemStack is = iv.getFuelSlotItemStack(i);
/* 117 */         if (!is.func_190926_b() && !(is.func_77973_b() instanceof MCH_ItemFuel)) {
/* 118 */           dropPlayerItem(player, i);
/*     */         }
/*     */       } 
/*     */       
/* 122 */       for (int ix = 0; ix < 2; ix++) {
/* 123 */         ItemStack is = iv.getParachuteSlotItemStack(ix);
/* 124 */         if (!is.func_190926_b() && !(is.func_77973_b() instanceof com.norwood.mcheli.parachute.MCH_ItemParachute)) {
/* 125 */           dropPlayerItem(player, 3 + ix);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dropPlayerItem(EntityPlayer player, int slotID) {
/* 132 */     if (!player.field_70170_p.field_72995_K) {
/* 133 */       ItemStack itemstack = this.aircraft.getGuiInventory().func_70304_b(slotID);
/* 134 */       if (!itemstack.func_190926_b())
/* 135 */         player.func_71019_a(itemstack, false); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_AircraftGuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */