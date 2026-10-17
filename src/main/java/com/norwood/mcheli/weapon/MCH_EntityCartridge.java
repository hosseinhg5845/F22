/*     */ package com.norwood.mcheli.weapon;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.helper.client._IModelCustom;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_MovingObjectPosition;
/*     */ import com.norwood.mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_EntityCartridge
/*     */   extends W_Entity
/*     */ {
/*     */   public final String texture_name;
/*     */   public final _IModelCustom model;
/*     */   private final float bound;
/*     */   private final float gravity;
/*     */   
/*     */   public MCH_EntityCartridge(World par1World, MCH_Cartridge c, double x, double y, double z, double mx, double my, double mz) {
/*  28 */     super(par1World);
/*  29 */     func_70080_a(x, y, z, 0.0F, 0.0F);
/*  30 */     this.field_70159_w = mx;
/*  31 */     this.field_70181_x = my;
/*  32 */     this.field_70179_y = mz;
/*  33 */     this.texture_name = c.name;
/*  34 */     this.model = c.model;
/*  35 */     this.bound = c.bound;
/*  36 */     this.gravity = c.gravity;
/*  37 */     this.scale = c.scale;
/*  38 */     this.countOnUpdate = 0;
/*     */   }
/*     */   private final float scale; public float targetYaw; public float targetPitch;
/*     */   private int countOnUpdate;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void spawnCartridge(World world, MCH_Cartridge cartridge, double x, double y, double z, double mx, double my, double mz, float yaw, float pitch) {
/*  45 */     if (cartridge != null) {
/*     */       
/*  47 */       MCH_EntityCartridge entityFX = new MCH_EntityCartridge(world, cartridge, x, y, z, mx + (world.field_73012_v.nextFloat() - 0.5D) * 0.07D, my, mz + (world.field_73012_v.nextFloat() - 0.5D) * 0.07D);
/*     */       
/*  49 */       entityFX.field_70126_B = yaw;
/*  50 */       entityFX.field_70177_z = yaw;
/*  51 */       entityFX.targetYaw = yaw;
/*  52 */       entityFX.field_70127_C = pitch;
/*  53 */       entityFX.field_70125_A = pitch;
/*  54 */       entityFX.targetPitch = pitch;
/*  55 */       float cy = yaw + cartridge.yaw;
/*  56 */       float cp = pitch + cartridge.pitch;
/*  57 */       double tX = (-MathHelper.func_76126_a(cy / 180.0F * 3.1415927F) * MathHelper.func_76134_b(cp / 180.0F * 3.1415927F));
/*  58 */       double tZ = (MathHelper.func_76134_b(cy / 180.0F * 3.1415927F) * MathHelper.func_76134_b(cp / 180.0F * 3.1415927F));
/*  59 */       double tY = -MathHelper.func_76126_a(cp / 180.0F * 3.1415927F);
/*  60 */       double d = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/*  61 */       if (Math.abs(d) > 0.001D) {
/*  62 */         entityFX.field_70159_w += tX * cartridge.acceleration / d;
/*  63 */         entityFX.field_70181_x += tY * cartridge.acceleration / d;
/*  64 */         entityFX.field_70179_y += tZ * cartridge.acceleration / d;
/*     */       } 
/*     */       
/*  67 */       world.func_72838_d((Entity)entityFX);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getScale() {
/*  72 */     return this.scale;
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/*  76 */     this.field_70169_q = this.field_70165_t;
/*  77 */     this.field_70167_r = this.field_70163_u;
/*  78 */     this.field_70166_s = this.field_70161_v;
/*  79 */     this.field_70126_B = this.field_70177_z;
/*  80 */     this.field_70127_C = this.field_70125_A;
/*  81 */     if (this.countOnUpdate < MCH_Config.AliveTimeOfCartridge.prmInt) {
/*  82 */       this.countOnUpdate++;
/*     */     } else {
/*  84 */       func_70106_y();
/*     */     } 
/*     */     
/*  87 */     this.field_70159_w *= 0.98D;
/*  88 */     this.field_70179_y *= 0.98D;
/*  89 */     this.field_70181_x += this.gravity;
/*  90 */     move();
/*     */   }
/*     */   
/*     */   public void rotation() {
/*  94 */     if (this.field_70177_z < this.targetYaw - 3.0F) {
/*  95 */       this.field_70177_z += 10.0F;
/*  96 */       if (this.field_70177_z > this.targetYaw) {
/*  97 */         this.field_70177_z = this.targetYaw;
/*     */       }
/*  99 */     } else if (this.field_70177_z > this.targetYaw + 3.0F) {
/* 100 */       this.field_70177_z -= 10.0F;
/* 101 */       if (this.field_70177_z < this.targetYaw) {
/* 102 */         this.field_70177_z = this.targetYaw;
/*     */       }
/*     */     } 
/*     */     
/* 106 */     if (this.field_70125_A < this.targetPitch) {
/* 107 */       this.field_70125_A += 10.0F;
/* 108 */       if (this.field_70125_A > this.targetPitch) {
/* 109 */         this.field_70125_A = this.targetPitch;
/*     */       }
/* 111 */     } else if (this.field_70125_A > this.targetPitch) {
/* 112 */       this.field_70125_A -= 10.0F;
/* 113 */       if (this.field_70125_A < this.targetPitch) {
/* 114 */         this.field_70125_A = this.targetPitch;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void move() {
/* 120 */     Vec3d vec1 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 121 */     Vec3d vec2 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 122 */     RayTraceResult m = W_WorldFunc.clip(this.field_70170_p, vec1, vec2);
/* 123 */     double d = Math.max(Math.abs(this.field_70159_w), Math.abs(this.field_70181_x));
/* 124 */     d = Math.max(d, Math.abs(this.field_70179_y));
/* 125 */     if (W_MovingObjectPosition.isHitTypeTile(m)) {
/* 126 */       func_70107_b(m.field_72307_f.field_72450_a, m.field_72307_f.field_72448_b, m.field_72307_f.field_72449_c);
/* 127 */       this.field_70159_w += d * (this.field_70146_Z.nextFloat() - 0.5F) * 0.10000000149011612D;
/* 128 */       this.field_70181_x += d * (this.field_70146_Z.nextFloat() - 0.5F) * 0.10000000149011612D;
/* 129 */       this.field_70179_y += d * (this.field_70146_Z.nextFloat() - 0.5F) * 0.10000000149011612D;
/* 130 */       if (d > 0.10000000149011612D) {
/* 131 */         this.targetYaw += (float)(d * (this.field_70146_Z.nextFloat() - 0.5F) * 720.0D);
/* 132 */         this.targetPitch = (float)(d * (this.field_70146_Z.nextFloat() - 0.5F) * 720.0D);
/*     */       } else {
/* 134 */         this.targetPitch = 0.0F;
/*     */       } 
/*     */       
/* 137 */       switch (m.field_178784_b) {
/*     */         case DOWN:
/* 139 */           if (this.field_70181_x > 0.0D) {
/* 140 */             this.field_70181_x = -this.field_70181_x * this.bound;
/*     */           }
/*     */           break;
/*     */         case UP:
/* 144 */           if (this.field_70181_x < 0.0D) {
/* 145 */             this.field_70181_x = -this.field_70181_x * this.bound;
/*     */           }
/*     */           
/* 148 */           this.targetPitch *= 0.3F;
/*     */           break;
/*     */         case NORTH:
/* 151 */           if (this.field_70179_y > 0.0D) {
/* 152 */             this.field_70179_y = -this.field_70179_y * this.bound; break;
/*     */           } 
/* 154 */           this.field_70161_v += this.field_70179_y;
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 158 */           if (this.field_70179_y < 0.0D) {
/* 159 */             this.field_70179_y = -this.field_70179_y * this.bound; break;
/*     */           } 
/* 161 */           this.field_70161_v += this.field_70179_y;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 165 */           if (this.field_70159_w > 0.0D) {
/* 166 */             this.field_70159_w = -this.field_70159_w * this.bound; break;
/*     */           } 
/* 168 */           this.field_70165_t += this.field_70159_w;
/*     */           break;
/*     */         
/*     */         case EAST:
/* 172 */           if (this.field_70159_w < 0.0D) {
/* 173 */             this.field_70159_w = -this.field_70159_w * this.bound; break;
/*     */           } 
/* 175 */           this.field_70165_t += this.field_70159_w;
/*     */           break;
/*     */       } 
/*     */     } else {
/* 179 */       this.field_70165_t += this.field_70159_w;
/* 180 */       this.field_70163_u += this.field_70181_x;
/* 181 */       this.field_70161_v += this.field_70179_y;
/* 182 */       if (d > 0.05000000074505806D)
/* 183 */         rotation(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_70037_a(@NotNull NBTTagCompound var1) {}
/*     */   
/*     */   protected void func_70014_b(@NotNull NBTTagCompound var1) {}
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\weapon\MCH_EntityCartridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */