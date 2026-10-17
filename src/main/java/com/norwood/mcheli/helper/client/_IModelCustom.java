package com.norwood.mcheli.helper.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface _IModelCustom {
  String getType();
  
  @SideOnly(Side.CLIENT)
  void renderAll();
  
  @SideOnly(Side.CLIENT)
  void renderOnly(String... paramVarArgs);
  
  @SideOnly(Side.CLIENT)
  void renderPart(String paramString);
  
  @SideOnly(Side.CLIENT)
  void renderAllExcept(String... paramVarArgs);
}


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\client\_IModelCustom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */