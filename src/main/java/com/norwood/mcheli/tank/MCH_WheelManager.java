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
/*  39 */     this.weightedCenter = Vec3d.ZERO;
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
/*  52 */       wheel.setWheelPos(new Vec3d((i % 2 == 0) ? wp.x : -wp.x, wp.y, wp.z), this.weightedCenter);
/*  53 */       Vec3d v = this.parent.getTransformedPosition(wheel.pos.x, wheel.pos.y, wheel.pos.z);
/*  54 */       wheel.setLocationAndAngles(v.x, v.y + 1.0D, v.z, 0.0F, 0.0F);
/*  55 */       this.wheels[i] = wheel;
/*  56 */       if (wheel.pos.z <= this.minZ) {
/*  57 */         this.minZ = wheel.pos.z;
/*     */       }
/*     */       
/*  60 */       if (wheel.pos.z >= this.maxZ) {
/*  61 */         this.maxZ = wheel.pos.z;
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
/*  74 */         wheel.prevPosX = wheel.posX;
/*  75 */         wheel.prevPosY = wheel.posY;
/*  76 */         wheel.prevPosZ = wheel.posZ;
/*  77 */         Vec3d v = ac.getTransformedPosition(wheel.pos.x, wheel.pos.y, wheel.pos.z);
/*  78 */         wheel.motionX = v.x - wheel.posX + x;
/*  79 */         wheel.motionY = v.y - wheel.posY;
/*  80 */         wheel.motionZ = v.z - wheel.posZ + z;
/*     */       } 
/*     */       
/*  83 */       for (MCH_EntityWheel wheel : this.wheels) {
/*  84 */         wheel.motionY *= 0.15D;
/*  85 */         wheel.move(MoverType.SELF, wheel.motionX, wheel.motionY, wheel.motionZ);
/*  86 */         double f = 1.0D;
/*  87 */         wheel.move(MoverType.SELF, 0.0D, -0.1D * f, 0.0D);
/*     */       } 
/*     */       
/*  90 */       int zmog = -1;
/*     */       
/*  92 */       for (int i = 0; i < this.wheels.length / 2; i++) {
/*  93 */         zmog = i;
/*  94 */         MCH_EntityWheel w1 = this.wheels[i * 2];
/*  95 */         MCH_EntityWheel w2 = this.wheels[i * 2 + 1];
/*  96 */         if (!w1.isPlus && (w1.onGround || w2.onGround)) {
/*  97 */           zmog = -1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 102 */       if (zmog >= 0) {
/* 103 */         (this.wheels[zmog * 2]).onGround = true;
/* 104 */         (this.wheels[zmog * 2 + 1]).onGround = true;
/*     */       } 
/*     */       
/* 107 */       zmog = -1;
/*     */       
/* 109 */       for (int ix = this.wheels.length / 2 - 1; ix >= 0; ix--) {
/* 110 */         zmog = ix;
/* 111 */         MCH_EntityWheel w1 = this.wheels[ix * 2];
/* 112 */         MCH_EntityWheel w2 = this.wheels[ix * 2 + 1];
/* 113 */         if (w1.isPlus && (w1.onGround || w2.onGround)) {
/* 114 */           zmog = -1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 119 */       if (zmog >= 0) {
/* 120 */         (this.wheels[zmog * 2]).onGround = true;
/* 121 */         (this.wheels[zmog * 2 + 1]).onGround = true;
/*     */       } 
/*     */       
/* 124 */       Vec3d rv = Vec3d.ZERO;
/* 125 */       Vec3d wc = ac.getTransformedPosition(this.weightedCenter);
/* 126 */       wc = new Vec3d(wc.x - ac.posX, this.weightedCenter.y, wc.z - ac.posZ);
/*     */       
/* 128 */       for (int ixx = 0; ixx < this.wheels.length / 2; ixx++) {
/* 129 */         MCH_EntityWheel w1 = this.wheels[ixx * 2];
/* 130 */         MCH_EntityWheel w2 = this.wheels[ixx * 2 + 1];
/* 131 */         Vec3d v1 = new Vec3d(w1.posX - ac.posX + wc.x, w1.posY - ac.posY + wc.y, w1.posZ - ac.posZ + wc.z);
/* 132 */         Vec3d v2 = new Vec3d(w2.posX - ac.posX + wc.x, w2.posY - ac.posY + wc.y, w2.posZ - ac.posZ + wc.z);
/* 133 */         Vec3d v = (w1.pos.z >= 0.0D) ? v2.crossProduct(v1) : v1.crossProduct(v2);
/* 134 */         v = v.normalize();
/* 135 */         double f = Math.abs(w1.pos.z / this.avgZ);
/* 136 */         if (!w1.onGround && !w2.onGround) {
/* 137 */           f = 0.0D;
/*     */         }
/*     */         
/* 140 */         rv = rv.addVector(v.x * f, v.y * f, v.z * f);
/*     */       } 
/*     */       
/* 143 */       rv = rv.normalize();
/* 144 */       if (rv.y > 0.01D && rv.y < 0.7D) {
/* 145 */         ac.motionX += rv.x / 50.0D;
/* 146 */         ac.motionZ += rv.z / 50.0D;
/*     */       } 
/*     */       
/* 149 */       rv = rv.rotateYaw((float)(ac.getRotYaw() * Math.PI / 180.0D));
/* 150 */       float pitch = (float)(90.0D - Math.atan2(rv.y, rv.z) * 180.0D / Math.PI);
/* 151 */       float roll = -((float)(90.0D - Math.atan2(rv.y, rv.x) * 180.0D / Math.PI));
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
/* 178 */         Vec3d vx = getTransformedPosition(wheel.pos.x, wheel.pos.y, wheel.pos.z, ac, ac.getRotYaw(), this.targetPitch, this.targetRoll);
/* 179 */         double rangeH = 2.0D;
/* 180 */         double poy = (wheel.stepHeight / 2.0F);
/* 181 */         if (wheel.posX > vx.x + rangeH) {
/* 182 */           wheel.posX = vx.x + rangeH;
/* 183 */           wheel.posY = vx.y + poy;
/*     */         } 
/*     */         
/* 186 */         if (wheel.posX < vx.x - rangeH) {
/* 187 */           wheel.posX = vx.x - rangeH;
/* 188 */           wheel.posY = vx.y + poy;
/*     */         } 
/*     */         
/* 191 */         if (wheel.posZ > vx.z + rangeH) {
/* 192 */           wheel.posZ = vx.z + rangeH;
/* 193 */           wheel.posY = vx.y + poy;
/*     */         } 
/*     */         
/* 196 */         if (wheel.posZ < vx.z - rangeH) {
/* 197 */           wheel.posZ = vx.z - rangeH;
/* 198 */           wheel.posY = vx.y + poy;
/*     */         } 
/*     */         
/* 201 */         wheel.setPositionAndRotation(wheel.posX, wheel.posY, wheel.posZ, 0.0F, 0.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Vec3d getTransformedPosition(double x, double y, double z, MCH_EntityAircraft ac, float yaw, float pitch, float roll) {
/* 207 */     Vec3d v = MCH_Lib.RotVec3(x, y, z, -yaw, -pitch, -roll);
/* 208 */     return v.addVector(ac.posX, ac.posY, ac.posZ);
/*     */   }
/*     */   
/*     */   public void updateBlock() {
/* 212 */     if (MCH_Config.Collision_DestroyBlock.prmBool) {
/* 213 */       MCH_EntityAircraft ac = this.parent;
/*     */       
/* 215 */       for (MCH_EntityWheel w : this.wheels) {
/* 216 */         Vec3d v = ac.getTransformedPosition(w.pos);
/* 217 */         int x = (int)(v.x + 0.5D);
/* 218 */         int y = (int)(v.y + 0.5D);
/* 219 */         int z = (int)(v.z + 0.5D);
/* 220 */         BlockPos blockpos = new BlockPos(x, y, z);
/* 221 */         IBlockState iblockstate = ac.world.getBlockState(blockpos);
/* 222 */         if (iblockstate.getBlock() == W_Block.getSnowLayer()) {
/* 223 */           ac.world.setBlockToAir(blockpos);
/*     */         }
/*     */         
/* 226 */         if (iblockstate.getBlock() == W_Blocks.WATERLILY || iblockstate.getBlock() == W_Blocks.CAKE) {
/* 227 */           W_WorldFunc.destroyBlock(ac.world, x, y, z, false);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void particleLandingGear() {
/* 234 */     if (this.wheels.length > 0) {
/* 235 */       MCH_EntityAircraft ac = this.parent;
/* 236 */       double d = ac.motionX * ac.motionX + ac.motionZ * ac.motionZ + Math.abs(this.prevYaw - ac.getRotYaw());
/* 237 */       this.prevYaw = ac.getRotYaw();
/* 238 */       if (d > 0.001D)
/* 239 */         for (int i = 0; i < 2; i++) {
/* 240 */           MCH_EntityWheel w = this.wheels[rand.nextInt(this.wheels.length)];
/* 241 */           Vec3d v = ac.getTransformedPosition(w.pos);
/* 242 */           int x = MathHelper.floor(v.x + 0.5D);
/* 243 */           int y = MathHelper.floor(v.y - 0.5D);
/* 244 */           int z = MathHelper.floor(v.z + 0.5D);
/* 245 */           BlockPos blockpos = new BlockPos(x, y, z);
/* 246 */           IBlockState iblockstate = ac.world.getBlockState(blockpos);
/* 247 */           if (Block.isEqualTo(iblockstate.getBlock(), Blocks.AIR)) {
/* 248 */             y = MathHelper.floor(v.y + 0.5D);
/* 249 */             blockpos = new BlockPos(x, y, z);
/* 250 */             iblockstate = ac.world.getBlockState(blockpos);
/*     */           } 
/*     */           
/* 253 */           if (!Block.isEqualTo(iblockstate.getBlock(), Blocks.AIR))
/* 254 */             MCH_ParticlesUtil.spawnParticleTileCrack(ac.world, x, y, z, v.x + rand
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 259 */                 .nextFloat() - 0.5D, v.y + 0.1D, v.z + rand
/*     */                 
/* 261 */                 .nextFloat() - 0.5D, -ac.motionX * 4.0D + (rand
/* 262 */                 .nextFloat() - 0.5D) * 0.1D, rand
/* 263 */                 .nextFloat() * 0.5D, -ac.motionZ * 4.0D + (rand
/* 264 */                 .nextFloat() - 0.5D) * 0.1D); 
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tank\MCH_WheelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */