/*    */ package com.norwood.mcheli.lweapon;
import com.norwood.mcheli.helper.network.HandleSide;
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.weapon.MCH_WeaponBase;
/*    */ import com.norwood.mcheli.weapon.MCH_WeaponCreator;
/*    */ import com.norwood.mcheli.weapon.MCH_WeaponParam;
/*    */ import com.norwood.mcheli.wrapper.W_EntityPlayer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.IThreadListener;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MCH_LightWeaponPacketHandler {
/*    */   @HandleSide({Side.SERVER})
/*    */   public static void onPacket_PlayerControl(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 21 */     if (!player.world.isRemote) {
/* 22 */       MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
/* 23 */       pc.readData(data);
/* 24 */       scheduler.addScheduledTask(() -> {
/*    */             if (pc.camMode == 1)
/*    */               player.removePotionEffect(MobEffects.NIGHT_VISION); 
/*    */             ItemStack is = player.getHeldItemMainhand();
/*    */             if (!is.isEmpty()) {
/*    */               Item patt1370$temp = is.getItem();
/*    */               if (patt1370$temp instanceof MCH_ItemLightWeaponBase) {
/*    */                 MCH_ItemLightWeaponBase lweapon = (MCH_ItemLightWeaponBase)patt1370$temp;
/*    */                 if (pc.camMode == 2 && MCH_ItemLightWeaponBase.isHeld(player))
/*    */                   player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 255, 0, false, false)); 
/*    */                 if (pc.camMode > 0)
/*    */                   MCH_Lib.DbgLog(false, "MCH_LightWeaponPacketHandler NV=%s", new Object[] { (pc.camMode == 2) ? "ON" : "OFF" }); 
/*    */                 if (pc.useWeapon && is.getMetadata() < is.getMaxDamage()) {
/*    */                   String name = MCH_ItemLightWeaponBase.getName(player.getHeldItemMainhand());
/*    */                   MCH_WeaponBase w = MCH_WeaponCreator.createWeapon(player.world, name, Vec3d.ZERO, 0.0F, 0.0F, null, false);
/*    */                   MCH_WeaponParam prm = new MCH_WeaponParam();
/*    */                   prm.entity = (Entity)player;
/*    */                   prm.user = (Entity)player;
/*    */                   prm.setPosAndRot(pc.useWeaponPosX, pc.useWeaponPosY, pc.useWeaponPosZ, player.rotationYaw, player.rotationPitch);
/*    */                   prm.option1 = pc.useWeaponOption1;
/*    */                   prm.option2 = pc.useWeaponOption2;
/*    */                   w.shot(prm);
/*    */                   if (!player.capabilities.isCreativeMode && is.getMaxDamage() == 1)
/*    */                     is.shrink(1); 
/*    */                   if (is.getMaxDamage() > 1)
/*    */                     is.setItemDamage(is.getMaxDamage()); 
/*    */                 } else if (pc.cmpReload > 0 && is.getMetadata() > 1 && W_EntityPlayer.hasItem(player, (Item)lweapon.bullet)) {
/*    */                   if (!player.capabilities.isCreativeMode)
/*    */                     W_EntityPlayer.consumeInventoryItem(player, (Item)lweapon.bullet); 
/*    */                   is.setItemDamage(0);
/*    */                 } 
/*    */               } 
/*    */             } 
/*    */           });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\lweapon\MCH_LightWeaponPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */