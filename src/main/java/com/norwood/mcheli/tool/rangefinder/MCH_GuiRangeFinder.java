/*     */ package com.norwood.mcheli.tool.rangefinder;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_KeyName;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.gui.MCH_Gui;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiRangeFinder
/*     */   extends MCH_Gui {
/*     */   public MCH_GuiRangeFinder(Minecraft minecraft) {
/*  21 */     super(minecraft);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  26 */     super.initGui();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  31 */     return MCH_ItemRangeFinder.canUse(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  36 */     if (!isThirdPersonView) {
/*  37 */       GL11.glLineWidth(scaleFactor);
/*  38 */       if (isDrawGui(player)) {
/*  39 */         GL11.glDisable(3042);
/*  40 */         if (MCH_ItemRangeFinder.isUsingScope(player)) {
/*  41 */           drawRF(player);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void drawRF(EntityPlayer player) {
/*  48 */     GL11.glEnable(3042);
/*  49 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/*  50 */     int srcBlend = GL11.glGetInteger(3041);
/*  51 */     int dstBlend = GL11.glGetInteger(3040);
/*  52 */     GL11.glBlendFunc(770, 771);
/*  53 */     W_McClient.MOD_bindTexture("textures/gui/rangefinder.png");
/*  54 */     double size = 512.0D;
/*     */     
/*  56 */     while (size < this.width || size < this.height) {
/*  57 */       size *= 2.0D;
/*     */     }
/*     */     
/*  60 */     drawTexturedModalRectRotate(-(size - this.width) / 2.0D, -(size - this.height) / 2.0D, size, size, 0.0D, 0.0D, 256.0D, 256.0D, 0.0F);
/*  61 */     GL11.glBlendFunc(srcBlend, dstBlend);
/*  62 */     GL11.glDisable(3042);
/*  63 */     double factor = size / 512.0D;
/*  64 */     double SCALE_FACTOR = scaleFactor * factor;
/*  65 */     double CX = this.mc.displayWidth / 2.0D;
/*  66 */     double CY = this.mc.displayHeight / 2.0D;
/*  67 */     double px = (CX - 80.0D * SCALE_FACTOR) / SCALE_FACTOR;
/*  68 */     double py = (CY + 55.0D * SCALE_FACTOR) / SCALE_FACTOR;
/*  69 */     GL11.glPushMatrix();
/*  70 */     GL11.glScaled(factor, factor, factor);
/*  71 */     ItemStack item = player.getHeldItemMainhand();
/*  72 */     int damage = (int)((item.getMaxDamage() - item.getMetadata()) / item.getMaxDamage() * 100.0D);
/*  73 */     drawDigit(String.format("%3d", new Object[] { Integer.valueOf(damage) }), (int)px, (int)py, 13, (damage > 0) ? -15663328 : -61424);
/*  74 */     if (damage <= 0) {
/*  75 */       drawString("Please craft", (int)px + 40, (int)py, -65536);
/*  76 */       drawString("redstone", (int)px + 40, (int)py + 10, -65536);
/*     */     } 
/*     */     
/*  79 */     px = (CX - 20.0D * SCALE_FACTOR) / SCALE_FACTOR;
/*  80 */     if (damage > 0) {
/*  81 */       Vec3d vs = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
/*  82 */       Vec3d ve = MCH_Lib.Rot2Vec3(player.rotationYaw, player.rotationPitch);
/*  83 */       ve = vs.addVector(ve.x * 300.0D, ve.y * 300.0D, ve.z * 300.0D);
/*  84 */       RayTraceResult mop = player.world.rayTraceBlocks(vs, ve, true);
/*  85 */       if (mop != null && mop.typeOfHit != RayTraceResult.Type.MISS) {
/*  86 */         int range = (int)player.getDistance(mop.hitVec.x, mop.hitVec.y, mop.hitVec.z);
/*  87 */         drawDigit(String.format("%4d", new Object[] { Integer.valueOf(range) }), (int)px, (int)py, 13, -15663328);
/*     */       } else {
/*  89 */         drawDigit("----", (int)px, (int)py, 13, -61424);
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     py -= 4.0D;
/*  94 */     px -= 80.0D;
/*  95 */     drawRect((int)px, (int)py, (int)px + 30, (int)py + 2, -15663328);
/*  96 */     drawRect((int)px, (int)py, (int)px + MCH_ItemRangeFinder.rangeFinderUseCooldown / 2, (int)py + 2, -61424);
/*  97 */     drawString(String.format("x%.1f", new Object[] { Float.valueOf(MCH_ItemRangeFinder.zoom) }), (int)px, (int)py - 20, -1);
/*  98 */     px += 130.0D;
/*  99 */     int mode = MCH_ItemRangeFinder.mode;
/* 100 */     drawString(">", (int)px, (int)py - 30 + mode * 10, -1);
/* 101 */     px += 10.0D;
/* 102 */     drawString("Players/Vehicles", (int)px, (int)py - 30, (mode == 0) ? -1 : -12566464);
/* 103 */     drawString("Monsters/Mobs", (int)px, (int)py - 20, (mode == 1) ? -1 : -12566464);
/* 104 */     drawString("Mark Point", (int)px, (int)py - 10, (mode == 2) ? -1 : -12566464);
/* 105 */     GL11.glPopMatrix();
/* 106 */     px = (CX - 160.0D * SCALE_FACTOR) / scaleFactor;
/* 107 */     py = (CY - 100.0D * SCALE_FACTOR) / scaleFactor;
/* 108 */     if (px < 10.0D) {
/* 109 */       px = 10.0D;
/*     */     }
/*     */     
/* 112 */     if (py < 10.0D) {
/* 113 */       py = 10.0D;
/*     */     }
/*     */     
/* 116 */     String s = "Spot      : " + MCH_KeyName.getDescOrName(MCH_Config.KeyAttack.prmInt);
/* 117 */     drawString(s, (int)px, (int)py, -1);
/* 118 */     s = "Zoom in   : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/* 119 */     drawString(s, (int)px, (int)py + 10, (MCH_ItemRangeFinder.zoom < 10.0F) ? -1 : -12566464);
/* 120 */     s = "Zoom out : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt);
/* 121 */     drawString(s, (int)px, (int)py + 20, (MCH_ItemRangeFinder.zoom > 1.2F) ? -1 : -12566464);
/* 122 */     s = "Mode      : " + MCH_KeyName.getDescOrName(MCH_Config.KeyFlare.prmInt);
/* 123 */     drawString(s, (int)px, (int)py + 30, -1);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tool\rangefinder\MCH_GuiRangeFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */