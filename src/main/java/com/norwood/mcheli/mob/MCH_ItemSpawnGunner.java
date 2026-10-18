/*     */ package com.norwood.mcheli.mob;
/*     */ 
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */ import com.norwood.mcheli.wrapper.W_Item;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_ItemSpawnGunner extends W_Item {
/*  27 */   public int primaryColor = 16777215;
/*  28 */   public int secondaryColor = 16777215;
/*  29 */   public int targetType = 0;
/*     */   
/*     */   public MCH_ItemSpawnGunner() {
/*  32 */     this.maxStackSize = 1;
/*  33 */     setCreativeTab(CreativeTabs.TRANSPORTATION);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static int getColorFromItemStack(ItemStack stack, int tintIndex) {
/*  38 */     MCH_ItemSpawnGunner item = (MCH_ItemSpawnGunner)stack.getItem();
/*  39 */     return (tintIndex == 0) ? item.primaryColor : item.secondaryColor; } @NotNull
/*     */   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @NotNull EnumHand handIn) { MCH_EntityGunner mCH_EntityGunner;
/*     */     MCH_EntitySeat mCH_EntitySeat;
/*     */     Entity mCH_EntityAircraft;
/*  43 */     ItemStack itemstack = player.getHeldItem(handIn);
/*  44 */     float f = 1.0F;
/*  45 */     float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
/*  46 */     float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
/*  47 */     double dx = player.prevPosX + (player.posX - player.prevPosX) * f;
/*  48 */     double dy = player.prevPosY + (player.posY - player.prevPosY) * f + player.getEyeHeight();
/*  49 */     double dz = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
/*  50 */     Vec3d vec3 = new Vec3d(dx, dy, dz);
/*  51 */     float f3 = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
/*  52 */     float f4 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
/*  53 */     float f5 = -MathHelper.cos(-pitch * 0.017453292F);
/*  54 */     float f6 = MathHelper.sin(-pitch * 0.017453292F);
/*  55 */     float f7 = f4 * f5;
/*  56 */     float f8 = f3 * f5;
/*  57 */     double d3 = 5.0D;
/*  58 */     Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
/*  59 */     List<MCH_EntityGunner> list = world.getEntitiesWithinAABB(MCH_EntityGunner.class, player.getEntityBoundingBox().grow(5.0D, 5.0D, 5.0D));
/*  60 */     Entity target = null;
/*     */     
/*  62 */     for (MCH_EntityGunner gunner : list) {
/*  63 */       if (gunner.getEntityBoundingBox().calculateIntercept(vec3, vec31) != null && (target == null || player.getDistanceSq((Entity)gunner) < player.getDistanceSq(target))) {
/*  64 */         mCH_EntityGunner = gunner;
/*     */       }
/*     */     } 
/*     */     
/*  68 */     if (mCH_EntityGunner == null) {
/*  69 */       List<MCH_EntitySeat> list1 = world.getEntitiesWithinAABB(MCH_EntitySeat.class, player.getEntityBoundingBox().grow(5.0D, 5.0D, 5.0D));
/*     */       
/*  71 */       for (MCH_EntitySeat seat : list1) {
/*  72 */         if (seat.getParent() != null && seat
/*  73 */           .getParent().getAcInfo() != null && seat
/*  74 */           .getEntityBoundingBox().calculateIntercept(vec3, vec31) != null && (mCH_EntityGunner == null || player
/*  75 */           .getDistanceSq((Entity)seat) < player.getDistanceSq((Entity)mCH_EntityGunner))) {
/*  76 */           if (seat.getRiddenByEntity() instanceof MCH_EntityGunner) {
/*  77 */             Entity entity = seat.getRiddenByEntity(); continue;
/*     */           } 
/*  79 */           mCH_EntitySeat = seat;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  85 */     if (mCH_EntitySeat == null) {
/*  86 */       List<MCH_EntityAircraft> list2 = world.getEntitiesWithinAABB(MCH_EntityAircraft.class, player.getEntityBoundingBox().grow(5.0D, 5.0D, 5.0D));
/*     */       
/*  88 */       for (MCH_EntityAircraft ac : list2) {
/*  89 */         if (!ac.isUAV() && ac
/*  90 */           .getAcInfo() != null && ac
/*  91 */           .getEntityBoundingBox().calculateIntercept(vec3, vec31) != null && (mCH_EntitySeat == null || player
/*  92 */           .getDistanceSq((Entity)ac) < player.getDistanceSq((Entity)mCH_EntitySeat))) {
/*  93 */           if (ac.getRiddenByEntity() instanceof MCH_EntityGunner) {
/*  94 */             Entity entity = ac.getRiddenByEntity(); continue;
/*     */           } 
/*  96 */           mCH_EntityAircraft = ac;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 102 */     if (mCH_EntityAircraft instanceof MCH_EntityGunner) {
/* 103 */       ((MCH_EntityGunner)mCH_EntityAircraft).processInitialInteract(player, handIn);
/* 104 */       return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */     } 
/* 106 */     if (this.targetType == 1 && !world.isRemote) {
/* 107 */       player.getTeam();
/*     */     }
/* 109 */     if (mCH_EntityAircraft == null) {
/* 110 */       if (!world.isRemote) {
/* 111 */         player.sendMessage((ITextComponent)new TextComponentString("Right click to seat."));
/*     */       }
/*     */       
/* 114 */       return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */     } 
/* 116 */     if (!world.isRemote) {
/* 117 */       MCH_EntityGunner gunner = new MCH_EntityGunner(world, ((Entity)mCH_EntityAircraft).posX, ((Entity)mCH_EntityAircraft).posY, ((Entity)mCH_EntityAircraft).posZ);
/* 118 */       gunner.rotationYaw = (((MathHelper.floor((player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
/* 119 */       gunner.isCreative = player.capabilities.isCreativeMode;
/* 120 */       gunner.targetType = this.targetType;
/* 121 */       gunner.ownerUUID = player.getUniqueID().toString();
/* 122 */       ScorePlayerTeam team = world.getScoreboard().getPlayersTeam(player.getDisplayName().getFormattedText());
/* 123 */       if (team != null) {
/* 124 */         gunner.setTeamName(team.getName());
/*     */       }
/*     */       
/* 127 */       world.spawnEntity((Entity)gunner);
/* 128 */       gunner.startRiding((Entity)mCH_EntityAircraft);
/* 129 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)gunner, "wrench", 1.0F, 3.0F);
/* 130 */       MCH_EntityAircraft ac = (mCH_EntityAircraft instanceof MCH_EntityAircraft) ? (MCH_EntityAircraft)mCH_EntityAircraft : ((MCH_EntitySeat)mCH_EntityAircraft).getParent();
/* 131 */       player.sendMessage((ITextComponent)new TextComponentString("The gunner was put on " + TextFormatting.GOLD + 
/*     */ 
/*     */ 
/*     */             
/* 135 */             (ac.getAcInfo()).displayName + TextFormatting.RESET + " seat " + (ac
/*     */ 
/*     */             
/* 138 */             .getSeatIdByEntity((Entity)gunner) + 1) + " by " + 
/*     */             
/* 140 */             ScorePlayerTeam.formatPlayerName(player.getTeam(), player.getDisplayName().getFormattedText())));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 145 */     if (!player.capabilities.isCreativeMode) {
/* 146 */       itemstack.shrink(1);
/*     */     }
/*     */     
/* 149 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack); }
/*     */ 
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\mob\MCH_ItemSpawnGunner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */