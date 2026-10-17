/*    */ package com.norwood.mcheli;
/*    */ import com.norwood.mcheli.helper.client.MCH_CameraManager;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.stats.RecipeBook;
/*    */ import net.minecraft.stats.StatisticsManager;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.world.World;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class MCH_ViewEntityDummy extends EntityPlayerSP {
/* 13 */   private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/* 14 */   private static MCH_ViewEntityDummy instance = null;
/*    */   private float zoom;
/*    */   
/*    */   private MCH_ViewEntityDummy(World world) {
/* 18 */     super(Minecraft.func_71410_x(), world, Minecraft.func_71410_x().func_147114_u(), new StatisticsManager(), new RecipeBook());
/* 19 */     this.field_70737_aN = 0;
/* 20 */     this.field_70738_aO = 1;
/* 21 */     func_70105_a(1.0F, 1.0F);
/*    */   }
/*    */   
/*    */   public static MCH_ViewEntityDummy getInstance(World w) {
/* 25 */     if ((instance == null || instance.field_70128_L) && w.field_72995_K) {
/* 26 */       instance = new MCH_ViewEntityDummy(w);
/* 27 */       if ((Minecraft.func_71410_x()).field_71439_g != null) {
/* 28 */         instance.field_71158_b = (Minecraft.func_71410_x()).field_71439_g.field_71158_b;
/*    */       }
/*    */       
/* 31 */       instance.func_70107_b(0.0D, -4.0D, 0.0D);
/* 32 */       w.func_72838_d((Entity)instance);
/*    */     } 
/*    */     
/* 35 */     return instance;
/*    */   }
/*    */   
/*    */   public static void onUnloadWorld() {
/* 39 */     if (instance != null) {
/* 40 */       instance.func_70106_y();
/* 41 */       instance = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void setCameraPosition(double x, double y, double z) {
/* 46 */     if (instance != null) {
/* 47 */       instance.field_70169_q = x;
/* 48 */       instance.field_70167_r = y;
/* 49 */       instance.field_70166_s = z;
/* 50 */       instance.field_70142_S = x;
/* 51 */       instance.field_70137_T = y;
/* 52 */       instance.field_70136_U = z;
/* 53 */       instance.field_70165_t = x;
/* 54 */       instance.field_70163_u = y;
/* 55 */       instance.field_70161_v = z;
/*    */     } 
/*    */   }
/*    */   @NotNull
/*    */   public AxisAlignedBB func_174813_aQ() {
/* 60 */     return ZERO_AABB;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_70071_h_() {}
/*    */   
/*    */   public void update(MCH_Camera camera) {
/* 67 */     if (camera != null) {
/* 68 */       this.zoom = camera.getCameraZoom();
/* 69 */       this.field_70126_B = this.field_70177_z;
/* 70 */       this.field_70127_C = this.field_70125_A;
/* 71 */       this.field_70177_z = camera.rotationYaw;
/* 72 */       this.field_70125_A = camera.rotationPitch;
/* 73 */       this.field_70169_q = camera.posX;
/* 74 */       this.field_70167_r = camera.posY;
/* 75 */       this.field_70166_s = camera.posZ;
/* 76 */       this.field_70165_t = camera.posX;
/* 77 */       this.field_70163_u = camera.posY;
/* 78 */       this.field_70161_v = camera.posZ;
/* 79 */       MCH_CameraManager.setCameraZoom(this.zoom);
/*    */     } 
/*    */   }
/*    */   
/*    */   public float func_175156_o() {
/* 84 */     return super.func_175156_o() * 1.0F / this.zoom;
/*    */   }
/*    */   
/*    */   public float func_70047_e() {
/* 88 */     return 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_ViewEntityDummy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */