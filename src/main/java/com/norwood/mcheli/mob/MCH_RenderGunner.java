/*     */ package com.norwood.mcheli.mob;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_RenderGunner extends RenderLivingBase<MCH_EntityGunner> {
/*  19 */   public static final IRenderFactory<MCH_EntityGunner> FACTORY = MCH_RenderGunner::new;
/*  20 */   private static final ResourceLocation steveTextures = new ResourceLocation("mcheli", "textures/mob/heligunner.png");
/*  21 */   public final ModelBiped modelBipedMain = (ModelBiped)this.mainModel;
/*  22 */   public final ModelBiped modelArmorChestplate = new ModelBiped(1.0F);
/*  23 */   public final ModelBiped modelArmor = new ModelBiped(0.5F);
/*     */   
/*     */   public MCH_RenderGunner(RenderManager renderManager) {
/*  26 */     super(renderManager, (ModelBase)new ModelBiped(0.0F), 0.5F);
/*     */   }
/*     */   
/*     */   protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
/*  30 */     return -1;
/*     */   }
/*     */   
/*     */   protected boolean canRenderName(MCH_EntityGunner targetEntity) {
/*  34 */     return (targetEntity.getTeam() != null);
/*     */   }
/*     */   
/*     */   public void doRender(MCH_EntityGunner entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  38 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*  39 */     this.modelBipedMain.isSneak = entity.isSneaking();
/*  40 */     double d3 = y;
/*  41 */     if (entity.isSneaking()) {
/*  42 */       d3 = y - 0.125D;
/*     */     }
/*     */     
/*  45 */     MCH_EntityAircraft ac = entity.getAc();
/*  46 */     if (ac != null && ac.getAcInfo() != null && (!(ac.getAcInfo()).hideEntity || !ac.isPilot((Entity)entity))) {
/*  47 */       super.doRender(entity, x, d3, z, entityYaw, partialTicks);
/*     */     }
/*     */     
/*  50 */     this.modelBipedMain.isSneak = false;
/*  51 */     this.modelBipedMain.rightArmPose = ModelBiped.ArmPose.EMPTY;
/*     */   }
/*     */   
/*     */   protected void preRenderCallback(@NotNull MCH_EntityGunner entitylivingbaseIn, float partialTickTime) {
/*  55 */     float f1 = 0.9375F;
/*  56 */     GL11.glScalef(f1, f1, f1);
/*     */   }
/*     */   
/*     */   public void renderFirstPersonArm(EntityPlayer p_82441_1_) {
/*  60 */     float f = 1.0F;
/*  61 */     GL11.glColor3f(f, f, f);
/*  62 */     this.modelBipedMain.swingProgress = 0.0F;
/*  63 */     this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)p_82441_1_);
/*  64 */     this.modelBipedMain.bipedRightArm.render(0.0625F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _renderOffsetLivingLabel(MCH_EntityGunner p_96449_1_, double p_96449_2_, double p_96449_4_, double p_96449_6_, String p_96449_8_, float p_96449_9_, double p_96449_10_) {}
/*     */ 
/*     */   
/*     */   protected void _preRenderCallback(MCH_EntityGunner entitylivingbaseIn, float partialTickTime) {
/*  73 */     preRenderCallback(entitylivingbaseIn, partialTickTime);
/*     */   }
/*     */   
/*     */   protected int _shouldRenderPass(MCH_EntityGunner p_77032_1_, int p_77032_2_, float p_77032_3_) {
/*  77 */     return shouldRenderPass(p_77032_1_, p_77032_2_, p_77032_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _renderEquippedItems(MCH_EntityGunner p_77029_1_, float p_77029_2_) {}
/*     */   
/*     */   protected void _rotateCorpse(MCH_EntityGunner entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
/*  84 */     applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
/*     */   }
/*     */   
/*     */   protected void _renderLivingAt(MCH_EntityGunner entityLivingBaseIn, double x, double y, double z) {
/*  88 */     renderLivingAt(entityLivingBaseIn, x, y, z);
/*     */   }
/*     */   
/*     */   public void _doRender(MCH_EntityGunner entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  92 */     doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */   
/*     */   protected ResourceLocation getEntityTexture(@NotNull MCH_EntityGunner entity) {
/*  96 */     return steveTextures;
/*     */   }
/*     */   
/*     */   public void _doRender2(MCH_EntityGunner entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 100 */     doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\mob\MCH_RenderGunner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */