/*    */ package com.norwood.mcheli.flare;
/*    */ import com.norwood.mcheli.wrapper.W_ModelBase;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_ModelFlare extends W_ModelBase {
/* 10 */   public final ModelRenderer model = (new ModelRenderer((ModelBase)this, 0, 0)).func_78787_b(4, 4);
/*    */   
/*    */   public MCH_ModelFlare() {
/* 13 */     this.model.func_78790_a(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
/*    */   }
/*    */   
/*    */   public void renderModel(double yaw, double pitch, float par7) {
/* 17 */     this.model.func_78785_a(par7);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\flare\MCH_ModelFlare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */