package com.manzoli.quati.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class Permissoes {
  public static boolean validarPermissoes(String[] paramArrayOfString, Activity paramActivity, int paramInt) {
    if (Build.VERSION.SDK_INT >= 23) {
      ArrayList<String> arrayList = new ArrayList();
      int j = paramArrayOfString.length;
      for (int i = 0; i < j; i++) {
        boolean bool;
        String str = paramArrayOfString[i];
        if (ContextCompat.checkSelfPermission((Context)paramActivity, str) == 0) {
          bool = true;
        } else {
          bool = false;
        } 
        if (!Boolean.valueOf(bool).booleanValue())
          arrayList.add(str); 
      } 
      if (arrayList.isEmpty())
        return true; 
      paramArrayOfString = new String[arrayList.size()];
      arrayList.toArray(paramArrayOfString);
      ActivityCompat.requestPermissions(paramActivity, paramArrayOfString, paramInt);
    } 
    return true;
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\helper\Permissoes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */