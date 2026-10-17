/*     */ package com.norwood.mcheli.tank;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.particles.MCH_ParticlesUtil;
/*     */ import com.norwood.mcheli.wrapper.W_Block;
/*     */ import com.norwood.mcheli.wrapper.W_Blocks;
/*     */ import com.norwood.mcheli.wrapper.W_Lib;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MCH_WheelManager
/*     */ {
/*  25 */   private static final Random rand = new Random();
/*     */   public final MCH_EntityAircraft parent;
/*     */   public MCH_EntityWheel[] wheels;
/*     */   public Vec3d weightedCenter;
/*     */   public float targetPitch;
/*     */   public float targetRoll;
/*     */   public float prevYaw;
/*     */   private double minZ;
/*     */   private double maxZ;
/*     */   private double avgZ;
/*     */   
/*     */   public MCH_WheelManager(MCH_EntityAircraft ac) {
/*  37 */     this.parent = ac;
/*  38 */     this.wheels = new MCH_EntityWheel[0];
/*  39 */     this.weightedCenter = Vec3d.field_186680_a;
/*     */   }
/*     */   
/*     */   public void createWheels(World w, List<MCH_AircraftInfo.Wheel> list, Vec3d weightedCenter) {
/*  43 */     this.wheels = new MCH_EntityWheel[list.size() * 2];
/*  44 */     this.minZ = 999999.0D;
/*  45 */     this.maxZ = -999999.0D;
/*  46 */     this.weightedCenter = weightedCenter;
/*     */     
/*  48 */     for (int i = 0; i < this.wheels.length; i++) {
/*  49 */       MCH_EntityWheel wheel = new MCH_EntityWheel(w);
/*  50 */       wheel.setParents(this.parent);
/*  51 */       Vec3d wp = ((MCH_AircraftInfo.Wheel)list.get(i / 2)).pos;
/*  52 */       wheel.setWheelPos(new Vec3d((i % 2 == 0) ? wp.field_72450_a : -wp.field_72450_a, wp.field_72448_b, wp.field_72449_c), this.weightedCenter);
/*  53 */       Vec3d v = this.parent.getTransformedPosition(wheel.pos.field_72450_a, wheel.pos.field_72448_b, wheel.pos.field_72449_c);
/*  54 */       wheel.func_70012_b(v.field_72450_a, v.field_72448_b + 1.0D, v.field_72449_c, 0.0F, 0.0F);
/*  55 */       this.wheels[i] = wheel;
/*  56 */       if (wheel.pos.field_72449_c <= this.minZ) {
/*  57 */         this.minZ = wheel.pos.field_72449_c;
/*     */       }
/*     */       
/*  60 */       if (wheel.pos.field_72449_c >= this.maxZ) {
/*  61 */         this.maxZ = wheel.pos.field_72449_c;
/*     */       }
/*     */     } 
/*     */     
/*  65 */     this.avgZ = this.maxZ - this.minZ;
/*     */   }
/*     */   
/*     */   public void move(double x, double y, double z) {
/*  69 */     MCH_EntityAircraft ac = this.parent;
/*  70 */     if (ac.getAcInfo() != null) {
/*  71 */       boolean showLog = false;
/*     */       
/*  73 */       for (MCH_EntityWheel wheel : this.wheels) {
/*  74 */         wheel.field_70169_q = wheel.field_70165_t;
/*  75 */         wheel.field_70167_r = wheel.field_70163_u;
/*  76 */         wheel.field_70166_s = wheel.field_70161_v;
/*  77 */         Vec3d v = ac.getTransformedPosition(wheel.pos.field_72450_a, wheel.pos.field_72448_b, wheel.pos.field_72449_c);
/*  78 */         wheel.field_70159_w = v.field_72450_a - wheel.field_70165_t + x;
/*  79 */         wheel.field_70181_x = v.field_72448_b - wheel.field_70163_u;
/*  80 */         wheel.field_70179_y = v.field_72449_c - wheel.field_70161_v + z;
/*     */       } 
/*     */       
/*  83 */       for (MCH_EntityWheel wheel : this.wheels) {
/*  84 */         wheel.field_70181_x *= 0.15D;
/*  85 */         wheel.func_70091_d(MoverType.SELF, wheel.field_70159_w, wheel.field_70181_x, wheel.field_70179_y);
/*  86 */         double f = 1.0D;
/*  87 */         wheel.func_70091_d(MoverType.SELF, 0.0D, -0.1D * f, 0.0D);
/*     */       } 
/*     */       
/*  90 */       int zmog = -1;
/*     */       
/*  92 */       for (int i = 0; i < this.wheels.length / 2; i++) {
/*  93 */         zmog = i;
/*  94 */         MCH_EntityWheel w1 = this.wheels[i * 2];
/*  95 */         MCH_EntityWheel w2 = this.wheels[i * 2 + 1];
/*  96 */         if (!w1.isPlus && (w1.field_70122_E || w2.field_70122_E)) {
/*  97 */           zmog = -1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 102 */       if (zmog >= 0) {
/* 103 */         (this.wheels[zmog * 2]).field_70122_E = true;
/* 104 */         (this.wheels[zmog * 2 + 1]).field_70122_E = true;
/*     */       } 
/*     */       
/* 107 */       zmog = -1;
/*     */       
/* 109 */       for (int ix = this.wheels.length / 2 - 1; ix >= 0; ix--) {
/* 110 */         zmog = ix;
/* 111 */         MCH_EntityWheel w1 = this.wheels[ix * 2];
/* 112 */         MCH_EntityWheel w2 = this.wheels[ix * 2 + 1];
/* 113 */         if (w1.isPlus && (w1.field_70122_E || w2.field_70122_E)) {
/* 114 */           zmog = -1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 119 */       if (zmog >= 0) {
/* 120 */         (this.wheels[zmog * 2]).field_70122_E = true;
/* 121 */         (this.wheels[zmog * 2 + 1]).field_70122_E = true;
/*     */       } 
/*     */       
/* 124 */       Vec3d rv = Vec3d.field_186680_a;
/* 125 */       Vec3d wc = ac.getTransformedPosition(this.weightedCenter);
/* 126 */       wc = new Vec3d(wc.field_72450_a - ac.field_70165_t, this.weightedCenter.field_72448_b, wc.field_72449_c - ac.field_70161_v);
/*     */       
/* 128 */       for (int ixx = 0; ixx < this.wheels.length / 2; ixx++) {
/* 129 */         MCH_EntityWheel w1 = this.wheels[ixx * 2];
/* 130 */         MCH_EntityWheel w2 = this.wheels[ixx * 2 + 1];
/* 131 */         Vec3d v1 = new Vec3d(w1.field_70165_t - ac.field_70165_t + wc.field_72450_a, w1.field_70163_u - ac.field_70163_u + wc.field_72448_b, w1.field_70161_v - ac.field_70161_v + wc.field_72449_c);
/* 132 */         Vec3d v2 = new Vec3d(w2.field_70165_t - ac.field_70165_t + wc.field_72450_a, w2.field_70163_u - ac.field_70163_u + wc.field_72448_b, w2.field_70161_v - ac.field_70161_v + wc.field_72449_c);
/* 133 */         Vec3d v = (w1.pos.field_72449_c >= 0.0D) ? v2.func_72431_c(v1) : v1.func_72431_c(v2);
/* 134 */         v = v.func_72432_b();
/* 135 */         double f = Math.abs(w1.pos.field_72449_c / this.avgZ);
/* 136 */         if (!w1.field_70122_E && !w2.field_70122_E) {
/* 137 */           f = 0.0D;
/*     */         }
/*     */         
/* 140 */         rv = rv.func_72441_c(v.field_72450_a * f, v.field_72448_b * f, v.field_72449_c * f);
/*     */       } 
/*     */       
/* 143 */       rv = rv.func_72432_b();
/* 144 */       if (rv.field_72448_b > 0.01D && rv.field_72448_b < 0.7D) {
/* 145 */         ac.field_70159_w += rv.field_72450_a / 50.0D;
/* 146 */         ac.field_70179_y += rv.field_72449_c / 50.0D;
/*     */       } 
/*     */       
/* 149 */       rv = rv.func_178785_b((float)(ac.getRotYaw() * Math.PI / 180.0D));
/* 150 */       float pitch = (float)(90.0D - Math.atan2(rv.field_72448_b, rv.field_72449_c) * 180.0D / Math.PI);
/* 151 */       float roll = -((float)(90.0D - Math.atan2(rv.field_72448_b, rv.field_72450_a) * 180.0D / Math.PI));
/* 152 */       float ogpf = (ac.getAcInfo()).onGroundPitchFactor;
/* 153 */       if (pitch - ac.getRotPitch() > ogpf) {
/* 154 */         pitch = ac.getRotPitch() + ogpf;
/*     */       }
/*     */       
/* 157 */       if (pitch - ac.getRotPitch() < -ogpf) {
/* 158 */         pitch = ac.getRotPitch() - ogpf;
/*     */       }
/*     */       
/* 161 */       float ogrf = (ac.getAcInfo()).onGroundRollFactor;
/* 162 */       if (roll - ac.getRotRoll() > ogrf) {
/* 163 */         roll = ac.getRotRoll() + ogrf;
/*     */       }
/*     */       
/* 166 */       if (roll - ac.getRotRoll() < -ogrf) {
/* 167 */         roll = ac.getRotRoll() - ogrf;
/*     */       }
/*     */       
/* 170 */       this.targetPitch = pitch;
/* 171 */       this.targetRoll = roll;
/* 172 */       if (!W_Lib.isClientPlayer(ac.getRiddenByEntity())) {
/* 173 */         ac.setRotPitch(pitch);
/* 174 */         ac.setRotRoll(roll);
/*     */       } 
/*     */       
/* 177 */       for (MCH_EntityWheel wheel : this.wheels) {
/* 178 */         Vec3d vx = getTransformedPosition(wheel.pos.field_72450_a, wheel.pos.field_72448_b, wheel.pos.field_72449_c, ac, ac.getRotYaw(), this.targetPitch, this.targetRoll);
/* 179 */         double rangeH = 2.0D;
/* 180 */         double poy = (wheel.field_70138_W / 2.0F);
/* 181 */         if (wheel.field_70165_t > vx.field_72450_a + rangeH) {
/* 182 */           wheel.field_70165_t = vx.field_72450_a + rangeH;
/* 183 */           wheel.field_70163_u = vx.field_72448_b + poy;
/*     */         } 
/*     */         
/* 186 */         if (wheel.field_70165_t < vx.field_72450_a - rangeH) {
/* 187 */           wheel.field_70165_t = vx.field_72450_a - rangeH;
/* 188 */           wheel.field_70163_u = vx.field_72448_b + poy;
/*     */         } 
/*     */         
/* 191 */         if (wheel.field_70161_v > vx.field_72449_c + rangeH) {
/* 192 */           wheel.field_70161_v = vx.field_72449_c + rangeH;
/* 193 */           wheel.field_70163_u = vx.field_72448_b + poy;
/*     */         } 
/*     */         
/* 196 */         if (wheel.field_70161_v < vx.field_72449_c - rangeH) {
/* 197 */           wheel.field_70161_v = vx.field_72449_c - rangeH;
/* 198 */           wheel.field_70163_u = vx.field_72448_b + poy;
/*     */         } 
/*     */         
/* 201 */         wheel.func_70080_a(wheel.field_70165_t, wheel.field_70163_u, wheel.field_70161_v, 0.0F, 0.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Vec3d getTransformedPosition(double x, double y, double z, MCH_EntityAircraft ac, float yaw, float pitch, float roll) {
/* 207 */     Vec3d v = MCH_Lib.RotVec3(x, y, z, -yaw, -pitch, -roll);
/* 208 */     return v.func_72441_c(ac.field_70165_t, ac.field_70163_u, ac.field_70161_v);
/*     */   }
/*     */   
/*     */   public void updateBlock() {
/* 212 */     if (MCH_Config.Collision_DestroyBlock.prmBool) {
/* 213 */       MCH_EntityAircraft ac = this.parent;
/*     */       
/* 215 */       for (MCH_EntityWheel w : this.wheels) {
/* 216 */         Vec3d v = ac.getTransformedPosition(w.pos);
/* 217 */         int x = (int)(v.field_72450_a + 0.5D);
/* 218 */         int y = (int)(v.field_72448_b + 0.5D);
/* 219 */         int z = (int)(v.field_72449_c + 0.5D);
/* 220 */         BlockPos blockpos = new BlockPos(x, y, z);
/* 221 */         IBlockState iblockstate = ac.field_70170_p.func_180495_p(blockpos);
/* 222 */         if (iblockstate.func_177230_c() == W_Block.getSnowLayer()) {
/* 223 */           ac.field_70170_p.func_175698_g(blockpos);
/*     */         }
/*     */         
/* 226 */         if (iblockstate.func_177230_c() == W_Blocks.field_150392_bi || iblockstate.func_177230_c() == W_Blocks.field_150414_aQ) {
/* 227 */           W_WorldFunc.destroyBlock(ac.field_70170_p, x, y, z, false);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void particleLandingGear() {
/* 234 */     if (this.wheels.length > 0) {
/* 235 */       MCH_EntityAircraft ac = this.parent;
/* 236 */       double d = ac.field_70159_w * ac.field_70159_w + ac.field_70179_y * ac.field_70179_y + Math.abs(this.prevYaw - ac.getRotYaw());
/* 237 */       this.prevYaw = ac.getRotYaw();
/* 238 */       if (d > 0.001D)
/* 239 */         for (int i = 0; i < 2; i++) {
/* 240 */           MCH_EntityWheel w = this.wheels[rand.nextInt(this.wheels.length)];
/* 241 */           Vec3d v = ac.getTransformedPosition(w.pos);
/* 242 */           int x = MathHelper.func_76128_c(v.field_72450_a + 0.5D);
/* 243 */           int y = MathHelper.func_76128_c(v.field_72448_b - 0.5D);
/* 244 */           int z = MathHelper.func_76128_c(v.field_72449_c + 0.5D);
/* 245 */           BlockPos blockpos = new BlockPos(x, y, z);
/* 246 */           IBlockState iblockstate = ac.field_70170_p.func_180495_p(blockpos);
/* 247 */           if (Block.func_149680_a(iblockstate.func_177230_c(), Blocks.field_150350_a)) {
/* 248 */             y = MathHelper.func_76128_c(v.field_72448_b + 0.5D);
/* 249 */             blockpos = new BlockPos(x, y, z);
/* 250 */             iblockstate = ac.field_70170_p.func_180495_p(blockpos);
/*     */           } 
/*     */           
/* 253 */           if (!Block.func_149680_a(iblockstate.func_177230_c(), Blocks.field_150350_a))
/* 254 */             MCH_ParticlesUtil.spawnParticleTileCrack(ac.field_70170_p, x, y, z, v.field_72450_a + rand
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 259 */                 .nextFloat() - 0.5D, v.field_72448_b + 0.1D, v.field_72449_c + rand
/*     */                 
/* 261 */                 .nextFloat() - 0.5D, -ac.field_70159_w * 4.0D + (rand
/* 262 */                 .nextFloat() - 0.5D) * 0.1D, rand
/* 263 */                 .nextFloat() * 0.5D, -ac.field_70179_y * 4.0D + (rand
/* 264 */                 .nextFloat() - 0.5D) * 0.1D); 
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tank\MCH_WheelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */