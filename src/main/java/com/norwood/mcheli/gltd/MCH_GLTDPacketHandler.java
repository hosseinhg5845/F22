/*    */ package com.norwood.mcheli.gltd;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.helper.network.HandleSide;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.IThreadListener;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ public class MCH_GLTDPacketHandler {
/*    */   @HandleSide({Side.SERVER})
/*    */   public static void onPacket_GLTDPlayerControl(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 13 */     Entity entity = player.func_184187_bx(); if (entity instanceof MCH_EntityGLTD) { MCH_EntityGLTD gltd = (MCH_EntityGLTD)entity;
/* 14 */       if (!player.field_70170_p.field_72995_K) {
/* 15 */         MCH_PacketGLTDPlayerControl pc = new MCH_PacketGLTDPlayerControl();
/* 16 */         pc.readData(data);
/* 17 */         scheduler.func_152344_a(() -> {
/*    */               if (pc.unmount) {
/*    */                 Entity riddenByEntity = gltd.getRiddenByEntity();
/*    */                 if (riddenByEntity != null)
/*    */                   riddenByEntity.func_184210_p(); 
/*    */               } else {
/*    */                 if (pc.switchCameraMode >= 0)
/*    */                   gltd.camera.setMode(0, pc.switchCameraMode); 
/*    */                 if (pc.switchWeapon >= 0)
/*    */                   gltd.switchWeapon(pc.switchWeapon); 
/*    */                 if (pc.useWeapon)
/*    */                   gltd.useCurrentWeapon(pc.useWeaponOption1, pc.useWeaponOption2); 
/*    */               } 
/*    */             });
/*    */       }  }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gltd\MCH_GLTDPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */