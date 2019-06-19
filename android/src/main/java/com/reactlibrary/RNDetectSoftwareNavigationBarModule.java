
package com.reactlibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNDetectSoftwareNavigationBarModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static boolean hasImmersive;
  private static boolean cached = false;

  public RNDetectSoftwareNavigationBarModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNDetectSoftwareNavigationBar";
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  @ReactMethod
  public boolean getIsSoftwareMode() {
    if (!cached) {
      if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
        hasImmersive = false;
        cached = true;
        return false;
      }
      Display d = ((WindowManager) reactContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

      DisplayMetrics realDisplayMetrics = new DisplayMetrics();
      d.getRealMetrics(realDisplayMetrics);

      int realHeight = realDisplayMetrics.heightPixels;
      int realWidth = realDisplayMetrics.widthPixels;

      DisplayMetrics displayMetrics = new DisplayMetrics();
      d.getMetrics(displayMetrics);

      int displayHeight = displayMetrics.heightPixels;
      int displayWidth = displayMetrics.widthPixels;

      hasImmersive = (realWidth > displayWidth) || (realHeight > displayHeight);
      cached = true;
    }

    return false;
  }
}