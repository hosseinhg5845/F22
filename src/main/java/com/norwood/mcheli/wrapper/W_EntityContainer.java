/*     */ package com.norwood.mcheli.wrapper;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import java.util.Arrays;
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
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ITeleporter;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public abstract class W_EntityContainer extends W_Entity implements IInventory {
/*     */   public static final int MAX_INVENTORY_SIZE = 54;
/*     */   public boolean dropContentsWhenDead = true;
/*     */   private ItemStack[] containerItems;
/*     */   
/*     */   public W_EntityContainer(World par1World) {
/*  25 */     super(par1World);
/*  26 */     this.containerItems = new ItemStack[54];
/*  27 */     Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
/*     */   }
/*     */   @NotNull
/*     */   public ItemStack func_70301_a(int par1) {
/*  31 */     return this.containerItems[par1];
/*     */   }
/*     */   
/*     */   public int func_70297_j_() {
/*  35 */     return 64;
/*     */   }
/*     */   
/*     */   public int getUsingSlotNum() {
/*     */     int numUsingSlot;
/*  40 */     if (this.containerItems == null) {
/*  41 */       numUsingSlot = 0;
/*     */     } else {
/*  43 */       int n = func_70302_i_();
/*  44 */       numUsingSlot = 0;
/*     */       
/*  46 */       for (int i = 0; i < n && i < this.containerItems.length; i++) {
/*  47 */         if (!func_70301_a(i).func_190926_b()) {
/*  48 */           numUsingSlot++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  53 */     return numUsingSlot;
/*     */   }
/*     */   
/*     */   public boolean func_191420_l() {
/*  57 */     for (ItemStack itemstack : this.containerItems) {
/*  58 */       if (!itemstack.func_190926_b()) {
/*  59 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  63 */     return true;
/*     */   }
/*     */   @NotNull
/*     */   public ItemStack func_70298_a(int par1, int par2) {
/*  67 */     if (!this.containerItems[par1].func_190926_b()) {
/*     */       ItemStack itemstack;
/*  69 */       if (this.containerItems[par1].func_190916_E() <= par2) {
/*  70 */         itemstack = this.containerItems[par1];
/*  71 */         this.containerItems[par1] = ItemStack.field_190927_a;
/*     */       } else {
/*  73 */         itemstack = this.containerItems[par1].func_77979_a(par2);
/*  74 */         if (this.containerItems[par1].func_190916_E() == 0) {
/*  75 */           this.containerItems[par1] = ItemStack.field_190927_a;
/*     */         }
/*     */       } 
/*     */       
/*  79 */       return itemstack;
/*     */     } 
/*  81 */     return ItemStack.field_190927_a;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ItemStack func_70304_b(int par1) {
/*  86 */     if (!this.containerItems[par1].func_190926_b()) {
/*  87 */       ItemStack itemstack = this.containerItems[par1];
/*  88 */       this.containerItems[par1] = ItemStack.field_190927_a;
/*  89 */       return itemstack;
/*     */     } 
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int par1, ItemStack par2ItemStack) {
/*  96 */     this.containerItems[par1] = par2ItemStack;
/*  97 */     if (!par2ItemStack.func_190926_b() && par2ItemStack.func_190916_E() > func_70297_j_()) {
/*  98 */       par2ItemStack.func_190920_e(func_70297_j_());
/*     */     }
/*     */     
/* 101 */     func_70296_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onInventoryChanged() {}
/*     */   
/*     */   public boolean func_70300_a(@NotNull EntityPlayer par1EntityPlayer) {
/* 108 */     return !this.field_70128_L;
/*     */   }
/*     */   
/*     */   public boolean func_94041_b(int par1, @NotNull ItemStack par2ItemStack) {
/* 112 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   public String getInvName() {
/* 120 */     return "Inventory";
/*     */   }
/*     */   @NotNull
/*     */   public String func_70005_c_() {
/* 124 */     return getInvName();
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/* 128 */     return getInvName();
/*     */   }
/*     */   @NotNull
/*     */   public ITextComponent func_145748_c_() {
/* 132 */     return (ITextComponent)new TextComponentString(getInventoryName());
/*     */   }
/*     */   
/*     */   public boolean isInvNameLocalized() {
/* 136 */     return false;
/*     */   }
/*     */   
/*     */   public boolean func_145818_k_() {
/* 140 */     return isInvNameLocalized();
/*     */   }
/*     */   
/*     */   public void func_70106_y() {
/* 144 */     if (this.dropContentsWhenDead && !this.field_70170_p.field_72995_K) {
/* 145 */       for (int i = 0; i < func_70302_i_(); i++) {
/* 146 */         ItemStack itemstack = func_70301_a(i);
/* 147 */         if (!itemstack.func_190926_b()) {
/* 148 */           float x = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
/* 149 */           float y = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
/* 150 */           float z = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
/*     */           
/* 152 */           while (itemstack.func_190916_E() > 0) {
/* 153 */             int j = this.field_70146_Z.nextInt(21) + 10;
/* 154 */             if (j > itemstack.func_190916_E()) {
/* 155 */               j = itemstack.func_190916_E();
/*     */             }
/*     */             
/* 158 */             itemstack.func_190918_g(j);
/*     */             
/* 160 */             EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v + z, new ItemStack(itemstack.func_77973_b(), j, itemstack.func_77960_j()));
/*     */             
/* 162 */             if (itemstack.func_77942_o()) {
/* 163 */               entityitem.func_92059_d().func_77982_d(itemstack.func_77978_p().func_74737_b());
/*     */             }
/*     */             
/* 166 */             float f3 = 0.05F;
/* 167 */             entityitem.field_70159_w = ((float)this.field_70146_Z.nextGaussian() * f3);
/* 168 */             entityitem.field_70181_x = ((float)this.field_70146_Z.nextGaussian() * f3 + 0.2F);
/* 169 */             entityitem.field_70179_y = ((float)this.field_70146_Z.nextGaussian() * f3);
/* 170 */             this.field_70170_p.func_72838_d((Entity)entityitem);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 176 */     super.func_70106_y();
/*     */   }
/*     */   
/*     */   protected void func_70014_b(@NotNull NBTTagCompound par1NBTTagCompound) {
/* 180 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 182 */     for (int i = 0; i < this.containerItems.length; i++) {
/* 183 */       if (!this.containerItems[i].func_190926_b()) {
/* 184 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 185 */         nbttagcompound1.func_74774_a("Slot", (byte)i);
/* 186 */         this.containerItems[i].func_77955_b(nbttagcompound1);
/* 187 */         nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     par1NBTTagCompound.func_74782_a("Items", (NBTBase)nbttaglist);
/*     */   }
/*     */   
/*     */   protected void func_70037_a(@NotNull NBTTagCompound par1NBTTagCompound) {
/* 195 */     NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "Items", 10);
/* 196 */     this.containerItems = new ItemStack[func_70302_i_()];
/* 197 */     Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
/* 198 */     MCH_Lib.DbgLog(this.field_70170_p, "W_EntityContainer.readEntityFromNBT.InventorySize = %d", new Object[] { Integer.valueOf(func_70302_i_()) });
/*     */     
/* 200 */     for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
/* 201 */       NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
/* 202 */       int j = nbttagcompound1.func_74771_c("Slot") & 0xFF;
/* 203 */       if (j < this.containerItems.length) {
/* 204 */         this.containerItems[j] = new ItemStack(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Entity changeDimension(int dimensionIn, @NotNull ITeleporter teleporter) {
/* 210 */     this.dropContentsWhenDead = false;
/* 211 */     return super.changeDimension(dimensionIn, teleporter);
/*     */   }
/*     */   
/*     */   public boolean displayInventory(EntityPlayer player) {
/* 215 */     if (!this.field_70170_p.field_72995_K && func_70302_i_() > 0) {
/* 216 */       player.func_71007_a(this);
/* 217 */       return true;
/*     */     } 
/* 219 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174889_b(@NotNull EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public void func_174886_c(@NotNull EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public void func_70296_d() {}
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 233 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174888_l() {}
/*     */   
/*     */   public int func_174887_a_(int id) {
/* 240 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174885_b(int id, int value) {}
/*     */   
/*     */   public int func_174890_g() {
/* 247 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_EntityContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */