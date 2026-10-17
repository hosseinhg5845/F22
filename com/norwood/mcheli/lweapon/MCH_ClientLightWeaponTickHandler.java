/*     */ package com.norwood.mcheli.lweapon;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_ClientTickHandlerBase;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Key;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponBase;
/*     */ import com.norwood.mcheli.weapon.MCH_WeaponGuidanceSystem;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_McClient;
/*     */ import com.norwood.mcheli.wrapper.W_Network;
/*     */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ public class MCH_ClientLightWeaponTickHandler extends MCH_ClientTickHandlerBase {
/*     */   public static int reloadCount;
/*     */   public static int lockonSoundCount;
/*     */   public static int weaponMode;
/*     */   public static int selectedZoom;
/*  34 */   public static Entity markEntity = null;
/*  35 */   public static Vec3d markPos = Vec3d.field_186680_a;
/*  36 */   public static MCH_WeaponGuidanceSystem gs = new MCH_WeaponGuidanceSystem();
/*  37 */   public static double lockRange = 120.0D;
/*     */   protected static MCH_WeaponBase weapon;
/*  39 */   private static final FloatBuffer screenPos = BufferUtils.createFloatBuffer(3);
/*  40 */   private static final FloatBuffer screenPosBB = BufferUtils.createFloatBuffer(3);
/*  41 */   private static final FloatBuffer matModel = BufferUtils.createFloatBuffer(16);
/*  42 */   private static final FloatBuffer matProjection = BufferUtils.createFloatBuffer(16);
/*  43 */   private static final IntBuffer matViewport = BufferUtils.createIntBuffer(16);
/*     */   public MCH_Key KeyAttack;
/*     */   public MCH_Key KeyUseWeapon;
/*     */   public MCH_Key KeySwWeaponMode;
/*     */   public MCH_Key KeyZoom;
/*     */   public MCH_Key KeyCameraMode;
/*     */   public MCH_Key[] Keys;
/*     */   protected boolean isHeldItem = false;
/*     */   protected boolean isBeforeHeldItem = false;
/*  52 */   protected EntityPlayer prevThePlayer = null;
/*  53 */   protected ItemStack prevItemStack = ItemStack.field_190927_a;
/*     */   
/*     */   public MCH_ClientLightWeaponTickHandler(Minecraft minecraft, MCH_Config config) {
/*  56 */     super(minecraft);
/*  57 */     updateKeybind(config);
/*  58 */     gs.canLockInAir = false;
/*  59 */     gs.canLockOnGround = false;
/*  60 */     gs.canLockInWater = false;
/*  61 */     gs.setLockCountMax(40);
/*  62 */     gs.lockRange = 120.0D;
/*  63 */     lockonSoundCount = 0;
/*  64 */     initWeaponParam((EntityPlayer)null);
/*     */   }
/*     */   
/*     */   public static void markEntity(Entity entity, double x, double y, double z) {
/*  68 */     if (gs.getLockingEntity() == entity) {
/*  69 */       GL11.glGetFloat(2982, matModel);
/*  70 */       GL11.glGetFloat(2983, matProjection);
/*  71 */       GL11.glGetInteger(2978, matViewport);
/*  72 */       GLU.gluProject((float)x, (float)y, (float)z, matModel, matProjection, matViewport, screenPos);
/*  73 */       MCH_AircraftInfo i = (entity instanceof MCH_EntityAircraft) ? ((MCH_EntityAircraft)entity).getAcInfo() : null;
/*  74 */       float w = (entity.field_70130_N > entity.field_70131_O) ? entity.field_70130_N : ((i != null) ? i.markerWidth : entity.field_70131_O);
/*  75 */       float h = (i != null) ? i.markerHeight : entity.field_70131_O;
/*  76 */       GLU.gluProject((float)x + w, (float)y + h, (float)z + w, matModel, matProjection, matViewport, screenPosBB);
/*  77 */       markEntity = entity;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Vec3d getMartEntityPos() {
/*  83 */     return (gs.getLockingEntity() == markEntity && markEntity != null) ? new Vec3d(screenPos.get(0), screenPos.get(1), screenPos.get(2)) : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Vec3d getMartEntityBBPos() {
/*  88 */     return (gs.getLockingEntity() == markEntity && markEntity != null) ? new Vec3d(screenPosBB.get(0), screenPosBB.get(1), screenPosBB.get(2)) : null;
/*     */   }
/*     */   
/*     */   public static int getPotionNightVisionDuration(EntityPlayer player) {
/*  92 */     PotionEffect cpe = player.func_70660_b(MobEffects.field_76439_r);
/*  93 */     return (cpe != null) ? cpe.func_76459_b() : 0;
/*     */   }
/*     */   
/*     */   public void initWeaponParam(EntityPlayer player) {
/*  97 */     reloadCount = 0;
/*  98 */     weaponMode = 0;
/*  99 */     selectedZoom = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/* 104 */     this.KeyAttack = new MCH_Key(MCH_Config.KeyAttack.prmInt);
/* 105 */     this.KeyUseWeapon = new MCH_Key(MCH_Config.KeyUseWeapon.prmInt);
/* 106 */     this.KeySwWeaponMode = new MCH_Key(MCH_Config.KeySwWeaponMode.prmInt);
/* 107 */     this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/* 108 */     this.KeyCameraMode = new MCH_Key(MCH_Config.KeyCameraMode.prmInt);
/* 109 */     this.Keys = new MCH_Key[] { this.KeyAttack, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeyZoom, this.KeyCameraMode };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(boolean inGUI) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield Keys : [Lcom/norwood/mcheli/MCH_Key;
/*     */     //   4: astore_2
/*     */     //   5: aload_2
/*     */     //   6: arraylength
/*     */     //   7: istore_3
/*     */     //   8: iconst_0
/*     */     //   9: istore #4
/*     */     //   11: iload #4
/*     */     //   13: iload_3
/*     */     //   14: if_icmpge -> 34
/*     */     //   17: aload_2
/*     */     //   18: iload #4
/*     */     //   20: aaload
/*     */     //   21: astore #5
/*     */     //   23: aload #5
/*     */     //   25: invokevirtual update : ()V
/*     */     //   28: iinc #4, 1
/*     */     //   31: goto -> 11
/*     */     //   34: aload_0
/*     */     //   35: aload_0
/*     */     //   36: getfield isHeldItem : Z
/*     */     //   39: putfield isBeforeHeldItem : Z
/*     */     //   42: aload_0
/*     */     //   43: getfield mc : Lnet/minecraft/client/Minecraft;
/*     */     //   46: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   49: astore_2
/*     */     //   50: aload_0
/*     */     //   51: getfield prevThePlayer : Lnet/minecraft/entity/player/EntityPlayer;
/*     */     //   54: ifnull -> 65
/*     */     //   57: aload_0
/*     */     //   58: getfield prevThePlayer : Lnet/minecraft/entity/player/EntityPlayer;
/*     */     //   61: aload_2
/*     */     //   62: if_acmpeq -> 75
/*     */     //   65: aload_0
/*     */     //   66: aload_2
/*     */     //   67: invokevirtual initWeaponParam : (Lnet/minecraft/entity/player/EntityPlayer;)V
/*     */     //   70: aload_0
/*     */     //   71: aload_2
/*     */     //   72: putfield prevThePlayer : Lnet/minecraft/entity/player/EntityPlayer;
/*     */     //   75: aload_2
/*     */     //   76: ifnull -> 86
/*     */     //   79: aload_2
/*     */     //   80: invokevirtual func_184614_ca : ()Lnet/minecraft/item/ItemStack;
/*     */     //   83: goto -> 89
/*     */     //   86: getstatic net/minecraft/item/ItemStack.field_190927_a : Lnet/minecraft/item/ItemStack;
/*     */     //   89: astore_3
/*     */     //   90: aload_2
/*     */     //   91: ifnull -> 114
/*     */     //   94: aload_2
/*     */     //   95: invokevirtual func_184187_bx : ()Lnet/minecraft/entity/Entity;
/*     */     //   98: instanceof com/norwood/mcheli/gltd/MCH_EntityGLTD
/*     */     //   101: ifne -> 114
/*     */     //   104: aload_2
/*     */     //   105: invokevirtual func_184187_bx : ()Lnet/minecraft/entity/Entity;
/*     */     //   108: instanceof com/norwood/mcheli/aircraft/MCH_EntityAircraft
/*     */     //   111: ifeq -> 118
/*     */     //   114: getstatic net/minecraft/item/ItemStack.field_190927_a : Lnet/minecraft/item/ItemStack;
/*     */     //   117: astore_3
/*     */     //   118: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.gs : Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   121: invokevirtual getLockingEntity : ()Lnet/minecraft/entity/Entity;
/*     */     //   124: ifnonnull -> 131
/*     */     //   127: aconst_null
/*     */     //   128: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.markEntity : Lnet/minecraft/entity/Entity;
/*     */     //   131: aload_3
/*     */     //   132: invokevirtual func_190926_b : ()Z
/*     */     //   135: ifne -> 568
/*     */     //   138: aload_3
/*     */     //   139: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   142: astore #5
/*     */     //   144: aload #5
/*     */     //   146: instanceof com/norwood/mcheli/lweapon/MCH_ItemLightWeaponBase
/*     */     //   149: ifeq -> 568
/*     */     //   152: aload #5
/*     */     //   154: checkcast com/norwood/mcheli/lweapon/MCH_ItemLightWeaponBase
/*     */     //   157: astore #4
/*     */     //   159: aload_0
/*     */     //   160: getfield prevItemStack : Lnet/minecraft/item/ItemStack;
/*     */     //   163: invokevirtual func_190926_b : ()Z
/*     */     //   166: ifne -> 197
/*     */     //   169: aload_0
/*     */     //   170: getfield prevItemStack : Lnet/minecraft/item/ItemStack;
/*     */     //   173: aload_3
/*     */     //   174: invokevirtual func_77969_a : (Lnet/minecraft/item/ItemStack;)Z
/*     */     //   177: ifne -> 256
/*     */     //   180: aload_0
/*     */     //   181: getfield prevItemStack : Lnet/minecraft/item/ItemStack;
/*     */     //   184: invokevirtual func_77977_a : ()Ljava/lang/String;
/*     */     //   187: aload_3
/*     */     //   188: invokevirtual func_77977_a : ()Ljava/lang/String;
/*     */     //   191: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   194: ifne -> 256
/*     */     //   197: aload_0
/*     */     //   198: aload_2
/*     */     //   199: invokevirtual initWeaponParam : (Lnet/minecraft/entity/player/EntityPlayer;)V
/*     */     //   202: aload_2
/*     */     //   203: getfield field_70170_p : Lnet/minecraft/world/World;
/*     */     //   206: aload_3
/*     */     //   207: invokestatic getName : (Lnet/minecraft/item/ItemStack;)Ljava/lang/String;
/*     */     //   210: getstatic net/minecraft/util/math/Vec3d.field_186680_a : Lnet/minecraft/util/math/Vec3d;
/*     */     //   213: fconst_0
/*     */     //   214: fconst_0
/*     */     //   215: aconst_null
/*     */     //   216: iconst_0
/*     */     //   217: invokestatic createWeapon : (Lnet/minecraft/world/World;Ljava/lang/String;Lnet/minecraft/util/math/Vec3d;FFLcom/norwood/mcheli/weapon/MCH_IEntityLockChecker;Z)Lcom/norwood/mcheli/weapon/MCH_WeaponBase;
/*     */     //   220: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.weapon : Lcom/norwood/mcheli/weapon/MCH_WeaponBase;
/*     */     //   223: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.weapon : Lcom/norwood/mcheli/weapon/MCH_WeaponBase;
/*     */     //   226: ifnull -> 256
/*     */     //   229: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.weapon : Lcom/norwood/mcheli/weapon/MCH_WeaponBase;
/*     */     //   232: invokevirtual getInfo : ()Lcom/norwood/mcheli/weapon/MCH_WeaponInfo;
/*     */     //   235: ifnull -> 256
/*     */     //   238: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.weapon : Lcom/norwood/mcheli/weapon/MCH_WeaponBase;
/*     */     //   241: invokevirtual getGuidanceSystem : ()Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   244: ifnull -> 256
/*     */     //   247: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.weapon : Lcom/norwood/mcheli/weapon/MCH_WeaponBase;
/*     */     //   250: invokevirtual getGuidanceSystem : ()Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   253: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.gs : Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   256: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.weapon : Lcom/norwood/mcheli/weapon/MCH_WeaponBase;
/*     */     //   259: ifnull -> 268
/*     */     //   262: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.gs : Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   265: ifnonnull -> 269
/*     */     //   268: return
/*     */     //   269: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.gs : Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   272: aload_2
/*     */     //   273: getfield field_70170_p : Lnet/minecraft/world/World;
/*     */     //   276: invokevirtual setWorld : (Lnet/minecraft/world/World;)V
/*     */     //   279: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.gs : Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   282: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.lockRange : D
/*     */     //   285: putfield lockRange : D
/*     */     //   288: aload_2
/*     */     //   289: invokevirtual func_184612_cw : ()I
/*     */     //   292: bipush #10
/*     */     //   294: if_icmple -> 333
/*     */     //   297: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.selectedZoom : I
/*     */     //   300: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.weapon : Lcom/norwood/mcheli/weapon/MCH_WeaponBase;
/*     */     //   303: invokevirtual getInfo : ()Lcom/norwood/mcheli/weapon/MCH_WeaponInfo;
/*     */     //   306: getfield zoom : [F
/*     */     //   309: arraylength
/*     */     //   310: irem
/*     */     //   311: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.selectedZoom : I
/*     */     //   314: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.weapon : Lcom/norwood/mcheli/weapon/MCH_WeaponBase;
/*     */     //   317: invokevirtual getInfo : ()Lcom/norwood/mcheli/weapon/MCH_WeaponInfo;
/*     */     //   320: getfield zoom : [F
/*     */     //   323: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.selectedZoom : I
/*     */     //   326: faload
/*     */     //   327: invokestatic setCameraZoom : (F)V
/*     */     //   330: goto -> 336
/*     */     //   333: invokestatic restoreCameraZoom : ()V
/*     */     //   336: aload_3
/*     */     //   337: invokevirtual func_77960_j : ()I
/*     */     //   340: aload_3
/*     */     //   341: invokevirtual func_77958_k : ()I
/*     */     //   344: if_icmpge -> 460
/*     */     //   347: aload_2
/*     */     //   348: invokevirtual func_184612_cw : ()I
/*     */     //   351: bipush #10
/*     */     //   353: if_icmple -> 444
/*     */     //   356: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.gs : Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   359: aload_2
/*     */     //   360: invokevirtual lock : (Lnet/minecraft/entity/Entity;)Z
/*     */     //   363: pop
/*     */     //   364: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.gs : Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   367: invokevirtual getLockCount : ()I
/*     */     //   370: ifle -> 453
/*     */     //   373: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.lockonSoundCount : I
/*     */     //   376: ifle -> 390
/*     */     //   379: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.lockonSoundCount : I
/*     */     //   382: iconst_1
/*     */     //   383: isub
/*     */     //   384: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.lockonSoundCount : I
/*     */     //   387: goto -> 453
/*     */     //   390: bipush #7
/*     */     //   392: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.lockonSoundCount : I
/*     */     //   395: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.lockonSoundCount : I
/*     */     //   398: i2d
/*     */     //   399: dconst_1
/*     */     //   400: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.gs : Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   403: invokevirtual getLockCount : ()I
/*     */     //   406: i2f
/*     */     //   407: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.gs : Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   410: invokevirtual getLockCountMax : ()I
/*     */     //   413: i2f
/*     */     //   414: fdiv
/*     */     //   415: f2d
/*     */     //   416: dsub
/*     */     //   417: dmul
/*     */     //   418: d2i
/*     */     //   419: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.lockonSoundCount : I
/*     */     //   422: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.lockonSoundCount : I
/*     */     //   425: iconst_3
/*     */     //   426: if_icmpge -> 433
/*     */     //   429: iconst_2
/*     */     //   430: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.lockonSoundCount : I
/*     */     //   433: ldc_w 'lockon'
/*     */     //   436: fconst_1
/*     */     //   437: fconst_1
/*     */     //   438: invokestatic MOD_playSoundFX : (Ljava/lang/String;FF)V
/*     */     //   441: goto -> 453
/*     */     //   444: invokestatic restoreCameraZoom : ()V
/*     */     //   447: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.gs : Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   450: invokevirtual clearLock : ()V
/*     */     //   453: iconst_0
/*     */     //   454: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.reloadCount : I
/*     */     //   457: goto -> 540
/*     */     //   460: iconst_0
/*     */     //   461: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.lockonSoundCount : I
/*     */     //   464: aload_2
/*     */     //   465: aload #4
/*     */     //   467: getfield bullet : Lcom/norwood/mcheli/lweapon/MCH_ItemLightWeaponBullet;
/*     */     //   470: invokestatic hasItem : (Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/Item;)Z
/*     */     //   473: ifeq -> 530
/*     */     //   476: aload_2
/*     */     //   477: invokevirtual func_184605_cv : ()I
/*     */     //   480: ifgt -> 530
/*     */     //   483: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.reloadCount : I
/*     */     //   486: bipush #10
/*     */     //   488: if_icmpne -> 499
/*     */     //   491: ldc_w 'fim92_reload'
/*     */     //   494: fconst_1
/*     */     //   495: fconst_1
/*     */     //   496: invokestatic MOD_playSoundFX : (Ljava/lang/String;FF)V
/*     */     //   499: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.reloadCount : I
/*     */     //   502: bipush #40
/*     */     //   504: if_icmpge -> 534
/*     */     //   507: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.reloadCount : I
/*     */     //   510: iconst_1
/*     */     //   511: iadd
/*     */     //   512: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.reloadCount : I
/*     */     //   515: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.reloadCount : I
/*     */     //   518: bipush #40
/*     */     //   520: if_icmpne -> 534
/*     */     //   523: aload_0
/*     */     //   524: invokevirtual onCompleteReload : ()V
/*     */     //   527: goto -> 534
/*     */     //   530: iconst_0
/*     */     //   531: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.reloadCount : I
/*     */     //   534: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.gs : Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   537: invokevirtual clearLock : ()V
/*     */     //   540: iload_1
/*     */     //   541: ifne -> 557
/*     */     //   544: aload_0
/*     */     //   545: aload_2
/*     */     //   546: aload_3
/*     */     //   547: aload_3
/*     */     //   548: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   551: checkcast com/norwood/mcheli/lweapon/MCH_ItemLightWeaponBase
/*     */     //   554: invokevirtual playerControl : (Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;Lcom/norwood/mcheli/lweapon/MCH_ItemLightWeaponBase;)V
/*     */     //   557: aload_0
/*     */     //   558: aload_2
/*     */     //   559: invokestatic isHeld : (Lnet/minecraft/entity/player/EntityPlayer;)Z
/*     */     //   562: putfield isHeldItem : Z
/*     */     //   565: goto -> 581
/*     */     //   568: iconst_0
/*     */     //   569: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.lockonSoundCount : I
/*     */     //   572: iconst_0
/*     */     //   573: putstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.reloadCount : I
/*     */     //   576: aload_0
/*     */     //   577: iconst_0
/*     */     //   578: putfield isHeldItem : Z
/*     */     //   581: aload_0
/*     */     //   582: getfield isBeforeHeldItem : Z
/*     */     //   585: aload_0
/*     */     //   586: getfield isHeldItem : Z
/*     */     //   589: if_icmpeq -> 650
/*     */     //   592: iconst_1
/*     */     //   593: ldc_w 'LWeapon cancel'
/*     */     //   596: iconst_0
/*     */     //   597: anewarray java/lang/Object
/*     */     //   600: invokestatic DbgLog : (ZLjava/lang/String;[Ljava/lang/Object;)V
/*     */     //   603: aload_0
/*     */     //   604: getfield isHeldItem : Z
/*     */     //   607: ifne -> 650
/*     */     //   610: aload_2
/*     */     //   611: invokestatic getPotionNightVisionDuration : (Lnet/minecraft/entity/player/EntityPlayer;)I
/*     */     //   614: sipush #250
/*     */     //   617: if_icmpge -> 647
/*     */     //   620: new com/norwood/mcheli/lweapon/MCH_PacketLightWeaponPlayerControl
/*     */     //   623: dup
/*     */     //   624: invokespecial <init> : ()V
/*     */     //   627: astore #4
/*     */     //   629: aload #4
/*     */     //   631: iconst_1
/*     */     //   632: putfield camMode : I
/*     */     //   635: aload #4
/*     */     //   637: invokestatic sendToServer : (Lcom/norwood/mcheli/wrapper/W_PacketBase;)V
/*     */     //   640: aload_2
/*     */     //   641: getstatic net/minecraft/init/MobEffects.field_76439_r : Lnet/minecraft/potion/Potion;
/*     */     //   644: invokevirtual func_184589_d : (Lnet/minecraft/potion/Potion;)V
/*     */     //   647: invokestatic restoreCameraZoom : ()V
/*     */     //   650: aload_0
/*     */     //   651: aload_3
/*     */     //   652: putfield prevItemStack : Lnet/minecraft/item/ItemStack;
/*     */     //   655: getstatic com/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler.gs : Lcom/norwood/mcheli/weapon/MCH_WeaponGuidanceSystem;
/*     */     //   658: invokevirtual update : ()V
/*     */     //   661: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #114	-> 0
/*     */     //   #115	-> 23
/*     */     //   #114	-> 28
/*     */     //   #118	-> 34
/*     */     //   #119	-> 42
/*     */     //   #120	-> 50
/*     */     //   #121	-> 65
/*     */     //   #122	-> 70
/*     */     //   #125	-> 75
/*     */     //   #126	-> 90
/*     */     //   #127	-> 114
/*     */     //   #130	-> 118
/*     */     //   #131	-> 127
/*     */     //   #134	-> 131
/*     */     //   #135	-> 159
/*     */     //   #136	-> 197
/*     */     //   #137	-> 202
/*     */     //   #138	-> 223
/*     */     //   #139	-> 247
/*     */     //   #143	-> 256
/*     */     //   #144	-> 268
/*     */     //   #147	-> 269
/*     */     //   #148	-> 279
/*     */     //   #149	-> 288
/*     */     //   #150	-> 297
/*     */     //   #151	-> 314
/*     */     //   #153	-> 333
/*     */     //   #156	-> 336
/*     */     //   #157	-> 347
/*     */     //   #158	-> 356
/*     */     //   #159	-> 364
/*     */     //   #160	-> 373
/*     */     //   #161	-> 379
/*     */     //   #163	-> 390
/*     */     //   #164	-> 395
/*     */     //   #165	-> 422
/*     */     //   #166	-> 429
/*     */     //   #169	-> 433
/*     */     //   #173	-> 444
/*     */     //   #174	-> 447
/*     */     //   #177	-> 453
/*     */     //   #179	-> 460
/*     */     //   #180	-> 464
/*     */     //   #181	-> 483
/*     */     //   #182	-> 491
/*     */     //   #185	-> 499
/*     */     //   #186	-> 507
/*     */     //   #187	-> 515
/*     */     //   #188	-> 523
/*     */     //   #192	-> 530
/*     */     //   #195	-> 534
/*     */     //   #198	-> 540
/*     */     //   #199	-> 544
/*     */     //   #202	-> 557
/*     */     //   #204	-> 568
/*     */     //   #205	-> 572
/*     */     //   #206	-> 576
/*     */     //   #209	-> 581
/*     */     //   #210	-> 592
/*     */     //   #211	-> 603
/*     */     //   #212	-> 610
/*     */     //   #213	-> 620
/*     */     //   #214	-> 629
/*     */     //   #215	-> 635
/*     */     //   #216	-> 640
/*     */     //   #219	-> 647
/*     */     //   #223	-> 650
/*     */     //   #224	-> 655
/*     */     //   #225	-> 661
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   23	5	5	k	Lcom/norwood/mcheli/MCH_Key;
/*     */     //   159	409	4	lweapon	Lcom/norwood/mcheli/lweapon/MCH_ItemLightWeaponBase;
/*     */     //   629	18	4	pc	Lcom/norwood/mcheli/lweapon/MCH_PacketLightWeaponPlayerControl;
/*     */     //   0	662	0	this	Lcom/norwood/mcheli/lweapon/MCH_ClientLightWeaponTickHandler;
/*     */     //   0	662	1	inGUI	Z
/*     */     //   50	612	2	player	Lnet/minecraft/entity/player/EntityPlayer;
/*     */     //   90	572	3	is	Lnet/minecraft/item/ItemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCompleteReload() {
/* 228 */     MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
/* 229 */     pc.cmpReload = 1;
/* 230 */     W_Network.sendToServer((W_PacketBase)pc);
/*     */   }
/*     */   
/*     */   protected void playerControl(EntityPlayer player, ItemStack is, MCH_ItemLightWeaponBase item) {
/* 234 */     MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
/* 235 */     boolean send = false;
/* 236 */     boolean autoShot = (MCH_Config.LWeaponAutoFire.prmBool && is.func_77960_j() < is.func_77958_k() && gs.isLockComplete());
/*     */     
/* 238 */     if (this.KeySwWeaponMode.isKeyDown() && weapon.numMode > 1) {
/* 239 */       weaponMode = (weaponMode + 1) % weapon.numMode;
/* 240 */       W_McClient.MOD_playSoundFX("pi", 0.5F, 0.9F);
/*     */     } 
/*     */     
/* 243 */     if (this.KeyAttack.isKeyPress() || autoShot) {
/* 244 */       boolean result = false;
/* 245 */       if (is.func_77960_j() < is.func_77958_k() && gs.isLockComplete()) {
/* 246 */         boolean canFire = true;
/* 247 */         if (weaponMode > 0 && gs.getTargetEntity() != null) {
/* 248 */           double dx = (gs.getTargetEntity()).field_70165_t - player.field_70165_t;
/* 249 */           double dz = (gs.getTargetEntity()).field_70161_v - player.field_70161_v;
/* 250 */           canFire = (Math.sqrt(dx * dx + dz * dz) >= 40.0D);
/*     */         } 
/*     */         
/* 253 */         if (canFire) {
/* 254 */           pc.useWeapon = true;
/* 255 */           pc.useWeaponOption1 = W_Entity.getEntityId(gs.lastLockEntity);
/* 256 */           pc.useWeaponOption2 = weaponMode;
/* 257 */           pc.useWeaponPosX = player.field_70165_t;
/* 258 */           pc.useWeaponPosY = player.field_70163_u + player.func_70047_e();
/* 259 */           pc.useWeaponPosZ = player.field_70161_v;
/* 260 */           gs.clearLock();
/* 261 */           send = true;
/* 262 */           result = true;
/*     */         } 
/*     */       } 
/*     */       
/* 266 */       if (this.KeyAttack.isKeyDown() && !result && player.func_184612_cw() > 5) {
/* 267 */         playSoundNG();
/*     */       }
/*     */     } 
/*     */     
/* 271 */     if (this.KeyZoom.isKeyDown()) {
/* 272 */       int prevZoom = selectedZoom;
/* 273 */       selectedZoom = (selectedZoom + 1) % (weapon.getInfo()).zoom.length;
/* 274 */       if (prevZoom != selectedZoom) {
/* 275 */         playSound("zoom", 0.5F, 1.0F);
/*     */       }
/*     */     } 
/*     */     
/* 279 */     if (this.KeyCameraMode.isKeyDown()) {
/* 280 */       PotionEffect pe = player.func_70660_b(MobEffects.field_76439_r);
/* 281 */       MCH_Lib.DbgLog(true, "LWeapon NV %s", new Object[] { (pe != null) ? "ON->OFF" : "OFF->ON" });
/* 282 */       if (pe != null) {
/* 283 */         player.func_184589_d(MobEffects.field_76439_r);
/* 284 */         pc.camMode = 1;
/* 285 */         send = true;
/* 286 */         W_McClient.MOD_playSoundFX("pi", 0.5F, 0.9F);
/* 287 */       } else if (player.func_184612_cw() > 60) {
/* 288 */         pc.camMode = 2;
/* 289 */         send = true;
/* 290 */         W_McClient.MOD_playSoundFX("pi", 0.5F, 0.9F);
/*     */       } else {
/* 292 */         playSoundNG();
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     if (send)
/* 297 */       W_Network.sendToServer((W_PacketBase)pc); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\lweapon\MCH_ClientLightWeaponTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */