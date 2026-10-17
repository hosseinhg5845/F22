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
/* 23 */     func_77656_e(10);
/* 24 */     func_77625_d(1);
/* 25 */     this.bullet = bullet;
/*    */   }
/*    */   
/*    */   public static String getName(ItemStack itemStack) {
/* 29 */     if (!itemStack.func_190926_b() && itemStack.func_77973_b() instanceof MCH_ItemLightWeaponBase) {
/* 30 */       String name = itemStack.func_77977_a();
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
/* 43 */     ItemStack is = (player != null) ? player.func_184614_ca() : ItemStack.field_190927_a;
/* 44 */     return (!is.func_190926_b() && is.func_77973_b() instanceof MCH_ItemLightWeaponBase && player.func_184612_cw() > 10);
/*    */   }
/*    */   
/*    */   public void onUsingTick(@NotNull ItemStack stack, EntityLivingBase player, int count) {
/* 48 */     PotionEffect pe = player.func_70660_b(MobEffects.field_76439_r);
/* 49 */     if (pe != null && pe.func_76459_b() < 220) {
/* 50 */       player.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 250, 0, false, false));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean onEntitySwing(@NotNull EntityLivingBase entityLiving, @NotNull ItemStack stack) {
/* 55 */     return true;
/*    */   }
/*    */   @NotNull
/*    */   public EnumAction func_77661_b(@NotNull ItemStack par1ItemStack) {
/* 59 */     return EnumAction.BOW;
/*    */   }
/*    */   
/*    */   public int func_77626_a(@NotNull ItemStack par1ItemStack) {
/* 63 */     return 72000;
/*    */   }
/*    */   @NotNull
/*    */   public ActionResult<ItemStack> func_77659_a(@NotNull World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
/* 67 */     ItemStack itemstack = playerIn.func_184586_b(handIn);
/* 68 */     if (!itemstack.func_190926_b()) {
/* 69 */       playerIn.func_184598_c(handIn);
/*    */     }
/*    */     
/* 72 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\lweapon\MCH_ItemLightWeaponBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */