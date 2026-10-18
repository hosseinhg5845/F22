/*     */ package com.norwood.mcheli.particles;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.helper.entity.ITargetMarkerObject;
/*     */ import com.norwood.mcheli.multiplay.MCH_GuiTargetMarker;
/*     */ import com.norwood.mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCH_EntityParticleMarkPoint
/*     */   extends MCH_EntityParticleBase implements ITargetMarkerObject {
/*     */   final Team team;
/*     */   
/*     */   public MCH_EntityParticleMarkPoint(World par1World, double x, double y, double z, Team team) {
/*  23 */     super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
/*  24 */     setParticleMaxAge(30);
/*  25 */     this.team = team;
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/*  29 */     this.prevPosX = this.posX;
/*  30 */     this.prevPosY = this.posY;
/*  31 */     this.prevPosZ = this.posZ;
/*  32 */     EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).player;
/*  33 */     if (entityPlayerSP == null) {
/*  34 */       setExpired();
/*     */     } else {
/*  36 */       entityPlayerSP.getTeam();
/*  37 */       entityPlayerSP.getTeam();
/*  38 */       if (!entityPlayerSP.isOnScoreboardTeam(this.team)) {
/*  39 */         setExpired();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setExpired() {
/*  45 */     super.setExpired();
/*  46 */     MCH_Lib.DbgLog(true, "MCH_EntityParticleMarkPoint.setExpired : " + this, new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/*  51 */     return 3;
/*     */   }
/*     */   
/*     */   public void renderParticle(@NotNull BufferBuilder buffer, @NotNull Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
/*  55 */     GL11.glPushMatrix();
/*  56 */     Minecraft mc = Minecraft.getMinecraft();
/*  57 */     EntityPlayerSP entityPlayerSP = mc.player;
/*  58 */     if (entityPlayerSP != null) {
/*  59 */       double ix = interpPosX;
/*  60 */       double iy = interpPosY;
/*  61 */       double iz = interpPosZ;
/*  62 */       if (mc.gameSettings.thirdPersonView > 0) {
/*  63 */         double dist = W_Reflection.getThirdPersonDistance();
/*  64 */         float yaw = -entityIn.rotationYaw;
/*  65 */         float pitch = -entityIn.rotationPitch;
/*  66 */         Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, -dist, yaw, pitch);
/*  67 */         if (mc.gameSettings.thirdPersonView == 2) {
/*  68 */           v = new Vec3d(-v.x, -v.y, -v.z);
/*     */         }
/*     */         
/*  71 */         Vec3d vs = new Vec3d(entityIn.posX, entityIn.posY + entityIn.getEyeHeight(), entityIn.posZ);
/*  72 */         RayTraceResult mop = entityIn.world.rayTraceBlocks(vs.addVector(0.0D, 0.0D, 0.0D), vs.addVector(v.x, v.y, v.z));
/*  73 */         double block_dist = dist;
/*  74 */         if (mop != null && mop.typeOfHit == RayTraceResult.Type.BLOCK) {
/*  75 */           block_dist = vs.distanceTo(mop.hitVec) - 0.4D;
/*  76 */           if (block_dist < 0.0D) {
/*  77 */             block_dist = 0.0D;
/*     */           }
/*     */         } 
/*     */         
/*  81 */         GL11.glTranslated(v.x * block_dist / dist, v.y * block_dist / dist, v.z * block_dist / dist);
/*  82 */         ix += v.x * block_dist / dist;
/*  83 */         iy += v.y * block_dist / dist;
/*  84 */         iz += v.z * block_dist / dist;
/*     */       } 
/*     */       
/*  87 */       double px = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - ix);
/*  88 */       double py = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - iy);
/*  89 */       double pz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - iz);
/*  90 */       double scale = Math.sqrt(px * px + py * py + pz * pz) / 10.0D;
/*  91 */       if (scale < 1.0D) {
/*  92 */         scale = 1.0D;
/*     */       }
/*     */       
/*  95 */       MCH_GuiTargetMarker.addMarkEntityPos(100, this, px / scale, py / scale, pz / scale, false);
/*  96 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/* 102 */     return this.posX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/* 107 */     return this.posY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 112 */     return this.posZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\particles\MCH_EntityParticleMarkPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */