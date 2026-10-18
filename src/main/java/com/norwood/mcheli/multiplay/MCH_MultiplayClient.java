/*     */ package com.norwood.mcheli.multiplay;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_FileSearch;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_OStream;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.zip.ZipEntry;
/*     */ import javax.imageio.ImageIO;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.fml.common.ModContainer;
/*     */ import net.minecraftforge.fml.relauncher.CoreModManager;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCH_MultiplayClient {
/*     */   private static IntBuffer pixelBuffer;
/*  33 */   private static List<String> modList = new ArrayList<>(); private static int[] pixelValues; private static MCH_OStream dataOutputStream;
/*     */   
/*     */   public static void startSendImageData() {
/*  36 */     Minecraft mc = Minecraft.getMinecraft();
/*  37 */     sendScreenShot(mc.displayWidth, mc.displayHeight, mc.getFramebuffer());
/*     */   }
/*     */   public static void sendScreenShot(int displayWidth, int displayHeight, Framebuffer framebufferMc) {
/*     */     try {
/*     */       BufferedImage bufferedimage;
/*  42 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*  43 */         displayWidth = framebufferMc.framebufferTextureWidth;
/*  44 */         displayHeight = framebufferMc.framebufferTextureHeight;
/*     */       } 
/*     */       
/*  47 */       int k = displayWidth * displayHeight;
/*  48 */       if (pixelBuffer == null || pixelBuffer.capacity() < k) {
/*  49 */         pixelBuffer = BufferUtils.createIntBuffer(k);
/*  50 */         pixelValues = new int[k];
/*     */       } 
/*     */       
/*  53 */       GL11.glPixelStorei(3333, 1);
/*  54 */       GL11.glPixelStorei(3317, 1);
/*  55 */       pixelBuffer.clear();
/*  56 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*  57 */         GL11.glBindTexture(3553, framebufferMc.framebufferTexture);
/*  58 */         GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
/*     */       } else {
/*  60 */         GL11.glReadPixels(0, 0, displayWidth, displayHeight, 32993, 33639, pixelBuffer);
/*     */       } 
/*     */       
/*  63 */       pixelBuffer.get(pixelValues);
/*  64 */       TextureUtil.processPixelValues(pixelValues, displayWidth, displayHeight);
/*     */       
/*  66 */       if (!OpenGlHelper.isFramebufferEnabled()) {
/*  67 */         bufferedimage = new BufferedImage(displayWidth, displayHeight, 1);
/*  68 */         bufferedimage.setRGB(0, 0, displayWidth, displayHeight, pixelValues, 0, displayWidth);
/*     */       } else {
/*  70 */         bufferedimage = new BufferedImage(framebufferMc.framebufferWidth, framebufferMc.framebufferHeight, 1);
/*  71 */         int l = framebufferMc.framebufferTextureHeight - framebufferMc.framebufferHeight;
/*     */         
/*  73 */         for (int i1 = l; i1 < framebufferMc.framebufferTextureHeight; i1++) {
/*  74 */           for (int j1 = 0; j1 < framebufferMc.framebufferWidth; j1++) {
/*  75 */             bufferedimage.setRGB(j1, i1 - l, pixelValues[i1 * framebufferMc.framebufferTextureWidth + j1]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  80 */       dataOutputStream = new MCH_OStream();
/*  81 */       ImageIO.write(bufferedimage, "png", (OutputStream)dataOutputStream);
/*  82 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static void readImageData(DataOutputStream dos) {
/*  87 */     dataOutputStream.write(dos);
/*     */   }
/*     */   
/*     */   public static void sendImageData() {
/*  91 */     if (dataOutputStream != null) {
/*  92 */       MCH_PacketLargeData.send();
/*  93 */       if (dataOutputStream.isDataEnd()) {
/*  94 */         dataOutputStream = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double getPerData() {
/* 100 */     return (dataOutputStream == null) ? -1.0D : (dataOutputStream.index / dataOutputStream.size());
/*     */   }
/*     */   
/*     */   public static void readModList(String playerName, String commandSenderName) {
/* 104 */     modList = new ArrayList<>();
/* 105 */     modList.add(TextFormatting.RED + "###### Name:" + commandSenderName + " ######");
/* 106 */     modList.add(TextFormatting.RED + "###### ID  :" + playerName + " ######");
/* 107 */     String[] classFileNameList = System.getProperty("java.class.path").split(File.pathSeparator);
/*     */     
/* 109 */     for (String classFileName : classFileNameList) {
/* 110 */       MCH_Lib.DbgLog(true, "java.class.path=" + classFileName, new Object[0]);
/* 111 */       if (classFileName.length() > 1) {
/* 112 */         File javaClassFile = new File(classFileName);
/* 113 */         if (javaClassFile.getAbsolutePath().toLowerCase().contains("versions")) {
/* 114 */           modList.add(TextFormatting.AQUA + "# Client class=" + javaClassFile.getName() + " : file size= " + javaClassFile.length());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     modList.add(TextFormatting.YELLOW + "=== ActiveModList ===");
/*     */     
/* 121 */     for (ModContainer mod : Loader.instance().getActiveModList()) {
/* 122 */       modList.add(mod + "  [" + mod.getModId() + "]  " + mod.getName() + "[" + mod.getDisplayVersion() + "]  " + mod.getSource().getName());
/*     */     }
/*     */     
/* 125 */     if (!CoreModManager.getAccessTransformers().isEmpty()) {
/* 126 */       modList.add(TextFormatting.YELLOW + "=== AccessTransformers ===");
/*     */       
/* 128 */       modList.addAll(CoreModManager.getAccessTransformers());
/*     */     } 
/*     */     
/* 131 */     if (!CoreModManager.getIgnoredMods().isEmpty()) {
/* 132 */       modList.add(TextFormatting.YELLOW + "=== LoadedCoremods ===");
/*     */       
/* 134 */       modList.addAll(CoreModManager.getIgnoredMods());
/*     */     } 
/*     */     
/* 137 */     if (!CoreModManager.getReparseableCoremods().isEmpty()) {
/* 138 */       modList.add(TextFormatting.YELLOW + "=== ReparseableCoremods ===");
/*     */       
/* 140 */       modList.addAll(CoreModManager.getReparseableCoremods());
/*     */     } 
/*     */     
/* 143 */     Minecraft mc = Minecraft.getMinecraft();
/* 144 */     MCH_FileSearch search = new MCH_FileSearch();
/* 145 */     File[] files = search.listFiles((new File(mc.mcDataDir, "mods")).getAbsolutePath(), "*.jar");
/* 146 */     modList.add(TextFormatting.YELLOW + "=== Manifest ===");
/*     */     
/* 148 */     for (File file : files) {
/*     */       try {
/* 150 */         String jarPath = file.getCanonicalPath();
/* 151 */         JarFile jarFile = new JarFile(jarPath);
/* 152 */         Enumeration<JarEntry> jarEntries = jarFile.entries();
/* 153 */         StringBuilder manifest = new StringBuilder();
/*     */         
/* 155 */         while (jarEntries.hasMoreElements()) {
/* 156 */           ZipEntry zipEntry = jarEntries.nextElement();
/* 157 */           if (zipEntry.getName().equalsIgnoreCase("META-INF/MANIFEST.MF") && !zipEntry.isDirectory()) {
/* 158 */             InputStream is = jarFile.getInputStream(zipEntry);
/* 159 */             BufferedReader br = new BufferedReader(new InputStreamReader(is));
/*     */             
/* 161 */             for (String line = br.readLine(); line != null; line = br.readLine()) {
/* 162 */               line = line.replace(" ", "").trim();
/* 163 */               if (!line.isEmpty()) {
/* 164 */                 manifest.append(" [").append(line).append("]");
/*     */               }
/*     */             } 
/*     */             
/* 168 */             is.close();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 173 */         jarFile.close();
/* 174 */         if (manifest.length() > 0) {
/* 175 */           modList.add(file.getName() + manifest);
/*     */         }
/* 177 */       } catch (Exception var20) {
/* 178 */         modList.add(file.getName() + " : Read Manifest failed.");
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     search = new MCH_FileSearch();
/* 183 */     files = search.listFiles((new File(mc.mcDataDir, "mods")).getAbsolutePath(), "*.litemod");
/* 184 */     modList.add(TextFormatting.LIGHT_PURPLE + "=== LiteLoader ===");
/*     */     
/* 186 */     for (File file : files) {
/*     */       try {
/* 188 */         String jarPath = file.getCanonicalPath();
/* 189 */         JarFile jarFile = new JarFile(jarPath);
/* 190 */         Enumeration<JarEntry> jarEntries = jarFile.entries();
/* 191 */         StringBuilder litemod_json = new StringBuilder();
/*     */         
/* 193 */         while (jarEntries.hasMoreElements()) {
/* 194 */           ZipEntry zipEntry = jarEntries.nextElement();
/* 195 */           String fname = zipEntry.getName().toLowerCase();
/* 196 */           if (!zipEntry.isDirectory()) {
/* 197 */             if (fname.equals("litemod.json")) {
/* 198 */               InputStream is = jarFile.getInputStream(zipEntry);
/* 199 */               BufferedReader br = new BufferedReader(new InputStreamReader(is));
/*     */               
/* 201 */               for (String linex = br.readLine(); linex != null; linex = br.readLine()) {
/* 202 */                 linex = linex.replace(" ", "").trim();
/* 203 */                 if (linex.toLowerCase().contains("name")) {
/* 204 */                   litemod_json.append(" [").append(linex).append("]");
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/* 209 */               is.close(); continue;
/*     */             } 
/* 211 */             int index = fname.lastIndexOf("/");
/* 212 */             if (index >= 0) {
/* 213 */               fname = fname.substring(index + 1);
/*     */             }
/*     */             
/* 216 */             if (fname.contains("litemod") && fname.endsWith("class")) {
/* 217 */               fname = zipEntry.getName();
/* 218 */               if (index >= 0) {
/* 219 */                 fname = fname.substring(index + 1);
/*     */               }
/*     */               
/* 222 */               litemod_json.append(" [").append(fname).append("]");
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 228 */         jarFile.close();
/* 229 */         if (litemod_json.length() > 0) {
/* 230 */           modList.add(file.getName() + litemod_json);
/*     */         }
/* 232 */       } catch (Exception var19) {
/* 233 */         modList.add(file.getName() + " : Read LiteLoader failed.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void sendModsInfo(String playerName, String commandSenderName, int id) {
/* 239 */     if (MCH_Config.DebugLog) {
/* 240 */       modList.clear();
/* 241 */       readModList(playerName, commandSenderName);
/*     */     } 
/*     */     
/* 244 */     MCH_PacketModList.send(modList, id);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\multiplay\MCH_MultiplayClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */