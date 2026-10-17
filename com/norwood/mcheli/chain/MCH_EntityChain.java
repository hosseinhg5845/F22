/*     */ package com.norwood.mcheli.chain;
/*     */ 
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityChain
/*     */   extends W_Entity {
/*  25 */   private static final DataParameter<Integer> TOWED_ID = EntityDataManager.func_187226_a(MCH_EntityChain.class, DataSerializers.field_187192_b);
/*  26 */   private static final DataParameter<Integer> TOW_ID = EntityDataManager.func_187226_a(MCH_EntityChain.class, DataSerializers.field_187192_b);
/*     */   public Entity towEntity;
/*     */   public Entity towedEntity;
/*     */   public String towEntityUUID;
/*     */   public String towedEntityUUID;
/*     */   private int isServerTowEntitySearchCount;
/*     */   private int chainLength;
/*     */   private boolean isTowing;
/*     */   
/*     */   public MCH_EntityChain(World world) {
/*  36 */     super(world);
/*  37 */     this.field_70156_m = true;
/*  38 */     func_70105_a(1.0F, 1.0F);
/*  39 */     this.towEntity = null;
/*  40 */     this.towedEntity = null;
/*  41 */     this.towEntityUUID = "";
/*  42 */     this.towedEntityUUID = "";
/*  43 */     this.isTowing = false;
/*  44 */     this.field_70158_ak = true;
/*  45 */     setChainLength(4);
/*  46 */     this.isServerTowEntitySearchCount = 50;
/*     */   }
/*     */   
/*     */   public MCH_EntityChain(World par1World, double par2, double par4, double par6) {
/*  50 */     this(par1World);
/*  51 */     func_70107_b(par2, par4, par6);
/*  52 */     this.field_70159_w = 0.0D;
/*  53 */     this.field_70181_x = 0.0D;
/*  54 */     this.field_70179_y = 0.0D;
/*  55 */     this.field_70169_q = par2;
/*  56 */     this.field_70167_r = par4;
/*  57 */     this.field_70166_s = par6;
/*     */   }
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  66 */     this.field_70180_af.func_187214_a(TOWED_ID, Integer.valueOf(0));
/*  67 */     this.field_70180_af.func_187214_a(TOW_ID, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/*  71 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/*  75 */     return null;
/*     */   }
/*     */   
/*     */   public boolean func_70104_M() {
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource d, float par2) {
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   public int getChainLength() {
/*  88 */     return this.chainLength;
/*     */   }
/*     */   
/*     */   public void setChainLength(int n) {
/*  92 */     if (n > 15) {
/*  93 */       n = 15;
/*     */     }
/*     */     
/*  96 */     if (n < 3) {
/*  97 */       n = 3;
/*     */     }
/*     */     
/* 100 */     this.chainLength = n;
/*     */   }
/*     */   
/*     */   public void func_70106_y() {
/* 104 */     super.func_70106_y();
/* 105 */     playDisconnectTowingEntity();
/* 106 */     this.isTowing = false;
/* 107 */     this.towEntity = null;
/* 108 */     this.towedEntity = null;
/*     */   }
/*     */   
/*     */   public boolean isTowingEntity() {
/* 112 */     return (this.isTowing && !this.field_70128_L && this.towedEntity != null && this.towEntity != null);
/*     */   }
/*     */   
/*     */   public void setTowEntity(Entity towedEntity, Entity towEntity) {
/* 116 */     if (towedEntity != null && towEntity != null && !towedEntity.field_70128_L && !towEntity.field_70128_L && !W_Entity.isEqual(towedEntity, towEntity)) {
/* 117 */       this.isTowing = true;
/* 118 */       this.towedEntity = towedEntity;
/* 119 */       this.towEntity = towEntity;
/* 120 */       if (!this.field_70170_p.field_72995_K) {
/* 121 */         this.field_70180_af.func_187227_b(TOWED_ID, Integer.valueOf(W_Entity.getEntityId(towedEntity)));
/* 122 */         this.field_70180_af.func_187227_b(TOW_ID, Integer.valueOf(W_Entity.getEntityId(towedEntity)));
/* 123 */         this.isServerTowEntitySearchCount = 0;
/*     */       } 
/*     */       
/* 126 */       if (towEntity instanceof MCH_EntityAircraft) {
/* 127 */         ((MCH_EntityAircraft)towEntity).setTowChainEntity(this);
/*     */       }
/*     */       
/* 130 */       if (towedEntity instanceof MCH_EntityAircraft) {
/* 131 */         ((MCH_EntityAircraft)towedEntity).setTowedChainEntity(this);
/*     */       }
/*     */     } else {
/* 134 */       this.isTowing = false;
/* 135 */       this.towedEntity = null;
/* 136 */       this.towEntity = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void searchTowingEntity() {
/* 141 */     if ((this.towedEntity == null || this.towEntity == null) && !this.towedEntityUUID.isEmpty() && !this.towEntityUUID.isEmpty()) {
/* 142 */       List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72314_b(32.0D, 32.0D, 32.0D));
/* 143 */       for (Entity entity : list) {
/* 144 */         String uuid = entity.getPersistentID().toString();
/* 145 */         if (this.towedEntity == null && uuid.compareTo(this.towedEntityUUID) == 0) {
/* 146 */           this.towedEntity = entity; continue;
/* 147 */         }  if (this.towEntity == null && uuid.compareTo(this.towEntityUUID) == 0) {
/* 148 */           this.towEntity = entity; continue;
/* 149 */         }  if (this.towEntity != null && this.towedEntity != null) {
/* 150 */           setTowEntity(this.towedEntity, this.towEntity);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/* 158 */     super.func_70071_h_();
/* 159 */     if (this.towedEntity == null || this.towedEntity.field_70128_L || this.towEntity == null || this.towEntity.field_70128_L) {
/* 160 */       this.towedEntity = null;
/* 161 */       this.towEntity = null;
/* 162 */       this.isTowing = false;
/*     */     } 
/*     */     
/* 165 */     if (this.towedEntity != null) {
/* 166 */       this.towedEntity.field_70143_R = 0.0F;
/*     */     }
/*     */     
/* 169 */     this.field_70169_q = this.field_70165_t;
/* 170 */     this.field_70167_r = this.field_70163_u;
/* 171 */     this.field_70166_s = this.field_70161_v;
/* 172 */     if (this.field_70170_p.field_72995_K) {
/* 173 */       onUpdate_Client();
/*     */     } else {
/* 175 */       onUpdate_Server();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdate_Client() {
/* 180 */     if (!isTowingEntity()) {
/* 181 */       setTowEntity(this.field_70170_p.func_73045_a(((Integer)this.field_70180_af.func_187225_a(TOWED_ID)).intValue()), this.field_70170_p.func_73045_a(((Integer)this.field_70180_af.func_187225_a(TOW_ID)).intValue()));
/*     */     }
/*     */     
/* 184 */     double d4 = this.field_70165_t + this.field_70159_w;
/* 185 */     double d5 = this.field_70163_u + this.field_70181_x;
/* 186 */     double d11 = this.field_70161_v + this.field_70179_y;
/* 187 */     func_70107_b(d4, d5, d11);
/* 188 */     if (this.field_70122_E) {
/* 189 */       this.field_70159_w *= 0.5D;
/* 190 */       this.field_70181_x *= 0.5D;
/* 191 */       this.field_70179_y *= 0.5D;
/*     */     } 
/*     */     
/* 194 */     this.field_70159_w *= 0.99D;
/* 195 */     this.field_70181_x *= 0.95D;
/* 196 */     this.field_70179_y *= 0.99D;
/*     */   }
/*     */   
/*     */   public void onUpdate_Server() {
/* 200 */     if (this.isServerTowEntitySearchCount > 0) {
/* 201 */       searchTowingEntity();
/* 202 */       if (this.towEntity != null && this.towedEntity != null) {
/* 203 */         this.isServerTowEntitySearchCount = 0;
/*     */       } else {
/* 205 */         this.isServerTowEntitySearchCount--;
/*     */       } 
/* 207 */     } else if (this.towEntity == null || this.towedEntity == null) {
/* 208 */       func_70106_y();
/*     */     } 
/*     */     
/* 211 */     this.field_70181_x -= 0.01D;
/* 212 */     if (!this.isTowing) {
/* 213 */       this.field_70159_w *= 0.8D;
/* 214 */       this.field_70181_x *= 0.8D;
/* 215 */       this.field_70179_y *= 0.8D;
/*     */     } 
/*     */     
/* 218 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 219 */     if (isTowingEntity()) {
/* 220 */       func_70107_b(this.towEntity.field_70165_t, this.towEntity.field_70163_u + 2.0D, this.towEntity.field_70161_v);
/* 221 */       updateTowingEntityPosRot();
/*     */     } 
/*     */     
/* 224 */     this.field_70159_w *= 0.99D;
/* 225 */     this.field_70181_x *= 0.95D;
/* 226 */     this.field_70179_y *= 0.99D;
/*     */   }
/*     */   
/*     */   public void updateTowingEntityPosRot() {
/* 230 */     double dx = this.towedEntity.field_70165_t - this.towEntity.field_70165_t;
/* 231 */     double dy = this.towedEntity.field_70163_u - this.towEntity.field_70163_u;
/* 232 */     double dz = this.towedEntity.field_70161_v - this.towEntity.field_70161_v;
/* 233 */     double dist = MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz);
/* 234 */     float DIST = getChainLength();
/* 235 */     float MAX_DIST = (getChainLength() + 2);
/* 236 */     if (dist > DIST) {
/* 237 */       this.towedEntity.field_70177_z = (float)(Math.atan2(dz, dx) * 180.0D / Math.PI) + 90.0F;
/* 238 */       this.towedEntity.field_70126_B = this.towedEntity.field_70177_z;
/* 239 */       double tmp = dist - DIST;
/* 240 */       float accl = 0.001F;
/* 241 */       this.towedEntity.field_70159_w -= dx * accl / tmp;
/* 242 */       this.towedEntity.field_70181_x -= dy * accl / tmp;
/* 243 */       this.towedEntity.field_70179_y -= dz * accl / tmp;
/* 244 */       if (dist > MAX_DIST) {
/* 245 */         this.towedEntity
/* 246 */           .func_70107_b(this.towEntity.field_70165_t + dx * MAX_DIST / dist, this.towEntity.field_70163_u + dy * MAX_DIST / dist, this.towEntity.field_70161_v + dz * MAX_DIST / dist);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void playDisconnectTowingEntity() {
/* 252 */     W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "chain_ct", 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void func_70014_b(@NotNull NBTTagCompound nbt) {
/* 256 */     if (this.isTowing && this.towEntity != null && !this.towEntity.field_70128_L && this.towedEntity != null && !this.towedEntity.field_70128_L) {
/* 257 */       nbt.func_74778_a("TowEntityUUID", this.towEntity.getPersistentID().toString());
/* 258 */       nbt.func_74778_a("TowedEntityUUID", this.towedEntity.getPersistentID().toString());
/* 259 */       nbt.func_74768_a("ChainLength", getChainLength());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbt) {
/* 264 */     this.towEntityUUID = nbt.func_74779_i("TowEntityUUID");
/* 265 */     this.towedEntityUUID = nbt.func_74779_i("TowedEntityUUID");
/* 266 */     setChainLength(nbt.func_74762_e("ChainLength"));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 271 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean func_184230_a(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 275 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\chain\MCH_EntityChain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */