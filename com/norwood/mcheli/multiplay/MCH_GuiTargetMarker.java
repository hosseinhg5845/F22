/*     */ package com.norwood.mcheli.multiplay;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_MarkEntityPos;
/*     */ import com.norwood.mcheli.MCH_ServerSettings;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.gui.MCH_Gui;
/*     */ import com.norwood.mcheli.helper.entity.ITargetMarkerObject;
/*     */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiTargetMarker extends MCH_Gui {
/*  31 */   private static final FloatBuffer matModel = BufferUtils.createFloatBuffer(16);
/*  32 */   private static final FloatBuffer matProjection = BufferUtils.createFloatBuffer(16);
/*  33 */   private static final IntBuffer matViewport = BufferUtils.createIntBuffer(16);
/*  34 */   private static final ArrayList<MCH_MarkEntityPos> entityPos = new ArrayList<>();
/*  35 */   private static final HashMap<Integer, Integer> spotedEntity = new HashMap<>();
/*     */   private static Minecraft s_minecraft;
/*  37 */   private static int spotedEntityCountdown = 0;
/*     */   
/*     */   public MCH_GuiTargetMarker(Minecraft minecraft) {
/*  40 */     super(minecraft);
/*  41 */     s_minecraft = minecraft;
/*     */   }
/*     */   
/*     */   public static void onClientTick() {
/*  45 */     if (!Minecraft.func_71410_x().func_147113_T()) {
/*  46 */       spotedEntityCountdown++;
/*     */     }
/*     */     
/*  49 */     if (spotedEntityCountdown >= 20) {
/*  50 */       spotedEntityCountdown = 0;
/*     */       
/*  52 */       for (Integer key : spotedEntity.keySet()) {
/*  53 */         int count = ((Integer)spotedEntity.get(key)).intValue();
/*  54 */         if (count > 0) {
/*  55 */           spotedEntity.put(key, Integer.valueOf(count - 1));
/*     */         }
/*     */       } 
/*     */       
/*  59 */       spotedEntity.values().removeIf(integer -> (integer.intValue() <= 0));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isSpotedEntity(@Nullable Entity entity) {
/*  64 */     if (entity != null) {
/*  65 */       int entityId = entity.func_145782_y();
/*     */       
/*  67 */       for (Iterator<Integer> iterator = spotedEntity.keySet().iterator(); iterator.hasNext(); ) { int key = ((Integer)iterator.next()).intValue();
/*  68 */         if (key == entityId) {
/*  69 */           return true;
/*     */         } }
/*     */     
/*     */     } 
/*     */     
/*  74 */     return false;
/*     */   }
/*     */   
/*     */   public static void addSpotedEntity(int entityId, int count) {
/*  78 */     if (spotedEntity.containsKey(Integer.valueOf(entityId))) {
/*  79 */       int now = ((Integer)spotedEntity.get(Integer.valueOf(entityId))).intValue();
/*  80 */       if (count > now) {
/*  81 */         spotedEntity.put(Integer.valueOf(entityId), Integer.valueOf(count));
/*     */       }
/*     */     } else {
/*  84 */       spotedEntity.put(Integer.valueOf(entityId), Integer.valueOf(count));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void addMarkEntityPos(int reserve, ITargetMarkerObject target, double x, double y, double z) {
/*  89 */     addMarkEntityPos(reserve, target, x, y, z, false);
/*     */   }
/*     */   
/*     */   public static void addMarkEntityPos(int reserve, ITargetMarkerObject target, double x, double y, double z, boolean nazo) {
/*  93 */     if (isEnableEntityMarker()) {
/*  94 */       MCH_TargetType spotType = MCH_TargetType.NONE;
/*  95 */       EntityPlayerSP entityPlayerSP = s_minecraft.field_71439_g;
/*  96 */       Entity entity = target.getEntity();
/*  97 */       if (entity instanceof MCH_EntityAircraft) { MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
/*  98 */         if (ac.isMountedEntity((Entity)entityPlayerSP)) {
/*     */           return;
/*     */         }
/*     */         
/* 102 */         if (ac.isMountedSameTeamEntity((EntityLivingBase)entityPlayerSP)) {
/* 103 */           spotType = MCH_TargetType.SAME_TEAM_PLAYER;
/*     */         } }
/* 105 */       else if (entity instanceof EntityPlayer)
/* 106 */       { if (entity == entityPlayerSP || entity.func_184187_bx() instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat || entity.func_184187_bx() instanceof MCH_EntityAircraft) {
/*     */           return;
/*     */         }
/*     */         
/* 110 */         entityPlayerSP.func_96124_cp();
/* 111 */         if (entityPlayerSP.func_184191_r(entity)) {
/* 112 */           spotType = MCH_TargetType.SAME_TEAM_PLAYER;
/*     */         } }
/*     */ 
/*     */       
/* 116 */       if (spotType == MCH_TargetType.NONE && isSpotedEntity(entity)) {
/* 117 */         spotType = MCH_Multiplay.canSpotEntity((Entity)entityPlayerSP, ((EntityPlayer)entityPlayerSP).field_70165_t, ((EntityPlayer)entityPlayerSP).field_70163_u + entityPlayerSP
/* 118 */             .func_70047_e(), ((EntityPlayer)entityPlayerSP).field_70161_v, entity, false);
/*     */       }
/*     */ 
/*     */       
/* 122 */       if (reserve == 100) {
/* 123 */         spotType = MCH_TargetType.POINT;
/*     */       }
/*     */       
/* 126 */       if (spotType != MCH_TargetType.NONE) {
/* 127 */         MCH_MarkEntityPos e = new MCH_MarkEntityPos(spotType.ordinal(), target);
/* 128 */         GL11.glGetFloat(2982, matModel);
/* 129 */         GL11.glGetFloat(2983, matProjection);
/* 130 */         GL11.glGetInteger(2978, matViewport);
/* 131 */         if (nazo) {
/* 132 */           GLU.gluProject((float)z, (float)y, (float)x, matModel, matProjection, matViewport, e.pos);
/* 133 */           float yy = e.pos.get(1);
/* 134 */           GLU.gluProject((float)x, (float)y, (float)z, matModel, matProjection, matViewport, e.pos);
/* 135 */           e.pos.put(1, yy);
/*     */         } else {
/* 137 */           GLU.gluProject((float)x, (float)y, (float)z, matModel, matProjection, matViewport, e.pos);
/*     */         } 
/*     */         
/* 140 */         entityPos.add(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void clearMarkEntityPos() {
/* 146 */     entityPos.clear();
/*     */   }
/*     */   
/*     */   public static boolean isEnableEntityMarker() {
/* 150 */     return (MCH_Config.DisplayEntityMarker.prmBool && (
/* 151 */       Minecraft.func_71410_x().func_71356_B() || MCH_ServerSettings.enableEntityMarker) && MCH_Config.EntityMarkerSize.prmDouble > 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawRhombus(BufferBuilder builder, int dir, double x, double y, double z, double size, int color) {
/* 156 */     size *= 2.0D;
/* 157 */     int red = color >> 16 & 0xFF;
/* 158 */     int green = color >> 8 & 0xFF;
/* 159 */     int blue = color >> 0 & 0xFF;
/* 160 */     int alpha = color >> 24 & 0xFF;
/* 161 */     double M = size / 3.0D;
/* 162 */     if ((dir & 0x1) != 0) {
/* 163 */       builder.func_181662_b(x - size, y, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 164 */       builder.func_181662_b(x - size + M, y - M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 165 */       builder.func_181662_b(x - size, y, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 166 */       builder.func_181662_b(x - size + M, y + M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/*     */     } 
/*     */     
/* 169 */     if ((dir & 0x4) != 0) {
/* 170 */       builder.func_181662_b(x + size, y, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 171 */       builder.func_181662_b(x + size - M, y - M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 172 */       builder.func_181662_b(x + size, y, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 173 */       builder.func_181662_b(x + size - M, y + M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/*     */     } 
/*     */     
/* 176 */     if ((dir & 0x8) != 0) {
/* 177 */       builder.func_181662_b(x, y - size, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 178 */       builder.func_181662_b(x + M, y - size + M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 179 */       builder.func_181662_b(x, y - size, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 180 */       builder.func_181662_b(x - M, y - size + M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/*     */     } 
/*     */     
/* 183 */     if ((dir & 0x2) != 0) {
/* 184 */       builder.func_181662_b(x, y + size, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 185 */       builder.func_181662_b(x + M, y + size - M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 186 */       builder.func_181662_b(x, y + size, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 187 */       builder.func_181662_b(x - M, y + size - M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void markPoint(int px, int py, int pz) {
/* 192 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/* 193 */     if (entityPlayerSP != null && ((EntityPlayer)entityPlayerSP).field_70170_p != null) {
/* 194 */       if (py < 1000) {
/* 195 */         MCH_ParticlesUtil.spawnMarkPoint((EntityPlayer)entityPlayerSP, 0.5D + px, 1.0D + py, 0.5D + pz);
/*     */       } else {
/* 197 */         MCH_ParticlesUtil.clearMarkPoint();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/* 204 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/* 209 */     return (player != null && player.field_70170_p != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/* 214 */     GL11.glLineWidth((scaleFactor * 2));
/* 215 */     if (isDrawGui(player)) {
/* 216 */       GL11.glDisable(3042);
/* 217 */       if (isEnableEntityMarker()) {
/* 218 */         drawMark();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void drawMark() {
/* 224 */     int[] COLOR_TABLE = { 0, -808464433, -805371904, -805306624, -822018049, -805351649, -65536, 0 };
/* 225 */     int scale = (scaleFactor > 0) ? scaleFactor : 2;
/* 226 */     GL11.glEnable(3042);
/* 227 */     GL11.glDisable(3553);
/* 228 */     GL11.glBlendFunc(770, 771);
/* 229 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 230 */     GL11.glDepthMask(false);
/* 231 */     int DW = this.field_146297_k.field_71443_c;
/* 232 */     int DSW = this.field_146297_k.field_71443_c / scale;
/* 233 */     int DSH = this.field_146297_k.field_71440_d / scale;
/*     */ 
/*     */ 
/*     */     
/* 237 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 238 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */     
/* 240 */     for (int i = 0; i < 2; i++) {
/* 241 */       if (i == 0) {
/* 242 */         builder.func_181668_a(4, DefaultVertexFormats.field_181706_f);
/*     */       }
/*     */       
/* 245 */       for (MCH_MarkEntityPos e : entityPos) {
/* 246 */         int color = COLOR_TABLE[e.type];
/* 247 */         double x = (e.pos.get(0) / scale);
/* 248 */         double z = e.pos.get(2);
/* 249 */         double y = (e.pos.get(1) / scale);
/* 250 */         if (z < 1.0D) {
/* 251 */           y = DSH - y;
/* 252 */         } else if (x < DW / 2.0D) {
/* 253 */           x = 10000.0D;
/* 254 */         } else if (x >= DW / 2.0D) {
/* 255 */           x = -10000.0D;
/*     */         } 
/*     */         
/* 258 */         if (i == 0) {
/* 259 */           double size = MCH_Config.EntityMarkerSize.prmDouble;
/* 260 */           if (e.type < MCH_TargetType.POINT.ordinal() && z < 1.0D && x >= 0.0D && x <= DSW && y >= 0.0D && y <= DSH)
/* 261 */             drawTriangle1(builder, x, y, size, color);  continue;
/*     */         } 
/* 263 */         if (e.type == MCH_TargetType.POINT.ordinal() && e.getTarget() != null) {
/* 264 */           ITargetMarkerObject target = e.getTarget();
/* 265 */           double MARK_SIZE = MCH_Config.BlockMarkerSize.prmDouble;
/* 266 */           if (z < 1.0D && x >= 0.0D && x <= (DSW - 20) && y >= 0.0D && y <= (DSH - 40)) {
/* 267 */             double dist = this.field_146297_k.field_71439_g.func_70011_f(target.getX(), target.getY(), target.getZ());
/* 268 */             GL11.glEnable(3553);
/* 269 */             drawCenteredString(String.format("%.0fm", new Object[] { Double.valueOf(dist) }), (int)x, (int)(y + MARK_SIZE * 1.1D + 16.0D), color);
/* 270 */             if (x >= DSW / 2.0D - 20.0D && x <= DSW / 2.0D + 20.0D && y >= DSH / 2.0D - 20.0D && y <= DSH / 2.0D + 20.0D) {
/* 271 */               drawString(String.format("x : %.0f", new Object[] { Double.valueOf(target.getX()) }), (int)(x + MARK_SIZE + 18.0D), (int)y - 12, color);
/* 272 */               drawString(String.format("y : %.0f", new Object[] { Double.valueOf(target.getY()) }), (int)(x + MARK_SIZE + 18.0D), (int)y - 4, color);
/* 273 */               drawString(String.format("z : %.0f", new Object[] { Double.valueOf(target.getZ()) }), (int)(x + MARK_SIZE + 18.0D), (int)y + 4, color);
/*     */             } 
/*     */             
/* 276 */             GL11.glDisable(3553);
/* 277 */             builder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
/* 278 */             drawRhombus(builder, 15, x, y, this.field_73735_i, MARK_SIZE, color);
/*     */           } else {
/* 280 */             builder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
/* 281 */             double S = 30.0D;
/* 282 */             if (x < S) {
/* 283 */               drawRhombus(builder, 1, S, DSH / 2.0D, this.field_73735_i, MARK_SIZE, color);
/* 284 */             } else if (x > DSW - S) {
/* 285 */               drawRhombus(builder, 4, DSW - S, DSH / 2.0D, this.field_73735_i, MARK_SIZE, color);
/*     */             } 
/*     */             
/* 288 */             if (y < S) {
/* 289 */               drawRhombus(builder, 8, DSW / 2.0D, S, this.field_73735_i, MARK_SIZE, color);
/* 290 */             } else if (y > DSH - S * 2.0D) {
/* 291 */               drawRhombus(builder, 2, DSW / 2.0D, DSH - S * 2.0D, this.field_73735_i, MARK_SIZE, color);
/*     */             } 
/*     */           } 
/*     */           
/* 295 */           tessellator.func_78381_a();
/*     */         } 
/*     */       } 
/*     */       
/* 299 */       if (i == 0) {
/* 300 */         tessellator.func_78381_a();
/*     */       }
/*     */     } 
/*     */     
/* 304 */     GL11.glDepthMask(true);
/* 305 */     GL11.glEnable(3553);
/* 306 */     GL11.glDisable(3042);
/*     */   }
/*     */   
/*     */   public void drawTriangle1(BufferBuilder builder, double x, double y, double size, int color) {
/* 310 */     int red = color >> 16 & 0xFF;
/* 311 */     int green = color >> 8 & 0xFF;
/* 312 */     int blue = color >> 0 & 0xFF;
/* 313 */     int alpha = color >> 24 & 0xFF;
/* 314 */     builder.func_181662_b(x + size / 2.0D, y - 10.0D - size, this.field_73735_i).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 315 */     builder.func_181662_b(x - size / 2.0D, y - 10.0D - size, this.field_73735_i).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 316 */     builder.func_181662_b(x + 0.0D, y - 10.0D, this.field_73735_i).func_181669_b(red, green, blue, alpha).func_181675_d();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_GuiTargetMarker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */