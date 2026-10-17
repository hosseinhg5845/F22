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
/*  32 */     this.field_77777_bU = 1;
/*  33 */     func_77637_a(CreativeTabs.field_78029_e);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static int getColorFromItemStack(ItemStack stack, int tintIndex) {
/*  38 */     MCH_ItemSpawnGunner item = (MCH_ItemSpawnGunner)stack.func_77973_b();
/*  39 */     return (tintIndex == 0) ? item.primaryColor : item.secondaryColor; } @NotNull
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, @NotNull EnumHand handIn) { MCH_EntityGunner mCH_EntityGunner;
/*     */     MCH_EntitySeat mCH_EntitySeat;
/*     */     MCH_EntityAircraft mCH_EntityAircraft;
/*  43 */     ItemStack itemstack = player.func_184586_b(handIn);
/*  44 */     float f = 1.0F;
/*  45 */     float pitch = player.field_70127_C + (player.field_70125_A - player.field_70127_C) * f;
/*  46 */     float yaw = player.field_70126_B + (player.field_70177_z - player.field_70126_B) * f;
/*  47 */     double dx = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * f;
/*  48 */     double dy = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * f + player.func_70047_e();
/*  49 */     double dz = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * f;
/*  50 */     Vec3d vec3 = new Vec3d(dx, dy, dz);
/*  51 */     float f3 = MathHelper.func_76134_b(-yaw * 0.017453292F - 3.1415927F);
/*  52 */     float f4 = MathHelper.func_76126_a(-yaw * 0.017453292F - 3.1415927F);
/*  53 */     float f5 = -MathHelper.func_76134_b(-pitch * 0.017453292F);
/*  54 */     float f6 = MathHelper.func_76126_a(-pitch * 0.017453292F);
/*  55 */     float f7 = f4 * f5;
/*  56 */     float f8 = f3 * f5;
/*  57 */     double d3 = 5.0D;
/*  58 */     Vec3d vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
/*  59 */     List<MCH_EntityGunner> list = world.func_72872_a(MCH_EntityGunner.class, player.func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D));
/*  60 */     Entity target = null;
/*     */     
/*  62 */     for (MCH_EntityGunner gunner : list) {
/*  63 */       if (gunner.func_174813_aQ().func_72327_a(vec3, vec31) != null && (target == null || player.func_70068_e((Entity)gunner) < player.func_70068_e(target))) {
/*  64 */         mCH_EntityGunner = gunner;
/*     */       }
/*     */     } 
/*     */     
/*  68 */     if (mCH_EntityGunner == null) {
/*  69 */       List<MCH_EntitySeat> list1 = world.func_72872_a(MCH_EntitySeat.class, player.func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D));
/*     */       
/*  71 */       for (MCH_EntitySeat seat : list1) {
/*  72 */         if (seat.getParent() != null && seat
/*  73 */           .getParent().getAcInfo() != null && seat
/*  74 */           .func_174813_aQ().func_72327_a(vec3, vec31) != null && (mCH_EntityGunner == null || player
/*  75 */           .func_70068_e((Entity)seat) < player.func_70068_e((Entity)mCH_EntityGunner))) {
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
/*  86 */       List<MCH_EntityAircraft> list2 = world.func_72872_a(MCH_EntityAircraft.class, player.func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D));
/*     */       
/*  88 */       for (MCH_EntityAircraft ac : list2) {
/*  89 */         if (!ac.isUAV() && ac
/*  90 */           .getAcInfo() != null && ac
/*  91 */           .func_174813_aQ().func_72327_a(vec3, vec31) != null && (mCH_EntitySeat == null || player
/*  92 */           .func_70068_e((Entity)ac) < player.func_70068_e((Entity)mCH_EntitySeat))) {
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
/* 103 */       mCH_EntityAircraft.func_184230_a(player, handIn);
/* 104 */       return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */     } 
/* 106 */     if (this.targetType == 1 && !world.field_72995_K) {
/* 107 */       player.func_96124_cp();
/*     */     }
/* 109 */     if (mCH_EntityAircraft == null) {
/* 110 */       if (!world.field_72995_K) {
/* 111 */         player.func_145747_a((ITextComponent)new TextComponentString("Right click to seat."));
/*     */       }
/*     */       
/* 114 */       return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */     } 
/* 116 */     if (!world.field_72995_K) {
/* 117 */       MCH_EntityGunner gunner = new MCH_EntityGunner(world, ((Entity)mCH_EntityAircraft).field_70165_t, ((Entity)mCH_EntityAircraft).field_70163_u, ((Entity)mCH_EntityAircraft).field_70161_v);
/* 118 */       gunner.field_70177_z = (((MathHelper.func_76128_c((player.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
/* 119 */       gunner.isCreative = player.field_71075_bZ.field_75098_d;
/* 120 */       gunner.targetType = this.targetType;
/* 121 */       gunner.ownerUUID = player.func_110124_au().toString();
/* 122 */       ScorePlayerTeam team = world.func_96441_U().func_96509_i(player.func_145748_c_().func_150254_d());
/* 123 */       if (team != null) {
/* 124 */         gunner.setTeamName(team.func_96661_b());
/*     */       }
/*     */       
/* 127 */       world.func_72838_d((Entity)gunner);
/* 128 */       gunner.func_184220_m((Entity)mCH_EntityAircraft);
/* 129 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)gunner, "wrench", 1.0F, 3.0F);
/* 130 */       MCH_EntityAircraft ac = (mCH_EntityAircraft instanceof MCH_EntityAircraft) ? mCH_EntityAircraft : ((MCH_EntitySeat)mCH_EntityAircraft).getParent();
/* 131 */       player.func_145747_a((ITextComponent)new TextComponentString("The gunner was put on " + TextFormatting.GOLD + 
/*     */ 
/*     */ 
/*     */             
/* 135 */             (ac.getAcInfo()).displayName + TextFormatting.RESET + " seat " + (ac
/*     */ 
/*     */             
/* 138 */             .getSeatIdByEntity((Entity)gunner) + 1) + " by " + 
/*     */             
/* 140 */             ScorePlayerTeam.func_96667_a(player.func_96124_cp(), player.func_145748_c_().func_150254_d())));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 145 */     if (!player.field_71075_bZ.field_75098_d) {
/* 146 */       itemstack.func_190918_g(1);
/*     */     }
/*     */     
/* 149 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack); }
/*     */ 
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\mob\MCH_ItemSpawnGunner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */