
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
import com.facebook.react.bridge.Callback;

import static android.content.Context.WINDOW_SERVICE;

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

  @ReactMethod(isBlockingSynchronousMethod = true)
  public void isSoftware(Callback callback) {
    callback.invoke(getIsSoftwareMode());
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public boolean getIsSoftwareMode() {
    WindowManager windowManager = (WindowManager)reactContext.getSystemService(Context.WINDOW_SERVICE);
    Display d = windowManager.getDefaultDisplay();

    DisplayMetrics realDisplayMetrics = new DisplayMetrics();
    d.getRealMetrics(realDisplayMetrics);

    int realHeight = realDisplayMetrics.heightPixels;
    int realWidth = realDisplayMetrics.widthPixels;

    DisplayMetrics displayMetrics = new DisplayMetrics();
    d.getMetrics(displayMetrics);

    int displayHeight = displayMetrics.heightPixels;
    int displayWidth = displayMetrics.widthPixels;

    return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
  }
}