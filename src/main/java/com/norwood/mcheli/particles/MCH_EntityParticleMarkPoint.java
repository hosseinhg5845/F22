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
/*     */   public void func_189213_a() {
/*  29 */     this.field_187123_c = this.field_187126_f;
/*  30 */     this.field_187124_d = this.field_187127_g;
/*  31 */     this.field_187125_e = this.field_187128_h;
/*  32 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*  33 */     if (entityPlayerSP == null) {
/*  34 */       func_187112_i();
/*     */     } else {
/*  36 */       entityPlayerSP.func_96124_cp();
/*  37 */       entityPlayerSP.func_96124_cp();
/*  38 */       if (!entityPlayerSP.func_184194_a(this.team)) {
/*  39 */         func_187112_i();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_187112_i() {
/*  45 */     super.func_187112_i();
/*  46 */     MCH_Lib.DbgLog(true, "MCH_EntityParticleMarkPoint.setExpired : " + this, new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70537_b() {
/*  51 */     return 3;
/*     */   }
/*     */   
/*     */   public void func_180434_a(@NotNull BufferBuilder buffer, @NotNull Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
/*  55 */     GL11.glPushMatrix();
/*  56 */     Minecraft mc = Minecraft.func_71410_x();
/*  57 */     EntityPlayerSP entityPlayerSP = mc.field_71439_g;
/*  58 */     if (entityPlayerSP != null) {
/*  59 */       double ix = field_70556_an;
/*  60 */       double iy = field_70554_ao;
/*  61 */       double iz = field_70555_ap;
/*  62 */       if (mc.field_71474_y.field_74320_O > 0) {
/*  63 */         double dist = W_Reflection.getThirdPersonDistance();
/*  64 */         float yaw = -entityIn.field_70177_z;
/*  65 */         float pitch = -entityIn.field_70125_A;
/*  66 */         Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, -dist, yaw, pitch);
/*  67 */         if (mc.field_71474_y.field_74320_O == 2) {
/*  68 */           v = new Vec3d(-v.field_72450_a, -v.field_72448_b, -v.field_72449_c);
/*     */         }
/*     */         
/*  71 */         Vec3d vs = new Vec3d(entityIn.field_70165_t, entityIn.field_70163_u + entityIn.func_70047_e(), entityIn.field_70161_v);
/*  72 */         RayTraceResult mop = entityIn.field_70170_p.func_72933_a(vs.func_72441_c(0.0D, 0.0D, 0.0D), vs.func_72441_c(v.field_72450_a, v.field_72448_b, v.field_72449_c));
/*  73 */         double block_dist = dist;
/*  74 */         if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK) {
/*  75 */           block_dist = vs.func_72438_d(mop.field_72307_f) - 0.4D;
/*  76 */           if (block_dist < 0.0D) {
/*  77 */             block_dist = 0.0D;
/*     */           }
/*     */         } 
/*     */         
/*  81 */         GL11.glTranslated(v.field_72450_a * block_dist / dist, v.field_72448_b * block_dist / dist, v.field_72449_c * block_dist / dist);
/*  82 */         ix += v.field_72450_a * block_dist / dist;
/*  83 */         iy += v.field_72448_b * block_dist / dist;
/*  84 */         iz += v.field_72449_c * block_dist / dist;
/*     */       } 
/*     */       
/*  87 */       double px = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * par2 - ix);
/*  88 */       double py = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * par2 - iy);
/*  89 */       double pz = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * par2 - iz);
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
/* 102 */     return this.field_187126_f;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/* 107 */     return this.field_187127_g;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 112 */     return this.field_187128_h;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\particles\MCH_EntityParticleMarkPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */