/*    */ package com.norwood.mcheli.debug;
/*    */ import com.norwood.mcheli.wrapper.W_ModelBase;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_ModelTest extends W_ModelBase {
/* 10 */   public final ModelRenderer test = new ModelRenderer((ModelBase)this, 0, 0);
/*    */   
/*    */   public MCH_ModelTest() {
/* 13 */     this.test.addBox(-5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F);
/*    */   }
/*    */   
/*    */   public void renderModel(double yaw, double pitch, float par7) {
/* 17 */     this.test.render(par7);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\debug\MCH_ModelTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */