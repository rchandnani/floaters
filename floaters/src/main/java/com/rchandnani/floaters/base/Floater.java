package com.rchandnani.floaters.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by rchandnani on 19/10/17
 */

public abstract class Floater extends View {

    private View view;

    public Floater(Context context) {
        super(context);
        view = LayoutInflater.from(context).inflate(getLayoutId(), null);
        onViewCreated(view);
    }

    public View getView() {
        return view;
    }

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void onViewCreated(View view);

    public abstract Bundle onSaveInstance();

    public abstract void onRecreateInstance();

}