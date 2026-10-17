/*    */ package com.norwood.mcheli.block;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Lib;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.helper.network.PacketHelper;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ 
/*    */ public class MCH_DraftingTableCreatePacket extends MCH_Packet {
/*    */   public IRecipe recipe;
/*    */   
/*    */   public static void send(IRecipe recipe) {
/* 17 */     if (recipe != null) {
/* 18 */       MCH_DraftingTableCreatePacket s = new MCH_DraftingTableCreatePacket();
/* 19 */       s.recipe = recipe;
/* 20 */       W_Network.sendToServer((W_PacketBase)s);
/* 21 */       MCH_Lib.DbgLog(true, "MCH_DraftingTableCreatePacket.send recipe = " + recipe.getRegistryName(), new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 27 */     return 537395216;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 33 */       this.recipe = PacketHelper.readRecipe(data);
/* 34 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 41 */       PacketHelper.writeRecipe(dos, this.recipe);
/* 42 */     } catch (IOException var3) {
/* 43 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\block\MCH_DraftingTableCreatePacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */