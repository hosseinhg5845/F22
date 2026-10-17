/*     */ package com.norwood.mcheli.plane;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*     */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ 
/*     */ public class MCP_PlaneInfo
/*     */   extends MCH_AircraftInfo {
/*  13 */   public MCP_ItemPlane item = null;
/*  14 */   public final List<MCH_AircraftInfo.DrawnPart> nozzles = new ArrayList<>();
/*  15 */   public final List<Rotor> rotorList = new ArrayList<>();
/*  16 */   public final List<Wing> wingList = new ArrayList<>();
/*     */   public boolean isEnableVtol = false;
/*     */   public boolean isDefaultVtol;
/*  19 */   public float vtolYaw = 0.3F;
/*  20 */   public float vtolPitch = 0.2F;
/*     */   public boolean isEnableAutoPilot = false;
/*     */   public boolean isVariableSweepWing = false;
/*  23 */   public float sweepWingSpeed = this.speed;
/*     */   
/*     */   public MCP_PlaneInfo(AddonResourceLocation location, String path) {
/*  26 */     super(location, path);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem() {
/*  31 */     return (Item)this.item;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDefaultRotorSpeed() {
/*  36 */     return 47.94F;
/*     */   }
/*     */   
/*     */   public boolean haveNozzle() {
/*  40 */     return !this.nozzles.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean haveRotor() {
/*  44 */     return !this.rotorList.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean haveWing() {
/*  48 */     return !this.wingList.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxSpeed() {
/*  53 */     return 1.8F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDefaultMaxZoom() {
/*  58 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefaultHudName(int seatId) {
/*  63 */     if (seatId <= 0) {
/*  64 */       return "plane";
/*     */     }
/*  66 */     return (seatId == 1) ? "plane" : "gunner";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate() throws Exception {
/*  72 */     if (haveHatch() && haveWing()) {
/*  73 */       this.wingList.clear();
/*  74 */       this.hatchList.clear();
/*     */     } 
/*     */     
/*  77 */     this.speed = (float)(this.speed * MCH_Config.AllPlaneSpeed.prmDouble);
/*  78 */     this.sweepWingSpeed = (float)(this.sweepWingSpeed * MCH_Config.AllPlaneSpeed.prmDouble);
/*  79 */     return super.validate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadItemData(String item, String data) {
/*  84 */     super.loadItemData(item, data);
/*  85 */     if (item.compareTo("addpartrotor") == 0) {
/*  86 */       String[] s = data.split("\\s*,\\s*");
/*  87 */       if (s.length >= 6) {
/*  88 */         float m = (s.length >= 7) ? (toFloat(s[6], -180.0F, 180.0F) / 90.0F) : 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  98 */         Rotor e = new Rotor(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), m, "rotor" + this.rotorList.size());
/*     */         
/* 100 */         this.rotorList.add(e);
/*     */       } 
/* 102 */     } else if (item.compareTo("addblade") == 0) {
/* 103 */       int idx = this.rotorList.size() - 1;
/* 104 */       Rotor r = !this.rotorList.isEmpty() ? this.rotorList.get(idx) : null;
/* 105 */       if (r != null) {
/* 106 */         String[] s = data.split("\\s*,\\s*");
/* 107 */         if (s.length == 8) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 117 */           Blade b = new Blade(this, toInt(s[0]), toInt(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), toFloat(s[7]), "blade" + idx);
/*     */ 
/*     */           
/* 120 */           r.blades.add(b);
/*     */         } 
/*     */       } 
/* 123 */     } else if (item.compareTo("addpartwing") == 0) {
/* 124 */       String[] s = data.split("\\s*,\\s*");
/* 125 */       if (s.length == 7) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 135 */         Wing n = new Wing(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), "wing" + this.wingList.size());
/*     */         
/* 137 */         this.wingList.add(n);
/*     */       } 
/* 139 */     } else if (item.equalsIgnoreCase("AddPartPylon")) {
/* 140 */       String[] s = data.split("\\s*,\\s*");
/* 141 */       if (s.length >= 7 && !this.wingList.isEmpty()) {
/* 142 */         Wing w = this.wingList.get(this.wingList.size() - 1);
/* 143 */         if (w.pylonList == null) {
/* 144 */           w.pylonList = new ArrayList<>();
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
/* 156 */         Pylon n = new Pylon(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), w.modelName + "_pylon" + w.pylonList.size());
/*     */         
/* 158 */         w.pylonList.add(n);
/*     */       } 
/* 160 */     } else if (item.compareTo("addpartnozzle") == 0) {
/* 161 */       String[] s = data.split("\\s*,\\s*");
/* 162 */       if (s.length == 6) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 171 */         MCH_AircraftInfo.DrawnPart n = new MCH_AircraftInfo.DrawnPart(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), "nozzle" + this.nozzles.size());
/*     */         
/* 173 */         this.nozzles.add(n);
/*     */       } 
/* 175 */     } else if (item.compareTo("variablesweepwing") == 0) {
/* 176 */       this.isVariableSweepWing = toBool(data);
/* 177 */     } else if (item.compareTo("sweepwingspeed") == 0) {
/* 178 */       this.sweepWingSpeed = toFloat(data, 0.0F, 5.0F);
/* 179 */     } else if (item.compareTo("enablevtol") == 0) {
/* 180 */       this.isEnableVtol = toBool(data);
/* 181 */     } else if (item.compareTo("defaultvtol") == 0) {
/* 182 */       this.isDefaultVtol = toBool(data);
/* 183 */     } else if (item.compareTo("vtolyaw") == 0) {
/* 184 */       this.vtolYaw = toFloat(data, 0.0F, 1.0F);
/* 185 */     } else if (item.compareTo("vtolpitch") == 0) {
/* 186 */       this.vtolPitch = toFloat(data, 0.01F, 1.0F);
/* 187 */     } else if (item.compareTo("enableautopilot") == 0) {
/* 188 */       this.isEnableAutoPilot = toBool(data);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDirectoryName() {
/* 194 */     return "planes";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKindName() {
/* 199 */     return "plane";
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPostReload() {
/* 204 */     MCH_MOD.proxy.registerModelsPlane(this, true);
/*     */   }
/*     */   
/*     */   public static class Blade extends MCH_AircraftInfo.DrawnPart {
/*     */     public final int numBlade;
/*     */     public final int rotBlade;
/*     */     
/*     */     public Blade(MCP_PlaneInfo paramMCP_PlaneInfo, int num, int r, float px, float py, float pz, float rx, float ry, float rz, String name) {
/* 212 */       super(paramMCP_PlaneInfo, px, py, pz, rx, ry, rz, name);
/* 213 */       this.numBlade = num;
/* 214 */       this.rotBlade = r;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Pylon extends MCH_AircraftInfo.DrawnPart {
/*     */     public final float maxRotFactor;
/*     */     public final float maxRot;
/*     */     
/*     */     public Pylon(MCP_PlaneInfo paramMCP_PlaneInfo, float px, float py, float pz, float rx, float ry, float rz, float mr, String name) {
/* 223 */       super(paramMCP_PlaneInfo, px, py, pz, rx, ry, rz, name);
/* 224 */       this.maxRot = mr;
/* 225 */       this.maxRotFactor = this.maxRot / 90.0F;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Rotor extends MCH_AircraftInfo.DrawnPart {
/*     */     public final float maxRotFactor;
/* 231 */     public final List<MCP_PlaneInfo.Blade> blades = new ArrayList<>();
/*     */     
/*     */     public Rotor(MCP_PlaneInfo paramMCP_PlaneInfo, float x, float y, float z, float rx, float ry, float rz, float mrf, String model) {
/* 234 */       super(paramMCP_PlaneInfo, x, y, z, rx, ry, rz, model);
/* 235 */       this.maxRotFactor = mrf;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Wing extends MCH_AircraftInfo.DrawnPart {
/*     */     public final float maxRotFactor;
/*     */     public final float maxRot;
/*     */     public List<MCP_PlaneInfo.Pylon> pylonList;
/*     */     
/*     */     public Wing(MCP_PlaneInfo paramMCP_PlaneInfo, float px, float py, float pz, float rx, float ry, float rz, float mr, String name) {
/* 245 */       super(paramMCP_PlaneInfo, px, py, pz, rx, ry, rz, name);
/* 246 */       this.maxRot = mr;
/* 247 */       this.maxRotFactor = this.maxRot / 90.0F;
/* 248 */       this.pylonList = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\plane\MCP_PlaneInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */