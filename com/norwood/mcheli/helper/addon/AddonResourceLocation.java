/*     */ package com.norwood.mcheli.helper.addon;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.norwood.mcheli.helper.MCH_Utils;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class AddonResourceLocation
/*     */   extends ResourceLocation {
/*  11 */   public static final AddonResourceLocation EMPTY_LOCATION = new AddonResourceLocation();
/*     */   public static final String SHARE_DOMAIN = "<!mcheli_share_domain>";
/*     */   public static final char SEPARATOR = '|';
/*     */   private final String addonDomain;
/*     */   private final boolean isEmpty;
/*     */   
/*     */   private AddonResourceLocation() {
/*  18 */     super(0, new String[] { "empty", "empty" });
/*  19 */     this.addonDomain = "@empty";
/*  20 */     this.isEmpty = true;
/*     */   }
/*     */   
/*     */   protected AddonResourceLocation(int unused, String... resourceName) {
/*  24 */     super(unused, new String[] { resourceName[0], resourceName[2] });
/*  25 */     this.addonDomain = resourceName[1];
/*  26 */     this.isEmpty = false;
/*     */   }
/*     */   
/*     */   public AddonResourceLocation(String resourceName) {
/*  30 */     this(0, parsePath(resourceName));
/*     */   }
/*     */   
/*     */   public AddonResourceLocation(String resourceName, String addonDomainIn) {
/*  34 */     this(0, parsePath(addonDomainIn, resourceName));
/*     */   }
/*     */   
/*     */   public AddonResourceLocation(ResourceLocation resourceLocation, String addonDomainIn) {
/*  38 */     this(resourceLocation.toString(), addonDomainIn);
/*     */   }
/*     */   
/*     */   public AddonResourceLocation(String resourceDomainIn, String addonDomainIn, String resourcePathIn) {
/*  42 */     this(0, parsePath(resourceDomainIn + ":" + addonDomainIn + '|' + resourcePathIn));
/*     */   }
/*     */   
/*     */   protected static String[] parsePath(String pathIn) {
/*  46 */     String[] spl = func_177516_a(pathIn);
/*  47 */     String[] ret = { spl[0], null, spl[1] };
/*  48 */     int i = ret[2].indexOf('|');
/*  49 */     if (i >= 0) {
/*  50 */       ret[1] = ret[2].substring(0, i);
/*  51 */       ret[2] = ret[2].substring(i + 1);
/*     */     } 
/*     */     
/*  54 */     ret[1] = Strings.isNullOrEmpty(ret[1]) ? "<!mcheli_share_domain>" : ret[1].toLowerCase(Locale.ROOT);
/*  55 */     if (ret[0].equals("minecraft")) {
/*  56 */       ret[0] = "mcheli";
/*  57 */     } else if (!spl[0].equals("mcheli")) {
/*  58 */       MCH_Utils.logger().warn("Invalid mod domain '{}', replace at '{}'. path:'{}'", ret[0], "mcheli", ret[2]);
/*  59 */       ret[0] = "mcheli";
/*     */     } 
/*     */     
/*  62 */     return ret;
/*     */   }
/*     */   
/*     */   protected static String[] parsePath(String addonDomain, String pathIn) {
/*  66 */     String[] spl = func_177516_a(pathIn);
/*  67 */     return new String[] { spl[0], addonDomain, spl[1] };
/*     */   }
/*     */   
/*     */   public static AddonResourceLocation share(ResourceLocation location) {
/*  71 */     return share(location.func_110624_b(), location.func_110623_a());
/*     */   }
/*     */   
/*     */   public static AddonResourceLocation share(String modid, String location) {
/*  75 */     return new AddonResourceLocation(modid, "<!mcheli_share_domain>", location);
/*     */   }
/*     */   
/*     */   public String getAddonDomain() {
/*  79 */     return this.addonDomain;
/*     */   }
/*     */   
/*     */   public String getAddonLocation() {
/*  83 */     return this.addonDomain + '|' + this.field_110625_b;
/*     */   }
/*     */   
/*     */   public ResourceLocation asVanillaLocation() {
/*  87 */     return new ResourceLocation(this.field_110626_a, getAddonLocation());
/*     */   }
/*     */   
/*     */   public ResourceLocation asVanillaDomainPath() {
/*  91 */     return new ResourceLocation(this.field_110626_a, this.field_110625_b);
/*     */   }
/*     */   
/*     */   public boolean isShareDomain() {
/*  95 */     return this.addonDomain.equals("<!mcheli_share_domain>");
/*     */   }
/*     */   
/*     */   public boolean isEmptyLocation() {
/*  99 */     return (this.isEmpty || equals(EMPTY_LOCATION));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 103 */     return 31 * (31 * super.hashCode() + this.addonDomain.hashCode()) + Boolean.hashCode(this.isEmpty);
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 107 */     if (this == p_equals_1_)
/* 108 */       return true; 
/* 109 */     if (p_equals_1_ instanceof AddonResourceLocation) { AddonResourceLocation location = (AddonResourceLocation)p_equals_1_; if (super.equals(p_equals_1_))
/* 110 */         return (location.addonDomain.equals(this.addonDomain) && location.isEmpty == this.isEmpty);  }
/*     */     
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsIgnore(ResourceLocation location) {
/* 117 */     if (this == location)
/* 118 */       return true; 
/* 119 */     if (super.equals(location)) {
/* 120 */       if (isShareDomain())
/* 121 */         return true; 
/* 122 */       if (location instanceof AddonResourceLocation) { AddonResourceLocation other = (AddonResourceLocation)location;
/* 123 */         return (other.isShareDomain() || other.addonDomain.equals(this.addonDomain)); }
/*     */       
/* 125 */       return false;
/*     */     } 
/*     */     
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ResourceLocation p_compareTo_1_) {
/* 133 */     int i = this.field_110626_a.compareTo(p_compareTo_1_.func_110624_b());
/* 134 */     if (i == 0 && p_compareTo_1_ instanceof AddonResourceLocation) {
/* 135 */       i = this.addonDomain.compareTo(((AddonResourceLocation)p_compareTo_1_).addonDomain);
/*     */     }
/*     */     
/* 138 */     if (i == 0) {
/* 139 */       i = this.field_110625_b.compareTo(p_compareTo_1_.func_110623_a());
/*     */     }
/*     */     
/* 142 */     return i;
/*     */   }
/*     */   @NotNull
/*     */   public String toString() {
/* 146 */     return this.field_110626_a + ":" + this.addonDomain + '|' + this.field_110625_b;
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\addon\AddonResourceLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */