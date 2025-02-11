
package com.reactlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class RNDetectSoftwareNavigationBarModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

  private ReactApplicationContext mReactContext;

  public RNDetectSoftwareNavigationBarModule(ReactApplicationContext reactContext) {
    super(reactContext);
    mReactContext = reactContext;
    mReactContext.addLifecycleEventListener(this);
  }

  @Override
  public String getName() {
    return "RNDetectSoftwareNavigationBar";
  }

  @Override
  public void onHostDestroy() {

  }

  @Override
  public void onHostPause() {

  }

  @Override
  public void onHostResume() {

  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants =  new HashMap<>();

    final Context ctx = getReactApplicationContext();
    final DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();


    if (Build.VERSION.SDK_INT >= 17) {
      Display display = ((WindowManager) mReactContext.getSystemService(Context.WINDOW_SERVICE))
              .getDefaultDisplay();
      try {
        Display.class.getMethod("getRealMetrics", DisplayMetrics.class).invoke(display, metrics);
      } catch (InvocationTargetException e) {
      } catch (IllegalAccessException e) {
      } catch (NoSuchMethodException e) {
      }
    }

    constants.put("REAL_WINDOW_HEIGHT", getRealHeight());
    constants.put("REAL_WINDOW_WIDTH", getRealWidth());
    constants.put("STATUS_BAR_HEIGHT", getStatusBarHeight());
    constants.put("SOFT_MENU_BAR_HEIGHT", getSoftMenuBarHeight());
    constants.put("SMART_BAR_HEIGHT", getSmartBarHeight());
    constants.put("SOFT_MENU_BAR_ENABLED", hasPermanentMenuKey());
    constants.put("IS_EMULATOR", isEmulator());

    return constants;
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @ReactMethod
  public void setNavBackgroundColor(final String color) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (color != null) {
          int newColor = Color.parseColor(color);
          getCurrentActivity().getWindow().setNavigationBarColor(newColor);
        }
      }
    });
  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
  private boolean hasPermanentMenuKey() {
    final Context ctx = getReactApplicationContext();

    if(isEmulator()) {
      boolean hasPermanentMenuKey = ViewConfiguration.get(ctx).hasPermanentMenuKey();
      return hasPermanentMenuKey;
    } else {
      WindowManager windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
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

  @ReactMethod(isBlockingSynchronousMethod = true)
  public float getStatusBarHeight() {
    final Context ctx = getReactApplicationContext();
    final DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
    final int heightResId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
    return
            heightResId > 0
                    ? ctx.getResources().getDimensionPixelSize(heightResId) / metrics.density
                    : 0;
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  public float getSoftMenuBarHeight() {
    final Context ctx = getReactApplicationContext();
    final DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
    final int heightResId = ctx.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
    return
            heightResId > 0
                    ? ctx.getResources().getDimensionPixelSize(heightResId) / metrics.density
                    : 0;
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  public float getRealHeight() {
    final Context ctx = getReactApplicationContext();
    final DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();

    return metrics.heightPixels / metrics.density;
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  public float getRealWidth() {
    final Context ctx = getReactApplicationContext();
    final DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();

    return metrics.widthPixels / metrics.density;
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  public float getSmartBarHeight() {
    final Context context = getReactApplicationContext();
    final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    final boolean isMeiZu = Build.MANUFACTURER.equals("Meizu");

    final boolean autoHideSmartBar = Settings.System.getInt(context.getContentResolver(),
            "mz_smartbar_auto_hide", 0) == 1;

    if (!isMeiZu || autoHideSmartBar) {
      return 0;
    }
    try {
      Class c = Class.forName("com.android.internal.R$dimen");
      Object obj = c.newInstance();
      Field field = c.getField("mz_action_button_min_height");
      int height = Integer.parseInt(field.get(obj).toString());
      return context.getResources().getDimensionPixelSize(height) / metrics.density;
    } catch (Throwable e) {
      return getNormalNavigationBarHeight(context) / metrics.density;
    }
  }

  protected static float getNormalNavigationBarHeight(final Context ctx) {
    try {
      final Resources res = ctx.getResources();
      int rid = res.getIdentifier("config_showNavigationBar", "bool", "android");
      if (rid > 0) {
        boolean flag = res.getBoolean(rid);
        if (flag) {
          int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
          if (resourceId > 0) {
            return res.getDimensionPixelSize(resourceId);
          }
        }
      }
    } catch (Throwable e) {
      return 0;
    }
    return 0;
  }

  public static boolean isEmulator() {
    return Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
            || "google_sdk".equals(Build.PRODUCT);
  }
}