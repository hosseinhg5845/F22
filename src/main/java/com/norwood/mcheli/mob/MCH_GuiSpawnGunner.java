/*     */ package com.norwood.mcheli.mob;
/*     */ 
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */ import com.norwood.mcheli.gui.MCH_Gui;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiSpawnGunner
/*     */   extends MCH_Gui {
/*     */   public MCH_GuiSpawnGunner(Minecraft minecraft) {
/*  20 */     super(minecraft);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  25 */     super.initGui();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  30 */     return (player != null && player.world != null && 
/*     */       
/*  32 */       !player.getHeldItemMainhand().isEmpty() && player
/*  33 */       .getHeldItemMainhand().getItem() instanceof MCH_ItemSpawnGunner);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  38 */     if (!isThirdPersonView && 
/*  39 */       isDrawGui(player)) {
/*  40 */       GL11.glLineWidth(scaleFactor);
/*  41 */       GL11.glDisable(3042);
/*  42 */       draw(player, searchTarget(player));
/*     */     } 
/*     */   }
/*     */   private Entity searchTarget(EntityPlayer player) {
/*     */     MCH_EntityGunner mCH_EntityGunner;
/*     */     MCH_EntityAircraft mCH_EntityAircraft;
/*  48 */     float f = 1.0F;
/*  49 */     float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
/*  50 */     float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
/*  51 */     double dx = player.prevPosX + (player.posX - player.prevPosX) * f;
/*  52 */     double dy = player.prevPosY + (player.posY - player.prevPosY) * f + player.getEyeHeight();
/*  53 */     double dz = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
/*  54 */     Vec3d vec3 = new Vec3d(dx, dy, dz);
/*  55 */     float f3 = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
/*  56 */     float f4 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
/*  57 */     float f5 = -MathHelper.cos(-pitch * 0.017453292F);
/*  58 */     float f6 = MathHelper.sin(-pitch * 0.017453292F);
/*  59 */     float f7 = f4 * f5;
/*  60 */     float f8 = f3 * f5;
/*  61 */     double d3 = 5.0D;
/*  62 */     Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
/*  63 */     Entity target = null;
/*  64 */     List<MCH_EntityGunner> list = player.world.getEntitiesWithinAABB(MCH_EntityGunner.class, player.getEntityBoundingBox().grow(5.0D, 5.0D, 5.0D));
/*     */     
/*  66 */     for (MCH_EntityGunner gunner : list) {
/*  67 */       if (gunner.getEntityBoundingBox().calculateIntercept(vec3, vec31) != null && (target == null || player.getDistanceSq((Entity)gunner) < player.getDistanceSq(target))) {
/*  68 */         mCH_EntityGunner = gunner;
/*     */       }
/*     */     } 
/*     */     
/*  72 */     if (mCH_EntityGunner == null) {
/*  73 */       MCH_EntitySeat mCH_EntitySeat; MCH_ItemSpawnGunner item = (MCH_ItemSpawnGunner)player.getHeldItemMainhand().getItem();
/*  74 */       if (item.targetType == 1 && !player.world.isRemote) {
/*  75 */         player.getTeam();
/*     */       }
/*  77 */       List<MCH_EntitySeat> list1 = player.world.getEntitiesWithinAABB(MCH_EntitySeat.class, player.getEntityBoundingBox().grow(5.0D, 5.0D, 5.0D));
/*     */       
/*  79 */       for (MCH_EntitySeat seat : list1) {
/*  80 */         if (seat.getParent() != null && seat
/*  81 */           .getParent().getAcInfo() != null && seat
/*  82 */           .getEntityBoundingBox().calculateIntercept(vec3, vec31) != null && (mCH_EntityGunner == null || player
/*  83 */           .getDistanceSq((Entity)seat) < player.getDistanceSq((Entity)mCH_EntityGunner))) {
/*  84 */           if (seat.getRiddenByEntity() instanceof MCH_EntityGunner) {
/*  85 */             Entity entity = seat.getRiddenByEntity(); continue;
/*     */           } 
/*  87 */           mCH_EntitySeat = seat;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  92 */       if (mCH_EntitySeat == null) {
/*     */         
/*  94 */         List<MCH_EntityAircraft> list2 = player.world.getEntitiesWithinAABB(MCH_EntityAircraft.class, player.getEntityBoundingBox().grow(5.0D, 5.0D, 5.0D));
/*     */         
/*  96 */         for (MCH_EntityAircraft ac : list2) {
/*  97 */           if (!ac.isUAV() && ac
/*  98 */             .getAcInfo() != null && ac
/*  99 */             .getEntityBoundingBox().calculateIntercept(vec3, vec31) != null && (mCH_EntitySeat == null || player
/* 100 */             .getDistanceSq((Entity)ac) < player.getDistanceSq((Entity)mCH_EntitySeat))) {
/* 101 */             if (ac.getRiddenByEntity() instanceof MCH_EntityGunner) {
/* 102 */               Entity entity = ac.getRiddenByEntity(); continue;
/*     */             } 
/* 104 */             mCH_EntityAircraft = ac;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 111 */     return (Entity)mCH_EntityAircraft;
/*     */   }
/*     */   
/*     */   void draw(EntityPlayer player, Entity entity) {
/* 115 */     if (entity != null) {
/* 116 */       GL11.glEnable(3042);
/* 117 */       GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/* 118 */       int srcBlend = GL11.glGetInteger(3041);
/* 119 */       int dstBlend = GL11.glGetInteger(3040);
/* 120 */       GL11.glBlendFunc(770, 771);
/* 121 */       double size = 512.0D;
/*     */       
/* 123 */       while (size < this.width || size < this.height) {
/* 124 */         size *= 2.0D;
/*     */       }
/*     */       
/* 127 */       GL11.glBlendFunc(srcBlend, dstBlend);
/* 128 */       GL11.glDisable(3042);
/* 129 */       double factor = size / 512.0D;
/* 130 */       double SCALE_FACTOR = scaleFactor * factor;
/* 131 */       double CX = this.mc.displayWidth / 2.0D;
/* 132 */       double CY = this.mc.displayHeight / 2.0D;
/* 133 */       double px = (CX - 0.0D) / SCALE_FACTOR;
/* 134 */       double py = (CY + 0.0D) / SCALE_FACTOR;
/* 135 */       GL11.glPushMatrix();
/* 136 */       if (entity instanceof MCH_EntityGunner) { MCH_EntityGunner gunner = (MCH_EntityGunner)entity;
/* 137 */         String seatName = "";
/* 138 */         if (gunner.getRidingEntity() instanceof MCH_EntitySeat) {
/* 139 */           seatName = "(seat " + (((MCH_EntitySeat)gunner.getRidingEntity()).seatID + 2) + ")";
/* 140 */         } else if (gunner.getRidingEntity() instanceof MCH_EntityAircraft) {
/* 141 */           seatName = "(seat 1)";
/*     */         } 
/*     */         
/* 144 */         drawCenteredString(gunner.getTeamName() + " Gunner " + seatName, (int)px, (int)py + 20, -8355840);
/* 145 */         int S = 10;
/* 146 */         drawLine(new double[] { px - S, py - S, px + S, py - S, px + S, py + S, px - S, py + S }, -8355840, 2); }
/* 147 */       else if (entity instanceof MCH_EntitySeat) { MCH_EntitySeat seat = (MCH_EntitySeat)entity;
/* 148 */         if (seat.getRiddenByEntity() == null) {
/* 149 */           drawCenteredString("seat " + (seat.seatID + 2), (int)px, (int)py + 20, -16711681);
/* 150 */           int S = 10;
/* 151 */           drawLine(new double[] { px - S, py - S, px + S, py - S, px + S, py + S, px - S, py + S }, -16711681, 2);
/*     */         } else {
/* 153 */           drawCenteredString("seat " + (seat.seatID + 2), (int)px, (int)py + 20, -65536);
/* 154 */           int S = 10;
/* 155 */           drawLine(new double[] { px - S, py - S, px + S, py - S, px + S, py + S, px - S, py + S }, -65536, 2);
/* 156 */           drawLine(new double[] { px - S, py - S, px + S, py + S }, -65536);
/* 157 */           drawLine(new double[] { px + S, py - S, px - S, py + S }, -65536);
/*     */         }  }
/* 159 */       else if (entity instanceof MCH_EntityAircraft) { MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
/* 160 */         if (ac.getRiddenByEntity() == null) {
/* 161 */           drawCenteredString("seat 1", (int)px, (int)py + 20, -16711681);
/* 162 */           int S = 10;
/* 163 */           drawLine(new double[] { px - S, py - S, px + S, py - S, px + S, py + S, px - S, py + S }, -16711681, 2);
/*     */         } else {
/* 165 */           drawCenteredString("seat 1", (int)px, (int)py + 20, -65536);
/* 166 */           int S = 10;
/* 167 */           drawLine(new double[] { px - S, py - S, px + S, py - S, px + S, py + S, px - S, py + S }, -65536, 2);
/* 168 */           drawLine(new double[] { px - S, py - S, px + S, py + S }, -65536);
/* 169 */           drawLine(new double[] { px + S, py - S, px - S, py + S }, -65536);
/*     */         }  }
/*     */ 
/*     */       
/* 173 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\mob\MCH_GuiSpawnGunner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */