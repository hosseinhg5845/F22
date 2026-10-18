/*    */ package com.norwood.mcheli.lweapon;
/*    */ 
/*    */ import com.norwood.mcheli.wrapper.W_Item;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.item.EnumAction;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ItemLightWeaponBase
/*    */   extends W_Item {
/*    */   public final MCH_ItemLightWeaponBullet bullet;
/*    */   
/*    */   public MCH_ItemLightWeaponBase(int par1, MCH_ItemLightWeaponBullet bullet) {
/* 22 */     super(par1);
/* 23 */     setMaxDamage(10);
/* 24 */     setMaxStackSize(1);
/* 25 */     this.bullet = bullet;
/*    */   }
/*    */   
/*    */   public static String getName(ItemStack itemStack) {
/* 29 */     if (!itemStack.isEmpty() && itemStack.getItem() instanceof MCH_ItemLightWeaponBase) {
/* 30 */       String name = itemStack.getUnlocalizedName();
/* 31 */       int li = name.lastIndexOf(":");
/* 32 */       if (li >= 0) {
/* 33 */         name = name.substring(li + 1);
/*    */       }
/*    */       
/* 36 */       return name;
/*    */     } 
/* 38 */     return "";
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isHeld(@Nullable EntityPlayer player) {
/* 43 */     ItemStack is = (player != null) ? player.getHeldItemMainhand() : ItemStack.EMPTY;
/* 44 */     return (!is.isEmpty() && is.getItem() instanceof MCH_ItemLightWeaponBase && player.getItemInUseMaxCount() > 10);
/*    */   }
/*    */   
/*    */   public void onUsingTick(@NotNull ItemStack stack, EntityLivingBase player, int count) {
/* 48 */     PotionEffect pe = player.getActivePotionEffect(MobEffects.NIGHT_VISION);
/* 49 */     if (pe != null && pe.getDuration() < 220) {
/* 50 */       player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 250, 0, false, false));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean onEntitySwing(@NotNull EntityLivingBase entityLiving, @NotNull ItemStack stack) {
/* 55 */     return true;
/*    */   }
/*    */   @NotNull
/*    */   public EnumAction getItemUseAction(@NotNull ItemStack par1ItemStack) {
/* 59 */     return EnumAction.BOW;
/*    */   }
/*    */   
/*    */   public int getMaxItemUseDuration(@NotNull ItemStack par1ItemStack) {
/* 63 */     return 72000;
/*    */   }
/*    */   @NotNull
/*    */   public ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
/* 67 */     ItemStack itemstack = playerIn.getHeldItem(handIn);
/* 68 */     if (!itemstack.isEmpty()) {
/* 69 */       playerIn.setActiveHand(handIn);
/*    */     }
/*    */     
/* 72 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\lweapon\MCH_ItemLightWeaponBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */