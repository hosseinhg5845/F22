/*     */ package com.norwood.mcheli.helicopter;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ 
/*     */ public class MCH_HeliInfo
/*     */   extends MCH_AircraftInfo {
/*  13 */   public MCH_ItemHeli item = null;
/*     */   public boolean isEnableFoldBlade;
/*     */   public final List<Rotor> rotorList;
/*     */   
/*     */   public MCH_HeliInfo(AddonResourceLocation location, String path) {
/*  18 */     super(location, path);
/*  19 */     this.isEnableGunnerMode = false;
/*  20 */     this.isEnableFoldBlade = false;
/*  21 */     this.rotorList = new ArrayList<>();
/*  22 */     this.minRotationPitch = -20.0F;
/*  23 */     this.maxRotationPitch = 20.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validate() throws Exception {
/*  28 */     this.speed = (float)(this.speed * MCH_Config.AllHeliSpeed.prmDouble);
/*  29 */     return super.validate();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDefaultSoundRange() {
/*  34 */     return 80.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDefaultRotorSpeed() {
/*  39 */     return 79.99F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDefaultMaxZoom() {
/*  44 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem() {
/*  49 */     return (Item)this.item;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefaultHudName(int seatId) {
/*  54 */     if (seatId <= 0) {
/*  55 */       return "heli";
/*     */     }
/*  57 */     return (seatId == 1) ? "heli_gnr" : "gunner";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadItemData(String item, String data) {
/*  63 */     super.loadItemData(item, data);
/*  64 */     if (item.compareTo("enablefoldblade") == 0) {
/*  65 */       this.isEnableFoldBlade = toBool(data);
/*  66 */     } else if (item.compareTo("addrotor") == 0 || item.compareTo("addrotorold") == 0) {
/*  67 */       String[] s = data.split("\\s*,\\s*");
/*  68 */       if (s.length == 8 || s.length == 9) {
/*  69 */         boolean cfb = (s.length == 9 && toBool(s[8]));
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
/*  82 */         Rotor e = new Rotor(this, toInt(s[0]), toInt(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), toFloat(s[7]), "blade" + this.rotorList.size(), cfb, (item.compareTo("addrotorold") == 0));
/*     */         
/*  84 */         this.rotorList.add(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDirectoryName() {
/*  91 */     return "helicopters";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKindName() {
/*  96 */     return "helicopter";
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPostReload() {
/* 101 */     MCH_MOD.proxy.registerModelsHeli(this, true);
/*     */   }
/*     */   
/*     */   public static class Rotor
/*     */     extends MCH_AircraftInfo.DrawnPart
/*     */   {
/*     */     public final int bladeNum;
/*     */     public final int bladeRot;
/*     */     public final boolean haveFoldFunc;
/*     */     public final boolean oldRenderMethod;
/*     */     
/*     */     public Rotor(MCH_HeliInfo paramMCH_HeliInfo, int b, int br, float x, float y, float z, float rx, float ry, float rz, String model, boolean hf, boolean old) {
/* 113 */       super(paramMCH_HeliInfo, x, y, z, rx, ry, rz, model);
/* 114 */       this.bladeNum = b;
/* 115 */       this.bladeRot = br;
/* 116 */       this.haveFoldFunc = hf;
/* 117 */       this.oldRenderMethod = old;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helicopter\MCH_HeliInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */