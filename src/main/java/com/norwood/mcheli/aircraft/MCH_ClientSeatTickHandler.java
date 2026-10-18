/*    */ package com.norwood.mcheli.aircraft;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_ClientTickHandlerBase;
/*    */ import com.norwood.mcheli.MCH_Config;
/*    */ import com.norwood.mcheli.MCH_Key;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class MCH_ClientSeatTickHandler extends MCH_ClientTickHandlerBase {
/*    */   public MCH_Key KeySwitchNextSeat;
/*    */   public MCH_Key KeySwitchPrevSeat;
/*    */   public MCH_Key KeyParachuting;
/*    */   public MCH_Key KeyFreeLook;
/*    */   public MCH_Key KeyUnmountForce;
/*    */   public MCH_Key[] Keys;
/*    */   protected boolean isRiding = false;
/*    */   protected boolean isBeforeRiding = false;
/*    */   
/*    */   public MCH_ClientSeatTickHandler(Minecraft minecraft, MCH_Config config) {
/* 23 */     super(minecraft);
/* 24 */     updateKeybind(config);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateKeybind(MCH_Config config) {
/* 29 */     this.KeySwitchNextSeat = new MCH_Key(MCH_Config.KeyExtra.prmInt);
/* 30 */     this.KeySwitchPrevSeat = new MCH_Key(MCH_Config.KeyGUI.prmInt);
/* 31 */     this.KeyParachuting = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
/* 32 */     this.KeyUnmountForce = new MCH_Key(42);
/* 33 */     this.KeyFreeLook = new MCH_Key(MCH_Config.KeyFreeLook.prmInt);
/* 34 */     this.Keys = new MCH_Key[] { this.KeySwitchNextSeat, this.KeySwitchPrevSeat, this.KeyParachuting, this.KeyUnmountForce, this.KeyFreeLook };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onTick(boolean inGUI) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: getfield Keys : [Lcom/norwood/mcheli/MCH_Key;
/*    */     //   4: astore_2
/*    */     //   5: aload_2
/*    */     //   6: arraylength
/*    */     //   7: istore_3
/*    */     //   8: iconst_0
/*    */     //   9: istore #4
/*    */     //   11: iload #4
/*    */     //   13: iload_3
/*    */     //   14: if_icmpge -> 34
/*    */     //   17: aload_2
/*    */     //   18: iload #4
/*    */     //   20: aaload
/*    */     //   21: astore #5
/*    */     //   23: aload #5
/*    */     //   25: invokevirtual update : ()V
/*    */     //   28: iinc #4, 1
/*    */     //   31: goto -> 11
/*    */     //   34: aload_0
/*    */     //   35: aload_0
/*    */     //   36: getfield isRiding : Z
/*    */     //   39: putfield isBeforeRiding : Z
/*    */     //   42: aload_0
/*    */     //   43: getfield mc : Lnet/minecraft/client/Minecraft;
/*    */     //   46: getfield player : Lnet/minecraft/client/entity/EntityPlayerSP;
/*    */     //   49: astore_2
/*    */     //   50: aconst_null
/*    */     //   51: astore_3
/*    */     //   52: aload_2
/*    */     //   53: ifnull -> 130
/*    */     //   56: aload_2
/*    */     //   57: invokevirtual getRidingEntity : ()Lnet/minecraft/entity/Entity;
/*    */     //   60: astore #5
/*    */     //   62: aload #5
/*    */     //   64: instanceof com/norwood/mcheli/aircraft/MCH_EntitySeat
/*    */     //   67: ifeq -> 130
/*    */     //   70: aload #5
/*    */     //   72: checkcast com/norwood/mcheli/aircraft/MCH_EntitySeat
/*    */     //   75: astore #4
/*    */     //   77: aload #4
/*    */     //   79: invokevirtual getParent : ()Lcom/norwood/mcheli/aircraft/MCH_EntityAircraft;
/*    */     //   82: ifnull -> 96
/*    */     //   85: aload #4
/*    */     //   87: invokevirtual getParent : ()Lcom/norwood/mcheli/aircraft/MCH_EntityAircraft;
/*    */     //   90: invokevirtual getAcInfo : ()Lcom/norwood/mcheli/aircraft/MCH_AircraftInfo;
/*    */     //   93: ifnonnull -> 97
/*    */     //   96: return
/*    */     //   97: aload #4
/*    */     //   99: invokevirtual getParent : ()Lcom/norwood/mcheli/aircraft/MCH_EntityAircraft;
/*    */     //   102: astore_3
/*    */     //   103: iload_1
/*    */     //   104: ifne -> 122
/*    */     //   107: aload_3
/*    */     //   108: invokevirtual isDestroyed : ()Z
/*    */     //   111: ifne -> 122
/*    */     //   114: aload_0
/*    */     //   115: aload_2
/*    */     //   116: aload #4
/*    */     //   118: aload_3
/*    */     //   119: invokespecial playerControl : (Lnet/minecraft/entity/player/EntityPlayer;Lcom/norwood/mcheli/aircraft/MCH_EntitySeat;Lcom/norwood/mcheli/aircraft/MCH_EntityAircraft;)V
/*    */     //   122: aload_0
/*    */     //   123: iconst_1
/*    */     //   124: putfield isRiding : Z
/*    */     //   127: goto -> 135
/*    */     //   130: aload_0
/*    */     //   131: iconst_0
/*    */     //   132: putfield isRiding : Z
/*    */     //   135: aload_0
/*    */     //   136: getfield isBeforeRiding : Z
/*    */     //   139: aload_0
/*    */     //   140: getfield isRiding : Z
/*    */     //   143: if_icmpeq -> 184
/*    */     //   146: aload_0
/*    */     //   147: getfield isRiding : Z
/*    */     //   150: ifeq -> 163
/*    */     //   153: aload_3
/*    */     //   154: getfield thirdPersonDist : F
/*    */     //   157: invokestatic setThirdPersonDistance : (F)V
/*    */     //   160: goto -> 184
/*    */     //   163: aload_2
/*    */     //   164: ifnull -> 177
/*    */     //   167: aload_2
/*    */     //   168: invokevirtual getRidingEntity : ()Lnet/minecraft/entity/Entity;
/*    */     //   171: instanceof com/norwood/mcheli/aircraft/MCH_EntityAircraft
/*    */     //   174: ifne -> 180
/*    */     //   177: invokestatic restoreDefaultThirdPersonDistance : ()V
/*    */     //   180: aload_2
/*    */     //   181: invokestatic setRenderViewEntity : (Lnet/minecraft/entity/EntityLivingBase;)V
/*    */     //   184: return
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #39	-> 0
/*    */     //   #40	-> 23
/*    */     //   #39	-> 28
/*    */     //   #43	-> 34
/*    */     //   #44	-> 42
/*    */     //   #45	-> 50
/*    */     //   #46	-> 52
/*    */     //   #47	-> 77
/*    */     //   #48	-> 96
/*    */     //   #51	-> 97
/*    */     //   #52	-> 103
/*    */     //   #53	-> 114
/*    */     //   #56	-> 122
/*    */     //   #58	-> 130
/*    */     //   #61	-> 135
/*    */     //   #62	-> 146
/*    */     //   #63	-> 153
/*    */     //   #65	-> 163
/*    */     //   #66	-> 177
/*    */     //   #69	-> 180
/*    */     //   #72	-> 184
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   23	5	5	k	Lcom/norwood/mcheli/MCH_Key;
/*    */     //   77	53	4	seat	Lcom/norwood/mcheli/aircraft/MCH_EntitySeat;
/*    */     //   0	185	0	this	Lcom/norwood/mcheli/aircraft/MCH_ClientSeatTickHandler;
/*    */     //   0	185	1	inGUI	Z
/*    */     //   50	135	2	player	Lnet/minecraft/entity/player/EntityPlayer;
/*    */     //   52	133	3	ac	Lcom/norwood/mcheli/aircraft/MCH_EntityAircraft;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void playerControl(EntityPlayer player, MCH_EntitySeat seat, MCH_EntityAircraft ac) {
/* 75 */     MCH_PacketSeatPlayerControl pc = new MCH_PacketSeatPlayerControl();
/* 76 */     boolean send = false;
/* 77 */     if (this.KeyFreeLook.isKeyDown() && ac.canSwitchGunnerFreeLook(player)) {
/* 78 */       ac.switchGunnerFreeLookMode();
/*    */     }
/*    */     
/* 81 */     if (this.KeyParachuting.isKeyDown()) {
/* 82 */       if (ac.canParachuting((Entity)player)) {
/* 83 */         pc.parachuting = true;
/* 84 */         send = true;
/* 85 */       } else if (ac.canRepelling((Entity)player)) {
/* 86 */         pc.parachuting = true;
/* 87 */         send = true;
/*    */       } else {
/* 89 */         playSoundNG();
/*    */       } 
/*    */     }
/*    */     
/* 93 */     if (send)
/* 94 */       W_Network.sendToServer((W_PacketBase)pc); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_ClientSeatTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */