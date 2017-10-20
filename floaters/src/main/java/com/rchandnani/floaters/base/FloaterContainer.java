package com.rchandnani.floaters.base;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by rchandnani on 19/10/17
 */

public class FloaterContainer implements View.OnTouchListener {

    private Context context;
    private Stack<Floater> backStack;
    private FrameLayout container;
    private static FloaterContainer instance;
    private HashMap<Floater, Bundle> states;

    private FloaterContainer(Context context) {
        this.context = context;
        this.backStack = new Stack<>();
        this.states = new HashMap<>();
    }

    public static void init(Context context) {
        instance = new FloaterContainer(context);
    }

    public static FloaterContainer getInstance() {
        if (null == instance) {
            throw new RuntimeException("Please initialize the FloaterContainer before trying to get the instance!");
        }
        return instance;
    }

    public void push(Floater floater) {
        if (null == container) {
            setupContainer();
        }
        if (!backStack.isEmpty()) {
            Floater currentTop = backStack.peek();
            states.put(currentTop, currentTop.onSaveInstance());
        }
        if (container.getChildCount() > 0) {
            container.removeAllViews();
        }
        container.addView(floater.getView(), viewLayoutParams());
        backStack.add(floater);
        if (container.isShown()) {
            windowManager().updateViewLayout(container, containerLayoutParams());
        } else {
            windowManager().addView(container, containerLayoutParams());
        }

    }

    private ViewGroup.LayoutParams viewLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private WindowManager.LayoutParams containerLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        return params;
    }

    private void setupContainer() {
        container = new FrameLayout(context);
        container.setLayoutParams(viewLayoutParams());
        container.setOnTouchListener(this);
    }

    private WindowManager windowManager() {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            removeContainer();
            return true;
        }
        return false;
    }

    private void removeContainer() {
        if (null != container && container.isShown()) {
            windowManager().removeView(container);
        }
    }

    public void back() {
        backStack.pop();
        if (!backStack.isEmpty()) {
            container.removeAllViews();
            Floater currentTop = backStack.pop();
            container.addView(currentTop.getView(), viewLayoutParams());
            windowManager().updateViewLayout(container, containerLayoutParams());
        } else {
            removeContainer();
        }
    }
}