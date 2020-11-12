package com.abcd.wifimanager.routerlogin.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewFontStyle extends TextView {
    public TextViewFontStyle(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public TextViewFontStyle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TextViewFontStyle(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Limerick-Regular.ttf"));
    }
}
