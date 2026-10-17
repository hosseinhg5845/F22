/*     */ package com.norwood.mcheli.debug._v1.model;
/*     */ 
/*     */ import com.norwood.mcheli.helper.debug.DebugException;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ public class MqoParser {
/*     */   public static MqoModel parse(InputStream inputStream) throws DebugException {
/*     */     MqoModel var44;
/*  15 */     List<_Vertex> vertices = new ArrayList<>();
/*  16 */     List<_Face> faceList = new ArrayList<>();
/*  17 */     List<_GroupObject> groupObjects = new ArrayList<>();
/*  18 */     int vertexNum = 0;
/*  19 */     int faceNum = 0;
/*  20 */     BufferedReader reader = null;
/*     */     
/*  22 */     int lineCount = 0;
/*     */ 
/*     */     
/*     */     try {
/*  26 */       reader = new BufferedReader(new InputStreamReader(inputStream));
/*     */       String currentLine;
/*  28 */       while ((currentLine = reader.readLine()) != null) {
/*  29 */         lineCount++;
/*  30 */         currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*  31 */         if (isValidGroupObjectLine(currentLine)) {
/*  32 */           _GroupObject.Builder group = parseGroupObject(currentLine, lineCount);
/*  33 */           if (group != null) {
/*  34 */             vertices.clear();
/*  35 */             faceList.clear();
/*  36 */             int vertexNum2 = 0;
/*  37 */             boolean mirror = false;
/*  38 */             double facet = Math.cos(0.785398163375D);
/*  39 */             boolean shading = false;
/*     */             
/*  41 */             while ((currentLine = reader.readLine()) != null) {
/*  42 */               lineCount++;
/*  43 */               currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*  44 */               if (currentLine.equalsIgnoreCase("mirror 1")) {
/*  45 */                 mirror = true;
/*     */               }
/*     */               
/*  48 */               if (currentLine.equalsIgnoreCase("shading 1")) {
/*  49 */                 shading = true;
/*     */               }
/*     */               
/*  52 */               String[] s = currentLine.split(" ");
/*  53 */               if (s.length == 2 && s[0].equalsIgnoreCase("facet")) {
/*  54 */                 facet = Math.cos(Double.parseDouble(s[1]) * 3.1415926535D / 180.0D);
/*     */               }
/*     */               
/*  57 */               if (isValidVertexLine(currentLine)) {
/*  58 */                 vertexNum2 = Integer.parseInt(currentLine.split(" ")[1]);
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*  63 */             if (vertexNum2 > 0) {
/*  64 */               while ((currentLine = reader.readLine()) != null) {
/*  65 */                 lineCount++;
/*  66 */                 currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*  67 */                 String[] sx = currentLine.split(" ");
/*  68 */                 if (sx.length == 3) {
/*  69 */                   _Vertex v = new _Vertex(Float.parseFloat(sx[0]) / 100.0F, Float.parseFloat(sx[1]) / 100.0F, Float.parseFloat(sx[2]) / 100.0F);
/*  70 */                   vertices.add(v);
/*  71 */                   if (--vertexNum2 <= 0)
/*     */                     break;  continue;
/*     */                 } 
/*  74 */                 if (sx.length > 0) {
/*  75 */                   throw new DebugException("format error : line=" + lineCount);
/*     */                 }
/*     */               } 
/*     */               
/*  79 */               int faceNum2 = 0;
/*     */               
/*  81 */               while ((currentLine = reader.readLine()) != null) {
/*  82 */                 lineCount++;
/*  83 */                 currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*  84 */                 if (isValidFaceLine(currentLine)) {
/*  85 */                   faceNum2 = Integer.parseInt(currentLine.split(" ")[1]);
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*  90 */               if (faceNum2 > 0) {
/*  91 */                 while ((currentLine = reader.readLine()) != null) {
/*  92 */                   lineCount++;
/*  93 */                   currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*  94 */                   String[] sx = currentLine.split(" ");
/*  95 */                   if (sx.length <= 2)
/*     */                     continue; 
/*  97 */                   if (Integer.parseInt(sx[0]) >= 3) {
/*  98 */                     _Face[] faces = parseFace(currentLine, lineCount, mirror, vertices);
/*     */                     
/* 100 */                     Collections.addAll(faceList, faces);
/*     */                   } 
/*     */                   
/* 103 */                   if (--faceNum2 <= 0) {
/*     */                     break;
/*     */                   }
/*     */                 } 
/*     */ 
/*     */                 
/* 109 */                 for (_Face face : faceList) {
/* 110 */                   group.addFace(face.calcVerticesNormal(faceList, shading, facet));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 115 */             vertexNum += vertices.size();
/* 116 */             faceNum += group.faceSize();
/* 117 */             vertices.clear();
/* 118 */             faceList.clear();
/* 119 */             groupObjects.add(group.build());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 124 */       var44 = new MqoModel(groupObjects, vertexNum, faceNum);
/* 125 */     } catch (IOException var32) {
/* 126 */       throw new DebugException("IO Exception reading model format.", var32);
/*     */     } finally {
/*     */       try {
/* 129 */         reader.close();
/* 130 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/*     */       try {
/* 134 */         inputStream.close();
/* 135 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */     
/* 139 */     return var44;
/*     */   }
/*     */   
/*     */   private static _GroupObject.Builder parseGroupObject(String line, int lineCount) throws DebugException {
/* 143 */     _GroupObject.Builder group = null;
/* 144 */     if (isValidGroupObjectLine(line)) {
/* 145 */       String[] s = line.split(" ");
/* 146 */       String trimmedLine = s[1].substring(1, s[1].length() - 1);
/* 147 */       if (!trimmedLine.isEmpty()) {
/* 148 */         group = _GroupObject.builder().name(trimmedLine);
/*     */       }
/*     */       
/* 151 */       return group;
/*     */     } 
/* 153 */     throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */   }
/*     */   
/*     */   private static _Face[] parseFace(String line, int lineCount, boolean mirror, List<_Vertex> vertices) {
/*     */     _TextureCoord[] texCoords1, texCoords2;
/* 158 */     String[] s = line.split("[ VU)(M]+");
/* 159 */     int vnum = Integer.parseInt(s[0]);
/* 160 */     if (vnum != 3 && vnum != 4)
/* 161 */       return new _Face[0]; 
/* 162 */     if (vnum == 3) {
/* 163 */       _TextureCoord[] texCoords; int[] verticesID = { Integer.parseInt(s[3]), Integer.parseInt(s[2]), Integer.parseInt(s[1]) };
/* 164 */       _Vertex[] verts = { vertices.get(verticesID[0]), vertices.get(verticesID[1]), vertices.get(verticesID[2]) };
/*     */       
/* 166 */       if (s.length >= 11) {
/*     */ 
/*     */ 
/*     */         
/* 170 */         texCoords = new _TextureCoord[] { new _TextureCoord(Float.parseFloat(s[9]), Float.parseFloat(s[10])), new _TextureCoord(Float.parseFloat(s[7]), Float.parseFloat(s[8])), new _TextureCoord(Float.parseFloat(s[5]), Float.parseFloat(s[6])) };
/*     */       } else {
/*     */         
/* 173 */         texCoords = new _TextureCoord[] { new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F) };
/*     */       } 
/*     */       
/* 176 */       return new _Face[] { new _Face(verticesID, verts, texCoords) };
/*     */     } 
/* 178 */     int[] verticesID1 = { Integer.parseInt(s[3]), Integer.parseInt(s[2]), Integer.parseInt(s[1]) };
/* 179 */     _Vertex[] verts1 = { vertices.get(verticesID1[0]), vertices.get(verticesID1[1]), vertices.get(verticesID1[2]) };
/*     */     
/* 181 */     if (s.length >= 12) {
/*     */ 
/*     */ 
/*     */       
/* 185 */       texCoords1 = new _TextureCoord[] { new _TextureCoord(Float.parseFloat(s[10]), Float.parseFloat(s[11])), new _TextureCoord(Float.parseFloat(s[8]), Float.parseFloat(s[9])), new _TextureCoord(Float.parseFloat(s[6]), Float.parseFloat(s[7])) };
/*     */     } else {
/*     */       
/* 188 */       texCoords1 = new _TextureCoord[] { new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F) };
/*     */     } 
/*     */     
/* 191 */     int[] verticesID2 = { Integer.parseInt(s[4]), Integer.parseInt(s[3]), Integer.parseInt(s[1]) };
/* 192 */     _Vertex[] verts2 = { vertices.get(verticesID2[0]), vertices.get(verticesID2[1]), vertices.get(verticesID2[2]) };
/*     */     
/* 194 */     if (s.length >= 14) {
/*     */ 
/*     */ 
/*     */       
/* 198 */       texCoords2 = new _TextureCoord[] { new _TextureCoord(Float.parseFloat(s[12]), Float.parseFloat(s[13])), new _TextureCoord(Float.parseFloat(s[10]), Float.parseFloat(s[11])), new _TextureCoord(Float.parseFloat(s[6]), Float.parseFloat(s[7])) };
/*     */     } else {
/*     */       
/* 201 */       texCoords2 = new _TextureCoord[] { new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F), new _TextureCoord(0.0F, 0.0F) };
/*     */     } 
/*     */     
/* 204 */     return new _Face[] { new _Face(verticesID1, verts1, texCoords1), new _Face(verticesID2, verts2, texCoords2) };
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidGroupObjectLine(String line) {
/* 209 */     String[] s = line.split(" ");
/* 210 */     return (s.length >= 2 && s[0].equals("Object") && s[1].length() >= 4 && s[1].charAt(0) == '"');
/*     */   }
/*     */   
/*     */   private static boolean isValidVertexLine(String line) {
/* 214 */     String[] s = line.split(" ");
/* 215 */     return s[0].equals("vertex");
/*     */   }
/*     */   
/*     */   private static boolean isValidFaceLine(String line) {
/* 219 */     String[] s = line.split(" ");
/* 220 */     return s[0].equals("face");
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v1\model\MqoParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */