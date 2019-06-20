
package com.reactlibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
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
import com.facebook.react.bridge.ReadableMap;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

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

//  @ReactMethod
//  public void isSoftware(final Promise promise) {
//    promise.resolve(getIsSoftwareMode());
//  }

  @ReactMethod
  public void getHeight(final Promise promise) {
    Point point = getNavigationBarSize(this.reactContext);
    Map res = null;
    res.put("width", point.x);
    res.put("height", point.y);
    promise.resolve(res);
  }

//  public int getSystemHeight() {
//    Resources resources = reactContext.getResources();
//    int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//    if (resourceId > 0) {
//      return resources.getDimensionPixelSize(resourceId);
//    }
//    return 0;
//  }

  public static Point getNavigationBarSize(Context context) {
    Point appUsableSize = getAppUsableScreenSize(context);
    Point realScreenSize = getRealScreenSize(context);

    if (appUsableSize.x < realScreenSize.x) {
      return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
    }

    if (appUsableSize.y < realScreenSize.y) {
      return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
    }

    return new Point();
  }

  public static Point getAppUsableScreenSize(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size;
  }

  public static Point getRealScreenSize(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    Point size = new Point();

    if (Build.VERSION.SDK_INT >= 17) {
      display.getRealSize(size);
    } else if (Build.VERSION.SDK_INT >= 14) {
      try {
        size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
        size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
      } catch (IllegalAccessException e) {} catch (InvocationTargetException e) {} catch (NoSuchMethodException e) {}
    }

    return size;
  }
}