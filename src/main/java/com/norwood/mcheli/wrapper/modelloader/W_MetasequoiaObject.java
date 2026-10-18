/*     */ package com.norwood.mcheli.wrapper.modelloader;
/*     */ 
/*     */ import com.norwood.mcheli.helper.client._ModelFormatException;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ public class W_MetasequoiaObject
/*     */   extends W_ModelCustom {
/*  23 */   public ArrayList<W_Vertex> vertices = new ArrayList<>();
/*  24 */   public final ArrayList<W_GroupObject> groupObjects = new ArrayList<>();
/*  25 */   private final W_GroupObject currentGroupObject = null;
/*     */   private final String fileName;
/*  27 */   private int vertexNum = 0;
/*  28 */   private int faceNum = 0;
/*     */   
/*     */   public W_MetasequoiaObject(ResourceLocation location, IResource resource) throws _ModelFormatException {
/*  31 */     this.fileName = resource.toString();
/*  32 */     loadObjModel(resource.getInputStream());
/*     */   }
/*     */   
/*     */   public W_MetasequoiaObject(ResourceLocation resource) throws _ModelFormatException {
/*  36 */     this.fileName = resource.toString();
/*     */     
/*     */     try {
/*  39 */       IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resource);
/*  40 */       loadObjModel(res.getInputStream());
/*  41 */     } catch (IOException var3) {
/*  42 */       throw new _ModelFormatException("IO Exception reading model format:" + this.fileName, var3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public W_MetasequoiaObject(String fileName, URL resource) throws _ModelFormatException {
/*  47 */     this.fileName = fileName;
/*     */     
/*     */     try {
/*  50 */       loadObjModel(resource.openStream());
/*  51 */     } catch (IOException var4) {
/*  52 */       throw new _ModelFormatException("IO Exception reading model format:" + this.fileName, var4);
/*     */     } 
/*     */   }
/*     */   
/*     */   public W_MetasequoiaObject(String filename, InputStream inputStream) throws _ModelFormatException {
/*  57 */     this.fileName = filename;
/*  58 */     loadObjModel(inputStream);
/*     */   }
/*     */   
/*     */   private static boolean isValidGroupObjectLine(String line) {
/*  62 */     String[] s = line.split(" ");
/*  63 */     return (s.length >= 2 && s[0].equals("Object") && s[1].length() >= 4 && s[1].charAt(0) == '"');
/*     */   }
/*     */   
/*     */   private static boolean isValidVertexLine(String line) {
/*  67 */     String[] s = line.split(" ");
/*  68 */     return s[0].equals("vertex");
/*     */   }
/*     */   
/*     */   private static boolean isValidFaceLine(String line) {
/*  72 */     String[] s = line.split(" ");
/*  73 */     return s[0].equals("face");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsPart(String partName) {
/*  78 */     for (W_GroupObject groupObject : this.groupObjects) {
/*  79 */       if (partName.equalsIgnoreCase(groupObject.name)) {
/*  80 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   private void loadObjModel(InputStream inputStream) throws _ModelFormatException {
/*  88 */     BufferedReader reader = null;
/*     */     
/*  90 */     int lineCount = 0;
/*     */     
/*     */     try {
/*  93 */       reader = new BufferedReader(new InputStreamReader(inputStream));
/*     */       String currentLine;
/*  95 */       while ((currentLine = reader.readLine()) != null) {
/*  96 */         lineCount++;
/*  97 */         currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*  98 */         if (isValidGroupObjectLine(currentLine)) {
/*  99 */           W_GroupObject group = parseGroupObject(currentLine, lineCount);
/* 100 */           if (group != null) {
/* 101 */             group.glDrawingMode = 4;
/* 102 */             this.vertices.clear();
/* 103 */             int vertexNum = 0;
/* 104 */             boolean mirror = false;
/* 105 */             double facet = Math.cos(0.785398163375D);
/* 106 */             boolean shading = false;
/*     */             
/* 108 */             while ((currentLine = reader.readLine()) != null) {
/* 109 */               lineCount++;
/* 110 */               currentLine = currentLine.replaceAll("\\s+", " ").trim();
/* 111 */               if (currentLine.equalsIgnoreCase("mirror 1")) {
/* 112 */                 mirror = true;
/*     */               }
/*     */               
/* 115 */               if (currentLine.equalsIgnoreCase("shading 1")) {
/* 116 */                 shading = true;
/*     */               }
/*     */               
/* 119 */               String[] s = currentLine.split(" ");
/* 120 */               if (s.length == 2 && s[0].equalsIgnoreCase("facet")) {
/* 121 */                 facet = Math.cos(Double.parseDouble(s[1]) * 3.1415926535D / 180.0D);
/*     */               }
/*     */               
/* 124 */               if (isValidVertexLine(currentLine)) {
/* 125 */                 vertexNum = Integer.parseInt(currentLine.split(" ")[1]);
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 130 */             if (vertexNum > 0) {
/* 131 */               while ((currentLine = reader.readLine()) != null) {
/* 132 */                 lineCount++;
/* 133 */                 currentLine = currentLine.replaceAll("\\s+", " ").trim();
/* 134 */                 String[] sx = currentLine.split(" ");
/* 135 */                 if (sx.length == 3) {
/* 136 */                   W_Vertex v = new W_Vertex(Float.parseFloat(sx[0]) / 100.0F, Float.parseFloat(sx[1]) / 100.0F, Float.parseFloat(sx[2]) / 100.0F);
/* 137 */                   checkMinMax(v);
/* 138 */                   this.vertices.add(v);
/* 139 */                   if (--vertexNum <= 0)
/*     */                     break;  continue;
/*     */                 } 
/* 142 */                 if (sx.length > 0) {
/* 143 */                   throw new _ModelFormatException("format error : " + this.fileName + " : line=" + lineCount);
/*     */                 }
/*     */               } 
/*     */               
/* 147 */               int faceNum = 0;
/*     */               
/* 149 */               while ((currentLine = reader.readLine()) != null) {
/* 150 */                 lineCount++;
/* 151 */                 currentLine = currentLine.replaceAll("\\s+", " ").trim();
/* 152 */                 if (isValidFaceLine(currentLine)) {
/* 153 */                   faceNum = Integer.parseInt(currentLine.split(" ")[1]);
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/* 158 */               if (faceNum > 0) {
/* 159 */                 while ((currentLine = reader.readLine()) != null) {
/* 160 */                   lineCount++;
/* 161 */                   currentLine = currentLine.replaceAll("\\s+", " ").trim();
/* 162 */                   String[] sx = currentLine.split(" ");
/* 163 */                   if (sx.length <= 2)
/*     */                     continue; 
/* 165 */                   if (Integer.parseInt(sx[0]) >= 3) {
/* 166 */                     W_Face[] faces = parseFace(currentLine, lineCount, mirror);
/*     */                     
/* 168 */                     Collections.addAll(group.faces, faces);
/*     */                   } 
/*     */                   
/* 171 */                   if (--faceNum <= 0) {
/*     */                     break;
/*     */                   }
/*     */                 } 
/*     */ 
/*     */                 
/* 177 */                 calcVerticesNormal(group, shading, facet);
/*     */               } 
/*     */             } 
/*     */             
/* 181 */             this.vertexNum += this.vertices.size();
/* 182 */             this.faceNum += group.faces.size();
/* 183 */             this.vertices.clear();
/* 184 */             this.groupObjects.add(group);
/*     */           } 
/*     */         } 
/*     */       } 
/* 188 */     } catch (IOException var28) {
/* 189 */       throw new _ModelFormatException("IO Exception reading model format : " + this.fileName, var28);
/*     */     } finally {
/* 191 */       checkMinMaxFinal();
/* 192 */       this.vertices = null;
/*     */       
/*     */       try {
/* 195 */         reader.close();
/* 196 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/*     */       try {
/* 200 */         inputStream.close();
/* 201 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void calcVerticesNormal(W_GroupObject group, boolean shading, double facet) {
/* 207 */     for (W_Face f : group.faces) {
/* 208 */       f.vertexNormals = new W_Vertex[f.verticesID.length];
/*     */       
/* 210 */       for (int i = 0; i < f.verticesID.length; i++) {
/* 211 */         W_Vertex vn = getVerticesNormalFromFace(f.faceNormal, f.verticesID[i], group, (float)facet);
/* 212 */         vn.normalize();
/* 213 */         if (shading) {
/* 214 */           if ((f.faceNormal.x * vn.x + f.faceNormal.y * vn.y + f.faceNormal.z * vn.z) >= facet) {
/* 215 */             f.vertexNormals[i] = vn;
/*     */           } else {
/* 217 */             f.vertexNormals[i] = f.faceNormal;
/*     */           } 
/*     */         } else {
/* 220 */           f.vertexNormals[i] = f.faceNormal;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public W_Vertex getVerticesNormalFromFace(W_Vertex faceNormal, int verticesID, W_GroupObject group, float facet) {
/* 227 */     W_Vertex v = new W_Vertex(0.0F, 0.0F, 0.0F);
/*     */     
/* 229 */     for (W_Face f : group.faces) {
/* 230 */       for (int id : f.verticesID) {
/* 231 */         if (id == verticesID) {
/* 232 */           if (f.faceNormal.x * faceNormal.x + f.faceNormal.y * faceNormal.y + f.faceNormal.z * faceNormal.z >= facet) {
/* 233 */             v.add(f.faceNormal);
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 240 */     v.normalize();
/* 241 */     return v;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAll() {
/* 246 */     Tessellator tessellator = Tessellator.getInstance();
/* 247 */     BufferBuilder builder = tessellator.getBuffer();
/* 248 */     builder.begin(4, DefaultVertexFormats.POSITION_TEX_NORMAL);
/*     */     
/* 250 */     tessellateAll(tessellator);
/* 251 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public void tessellateAll(Tessellator tessellator) {
/* 255 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 256 */       groupObject.render(tessellator);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderOnly(String... groupNames) {
/* 262 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 263 */       for (String groupName : groupNames) {
/* 264 */         if (groupName.equalsIgnoreCase(groupObject.name)) {
/* 265 */           groupObject.render();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tessellateOnly(Tessellator tessellator, String... groupNames) {
/* 272 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 273 */       for (String groupName : groupNames) {
/* 274 */         if (groupName.equalsIgnoreCase(groupObject.name)) {
/* 275 */           groupObject.render(tessellator);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderPart(String partName) {
/* 283 */     if (partName.charAt(0) == '$') {
/* 284 */       for (int i = 0; i < this.groupObjects.size(); i++) {
/* 285 */         W_GroupObject groupObject = this.groupObjects.get(i);
/* 286 */         if (partName.equalsIgnoreCase(groupObject.name)) {
/* 287 */           groupObject.render();
/* 288 */           i++;
/*     */           
/* 290 */           while (i < this.groupObjects.size()) {
/* 291 */             groupObject = this.groupObjects.get(i);
/* 292 */             if (groupObject.name.charAt(0) == '$') {
/*     */               break;
/*     */             }
/*     */             
/* 296 */             groupObject.render();
/* 297 */             i++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 302 */       for (W_GroupObject groupObject : this.groupObjects) {
/* 303 */         if (partName.equalsIgnoreCase(groupObject.name)) {
/* 304 */           groupObject.render();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tessellatePart(Tessellator tessellator, String partName) {
/* 311 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 312 */       if (partName.equalsIgnoreCase(groupObject.name)) {
/* 313 */         groupObject.render(tessellator);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAllExcept(String... excludedGroupNames) {
/* 320 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 321 */       boolean skipPart = false;
/*     */       
/* 323 */       for (String excludedGroupName : excludedGroupNames) {
/* 324 */         if (excludedGroupName.equalsIgnoreCase(groupObject.name)) {
/* 325 */           skipPart = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 330 */       if (!skipPart) {
/* 331 */         groupObject.render();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tessellateAllExcept(Tessellator tessellator, String... excludedGroupNames) {
/* 337 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 338 */       boolean exclude = false;
/*     */       
/* 340 */       for (String excludedGroupName : excludedGroupNames) {
/* 341 */         if (excludedGroupName.equalsIgnoreCase(groupObject.name)) {
/* 342 */           exclude = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 347 */       if (!exclude) {
/* 348 */         groupObject.render(tessellator);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private W_Face[] parseFace(String line, int lineCount, boolean mirror) {
/* 354 */     String[] s = line.split("[ VU)(M]+");
/* 355 */     int vnum = Integer.parseInt(s[0]);
/* 356 */     if (vnum != 3 && vnum != 4)
/* 357 */       return new W_Face[0]; 
/* 358 */     if (vnum == 3) {
/* 359 */       W_Face face = new W_Face();
/* 360 */       face.verticesID = new int[] { Integer.parseInt(s[3]), Integer.parseInt(s[2]), Integer.parseInt(s[1]) };
/* 361 */       face.vertices = new W_Vertex[] { this.vertices.get(face.verticesID[0]), this.vertices.get(face.verticesID[1]), this.vertices.get(face.verticesID[2]) };
/* 362 */       if (s.length >= 11) {
/* 363 */         face
/*     */ 
/*     */           
/* 366 */           .textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(Float.parseFloat(s[9]), Float.parseFloat(s[10])), new W_TextureCoordinate(Float.parseFloat(s[7]), Float.parseFloat(s[8])), new W_TextureCoordinate(Float.parseFloat(s[5]), Float.parseFloat(s[6])) };
/*     */       } else {
/*     */         
/* 369 */         face.textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(0.0F, 0.0F), new W_TextureCoordinate(0.0F, 0.0F), new W_TextureCoordinate(0.0F, 0.0F) };
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 374 */       face.faceNormal = face.calculateFaceNormal();
/* 375 */       return new W_Face[] { face };
/*     */     } 
/* 377 */     W_Face face1 = new W_Face();
/* 378 */     face1.verticesID = new int[] { Integer.parseInt(s[3]), Integer.parseInt(s[2]), Integer.parseInt(s[1]) };
/* 379 */     face1
/* 380 */       .vertices = new W_Vertex[] { this.vertices.get(face1.verticesID[0]), this.vertices.get(face1.verticesID[1]), this.vertices.get(face1.verticesID[2]) };
/*     */     
/* 382 */     if (s.length >= 12) {
/* 383 */       face1
/*     */ 
/*     */         
/* 386 */         .textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(Float.parseFloat(s[10]), Float.parseFloat(s[11])), new W_TextureCoordinate(Float.parseFloat(s[8]), Float.parseFloat(s[9])), new W_TextureCoordinate(Float.parseFloat(s[6]), Float.parseFloat(s[7])) };
/*     */     } else {
/*     */       
/* 389 */       face1.textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(0.0F, 0.0F), new W_TextureCoordinate(0.0F, 0.0F), new W_TextureCoordinate(0.0F, 0.0F) };
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 394 */     face1.faceNormal = face1.calculateFaceNormal();
/* 395 */     W_Face face2 = new W_Face();
/* 396 */     face2.verticesID = new int[] { Integer.parseInt(s[4]), Integer.parseInt(s[3]), Integer.parseInt(s[1]) };
/* 397 */     face2
/* 398 */       .vertices = new W_Vertex[] { this.vertices.get(face2.verticesID[0]), this.vertices.get(face2.verticesID[1]), this.vertices.get(face2.verticesID[2]) };
/*     */     
/* 400 */     if (s.length >= 14) {
/* 401 */       face2
/*     */ 
/*     */         
/* 404 */         .textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(Float.parseFloat(s[12]), Float.parseFloat(s[13])), new W_TextureCoordinate(Float.parseFloat(s[10]), Float.parseFloat(s[11])), new W_TextureCoordinate(Float.parseFloat(s[6]), Float.parseFloat(s[7])) };
/*     */     } else {
/*     */       
/* 407 */       face2.textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(0.0F, 0.0F), new W_TextureCoordinate(0.0F, 0.0F), new W_TextureCoordinate(0.0F, 0.0F) };
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 412 */     face2.faceNormal = face2.calculateFaceNormal();
/* 413 */     return new W_Face[] { face1, face2 };
/*     */   }
/*     */ 
/*     */   
/*     */   private W_GroupObject parseGroupObject(String line, int lineCount) throws _ModelFormatException {
/* 418 */     W_GroupObject group = null;
/* 419 */     if (isValidGroupObjectLine(line)) {
/* 420 */       String[] s = line.split(" ");
/* 421 */       String trimmedLine = s[1].substring(1, s[1].length() - 1);
/* 422 */       if (!trimmedLine.isEmpty()) {
/* 423 */         group = new W_GroupObject(trimmedLine);
/*     */       }
/*     */       
/* 426 */       return group;
/*     */     } 
/* 428 */     throw new _ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 434 */     return "mqo";
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAllLine(int startLine, int maxLine) {
/* 439 */     Tessellator tessellator = Tessellator.getInstance();
/* 440 */     BufferBuilder builder = tessellator.getBuffer();
/* 441 */     builder.begin(1, DefaultVertexFormats.POSITION);
/* 442 */     renderAllLine(tessellator, startLine, maxLine);
/* 443 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public void renderAllLine(Tessellator tessellator, int startLine, int maxLine) {
/* 447 */     int lineCnt = 0;
/* 448 */     BufferBuilder builder = tessellator.getBuffer();
/*     */     
/* 450 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 451 */       if (!groupObject.faces.isEmpty()) {
/* 452 */         for (W_Face face : groupObject.faces) {
/* 453 */           for (int i = 0; i < face.vertices.length / 3; i++) {
/* 454 */             W_Vertex v1 = face.vertices[i * 3];
/* 455 */             W_Vertex v2 = face.vertices[i * 3 + 1];
/* 456 */             W_Vertex v3 = face.vertices[i * 3 + 2];
/* 457 */             if (++lineCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 461 */             builder.pos(v1.x, v1.y, v1.z).endVertex();
/* 462 */             builder.pos(v2.x, v2.y, v2.z).endVertex();
/* 463 */             if (++lineCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 467 */             builder.pos(v2.x, v2.y, v2.z).endVertex();
/* 468 */             builder.pos(v3.x, v3.y, v3.z).endVertex();
/* 469 */             if (++lineCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 473 */             builder.pos(v3.x, v3.y, v3.z).endVertex();
/* 474 */             builder.pos(v1.x, v1.y, v1.z).endVertex();
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVertexNum() {
/* 483 */     return this.vertexNum;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFaceNum() {
/* 488 */     return this.faceNum;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderAll(int startFace, int maxFace) {
/* 493 */     if (startFace < 0) {
/* 494 */       startFace = 0;
/*     */     }
/*     */     
/* 497 */     Tessellator tessellator = Tessellator.getInstance();
/* 498 */     BufferBuilder builder = tessellator.getBuffer();
/* 499 */     builder.begin(4, DefaultVertexFormats.POSITION_TEX_NORMAL);
/* 500 */     renderAll(tessellator, startFace, maxFace);
/* 501 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public void renderAll(Tessellator tessellator, int startFace, int maxLine) {
/* 505 */     int faceCnt = 0;
/*     */     
/* 507 */     for (W_GroupObject groupObject : this.groupObjects) {
/* 508 */       if (!groupObject.faces.isEmpty())
/* 509 */         for (W_Face face : groupObject.faces) {
/* 510 */           if (++faceCnt >= startFace) {
/* 511 */             if (faceCnt > maxLine) {
/*     */               return;
/*     */             }
/*     */             
/* 515 */             face.addFaceForRender(tessellator);
/*     */           } 
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\wrapper\modelloader\W_MetasequoiaObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */