/*    */ package com.norwood.mcheli.helicopter;
/*    */ import com.norwood.mcheli.aircraft.MCH_Blade;
/*    */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*    */ import com.norwood.mcheli.aircraft.MCH_RenderAircraft;
/*    */ import com.norwood.mcheli.aircraft.MCH_Rotor;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderHeli extends MCH_RenderAircraft<MCH_EntityHeli> {
/* 16 */   public static final IRenderFactory<MCH_EntityHeli> FACTORY = MCH_RenderHeli::new;
/*    */   
/*    */   public MCH_RenderHeli(RenderManager renderManager) {
/* 19 */     super(renderManager);
/* 20 */     this.shadowSize = 2.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderAircraft(MCH_EntityAircraft entity, double posX, double posY, double posZ, float yaw, float pitch, float roll, float tickTime) {
/* 26 */     if (entity instanceof MCH_EntityHeli) { MCH_EntityHeli heli = (MCH_EntityHeli)entity;
/* 27 */       MCH_HeliInfo heliInfo = heli.getHeliInfo();
/* 28 */       if (heliInfo != null) {
/* 29 */         posY += 0.3499999940395355D;
/* 30 */         renderDebugHitBox(heli, posX, posY, posZ, yaw, pitch);
/* 31 */         renderDebugPilotSeat(heli, posX, posY, posZ, yaw, pitch, roll);
/* 32 */         GL11.glTranslated(posX, posY, posZ);
/* 33 */         GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/* 34 */         GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/* 35 */         GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
/* 36 */         bindTexture("textures/helicopters/" + heli.getTextureName() + ".png", heli);
/* 37 */         renderBody(heliInfo.model);
/* 38 */         drawModelBlade(heli, heliInfo, tickTime);
/*    */       }  }
/*    */   
/*    */   }
/*    */   
/*    */   public void drawModelBlade(MCH_EntityHeli heli, MCH_HeliInfo info, float tickTime) {
/* 44 */     for (int i = 0; i < heli.rotors.length && i < info.rotorList.size(); i++) {
/* 45 */       MCH_HeliInfo.Rotor rotorInfo = info.rotorList.get(i);
/* 46 */       MCH_Rotor rotor = heli.rotors[i];
/* 47 */       GL11.glPushMatrix();
/* 48 */       if (rotorInfo.oldRenderMethod) {
/* 49 */         GL11.glTranslated(rotorInfo.pos.x, rotorInfo.pos.y, rotorInfo.pos.z);
/*    */       }
/*    */       
/* 52 */       for (MCH_Blade b : rotor.blades) {
/* 53 */         GL11.glPushMatrix();
/* 54 */         float rot = b.getRotation();
/* 55 */         float prevRot = b.getPrevRotation();
/* 56 */         if (rot - prevRot < -180.0F) {
/* 57 */           prevRot -= 360.0F;
/* 58 */         } else if (prevRot - rot < -180.0F) {
/* 59 */           prevRot += 360.0F;
/*    */         } 
/*    */         
/* 62 */         if (!rotorInfo.oldRenderMethod) {
/* 63 */           GL11.glTranslated(rotorInfo.pos.x, rotorInfo.pos.y, rotorInfo.pos.z);
/*    */         }
/*    */         
/* 66 */         GL11.glRotatef(prevRot + (rot - prevRot) * tickTime, (float)rotorInfo.rot.x, (float)rotorInfo.rot.y, (float)rotorInfo.rot.z);
/* 67 */         if (!rotorInfo.oldRenderMethod) {
/* 68 */           GL11.glTranslated(-rotorInfo.pos.x, -rotorInfo.pos.y, -rotorInfo.pos.z);
/*    */         }
/*    */         
/* 71 */         renderPart(rotorInfo.model, info.model, rotorInfo.modelName);
/* 72 */         GL11.glPopMatrix();
/*    */       } 
/*    */       
/* 75 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityHeli entity) {
/* 80 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helicopter\MCH_RenderHeli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */