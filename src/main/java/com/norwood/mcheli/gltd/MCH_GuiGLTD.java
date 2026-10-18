/*     */ package com.norwood.mcheli.gltd;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Camera;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_KeyName;
/*     */ import com.norwood.mcheli.gui.MCH_Gui;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiGLTD extends MCH_Gui {
/*     */   public MCH_GuiGLTD(Minecraft minecraft) {
/*  23 */     super(minecraft);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  28 */     super.initGui();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  33 */     return (player.getRidingEntity() != null && player.getRidingEntity() instanceof MCH_EntityGLTD);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  38 */     if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool) {
/*  39 */       GL11.glLineWidth(scaleFactor);
/*  40 */       if (isDrawGui(player)) {
/*  41 */         MCH_EntityGLTD gltd = (MCH_EntityGLTD)player.getRidingEntity();
/*  42 */         if (gltd.camera.getMode(0) == 1) {
/*  43 */           GL11.glEnable(3042);
/*  44 */           GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.3F);
/*  45 */           int srcBlend = GL11.glGetInteger(3041);
/*  46 */           int dstBlend = GL11.glGetInteger(3040);
/*  47 */           GL11.glBlendFunc(1, 1);
/*  48 */           W_McClient.MOD_bindTexture("textures/gui/alpha.png");
/*  49 */           drawTexturedModalRectRotate(0.0D, 0.0D, this.width, this.height, this.rand.nextInt(256), this.rand.nextInt(256), 256.0D, 256.0D, 0.0F);
/*  50 */           GL11.glBlendFunc(srcBlend, dstBlend);
/*  51 */           GL11.glDisable(3042);
/*     */         } 
/*     */         
/*  54 */         drawString(String.format("x%.1f", new Object[] { Float.valueOf(gltd.camera.getCameraZoom()) }), this.centerX - 70, this.centerY + 10, -805306369);
/*  55 */         drawString(gltd.weaponCAS.getName(), this.centerX - 200, this.centerY + 65, (gltd.countWait == 0) ? -819986657 : -807468024);
/*  56 */         drawCommonPosition(gltd, -819986657);
/*  57 */         drawString(gltd.camera.getModeName(0), this.centerX + 30, this.centerY - 50, -819986657);
/*  58 */         drawSight(gltd.camera, -819986657);
/*  59 */         drawTargetPosition(gltd, -819986657, -807468024);
/*  60 */         drawKeyBind(gltd.camera, -805306369, -813727873);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawKeyBind(MCH_Camera camera, int color, int colorCannotUse) {
/*  66 */     int OffX = this.centerX + 55;
/*  67 */     int OffY = this.centerY + 40;
/*  68 */     drawString("DISMOUNT :", OffX, OffY, color);
/*  69 */     drawString("CAM MODE :", OffX, OffY + 10, color);
/*  70 */     drawString("ZOOM IN   :", OffX, OffY + 20, (camera.getCameraZoom() < 10.0F) ? color : colorCannotUse);
/*  71 */     drawString("ZOOM OUT :", OffX, OffY + 30, (camera.getCameraZoom() > 1.0F) ? color : colorCannotUse);
/*  72 */     OffX += 60;
/*  73 */     drawString(MCH_KeyName.getDescOrName(42) + " or " + MCH_KeyName.getDescOrName(MCH_Config.KeyUnmount.prmInt), OffX, OffY, color);
/*  74 */     drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt), OffX, OffY + 10, color);
/*  75 */     drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt), OffX, OffY + 20, (camera.getCameraZoom() < 10.0F) ? color : colorCannotUse);
/*  76 */     drawString(MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt), OffX, OffY + 30, (camera.getCameraZoom() > 1.0F) ? color : colorCannotUse);
/*     */   }
/*     */   
/*     */   public void drawCommonPosition(MCH_EntityGLTD gltd, int color) {
/*  80 */     Entity riddenByEntity = gltd.getRiddenByEntity();
/*  81 */     drawString(String.format("X: %+.1f", new Object[] { Double.valueOf(gltd.posX) }), this.centerX - 145, this.centerY, color);
/*  82 */     drawString(String.format("Y: %+.1f", new Object[] { Double.valueOf(gltd.posY) }), this.centerX - 145, this.centerY + 10, color);
/*  83 */     drawString(String.format("Z: %+.1f", new Object[] { Double.valueOf(gltd.posZ) }), this.centerX - 145, this.centerY + 20, color);
/*  84 */     drawString(String.format("AX: %+.1f", new Object[] { Float.valueOf(riddenByEntity.rotationYaw) }), this.centerX - 145, this.centerY + 40, color);
/*  85 */     drawString(String.format("AY: %+.1f", new Object[] { Float.valueOf(riddenByEntity.rotationPitch) }), this.centerX - 145, this.centerY + 50, color);
/*     */   }
/*     */   
/*     */   public void drawTargetPosition(MCH_EntityGLTD gltd, int color, int colorDanger) {
/*  89 */     Entity riddenByEntity = gltd.getRiddenByEntity();
/*  90 */     if (riddenByEntity != null) {
/*  91 */       World w = riddenByEntity.world;
/*  92 */       float yaw = riddenByEntity.rotationYaw;
/*  93 */       float pitch = riddenByEntity.rotationPitch;
/*  94 */       double tX = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/*  95 */       double tZ = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
/*  96 */       double tY = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
/*  97 */       double dist = MathHelper.sqrt(tX * tX + tY * tY + tZ * tZ);
/*  98 */       tX = tX * 80.0D / dist;
/*  99 */       tY = tY * 80.0D / dist;
/* 100 */       tZ = tZ * 80.0D / dist;
/* 101 */       MCH_Camera c = gltd.camera;
/* 102 */       Vec3d src = W_WorldFunc.getWorldVec3(w, c.posX, c.posY, c.posZ);
/* 103 */       Vec3d dst = W_WorldFunc.getWorldVec3(w, c.posX + tX, c.posY + tY, c.posZ + tZ);
/* 104 */       RayTraceResult m = W_WorldFunc.clip(w, src, dst);
/* 105 */       if (m != null) {
/* 106 */         drawString(String.format("X: %+.2fm", new Object[] { Double.valueOf(m.hitVec.x) }), this.centerX + 50, this.centerY - 5 - 15, color);
/* 107 */         drawString(String.format("Y: %+.2fm", new Object[] { Double.valueOf(m.hitVec.y) }), this.centerX + 50, this.centerY - 5, color);
/* 108 */         drawString(String.format("Z: %+.2fm", new Object[] { Double.valueOf(m.hitVec.z) }), this.centerX + 50, this.centerY - 5 + 15, color);
/* 109 */         double x = m.hitVec.x - c.posX;
/* 110 */         double y = m.hitVec.y - c.posY;
/* 111 */         double z = m.hitVec.z - c.posZ;
/* 112 */         double len = Math.sqrt(x * x + y * y + z * z);
/* 113 */         drawCenteredString(String.format("[%.2fm]", new Object[] { Double.valueOf(len) }), this.centerX, this.centerY + 30, (len > 20.0D) ? color : colorDanger);
/*     */       } else {
/* 115 */         drawString("X: --.--m", this.centerX + 50, this.centerY - 5 - 15, color);
/* 116 */         drawString("Y: --.--m", this.centerX + 50, this.centerY - 5, color);
/* 117 */         drawString("Z: --.--m", this.centerX + 50, this.centerY - 5 + 15, color);
/* 118 */         drawCenteredString("[--.--m]", this.centerX, this.centerY + 30, colorDanger);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawSight(MCH_Camera camera, int color) {
/* 124 */     double posX = this.centerX;
/* 125 */     double posY = this.centerY;
/* 126 */     double[] line2 = { posX - 30.0D, posY - 10.0D, posX - 30.0D, posY - 20.0D, posX - 30.0D, posY - 20.0D, posX - 10.0D, posY - 20.0D, posX - 30.0D, posY + 10.0D, posX - 30.0D, posY + 20.0D, posX - 30.0D, posY + 20.0D, posX - 10.0D, posY + 20.0D, posX + 30.0D, posY - 10.0D, posX + 30.0D, posY - 20.0D, posX + 30.0D, posY - 20.0D, posX + 10.0D, posY - 20.0D, posX + 30.0D, posY + 10.0D, posX + 30.0D, posY + 20.0D, posX + 30.0D, posY + 20.0D, posX + 10.0D, posY + 20.0D };
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
/* 160 */     drawLine(line2, color);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gltd\MCH_GuiGLTD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */