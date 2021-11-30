package com.nativeopenrn;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;

import com.facebook.react.PackageList;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.soloader.SoLoader;

import java.util.List;

public class MyReactActivity extends ReactActivity {
    private static final String TAG = "MainReactActivity";
    /**
     * Returns the name of the main component registered from JavaScript. This is used to schedule
     * rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "MyApp";
    }

}