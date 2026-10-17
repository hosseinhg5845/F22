/*     */ package com.norwood.mcheli.aircraft;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import com.norwood.mcheli.MCH_Packet;
/*     */ import com.norwood.mcheli.wrapper.W_Entity;
/*     */ import com.norwood.mcheli.wrapper.W_Network;
/*     */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ public class MCH_PacketNotifyAmmoNum extends MCH_Packet {
/*  14 */   public int entityID_Ac = -1;
/*     */   public boolean all = false;
/*  16 */   public byte weaponID = -1;
/*  17 */   public byte num = 0;
/*  18 */   public short[] ammo = new short[0];
/*  19 */   public short[] restAmmo = new short[0];
/*     */   
/*     */   public static void sendAllAmmoNum(MCH_EntityAircraft ac, EntityPlayer target) {
/*  22 */     MCH_PacketNotifyAmmoNum s = new MCH_PacketNotifyAmmoNum();
/*  23 */     s.entityID_Ac = W_Entity.getEntityId((Entity)ac);
/*  24 */     s.all = true;
/*  25 */     s.num = (byte)ac.getWeaponNum();
/*  26 */     s.ammo = new short[s.num];
/*  27 */     s.restAmmo = new short[s.num];
/*     */     
/*  29 */     for (int i = 0; i < s.num; i++) {
/*  30 */       s.ammo[i] = (short)ac.getWeapon(i).getAmmoNum();
/*  31 */       s.restAmmo[i] = (short)ac.getWeapon(i).getRestAllAmmoNum();
/*     */     } 
/*     */     
/*  34 */     send(s, ac, target);
/*     */   }
/*     */   
/*     */   public static void sendAmmoNum(MCH_EntityAircraft ac, EntityPlayer target, int wid) {
/*  38 */     sendAmmoNum(ac, target, wid, ac.getWeapon(wid).getAmmoNum(), ac.getWeapon(wid).getRestAllAmmoNum());
/*     */   }
/*     */   
/*     */   public static void sendAmmoNum(MCH_EntityAircraft ac, EntityPlayer target, int wid, int ammo, int rest_ammo) {
/*  42 */     MCH_PacketNotifyAmmoNum s = new MCH_PacketNotifyAmmoNum();
/*  43 */     s.entityID_Ac = W_Entity.getEntityId((Entity)ac);
/*  44 */     s.all = false;
/*  45 */     s.weaponID = (byte)wid;
/*  46 */     s.ammo = new short[] { (short)ammo };
/*  47 */     s.restAmmo = new short[] { (short)rest_ammo };
/*  48 */     send(s, ac, target);
/*     */   }
/*     */   
/*     */   public static void send(MCH_PacketNotifyAmmoNum s, MCH_EntityAircraft ac, EntityPlayer target) {
/*  52 */     if (target == null) {
/*  53 */       for (int i = 0; i < ac.getSeatNum() + 1; i++) {
/*  54 */         Entity e = ac.getEntityBySeatId(i);
/*  55 */         if (e instanceof EntityPlayer) {
/*  56 */           W_Network.sendToPlayer((W_PacketBase)s, (EntityPlayer)e);
/*     */         }
/*     */       } 
/*     */     } else {
/*  60 */       W_Network.sendToPlayer((W_PacketBase)s, target);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMessageID() {
/*  66 */     return 268439604;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteArrayDataInput data) {
/*     */     try {
/*  72 */       this.entityID_Ac = data.readInt();
/*  73 */       this.all = (data.readByte() != 0);
/*  74 */       if (this.all) {
/*  75 */         this.num = data.readByte();
/*  76 */         this.ammo = new short[this.num];
/*  77 */         this.restAmmo = new short[this.num];
/*     */         
/*  79 */         for (int i = 0; i < this.num; i++) {
/*  80 */           this.ammo[i] = data.readShort();
/*  81 */           this.restAmmo[i] = data.readShort();
/*     */         } 
/*     */       } else {
/*  84 */         this.weaponID = data.readByte();
/*  85 */         this.ammo = new short[] { data.readShort() };
/*  86 */         this.restAmmo = new short[] { data.readShort() };
/*     */       } 
/*  88 */     } catch (Exception var3) {
/*  89 */       var3.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(DataOutputStream dos) {
/*     */     try {
/*  96 */       dos.writeInt(this.entityID_Ac);
/*  97 */       dos.writeByte(this.all ? 1 : 0);
/*  98 */       if (this.all) {
/*  99 */         dos.writeByte(this.num);
/*     */         
/* 101 */         for (int i = 0; i < this.num; i++) {
/* 102 */           dos.writeShort(this.ammo[i]);
/* 103 */           dos.writeShort(this.restAmmo[i]);
/*     */         } 
/*     */       } else {
/* 106 */         dos.writeByte(this.weaponID);
/* 107 */         dos.writeShort(this.ammo[0]);
/* 108 */         dos.writeShort(this.restAmmo[0]);
/*     */       } 
/* 110 */     } catch (IOException var3) {
/* 111 */       var3.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\aircraft\MCH_PacketNotifyAmmoNum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */