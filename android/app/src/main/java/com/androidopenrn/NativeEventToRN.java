package com.androidopenrn;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.events.ReactEventEmitter;

import org.jetbrains.annotations.NotNull;

public class NativeEventToRN extends ReactContextBaseJavaModule implements LifecycleEventListener {
    private final ReactApplicationContext mReactContext;
    private static final String TAG = "NativeEventToRN";

    public NativeEventToRN(@Nullable @org.jetbrains.annotations.Nullable ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
        reactContext.addLifecycleEventListener(this);
    }

    @NonNull
    @NotNull
    @Override
    public String getName() {
        return "NativeEventToRN";
    }

    public void sendEvent(String eventName, String msg) {
        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, msg);
    }


    @Override
    public void onHostResume() {
        Log.d(TAG, "onHostResume: ");
        sendEvent("push", "onHostResume");
    }

    @Override
    public void onHostPause() {
        Log.d(TAG, "onHostPause: ");
        sendEvent("push", "onHostPause");
    }

    @Override
    public void onHostDestroy() {
        Log.d(TAG, "onHostDestroy: ");
        sendEvent("push", "onHostDestroy");
    }

    @ReactMethod
    public void getIntentData(Promise promise) {
        try{
            Intent intent = getCurrentActivity().getIntent();
            String msg = intent.getStringExtra("extrData");
            promise.resolve(msg);
        } catch (Throwable e) {
            promise.resolve(e);
        }

    }
    // 退出RN
    @ReactMethod
    public void popReactNative(Promise promise) {
        try{
            Activity activity = getCurrentActivity();
            activity.finish();
        } catch (Throwable e) {
            promise.resolve(e);
        }

    }
}
