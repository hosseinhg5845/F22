/*     */ package com.norwood.mcheli.vehicle;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_ModelManager;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_RenderAircraft;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponSet;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_RenderVehicle extends MCH_RenderAircraft<MCH_EntityVehicle> {
/*  18 */   public static final IRenderFactory<MCH_EntityVehicle> FACTORY = MCH_RenderVehicle::new;
/*     */   
/*     */   public MCH_RenderVehicle(RenderManager renderManager) {
/*  21 */     super(renderManager);
/*  22 */     this.shadowSize = 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAircraft(MCH_EntityAircraft entity, double posX, double posY, double posZ, float yaw, float pitch, float roll, float tickTime) {
/*  28 */     if (entity instanceof MCH_EntityVehicle) { MCH_EntityVehicle vehicle = (MCH_EntityVehicle)entity;
/*  29 */       MCH_VehicleInfo vehicleInfo = vehicle.getVehicleInfo();
/*  30 */       if (vehicleInfo != null) {
/*  31 */         if (vehicle.getRiddenByEntity() != null && !vehicle.isDestroyed()) {
/*  32 */           vehicle.isUsedPlayer = true;
/*  33 */           vehicle.lastRiderYaw = (vehicle.getRiddenByEntity()).rotationYaw;
/*  34 */           vehicle.lastRiderPitch = (vehicle.getRiddenByEntity()).rotationPitch;
/*  35 */         } else if (!vehicle.isUsedPlayer) {
/*  36 */           vehicle.lastRiderYaw = vehicle.rotationYaw;
/*  37 */           vehicle.lastRiderPitch = vehicle.rotationPitch;
/*     */         } 
/*     */         
/*  40 */         posY += 0.3499999940395355D;
/*  41 */         renderDebugHitBox(vehicle, posX, posY, posZ, yaw, pitch);
/*  42 */         renderDebugPilotSeat(vehicle, posX, posY, posZ, yaw, pitch, roll);
/*  43 */         GL11.glTranslated(posX, posY, posZ);
/*  44 */         GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*  45 */         GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*  46 */         bindTexture("textures/vehicles/" + vehicle.getTextureName() + ".png", vehicle);
/*  47 */         renderBody(vehicleInfo.model);
/*  48 */         MCH_WeaponSet ws = vehicle.getFirstSeatWeapon();
/*  49 */         drawPart(vehicle, vehicleInfo, yaw, pitch, ws, tickTime);
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   public void drawPart(MCH_EntityVehicle vehicle, MCH_VehicleInfo info, float yaw, float pitch, MCH_WeaponSet ws, float tickTime) {
/*  55 */     float rotBrl = ws.prevRotBarrel + (ws.rotBarrel - ws.prevRotBarrel) * tickTime;
/*  56 */     int index = 0;
/*     */     
/*  58 */     for (MCH_VehicleInfo.VPart vp : info.partList) {
/*  59 */       index = drawPart(vp, vehicle, info, yaw, pitch, rotBrl, tickTime, ws, index);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int drawPart(MCH_VehicleInfo.VPart vp, MCH_EntityVehicle vehicle, MCH_VehicleInfo info, float yaw, float pitch, float rotBrl, float tickTime, MCH_WeaponSet ws, int index) {
/*  74 */     GL11.glPushMatrix();
/*  75 */     float recoilBuf = 0.0F;
/*  76 */     if (index < ws.getWeaponNum()) {
/*  77 */       MCH_WeaponSet.Recoil r = ws.recoilBuf[index];
/*  78 */       recoilBuf = r.prevRecoilBuf + (r.recoilBuf - r.prevRecoilBuf) * tickTime;
/*     */     } 
/*     */     
/*  81 */     int bkIndex = index;
/*  82 */     if (vp.rotPitch || vp.rotYaw || vp.type == 1) {
/*  83 */       GL11.glTranslated(vp.pos.x, vp.pos.y, vp.pos.z);
/*  84 */       if (vp.rotYaw) {
/*  85 */         GL11.glRotatef(-vehicle.lastRiderYaw + yaw, 0.0F, 1.0F, 0.0F);
/*     */       }
/*     */       
/*  88 */       if (vp.rotPitch) {
/*  89 */         float p = MCH_Lib.RNG(vehicle.lastRiderPitch, info.minRotationPitch, info.maxRotationPitch);
/*  90 */         GL11.glRotatef(p - pitch, 1.0F, 0.0F, 0.0F);
/*     */       } 
/*     */       
/*  93 */       if (vp.type == 1) {
/*  94 */         GL11.glRotatef(rotBrl, 0.0F, 0.0F, -1.0F);
/*     */       }
/*     */       
/*  97 */       GL11.glTranslated(-vp.pos.x, -vp.pos.y, -vp.pos.z);
/*     */     } 
/*     */     
/* 100 */     if (vp.type == 2) {
/* 101 */       GL11.glTranslated(0.0D, 0.0D, (-vp.recoilBuf * recoilBuf));
/*     */     }
/*     */     
/* 104 */     if (vp.type == 2 || vp.type == 3) {
/* 105 */       index++;
/*     */     }
/*     */     
/* 108 */     if (vp.child != null) {
/* 109 */       for (MCH_VehicleInfo.VPart vcp : vp.child) {
/* 110 */         index = drawPart(vcp, vehicle, info, yaw, pitch, rotBrl, recoilBuf, ws, index);
/*     */       }
/*     */     }
/*     */     
/* 114 */     if ((vp.drawFP || !W_Lib.isClientPlayer(vehicle.getRiddenByEntity()) || !W_Lib.isFirstPerson()) && (vp.type != 3 || 
/* 115 */       !vehicle.isWeaponNotCooldown(ws, bkIndex))) {
/* 116 */       renderPart(vp.model, info.model, vp.modelName);
/* 117 */       MCH_ModelManager.render("vehicles", vp.modelName);
/*     */     } 
/*     */     
/* 120 */     GL11.glPopMatrix();
/* 121 */     return index;
/*     */   }
/*     */   
/*     */   protected ResourceLocation getEntityTexture(MCH_EntityVehicle entity) {
/* 125 */     return TEX_DEFAULT;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\vehicle\MCH_RenderVehicle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */