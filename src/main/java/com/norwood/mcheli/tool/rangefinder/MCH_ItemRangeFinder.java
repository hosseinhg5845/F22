/*     */ package com.norwood.mcheli.tool.rangefinder;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */ import com.norwood.mcheli.multiplay.MCH_PacketIndSpotEntity;
/*     */ import com.norwood.mcheli.wrapper.W_Item;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_ItemRangeFinder extends W_Item {
/*  22 */   public static int rangeFinderUseCooldown = 0;
/*     */   public static boolean continueUsingItem = false;
/*  24 */   public static float zoom = 2.0F;
/*  25 */   public static int mode = 0;
/*     */   
/*     */   public MCH_ItemRangeFinder(int itemId) {
/*  28 */     super(itemId);
/*  29 */     this.maxStackSize = 1;
/*  30 */     setMaxDamage(10);
/*     */   }
/*     */   
/*     */   public static boolean canUse(EntityPlayer player) {
/*  34 */     if (player == null)
/*  35 */       return false; 
/*  36 */     if (player.world == null)
/*  37 */       return false; 
/*  38 */     if (player.getHeldItemMainhand().isEmpty())
/*  39 */       return false; 
/*  40 */     if (!(player.getHeldItemMainhand().getItem() instanceof MCH_ItemRangeFinder))
/*  41 */       return false; 
/*  42 */     if (player.getRidingEntity() instanceof MCH_EntityAircraft) {
/*  43 */       return false;
/*     */     }
/*  45 */     if (player.getRidingEntity() instanceof MCH_EntitySeat) {
/*  46 */       MCH_EntityAircraft ac = ((MCH_EntitySeat)player.getRidingEntity()).getParent();
/*  47 */       return (ac == null || (!ac.getIsGunnerMode((Entity)player) && ac.getWeaponIDBySeatID(ac.getSeatIdByEntity((Entity)player)) < 0));
/*     */     } 
/*     */     
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isUsingScope(EntityPlayer player) {
/*  55 */     return (player.getItemInUseMaxCount() > 8 || continueUsingItem);
/*     */   }
/*     */   
/*     */   public static void onStartUseItem() {
/*  59 */     zoom = 2.0F;
/*  60 */     W_Reflection.setCameraZoom(2.0F);
/*  61 */     continueUsingItem = true;
/*     */   }
/*     */   
/*     */   public static void onStopUseItem() {
/*  65 */     W_Reflection.restoreCameraZoom();
/*  66 */     continueUsingItem = false;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void spotEntity(EntityPlayer player, ItemStack itemStack) {
/*  71 */     if (player != null && player.world.isRemote && rangeFinderUseCooldown == 0 && player.getItemInUseMaxCount() > 8) {
/*  72 */       if (mode == 2) {
/*  73 */         rangeFinderUseCooldown = 60;
/*  74 */         MCH_PacketIndSpotEntity.send((EntityLivingBase)player, 0);
/*  75 */       } else if (itemStack.getMetadata() < itemStack.getMaxDamage()) {
/*  76 */         rangeFinderUseCooldown = 60;
/*  77 */         MCH_PacketIndSpotEntity.send((EntityLivingBase)player, (mode == 0) ? 60 : 3);
/*     */       } else {
/*  79 */         W_McClient.MOD_playSoundFX("ng", 1.0F, 1.0F);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlayerStoppedUsing(@NotNull ItemStack stack, World worldIn, @NotNull EntityLivingBase entityLiving, int timeLeft) {
/*  85 */     if (worldIn.isRemote)
/*  86 */       onStopUseItem(); 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ItemStack onItemUseFinish(@NotNull ItemStack stack, @NotNull World worldIn, @NotNull EntityLivingBase entityLiving) {
/*  91 */     return stack;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean isFull3D() {
/*  96 */     return true;
/*     */   }
/*     */   @NotNull
/*     */   public EnumAction getItemUseAction(@NotNull ItemStack itemStack) {
/* 100 */     return EnumAction.BOW;
/*     */   }
/*     */   
/*     */   public int getMaxItemUseDuration(@NotNull ItemStack itemStack) {
/* 104 */     return 72000;
/*     */   }
/*     */   @NotNull
/*     */   public ActionResult<ItemStack> onItemRightClick(@NotNull World world, EntityPlayer player, @NotNull EnumHand handIn) {
/* 108 */     ItemStack itemstack = player.getHeldItem(handIn);
/* 109 */     if (canUse(player)) {
/* 110 */       player.setActiveHand(handIn);
/*     */     }
/*     */     
/* 113 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tool\rangefinder\MCH_ItemRangeFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */