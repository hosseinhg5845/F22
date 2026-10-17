/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.helper.entity.IEntitySinglePassenger;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntitySeat
/*     */   extends W_Entity
/*     */   implements IEntitySinglePassenger
/*     */ {
/*     */   public static final float BB_SIZE = 1.0F;
/*     */   public String parentUniqueID;
/*     */   public int seatID;
/*     */   public int parentSearchCount;
/*     */   protected Entity lastRiddenByEntity;
/*     */   private MCH_EntityAircraft parent;
/*     */   
/*     */   public MCH_EntitySeat(World world) {
/*  32 */     super(world);
/*  33 */     func_70105_a(1.0F, 1.0F);
/*  34 */     this.field_70159_w = 0.0D;
/*  35 */     this.field_70181_x = 0.0D;
/*  36 */     this.field_70179_y = 0.0D;
/*  37 */     this.seatID = -1;
/*  38 */     setParent((MCH_EntityAircraft)null);
/*  39 */     this.parentSearchCount = 0;
/*  40 */     this.lastRiddenByEntity = null;
/*  41 */     this.field_70158_ak = true;
/*  42 */     this.field_70178_ae = true;
/*     */   }
/*     */   
/*     */   public MCH_EntitySeat(World world, double x, double y, double z) {
/*  46 */     this(world);
/*  47 */     func_70107_b(x, y + 1.0D, z);
/*  48 */     this.field_70169_q = x;
/*  49 */     this.field_70167_r = y + 1.0D;
/*  50 */     this.field_70166_s = z;
/*     */   }
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  54 */     return false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/*  58 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/*  62 */     return func_174813_aQ();
/*     */   }
/*     */   
/*     */   public double func_70042_X() {
/*  66 */     return -0.3D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/*  71 */     return (getParent() != null && getParent().func_70097_a(par1DamageSource, par2));
/*     */   }
/*     */   
/*     */   public boolean func_70067_L() {
/*  75 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_180426_a(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {}
/*     */   
/*     */   public void func_70106_y() {
/*  83 */     super.func_70106_y();
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/*  87 */     super.func_70071_h_();
/*  88 */     this.field_70143_R = 0.0F;
/*  89 */     Entity riddenByEntity = getRiddenByEntity();
/*  90 */     if (riddenByEntity != null) {
/*  91 */       riddenByEntity.field_70143_R = 0.0F;
/*     */     }
/*     */     
/*  94 */     if (this.lastRiddenByEntity == null && riddenByEntity != null) {
/*  95 */       if (getParent() != null) {
/*  96 */         MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntitySeat.onUpdate:SeatID=%d", new Object[] { Integer.valueOf(this.seatID), riddenByEntity.toString() });
/*  97 */         getParent().onMountPlayerSeat(this, riddenByEntity);
/*     */       } 
/*  99 */     } else if (this.lastRiddenByEntity != null && riddenByEntity == null && getParent() != null) {
/* 100 */       MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntitySeat.onUpdate:SeatID=%d", new Object[] { Integer.valueOf(this.seatID), this.lastRiddenByEntity.toString() });
/* 101 */       getParent().onUnmountPlayerSeat(this, this.lastRiddenByEntity);
/*     */     } 
/*     */     
/* 104 */     if (this.field_70170_p.field_72995_K) {
/* 105 */       onUpdate_Client();
/*     */     } else {
/* 107 */       onUpdate_Server();
/*     */     } 
/*     */     
/* 110 */     this.lastRiddenByEntity = getRiddenByEntity();
/*     */   }
/*     */   
/*     */   private void onUpdate_Client() {
/* 114 */     checkDetachmentAndDelete();
/*     */   }
/*     */   
/*     */   private void onUpdate_Server() {
/* 118 */     checkDetachmentAndDelete();
/* 119 */     Entity riddenByEntity = getRiddenByEntity();
/* 120 */     if (riddenByEntity == null || riddenByEntity.field_70128_L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_184232_k(@NotNull Entity passenger) {
/* 125 */     updatePosition(passenger);
/*     */   }
/*     */   
/*     */   public void updatePosition(@Nullable Entity ridEnt) {
/* 129 */     if (ridEnt != null) {
/* 130 */       ridEnt.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 131 */       ridEnt.field_70159_w = ridEnt.field_70181_x = ridEnt.field_70179_y = 0.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateRotation(@Nullable Entity ridEnt, float yaw, float pitch) {
/* 136 */     if (ridEnt != null) {
/* 137 */       ridEnt.field_70177_z = yaw;
/* 138 */       ridEnt.field_70125_A = pitch;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void checkDetachmentAndDelete() {
/* 143 */     if (!this.field_70128_L && (this.seatID < 0 || getParent() == null || (getParent()).field_70128_L)) {
/* 144 */       if (getParent() != null && (getParent()).field_70128_L) {
/* 145 */         this.parentSearchCount = 100000000;
/*     */       }
/*     */       
/* 148 */       if (this.parentSearchCount >= 1200) {
/* 149 */         func_70106_y();
/* 150 */         if (!this.field_70170_p.field_72995_K) {
/* 151 */           Entity riddenByEntity = getRiddenByEntity();
/* 152 */           if (riddenByEntity != null) {
/* 153 */             riddenByEntity.func_184210_p();
/*     */           }
/*     */         } 
/*     */         
/* 157 */         setParent((MCH_EntityAircraft)null);
/* 158 */         MCH_Lib.DbgLog(this.field_70170_p, "[Error]座席エンティティは本体が見つからないため削除 seat=%d, parentUniqueID=%s", new Object[] { Integer.valueOf(this.seatID), this.parentUniqueID });
/*     */       } else {
/* 160 */         this.parentSearchCount++;
/*     */       } 
/*     */     } else {
/* 163 */       this.parentSearchCount = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 168 */     par1NBTTagCompound.func_74768_a("SeatID", this.seatID);
/* 169 */     par1NBTTagCompound.func_74778_a("ParentUniqueID", this.parentUniqueID);
/*     */   }
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 173 */     this.seatID = par1NBTTagCompound.func_74762_e("SeatID");
/* 174 */     this.parentUniqueID = par1NBTTagCompound.func_74779_i("ParentUniqueID");
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 179 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean canRideMob(Entity entity) {
/* 183 */     return (getParent() != null && this.seatID >= 0 && !(getParent().getSeatInfo(this.seatID + 1) instanceof MCH_SeatRackInfo));
/*     */   }
/*     */   
/*     */   public boolean isGunnerMode() {
/* 187 */     Entity riddenByEntity = getRiddenByEntity();
/* 188 */     return (riddenByEntity != null && getParent() != null && getParent().getIsGunnerMode(riddenByEntity));
/*     */   }
/*     */   
/*     */   public boolean func_184230_a(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
/* 192 */     if (getParent() != null && !getParent().isDestroyed()) {
/* 193 */       if (!getParent().checkTeam(player)) {
/* 194 */         return false;
/*     */       }
/* 196 */       ItemStack itemStack = player.func_184586_b(hand);
/* 197 */       if (!itemStack.func_190926_b() && itemStack.func_77973_b() instanceof com.norwood.mcheli.tool.MCH_ItemWrench)
/* 198 */         return getParent().func_184230_a(player, hand); 
/* 199 */       if (!itemStack.func_190926_b() && itemStack.func_77973_b() instanceof com.norwood.mcheli.mob.MCH_ItemSpawnGunner) {
/* 200 */         return getParent().func_184230_a(player, hand);
/*     */       }
/* 202 */       Entity riddenByEntity = getRiddenByEntity();
/* 203 */       if (riddenByEntity != null)
/* 204 */         return false; 
/* 205 */       if (player.func_184187_bx() != null)
/* 206 */         return false; 
/* 207 */       if (!canRideMob((Entity)player)) {
/* 208 */         return false;
/*     */       }
/* 210 */       player.func_184220_m((Entity)this);
/* 211 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 216 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MCH_EntityAircraft getParent() {
/* 222 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void setParent(MCH_EntityAircraft parent) {
/* 226 */     this.parent = parent;
/* 227 */     Entity riddenByEntity = getRiddenByEntity();
/* 228 */     if (riddenByEntity != null) {
/* 229 */       MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntitySeat.setParent:SeatID=%d %s : " + getParent(), new Object[] { Integer.valueOf(this.seatID), riddenByEntity.toString() });
/* 230 */       if (getParent() != null) {
/* 231 */         getParent().onMountPlayerSeat(this, riddenByEntity);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 239 */     List<Entity> passengers = func_184188_bt();
/* 240 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_EntitySeat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */