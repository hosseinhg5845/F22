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
/*  25 */   private ItemStack[] containerItems = new ItemStack[getSizeInventory()];
/*     */   
/*     */   public MCH_AircraftInventory(MCH_EntityAircraft ac) {
/*  28 */     Arrays.fill((Object[])this.containerItems, ItemStack.EMPTY);
/*  29 */     this.aircraft = ac;
/*     */   }
/*     */   
/*     */   public ItemStack getFuelSlotItemStack(int i) {
/*  33 */     return getStackInSlot(i);
/*     */   }
/*     */   
/*     */   public ItemStack getParachuteSlotItemStack(int i) {
/*  37 */     return getStackInSlot(3 + i);
/*     */   }
/*     */   
/*     */   public boolean haveParachute() {
/*  41 */     for (int i = 0; i < 2; i++) {
/*  42 */       ItemStack item = getParachuteSlotItemStack(i);
/*  43 */       if (!item.isEmpty() && item.getItem() instanceof com.norwood.mcheli.parachute.MCH_ItemParachute) {
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
/*  54 */       if (!item.isEmpty() && item.getItem() instanceof com.norwood.mcheli.parachute.MCH_ItemParachute) {
/*  55 */         setInventorySlotContents(3 + i, ItemStack.EMPTY);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getSizeInventory() {
/*  62 */     return 10;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  66 */     for (ItemStack itemstack : this.containerItems) {
/*  67 */       if (!itemstack.isEmpty()) {
/*  68 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  72 */     return true;
/*     */   }
/*     */   @NotNull
/*     */   public ItemStack getStackInSlot(int var1) {
/*  76 */     return this.containerItems[var1];
/*     */   }
/*     */   
/*     */   public void setDead() {
/*  80 */     Random rand = new Random();
/*  81 */     if (this.aircraft.dropContentsWhenDead && !this.aircraft.world.isRemote)
/*  82 */       for (int i = 0; i < getSizeInventory(); i++) {
/*  83 */         ItemStack itemstack = getStackInSlot(i);
/*  84 */         if (!itemstack.isEmpty()) {
/*  85 */           float x = rand.nextFloat() * 0.8F + 0.1F;
/*  86 */           float y = rand.nextFloat() * 0.8F + 0.1F;
/*  87 */           float z = rand.nextFloat() * 0.8F + 0.1F;
/*     */           
/*  89 */           while (itemstack.getCount() > 0) {
/*  90 */             int j = rand.nextInt(21) + 10;
/*  91 */             if (j > itemstack.getCount()) {
/*  92 */               j = itemstack.getCount();
/*     */             }
/*     */             
/*  95 */             itemstack.shrink(j);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 101 */             EntityItem entityitem = new EntityItem(this.aircraft.world, this.aircraft.posX + x, this.aircraft.posY + y, this.aircraft.posZ + z, new ItemStack(itemstack.getItem(), j, itemstack.getMetadata()));
/*     */             
/* 103 */             if (itemstack.hasTagCompound()) {
/* 104 */               entityitem.getItem().setTagCompound(itemstack.getTagCompound().copy());
/*     */             }
/*     */             
/* 107 */             float f3 = 0.05F;
/* 108 */             entityitem.motionX = ((float)rand.nextGaussian() * f3);
/* 109 */             entityitem.motionY = ((float)rand.nextGaussian() * f3 + 0.2F);
/* 110 */             entityitem.motionZ = ((float)rand.nextGaussian() * f3);
/* 111 */             this.aircraft.world.spawnEntity((Entity)entityitem);
/*     */           } 
/*     */         } 
/*     */       }  
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ItemStack decrStackSize(int par1, int par2) {
/* 119 */     if (!this.containerItems[par1].isEmpty()) {
/*     */       ItemStack itemstack;
/* 121 */       if (this.containerItems[par1].getCount() <= par2) {
/* 122 */         itemstack = this.containerItems[par1];
/* 123 */         this.containerItems[par1] = ItemStack.EMPTY;
/*     */       } else {
/* 125 */         itemstack = this.containerItems[par1].splitStack(par2);
/* 126 */         if (this.containerItems[par1].getCount() == 0) {
/* 127 */           this.containerItems[par1] = ItemStack.EMPTY;
/*     */         }
/*     */       } 
/*     */       
/* 131 */       return itemstack;
/*     */     } 
/* 133 */     return ItemStack.EMPTY;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ItemStack removeStackFromSlot(int par1) {
/* 138 */     if (!this.containerItems[par1].isEmpty()) {
/* 139 */       ItemStack itemstack = this.containerItems[par1];
/* 140 */       this.containerItems[par1] = ItemStack.EMPTY;
/* 141 */       return itemstack;
/*     */     } 
/* 143 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
/* 148 */     this.containerItems[par1] = par2ItemStack;
/* 149 */     if (!par2ItemStack.isEmpty() && par2ItemStack.getCount() > getInventoryStackLimit()) {
/* 150 */       par2ItemStack.setCount(getInventoryStackLimit());
/*     */     }
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/* 155 */     return getInvName();
/*     */   }
/*     */   @NotNull
/*     */   public String getName() {
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
/*     */   public ITextComponent getDisplayName() {
/* 176 */     return (ITextComponent)new TextComponentString(getInvName());
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/* 180 */     return isInvNameLocalized();
/*     */   }
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 184 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public void markDirty() {}
/*     */   
/*     */   public boolean isUsableByPlayer(EntityPlayer player) {
/* 191 */     return (player.getDistanceSq((Entity)this.aircraft) <= 144.0D);
/*     */   }
/*     */   
/*     */   public boolean isItemValidForSlot(int par1, @NotNull ItemStack par2ItemStack) {
/* 195 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void openInventory(@NotNull EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public void closeInventory(@NotNull EntityPlayer player) {}
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
/* 209 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 211 */     for (int i = 0; i < this.containerItems.length; i++) {
/* 212 */       if (!this.containerItems[i].isEmpty()) {
/* 213 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 214 */         nbttagcompound1.setByte("SlotAC", (byte)i);
/* 215 */         this.containerItems[i].writeToNBT(nbttagcompound1);
/* 216 */         nbttaglist.appendTag((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     par1NBTTagCompound.setTag("ItemsAC", (NBTBase)nbttaglist);
/*     */   }
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
/* 224 */     NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "ItemsAC", 10);
/* 225 */     this.containerItems = new ItemStack[getSizeInventory()];
/* 226 */     Arrays.fill((Object[])this.containerItems, ItemStack.EMPTY);
/*     */     
/* 228 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 229 */       NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
/* 230 */       int j = nbttagcompound1.getByte("SlotAC") & 0xFF;
/* 231 */       if (j < this.containerItems.length) {
/* 232 */         this.containerItems[j] = new ItemStack(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onInventoryChanged() {}
/*     */   
/*     */   public int getField(int id) {
/* 241 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount() {
/* 248 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 252 */     for (int i = 0; i < getSizeInventory(); i++)
/* 253 */       this.containerItems[i] = ItemStack.EMPTY; 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_AircraftInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */