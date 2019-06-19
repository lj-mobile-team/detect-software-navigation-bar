
package com.reactlibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNDetectSoftwareNavigationBarModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNDetectSoftwareNavigationBarModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNDetectSoftwareNavigationBar";
  }

  @ReactMethod
  public void isSoftware(final Promise promise) {
    promise.resolve(getIsSoftwareMode());
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public boolean getIsSoftwareMode() {
    WindowManager windowManager = (WindowManager)reactContext.getSystemService(Context.WINDOW_SERVICE);

    boolean hasSoftwareKeys = true;

    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
      Display d = windowManager.getDefaultDisplay();

      DisplayMetrics realDisplayMetrics = new DisplayMetrics();
      d.getRealMetrics(realDisplayMetrics);

      int realHeight = realDisplayMetrics.heightPixels;
      int realWidth = realDisplayMetrics.widthPixels;

      DisplayMetrics displayMetrics = new DisplayMetrics();
      d.getMetrics(displayMetrics);

      int displayHeight = displayMetrics.heightPixels;
      int displayWidth = displayMetrics.widthPixels;

      hasSoftwareKeys =  (realWidth - displayWidth) > 0 ||
              (realHeight - displayHeight) > 0;
    } else {
      boolean hasMenuKey = ViewConfiguration.get(reactContext).hasPermanentMenuKey();
      boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
      hasSoftwareKeys = !hasMenuKey && !hasBackKey;
    }
    return hasSoftwareKeys;
  }
}