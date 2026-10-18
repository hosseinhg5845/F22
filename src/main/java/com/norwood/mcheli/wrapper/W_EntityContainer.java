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
/*  27 */     Arrays.fill((Object[])this.containerItems, ItemStack.EMPTY);
/*     */   }
/*     */   @NotNull
/*     */   public ItemStack getStackInSlot(int par1) {
/*  31 */     return this.containerItems[par1];
/*     */   }
/*     */   
/*     */   public int getInventoryStackLimit() {
/*  35 */     return 64;
/*     */   }
/*     */   
/*     */   public int getUsingSlotNum() {
/*     */     int numUsingSlot;
/*  40 */     if (this.containerItems == null) {
/*  41 */       numUsingSlot = 0;
/*     */     } else {
/*  43 */       int n = getSizeInventory();
/*  44 */       numUsingSlot = 0;
/*     */       
/*  46 */       for (int i = 0; i < n && i < this.containerItems.length; i++) {
/*  47 */         if (!getStackInSlot(i).isEmpty()) {
/*  48 */           numUsingSlot++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  53 */     return numUsingSlot;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  57 */     for (ItemStack itemstack : this.containerItems) {
/*  58 */       if (!itemstack.isEmpty()) {
/*  59 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  63 */     return true;
/*     */   }
/*     */   @NotNull
/*     */   public ItemStack decrStackSize(int par1, int par2) {
/*  67 */     if (!this.containerItems[par1].isEmpty()) {
/*     */       ItemStack itemstack;
/*  69 */       if (this.containerItems[par1].getCount() <= par2) {
/*  70 */         itemstack = this.containerItems[par1];
/*  71 */         this.containerItems[par1] = ItemStack.EMPTY;
/*     */       } else {
/*  73 */         itemstack = this.containerItems[par1].splitStack(par2);
/*  74 */         if (this.containerItems[par1].getCount() == 0) {
/*  75 */           this.containerItems[par1] = ItemStack.EMPTY;
/*     */         }
/*     */       } 
/*     */       
/*  79 */       return itemstack;
/*     */     } 
/*  81 */     return ItemStack.EMPTY;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ItemStack removeStackFromSlot(int par1) {
/*  86 */     if (!this.containerItems[par1].isEmpty()) {
/*  87 */       ItemStack itemstack = this.containerItems[par1];
/*  88 */       this.containerItems[par1] = ItemStack.EMPTY;
/*  89 */       return itemstack;
/*     */     } 
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
/*  96 */     this.containerItems[par1] = par2ItemStack;
/*  97 */     if (!par2ItemStack.isEmpty() && par2ItemStack.getCount() > getInventoryStackLimit()) {
/*  98 */       par2ItemStack.setCount(getInventoryStackLimit());
/*     */     }
/*     */     
/* 101 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onInventoryChanged() {}
/*     */   
/*     */   public boolean isUsableByPlayer(@NotNull EntityPlayer par1EntityPlayer) {
/* 108 */     return !this.isDead;
/*     */   }
/*     */   
/*     */   public boolean isItemValidForSlot(int par1, @NotNull ItemStack par2ItemStack) {
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
/*     */   public String getName() {
/* 124 */     return getInvName();
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/* 128 */     return getInvName();
/*     */   }
/*     */   @NotNull
/*     */   public ITextComponent getDisplayName() {
/* 132 */     return (ITextComponent)new TextComponentString(getInventoryName());
/*     */   }
/*     */   
/*     */   public boolean isInvNameLocalized() {
/* 136 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/* 140 */     return isInvNameLocalized();
/*     */   }
/*     */   
/*     */   public void setDead() {
/* 144 */     if (this.dropContentsWhenDead && !this.world.isRemote) {
/* 145 */       for (int i = 0; i < getSizeInventory(); i++) {
/* 146 */         ItemStack itemstack = getStackInSlot(i);
/* 147 */         if (!itemstack.isEmpty()) {
/* 148 */           float x = this.rand.nextFloat() * 0.8F + 0.1F;
/* 149 */           float y = this.rand.nextFloat() * 0.8F + 0.1F;
/* 150 */           float z = this.rand.nextFloat() * 0.8F + 0.1F;
/*     */           
/* 152 */           while (itemstack.getCount() > 0) {
/* 153 */             int j = this.rand.nextInt(21) + 10;
/* 154 */             if (j > itemstack.getCount()) {
/* 155 */               j = itemstack.getCount();
/*     */             }
/*     */             
/* 158 */             itemstack.shrink(j);
/*     */             
/* 160 */             EntityItem entityitem = new EntityItem(this.world, this.posX + x, this.posY + y, this.posZ + z, new ItemStack(itemstack.getItem(), j, itemstack.getMetadata()));
/*     */             
/* 162 */             if (itemstack.hasTagCompound()) {
/* 163 */               entityitem.getItem().setTagCompound(itemstack.getTagCompound().copy());
/*     */             }
/*     */             
/* 166 */             float f3 = 0.05F;
/* 167 */             entityitem.motionX = ((float)this.rand.nextGaussian() * f3);
/* 168 */             entityitem.motionY = ((float)this.rand.nextGaussian() * f3 + 0.2F);
/* 169 */             entityitem.motionZ = ((float)this.rand.nextGaussian() * f3);
/* 170 */             this.world.spawnEntity((Entity)entityitem);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 176 */     super.setDead();
/*     */   }
/*     */   
/*     */   protected void writeEntityToNBT(@NotNull NBTTagCompound par1NBTTagCompound) {
/* 180 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 182 */     for (int i = 0; i < this.containerItems.length; i++) {
/* 183 */       if (!this.containerItems[i].isEmpty()) {
/* 184 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 185 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 186 */         this.containerItems[i].writeToNBT(nbttagcompound1);
/* 187 */         nbttaglist.appendTag((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     par1NBTTagCompound.setTag("Items", (NBTBase)nbttaglist);
/*     */   }
/*     */   
/*     */   protected void readEntityFromNBT(@NotNull NBTTagCompound par1NBTTagCompound) {
/* 195 */     NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "Items", 10);
/* 196 */     this.containerItems = new ItemStack[getSizeInventory()];
/* 197 */     Arrays.fill((Object[])this.containerItems, ItemStack.EMPTY);
/* 198 */     MCH_Lib.DbgLog(this.world, "W_EntityContainer.readEntityFromNBT.InventorySize = %d", new Object[] { Integer.valueOf(getSizeInventory()) });
/*     */     
/* 200 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 201 */       NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
/* 202 */       int j = nbttagcompound1.getByte("Slot") & 0xFF;
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
/* 215 */     if (!this.world.isRemote && getSizeInventory() > 0) {
/* 216 */       player.displayGUIChest(this);
/* 217 */       return true;
/*     */     } 
/* 219 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void openInventory(@NotNull EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public void closeInventory(@NotNull EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public void markDirty() {}
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 233 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {}
/*     */   
/*     */   public int getField(int id) {
/* 240 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount() {
/* 247 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\W_EntityContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */