package com.abcd.wifimanager.routerlogin.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewFontStyleSemiBold extends AppCompatTextView {
    public TextViewFontStyleSemiBold(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public TextViewFontStyleSemiBold(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TextViewFontStyleSemiBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Limerick-Regular.ttf"));
    }
}
