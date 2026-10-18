/*     */ package com.norwood.mcheli.debug._v1.model;
/*     */ 
/*     */ import com.norwood.mcheli.helper.debug.DebugException;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class ObjParser
/*     */ {
/*  14 */   private static final Pattern vertexPattern = Pattern.compile("(v( (\\-){0,1}\\d+\\.\\d+){3,4} *\\n)|(v( (\\-){0,1}\\d+\\.\\d+){3,4} *$)");
/*  15 */   private static final Pattern vertexNormalPattern = Pattern.compile("(vn( (\\-){0,1}\\d+\\.\\d+){3,4} *\\n)|(vn( (\\-){0,1}\\d+\\.\\d+){3,4} *$)");
/*  16 */   private static final Pattern textureCoordinatePattern = Pattern.compile("(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *\\n)|(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *$)");
/*  17 */   private static final Pattern face_V_VT_VN_Pattern = Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)");
/*  18 */   private static final Pattern face_V_VT_Pattern = Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)");
/*  19 */   private static final Pattern face_V_VN_Pattern = Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)");
/*  20 */   private static final Pattern face_V_Pattern = Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)");
/*  21 */   private static final Pattern groupObjectPattern = Pattern.compile("([go]( [-\\$\\w\\d]+) *\\n)|([go]( [-\\$\\w\\d]+) *$)");
/*     */   public static ObjModel parse(InputStream inputStream) throws DebugException {
/*     */     ObjModel var31;
/*  24 */     List<_Vertex> vertices = new ArrayList<>();
/*  25 */     List<_TextureCoord> textureCoordinates = new ArrayList<>();
/*  26 */     List<_Vertex> vertexNormals = new ArrayList<>();
/*  27 */     List<_GroupObject> groupObjects = new ArrayList<>();
/*  28 */     _GroupObject.Builder group = null;
/*  29 */     int vertexNum = 0;
/*  30 */     int faceNum = 0;
/*  31 */     BufferedReader reader = null;
/*     */     
/*  33 */     int lineCount = 0;
/*     */ 
/*     */     
/*     */     try {
/*  37 */       reader = new BufferedReader(new InputStreamReader(inputStream));
/*     */       String currentLine;
/*  39 */       while ((currentLine = reader.readLine()) != null) {
/*  40 */         lineCount++;
/*  41 */         currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*  42 */         if (!currentLine.startsWith("#") && !currentLine.isEmpty()) {
/*  43 */           if (currentLine.startsWith("v ")) {
/*  44 */             _Vertex vertex = parseVertex(currentLine, lineCount);
/*  45 */             if (vertex != null)
/*  46 */               vertices.add(vertex);  continue;
/*     */           } 
/*  48 */           if (currentLine.startsWith("vn ")) {
/*  49 */             _Vertex vertex = parseVertexNormal(currentLine, lineCount);
/*  50 */             if (vertex != null)
/*  51 */               vertexNormals.add(vertex);  continue;
/*     */           } 
/*  53 */           if (currentLine.startsWith("vt ")) {
/*  54 */             _TextureCoord textureCoordinate = parseTextureCoordinate(currentLine, lineCount);
/*  55 */             if (textureCoordinate != null)
/*  56 */               textureCoordinates.add(textureCoordinate);  continue;
/*     */           } 
/*  58 */           if (currentLine.startsWith("f ")) {
/*  59 */             if (group == null) {
/*  60 */               group = _GroupObject.builder().name("Default");
/*     */             }
/*     */             
/*  63 */             _Face face = parseFace(currentLine, lineCount, vertices, textureCoordinates, vertexNormals);
/*  64 */             group.addFace(face); continue;
/*  65 */           }  if ((currentLine.startsWith("g ") || currentLine.startsWith("o ")) && currentLine.charAt(2) == '$') {
/*  66 */             _GroupObject.Builder group2 = parseGroupObject(currentLine, lineCount);
/*  67 */             if (group2 != null && group != null) {
/*  68 */               groupObjects.add(group.build());
/*     */             }
/*     */             
/*  71 */             group = group2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  76 */       groupObjects.add(group.build());
/*  77 */       var31 = new ObjModel(groupObjects, vertexNum, faceNum);
/*  78 */     } catch (IOException var23) {
/*  79 */       throw new DebugException("IO Exception reading model format", var23);
/*     */     } finally {
/*     */       try {
/*  82 */         reader.close();
/*  83 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/*     */       try {
/*  87 */         inputStream.close();
/*  88 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */     
/*  92 */     return var31;
/*     */   }
/*     */   
/*     */   private static _Vertex parseVertex(String line, int lineCount) throws DebugException {
/*  96 */     _Vertex vertex = null;
/*  97 */     if (isValidVertexLine(line)) {
/*  98 */       line = line.substring(line.indexOf(" ") + 1);
/*  99 */       String[] tokens = line.split(" ");
/*     */       
/*     */       try {
/* 102 */         if (tokens.length == 2) {
/* 103 */           return new _Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]));
/*     */         }
/* 105 */         return (tokens.length == 3) ? new _Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])) : vertex;
/*     */       }
/* 107 */       catch (NumberFormatException var5) {
/* 108 */         throw new DebugException(String.format("Number formatting error at line %d", new Object[] { Integer.valueOf(lineCount) }), var5);
/*     */       } 
/*     */     } 
/* 111 */     throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */   }
/*     */ 
/*     */   
/*     */   private static _Vertex parseVertexNormal(String line, int lineCount) throws DebugException {
/* 116 */     _Vertex vertexNormal = null;
/* 117 */     if (isValidVertexNormalLine(line)) {
/* 118 */       line = line.substring(line.indexOf(" ") + 1);
/* 119 */       String[] tokens = line.split(" ");
/*     */       
/*     */       try {
/* 122 */         return (tokens.length == 3) ? new _Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])) : vertexNormal;
/* 123 */       } catch (NumberFormatException var5) {
/* 124 */         throw new DebugException(String.format("Number formatting error at line %d", new Object[] { Integer.valueOf(lineCount) }), var5);
/*     */       } 
/*     */     } 
/* 127 */     throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */   }
/*     */ 
/*     */   
/*     */   private static _TextureCoord parseTextureCoordinate(String line, int lineCount) throws DebugException {
/* 132 */     _TextureCoord textureCoordinate = null;
/* 133 */     if (isValidTextureCoordinateLine(line)) {
/* 134 */       line = line.substring(line.indexOf(" ") + 1);
/* 135 */       String[] tokens = line.split(" ");
/*     */       
/*     */       try {
/* 138 */         if (tokens.length == 2) {
/* 139 */           return new _TextureCoord(Float.parseFloat(tokens[0]), 1.0F - Float.parseFloat(tokens[1]));
/*     */         }
/* 141 */         return (tokens.length == 3) ? 
/* 142 */           new _TextureCoord(Float.parseFloat(tokens[0]), 1.0F - Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])) : 
/* 143 */           textureCoordinate;
/*     */       }
/* 145 */       catch (NumberFormatException var5) {
/* 146 */         throw new DebugException(String.format("Number formatting error at line %d", new Object[] { Integer.valueOf(lineCount) }), var5);
/*     */       } 
/*     */     } 
/* 149 */     throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */   }
/*     */ 
/*     */   
/*     */   private static _Face parseFace(String line, int lineCount, List<_Vertex> vertices, List<_TextureCoord> textureCoordinates, List<_Vertex> vertexNormals) throws DebugException {
/*     */     _Face face;
/* 155 */     if (!isValidFaceLine(line)) {
/* 156 */       throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */     }
/* 158 */     String trimmedLine = line.substring(line.indexOf(" ") + 1);
/* 159 */     String[] tokens = trimmedLine.split(" ");
/*     */     
/* 161 */     int[] verticesID = new int[tokens.length];
/* 162 */     _Vertex[] verts = new _Vertex[tokens.length];
/* 163 */     _TextureCoord[] texCoords = new _TextureCoord[tokens.length];
/* 164 */     _Vertex[] normals = new _Vertex[tokens.length];
/* 165 */     if (isValidFace_V_VT_VN_Line(line)) {
/* 166 */       for (int i = 0; i < tokens.length; i++) {
/* 167 */         String[] subTokens = tokens[i].split("/");
/* 168 */         verticesID[i] = Integer.parseInt(subTokens[0]) - 1;
/* 169 */         verts[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 170 */         texCoords[i] = textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
/* 171 */         normals[i] = vertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
/*     */       } 
/*     */       
/* 174 */       face = new _Face(verticesID, verts, normals, texCoords);
/* 175 */     } else if (isValidFace_V_VT_Line(line)) {
/* 176 */       for (int i = 0; i < tokens.length; i++) {
/* 177 */         String[] subTokens = tokens[i].split("/");
/* 178 */         verticesID[i] = Integer.parseInt(subTokens[0]) - 1;
/* 179 */         verts[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 180 */         texCoords[i] = textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
/*     */       } 
/*     */       
/* 183 */       face = new _Face(verticesID, verts, new _Vertex[0], texCoords);
/* 184 */     } else if (isValidFace_V_VN_Line(line)) {
/* 185 */       for (int i = 0; i < tokens.length; i++) {
/* 186 */         String[] subTokens = tokens[i].split("//");
/* 187 */         verticesID[i] = Integer.parseInt(subTokens[0]) - 1;
/* 188 */         verts[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 189 */         normals[i] = vertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
/*     */       } 
/*     */       
/* 192 */       face = new _Face(verticesID, verts, normals, new _TextureCoord[0]);
/*     */     } else {
/* 194 */       if (!isValidFace_V_Line(line)) {
/* 195 */         throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */       }
/*     */       
/* 198 */       for (int i = 0; i < tokens.length; i++) {
/* 199 */         verticesID[i] = Integer.parseInt(tokens[0]) - 1;
/* 200 */         verts[i] = vertices.get(Integer.parseInt(tokens[0]) - 1);
/*     */       } 
/*     */       
/* 203 */       face = new _Face(verticesID, verts, new _Vertex[0], new _TextureCoord[0]);
/*     */     } 
/*     */     
/* 206 */     return face;
/*     */   }
/*     */ 
/*     */   
/*     */   private static _GroupObject.Builder parseGroupObject(String line, int lineCount) throws DebugException {
/* 211 */     _GroupObject.Builder group = null;
/* 212 */     if (isValidGroupObjectLine(line)) {
/* 213 */       String trimmedLine = line.substring(line.indexOf(" ") + 1);
/* 214 */       if (!trimmedLine.isEmpty()) {
/* 215 */         group = _GroupObject.builder().name(trimmedLine);
/*     */       }
/*     */       
/* 218 */       return group;
/*     */     } 
/* 220 */     throw new DebugException("Error parsing entry ('" + line + "', line " + lineCount + ") in file - Incorrect format");
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidVertexLine(String line) {
/* 225 */     return vertexPattern.matcher(line).matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidVertexNormalLine(String line) {
/* 229 */     return vertexNormalPattern.matcher(line).matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidTextureCoordinateLine(String line) {
/* 233 */     return textureCoordinatePattern.matcher(line).matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidFace_V_VT_VN_Line(String line) {
/* 237 */     return face_V_VT_VN_Pattern.matcher(line).matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidFace_V_VT_Line(String line) {
/* 241 */     return face_V_VT_Pattern.matcher(line).matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidFace_V_VN_Line(String line) {
/* 245 */     return face_V_VN_Pattern.matcher(line).matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidFace_V_Line(String line) {
/* 249 */     return face_V_Pattern.matcher(line).matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidFaceLine(String line) {
/* 253 */     return (isValidFace_V_VT_VN_Line(line) || isValidFace_V_VT_Line(line) || isValidFace_V_VN_Line(line) || isValidFace_V_Line(line));
/*     */   }
/*     */   
/*     */   private static boolean isValidGroupObjectLine(String line) {
/* 257 */     return groupObjectPattern.matcher(line).matches();
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\_v1\model\ObjParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */