/*     */ package com.norwood.mcheli.wrapper.modelloader;
/*     */ 
/*     */ import com.norwood.mcheli.helper.client._ModelFormatException;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class W_WavefrontObject
/*     */   extends W_ModelCustom {
/*  24 */   private static final Pattern vertexPattern = Pattern.compile("(v( (\\-){0,1}\\d+\\.\\d+){3,4} *\\n)|(v( (\\-){0,1}\\d+\\.\\d+){3,4} *$)");
/*  25 */   private static final Pattern vertexNormalPattern = Pattern.compile("(vn( (\\-){0,1}\\d+\\.\\d+){3,4} *\\n)|(vn( (\\-){0,1}\\d+\\.\\d+){3,4} *$)");
/*  26 */   private static final Pattern textureCoordinatePattern = Pattern.compile("(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *\\n)|(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *$)");
/*  27 */   private static final Pattern face_V_VT_VN_Pattern = Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)");
/*  28 */   private static final Pattern face_V_VT_Pattern = Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)");
/*  29 */   private static final Pattern face_V_VN_Pattern = Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)");
/*  30 */   private static final Pattern face_V_Pattern = Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)");
/*  31 */   private static final Pattern groupObjectPattern = Pattern.compile("([go]( [-\\$\\w\\d]+) *\\n)|([go]( [-\\$\\w\\d]+) *$)");
/*     */   private static Matcher vertexMatcher;
/*     */   private static Matcher vertexNormalMatcher;
/*     */   private static Matcher textureCoordinateMatcher;
/*     */   private static Matcher face_V_VT_VN_Matcher;
/*     */   private static Matcher face_V_VT_Matcher;
/*     */   private static Matcher face_V_VN_Matcher;
/*     */   private static Matcher face_V_Matcher;
/*     */   private static Matcher groupObjectMatcher;
/*  40 */   public final ArrayList<W_Vertex> vertices = new ArrayList<>();
/*  41 */   public final ArrayList<W_Vertex> vertexNormals = new ArrayList<>();
/*  42 */   public final ArrayList<W_TextureCoordinate> textureCoordinates = new ArrayList<>();
/*  43 */   public final ArrayList<W_GroupObject> groupObjects = new ArrayList<>();
/*     */   private W_GroupObject currentGroupObject;
/*     */   private final String fileName;
/*     */   
/*     */   public W_WavefrontObject(ResourceLocation location, IResource resource) throws _ModelFormatException {
/*  48 */     this.fileName = resource.toString();
/*  49 */     loadObjModel(resource.getInputStream());
/*     */   }
/*     */   
/*     */   public W_WavefrontObject(ResourceLocation resource) throws _ModelFormatException {
/*  53 */     this.fileName = resource.toString();
/*     */     
/*     */     try {
/*  56 */       IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resource);
/*  57 */       loadObjModel(res.getInputStream());
/*  58 */     } catch (IOException var3) {
/*  59 */       throw new _ModelFormatException("IO Exception reading model format", var3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public W_WavefrontObject(String fileName, URL resource) throws _ModelFormatException {
/*  64 */     this.fileName = fileName;
/*     */     
/*     */     try {
/*  67 */       loadObjModel(resource.openStream());
/*  68 */     } catch (IOException var4) {
/*  69 */       throw new _ModelFormatException("IO Exception reading model format", var4);
/*     */     } 
/*     */   }
/*     */   
/*     */   public W_WavefrontObject(String filename, InputStream inputStream) throws _ModelFormatException {
/*  74 */     this.fileName = filename;
/*  75 */     loadObjModel(inputStream);
/*     */   }
/*     */   
/*     */   private static boolean isValidVertexLine(String line) {
/*  79 */     if (vertexMatcher != null) {
/*  80 */       vertexMatcher.reset();
/*     */     }
/*     */     
/*  83 */     vertexMatcher = vertexPattern.matcher(line);
/*  84 */     return vertexMatcher.matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidVertexNormalLine(String line) {
/*  88 */     if (vertexNormalMatcher != null) {
/*  89 */       vertexNormalMatcher.reset();
/*     */     }
/*     */     
/*  92 */     vertexNormalMatcher = vertexNormalPattern.matcher(line);
/*  93 */     return vertexNormalMatcher.matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidTextureCoordinateLine(String line) {
/*  97 */     if (textureCoordinateMatcher != null) {
/*  98 */       textureCoordinateMatcher.reset();
/*     */     }
/*     */     
/* 101 */     textureCoordinateMatcher = textureCoordinatePattern.matcher(line);
/* 102 */     return textureCoordinateMatcher.matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidFace_V_VT_VN_Line(String line) {
/* 106 */     if (face_V_VT_VN_Matcher != null) {
/* 107 */       face_V_VT_VN_Matcher.reset();
/*     */     }
/*     */     
/* 110 */     face_V_VT_VN_Matcher = face_V_VT_VN_Pattern.matcher(line);
/* 111 */     return face_V_VT_VN_Matcher.matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidFace_V_VT_Line(String line) {
/* 115 */     if (face_V_VT_Matcher != null) {
/* 116 */       face_V_VT_Matcher.reset();
/*     */     }
/*     */     
/* 119 */     face_V_VT_Matcher = face_V_VT_Pattern.matcher(line);
/* 120 */     return face_V_VT_Matcher.matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidFace_V_VN_Line(String line) {
/* 124 */     if (face_V_VN_Matcher != null) {
/* 125 */       face_V_VN_Matcher.reset();
/*     */     }
/*     */     
/* 128 */     face_V_VN_Matcher = face_V_VN_Pattern.matcher(line);
/* 129 */     return face_V_VN_Matcher.matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidFace_V_Line(String line) {
/* 133 */     if (face_V_Matcher != null) {
/* 134 */       face_V_Matcher.reset();
/*     */     }
/*     */     
/* 137 */     face_V_Matcher = face_V_Pattern.matcher(line);
/* 138 */     return face_V_Matcher.matches();
/*     */   }
/*     */   
/*     */   private static boolean isValidFaceLine(String line) {
/* 142 */     return (isValidFace_V_VT_VN_Line(line) || isValidFace_V_VT_Line(line) || isValidFace_V_VN_Line(line) || isValidFace_V_Line(line));
/*     */   }
/*     */   
/*     */   private static boolean isValidGroupObjectLine(String line) {
/* 146 */     if (groupObjectMatcher != null) {
/* 147 */       groupObjectMatcher.reset();
/*     */     }
/*     */     
/* 150 */     groupObjectMatcher = groupObjectPattern.matcher(line);
/* 151 */     return groupObjectMatcher.matches();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsPart(String partName) {
/* 156 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 157 */       if (partName.equalsIgnoreCase(groupObject.name)) {
/* 158 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 162 */     return false;
/*     */   }
/*     */   
/*     */   private void loadObjModel(InputStream inputStream) throws _ModelFormatException {
/* 166 */     BufferedReader reader = null;
/*     */     
/* 168 */     int lineCount = 0;
/*     */     
/*     */     try {
/* 171 */       reader = new BufferedReader(new InputStreamReader(inputStream));
/*     */       String currentLine;
/* 173 */       while ((currentLine = reader.readLine()) != null) {
/* 174 */         lineCount++;
/* 175 */         currentLine = currentLine.replaceAll("\\s+", " ").trim();
/* 176 */         if (!currentLine.startsWith("#") && !currentLine.isEmpty()) {
/* 177 */           if (currentLine.startsWith("v ")) {
/* 178 */             W_Vertex vertex = parseVertex(currentLine, lineCount);
/* 179 */             if (vertex != null) {
/* 180 */               checkMinMax(vertex);
/* 181 */               this.vertices.add(vertex);
/*     */             }  continue;
/* 183 */           }  if (currentLine.startsWith("vn ")) {
/* 184 */             W_Vertex vertex = parseVertexNormal(currentLine, lineCount);
/* 185 */             if (vertex != null)
/* 186 */               this.vertexNormals.add(vertex);  continue;
/*     */           } 
/* 188 */           if (currentLine.startsWith("vt ")) {
/* 189 */             W_TextureCoordinate textureCoordinate = parseTextureCoordinate(currentLine, lineCount);
/* 190 */             if (textureCoordinate != null)
/* 191 */               this.textureCoordinates.add(textureCoordinate);  continue;
/*     */           } 
/* 193 */           if (currentLine.startsWith("f ")) {
/* 194 */             if (this.currentGroupObject == null) {
/* 195 */               this.currentGroupObject = new W_GroupObject("Default");
/*     */             }
/*     */             
/* 198 */             W_Face face = parseFace(currentLine, lineCount);
/* 199 */             this.currentGroupObject.faces.add(face); continue;
/* 200 */           }  if ((currentLine.startsWith("g ") || currentLine.startsWith("o ")) && currentLine.charAt(2) == '$') {
/* 201 */             W_GroupObject group = parseGroupObject(currentLine, lineCount);
/* 202 */             if (group != null && this.currentGroupObject != null) {
/* 203 */               this.groupObjects.add(this.currentGroupObject);
/*     */             }
/*     */             
/* 206 */             this.currentGroupObject = group;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 211 */       this.groupObjects.add(this.currentGroupObject);
/* 212 */     } catch (IOException var16) {
/* 213 */       throw new _ModelFormatException("IO Exception reading model format", var16);
/*     */     } finally {
/* 215 */       checkMinMaxFinal();
/*     */       
/*     */       try {
/* 218 */         reader.close();
/* 219 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/*     */       try {
/* 223 */         inputStream.close();
/* 224 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAll() {
/* 231 */     Tessellator tessellator = Tessellator.getInstance();
/* 232 */     BufferBuilder builder = tessellator.getBuffer();
/* 233 */     if (this.currentGroupObject != null) {
/* 234 */       builder.begin(this.currentGroupObject.glDrawingMode, DefaultVertexFormats.POSITION_TEX_NORMAL);
/*     */     } else {
/* 236 */       builder.begin(4, DefaultVertexFormats.POSITION_TEX_NORMAL);
/*     */     } 
/*     */     
/* 239 */     tessellateAll(tessellator);
/* 240 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public void tessellateAll(Tessellator tessellator) {
/* 244 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 245 */       groupObject.render(tessellator);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderOnly(String... groupNames) {
/* 251 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 252 */       for (String groupName : groupNames) {
/* 253 */         if (groupName.equalsIgnoreCase(groupObject.name)) {
/* 254 */           groupObject.render();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tessellateOnly(Tessellator tessellator, String... groupNames) {
/* 261 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 262 */       for (String groupName : groupNames) {
/* 263 */         if (groupName.equalsIgnoreCase(groupObject.name)) {
/* 264 */           groupObject.render(tessellator);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderPart(String partName) {
/* 272 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 273 */       if (partName.equalsIgnoreCase(groupObject.name)) {
/* 274 */         groupObject.render();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tessellatePart(Tessellator tessellator, String partName) {
/* 280 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 281 */       if (partName.equalsIgnoreCase(groupObject.name)) {
/* 282 */         groupObject.render(tessellator);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAllExcept(String... excludedGroupNames) {
/* 289 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 290 */       boolean skipPart = false;
/*     */       
/* 292 */       for (String excludedGroupName : excludedGroupNames) {
/* 293 */         if (excludedGroupName.equalsIgnoreCase(groupObject.name)) {
/* 294 */           skipPart = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 299 */       if (!skipPart) {
/* 300 */         groupObject.render();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tessellateAllExcept(Tessellator tessellator, String... excludedGroupNames) {
/* 306 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 307 */       boolean exclude = false;
/*     */       
/* 309 */       for (String excludedGroupName : excludedGroupNames) {
/* 310 */         if (excludedGroupName.equalsIgnoreCase(groupObject.name)) {
/* 311 */           exclude = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 316 */       if (!exclude) {
/* 317 */         groupObject.render(tessellator);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private W_Vertex parseVertex(String line, int lineCount) throws _ModelFormatException {
/* 323 */     W_Vertex vertex = null;
/* 324 */     if (isValidVertexLine(line)) {
/* 325 */       line = line.substring(line.indexOf(" ") + 1);
/* 326 */       String[] tokens = line.split(" ");
/*     */       
/*     */       try {
/* 329 */         if (tokens.length == 2) {
/* 330 */           return new W_Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]));
/*     */         }
/* 332 */         return (tokens.length == 3) ? new W_Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])) : vertex;
/*     */       }
/* 334 */       catch (NumberFormatException var6) {
/* 335 */         throw new _ModelFormatException(String.format("Number formatting error at line %d", new Object[] { Integer.valueOf(lineCount) }), var6);
/*     */       } 
/*     */     } 
/* 338 */     throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */   }
/*     */ 
/*     */   
/*     */   private W_Vertex parseVertexNormal(String line, int lineCount) throws _ModelFormatException {
/* 343 */     W_Vertex vertexNormal = null;
/* 344 */     if (isValidVertexNormalLine(line)) {
/* 345 */       line = line.substring(line.indexOf(" ") + 1);
/* 346 */       String[] tokens = line.split(" ");
/*     */       
/*     */       try {
/* 349 */         return (tokens.length == 3) ? new W_Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])) : vertexNormal;
/* 350 */       } catch (NumberFormatException var6) {
/* 351 */         throw new _ModelFormatException(String.format("Number formatting error at line %d", new Object[] { Integer.valueOf(lineCount) }), var6);
/*     */       } 
/*     */     } 
/* 354 */     throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */   }
/*     */ 
/*     */   
/*     */   private W_TextureCoordinate parseTextureCoordinate(String line, int lineCount) throws _ModelFormatException {
/* 359 */     W_TextureCoordinate textureCoordinate = null;
/* 360 */     if (isValidTextureCoordinateLine(line)) {
/* 361 */       line = line.substring(line.indexOf(" ") + 1);
/* 362 */       String[] tokens = line.split(" ");
/*     */       
/*     */       try {
/* 365 */         if (tokens.length == 2) {
/* 366 */           return new W_TextureCoordinate(Float.parseFloat(tokens[0]), 1.0F - Float.parseFloat(tokens[1]));
/*     */         }
/* 368 */         return (tokens.length == 3) ? 
/* 369 */           new W_TextureCoordinate(Float.parseFloat(tokens[0]), 1.0F - Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])) : 
/* 370 */           textureCoordinate;
/*     */       }
/* 372 */       catch (NumberFormatException var6) {
/* 373 */         throw new _ModelFormatException(String.format("Number formatting error at line %d", new Object[] { Integer.valueOf(lineCount) }), var6);
/*     */       } 
/*     */     } 
/* 376 */     throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private W_Face parseFace(String line, int lineCount) throws _ModelFormatException {
/* 382 */     if (isValidFaceLine(line)) {
/* 383 */       W_Face face = new W_Face();
/* 384 */       String trimmedLine = line.substring(line.indexOf(" ") + 1);
/* 385 */       String[] tokens = trimmedLine.split(" ");
/*     */       
/* 387 */       if (tokens.length == 3) {
/* 388 */         if (this.currentGroupObject.glDrawingMode == -1) {
/* 389 */           this.currentGroupObject.glDrawingMode = 4;
/* 390 */         } else if (this.currentGroupObject.glDrawingMode != 4) {
/* 391 */           throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Invalid number of points for face (expected 4, found " + tokens.length + ")");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 403 */       else if (tokens.length == 4) {
/* 404 */         if (this.currentGroupObject.glDrawingMode == -1) {
/* 405 */           this.currentGroupObject.glDrawingMode = 7;
/* 406 */         } else if (this.currentGroupObject.glDrawingMode != 7) {
/* 407 */           throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Invalid number of points for face (expected 3, found " + tokens.length + ")");
/*     */         } 
/*     */       } 
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
/* 421 */       if (isValidFace_V_VT_VN_Line(line)) {
/* 422 */         face.vertices = new W_Vertex[tokens.length];
/* 423 */         face.textureCoordinates = new W_TextureCoordinate[tokens.length];
/* 424 */         face.vertexNormals = new W_Vertex[tokens.length];
/*     */         
/* 426 */         for (int i = 0; i < tokens.length; i++) {
/* 427 */           String[] subTokens = tokens[i].split("/");
/* 428 */           face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 429 */           face.textureCoordinates[i] = this.textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
/* 430 */           face.vertexNormals[i] = this.vertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
/*     */         } 
/*     */         
/* 433 */         face.faceNormal = face.calculateFaceNormal();
/* 434 */       } else if (isValidFace_V_VT_Line(line)) {
/* 435 */         face.vertices = new W_Vertex[tokens.length];
/* 436 */         face.textureCoordinates = new W_TextureCoordinate[tokens.length];
/*     */         
/* 438 */         for (int i = 0; i < tokens.length; i++) {
/* 439 */           String[] subTokens = tokens[i].split("/");
/* 440 */           face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 441 */           face.textureCoordinates[i] = this.textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
/*     */         } 
/*     */         
/* 444 */         face.faceNormal = face.calculateFaceNormal();
/* 445 */       } else if (isValidFace_V_VN_Line(line)) {
/* 446 */         face.vertices = new W_Vertex[tokens.length];
/* 447 */         face.vertexNormals = new W_Vertex[tokens.length];
/*     */         
/* 449 */         for (int i = 0; i < tokens.length; i++) {
/* 450 */           String[] subTokens = tokens[i].split("//");
/* 451 */           face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 452 */           face.vertexNormals[i] = this.vertexNormals.get(Integer.parseInt(subTokens[1]) - 1);
/*     */         } 
/*     */         
/* 455 */         face.faceNormal = face.calculateFaceNormal();
/*     */       } else {
/* 457 */         if (!isValidFace_V_Line(line)) {
/* 458 */           throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 463 */         face.vertices = new W_Vertex[tokens.length];
/*     */         
/* 465 */         for (int i = 0; i < tokens.length; i++) {
/* 466 */           face.vertices[i] = this.vertices.get(Integer.parseInt(tokens[i]) - 1);
/*     */         }
/*     */         
/* 469 */         face.faceNormal = face.calculateFaceNormal();
/*     */       } 
/*     */       
/* 472 */       return face;
/*     */     } 
/* 474 */     throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */   }
/*     */ 
/*     */   
/*     */   private W_GroupObject parseGroupObject(String line, int lineCount) throws _ModelFormatException {
/* 479 */     W_GroupObject group = null;
/* 480 */     if (isValidGroupObjectLine(line)) {
/* 481 */       String trimmedLine = line.substring(line.indexOf(" ") + 1);
/* 482 */       if (!trimmedLine.isEmpty()) {
/* 483 */         group = new W_GroupObject(trimmedLine);
/*     */       }
/*     */       
/* 486 */       return group;
/*     */     } 
/* 488 */     throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 494 */     return "obj";
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAllLine(int startLine, int maxLine) {
/* 499 */     Tessellator tessellator = Tessellator.getInstance();
/* 500 */     BufferBuilder builder = tessellator.getBuffer();
/* 501 */     builder.begin(1, DefaultVertexFormats.POSITION);
/* 502 */     renderAllLine(tessellator, startLine, maxLine);
/* 503 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public void renderAllLine(Tessellator tessellator, int startLine, int maxLine) {
/* 507 */     int lineCnt = 0;
/* 508 */     BufferBuilder builder = tessellator.getBuffer();
/*     */     
/* 510 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 511 */       if (!groupObject.faces.isEmpty()) {
/* 512 */         for (W_Face face : groupObject.faces) {
/* 513 */           for (int i = 0; i < face.vertices.length / 3; i++) {
/* 514 */             W_Vertex v1 = face.vertices[i * 3];
/* 515 */             W_Vertex v2 = face.vertices[i * 3 + 1];
/* 516 */             W_Vertex v3 = face.vertices[i * 3 + 2];
/* 517 */             if (++lineCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 521 */             builder.pos(v1.x, v1.y, v1.z).endVertex();
/* 522 */             builder.pos(v2.x, v2.y, v2.z).endVertex();
/* 523 */             if (++lineCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 527 */             builder.pos(v2.x, v2.y, v2.z).endVertex();
/* 528 */             builder.pos(v3.x, v3.y, v3.z).endVertex();
/* 529 */             if (++lineCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 533 */             builder.pos(v3.x, v3.y, v3.z).endVertex();
/* 534 */             builder.pos(v1.x, v1.y, v1.z).endVertex();
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVertexNum() {
/* 543 */     return this.vertices.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFaceNum() {
/* 548 */     return getVertexNum() / 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAll(int startFace, int maxFace) {
/* 553 */     if (startFace < 0) {
/* 554 */       startFace = 0;
/*     */     }
/*     */     
/* 557 */     Tessellator tessellator = Tessellator.getInstance();
/* 558 */     BufferBuilder builder = tessellator.getBuffer();
/* 559 */     builder.begin(4, DefaultVertexFormats.POSITION_TEX_NORMAL);
/* 560 */     renderAll(tessellator, startFace, maxFace);
/* 561 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public void renderAll(Tessellator tessellator, int startFace, int maxLine) {
/* 565 */     int faceCnt = 0;
/*     */     
/* 567 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 568 */       if (!groupObject.faces.isEmpty())
/* 569 */         for (W_Face face : groupObject.faces) {
/* 570 */           if (++faceCnt >= startFace) {
/* 571 */             if (faceCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 575 */             face.addFaceForRender(tessellator);
/*     */           } 
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\modelloader\W_WavefrontObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */