/*    */ package com.norwood.mcheli.uav;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ContainerUavStation extends Container {
/*    */   protected final MCH_EntityUavStation uavStation;
/*    */   
/*    */   public MCH_ContainerUavStation(InventoryPlayer inventoryPlayer, MCH_EntityUavStation te) {
/* 15 */     this.uavStation = te;
/* 16 */     func_75146_a(new Slot((IInventory)this.uavStation, 0, 20, 20));
/* 17 */     bindPlayerInventory(inventoryPlayer);
/*    */   }
/*    */   
/*    */   public boolean func_75145_c(@NotNull EntityPlayer player) {
/* 21 */     return this.uavStation.func_70300_a(player);
/*    */   }
/*    */   
/*    */   public void func_75130_a(@NotNull IInventory par1IInventory) {
/* 25 */     super.func_75130_a(par1IInventory);
/*    */   }
/*    */   protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
/*    */     int i;
/* 29 */     for (i = 0; i < 3; i++) {
/* 30 */       for (int j = 0; j < 9; j++) {
/* 31 */         func_75146_a(new Slot((IInventory)inventoryPlayer, 9 + j + i * 9, 8 + j * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/*    */     
/* 35 */     for (i = 0; i < 9; i++)
/* 36 */       func_75146_a(new Slot((IInventory)inventoryPlayer, i, 8 + i * 18, 142)); 
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public ItemStack func_82846_b(@NotNull EntityPlayer player, int slot) {
/* 41 */     return ItemStack.field_190927_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mchel\\uav\MCH_ContainerUavStation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */