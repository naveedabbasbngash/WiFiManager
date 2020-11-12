package com.abcd.wifimanager.routerlogin.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonFontStyle extends Button {
    public ButtonFontStyle(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public ButtonFontStyle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ButtonFontStyle(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Limerick-Regular.ttf"));
    }
}
