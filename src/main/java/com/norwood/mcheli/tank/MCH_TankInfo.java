/*    */ package com.norwood.mcheli.tank;
/*    */ 
/*    */ import com.norwood.mcheli.MCH_Config;
/*    */ import com.norwood.mcheli.MCH_MOD;
/*    */ import com.norwood.mcheli.aircraft.MCH_AircraftInfo;
/*    */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class MCH_TankInfo
/*    */   extends MCH_AircraftInfo {
/* 14 */   public MCH_ItemTank item = null;
/* 15 */   public int weightType = 0;
/* 16 */   public float weightedCenterZ = 0.0F;
/*    */   
/*    */   public MCH_TankInfo(AddonResourceLocation location, String path) {
/* 19 */     super(location, path);
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getItem() {
/* 24 */     return (Item)this.item;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<MCH_AircraftInfo.Wheel> getDefaultWheelList() {
/* 29 */     List<MCH_AircraftInfo.Wheel> list = new ArrayList<>();
/* 30 */     list.add(new MCH_AircraftInfo.Wheel(this, new Vec3d(1.5D, -0.24D, 2.0D)));
/* 31 */     list.add(new MCH_AircraftInfo.Wheel(this, new Vec3d(1.5D, -0.24D, -2.0D)));
/* 32 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDefaultSoundRange() {
/* 37 */     return 50.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDefaultRotorSpeed() {
/* 42 */     return 47.94F;
/*    */   }
/*    */   
/*    */   private float getDefaultStepHeight() {
/* 46 */     return 0.6F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getMaxSpeed() {
/* 51 */     return 1.8F;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDefaultMaxZoom() {
/* 56 */     return 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDefaultHudName(int seatId) {
/* 61 */     if (seatId <= 0) {
/* 62 */       return "tank";
/*    */     }
/* 64 */     return (seatId == 1) ? "tank" : "gunner";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validate() throws Exception {
/* 70 */     this.speed = (float)(this.speed * MCH_Config.AllTankSpeed.prmDouble);
/* 71 */     return super.validate();
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadItemData(String item, String data) {
/* 76 */     super.loadItemData(item, data);
/* 77 */     if (item.equalsIgnoreCase("WeightType")) {
/* 78 */       data = data.toLowerCase();
/* 79 */       this.weightType = data.equals("car") ? 1 : (data.equals("tank") ? 2 : 0);
/* 80 */     } else if (item.equalsIgnoreCase("WeightedCenterZ")) {
/* 81 */       this.weightedCenterZ = toFloat(data, -1000.0F, 1000.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDirectoryName() {
/* 87 */     return "tanks";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getKindName() {
/* 92 */     return "tank";
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPostReload() {
/* 97 */     MCH_MOD.proxy.registerModelsTank(this, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\tank\MCH_TankInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */