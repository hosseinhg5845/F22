package com.norwood.mcheli.helper.info;

import com.norwood.mcheli.helper.addon.AddonResourceLocation;
import java.util.List;

public interface IContentData {
  void parse(List<String> paramList, String paramString, boolean paramBoolean);
  
  boolean validate() throws Exception;
  
  void onPostReload();
  
  AddonResourceLocation getLoation();
  
  String getContentPath();
}


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\info\IContentData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */