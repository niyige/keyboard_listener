package com.oyy.keyboard_listener;

import android.app.Activity;
import android.app.Application;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.EventChannel.EventSink;
import io.flutter.plugin.common.PluginRegistry.Registrar;


/**
 * 改编自  https://pub.dev/packages/keyboard_visibility
 *
 */

public class KeyboardListener implements ViewTreeObserver.OnGlobalLayoutListener,
        Application.ActivityLifecycleCallbacks, EventChannel.StreamHandler {

    View mainLayout;

    boolean isShow; // show or hide keyboard

    EventSink eventSink;

    Registrar registrar;

    static final String KEYBOARD_CHANNEL = "mq://oyy/keyboardListener";

    KeyboardListener(Registrar registrar) {

        this.registrar = registrar;

    }

    // entrance
    public static void registerWith(Registrar registrar) {
        KeyboardListener listener = new KeyboardListener(registrar);
        //
        EventChannel eventChannel = new EventChannel(registrar.messenger(), KEYBOARD_CHANNEL);
        eventChannel.setStreamHandler(listener);
        registrar.activity().getApplication().registerActivityLifecycleCallbacks(listener);
    }

    @Override
    public void onGlobalLayout() {
        Rect outRect = new Rect();
        mainLayout.getWindowVisibleDisplayFrame(outRect);

        isShow = outRect.height() / mainLayout.getRootView().getHeight() < 0.7 ? true : false;

        // start
        eventSink.success(isShow);
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mainLayout = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    @Override
    public void onListen(Object o, EventSink eventSink) {
        this.eventSink = eventSink;
    }

    @Override
    public void onCancel(Object o) {
        this.eventSink = null;
    }


    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        UnRegister();

    }

    public void UnRegister() {
        if (mainLayout != null) {

            mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            mainLayout = null;
        }

    }


}
