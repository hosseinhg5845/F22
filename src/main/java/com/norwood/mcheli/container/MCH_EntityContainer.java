/*     */ package com.norwood.mcheli.container;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_IEntityCanRideAircraft;
/*     */ import com.norwood.mcheli.aircraft.MCH_SeatRackInfo;
/*     */ import com.norwood.mcheli.wrapper.W_AxisAlignedBB;
/*     */ import com.norwood.mcheli.wrapper.W_Block;
/*     */ import com.norwood.mcheli.wrapper.W_Blocks;
/*     */ import com.norwood.mcheli.wrapper.W_EntityContainer;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityContainer extends W_EntityContainer implements MCH_IEntityCanRideAircraft {
/*  34 */   private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.func_187226_a(MCH_EntityContainer.class, DataSerializers.field_187192_b); public static final float Y_OFFSET = 0.5F;
/*  35 */   private static final DataParameter<Integer> FORWARD_DIR = EntityDataManager.func_187226_a(MCH_EntityContainer.class, DataSerializers.field_187192_b);
/*  36 */   private static final DataParameter<Integer> DAMAGE_TAKEN = EntityDataManager.func_187226_a(MCH_EntityContainer.class, DataSerializers.field_187192_b);
/*  37 */   private double speedMultiplier = 0.07D;
/*     */   private int boatPosRotationIncrements;
/*     */   private double boatX;
/*     */   private double boatY;
/*     */   private double boatZ;
/*     */   private double boatYaw;
/*     */   private double boatPitch;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityY;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityZ;
/*     */   
/*     */   public MCH_EntityContainer(World par1World) {
/*  52 */     super(par1World);
/*  53 */     this.field_70156_m = true;
/*  54 */     func_70105_a(2.0F, 1.0F);
/*  55 */     this.field_70138_W = 0.6F;
/*  56 */     this.field_70178_ae = true;
/*  57 */     this._renderDistanceWeight = 2.0D;
/*     */   }
/*     */   
/*     */   public MCH_EntityContainer(World par1World, double par2, double par4, double par6) {
/*  61 */     this(par1World);
/*  62 */     func_70107_b(par2, par4 + 0.5D, par6);
/*  63 */     this.field_70159_w = 0.0D;
/*  64 */     this.field_70181_x = 0.0D;
/*  65 */     this.field_70179_y = 0.0D;
/*  66 */     this.field_70169_q = par2;
/*  67 */     this.field_70167_r = par4;
/*  68 */     this.field_70166_s = par6;
/*     */   }
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  77 */     this.field_70180_af.func_187214_a(TIME_SINCE_HIT, Integer.valueOf(0));
/*  78 */     this.field_70180_af.func_187214_a(FORWARD_DIR, Integer.valueOf(1));
/*  79 */     this.field_70180_af.func_187214_a(DAMAGE_TAKEN, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/*  83 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/*  87 */     return func_174813_aQ();
/*     */   }
/*     */   
/*     */   public boolean func_70104_M() {
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  96 */     return 54;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInvName() {
/* 101 */     return "Container " + super.getInvName();
/*     */   }
/*     */   
/*     */   public double func_70042_X() {
/* 105 */     return -0.3D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource ds, float damage) {
/* 110 */     if (func_180431_b(ds))
/* 111 */       return false; 
/* 112 */     if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
/* 113 */       damage = MCH_Config.applyDamageByExternal((Entity)this, ds, damage);
/* 114 */       if (!MCH_Multiplay.canAttackEntity(ds, (Entity)this))
/* 115 */         return false; 
/* 116 */       if (ds.func_76346_g() instanceof EntityPlayer && ds.func_76355_l().equalsIgnoreCase("player")) {
/* 117 */         MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityContainer.attackEntityFrom:damage=%.1f:%s", new Object[] { Float.valueOf(damage), ds.func_76355_l() });
/* 118 */         W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "hit", 1.0F, 1.3F);
/* 119 */         setDamageTaken(getDamageTaken() + (int)(damage * 20.0F));
/* 120 */         setForwardDirection(-getForwardDirection());
/* 121 */         setTimeSinceHit(10);
/* 122 */         func_70018_K();
/* 123 */         boolean flag = (ds.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)ds.func_76346_g()).field_71075_bZ.field_75098_d);
/* 124 */         if (flag || getDamageTaken() > 40.0F) {
/* 125 */           if (!flag) {
/* 126 */             func_145778_a((Item)MCH_MOD.itemContainer, 1, 0.0F);
/*     */           }
/*     */           
/* 129 */           func_70106_y();
/*     */         } 
/*     */         
/* 132 */         return true;
/*     */       } 
/* 134 */       return false;
/*     */     } 
/*     */     
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70057_ab() {
/* 143 */     setForwardDirection(-getForwardDirection());
/* 144 */     setTimeSinceHit(10);
/* 145 */     setDamageTaken(getDamageTaken() * 11);
/*     */   }
/*     */   
/*     */   public boolean func_70067_L() {
/* 149 */     return !this.field_70128_L;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
/* 154 */     this.boatPosRotationIncrements = par9 + 10;
/* 155 */     this.boatX = par1;
/* 156 */     this.boatY = par3;
/* 157 */     this.boatZ = par5;
/* 158 */     this.boatYaw = par7;
/* 159 */     this.boatPitch = par8;
/* 160 */     this.field_70159_w = this.velocityX;
/* 161 */     this.field_70181_x = this.velocityY;
/* 162 */     this.field_70179_y = this.velocityZ;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double par1, double par3, double par5) {
/* 167 */     this.velocityX = this.field_70159_w = par1;
/* 168 */     this.velocityY = this.field_70181_x = par3;
/* 169 */     this.velocityZ = this.field_70179_y = par5;
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/* 173 */     super.func_70071_h_();
/* 174 */     if (getTimeSinceHit() > 0) {
/* 175 */       setTimeSinceHit(getTimeSinceHit() - 1);
/*     */     }
/*     */     
/* 178 */     if (getDamageTaken() > 0.0F) {
/* 179 */       setDamageTaken(getDamageTaken() - 1);
/*     */     }
/*     */     
/* 182 */     this.field_70169_q = this.field_70165_t;
/* 183 */     this.field_70167_r = this.field_70163_u;
/* 184 */     this.field_70166_s = this.field_70161_v;
/* 185 */     byte b0 = 5;
/* 186 */     double d0 = 0.0D;
/*     */     
/* 188 */     for (int i = 0; i < b0; i++) {
/* 189 */       AxisAlignedBB boundingBox = func_174813_aQ();
/* 190 */       double d1 = boundingBox.field_72338_b + (boundingBox.field_72337_e - boundingBox.field_72338_b) * i / b0 - 0.125D;
/* 191 */       double d2 = boundingBox.field_72338_b + (boundingBox.field_72337_e - boundingBox.field_72338_b) * (i + 1) / b0 - 0.125D;
/* 192 */       AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(boundingBox.field_72340_a, d1, boundingBox.field_72339_c, boundingBox.field_72336_d, d2, boundingBox.field_72334_f);
/* 193 */       if (this.field_70170_p.func_72875_a(axisalignedbb, Material.field_151586_h)) {
/* 194 */         d0 += 1.0D / b0;
/* 195 */       } else if (this.field_70170_p.func_72875_a(axisalignedbb, Material.field_151587_i)) {
/* 196 */         d0 += 1.0D / b0;
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     double d3 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 201 */     if (d3 > 0.2625D);
/*     */ 
/*     */     
/* 204 */     if (this.field_70170_p.field_72995_K) {
/* 205 */       if (this.boatPosRotationIncrements > 0) {
/* 206 */         double d4 = this.field_70165_t + (this.boatX - this.field_70165_t) / this.boatPosRotationIncrements;
/* 207 */         double d5 = this.field_70163_u + (this.boatY - this.field_70163_u) / this.boatPosRotationIncrements;
/* 208 */         double d11 = this.field_70161_v + (this.boatZ - this.field_70161_v) / this.boatPosRotationIncrements;
/* 209 */         double d10 = MathHelper.func_76138_g(this.boatYaw - this.field_70177_z);
/* 210 */         this.field_70177_z = (float)(this.field_70177_z + d10 / this.boatPosRotationIncrements);
/* 211 */         this.field_70125_A = (float)(this.field_70125_A + (this.boatPitch - this.field_70125_A) / this.boatPosRotationIncrements);
/* 212 */         this.boatPosRotationIncrements--;
/* 213 */         func_70107_b(d4, d5, d11);
/* 214 */         func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */       } else {
/* 216 */         double d4 = this.field_70165_t + this.field_70159_w;
/* 217 */         double d5 = this.field_70163_u + this.field_70181_x;
/* 218 */         double d11 = this.field_70161_v + this.field_70179_y;
/* 219 */         func_70107_b(d4, d5, d11);
/* 220 */         if (this.field_70122_E) {
/* 221 */           this.field_70159_w *= 0.8999999761581421D;
/* 222 */           this.field_70179_y *= 0.8999999761581421D;
/*     */         } 
/*     */         
/* 225 */         this.field_70159_w *= 0.99D;
/* 226 */         this.field_70181_x *= 0.95D;
/* 227 */         this.field_70179_y *= 0.99D;
/*     */       } 
/*     */     } else {
/* 230 */       if (d0 < 1.0D) {
/* 231 */         double d = d0 * 2.0D - 1.0D;
/* 232 */         this.field_70181_x += 0.04D * d;
/*     */       } else {
/* 234 */         if (this.field_70181_x < 0.0D) {
/* 235 */           this.field_70181_x /= 2.0D;
/*     */         }
/*     */         
/* 238 */         this.field_70181_x += 0.007D;
/*     */       } 
/*     */       
/* 241 */       double d4 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 242 */       if (d4 > 0.35D) {
/* 243 */         double d = 0.35D / d4;
/* 244 */         this.field_70159_w *= d;
/* 245 */         this.field_70179_y *= d;
/* 246 */         d4 = 0.35D;
/*     */       } 
/*     */       
/* 249 */       if (d4 > d3 && this.speedMultiplier < 0.35D) {
/* 250 */         this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
/* 251 */         if (this.speedMultiplier > 0.35D) {
/* 252 */           this.speedMultiplier = 0.35D;
/*     */         }
/*     */       } else {
/* 255 */         this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
/* 256 */         if (this.speedMultiplier < 0.07D) {
/* 257 */           this.speedMultiplier = 0.07D;
/*     */         }
/*     */       } 
/*     */       
/* 261 */       if (this.field_70122_E) {
/* 262 */         this.field_70159_w *= 0.8999999761581421D;
/* 263 */         this.field_70179_y *= 0.8999999761581421D;
/*     */       } 
/*     */       
/* 266 */       func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 267 */       this.field_70159_w *= 0.99D;
/* 268 */       this.field_70181_x *= 0.95D;
/* 269 */       this.field_70179_y *= 0.99D;
/* 270 */       this.field_70125_A = 0.0F;
/* 271 */       double d5 = this.field_70177_z;
/* 272 */       double d11 = this.field_70169_q - this.field_70165_t;
/* 273 */       double d10 = this.field_70166_s - this.field_70161_v;
/* 274 */       if (d11 * d11 + d10 * d10 > 0.001D) {
/* 275 */         d5 = (float)(Math.atan2(d10, d11) * 180.0D / Math.PI);
/*     */       }
/*     */       
/* 278 */       double d12 = MathHelper.func_76138_g(d5 - this.field_70177_z);
/* 279 */       if (d12 > 5.0D) {
/* 280 */         d12 = 5.0D;
/*     */       }
/*     */       
/* 283 */       if (d12 < -5.0D) {
/* 284 */         d12 = -5.0D;
/*     */       }
/*     */       
/* 287 */       this.field_70177_z = (float)(this.field_70177_z + d12);
/* 288 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/* 289 */       if (!this.field_70170_p.field_72995_K) {
/* 290 */         List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72314_b(0.2D, 0.0D, 0.2D));
/* 291 */         if (!list.isEmpty()) {
/* 292 */           for (Entity entity : list) {
/* 293 */             if (entity.func_70104_M() && entity instanceof MCH_EntityContainer) {
/* 294 */               entity.func_70108_f((Entity)this);
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 299 */         if (MCH_Config.Collision_DestroyBlock.prmBool) {
/* 300 */           for (int lx = 0; lx < 4; lx++) {
/* 301 */             int i1 = MathHelper.func_76128_c(this.field_70165_t + ((lx % 2) - 0.5D) * 0.8D);
/* 302 */             int j1 = MathHelper.func_76128_c(this.field_70161_v + (lx / 2.0D - 0.5D) * 0.8D);
/*     */             
/* 304 */             for (int k1 = 0; k1 < 2; k1++) {
/* 305 */               int l1 = MathHelper.func_76128_c(this.field_70163_u) + k1;
/* 306 */               if (W_WorldFunc.isEqualBlock(this.field_70170_p, i1, l1, j1, W_Block.getSnowLayer())) {
/* 307 */                 this.field_70170_p.func_175698_g(new BlockPos(i1, l1, j1));
/* 308 */               } else if (W_WorldFunc.isEqualBlock(this.field_70170_p, i1, l1, j1, W_Blocks.field_150392_bi)) {
/* 309 */                 W_WorldFunc.destroyBlock(this.field_70170_p, i1, l1, j1, true);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 320 */     super.func_70014_b(par1NBTTagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 325 */     super.func_70037_a(par1NBTTagCompound);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 330 */     return 2.0F;
/*     */   }
/*     */   
/*     */   public boolean func_184230_a(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 334 */     displayInventory(player);
/*     */     
/* 336 */     return true;
/*     */   }
/*     */   
/*     */   public int getDamageTaken() {
/* 340 */     return ((Integer)this.field_70180_af.func_187225_a(DAMAGE_TAKEN)).intValue();
/*     */   }
/*     */   
/*     */   public void setDamageTaken(int par1) {
/* 344 */     this.field_70180_af.func_187227_b(DAMAGE_TAKEN, Integer.valueOf(par1));
/*     */   }
/*     */   
/*     */   public int getTimeSinceHit() {
/* 348 */     return ((Integer)this.field_70180_af.func_187225_a(TIME_SINCE_HIT)).intValue();
/*     */   }
/*     */   
/*     */   public void setTimeSinceHit(int par1) {
/* 352 */     this.field_70180_af.func_187227_b(TIME_SINCE_HIT, Integer.valueOf(par1));
/*     */   }
/*     */   
/*     */   public int getForwardDirection() {
/* 356 */     return ((Integer)this.field_70180_af.func_187225_a(FORWARD_DIR)).intValue();
/*     */   }
/*     */   
/*     */   public void setForwardDirection(int par1) {
/* 360 */     this.field_70180_af.func_187227_b(FORWARD_DIR, Integer.valueOf(par1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canRideAircraft(MCH_EntityAircraft ac, int seatID, MCH_SeatRackInfo info) {
/* 365 */     for (String s : info.names) {
/* 366 */       if (s.equalsIgnoreCase("container")) {
/* 367 */         return (ac.func_184187_bx() == null && func_184187_bx() == null);
/*     */       }
/*     */     } 
/*     */     
/* 371 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSkipNormalRender() {
/* 376 */     return func_184187_bx() instanceof com.norwood.mcheli.aircraft.MCH_EntitySeat;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\container\MCH_EntityContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */