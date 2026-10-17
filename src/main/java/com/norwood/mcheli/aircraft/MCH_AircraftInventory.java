/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.norwood.mcheli.wrapper.W_NBTTag;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_AircraftInventory implements IInventory {
/*  19 */   public final int SLOT_FUEL0 = 0;
/*  20 */   public final int SLOT_FUEL1 = 1;
/*  21 */   public final int SLOT_FUEL2 = 2;
/*  22 */   public final int SLOT_PARACHUTE0 = 3;
/*  23 */   public final int SLOT_PARACHUTE1 = 4;
/*     */   final MCH_EntityAircraft aircraft;
/*  25 */   private ItemStack[] containerItems = new ItemStack[func_70302_i_()];
/*     */   
/*     */   public MCH_AircraftInventory(MCH_EntityAircraft ac) {
/*  28 */     Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
/*  29 */     this.aircraft = ac;
/*     */   }
/*     */   
/*     */   public ItemStack getFuelSlotItemStack(int i) {
/*  33 */     return func_70301_a(i);
/*     */   }
/*     */   
/*     */   public ItemStack getParachuteSlotItemStack(int i) {
/*  37 */     return func_70301_a(3 + i);
/*     */   }
/*     */   
/*     */   public boolean haveParachute() {
/*  41 */     for (int i = 0; i < 2; i++) {
/*  42 */       ItemStack item = getParachuteSlotItemStack(i);
/*  43 */       if (!item.func_190926_b() && item.func_77973_b() instanceof com.norwood.mcheli.parachute.MCH_ItemParachute) {
/*  44 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  48 */     return false;
/*     */   }
/*     */   
/*     */   public void consumeParachute() {
/*  52 */     for (int i = 0; i < 2; i++) {
/*  53 */       ItemStack item = getParachuteSlotItemStack(i);
/*  54 */       if (!item.func_190926_b() && item.func_77973_b() instanceof com.norwood.mcheli.parachute.MCH_ItemParachute) {
/*  55 */         func_70299_a(3 + i, ItemStack.field_190927_a);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int func_70302_i_() {
/*  62 */     return 10;
/*     */   }
/*     */   
/*     */   public boolean func_191420_l() {
/*  66 */     for (ItemStack itemstack : this.containerItems) {
/*  67 */       if (!itemstack.func_190926_b()) {
/*  68 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  72 */     return true;
/*     */   }
/*     */   @NotNull
/*     */   public ItemStack func_70301_a(int var1) {
/*  76 */     return this.containerItems[var1];
/*     */   }
/*     */   
/*     */   public void setDead() {
/*  80 */     Random rand = new Random();
/*  81 */     if (this.aircraft.dropContentsWhenDead && !this.aircraft.field_70170_p.field_72995_K)
/*  82 */       for (int i = 0; i < func_70302_i_(); i++) {
/*  83 */         ItemStack itemstack = func_70301_a(i);
/*  84 */         if (!itemstack.func_190926_b()) {
/*  85 */           float x = rand.nextFloat() * 0.8F + 0.1F;
/*  86 */           float y = rand.nextFloat() * 0.8F + 0.1F;
/*  87 */           float z = rand.nextFloat() * 0.8F + 0.1F;
/*     */           
/*  89 */           while (itemstack.func_190916_E() > 0) {
/*  90 */             int j = rand.nextInt(21) + 10;
/*  91 */             if (j > itemstack.func_190916_E()) {
/*  92 */               j = itemstack.func_190916_E();
/*     */             }
/*     */             
/*  95 */             itemstack.func_190918_g(j);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 101 */             EntityItem entityitem = new EntityItem(this.aircraft.field_70170_p, this.aircraft.field_70165_t + x, this.aircraft.field_70163_u + y, this.aircraft.field_70161_v + z, new ItemStack(itemstack.func_77973_b(), j, itemstack.func_77960_j()));
/*     */             
/* 103 */             if (itemstack.func_77942_o()) {
/* 104 */               entityitem.func_92059_d().func_77982_d(itemstack.func_77978_p().func_74737_b());
/*     */             }
/*     */             
/* 107 */             float f3 = 0.05F;
/* 108 */             entityitem.field_70159_w = ((float)rand.nextGaussian() * f3);
/* 109 */             entityitem.field_70181_x = ((float)rand.nextGaussian() * f3 + 0.2F);
/* 110 */             entityitem.field_70179_y = ((float)rand.nextGaussian() * f3);
/* 111 */             this.aircraft.field_70170_p.func_72838_d((Entity)entityitem);
/*     */           } 
/*     */         } 
/*     */       }  
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ItemStack func_70298_a(int par1, int par2) {
/* 119 */     if (!this.containerItems[par1].func_190926_b()) {
/*     */       ItemStack itemstack;
/* 121 */       if (this.containerItems[par1].func_190916_E() <= par2) {
/* 122 */         itemstack = this.containerItems[par1];
/* 123 */         this.containerItems[par1] = ItemStack.field_190927_a;
/*     */       } else {
/* 125 */         itemstack = this.containerItems[par1].func_77979_a(par2);
/* 126 */         if (this.containerItems[par1].func_190916_E() == 0) {
/* 127 */           this.containerItems[par1] = ItemStack.field_190927_a;
/*     */         }
/*     */       } 
/*     */       
/* 131 */       return itemstack;
/*     */     } 
/* 133 */     return ItemStack.field_190927_a;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ItemStack func_70304_b(int par1) {
/* 138 */     if (!this.containerItems[par1].func_190926_b()) {
/* 139 */       ItemStack itemstack = this.containerItems[par1];
/* 140 */       this.containerItems[par1] = ItemStack.field_190927_a;
/* 141 */       return itemstack;
/*     */     } 
/* 143 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int par1, ItemStack par2ItemStack) {
/* 148 */     this.containerItems[par1] = par2ItemStack;
/* 149 */     if (!par2ItemStack.func_190926_b() && par2ItemStack.func_190916_E() > func_70297_j_()) {
/* 150 */       par2ItemStack.func_190920_e(func_70297_j_());
/*     */     }
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/* 155 */     return getInvName();
/*     */   }
/*     */   @NotNull
/*     */   public String func_70005_c_() {
/* 159 */     return getInvName();
/*     */   }
/*     */   
/*     */   public String getInvName() {
/* 163 */     if (this.aircraft.getAcInfo() == null) {
/* 164 */       return "";
/*     */     }
/* 166 */     String s = (this.aircraft.getAcInfo()).displayName;
/* 167 */     return (s.length() <= 32) ? s : s.substring(0, 31);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvNameLocalized() {
/* 172 */     return (this.aircraft.getAcInfo() != null);
/*     */   }
/*     */   @NotNull
/*     */   public ITextComponent func_145748_c_() {
/* 176 */     return (ITextComponent)new TextComponentString(getInvName());
/*     */   }
/*     */   
/*     */   public boolean func_145818_k_() {
/* 180 */     return isInvNameLocalized();
/*     */   }
/*     */   
/*     */   public int func_70297_j_() {
/* 184 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {}
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 191 */     return (player.func_70068_e((Entity)this.aircraft) <= 144.0D);
/*     */   }
/*     */   
/*     */   public boolean func_94041_b(int par1, @NotNull ItemStack par2ItemStack) {
/* 195 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174889_b(@NotNull EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public void func_174886_c(@NotNull EntityPlayer player) {}
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
/* 209 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 211 */     for (int i = 0; i < this.containerItems.length; i++) {
/* 212 */       if (!this.containerItems[i].func_190926_b()) {
/* 213 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 214 */         nbttagcompound1.func_74774_a("SlotAC", (byte)i);
/* 215 */         this.containerItems[i].func_77955_b(nbttagcompound1);
/* 216 */         nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     par1NBTTagCompound.func_74782_a("ItemsAC", (NBTBase)nbttaglist);
/*     */   }
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
/* 224 */     NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "ItemsAC", 10);
/* 225 */     this.containerItems = new ItemStack[func_70302_i_()];
/* 226 */     Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
/*     */     
/* 228 */     for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
/* 229 */       NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
/* 230 */       int j = nbttagcompound1.func_74771_c("SlotAC") & 0xFF;
/* 231 */       if (j < this.containerItems.length) {
/* 232 */         this.containerItems[j] = new ItemStack(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onInventoryChanged() {}
/*     */   
/*     */   public int func_174887_a_(int id) {
/* 241 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174885_b(int id, int value) {}
/*     */   
/*     */   public int func_174890_g() {
/* 248 */     return 0;
/*     */   }
/*     */   
/*     */   public void func_174888_l() {
/* 252 */     for (int i = 0; i < func_70302_i_(); i++)
/* 253 */       this.containerItems[i] = ItemStack.field_190927_a; 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_AircraftInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */