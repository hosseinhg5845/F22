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
/* 18 */     super(Minecraft.getMinecraft(), world, Minecraft.getMinecraft().getConnection(), new StatisticsManager(), new RecipeBook());
/* 19 */     this.hurtTime = 0;
/* 20 */     this.maxHurtTime = 1;
/* 21 */     setSize(1.0F, 1.0F);
/*    */   }
/*    */   
/*    */   public static MCH_ViewEntityDummy getInstance(World w) {
/* 25 */     if ((instance == null || instance.isDead) && w.isRemote) {
/* 26 */       instance = new MCH_ViewEntityDummy(w);
/* 27 */       if ((Minecraft.getMinecraft()).player != null) {
/* 28 */         instance.movementInput = (Minecraft.getMinecraft()).player.movementInput;
/*    */       }
/*    */       
/* 31 */       instance.setPosition(0.0D, -4.0D, 0.0D);
/* 32 */       w.spawnEntity((Entity)instance);
/*    */     } 
/*    */     
/* 35 */     return instance;
/*    */   }
/*    */   
/*    */   public static void onUnloadWorld() {
/* 39 */     if (instance != null) {
/* 40 */       instance.setDead();
/* 41 */       instance = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void setCameraPosition(double x, double y, double z) {
/* 46 */     if (instance != null) {
/* 47 */       instance.prevPosX = x;
/* 48 */       instance.prevPosY = y;
/* 49 */       instance.prevPosZ = z;
/* 50 */       instance.lastTickPosX = x;
/* 51 */       instance.lastTickPosY = y;
/* 52 */       instance.lastTickPosZ = z;
/* 53 */       instance.posX = x;
/* 54 */       instance.posY = y;
/* 55 */       instance.posZ = z;
/*    */     } 
/*    */   }
/*    */   @NotNull
/*    */   public AxisAlignedBB getEntityBoundingBox() {
/* 60 */     return ZERO_AABB;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {}
/*    */   
/*    */   public void update(MCH_Camera camera) {
/* 67 */     if (camera != null) {
/* 68 */       this.zoom = camera.getCameraZoom();
/* 69 */       this.prevRotationYaw = this.rotationYaw;
/* 70 */       this.prevRotationPitch = this.rotationPitch;
/* 71 */       this.rotationYaw = camera.rotationYaw;
/* 72 */       this.rotationPitch = camera.rotationPitch;
/* 73 */       this.prevPosX = camera.posX;
/* 74 */       this.prevPosY = camera.posY;
/* 75 */       this.prevPosZ = camera.posZ;
/* 76 */       this.posX = camera.posX;
/* 77 */       this.posY = camera.posY;
/* 78 */       this.posZ = camera.posZ;
/* 79 */       MCH_CameraManager.setCameraZoom(this.zoom);
/*    */     } 
/*    */   }
/*    */   
/*    */   public float getFovModifier() {
/* 84 */     return super.getFovModifier() * 1.0F / this.zoom;
/*    */   }
/*    */   
/*    */   public float getEyeHeight() {
/* 88 */     return 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_ViewEntityDummy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */