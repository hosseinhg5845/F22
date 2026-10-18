/*     */ package com.norwood.mcheli;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import com.norwood.mcheli.wrapper.W_Network;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class MCH_PacketEffectExplosion
/*     */   extends MCH_Packet {
/*  13 */   ExplosionParam prm = new ExplosionParam(this);
/*     */   
/*     */   public static ExplosionParam create() {
/*  16 */     return (new MCH_PacketEffectExplosion()).aaa();
/*     */   }
/*     */   
/*     */   public static void send(ExplosionParam param) {
/*  20 */     if (param != null) {
/*  21 */       MCH_PacketEffectExplosion s = new MCH_PacketEffectExplosion();
/*  22 */       s.prm = param;
/*  23 */       W_Network.sendToAllPlayers(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMessageID() {
/*  29 */     return 268437520;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteArrayDataInput data) {
/*     */     try {
/*  35 */       this.prm.posX = data.readDouble();
/*  36 */       this.prm.posY = data.readDouble();
/*  37 */       this.prm.posZ = data.readDouble();
/*  38 */       this.prm.size = data.readFloat();
/*  39 */       this.prm.exploderID = data.readInt();
/*  40 */       this.prm.inWater = (data.readByte() != 0);
/*  41 */       this.prm.readAffectedPositions(data);
/*  42 */     } catch (Exception var3) {
/*  43 */       var3.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(DataOutputStream dos) {
/*     */     try {
/*  50 */       dos.writeDouble(this.prm.posX);
/*  51 */       dos.writeDouble(this.prm.posY);
/*  52 */       dos.writeDouble(this.prm.posZ);
/*  53 */       dos.writeFloat(this.prm.size);
/*  54 */       dos.writeInt(this.prm.exploderID);
/*  55 */       dos.writeByte(this.prm.inWater ? 1 : 0);
/*  56 */       this.prm.writeAffectedPositions(dos);
/*  57 */     } catch (IOException var3) {
/*  58 */       var3.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private ExplosionParam aaa() {
/*  63 */     return new ExplosionParam(this);
/*     */   }
/*     */   
/*     */   public static class ExplosionParam
/*     */   {
/*     */     public double posX;
/*     */     public double posY;
/*     */     public double posZ;
/*     */     public float size;
/*     */     public int exploderID;
/*     */     public boolean inWater;
/*     */     private List<BlockPos> affectedPositions;
/*     */     
/*     */     public ExplosionParam(MCH_PacketEffectExplosion paramMCH_PacketEffectExplosion) {}
/*     */     
/*     */     public void setAffectedPositions(List<BlockPos> affectedPositions) {
/*  79 */       this.affectedPositions = Lists.newArrayList(affectedPositions);
/*     */     }
/*     */     
/*     */     public List<BlockPos> getAffectedBlockPositions() {
/*  83 */       return this.affectedPositions;
/*     */     }
/*     */     
/*     */     void writeAffectedPositions(DataOutputStream dos) throws IOException {
/*  87 */       dos.writeInt(this.affectedPositions.size());
/*     */       
/*  89 */       for (BlockPos blockpos : this.affectedPositions) {
/*  90 */         dos.writeInt(blockpos.getX());
/*  91 */         dos.writeInt(blockpos.getY());
/*  92 */         dos.writeInt(blockpos.getZ());
/*     */       } 
/*     */     }
/*     */     
/*     */     void readAffectedPositions(ByteArrayDataInput data) {
/*  97 */       int i = data.readInt();
/*  98 */       this.affectedPositions = Lists.newArrayListWithCapacity(i);
/*     */       
/* 100 */       for (int i1 = 0; i1 < i; i1++) {
/* 101 */         int j1 = data.readInt();
/* 102 */         int k1 = data.readInt();
/* 103 */         int l1 = data.readInt();
/* 104 */         this.affectedPositions.add(new BlockPos(j1, k1, l1));
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\MCH_PacketEffectExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */