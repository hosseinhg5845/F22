/*     */ package com.norwood.mcheli.gltd;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_ClientTickHandlerBase;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_Key;
/*     */ import com.norwood.mcheli.wrapper.W_Network;
/*     */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ 
/*     */ public class MCH_ClientGLTDTickHandler
/*     */   extends MCH_ClientTickHandlerBase
/*     */ {
/*     */   public MCH_Key KeyUseWeapon;
/*     */   public MCH_Key KeySwitchWeapon1;
/*     */   public MCH_Key KeySwitchWeapon2;
/*     */   public MCH_Key KeySwWeaponMode;
/*     */   public MCH_Key KeyZoom;
/*     */   
/*     */   public MCH_ClientGLTDTickHandler(Minecraft minecraft, MCH_Config config) {
/*  22 */     super(minecraft);
/*  23 */     updateKeybind(config);
/*     */   }
/*     */   public MCH_Key KeyCameraMode; public MCH_Key KeyUnmount; public MCH_Key KeyUnmount_1_6; public MCH_Key[] Keys; protected boolean isRiding = false; protected boolean isBeforeRiding = false;
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  28 */     this.KeyUseWeapon = new MCH_Key(MCH_Config.KeyUseWeapon.prmInt);
/*  29 */     this.KeySwitchWeapon1 = new MCH_Key(MCH_Config.KeySwitchWeapon1.prmInt);
/*  30 */     this.KeySwitchWeapon2 = new MCH_Key(MCH_Config.KeySwitchWeapon2.prmInt);
/*  31 */     this.KeySwWeaponMode = new MCH_Key(MCH_Config.KeySwWeaponMode.prmInt);
/*  32 */     this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/*  33 */     this.KeyCameraMode = new MCH_Key(MCH_Config.KeyCameraMode.prmInt);
/*  34 */     this.KeyUnmount = new MCH_Key(MCH_Config.KeyUnmount.prmInt);
/*  35 */     this.KeyUnmount_1_6 = new MCH_Key(42);
/*  36 */     this.Keys = new MCH_Key[] { this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmount_1_6 };
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
/*     */   protected void updateGLTD(EntityPlayer player, MCH_EntityGLTD gltd) {
/*  49 */     if (player.rotationPitch < -70.0F) {
/*  50 */       player.rotationPitch = -70.0F;
/*     */     }
/*     */     
/*  53 */     if (player.rotationPitch > 70.0F) {
/*  54 */       player.rotationPitch = 70.0F;
/*     */     }
/*     */     
/*  57 */     float yaw = gltd.rotationYaw;
/*  58 */     if (player.rotationYaw < yaw - 70.0F) {
/*  59 */       player.rotationYaw = yaw - 70.0F;
/*     */     }
/*     */     
/*  62 */     if (player.rotationYaw > yaw + 70.0F) {
/*  63 */       player.rotationYaw = yaw + 70.0F;
/*     */     }
/*     */     
/*  66 */     gltd.camera.rotationYaw = player.rotationYaw;
/*  67 */     gltd.camera.rotationPitch = player.rotationPitch;
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
/*     */     //   36: getfield isRiding : Z
/*     */     //   39: putfield isBeforeRiding : Z
/*     */     //   42: aload_0
/*     */     //   43: getfield mc : Lnet/minecraft/client/Minecraft;
/*     */     //   46: getfield player : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   49: astore_2
/*     */     //   50: aconst_null
/*     */     //   51: astore_3
/*     */     //   52: aload_2
/*     */     //   53: ifnull -> 134
/*     */     //   56: aload_2
/*     */     //   57: invokevirtual getRidingEntity : ()Lnet/minecraft/entity/Entity;
/*     */     //   60: astore #5
/*     */     //   62: aload #5
/*     */     //   64: instanceof com/norwood/mcheli/gltd/MCH_EntityGLTD
/*     */     //   67: ifeq -> 134
/*     */     //   70: aload #5
/*     */     //   72: checkcast com/norwood/mcheli/gltd/MCH_EntityGLTD
/*     */     //   75: astore #4
/*     */     //   77: aload_0
/*     */     //   78: aload_2
/*     */     //   79: aload #4
/*     */     //   81: invokevirtual updateGLTD : (Lnet/minecraft/entity/player/EntityPlayer;Lcom/norwood/mcheli/gltd/MCH_EntityGLTD;)V
/*     */     //   84: aload_2
/*     */     //   85: invokevirtual getHeldItemMainhand : ()Lnet/minecraft/item/ItemStack;
/*     */     //   88: invokestatic disableFirstPersonItemRender : (Lnet/minecraft/item/ItemStack;)V
/*     */     //   91: aload_0
/*     */     //   92: getfield mc : Lnet/minecraft/client/Minecraft;
/*     */     //   95: getfield world : Lnet/minecraft/client/multiplayer/WorldClient;
/*     */     //   98: invokestatic getInstance : (Lnet/minecraft/world/World;)Lcom/norwood/mcheli/MCH_ViewEntityDummy;
/*     */     //   101: astore_3
/*     */     //   102: aload_3
/*     */     //   103: aload #4
/*     */     //   105: getfield camera : Lcom/norwood/mcheli/MCH_Camera;
/*     */     //   108: invokevirtual update : (Lcom/norwood/mcheli/MCH_Camera;)V
/*     */     //   111: iload_1
/*     */     //   112: ifne -> 122
/*     */     //   115: aload_0
/*     */     //   116: aload_2
/*     */     //   117: aload #4
/*     */     //   119: invokevirtual playerControl : (Lnet/minecraft/entity/player/EntityPlayer;Lcom/norwood/mcheli/gltd/MCH_EntityGLTD;)V
/*     */     //   122: aload_3
/*     */     //   123: invokestatic setRenderViewEntity : (Lnet/minecraft/entity/EntityLivingBase;)V
/*     */     //   126: aload_0
/*     */     //   127: iconst_1
/*     */     //   128: putfield isRiding : Z
/*     */     //   131: goto -> 139
/*     */     //   134: aload_0
/*     */     //   135: iconst_0
/*     */     //   136: putfield isRiding : Z
/*     */     //   139: aload_0
/*     */     //   140: getfield isBeforeRiding : Z
/*     */     //   143: aload_0
/*     */     //   144: getfield isRiding : Z
/*     */     //   147: if_icmpeq -> 191
/*     */     //   150: aload_0
/*     */     //   151: getfield isRiding : Z
/*     */     //   154: ifeq -> 184
/*     */     //   157: aload_3
/*     */     //   158: aload_3
/*     */     //   159: getfield posX : D
/*     */     //   162: putfield prevPosX : D
/*     */     //   165: aload_3
/*     */     //   166: aload_3
/*     */     //   167: getfield posY : D
/*     */     //   170: putfield prevPosY : D
/*     */     //   173: aload_3
/*     */     //   174: aload_3
/*     */     //   175: getfield posZ : D
/*     */     //   178: putfield prevPosZ : D
/*     */     //   181: goto -> 191
/*     */     //   184: invokestatic enableFirstPersonItemRender : ()V
/*     */     //   187: aload_2
/*     */     //   188: invokestatic setRenderViewEntity : (Lnet/minecraft/entity/EntityLivingBase;)V
/*     */     //   191: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #72	-> 0
/*     */     //   #73	-> 23
/*     */     //   #72	-> 28
/*     */     //   #76	-> 34
/*     */     //   #77	-> 42
/*     */     //   #78	-> 50
/*     */     //   #79	-> 52
/*     */     //   #80	-> 77
/*     */     //   #81	-> 84
/*     */     //   #82	-> 91
/*     */     //   #83	-> 102
/*     */     //   #84	-> 111
/*     */     //   #85	-> 115
/*     */     //   #88	-> 122
/*     */     //   #89	-> 126
/*     */     //   #91	-> 134
/*     */     //   #94	-> 139
/*     */     //   #95	-> 150
/*     */     //   #96	-> 157
/*     */     //   #97	-> 165
/*     */     //   #98	-> 173
/*     */     //   #100	-> 184
/*     */     //   #101	-> 187
/*     */     //   #104	-> 191
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   23	5	5	k	Lcom/norwood/mcheli/MCH_Key;
/*     */     //   77	57	4	gltd	Lcom/norwood/mcheli/gltd/MCH_EntityGLTD;
/*     */     //   0	192	0	this	Lcom/norwood/mcheli/gltd/MCH_ClientGLTDTickHandler;
/*     */     //   0	192	1	inGUI	Z
/*     */     //   50	142	2	player	Lnet/minecraft/entity/player/EntityPlayer;
/*     */     //   52	140	3	viewEntityDummy	Lcom/norwood/mcheli/MCH_ViewEntityDummy;
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
/*     */   protected void playerControl(EntityPlayer player, MCH_EntityGLTD gltd) {
/* 107 */     MCH_PacketGLTDPlayerControl pc = new MCH_PacketGLTDPlayerControl();
/* 108 */     boolean send = false;
/* 109 */     if (this.KeyUnmount.isKeyDown()) {
/* 110 */       pc.unmount = true;
/* 111 */       send = true;
/*     */     } 
/*     */     
/* 114 */     if ((!this.KeySwitchWeapon1.isKeyDown() || !this.KeySwitchWeapon2.isKeyDown()) && this.KeyUseWeapon.isKeyPress()) {
/* 115 */       if (gltd.useCurrentWeapon(0, 0)) {
/* 116 */         pc.useWeapon = true;
/* 117 */         send = true;
/* 118 */       } else if (this.KeyUseWeapon.isKeyDown()) {
/* 119 */         playSoundNG();
/*     */       } 
/*     */     }
/*     */     
/* 123 */     float prevZoom = gltd.camera.getCameraZoom();
/* 124 */     if (this.KeyZoom.isKeyPress() && !this.KeySwWeaponMode.isKeyPress()) {
/* 125 */       gltd.zoomCamera(0.1F * gltd.camera.getCameraZoom());
/*     */     }
/*     */     
/* 128 */     if (!this.KeyZoom.isKeyPress() && this.KeySwWeaponMode.isKeyPress()) {
/* 129 */       gltd.zoomCamera(-0.1F * gltd.camera.getCameraZoom());
/*     */     }
/*     */     
/* 132 */     if (prevZoom != gltd.camera.getCameraZoom()) {
/* 133 */       playSound("zoom", 0.1F, (prevZoom < gltd.camera.getCameraZoom()) ? 1.0F : 0.85F);
/*     */     }
/*     */     
/* 136 */     if (this.KeyCameraMode.isKeyDown()) {
/* 137 */       int beforeMode = gltd.camera.getMode(0);
/* 138 */       gltd.camera.setMode(0, gltd.camera.getMode(0) + 1);
/* 139 */       int mode = gltd.camera.getMode(0);
/* 140 */       if (mode != beforeMode) {
/* 141 */         pc.switchCameraMode = (byte)mode;
/* 142 */         playSoundOK();
/* 143 */         send = true;
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     if (send)
/* 148 */       W_Network.sendToServer((W_PacketBase)pc); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\gltd\MCH_ClientGLTDTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */