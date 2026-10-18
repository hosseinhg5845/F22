/*    */ package com.norwood.mcheli.command;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.norwood.mcheli.MCH_Packet;
/*    */ import com.norwood.mcheli.wrapper.W_Network;
/*    */ import com.norwood.mcheli.wrapper.W_PacketBase;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ public class MCH_PacketTitle
/*    */   extends MCH_Packet {
/* 13 */   public ITextComponent chatComponent = null;
/* 14 */   public int showTime = 1;
/* 15 */   public int position = 0;
/*    */   
/*    */   public static void send(ITextComponent chat, int showTime, int pos) {
/* 18 */     MCH_PacketTitle s = new MCH_PacketTitle();
/* 19 */     s.chatComponent = chat;
/* 20 */     s.showTime = showTime;
/* 21 */     s.position = pos;
/* 22 */     W_Network.sendToAllPlayers((W_PacketBase)s);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 27 */     return 268438272;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 33 */       this.chatComponent = ITextComponent.Serializer.jsonToComponent(data.readUTF());
/* 34 */       this.showTime = data.readShort();
/* 35 */       this.position = data.readShort();
/* 36 */     } catch (Exception var3) {
/* 37 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 44 */       dos.writeUTF(ITextComponent.Serializer.componentToJson(this.chatComponent));
/* 45 */       dos.writeShort(this.showTime);
/* 46 */       dos.writeShort(this.position);
/* 47 */     } catch (IOException var3) {
/* 48 */       var3.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\command\MCH_PacketTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */