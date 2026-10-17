/*     */ package com.norwood.mcheli.vehicle;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ 
/*     */ public class MCH_VehicleInfo
/*     */   extends MCH_AircraftInfo {
/*  12 */   public MCH_ItemVehicle item = null;
/*     */   public boolean isEnableMove = false;
/*     */   public boolean isEnableRot = false;
/*  15 */   public final List<VPart> partList = new ArrayList<>();
/*     */   
/*     */   public MCH_VehicleInfo(AddonResourceLocation location, String path) {
/*  18 */     super(location, path);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMinRotationPitch() {
/*  23 */     return -90.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxRotationPitch() {
/*  28 */     return 90.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem() {
/*  33 */     return (Item)this.item;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validate() throws Exception {
/*  38 */     return super.validate();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefaultHudName(int seatId) {
/*  43 */     return "vehicle";
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadItemData(String item, String data) {
/*  48 */     super.loadItemData(item, data);
/*  49 */     if (item.compareTo("canmove") == 0) {
/*  50 */       this.isEnableMove = toBool(data);
/*  51 */     } else if (item.compareTo("canrotation") == 0) {
/*  52 */       this.isEnableRot = toBool(data);
/*  53 */     } else if (item.compareTo("rotationpitchmin") == 0) {
/*  54 */       super.loadItemData("minrotationpitch", data);
/*  55 */     } else if (item.compareTo("rotationpitchmax") == 0) {
/*  56 */       super.loadItemData("maxrotationpitch", data);
/*  57 */     } else if (item.compareTo("addpart") == 0) {
/*  58 */       String[] s = data.split("\\s*,\\s*");
/*  59 */       if (s.length >= 7) {
/*  60 */         float rb = (s.length >= 8) ? toFloat(s[7]) : 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  70 */         VPart n = new VPart(this, toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), "part" + this.partList.size(), toBool(s[0]), toBool(s[1]), toBool(s[2]), toInt(s[3]), rb);
/*     */ 
/*     */         
/*  73 */         this.partList.add(n);
/*     */       } 
/*  75 */     } else if (item.compareTo("addchildpart") == 0 && !this.partList.isEmpty()) {
/*  76 */       String[] s = data.split("\\s*,\\s*");
/*  77 */       if (s.length >= 7) {
/*  78 */         float rb = (s.length >= 8) ? toFloat(s[7]) : 0.0F;
/*  79 */         VPart p = this.partList.get(this.partList.size() - 1);
/*  80 */         if (p.child == null) {
/*  81 */           p.child = new ArrayList<>();
/*     */         }
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
/*  93 */         VPart n = new VPart(this, toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), p.modelName + "_" + p.child.size(), toBool(s[0]), toBool(s[1]), toBool(s[2]), toInt(s[3]), rb);
/*     */ 
/*     */         
/*  96 */         p.child.add(n);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDirectoryName() {
/* 103 */     return "vehicles";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKindName() {
/* 108 */     return "vehicle";
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPostReload() {
/* 113 */     MCH_MOD.proxy.registerModelsVehicle(this, true);
/*     */   }
/*     */   
/*     */   public static class VPart
/*     */     extends MCH_AircraftInfo.DrawnPart
/*     */   {
/*     */     public final boolean rotPitch;
/*     */     public final boolean rotYaw;
/*     */     public final int type;
/*     */     public final boolean drawFP;
/*     */     public final float recoilBuf;
/*     */     public List<VPart> child;
/*     */     
/*     */     public VPart(MCH_VehicleInfo paramMCH_VehicleInfo, float x, float y, float z, String model, boolean drawfp, boolean roty, boolean rotp, int type, float rb) {
/* 127 */       super(paramMCH_VehicleInfo, x, y, z, 0.0F, 0.0F, 0.0F, model);
/* 128 */       this.rotYaw = roty;
/* 129 */       this.rotPitch = rotp;
/* 130 */       this.type = type;
/* 131 */       this.child = null;
/* 132 */       this.drawFP = drawfp;
/* 133 */       this.recoilBuf = rb;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\vehicle\MCH_VehicleInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */