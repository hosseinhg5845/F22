/*     */ package com.norwood.mcheli.hud;
/*     */ 
/*     */ import com.norwood.mcheli.MCH_BaseInfo;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.aircraft.MCH_EntityAircraft;
/*     */ import com.norwood.mcheli.helper.MCH_Utils;
/*     */ import com.norwood.mcheli.helper.addon.AddonResourceLocation;
/*     */ import com.norwood.mcheli.helper.info.ContentParseException;
/*     */ import com.norwood.mcheli.wrapper.W_ScaledResolution;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ public class MCH_Hud
/*     */   extends MCH_BaseInfo
/*     */ {
/*  18 */   public static final MCH_Hud NoDisp = new MCH_Hud(MCH_Utils.buildinAddon("none"), "none");
/*     */   public final String name;
/*     */   public final String fileName;
/*     */   public boolean isWaitEndif;
/*     */   public boolean isIfFalse;
/*     */   public boolean exit;
/*     */   private final List<MCH_HudItem> list;
/*     */   private boolean isDrawing;
/*     */   
/*     */   public MCH_Hud(AddonResourceLocation location, String filePath) {
/*  28 */     super(location, filePath);
/*  29 */     this.name = location.func_110623_a();
/*  30 */     this.fileName = filePath;
/*  31 */     this.list = new ArrayList<>();
/*  32 */     this.isDrawing = false;
/*  33 */     this.isIfFalse = false;
/*  34 */     this.exit = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validate() {
/*  39 */     for (MCH_HudItem hud : this.list) {
/*  40 */       hud.parent = this;
/*     */     }
/*     */     
/*  43 */     if (this.isWaitEndif) {
/*  44 */       throw new RuntimeException("Endif not found!");
/*     */     }
/*  46 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadItemData(int fileLine, String item, String data) {
/*  51 */     String[] prm = data.split("\\s*,\\s*");
/*  52 */     if (prm.length != 0) {
/*  53 */       if (item.equalsIgnoreCase("If")) {
/*  54 */         if (this.isWaitEndif) {
/*  55 */           throw new RuntimeException("Endif not found!");
/*     */         }
/*     */         
/*  58 */         this.list.add(new MCH_HudItemConditional(fileLine, false, prm[0]));
/*  59 */         this.isWaitEndif = true;
/*  60 */       } else if (item.equalsIgnoreCase("Endif")) {
/*  61 */         if (!this.isWaitEndif) {
/*  62 */           throw new RuntimeException("IF in a pair can not be found!");
/*     */         }
/*     */         
/*  65 */         this.list.add(new MCH_HudItemConditional(fileLine, true, ""));
/*  66 */         this.isWaitEndif = false;
/*  67 */       } else if (item.equalsIgnoreCase("DrawString") || item.equalsIgnoreCase("DrawCenteredString")) {
/*  68 */         if (prm.length >= 3) {
/*  69 */           String s = prm[2];
/*  70 */           if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
/*  71 */             s = s.substring(1, s.length() - 1);
/*  72 */             this.list.add(new MCH_HudItemString(fileLine, prm[0], prm[1], s, prm, item.equalsIgnoreCase("DrawCenteredString")));
/*     */           } 
/*     */         } 
/*  75 */       } else if (item.equalsIgnoreCase("Exit")) {
/*  76 */         this.list.add(new MCH_HudItemExit(fileLine));
/*  77 */       } else if (item.equalsIgnoreCase("Color")) {
/*  78 */         if (prm.length == 1) {
/*  79 */           MCH_HudItemColor c = MCH_HudItemColor.createByParams(fileLine, new String[] { prm[0] });
/*  80 */           if (c != null) {
/*  81 */             this.list.add(c);
/*     */           }
/*  83 */         } else if (prm.length == 4) {
/*  84 */           String[] s = { prm[0], prm[1], prm[2], prm[3] };
/*  85 */           MCH_HudItemColor c = MCH_HudItemColor.createByParams(fileLine, s);
/*  86 */           if (c != null) {
/*  87 */             this.list.add(c);
/*     */           }
/*     */         } 
/*  90 */       } else if (item.equalsIgnoreCase("DrawTexture")) {
/*  91 */         if (prm.length >= 9 && prm.length <= 10) {
/*  92 */           String rot = (prm.length == 10) ? prm[9] : "0";
/*  93 */           this.list.add(new MCH_HudItemTexture(fileLine, prm[0], prm[1], prm[2], prm[3], prm[4], prm[5], prm[6], prm[7], prm[8], rot));
/*     */         } 
/*  95 */       } else if (item.equalsIgnoreCase("DrawRect")) {
/*  96 */         if (prm.length == 4) {
/*  97 */           this.list.add(new MCH_HudItemRect(fileLine, prm[0], prm[1], prm[2], prm[3]));
/*     */         }
/*  99 */       } else if (item.equalsIgnoreCase("DrawLine")) {
/* 100 */         int len = prm.length;
/* 101 */         if (len >= 4 && len % 2 == 0) {
/* 102 */           this.list.add(new MCH_HudItemLine(fileLine, prm));
/*     */         }
/* 104 */       } else if (item.equalsIgnoreCase("DrawLineStipple")) {
/* 105 */         int len = prm.length;
/* 106 */         if (len >= 6 && len % 2 == 0) {
/* 107 */           this.list.add(new MCH_HudItemLineStipple(fileLine, prm));
/*     */         }
/* 109 */       } else if (item.equalsIgnoreCase("Call")) {
/* 110 */         int len = prm.length;
/* 111 */         if (len == 1) {
/* 112 */           this.list.add(new MCH_HudItemCall(fileLine, prm[0]));
/*     */         }
/* 114 */       } else if (item.equalsIgnoreCase("DrawEntityRadar") || item.equalsIgnoreCase("DrawEnemyRadar")) {
/* 115 */         if (prm.length == 5) {
/* 116 */           this.list.add(new MCH_HudItemRadar(fileLine, item.equalsIgnoreCase("DrawEntityRadar"), prm[0], prm[1], prm[2], prm[3], prm[4]));
/*     */         }
/* 118 */       } else if (item.equalsIgnoreCase("DrawGraduationYaw") || item
/* 119 */         .equalsIgnoreCase("DrawGraduationPitch1") || item
/* 120 */         .equalsIgnoreCase("DrawGraduationPitch2") || item
/* 121 */         .equalsIgnoreCase("DrawGraduationPitch3")) {
/* 122 */         if (prm.length == 4) {
/* 123 */           int type = -1;
/* 124 */           if (item.equalsIgnoreCase("DrawGraduationYaw")) {
/* 125 */             type = 0;
/*     */           }
/*     */           
/* 128 */           if (item.equalsIgnoreCase("DrawGraduationPitch1")) {
/* 129 */             type = 1;
/*     */           }
/*     */           
/* 132 */           if (item.equalsIgnoreCase("DrawGraduationPitch2")) {
/* 133 */             type = 2;
/*     */           }
/*     */           
/* 136 */           if (item.equalsIgnoreCase("DrawGraduationPitch3")) {
/* 137 */             type = 3;
/*     */           }
/*     */           
/* 140 */           this.list.add(new MCH_HudItemGraduation(fileLine, type, prm[0], prm[1], prm[2], prm[3]));
/*     */         } 
/* 142 */       } else if (item.equalsIgnoreCase("DrawCameraRot") && prm.length == 2) {
/* 143 */         this.list.add(new MCH_HudItemCameraRot(fileLine, prm[0], prm[1]));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void draw(MCH_EntityAircraft ac, EntityPlayer player, float partialTicks) {
/* 149 */     if (MCH_HudItem.mc == null) {
/* 150 */       MCH_HudItem.mc = Minecraft.func_71410_x();
/*     */     }
/*     */     
/* 153 */     MCH_HudItem.ac = ac;
/* 154 */     MCH_HudItem.player = player;
/* 155 */     MCH_HudItem.partialTicks = partialTicks;
/* 156 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(MCH_HudItem.mc, MCH_HudItem.mc.field_71443_c, MCH_HudItem.mc.field_71440_d);
/* 157 */     MCH_HudItem.scaleFactor = w_ScaledResolution.func_78325_e();
/* 158 */     if (MCH_HudItem.scaleFactor <= 0) {
/* 159 */       MCH_HudItem.scaleFactor = 1;
/*     */     }
/*     */     
/* 162 */     MCH_HudItem.width = MCH_HudItem.mc.field_71443_c / MCH_HudItem.scaleFactor;
/* 163 */     MCH_HudItem.height = MCH_HudItem.mc.field_71440_d / MCH_HudItem.scaleFactor;
/* 164 */     MCH_HudItem.centerX = MCH_HudItem.width / 2.0D;
/* 165 */     MCH_HudItem.centerY = MCH_HudItem.height / 2.0D;
/* 166 */     this.isIfFalse = false;
/* 167 */     this.isDrawing = false;
/* 168 */     this.exit = false;
/* 169 */     if (ac != null && ac.getAcInfo() != null && player != null) {
/* 170 */       MCH_HudItem.update();
/* 171 */       drawItems();
/* 172 */       MCH_HudItem.drawVarMap();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawItems() {
/* 177 */     if (!this.isDrawing) {
/* 178 */       this.isDrawing = true;
/*     */       
/* 180 */       for (MCH_HudItem hud : this.list) {
/* 181 */         int line = -1;
/*     */         
/*     */         try {
/* 184 */           line = hud.fileLine;
/* 185 */           if (hud.canExecute()) {
/* 186 */             hud.execute();
/* 187 */             if (this.exit) {
/*     */               break;
/*     */             }
/*     */           } 
/* 191 */         } catch (Exception var5) {
/* 192 */           MCH_Lib.Log("#### Draw HUD Error!!!: line=%d, file=%s", new Object[] { Integer.valueOf(line), this.fileName });
/* 193 */           var5.printStackTrace();
/* 194 */           throw new RuntimeException(var5);
/*     */         } 
/*     */       } 
/*     */       
/* 198 */       this.exit = false;
/* 199 */       this.isIfFalse = false;
/* 200 */       this.isDrawing = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void parse(List<String> lines, String fileExtension, boolean reload) {
/* 206 */     if ("txt".equals(fileExtension)) {
/* 207 */       int line = 0;
/*     */       
/*     */       try {
/* 210 */         for (String str : lines) {
/* 211 */           line++;
/* 212 */           str = str.trim();
/* 213 */           if (str.equalsIgnoreCase("endif")) {
/* 214 */             str = "endif=0";
/*     */           }
/*     */           
/* 217 */           if (str.equalsIgnoreCase("exit")) {
/* 218 */             str = "exit=0";
/*     */           }
/*     */           
/* 221 */           int eqIdx = str.indexOf('=');
/* 222 */           if (eqIdx >= 0 && str.length() > eqIdx + 1) {
/* 223 */             loadItemData(line, str.substring(0, eqIdx).trim().toLowerCase(), str.substring(eqIdx + 1).trim());
/*     */           }
/*     */         } 
/* 226 */       } catch (Exception var8) {
/* 227 */         throw new ContentParseException(var8, line);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\hud\MCH_Hud.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */