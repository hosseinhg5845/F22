/*    */ package com.norwood.mcheli;
/*    */ 
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*    */ import com.norwood.mcheli.aircraft.MCH_ItemAircraft;
/*    */ import com.norwood.mcheli.wrapper.W_Item;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_CreativeTabs
/*    */   extends CreativeTabs {
/*    */   private final List<ItemStack> iconItems;
/*    */   private ItemStack lastItem;
/*    */   private int currentIconIndex;
/*    */   private int switchItemWait;
/* 23 */   private Item fixedItem = null;
/*    */   
/*    */   public MCH_CreativeTabs(String label) {
/* 26 */     super(label);
/* 27 */     this.iconItems = new ArrayList<>();
/* 28 */     this.currentIconIndex = 0;
/* 29 */     this.switchItemWait = 0;
/* 30 */     this.lastItem = ItemStack.EMPTY;
/*    */   }
/*    */   
/*    */   public void setFixedIconItem(String itemName) {
/* 34 */     if (itemName.indexOf(':') >= 0) {
/* 35 */       this.fixedItem = W_Item.getItemByName(itemName);
/*    */     } else {
/* 37 */       this.fixedItem = W_Item.getItemByName("mcheli:" + itemName);
/* 38 */       if (this.fixedItem != null);
/*    */     } 
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public ItemStack getTabIconItem() {
/* 44 */     if (this.iconItems.isEmpty()) {
/* 45 */       return ItemStack.EMPTY;
/*    */     }
/* 47 */     this.currentIconIndex = (this.currentIconIndex + 1) % this.iconItems.size();
/* 48 */     return this.iconItems.get(this.currentIconIndex);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public ItemStack getIconItemStack() {
/* 53 */     if (this.fixedItem != null) {
/* 54 */       return new ItemStack(this.fixedItem, 1, 0);
/*    */     }
/* 56 */     if (this.switchItemWait > 0) {
/* 57 */       this.switchItemWait--;
/*    */     } else {
/* 59 */       this.lastItem = getTabIconItem();
/* 60 */       this.switchItemWait = 60;
/*    */     } 
/*    */     
/* 63 */     if (this.lastItem.isEmpty()) {
/* 64 */       this.lastItem = new ItemStack(W_Item.getItemByName("iron_block"));
/*    */     }
/*    */     
/* 67 */     return this.lastItem;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void displayAllRelevantItems(@NotNull NonNullList<ItemStack> list) {
/* 73 */     super.displayAllRelevantItems(list);
/* 74 */     Comparator<ItemStack> cmp = (i1, i2) -> {
/*    */         if (i1.getItem() instanceof MCH_ItemAircraft && i2.getItem() instanceof MCH_ItemAircraft) {
/*    */           MCH_AircraftInfo info1 = ((MCH_ItemAircraft)i1.getItem()).getAircraftInfo();
/*    */           
/*    */           MCH_AircraftInfo info2 = ((MCH_ItemAircraft)i2.getItem()).getAircraftInfo();
/*    */           if (info1 != null && info2 != null) {
/*    */             String s1 = info1.category + "." + info1.name;
/*    */             String s2 = info2.category + "." + info2.name;
/*    */             return s1.compareTo(s2);
/*    */           } 
/*    */         } 
/*    */         return i1.getItem().getUnlocalizedName().compareTo(i2.getItem().getUnlocalizedName());
/*    */       };
/* 87 */     list.sort(cmp);
/*    */   }
/*    */   
/*    */   public void addIconItem(Item i) {
/* 91 */     if (i != null)
/* 92 */       this.iconItems.add(new ItemStack(i)); 
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String getTranslatedTabLabel() {
/* 97 */     return "MC Heli";
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_CreativeTabs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */