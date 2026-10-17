/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import com.norwood.mcheli.MCH_Packet;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public abstract class MCH_PacketPlayerControlBase
/*     */   extends MCH_Packet {
/*  10 */   public byte isUnmount = 0;
/*  11 */   public byte switchMode = -1;
/*  12 */   public byte switchCameraMode = 0;
/*  13 */   public byte switchWeapon = -1;
/*  14 */   public byte useFlareType = 0;
/*     */   public boolean useWeapon = false;
/*  16 */   public int useWeaponOption1 = 0;
/*  17 */   public int useWeaponOption2 = 0;
/*  18 */   public double useWeaponPosX = 0.0D;
/*  19 */   public double useWeaponPosY = 0.0D;
/*  20 */   public double useWeaponPosZ = 0.0D;
/*     */   public boolean throttleUp = false;
/*     */   public boolean throttleDown = false;
/*     */   public boolean moveLeft = false;
/*     */   public boolean moveRight = false;
/*     */   public boolean openGui;
/*  26 */   public byte switchHatch = 0;
/*  27 */   public byte switchFreeLook = 0;
/*  28 */   public byte switchGear = 0;
/*     */   public boolean ejectSeat = false;
/*  30 */   public byte putDownRack = 0;
/*     */   
/*     */   public boolean switchSearchLight = false;
/*     */   public boolean useBrake = false;
/*     */   public boolean switchGunnerStatus = false;
/*     */   
/*     */   public void readData(ByteArrayDataInput data) {
/*     */     try {
/*  38 */       short bf = data.readShort();
/*  39 */       this.useWeapon = getBit(bf, 0);
/*  40 */       this.throttleUp = getBit(bf, 1);
/*  41 */       this.throttleDown = getBit(bf, 2);
/*  42 */       this.moveLeft = getBit(bf, 3);
/*  43 */       this.moveRight = getBit(bf, 4);
/*  44 */       this.switchSearchLight = getBit(bf, 5);
/*  45 */       this.ejectSeat = getBit(bf, 6);
/*  46 */       this.openGui = getBit(bf, 7);
/*  47 */       this.useBrake = getBit(bf, 8);
/*  48 */       this.switchGunnerStatus = getBit(bf, 9);
/*  49 */       bf = (short)data.readByte();
/*  50 */       this.putDownRack = (byte)(bf >> 6 & 0x3);
/*  51 */       this.isUnmount = (byte)(bf >> 4 & 0x3);
/*  52 */       this.useFlareType = (byte)(bf >> 0 & 0xF);
/*  53 */       this.switchMode = data.readByte();
/*  54 */       this.switchWeapon = data.readByte();
/*  55 */       if (this.useWeapon) {
/*  56 */         this.useWeaponOption1 = data.readInt();
/*  57 */         this.useWeaponOption2 = data.readInt();
/*  58 */         this.useWeaponPosX = data.readDouble();
/*  59 */         this.useWeaponPosY = data.readDouble();
/*  60 */         this.useWeaponPosZ = data.readDouble();
/*     */       } 
/*     */       
/*  63 */       bf = (short)data.readByte();
/*  64 */       this.switchCameraMode = (byte)(bf >> 6 & 0x3);
/*  65 */       this.switchHatch = (byte)(bf >> 4 & 0x3);
/*  66 */       this.switchFreeLook = (byte)(bf >> 2 & 0x3);
/*  67 */       this.switchGear = (byte)(bf >> 0 & 0x3);
/*  68 */     } catch (Exception var3) {
/*  69 */       var3.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(DataOutputStream dos) {
/*     */     try {
/*  76 */       short bf = 0;
/*  77 */       bf = setBit(bf, 0, this.useWeapon);
/*  78 */       bf = setBit(bf, 1, this.throttleUp);
/*  79 */       bf = setBit(bf, 2, this.throttleDown);
/*  80 */       bf = setBit(bf, 3, this.moveLeft);
/*  81 */       bf = setBit(bf, 4, this.moveRight);
/*  82 */       bf = setBit(bf, 5, this.switchSearchLight);
/*  83 */       bf = setBit(bf, 6, this.ejectSeat);
/*  84 */       bf = setBit(bf, 7, this.openGui);
/*  85 */       bf = setBit(bf, 8, this.useBrake);
/*  86 */       bf = setBit(bf, 9, this.switchGunnerStatus);
/*  87 */       dos.writeShort(bf);
/*  88 */       bf = (short)(byte)((this.putDownRack & 0x3) << 6 | (this.isUnmount & 0x3) << 4 | this.useFlareType & 0xF);
/*  89 */       dos.writeByte(bf);
/*  90 */       dos.writeByte(this.switchMode);
/*  91 */       dos.writeByte(this.switchWeapon);
/*  92 */       if (this.useWeapon) {
/*  93 */         dos.writeInt(this.useWeaponOption1);
/*  94 */         dos.writeInt(this.useWeaponOption2);
/*  95 */         dos.writeDouble(this.useWeaponPosX);
/*  96 */         dos.writeDouble(this.useWeaponPosY);
/*  97 */         dos.writeDouble(this.useWeaponPosZ);
/*     */       } 
/*     */       
/* 100 */       bf = (short)(byte)((this.switchCameraMode & 0x3) << 6 | (this.switchHatch & 0x3) << 4 | (this.switchFreeLook & 0x3) << 2 | (this.switchGear & 0x3) << 0);
/* 101 */       dos.writeByte(bf);
/* 102 */     } catch (IOException var3) {
/* 103 */       var3.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketPlayerControlBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */