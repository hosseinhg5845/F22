/*     */ package com.norwood.mcheli.tool;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */ import com.norwood.mcheli.wrapper.W_Item;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_ItemWrench
/*     */   extends W_Item {
/*  38 */   private static final Random rand = new Random();
/*     */   private final Item.ToolMaterial toolMaterial;
/*     */   private final float damageVsEntity;
/*     */   
/*     */   public MCH_ItemWrench(int itemId, Item.ToolMaterial material) {
/*  43 */     super(itemId);
/*  44 */     this.toolMaterial = material;
/*  45 */     this.field_77777_bU = 1;
/*  46 */     func_77656_e(material.func_77997_a());
/*  47 */     this.damageVsEntity = 4.0F + material.func_78000_c();
/*     */   }
/*     */   
/*     */   public static float getUseAnimSmooth(ItemStack stack, float partialTicks) {
/*  51 */     int i = Math.abs(getUseAnimCount(stack) - 8);
/*  52 */     int j = Math.abs(getUseAnimPrevCount(stack) - 8);
/*  53 */     return j + (i - j) * partialTicks;
/*     */   }
/*     */   
/*     */   public static int getUseAnimPrevCount(ItemStack stack) {
/*  57 */     return getAnimCount(stack, "MCH_WrenchAnimPrev");
/*     */   }
/*     */   
/*     */   public static int getUseAnimCount(ItemStack stack) {
/*  61 */     return getAnimCount(stack, "MCH_WrenchAnim");
/*     */   }
/*     */   
/*     */   public static void setUseAnimCount(ItemStack stack, int n, int prev) {
/*  65 */     setAnimCount(stack, "MCH_WrenchAnim", n);
/*  66 */     setAnimCount(stack, "MCH_WrenchAnimPrev", prev);
/*     */   }
/*     */   
/*     */   public static int getAnimCount(ItemStack stack, String name) {
/*  70 */     if (!stack.func_77942_o()) {
/*  71 */       stack.func_77982_d(new NBTTagCompound());
/*     */     }
/*     */     
/*  74 */     if (stack.func_77978_p().func_74764_b(name)) {
/*  75 */       return stack.func_77978_p().func_74762_e(name);
/*     */     }
/*  77 */     stack.func_77978_p().func_74768_a(name, 0);
/*  78 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setAnimCount(ItemStack stack, String name, int n) {
/*  83 */     if (!stack.func_77942_o()) {
/*  84 */       stack.func_77982_d(new NBTTagCompound());
/*     */     }
/*     */     
/*  87 */     stack.func_77978_p().func_74768_a(name, n);
/*     */   }
/*     */   
/*     */   private static RayTraceResult rayTrace(EntityLivingBase entity, double dist, float tick) {
/*  91 */     Vec3d vec3 = new Vec3d(entity.field_70165_t, entity.field_70163_u + entity.func_70047_e(), entity.field_70161_v);
/*  92 */     Vec3d vec31 = entity.func_70676_i(tick);
/*  93 */     Vec3d vec32 = vec3.func_72441_c(vec31.field_72450_a * dist, vec31.field_72448_b * dist, vec31.field_72449_c * dist);
/*  94 */     return entity.field_70170_p.func_147447_a(vec3, vec32, false, false, true);
/*     */   }
/*     */   
/*     */   public boolean func_150897_b(IBlockState blockIn) {
/*  98 */     Material material = blockIn.func_185904_a();
/*  99 */     return (material == Material.field_151573_f || material instanceof net.minecraft.block.material.MaterialLogic);
/*     */   }
/*     */   
/*     */   public float func_150893_a(@NotNull ItemStack stack, IBlockState state) {
/* 103 */     Material material = state.func_185904_a();
/* 104 */     if (material == Material.field_151573_f) {
/* 105 */       return 20.5F;
/*     */     }
/* 107 */     return (material instanceof net.minecraft.block.material.MaterialLogic) ? 5.5F : 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77644_a(@NotNull ItemStack itemStack, @NotNull EntityLivingBase entity, EntityLivingBase player) {
/* 112 */     if (!player.field_70170_p.field_72995_K) {
/* 113 */       if (rand.nextInt(40) == 0) {
/* 114 */         entity.func_70099_a(new ItemStack(W_Item.getItemByName("iron_ingot"), 1, 0), 0.0F);
/* 115 */       } else if (rand.nextInt(20) == 0) {
/* 116 */         entity.func_70099_a(new ItemStack(W_Item.getItemByName("gunpowder"), 1, 0), 0.0F);
/*     */       } 
/*     */     }
/*     */     
/* 120 */     itemStack.func_77972_a(2, player);
/* 121 */     return true;
/*     */   }
/*     */   
/*     */   public void func_77615_a(@NotNull ItemStack stack, @NotNull World worldIn, @NotNull EntityLivingBase entityLiving, int timeLeft) {
/* 125 */     setUseAnimCount(stack, 0, 0);
/*     */   }
/*     */   
/*     */   public void onUsingTick(@NotNull ItemStack stack, EntityLivingBase player, int count) {
/* 129 */     if (player.field_70170_p.field_72995_K) {
/* 130 */       MCH_EntityAircraft ac = getMouseOverAircraft(player);
/* 131 */       if (ac != null) {
/* 132 */         int cnt = getUseAnimCount(stack);
/* 133 */         int prev = cnt;
/* 134 */         if (cnt <= 0) {
/* 135 */           cnt = 16;
/*     */         } else {
/* 137 */           cnt--;
/*     */         } 
/*     */         
/* 140 */         setUseAnimCount(stack, cnt, prev);
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     if (!player.field_70170_p.field_72995_K && count < func_77626_a(stack) && count % 20 == 0) {
/* 145 */       MCH_EntityAircraft ac = getMouseOverAircraft(player);
/* 146 */       if (ac != null && ac.getHP() > 0 && ac.repair(10)) {
/* 147 */         stack.func_77972_a(1, player);
/* 148 */         W_WorldFunc.MOD_playSoundEffect(player.field_70170_p, (int)ac.field_70165_t, (int)ac.field_70163_u, (int)ac.field_70161_v, "wrench", 1.0F, 0.9F + rand.nextFloat() * 0.2F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_77663_a(@NotNull ItemStack item, @NotNull World world, @NotNull Entity entity, int n, boolean b) {
/* 154 */     if (entity instanceof EntityPlayer) { EntityPlayer player = (EntityPlayer)entity;
/* 155 */       ItemStack itemStack = player.func_184614_ca();
/* 156 */       if (itemStack == item) {
/* 157 */         MCH_MOD.proxy.setCreativeDigDelay(0);
/*     */       } }
/*     */   
/*     */   }
/*     */   
/*     */   public MCH_EntityAircraft getMouseOverAircraft(EntityLivingBase entity) {
/* 163 */     RayTraceResult m = getMouseOver(entity, 1.0F);
/* 164 */     MCH_EntityAircraft ac = null;
/* 165 */     if (m != null) {
/* 166 */       if (m.field_72308_g instanceof MCH_EntityAircraft)
/* 167 */       { ac = (MCH_EntityAircraft)m.field_72308_g; }
/* 168 */       else { Entity entity1 = m.field_72308_g; if (entity1 instanceof MCH_EntitySeat) { MCH_EntitySeat seat = (MCH_EntitySeat)entity1;
/* 169 */           if (seat.getParent() != null) {
/* 170 */             ac = seat.getParent();
/*     */           } }
/*     */          }
/*     */     
/*     */     }
/* 175 */     return ac;
/*     */   }
/*     */ 
/*     */   
/*     */   private RayTraceResult getMouseOver(EntityLivingBase user, float tick) {
/* 180 */     double d0 = 4.0D;
/* 181 */     RayTraceResult objectMouseOver = rayTrace(user, d0, tick);
/* 182 */     double d1 = d0;
/* 183 */     Vec3d vec3 = new Vec3d(user.field_70165_t, user.field_70163_u + user.func_70047_e(), user.field_70161_v);
/* 184 */     if (objectMouseOver != null) {
/* 185 */       d1 = objectMouseOver.field_72307_f.func_72438_d(vec3);
/*     */     }
/*     */     
/* 188 */     Vec3d vec31 = user.func_70676_i(tick);
/* 189 */     Vec3d vec32 = vec3.func_72441_c(vec31.field_72450_a * d0, vec31.field_72448_b * d0, vec31.field_72449_c * d0);
/* 190 */     Entity pointedEntity = null;
/* 191 */     Vec3d vec33 = null;
/* 192 */     float f1 = 1.0F;
/*     */     
/* 194 */     List<Entity> list = user.field_70170_p.func_72839_b((Entity)user, user.func_174813_aQ().func_72321_a(vec31.field_72450_a * d0, vec31.field_72448_b * d0, vec31.field_72449_c * d0).func_72314_b(f1, f1, f1));
/* 195 */     double d2 = d1;
/*     */     
/* 197 */     for (Entity entity : list) {
/* 198 */       if (entity.func_70067_L()) {
/* 199 */         float f2 = entity.func_70111_Y();
/* 200 */         AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_72314_b(f2, f2, f2);
/* 201 */         RayTraceResult movingobjectposition = axisalignedbb.func_72327_a(vec3, vec32);
/* 202 */         if (axisalignedbb.func_72318_a(vec3)) {
/* 203 */           if (0.0D < d2 || d2 == 0.0D) {
/* 204 */             pointedEntity = entity;
/* 205 */             vec33 = (movingobjectposition == null) ? vec3 : movingobjectposition.field_72307_f;
/* 206 */             d2 = 0.0D;
/*     */           }  continue;
/* 208 */         }  if (movingobjectposition != null) {
/* 209 */           double d3 = vec3.func_72438_d(movingobjectposition.field_72307_f);
/* 210 */           if (d3 < d2 || d2 == 0.0D) {
/* 211 */             if (entity != user.func_184187_bx() || entity.canRiderInteract()) {
/* 212 */               pointedEntity = entity;
/* 213 */               vec33 = movingobjectposition.field_72307_f;
/* 214 */               d2 = d3; continue;
/* 215 */             }  if (d2 == 0.0D) {
/* 216 */               pointedEntity = entity;
/* 217 */               vec33 = movingobjectposition.field_72307_f;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
/* 225 */       objectMouseOver = new RayTraceResult(pointedEntity, vec33);
/*     */     }
/*     */     
/* 228 */     return objectMouseOver;
/*     */   }
/*     */   
/*     */   public boolean func_179218_a(@NotNull ItemStack itemStack, @NotNull World world, IBlockState state, @NotNull BlockPos pos, @NotNull EntityLivingBase entity) {
/* 232 */     if (state.func_185887_b(world, pos) != 0.0D) {
/* 233 */       itemStack.func_77972_a(2, entity);
/*     */     }
/*     */     
/* 236 */     return true;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_77662_d() {
/* 241 */     return true;
/*     */   }
/*     */   @NotNull
/*     */   public EnumAction func_77661_b(@NotNull ItemStack itemStack) {
/* 245 */     return EnumAction.BLOCK;
/*     */   }
/*     */   
/*     */   public int func_77626_a(@NotNull ItemStack itemStack) {
/* 249 */     return 72000;
/*     */   }
/*     */   @NotNull
/*     */   public ActionResult<ItemStack> func_77659_a(@NotNull World world, EntityPlayer player, @NotNull EnumHand handIn) {
/* 253 */     player.func_184598_c(handIn);
/* 254 */     return ActionResult.newResult(EnumActionResult.SUCCESS, player.func_184586_b(handIn));
/*     */   }
/*     */   
/*     */   public int func_77619_b() {
/* 258 */     return this.toolMaterial.func_77995_e();
/*     */   }
/*     */   
/*     */   public String getToolMaterialName() {
/* 262 */     return this.toolMaterial.toString();
/*     */   }
/*     */   
/*     */   public boolean func_82789_a(@NotNull ItemStack item1, @NotNull ItemStack item2) {
/* 266 */     ItemStack mat = this.toolMaterial.getRepairItemStack();
/* 267 */     return ((!mat.func_190926_b() && OreDictionary.itemMatches(mat, item2, false)) || super.func_82789_a(item1, item2));
/*     */   }
/*     */   @NotNull
/*     */   public Multimap<String, AttributeModifier> func_111205_h(@NotNull EntityEquipmentSlot equipmentSlot) {
/* 271 */     Multimap<String, AttributeModifier> multimap = super.func_111205_h(equipmentSlot);
/* 272 */     if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
/* 273 */       multimap.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Weapon modifier", this.damageVsEntity, 0));
/*     */     }
/*     */     
/* 276 */     return multimap;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tool\MCH_ItemWrench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */