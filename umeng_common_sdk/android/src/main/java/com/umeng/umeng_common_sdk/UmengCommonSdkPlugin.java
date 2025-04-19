package com.umeng.umeng_common_sdk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** UmengCommonSdkPlugin */
public class UmengCommonSdkPlugin implements FlutterPlugin, MethodCallHandler {
  private MethodChannel channel;
  private Context applicationContext;
  private static Boolean versionMatch = false;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    applicationContext = flutterPluginBinding.getApplicationContext();
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "umeng_common_sdk");
    channel.setMethodCallHandler(this);
    onAttachedEngineAdd();
  }

  private void onAttachedEngineAdd() {
    // add by umeng
    try {
      Class<?> agent = Class.forName("com.umeng.analytics.MobclickAgent");
      Method[] methods = agent.getDeclaredMethods();
      for (Method m : methods) {
        Log.e("UMLog", "Reflect:" + m);
        if (m.getName().equals("onEventObject")) {
          versionMatch = true;
          break;
        }
      }
      if (!versionMatch) {
        Log.e("UMLog", "安卓SDK版本过低，建议升级至8以上");
      } else {
        Log.e("UMLog", "安卓依赖版本检查成功");
      }
    } catch (Exception e) {
      e.printStackTrace();
      Log.e("UMLog", "SDK版本过低，请升级至8以上" + e.toString());
      return;
    }

    try {
      Class<?> config = Class.forName("com.umeng.commonsdk.UMConfigure");
      Method method = config.getDeclaredMethod("setWraperType", String.class, String.class);
      method.setAccessible(true);
      method.invoke(null, "flutter", "1.0");
      Log.i("UMLog", "setWraperType:flutter1.0 success");
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
      Log.e("UMLog", "setWraperType:flutter1.0" + e.toString());
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    applicationContext = null;
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (!versionMatch) {
      Log.e("UMLog", "onMethodCall:" + call.method + ":安卓SDK版本过低，请升级至8以上");
    }
    try {
      List args = (List) call.arguments;
      switch (call.method) {
        case "getPlatformVersion":
          result.success("Android " + android.os.Build.VERSION.RELEASE);
          break;
        case "initCommon":
          initCommon(args);
          break;
        case "onEvent":
          onEvent(args);
          break;
        case "onProfileSignIn":
          onProfileSignIn(args);
          break;
        case "onProfileSignOff":
          onProfileSignOff();
          break;
        case "setPageCollectionModeAuto":
          setPageCollectionModeAuto();
          break;
        case "setPageCollectionModeManual":
          setPageCollectionModeManual();
          break;
        case "onPageStart":
          onPageStart(args);
          break;
        case "onPageEnd":
          onPageEnd(args);
          break;
        case "reportError":
          reportError(args);
          break;
        default:
          result.notImplemented();
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      Log.e("Umeng", "Exception:" + e.getMessage());
    }
  }

  private void initCommon(List args) {
    String appkey = (String) args.get(0);
    String channel = (String) args.get(2);
    String pushSecret = args.size() > 3 ? (String) args.get(3) : null;

    UMConfigure.init(applicationContext, appkey, channel, UMConfigure.DEVICE_TYPE_PHONE, pushSecret);
  }

  private void onEvent(List args) {
    String event = (String) args.get(0);
    Map map = args.size() > 1 ? (Map) args.get(1) : null;
    MobclickAgent.onEventObject(applicationContext, event, map);
  }

  private void onProfileSignIn(List args) {
    String userID = (String) args.get(0);
    MobclickAgent.onProfileSignIn(userID);
    Log.i("UMLog", "onProfileSignIn:" + userID);
  }

  private void onProfileSignOff() {
    MobclickAgent.onProfileSignOff();
    Log.i("UMLog", "onProfileSignOff");
  }

  private void setPageCollectionModeAuto() {
    MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    Log.i("UMLog", "setPageCollectionModeAuto");
  }

  private void setPageCollectionModeManual() {
    MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL);
    Log.i("UMLog", "setPageCollectionModeManual");
  }

  private void onPageStart(List args) {
    String event = (String) args.get(0);
    MobclickAgent.onPageStart(event);
    Log.i("UMLog", "onPageStart:" + event);
  }

  private void onPageEnd(List args) {
    String event = (String) args.get(0);
    MobclickAgent.onPageEnd(event);
    Log.i("UMLog", "onPageEnd:" + event);
  }

  private void reportError(List args) {
    String error = (String) args.get(0);
    MobclickAgent.reportError(applicationContext, error);
    Log.i("UMLog", "reportError:" + error);
  }
}