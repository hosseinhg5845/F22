package com.norwood.mcheli.helper.info;

import com.norwood.mcheli.helper.addon.AddonResourceLocation;
import javax.annotation.Nullable;

public interface IContentFactory {
  @Nullable
  IContentData create(AddonResourceLocation paramAddonResourceLocation, String paramString);
  
  ContentType getType();
}


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\info\IContentFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */