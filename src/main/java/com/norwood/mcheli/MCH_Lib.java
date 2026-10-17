/*     */ package com.norwood.mcheli;
/*     */ import com.norwood.mcheli.wrapper.W_Block;
/*     */ import com.norwood.mcheli.wrapper.W_Blocks;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.W_Reflection;
/*     */ import com.norwood.mcheli.wrapper.W_Vec3;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.io.File;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_Lib {
/*  24 */   public static final String[] AZIMUTH_8 = new String[] { "S", "SW", "W", "NW", "N", "NE", "E", "SE" };
/*  25 */   public static final int AZIMUTH_8_ANG = 360 / AZIMUTH_8.length;
/*  26 */   private static final HashMap<String, Material> mapMaterial = new HashMap<>();
/*     */   
/*     */   public static void init() {
/*  29 */     mapMaterial.clear();
/*  30 */     mapMaterial.put("air", Material.field_151579_a);
/*  31 */     mapMaterial.put("grass", Material.field_151577_b);
/*  32 */     mapMaterial.put("ground", Material.field_151578_c);
/*  33 */     mapMaterial.put("wood", Material.field_151575_d);
/*  34 */     mapMaterial.put("rock", Material.field_151576_e);
/*  35 */     mapMaterial.put("iron", Material.field_151573_f);
/*  36 */     mapMaterial.put("anvil", Material.field_151574_g);
/*  37 */     mapMaterial.put("water", Material.field_151586_h);
/*  38 */     mapMaterial.put("lava", Material.field_151587_i);
/*  39 */     mapMaterial.put("leaves", Material.field_151584_j);
/*  40 */     mapMaterial.put("plants", Material.field_151585_k);
/*  41 */     mapMaterial.put("vine", Material.field_151582_l);
/*  42 */     mapMaterial.put("sponge", Material.field_151583_m);
/*  43 */     mapMaterial.put("cloth", Material.field_151580_n);
/*  44 */     mapMaterial.put("fire", Material.field_151581_o);
/*  45 */     mapMaterial.put("sand", Material.field_151595_p);
/*  46 */     mapMaterial.put("circuits", Material.field_151594_q);
/*  47 */     mapMaterial.put("carpet", Material.field_151593_r);
/*  48 */     mapMaterial.put("glass", Material.field_151592_s);
/*  49 */     mapMaterial.put("redstoneLight", Material.field_151591_t);
/*  50 */     mapMaterial.put("tnt", Material.field_151590_u);
/*  51 */     mapMaterial.put("coral", Material.field_151589_v);
/*  52 */     mapMaterial.put("ice", Material.field_151588_w);
/*  53 */     mapMaterial.put("packedIce", Material.field_151598_x);
/*  54 */     mapMaterial.put("snow", Material.field_151597_y);
/*  55 */     mapMaterial.put("craftedSnow", Material.field_151596_z);
/*  56 */     mapMaterial.put("cactus", Material.field_151570_A);
/*  57 */     mapMaterial.put("clay", Material.field_151571_B);
/*  58 */     mapMaterial.put("gourd", Material.field_151572_C);
/*  59 */     mapMaterial.put("dragonEgg", Material.field_151566_D);
/*  60 */     mapMaterial.put("portal", Material.field_151567_E);
/*  61 */     mapMaterial.put("cake", Material.field_151568_F);
/*  62 */     mapMaterial.put("web", Material.field_151569_G);
/*  63 */     mapMaterial.put("piston", Material.field_76233_E);
/*     */   }
/*     */   
/*     */   public static Material getMaterialFromName(String name) {
/*  67 */     return mapMaterial.getOrDefault(name, null);
/*     */   }
/*     */   
/*     */   public static Vec3d calculateFaceNormal(Vec3d[] vertices) {
/*  71 */     Vec3d v1 = new Vec3d((vertices[1]).field_72450_a - (vertices[0]).field_72450_a, (vertices[1]).field_72448_b - (vertices[0]).field_72448_b, (vertices[1]).field_72449_c - (vertices[0]).field_72449_c);
/*  72 */     Vec3d v2 = new Vec3d((vertices[2]).field_72450_a - (vertices[0]).field_72450_a, (vertices[2]).field_72448_b - (vertices[0]).field_72448_b, (vertices[2]).field_72449_c - (vertices[0]).field_72449_c);
/*  73 */     return v1.func_72431_c(v2).func_72432_b();
/*     */   }
/*     */   
/*     */   public static double parseDouble(String s) {
/*  77 */     return (s == null) ? 0.0D : Double.parseDouble(s.replace(',', '.'));
/*     */   }
/*     */   
/*     */   public static float RNG(float a, float min, float max) {
/*  81 */     return (a > max) ? max : Math.max(a, min);
/*     */   }
/*     */   
/*     */   public static double RNG(double a, double min, double max) {
/*  85 */     return (a > max) ? max : Math.max(a, min);
/*     */   }
/*     */   
/*     */   public static float smooth(float rot, float prevRot, float tick) {
/*  89 */     return prevRot + (rot - prevRot) * tick;
/*     */   }
/*     */   
/*     */   public static float smoothRot(float rot, float prevRot, float tick) {
/*  93 */     if (rot - prevRot < -180.0F) {
/*  94 */       prevRot -= 360.0F;
/*  95 */     } else if (prevRot - rot < -180.0F) {
/*  96 */       prevRot += 360.0F;
/*     */     } 
/*     */     
/*  99 */     return prevRot + (rot - prevRot) * tick;
/*     */   }
/*     */   
/*     */   public static double getRotateDiff(double base, double target) {
/* 103 */     base = getRotate360(base);
/* 104 */     target = getRotate360(target);
/* 105 */     if (target - base < -180.0D) {
/* 106 */       target += 360.0D;
/* 107 */     } else if (target - base > 180.0D) {
/* 108 */       base += 360.0D;
/*     */     } 
/*     */     
/* 111 */     return target - base;
/*     */   }
/*     */   
/*     */   public static float getPosAngle(double tx, double tz, double cx, double cz) {
/* 115 */     double length_A = Math.sqrt(tx * tx + tz * tz);
/* 116 */     double length_B = Math.sqrt(cx * cx + cz * cz);
/* 117 */     double cos_sita = (tx * cx + tz * cz) / length_A * length_B;
/* 118 */     double sita = Math.acos(cos_sita);
/* 119 */     return (float)(sita * 180.0D / Math.PI);
/*     */   }
/*     */   
/*     */   public static void applyEntityHurtResistantTimeConfig(Entity entity) {
/* 123 */     if (entity instanceof EntityLivingBase) { EntityLivingBase elb = (EntityLivingBase)entity;
/* 124 */       double h_time = MCH_Config.HurtResistantTime.prmDouble * elb.field_70172_ad;
/* 125 */       elb.field_70172_ad = (int)h_time; }
/*     */   
/*     */   }
/*     */   
/*     */   public static int round(double d) {
/* 130 */     return (int)(d + 0.5D);
/*     */   }
/*     */   
/*     */   public static Vec3d Rot2Vec3(float yaw, float pitch) {
/* 134 */     return new Vec3d((
/* 135 */         -MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F)), 
/* 136 */         -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F), (
/* 137 */         MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d RotVec3(double x, double y, double z, float yaw, float pitch) {
/* 142 */     Vec3d v = new Vec3d(x, y, z);
/* 143 */     v = v.func_178789_a(pitch / 180.0F * 3.1415927F);
/* 144 */     return v.func_178785_b(yaw / 180.0F * 3.1415927F);
/*     */   }
/*     */   
/*     */   public static Vec3d RotVec3(double x, double y, double z, float yaw, float pitch, float roll) {
/* 148 */     Vec3d v = new Vec3d(x, y, z);
/* 149 */     v = W_Vec3.rotateRoll(roll / 180.0F * 3.1415927F, v);
/* 150 */     v = v.func_178789_a(pitch / 180.0F * 3.1415927F);
/* 151 */     return v.func_178785_b(yaw / 180.0F * 3.1415927F);
/*     */   }
/*     */   
/*     */   public static Vec3d RotVec3(Vec3d vin, float yaw, float pitch) {
/* 155 */     Vec3d v = new Vec3d(vin.field_72450_a, vin.field_72448_b, vin.field_72449_c);
/* 156 */     v = v.func_178789_a(pitch / 180.0F * 3.1415927F);
/* 157 */     return v.func_178785_b(yaw / 180.0F * 3.1415927F);
/*     */   }
/*     */   
/*     */   public static Vec3d RotVec3(Vec3d vin, float yaw, float pitch, float roll) {
/* 161 */     Vec3d v = new Vec3d(vin.field_72450_a, vin.field_72448_b, vin.field_72449_c);
/* 162 */     v = W_Vec3.rotateRoll(roll / 180.0F * 3.1415927F, v);
/* 163 */     v = v.func_178789_a(pitch / 180.0F * 3.1415927F);
/* 164 */     return v.func_178785_b(yaw / 180.0F * 3.1415927F);
/*     */   }
/*     */   
/*     */   public static Vec3d _Rot2Vec3(float yaw, float pitch, float roll) {
/* 168 */     return new Vec3d((
/* 169 */         -MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F)), 
/* 170 */         -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F), (
/* 171 */         MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getRotate360(double r) {
/* 176 */     r %= 360.0D;
/* 177 */     return (r >= 0.0D) ? r : (r + 360.0D);
/*     */   }
/*     */   
/*     */   public static void Log(String format, Object... data) {
/* 181 */     String side = MCH_MOD.proxy.isRemote() ? "[Client]" : "[Server]";
/* 182 */     System.out.printf("[" + getTime() + "][mcheli]" + side + " " + format + "\n", data);
/*     */   }
/*     */   
/*     */   public static void Log(World world, String format, Object... data) {
/* 186 */     if (world != null) {
/* 187 */       Log((world.field_72995_K ? "[ClientWorld]" : "[ServerWorld]") + " " + format, data);
/*     */     } else {
/* 189 */       Log("[UnknownWorld]" + format, data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void Log(Entity entity, String format, Object... data) {
/* 194 */     if (entity != null) {
/* 195 */       Log(entity.field_70170_p, format, data);
/*     */     } else {
/* 197 */       Log((World)null, format, data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void DbgLog(boolean isRemote, String format, Object... data) {
/* 202 */     if (MCH_Config.DebugLog) {
/* 203 */       if (isRemote) {
/* 204 */         if (getClientPlayer() instanceof net.minecraft.entity.player.EntityPlayer);
/*     */ 
/*     */         
/* 207 */         System.out.printf(format + "%n", data);
/*     */       } else {
/* 209 */         System.out.printf(format + "%n", data);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void DbgLog(World w, String format, Object... data) {
/* 215 */     DbgLog(w.field_72995_K, format, data);
/*     */   }
/*     */   
/*     */   public static String getTime() {
/* 219 */     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
/* 220 */     return sdf.format(new Date());
/*     */   }
/*     */   
/*     */   public static String getAzimuthStr8(int dir) {
/* 224 */     dir %= 360;
/* 225 */     if (dir < 0) {
/* 226 */       dir += 360;
/*     */     }
/*     */     
/* 229 */     dir /= AZIMUTH_8_ANG;
/* 230 */     return AZIMUTH_8[dir];
/*     */   }
/*     */   
/*     */   public static void rotatePoints(double[] points, float r) {
/* 234 */     r = r / 180.0F * 3.1415927F;
/*     */     
/* 236 */     for (int i = 0; i + 1 < points.length; i += 2) {
/* 237 */       double x = points[i];
/* 238 */       double y = points[i + 1];
/* 239 */       points[i] = x * MathHelper.func_76134_b(r) - y * MathHelper.func_76126_a(r);
/* 240 */       points[i + 1] = x * MathHelper.func_76126_a(r) + y * MathHelper.func_76134_b(r);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void rotatePoints(ArrayList<MCH_Vector2> points, float r) {
/* 245 */     r = r / 180.0F * 3.1415927F;
/*     */     
/* 247 */     for (int i = 0; i + 1 < points.size(); i += 2) {
/* 248 */       double x = ((MCH_Vector2)points.get(i)).x;
/* 249 */       double y = ((MCH_Vector2)points.get(i)).y;
/* 250 */       ((MCH_Vector2)points.get(i)).x = x * MathHelper.func_76134_b(r) - y * MathHelper.func_76126_a(r);
/* 251 */       ((MCH_Vector2)points.get(i)).y = x * MathHelper.func_76126_a(r) + y * MathHelper.func_76134_b(r);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String[] listupFileNames(String path) {
/* 256 */     File dir = new File(path);
/* 257 */     return dir.list();
/*     */   }
/*     */   
/*     */   public static boolean isBlockInWater(World w, int x, int y, int z) {
/* 261 */     int[][] offset = { { 0, -1, 0 }, { 0, 0, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 }, { 0, 1, 0 } };
/* 262 */     if (y > 0) {
/* 263 */       for (int[] o : offset) {
/* 264 */         if (W_WorldFunc.isBlockWater(w, x + o[0], y + o[1], z + o[2])) {
/* 265 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 270 */     return false;
/*     */   }
/*     */   
/*     */   public static int getBlockIdY(World w, double posX, double posY, double posZ, int size, int lenY, boolean canColliableOnly) {
/* 274 */     Block block = getBlockY(w, posX, posY, posZ, size, lenY, canColliableOnly);
/* 275 */     return (block == null) ? 0 : W_Block.func_149682_b(block);
/*     */   }
/*     */   
/*     */   public static int getBlockIdY(Entity entity, int size, int lenY) {
/* 279 */     return getBlockIdY(entity, size, lenY, true);
/*     */   }
/*     */   
/*     */   public static int getBlockIdY(Entity entity, int size, int lenY, boolean canColliableOnly) {
/* 283 */     Block block = getBlockY(entity, size, lenY, canColliableOnly);
/* 284 */     return (block == null) ? 0 : W_Block.func_149682_b(block);
/*     */   }
/*     */   
/*     */   public static Block getBlockY(Entity entity, int size, int lenY, boolean canColliableOnly) {
/* 288 */     return getBlockY(entity.field_70170_p, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, size, lenY, canColliableOnly);
/*     */   }
/*     */   
/*     */   public static Block getBlockY(World world, Vec3d pos, int size, int lenY, boolean canColliableOnly) {
/* 292 */     return getBlockY(world, pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, size, lenY, canColliableOnly);
/*     */   }
/*     */   
/*     */   public static Block getBlockY(World world, double posX, double posY, double posZ, int size, int lenY, boolean canColliableOnly) {
/* 296 */     if (lenY != 0) {
/* 297 */       int px = (int)(posX + 0.5D);
/* 298 */       int py = (int)(posY + 0.5D);
/* 299 */       int pz = (int)(posZ + 0.5D);
/* 300 */       int cntY = (lenY > 0) ? lenY : -lenY;
/*     */       
/* 302 */       for (int y = 0; y < cntY; y++) {
/* 303 */         if (py + y < 0 || py + y > 255) {
/* 304 */           return W_Blocks.field_150350_a;
/*     */         }
/*     */         
/* 307 */         for (int x = -size / 2; x <= size / 2; x++) {
/* 308 */           for (int z = -size / 2; z <= size / 2; z++) {
/* 309 */             IBlockState iblockstate = world.func_180495_p(new BlockPos(px + x, py + ((lenY > 0) ? y : -y), pz + z));
/* 310 */             Block block = W_WorldFunc.getBlock(world, px + x, py + ((lenY > 0) ? y : -y), pz + z);
/* 311 */             if (block != W_Blocks.field_150350_a) {
/* 312 */               if (!canColliableOnly) {
/* 313 */                 return block;
/*     */               }
/*     */               
/* 316 */               if (block.func_176209_a(iblockstate, true)) {
/* 317 */                 return block;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 325 */     return W_Blocks.field_150350_a;
/*     */   }
/*     */   
/*     */   public static Vec3d getYawPitchFromVec(Vec3d v) {
/* 329 */     return getYawPitchFromVec(v.field_72450_a, v.field_72448_b, v.field_72449_c);
/*     */   }
/*     */   
/*     */   public static Vec3d getYawPitchFromVec(double x, double y, double z) {
/* 333 */     double p = MathHelper.func_76133_a(x * x + z * z);
/* 334 */     float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI);
/* 335 */     float roll = (float)(Math.atan2(y, p) * 180.0D / Math.PI);
/* 336 */     return new Vec3d(0.0D, yaw, roll);
/*     */   }
/*     */   
/*     */   public static float getAlpha(int argb) {
/* 340 */     return (argb >> 24) / 255.0F;
/*     */   }
/*     */   
/*     */   public static float getRed(int argb) {
/* 344 */     return (argb >> 16 & 0xFF) / 255.0F;
/*     */   }
/*     */   
/*     */   public static float getGreen(int argb) {
/* 348 */     return (argb >> 8 & 0xFF) / 255.0F;
/*     */   }
/*     */   
/*     */   public static float getBlue(int argb) {
/* 352 */     return (argb & 0xFF) / 255.0F;
/*     */   }
/*     */   
/*     */   public static void enableFirstPersonItemRender() {
/* 356 */     switch (MCH_Config.DisableItemRender.prmInt) { case 2:
/* 357 */         MCH_ItemRendererDummy.disableDummyItemRenderer(); break;
/* 358 */       case 3: W_Reflection.restoreCameraZoom();
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableFirstPersonItemRender(ItemStack itemStack) {
/* 365 */     if (!itemStack.func_190926_b() || !(itemStack.func_77973_b() instanceof net.minecraft.item.ItemMapBase) || W_McClient.getRenderEntity() instanceof MCH_ViewEntityDummy) {
/* 366 */       disableFirstPersonItemRender();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void disableFirstPersonItemRender() {
/* 371 */     switch (MCH_Config.DisableItemRender.prmInt) {
/*     */       case 1:
/* 373 */         W_Reflection.setItemRendererMainHand(new ItemStack((Item)MCH_MOD.invisibleItem));
/*     */         break;
/*     */       case 2:
/* 376 */         MCH_ItemRendererDummy.enableDummyItemRenderer();
/*     */         break;
/*     */       case 3:
/* 379 */         W_Reflection.setCameraZoom(1.01F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public static Entity getClientPlayer() {
/* 384 */     return MCH_MOD.proxy.getClientPlayer();
/*     */   }
/*     */   
/*     */   public static void setRenderViewEntity(EntityLivingBase entity) {
/* 388 */     if (MCH_Config.ReplaceRenderViewEntity.prmBool) {
/* 389 */       W_McClient.setRenderEntity(entity);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isFlansModEntity(Entity entity) {
/* 394 */     if (entity == null) {
/* 395 */       return false;
/*     */     }
/* 397 */     String className = entity.getClass().getName();
/* 398 */     return (className.contains("EntityVehicle") || className.contains("EntityPlane") || className.contains("EntityMecha") || className.contains("EntityAAGun"));
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_Lib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */